package tiberius

case class Env(
  parent: Option[Env],
  table: Map[Expression, Expression]) {

  def lookup(e: Expression): Option[Expression] =
    table.get(e) match {
      case Some(e) => Some(e)
      case None    =>
        parent.flatMap(_.lookup(e))
    }

  def set(e: Expression, v: Expression): Env =
    copy(table=table + (e -> v))
}
