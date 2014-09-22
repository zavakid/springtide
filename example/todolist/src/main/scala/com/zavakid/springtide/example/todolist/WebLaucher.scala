package com.zavakid.springtide.example.todolist

import org.eclipse.jetty.annotations.AnnotationConfiguration
import org.eclipse.jetty.server.{HttpConfiguration, HttpConnectionFactory, Server, ServerConnector}
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.util.resource.Resource
import org.eclipse.jetty.webapp.WebAppContext

/**
 *
 * @author zebin.xuzb 2014-09-22
 */

class WebLaucher

object WebLaucher {

  def main(args: Array[String]) {
    val server = new Server()

    val connectionConfig = new HttpConfiguration()
    connectionConfig.setSendServerVersion(false)
    connectionConfig.setSendXPoweredBy(false)
    val connector = new ServerConnector(server, new HttpConnectionFactory(connectionConfig))
    connector.setPort(8080)

    server.addConnector(connector)

    val url = classOf[WebLaucher].getProtectionDomain.getCodeSource.getLocation.toExternalForm
    val webApp = new WebAppContext()
    webApp.setConfigurations(Array(new AnnotationConfiguration()))
    webApp.setContextPath("/")
    webApp.setParentLoaderPriority(true)
    webApp.setResourceBase(url + "webapp/")
    webApp.getMetaData.addContainerResource(Resource.newResource(url))

    server.setHandler(webApp)
    server.start()
    sys.addShutdownHook {
      server.stop()
    }
    server.join()
  }

}