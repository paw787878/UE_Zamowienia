import scala.io.Source

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

  return (panstwo,info,i)
}
val path="2011-01.txt"
val source = Source.fromFile(path)
val linie = source.getLines().toList

val tup=wczytaj_panstwo(linie,0)
tup._2.print()