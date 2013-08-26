package tiberius.std

import tiberius._

object String {
  import Expression._
  import Tiberius._

  val cat = binaryOp { (a: StringExp, b: StringExp) =>
    StringExp(b.s + a.s)
  }

  val toUpper = unaryOp { a: StringExp =>
    StringExp(a.s.toUpperCase)
  }

  val toLower = unaryOp { a: StringExp =>
    StringExp(a.s.toLowerCase)
  }

  val stringToStack = unaryOp { a: StringExp =>
    StackExp(a.s.split("").map(StringExp).toList)
  }
}
