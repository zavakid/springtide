package com.zavakid.springtide.example.todolist

import javax.servlet.ServletContext

import com.zavakid.springtide.example.todolist.config.SpringMvcConfig
import org.springframework.web.WebApplicationInitializer
import org.springframework.web.context.ContextLoaderListener
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext
import org.springframework.web.servlet.DispatcherServlet

/**
 *
 * @author zebin.xuzb 2014-09-22
 */
class ServletBootstrap extends WebApplicationInitializer {

  override def onStartup(sc: ServletContext): Unit = {
    val context = getWebContext

    sc.addListener(new ContextLoaderListener(context))
    val dispatcher = sc.addServlet("spring-dispatcher", new DispatcherServlet(context))
    dispatcher.addMapping("/*")
  }


  def getWebContext: AnnotationConfigWebApplicationContext = {
    val context = new AnnotationConfigWebApplicationContext()
    context.register(classOf[SpringMvcConfig])
    context
  }

}
