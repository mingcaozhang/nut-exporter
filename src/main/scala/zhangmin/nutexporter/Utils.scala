package zhangmin.nutexporter

object Utils {

  /**
    * Transform a list into an optional tuple if it contains exactly two elements.
    *
    * @return a tuple or None
    */
  def listToPair[T](list: List[T]): Option[(T, T)] =
    list match {
      case key :: value :: Nil => Some(key, value)
      case _                   => None
    }

}
