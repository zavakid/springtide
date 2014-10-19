package com.zavakid.springtide.args

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.web.accept.ContentNegotiationManager
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.{HandlerMethodArgumentResolver, ModelAndViewContainer}

/**
 *
 * @author zebin.xuzb 2014-10-19
 */
class AcceptsArgResolver extends HandlerMethodArgumentResolver {

  @Autowired
  var contentNegotiationManager: ContentNegotiationManager = _

  override def supportsParameter(parameter: MethodParameter): Boolean =
    classOf[Accepts].isAssignableFrom(parameter.getParameterType)

  override def resolveArgument(parameter: MethodParameter, mavContainer: ModelAndViewContainer, webRequest: NativeWebRequest, binderFactory: WebDataBinderFactory): AnyRef = {
    val mediaType = contentNegotiationManager.resolveMediaTypes(webRequest).get(0)
    Accepts(mediaType)
  }
}


case class Accepts(mediaType: MediaType)