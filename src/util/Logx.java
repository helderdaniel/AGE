package util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Logs to a PrintStream
 * @author hdaniel@ualg.pt
 * @version 202502160244
 */
public class Logx {

    static protected PrintStream out = System.err;
    static protected PrintStream echo = System.err;
    static protected boolean echoEnabled = false;

    static public void setStream(PrintStream ps) { out = ps; }
    static public void setStream(String fpath) throws IOException {
        out = new PrintStream(new FileOutputStream(fpath, true));
    }

    static public void enableEcho()  { echoEnabled = true; }
    static public void disableEcho() { echoEnabled = false; }

    static public void println(String msg) {
        if (echoEnabled) echo.println(msg);
        out.println(msg);
    }

    static public void printf(String format, Object... args) {
        if (echoEnabled) echo.printf(format, args);
        out.printf(format, args);
    }

}
