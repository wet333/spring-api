package online.awet.springapi.core.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    @Pointcut("execution(* online.awet.springapi.features..*(..))")
    public void forAll() {
        // This pointcut intercepts all methods executed in any class/bean from the features package
    }

    @Pointcut("execution(* online.awet.springapi.features..*Controller.*(..))")
    public void forController() {
        // This pointcut intercepts all methods executed by a Controller
    }

}
