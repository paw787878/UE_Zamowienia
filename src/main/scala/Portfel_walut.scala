import java.io.{File, PrintWriter}
class Portfel_walut() {
  var portfel=collection.mutable.Map[String, Double]()
  def add_money(waluta:String,ilosc:Double): Unit ={
    if (portfel.contains(waluta)) {
      portfel(waluta) += ilosc
    }else{
      portfel+= waluta-> ilosc
    }
  }

  def add_portfel(inny:Portfel_walut): Unit ={
    for ((waluta,wartosc)<-inny.portfel)
      add_money(waluta,wartosc)
  }

  def print(): Unit ={
    for ( (waluta,ilosc)<- portfel)
      println("    "+waluta+" "+ilosc)
  }

  def print_do_pliku(pw:java.io.PrintWriter): Unit ={
    for ( (waluta,ilosc)<- portfel)
      pw.write("s "+waluta+" "+ilosc+"\n")
  }

  def print_do_pliku_czysto(pw:java.io.PrintWriter): Unit ={
    for ( (waluta,ilosc)<- portfel)
      pw.write("    "+Waluty.nazwa_ze_skroto(waluta)+" "+ilosc+"\n")
  }

}
