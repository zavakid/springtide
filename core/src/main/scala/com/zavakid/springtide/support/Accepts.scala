package com.zavakid.springtide.support

import javax.servlet.FilterChain
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.web.accept.{ContentNegotiationManager, ContentNegotiationStrategy}
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.{NativeWebRequest, ServletWebRequest}
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.method.support.{HandlerMethodArgumentResolver, ModelAndViewContainer}

/**
 *
 * @author zebin.xuzb 2014-10-19
 */

case class Accepts(mediaType: MediaType)

object Accepts {

  private val CACHE_KEY = "__resolved_media_type__"

  def resolve(contentNegotiationStrategy: ContentNegotiationStrategy, webRequest: NativeWebRequest): Accepts = {
    val request = webRequest.getNativeRequest(classOf[HttpServletRequest])
    Option(request.getAttribute(CACHE_KEY)).map(_.asInstanceOf[Accepts]).getOrElse {
      val mediaTypes = contentNegotiationStrategy.resolveMediaTypes(webRequest)
      val mediaType = if (mediaTypes.size() > 0) mediaTypes.get(0) else MediaType.TEXT_HTML
      val accepts = Accepts(mediaType)
      request.setAttribute(CACHE_KEY, accepts)
      Accepts(mediaType)
    }
  }

  def resolved(request: HttpServletRequest): Option[Accepts] =
    Option(request.getAttribute(CACHE_KEY)).map(_.asInstanceOf[Accepts])

}

class AcceptsArgResolver extends HandlerMethodArgumentResolver {
  @Autowired
  var contentNegotiationManager: ContentNegotiationManager = _

  override def supportsParameter(parameter: MethodParameter): Boolean =
    classOf[Accepts].isAssignableFrom(parameter.getParameterType)

  override def resolveArgument(parameter: MethodParameter, mavContainer: ModelAndViewContainer, webRequest: NativeWebRequest, binderFactory: WebDataBinderFactory): AnyRef =
    Accepts.resolve(contentNegotiationManager, webRequest)
}

class AcceptsResolveFilter extends OncePerRequestFilter {

  @Autowired
  var contentNegotiationManager: ContentNegotiationManager = _

  override def doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain): Unit = try{
    val webRequest = new ServletWebRequest(request, response)
    Accepts.resolve(contentNegotiationManager, webRequest)
  }finally {
    filterChain.doFilter(request, response)
  }
}

