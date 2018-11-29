# springboot-thrift-starter
thrift starter设计方式，提供了两种方式：<br>
1.通过自定义注解@ThriftService（该注解上有@Component），然后在tomcat启动的时候，获取所有被@ThriftService标识的类，然后通过ExecutorService开户一个异步线程来启动一个TServer<br>
2.另一种实现是通过config的配置文件直接配置thrift的实现类的位置，然后通过反射来获取相关的信息，后续同上
 <br>
 如果你有@ThriftService标识的thrift serivceimpl且又在config文件指定了thrift serivceimpl，默认只会有标识了@ThriftService的实现类会生效。此外还提供了一些自定义设置，详见ThriftProperties类。<br>
example使用步骤,该例子是基于spring-cloud的 ：<br>
1.将spring-boot-thrift-satarter 模块通过maven install命令将期jar安装到本地<br>
2.启动example下面的netflix-service-discover-demo项目<br>
3.启动example下面的spring-boot-thrift-server-demo项目<br>
4.启动example下面的spring-boot-thrift-client-demo项目<br>
5.访问http://localhost:4445/hello 查看client通过thrift协议调用thrift server结果
<br>
