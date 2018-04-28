import Hello.Dokument

import scala.collection.mutable.ListBuffer
import scala.sys.process._
import scala.xml.XML
//val moj_plik="386737_2017.xml"



val port=new Portfel_walut()
port.add_money("pln",1000)
port.add_money("pln",1000)
port.add_money("euro",20)

val port2=new Portfel_walut()
port2.add_money("pln",5000)
port2.add_money("inna",2)

port.add_portfel(port2)



port.portfel("pln")




