import scala.sys.process._

object Hello {
  def main(args:Array[String]): Unit ={


    val spakowane=ls("dane")
    //untar(spakowane(0))

    val dni=ls(rozp)
    val testowe=ls(dni(0))
    val sciezka=testowe(0)
    println(sciezka)





  }

  val rozp="rozpakowane";

  def untar(path: String): Unit ={
    "tar -zxvf "+path+" -C rozpakowane" !;
  }

  def ls(path: String): Array[String] ={
    //results list of directories in path
    val a="ls "+path !!;
    val b=a.split("\n")
    b.map(x=> path+"/"+x)
  }

}
