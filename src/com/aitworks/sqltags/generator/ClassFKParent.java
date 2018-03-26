/* $Id: ClassFKParent.java,v 1.7 2002/09/09 13:43:50 solson Exp $
 * $Log: ClassFKParent.java,v $
 * Revision 1.7  2002/09/09 13:43:50  solson
 * to cahce or not to cache FK objects ... that is the question
 *
 * Revision 1.6  2002/09/09 04:23:30  solson
 * changed get FK_Parent's to set the local FK variable upon query of FK
 *
 * Revision 1.5  2002/04/23 20:40:47  booker
 * Removed check for null within fkParent(depth) method.
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
import java.util.Enumeration;
import java.util.Hashtable;
/**
 * <code>ClassFKParent</code>  
 * <p>  
 * This class is responsible for creating the foreign key parent methods.
 * </p>  
 * @author  Booker Northington II  
 * @version 1.1  
 * @since   1.3  
 * @param  <code>none</code>  
 * @return <code>none</code>\
 */
public class ClassFKParent{
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
   public ClassFKParent(SQLTagsGeneratorTable sqlTagsGeneratorTable){
   //---------------------------------------------------------------------------
      this.sqlTagsGeneratorTable=sqlTagsGeneratorTable;
      this.tableName=sqlTagsGeneratorTable.getTableName();
      createClassFKParent();
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
	protected void finalize(){		
   //---------------------------------------------------------------------------
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
      ClassFKParent classFKParent=new ClassFKParent(sqlTagsGeneratorTable);
      System.out.println(classFKParent.getClassFKParent());
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
      buff.append("\theaderDefinition="+header);  
      buff.append("\tsqlTagsGeneratorTable="+sqlTagsGeneratorTable);  
      buff.append("\ttableName="+tableName);  
      return buff.toString();  
	}// toString() 

   //***************************************************************************  
   // Friendly Methods  
   //***************************************************************************  
   /**  
    * <code>getClassFKParent</code>  
    * <p>  
    * This method returns the class accessor methods 
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return buffer <code>String</code> the class accessor methods.  
    */  
   //---------------------------------------------------------------------------
	String getClassFKParent(){		
   //---------------------------------------------------------------------------
      buffer.append(getFKMethodBuffer.toString());
      buffer.append(setFKMethodBuffer.toString());
      return buffer.toString();
	}// getClassAccessor() ENDS

   //***************************************************************************  
   // Private Methods  
   //***************************************************************************  
   /**  
    * <code>createClassFKParent</code>  
    * <p>  
    * This method creates the FK_PARENT methods.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code> 
    * @return  <code>none</code>
    */  
   //---------------------------------------------------------------------------
	private void createClassFKParent(){		
   //---------------------------------------------------------------------------
      Enumeration enum=sqlTagsGeneratorTable.getColumns();
      //here we get the foreign key columns.
      createFKParentAccessors();
      createFKParentMutators();
   }//createClassFKParent() ENDS

   /**  
    * <code>createFKParentAccessors</code>  
    * <p>  
    * This method creates the fk parent accessor methods.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code> 
    * @return  <code>none</code>
    */  
   //---------------------------------------------------------------------------
	private void createFKParentAccessors(){		
   //---------------------------------------------------------------------------
      Hashtable table=sqlTagsGeneratorTable.getImportedForeignKeyHash();
      Enumeration enum=table.keys();

      for(;enum.hasMoreElements();){
         String foreignKeyName=(String)enum.nextElement();
         object=(SQLTagsGeneratorForeignKey)table.get(foreignKeyName);
         importTable=object.getImportTableName();
         exportTable=object.getExportTableName();
         fkName=object.getFkName();
         joinColumns=object.getJoinColumnHashtable();
         joinKeys=joinColumns.keys();
         createFKParentAccessor();
         createFKParentMutator(fkName);
      }
   }//createFKParentAccessors() ENDS

   /**  
    * <code>createFKParentMutators</code>  
    * <p>  
    * This method creates the fk parent  mutator methods.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code> 
    * @return  <code>none</code>
    */  
   //---------------------------------------------------------------------------
	private void createFKParentMutators(){		
   //---------------------------------------------------------------------------
      StringBuffer message=new StringBuffer(" This method sets ");
      message.append("the foreign key parent associated with this table.");
   }//createFKParentMutators() ENDS

   /**  
    * <code>createMethodHeader</code>  
    * <p>  
    * This method creates the method header.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code> 
    * @return  <code>none</code>
    */  
   //---------------------------------------------------------------------------
	private String createMethodHeader(String column,String message){		
   //---------------------------------------------------------------------------
      header=new HeaderDefinition("Booker Northington II","1.0","1.3",  
      "get"+column+"_PARENT",message, 1);  
      String msg="Object associated with this Foreign Key.";
      header.createParameters(column+"_PARENT","object","param",msg,1);  
      header.createParameters("","none","return","",1);  
      return header.getHeader();
	}// createMethodHeader() ENDS

   /**  
    * <code>createFKParentAccessor</code>  
    * <p>  
    * This method creates the FK Parent get methods.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code> 
    * @return  <code>none</code>
    */  
   //---------------------------------------------------------------------------
   private void createFKParentAccessor(){
   //---------------------------------------------------------------------------
      StringBuffer message=new StringBuffer("This method returns ");
      message.append("an instance of the foreign key's parent.");
      getFKMethodBuffer.append(createMethodHeader(fkName,message.toString()));
      getFKMethodBuffer.append(spacer);
      getFKMethodBuffer.append("   public "+exportTable);
      getFKMethodBuffer.append(" get"+fkName+"_PARENT(){\n");
      getFKMethodBuffer.append(spacer);
      getFKMethodBuffer.append("      if("+fkName+"_PARENT");
      getFKMethodBuffer.append("!=null){\n         return ");
      getFKMethodBuffer.append(fkName+"_PARENT;\n}\n\n      ");
      getFKMethodBuffer.append("return get"+fkName+"_PARENT(depth);\n");
      getFKMethodBuffer.append("\n   }//get"+fkName+"_PARENT() ENDS");
      getFKMethodBuffer.append("\n\n");

      // now create overloaded one
      getFKMethodBuffer.append(createMethodHeader(fkName,message.toString()));
      getFKMethodBuffer.append(spacer);
      getFKMethodBuffer.append("   public "+exportTable);
      getFKMethodBuffer.append(" get"+fkName+"_PARENT(int depth){\n");
      getFKMethodBuffer.append(spacer);
      getFKMethodBuffer.append("      if("+fkName+"_PARENT");
      getFKMethodBuffer.append("!=null)\n         return ");
      getFKMethodBuffer.append(fkName+"_PARENT;\n\n      ");
      // use next line to save FK object ... 
      getFKMethodBuffer.append("      "+fkName+"_PARENT = new "+exportTable+"(");
      // or use next line to NOT SAVE FK object ... 
      // getFKMethodBuffer.append("      "+exportTable+" "+fkName+"_PARENT = new "+exportTable+"(");
      Enumeration keyEnum=(object.getJoinColumnHashtable()).keys();

      for(;keyEnum.hasMoreElements();){
         String key=(String)keyEnum.nextElement();
         getFKMethodBuffer.append("get"+key+"(),");
      }

      getFKMethodBuffer.append("depth,connectionTag);\n");
      getFKMethodBuffer.append("      return  "+ fkName+"_PARENT;\n");
      getFKMethodBuffer.append("   }//get"+fkName+"_PARENT() ENDS");
      getFKMethodBuffer.append("\n\n");
   }//createFKParentAccessor() ENDS

   /**  
    * <code>createMethodHeader</code>  
    * <p>  
    * This method creates the method header.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code> 
    * @return  <code>none</code>
    */  
   //---------------------------------------------------------------------------
	private void createFKParentMutator(String column){		
   //---------------------------------------------------------------------------
//      setFKMethodBuffer.setLength(0);
      StringBuffer message=new StringBuffer("This method set the foreign ");
      message.append("key parent associated with this table.");
      header=new HeaderDefinition("Booker Northington II","1.0","1.3",  
      "set"+column+"_PARENT",message.toString(), 1);  
      String msg="Object associated with this Foreign Key.";
      header.createParameters(column+"_PARENT","object","param",msg,1);  
      header.createParameters("","none","return","",1);  
      setFKMethodBuffer.append(header.getHeader());
      setFKMethodBuffer.append(spacer);
      setFKMethodBuffer.append("   public void set"+fkName+"_PARENT(");
      setFKMethodBuffer.append(exportTable+" object){\n");
      setFKMethodBuffer.append(spacer);

        Enumeration e = object.getImportColumnNames();
        while( e.hasMoreElements() ) {
            String key = (String) e.nextElement();
            String value=object.getExportColumnName(key);
/*
        }



      for(;joinKeys.hasMoreElements();){
         String key=(String)joinKeys.nextElement();
**/
         setFKMethodBuffer.append("      set");
         setFKMethodBuffer.append(key+"(object.get"+value);
         setFKMethodBuffer.append("());\n");
      }

      setFKMethodBuffer.append("      "+fkName+"_PARENT=object;\n");
      setFKMethodBuffer.append("      return;\n   ");
      setFKMethodBuffer.append("}//set"+fkName+"_PARENT() ENDS\n\n");
      return;
	}// createMethodHeader() ENDS

   //***************************************************************************  
   // Class Variables   
   //***************************************************************************  
	private StringBuffer buffer=new StringBuffer();;
   private String exportTable;
   private String fkName;
	private HeaderDefinition header;
   private StringBuffer getFKMethodBuffer=new StringBuffer();;
   private String importTable;
   private Hashtable joinColumns;
   private Enumeration joinKeys;
	private StringBuffer setFKMethodBuffer=new StringBuffer();;
   private String spacer="   //---------------------------------------------------------------------------\n";  
	private SQLTagsGeneratorForeignKey object;
	private SQLTagsGeneratorTable sqlTagsGeneratorTable;
   private String tableName;
}//ClassFKParent() ENDS
