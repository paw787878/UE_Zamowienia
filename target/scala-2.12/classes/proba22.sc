import scala.collection.mutable.ListBuffer

def to_double(napis: String): Double = {
  //zwroci -1 jesli sie nie da
  val bez_przecinkow = napis.replaceAll(",", ".")
  val pozycje = new ListBuffer[Int]()
  for (i <- 0 until bez_przecinkow.size)
    if (bez_przecinkow(i) == '.')
      pozycje += i
  val chary = bez_przecinkow.toCharArray()
  if (pozycje.size > 1) {
    for (i <- 0 until pozycje.size - 1)
      chary(pozycje(i)) = ' '
  }
  val ostateczny_tekst=String.valueOf(chary).replaceAll("\\s", "")
  try {return ostateczny_tekst.toDouble}
  catch {
    case _ => return -1
  }
}

to_double("1.5 ,8")