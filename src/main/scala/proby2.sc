import Hello.Dokument

import scala.collection.mutable.ListBuffer
import scala.sys.process._
import scala.xml.XML
//val moj_plik="386737_2017.xml"


def czytaj_xml_stare(path: String): Dokument = {

  def to_double(napis: String): Double = {
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
    String.valueOf(chary).replaceAll("\\s", "").toDouble
  }

  def czy_award_notice(wczytane: scala.xml.Elem): Boolean = {
    val b = wczytane \\ "TD_DOCUMENT_TYPE"
    val kod = ((b(0) \ "@CODE").text)
    kod == "7"
  }

  def hasOnlyTextChild(node: scala.xml.Node) =
    node.child.size == 1 && node.child(0).isInstanceOf[scala.xml.Text]


  val wczytane_dane = XML.loadFile(path)
  val country_iso = (((wczytane_dane \\ "ISO_COUNTRY") (0)
    \ "@VALUE").text).replaceAll("\\s", "")


  if (czy_award_notice(wczytane_dane)) {
    val b = wczytane_dane \\ "VALUE"
    if (b.size != 0) {
      if (hasOnlyTextChild(b(0))) {
        val currency = ((b(0) \ "@CURRENCY")).text
        try {
          val amount = to_double(b(0).text)
        } catch {
          case _ => return Dokument(true, path, country_iso, dziwny = true)
        }
        val amount = to_double(b(0).text)





        return Dokument(true, path, country_iso.replaceAll("\\s", ""),
          currency.replaceAll("\\s", ""), amount, amount)

      } else {
        val b=wczytane_dane \\ "VAL_TOTAL"
        if(b.size!=0){
          val currency = ((b(0) \ "@CURRENCY")).text
          if(hasOnlyTextChild(b(0))) {
            try {
              val amount = to_double(b(0).text)
            } catch {
              case _ => return Dokument(true, path, country_iso, dziwny = true)
            }
            val amount = to_double(b(0).text)
            return Dokument(true, path, country_iso.replaceAll("\\s", ""),
              currency.replaceAll("\\s", ""), amount, amount)
          }else{
            println(path)
            return Dokument(false, path, country_iso)
          }



        }else{
          println(path)
          return Dokument(false, path, country_iso)
        }
      }

    } else {
      val b = wczytane_dane \\ "VALUE_RANGE"
      if (b.size != 0) {

        val min = to_double((b(0) \ "LOW").text)
        val max = to_double((b(0) \ "HIGH").text)


        val currency = (b(0) \ "@CURRENCY").text


        if (currency.replaceAll("\\s", "") == "")
          println("brak currency " + path)


        return Dokument(true, path, country_iso, currency.replaceAll("\\s", ""), min, max)
      }

      return Dokument(true, path, country_iso.replaceAll("\\s", ""), dziwny = true)
    }
  } else
    return Dokument(false, path, country_iso)

}

//val file = "dziwne_przypadki/046960_2018.xml"
//val file= "dziwne_przypadki/juz_dziala/386737_2017.xml"

def czytaj_xml_nowe(path: String): Dokument = {

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

  def czy_award_notice(wczytane: scala.xml.Elem): Boolean = {
    val b = wczytane \\ "TD_DOCUMENT_TYPE"
    val kod = ((b(0) \ "@CODE").text)
    kod == "7"
  }

  def hasOnlyTextChild(node: scala.xml.Node) =
    node.child.size == 1 && node.child(0).isInstanceOf[scala.xml.Text]

  val wczytane_dane = XML.loadFile(path)

  def liczba_w_polu(tag:String):Double ={
  //-1 to znaczy, ze sie nie udalo
  //-2 to znaczy, ze tam cos dziwnego w srodku sieci jeszcze
  val node_list = wczytane_dane \\ tag
  if(node_list.size!=0){
    if(hasOnlyTextChild(node_list(0))){
    return to_double(node_list(0).text)
  }else{
    return -2
  }
  }else{
    return -1
  }


  }



  val country_iso = (((wczytane_dane \\ "ISO_COUNTRY") (0)
    \ "@VALUE").text).replaceAll("\\s", "")

  return Dokument(true,path,country_iso)


}














