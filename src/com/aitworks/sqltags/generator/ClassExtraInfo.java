/* $Id: ClassExtraInfo.java,v 1.3 2002/03/15 14:23:45 solson Exp $
 * $Log: ClassExtraInfo.java,v $
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
import java.util.Vector;

/**
 * <code>ClassExtraInfo</code>  
 * <p>  
 * This class is responsible for creating the _BIND methods.
 * methods.
 * </p>  
 * @author  Booker Northington II  
 * @version 1.1
 * @since   1.3  
 * @param  <code>none</code>  
 * @return <code>none</code>
 */
public class ClassExtraInfo{
   //***************************************************************************  
   // Class Constructors  
   //***************************************************************************  
   /**  
    * <code>ClassExtraInfo</code>  
    * <p>  
    * Class constructor. Responsible for controlling creation of the
    * _BIND methods.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param sqlTagsGeneratorTable<code>SQLTagsGeneratorTable</code>table data.
    */  
   //---------------------------------------------------------------------------
   public ClassExtraInfo(SQLTagsGeneratorTable sqlTagsGeneratorTable){
   //---------------------------------------------------------------------------
      this.sqlTagsGeneratorTable=sqlTagsGeneratorTable;
      this.tableName=sqlTagsGeneratorTable.getTableName();
      createClassExtraInfo();
   }// ClassExtraInfo() ENDS

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
      ClassExtraInfo classExtraInfo=new ClassExtraInfo(sqlTagsGeneratorTable);
      System.out.println(classExtraInfo.getClassExtraInfo());
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
    * <code>getClassExtraInfo</code>  
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
	String getClassExtraInfo(){		
   //---------------------------------------------------------------------------
      return buffer.toString();
	}// getClassExtraInfo() ENDS


   //***************************************************************************  
   // Private Methods  
   //***************************************************************************  
   /**  
    * <code>createClassExtraInfo</code>  
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
	private void createClassExtraInfo(){
   //---------------------------------------------------------------------------
      buffer.append("package ");
      buffer.append(sqlTagsGeneratorTable.getPropertyValue("packageName"));
      buffer.append(";\n");
      buffer.append("import java.io.*;\n");
      buffer.append("import javax.servlet.jsp.tagext.*;\n");
      buffer.append("import com.aitworks.sqltags.jsptags.*;\n");
      buffer.append("/**\n");
      buffer.append(" * <code>"+tableName+"_TagExtraInfo</code>\n");
      buffer.append(" * <p>\n");
      buffer.append(" * This class is used to create the scripting ");
      buffer.append("variables associated with the \n");
      buffer.append(" * tags.\n");
      buffer.append(" * </p>\n");
      buffer.append(" * @author  Booker Northington II\n");
      buffer.append(" * @version 1.0\n");
      buffer.append(" * @since   1.3\n");
      buffer.append(" * @param none <code>none</code> none.\n");
      buffer.append(" * @see BodyTagSupport <code>BodyTagSupport</code> ");
      buffer.append("For more information.\n");
      buffer.append(" * @return none <code>none</code> none.\n");
      buffer.append(" */\n");
      buffer.append(spacer);
      buffer.append("public class "+tableName+"_TagExtraInfo extends ");
      buffer.append("TagExtraInfo{\n");
      buffer.append(spacer);
      buffer.append("   /**\n");
      buffer.append("    * <code>"+tableName+"_TagExtraInfo</code>\n");
      buffer.append("    * <p>\n");
      buffer.append("    * This is the class constructor. There really ");
      buffer.append("is no need to actually call \n");
      buffer.append("    * this directly.\n");
      buffer.append("    * </p>\n");
      buffer.append("    * @author  Booker Northington II\n");
      buffer.append("    * @version 1.0\n");
      buffer.append("    * @since   1.3\n");
      buffer.append("    * @param none <code>none</code> none.\n");
      buffer.append("    * @return none <code>none</code> none.\n");
      buffer.append("    */\n");
      buffer.append(spacer);
      buffer.append("   public "+tableName+"_TagExtraInfo(){\n");
      buffer.append(spacer);
      buffer.append("   }\n\n");
      buffer.append("   /**\n");
      buffer.append("    * <code>getVariableInfo</code>\n");
      buffer.append("    * <p>\n");
      buffer.append("    * This method makes the scripting variables ");
      buffer.append("available to the jsp.\n");
      buffer.append("    * </p>\n");
      buffer.append("    * @author  Booker Northington II\n");
      buffer.append("    * @version 1.0\n");
      buffer.append("    * @since   1.3\n");
      buffer.append("    * @param value <code>TagData</code> The data ");
      buffer.append("associated with the tag.\n");
      buffer.append("    * @see TagExtraInfo <code>TagExtraInfo</code> ");
      buffer.append("For more information.\n");
      buffer.append("    * @see VariableInfo <code>VariableInfo</code> ");
      buffer.append("For more information.\n");
      buffer.append("    * @see TagData <code>TagData</code> For more ");
      buffer.append("information.\n");
      buffer.append("    * @return variableInfo <code>VariableInfo[]");
      buffer.append("</code> An array of variableInfo.\n");
      buffer.append("    */\n");
      buffer.append(spacer);
      buffer.append("   public VariableInfo[] getVariableInfo(");
      buffer.append("TagData value) {\n");
      buffer.append(spacer);
      buffer.append("      String objectType=value.getAttribute");
      buffer.append("String(\"handlerClass\");\n");
      buffer.append("      String handlerType=value.getAttribute");
      buffer.append("String(\"handlerID\");\n");
      buffer.append("      if(objectType!=null&&handlerType!=null){\n");
      buffer.append("         return new VariableInfo[]{\n");
      buffer.append("            new VariableInfo(value.getId(),\"");
      buffer.append(tableName+"_TAG\",true,VariableInfo.NESTED),\n");
      buffer.append("            new VariableInfo(handlerType,objectType,");
      buffer.append("true,VariableInfo.NESTED)\n");
      buffer.append("         };\n");
      buffer.append("      }\n");
      buffer.append("      else{\n");
      buffer.append("         return new VariableInfo[]{\n");
      buffer.append("            new VariableInfo(value.getId(),\"");
      buffer.append(tableName+"_TAG\",true,VariableInfo.NESTED)\n");
      buffer.append("         };\n");
      buffer.append("      }\n");
      buffer.append("   }\n");
      buffer.append("}//"+tableName+"_TagExtraInfo ENDS()\n");
	}//createClassExtraInfo() ENDS

   //***************************************************************************  
   // Class Variables   
   //***************************************************************************  
	private StringBuffer buffer=new StringBuffer();;
   private String spacer="   //--------------------------------"+
                         "-------------------------------------------\n";  
	private SQLTagsGeneratorTable sqlTagsGeneratorTable;
   private String tableName;
}// ClassExtraInfo() ENDS