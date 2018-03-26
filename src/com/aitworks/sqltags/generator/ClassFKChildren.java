/* $Id: ClassFKChildren.java,v 1.6 2002/08/12 16:48:23 jpoon Exp $
 * $Log: ClassFKChildren.java,v $
 * Revision 1.6  2002/08/12 16:48:23  jpoon
 * change getTagConnection to getConnection
 *
 * Revision 1.5  2002/03/15 14:23:45  solson
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
import java.util.Vector;

/**
 * <code>ClassFKChildren</code>  
 * <p>  
 * This class is responsible for creating the foreign key children
 * methods.
 * </p>  
 * @author  Booker Northington II  
 * @version 1.1
 * @since   1.3  
 * @param  <code>none</code>  
 * @return <code>none</code>
 */
public class ClassFKChildren{
   //***************************************************************************  
   // Class Constructors  
   //***************************************************************************  
   /**  
    * <code>ClassFKChildren</code>  
    * <p>  
    * Class constructor. Responsible for controlling creation of the
    * _CHILDREN methods.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param sqlTagsGeneratorTable<code>SQLTagsGeneratorTable</code>table data.
    */  
   //---------------------------------------------------------------------------
   public ClassFKChildren(SQLTagsGeneratorTable sqlTagsGeneratorTable){
   //---------------------------------------------------------------------------
      this.sqlTagsGeneratorTable=sqlTagsGeneratorTable;
      this.tableName=sqlTagsGeneratorTable.getTableName();
      createClassFKChildren();
   }// ClassFKChildren() ENDS

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
      ClassFKChildren classFKChildren=
         new ClassFKChildren(sqlTagsGeneratorTable);
      System.out.println(classFKChildren.getClassFKChildren());
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
      buff.append("\tsqlTagsGeneratorTable="+sqlTagsGeneratorTable);  
      buff.append("\ttableName="+tableName);  
      return buff.toString();  
	}// toString() 

   //***************************************************************************  
   // Friendly Methods  
   //***************************************************************************  
   /**  
    * <code>getClassFKChildren</code>  
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
	String getClassFKChildren(){		
   //---------------------------------------------------------------------------
      return buffer.toString();
	}// getClassFKChildren() ENDS

   //***************************************************************************  
   // Private Methods  
   //***************************************************************************  
   /**  
    * <code>createClassFKChildren</code>  
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
	private void createClassFKChildren(){
   //---------------------------------------------------------------------------
      Hashtable table=sqlTagsGeneratorTable.getExportedForeignKeyHash();
      Enumeration enum=table.keys();
      buffer=new StringBuffer();
      String importTable="";

      for(;enum.hasMoreElements();){
         String fkName=(String)enum.nextElement();
         Vector primaryKeyVector=sqlTagsGeneratorTable.getPrimaryKeyVector();
         int vectorSize=primaryKeyVector.size();
         StringBuffer keyBuffer=new StringBuffer();
         SQLTagsGeneratorForeignKey object=(SQLTagsGeneratorForeignKey)
            table.get(fkName);
         importTable=object.getImportTableName();
         createNoArgumentMethod(fkName,importTable);
         createOneArgumentMethod(fkName,importTable);
      }
	}//createClassFKChildren() ENDS

   private void createNoArgumentMethod(String fkName,String importTable){
      buffer.append("   /**\n");
      buffer.append("    * <code>get"+fkName+"_CHILDREN</code>\n");
      buffer.append("    * <p>\n");
      buffer.append("    * This method is responsible for creating a \n");
      buffer.append("    * child instance of the "+importTable+" table.\n");
      buffer.append("    * </p>\n");
      buffer.append("    * @author  Booker Northington II\n");
      buffer.append("    * @version 1.0\n");
      buffer.append("    * @since   1.3\n");
      buffer.append("    * @param none <code></code> \n");
      buffer.append("    * @return "+importTable.toLowerCase());
      buffer.append(" <code>"+importTable+"</code>");
      buffer.append(" an instacne of the "+importTable+" table.\n");
      buffer.append("    */\n");
      buffer.append(spacer);
      buffer.append("   public "+importTable+" get"+fkName);
      buffer.append("_CHILDREN(){\n");
      buffer.append(spacer);
      buffer.append("      return get"+fkName+"_CHILDREN(\"\");\n");
      buffer.append("   }// get"+fkName+"_CHILDREN() ENDS\n\n");
   }//createNoArgumentMethod() ENDS

   private void createOneArgumentMethod(String fkName,String importTable){
         buffer.append("   /**\n");
         buffer.append("    * <code>get"+fkName+"_CHILDREN</code>\n");
         buffer.append("    * <p>\n");
         buffer.append("    * This method is responsible for creating a \n");
         buffer.append("    * child instance of the "+importTable+" table.\n");
         buffer.append("    * This method takes a order by clause.\n");
         buffer.append("    * </p>\n");
         buffer.append("    * @author  Booker Northington II\n");
         buffer.append("    * @version 1.0\n");
         buffer.append("    * @since   1.3\n");
         buffer.append("    * @param orderBy <code>String</code>order clause\n");
         buffer.append("    * @return "+importTable.toLowerCase());
         buffer.append(" <code>"+importTable+"</code>");
         buffer.append(" an instacne of the "+importTable+" table.\n");
         buffer.append("    */\n");
         buffer.append(spacer);
         buffer.append("   public "+importTable+" get"+fkName);
         buffer.append("_CHILDREN(String orderBy){\n");
         buffer.append(spacer);
         buffer.append("      "+fkName+"_CHILD=new "+importTable+"();\n");
         buffer.append("      "+fkName+"_CHILD.setConnectionTag(connectionTag);\n");
         buffer.append("      "+fkName+"_CHILD.select(get"+fkName+"_WHERE");
         buffer.append("(orderBy));\n\n");
         buffer.append("      return "+fkName+"_CHILD;\n");
         buffer.append("   }// get"+fkName+"_CHILDREN() ENDS\n\n");
   }//createOneArgumentMethod() ENDS

   //***************************************************************************  
   // Class Variables   
   //***************************************************************************  
	private StringBuffer buffer=new StringBuffer();;
   private String spacer="   //---------------------------------------------------------------------------\n";  
	private SQLTagsGeneratorTable sqlTagsGeneratorTable;
   private String tableName;
}// ClassFKChildren() ENDS