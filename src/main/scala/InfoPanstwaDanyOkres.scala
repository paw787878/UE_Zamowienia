import java.io.{File, PrintWriter}

import Wstepne_przetwarzanie.Dokument
import scala.io.Source

class InfoPanstwaDanyOkres() {
  /*
  Klasa ta służy do gromadzenia informacji o łącznej kwocie zleceń oraz o ich liczbie dla wszystkich państw
  zlecających w danym okresie czasu.

   */
  var panstwa=collection.mutable.Map[String, InfoPanstwoDanyOkres]()

  def add_dokument(dok:Dokument): Unit ={
    if (panstwa.contains(dok.country_iso)){
      panstwa(dok.country_iso).add_dokument(dok)
    }else{
      panstwa+= dok.country_iso -> new InfoPanstwoDanyOkres()
      panstwa(dok.country_iso).add_dokument(dok)
    }
  }
  def add_jedno_panstwo(iso:String,info:InfoPanstwoDanyOkres): Unit ={
    if(panstwa.contains(iso)){
      panstwa(iso).add_infoPanstwoDanyOkres(info)
    }else{
      panstwa+= iso-> info
    }
  }

  def add_panstwa(inny: InfoPanstwaDanyOkres): Unit ={
    for ((iso,info)<-inny.panstwa)
      add_jedno_panstwo(iso,info)
  }
  def print(): Unit ={
    for ((iso,info)<- panstwa){
      println(iso)
      info.print()
      println("")
      println("")
    }
  }
  def print_do_pliku(nazwa:String ): Unit ={
    val pw= new PrintWriter(new File(nazwa))


    for ((iso,info)<- panstwa){
      pw.write(iso+"\n")
      info.print_do_pliku(pw)
    }
    pw.close()
  }

  def print_do_pliku_czysto(nazwa:String ): Unit ={
    val pw= new PrintWriter(new File(nazwa))


    for ((iso,info)<- panstwa){
      pw.write(Panstwa.nazwa_ze_skroto(iso)+"\n")
      info.print_do_pliku_czysto(pw)
    }
    pw.close()
  }

  def doczytaj(path:String): Unit ={
    val source = Source.fromFile(path)
    val linie = source.getLines().toList
    val n=linie.size


    def wczytaj_panstwo(linie:List[String], poczatek:Int):(String,InfoPanstwoDanyOkres,Int)={
      var info=new InfoPanstwoDanyOkres()
      val n=linie.size
      val panstwo=linie(poczatek)

      var i=poczatek+2

      while (linie(i)(0)=='s') {
        info.min_value.add_money(linie(i).split(" ")(1), linie(i).split(" ")(2).toDouble)
        i+=1
      }
      i+=1

      while (linie(i)(0)=='s') {
        info.max_value.add_money(linie(i).split(" ")(1), linie(i).split(" ")(2).toDouble)
        i+=1
      }
      i+=1

      (info.liczba_wszystkich)+=(linie(i).toInt)
      i+=2
      (info.liczba_nieodczytywalnych)+=(linie(i).toInt)

      return (panstwo,info,i+1)
    }

    var i=0
    while( i<n){
      val tup=wczytaj_panstwo(linie,i)
      i=tup._3
      this.add_jedno_panstwo(tup._1,tup._2)
    }





  }

  def zmien_walute(data:String): Unit ={
    for ((panstwo,info)<- panstwa){
      info.zmien_walute(data)
    }
  }


}
