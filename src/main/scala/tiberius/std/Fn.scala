package tiberius.std

import java.util.UUID

import tiberius._

object Fn {
  import Expression._
  import Tiberius._

  val app = native { (env: Env, stack: Stack) =>
    stack match {
      case LambdaExp(body) :: xs => {
        val name = SymbolExp(s"Lambda#${UUID.randomUUID.toString}")
        val fn = FunctionExp(name, body)

        eval(fn, env, stack).right.flatMap {
          case (_, env, stack) => eval(name, env, stack.tail).right.flatMap {
            case (exp, env, stack) => succ(exp, env drop name, stack)
          }
        }
      }
      case x :: xs => fail(s"Cannot apply '${x.show}'.")
      case xs      => fail(s"""Bad arity (${xs.map(_.show).mkString(" ")}).""")
    }
  }
}
