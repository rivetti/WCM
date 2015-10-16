package nca15.proportional

abstract class ASample(val value: Int)

case class Sample(override val value: Int) extends ASample(value) {
  override def equals(o: Any) = o match {
    case that: Sample => that.value == this.value
    case _ => false
  }
}
