package tiberius.std

import tiberius._

object Stack {
  import Expression._
  import Tiberius._

  import Fn.app

  val map = native { (env: Env, stack: Stack) =>
    stack match {
      case (fn: LambdaExp) :: StackExp(xs) :: stack => {
        val initialState: Result = succ(StackExp(List()), env, stack)

        val res = (initialState /: xs) {
          case (Right((StackExp(xs), env, stack)), e) => {
            app.fn(env, fn :: e :: stack).right.flatMap {
              case (res, env, _) =>
                succ(StackExp(xs ++ List(res)), env, stack)
            }
          }
          case (err, _) => err
        }

        res.right.flatMap {
          case (exp, env, stack) => eval(exp, env, stack)
        }
      }
      case LambdaExp(fn) :: se :: stack => fail(s"Invalid parameter '${se.show}'.")
      case           fn  :: se :: stack => fail(s"Invalid parameter '${fn.show}'.")
      case                        stack => fail(s"Bad arity (${stack.map(_.show).mkString(" ")}).")
    }
  }
}
