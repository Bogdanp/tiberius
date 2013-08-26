package tiberius.std

import tiberius._

object Math {
  import Expression._
  import Tiberius._

  def binaryOp(fn: (Double, Double) => Double) =
    native { (env: Env, stack: Stack) =>
      stack match {
        case NumberExp(a) :: NumberExp(b) :: rest => {
          val res = NumberExp(fn(a, b))

          succ(res, env, res :: rest)
        }
        case as => fail(s"Invalid arguments to + (${as}).")
      }
    }

  val sum = binaryOp(_+_)
  val dif = binaryOp(_-_)
  val mul = binaryOp(_*_)
  val div = binaryOp(_/_)
}
