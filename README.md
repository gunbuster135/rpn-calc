## RPN Calculator
Calculator CLI application that receives commands in reverse polish notation.

### Requirements
To build and run, you will need Java 11+

    $ java -version
    openjdk version "11.0.2" ...

### Build 
Run the following commands to build:

    $ ./gradlew clean build
### Running
Run the application through gradle

    $ ./gradlew run
If you prefer, you can also just run the following to execute the built `JAR`

    $ java -jar build/libs/rpn-calc-1.0-SNAPSHOT.jar

### Instructions
Input is provided as either operands or operations. Operands are just that, plain numerical value. 
These will be pushed to the stack

    $ 5 6
    Stack: 5 6 

Operations are mathematical operations & should be self-explanatory. 
See following for a list of all supported math operations:

    + - * /
    sqrt 

The following operations are for manipulating the stack
 
    clear ## clears the current stack
    undo  ## undo's the most recent operation (Undo does not undo other undo's!)

### Examples

Add two numbers from the top of the stack
        
    $ 5 6 3 +
    Stack: 5 9 

Undo the most recent operation

    $ 5 5 * 
    Stack: 25
    $ undo
    Stack: 5 5

Clear the stack 

    $ 5 5 3 *
    Stack: 5 15
    $ clear
    Stack:
    $ 100 10 /
    Stack: 10

