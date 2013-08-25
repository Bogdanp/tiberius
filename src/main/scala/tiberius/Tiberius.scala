package tiberius

object Tiberius {
  type Stack = List[Expression]
  type Result = Either[String, (Expression, Env, Stack)]

  def initialStack: Stack = List()

  def eval(exp: Expression, env: Env, stack: Stack): Result = exp match {
    case NativeExp(_, fn) => fn(env, stack)
    case FunctionExp(_, StackExp(xs)) => {
      val current: Result = Right((exp, env, stack))

      (current /: xs) {
        case (Right((_, env, stack)), exp) => eval(exp, env, stack)
        case (err, _) => err
      }
    }
    case sym@SymbolExp(s: String) => env.lookup(sym) match {
      case None    => Left(s"Failed to lookup '${s}'.")
      case Some(e) => eval(e, env, stack)
    }
    case e => Right((e, env, e :: stack))
  }
}
