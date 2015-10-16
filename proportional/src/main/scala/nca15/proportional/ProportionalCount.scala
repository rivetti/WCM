package nca15.proportional

import scala.Array.canBuildFrom
import scala.annotation.elidable
import org.apache.commons.math3.random.RandomDataGenerator

object ProportionalCount {

  def apply(epsilon: Double, delta: Double, seed: Long) = {
    val w = Math.ceil(Math.E / epsilon).toInt
    val d = Math.ceil(Math.log(1 / delta) / Math.log(2)).toInt

    new ProportionalCount(w, d, seed)
  }
}

class ProportionalCount(val w: Int, val d: Int, seed: Long) extends Logger {

  val uniformGenerator: RandomDataGenerator = new RandomDataGenerator();
  uniformGenerator.reSeed(seed);

  val count = Array.fill[Line](d) { new Line(w, uniformGenerator) }

  def increment(value: Int) {
    count.foreach(line => {
      line.increment(value)

    })

  }

  def getMin(value: Int) = {
    Math.round(count.map(_.getCount(value)).min).toInt
  }

  def getMinIndex(index: Int) = {
    Math.round(count.map(_.getCountIndex(index)).min).toInt
  }



  override def toString() = {
    count.mkString("##################################\n", "\n", "\n##################################\n")
  }

  def -(that: ProportionalCount) {
    0 to d - 1 foreach (i => {
      count(i).decrement(that.count(i).getDistrib())
    })
  }

  class Line(w: Int, uniformGenerator: RandomDataGenerator) {
    val prime: Long = 10000019L;
    val hashFunction = new CWHashFunction(prime, w,
      uniformGenerator.nextLong(1, prime - 1),
      uniformGenerator.nextLong(1, prime - 1))
    println("p" + hashFunction)
    val line = Array.fill[Double](w) { 0 }
    var sum = 0
    logger.debug("Created Line")

    def increment(value: Int) {
      sum += 1
      line(hashFunction.hash(value)) += 1
    }

    def decrement(distrib: Array[Double]) {
      sum -= 1
      0 to line.length - 1 foreach (i => {
        line(i) -= distrib(i)
        if (line(i) < 0)
          line(i) = 0
      })
    }

    var distrib: Option[Array[Double]] = None

    def getDistrib(): Array[Double] = {
      distrib.getOrElse({
        val sum = line.sum
        val ret = new Array[Double](w)
        0 to ret.length - 1 foreach (i => {
          ret(i) = line(i) / sum
        })
        distrib = Some(ret)
        ret
      })

    }

    def getCount(value: Int) = {
      Math.round(line(hashFunction.hash(value))).toInt
    }

    def getCountIndex(index: Int) = {
      line(index)
    }

    override def toString() = {
      line.mkString(" ")
    }
  }

}