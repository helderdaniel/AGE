package util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Logs to a PrintStream
 * @author hdaniel@ualg.pt
 * @version 202502160244
 */
public class Log {

    static protected PrintStream out = System.err;

    static public void setStream(String fpath) throws IOException {
        out = new PrintStream(new FileOutputStream(fpath, true));
    }

    static public void println(String msg) { out.println(msg); }
    static public void printf(String format, Object... args) { out.printf(format, args); }

}
