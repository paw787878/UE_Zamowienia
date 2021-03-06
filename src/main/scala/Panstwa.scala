import scala.io.Source

object Panstwa {
  /*
  Obiekt pozwalający odczytać nazwę państwa na podstawie jego kodu iso.
   */
  var panstwa = collection.mutable.Map[String, String]()
  val path = "iso_panstwa"
  val source = Source.fromFile(path)
  val linie = source.getLines().toList
  val n = linie.size
  for (i <- 0 until n) {
    val arr = linie(i).split(";")
    panstwa += arr(1) -> arr(0)
  }

  def nazwa_ze_skroto(kod: String): String ={
    if (panstwa.contains(kod)) {
      return kod+" ("+panstwa(kod)+")"
    }else{
      return kod
    }
  }

}
