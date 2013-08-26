import scala.io.Source

import jline._

import tiberius._
import tiberius.std.Prelude

object Main {
  val prompt = "> "

  def main(args: Array[String]): Unit = {
    args match {
      case Array(filename) =>
        val source = Source.fromFile(filename).mkString
        val result = Tiberius.eval(filename, source, Prelude.env) match {
          case Left(err) => println(s"error: ${err}")
          case success   => success
        }
      case Array() =>
        Terminal.getTerminal.initializeTerminal

        val reader = new ConsoleReader()

        Iterator
          .continually(reader.readLine(prompt))
          .takeWhile(l => l != ":q" && l != null)
          .foldLeft((Prelude.env, Tiberius.initialStack)) {
            case ((env, stack), line) => {
              Tiberius.eval("<stdin>", line, env, stack) match {
                case Right((e, env, stack)) => {
                  println(e.show)
                  (env, stack)
                }
                case Left(err) => {
                  println(s"error: ${err}")
                  (env, stack)
                }
              }
            }
          }
      case _ =>
        println("""Usage: sbt "run [filename]"""")
    }
  }
}
