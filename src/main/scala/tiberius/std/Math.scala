package tiberius.std

import tiberius._

object Math {
  import Expression._
  import Tiberius._

  val sum = binaryOp { (a: NumberExp, b: NumberExp) => NumberExp(a.n + b.n) }
  val dif = binaryOp { (a: NumberExp, b: NumberExp) => NumberExp(a.n - b.n) }
  val mul = binaryOp { (a: NumberExp, b: NumberExp) => NumberExp(a.n * b.n) }
  val div = binaryOp { (a: NumberExp, b: NumberExp) => NumberExp(a.n / b.n) }

  val numberToString = unaryOp { (n: NumberExp) => StringExp(n.n.toString) }
}
