import scala.sys.process._
import java.io.File

import scala.collection.mutable.ListBuffer
import xml._
import scala.xml.parsing
import scala.io.Source



object Hello {
  def main(args:Array[String]): Unit ={



    //untar("dane/2018-02.tar.gz")

    val dni=ls(rozp)
   val testowe=ls(dni(0))

//    for (i<- 0 until testowe.size){
//      println(i)
//      println(testowe(i))
//      czytaj_xml(testowe(i))
//
//    }


    val dokumenty = testowe.map(czytaj_xml)
    def czy_dziwny(d:Dokument):Boolean={
      d.dziwny
    }
    def czy_kontrakt(d:Dokument):Boolean={
      d.czy_award_notice
    }
    val dziwne=dokumenty.filter(czy_dziwny)
    def drukuj_nazwe(d:Dokument): Unit ={
      println(d.plik)
    }

    println("tyle dziwnych"+dziwne.size)
    println("tyle wszystkich kontraktow"+dokumenty.filter(czy_kontrakt).size)
   //dziwne.foreach(drukuj_nazwe)










    //println(a)

    // val linie=Source.fromFile(filename).getLines()
   // for(f<-linie)
   //   println(f)
//    val zly="397361_2017.xml"
//    val dobry="386737_2017.xml"

















  }

  case class Dokument(czy_award_notice:Boolean,plik:String, currency:String="", amount_min: Double=0,
                      amount_max:Double=0,
                      country_iso: String="",dziwny:Boolean=false)




  def czytaj_xml(path:String): Dokument={
    def to_double(napis:String): Double={


      val bez_przecinkow=napis.replaceAll(",",".")
      val pozycje= new ListBuffer[Int]()
      for (i <- 0 until bez_przecinkow.size)
        if (bez_przecinkow(i)=='.')
          pozycje+=i

      val chary=bez_przecinkow.toCharArray()
      if (pozycje.size>1){
        for (i <- 0 until pozycje.size-1)
          chary(pozycje(i))=' '
      }

      String.valueOf(chary).replaceAll("\\s","").toDouble



    }
    val wczytane_dane=XML.loadFile(path)

    def czy_award_notice(wczytane:scala.xml.Elem): Boolean ={
      val b= wczytane \\ "TD_DOCUMENT_TYPE"
      val kod=((b(0) \ "@CODE").text)

      kod=="7"
    }

    if (czy_award_notice(wczytane_dane)) {
      val b= wczytane_dane \\ "VALUE"
      if (b.size!=0) {
        val currency = ((b(0) \ "@CURRENCY")).text

        val country_iso = ((wczytane_dane \\ "ISO_COUNTRY")(0) \ "@VALUE").text
        try {
          val amount = to_double(b(0).text)
        }catch{ case _ =>return Dokument(true,path,dziwny=true)}
        val amount = to_double(b(0).text)
        return Dokument(true, path, currency.replaceAll("\\s", ""), amount,amount, country_iso.replaceAll("\\s", ""))
      }else{
        val b= wczytane_dane \\ "VALUE_RANGE"
        if(b.size!=0){

          val min=to_double((b(0) \ "LOW").text)
          val max=to_double((b(0) \ "HIGH").text)

          val country_iso = ((wczytane_dane \\ "ISO_COUNTRY")(0) \ "@VALUE").text
          val currency= (b(0) \ "@CURRENCY" ).text
          return Dokument(true, path,currency.replaceAll("\\s", ""),min,max,country_iso.replaceAll("\\s", ""))
        }

        return Dokument(true,path,dziwny=true)
      }
    } else
      return Dokument(false,path)

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
    val b=a.split("\n")
    b.map(x=> path+"/"+x)
  }



}
