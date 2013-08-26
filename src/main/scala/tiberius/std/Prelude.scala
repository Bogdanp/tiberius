package tiberius.std

import tiberius._

object Prelude {
  import Expression._
  import Tiberius._

  import Fn._
  import IO._
  import Math._
  import Stack._
  import String._

  val cls = native { (env: Env, _ : Stack) =>
    succ(unit, env, initialStack)
  }

  val env =
    Env()
      .set(sym("•"), cls)
      // Fn
      .set(sym("@"), app)
      // IO
      .set(sym("show"), show)
      .set(sym("showLn"), showLn)
      // Math
      .set(sym("+"), sum)
      .set(sym("-"), dif)
      .set(sym("*"), mul)
      .set(sym("/"), div)
      // Stack
      .set(sym("foldl"), foldl)
      .set(sym("foldr"), foldr)
      .set(sym("map"), map)
      // String
      .set(sym("cat"), cat)
}
