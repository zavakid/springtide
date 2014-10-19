package com.zavakid.springtide.example.todolist.util

import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import com.zavakid.springtide.args.Accepts
import com.zavakid.springtide.support.{TwirlHandleExceptionResolver, TwirlReturnTypeHandler}
import com.zavakid.springtide.twirl.Json
import org.springframework.http.MediaType
import play.twirl.api.Html

/**
 *
 * @author zebin.xuzb 2014-10-19
 */
object Exceptions {

  def handleWebException(code: Int, request: HttpServletRequest, response: HttpServletResponse,
                         handler: scala.Any, accepts: Accepts, ex: Exception): Unit = code match {
    case -1 | 500 =>
      writeWithAccept(accepts, code, response, html.page500(ex), Json.fail())
    case 404 =>
      writeWithAccept(accepts, code, response, html.page404(ex), Json.fail(404, "page not found"))
    case 400 | 405 | 415 | 406 =>
      writeWithAccept(accepts, code, response, html.page400(ex), Json.fail(400, "bad request"))
    case _ => writeWithAccept(accepts, code, response, html.page500(ex), Json.fail())
  }

  val webExceptionHandler: TwirlHandleExceptionResolver.TwirlExceptionHandler =
    handleWebException


  private def writeWithAccept(accepts: Accepts, code: Int, response: HttpServletResponse, html: Html, json: Json[_]) = {
    response.setStatus(code)
    accepts.mediaType match {
      case MediaType.TEXT_HTML =>
        TwirlReturnTypeHandler.writeToResponse(html, response)
      case MediaType.APPLICATION_JSON => TwirlReturnTypeHandler.writeToResponse(json, response)
      case _ => TwirlReturnTypeHandler.writeToResponse(html, response)
    }
  }

}
