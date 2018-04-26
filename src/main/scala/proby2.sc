import scala.sys.process._

"cd /home/pawelc/Desktop/Intelij_projects/Zamowienia_UE" !



val rozp="rozpakowane"

// "ls -al" !;

//"tar -zxvf dane/2017-10.tar.gz -C rozpakowane" !;
//rozpakoj("costam")
//"rm -rf rozpakowane/*" !;
//"ls rozpakowane" !;

val result=list("")

println(result)
println(result)

println("Helloooo")


def list(path: String): String ={
  "ls "+path !!
}


