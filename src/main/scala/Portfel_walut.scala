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

  def zmien_walute(data:String): Unit ={
    val eur="EUR"
    for((waluta,wartosc)<- portfel){
      if(waluta!=eur && Exchange_rate_history.contains(data,waluta)){
        this.add_money(eur,wartosc / Exchange_rate_history.exchange_with_euro(data,waluta))
        portfel-=waluta
      }
    }
  }

}
