import scala.sys.process._
import scala.xml.XML
val moj_plik="386737_2017.xml"
"ls" !!
val a=XML.loadFile(moj_plik)
val b= a(0) \\ "VALUE"
val currency= (b \ "@CURRENCY").text
val amount=b.text.toFloat


def czytaj_xml(path:String): Unit ={
  val wczytane_dane=XML.loadFile(path)

  def czy_award_notice(wczytane:scala.xml.Elem): Boolean ={
    val b= wczytane \\ "TD_DOCUMENT_TYPE"

    val kod=((b(0) \ "@CODE").text)

    kod=="7"
  }

  if (czy_award_notice(wczytane_dane)) {
    val b= wczytane_dane(0) \\ "VALUE"
  val currency= (b \ "@CURRENCY").text
  val amount=b.text.toFloat)
    println(currency)
    println(amount)
  }


}

czytaj_xml(moj_plik)


