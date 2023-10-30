package no.gruppe15.remote;

import no.gruppe15.remote.gui.RemoteApp;
import java.io.IOException;

/**
 * This class represents a starting point for the Remote application.
 *
 * @author Matti Kjellstadli, Adrian Johansen, Håkon Karlsen, Di Xie
 * @version 30.10.2023
 */

public class RemoteMain {

    public static void main(String[] args) throws IOException {
        RemoteLogic logic = new RemoteLogic();
        if (logic.start()) {
            RemoteApp.startApp();
            logic.stop();
        } else {
            System.err.println("Could not connect to a smart TV!");
        }


    }
}
