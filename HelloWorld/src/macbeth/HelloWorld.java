package macbeth;

public class HelloWorld {

    private String name;

    public HelloWorld(String name) {
        this.name = name;
    }

    public void sayHello() {
        System.out.println("Hello "+name);
    }

    public static void main(String [] args) {
        //System.out.println("Hello World");
        HelloWorld hi = new HelloWorld("Bob");
        hi.sayHello();

        new HelloWorld("Sue").sayHello();
    }
}
