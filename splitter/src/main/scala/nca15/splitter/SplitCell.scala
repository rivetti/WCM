package nca15.splitter

import java.util.UUID

class SplitCell(initCnt: Double, var init: Int, val i: Int, var pred: Option[SplitCell]) {

  def this(startTs: Int, i: Int, pred: Option[SplitCell]) = this(1.0, startTs, i, pred)
  def this(startTs: Int, i: Int) = this(1, startTs, i, None)

  var freq: Option[Double] = None
  val id = UUID.randomUUID()
  var last: Int = init
  var count: Double = initCnt

  override def toString(): String = {
    "Cell init %d, last %d, count %f".format(init, last, count)
  }
}