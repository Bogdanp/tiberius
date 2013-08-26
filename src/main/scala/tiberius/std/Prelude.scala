package tiberius.std

import tiberius._

object Prelude {
  import Expression._
  import Tiberius._

  import Fn._
  import IO._
  import Math._

  val env =
    Env()
      .set(sym("show"), show)
      .set(sym("showLn"), showLn)
      .set(sym("+"), sum)
      .set(sym("-"), dif)
      .set(sym("*"), mul)
      .set(sym("/"), div)
      .set(sym("@"), app)
}
