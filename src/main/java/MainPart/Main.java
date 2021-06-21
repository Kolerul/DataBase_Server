package MainPart;

import java.io.IOException;

public class Main {
    public static void main(String[] args){
        try {
            Server server = new Server();
            do {
                server.doServerBuisness();
            } while (true);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}