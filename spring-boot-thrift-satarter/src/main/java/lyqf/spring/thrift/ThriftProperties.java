package lyqf.spring.thrift;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by chenlang on 2017/3/3.
 */
@ConfigurationProperties(
        prefix = "thrift.properties"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ThriftProperties {

    private String serverType = "TThreadPoolServer";//for TSimpleServer,TThreadPoolServer,TNonblockingServer,THsHaServer,TThreadedSelectotServer

    private Integer port = 7911;

    private String serviceImplClassUrls;// the service Impl

}
