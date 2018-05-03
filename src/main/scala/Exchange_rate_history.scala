class Exchange_rate_history() {
  def wczytaj():collection.mutable.Map[(String,String), Double]={


    val path = "exchange_rates_to_euro"
    val bufferedSource = io.Source.fromFile(path)
    var linie = bufferedSource.getLines.toList
    bufferedSource.close
    val header = linie(0).split(",").map(_.trim)

    var dane = collection.mutable.Map[(String, String), Double]()
    var dodane_miesiace = collection.mutable.Map[String, Unit]()

    def czy_przed_17(s: String): Boolean = {
      return s.substring(s.size - 2, s.size).toDouble < 17
    }


    for (i <- 1 until linie.size) {
      val values = linie(i).split(",").map(_.trim)
      if (czy_przed_17(values(0)) && !dodane_miesiace.contains(values(0).substring(0, 7))) {
        dodane_miesiace += values(0).substring(0, 7) -> Unit
        for (j <- 1 until values.size)
          try {
            val exchange = values(j).toDouble
            dane += (values(0).substring(0, 7), header(j)) -> exchange

          } catch {
            case _ =>
          }
      }
    }
     dane
  }
  val dane=wczytaj()


  //dane(("2018-04","USD"))
  def contains(data:String,waluta:String):Boolean={
    return dane.contains((data,waluta))
  }
  def exchange_with_euro(data:String, waluta:String): Double={
    return dane((data,waluta))

  }


}
