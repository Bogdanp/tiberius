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

  val cls = native { (env: Env, _) => succ(unit, env, initialStack) }

  val env =
    Env()
      .set(sym("•"), cls)
      // Fn
      .set(sym("@"), app)
      // IO
      .set(sym("show"), show)
      .set(sym("showLn"), showLn)
      .set(sym("read"), read)
      .set(sym("exists"), exists)
      // Math
      .set(sym("+"), sum)
      .set(sym("-"), dif)
      .set(sym("*"), mul)
      .set(sym("/"), div)
      .set(sym("num/str"), numberToString)
      // Stack
      .set(sym("foldl"), foldl)
      .set(sym("foldr"), foldr)
      .set(sym("map"), map)
      .set(sym("stack/str"), stackToString)
      .set(sym("st"), stackLookup)
      .set(sym("nd"), stackLookup)
      .set(sym("rd"), stackLookup)
      .set(sym("th"), stackLookup)
      // String
      .set(sym("cat"), cat)
      .set(sym("lowercase"), toLower)
      .set(sym("uppercase"), toUpper)
      .set(sym("str/stack"), stringToStack)
}
