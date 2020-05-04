**The instanceof Operator**

The instanceof operator is a handy tool in Java to help you identify if an object is of a certain type.  This can be used with interfaces and other base classes.  Most helpful is when you use this to see if an object has inherited from a base class or an interface.  With interfaces, it is common that classes that implement them are not related in any other way.  Other classes that receive objects can test whether the object has implemented an interface.  If you have the following code:

```java
    if (myObject instanceof Printable)
```

This will return True if myObject has inherited Printable.  It will return False either if it is not or if myObject was null.

With all the above said, one should not always feel that they need to use instaneof in their code.  If I have a variable whose type is an interface or base class, by default those functions in that interface or base class are the only ones I should usually be using.  However, depending on our architecture, if we have multiple different type of interface that children classes may have implemented (like we did in the game of life), instanceof can be very helpful.

**Null in Java**

In your earlier training, you were taught to be very careful with null.  In Java, all variables (except those of native type like int or boolean) are really storing the address (aka pointer) to the object that is actually stored on the heap.  If the object has been created (using the new operator), then you will save the address into the variable:

```java
    Animal a = new Animal();
```

If you don't have an object yet, you can just do this:

```java
    Animal a = null;  // If you forget to set it equal to null, Java will set it equal to null. 
```

Just like in other languages, before you call a function on an object, you need to be sure the object really exists.  If your code is designed such that you are guaranteed there is an object, then you can just call the function.  However, if its possible that the object may not exist, then you should check for null first:

```java
    if (myAnimal != null) {
        myAnimal.feed();
    }
```    

In the game, we frequently had to check for null.  When a target (a creature) was passed in for attack or sensing, its possible there was no creature actually to attack or sense.  In these cases, null was passed in.

**The new Operator**

In Java, all objects are dynamically created (no other option).  Therefore, the syntax has us use the "new" operator whenever we want to create an object:

```java
    ArrayList<String> names = new ArrayList<String>();
```

If we don't do the new operator, then no new object is created.  In this example, names2 is not a copy of the list but rather its an "alias" to the same list already created:

```java
    ArrayList<String> names = new ArrayList<String>();
    ArrayList<String> names2 = names;
```

If you needed to make a copy, you can use the clone function.  Every object in Java implements this function (https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/lang/Object.html).

```java
    ArrayList<String> names = new ArrayList<String>();
    ArrayList<String> names2 = names.clone();
```

**Type Casting**

In the assignment, you saw code that looked like this:

```java
    if (c instanceof Spawner) {
        Spawner spawner = (Spawner) c;
        Creature newCreature = spawner.spawnNewCreature();
```

Notice that in the for loop, the variable "c" is of type Creature.  That means that only the functions in the Creature class can be called using "c".  Using instanceof we determined that "c" is also an Spawner.  However, to call the spawnNewCreature function (which is part of the Spawner interface), we need to have a variable of type Spawner.  The type cast of "c" to type "Spawner" will give us a variable that now has access to functions in Spawner.  Remember, you can only call functions based on the variable type.

One other comment.  Let's pretend that we had a variable "Wolf w".  In this case, since Wolf is a derived class, we would be able to call all functions in Wolf and in Creature (at least the ones we have inherited ... public, protected, and package scope items).

**Passing Variables**

When you pass a variable into a function, one of two things happens:
1. If the variable is a native type (booealn, byte, char, short, int, long, float, double) then it is pass by value.  If the function changes the variable, the calling function will not know about it.
2. If the variable is an object, then the address of the object is passed by value.  If the function calls a function on the object (like the add function if it was an ArrayList), then the object will get updated and the calling function will know about it.  If you received an object from another function, you should not do another new and store the result into that same variable.  That new object will not be seen by the calling function.

Note that sharing information between functions (or even between functions of different classes) is not inheritance.  Inheritance does not pass information to a class but rather gives characteristics (e.g. member data and member functions) from a base class or interface to the derived classes.

**Getters**

If you have private member data in class and you want someone outside of the class to access it, then you can provide a getter function.  In Intell-J you can autogenerate these functions by typing Alt-Insert.

```java
    private int speed;
    private Animal animal;
    
    public int getSpeed() { 
        return speed;
    }
    
    public Animal getAnimal() {
        return animal;
    }
```

Be careful with getters in Java.  In C++, a copy was always made (unless you returned by reference).  However in Java, if you return an object (not native types ... so this doesn't apply to the getSpeed function above) then the calling function will be able to call functions on that object to change it.  If you wanted to send back a copy, you could use the clone function to create a copy.

**Problem Solving and Design**

You will notice that the assignments have two parts ... the first is to understand the architecture of code you have received and then second to solve a problem within that framework.  This is similar to what you will do with your app.  You won't have any starting code, but you will have the Android framework and you will need to work within that framework.  You will likely use other libraries provided to you and need to see how to interface with them. 

When you build an architecture, you will frequently see a separation between user interface, the logic, and the data (we will see this when we talk about the MVC (Model-View-Controller) Design Pattern.  

When you organize your code, you should first start with a Design.  Pull out the notebook and draw out a quick UML diagram.  Walkthrough the design pretending you are the code.  Identify what type of behavior you will have in each of those class diagrams.  When you can do this, then the code becomes an output of the process instead of an input.  When code is in input, then you find yourself writing, rewriting, rewriting, starting over, over, and over again.  When you design first, the code is an output of your preparation.

**Spawn Creatures**

The Spawner was one of the most complicated parts of the assignment.  Instead of a brute force approach, start with the Design.  When we look at the UML diagram, we can see that the CreatureHandler will call spawnNewCreature function and expect to return a Creature back.  You need to start with this and add to the functionality one step at a time.  If you start by returning a creature every time (which is more than what you should do) you would quickly get the ConcurrentModificationException.  This is something you should lookup in JavaDoc or in other support sites (also look at notes below for ArrayLists and Iterators).  Once this is working, you may note that you don't see the new creatures.  This is likely because when you created new creature you used the same location memory instead a copy of the location from the parent.  

```java
// Wrong Way: 2 Varaibles pointing to the same memory
Point childLocation = location; 

// Right Way:  2 Different Point Objects with the same values
// You could then change the x for the child to be one step away
Point childLocation = new Point(location.x, location.y);  

```

You should then consider when you should creating a new Creature.  You will need something like a boolean in the class to keep track of whether a baby can be born. When you do this, you realize (either before running or after) that you need to check for Null in the CreatureHandler.  In Java, a check for Null should be something you think about before you even test the code.  

The Spawner Interface could also work for other Creatures.  Imagine if Plant implemented the Spawner interface.  A random number generator in the spawnNewCreature function for the Plant could determine if a plant should be planted on an empty square next to the Plant (think weeds).

Another common question is why does the Wolf class need a function that returns a Creature and not a Wolf.  Indeed, the spawnNewCreature function does return a Wolf, but the Spawner interface is more general and allows the implementing class to return any type of Creature.  An object of the Creature class can be returned or used in any class that needs it (not just the Creature class).

**ArrayLists and Iterators**

An ArrayList in Java is a dynamic array just like a Vector in C++ or a list in Python.  An array in Java is fixed in size whereas the ArrayList can grow.  Instead of a push_back function, we use an add function in Java.  To see the full list of functions for an ArrayList, use Javadoc:

https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/ArrayList.html

Just like C++ (and other languages), when you traverse a data structure like an array list, you need to avoid adding to the list while you are traversing through it.  The iterator object is keeping track of where in memory you are currently at in your list.  If the list has to expand because it is full, the memory will change but the iterator will not get updated.  Java will raise an exception (ConcurrentModificationException) if it detects this condition.

A good approach is to save the new items that you want to add to the list into a separate ArrayList.  When you are done traversing the list, use the addAll function to add all objects from your temporary ArrayList to the master ArrayList.

There is another way (a little more complicated but similar to C++ iterators).  Instead of doing the for loop on _creatures in the updateCreatures functions, we could use a ListIterator object with a while loop.  Every List object (including ArrayList), has a function called listIterator() which returns this iterator.  To setup the loop you would do:

```java
    ListIterator<Creature> iterator = _creatures.listIterator();
    while (iterator.hasNext()) {  // Keep going until there are no more in the list
        Creature c = iterator.next()
```        

After this you would do the normal code to update each creature.  The ListIterator has another benefit.  When it is time to add something to the list, the ListIterator has an add function that will both add the item to the list and also update the iterator (something that we could not do with just the for loop):

```java
     iterator.add(newCreatureSpawned);
```

Generically speaking about iterators, you can iterate through any class that implements the Iterable class.  This includes all the class that inherit from Collection and Map interfaces (includes ArrayList, TreeMap, HashSet, etc).  The format is as follows:

```java
    for (Object entry : list) {
        System.out.println(entry.toString());
    }
```    

We read this as "Loop through "list" and do something with each "entry""

Related to these subjects, the World.java can experience a ConcurrentModificationException if you try to resize the window while the game is playing.  Notice the for loop in the paint function.  Try putting a try block around all this code and catch this exception.  You don't need to do anything because we are confident that the very next frame everything will draw properly again.

**Threading**

In some of the exceptions you may have seen the phrase "Thread".  For the graphics to work, they are drawn within a separate thread of execution.  Technically, the main function has already finished while the game is being played.  If you added the following test code to your main function, you would notice that you can type information into the console while the game is playing.   

```java
public static void main(String[] args) {
   // It all starts here.
   new Game().run();
   while (true) {
      Scanner scanner = new Scanner(System.in);
      if (scanner.next().equals("c")) {
         System.out.println("c pressed");
      }
   }
}
```

 **Graphics in Java**

Java has built-in graphics classes.  The most common one is the JFrame class.  Here is a good tutorial online: https://www.javatpoint.com/java-swing  

You will notice that the World class implements Runnable which causes a Thread to start.  This Thread will update the creatures (_handler.updateCreatures()) and then will display the updated board (repaint() ... this is a function in the Canvas class that World extends).  

It turns out that using a JPanel in the World.java will improve performance (some computers don't react well with the Canvas approach):

```java
public class World extends JPanel implements Runnable  {
    
// Keep other functions the same

@Override
public void paintComponent(Graphics context) {
   super.paintComponent(context);

   try {
      // Java's enhanced for loop (AKA for-each loop).
      // See: http://stackoverflow.com/a/11685345/28106
      for (Creature c : _handler.getCreatures()) {
          
          // Keep this code the same
```

Java also can listen for key presses in the graphics.  Look at the KeyListener's and KeyAdapter's.

To draw different 2D shapes (3D shapes would require a 3DCanvas to support) in the Game of Life, you need to do two things:

1. Update the Shape Enumeration to define something new like a Hexagon:

```java
public enum Shape {
	Circle, Square, Hexagon;
}
```

2. Implement code to draw the Hexagon in the World.java (you can learn how to do this by looking in the JavaDoc for the fillPolygon function).  Some graph paper can help to do this (the lower left hand corner of the grid is the x,y coordinate):

```java
switch (c.getShape()) {
    case Circle:
        context.fillOval(x, y, _cell.width, _cell.height);
        break;
    case Square:
        context.fillRect(x, y, _cell.width, _cell.height);
        break;
    case Hexagon:
        int x_coords[] = {x, x+_cell.width/3, x+_cell.width*2/3, x+_cell.width, x+_cell.width*2/3, x+_cell.width/3};
        int y_coords[] = {y+_cell.height/2, y+_cell.height, y+_cell.height, y+_cell.height/2, y, y};
        context.fillPolygon(x_coords, y_coords, 6);
        break;
    default:
        break;
}
```

When we work in Android, we will have do some graphics depending on your individual app's.  Android Studio has a tool that will let you design the screen graphically instead of writing code for each piece.

**Garbage Collection**

In Java you create new objects with the "new" keyword and you pass references of objects around frequently.  Passing by reference is okay since object creation is always done on the heap (instead of the stack ... which would make passing by reference dangerous since the stack is cleared when the function exits).  However, you don't have to free or delete the memory when it is no longer used.  Java keeps track of whether there is a reference used in the code for any object that has been created.  For example, if you put an object into a list, the reference to that object is stored in the list.  Java see's that the reference exists, and so it allows the object to remain in existance.  If the object is deleted form the list, then the reference is gone.  If Java see's that there are no references to data on the heap, then it will "collect the garbage" or in other words, delete the object from the heap.  Deleting occurs automatically but not immediately.

**Final Keyword**

The "final" keyword is used to specify that a class can be inherited.  In other words, you cannot modify the class by inheriting it.  The class is "final".

**World Grid Dimensions**

The code is written to build a customized grid based on the number of creatures you add to the world.  There are two things you can do:

1. Change the dimensions of the window in the World class constructor.  This will not change the number of cells, but it will make the cells bigger.
2. Change the number of creatures in the Game class.  This will change the number of cells but keep the same window dimension.

**Creature Handler**

The creature handler in the prove assignment makes many assumptions and has some interesting side effects.  First thing to notice is that it loops through all the creature objects to update them.  After the loop is done, the screen will get refreshed with the results.  When the creatures are updated, it follows this process in order:

1. Instruct Aware creatures to sense neighbors 
2. Instruct Moveable creatures to move
3. Instruct Aggressor creatures to attack
4. Instruct Spawner creatures to spawn

So, if you are Aware, then you will be able to move into place to attack the creature before the loop goes to the next creature.

Another thing to watch out for in this loop is to recognize that the list of creatures is somewhat ordered.  It is in the same order as the Game.cpp added them.  This means that is a priority: plants, animals, zombies, and then wolves.  Unless you decide to change that, the animals will be able to move before the wolf has a chance to sense something.  The graphics will not show this because the graphics do not update until the Creature Handler completes the loop.

**Moving Wolves**

Unlike the animal, the wolf moves in a predictable direction.  While the animal does random generation to determine where to move each frame, the wolf needs to remember which direction it is currently going (at least until sense neighbors changes that direction).  This means that the Wolf class needs member data to keep track of the current direction.

**Sense Neighbors**

The senseNeighbors function receives 4 Creature objects (up, down, left and right).  This is similar to the attack function in that you receive 1 Creature as  a target.  However, unlike attack which will likely change the health of the target creature, senseNeighbors will not change the member data in the 4 Creatures but use that creature information to determine if a change of direction is needed.

If there is no creature, the parameter will be null.  If the creature is not null (it exists) and if the creature is an Animal, then a wolf wants to change direction to go after that Animal.  

```java
    if (above instanceof Animal) {
        direction = Direction.Up; // I created an enumeration for Direction
    } 
    else if (right instanceof Animal) {
        direction = Direction.Right;
    }
    .... continue for below and left
```

If you wanted, you could also make this more accurate by checking the creature in the direction the wolf was already moving first.  This adds a bit more complexity to the if statements.  The move function should then move in the direction that senseNeighbors has selected.

**Enumeration**

An enumeration is a simple class that has neither data nor functions.  It only has a list of names which are used to represent a list of values.  Instead of assigning numbers to constant variables, you can create an enumeration and refer to the values in the enumeration throughout your code.

```java
    public enum Seasons {
        Spring, Fall, Summer, Winter;
    }
```

In your code, you can create variables of this enumerated type:

```java
    Seasons theSeason = Seasons.Winter;
    
    if (theSeason == Seasons.Winter) {
        ...
    }
```

Enumerations are great when you have a list of choices or possibilities that you need to select between in your software.  You could use constant (final) integers or strings to represent these choices, but the Enumeration allows the Java compiler to check to make sure you are using the right ones.  In the example above, if I would have selected Seasons.NeverEndingIdahoWinter, there would be a compiler error because its not defined in the Seasons enum (even though the winters never seem to end in Idaho).

**Java Libraries**

One of the great strengths of Java is large collection of libraries that are included with the JDK as well as libraries which are written by others that can easily be integrated into you application.  Using the OOP relationships of inheritance and composition, the library can be used and extended to meet any needs.  

**Javadoc**

The Javadoc website is an essential reference for a Java programmer.  It might be intimidating at first, but as you practice using it you will quickly discover its benefits.  It contains overview (and sometimes sample code), constructors, functions, and information related to inheritance.

The Javadoc website is auto-generated from comments put in the Java library code.  You know that comments are important (and mandatory) when you code.  When you code in Java, you should take advantage of the javadoc format for your comments.  When you write a comment block before each class and each function, use this format:

```java
    /**
     * Your Comments Here
     */
```

Intelli-J (and many other IDE's) will auto format these for you and include additional detail about function parameters.  Here a good summary link: https://en.wikipedia.org/wiki/Javadoc 

**Testing Your Software**

Using TestBed is earlier classes is both a blessing and a curse.  Its a blessing because you know exactly whether your software is working or not.  Its a curse though because you have to get the output perfect and perhaps you are not as certain what the software was supposed to do the first place.

The best way to understand your software requirements is to write down some test cases.  For example, in the Game of Life, you need to ensure that a baby wolf is born after it eats an animal.  This can be hard to see sometimes.  However, you can simplify the test by changing the World class so that the game only has 3 Animals and 2 Wolves.  Additionally, debug statements using System.out.println can be used to tell you that a baby was born.  

**Additional Practice**

These free online books through the BYU-I library are good resources for practice:

Free Safari Books @ BYU-I Library:

- Head First Java: https://learning.oreilly.com/library/view/head-first-java/0596009208/
- Java in a Nutshell: https://learning.oreilly.com/library/view/java-in-a/9781492037248/

You should also experiment more with the assignment and try adding more functionality to each of them.

**Interfaces**

Interfaces and Abstract Classes are both components of doing inheritance in Java (and also all other object oriented programming languages).  When you do inheritance, you can inherit any of the following:
* Class (so long as that class was not declared as final) - This will cause you to inherit all the characteristics of the that class (also called the base class).  You will be able to call or directly access anything in the base class that was public, protected, or package scope.
* Abstract Class - This is the same as the one above, but an abstract class is one that likely has abstract functions (meaning the function has no implementation).  If you inherit an abstract class, the derived class must implement all of those functions. (Type Ctrl-O in IntelliJ to quickly do this).  The functions already implemented in the Abstract class do not need to be re-implemented in the derived classes (unless you wanted to override them to do something different).
* Interface - An interface in any language is a class that only abstract functions in them. This is why an interface only has prototypes of functions.  In C++, you can make an interface by creating a class that only has pure virtual functions in it.  In Python, you can make an interface by creating a class that only has abstract methods that have "pass" in the implementation.

General rules about inheritance in Java:
* You can extend only one Class (or Abstract Class)
* You can extend an unlimited number of Interfaces
* An interface can inherit another interface in Java by using "extends".  This is useful if you want to consolidate multiple interfaces that many classes implement into a single interface.

When you design your project, you want to think about member data and member functions. What is needed to model the object (the data) and what actions can be taken on the object (the functions).  When you look at a function, you need to determine what data will be used or modified.  Then when you look at all of your data and functions, you look for things in common to develop your inheritance strategy.

When you have commonality, you should put common data and functions into the base class so that it can inherited in the derived classes (also called sub-classes).  If you have common functions that are implemented differently in all the derived classes, then this would be abstract in the base class.  If you have common functions in some of your derived classes (or in un-related classes), you have the option of creating an interface.  The interface is used by the user who creates and/or uses the objects of the derived classes.  If there are no functions then an interface may not be needed.  However, you should challenge your design and look to see if the user of your objects has too much detail.  An interface can help to generalize the user implementation.

An interface should be created to support a planned architecture.  If you have (or anticipate) having multiple different types of classes that will have a common interface function, then you should create the interface.  If there is only one class that will support (or ever support) a particular function, that an interface would not be needed.

Interfaces also have to be written with care to ensure that the functions and especially their parameters and data types apply to all potential implementers.  

You should always consider drawing a UML diagram for your software prior to implementation.  

When we explore Design Patterns, you will see several other good uses of interfaces.

**Multiple Inheritance**

Other languages like C++ and Python also support multiple inheritance.  These other languages don't provide a separation of class and interface and therefore allow inheritance of more than one base class unlike Java.  In C++, an interface can still be created (still called a class) if you make a class that only has pure virtual functions.  If Python, an interface can still be created (still called a class) if you make a class that inherits ABC and has only @abstractmethod's that contain "pass" in each of them.  

The reason Java has rules about multiple inheritance is because they are trying to avoid the diamond problem.  Consider a class A that inherits classes B and C.  For some (strange) reason both class B inherits from D and class C inherits from D.  Too make it even stranger, suppose that the grandparent class D has an abstract function that both B and C implement differently.  So far so good for B and C, but what about A.  A has inherited different implementation of that abstract function from both B and C.  Which one should it use.  Java has prevented this potential problem by not allowing multiple base classes to be extended.

**Inheritance in Game of Life**

Notice that the Game of Life has both an abstract base class (Creature) and interfaces to describe the various behaviors that Creatures could have.  

None of the Derived Creatures inherited from another Derived Creature.  For example, the Wolf did not inherit from Animal.  One could make the case that a Wolf is a specialized Animal.  However, the implementation of the abstract functions and the interface functions are all very different.  Even though speaking from a Biology perspective, a Wolf IS-A Animal, it doesn't work out well in the code.  They are too different.

**Java Virtual Machine**

If you need to change any settings for the Java Virtual Machine to support your computer, you would make those in the Run/Debug Configuration in IntelliJ.  The VM Options line is where you would add your modifications.  For example, if I wanted to start with 64MB of heap for my program, I could put -Xmx64m.  You will likely not need to make any changes to this.