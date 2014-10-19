package com.zavakid.springtide.example.todolist

import java.util.EnumSet
import javax.servlet.{DispatcherType, ServletContext}

import com.zavakid.springtide.example.todolist.config.SpringMvcConfig
import com.zavakid.springtide.support.AcceptsResolveFilter
import org.springframework.web.WebApplicationInitializer
import org.springframework.web.context.ContextLoaderListener
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext
import org.springframework.web.filter.{DelegatingFilterProxy, CharacterEncodingFilter}
import org.springframework.web.servlet.DispatcherServlet

/**
 *
 * @author zebin.xuzb 2014-09-22
 */
class ServletBootstrap extends WebApplicationInitializer {

  private val DISPATCH_NAME = "spring-dispatcher"

  override def onStartup(sc: ServletContext): Unit = {
    val context = getWebContext

    sc.addListener(new ContextLoaderListener(context))
    val dispatcher = sc.addServlet(DISPATCH_NAME, new DispatcherServlet(context))
    dispatcher.setInitParameter("throwExceptionIfNoHandlerFound", "true")
    dispatcher.addMapping("/*")

    val charencodingFilter = sc.addFilter("charencodingFilter", classOf[CharacterEncodingFilter])
    charencodingFilter.setInitParameter("encoding", "UTF-8")
    charencodingFilter.setInitParameter("forceEncoding", "true")
    charencodingFilter.addMappingForUrlPatterns(EnumSet.allOf(classOf[DispatcherType]), true, "/*")

    val acceptsResolveFilter = sc.addFilter("acceptsResolveFilter", new DelegatingFilterProxy("acceptsResolveFilter"))
    acceptsResolveFilter.addMappingForServletNames(EnumSet.allOf(classOf[DispatcherType]), true, DISPATCH_NAME)
  }


  def getWebContext: AnnotationConfigWebApplicationContext = {
    val context = new AnnotationConfigWebApplicationContext()
    context.register(classOf[SpringMvcConfig])
    context
  }

}
