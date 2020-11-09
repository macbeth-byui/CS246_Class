package macbeth;

/**
 * This class will display messages received from the DataReader.
 */

//public class Client implements DataListener {
//
//    private String name;
//
//    public Client(String name, DataReader reader) {
//        this.name = name;
//        reader.addListener(this);
//    }
//
//    /**
//     * This function is called by the DataReader when a message
//     * needs to be sent to us.
//     * @param data
//     */
//    @Override
//    public void dataReceived(String data) {
//        System.out.println("Received on "+name+": "+data);
//    }
//}

/*
In the follow option, instead of implementing DataListener, a new
DataListener object is created when addListener is called.
 */

public class Client implements DataListener{
    @Override
    public void dataReceived(String data) {

    }

    private String name;

    public Client(String name, DataReader reader) {
        this.name = name;
        reader.addListener(new DataListener() {
            @Override
            public void dataReceived(String data) {
                System.out.println("Received on "+name+": "+data);
            }
        });
    }

}
