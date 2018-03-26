/* $Id: Utilities.java,v 1.22 2002/07/29 18:25:57 jpoon Exp $
 * $Log: Utilities.java,v $
 * Revision 1.22  2002/07/29 18:25:57  jpoon
 * updated
 *
 * Revision 1.21  2002/07/26 00:52:16  jpoon
 * fixed auto merge
 *
 * Revision 1.20  2002/07/26 00:47:59  jpoon
 * file upload
 *
 * Revision 1.19  2002/07/24 21:28:02  hongxia
 * Fix the upload page.
 *
 * Revision 1.18  2002/06/27 19:18:58  jpoon
 * change error message
 *
 * Revision 1.17  2002/06/13 19:21:00  jpoon
 * updated
 *
 * Revision 1.16  2002/05/21 12:36:07  booker
 * final checkin of sqltags
 *
 * Revision 1.15  2002/05/16 18:02:21  booker
 * added method
 *
 * Revision 1.14  2002/05/16 10:23:18  booker
 * Added writeBytesToFile() and writeRequestHeaderToFile() methods
 *
 * Revision 1.13  2002/04/20 14:58:56  booker
 * fixed nvl method.
 *
 * Revision 1.12  2002/04/03 14:49:23  booker
 * Worked on adding Object to ColumnProperties.
 *
 * Revision 1.11  2002/03/19 19:14:01  booker
 * checking in sqltags. working on 2nd cut of ojp
 *
 * Revision 1.10  2002/03/15 22:26:20  solson
 * removed ImageIcon stuff; put in the About Class ...
 *
 * Revision 1.9  2002/03/15 14:33:31  solson
 * added License, ID, and Log
 *
 * ====================================================================
 *
 * Applied Information Technologies, Inc.
 * Steve A. Olson
 *
 * Copyright (c) 2002 Applied Information Technologies, Inc.
 * Copyright (c) 2002 Steve A. Olson
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by
 *    Applied Information Technologies, Inc. (http://www.ait-inc.com/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "Applied Information Technologies, Inc.", "AIT", "AITWorks",
 *    "SQLTags", and "<SQLTags:>" must not be used to endorse or promote
 *    products derived from this software without prior written permission.
 *    For written permission, please contact support@ait-inc.com.
 *
 * 5. Products derived from this software may not be called "SQLTags" or
 *    "<SQLTags:>" nor may "SQLTags" or "<SQLTags:>" appear in their
 *    names without prior written permission of the Applied Information
 *    Technologies, Inc..
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL APPLIED INFORMATION TECHNOLOGIES,
 * INC. OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of Applied Information Technologies, Inc.  For more
 * information on Applied Information Technologies, Inc., please see
 * <http://www.ait-inc.com/>.
 *
 */
package com.aitworks.sqltags.utilities;
import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.Cookie;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.util.Enumeration;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.JspWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletContext;
import javax.swing.ImageIcon;

/**
 * <code>Utilities</code>
 * <p>
 * This class is a collection of methods used to provide behavior to the
 * calling class.
 * </p>
 * @author  Booker Northington II
 * @version 1.0
 * @since   1.3
 * @param  <code>none</code>
 * @return <code>none</code>
 */
public class Utilities{
    private String             cr="\n";
    private int                debugLevel=0;
    private HttpServletRequest request;
    private Runtime            runtime=Runtime.getRuntime();
    private String             tab="   ";
    private StringBuffer       tagData=new StringBuffer();
    private String             xslFile="";
    
    /**
     * <code>Class Constructor</code>
     * <p>
     * This
     * </p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   1.3
     * @param  <code>none</code>
     * @return <code>none</code>
     */
    //-------------------------------------------------------------------------
    public Utilities(){
        //-------------------------------------------------------------------------
    }// Utilities() END
    
    /**
     * <code>CloseTag</code>
     * <p>
     * This
     * </p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   1.3
     * @param  <code>none</code>
     * @return <code>none</code>
     */
    //----------------------------------------------------------------------
    public void closeTag(String spacer, String tag){
        //----------------------------------------------------------------------
        tagData.append(spacer);
        tagData.append("</"+tag+">"+cr);
    }
    /**
     * <code>exists</code>
     * <p>
     * This method checks to see if a file already exists.
     * </p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   1.3
     * @param filename <code>String</code>the file we are looking for.
     * @return returnValue<code>boolean</code>true if the file exists.
     */
    //---------------------------------------------------------------------------
    public synchronized static boolean exists(String filename){
        //---------------------------------------------------------------------------
        boolean returnValue=false;
        try{
            File file=new File(filename);
            returnValue=file.exists();
        }
        catch(SecurityException exception){
        }
        
        return returnValue;
    }//exists() ENDS
    
    /**
     * <code>promptCommandLine</code>
     * <p>
     * This
     * </p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   1.3
     * @param  <code>none</code>
     * @return <code>none</code>
     */
    //----------------------------------------------------------------------
    public static String promptCommandLine(String question){
        //----------------------------------------------------------------------
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        String answer="";
        
        try{
            System.out.println(question);
            answer=br.readLine();
        }
        catch(IOException ioe){
            System.out.println("invalid input detected.");
        }
        
        return answer;
    }// promptCommandLine() ENDS
    
    /**
     * <code>getParameter</code>
     * <p>
     * This
     * </p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   1.3
     * @param  <code>none</code>
     * @return <code>none</code>
     */
    //----------------------------------------------------------------------
    //-------------------------------------------------------------------------
    public static String getParameter(HttpServletRequest request, String name){
        //-------------------------------------------------------------------------
        return nvl(request.getParameter(name), "");
    }// getParameterValues() ENDS
    
    /**
     * <code>getEnvironmentVariable</code>
     * <p>
     * This
     * </p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   1.3
     * @param  <code>none</code>
     * @return <code>none</code>
     */
    //----------------------------------------------------------------------
    public String getEnvironmentVariable(String key){
        //----------------------------------------------------------------------
        return System.getProperty(key);
    }//getEnvironmentVariable() ENDS
    
    
    /**
     * <code>getTokenHash</code>
     * <p>
     * This
     * </p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   1.3
     * @param  <code>none</code>
     * @return <code>none</code>
     */
    //-------------------------------------------------------------------------
    public static Hashtable getTokenHash(String data, String token){
        //-------------------------------------------------------------------------
        Hashtable hashtable=new Hashtable();
        Vector vector=getStringTokenVector(data,token);
        int size=vector.size();
        
        for(int index=0;index<size;index++){
            String key=(String)vector.get(index);
            hashtable.put(key,key);
        }
        
        return hashtable;
    }// getParameterValues() ENDS
    
    /**
     * <code>getStringTokenVector</code>
     * <p>
     * This
     * </p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   1.3
     * @param  <code>none</code>
     * @return <code>none</code>
     */
    //-------------------------------------------------------------------------
    public static Vector getStringTokenVector(String data, String token){
        //-------------------------------------------------------------------------
        Vector results=new Vector();
        
        if(data!=null&&token!=null&&!data.equals("")&&!token.equals("")){
            StringTokenizer tokenizer=new StringTokenizer(data,token);
            
            while (tokenizer.hasMoreTokens())  {
                String value=tokenizer.nextToken();
                results.addElement(value);
            }
        }
        
        return results;
    }// getParameterValues() ENDS
    
    /**
     * <code>getXMLFile</code>
     * <p>
     * This
     * </p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   1.3
     * @param  <code>none</code>
     * @return <code>none</code>
     */
    //----------------------------------------------------------------------
    public String getXMLFile(){
        //----------------------------------------------------------------------
        return tagData.toString();
    }
    
    /**
     * <code>setXMLHeader</code>
     * <p>
     * This
     * </p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   1.3
     * @param  <code>none</code>
     * @return <code>none</code>
     */
    //----------------------------------------------------------------------
    public void setXMLHeader(String xslFile){
        //----------------------------------------------------------------------
        int start=xslFile.lastIndexOf('/');
        xslFile=xslFile.substring((start+1),xslFile.length());
        tagData.append("<?xml version=\"1.0\" encoding=\"ISO8859-1\" ?>"+cr);
    }
    
    /**
     * <code>setXSLFile</code>
     * <p>
     * This
     * </p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   1.3
     * @param  <code>none</code>
     * @return <code>none</code>
     */
    //----------------------------------------------------------------------
    public void setXSLFile(String xslFile){
        //----------------------------------------------------------------------
        this.xslFile=xslFile;
    }
    
    /**
     * <code>log</code>
     * <p>
     * This method logs errors to netscapes error log.
     * </p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   1.3
     * @param message <code>String</code> the error to log.
     * @return true <code>boolean</code> If there are more records to fetch.
     */
    //---------------------------------------------------------------------------
    public static void log(String message,ServletContext context){
        //---------------------------------------------------------------------------
        StringBuffer buffer=new StringBuffer(
        Calendar.getInstance().getTime().toString()+": "+message);
        context.log(buffer.toString());
    }// log() ENDS
    
    /**
     * <code>nvl</code>
     * <p>
     * This
     * </p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   1.3
     * @param  <code>none</code>
     * @return <code>none</code>
     */
    //------------------------------------------------------------------------
    public static String nvl(String parameter,String defaultValue){
        //-------------------------------------------------------------------------
        if(parameter==null||parameter.equals(""))
            parameter=defaultValue;
        
        return parameter;
    }//getNVL() ENDS
    
    
    
    /**
     * <code>factory</code>
     * <p>
     * This
     * </p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   1.3
     * @param  <code>none</code>
     * @return <code>none</code>
     */
    //-------------------------------------------------------------------------
    public Object factory(String factoryName){
        //-------------------------------------------------------------------------
        Object factoryObject=null;
        Class factoryWorker=null;
        
        try {
            factoryWorker=Class.forName(factoryName);
            factoryObject=factoryWorker.newInstance();
        }
        catch (Exception e) {
            System.err.println("I could not get a instance of " + factoryName);
        }
        
        return factoryObject;
    }// factory() ENDS
    
    /**
     * <code>enumerateList</code>
     * <p>
     * This
     * </p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   1.3
     * @param  <code>none</code>
     * @return <code>none</code>
     */
    //----------------------------------------------------------------------
    public static void enumerateList(Hashtable hashtable,PageContext pageContext){
        //-------------------------------------------------------------------------
        String key="";
        String value="";
        
        if(hashtable!=null){
            Enumeration enum=hashtable.keys();
            
            for(;enum.hasMoreElements();){
                key=(String)enum.nextElement();
                value=(String)hashtable.get(key);
                try{
                    pageContext.getOut().println(key+"="+value);
                    System.out.println(key+"="+value);
                }
                catch(IOException exception){
                    System.out.println("Utilities.enumerateList: "+exception);
                }
            }
        }
    }// enumerateList() ENDS
    
    /**
     * <code>enumerateList</code>
     * <p>
     * This methods enumerates through a hashtable, It expects the values to
     * be String.
     * </p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   1.3
     * @param  <code>none</code>
     * @return <code>none</code>
     */
    //-------------------------------------------------------------------------
    public static void enumerateList(Hashtable hashtable){
        //-------------------------------------------------------------------------
        String key="";
        String value="";
        
        if(hashtable!=null){
            Enumeration enum=hashtable.keys();
            
            for(;enum.hasMoreElements();){
                key=(String)enum.nextElement();
                value=(String)hashtable.get(key);
                System.out.println(key+"="+value);
            }
        }
    }// enumerateList() ENDS
    
    /**
     * <code></code>
     * <p>
     * This methods enumerates through a list and sends the output to
     * STDOUT. It is mainly used for debugging.
     * </p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   1.3
     * @param  <code>none</code>
     * @return <code>none</code>
     */
    //-------------------------------------------------------------------------
    public static void enumerateList(Enumeration enum){
        //-------------------------------------------------------------------------
        for(;enum.hasMoreElements();)
            System.out.println(enum.nextElement());
    }// enumerateList() ENDS
    
    
    /**
     * <code>callFKWhere</code>
     * <p>
     * This method is used to invoke the FK_n_WHERE method in another class.
     * The "invoked" method takes NO parameters.
     * </p>
     * @author  Steve A. Olson
     * @version 1.0
     * @since   1.0
     * @param   parentObject the other class that contains the method to be invoked
     * @param   foreignKey the Foreign Key name that determines the method name.
     * @return  String whereClause based on the ForeignKey
     */
    //-------------------------------------------------------------------------
    public final String callFKWhere(java.lang.Object parentObject,
    java.lang.String foreignKey) {
        //-------------------------------------------------------------------------
        Object methodReturn = "";
        try{
            Class c = parentObject.getClass();
            Method method = c.getMethod("get"+foreignKey+"_WHERE",null);
            methodReturn = method.invoke(parentObject,null);
        }
        catch(Exception e){
            System.out.println("Invalid ForeignKey("+foreignKey+")"+e);
        }
        return (String) methodReturn;
    }
    
    /**
     * <code>invokeMethod</code>
     * <p>
     * This method is used to invoke a method within another class.
     * The method must take 1 parameter only. This parameter must be
     * of type Object.
     * </p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   1.3
     * @param   bean the string class name
     * @param   beanMethod the method being invoked.
     * @param   classObject an instance of an object we can operate on.
     * @return  none
     */
    //-------------------------------------------------------------------------
    public Object invokeMethod(String bean, String beanMethod,
    Object classObject){
        //-------------------------------------------------------------------------
        Object argumentList[]=new Object[1];
        Object factoryObject=new Object();
        Class parameterList[]=new Class[1];
        parameterList[0]=factoryObject.getClass();
        
        try{
            /**
             * first we get an instance of the class. Then we get the Class class
             * of that object. Next we get an instance of the method and finally
             * we invoke the method.
             */
            factoryObject=factory(bean);
            Class beanObject=factoryObject.getClass();
            Method classMethod=beanObject.getMethod(beanMethod, parameterList);
            argumentList[0]=classObject;
            classMethod.invoke(factoryObject,argumentList);
        }
        catch(Throwable exception) {
            System.out.println("invokeMethod: "+exception);
        }
        return factoryObject;
    } // invokeMethod() ENDS
    
    /**
     * <code>invokeMethod</code>
     * <p>
     * This method is used to invoke a method within another class.
     * </p>
     * @since   1.3
     * @author  Booker Northington II
     * @version 1.0
     * @param   bean is the class object
     * @param   beanMethod is the method were invoking
     * @param   list is the parameter list if no parmeters, send null
     */
    //-------------------------------------------------------------------------
    public Object invokeMethod(Object bean, String beanMethod,
    Object list){
        //-------------------------------------------------------------------------
        Object argumentList[]=new Object[1];
        Class parameterList[]=new Class[1];
        Object methodResults=null;
        Class beanObject=null;
        Method classMethod=null;
        
        try{
            if(list==null){
                Method[] methodArray=bean.getClass().getMethods();
                int size=methodArray.length;
                
                for(int index=0;index<size;index++){
                    classMethod=methodArray[index];
                    
                    if(classMethod.getName().trim().equals(beanMethod.trim()))
                        break;
                }
                
                methodResults=classMethod.invoke(bean,null);
            }
            else{
                Method[] methodArray=bean.getClass().getMethods();
                int size=methodArray.length;
                argumentList[0]=list;
                parameterList[0]=list.getClass();
                
                for(int index=0;index<size;index++){
                    classMethod=methodArray[index];
                    
                    if(classMethod.getName().trim().equals(beanMethod.trim()))
                        break;
                }
                
                methodResults=classMethod.invoke(bean,argumentList);
            }
            
            //classMethod=beanObject.getMethod(beanMethod, parameterList);
            //methodResults=classMethod.invoke(bean,argumentList);
        }
        catch(InvocationTargetException exception) {
            System.out.println("Utilities.invokeMethod: "+exception);
            
            if(classMethod!=null)
                System.out.println("methodName = "+classMethod.getName());
            
            if(beanObject!=null)
                System.out.println("className = "+beanObject.getName());
            
            if(parameterList!=null)
                System.out.println("parameterList = "+parameterList);
            
            System.out.println("--------------------------------------\n");
        }
        catch(Exception exception) {
            System.out.println("1. Utilities.invokeMethod: "+exception+"\n");
            
            if(classMethod!=null)
                System.out.println("methodName = "+classMethod.getName());
            
            if(beanObject!=null)
                System.out.println("className = "+beanObject.getName());
            
            if(parameterList!=null)
                System.out.println("parameterList = "+parameterList);
            
            System.out.println("--------------------------------------\n");
        }
        
        return methodResults;
    } // invokeMethod() ENDS
    
    /**
     * <code>isFileEncrypted</code>
     * <p>
     * This method checks to see if the file is encrypted.
     * <p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   JDK1.3
     * @param inputStream <code>InputStream</code> the files inputstream.
     * @return results <code>boolean</code>true if the file is encrypted.
     */
    //---------------------------------------------------------------------------
    public boolean isFileEncrypted(InputStream inputStream){
        //---------------------------------------------------------------------------
        StringBuffer buffer=new StringBuffer();
        boolean fileEncrypted=false;
        StringBuffer suffix=new StringBuffer(RandomHash.offset);
        suffix=suffix.reverse();
        int aChar=0;
        
        try{
            while((aChar=inputStream.read())!=-1)
                buffer.append((char)aChar);
        }
        catch(IOException exception){
            exception.printStackTrace();
        }
        
        if(buffer.toString().endsWith(suffix.toString()))
            fileEncrypted=true;
        
        return fileEncrypted;
    }//isFileEncrypted()
    
    /**
     * <code>isFileEncryptedStringPrep</code>
     * <p>
     * This methods turns a filename into an inputStream. It passes that
     * information to isFileEncrypted to check to see if this file is
     * encrypted.
     * <p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   JDK1.3
     * @param string <code>String</code> the file name.
     * @return results <code>boolean</code>true if the file is encrypted.
     */
    //---------------------------------------------------------------------------
    public boolean isFileEncryptedStringPrep(String initSrc,
    ServletContext context){
        //---------------------------------------------------------------------------
        InputStream inputStream=context.getResourceAsStream(initSrc);
        return isFileEncrypted(inputStream);
    }//isFileEncryptedStringPrep() ENDS
    
    
    /**
     * <code>escapeHtml</code>
     * <p>
     * </p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   1.3
     * @param  <code>none</code>
     * @return <code>none</code>
     */
    //------------------------------------------------------------------------
    public static String escapeHtml(String data){
        //------------------------------------------------------------------------
        if(data==null)
            data="";
        
        StringBuffer buffer=new StringBuffer(data);
        String value="";
        char letter=' ';
        int stringLength=buffer.length();
        boolean needToChange=false;
        
        for(int index=0;index<stringLength;index++){
            letter=buffer.charAt(index);
            
            if(letter=='<'){
                value="lt;";
                needToChange=true;
            }
            else if(letter=='>'){
                value="gt;";
                needToChange=true;
            }
            else if(letter=='&'){
                value="amp;";
                needToChange=true;
            }
            else if(letter=='"'){
                value="quot;";
                needToChange=true;
            }
            
            if(needToChange){
                buffer.setCharAt(index,'&');
                buffer.insert((index+1),value);
                needToChange=false;
                stringLength=buffer.length();
            }
        }
        return buffer.toString();
    }
    
    /**
     * <code>createSignedJar</code>
     * <p>
     * </p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   1.3
     * @param identity what to call this identity
     * @param publicKeyName file to store the public key in
     * @param privateKeyName file to store the private key in
     */
    //-------------------------------------------------------------------------
    public void createSignedJar(String identity,String publicKeyName,
    String privateKeyName){
        //-------------------------------------------------------------------------
        /**
      forkProcess("javakey -cs "+identity+" true");
      forkProcess("javakey -gk "+Duke+"DSA 512 "+publicKeyName+" "privateKeyName);
      javakey -gc cert_directive_Duke
      jar cf signedWriteFile.jar writeFile.class writeFile.html
      javakey -gs sign_directive_Duke signedWriteFile.jar
      mv signedWriteFile.jar.sig signedWriteFile.jar
      jar tvf signedWriteFile.jar
      javakey -ld
         **/
    }
    
    /**
     * <code>forkProcess</code>
     * <p>
     * </p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   1.3
     * @param  <code>none</code>
     * @return <code>none</code>
     */
    //-------------------------------------------------------------------------
    public synchronized static boolean forkProcess(String cmd){
        //-------------------------------------------------------------------------
        return forkProcess(cmd, null);
    }
    
    /**
     * method: forkProcess
     * usage: this method compiles the generated java files.
     * @param none
     * @return true if everything went ok
     */
    //---------------------------------------------------------------------------
    public synchronized static boolean forkProcess(String cmd, String[] env){
        //---------------------------------------------------------------------------
        boolean status=true;
        String errorBuffer="";
        String outputBuffer="";
        
        try{
            Runtime runtime=Runtime.getRuntime();
            Process proc=runtime.exec(cmd,env);
            errorBuffer=processErrorMessages(proc);
            outputBuffer=processOutputMessages(proc);
            
            if(errorBuffer.length()>0){
                status=false;
                System.out.println("<Errors>");
                System.out.println(cmd);
                System.out.println(errorBuffer);
                System.out.println("</Errors>");
            }
            if(outputBuffer.length()>0){
                System.out.println("<Output>");
                System.out.println(outputBuffer);
                System.out.println("</Output>");
            }
            
            int exitVal = proc.waitFor();
            System.out.println("Process exitValue: " + exitVal);
        }
        catch(InterruptedException exception){
            exception.printStackTrace();
        }
        catch(SecurityException exception){
            exception.printStackTrace();
        }
        catch(IOException exception){
            exception.printStackTrace();
        }
        
        return status;
    }//forkProcess() ENDS
    
    /**
     * method: forkProcess
     * usage: this method compiles the generated java files.
     * @param none
     * @return true if everything went ok
     */
    //---------------------------------------------------------------------------
    public synchronized static boolean forkProcess(String[] cmd){
        //---------------------------------------------------------------------------
        return forkProcess(cmd, null);
    }//forkProcess() ENDS
    
    /**
     * method: forkProcess
     * usage: this method compiles the generated java files.
     * @param none
     * @return true if everything went ok
     */
    //---------------------------------------------------------------------------
    public synchronized static boolean forkProcess(String[] cmd, String[] env){
        //---------------------------------------------------------------------------
        boolean status=true;
        String errorBuffer="";
        String outputBuffer="";
        
        try{
            Runtime runtime=Runtime.getRuntime();
            Process proc=runtime.exec(cmd, env);
            errorBuffer=processErrorMessages(proc);
            outputBuffer=processOutputMessages(proc);
            
            if(errorBuffer.length()>0){
                status=false;
                System.out.println("<Errors>");
                System.out.println(cmd);
                System.out.println(errorBuffer);
                System.out.println("</Errors>");
            }
            if(outputBuffer.length()>0){
                System.out.println("<Output>");
                System.out.println(outputBuffer);
                System.out.println("</Output>");
            }
            
            int exitVal = proc.waitFor();
            System.out.println("Process exitValue: " + exitVal);
        }
        catch(InterruptedException exception){
            exception.printStackTrace();
        }
        catch(SecurityException exception){
            exception.printStackTrace();
        }
        catch(IOException exception){
            exception.printStackTrace();
        }
        
        return status;
    }//forkProcess() ENDS
    
    /**
     * <code>getInputStream</code>
     * <p>
     * This method creates an open and a close tag at the same time.
     * </p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   1.3
     * @param  tag<code>String</code>
     * @return value<code>String</code>
     */
    //----------------------------------------------------------------------
    public InputStream getInputStream(String filename){
        //----------------------------------------------------------------------
        FileInputStream fileInputStream=null;
        try{
            fileInputStream=new FileInputStream(filename);
        }
        catch(FileNotFoundException exception){
            exception.printStackTrace();
        }
        catch(SecurityException exception){
            exception.printStackTrace();
        }
        return fileInputStream;
    }
    
    /**
     * <code>openCloseTag</code>
     * <p>
     * This method creates an open and a close tag at the same time.
     * </p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   1.3
     * @param  tag<code>String</code>
     * @return value<code>String</code>
     */
    //----------------------------------------------------------------------
    public void openCloseTag(String tag, String value){
        //----------------------------------------------------------------------
        tagData.append("<"+tag+">"+value+"</"+tag+">"+cr);
    }
    
    /**
     * <code>openTag</code>
     * <p>
     * </p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   1.3
     * @param  <code>none</code>
     * @return <code>none</code>
     */
    //----------------------------------------------------------------------
    public void openTag(String spacer, String tag){
        //----------------------------------------------------------------------
        tagData.append(spacer);
        tagData.append("<"+tag+">"+cr);
    }
    
    /**
     * <code>processErrorMessages</code>
     * <p>
     * </p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   1.3
     * @param  <code>none</code>
     * @return <code>none</code>
     */
    //-------------------------------------------------------------------------
    public static String processErrorMessages(Process process){
        //-------------------------------------------------------------------------
        return processMessages(process.getErrorStream());
    }
    
    /**
     * <code>processOutputMessages</code>
     * <p>
     * </p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   1.3
     * @param  <code>none</code>
     * @return <code>none</code>
     */
    //-------------------------------------------------------------------------
    public static String processOutputMessages(Process process){
        //-------------------------------------------------------------------------
        return processMessages(process.getInputStream());
    }
    
    /**
     * <code>processMessages</code>
     * <p>
     * </p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   1.3
     * @param  <code>none</code>
     * @return <code>none</code>
     */
    //-------------------------------------------------------------------------
    public static String processMessages(InputStream inputStream){
        //-------------------------------------------------------------------------
        try{
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            StringBuffer errorBuffer=new StringBuffer();
            
            while ((line=br.readLine())!=null) {
                errorBuffer.append(line);
                errorBuffer.append("\n");
            }
            
            return errorBuffer.toString();
        }
        catch (Throwable t){
            t.printStackTrace();
            return "";
        }
    }
    
    /**
     * <code></code>
     * <p>
     * The getTableKeys places the tables foreign keys in
     * a hash for use later.
     * </p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   1.3
     * @param  <code>none</code>
     * @return <code>none</code>
     * @param   connection <code>Connection</code>the db connection.
     * @param   table <code>String</code>the table were looking up.
     * @param   type <code>String</code>either "import" or "export".
     * @return  userHash <code>Hashtable</code>hashtable which holds the answers.
     * @since   JDK1.3
     */
    //----------------------------------------------------------------------
    public Hashtable getTableKeys(Connection connection, String table,
    String type, Hashtable userHash){
        //----------------------------------------------------------------------
        DatabaseMetaData dbmd=null;
        ResultSet rs=null;
        Hashtable tempHash=null;
        String pkTable="";
        String pkColumn="";
        String fkTable="";
        String fkColumn="";
        
        try{
            dbmd=connection.getMetaData();
            
            if(type.toUpperCase().equals("IMPORT"))
                rs=dbmd.getImportedKeys("","",table);
            else
                rs=dbmd.getExportedKeys("","",table);
            
            while(rs.next()){
                pkTable=rs.getString(3).toUpperCase();
                pkColumn=rs.getString(4).toUpperCase();
                fkTable=rs.getString(7).toUpperCase();
                fkColumn=rs.getString(8).toUpperCase();
                /**
System.out.print("table = "+table+" ");
System.out.print("pkTable = "+pkTable+" ");
System.out.print("pkColumn = "+pkColumn+" ");
System.out.print("fkTable = "+fkTable+" ");
System.out.println("fkColumn = "+fkColumn+" ");
                 **/
                
                
                /**
                 * if the table has already been added to the
                 * hashtable, update the current hashtable
                 * otherwise add a new table.
                 */
                if(type.toUpperCase().equals("IMPORT")&&!userHash.containsKey(pkTable)){
                    tempHash=new Hashtable();
                }
                else if(type.toUpperCase().equals("EXPORT")&&!userHash.containsKey(fkTable)){
                    tempHash=new Hashtable();
                }
                else if(type.toUpperCase().equals("IMPORT")&&userHash.containsKey(pkTable)){
                    tempHash=(Hashtable)userHash.get(pkTable);
                }
                else if(type.toUpperCase().equals("EXPORT")&&userHash.containsKey(fkTable)){
                    tempHash=(Hashtable)userHash.get(fkTable);
                }
                
                
                /**
                 * store the basetable name as the key.
                 */
                if(!type.toUpperCase().equals("IMPORT")){
                    tempHash.put(pkColumn,fkColumn);
                    userHash.put(fkTable,tempHash);
                    
                    //System.out.print("Mapping1: "+pkTable+"."+pkColumn);
                    //System.out.println(" =  "+fkTable+"."+fkColumn+ " access name is "+fkTable);
                }
                else{
                    tempHash.put(fkColumn,pkColumn);
                    userHash.put(pkTable,tempHash);
                    
                    //System.out.print("Mapping2: "+fkTable+"."+fkColumn);
                    //System.out.println(" =  "+pkTable+"."+pkColumn+ " access name is "+pkTable);
                }
                
            }
        }
        catch(SQLException exception){
            System.out.println("*******Something went wrong: "+exception);
        }
        
        return userHash;
    }
    
    /**
     * <code>characterData</code>
     * <p>
     * </p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   1.3
     * @param  <code>none</code>
     * @return <code>none</code>
     */
    //----------------------------------------------------------------------
    public void characterData(String data){
        //----------------------------------------------------------------------
        tagData.append(data+cr);
    }
    
    /**
     * <code>writeFile</code>
     * <p>
     * </p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   1.3
     * @param  <code>none</code>
     * @return <code>none</code>
     */
    //-------------------------------------------------------------------------
    public boolean writeFile(String path,String xmlFile)throws IOException{
        //-------------------------------------------------------------------------
        boolean returnValue=true;
        
        if(xmlFile!=null&&!xmlFile.equals("")){
            PrintWriter pw = new PrintWriter(
            new BufferedWriter(
            new FileWriter(path))
            );
            pw.print(xmlFile);
            pw.close();
        }
        return returnValue;
    }// writeFile() ENDS

    
    /**
     * <code>writeBytesToFile</code>
     * <p>
     * This method writes an array of bytes to a file.
     * </p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   1.3
     * @param bytes <code>byte[]</code> the bytes to be written.
     * @param filename <code>String</code> full path to the file to write to.
     * @return <code>none</code>
     */
    //---------------------------------------------------------------------------
    public synchronized static void writeToFile(String from_name,String to_name)throws IOException{
    //---------------------------------------------------------------------------
        File from_file = new File(from_name);
        File to_file = new File (to_name);

        FileInputStream from = null;
        FileOutputStream to = null;

        try { 
        from = new FileInputStream(from_file);
        to = new FileOutputStream(to_file);
        byte[] buffer = new byte[4096];
        int bytes_read = 0;
                
        while((bytes_read = from.read(buffer)) != -1) // Read bytes until EOF
            to.write(buffer, 0, bytes_read);
        }
        catch (IOException ioexception) {
            System.out.println("Utilities.writeToFile"+ioexception);
        }
        
        
        finally{
        if (from != null) try {from.close();} catch (IOException e) {;}
        if (to != null) try {to.close();} catch (IOException e) {;}
        }
        
        //delete the temparary file.
        from_file.deleteOnExit();
     }// writeToFile() END    
    
    /**
     * <code>invokeMethod</code>
     * <p>
     * This method is used to invoke a method within another class.
     * if you invoke your method using this method, you can specify
     * any parameter list. The elements must appear in-order within
     * the vector.
     * </p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   1.3
     * @param   bean <code>String</code>the string class name
     * @param   beanMethod <code>String</code>the method being invoked.
     * @param   parameters <code>Vector</code>the method parameter list.
     * @return  answer<code>Object</code>the results.
     */
    //-------------------------------------------------------------------------
    public Object invokeMethod(String bean, String beanMethod,
    Vector parameters){
        //-------------------------------------------------------------------------
        int totalParameters=parameters.size();
        Class parameterList[]=new Class[totalParameters];
        Object argumentList[]=new Object[totalParameters];
        Object factoryObject=null;
        
        for(int index=0;index<totalParameters;index++){
            parameterList[index]=parameters.get(index).getClass();
            argumentList[index]=parameters.get(index);
        }
        
        
        try{
            /**
             * first we get an instance of the class. Then we get the Class class
             * of that object. Next we get an instance of the method and finally
             * we invoke the method.
             */
            factoryObject=factory(bean);
            Class beanObject=factoryObject.getClass();
            Method classMethod=beanObject.getMethod(beanMethod, parameterList);
            classMethod.invoke(factoryObject,argumentList);
        }
        catch(Throwable exception) {
            System.out.println("invokeMethod: "+exception);
        }
        
        return factoryObject;
    } // invokeMethod() ENDS
    
    /**
     * <code>getClientCookies</code>
     * <p>
     * </p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   1.3
     * @param  <code>none</code>
     * @return <code>none</code>
     */
    public Object getClientCookie(HttpServletRequest request, String cookie){
        Cookie[] cookies = request.getCookies();
        Object object=new Object();
        
        for(int i=0; i < cookies.length; i++){
            Cookie thisCookie = cookies[i];
            
            if (thisCookie.getName().equals(cookie)){
                ;
            }
            else {
                ;
            }
        }
        
        return object;
    }
    
    /**
     * <code>getEnvironment</code>
     * <p>
     * </p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   1.3
     * @param  <code>none</code>
     * @return <code>none</code>
     */
    //-------------------------------------------------------------------------
    public Properties getEnvironment() throws IOException{
        //-------------------------------------------------------------------------
        Properties properties = new Properties();
        String operatingSystem = System.getProperty("os.name").toLowerCase();
        
        if (operatingSystem.indexOf("sunos") > -1||
        operatingSystem.indexOf("unix") > -1){
            properties.load(Runtime.getRuntime().exec("env").getInputStream());
        }
        else if (operatingSystem.indexOf("nt") > -1){
        }
        else if (operatingSystem.indexOf("windows 9") > -1){
        }
        
        return properties;
    }
    
    /**
     * <code>hashtableDeepCopy</code>
     * <p>
     * </p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   1.3
     * @param  <code>none</code>
     * @return <code>none</code>
     */
    //-------------------------------------------------------------------------
    public Hashtable hashtableDeepCopy(Hashtable table){
        //-------------------------------------------------------------------------
        Hashtable newTable=new Hashtable();
        
        if(table!=null){
            Enumeration enum=table.keys();
            
            for(;enum.hasMoreElements();){
                Object key=enum.nextElement();
                newTable.put(key,table.get(key));
            }
        }
        
        return newTable;
    }
    
    /**
     * <code>errorLog</code>
     * <p>
     * </p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   1.3
     * @param  <code>none</code>
     * @return <code>none</code>
     */
    //-------------------------------------------------------------------------
    public static void errorLog(String msg){
        //-------------------------------------------------------------------------
    }// errorLog() ENDS
    
    
    /**
     * <code>getLevel</code>
     * <p>
     * </p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   1.3
     * @param  <code>none</code>
     * @return <code>none</code>
     */
    //-------------------------------------------------------------------------
    public int getLevel(){
        //-------------------------------------------------------------------------
        return debugLevel;
    }// getDebugLevel() ENDS
    
    
    /**
     * <code>setLevel</code>
     * <p>
     * </p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   1.3
     * @param  <code>none</code>
     * @return <code>none</code>
     */
    //-------------------------------------------------------------------------
    public void setLevel(String debugLevel){
        //-------------------------------------------------------------------------
        this.debugLevel=1;
        
        try{
            int temp=(new Integer(debugLevel)).intValue();
            
            if(temp<=5)
                this.debugLevel=temp;
        }
        catch(NumberFormatException exception){
            System.out.println("setLevel.setDebugLevel() ");
        }
    }// setDebugLevel() ENDS
    
    
    /**
     * <code>highlightText</code>
     * <p>
     * </p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   1.3
     * @param  <code>none</code>
     * @return <code>none</code>
     */
    //------------------------------------------------------------------------
    public String highlightText(String data,String highlightedText){
        //------------------------------------------------------------------------
        JTextArea area = new JTextArea(data);
        return area.getText();
    }
    
    /**
     * <code>getParameterValues</code>
     * <p>
     * </p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   1.3
     * @param  <code>none</code>
     * @return <code>none</code>
     */
    //-------------------------------------------------------------------------
    public String[] getParameterValues(HttpServletRequest request,String name){
        //-------------------------------------------------------------------------
        String[] values=request.getParameterValues(name);
        if(values==null) {
            values=new String[1];
            values[0]="";
        }
        return values;
    }// getParameterValues() ENDS
    
    /**
     * <code>forwardRequest</code>
     * <p>
     * </p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   1.3
     * @param  <code>none</code>
     * @return <code>none</code>
     */
    //-------------------------------------------------------------------------
    public boolean forwardRequest(String url, HttpServletRequest request,
    HttpServletResponse response){
        //-------------------------------------------------------------------------
        boolean result=true;
        try{
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request,response);
        }
        catch(IOException exception){}
        catch(ServletException exception){}
        return result;
    }// forwardRequest() ENDS
    
    /**
     * <code>parseConnectionName</code>
     * <p>
     * This methods will parse the standard connection name that is printed
     * out if the object has not defined its own toString method.
     * </p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   1.3
     * @param name <code>String</code> the object being parsed.
     * @return result <code>String</code> the neatly formatted string object name
     */
    //-------------------------------------------------------------------------
    public String parseObjectName(String connectionName){
        //-------------------------------------------------------------------------
        int start=(connectionName.indexOf("@"))+1;
        connectionName=connectionName.substring(start,connectionName.length());
        return connectionName;
    }// parseObjectName() ENDS
    
    
    /**
     * <code>stringToInt</code>
     * <p>
     * This methods converts a string into an int.
     * </p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   1.3
     * @param intValue <code>String</code> the value to be converted.
     * @return result <code>int</code> the requested value as an int. If an
     * error is encountered, the Integer.MAX_VALUE is returned.
     */
    //-------------------------------------------------------------------------
    public static int stringToInt(String intValue){
        //-------------------------------------------------------------------------
        int results=0;
        
        if(intValue!=null){
            try{
                results=Integer.parseInt(intValue);
            }
            catch(NumberFormatException exception){
                results=Integer.MAX_VALUE;
            }
        }
        
        return results;
    }// stringToInt() ENDS
    
    /**
     * <code>stringToInt</code>
     * <p>
     * This methods converts a string into an int.
     * </p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   1.3
     * @param intValue <code>String</code> the value to be converted.
     * @param defaultValue <code>int</code> the default value if the string is
     * invalid.
     * @return result <code>int</code> the requested value as an int.
     */
    //-------------------------------------------------------------------------
    public int stringToInt(String intValue, int defaultValue){
        //-------------------------------------------------------------------------
        try {
            return Integer.parseInt(nvl(intValue, defaultValue+""));
        }
        catch(NumberFormatException exception) {
            return defaultValue;
        }
    }// stringToInt() ENDS
    
    
    /**
     * <code>writeErrorToBrowser</code>
     * <p>
     * </p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   1.3
     * @param  <code>none</code>
     * @return <code>none</code>
     */
    //-------------------------------------------------------------------------
    public static void writeToBrowser(PageContext pageContext,String message){
        //-------------------------------------------------------------------------
        try{
            pageContext.getOut().println(message+"<br>");
        }
        catch(IOException exception){}
    }// writeToBrowser() ENDS
    
    /**
     * <code>writeBytesToFile</code>
     * <p>
     * This method writes an array of bytes to a file.
     * </p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   1.3
     * @param bytes <code>byte[]</code> the bytes to be written.
     * @param filename <code>String</code> full path to the file to write to.
     * @return <code>none</code>
     */
    //---------------------------------------------------------------------------
    public synchronized static void
    writeBytesToFile(byte[] bytes,String filename) throws IOException{
        //---------------------------------------------------------------------------
        DataOutputStream dataOutputStream=new DataOutputStream(
        new BufferedOutputStream(new FileOutputStream(filename)));
        dataOutputStream.write(bytes,0,bytes.length);
        dataOutputStream.close();
    }//writeBytesToFile() ENDS

    /**
     * <code>writeHeaderToFile</code>
     * <p>
     * This method writes the mime header to a file.
     * </p>
     * @author  Booker Northington II
     * @version 1.0
     * @since   1.3
     * @param request <code>HTTPServletRequest</code> the servlet request
     * @param filename <code>String</code> full path to the file to write to.
     * @return <code>none</code>
     */
    //---------------------------------------------------------------------------
    public static void writeRequestHeaderToFile(InputStream inputStream,
    String filename)throws IOException{
        //---------------------------------------------------------------------------
        //     try{
        PrintWriter printWriter=new PrintWriter(new BufferedWriter(
        new FileWriter(filename)));
        int data=inputStream.read();
        
        while(data!=-1){
            printWriter.print((char) data);
            data=inputStream.read();
        }
        
        printWriter.close();
        //     }
        //     catch(IOException exception){
        //        exception.printStackTrace();
        //     }
    }//writeHeaderToFile() ENDS
}// Utilities() ENDS
