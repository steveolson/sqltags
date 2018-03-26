/* $Id: ClassObjectHeader.java,v 1.5 2002/04/03 14:49:59 booker Exp $
 * $Log: ClassObjectHeader.java,v $
 * Revision 1.5  2002/04/03 14:49:59  booker
 * Worked on adding Object to ColumnProperties.
 *
 * Revision 1.4  2002/03/15 14:23:46  solson
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
/** 
 * <code>ClassObjectHeader</code>  
 * <p>  
 * This class is responsible for creating the method headers.
 * </p>  
 * @author  Booker Northington II  
 * @version 1.1
 * @since   1.3  
 * @param  <code>none</code>  
 * @return <code>none</code>
 */
public class ClassObjectHeader{
   //***************************************************************************  
   // Class Constructors  
   //***************************************************************************  
   /**  
    * <code>ClassObjectHeader</code>  
    * <p>  
    * Class constructor. Responsible for controlling creation of the
    * _WHERE methods.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param sqlTagsGeneratorTable<code>SQLTagsGeneratorTable</code>table data.
    */  
   //---------------------------------------------------------------------------
   public ClassObjectHeader(SQLTagsGeneratorTable sqlTagsGeneratorTable,
                            boolean tagOrBaseTable){
   //---------------------------------------------------------------------------
      this.tagOrBaseTable=tagOrBaseTable;
      sqlTagsGeneratorTable=sqlTagsGeneratorTable;
      tableName=sqlTagsGeneratorTable.getTableName();
      packageName=sqlTagsGeneratorTable.getPropertyValue("packageName")+";\n";
      createClassObjectHeader();
   }// ClassObjectHeader() ENDS

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
    * This method is used for testing and stand-alone fuctionality.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param args <code>String[]</code> command line arguments.  
    * @return  <code>none</code>
    */  
   //---------------------------------------------------------------------------
	public static void main(String[] args){		
   //---------------------------------------------------------------------------
      SQLTagsGeneratorTable sqlTagsGeneratorTable=new SQLTagsGeneratorTable();
      ClassObjectHeader classObjectHeader=
         new ClassObjectHeader(sqlTagsGeneratorTable,true);
      System.out.println(classObjectHeader.getClassObjectHeader());
	}// main() ENDS

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
      StringBuffer buff=new StringBuffer("\n*****ClassFKParent: ");  
      buff.append("\tbuffer="+buffer.toString());  
      buff.append("\tclassTableName="+classTableName);  
      buff.append("\textension="+extension);  
      buff.append("\tpackageName="+packageName);  
      buff.append("\tsqlTagsGeneratorTable="+sqlTagsGeneratorTable);  
      buff.append("\ttableName="+tableName);  
      buff.append("\ttagOrBaseTable="+tagOrBaseTable);  
      return buff.toString();  
	}// toString() 

   //***************************************************************************  
   // Friendly Methods  
   //***************************************************************************  
   /**  
    * <code>getClassObjectHeader</code>  
    * <p>  
    * This method returns the methods which were created.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param args <code>/code> command line arguments.  
    * @return  buffer<code>String</code>The methods created
    */  
   //---------------------------------------------------------------------------
	String getClassObjectHeader(){		
   //---------------------------------------------------------------------------
      return buffer.toString();
	}// getClassObjectHeader() ENDS

   //***************************************************************************  
   // Private Methods  
   //***************************************************************************  
   /**  
    * <code>createClassObjectHeader</code>  
    * <p>  
    * This method returns creates the _CHILDREN methods
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return buffer <code>String</code> the class accessor methods.  
    */  
   //---------------------------------------------------------------------------
	private void createClassObjectHeader(){
   //---------------------------------------------------------------------------
      buffer.append("package ");
      buffer.append(packageName);
      classTableName=tableName;
      extension="SQLTagsTag";

      if(tagOrBaseTable)
         setTablePackageImport();
      else{ 
         classTableName=tableName+"_TAG";
         extension=tableName;
         setTagPackageImport();
      }

      buffer.append("\n/**\n");
      buffer.append(" * <code>"+tableName+"</code>\n");
      buffer.append(" * <p>\n");
      buffer.append(" * The "+tableName+" table is used to interact with a ");
      buffer.append("database and a JSP page.\n");
      buffer.append(" * </p>\n");
      buffer.append(" * @author  Booker Northington II\n");
      buffer.append(" * @version 1.0\n");
      buffer.append(" * @since   1.3\n");
      buffer.append(" * @param none <code>none</code> none.\n");
      buffer.append(" * @return none <code>none</code> none.\n");
      buffer.append(" */\n");
      buffer.append("public class "+classTableName+" extends "+extension+"{\n");

      if(!tagOrBaseTable){
         buffer.append("   private boolean connectionValid=true;\n");
         buffer.append("   protected String id=\"\";\n");
         buffer.append("   protected Object resultBean=null;\n");
         buffer.append("   protected boolean load=false;\n\n");
      }
	}//createClassObjectHeader() ENDS

   /**  
    * <code>setTablePackageImport</code>  
    * <p>  
    * This method sets the package and import lines for the base tables.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return <code>none</code>
    */  
   //---------------------------------------------------------------------------
   private void setTablePackageImport(){
   //---------------------------------------------------------------------------
      buffer.append("import com.aitworks.sqltags.utilities.*;\n");
      buffer.append("import com.aitworks.sqltags.jsptags.*;\n");
      buffer.append("import java.util.*;\n");
      buffer.append("import java.io.*;\n");
      buffer.append("import java.sql.*;\n");
      buffer.append("import javax.servlet.*;\n");
      buffer.append("import javax.servlet.http.*;\n");
      buffer.append("import javax.servlet.jsp.*;\n");
      buffer.append("import javax.servlet.jsp.tagext.*;\n");
   }// setTablePackageImport() ENDS

   /**  
    * <code>setTagPackageImport</code>  
    * <p>  
    * This method sets the package and import lines for the tag tables.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return <code>none</code>
    */  
   //---------------------------------------------------------------------------
   private void setTagPackageImport(){
   //---------------------------------------------------------------------------
      buffer.append("import com.aitworks.sqltags.utilities.*;\n");
      buffer.append("import java.io.*;\n");
      buffer.append("import java.sql.*;\n");
      buffer.append("import com.aitworks.sqltags.jsptags.*;\n");
      buffer.append("import javax.servlet.jsp.tagext.*;\n");
   }// setTagPackageImport() ENDS

   //***************************************************************************  
   // Class Variables   
   //***************************************************************************  
	private StringBuffer buffer=new StringBuffer();;
	private String classTableName;
	private String extension;
	private String packageName;
   private String section="   //********************************"+
                          "*******************************************\n";
   private String spacer="   //---------------------------------------------"+
                         "------------------------------\n";  
	private SQLTagsGeneratorTable sqlTagsGeneratorTable;
   private boolean tagOrBaseTable;
   private String tableName;
}//ClassObjectHeader() ENDS
