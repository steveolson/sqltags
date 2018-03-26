/* $Id: CreateJar.java,v 1.19 2002/06/27 18:35:27 solson Exp $
 * $Log: CreateJar.java,v $
 * Revision 1.19  2002/06/27 18:35:27  solson
 * added "contrib" to aitworks packages included into jar
 *
 * Revision 1.18  2002/05/30 19:10:10  solson
 * removed aitworks-jsptags and aitworks-ldap from jar ...
 *
 * Revision 1.17  2002/03/15 14:33:32  solson
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
import com.aitworks.sqltags.generator.GeneratorProperties;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.jar.JarOutputStream;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**  
 * <code>CreateJar</code>  
 * <p>  
 * This class creates a jar file which contains project specific information.
 * </p>  
 * @author  Booker Northington II  
 * @version 1.0  
 * @since   1.3  
 */  
public class CreateJar{
   //***************************************************************************  
   //Class Constructors
   //***************************************************************************  
   /**  
    * <code>Class Constructor</code>  
    * <p>  
    * Creates the default jar file. It expects GeneratorProperties to be
    * loaded.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
   public CreateJar(){
   //---------------------------------------------------------------------------  
      properties=GeneratorProperties.getProperties();
      buildJar();
   }//Constructor ENDS

   /**  
    * <code>Class Constructor</code>  
    * <p>  
    * Creates a jar file. The properties are passed in.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param properties <code>Properties</code> Connection Properties
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
   public CreateJar(Properties properties){
      this.properties=properties;
      buildJar();
   }//Constructor ENDS

   //***************************************************************************  
   //Finalize Method  
   //***************************************************************************  
   /**  
    * <code>Finalize</code>  
    * <p>  
    * This method is invoked at garbage collection time.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
   protected void finalize() throws Throwable{  
   //---------------------------------------------------------------------------  
      super.finalize();  
   }// finalizer() ENDS  

   //***************************************************************************  
   //Public Method  
   //***************************************************************************  
   /**  
    * <code>main</code>  
    * <p>  
    * This method is used for testing.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
   public static final void main(String [] args){
   //---------------------------------------------------------------------------
      Properties properties=CreateJar.loadFileProperties(new File(args[0]));
      new CreateJar(properties);
   }// main() ENDS

   //***************************************************************************  
   //Private Method  
   //***************************************************************************  
   /**  
    * <code>buildJar</code>  
    * <p>  
    * Builds the jar.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
   private void buildJar(){
   //---------------------------------------------------------------------------  
      packageName=(String)properties.get("packageName");
      whereToPlaceJar=(String)properties.get("whereToPlaceJar");
      jarName=(String)properties.get("jarFilename");;
      tagLibDir=(String)properties.get("taglibJarDirectory");
      outputDirectory=(String)properties.get("outputDirectory");
      includeClasses=(new Boolean
         ((String)properties.get("includeClasses"))).booleanValue();
      includeSource=(new Boolean
         ((String)properties.get("includeSource"))).booleanValue();
      generateClasses=(new Boolean
         ((String)properties.get("generatedClasses"))).booleanValue();
      aitworksPackageBase=
         ((String)properties.get("aitworksPackageBase"))+fileSeparator;
      
      if(!outputDirectory.endsWith(fileSeparator)){
         properties.setProperty("outputDirectory",
                                 outputDirectory+fileSeparator);
         outputDirectory=(String)properties.get("outputDirectory");
      }
      
      if(!whereToPlaceJar.endsWith(fileSeparator)){
         properties.setProperty("whereToPlaceJar",
                                 whereToPlaceJar+fileSeparator);
         whereToPlaceJar=(String)properties.get("whereToPlaceJar");
      }
      
      try{
         getJavaClassNames();
         zipItUp();
      }
      catch(Exception exception){
         System.out.println("CreateJar.buildJar: "+exception);
      }
   }//buildJar() ENds
   
   /**  
    * <code>getJarData</code>  
    * <p>  
    * This method isolates files of various types.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
   private Vector getJarData(String[] names,String fileType){
   //---------------------------------------------------------------------------  
      int size=names.length;
      Vector storage=new Vector();

      for(int index=0;index<size;index++)
         if(names[index].endsWith(fileType.trim()))
            storage.add(names[index]);
      
      return storage;
   }// getJarData() ENDS

   /**  
    * <code>getJavaClassNames</code>  
    * <p>  
    * This method gets the classes which are to be included in the jar.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
   private void getJavaClassNames(){
   //---------------------------------------------------------------------------  
      javaNameVector=new Vector();
      javaClassesVector=new Vector();
      File file=null;
      String[] names=null;
      setPackagePaths();
      
      if(includeClasses){
         loadJarPackages(sqlPackage,sqltagsNames,".class",sqlOutputPath);
         // loadJarPackages(jspPackage,jsptagsNames,".class",jspOutputPath);
         // loadJarPackages(ldapPackage,defaultNames,".class",ldapOutputPath);
         loadJarPackages(creditCardPackage,creditCardNames,".class",
            creditCardOutputPath);
      }

      if(includeSource){
         loadJarPackages(sqlPackage,sqltagsNames,".java",sqlOutputPath);
         // loadJarPackages(jspPackage,jsptagsNames,".java",jspOutputPath);
         // loadJarPackages(ldapPackage,defaultNames,".java",ldapOutputPath);
         loadJarPackages(creditCardPackage,creditCardNames,".java",
            creditCardOutputPath);
      }
      
      // We place this in the jar by default.
      String name=packageName.replace('.','/');
      loadJarPackages(outputDirectory,defaultNames,".class",name+"/");

      if(generateClasses)
         loadJarPackages(outputDirectory,defaultNames,".java",name+"/");
   }// getJavaClassNames() ENDS

   /**  
    * <code>loadFileProperties</code>  
    * <p>  
    * This method update the property text fields on the gui.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
   private static Properties loadFileProperties(File file){
   //---------------------------------------------------------------------------  
      Properties properties=new Properties();
      
      try{
         FileInputStream inputStream=new FileInputStream(file);
         PropertyResourceBundle bundle=
            new PropertyResourceBundle(inputStream);
         Enumeration enum=bundle.getKeys();

         for(;enum.hasMoreElements();){
            String key=(String)enum.nextElement();
            properties.setProperty(key,bundle.getString(key));
         }
      }
      catch (Exception exception){
         System.err.println("CreateJar.loadProperties:: " + exception);
      }
      return properties;
   }//loadProperties() END


   /**  
    * <code>loadJarPackages</code>  
    * <p>  
    * This method gets the names and types of the file to be placed in the jar.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
   private void loadJarPackages(String prefix,String[] packageNames,
                                String type,String outputName){
   //---------------------------------------------------------------------------  
      int size=packageNames.length;
      File file=null;

      for(int index=0;index<size;index++){
         String name=packageNames[index];
         file=new File(prefix+name);
         String[] names=file.list();
         Vector data=null;
         
         if(names!=null)
            data=getJarData(names,type);
         else
            System.out.println("no "+type+" files found in "+prefix+name+
               " dir.");

         if(data!=null&&data.size()>0){
            String[] temp=new String[2];
            temp[0]=prefix+name;
            outputName=outputName.replace('\\','/');
            
            if(type.equals(".java"))
               temp[1]="src/"+outputName;
            else
               temp[1]=outputName;          
          
            if(!name.trim().equals(""))
               temp[1]=temp[1]+name+"/";
            
            hashtableVector.put(data,temp);
         }
      }
   }// loadJarPackages() ENDS

   /**  
    * <code>placeEntryInJar</code>  
    * <p>  
    * This method places the entry in the jarfile.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param stream <code>ZipOutputStream</code> the output stream.
    * @param filename <code>String</code> the filename
    * @param data <code>byte[]</code>the data.
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
   private void placeEntryInJar(JarOutputStream stream,String filename,
                                 byte[] data){
   //---------------------------------------------------------------------------  
      try {
         ZipEntry zipEntry= new ZipEntry(filename);
         zipEntry.setMethod(ZipEntry.DEFLATED);
         stream.putNextEntry(zipEntry);
         stream.write(data,0,data.length);
         stream.closeEntry();
      }
      catch(IOException e){
         e.printStackTrace();
      }
   }// placeEntryInJar() ENDS

   /**  
    * <code>setPackagePaths</code>  
    * <p>  
    * This method sets the packages default paths.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
   private void setPackagePaths(){
   //---------------------------------------------------------------------------  
      if(fileSeparator.equals("/")){
         sqlPackage=aitworksPackageBase+"aitworks-sqltags/src/"+
            sqltagsUnixPrefix;
         /*
         jspPackage=aitworksPackageBase+"aitworks-jsptags/src/"+
            jsptagsUnixPrefix;
         ldapPackage=aitworksPackageBase+"aitworks-ldap/src/"+ldapUnixPrefix;
          */
         creditCardPackage=aitworksPackageBase+"aitworks-creditcard/src/"+
            creditCardUnixPrefix;
         sqlOutputPath=sqltagsUnixPrefix;
         jspOutputPath=jsptagsUnixPrefix;
         ldapOutputPath=ldapUnixPrefix;
         creditCardOutputPath=creditCardUnixPrefix;
      }
      else{
         sqlPackage=aitworksPackageBase+"aitworks-sqltags\\src\\"+
            sqltagsDosPrefix;
         /*
         jspPackage=aitworksPackageBase+"aitworks-jsptags\\src\\"+
            jsptagsDosPrefix;
         ldapPackage=aitworksPackageBase+"aitworks-ldap\\src\\"+ldapDosPrefix;
          */
         creditCardPackage=aitworksPackageBase+"aitworks-creditcard\\src\\"+
            creditCardDosPrefix;
         sqlOutputPath=sqltagsDosPrefix;
         jspOutputPath=jsptagsDosPrefix;
         ldapOutputPath=ldapDosPrefix;
         creditCardOutputPath=creditCardDosPrefix;
      }
   }//setPackagePaths() ENDS

  /**  
    * <code>zipItUP</code>  
    * <p>  
    * This methods creates the zip file.
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param stream <code>ZipOutputStream</code> the output stream.
    * @param filename <code>String</code> the filename
    * @param data <code>byte[]</code>the data.
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
   private void zipItUp() throws IOException{
   //---------------------------------------------------------------------------  
      outputStream=new FileOutputStream(whereToPlaceJar+jarName);
      JarOutputStream jar=new JarOutputStream(outputStream);
      Enumeration enum=hashtableVector.keys();

      for(;enum.hasMoreElements();){
         Vector vector=(Vector)enum.nextElement();
         String[] filePath=(String[])hashtableVector.get(vector);
         int size=vector.size();

         for(int index=0;index<size;index++){
            String name=(String)vector.elementAt(index);

            inputStream=new FileInputStream(filePath[0]+fileSeparator+name);
            int arraySize=inputStream.available();
            byte[] byteArray=new byte[arraySize];
            inputStream.read(byteArray);
            placeEntryInJar(jar,filePath[1]+name,byteArray);
         }
      }

      inputStream.close();
      String tldFullPath=properties.get("tmpWorkDir")+File.separator+GeneratorProperties.TagLibName;
      inputStream=new FileInputStream(tldFullPath);
      byte[] byteArray=new byte[inputStream.available()];
      inputStream.read(byteArray);
      placeEntryInJar(jar,"META-INF/"+GeneratorProperties.TagLibName,byteArray);
      inputStream.close();
      outputStream.flush();
      jar.close();
      outputStream.close();
   }// zipItUp() ENDS

   //***************************************************************************  
   //Class Variables
   //***************************************************************************  
   private String sqlOutputPath;
   private String jspOutputPath;
   private String ldapOutputPath;
   private String creditCardOutputPath;
   private String sqlPackage="";
   private String jspPackage="";
   private String ldapPackage="";
   private String creditCardPackage="";
   private String aitworksPackageBase="g:\\corepackages\\aitworks-sqltags\\src";
   private String creditCardDosPrefix="com\\aitworks\\creditcard\\";
   private String creditCardUnixPrefix="com/aitworks/creditcard/";
   private Properties properties;
   private String fileSeparator=File.separator;
   private boolean generateClasses=true;
   private boolean includeClasses=true;
   private boolean includeSource=true;
   private FileInputStream inputStream;
   private Vector javaNameVector;
   private Vector javaClassesVector;
   private File jarFile;
   private String jarName="c:\\aitworks.jar";
   private String jsptagsDosPrefix="com\\aitworks\\jsptags\\";
   private String jsptagsUnixPrefix="com/aitworks/jsptags/";
   private String ldapDosPrefix="com\\aitworks\\ldap\\";
   private String ldapUnixPrefix="com/aitworks/ldap/";
   private String sqltagsDosPrefix="com\\aitworks\\sqltags\\";
   private String sqltagsUnixPrefix="com/aitworks/sqltags/";
   private String jsptagsPrefix="com\\aitworks\\jsptags\\";
   private String outputDirectory;
   private String sqltagsBase="g:\\corepackages\\aitworks-sqltags\\src\\";
   private File baseDir;
   private FileOutputStream outputStream;
   private Vector items = new Vector();
   private Vector ignoreList = new Vector();
   private String packageName;
   private String tagLibDir;
   private Hashtable hashtableVector=new Hashtable();
   private String[] sqltagsNames={"generator","interfaces","jsptags",
                                  "tagextrainfo","utilities","contrib"};
   private String[] jsptagsNames={"access","conditional","tagextrainfo"};
   private String[] creditCardNames={"authorizenet","viakliks"};
   private String[] defaultNames={""};
   private String sqltagsPrefix;
   private String whereToPlaceJar;
}// CreateJar() ENDS