package com.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.UUID;

public class InitialID implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        servletContext.setAttribute("uuid",uuid);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
