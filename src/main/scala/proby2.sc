import Hello.Dokument

import scala.sys.process._
import scala.xml.XML
val moj_plik="386737_2017.xml"

def czytaj_xml(path:String): Dokument={
  val wczytane_dane=XML.loadFile(path)

  def czy_award_notice(wczytane:scala.xml.Elem): Boolean ={
    val b= wczytane \\ "TD_DOCUMENT_TYPE"
    val kod=((b \ "@CODE").text)

    kod=="7"
  }

  if (czy_award_notice(wczytane_dane)) {
    val b= wczytane_dane \\ "VALUE"
    if (b.size!=0) {
      val currency = ((b(0) \ "@CURRENCY"))(0).text
      val amount = b(0).text.toDouble
      val country_iso = ((wczytane_dane \\ "COUNTRY")(0) \ "@VALUE")(0).text

      Dokument(true, path, currency, amount, country_iso)
    }else{
      val b= wczytane_dane \\ "VALUE_RANGE"
      if(b.size!=0){
        val min=(b(0) \ "LOW")(0).text.toDouble
        val max=(b(0) \ "HIGH")(0).text.toDouble
        val amount= (min+max)/2

      }
    }
  } else
    Dokument(false,path)

}

