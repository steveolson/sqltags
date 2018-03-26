/* $Id: Initialize.java,v 1.15 2002/08/12 13:10:06 jpoon Exp $
 * $Log: Initialize.java,v $
 * Revision 1.15  2002/08/12 13:10:06  jpoon
 * clean up
 *
 * Revision 1.14  2002/06/20 21:00:50  jpoon
 * fix init id and name
 *
 * Revision 1.13  2002/03/20 12:29:18  booker
 * Removed session attribute.
 *
 * Revision 1.12  2002/03/19 20:02:33  booker
 * placed initializationProperties in the session. Will figure out
 * a better implementation later...
 *
 * Revision 1.11  2002/03/19 19:14:01  booker
 * checking in sqltags. working on 2nd cut of ojp
 *
 * Revision 1.10  2002/03/15 14:33:31  solson
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
import javax.servlet.ServletInputStream;  
import java.util.Dictionary;  
import java.util.Dictionary;  
import java.util.Hashtable;  
import java.util.Enumeration;  
import java.util.Properties;  
import java.io.BufferedReader;  
import java.io.File;  
import java.io.FileInputStream;
import java.io.FileReader;  
import java.io.InputStream;  
import java.io.InputStreamReader;  
import java.io.IOException;  
import java.net.URL;  
import java.net.URLConnection;  
import javax.servlet.ServletContext;  
import javax.servlet.jsp.PageContext;  
import javax.servlet.jsp.tagext.BodyTagSupport;  
import javax.servlet.jsp.JspException;  
import javax.servlet.jsp.JspWriter;  
import javax.servlet.jsp.tagext.BodyContent;  
import java.io.*;
import java.net.*;
/**  
 * <code>Initialize</code>   
 * <p>  
 * This class reads a properties file and stores the  
 * information in the session. The properties are then accessed by  
 * beans, tags ect.  
 * <p>  
 * @author  Booker Northington II  
 * @version 1.0  
 * @since   JDK1.3  
 */  
//------------------------------------------------------------------------------  
public class Initialize extends BodyTagSupport{  
//------------------------------------------------------------------------------  
   /**  
    * <code>Initialize</code>   
    * <p>  
    * Class constructor. Responsible for creating the hash and making it  
    * available for the session.  
    * <p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param initSrc <code>String</code> the name of the url.  
    * @param encryptFile <code>boolean</code> flag setting encryption on or off.  
    * @return none <code>none</code> none.  
    */  
   //------------------------------------------------------------------------------  
   public Initialize(){  
   //------------------------------------------------------------------------------  
   }  
  
   /**  
    * <code>Initialize</code>   
    * <p>  
    * Class constructor. Responsible for creating the hash and making it  
    * available for the session.  
    * <p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param initSrc <code>String</code> the name of the url.  
    * @param encryptFile <code>boolean</code> flag setting encryption on or off.  
    * @return none <code>none</code> none.  
    */  
   //------------------------------------------------------------------------------  
   public Initialize(String src){  
   //------------------------------------------------------------------------------  
       
        this.initSrc=src;  

        if(!initSrc.trim().equals("")&&isFileEncryptedStringPrep(src))
            properties=(new RandomHash(src)).getHashProperties();  
        else
            setPropertiesFile(initSrc);
   }//Initialize() ENDS  

   /**  
    * <code>Initialize</code>   
    * <p>  
    * Class constructor. Responsible for creating the hash and making it  
    * available for the session.  
    * <p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param initSrc <code>String</code> the name of the url.  
    * @param encryptFile <code>boolean</code> flag setting encryption on or off.  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public Initialize(String initSrc,ServletContext context){  
   //---------------------------------------------------------------------------  
      this.initSrc=initSrc;
      this.context=context;
      getInitSrcProperties(initSrc,context);
   }//Initialize() ENDS  

   
   //***************************************************************************  
   // Finalize Method  
   //***************************************************************************  
   /**  
    * This is the finalizer method for this class  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   protected void finalize() throws Throwable{  
   //-------------------------------------------------------------------------  
      super.finalize();  
   }// finalizer() ENDS  
  
   /**  
    * <code>fileReader</code>   
    * <p>  
    * This method takes a url and reads the properties from it.   
    * The properties must be in the form key=value.  
    * <p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param none <code>none</code> none.  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   private void fileReader(){  
   //---------------------------------------------------------------------------  
        try{  
            FileInputStream inputStream=new FileInputStream(initSrc);
            properties.load(inputStream);
        }  
        catch(IOException exception){  
            System.out.println("Exception "+exception);  
        }  
   }//fileReader() ENDS  
   
  /**  
    * The <b>getId</b> method is used to return the name attribute.  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public String getId(){  
   //---------------------------------------------------------------------------  
      return id;  
   }// getId() ENDS  
   
    
    /**  
    * The <b>getInitializationProperties</b> method is return the hash properties.  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public Properties getInitializationProperties(){  
   //---------------------------------------------------------------------------  
      return initializationProperties;  
   }// getInitializationProperties() ENDS  
   
   /**  
    * The <b>getInitialize</b>   
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public Initialize getInitialize(){  
   //---------------------------------------------------------------------------  
      return this;  
   }// getInitialize() ENDS  
  
   /**  
    * The <b>getInitID</b>
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param name the name value  
    * @return  none  
    */  
   //---------------------------------------------------------------------------
   public String getInitID(){  
   //---------------------------------------------------------------------------  
      return initID;  
       //return getId();
   }// getInitID() ENDS    

   /**  
    * The <b>getInitId</b>  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param name the name value  
    * @return  none  
    */  
   //---------------------------------------------------------------------------  
   public String getInitId(){
   //---------------------------------------------------------------------------  
       return getInitID();
   }//getInitID() ENDS
   
   /**  
    * The <b>getInitProperties</b>  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param name the name value  
    * @return  none  
    */  
   //---------------------------------------------------------------------------  
   public String getInitProperties(){  
   //---------------------------------------------------------------------------  
      return initProperties;  
   }// getInitProperties() ENDS  
   
 
   /**  
    * The <b>getInitSrc</b>   
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public String getInitSrc(){  
   //---------------------------------------------------------------------------  
      return initSrc;  
   }// getInitSrc() ENDS  
    
   /**  
    * <code>getInitSrcProperties</code>   
    * <p>  
    * This method takes a url and reads the properties from it.   
    * The properties must be in the form key=value.  
    * <p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param none <code>none</code> none.  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public Properties getInitSrcProperties(String initSrc,
           ServletContext context){ 
   //--------------------------------------------------------------------------- 
      this.context=context;
      
      if(!initSrc.trim().equals("")){  
        if(isFileEncryptedStringPrep(initSrc)){  
           RandomHash randomHash=new RandomHash(initSrc,context);  
           properties=randomHash.getHashProperties();  
        }  
        else
            setPropertiesFile(initSrc);
      } 
      
      return properties;
   }// getInitSrcProperties() ENDS     
   
  /**  
    * The <b>getInitURL</b> 
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param name the name value  
    * @return  none  
    */  
   //---------------------------------------------------------------------------  
   public String getInitURL(){  
   //---------------------------------------------------------------------------  
      return initURL;  
   }// getInitURL() ENDS  

   /**  
    * <code>getProperties</code>   
    * <p>  
    * This method checks to see if the file is encrypted. 
    * <p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param  <code>none</code>
    * @return properties <code>Properties</code>the init properties. 
    */  
   //---------------------------------------------------------------------------  
     public Properties getProperties(){
   //---------------------------------------------------------------------------  
       return properties;
   }//getProperties() ENDS
     
   /**
    * <code>getProperty</code>  
    * <p>  
    * This method returns a initialization property.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param string <code>String</code> the property key.  
    * @return string <code>String</code> The property value
    */  
   //---------------------------------------------------------------------------  
   public String getProperty(String string){  
   //--------------------------------------------------------------------------- 
       if(initializationProperties==null)
           return null;
      return initializationProperties.getProperty(string);
   }// getConnection() ENDS  
   
   /**
    * <code>getProperty</code>  
    * <p>  
    * This method returns a initialization property or the specified default
    * value if the property dose not exist.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param string <code>String</code> the property key.  
    * @param defaultString <code>String</code> the default to return on null. 
    * @return string <code>String</code> The property value
    */  
   //---------------------------------------------------------------------------  
   public String getProperty(String string,String defaultString){  
   //---------------------------------------------------------------------------  
    if(initializationProperties==null)
        return defaultString;

      return initializationProperties.getProperty(string,defaultString);
   }// getProperty() ENDS  
  
   /**  
    * <code>getValue</code>   
    * <p>  
    * This method returns the values of property within the properties  
    * hash.  
    * <p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param key <code>String</code> the key.  
    * @return value <code>String</code> the value.  
    */  
   //------------------------------------------------------------------------------  
   public String getPropertyValue(String key){  
   //------------------------------------------------------------------------------  
      String value=(String)properties.get(key);   
  
      if(value==null)  
         value="";  
  
      return value;  
   }//getPropertyValue() ENDS  
   
  /**  
    * <code>getValue</code>   
    * <p>  
    * This method returns the values of property within the properties  
    * hash.  
    * <p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param key <code>String</code> the key.  
    * @return value <code>String</code> the value.  
    */  
   //------------------------------------------------------------------------------  
   public String getValue(String key,String defaultValue){  
   //------------------------------------------------------------------------------  
      String value=(String)properties.get(key);   
  
      if(value==null)  
         value=defaultValue;  
  
      return value;  
   }//getValue() ENDS  
 
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
    private boolean isFileEncrypted(InputStream inputStream){
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
   private boolean isFileEncryptedStringPrep(String initSrc){
   //---------------------------------------------------------------------------  
      InputStream inputStream=context.getResourceAsStream(initSrc);
      return isFileEncrypted(inputStream);
   }//isFileEncryptedStringPrep() ENDS 
         
   /**  
    * <code>log</code>  
    * <p>  
    * This method logs errors to netscapes error log.  
    * </p>  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param message <code>String</code> the error to log.  
    * @return true <code>boolean</code> If there are more records to fetch.  
    */  
   //---------------------------------------------------------------------------  
   public void log(String string){  
   //---------------------------------------------------------------------------  
      pageContext.getServletContext().log(string);  
      return;  
   }// log() ENDS  
   
   /**  
    * <code>setFileOrURL</code>   
    * <p>  
    * This method returns the name of the url.  
    * <p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param none <code>none</code> none.  
    * @return value <code>String</code> the name of the url.  
    */  
   //---------------------------------------------------------------------------  
   public void setFileOrURL(String fileOrURL){  
   //---------------------------------------------------------------------------  
      if(fileOrURL!=null&&fileOrURL.equals("true"))  
         this.fileOrURL=true;  
   }//setFileOrURL() ENDS  
  
   /**  
    * The <b>setId</b> method sets the value of the name attribute.  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param name the name value  
    * @return  none  
    */  
   //---------------------------------------------------------------------------  
   public void setId(String string){  
   //---------------------------------------------------------------------------  
      id=string;  
      return;  
   }// setId() ENDS  
   
   /**  
    * The <b>setInitEncrypted</b> depreciated!  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param name the name value  
    * @return  none  
    */  
   //---------------------------------------------------------------------------  
     public void setInitEncrypted(String s){
   //---------------------------------------------------------------------------  
        ; // do nothing!
     }//setInitEncrypted() ENDS
     
   /**  
    * The <b>setInitializationProperties</b> depreciated!  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param name the name value  
    * @return  none  
    */  
   //---------------------------------------------------------------------------  
   public void setInitializationProperties() throws JspException{
   //---------------------------------------------------------------------------  
       initializationProperties=
            (Properties) pageContext.getSession().getAttribute(getInitId());
       
       context=pageContext.getServletContext();
       
       if(initializationProperties==null){
            if(!initSrc.trim().equals("")){
               initializationProperties=getInitSrcProperties(initSrc,context);  
            }
            else if(!initURL.trim().equals(""))
                initializationProperties=urlReader(initURL);
            else if(!initProperties.trim().equals(""))
                initializationProperties=getInitSrcProperties
                (initSrc,pageContext.getServletContext());  

            if(initializationProperties==null){  
                exceptionString=
                    "InitializeTag could not locate properties file.";  
                pageContext.getSession().setAttribute
                    ("exceptionString",exceptionString);  
                throw new JspException
                    ("No connection properties file was specified");  
            }  
            else
                pageContext.getSession().setAttribute
                    (getInitId(),initializationProperties);        
        }
       
      return;
  }//setInitializationProperties() ENDS
   
   public void setInitId(String string){ setInitID(string);}
 
   /**  
    * The <b>setInitID</b>
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param name the name value  
    * @return  none  
    */  
   //---------------------------------------------------------------------------  
   public void setInitID(String string){  
   //---------------------------------------------------------------------------  
      initID=string;  
      return;  
   }// setInitID() ENDS  
   
   /**  
    * The <b>setInitProperties</b>  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param name the name value  
    * @return  none  
    */  
   //---------------------------------------------------------------------------  
   public void setInitProperties(String string){  
   //---------------------------------------------------------------------------  
      initProperties=string;  
      return;  
   }// setInitProperties() ENDS  
   
   /**  
    * The <b>setInitSrc</b>   
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param name the name value  
    * @return  none  
    */  
   //---------------------------------------------------------------------------  
   public void setInitSrc(String string){  
   //---------------------------------------------------------------------------  
      initSrc=string;  
      return;  
   }// setInitSrc() ENDS  
    
   /**  
    * <code>setPropertiesFile</code>   
    * <p>  
    * This method loads properties from a file.
    * <p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param  initSrc<code>String</code>the file name.
    * @return none <code></code>
    */  
   //---------------------------------------------------------------------------  
   private void setPropertiesFile(String initSrc){
      properties=new Properties();    
      InputStream inputStream=context.getResourceAsStream(initSrc);
      
      try{
        properties.load(inputStream);
      }
      catch(IOException exception){
          exception.printStackTrace();
      }
      return;
   }//setPropertiesFile() ENDS 
   
   /**  
    * <code>setValue</code>   
    * <p>  
    * This method sets a property within the properties hash.  
    * <p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param key <code>String</code> the key.  
    * @param value <code>String</code> the value.  
    * @return none <code>none</code> none.  
    */  
   //------------------------------------------------------------------------------  
   public void setValue(String key, String value){  
   //------------------------------------------------------------------------------  
      if(key!=null&&value!=null)  
         properties.put(key,value);   
  
      return;  
   }    
           
   /**  
    * The <b>setInitURL</b> 
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param name the name value  
    * @return  none  
    */  
   //---------------------------------------------------------------------------  
   public void setInitURL(String string){  
   //---------------------------------------------------------------------------  
      initURL=string;  
      return;  
   }// setInitURL() ENDS  
   
  /**  
    * <code>toString</code>  
    * <p>  
    * This method sets the column bind.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param bind <code>String</code> the column bind  
    * @return <code>none</code>  
    */  
   //---------------------------------------------------------------------------  
   public String toString(){  
   //---------------------------------------------------------------------------  
      StringBuffer buffer=new StringBuffer("*****Initialize:");   
      buffer.append("\tfileOrURL="+fileOrURL);  
      buffer.append("\tid="+id);  
      buffer.append("\tinitSrc="+initSrc);  
      buffer.append("\tNAME="+NAME);  
      return buffer.toString();  
   }// toString() ENDS  
  
   
   /**  
    * <code>urlReader</code>   
    * <p>  
    * This method takes a url and reads the properties from it.   
    * The properties must be in the form key=value.  
    * <p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param none <code>none</code> none.  
    * @return none <code>none</code> none.  
    */  
   //--------------------------------------------------------------------------- 
   public Properties urlReader(String urlString){  
   //---------------------------------------------------------------------------
      Properties properties=new Properties();
      
      try{  
        URL url=new URL(urlString);  
        InputStream inputStream=url.openStream();
           
        if(isFileEncrypted(inputStream)){  
           RandomHash randomHash=new RandomHash();  
           properties=randomHash.readEncryptedStream(inputStream);  
        }   
        else
            properties.load(url.openStream());
      }  
     catch (MalformedURLException exception) {
        System.out.println("Exception "+exception);  
      }  
      catch(IOException exception){  
        System.out.println("Exception "+exception);  
      }  
      return properties;
   }//urlReader() ENDS  
   
   //***************************************************************************  
   // Class Variables  
   //***************************************************************************       
   private ServletContext     context;
   protected String           exceptionString="";  
   private boolean            fileOrURL=false;  
   protected String           id="";  
   protected Properties       initializationProperties=null;  
   protected String           initEncrypted="false";  
   protected String           initID="InitializeTag";  
   protected String           initProperties="";
   protected String           initSrc="";   
   protected String           initURL="";
   public static final String NAME="initialize";  
   private Properties         properties=new Properties();
}// Initialize Class ENDS  