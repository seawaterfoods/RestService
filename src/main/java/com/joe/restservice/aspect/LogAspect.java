package com.joe.restservice.aspect;

import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//@Aspect:將其定義為切面class
//@Component:實體管理的註解，可使spring辨識到該class


@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

//    定義切入點
    @Pointcut("execution(* com.joe.restservice.api.LogTestApi.log(..))")
    public void log(){
    }

    @Before("log()")
    public void doBefore(){
        logger.info("--------------doBefore 1------------------");
    }

    @After("log()")
    public void doAfter(){
        logger.info("--------------doAfter 2------------------");
    }

    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturning(Object result){
        logger.info("-----------doAfterReturning------------ :內容{}",result);
    }
}


