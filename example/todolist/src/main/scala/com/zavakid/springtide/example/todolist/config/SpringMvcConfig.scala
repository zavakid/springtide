package com.zavakid.springtide.example.todolist.config

import java.util
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

import com.alibaba.druid.pool.DruidDataSource
import com.zavakid.springtide.args.AcceptsArgResolver
import com.zavakid.springtide.example.todolist.config.SpringMvcConfig._
import com.zavakid.springtide.support.TwirlReturnTypeHandler
import com.zavakid.springtide.util.JacksonUtil
import com.zavakid.springtide.view.JsonViewResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.{Bean, ComponentScan, Configuration, Description}
import org.springframework.core.env.Environment
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.http.MediaType
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.orm.jpa.{JpaTransactionManager, LocalContainerEntityManagerFactoryBean}
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate
import org.springframework.web.method.support.{HandlerMethodArgumentResolver, HandlerMethodReturnValueHandler}
import org.springframework.web.servlet.ViewResolver
import org.springframework.web.servlet.config.annotation.{ContentNegotiationConfigurer, ResourceHandlerRegistry, WebMvcConfigurationSupport}
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver
import org.thymeleaf.spring4.SpringTemplateEngine
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver
import org.thymeleaf.spring4.view.ThymeleafViewResolver

/**
 *
 * @author zebin.xuzb 2014-09-22
 */
@Configuration
//@EnableWebMvc 直接继承 WebMvcConfigurationSupport 就不需要此注解
@ComponentScan(basePackages = Array(
  "com.zavakid.springtide.example.todolist.controller",
  "com.zavakid.springtide.example.todolist.dao",
  "com.zavakid.springtide.example.todolist.service"
))
@EnableJpaRepositories(Array("com.zavakid.springtide.example.todolist.repository"))
class SpringMvcConfig extends WebMvcConfigurationSupport {


  @Autowired
  var env: Environment = _

  @Bean(initMethod = "init", destroyMethod = "close")
  def dataSource(): DataSource = {
    val ds = new DruidDataSource()
    ds.setUrl("jdbc:mysql://127.0.0.1:3306/c100k")
    ds.setUsername("c100k")
    ds.setPassword("c100k")
    ds.setMaxActive(10)
    ds.setFilters("stat,slf4j")
    System.setProperty("druid.log.stmt.executableSql", "true")
    ds
  }

  @Bean
  def entityManagerFactory: EntityManagerFactory = {
    val adapter = new HibernateJpaVendorAdapter()
    adapter.setGenerateDdl(false)
    adapter.setShowSql(true)
    val factory = new LocalContainerEntityManagerFactoryBean()
    factory.setJpaVendorAdapter(adapter)
    factory.setPackagesToScan("com.zavakid.springtide.example.todolist.model")
    factory.setDataSource(dataSource())
    factory.afterPropertiesSet()
    factory.getObject
  }

  @Bean
  def transactionManager(): PlatformTransactionManager = {
    val txManager = new JpaTransactionManager()
    txManager.setEntityManagerFactory(entityManagerFactory)
    txManager
  }

  @Bean
  def transactionTemplate(): TransactionTemplate = {
    val template = new TransactionTemplate()
    template.setTransactionManager(transactionManager())
    template.afterPropertiesSet()
    template
  }


  override def addReturnValueHandlers(returnValueHandlers: util.List[HandlerMethodReturnValueHandler]): Unit = {
    super.addReturnValueHandlers(returnValueHandlers)
    returnValueHandlers.add(0, new TwirlReturnTypeHandler)
  }

  override def configureContentNegotiation(configurer: ContentNegotiationConfigurer): Unit = {
    super.configureContentNegotiation(configurer)
    configurer.favorPathExtension(true)
      .ignoreAcceptHeader(true)
      .useJaf(false)
      .defaultContentType(MediaType.TEXT_HTML)
  }


  override def addArgumentResolvers(argumentResolvers: util.List[HandlerMethodArgumentResolver]): Unit = {
    super.addArgumentResolvers(argumentResolvers)
    argumentResolvers.add(0, acceptsArgResolver)
  }

  @Bean
  def acceptsArgResolver = new AcceptsArgResolver

  @Bean
  def contentNegotiatingViewResolver(): ViewResolver = {
    val resolver = new ContentNegotiatingViewResolver()
    resolver.setContentNegotiationManager(mvcContentNegotiationManager())
    val jsonViewResolver: ViewResolver = new JsonViewResolver()
    import scala.collection.JavaConversions._
    import scala.collection._
    resolver.setViewResolvers(mutable.MutableList(thymeleafViewResolver, jsonViewResolver))
    resolver
  }

  //create thymeleaf view Resolver
  def thymeleafViewResolver: ViewResolver = {
    val viewResolver = new ThymeleafViewResolver
    viewResolver.setTemplateEngine(thymeleafEngine)
    viewResolver.setCharacterEncoding("UTF-8")
    val ts: java.lang.Long = System.currentTimeMillis
    val staticVariables = Map(
      "jsons" -> JacksonUtil.getClass,
      "ts" -> ts
    )
    import scala.collection.JavaConversions._
    viewResolver.setStaticVariables(staticVariables)
    viewResolver
  }

  @Bean
  @Description("thyme's template engine")
  def thymeleafEngine: SpringTemplateEngine = {
    val engine: SpringTemplateEngine = new SpringTemplateEngine
    engine.setTemplateResolver(thymeleafResourceResolver)
    //engine.addDialect(new LayoutDialect)
    engine
  }

  @Bean
  @Description("thyme's template resolver with spring resource")
  def thymeleafResourceResolver: SpringResourceTemplateResolver = {
    val resourceResolver: SpringResourceTemplateResolver = new SpringResourceTemplateResolver
    resourceResolver.setPrefix("classpath:/webapp/template/")
    resourceResolver.setSuffix(".html")
    resourceResolver.setTemplateMode("LEGACYHTML5")
    resourceResolver.setCharacterEncoding("UTF-8")
    resourceResolver.setCacheable(env.acceptsProfiles(ONLINE))
    resourceResolver
  }

  override def addResourceHandlers(registry: ResourceHandlerRegistry): Unit = {
    super.addResourceHandlers(registry)
    registry.addResourceHandler("/static/**").addResourceLocations("classpath:/webapp/static/")

    if (!env.acceptsProfiles(ONLINE))
      registry.addResourceHandler("/demo/**").addResourceLocations("classpath:/webapp/template/")
  }

}

object SpringMvcConfig {
  val DEV = "dev"
  val ONLINE = "online"
}
