package edu.taesu.mySpring.common.context.aop;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import edu.taesu.mySpring.common.context.aop.logging.TraceLoggingInAOP;


@Configuration
@EnableAspectJAutoProxy
public class AOPContext {

	@Bean(name = "traceLoggingInAOP")
	public TraceLoggingInAOP getLoggingInAOP() {
		return new TraceLoggingInAOP();
	}
}