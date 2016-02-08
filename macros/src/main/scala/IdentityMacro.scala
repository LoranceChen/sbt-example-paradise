import scala.reflect.internal.annotations.compileTimeOnly
import scala.reflect.macros.whitebox.Context
import scala.language.experimental.macros
import scala.annotation.StaticAnnotation

/**
  * http://docs.scala-lang.org/overviews/macros/annotations.html
  * http://docs.scala-lang.org/overviews/macros/blackbox-whitebox.html
  */
object identityMacro {
  def impl(c: Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._
    val inputs = annottees.map(_.tree).toList
    val (annottee, expandees) = inputs match {
      case (param: ValDef) :: (rest @ (_ :: _)) => (param, rest)
      case (param: TypeDef) :: (rest @ (_ :: _)) => (param, rest)
      case _ => (EmptyTree, inputs)
    }
    println((annottee, expandees))
    val outputs = expandees
    c.Expr[Any](Block(outputs, Literal(Constant(()))))
  }
}

@compileTimeOnly("enable macro paradise to expand macro annotations")
class identity extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro ???
}
