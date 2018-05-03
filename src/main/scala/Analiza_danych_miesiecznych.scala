object Analiza_danych_miesiecznych {
  def main(args:Array[String]): Unit ={
    println("to jest inny obiekt!")

    var a= new InfoPanstwaDanyOkres()
    a.doczytaj("wyniki/2011-01.txt")
    a.print()
  }

}
