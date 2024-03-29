# Subroutines

 Subroutines are first class citizens in ScriptBasic for Java. You can write subroutines in your
 BASIC program and call them from the main part of the BASIC program, or from another subroutine or
 from the same subroutine recursively.
 
 ScriptBasic for Java subroutines can have return values. There is no syntactic difference between
 subroutines and functions, procedures or anything else. There is subroutine only, that may return
 some value at its will. To return a value the statement `RETURN` is to be used. The format of a
 subroutine is:
 
```
SUB mySubroutine [ ( arguments ) ]

 commands
 [ RETURN expression ]

ENDSUB
```

 A subroutine starts with the keyword `SUB` and should have a unique name. You can not have two subroutines
 having the same name in a BASIC program. A subroutine can have arguments, but they are optional. If the subroutine
 does not have arguments, then you can optionally leave the parentheses off and you can simply write
 
```
SUB mySubroutine
 commands
ENDSUB
```

 or you can write, just as well
 
```
SUB mySubroutine()
 commands
ENDSUB
```
   
 Both approaches are equally good. However when you use a subroutine in an expression you can not omit the
 opening and closing parentheses. If you do so, the interpreter will treat the identifier as an undeclared
 variable and will not call the subroutine. Instead you will get `undef` value. 
 
 If you have arguments they behave like local variables. If there are arguments for your subroutine you have to
 provide those values when you call the subroutine. You have to provide exactly that many arguments as many the
 subroutine expects otherwise you will receive runtime error.
 
 To invoke a subroutine you can use the name of the subroutine in an expression followed by `(` and `)` and
 an expression list between the parentheses, like
 
```
a = 1 + mySubroutine(13,"apple","pie")
```
 
 If your subroutine does not return any value then the special value `undef` will be used as return value.
 If your subroutine is more a real subroutine than a function call you can call it using the keyword `CALL`:

```
CALL mySubroutine(13,"apple","pie")
```
 
 ScriptBasic for Java is quite liberal with the keyword `CALL` making it optional. Thus you can also write
 
```
mySubroutine(13,"apple","pie")
```
 
 You can also omit the opening and closing parentheses, thus it becomes:
 
```
CALL mySubroutine 13,"apple","pie"
```
   
 or

```
mySubroutine 13,"apple","pie"
```

 with this features ScriptBasic for Java can be used as a simple domain specific language implementation. 

   