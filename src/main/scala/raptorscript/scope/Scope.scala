package raptorscript.scope

import scala.collection.mutable.LinkedHashMap


trait Scope {
  val parentScope: Option[Scope] = None

  val name: Option[String] = None

  private val symbols: LinkedHashMap[String, Symbol] = LinkedHashMap()

  def lookup(name: String): Option[Symbol] = symbols.get(name).orElse(parentScope.map(_.lookup(name).orNull))

  def define(symbol: Symbol): Unit = symbols.put(symbol.name, symbol)
}

class LocalScope(_parentScope: Scope) extends Scope {
  val parentScope = Some(_parentScope)
}

class FunScope(val funSymbol: FunSymbol) extends Scope {
  val name: Option[String] = Some(funSymbol.name)
  val parentScope = Some(funSymbol)
}

class GlobalScope extends Scope {
  val parentScope: Scope = null
  val name = Some("GLOBAL")
}
