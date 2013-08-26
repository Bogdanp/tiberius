package tiberius.std

import tiberius._

object Stack {
  import Expression._
  import Tiberius._

  import Fn.app

  val foldl = native { (env: Env, stack: Stack) =>
    stack match {
      case (fn: LambdaExp) :: StackExp(xs) :: e :: stack => {
        val initialState: Result = succ(e, env, stack)

        (initialState /: xs) {
          case (Right((le, env, stack)), re) => {
            app.fn(env, fn :: le :: re :: stack).right.flatMap {
              case (res, env, _) => succ(res, env, stack)
            }
          }
          case (err, _) => err
        }.right.flatMap {
          case (exp, env, stack) => eval(exp, env, stack)
        }
      }
      case (fn: LambdaExp) :: se :: e :: stack => fail(s"Invalid parameter '${se.show}', expected stack.")
      case  fn             :: se :: e :: stack => fail(s"Invalid parameter '${fn.show}', expected lambda.")
      case                               stack => fail(s"Bad arity (${listStack(stack)}).")
    }
  }

  val foldr = native { (env: Env, stack: Stack) =>
    stack match {
      case (fn: LambdaExp) :: StackExp(xs) :: e :: stack => {
        val finalState: Result = succ(e, env, stack)

        (xs :\ finalState) {
          case (le, Right((re, env, stack))) => {
            app.fn(env, fn :: le :: re :: stack).right.flatMap {
              case (res, env, _) => succ(res, env, stack)
            }
          }
          case (_, err) => err
        }.right.flatMap {
          case (exp, env, stack) => eval(exp, env, stack)
        }
      }
      case (fn: LambdaExp) :: se :: e :: stack => fail(s"Invalid parameter '${se.show}', expected stack.")
      case  fn             :: se :: e :: stack => fail(s"Invalid parameter '${fn.show}', expected lambda.")
      case                               stack => fail(s"Bad arity (${listStack(stack)}).")
    }
  }

  val map = native { (env: Env, stack: Stack) =>
    stack match {
      case (fn: LambdaExp) :: StackExp(xs) :: stack => {
        val initialState: Result = succ(StackExp(List()), env, stack)

        (initialState /: xs) {
          case (Right((StackExp(xs), env, stack)), e) => {
            app.fn(env, fn :: e :: stack).right.flatMap {
              case (res, env, _) =>
                succ(StackExp(xs ++ List(res)), env, stack)
            }
          }
          case (err, _) => err
        }.right.flatMap {
          case (exp, env, stack) => eval(exp, env, stack)
        }
      }
      case LambdaExp(fn) :: se :: stack => fail(s"Invalid parameter '${se.show}', expected stack.")
      case           fn  :: se :: stack => fail(s"Invalid parameter '${fn.show}', expected lambda.")
      case                        stack => fail(s"Bad arity (${listStack(stack)}).")
    }
  }
}
