package com.zavakid.springtide.util

import java.io.OutputStream

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper

/**
 *
 * @author zebin.xuzb 2014-10-12
 */
object JacksonUtil {

  val objectMapper = {
    val mapper = new ObjectMapper() with ScalaObjectMapper
    mapper.registerModule(DefaultScalaModule)
    mapper
  }

  def toJSON(obj: Any): String =
    objectMapper.writeValueAsString(obj)

  def fromJSON[T](json: String)(implicit m: Manifest[T]): T =
    objectMapper.readValue[T](json)

  def toOutstrem(obj: Any, out: OutputStream) =
    objectMapper.writeValue(out, obj)

}
