package com.zavakid.springtide.example.todolist.controller

import org.springframework.web.bind.annotation.{RequestMapping, RestController}

@RestController
@RequestMapping(Array("/test"))
class TestController {

  @RequestMapping
  def test: String = "test-ok"

}
