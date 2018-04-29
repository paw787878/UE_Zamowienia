import scala.xml.XML
val path="dziwne_przypadki/000152_2013.xml"
val wczytane_dane = XML.loadFile(path)
val a= wczytane_dane \\ "VALUES_LIST"
a.text