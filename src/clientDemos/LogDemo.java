package clientDemos;

import util.Log;

import java.io.IOException;

/**
 * @author hdaniel@ualg.pt
 * @version 202502160300
 */
public class LogDemo {

    static String logFileName = "log.txt";

    public static void main(String[] args) throws IOException {

        Log.println("TestLog: " + 90);
        Log.printf("Log file: %s\n", System.getProperty("user.dir") + "/log.txt");

        Log.setStream(logFileName);
        Log.println("TestLog: " + 90);
        Log.printf("Log file: %s\n", System.getProperty("user.dir") + "/log.txt");
    }
}
