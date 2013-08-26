package tiberius.std

import tiberius._

object IO {
  import Expression._
  import Tiberius._

  val show = native { (env: Env, stack: Stack) =>
    print(stack(0).show)
    succ(unit, env, stack.tail)
  }

  val showLn = native { (env: Env, stack: Stack) =>
    println(stack(0).show)
    succ(unit, env, stack.tail)
  }
}
