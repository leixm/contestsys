package com.app.tools;



import org.apache.log4j.Logger;
import org.junit.Test;

import junit.framework.TestCase;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)	// 不能是PowerMock等别的class，否则无法识别spring的配置文件
@ContextConfiguration("file:resource/spring/applicationContext.xml")	// 读取spring配置文件
public class test extends TestCase {
	private static final Logger logger = org.apache.log4j.Logger.getLogger(test.class);
	
	@Test
	public void test() {
		logger.info("Logger works!");
	}
	
}
