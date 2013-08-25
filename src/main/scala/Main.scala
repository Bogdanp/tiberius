import scala.io.Source

import tiberius._

object Main {
  def main(args: Array[String]): Unit = {
    args match {
      case Array(filename) =>
        val result = Tiberius.eval(filename, Source.fromFile(filename).mkString) match {
          case Left(err) => s"error: ${err}"
          case success   => success
        }

        println(result)
      case _ =>
        println("""Usage: sbt "run filename"""")
    }
  }
}
