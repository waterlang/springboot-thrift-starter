package lyqf.spring.thrift;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.*;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.lang.reflect.Constructor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chenlang on 2017/3/3.
 */

@Slf4j
@Configuration
@EnableConfigurationProperties(ThriftProperties.class)
public class ThriftAutoConfiguration implements ServletContextInitializer,ApplicationContextAware{

    @Autowired
    TProtocolFactory tProtocolFactory;

    @Autowired
    private ThriftProperties thriftProperties;

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    private volatile  boolean thriftFlag = true;

    @Bean
    @ConditionalOnMissingBean
    public TProtocolFactory tProtocolFactory() {
        // this use binary protocol, you can use JSON or  others protocol
        return new TBinaryProtocol.Factory();
    }


        private ApplicationContext applicationContext;
        private Class interfcaeInnerClass;
        private Class serviceImplClass;
        Class<TProcessor> processorClass;



        private String TS="TSIMPLESERVER",TT="TTHREADPOOLSERVER",TN="TNONBLOCKINGSERVER",THS ="THSHASERVER",THH="TTHREADEDSELECTOTSERVER";
        private String BLOCKING = "blocking",NO_BLOCKING="noblocking";



        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.applicationContext = applicationContext;
        }

        @Override
        public void onStartup(ServletContext servletContext) throws ServletException {
            String[] beanNames = applicationContext.getBeanNamesForAnnotation(ThriftService.class);
            checkAnnotationBeans(beanNames);
            if(thriftFlag){// 通过注解找到thrift class
                log.info("find thrift class by annotation");
            }else {
                checkPropertiestBeans();
                if(!thriftFlag){
                    log.error("can't find thrift class ");
                    throw  new IllegalStateException("No Thrift Ifaces found");
                }
            }
        }


        private void checkPropertiestBeans(){
            String classUrl = thriftProperties.getServiceImplClassUrls();
            if(StringUtils.isNotBlank(classUrl)){
                 try {
                   serviceImplClass =   Class.forName(classUrl);
                   Class<?>[] handlerInterfaces  = serviceImplClass.getInterfaces();
                   hanndleClass(handlerInterfaces);
                   thriftFlag = true;
                 }catch (ClassNotFoundException e){
                     e.printStackTrace();
                     throw  new IllegalStateException("No Thrift Ifaces found,can't find the thrift class ,the class url :"+classUrl);
                 }
            }else{
                 throw  new IllegalStateException("No Thrift Ifaces found,thrift class url is null");
            }
        }

        /**
         *
         * @param thriftServiceBeans
         */
        private void checkAnnotationBeans(String[] thriftServiceBeans){
            for (String s: thriftServiceBeans){
                ThriftService t = applicationContext.findAnnotationOnBean(s,ThriftService.class);
                serviceImplClass = applicationContext.getBean(s).getClass();
                Class<?>[] handlerInterfaces  = serviceImplClass.getInterfaces();
                hanndleClass(handlerInterfaces);
            }
            if (interfcaeInnerClass == null) {
                thriftFlag = false;
            }
        }

        private void hanndleClass(Class<?>[] handlerInterfaces){
            for (Class<?> handlerInterfaceClass : handlerInterfaces) {
                if (!handlerInterfaceClass.getName().endsWith("$Iface")) {
                    continue;
                }

                Class  interfcaeClass = handlerInterfaceClass.getDeclaringClass();

                for (Class<?> innerClass : interfcaeClass.getDeclaredClasses()) {
                    if (!innerClass.getName().endsWith("$Processor")) {
                        continue;
                    }

                    if (!TProcessor.class.isAssignableFrom(innerClass)) {
                        continue;
                    }

                    if (interfcaeInnerClass != null) {
                        throw new IllegalStateException("Multiple Thrift Ifaces defined on handler");
                    }
                    interfcaeInnerClass = handlerInterfaceClass;
                    processorClass = (Class<TProcessor>)innerClass;
                    break;
                }
            }
        }

        @Bean
        public TServer tServer()  throws Exception{
            //发布服务
            Constructor cons = processorClass.getConstructor(interfcaeInnerClass);
            TProcessor processor = (TProcessor)cons.newInstance(serviceImplClass.newInstance());
            TServer server = createTServer(thriftProperties,processor);
            //start tServer
            executor.execute(() -> {
                log.info("starting server on port:{}",7911);
                server.serve();
            });
            return server;
        }



        private TServer createTServer(ThriftProperties thriftProperties,TProcessor processor) throws TTransportException {
            TServer tServer = null;

            if (TT.equals(thriftProperties.getServerType().toUpperCase())){
                TThreadPoolServer.Args arg = new TThreadPoolServer.Args(new TServerSocket(thriftProperties.getPort())).processor(processor);
                arg.protocolFactory(tProtocolFactory);
                tServer = new TThreadPoolServer(arg);
            } else if (TN.equals(thriftProperties.getServerType().toUpperCase())){
                tServer = new TNonblockingServer(new TNonblockingServer.Args(new TNonblockingServerSocket(thriftProperties.getPort())).processor(processor).protocolFactory(tProtocolFactory));
            } else if (THS.equals(thriftProperties.getServerType().toUpperCase())){
                tServer = new THsHaServer(new THsHaServer.Args(new TNonblockingServerSocket(thriftProperties.getPort())).processor(processor).protocolFactory(tProtocolFactory));
            }else if (THH.equals(thriftProperties.getServerType().toUpperCase())){
                tServer = new TThreadedSelectorServer(new TThreadedSelectorServer.Args(new TNonblockingServerSocket(thriftProperties.getPort())).processor(processor).protocolFactory(tProtocolFactory));
            }else{
                TThreadPoolServer.Args arg = new TThreadPoolServer.Args(new TServerSocket(thriftProperties.getPort())).processor(processor);
                arg.protocolFactory(tProtocolFactory);
                tServer = new TSimpleServer(arg);
            }
            return  tServer;
        }

}
