package kr.co.itcen.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


@WebListener
public class ContextLoadListener implements ServletContextListener {

    
   
	
    public void contextDestroyed(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    }

	
    public void contextInitialized(ServletContextEvent sevletcontextevent)  { 
    	
    	String contextConfigLocation =  sevletcontextevent.getServletContext().getInitParameter("");
        System.out.println("MySite2 Application Starts With " + contextConfigLocation);
    }
	
}
