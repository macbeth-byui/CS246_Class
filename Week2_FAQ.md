**Iterfaces**

Interfaces and Abstract Classes are both components of doing inheritance in Java (and also all other object oriented programming langauges).  When you do inheritance, you can inherit any of the following:
* Class (so long as that class was not declared as final) - This will cause you to inherit all the characteristics of the that class (also called the base class).  You will be able to call or directly access anything in the base class that was public, protected, or package scope.
* Abstract Class - This is the same as the one above, but an abstract class is one that likely has abstract functions (meaning the function has no implementation).  If you inherit an abstract class, the derived class must implement all of those functions. (Type Ctrl-O in Intelli-J to quickly do this).
* Interface - An interface in any language is a class that only abstract functions in them. This is why an interface only has prototypes of functions.  In C++, you can make an interface by creating a class that only has pure virtual functions in it.  In Python, you can make an interface by creating a class that only has abstract methods that have "pass" in the implementation.

General rules about inheritance in Java:
* You can extend only one Class (or Abstract Class)
* You can extend an unlimited number of Interfaces
* An interface can inherit another interface in Java by using "extends".  

When you design your project, you will likely create base classes (abstract or not) for super sets of your derived classes.  In other words, you will look at all of your different types of classes and group hierarchically so that each base class will be able to contain the common data and common implemented functions within it.  Then you will look at your UML structure (like a big upside tree) and look for common functions that some of the classes throughout your design need to implement ... you would create an interface for those classes to implement.

When we explore Design Patterns, you will see several other good uses of interfaces.

**The instanceof Operator**

The instanceof operator is a handy tool in Java to help you identify if an object is of a certain type.  Most helpful is when you use this to see if an object has inherited from a base class or an interface.  With interfaces, it is common that classes that implement them are not related in any other way.  Other classes that receive objects can test whether the object has implemented an interface.  If you have the following code:

    if (myObject instanceof Printable)

This will return True if myObject has inherited Printable.  It will return False either if it is not or if myObject was null.

**Null in Java**

In your earlier training, you were taught to be very careful with null.  In Java, all variables (except those of native type like int or boolean) are really storing the address (aka pointer) to the object that is actually stored on the heap.  If the object has been created (using the new operator), then you will save the address into the variable:

    Animal a = new Animal();

If you don't have an object yet, you can just do this:

    Animal a = null;  // If you forget to set it equal to null, Java will set it equal to null. 

Just like in other languages, before you call a function on an object, you need to be sure the object really exists.  If your code is designed such that you are guarenteed there is an object, then you can just call the function.  However, if its possible that the object may not exist, then you should check for null first:

    if (myAnimal != null) {
        myAnimal.feed();
    }

**The new Operator**

In Java, all objects are dynamically created (no other option).  Therefore, the syntax has us use the "new" operator whenever we want to create an object:

    ArrayList<String> names = new ArrayList<String>();

If we don't do the new operator, then no new object is created.  In this example, names2 is not a copy of the list but rather its an "alias" to the same list already created:

    ArrayList<String> names = new ArrayList<String>();
    ArrayList<String> names2 = names;

If you needed to make a copy, you can use the clone function.  Every object in Java implements this function (https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/lang/Object.html).

    ArrayList<String> names = new ArrayList<String>();
    ArrayList<String> names2 = names.clone();

**Type Casting**

In the assignment, you saw code that looked like this:

    if (c instanceof Spawner) {
        Spawner spawner = (Spawner) c;
        Creature newCreature = spawner.spawnNewCreature();

Notice that in the for loop, the variable "c" is of type Creature.  That means that only the functions in the Creature class can be called using "c".  Using instanceof we determined that "c" is also an Spawner.  However, to call the spawnNewCreature function (which is part of the Spawner interface), we need to have a variable of type Spawner.  The type cast of "c" to type "Spawner" will give us a variable that now has access to functions in Spawner.  Remember, you can only call functions based on the variable type.

One other comment.  Let's pretend that we had a variable "Wolf w".  In this case, since Wolf is a derived class, we would be able to call all functions in Wolf and in Creature (at least the ones we have inherited ... public, protected, and package scope items).

**Passing Variables**

When you pass a variable into a function, one of two things happens:
1. If the variable is a native type (booealn, byte, char, short, int, long, float, double) then it is pass by value.  If the function changes the variable, the calling function will not know about it.
2. If the variable is an object, then the address of the object is passed by value.  If the function calls a function on the object (like the add function if it was an ArrayList), then the object will get updated and the calling function will know about it.  If you received an object from another function, you should not do another new and store the result into that same variable.  That new object will not be seen by the calling function.

**Getters**

If you have private member data in class and you want someone outside of the class to access it, then you can provide a getter function.  In Intell-J you can autogenerate these functions by typing Alt-Insert.

    private int speed;
    private Animal animal;

    public int getSpeed() { 
        return speed;
    }

    public Animal getAnimal() {
        return animal;
    }

Be careful with getters in Java.  In C++, a copy was always made (unless you returned by reference).  However in Java, if you return an object (not native types ... so this doesn't apply to the getSpeed function above) then the calling function will be able to call functions on that object to change it.  If you wanted to send back a copy, you could use the clone function to create a copy.

**Problem Solving and Design**

You will notice that the assignments have two parts ... the first is to understand the architecture of code you have received and then second to solve a problem within that framework.  This is similair to what you will do with your app.  You won't have any starting code, but you will have the Android framework and you will need to work within that framework.  You will likely use other libraries provided to you and need to see how to interface with them. 

When you build an architecture, you will frequently see a separation between user interface, the logic, and the data (we will see this when we talk about the MVC (Model-View-Controller) Design Pattern.  

When you organize your code, you should first start with a Design.  Pull out the notebook and draw out a quick UML diagram.  Walkthrough the design pretending you are the code.  Identify what type of behavior you will have in each of those class diagrams.  When you can do this, then the code becomes an output of the process instead of an input.  When code is in input, then you find yourself writing, rewriting, rewriting, starting over, over, and over again.  When you design first, the code is an output of your preparation.

**ArrayLists and Iterators**

An ArrayList in Java is like a Vector in C++.  Instead of a push_back function, we use an add function in Java.  To see the full list of functions for an ArrayList, use Javadoc:

https://docs.oracle.com/javase/8/docs/api/

Just like C++ (and other languages), when you traverse a data structure like an array list, you need to avoid adding to the list while you are traversing through it.  The iterator object is keeping track of where in memory you are currently at in your list.  If the list has to expand because it is full, the memory will change but the iterator will not get updated.  Java will raise an exception if it detects this condition.

A good approach is to save the new items that you want to add to the list into a separate ArrayList.  When you are done traversing the list, use the addAll function to add all objects from your temporary ArrayList to the master ArrayList.

There is another way.  Instead of doing the for loop on _creatures in the updateCreatures functions, we could use a ListIterator object with a while loop.  Every List object (including ArrayList), has a function called listIterator() which returns this iterator.  To setup the loop you would do:

    ListIterator<Creature> iterator = _creatures.listIterator();
    while (iterator.hasNext()) {  // Keep going until there are no more in the list
        Creature c = iterator.next()

After this you would do the normal code to update each creature.  The ListIterator has another benefit.  When it is time to add something to the list, the ListIterator has an add function that will both add the item to the list and also update the iterator (something that we could not do with just the for loop):

     iterator.add(newCreatureSpawned);

Generically speaking about iterators, you can iterate through any class that implements the Iterable class.  This includes all the class that inherit from Collection and Map interfaces (includes ArrayList, TreeMap, HashSet, etc).  The format is as follows:

    for (Object entry : list) {
        System.out.println(entry.toString());
    }

We read this as "Loop through "list" and do something with each "entry""

**Graphics in Java**

Java has built-in graphics classes.  The most common one is the JFrame class.  The information displayed on the screen is constantly being refreshed.  Here is a good tutorial online: https://www.javatpoint.com/java-swing

When we work in Android, we will have do some graphics depending on your individual app's.  Android Studio has a tool that will let you design the screen graphically instead of writing code for each piece.

**Garbage Collection**

In Java you create new objects with the "new" keyword and you pass references of objects around frequently.  Passing by reference is okay since object creation is always done on the heap (instead of the stack ... which would make passing by reference dangerous since the stack is cleared when the function exits).  However, you don't have to free or delete the memory when it is no longer used.  Java keeps track of whether there is a reference used in the code for any object that has been created.  For example, if you put an object into a list, the reference to that object is stored in the list.  Java see's that the reference exists, and so it allows the object to remain in existance.  If the object is deleted form the list, then the reference is gone.  If Java see's that there are no references to data on the heap, then it will "collect the garbage" or in other words, delete the object from the heap.  Deleting occurs automatically but not immediately.

**Final Keyword**

The "final" keyword is used to specify that a class can be inherited.  In other words, you cannot modify the class by inheriting it.  The class is "final".

**SenseNeighbors**

The senseNeighbors function receives 4 Creature objects (up, down, left and right).  If there is no creature, the parameter will be null.  If the creature is not null (it exists) and if the creature is an Animal, then a wolf wants to change direction to go after that Animal.  

    if (above instanceof Animal) {
        direction = Direction.Up; // I created an enumeration for Direction
    } 
    else if (right instanceof Animal) {
        direction = Direction.Right;
    }
    .... continue for below and left

If you wanted, you could also make this more accurate by checking the creature in the direction the wolf was already moving first.  This adds a bit more complexity to the if statements.  The move function should then move in the direction that senseNeighbors has selected.

**Spawing Wolves**

When you implement the spawnNewCreature function, you will need to determine if you can spawn the new creature.  You will need something like a boolean in the class to keep track of whether a baby can be born.  When you create the new creature (and move it over into a new square), then you should return that back to CreatureHandler to add it to the creature list.  See the conversation above about how to get it into the ArrayList.

**Enumeration**

An enumeration is a simple class that has neither data nor functions.  It only has a list of names which are used to represent a list of values.  Instead of assigning numbers to constant variables, you can create an enumeration and refer to the values in the enumeration throughout your code.

    public enum Seasons {
        Spring, Fall, Summer, Winter;
    }

In your code, you can create variables of this enumerated type:

    Seasons theSeason = Seasons.Winter;

    if (theSeason == Seasons.Winter) {
        ...
    }

**Java Libraries**

One of the great strengths of Java is large collection of libraries that are included with the JDK as well as libraries which are written by others that can easily be integrated into you application.  Using the OOP relationships of inheritance and composition, the library can be used and extended to meet any needs.  

**Javadoc**

You know that comments are important (and mandatory) when you code.  When you code in Java, you should take advantage of the javadoc format for your comments.  When you write a comment block before each class and each function, use this format:

    /**
     * Your Comments Here
     */

Intelli-J (and many other IDE's) will auto format these for you and include additional detail about function parameters.  Here a good summary link: https://en.wikipedia.org/wiki/Javadoc 
