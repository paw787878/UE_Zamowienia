import Hello.Dokument

class InfoPanstwoDanyOkres() {
  var min_value=new Portfel_walut()
  var max_value=new Portfel_walut()
  var liczba_wszystkich =0
  var liczba_nieodczytywalnych=0
  def add_dokument(dok:Dokument): Unit ={
    if(dok.czy_award_notice){
      liczba_wszystkich+=1
      if(dok.dziwny)
        liczba_nieodczytywalnych+=1
      else{
        min_value.add_money(dok.currency,dok.amount_min)
        max_value.add_money(dok.currency,dok.amount_max)
      }
    }
  }

  def add_infoPanstwoDanyOkres(inny:InfoPanstwoDanyOkres): Unit ={
    min_value.add_portfel(inny.min_value)
    max_value.add_portfel(inny.max_value)
    liczba_wszystkich+=inny.liczba_wszystkich
    liczba_nieodczytywalnych+=inny.liczba_nieodczytywalnych
  }
}
