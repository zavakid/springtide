package com.zavakid.springtide.example.todolist.controller

import javax.validation.Valid

import com.zavakid.springtide.dto.JsonDto
import com.zavakid.springtide.example.todolist.dto.TestForm
import com.zavakid.springtide.support.Accepts
import com.zavakid.springtide.twirl.Json
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.RequestMapping

import scala.util.control.NoStackTrace

@Controller
@RequestMapping(Array("/health"))
class HealthCheckController {

  @RequestMapping(Array("check"))
  def healthCheck(accept: Accepts) = accept.mediaType match {
    case MediaType.TEXT_HTML => html.health()
    case MediaType.APPLICATION_XML => xml.health()
    case MediaType.APPLICATION_JSON => Json(JsonDto("OK"))
  }

  @RequestMapping(Array("exception"))
  def exceptionHandle() = throw new RuntimeException with NoStackTrace

  @RequestMapping(Array("errorForm"))
  def errorForm(@Valid form: TestForm, bindingResult: BindingResult, model: Model) = {
    println(bindingResult)
    throw new RuntimeException with NoStackTrace
  }

}

