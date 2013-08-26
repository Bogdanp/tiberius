package tiberius.std

import tiberius._

object IO {
  import Expression._
  import Tiberius._

  val putStr = native { (env: Env, stack: Stack) =>
    print(stack(0).show)
    Right((unit), env, stack.tail)
  }

  val putStrLn = native { (env: Env, stack: Stack) =>
    println(stack(0).show)
    Right((unit), env, stack.tail)
  }
}
