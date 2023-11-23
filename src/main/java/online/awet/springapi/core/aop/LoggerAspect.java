package online.awet.springapi.core.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class LoggerAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggerAspect.class);

    @Before("online.awet.springapi.core.aop.Pointcuts.forAll()")
    public void logBefore(JoinPoint joinPoint) {
        String joinpointClass = joinPoint.getSignature().getDeclaringTypeName();
        String joinpointMethod = joinPoint.getSignature().getName();

        logger.info("Executing {}.{}", joinpointClass, joinpointMethod);
    }
}
