import scala.sys.process._
import scala.xml.XML
val moj_plik="386737_2017.xml"
"ls" !!
val a=XML.loadFile(moj_plik)
val country= ((a \\ "COUNTRY" )(0) \ "@VALUE")(0).text

def czytaj_xml(path:String): Unit ={
  val wczytane_dane=XML.loadFile(path)

  def czy_award_notice(wczytane:scala.xml.Elem): Boolean ={
    val b= wczytane \\ "TD_DOCUMENT_TYPE"

    val kod=((b(0) \ "@CODE").text)

    kod=="7"
  }

  if (czy_award_notice(wczytane_dane)) {
    val b= (wczytane_dane \\ "VALUE")(0)
    val currency= ((b \ "@CURRENCY"))(0).text
    val amount=b.text.toFloat
    val country= ((wczytane_dane \\ "COUNTRY" )(0) \ "@VALUE")(0).text

    println(currency)
    println(amount)
    println(country)
  }


}

czytaj_xml(moj_plik)


