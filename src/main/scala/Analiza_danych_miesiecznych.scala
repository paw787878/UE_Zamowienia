object Analiza_danych_miesiecznych {
  def main(args:Array[String]): Unit ={

    println("to jest inny obiekt!")
    analiza_bez_exchange()
    /*


    println(Exchange_rate_history.exchange_with_euro("2018-04","USD"))

    println(Panstwa.nazwa_ze_skroto("AF"))



    for ((a,b)<-Panstwa.panstwa)
      println(a+";"+b)
    */

    /*
    var a= new InfoPanstwaDanyOkres()
    a.doczytaj("wyniki/2011-01.txt")
    a.print()
    */
  }

  def analiza_bez_exchange(): Unit ={
    var wszystkie=new InfoPanstwaDanyOkres()
    val do_przerobienia=Hello.ls("wyniki")
    for(path<- do_przerobienia){
      //val miesiac=path.split("/")(1).substring(0,7)
      wszystkie.doczytaj(path)

    }
    wszystkie.print_do_pliku_czysto("wyniki_bez_exchange.txt")

  }

}
