package ts.demo

import demo.IExecutor;

object Responstory {
  def say(name: String) = println(name)

  def say(datas: Seq[String]) = datas.foreach(f => println(f))

  def say(datas: List[String]) = datas.foreach(f => println(f))

  def say(datas: Set[String]) = datas.foreach(f => println(f))

  //  def say(datas:Map[String,String])=datas.foreach(f=>println(f._1,f._2))

  def say(datas: Map[String, String]) = datas.foreach { case (key, value) => println(">>> key=" + key + ", value=" + value) }

  def say(datas: Iterator[String]) = datas.foreach(f => println(f))

  var sayCallback = (datas: List[String]) => {
    datas.foreach(f => println(f, "!"))
    "completed"
  }

  def say(data:String,f:String=>String)=f(data)
  
  def say(datas: List[String], f: List[String] => String) = f(datas)
  
  def say(data:String,executor:IExecutor) = executor.execute(data)
  
  def say(data:String,executor:Res) = executor.exec(data)
}