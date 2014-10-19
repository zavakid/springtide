package com.zavakid.springtide.dto


case class JsonDto[T](var success: Boolean = true,
                      var data: Option[T] = None,
                      var error: Option[ErrorMessage] = None
                       )
case class ErrorMessage(var code: Int = 500,
                        var message: Option[String] = None
                         )

object JsonDto {

  val JSON_MODEL_KEY = "__json__"

  def apply[T](data: T): JsonDto[T] =
    new JsonDto[T](data = Option(data))

  def apply[T](errorCode: Int, errorMessage: String): JsonDto[T] =
    new JsonDto[T](success = false,
      error = Option(ErrorMessage(code = errorCode, message = Option(errorMessage))))

}