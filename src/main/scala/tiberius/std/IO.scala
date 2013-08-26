package tiberius.std

import java.io.File

import scala.io.Source

import tiberius._

object IO {
  import Expression._
  import Tiberius._

  val show   = unaryOp { e: Expression => print(e.show); e}
  val showLn = unaryOp { e: Expression => println(e.show); e}

  val exists = unaryOp { a: StringExp =>
    BooleanExp((new File(a.s)).exists)
  }

  val read = unaryOp { a: StringExp =>
    StringExp(Source.fromFile(a.s).getLines.mkString("\n"))
  }
}
