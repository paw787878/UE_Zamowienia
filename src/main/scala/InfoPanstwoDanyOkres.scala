import java.io.{File, PrintWriter}

import Wstepne_przetwarzanie.Dokument

class InfoPanstwoDanyOkres() {
  /*
  Pomocnicza klasa dla klasy InfoPanstwaDanyOkres

   */
  val nazwa_folderu="wyniki"
  var min_value=new Portfel_walut()
  var max_value=new Portfel_walut()
  var liczba_wszystkich =0
  var liczba_nieodczytywalnych=0
  def add_dokument(dok:Dokument): Unit ={
    if(dok.czy_award_notice){
      liczba_wszystkich+=1
      if(dok.nieodczytywalny)
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
  def print(): Unit ={
    println("  min_value")
    min_value.print()
    println("")
    println("  max_value")
    max_value.print()
    println("")
    println("  wszystkich transakcji ( w tym nieodczytywalnych")
    println("    "+liczba_wszystkich)
    println("")
    println("  liczba_nieodczytywalnych")
    println("    "+liczba_nieodczytywalnych)

  }
  def print_do_pliku(pw:java.io.PrintWriter): Unit ={

    pw.write("min_value"+"\n")
    min_value.print_do_pliku(pw)
    pw.write("max_value"+"\n")
    max_value.print_do_pliku(pw)
    pw.write("wszystkich transakcji ( w tym nieodczytywalnych)"+"\n")
    pw.write(liczba_wszystkich.toString+"\n")
    pw.write("liczba_nieodczytywalnych"+"\n")
    pw.write(liczba_nieodczytywalnych.toString+"\n")



  }

  def print_do_pliku_czysto(pw:java.io.PrintWriter): Unit ={

    pw.write("  Minimalna wartosc w wczytywalnych"+"\n")
    min_value.print_do_pliku_czysto(pw)
    pw.write("  Maksymalna wartosc w wczytywalnych"+"\n")
    max_value.print_do_pliku_czysto(pw)
    pw.write("  Liczba wszystkich transakcji ( w tym nieodczytywalnych)"+"\n")
    pw.write(liczba_wszystkich.toString+"\n")
    pw.write("  Liczba_nieodczytywalnych"+"\n")
    pw.write(liczba_nieodczytywalnych.toString+"\n")



  }

  def zmien_walute(data:String): Unit ={
    min_value.zmien_walute(data)
    max_value.zmien_walute(data)
  }


}
