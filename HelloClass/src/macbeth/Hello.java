package macbeth;

public class Hello {

    public void sayHello() {
        System.out.println("Hello Class!");
    }

    public static void main(String[] args) {
        Hello hello = null;
        hello = new Hello();
        hello.sayHello();

        /* Could also do this:
        Hello hello = new Hello();
        hello.sayHello();

        or if I don't need the object anymore ....

        new Hello().sayHello();
         */

    }

}
