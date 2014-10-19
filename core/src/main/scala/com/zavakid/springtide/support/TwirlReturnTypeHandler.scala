package com.zavakid.springtide.support

import javax.servlet.http.HttpServletResponse

import org.springframework.core.MethodParameter
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.{HandlerMethodReturnValueHandler, ModelAndViewContainer}
import play.twirl.api.BufferedContent

/**
 *
 * @author zebin.xuzb 2014-10-18
 */
class TwirlReturnTypeHandler extends HandlerMethodReturnValueHandler {

  override def supportsReturnType(returnType: MethodParameter): Boolean =
    classOf[BufferedContent[_]].isAssignableFrom(returnType.getParameterType)

  override def handleReturnValue(returnValue: scala.Any, returnType: MethodParameter, mavContainer: ModelAndViewContainer, webRequest: NativeWebRequest): Unit = {
    val bufferedContent = returnValue.asInstanceOf[BufferedContent[_]]
    val response = webRequest.getNativeResponse(classOf[HttpServletResponse])
    TwirlReturnTypeHandler.writeToResponse(bufferedContent, response)
    mavContainer.setRequestHandled(true)
  }


}

object TwirlReturnTypeHandler {

  def writeToResponse(bufferedContent: BufferedContent[_], response: HttpServletResponse) {
    response.setContentType(bufferedContent.contentType)
    response.getWriter.write(bufferedContent.body)
  }
}
