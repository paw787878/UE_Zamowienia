import Hello.Dokument

import scala.collection.mutable.ListBuffer
import scala.sys.process._
import scala.xml.XML
//val moj_plik="386737_2017.xml"


def to_double(napis:String): Double={

  val bez_przecinkow=napis.replaceAll(",",".")
  val pozycje= new ListBuffer[Int]()
  for (i <- 0 until bez_przecinkow.size)
    if (bez_przecinkow(i)=='.')
      pozycje+=i

  val chary=bez_przecinkow.toCharArray()
  if (pozycje.size>1){
    for (i <- 0 until pozycje.size-1)
      chary(pozycje(i))=' '
  }

  String.valueOf(chary).replaceAll("\\s","").toDouble



}


def czytaj_xml(path:String): Dokument={
  def to_double(napis:String): Double={

    val bez_przecinkow=napis.replaceAll(",",".")
    val pozycje= new ListBuffer[Int]()
    for (i <- 0 until bez_przecinkow.size)
      if (bez_przecinkow(i)=='.')
        pozycje+=i

    val chary=bez_przecinkow.toCharArray()
    if (pozycje.size>1){
      for (i <- 0 until pozycje.size-1)
        chary(pozycje(i))=' '
    }

    String.valueOf(chary).replaceAll("\\s","").toDouble



  }
  val wczytane_dane=XML.loadFile(path)

  def czy_award_notice(wczytane:scala.xml.Elem): Boolean ={
    val b= wczytane \\ "TD_DOCUMENT_TYPE"
    val kod=((b \ "@CODE").text)

    kod=="7"
  }

  if (czy_award_notice(wczytane_dane)) {
    val b= wczytane_dane \\ "VALUE"
    if (b.size!=0) {
      val currency = ((b(0) \ "@CURRENCY")).text
      val amount = to_double(b(0).text)
      val country_iso = ((wczytane_dane \\ "COUNTRY")(0) \ "@VALUE").text

      return Dokument(true, path, currency.replaceAll("\\s", ""), amount, country_iso.replaceAll("\\s", ""))
    }else{
      val b= wczytane_dane \\ "VALUE_RANGE"
      if(b.size!=0){

        val min=to_double((b(0) \ "LOW").text)
        val max=to_double((b(0) \ "HIGH").text)
        val amount= (min+max)/2
        val country_iso = ((wczytane_dane \\ "COUNTRY")(0) \ "@VALUE").text
        val currency= (b(0) \ "@CURRENCY" ).text
        return Dokument(true, path,currency.replaceAll("\\s", ""),amount,country_iso.replaceAll("\\s", ""))
      }

      return Dokument(true,path,dziwny=true)
    }
  } else
    return Dokument(false,path)

}


val wczytane_dane=XML.loadFile("jeden_z_127.xml")
val b= wczytane_dane \\ "VALUE_RANGE"
val min=to_double((b(0) \ "LOW").text)
val max=to_double((b(0) \ "HIGH").text)
val amount= (min+max)/2
val country_iso = ((wczytane_dane \\ "COUNTRY")(0) \ "@VALUE").text
val currency= (b(0) \ "@CURRENCY" ).text




val moj_plik="388052_2017.xml"
//ten plik sie wykrzaczyl
czytaj_xml("jeden_z_127.xml")







//val dok=czytaj_xml(moj_plik)





