package tiberius.std

import tiberius._

object Math {
  import Expression._
  import Tiberius._

  val sum = binaryOp { (b: NumberExp, a: NumberExp) => NumberExp(a.n + b.n) }
  val dif = binaryOp { (b: NumberExp, a: NumberExp) => NumberExp(a.n - b.n) }
  val mul = binaryOp { (b: NumberExp, a: NumberExp) => NumberExp(a.n * b.n) }
  val div = binaryOp { (b: NumberExp, a: NumberExp) => NumberExp(a.n / b.n) }

  val numberToString = unaryOp { (n: NumberExp) => StringExp(n.n.toString) }
}
