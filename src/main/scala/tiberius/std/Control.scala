package tiberius.std

import tiberius._

object Control {
  import Expression._
  import Tiberius._

  val if_ = tertiaryOp {
    (f: Expression, t: Expression, c: Expression) => c match {
      case BooleanExp(false) => f
      case NumberExp(0)      => f
      case StringExp("")     => f
      case StackExp(Nil)     => f
      case _                 => t
    }
  }
}
