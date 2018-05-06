import scala.io.Source

object Waluty {
  /*
  Obiekt ten pozwala na odczytanie nazwy waluty na podstawie jej kodu.
   */
  var waluty = collection.mutable.Map[String, String]()
  val path = "waluty"
  val source = Source.fromFile(path)
  val linie = source.getLines().toList
  val n = linie.size

  def srodek(s: String): String = {
    s.substring(1, s.size - 1)
  }





  for (i <- 1 until n) {
    val arr = linie(i).split("\\|")
    waluty += srodek(arr(1)) -> srodek(arr(2))
  }

  def nazwa_ze_skroto(kod: String): String ={
    if (waluty.contains(kod)) {
      return kod+" ("+waluty(kod)+")"
    }else{
      return kod
  }
  }
}
