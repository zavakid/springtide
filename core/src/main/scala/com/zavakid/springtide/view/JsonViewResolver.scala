package com.zavakid.springtide.view

import java.io.{ByteArrayOutputStream, OutputStream}
import java.util
import java.util.Locale
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import com.fasterxml.jackson.core.JsonEncoding
import com.zavakid.springtide.dto.JsonDto
import com.zavakid.springtide.util.JacksonUtil
import org.apache.commons.lang3.StringUtils
import org.springframework.web.servlet.view.AbstractView
import org.springframework.web.servlet.{View, ViewResolver}

/**
 *
 * @author zebin.xuzb 2014-10-12
 */
class JsonViewResolver extends ViewResolver {

  import com.zavakid.springtide.view.JsonViewResolver._

  override def resolveViewName(viewName: String, locale: Locale): View =
    if (StringUtils.endsWithIgnoreCase(viewName, EXTENSION))
      new JsonView
    else
      null

}

object JsonViewResolver {

  private val EXTENSION = ".json"
  private val CONTENT_TYPE = "application/json"
  private val JSON_ENCODING = JsonEncoding.UTF8


  class JsonView extends AbstractView {
    setContentType(CONTENT_TYPE)
    setExposePathVariables(false)

    val encoding = JSON_ENCODING
    val disableCaching = false
    val updateContentLength = false


    override def prepareResponse(request: HttpServletRequest, response: HttpServletResponse): Unit = {
      setResponseContentType(request, response)
      response.setCharacterEncoding(this.encoding.getJavaName)
      if (this.disableCaching) {
        response.addHeader("Pragma", "no-cache")
        response.addHeader("Cache-Control", "no-cache, no-store, max-age=0")
        response.addDateHeader("Expires", 1L)
      }

    }

    override def renderMergedOutputModel(model: util.Map[String, AnyRef], request: HttpServletRequest, response: HttpServletResponse): Unit = {
      val stream = if (this.updateContentLength) createTemporaryOutputStream() else response.getOutputStream
      Option(model.get(JsonDto.JSON_MODEL_KEY)).orElse {
        Some(JsonDto(99, "currently not support json request"))
      }.map { jsonResult =>
        writeContent(stream, jsonResult)
        if (this.updateContentLength)
          writeToResponse(response, stream.asInstanceOf[ByteArrayOutputStream])
      }
    }

    def writeContent(stream: OutputStream, obj: AnyRef) = JacksonUtil.toOutstrem(obj, stream)

  }

}
