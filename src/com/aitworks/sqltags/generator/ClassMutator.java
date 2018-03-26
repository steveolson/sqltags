/* $Id: ClassMutator.java,v 1.7 2002/03/15 14:23:44 solson Exp $
 * $Log: ClassMutator.java,v $
 * Revision 1.7  2002/03/15 14:23:44  solson
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

/**
 * <code>ClassMutator</code>  
 * <p>  
 * This class is responsible for creating the class mutator methods.
 * </p>  
 * @author  Booker Northington II  
 * @version 1.1
 * @since   1.3  
 * @param  <code>none</code>  
 * @return <code>none</code>
 */
public class ClassMutator{
   //***************************************************************************  
   // Class Constructors  
   //***************************************************************************  
   /**  
    * <code>ClassMutator</code>  
    * <p>  
    * Class constructor. Responsible for controlling creation of the mutators.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param sqlTagsGeneratorTable<code>SQLTagsGeneratorTable</code>table data.
    */  
   //---------------------------------------------------------------------------
   public ClassMutator(SQLTagsGeneratorTable sqlTagsGeneratorTable){
   //---------------------------------------------------------------------------
      this.sqlTagsGeneratorTable=sqlTagsGeneratorTable;
      createClassMutator();
   }// ClassMutator() ENDS

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
	protected void finalize() throws Throwable{		
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
      ClassMutator classMutator=new ClassMutator(sqlTagsGeneratorTable);
      System.out.println(classMutator.getClassMutator());
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
      StringBuffer buff=new StringBuffer("\n*****ClassMutator: ");  
      buff.append("\tbuffer="+buffer.toString());  
      buff.append("\theaderDefinition="+header);  
      buff.append("\tsqlTagsGeneratorTable="+sqlTagsGeneratorTable);  
      return buff.toString();  
	}//toString() ENDS

   //***************************************************************************  
   // Friendly Methods  
   //***************************************************************************  
   /**  
    * <code>getClassMutator</code>  
    * <p> 
    * This method returns the mutator methods which were created.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none/code>
    * @return  <code>none</code>
    */  
	String getClassMutator(){
      return buffer.toString();
	}//getClassMutator() ENDS

   //***************************************************************************  
   // Private Methods  
   //***************************************************************************  
   /**  
    * <code>createClassAccessor</code>  
    * <p>  
    * This method creates the accessor methods.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code> 
    * @return  <code>none</code>
    */  
   //---------------------------------------------------------------------------
	private void createClassMutator(){		
   //---------------------------------------------------------------------------
      Enumeration enum=sqlTagsGeneratorTable.getColumns();

      for(;enum.hasMoreElements();){
         String column=(String)enum.nextElement();
         buffer.append(createMethodHeader(column));
         buffer.append(spacer+"   public void set"+column);  
         buffer.append("(String string){\n"+spacer);
         buffer.append("      ");
         buffer.append("setColumnValue(\""+column+"\",string);\n");
         buffer.append("      return;\n");
         buffer.append("   }// set"+column+"() ENDS\n\n");  
      }
	}// createClassAccessor() ENDS

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
	private String createMethodHeader(String column){		
   //---------------------------------------------------------------------------
      header=new HeaderDefinition("Booker Northington II","1.0","1.3",  
      "set"+column,  
      "This method is responsible for setting the value of the "+  
      column.toLowerCase()+" column.", 1);  
      header.createParameters("String","string","param",column.toLowerCase()+
         "'s new value.",1);  
      header.createParameters("","none","return","",1);  
      return header.getHeader();
	}// createMethodHeader() ENDS

   //***************************************************************************  
   // Class Variables   
   //***************************************************************************  
	private StringBuffer buffer=new StringBuffer();;
	private HeaderDefinition header;
   private String spacer="   //---------------------------------------------------------------------------\n";  
	private SQLTagsGeneratorTable sqlTagsGeneratorTable;
}//ClassMutator ENDS
