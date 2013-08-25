package tiberius

import scala.util.parsing.combinator._

object Parser extends RegexParsers {
  override val whiteSpace = """(--.*|[ \t\r\n]+)""".r

  def expression: Parser[Expression] =
    boolean  |
    number   |
    string   |
    stack    |
    pop      |
    push     |
    function |
    symbol

  def boolean: Parser[BooleanExp] =
    "#" ~> "(T|F)".r ^^ {
      case "T" => BooleanExp(true)
      case "F" => BooleanExp(false)
    }

  def number: Parser[NumberExp] = {
    val zero: Parser[NumberExp] =
      "0" ^^ { _ => NumberExp(0) }

    val whole: Parser[NumberExp] =
      """[1-9][0-9]*""".r ^^ { number =>
        NumberExp(number.toDouble)
      }

    val real: Parser[NumberExp] =
      """([1-9][0-9]*)?\.\d+""".r ^^ { number =>
        NumberExp(number.toDouble)
      }

    real | whole | zero
  }

  def string: Parser[StringExp] =
    """"([^"]|"")*"""".r ^^ { string =>
      if (string.length == 2) {
        StringExp("")
      } else {
        val escaped = string.replace("\"\"", "\"")

        StringExp(escaped.substring(1, escaped.length - 1))
      }
    }

  def symbol: Parser[SymbolExp] =
    """[^ \t\r\n\{\}]+""".r ^^ SymbolExp

  def stack: Parser[StackExp] =
    "{" ~> rep(expression) <~ "}" ^^ {
      case xs => StackExp(xs)
    }

  def pop: Parser[PopExp] =
    "->" ~> symbol ^^ {
      case sym => PopExp(sym)
    }

  def push: Parser[PushExp] =
    "<-" ~> symbol ^^ {
      case sym => PushExp(sym)
    }

  def function: Parser[FunctionExp] =
    "fn" ~> symbol ~ stack ^^ {
      case symbol ~ stack => FunctionExp(symbol, stack)
    }

  def apply(filename: String, input: String): Either[String, (String, Seq[Expression])] =
    parseAll(rep(expression), input) match {
      case Success(expressions, _) => Right((filename, expressions))
      case NoSuccess(error, next)  =>
        Left(s"${filename}:${next.pos.line}:${next.pos.column}:${error}.")
    }
}
