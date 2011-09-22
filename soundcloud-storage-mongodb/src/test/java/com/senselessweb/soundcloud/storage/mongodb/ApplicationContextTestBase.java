package com.senselessweb.soundcloud.storage.mongodb;

import org.junit.Before;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;

public class ApplicationContextTestBase
{

	protected ConfigurableApplicationContext context;
	
	@Before
	public void setupSpringApplicationContext()
	{
		 this.context = new ClassPathXmlApplicationContext(
				 "test-spring-datasource.xml", "soundcloud-strorage-applicationcontext.xml");
		 this.context.getBean(MongoTemplate.class).getDb().dropDatabase();
		 
	}
}
