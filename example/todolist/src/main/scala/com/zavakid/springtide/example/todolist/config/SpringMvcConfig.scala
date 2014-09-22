package com.zavakid.springtide.example.todolist.config

import org.springframework.context.annotation.{ComponentScan, Configuration}
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
class SpringMvcConfig extends WebMvcConfigurationSupport {

}
