import scala.io.Source

import tiberius._
import tiberius.std.Prelude

object Main {
  def main(args: Array[String]): Unit = {
    args match {
      case Array(filename) =>
        val source = Source.fromFile(filename).mkString
        val result = Tiberius.eval(filename, source, Prelude.env) match {
          case Left(err) => s"error: ${err}"
          case success   => success
        }
      case _ => println("""Usage: sbt "run filename"""")
    }
  }
}
