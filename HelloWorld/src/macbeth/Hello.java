package macbeth;

public class Hello {

    public void sayHello(String name) {
        System.out.println("Hello, " + name);
    }

    public static void main(String [] args) {
        Hello hello = new Hello();
        hello.sayHello("Bob");
    }
}
