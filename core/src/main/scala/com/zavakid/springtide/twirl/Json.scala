package com.zavakid.springtide.twirl

import com.zavakid.springtide.dto.JsonDto
import com.zavakid.springtide.util.JacksonUtil
import org.apache.commons.lang3.StringUtils
import play.twirl.api.BufferedContent

/**
 *
 * @author zebin.xuzb 2014-10-19
 */
class Json[T] private(private val data: T) extends BufferedContent[Json[T]](Nil, StringUtils.EMPTY) {
  override def contentType: String = Json.mimeTypes

  override protected def buildString(builder: StringBuilder): Unit = {
    builder.append(JacksonUtil.toJSON(data))
  }
}

object Json {
  val mimeTypes = "application/json"

  def apply[T](data: T): Json[T] = new Json(data)
  def success[T](data: T): Json[JsonDto[T]] = new Json(JsonDto(data))
  def fail(errorCode:Int = 500, errorMsg: String = "server error"): Json[JsonDto[_]] = new Json(JsonDto(errorCode, errorMsg))
}

// we don't need to implement a JsonFormat as we don't need to a template for jsonResult

