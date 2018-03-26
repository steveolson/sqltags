/*
 * ViaKlixException.java
 *
 * Created on May 2, 2002, 6:34 PM
 */

package Templates.Classes;

/**
 *
 * @author  solson
 */
public class Exception extends java.lang.Exception {

    /**
     * Creates a new instance of <code>ViaKlixException</code> without detail message.
     */
    public Exception () {
    }


    /**
     * Constructs an instance of <code>ViaKlixException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public Exception (String msg) {
        super(msg);
    }
}


