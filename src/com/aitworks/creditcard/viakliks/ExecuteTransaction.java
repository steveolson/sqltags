/*
 * ExecuteTransaction.java
 *
 * Created on May 2, 2002, 5:51 PM
 */

package com.aitworks.creditcard.viakliks;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StreamCorruptedException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import javax.servlet.jsp.JspWriter;
import javax.servlet.ServletRequest;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.http.HttpServlet;
import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.security.*;
/**
 *
 * @author  solson
 */
public class ExecuteTransaction extends TagSupport {

    private Hashtable Attributes = new Hashtable();
    
    public static final int APPROVED = 0;
    public static final int DECLINED = 1;
    public static final int FAILURE = 2;
    
    /** Creates a new instance of ExecuteTransaction */
    public ExecuteTransaction() {
    }

    public boolean isApproved() {
        return false;
    }
    
    public boolean isDeclined() {
        return false;
    }
    
    public boolean isFailure() {
        return false;
    }
    
    public void setAttribute(java.lang.String name, java.lang.String value) {
        Attributes.put(name,value);
    }
    
    public String getAttribute(java.lang.String name) {
        return (String) Attributes.get(name);
    }
    
    public int execute() {
    }
    
}
