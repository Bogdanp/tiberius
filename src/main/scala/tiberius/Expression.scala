package tiberius

import Tiberius._

sealed trait Expression
case class BooleanExp(b: Boolean) extends Expression
case class NumberExp(n: Double) extends Expression
case class StringExp(s: String) extends Expression
case class SymbolExp(s: String) extends Expression
case class StackExp(xs: Stack) extends Expression
case class FunctionExp(s: SymbolExp, fn: StackExp) extends Expression
case class NativeExp(s: SymbolExp, fn: (Env, Stack) => Result) extends Expression
