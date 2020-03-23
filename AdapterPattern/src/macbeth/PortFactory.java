package macbeth;

public class PortFactory {

   public static AvionicPort createPort(String configuration) {
       AvionicPort port = null;
       String[] fields = configuration.split(",");
       switch(fields[0]) {
           case "AFDX" :
               port = new AFDXPort(fields[1],Integer.parseInt(fields[2]),Integer.parseInt(fields[3]),
                       Integer.parseInt(fields[4]), fields[5], Integer.parseInt(fields[6]));
               break;
           case "DISCRETE" :
               port = new DiscretePort(fields[1], Integer.parseInt(fields[2].replace("V","")),
                       Integer.parseInt(fields[3].replace("V","")), fields[4]);
               break;
           case "A429" :
               port = new A429Port(fields[1], Integer.decode(fields[2]), Integer.parseInt(fields[3]),
                       fields[4]);
               break;
           default :
               System.out.println("Invalid port ID: "+configuration);

       }
       return port;
   }
}
