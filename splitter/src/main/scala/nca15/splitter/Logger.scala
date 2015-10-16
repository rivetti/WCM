package nca15.splitter


import org.slf4j.LoggerFactory
import scala.annotation.elidable
import scala.annotation.elidable._

trait Logger {

  protected lazy val logger = new MyLogger(LoggerFactory.getLogger(Logger.this.getClass()))

}

class MyLogger(logger: org.slf4j.Logger) {

  final val ELIDABLE_TRACE = 3100
  final val ELIDABLE_DEBUG = 3200
  final val ELIDABLE_INFO = 3300
  final val ELIDABLE_WARN = 3400
  final val ELIDABLE_ERROR = 3500
  final val ELIDABLE_LOGGER = 3600

  @elidable(ELIDABLE_TRACE)
  def trace(string: String) {
    logger.trace(string)
  }

  @elidable(ELIDABLE_DEBUG)
  def debug(string: String) {
    logger.debug(string)
  }

  @elidable(ELIDABLE_INFO)
  def info(string: String) {
    logger.info(string)
  }

  @elidable(ELIDABLE_INFO)
  def info(format: String, string: String) {
    logger.info(format, string)
  }

  @elidable(ELIDABLE_WARN)
  def warn(string: String) {
    logger.warn(string)
  }

  @elidable(ELIDABLE_ERROR)
  def error(string: String) {
    logger.error(string)
  }

  @elidable(ELIDABLE_LOGGER)
  protected def getStackTrace(stackTrace: Array[StackTraceElement]) = {
    var str = ""
    for (stackElem <- stackTrace)
      str += stackElem + "\n"

    str
  }

}
 