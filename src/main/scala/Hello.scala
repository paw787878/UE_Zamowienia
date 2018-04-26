import scala.sys.process._

object Hello {
  def main(args:Array[String]): Unit ={
    "ls -al" !;

    "tar -zxvf dane/2017-10.tar.gz -C rozpakowane" !;
    println("Helloooo")
  }
}
