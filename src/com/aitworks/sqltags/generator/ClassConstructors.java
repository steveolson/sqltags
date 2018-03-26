/* $Id: ClassConstructors.java,v 1.10 2002/08/16 15:44:16 jpoon Exp $
 * $Log: ClassConstructors.java,v $
 * Revision 1.10  2002/08/16 15:44:16  jpoon
 * made changes to create tag constructors
 *
 * Revision 1.9  2002/08/07 15:13:10  jpoon
 * re-construct connection manager
 *
 * Revision 1.8  2002/03/15 14:23:46  solson
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
import java.util.Vector;

/**
 * <code>ClassConstructors</code>  
 * <p>  
 * This class is responsible for creating the class constructors for the table.
 * </p>  
 * @author  Booker Northington II  
 * @version 1.1
 * @since   1.3  
 * @param  <code>none</code>  
 * @return <code>none</code>
 */
public class ClassConstructors{
   //***************************************************************************  
   // Class Constructors  
   //***************************************************************************  
   /**  
    * <code>ClassAccessor</code>  
    * <p>  
    * Class constructor. Responsible for controlling creation of accessors.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param sqlTagsGeneratorTable<code>SQLTagsGeneratorTable</code>table data.
    */  
   //---------------------------------------------------------------------------
   public ClassConstructors(SQLTagsGeneratorTable sqlTagsGeneratorTable){
   //---------------------------------------------------------------------------
      this.sqlTagsGeneratorTable=sqlTagsGeneratorTable;
      this.tableName=sqlTagsGeneratorTable.getTableName();
      createClassConstructors();
   }// ClassConstructors() ENDS

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
      ClassConstructors classConstructors=
         new ClassConstructors(sqlTagsGeneratorTable);
	}// main() ENDS

   /**  
    * <code>toString</code>  
    * <p>  
    * This method returns the state of the class variables.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code>
    * @return  <code>none</code>
    */  
	public String toString(){
      StringBuffer buff=new StringBuffer("\n*****ClassConstructors: ");  
      buff.append("\tbuffer="+buffer.toString());  
      buff.append("\theaderDefinition="+header);  
      buff.append("\tsqlTagsGeneratorTable="+sqlTagsGeneratorTable);  
      return buff.toString();  
	}//toString() ENDS

   //***************************************************************************  
   // Friendly Methods  
   //***************************************************************************  
   /**  
    * <code>getClassConstructor</code>  
    * <p> 
    * This method returns the class constructors.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none/code>
    * @return  <code>none</code>
    */  
   //---------------------------------------------------------------------------
	String getClassConstructors(){
   //---------------------------------------------------------------------------
      return buffer.toString();
	}//getClassConstructors() ENDS

 
   //***************************************************************************  
   // Private Methods  
   //***************************************************************************  
   /**  
    * <code>createClassConstructors</code>  
    * <p>  
    * This method creates the class constructor methods.
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code> 
    * @return  <code>none</code>
    */  
   //---------------------------------------------------------------------------
	private void createClassConstructors(){		
   //---------------------------------------------------------------------------
      createDefaultConstructor();
      createOneArgumentConstructor();
      //createTwoArgumentConstructor();
      createThreeArgumentConstructor();
	}// createClassConstructors() ENDS

   /**  
    * <code>createDefaultConstructor</code>  
    * <p>  
    * This method creates the tables default constructor.
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code> 
    * @return  <code>none</code>
    */  
   //---------------------------------------------------------------------------
   private void createDefaultConstructor(){
   //---------------------------------------------------------------------------
      header=new HeaderDefinition("Booker Northington II","1.0","1.3",  
      tableName,"This is "+tableName+"'s default constructor.", 1);  
      header.createParameters("none","none","param","none.",1);  
      header.createParameters("none","none","return","none.",1);  
      buffer.append(header.getHeader());  
      buffer.append(spacer);  
      buffer.append("   public "+tableName+"(){\n"+spacer);  
      buffer.append("      initializeHashTables();\n");  
      buffer.append("   }//"+tableName+"() Default Constructor ENDS\n\n");  
   }//createDefaultConstructor() ENDS

   /**  
    * <code>createOneArgumentConstructor</code>  
    * <p>  
    * This method creates the one argument constructor
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code> 
    * @return  <code>none</code>
    */  
   //---------------------------------------------------------------------------
   private void createOneArgumentConstructor(){
   //---------------------------------------------------------------------------
      if(sqlTagsGeneratorTable.getPrimaryKeyVector().size()>0){
         StringBuffer moreBody=new StringBuffer("      ");
         Vector parameterVector=getParameterVector();
         header=new HeaderDefinition("Booker Northington II","1.0","1.3",  
         tableName,"This is "+tableName+"'s "+"one argument constructor.", 1);
         setJavaDocParameters();
         header.createParameters("none","","return","",1);
         setConstructorBody(",ConnectionTag connectionTag", moreBody.toString());
         buffer.append("   }// "+tableName+"() One Argument Constructor ");
         buffer.append("ENDS\n\n");
      }
   }//createOneArgumentConstructor() ENDS

   /**  
    * <code>createThreeArgumentConstructor</code>  
    * <p>  
    * This method creates the three argument constructor
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code> 
    * @return  <code>none</code>
    */  
   //---------------------------------------------------------------------------
   private void createThreeArgumentConstructor(){
   //---------------------------------------------------------------------------
      if(sqlTagsGeneratorTable.getPrimaryKeyVector().size()>0){
         StringBuffer moreBody=new StringBuffer("      ");
         moreBody.append("this.connectionTag=connectionTag;\n      ");
         Vector parameterVector=getParameterVector();
         header=new HeaderDefinition("Booker Northington II","1.0","1.3",  
         tableName,"This is "+tableName+"'s "+
            " three argument constructor.", 1);
         setJavaDocParameters();
         header.createParameters("int","depth","param","the table depth.",1);
         header.createParameters("Connection","connection","param",
                                 "a database connection.",1);
         header.createParameters("none","","return","",1);
         setConstructorBody(",int depth,ConnectionTag connectionTag",
                            moreBody.toString());
         buffer.append("   }// "+tableName+"() Three Argument Constructor ");
         buffer.append("ENDS\n\n");
      }
   }//createThreeArgumentConstructor() ENDS

   /**  
    * <code>createTwoArgumentConstructor</code>  
    * <p>  
    * This method creates the two argument constructor
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code> 
    * @return  <code>none</code>
    */  
   //---------------------------------------------------------------------------
   private void createTwoArgumentConstructor(){
   //---------------------------------------------------------------------------
      if(sqlTagsGeneratorTable.getPrimaryKeyVector().size()>0){
         Vector parameterVector=getParameterVector();
         header=new HeaderDefinition("Booker Northington II","1.0","1.3",  
         tableName,"This is "+tableName+"'s "+"two argument constructor.", 1);
         setJavaDocParameters();
         header.createParameters("int","depth","param","the table depth.",1);
         header.createParameters("none","","return","",1);
         setConstructorBody(",int depth","");
         buffer.append("   }// "+tableName+"() Two Argument Constructor ");
         buffer.append("ENDS\n\n");
      }
   }//createOneArgumentConstructor() ENDS

   /**  
    * <code>getParameterVector</code>  
    * <p>  
    * This method a vector of parameters which are to be included in the java
    * doc
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code> 
    * @return  <code>none</code>
    */  
   //---------------------------------------------------------------------------
   private Vector getParameterVector(){
   //---------------------------------------------------------------------------
      String[] parameterHeader=new String[4];
      parameterVector=new Vector();
      Enumeration enum=sqlTagsGeneratorTable.getPrimaryKeys();
      primaryKeyList=new StringBuffer();
      setColumnValueBuffer=new StringBuffer();
      primaryKeyNames=new StringBuffer();

      for(;enum.hasMoreElements();){
         String name=(String)enum.nextElement();
         primaryKeyList.append("String "+name+",");
         setColumnValueBuffer.append("      setColumnValue(\"");
         setColumnValueBuffer.append(name+"\","+name);
         setColumnValueBuffer.append(");\n");
         primaryKeyNames.append(name+",");
         parameterHeader[0]="String";
         parameterHeader[1]=name;
         parameterHeader[2]="param";
         parameterHeader[3]=tableName+"'s primary key.";
         parameterVector.addElement(parameterHeader);
         parameterHeader=new String[4];
      }

      primaryKeyNames.setLength(primaryKeyNames.length()-1);         
      primaryKeyList.setLength(primaryKeyList.length()-1);         
      return parameterVector;
   }//getParameterVector() ENDS

   /**  
    * <code>setConstructorBody</code>  
    * <p>  
    * This method sets the body of the one and two argument constructors.
    * doc
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code> 
    * @return  <code>none</code>
    */  
   //---------------------------------------------------------------------------
   private void setConstructorBody(String additionalParameters,
                                   String additionalBody){
   //---------------------------------------------------------------------------
      buffer.append(header.getHeader());  
      buffer.append(spacer);  
      buffer.append("   public "+tableName+"(");  
      buffer.append(primaryKeyList.toString()+additionalParameters+"){\n");  
      buffer.append(spacer);  
      buffer.append(additionalBody);
      buffer.append(setColumnValueBuffer.toString());
      buffer.append("      initializeHashTables();\n");
      buffer.append("      initialize("+primaryKeyNames.toString()+",");
      buffer.append("depth);\n");
   }//setConstructorBody() ENDS

   /**  
    * <code>setJavaDocParametere</code>  
    * <p>  
    * This method sets the parameters for the constructors. These parameters
    * will show up if javadoc is ran.
    * doc
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code> 
    * @return  <code>none</code>
    */  
   //---------------------------------------------------------------------------
   private void setJavaDocParameters(){
      int parameterVectorSize=parameterVector.size();

      for(int index=0;index<parameterVectorSize;index++){
         String[] value=(String[])parameterVector.elementAt(index);
         header.createParameters(value[0],value[1],value[2],value[3],1);
      }
   }//setJavaDocParameters() ENDS

   //*************************************************************************** 
   // Class Variables
   //***************************************************************************
	private StringBuffer buffer=new StringBuffer();;
   private StringBuffer setColumnValueBuffer;
	private HeaderDefinition header;
   private StringBuffer primaryKeyList;
   private StringBuffer primaryKeyNames;
   private Vector parameterVector;
   private String spacer="   //---------------------------------------------------------------------------\n";  
	private SQLTagsGeneratorTable sqlTagsGeneratorTable;
   private String tableName;
}//ClassConstructors() ENDS