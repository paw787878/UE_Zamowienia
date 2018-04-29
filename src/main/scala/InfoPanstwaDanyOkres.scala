import java.io.{File, PrintWriter}

import Hello.Dokument

class InfoPanstwaDanyOkres() {
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


}
