package nca15.splitter

object SplitWCM {
  val name = "SplitWCM"
}

class SplitWCM(val domainSize: Int, epsilon: Double, delta: Double,
               seed: Long, windowSize: Int, mu: Double, tau: Double) {

  SplitLineSimplified.id = 0

  val count = SplitingCount(epsilon, delta, seed, mu, tau, windowSize)
  def newSample(sample: ASample) {
    count.increment(sample.value);

  }

  def getMin(sample: ASample): Int = {
    count.getMin(sample.value)
  }

  override def toString() = {
    count.toString
  }

}