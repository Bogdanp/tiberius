package tiberius

import Tiberius._

sealed trait Expression
case class UnitExp() extends Expression
case class BooleanExp(b: Boolean) extends Expression
case class NumberExp(n: Double) extends Expression
case class StringExp(s: String) extends Expression
case class SymbolExp(s: String) extends Expression
case class StackExp(xs: Stack) extends Expression
case class PopExp(s: SymbolExp) extends Expression
case class PushExp(s: SymbolExp) extends Expression
case class FunctionExp(s: SymbolExp, fn: StackExp) extends Expression
case class NativeExp(fn: (Env, Stack) => Result) extends Expression

object Expression {
  def sym(s: String) = SymbolExp(s)
  def native(fn: (Env, Stack) => Result) = NativeExp(fn)
}
