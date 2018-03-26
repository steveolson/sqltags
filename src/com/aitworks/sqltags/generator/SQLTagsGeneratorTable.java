/* $Id: SQLTagsGeneratorTable.java,v 1.31 2002/07/05 18:22:33 lindahl Exp $
 * $Log: SQLTagsGeneratorTable.java,v $
 * Revision 1.31  2002/07/05 18:22:33  lindahl
 * Made compatible with updated CreateTLD interface
 *
 * Revision 1.30  2002/05/23 15:49:42  solson
 * removed xml type defaulting feature
 *
 * Revision 1.29  2002/04/24 15:05:33  booker
 * Added a check to remove System Tables from
 * db generation.
 *
 * Revision 1.28  2002/04/23 16:41:48  booker
 * Fixed dr$
 *
 * Revision 1.27  2002/04/15 11:21:26  booker
 * Modified time and who turns the buildMenu back on after
 * the generation has been completed.
 *
 * Revision 1.26  2002/04/15 11:10:16  booker
 * Changed scope of textArea and buildMenu. Added
 * methods to reflect new behavior.
 *
 * Revision 1.25  2002/04/12 12:24:04  booker
 * Added Scrolling ability to the textarea.
 *
 * Revision 1.24  2002/04/10 17:39:10  booker
 * Added functionality to read in default values for
 * the column properties type fields.
 *
 * Revision 1.23  2002/03/15 14:23:47  solson
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
// import com.aitworks.sqltags.utilities.BindReader;
import com.aitworks.sqltags.utilities.CreateJar;
import com.aitworks.sqltags.utilities.Utilities;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import java.io.InputStream;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;

public class SQLTagsGeneratorTable{
   //*************************************************************************** 
   // Class Constructor 
   //***************************************************************************
   /**  
    * <code>Class Constructor</code>  
    * <p>  
    * Builds and displays the GUI
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
   public SQLTagsGeneratorTable(){
   //---------------------------------------------------------------------------  
      startTime=Calendar.getInstance().getTime().toString();  
      loadProperties();
      setConnection();
      createTableHash();
      load();
   }//Constructor() ENDS
   
   /**  
    * <code>Class Constructor</code>  
    * <p>  
    * Builds and displays the GUI
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
   public SQLTagsGeneratorTable(InputStream inputStream){
   //---------------------------------------------------------------------------  
      startTime=Calendar.getInstance().getTime().toString();  
      setPropertiesFile(propertiesFile.toString());
      loadPropertiesStream(inputStream);
      String packageName=properties.getProperty("packageName","");
      StringBuffer tmpWorkDir=new StringBuffer(
        properties.getProperty("tmpWorkDir",""));         
      packageName=packageName.replace('.',File.separatorChar);
      Utilities utilities=new Utilities();      
      Vector dir=utilities.getStringTokenVector(packageName,File.separator);  
      int size=dir.size();

      for(int index=0;index<size;index++){
          String name=(String)dir.elementAt(index);
          tmpWorkDir.append(File.separator+name);
          File file=new File(tmpWorkDir.toString());
          file.mkdir();
      }
      properties.setProperty("outputDirectory",tmpWorkDir.toString());
      
      setConnection();
      createTableHash();
      load();
   }//Constructor() ENDS
   
   /**  
    * <code>Class Constructor</code>  
    * <p>  
    * Builds and displays the GUI
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
   public SQLTagsGeneratorTable(String propertiesFile,boolean noGui){
   //---------------------------------------------------------------------------  
      startTime=Calendar.getInstance().getTime().toString();  
      setPropertiesFile(propertiesFile.toString());
      loadProperties();
      setConnection();
      createTableHash();
      load();
   }//Constructor() ENDS

   /**  
    * <code>Class Constructor</code>  
    * <p>  
    * Builds and displays the GUI
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
   public SQLTagsGeneratorTable(Properties properties){
   //---------------------------------------------------------------------------  
      startTime=Calendar.getInstance().getTime().toString();  
      this.properties=properties;
      selectBindDefaults=properties.getProperty("selectBindDefaults","");
      
      /*
      if(selectBindDefaults.length()>0)
          new BindReader(selectBindDefaults);
       */
      
      setConnection();
      createTableHash();
      load();
      
      if(GeneratorGUI.getTextArea()!=null)
          GeneratorGUI.setBuildMenuEnabled(true);
   }//Constructor() ENDS
    
   /**  
    * <code>Class Constructor</code>  
    * <p>  
    * Builds and displays the GUI
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param tableName <code>String</code> the table to build
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
   public SQLTagsGeneratorTable(String tableName){
   //---------------------------------------------------------------------------  
      startTime=Calendar.getInstance().getTime().toString();  
      loadProperties();
      setConnection();
      load(tableName);
   }//Constructor() ENDS

   /**  
    * <code>Class Constructor</code>  
    * <p>  
    * Builds and displays the GUI
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param tableName <code>String</code> table to build.
    * @param propertiesFile <code>String</code> database connection properties.
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
   public SQLTagsGeneratorTable(String tableName, String propertiesFile){
   //---------------------------------------------------------------------------  
      startTime=Calendar.getInstance().getTime().toString();  
      setPropertiesFile(propertiesFile);
      loadProperties();
      setConnection();
      load(tableName);
   }//Constructor ENDS

   //*************************************************************************** 
   //Finalizer Method
   //***************************************************************************
   /**  
    * This is the finalizer method for this class  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //---------------------------------------------------------------------------  
   protected void finalize() throws Throwable{  
   //---------------------------------------------------------------------------  
      super.finalize();  
   }// finalizer() ENDS  

   //*************************************************************************** 
   //Public Method
   //***************************************************************************
   public static void main(String args[] ){
      SQLTagsGeneratorTable generatorTable=null;
      
       if(args.length==0)
        generatorTable=new SQLTagsGeneratorTable();
       else{
            Utilities utilities=new Utilities();
            InputStream inputStream=utilities.getInputStream(args[0]);
            
            if(inputStream!=null)
                generatorTable=new SQLTagsGeneratorTable(inputStream);
        }
           
   }// main() ENDS
   
   public String toString() {
     return "Table: "+ getTableName()
             + " \n\tColumns: " + columnHash
             + " \n\tPK: "+ primaryKeyVector
             + " \n\tExported Keys: " + exportedForeignKeyHash
             + " \n\tImported Keys: " + importedForeignKeyHash
             ;
   }

   //*************************************************************************** 
   //Friendly Methods
   //***************************************************************************
   //---------------------------------------------------------------------------  
   String getPropertyValue(String name){
   //---------------------------------------------------------------------------  
        return properties.getProperty(name);
    }
   
   //---------------------------------------------------------------------------  
   Enumeration getColumns(){
   //---------------------------------------------------------------------------  
      return columnHash.keys();
   }

   //---------------------------------------------------------------------------  
   SQLTagsGeneratorColumn getColumn(String columnName) {
   //---------------------------------------------------------------------------  
      return (SQLTagsGeneratorColumn) columnHash.get(columnName);
   }

   //---------------------------------------------------------------------------  
   Enumeration getPrimaryKeys() {
   //---------------------------------------------------------------------------  
      return primaryKeyVector.elements();
   }

   //---------------------------------------------------------------------------  
   Enumeration getImportedForeignKeys() {
   //---------------------------------------------------------------------------  
      return importedForeignKeyHash.keys();
   }

   //---------------------------------------------------------------------------  
   SQLTagsGeneratorForeignKey getImportedForeignKey(String foreignKey) {
   //---------------------------------------------------------------------------  
      return (SQLTagsGeneratorForeignKey)importedForeignKeyHash.get(foreignKey);
   }

   //---------------------------------------------------------------------------  
   Enumeration getExportedForeignKeys() {
   //---------------------------------------------------------------------------  
      return exportedForeignKeyHash.keys();
   }

   //---------------------------------------------------------------------------  
   SQLTagsGeneratorForeignKey getExportedForeignKey(String foreignKey) {
   //---------------------------------------------------------------------------  
      return (SQLTagsGeneratorForeignKey)exportedForeignKeyHash.get(foreignKey);
   }

   //---------------------------------------------------------------------------  
   void setPropertiesFile(String propertiesFile){
   //---------------------------------------------------------------------------  
      this.propertiesFile=propertiesFile;
   }

   //---------------------------------------------------------------------------  
   String getPropertiesFile(){
   //---------------------------------------------------------------------------  
      return propertiesFile;
   }

   //---------------------------------------------------------------------------  
   Properties getPropertiesObject(){
   //---------------------------------------------------------------------------  
      return properties;
   }

   //---------------------------------------------------------------------------  
   String getUrlString() {
   //---------------------------------------------------------------------------  
      return urlString;
   }    

   //---------------------------------------------------------------------------  
   void setUrlString(String urlString) {
   //---------------------------------------------------------------------------  
      this.urlString=urlString;
   }

   //---------------------------------------------------------------------------  
   String getUserName() {
   //---------------------------------------------------------------------------  
      return this.userName;
   }

   //---------------------------------------------------------------------------  
   void setUserName(String userName) {
   //---------------------------------------------------------------------------  
      this.userName = userName;
   }

   //---------------------------------------------------------------------------  
   String getPassword() {
   //---------------------------------------------------------------------------  
      return this.password;
   }

   //---------------------------------------------------------------------------  
   void setPassword(String password) {
   //---------------------------------------------------------------------------  
      this.password = password;
   }

   //---------------------------------------------------------------------------  
   void setConnection(Connection connection){
   //---------------------------------------------------------------------------  
      this.connection=connection;
   }

   //---------------------------------------------------------------------------  
   void setConnection() {
   //---------------------------------------------------------------------------  
     if(connection != null) {
         return;
     }

     setUrlString(properties.getProperty("urlString"));
     setUserName(properties.getProperty("userName"));
     setPassword(properties.getProperty("password"));
     /*
System.out.println("url="+properties.getProperty("urlString"));
System.out.println("user="+properties.getProperty("userName"));
System.out.println("password="+properties.getProperty("password"));
      */
     try{
        Class.forName((String)properties.getProperty("databaseDriver"));
         connection=DriverManager.getConnection(getUrlString(),getUserName(),
                        getPassword());
         metaData = connection.getMetaData();
      } 
     catch(ClassNotFoundException e){
         System.out.println("Class not found: "+e);
     }
     catch( SQLException e) {
         System.out.println("SQLException: "+e);
     }
     this.connection = connection;
   }

   //---------------------------------------------------------------------------  
   String getTableName() {
   //---------------------------------------------------------------------------  
      return this.tableName;
   }

   //---------------------------------------------------------------------------  
   void setTableName(String tableName) {
   //---------------------------------------------------------------------------  
      this.tableName = tableName;
   }

   //---------------------------------------------------------------------------  
   Hashtable getExportedForeignKeyHash(){
   //---------------------------------------------------------------------------  
   return exportedForeignKeyHash;
   }//getExportedForeignKeyHash() ENDS

   //---------------------------------------------------------------------------  
   Hashtable getImportedForeignKeyHash(){
   //---------------------------------------------------------------------------  
   return importedForeignKeyHash;
   }//getImportedForeignKeyHash() ENDS

   //---------------------------------------------------------------------------  
   Vector getPrimaryKeyVector(){
   //---------------------------------------------------------------------------  
   return primaryKeyVector;
   }//getPrimaryKeyVector() ENDS

   Hashtable getColumnHash(){
     return columnHash;
   }

   //---------------------------------------------------------------------------  
   void resetBuffers(){
   //---------------------------------------------------------------------------  
      primaryKeyVector.setSize(0);
      importedForeignKeyHash=new Hashtable();
      exportedForeignKeyHash=new Hashtable();
      columnHash=new Hashtable();
   }

   //*************************************************************************** 
   //Private Methods
   //***************************************************************************
   //---------------------------------------------------------------------------  
   private void createTableHash(){
   //---------------------------------------------------------------------------  
      try{
         schemaName=properties.getProperty("metadataSchema");
         haveTables=false;
         
         if(schemaName!=null&&schemaName.equalsIgnoreCase("null"))
             schemaName=null;
         
         String[] tableTypes={"TABLE"};
         DatabaseMetaData dbMetaData= connection.getMetaData(); 
         ResultSet resultSet=dbMetaData.getTables
            (null,schemaName,"%",tableTypes);
         tableNameHash.clear();

          while(resultSet.next()){
            haveTables=true;
            String tableName=resultSet.getString(3);

            if(isTableValid(tableName))
               tableNameHash.put(tableName,tableName);
         }       
      }
      catch(SQLException ignore){
      }
   }//createTableHash() ENDS

   private boolean isTableValid(String table){
       boolean returnValue=true;
       if(table.indexOf(IGNORE1)>=0||
          table.indexOf(IGNORE2)>=0||
          table.indexOf(IGNORE3)>=0||
          table.indexOf(IGNORE4)>=0||
          table.indexOf(IGNORE5)>=0||
          table.indexOf(IGNORE6)>=0||
          table.indexOf(IGNORE7)>=0||
          table.indexOf(IGNORE8)>=0||
          table.indexOf(IGNORE9)>=0||
          table.indexOf(IGNORE10)>=0||
          table.indexOf(IGNORE11)>=0
         )
          returnValue=false;
       return returnValue;
   }//isTableValid() ENDS
   
   //*************************************************************************** 
   // Private Methods
   //***************************************************************************
   private void load(){
      primaryKeyVector.setSize(0);
      importedForeignKeyHash=new Hashtable();
      exportedForeignKeyHash=new Hashtable();
      columnHash=new Hashtable();
      Enumeration enum=tableNameHash.keys();
      String packageName=getPropertyValue("packageName");
      CreateTLD createTLD=new CreateTLD("taglib",packageName);  
      String outputTagName=schemaName;
      
      if(outputTagName==null)
          outputTagName=userName;
      
      try{
         sendMessageToGUI("<"+outputTagName+">\n");
         sendMessageToGUI("     <tables>\n");
         
         for(;enum.hasMoreElements();){
            String tableName=(String)enum.nextElement();
            setTableName(tableName);
            ResultSet rsColumns=metaData.getColumns
                    (null,schemaName,getTableName(),"%");

            while(rsColumns.next()) {
               SQLTagsGeneratorColumn col = new SQLTagsGeneratorColumn(
                  rsColumns.getString("COLUMN_NAME").toUpperCase()
                  ,rsColumns.getString("TYPE_NAME")
                  ,rsColumns.getInt("ORDINAL_POSITION")
                  ,rsColumns.getInt("COLUMN_SIZE")
               );
               columnHash.put
                (rsColumns.getString("COLUMN_NAME").toUpperCase(), col);
            }

            rsColumns.close();

            // Primary KEYS
            ResultSet pkColumns = metaData.getPrimaryKeys
                    (null,schemaName,getTableName());

            while(pkColumns.next())
               primaryKeyVector.addElement
                    (pkColumns.getString("COLUMN_NAME").toUpperCase());

            pkColumns.close();

            // Imported KEYS
            ResultSet rsIK = metaData.getImportedKeys
                        (null,schemaName,getTableName());

            while(rsIK.next()){
               SQLTagsGeneratorForeignKey ifk=(SQLTagsGeneratorForeignKey) 
                  importedForeignKeyHash.get
                    (rsIK.getString("FK_NAME").toUpperCase());

               if( ifk== null) {
                  ifk = new SQLTagsGeneratorForeignKey(
                  rsIK.getString("FK_NAME").toUpperCase(),
                  rsIK.getString("FKTABLE_NAME"),
                  rsIK.getString("PKTABLE_NAME"));
                  importedForeignKeyHash.put
                    ( rsIK.getString("FK_NAME").toUpperCase(), ifk);
               }

               ifk.addJoinPair(rsIK.getString("FKCOLUMN_NAME").toUpperCase(),
                  rsIK.getString("PKCOLUMN_NAME").toUpperCase());
            }

            rsIK.close();

            // Exported KEYS
            ResultSet rsEK = metaData.getExportedKeys
                    (null,schemaName,getTableName());

            while(rsEK.next()){
               SQLTagsGeneratorForeignKey efk = (SQLTagsGeneratorForeignKey) 
                  exportedForeignKeyHash.get
                    (rsEK.getString("FK_NAME").toUpperCase());

               if( efk== null) {
                  efk = new SQLTagsGeneratorForeignKey(
                     rsEK.getString("FK_NAME").toUpperCase()
                     ,rsEK.getString("FKTABLE_NAME"),
                     rsEK.getString("PKTABLE_NAME"));

                  exportedForeignKeyHash.put
                    (rsEK.getString("FK_NAME").toUpperCase(), efk);
               }
                  efk.addJoinPair(rsEK.getString("FKCOLUMN_NAME").toUpperCase(),
                     rsEK.getString("PKCOLUMN_NAME").toUpperCase());
            }

            rsEK.close();
            createFKTag(tableName,createTLD);
            new ClassBuilder(this);
         }
            if(haveTables){
                sendMessageToGUI("     </tables>\n\n");
                String outputDirectory=getPropertyValue("outputDirectory");
                String[] classPath=new String[]{
                "CLASSPATH="+System.getProperty("java.class.path")};
                String javac="javac "+outputDirectory+"*.java";  
                sendMessageToGUI("     <compile>\n"); 
                boolean errorsFound=forkProcess("javac "+outputDirectory+
                    java.io.File.separator+"*.java",classPath);

                if(!errorsFound)
                    sendMessageToGUI("          class files created\n"); 

                sendMessageToGUI("     </compile>\n\n"); 

                if(!errorsFound){
                    sendMessageToGUI("     <taglib>\n"); 
                    createTLD.cleanUp();  
                    String taglibName=getPropertyValue("tmpWorkDir")+
                        java.io.File.separator+GeneratorProperties.TagLibName;

                    writeFile(taglibName,createTLD.getTagLib());     
                    sendMessageToGUI("          "+taglibName+" created"); 
                    sendMessageToGUI("\n     </taglib>\n\n"); 
     
                    String jarFilename=properties.getProperty("jarFilename");
                    
                    if(jarFilename!=null&&jarFilename.trim().length()>0){
                        sendMessageToGUI("     <jar>\n"); 
                        CreateJar createJar=new CreateJar(properties);
                        sendMessageToGUI("          "+
                            properties.getProperty("jarFilename")+
                            " created\n"); 
                        sendMessageToGUI("     </jar>\n\n"); 
                    }
                }
            }
            else{
             sendMessageToGUI("          No tables found in "+schemaName+". ");
             sendMessageToGUI("Hint, the database metadata schema name could ");
             sendMessageToGUI("be mispelled or should\n     ");
             sendMessageToGUI("     be in uppercase.\n");
             sendMessageToGUI("     </tables>\n\n");
            }
      } 
      catch( SQLException e) {
        System.out.println("SQLError: "+e);
      }
      String endTime=Calendar.getInstance().getTime().toString();  
      sendMessageToGUI("     <runtime>\n"); 
      sendMessageToGUI("          Runtime: "+startTime+" - "+endTime);  
      sendMessageToGUI("\n     </runtime>\n"); 
     sendMessageToGUI("</"+outputTagName+">\n");

   }


   private void createFKTag(String tableName,CreateTLD createTLD){
       Enumeration enum=columnHash.keys();
       Vector optionalAttributes = new Vector();

       for(;enum.hasMoreElements();){
           String name=(String)enum.nextElement();
           optionalAttributes.add(name);
           optionalAttributes.add(name+"_SELECT");
           optionalAttributes.add(name+"_WHERE");
           optionalAttributes.add(name+"_BIND");
       }

       createTLD.initializeTLD(tableName,getPropertyValue("packageName"),optionalAttributes);  
    }

   private void load(String tableName){
      tableNameHash.clear();
      tableNameHash.put(tableName,tableName);
      loadProperties();
      load();
   }//load() ENDS

   private void loadProperties(){
         InputStream is=SQLTagsGeneratorTable.class.getResourceAsStream
            ("/"+getPropertiesFile());
          loadPropertiesStream(is);
   }
   
   private void loadPropertiesStream(InputStream is){
      try{
         properties = new Properties();
         if(is!=null && properties!=null)
            properties.load(is);
         else {
            System.out.println("Failed to load properties from "+
            getPropertiesFile());
         }
      }        
      catch( IOException e) {
         System.out.println("Error: "+e);
      }
   }

   /**
    * method: writeFile
    * usage: this method writes the generated java file to
    * disk.
    * @param fileName the name of the file were writing.
    * @param outputFile the actual data were writing. */   
   //---------------------------------------------------------------------------  
   void writeFile(String fileName, StringBuffer outputFile){
   //---------------------------------------------------------------------------  
      try{  
         java.io.FileWriter writer=new java.io.FileWriter(fileName);   
         writer.write((outputFile.toString()).toCharArray());  
         writer.close();  
      }  
      catch(IOException exception){  
         System.out.println("SQLTagsGeneratorTable.writeFile: "+exception);  
      }  
   }  //writeFile() ENDS
   
   //---------------------------------------------------------------------------  
   private synchronized boolean forkProcess(String cmd, String[] env){  
   //---------------------------------------------------------------------------  
      boolean errorsFound=false;  
  
      try{  
        Process proc=Runtime.getRuntime().exec(cmd,env);
        InputStream errorStream=proc.getErrorStream();
        InputStreamReader errorStreamReader=new InputStreamReader(errorStream);
        BufferedReader errorOutput=new BufferedReader(errorStreamReader);
        InputStream inputStream=proc.getInputStream();
        InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
        BufferedReader processOutput=new BufferedReader(inputStreamReader);        
        String line=null;
        StringBuffer errorBuffer=new StringBuffer();
        StringBuffer processOutputBuffer=new StringBuffer();
        
        while ((line=errorOutput.readLine())!=null)
            errorBuffer.append(line);
        
        while ((line=processOutput.readLine())!=null)
            processOutputBuffer.append(line);

        if(errorBuffer.length()>0){
            errorsFound=true;
            StringBuffer infoBuffer=new StringBuffer();
            int size=errorBuffer.length();
            sendMessageToGUI("          <errors>\n");
            sendMessageToGUI("               "+cmd+"\n");
            sendMessageToGUI("              "+errorBuffer.toString()+"\n");
                sendMessageToGUI("          </errors>\n");
            }
        
        if(processOutputBuffer.length()>0){
            sendMessageToGUI("          <output>\n");
            sendMessageToGUI("               "+errorBuffer.toString()+"\n");
            sendMessageToGUI("          </output>\n");
        }

        proc.waitFor();
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
  
      return errorsFound;  
   }//forkProcess() ENDS
   
   void sendMessageToGUI(String message){
      if(GeneratorGUI.getTextArea()!=null)
         GeneratorGUI.setTextArea(message);
      else
       System.out.println(message);
   }//sendMessageToGUI() ENDS

   //*************************************************************************** 
   // Class Variables
   //***************************************************************************
   private String tableName = null;
   private boolean haveTables=false;
   private String startTime="";
   private StringBuffer tld=new StringBuffer();
   private SQLTagsGeneratorTable classInstance;
   private Hashtable columnHash = new Hashtable();
   private Vector primaryKeyVector = new Vector();
   private Hashtable tableNameHash=new Hashtable();
   private Hashtable importedForeignKeyHash = new Hashtable();
   private Hashtable exportedForeignKeyHash = new Hashtable() ;
   private Properties properties = null;
   private String propertiesFile = "SQLTagsGeneratorTable.properties";
   private String schemaName=null;
   private String urlString = null;
   private String userName = null;
   private String password = null;
   private Connection connection=null;
   private DatabaseMetaData metaData = null;
   private String selectBindDefaults;
   private static String IGNORE1="SYSTEM_PRIVILEGE_MAP";
   private static String IGNORE2="MICROSOFTDTPROPERTIES";
   private static String IGNORE3="TABLE_PRIVILEGE_MAP";
   private static String IGNORE4="JK";
   private static String IGNORE5="DUAL";
   private static String IGNORE6="TEST_TAB";
   private static String IGNORE7="PSTUBTBL";
   private static String IGNORE8="AUDIT_ACTIONS";
   private static String IGNORE9="DR$";
   private static String IGNORE10="JUNK_TEMPLATES";
   private static String IGNORE11="STMT_AUDIT_OPTION_MAP";
}//SQLTagsGeneratorTable() ENDS