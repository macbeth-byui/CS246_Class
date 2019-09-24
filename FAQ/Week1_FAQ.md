**Exception Handling**

Just like C++ and Python, Java provides exception handling.  You can throw an exception from a function and catch it in another function.  

```java
public void doSomething() throws Exception {
   // Do some stuff
   if (error) {
      throw new Exception("An Error Occurred");
   }
   // Do some other stuff
}
```

In the code above, notice that the exception is an object of an Exception class.  You can create your own exception classes (that can store additional data and provide additional functions) by extending the Exception class (inheriting).

```java
public class MyException extends Exception {
    public MyException(String errorMessage) {
        super(errorMessage);
        // I can have additional data initialized here
    }
}
```

In the example above, the constructor in MyException has to call the constructor in Exception explicitly.  The super() function will call the non-default constructor in the Exception class that takes a single String.  We can now modify the doSomething function to throw this new MyException as well:

```java
public void doSomething() throws Exception {
   // Do some stuff
   if (error) {
      throw new Exception("An Error Occurred");
   }
   else if (otherError) {
       throw new MyException("The Other Error Occurred")
   }
   // Do some other stuff
}
```

The calling function must catch the exception or pass it along to the next function.  To pass it along, the function declaration can just write "throws Exception".  To catch it, you need to use the try/catch syntax.

```java
try
{
    doSomething();  // This function can throw an exception
    // Do some other code.
    // If exception is thrown in doSomething, then this other code won't run.
}
catch (MyException mye) {
    System.out.println("MyException Occurred!!! Details: " + mye.getMessage()); 
}
catch (Exception e) {
    System.out.println("Exception Occurred!!!  Details: " + e.getMessage());
}
```

In the example above, notice that you can catch more than one Exception.  MyException was listed first since technically MyException "isa" an Exception.

**Variables in Java**

In Java, all variables (native types or objects of classes), must be declared before they are used.  You should ensure that your variables are initialized before use.  You can initialize a variable of a class type to null if you don't want to create the object yet.

```java
Product p = null;
// Do some code
p = new Product();
```

When you pass an object to a function via a parameter, the address (think pointer in C++) is passed by value.  This means that the function will have access to the object and therefore be able to modify it.  When you pass a variable of native type (eg., int, float, char), it is passed by value.  

If a variable is equal to an object and then you set it equal to a different object, then Java will take care of deleting the old object when it determines it is no longer being used by anyone else.

When you create variables, you are encouraged to use the camelBack case (capitalize the first letter of each word except the first letter).  However, there is no mandatory rule that you have to follow.  The use of underscore is common in some of the examples especially from private data.  You are not expected to follow this pattern.

**Regular Expression**

If you look in the JavaDoc (JDK 10: https://docs.oracle.com/javase/10/docs/api/index.html?overview-summary.html) under the String class, you will a function called matches.  This function allows you to create regular expression statements that are used to match text you are searching for.

In the teacher solution, it used: .*\\d+.*  This means match any string that has at least one digit (.* means match 0 or more of anything and \\d+ means match at least one (the +) digit (the \\d ... java requires an extra slash ... just like in C++).  This is a powerful technique to learn.  Here is a website to learn more about the syntax:  https://regexone.com/

There are multiple ways to use regular expression in Java.  The easiest is to use the matches function from the String class (https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/lang/String.html#matches(java.lang.String)).  

The other option (instead of regular expression), is to loop through each character of the password and count the digits:

```java
boolean hasDigit = false;
for (int i=0; i<password.length; i++) {
    if (Character.isDigit(password[i])) {
        hasDigit = true;
    }
}
if (!hasDigit) {
    throw new WeakPasswordException("Password must contain at least 1 digit.");
}
```

**Cryptography**

Here is a link with detailed information about Java libraries that were used to provide cryptography in NSALoginController:
https://docs.oracle.com/javase/8/docs/technotes/guides/security/crypto/CryptoSpec.html

**Classes**

In Java, everything is a class.  If you think about the benefits of creating a class in C++, a class allows you to build a collection of libraries with API's that other's can use build their software.  Since Java requires everything to be a class, programmers employ this reuse strategy with their software.

You are able to extend any class to make a more specialized class.  However, if a class is marked "final" then you cannot extend it further.

**UML Diagrams**

This is a course is design and development.  Whenever we tackle a project or look at a new design pattern, we will rely on UML to communicate the design architecture with each other.  UML diagrams are good way to organize data, functions, and relationships.  While you will only be graded on one UML diagram that you make for your android app, you are encouraged to draw UML diagrams with all of your object oriented projects.  You can create a free account on www.lucidchart.com to make UML diagrams electronically.

**Java Libraries**

You will notice that Java has alot of libraries that you can import and use.  This is by design.  Imagine all the java code that is behind all the classes that you are re-using in your own code.  This the beauty of an object oriented environment.  You don't need to understand how all of those functions work.  You just get to use them.  When you develop your Android app, you will be using many more classes that will perform large quantities of work for you behind the scenes.

You can learn more about these libraries here:

* JDK 12: https://docs.oracle.com/en/java/javase/12/docs/api/index.html
* Java 8 (old but nicer format): https://docs.oracle.com/javase/8/docs/api/

We use the import command to gain access to classes in other packages.  If you write several classes (files) in your project and put them all in one package, then each class will be able to see all the other classes in that package automatically.  Unlike C++, you don't have to #include every other class file before you use it ... so long as its in your package.

**Javadoc**

You know that comments are important (and mandatory) when you code.  When you code in Java, you should take advantage of the javadoc format for your comments.  When you write a comment block before each class and each function, use this format:

```java
/**
 * Your Comments Here
 */
```

Intelli-J (and many other IDE's) will auto format these for you and include additional detail about function parameters.  Here a good summary link: https://en.wikipedia.org/wiki/Javadoc 

**Arrays**

The Java Collection Framework defines several classes for collections like List and Map.  However, if you want an old fashioned array, you will declare it like this:

```java
DataType[] arrayVariable;
```

The DataType can be any class or native type.  If you want the length of the array (since these are dynamic), you used:

```java
arrayVariable.length
```

**Singleton Pattern**

When we talked about static functions and data during class, we noted that static is usually just used for the main function (see discussion of Static below).  The Singleton Design Pattern is another user of the static.  We will talk about this later in the semester, but as an overview, a Singleton Class is one for which one and only one object can be created.  Here is a link for more information: https://sourcemaking.com/design_patterns/singleton

**Setters**

Frequently we will have the need to set member data in an class using constructors or setter functions.  When we do this, we may need to use "this" (just like C++, in python its like "self"):

```java
public Asteroid(Point point) {
    this.point = point
}
```

If we use different names in the parameter, then we can avoid using "this".

**Scanner**

The Scanner class is used to read from standard input (like "input" in Python or "cin" in C++).  Scanner is nice because it has multiple functions that combine all the capabilities we had in C++ (cin, getline, string versions) into one class.  To use it, you need to create a Scanner object first:

```java
Scanner scanner = new Scanner(System.in);
word = scanner.next(); // Read one word
line = scanner.nextLine(); // Read one line
```

If you will use this in multiple functions in your class, then you can make it private member data. 
More information: https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/Scanner.html

**While Loops**

Asking the user to re-enter the password was not necessary in the assignment, however it can be an exercise to complete.  Java has both a while loop and a do while loop.  A do while loop should be used when you need to do something at least once.  If the password example, we wanted the user to provide a password at least once.  If there was a problem with it (too short or missing a number), then we would continue the loop.

```java
User user;
boolean validPassword = false;
do {
    System.out.print("Enter password: ");
    password = scanner.nextLine();
    user = new User(password);
    try {
        NSALoginController.hashUserPassword(user);
        validPassword = true;
    } catch (WeakPasswordException e) {
        System.out.println("Weak Password Error: " + e.getMessage());
    } catch (Exception e) {
        System.out.println("General Exception: " + e.toString());
    }
} while (!validPassword);
```

Notice an interesting side effect.  If we used a while loop instead, its possible that user never gets set.  However, when you use a do-while loop, its guaranteed that user will get set.

**Overrides**

In IntellJ, using the shortcut Ctrl-O will prompt you to select functions you can override from inherited classes.  The list will include any overridable functions including:
* Public or Protected Functions (unless they are marked final)
* Functions in interfaces

**Final**

The keyword "final" is used for these purposes:
* If used on member data in a class, then the data cannot be changed after its initialized.  This is like const in C++.
* If used on member function in a class, then the function can not be overriden.
* If used on a class, then another class cannot inherit it (in other words, the class cannot be derived any further).

**Static**

Static member data in a class will exist whether an object is created or not.  All objects of the same class will share the static member data.  Static member functions in a class can be executed whether an object exists or not.  Main is a great example of a static member function.  The main function is called before an object of the class is created.  Actually, the main function usually create the first object.  

Static member functions cannot use member data unless the member data is static.

In general, you should avoid using statics except for main and for the singleton design pattern that will learn in the future.

**Creating a Project in IntelliJ**

When you create a project in IntelliJ, it can be easier not to select a template.  This will create a complete empty project.  Do the following in the empty project:

1. Right click on the src folder and create a package (your last name is good)
2. Right click on the package and create a java file (class, interface, or enum)

It would be useful to go and look at the files that were created after your project is setup.  Knowing where your project files is very important for everyone to learn to do.

**Resources**

Websites:
* JDK 12: https://docs.oracle.com/en/java/javase/12/docs/api/index.html
* Java 8 (Old but nicer format): https://docs.oracle.com/javase/8/docs/api/

Free Safari Books @ BYU-I Library:
* Head First Java: https://learning.oreilly.com/library/view/head-first-java/0596009208/
* Java in a Nutshell: https://learning.oreilly.com/library/view/java-in-a/9781492037248/
* Head First Android: https://learning.oreilly.com/library/view/head-first-android/9781491974049/

**Interfaces**

Interfaces in Java are a form of class that can be inherited by other classes and interfaces.  In Java, you can only inherit (extends) one class but you can inherit (implements) multiple interfaces.

An interface contains function declarations only (no implementation).  This is just like pure virtual functions in C++.  Here is an example of an interface in Java and in C++:

```java
public interface Messenger {
    public int sendMessage(String message);
    public int receiveMessage(String message);
}

class Messenger
{
    public virtual int sendMessage(String message) = 0;
    public virtual int receiveMessage(String message) = 0;
}
```

Use interfaces in the following cases:
1) You need to do multiple inheritance
2) You have a common interface that needs to be implemented by un-related classes (they don't have the same base class).  This is the most common reason for creating an interface.

If you have dervied classes from a common base class but only some of those derived classes share a common interface.  The use of the "instanceof" operator can help you determine if an object of the base class implemented those interfaces.

**Inheritance and Super**

When a class inherits another class using extends, the constructor in the derived class must explicitly call the constructor in the base class.  This is done with super().  The parameters you put into super are passed into the base class constructor.

```java
public class Parent {
    public Parent(int param1, String param2) {
    }
}

public class Child extends Parent {
    public Child() {
        super(32, "dog");
    }
}
```

**Inheritance Scenarios**

Here are some specific scenarios and their impacts:
1. If Class A extends Class B and Class B extends Class C, then Class A has inherited all the characteristics of both classes B and C.
2. If Class A extends Class B and Class B implements Interface C, then class A does not implement Interface C.  Interface C is only implemented by Class B.  Class A inherits all the implemented functions in Class B.
3. An Interface B can extend (not implement) multiple Interfaces.  If Interface B extends Interface C, D, and E and Class A implements B, then Class A will need to implement all functions in B, C, D, and E.

Java does not allow you to extend more than one class.  In C++ you can extend multiple classes.  One reason Java avoids this is because of the so-called "diamond" problem.  If Class A extends Class B and Class C, this could bring in characteristics from two base classes.  However, what if both of these classes B and C extended Class D.  The potential exists that Classes B and C would provide their own overriden implementations for a function in class D.  Technically, Class A now inherits the characteristics of B, C, and D.  However, for that one function that was overriden, there is now confusion (and a compiler error) about which one to use in Class A.  This website offers an example: https://medium.freecodecamp.org/multiple-inheritance-in-c-and-the-diamond-problem-7c12a9ddbbec

**Packages**

In Java, all code is usually organized into Packages (which is a fancy name for "directory" or "folder").  You could write code without putting it into a package, but this it not ideal especially in an IDE like IntelliJ.  Packages are usually named based on author, company, and/or project.  Some will use their website name (e.g. com.google.pakagename).  When you write larger software, it can be covenant to organized the code into separate sub-packages.  This gives you the benefit of developing software in different teams and creating reuse opportunities.  Each sub-package would be in its own sub-directory.

**Constructors**

In Java (as with other programming languages), a constructor is used to initialize an object.  If you use "new" to create a new object in Java, the constructor will be called.  You can define more than one constructor if you want different ways of initializing the object (usually based on data passed in from the user when they called "new").  The goal of every constructor is to initialize all the member data.

```java
public class User {
    private String name;
    private String password;
    
    public User() {
        name = "";
        password = "";
    }
    
    public User(String name) {
        this.name = name; // "this" means that we are referring to the member data
        password = "";
    }
    
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
    
    public static void main(String[] args) {
        // Three users each created using a different constructor
        User user1 = new User();
        User user2 = new User("Bob");
        User user3 = new User("Sue","123456!");
    }
}
```

**Working with Multiple Files**

In Java, every class is a separate file.  If I put several public classes (files) into a single package, then Java will be able to access them directly without being told where to look.  If the files are in different packages (which is the case with all of the Java libraries that we use), then we need an import statement:

```java
import packagename.classname; // This is the format

import java.util.Scanner; // This is an example
```

IntelliJ (like other IDE's) will provide an auto-complete feature which contains all the variables, functions, and classes that you currently have access to without your class or function.