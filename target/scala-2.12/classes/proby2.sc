import scala.sys.process._
import scala.xml.XML
val moj_plik="386737_2017.xml"
"ls" !!
val a=XML.loadFile(moj_plik)
val b= a \\ "TD_DOCUMENT_TYPE"
((b(0) \ "@CODE").text).toInt
"57".toInt

def czytaj_xml(path:String): Unit ={
  val wczytane_dane=XML.loadFile(path)

  def czy_award_notice(wczytane:scala.xml.Elem): Boolean ={
    val b= wczytane \\ "TD_DOCUMENT_TYPE"

    val kod=((b(0) \ "@CODE").text).toInt

    if (kod==7) true else false
  }
  print(czy_award_notice(wczytane_dane))


}

czytaj_xml(moj_plik)


