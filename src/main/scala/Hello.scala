import scala.sys.process._
import java.io.File
import xml._
import scala.xml.parsing

import scala.io.Source



object Hello {
  def main(args:Array[String]): Unit ={


    val spakowane=ls("dane")
    //untar(spakowane(0))

    val dni=ls(rozp)
    val testowe=ls(dni(0))
    val sciezka=testowe(0)
    val filename=sciezka

     //val linie=Source.fromFile(filename,enc="UTF-8").getLines()
    //val string_z_pliku = scala.io.Source.fromFile("example.xml").mkString
//     for(f<-linie)
//       println(f)

    //val a=XML.loadFile("example.xml")



    //println(a)

    // val linie=Source.fromFile(filename).getLines()
   // for(f<-linie)
   //   println(f)
    val zly="397361_2017.xml"
    val dobry="386737_2017.xml"
    czytaj_xml(dobry)



   //println(sciezka)









  }

//  def czytaj_xml(path:String): Unit ={
//    val wczytane_dane=XML.loadFile(path)
//
//    def czy_award_notice(wczytane:scala.xml.Elem): Boolean ={
//      val b= wczytane \\ "TD_DOCUMENT_TYPE"
//
//      val kod=((b(0) \ "@CODE").text)
//
//      kod=="7"
//    }
//    print(czy_award_notice(wczytane_dane))
//
//
//  }
def czytaj_xml(path:String): Unit ={
  val wczytane_dane=XML.loadFile(path)

  def czy_award_notice(wczytane:scala.xml.Elem): Boolean ={
    val b= wczytane \\ "TD_DOCUMENT_TYPE"

    val kod=((b(0) \ "@CODE").text)

    kod=="7"
  }

  if (czy_award_notice(wczytane_dane)) {
    val b= wczytane_dane(0) \\ "VALUE"
    val currency= (b \ "@CURRENCY").text
    val amount=b.text.toFloat
    println(currency)
    println(amount)
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
    czysc()
    "tar -zxvf "+path+" -C rozpakowane" !;
  }

  def ls(path: String): Array[String] ={
    //results list of directories in path
    val a="ls "+path !!;
    val b=a.split("\n")
    b.map(x=> path+"/"+x)
  }



}
