package com.zavakid.springtide.support

import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import com.zavakid.springtide.support.TwirlHandleExceptionResolver.TwirlExceptionHandler
import org.springframework.beans.{ConversionNotSupportedException, TypeMismatchException}
import org.springframework.http.MediaType
import org.springframework.http.converter.{HttpMessageNotReadableException, HttpMessageNotWritableException}
import org.springframework.validation.BindException
import org.springframework.web.bind.{MethodArgumentNotValidException, MissingServletRequestParameterException, ServletRequestBindingException}
import org.springframework.web.multipart.support.MissingServletRequestPartException
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException
import org.springframework.web.servlet.{HandlerExceptionResolver, ModelAndView, NoHandlerFoundException}
import org.springframework.web.{HttpMediaTypeNotAcceptableException, HttpMediaTypeNotSupportedException, HttpRequestMethodNotSupportedException}

import scala.beans.BeanProperty

/**
 *
 * @author zebin.xuzb 2014-10-19
 */
class TwirlHandleExceptionResolver extends HandlerExceptionResolver {


  @BeanProperty
  var defaultExceptionHandler: TwirlExceptionHandler = _

  override def resolveException(request: HttpServletRequest, response: HttpServletResponse, handler: scala.Any, ex: Exception): ModelAndView = {
    val accepts = Accepts(Accepts.resolved(request).map(_.mediaType).getOrElse(MediaType.TEXT_HTML))
    ex match {
      case _@(_: NoSuchRequestHandlingMethodException | _: NoHandlerFoundException) => defaultExceptionHandler(404, request, response, handler, accepts, ex)
      case _@(_: HttpRequestMethodNotSupportedException) => defaultExceptionHandler(405, request, response, handler, accepts, ex)
      case _@(_: HttpMediaTypeNotSupportedException) => defaultExceptionHandler(415, request, response, handler, accepts, ex)
      case _@(_: HttpMediaTypeNotAcceptableException) => defaultExceptionHandler(406, request, response, handler, accepts, ex)
      case _@(_: MissingServletRequestParameterException | _: ServletRequestBindingException |
              _: TypeMismatchException | _: HttpMessageNotReadableException | _: MethodArgumentNotValidException |
              _: MissingServletRequestPartException | _: BindException) => defaultExceptionHandler(400, request, response, handler, accepts, ex)
      case _@(_: ConversionNotSupportedException | _: HttpMessageNotWritableException) => defaultExceptionHandler(500, request, response, handler, accepts, ex)
      case _ => defaultExceptionHandler(-1, request, response, handler, accepts, ex)
    }
    TwirlHandleExceptionResolver.emptyModelAndView
  }
}

object TwirlHandleExceptionResolver {
  type TwirlExceptionHandler = (Int, HttpServletRequest, HttpServletResponse, scala.Any, Accepts, Exception) => Unit
  val emptyModelAndView = new ModelAndView()

  def apply(handler: TwirlExceptionHandler): TwirlHandleExceptionResolver = {
    val resolver = new TwirlHandleExceptionResolver()
    resolver.defaultExceptionHandler = handler
    resolver
  }

}
