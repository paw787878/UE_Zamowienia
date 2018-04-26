import scala.sys.process._

val rozp="rozpakowane";

def list(path: String): Unit ={
  "ls "+path !;
}

// "ls -al" !;

//"tar -zxvf dane/2017-10.tar.gz -C rozpakowane" !;
//rozpakoj("costam")
//"rm -rf rozpakowane/*" !;
//"ls rozpakowane" !;
val result=list(rozp)
println(result)
println("Helloooo")


