/*
 * ViaKlixException.java
 *
 * Created on May 2, 2002, 6:34 PM
 */

package com.aitworks.creditcard.viakliks;

/**
 *
 * @author  solson
 */
public class ViaKlixBeanException extends java.lang.Exception {

    /**
     * Creates a new instance of <code>ViaKlixException</code> without detail message.
     */
    public ViaKlixBeanException() {
    }


    /**
     * Constructs an instance of <code>ViaKlixException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public ViaKlixBeanException(String msg) {
        super(msg);
    }
}


