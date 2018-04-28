class Portfel_walut() {
  var portfel=collection.mutable.Map[String, Double]()
  def add_money(waluta:String,ilosc:Double): Unit ={
    if (portfel.contains(waluta)) {
      portfel(waluta) += ilosc
    }else{
      portfel+= waluta-> ilosc
    }
  }

}
