package com.senselessweb.soundcloud.storage.mongodb;

import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Abstract test class for test cases that need a spring {@link ApplicationContext}.
 * 
 * @author thomas
 */
public class ApplicationContextTestBase
{

	/**
	 * The {@link ApplicationContext}.
	 */
	protected ApplicationContext context;
	
	
	/**
	 * Initializes the {@link ApplicationContext}. 
	 */
	@Before
	public final void setupSpringApplicationContext()
	{
		 this.context = new ClassPathXmlApplicationContext(
				 "test-spring-datasource.xml", "soundcloud-strorage-applicationcontext.xml");
		 this.context.getBean(MongoTemplate.class).getDb().dropDatabase();
		 
	}
}
