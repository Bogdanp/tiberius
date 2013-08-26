package tiberius

import scala.reflect.ClassTag

object Tiberius {
  import Expression._

  type Stack = List[Expression]
  type Result = Either[String, (Expression, Env, Stack)]

  val unit = UnitExp()
  val initialStack = List[Expression]()

  def eval(input: String): Result =
    eval("<stdin>", input)

  def eval(source: String, input: String): Result =
    eval(source, input, Env(), initialStack)

  def eval(source: String, input: String, env: Env): Result =
    eval(source, input, env, initialStack)

  def eval(source: String, input: String, env: Env, stack: Stack): Result =
    Parser(source, input).right.flatMap {
      case (_, xs) => {
        val initialState = succ(unit, env, stack)

        (initialState /: xs) {
          case (Right((_, env, stack)), exp) => eval(exp, env, stack)
          case (err, _)                      => err
        }
      }
    }

  def eval(exp: Expression, env: Env, stack: Stack): Result =
    exp match {
      case sym@SymbolExp(name) =>
        (env lookup sym) match {
          case None    => fail(s"Failed to look up '${name}'.")
          case Some(e) => e match {
            case FunctionExp(_, StackExp(xs)) => {
              val current: Result = succ(exp, env, stack)

              (current /: xs) {
                case (Right((_, env, stack)), exp) => eval(exp, env, stack)
                case (err, _) => err
              }
            }
            case NativeExp(fn) => fn(env, stack)
            case e             => eval(e, env, stack)
          }
        }
      case PopExp(sym) => {
        stack match {
          case LambdaExp(body) :: xs => eval(FunctionExp(sym, body), env, xs)
          case e               :: xs => eval(unit, env.set(sym, e), xs)
          case _                     => fail("Empty stack.")
        }
      }
      case PushExp(sym)        => eval(sym, env, stack)
      case FunctionExp(sym, _) => succ(unit, env.set(sym, exp), stack)
      case e                   => succ(e, env, e :: stack)
    }

  def fail(err: String): Result = Left(err)
  def succ(exp: Expression, env: Env, stack: Stack): Result = Right((exp, env, stack))

  def unaryOp[A <: Expression : ClassTag,
              B <: Expression : ClassTag](fn: A => B) =
    native { (env: Env, stack: Stack) =>
      stack match {
        case (a: A) :: xs => {
          val res = fn(a)

          succ(res, env, res :: xs)
        }
        case a :: xs => fail(s"Invalid parameter '${a.show}'.")
        case xs      => fail(s"""Bad arity (${xs.map(_.show).mkString(" ")}).""")
      }
    }

  def binaryOp[A <: Expression : ClassTag,
               B <: Expression : ClassTag,
               C <: Expression : ClassTag](fn: (A, B) => C) =
    native { (env: Env, stack: Stack) =>
      stack match {
        case (a: A) :: (b: B) :: xs => {
          val res = fn(a, b)

          succ(res, env, res :: xs)
        }
        case (a: A) :: b :: xs => fail(s"Invalid parameter '${b.show}'.")
        case a :: b :: xs      => fail(s"Invalid parameter '${a.show}'.")
        case xs                => fail(s"""Bad arity (${xs.map(_.show).mkString(" ")}).""")
      }
    }
}
