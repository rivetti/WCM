package nca15.splitter

trait TMergeError {

  def error(prev: SplitCell, curr: SplitCell, ts: Int): Double

}

class STDError extends TMergeError {

  def error(pred: SplitCell, last: SplitCell, ts: Int): Double = {
    val Fprev = pred.count / (last.init - pred.init)
    val Fnow = last.count / (last.last - last.init + 1)

    val error = if (Fprev > Fnow) Fprev / Fnow else Fnow / Fprev

    error
  }

}