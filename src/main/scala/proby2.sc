import Hello.Dokument

import scala.collection.mutable.ListBuffer
import scala.sys.process._
import scala.xml.XML
//val moj_plik="386737_2017.xml"



val port=new Portfel_walut()
port.add_money("pln",1000)
port.add_money("pln",1000)
port.add_money("euro",20)


port.portfel("euro")




