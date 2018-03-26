/* $Id: GeneratorProperties.java,v 1.7 2002/04/10 17:39:10 booker Exp $
 * $Log: GeneratorProperties.java,v $
 * Revision 1.7  2002/04/10 17:39:10  booker
 * Added functionality to read in default values for
 * the column properties type fields.
 *
 * Revision 1.6  2002/03/15 14:23:46  solson
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
package com.aitworks.sqltags.generator;
import java.util.Hashtable;
import java.util.Properties;
import javax.swing.JTextArea;

/**  
 * <code>GeneratorProperties</code>  
 * <p>  
 * This class holds the properties used to run the generator.
 * </p>  
 * @author  Booker Northington II  
 * @version 1.0  
 * @since   1.3  
 */  
//------------------------------------------------------------------------------  
public class GeneratorProperties {
//------------------------------------------------------------------------------  
   //*************************************************************************** 
   // Class Constructor 
   //***************************************************************************
   /**  
    * <code>Class Constructor</code>  
    * <p>  
    * Sets the current generator properties
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param hashtable <code>Hashtable</code>a hashtable with the current
    * properties.
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
   public GeneratorProperties(Hashtable  hashtable){
   //---------------------------------------------------------------------------  
      selectBindDefaults=(String)hashtable.get("selectBindDefaults");
      urlString=(String)hashtable.get("urlString");
      userName=(String)hashtable.get("userName"); 
      password=(String)hashtable.get( "password"); 
      packageName=(String)hashtable.get("packageName"); 
      outputDirectory=(String)hashtable.get("outputDirectory"); 
      jarFilename=(String)hashtable.get("jarFilename"); 
      whereToPlaceJar=(String)hashtable.get("whereToPlaceJar"); 
      tmpWorkDir=(String)hashtable.get("tmpWorkDir"); 
      textArea=(JTextArea)hashtable.get("textArea");
      databaseDriver=(String)hashtable.get("databaseDriver");
      aitworksPackageBase=(String)hashtable.get("aitworksPackageBase");      
      includeSource=(new Boolean((String)hashtable.get("includeSource"))).booleanValue();
      includeClasses=(new Boolean((String)hashtable.get("includeClasses"))).booleanValue();
      generatedClasses=
         (new Boolean((String)hashtable.get("generatedClasses"))).booleanValue();
   }//Constructor ENDS

   //***************************************************************************  
   //Finalize Method  
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

   //*************************************************************************** 
   //Public Methods
   //***************************************************************************
   public static Properties getProperties(){
      properties=new Properties();
 //     properties.setProperty("selectBindDefaults",selectBindDefaults);
      properties.setProperty("urlString",urlString);
      properties.setProperty("userName",userName);
      properties.setProperty("password",password);
      properties.setProperty("packageName",packageName);
      properties.setProperty("outputDirectory",outputDirectory);
      properties.setProperty("taglibName",TagLibName);
      properties.setProperty("jarFilename",jarFilename);
      properties.setProperty("whereToPlaceJar",whereToPlaceJar);
      properties.setProperty("tmpWorkDir",tmpWorkDir);
      properties.setProperty("aitworksPackageBase",aitworksPackageBase);
      properties.setProperty("databaseDriver",databaseDriver);
      
      if(includeSource)
         properties.setProperty("includeSource","true");
      else
         properties.setProperty("includeSource","false");
      
      if(includeClasses)
         properties.setProperty("includeClasses","true");
      else
         properties.setProperty("includeClasses","false");
      
      if(generatedClasses)
         properties.setProperty("generatedClasses","true");
      else
         properties.setProperty("generatedClasses","false");

      return properties;
   }//getProperties() ENDS

   /**  
    * <code>toString</code>  
    * <p>  
    * This methods returns the current value of it members.
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
      StringBuffer buffer=new StringBuffer("*****GeneratorProperties:");  
      buffer.append("\tdatabaseDriver"+databaseDriver);  
      buffer.append("\turlString"+urlString);  
      buffer.append("\tuserName="+userName);  
      buffer.append("\tpassword="+password);  
      buffer.append("\tpackageName="+packageName);  
      buffer.append("\toutputDirectory="+outputDirectory);  
      buffer.append("\tjarFilename="+jarFilename);  
      buffer.append("\twhereToPlaceJar="+whereToPlaceJar);  
      buffer.append("\ttmpWorkDir="+tmpWorkDir);  
      buffer.append("\taitworksPackageBase="+aitworksPackageBase);  
      buffer.append("\tincludeClasses="+includeClasses);  
      buffer.append("\tincludeSource="+includeSource);  
      buffer.append("\tgeneratedClasses="+generatedClasses);      
      return buffer.toString();
   }//toString() ENDS

   //*************************************************************************** 
   // Class Variables 
   //***************************************************************************
   private static String databaseDriver="";
   private static String urlString="";
   private static String userName=""; 
   private static String password="";
   private static String packageName="";
   private static String outputDirectory="";
   private static String jarFilename="";
   private static String whereToPlaceJar="";
   private static String tmpWorkDir="";
   private static String aitworksPackageBase="";
   public static boolean includeClasses=false;
   public static boolean includeSource=false;
   public static boolean generatedClasses=false;
   private static String selectBindDefaults="";
   private static Properties properties;
   public static JTextArea textArea;
   public static final String TagLibName="taglib.tld";
}//GeneratorProperties() ENDS