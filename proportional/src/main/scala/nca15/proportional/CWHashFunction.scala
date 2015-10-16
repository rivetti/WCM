package  nca15.proportional

import scala.util.Random

class CWHashFunction(prime: Long, codomainSize: Int, a: Long, b: Long) extends Logger {

  def this(prime: Int, codomainSize: Int) {
    this(prime, codomainSize, (Random.nextInt(prime - 1) + 1).toLong, (Random.nextInt(prime)).toLong);
  }

  logger.debug("CW Hash Function[prime: %d, a: %d, b: %d]".format(prime, a, b))

  private def computeHash(value: Int) = ((a * value + b) % prime) % codomainSize

  def hash(value: Int) = {

    ((a * value.toLong + b) % prime.toLong).toInt % codomainSize
  }

}