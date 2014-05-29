package com.core.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyAop {

	@Pointcut("execution(public * com.test.*.login(..))")  // 统配方式   
	public void point(){
		
	}
	
	@Before("point()")
	public void befor(){
		//System.out.println("befor");
	}
	
	@After("point()")
	public void after(){
		//System.out.println("after");
	}
}
