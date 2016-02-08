@hello
object Test extends App {
  println("x - " + this.hello)

}

object Test2 extends App {
  import printfMacro._
  printf("hello %s!", "1")
}