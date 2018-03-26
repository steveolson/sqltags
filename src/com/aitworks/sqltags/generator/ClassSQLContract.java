/* $Id: ClassSQLContract.java,v 1.3 2002/03/15 14:23:45 solson Exp $
 * $Log: ClassSQLContract.java,v $
 * Revision 1.3  2002/03/15 14:23:45  solson
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
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * <code>ClassSQLContract</code>  
 * <p>  
 * This class is responsible for signing the contract of the SQLTags abstract class.
 * </p>  
 * @author  Booker Northington II  
 * @version 1.01 
 * @since   1.3  
 * @param  <code>none</code>  
 * @return <code>none</code>
 */
public class ClassSQLContract{
   //***************************************************************************  
   // Class Constructors  
   //***************************************************************************  
   /**  
    * <code>ClassFKParent</code>  
    * <p>  
    * Class constructor. Responsible for controlling creation of accessors.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param sqlTagsGeneratorTable<code>SQLTagsGeneratorTable</code>table data.
    */  
   //---------------------------------------------------------------------------
   public ClassSQLContract(SQLTagsGeneratorTable sqlTagsGeneratorTable){
   //---------------------------------------------------------------------------
      this.sqlTagsGeneratorTable=sqlTagsGeneratorTable;
      this.tableName=sqlTagsGeneratorTable.getTableName();
      createClassSQLContract();
   }// ClassAccessor() ENDS

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
	public static void main(String args[]){
      SQLTagsGeneratorTable sqlTagsGeneratorTable=new SQLTagsGeneratorTable();
      ClassSQLContract classSQLContract=
         new ClassSQLContract(sqlTagsGeneratorTable);
      System.out.println(classSQLContract.getClassSQLContract());
	}

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
      StringBuffer buff=new StringBuffer("\n*****ClassSQLContract: ");  
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
    * <code>getClassSQLContract</code>  
    * <p>  
    * This method returns the methods created. 
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>none</code>  
    */  
	String getClassSQLContract(){
      return buffer.toString();
	}//getClassSQLContract() ENDS


   //***************************************************************************  
   // Private Methods
   //***************************************************************************  
   /**  
    * <code>createClassSQLContract</code>  
    * <p>  
    * this method creates the methods which each table must implement
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code>
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------
	private void createClassSQLContract(){
   //---------------------------------------------------------------------------
      buffer=new StringBuffer();
      createGetColumnPropertiesHash();
      createGetPrimaryKeyVector();
      createGetTableName();
      createSetFKParent();
	}

   /**  
    * <code>createGetColumnPropertiesHash</code>  
    * <p>  
    * this method creates the getColumnPropertiesHash method.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code>
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------
   private void createGetColumnPropertiesHash(){
   //---------------------------------------------------------------------------
       buffer.append("   /**\n");
       buffer.append("    * <code>getColumnPropertiesHash</code>\n");
       buffer.append("    * <p>\n");
       buffer.append("    * This method will return a hash table. The hashtable will contain the name\n");
       buffer.append("    * of the columns in the table. Each column will have an associated ColumnProperties\n");
       buffer.append("    *o bject which contains the columns values.\n");
       buffer.append("    * </p>\n");
       buffer.append("    * @author  Booker Northington II\n");
       buffer.append("    * @version 1.0\n");
       buffer.append("    * @since   1.3\n");
       buffer.append("    * @param none <code></code>\n"); 
       buffer.append("    * @see ColumnProperties <code>ColumnProperties</code>\n");
       buffer.append("    * @return columnPropertiesHash <code>Hashtable</code> A hash table\n");
       buffer.append("    *       of columns.\n");
       buffer.append("    */\n");
       buffer.append(spacer);
       buffer.append("   public Hashtable getColumnPropertiesHash(){\n");
       buffer.append(spacer);
       buffer.append("      return columnPropertiesHash;\n");
       buffer.append("   }//getColumnPropertiesHash() ENDS\n\n");
   }//createGetColumnPropertiesHash() ENDS

   /**  
    * <code>createGetPrimaryKeyVector</code>  
    * <p>  
    * This method creates the getPrimaryKeyVector method.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code>
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------
   private void createGetPrimaryKeyVector(){
   //---------------------------------------------------------------------------
      buffer.append("   /**\n");
      buffer.append("    * <code>getPrimaryKeyVector</code>\n");
      buffer.append("    * <p>\n");
      buffer.append("    * This method return the primary key vector.\n");
      buffer.append("    * </p>\n");
      buffer.append("    * @author  Booker Northington II\n");
      buffer.append("    * @version 1.0\n");
      buffer.append("    * @since   1.3\n");
      buffer.append("    * @param none <code></code> \n");
      buffer.append("    * @return primaryKeyVector <code>Vector</code> ");
      buffer.append(" A vector of primary\n");
      buffer.append("    *        keys.\n");
      buffer.append("    */\n");
      buffer.append(spacer);
      buffer.append("   public Vector getPrimaryKeyVector(){\n");
      buffer.append(spacer);
      buffer.append("      return primaryKeyVector;\n");
      buffer.append("   }//getPrimaryKeyVector() ENDS\n\n");
   }//getCreatePrimaryKeyVector() ENDS

   /**  
    * <code>createGetTableName</code>  
    * <p>  
    * This method creates the getTableName method.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code>
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------
   private void createGetTableName(){
   //---------------------------------------------------------------------------
      buffer.append("   /**\n");
      buffer.append("    * <code>getTableName</code>\n");
      buffer.append("    * <p>\n");
      buffer.append("    * This method return the current tablename.\n");
      buffer.append("    * </p>\n");
      buffer.append("    * @author  Booker Northington II\n");
      buffer.append("    * @version 1.0\n");
      buffer.append("    * @since   1.3\n");
      buffer.append("    * @return tableName <code>String</code> the name ");
      buffer.append("of this table.\n");
      buffer.append("    */\n");
      buffer.append(spacer);
      buffer.append("   public String getTableName(){\n");
      buffer.append(spacer);
      buffer.append("      return tableName;\n");
      buffer.append("   }//getTableName() ENDS\n\n");
   }//createGetTableName() ENDS


   /**  
    * <code>createSetFKParent</code>  
    * <p>  
    * This method creates the setFKParent method.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code>
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------
   private void createSetFKParent(){
      Hashtable table=sqlTagsGeneratorTable.getImportedForeignKeyHash();
      Enumeration enum=table.keys();

      buffer.append("   /**\n");
      buffer.append("    * The <b><code>setFKParent</code></b> method is ");
      buffer.append("used to initializes Foreign\n");
      buffer.append("    * Key columns within the HCYP_EVENTS table.\n");
      buffer.append("    * @author  Booker Northington II\n");
      buffer.append("    * @version 1.0\n");
      buffer.append("    * @param   depth how many levels to initialize.\n");
      buffer.append("    * @return  none\n");
      buffer.append("    * @since   JDK1.3\n");
      buffer.append("    */\n");
      buffer.append(spacer);
      buffer.append("   public void setFKParent(int depth){\n");
      buffer.append(spacer);
      buffer.append("      if(depth>0){\n");

      for(;enum.hasMoreElements();){
         String key=(String)enum.nextElement();
         SQLTagsGeneratorForeignKey object=(SQLTagsGeneratorForeignKey)
            table.get(key);
         String fkName=object.getFkName();
         buffer.append("         "+fkName+"_PARENT=get"+fkName+"_PARENT");
         buffer.append("(depth-1);\n");
      }
      buffer.append("      }\n");
      buffer.append("      return;\n");
      buffer.append("   }// setFKParent() ENDS\n");
   }//createSetFKParent() ENDS

   //***************************************************************************  
   // Class Variables
   //***************************************************************************  
	private StringBuffer buffer;
	private HeaderDefinition header;
   private String spacer="   //---------------------------------------------------------------------------\n";  
	private SQLTagsGeneratorTable sqlTagsGeneratorTable;
   private String tableName;
}// END CLASS DEFINITION ClassSQLContract
