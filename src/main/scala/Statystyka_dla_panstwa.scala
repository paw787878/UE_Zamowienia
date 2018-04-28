import Hello.Dokument

class Statystyka_dla_panstwa() {
  var min_amount=0.0
  var max_amount=0.0
  var liczba_wszystkich=0.0
  var liczba_nieodczytywalnych=0.0

  def add(inny: Statystyka_dla_panstwa): Unit ={
    min_amount+=inny.min_amount
    max_amount+=inny.max_amount
    liczba_wszystkich+=inny.liczba_wszystkich
    liczba_nieodczytywalnych+=inny.liczba_nieodczytywalnych
  }
  def add(inny:Dokument): Unit ={
    min_amount+=inny.amount_min
    max_amount+=inny.amount_max
    if (inny.czy_award_notice)
      liczba_wszystkich+=1
    if(inny.czy_award_notice && inny.dziwny)
      liczba_nieodczytywalnych+=1
  }

}
