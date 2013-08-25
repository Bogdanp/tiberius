package tiberius

object Tiberius {
  type Stack = List[Expression]
  type Result = Either[String, (Expression, Env, Stack)]

  val unit = UnitExp()

  def initialStack: Stack = List()

  def eval(input: String): Result =
    eval("<stdin>", input)

  def eval(source: String, input: String): Result =
    eval(source, input, Env(), initialStack)

  def eval(source: String, input: String, env: Env, stack: Stack): Result =
    Parser(source, input).right.flatMap {
      case (_, xs) => {
        val initialState: Result = Right((unit, env, stack))

        (initialState /: xs) {
          case (Right((_, env, stack)), exp) => eval(exp, env, stack)
          case (err, _) => err
        }
      }
    }

  def eval(exp: Expression, env: Env, stack: Stack): Result = exp match {
    case sym@SymbolExp(s: String) => env.lookup(sym) match {
      case None    => Left(s"Failed to lookup '${s}'.")
      case Some(e) => e match {
        case NativeExp(_, fn) => fn(env, stack)
        case FunctionExp(_, StackExp(xs)) => {
          val current: Result = Right((exp, env, stack))

          (current /: xs) {
            case (Right((_, env, stack)), exp) => eval(exp, env, stack)
            case (err, _) => err
          }
        }
        case e => eval(e, env, stack)
      }
    }
    case FunctionExp(sym, _) => Right((unit, env.set(sym, exp), stack))
    case e                   => Right((e, env, e :: stack))
  }
}
