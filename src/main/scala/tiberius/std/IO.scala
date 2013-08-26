package tiberius.std

import tiberius._

object IO {
  import Expression._
  import Tiberius._

  val show   = unaryOp { e: Expression => print(e.show); e}
  val showLn = unaryOp { e: Expression => println(e.show); e}
}
