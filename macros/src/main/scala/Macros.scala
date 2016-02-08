import scala.reflect.macros.whitebox.Context
import scala.language.experimental.macros
import scala.annotation.StaticAnnotation

object helloMacro {
  def impl(c: Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._
    import Flag._
    val result = {
      /**
        * http://docs.scala-lang.org/overviews/quasiquotes/intro.html
        * key words:q, tq, pq, Unquote, ..$, ...$
        *
        * http://docs.scala-lang.org/overviews/quasiquotes/syntax-summary.html
        */
      annottees.map(_.tree).toList match {
        case q"object $name extends ..$parents { ..$body }" :: Nil =>
          q"""
            object $name extends ..$parents {
              def hello: ${typeOf[String]} = "hello1"
              ..$body
            }
          """
      }
    }
    c.Expr[Any](result)
  }
}

class hello extends StaticAnnotation {
  def macroTransform(annottees: Any*) = macro helloMacro.impl
}
