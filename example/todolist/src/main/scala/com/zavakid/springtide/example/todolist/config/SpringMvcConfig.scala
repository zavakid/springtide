package com.zavakid.springtide.example.todolist.config

import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

import com.alibaba.druid.pool.DruidDataSource
import org.springframework.context.annotation.{Bean, ComponentScan, Configuration}
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.orm.jpa.{JpaTransactionManager, LocalContainerEntityManagerFactoryBean}
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate
import org.springframework.web.servlet.config.annotation.{EnableWebMvc, WebMvcConfigurationSupport}

/**
 *
 * @author zebin.xuzb 2014-09-22
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = Array(
  "com.zavakid.springtide.example.todolist.controller",
  "com.zavakid.springtide.example.todolist.dao",
  "com.zavakid.springtide.example.todolist.service"
))
@EnableJpaRepositories(Array("com.zavakid.springtide.example.todolist.repository"))
class SpringMvcConfig extends WebMvcConfigurationSupport {


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
}
