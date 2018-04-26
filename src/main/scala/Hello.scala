import scala.sys.process._

object Hello {
  def main(args:Array[String]): Unit ={


   // "ls -al" !;

    //"tar -zxvf dane/2017-10.tar.gz -C rozpakowane" !;
    //rozpakoj("costam")
    //"rm -rf rozpakowane/*" !;
    //"ls rozpakowane" !;
    val dni=list(rozp)
    val testowe=list(rozp+"/"+dni(0))
    val sciezka=rozp+"/"+dni(0)+"/"+testowe(0)
    println(sciezka)




  }

  val rozp="rozpakowane";



  def list(path: String): Array[String] ={
    //results list of directories in path
    val a="ls "+path !!;
    a.split("\n")
  }

}
