package nca15.splitter

import java.util.UUID
import scala.collection.mutable.Queue
import scala.collection.mutable.HashMap
import org.apache.commons.math3.random.RandomDataGenerator

object SplitingCount {

  def apply(epsilon: Double, delta: Double, seed: Long, mu: Double, tau: Double, windowSize: Int) = {
    val w = Math.ceil(Math.E / epsilon).toInt
    val d = Math.ceil(Math.log(1 / delta) / Math.log(2)).toInt

    new SplitingCount(w, d, seed, mu, tau, windowSize)
  }
}

class SplitingCount(val w: Int, val d: Int, seed: Long, mu: Double, tau: Double, windowSize: Int) extends Logger {

  import SplitingCount._

  val uniformGenerator: RandomDataGenerator = new RandomDataGenerator();
  uniformGenerator.reSeed(seed);

  val count = Array.fill[SplitLineSimplified](d) { new SplitLineSimplified(w, uniformGenerator, windowSize, mu, tau, new STDError) }

  def increment(value: Int) {
    count.foreach(line => {
      line.increment(value)
    })
  }

  def getMin(value: Int) = {
    Math.round(count.map(_.getCount(value)).min).toInt
  }

  override def toString() = {
    count.mkString("##################################\n", "\n", "\n##################################\n")
  }

}