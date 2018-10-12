package edu.asupoly.ser422.restexample.services;

import java.util.Properties;

import edu.asupoly.ser422.restexample.services.impl.RDBMBooktownServiceImpl;
import edu.asupoly.ser422.restexample.services.impl.SimpleBooktownServiceImpl;

// we'll build on this later
public class BooktownServiceFactory {
    private BooktownServiceFactory() {}

    public static BooktownService getInstance() {
	// should really read from a property here
	if (__bookservice == null) {
		 //TODO Change to RDBM here
		__bookservice = new RDBMBooktownServiceImpl(); 
	  }
	return __bookservice;
    }

    private static BooktownService __bookservice;
    
	// This class is going to look for a file named booktown.properties in the classpath
	// to get its initial settings
	static {
		try {
			Properties dbProperties = new Properties();
			Class<?> initClass = null;
			dbProperties.load(BooktownServiceFactory.class.getClassLoader().getResourceAsStream("/booktown.properties"));
			String serviceImpl = dbProperties.getProperty("serviceImpl");
			if (serviceImpl != null) {
				initClass = Class.forName(serviceImpl);
			} else {
				 //TODO Change to RDBM here
				initClass = Class.forName("edu.asupoly.ser422.restexample.services.impl.RDBMBooktownService");
			}
			__bookservice = (BooktownService)initClass.newInstance();
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
		}
	}
    
}
