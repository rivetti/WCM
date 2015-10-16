package nca15.proportional

import scala.collection.mutable.Queue

object ProportionalWCM {
  val name = "ProportionalWCM"
}

class ProportionalWCM(val domainSize: Int, columns: Int, rows: Int,
                      seed: Long, windowSize: Int, splits: Int) {

  val eternalCount = new ProportionalCount(columns, rows, seed)
  var currentCount = new ProportionalCount(columns, rows, seed)
  var prevCount: Option[ProportionalCount] = None
  var prevCountQueue = new Queue[ProportionalCount]

  var count = 0
  var currSplit = 0
  var prevSplit = 1 % splits

  val splitSize = windowSize / splits

  val threshold = splitSize * splits

  def newSample(sample: ASample) {
    eternalCount.increment(sample.value);
    currentCount.increment(sample.value);
    prevCount.map(eternalCount - _)

    count += 1;
    if (count % splitSize == 0) {
      prevCountQueue += currentCount
      currentCount = new ProportionalCount(columns, rows, seed)
      if (count >= threshold) {
        prevCount = Some(prevCountQueue.dequeue)
      } else {
      }
    }
  }

  def print() {}

  def getMin(sample: ASample): Int = {
    eternalCount.getMin(sample.value)
  }

  override def toString() = {
    eternalCount.toString
  }

}