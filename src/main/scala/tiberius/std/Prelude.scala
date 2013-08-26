package tiberius.std

import tiberius._

object Prelude {
  import Expression._
  import Tiberius._

  import IO._
  import Math._

  val env =
    Env()
      .set(sym("putStr"), putStr)
      .set(sym("putStrLn"), putStrLn)
      .set(sym("+"), sum)
      .set(sym("-"), dif)
      .set(sym("*"), mul)
      .set(sym("/"), div)
}
