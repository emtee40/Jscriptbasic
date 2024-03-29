# METHOD and USE, calling out to Java

 BASIC is a language that has very simple syntax. Because of this you can not use Java syntax to
 call a Java method. To let the BASIC script some way call java methods ScriptBasic for Java supports
 two format of method call. One is `O.m()` where `O` is a variable that holds a java object
 reference to call an instance method or it is a reference to a class to call a static method.
 
 To keep the syntax simple you can not call `java.lang.Double.valueOf(1.0)` to get the
 double value of the string `"1.0"`.
 It would not be BASIC like and also it would be very inconvenient. Instead you can define that you want to
 use the `Double` class from the package `java.lang`:
 
```
USE Double FROM java.lang
z = Double.valueOf("1.0")
PRINT z
```   

 The general format of the command is
 
```   
USER class FROM package [AS alias]
```   
 
The last optional part `AS alias` can be used to define an alias to be used instead of the java name
of the class. This is handy when the java class name is long, not BASIC like, or -- most of all -- the
class name is already used from a different package. Without the optional `AS alias` part you could not use 
`java.util.Date` and `java.sql.Date` in the same BASIC program. With it you can

```   
USE Date FROM java.util AS UtilDate
USE Date FROM java.sql AS SqlDate
```   

declare both of them with two different aliases. The above example is simple because the method
`valueOf()` has a string argument, and BASIC has String as a built in type. On the other hand
BASIC does not have many types. All it has character, string, floating point numbers, integer
numbers and boolean types. They are represented in the interpreter as Java `Character`,
`String`, `Double`, `Long` and `Boolean`. When the method `valueOf()` was called
the interpreter could easily find the java method `Double.valueOf(String)` with the defined
signature.

If you want to use the `java.lang.Math.sin(double)` method from BASIC you can not
 
```
REM this example is not working, it misses the command 'METHOD'
USE Math FROM java.lang
z = Math.sin(1.0)
PRINT z
```   
 
call `Math.sin` this way, because ScriptBasic is not so clevel and needs some hint.
The declaration above would like to invoke the method `java.lang.Method.sin(Double)`, which
does not exist in Java. The method signature contains `double` but the BASIC interpreter
seeks a method with `Double` argument (the first is primitive, the second is object).
 You have to help the interpreter telling that the method has `double` argument.
 To do that the BASIC command `METHOD` has to be extended with the `IS` part:
 
```
USE Math FROM java.lang
METHOD sin FROM java.lang.Math IS (double)
z = Math.sin(1.0)
PRINT z
```   

The command `METHOD` declares the signature of the method. The full syntax of
the command is

```
METHOD method FROM package IS (argument types) [USE AS alias]
```   
 
The part `argument types` is a comma separated list of the argument types. It should list the argument
classes of the method. For primitive types you have to use the Java name of the primitives:
`int`, `long`, `double`... and so on.

The arguments that are supposed to be objects the fully qualified class name has to be given.

The optional part `[USE AS alias]` provides a mean to use methods of the same name and different signatures.
BASIC can not overload the method. Instead the command 'METHOD' can define different aliases for the
different methods sharing a name:
 
```
METHOD abs FROM java.lang.Math IS (double) USE AS dabs
METHOD abs FROM java.lang.Math IS (int) USE AS iabs
```   
 
When you call the method from BASIC the interpreter converts automatically the arguments to the appropriate
type if that is possible.

The commands `USE` and `METHOD` are NOT declarative type commands in the sense that they
do not register the method at the start of the program. The commands have to be executed in the BASIC
program before the method can be used. It is a safe practice to list all these commands like declarative
commands at the start of the program, if there is any needed. 

Both the command `USE` and `METHOD` can be used with strings in place of the method name, alias and 
class/package names, although it is recommended to use constant string literals.

## Security

The USE and METHOD commands provide a possibility to call arbitrary class static methods 
out of the BASIC code. This is usually not a safe practice. ScriptBasic for Java have to be configured
to allow the execution of USE and METHOD.  
 