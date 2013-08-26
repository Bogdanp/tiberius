import scala.io.Source

import jline._

import tiberius._
import tiberius.std.Prelude

object Main {
  val header = """|Tiberius 0.1.0
                  |Type :q or use Ctrl-d to quit.""".stripMargin
  val prompt = "> "

  def main(args: Array[String]): Unit = {
    args.toList match {
      case filename :: ps => {
        val source = Source.fromFile(filename).mkString
        val initialEnv = Prelude.env.set(SymbolExp("@#"), NumberExp(ps.length))
        val (env, _) = ((initialEnv, 0) /: ps) {
          case ((env, n), p) => {
            (env.set(SymbolExp(s"@${n}"), StringExp(p)), n + 1)
          }
        }

        Tiberius.eval(filename, source, env) match {
          case Left(err) => println(s"error: ${err}")
          case _         =>
        }
      }
      case Nil => {
        Terminal.getTerminal.initializeTerminal

        val reader = new ConsoleReader()

        println(header)

        Iterator
          .continually(reader.readLine(prompt))
          .takeWhile(l => l != ":q" && l != null)
          .foldLeft((Prelude.env, Tiberius.initialStack)) {
            case ((env, stack), line) => {
              Tiberius.eval("<stdin>", line, env, stack) match {
                case Right((e, env, stack)) => {
                  stack.map(e => println(e.show))
                  (env, stack)
                }
                case Left(err) => {
                  println(s"error: ${err}")
                  (env, stack)
                }
              }
            }
          }
      }
    }
  }
}
