/* $Id: ClassFKWhere.java,v 1.5 2002/03/16 03:01:58 solson Exp $
 * $Log: ClassFKWhere.java,v $
 * Revision 1.5  2002/03/16 03:01:58  solson
 * removed keyBuf debug output
 *
 * Revision 1.4  2002/03/15 14:23:44  solson
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
 * <code>ClassFKWhere</code>  
 * <p>  
 * This class is responsible for creating the foreign key wheremethods.
 * </p>  
 * @author  Booker Northington II  
 * @version 1.1
 * @since   1.3  
 * @param  <code>none</code>  
 * @return <code>none</code>
 */
public class ClassFKWhere{
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
   public ClassFKWhere(SQLTagsGeneratorTable sqlTagsGeneratorTable){
   //---------------------------------------------------------------------------
      this.sqlTagsGeneratorTable=sqlTagsGeneratorTable;
      this.tableName=sqlTagsGeneratorTable.getTableName();
      createClassFKWhere();
   }// ClassFKWhere() ENDS

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
      SQLTagsGeneratorTable sqlTagsGeneratorTable=new SQLTagsGeneratorTable("TMP_3","generator.properties");
      ClassFKWhere classFKWhere=new ClassFKWhere(sqlTagsGeneratorTable); 
      System.out.println(classFKWhere.getClassFKWhere());
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
    * <code>getClassFKWhere</code>  
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
	String getClassFKWhere(){		
   //---------------------------------------------------------------------------
      return buffer.toString();
	}// getClassFKWhere() ENDS

   //***************************************************************************  
   // Private Methods  
   //***************************************************************************  
   /**  
    * <code>createClassFKWhere</code>  
    * <p>  
    * This method returns creates the _WHERE methods
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return buffer <code>String</code> the class accessor methods.  
    */  
   //---------------------------------------------------------------------------
	private void createClassFKWhere(){
   //---------------------------------------------------------------------------

      Enumeration eKeys = sqlTagsGeneratorTable.getExportedForeignKeys();
      for( ;eKeys.hasMoreElements(); ) {
          String fkName = (String) eKeys.nextElement();
          // System.out.println("FK="+ fkName);
          SQLTagsGeneratorForeignKey fk = sqlTagsGeneratorTable.getExportedForeignKey(fkName);
          Enumeration iCols = fk.getImportColumnNames();
          StringBuffer keyBuffer = new StringBuffer();
          String joinOperator = " ";
          for( ;iCols.hasMoreElements(); ){
              String iCol = (String) iCols.nextElement();
              String eCol = (String) fk.getExportColumnName(iCol);
              keyBuffer.append("\" "+joinOperator +iCol+" = '\"+get"+eCol+"()+\"'\"+");
              joinOperator=" and ";
              // System.out.println("Col="+ iCol+" eCol=" +eCol);
          }
          keyBuffer.setLength(keyBuffer.length()-1);
          // System.out.println("keyBuf="+ keyBuffer.toString());


      /*
      Hashtable table=sqlTagsGeneratorTable.getExportedForeignKeyHash();
      Enumeration enum=table.keys();
      buffer=new StringBuffer();

      for(;enum.hasMoreElements();){
         String fkName=(String)enum.nextElement();
         Vector primaryKeyVector=sqlTagsGeneratorTable.getPrimaryKeyVector();
         int vectorSize=primaryKeyVector.size();
         StringBuffer keyBuffer=new StringBuffer();

         for(int index=0;index<vectorSize;index++){
            String key=(String)primaryKeyVector.get(index);
            keyBuffer.append("\""+key+" = '\"+get"+key+"()+\"'\"+\" \"+");
         }
keyBuffer.setLength(keyBuffer.length()-1);
       */

         for(int count=0;count<2;count++){
            buffer.append("   /**\n");
            buffer.append("    * <code>get"+fkName+"_WHERE</code>\n");
            buffer.append("    * <p>\n");
            buffer.append("    * This method returns the where clause.\n");
            buffer.append("    * </p>\n");
            buffer.append("    * @author  Booker Northington II\n");
            buffer.append("    * @version 1.0\n");
            buffer.append("    * @since   1.3\n");
            buffer.append("    * @param none <code></code> \n");
            buffer.append("    * @return where <code>String</code> ");
            buffer.append("The where clause.\n");
            buffer.append("    */\n");
            buffer.append(spacer);

            if(count!=0)
               buffer.append("   public String get"+fkName+
                  "_WHERE(String string){\n");
            else
               buffer.append("   public String get"+fkName+"_WHERE(){\n");

            buffer.append(spacer);

            if(count!=0)
               buffer.append("      return (\"where \"+"+
                  keyBuffer.toString()+"+string);\n");
            else
               buffer.append("      return (\"where \"+"+
                  keyBuffer.toString()+");\n");

            buffer.append("   }// get"+fkName+"_WHERE() ENDS\n\n");
        }
      }
	}//createClassFKWhere() ENDS

   //***************************************************************************  
   // Class Variables   
   //***************************************************************************  
	private StringBuffer buffer=new StringBuffer();;
   private String spacer="   //---------------------------------------------------------------------------\n";  
	private SQLTagsGeneratorTable sqlTagsGeneratorTable;
   private String tableName;
}// ClassFKWhere() ENDS
