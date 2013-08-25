package tiberius

sealed trait Expression
case class BooleanExp(b: Boolean) extends Expression
case class NumberExp(n: Double) extends Expression
case class StringExp(s: String) extends Expression
case class SymbolExp(s: String) extends Expression
case class StackExp(xs: Seq[Expression]) extends Expression
case class FunctionExp(fn: Seq[Expression]) extends Expression
case class NativeExp(fn: Env => Expression) extends Expression
case class ApplicationExp(fn: Symbol) extends Expression
