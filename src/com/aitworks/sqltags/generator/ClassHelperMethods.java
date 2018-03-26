/* $Id: ClassHelperMethods.java,v 1.8 2002/05/23 15:49:42 solson Exp $
 * $Log: ClassHelperMethods.java,v $
 * Revision 1.8  2002/05/23 15:49:42  solson
 * removed xml type defaulting feature
 *
 * Revision 1.7  2002/04/12 12:24:05  booker
 * Added Scrolling ability to the textarea.
 *
 * Revision 1.6  2002/04/10 17:39:10  booker
 * Added functionality to read in default values for
 * the column properties type fields.
 *
 * Revision 1.5  2002/04/03 14:49:59  booker
 * Worked on adding Object to ColumnProperties.
 *
 * Revision 1.4  2002/03/15 14:23:45  solson
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
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * <code>ClassHelperMethods</code>  
 * <p>  
 * This class is responsible for creating the base table class helper methods.
 * </p>  
 * @author  Booker Northington II  
 * @version 1.1
 * @since   1.3  
 * @param  <code>none</code>  
 * @return <code>none</code>
 */
public class ClassHelperMethods{
   //***************************************************************************  
   // Class Constructors  
   //***************************************************************************  
   /**  
    * <code>ClassHelperMethods</code>  
    * <p>  
    * Class constructor. Responsible for controlling creation of accessors.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param sqlTagsGeneratorTable<code>SQLTagsGeneratorTable</code>table data.
    */  
   //---------------------------------------------------------------------------
   public ClassHelperMethods(SQLTagsGeneratorTable sqlTagsGeneratorTable){
   //---------------------------------------------------------------------------
      this.sqlTagsGeneratorTable=sqlTagsGeneratorTable;
      this.tableName=sqlTagsGeneratorTable.getTableName();
      createClassHelperMethods();
   }// ClassHelperMethods() ENDS

   //***************************************************************************  
   // Finalize Method  
   //***************************************************************************  
   /**  
    * <code>finalize</code>  
    * <p>  
    * This method is called when the object is destroyed.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>none</code>  
    */  
   //---------------------------------------------------------------------------
	protected void finalize()throws Throwable{		
   //---------------------------------------------------------------------------
      super.finalize();
	}// finalize() ENDS

   //***************************************************************************  
   // Public Methods  
   //***************************************************************************  
   /**  
    * <code>main</code>  
    * <p>  
    * This method will create the class helper methods.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>none</code>  
    */  
   //---------------------------------------------------------------------------
	public static void main(String args[]){
   //---------------------------------------------------------------------------
     SQLTagsGeneratorTable sqlTagsGeneratorTable=new SQLTagsGeneratorTable();
      ClassHelperMethods classHelperMethods=
         new ClassHelperMethods(sqlTagsGeneratorTable);
      System.out.println(classHelperMethods.getClassHelperMethods());
	}//main() ENDS

   /**  
    * <code>toString</code>  
    * <p>  
    * This method returns the class structure.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code>  
    * @return  buff<code>String</code>class variable information.
    */  
   //---------------------------------------------------------------------------
	public String toString(){		
   //---------------------------------------------------------------------------
      StringBuffer buff=new StringBuffer("\n*****ClassHelperMethods: ");  
      buff.append("\tbuffer="+buffer.toString());  
      buff.append("\theaderDefinition="+header);  
      buff.append("\tsqlTagsGeneratorTable="+sqlTagsGeneratorTable);  
      buff.append("\ttableName="+tableName);  
      return buff.toString();  
	}// toString() 

   //***************************************************************************  
   // Friendly Methods  
   //***************************************************************************  
   /**  
    * <code>getClassHelperMethods</code>  
    * <p>  
    * This method returns the methods created. 
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>none</code>  
    */  
   //---------------------------------------------------------------------------
	String getClassHelperMethods(){
   //---------------------------------------------------------------------------
      return buffer.toString();
	}//getClassHelperMethods() ENDS


   //***************************************************************************  
   // Private Methods
   //***************************************************************************  
   /**  
    * <code>createClassSQLContract</code>  
    * <p>  
    * this method controls the creation of the helper methods.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code>
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------
	private void createClassHelperMethods(){
   //---------------------------------------------------------------------------
      buffer=new StringBuffer();
      createInitialize();
      createInitializeColumnPropertiesHash();
      createInitializeHashtables();
      createInitializePrimaryKeyVector();
 	}//createClassHelperMethods() ENDS

   /**  
    * <code>createInitialize</code>  
    * <p>  
    * This method creates the initializet method.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code>
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------
   private void createInitialize(){
   //---------------------------------------------------------------------------
      Enumeration enum=sqlTagsGeneratorTable.getPrimaryKeys();
      StringBuffer primaryKeyList=new StringBuffer();
      StringBuffer ifString=new StringBuffer();
      StringBuffer columnValueBuffer=new StringBuffer();

      for(;enum.hasMoreElements();){
         String name=(String)enum.nextElement();
         primaryKeyList.append("String "+name+",");
         ifString.append(name+".equals(\"\")||");
         columnValueBuffer.append("setColumnValue(\""+name+"\","+name+");\n");
      }

      if(primaryKeyList.toString().trim().length()>0){
      buffer.append("   /**\n");
      buffer.append("    * <code>initialize</code>\n");
      buffer.append("    * <p>\n");
      buffer.append("    * This initialize method creates an instance ");
      buffer.append("of "+tableName.toLowerCase()+" based on the \n");
      buffer.append("    * primary key and the depth.\n");
      buffer.append("    * </p>\n");
      buffer.append("    * @author  Booker Northington II\n");
      buffer.append("    * @version 1.0\n");
      buffer.append("    * @since   1.3\n");
      buffer.append("    * @param depth <code>int</code> The number of ");
      buffer.append("layers to be initialize.\n");
      buffer.append("    * @return none <code></code> \n");
      buffer.append("    */\n");
      buffer.append(spacer);
      buffer.append("   private void initialize("+primaryKeyList.toString());
      buffer.append("int depth){\n");
      buffer.append(spacer);
      buffer.append("      if("+ifString.toString()+"depth<0){\n");
      buffer.append("         new "+tableName+"();\n");
      buffer.append("         return;\n");
      buffer.append("      }\n\n");
      buffer.append("      "+columnValueBuffer.toString());
      buffer.append("      initialize(depth);\n");
      buffer.append("   }// initialize() ENDS\n\n");
      }
   }//createInitialize() ENDS

   /**  
    * <code>createInitializeColumnPropertiesHash</code>  
    * <p>  
    * This method creates the initializeColumnPropertiesHash method.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code>
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------
   private void createInitializeColumnPropertiesHash(){
   //---------------------------------------------------------------------------
      buffer.append("   /**\n");
      buffer.append("    * <code>initializeColumnPropertiesHash</code>\n");
      buffer.append("    * <p>\n");
      buffer.append("    * This method holds all the information about ");
      buffer.append("a column.\n");
      buffer.append("    * </p>\n");
      buffer.append("    * @author  Booker Northington II\n");
      buffer.append("    * @version 1.0\n");
      buffer.append("    * @since   1.3\n");
      buffer.append("    * @param none <code>none</code> none.\n");
      buffer.append("    * @return none <code>none</code> none.\n");
      buffer.append("    */\n");
      buffer.append(spacer);
      buffer.append("   private void initializeColumnPropertiesHash(){\n");
      buffer.append(spacer);

      Hashtable columnHash=sqlTagsGeneratorTable.getColumnHash();
      Enumeration enum=columnHash.keys();
      
      for(;enum.hasMoreElements();){
         String[] bindArray=null;
         Object key=enum.nextElement();
         SQLTagsGeneratorColumn column=(SQLTagsGeneratorColumn)
            columnHash.get(key);
         String name=column.getColumnName();
         String type=column.getDataType();
         String size=column.getSize()+"";
         String bindFormat="?";
         String selectFormat=name;
         buffer.append("      columnPropertiesHash.put(\""+name+"\",\n");
         buffer.append("         new ColumnProperties(\""+name+"\",\"");
         boolean selectFormatChanged=false;
/*      
         if(BindReader.bindContent!=null){
             bindArray=BindReader.get(type);
             
             if(bindArray!=null){
                if(bindArray[0].length()>0)
                    bindFormat=bindArray[0];
                
                if(bindArray[1].length()>0){
                    selectFormat=bindArray[1];
                    int start=selectFormat.indexOf("![]!");
                    StringBuffer buf=new StringBuffer(selectFormat);

                    if(start>-1){
                        buf.replace(start,start+4,name);
                        start=buf.toString().indexOf("![]!");
                        
                        while(start>-1){
                           buf.replace(start,start+4,name);
                           start=buf.toString().indexOf("![]!");
                        }
                  }
                   
                   selectFormat=buf.toString();
                   selectFormatChanged=true;
               }
             }
         }
 */
         buffer.append(size+"\",\n            \""+selectFormat+"\",\""+type);
 
         if(!selectFormatChanged)
            buffer.append("\",\""+bindFormat+"\",\"\",new Object()));\n\n");
         else
            buffer.append("\",\n\t\t\t\t\""+bindFormat+"\",\"\",new Object()));\n\n");
      }

      buffer.append("   }//initializeColumnPropertiesHash() ENDS}\n\n");
   }//createInitializeColumnPropertiesHash() ENDS


   /**  
    * <code>createInitializeHashtables</code>  
    * <p>  
    * This method creates the initializeHashtables() method
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code>
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------
   private void createInitializeHashtables(){
   //---------------------------------------------------------------------------
      buffer.append("   /**\n");
      buffer.append("    * <code>initializeHashTables</code>\n");
      buffer.append("    * <p>\n");
      buffer.append("    * This method initializes all the hashtables ");
      buffer.append("associated with this class.\n");
      buffer.append("    * </p>\n");
      buffer.append("    * @author  Booker Northington II\n");
      buffer.append("    * @version 1.0\n");
      buffer.append("    * @since   1.3\n");
      buffer.append("    * @param none <code>none</code> none.\n");
      buffer.append("    * @return none <code>none</code> none\n");
      buffer.append("    */\n");
      buffer.append(spacer);
      buffer.append("   private void initializeHashTables(){\n");
      buffer.append(spacer);
      buffer.append("      initializeColumnPropertiesHash();\n");
      buffer.append("      initializePrimaryKeyVector();\n");
      buffer.append("   }// initializeHashTables() ENDS\n\n");
   }//createInitializeHashtables() ENDS



   /**  
    * <code>createInitializePrimaryKeyVector</code>  
    * <p>  
    * This method creates the initializeHashtables() method
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code>
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------
   private void createInitializePrimaryKeyVector(){
   //---------------------------------------------------------------------------
      Enumeration enum=sqlTagsGeneratorTable.getPrimaryKeys();
      buffer.append("   /**\n");
      buffer.append("    * <code>initializePrimaryKeyVector</code>\n");
      buffer.append("    * <p>\n");
      buffer.append("    * This method initializes a vector which contains\n");
      buffer.append("    * the primary keys for this table.\n");
      buffer.append("    * </p>\n");
      buffer.append("    * @author  Booker Northington II\n");
      buffer.append("    * @version 1.0\n");
      buffer.append("    * @since   1.3\n");
      buffer.append("    * @param none <code></code> \n");
      buffer.append("    * @return none <code>none</code> none.\n");
      buffer.append("    */\n");
      buffer.append(spacer);
      buffer.append("   private void initializePrimaryKeyVector(){\n");
      buffer.append(spacer);

      for(;enum.hasMoreElements();){
         String name=(String)enum.nextElement();
         buffer.append("      primaryKeyVector.add(\""+name+"\");\n");
      }

      buffer.append("   }// initializePrimaryKeyVector() ENDS\n\n");
   }//createInitializePrimaryKeyVector() ENDS

   //***************************************************************************  
   // Class Variables
   //***************************************************************************  
   private StringBuffer buffer;
	private HeaderDefinition header;
   private String spacer="   //---------------------------------------------------------------------------\n";  
	private SQLTagsGeneratorTable sqlTagsGeneratorTable;
   private String tableName;
}// ClassHelperMethods ENDS
