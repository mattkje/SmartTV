package no.gruppe15;

/**
 * Run the TCP Server.
 */

public class SmartTV {
    public static void main(String[] args) {
        TVLogic logic = new TVLogic(13);
        TVServer server = new TVServer(logic);
        server.startServer();
    }
}
