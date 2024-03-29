# `FOR` loop

ScriptBasic for Java supports the classical `FOR` loop. The syntax of this looping construct is
 
```
FOR loop_variable = start_expression TO end_expression [ STEP step_expression ]
  commands
NEXT [ loop_variable ]  
```

The optional `STEP step_expression` may be missing. In that case the step
value used to increment  the loop variable is 1.

When the loop starts the expressions are evaluated and the loop variable gets
the value of the `start_expression`. At each iteration of the loop before
the `commands` start the command `FOR` checks that the current `loop_variable`
is still between the start and end values including the boundaries. To make this decision the
the signum value of the `step_expression` is taken into account.

In other words the loop variable value will run from the start till the end but if the start value
is already larger than the end value and the step value is positive, or if the 
start value is already smaller than the end value and thes tep value is negative at the
start of the loop then the `commands` parts are not executed at all.

Still in other words: the `FOR` loop checks the condition at the start. For example

```
for i=9 to 1
 print i
next i 
```

does not print out any number, because the `for` loop identifies that the loop variable is out
of the looping range even before starting looping.

The expressions can be integer or floating point type numbers.

Then end of the loop is denoted with the command `NEXT`. When this command is reached
the execution goes to the start of the loop, the loop variable is stepped by the step value and
the execution of the loop body is started again unless the loop is ended. The command keyword
`NEXT` is optionally followed by the loop variable. It is optional. You can just write
 
```
for i=1 to 10
 print i
next
```

to print the numbers from 1 to 10. However if you present the variable following the keyword
`NEXT` it has to be the same as the one used in the command `FOR`.

The loop variable is not protected inside the loop. You can modify the loop variable
even though it is a bad practice. The loop

```
for i=1 to 10
 print i
 i = 10
next i
```
will print out only the number 1 and after that it will exit the loop. The start, end and step values are
only calculated once when the loop starts. For example the following program will print out 22:

```
startV = 1
endV = 22
stepV = 1
count = 0
for d=startV to endV step stepV
  count = count + 1
  stepV = stepV + 1
  endV = 2
next
print count
```
 
The loop will start from 1 and go till 22 and this is not disturbed by the fact that the variables `endV` and `startV`
are modified during the execution of the loop. If the loop is started again then the start, end and step expressions
are evaluated again before starting the loop.

## Recursive calls and `FOR` bug

For loop "end" and "step" values are evaluated only once at the start of the execution of the loop.
The calculated values are stored in objects that are associated with the actual command instance.
When the same loop starts again these expressions are evaluated again and then the variables
are overwritten. This can happen in case of recursive calls before the loop finishes. In this case the
the original for loop end and step values are overwritten and the new values used to finish the loop after
the recursive call. This is eventually a structural bug in ScriptBasic for Java.

The following code demonstartes this bug. The output of the ptogram is `12323`.

```
' For loop end and step values are evaluated only once at the start of the execution of the loop.
' The calculated values are stored in objects that are associated with the actual command instance.
' When the same loop starts again these expressions are evaluated again and then the variables
' are overwritten. This can happen in case of recursive calls before the loop finishes. This is
' a structural bug in ScriptBasic and is demonstrated by the following test code.
'
' If ever this gets fixed the documentation of the FOR loop also has to be modified, because this
' bug is documented there.
'

' true once and then set to false so we call
' the subroutine 'a' only once recursively
z = true

' the original start finish values
start = 1
finish = 5

sub a
' we access these global variables, have to be declared
' otherwise we modify local variables
global z,start,finish, delta

' first time '1 to 5'
' before the recursive call these are modified '2 to 3'
' the first execution will also finish at 3 as the finish value
' is overwritten during the recursive call
for i=start to finish
  print i
  ' new start value when calling recursive
  start = 2
  ' new finish value when calling recursive
  finish = 3
  if z then
    ' set to false so this is the only recursive
    z = false
    call a
    ' recursive call already evaluated the end value
    ' to 3, this assignment does not change anything
    finish = 4
  endif
next
endsub

call a
```