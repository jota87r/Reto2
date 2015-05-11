/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.j.backend.configuration;

import com.j.backend.components.SimpleCORSFilter;
import com.j.mom.MomInitializer;
import java.util.EnumSet;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 *
 * @author jonatan
 */
public class BackendInitializer implements WebApplicationInitializer {
  
  @Override
  public void onStartup(ServletContext container) throws ServletException {
    AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
    context.register(BackendConfiguration.class);
    context.setServletContext(container);
    
    ServletRegistration.Dynamic servlet = container.addServlet("dispatcher", new DispatcherServlet(context));
    servlet.setLoadOnStartup(1);
    servlet.addMapping("/");
    
    FilterRegistration.Dynamic filter = container.addFilter("SimpleCORSFilter", new SimpleCORSFilter());
    filter.addMappingForServletNames(EnumSet.of(DispatcherType.REQUEST), true, "/*");
    
    startMomClient();
  }
  
  private void startMomClient() {
    MomInitializer.instance().start();
  }
}