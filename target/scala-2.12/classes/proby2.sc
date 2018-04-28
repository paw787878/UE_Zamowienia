import Hello.Dokument

import scala.collection.mutable.ListBuffer
import scala.sys.process._
import scala.xml.XML
//val moj_plik="386737_2017.xml"



def czytaj_xml(path:String): Dokument={
  def to_double(napis:String): Double={
    napis.replaceAll("\\s", "").replaceAll(",",".").toDouble
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





      println("a_tuJeszcze")
      val amount = to_double(b(0).text)
      println("czy_tu")




      val country_iso = ((wczytane_dane \\ "COUNTRY")(0) \ "@VALUE").text

      Dokument(true, path, currency.replaceAll("\\s", ""), amount, country_iso.replaceAll("\\s", ""))
    }else{
      val b= wczytane_dane \\ "VALUE_RANGE"
      if(b.size!=0){
        val min=to_double((b(0) \ "LOW").text)
        val max=to_double((b(0) \ "HIGH").text)
        val amount= (min+max)/2
        val country_iso = ((wczytane_dane \\ "COUNTRY")(0) \ "@VALUE").text
        val currency= (b(0) \ "@CURRENCY" ).text
        Dokument(true, path,currency.replaceAll("\\s", ""),amount,country_iso.replaceAll("\\s", ""))
      }
      Dokument(true,path,dziwny=true)
    }
  } else
    Dokument(false,path)

}
val moj_plik="388052_2017.xml"
//ten plik sie wykrzaczyl

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
to_double("12 3434 ,1.3")



//val dok=czytaj_xml(moj_plik)





