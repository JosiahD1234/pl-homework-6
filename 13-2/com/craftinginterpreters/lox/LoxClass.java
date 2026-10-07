package com.craftinginterpreters.lox;

import java.util.List;
import java.util.Map;

class LoxClass implements LoxCallable {
  final String name;
  final LoxClass superclass;
  private final Map<String, LoxFunction> methods;

  LoxClass(String name, LoxClass superclass,
           Map<String, LoxFunction> methods) {
    this.superclass = superclass;
    this.name = name;
    this.methods = methods;
  }
  /*-------- Start of Code --------*/
  LoxFunction findMethod(LoxInstance instance, String name) {
    LoxFunction method = null;
    LoxFunction inner = null;
    LoxClass klass = this;
    while (klass != null) {
      if (klass.methods.containsKey(name)) {
        inner = method;
        method = klass.methods.get(name);
      }

      klass = klass.superclass;
    }

    if (method != null) {
      return method.bind(instance, inner);
    }

    return null;
  }
  /*-------- End of Code --------*/

  @Override
  public String toString() {
    return name;
  }
  @Override
  /*-------- Start of Code --------*/
  public Object call(Interpreter interpreter, List<Object> arguments) {
    LoxInstance instance = new LoxInstance(this);
    LoxFunction initializer = findMethod(instance, "init");
    if (initializer != null) {
      initializer.call(interpreter, arguments);
    }

    return instance;
  }
  /*-------- End of Code --------*/

  @Override
  public int arity() {
    /*-------- Start of Code --------*/
    LoxInstance instance = new LoxInstance(this);
    LoxFunction initializer = findMethod(instance, "init");
    /*-------- End of Code --------*/
    if (initializer == null) return 0;
    return initializer.arity();
  }
}
