package ts.demo

import demo.UserInfo
import scala.collection.immutable.Seq

/**
 * @author ${user.name}
 */
object App {
  
  def foo(x : Array[String]) = x.foldLeft("")((a,b) =>{
    a + b
  })
  
  def main(args : Array[String]) {
    println( "Hello World!" )
    
    var z = Array("Runoob", "Baidu", "Google")
    println("concat arguments = " + foo(z))
    
    var user:UserInfo = new UserInfo(2342)
    println(user)
    
    var datas= Seq("Runoob", "Baidu", "Google");
    for(item <- datas)
      println(item)
      
    Responstory.say(datas)
    
    val it = Iterator("Baidu", "Google", "Runoob", "Taobao")
    Responstory.say(it)
  }

}
