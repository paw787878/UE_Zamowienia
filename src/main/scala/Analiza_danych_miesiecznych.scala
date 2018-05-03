object Analiza_danych_miesiecznych {
  def main(args:Array[String]): Unit ={
    println("to jest inny obiekt!")

val a= new Exchange_rate_history()
    println(a.exchange_with_euro("2018-04","USD"))

/*
    val w=new Panstwa()
    for ((a,b)<-w.panstwa)
      println(a+";"+b)
      */

    /*
    var a= new InfoPanstwaDanyOkres()
    a.doczytaj("wyniki/2011-01.txt")
    a.print()
    */
  }

}
