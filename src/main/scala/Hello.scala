import scala.sys.process._
import java.io.File

import scala.collection.mutable.ListBuffer
import xml._

import scala.xml.parsing
import scala.io.Source



object Hello {
  def main(args:Array[String]): Unit ={
  println("dziala")


















  }

  def Czytaj_miesieczne_z_folderu_dane(): Unit ={
    val lista_miesiecy=ls("dane")
    lista_miesiecy.foreach(println)



    for (mies<-lista_miesiecy){
      val nazwa="wyniki/"+rob_nazwe(mies)+".txt"
      var dane=new InfoPanstwaDanyOkres()
      untar(mies)
      val dni=ls(rozp)
      for(dzien<-dni){
        println(dzien)
        val sciezki_dokumentow=ls(dzien)
        val dokumenty=sciezki_dokumentow.map(czytaj_xml)
        for(d<- dokumenty)
          dane.add_dokument(d)

      }
      dane.print_do_pliku(nazwa)
    }
  }

  case class Dokument(czy_award_notice:Boolean, plik:String, country_iso: String, currency:String="", amount_min: Double=0,
                      amount_max:Double=0,
                      nieodczytywalny:Boolean=false, cos_jest_glebiej:Boolean=false, superdziwny:Boolean=false)
  //nieodczytywalny znaczy ze nie umiem tego wyciagnac wartosci
  //cos_jest_glebiej to znaczy, ze informacja o walucie moze byc zakopana glebiej niz patrze dotychczas
  // superdziwny to znaczy, ze nie reaguje wcale na moje przeszukiwania.










  def czytaj_xml(path: String): Dokument = {

    def to_double(napis: String): Double = {
      //zwroci -1 jesli sie nie da
      val bez_przecinkow = napis.replaceAll(",", ".")
      val pozycje = new ListBuffer[Int]()
      for (i <- 0 until bez_przecinkow.size)
        if (bez_przecinkow(i) == '.')
          pozycje += i
      val chary = bez_przecinkow.toCharArray()
      if (pozycje.size > 1) {
        for (i <- 0 until pozycje.size - 1)
          chary(pozycje(i)) = ' '
      }
      val ostateczny_tekst=String.valueOf(chary).replaceAll("\\s", "")
      try {return ostateczny_tekst.toDouble}
      catch {
        case _ => return -1
      }
    }

    def czy_award_notice(wczytane: scala.xml.Elem): Boolean = {
      val b = wczytane \\ "TD_DOCUMENT_TYPE"
      val kod = ((b(0) \ "@CODE").text)
      kod == "7"
    }

    def hasOnlyTextChild(node: scala.xml.Node) =
      node.child.size == 1 && node.child(0).isInstanceOf[scala.xml.Text]

    val wczytane_dane = XML.loadFile(path)



    def liczba_w_polu(tag:String,gdzie:scala.xml.Node):(Double,String) ={
      //-1 to znaczy, ze sie nie udalo
      //-2 to znaczy, ze tam cos dziwnego w srodku siedzi jeszcze
      val node_list = gdzie \\ tag
      if(node_list.size!=0){
        if(hasOnlyTextChild(node_list(0))){
          return (to_double(node_list(0).text),
            (node_list(0) \\ "@CURRENCY").text.replaceAll("\\s", ""))
        }else{
          return (-2,"")
        }
      }else{
        return (-1,"")
      }


    }

    def range_w_polu(tag: String,gdzie:scala.xml.Node): (Double,Double,String)={
      //zwraca min,max w tym polu jesli sa albo -1
      //gdy nie ma
      //lub -2 jak cos dziwnego siedzi w srodku
      val node_list = gdzie \\ tag
      if (node_list.size!=0){
        return (liczba_w_polu("LOW",node_list(0))._1,
          liczba_w_polu("HIGH",node_list(0))._1,
          (node_list(0) \\ "@CURRENCY").text.replaceAll("\\s", ""))
      }else{
        return (-1,-1,"")
      }
    }



    val country_iso = (((wczytane_dane \\ "ISO_COUNTRY") (0)
      \ "@VALUE").text).replaceAll("\\s", "")

    if (czy_award_notice(wczytane_dane)) {

      //zacznijmy od poszukania glebokich rzeczy

      var value_r= range_w_polu("VAL_RANGE_TOTAL",wczytane_dane)
      if(value_r._1 == -2 || value_r._2 == -2)
        return Dokument(true, path, country_iso, nieodczytywalny = true, cos_jest_glebiej = true)
      if(!(value_r._1 == -1 || value_r._2 == -1))
        return Dokument(true,path,country_iso,value_r._3,value_r._1,value_r._2)
      //wykluczony taki przypadek




      var value = liczba_w_polu("VAL_TOTAL", wczytane_dane)
      if (value._1 == -2)
        return Dokument(true, path, country_iso, nieodczytywalny = true, cos_jest_glebiej = true)
      if (value._1 != -1)
        return Dokument(true,path,country_iso,value._2,value._1,value._1)
      //teraz mamy juz wykluczony przypadek taki


      {
        var value= range_w_polu("VALUE_RANGE",wczytane_dane)
        if(value._1 == -2 || value._2 == -2)
          return Dokument(true, path, country_iso, nieodczytywalny = true, cos_jest_glebiej = true)
        if(!(value._1 == -1 || value._2 == -1))
          return Dokument(true,path,country_iso,value._3,value._1,value._2)
        //wykluczony taki przypadek
      }

      {
        var value = liczba_w_polu("VALUE", wczytane_dane)
        if (value._1 == -2)
          return Dokument(true, path, country_iso, nieodczytywalny = true, cos_jest_glebiej = true)
        if (value._1 != -1)
          return Dokument(true, path, country_iso, value._2, value._1, value._1)
        //teraz mamy juz wykluczony przypadek taki
      }


      val pom =(wczytane_dane \\ "VALUES_LIST")
      if(pom.size!=0 && pom(0).text=="")
        return Dokument(true,path,country_iso,"",nieodczytywalny=true)
      return Dokument(true,path,country_iso,nieodczytywalny=true,superdziwny=true)

    }else{

      Dokument(false,path,country_iso)
    }

  }









  val rozp="rozpakowane";
  def untar(path: String): Unit ={
    // untar .gz.tar folder to file rozpakowane
    def czysc(): Unit ={
      def deleteRecursively(file: File): Unit = {
        if (file.exists()) {
          if (file.isDirectory)
            file.listFiles.foreach(deleteRecursively)
          if (file.exists && !file.delete)
            throw new Exception(s"Unable to delete ${file.getAbsolutePath}")
        }
      }
      val a = new File(rozp)
      deleteRecursively(a)
      "mkdir "+rozp !

    }
    def dodatkowe_untarowywanie(sciezka:String): Unit ={

      def czy_starowany(path:String):Boolean={
        path.slice(path.size-7,path.size)==".tar.gz"
      }
      if (czy_starowany(sciezka)){
        "tar -zxvf "+sciezka+" -C "+rozp !;
        val plik = new File(sciezka)
        plik.delete

      }



    }
    czysc()
    "tar -zxvf "+path+" -C rozpakowane" !;
    val foldery=ls(rozp)
    foldery.foreach(dodatkowe_untarowywanie)

  }

  def ls(path: String): Array[String] ={
    //results list of directories in path
    val a="ls "+path !!;
    val b=a.split("\n").reverse
    b.map(x=> path+"/"+x)
  }

  def rob_nazwe(s:String):String={
    val nazwa=s.split("/")(1)

    return nazwa.substring(0,7)
  }



}
