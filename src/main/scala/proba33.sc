5+5
val s="costam/2011-01-04"
val a=s.split("/")(1)

def rob_nazwe(s:String):String={
  val nazwa=s.split("/")(1)

  return nazwa.substring(0,7)
}
rob_nazwe(s)