package nca15.splitter

import scala.collection.mutable.Queue

import org.apache.commons.math3.random.RandomDataGenerator

object SplitLineSimplified {
  var id = 0

}

class SplitLineSimplified(w: Int, uniformGenerator: RandomDataGenerator, windowSize: Int, mu: Double,
                          tau: Double, errorFunction: TMergeError) {

  import SplitLineSimplified._
  val lineId = id
  id += 1


  var ts = 0;

  val prime: Long = 10000019L;
  val hashFunction = new CWHashFunction(prime, w,
    uniformGenerator.nextLong(1, prime - 1),
    uniformGenerator.nextLong(1, prime - 1))

  println("s" + hashFunction)
  val line = Array.fill[(Queue[SplitCell], Double)](w) { (new Queue[SplitCell](), 0.0) }

  val minSize = Math.floor(windowSize.toDouble * tau / w.toDouble).toInt

  def getCount(value: Int) = {
    line(hashFunction.hash(value))._2
  }

  def increment(value: Int) {

    val hash = hashFunction.hash(value)
    var sum = 0.0

    0 to w - 1 foreach (k => {
      var (queue, c) = line(k)

      queue.headOption.map(first => {

        if (first.init == (ts - windowSize)) {

          var cprime = if (first.last - first.init > 0) { first.count / (first.last - first.init + 1).toDouble } else { 1 }

          if (cprime <= 0) {
            println("cprime non positive! %f ".format(cprime))
            Thread.sleep(1000)
          }

          if (first.init == first.last && first.count != cprime) {
            cprime = first.count
          }

          if (c - cprime < -1) {
            println("c non positive! c: %f, cprime: %f first.count: %f, first.last: %d, first.init: %d, k: %d, hsah: %d".format(c, cprime, first.count, first.last, first.init, k, hash))
            Thread.sleep(1000)
          }

          c = c - cprime

          first.init = first.init + 1
          first.count = first.count - cprime

          if (first.init > first.last) {

            if (first.count != 0) {
              println("ERROR! first.count= %20.15f".format(first.count))
              Thread.sleep(5000)
            }

            queue.dequeue

          }

        }

      })

      if (ts >= windowSize && ts % windowSize == 0 && queue.lastOption.isDefined) {

        queue.lastOption.map(first => {

          first.pred = None
        })

      }

      if (hash == k) {

        c = c + 1
        queue.lastOption.map(last => {

          if (last.count >= minSize) {

            last.pred.map(pred => {

              var error = errorFunction.error(pred, last, ts)

              if (error <= mu) {
                // Merge

                pred.count = pred.count + last.count
                pred.last = last.last
                last.count = 1
                last.init = ts
                last.last = ts
              } else {
                // Create New Split
                val newSC = new SplitCell(ts, k, Some(last))
                queue.enqueue(newSC)

              }
              pred
            }).getOrElse({
              // Create Current Cell

              val newSC = new SplitCell(ts, k, Some(last))
              queue.enqueue(newSC)

            })

          } else {
            last.count = last.count + 1
            last.last = ts
          }
          last
        }).getOrElse({
          val newSC = new SplitCell(ts, k)
          queue.enqueue(newSC)

        })
      }

      line(k) = (queue, c)
      sum += c

    })

    ts = ts + 1


  }

  override def toString() = {
    var str = ""
    0 to w - 1 foreach (k => {
      val (queue, c) = line(k)
      if (c != 0)
        str += "[%d] c: %f %s \n".format(k, c, queue.mkString(";"))
    })
    str
  }

}

