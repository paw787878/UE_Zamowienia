import scala.sys.process._
import java.io.File

import scala.collection.mutable.ListBuffer
import xml._

import scala.xml.parsing
import scala.io.Source



object Wstepne_przetwarzanie {
  def main(args:Array[String]): Unit ={
    /*
    Program ten przeprowadza wstępną analizę danych znajdujących się w dokumentach xml.
    Na początek należy umieścić w folderze "dane" pobrane dane miesięczne w formacie xml.
    Pliki te powinny być dalej w formacie .tar.gz
    Dane te mogą być z dowolnego okresu dla którego dostępne są dane miesięczne.
    Metoda "Czytaj_miesieczne_z_folderu_dane()" będzie dla każdego miesiąca zliczać liczbę zamówień oraz
    ich łączne kwoty w różnych walutach, a następnie zapisze wyniki dla danego miesiąca w folderze "wyniki".

    Mój program czyta tylko dokumenty typu "award notice" to znaczy takie, które mają w tagu <td_document_type>
    code=7. Dla takich dokumentów państwo zamawiające jest ustalane na podstawie tagu <ISO_COUNTRY>. Może to prowadzić
    do kłopotów związanych z sytuacjami, gdy jest więcej niż jedno państwo zamawiające. Zakładam, że państwo z tagu
    <ISO_COUNTRY> ma największe udziały.

    Wartość otrzymanych funduszy poszukuję w tagach VAL_RANGE_TOTAL, VAL_TOTAL, VALUE_RANGE oraz VALUE kolejno.
    Jeśli te tagi mają w w sobie zakres wartości od minimalnej do maksymalnej to wczytuję obydwie wielkości.
    Z moich doświadczeń w inspekcji przypadków w których program sobie nie radził doszedłem do wniosku, że
    jeśli nie uda mi się wczytać kwoty w ten sposób, to prawdopodobnie tej informacji nie ma w dokumencie lub jest w
    tekście w formie trudnej do odczytania. Zakładam również, że w przypadku ogłoszenia kilku nagród w jednym
    dokumencie pierwszym tagiem typu VAL_RANGE_TOTAL, VAL_TOTAL, VALUE_RANGE lub VALUE jest tag zawierający informację
    o całkowitej wartości wygranych, gdyż tak było w dokumentach które sprawdziłem ręcznie.

    Jako zlecenie liczę sytuację, gdy występuje contract award notice z danym państwem w tagu <ISO_COUNTRY>.

     */
  Czytaj_miesieczne_z_folderu_dane()

  }

  def Czytaj_miesieczne_z_folderu_dane(): Unit ={
    val lista_miesiecy=ls("dane")
    lista_miesiecy.foreach(println)



    for (mies<-lista_miesiecy){
      val nazwa="wyniki/"+rob_nazwe(mies)+".txt"
      //tworzę obiekt który będzie gromadził w sobie informacje o liczbie zleceń dla państw oraz ich sumie
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
  //poniższa klasa pamięta najważniejsze wartości dla danego dokumentu. Zawiera w sobie także informacje, które
  //były mi użyteczne przy poprawianiu programu.
  case class Dokument(czy_award_notice:Boolean, plik:String, country_iso: String, currency:String="", amount_min: Double=0,
                      amount_max:Double=0,
                      nieodczytywalny:Boolean=false, cos_jest_glebiej:Boolean=false, superdziwny:Boolean=false)
  //nieodczytywalny znaczy ze nie umiem tego wyciagnac wartosci
  //cos_jest_glebiej to znaczy, ze informacja o walucie moze byc zakopana glebiej niz patrze dotychczas
  // superdziwny to znaczy, ze nie reaguje wcale na moje przeszukiwania.










  def czytaj_xml(path: String): Dokument = {
    /*
    Metoda da odczytuje najważniejsze informacje z dokumentu xml, to znaczy
    zwraca obiekt typu Dokument z tymi informacjami.
     */

    def to_double(napis: String): Double = {
      //zamienia string z liczą na Double. Potrafi obrobić także przypadki typu "1 000.000,00"
      //zwroci -1 jesli sie nie da zamienić tego napisu na Double
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
      //Sprawdza czy dany dokument jest typu award notice
      val b = wczytane \\ "TD_DOCUMENT_TYPE"
      val kod = ((b(0) \ "@CODE").text)
      kod == "7"
    }

    def hasOnlyTextChild(node: scala.xml.Node) =
      node.child.size == 1 && node.child(0).isInstanceOf[scala.xml.Text]

    val wczytane_dane = XML.loadFile(path)



    def liczba_w_polu(tag:String,gdzie:scala.xml.Node):(Double,String) ={
      //Metoda stara się odczytać liczbę znajdującą się w tagu tag
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
      //zwraca wartość tagu <low> oraz <high> w tym polu lub -1
      //gdy nie ma tam takich tagów
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

      //zacznijmy od poszukania tagów, które są żadko ale za to mogą być zakopane głęboko w strukturze

      var value_r= range_w_polu("VAL_RANGE_TOTAL",wczytane_dane)
      if(value_r._1 == -2 || value_r._2 == -2)
        return Dokument(true, path, country_iso, nieodczytywalny = true, cos_jest_glebiej = true)
      if(!(value_r._1 == -1 || value_r._2 == -1))
        return Dokument(true,path,country_iso,value_r._3,value_r._1,value_r._2)





      var value = liczba_w_polu("VAL_TOTAL", wczytane_dane)
      if (value._1 == -2)
        return Dokument(true, path, country_iso, nieodczytywalny = true, cos_jest_glebiej = true)
      if (value._1 != -1)
        return Dokument(true,path,country_iso,value._2,value._1,value._1)



      {
        var value= range_w_polu("VALUE_RANGE",wczytane_dane)
        if(value._1 == -2 || value._2 == -2)
          return Dokument(true, path, country_iso, nieodczytywalny = true, cos_jest_glebiej = true)
        if(!(value._1 == -1 || value._2 == -1))
          return Dokument(true,path,country_iso,value._3,value._1,value._2)

      }

      {
        var value = liczba_w_polu("VALUE", wczytane_dane)
        if (value._1 == -2)
          return Dokument(true, path, country_iso, nieodczytywalny = true, cos_jest_glebiej = true)
        if (value._1 != -1)
          return Dokument(true, path, country_iso, value._2, value._1, value._1)

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
    // untarowuje plik o rozszerzeniu .gz.tar do folderu rozpakowane, wcześniej opróżniając tamten folder.
    //Uwzględnia fakt, że w starszych plikach każdy dzień jest również spakowany.
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
    //gives list of directories in path
    val a="ls "+path !!;
    val b=a.split("\n").reverse
    b.map(x=> path+"/"+x)
  }

  def rob_nazwe(s:String):String={
    //pomocnicza funkcja
    val nazwa=s.split("/")(1)

    return nazwa.substring(0,7)
  }



}
