package com.zavakid.springtide.example.todolist.controller

import com.zavakid.springtide.args.Accepts
import com.zavakid.springtide.dto.JsonDto
import com.zavakid.springtide.twirl.Json
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping(Array("/health"))
class HealthCheckController {

  @RequestMapping(Array("check"))
  def healthCheck(accept: Accepts) = accept.mediaType match {
    case MediaType.TEXT_HTML => html.health()
    case MediaType.APPLICATION_XML => xml.health()
    case MediaType.APPLICATION_JSON => Json(JsonDto("OK"))
  }


}
