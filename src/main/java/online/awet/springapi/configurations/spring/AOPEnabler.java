package online.awet.springapi.configurations.spring;

import online.awet.springapi.core.aop.LoggerAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AOPEnabler {

    // I have to declare the Aspect's beans here, otherwise they are not found

    @Bean
    public LoggerAspect loggerAspect() {
        return new LoggerAspect();
    }
}
