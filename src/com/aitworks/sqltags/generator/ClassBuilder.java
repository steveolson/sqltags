/* $Id: ClassBuilder.java,v 1.10 2002/08/16 15:44:16 jpoon Exp $
 * $Log: ClassBuilder.java,v $
 * Revision 1.10  2002/08/16 15:44:16  jpoon
 * made changes to create tag constructors
 *
 * Revision 1.9  2002/04/15 11:10:16  booker
 * Changed scope of textArea and buildMenu. Added
 * methods to reflect new behavior.
 *
 * Revision 1.8  2002/03/15 14:23:45  solson
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
import java.awt.Color;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import javax.swing.JTextArea;

/**
 * <code>ClassBuilder</code>  
 * <p>  
 * This class is controls the creation of the java files.
 * </p>  
 * @author  Booker Northington II  
 * @version 1.1
 * @since   1.3  
 * @param  <code>none</code>  
 * @return <code>none</code>
 */
public class ClassBuilder{
   //*************************************************************************** 
   // Class Constructor 
   //***************************************************************************
   /**  
    * <code>Class Constructor</code>  
    * <p>  
    * Builds the table methods.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param table <code>SQLTagsGeneratorTable</code> database table data.
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
   public ClassBuilder(SQLTagsGeneratorTable table){
      this.table=table;
      createTableMethods();
      createJavaFile();
   }// Constructor() ENDS
   
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
   // Private Methods  
   //***************************************************************************  
   private void clearBuffers(){
      classAccessorMethods.setLength(0);
      classBindMethods.setLength(0);
      classExtraInfoMethods.setLength(0);
      classFinalizeMethod.setLength(0);
      classConstructorMethods.setLength(0);
      classFKChildrenMethods.setLength(0);
      classFKParentMethods.setLength(0);
      classFKWhereMethods.setLength(0);
      classFormatMethods.setLength(0);
      classHelperMethodsBuffer.setLength(0);
      classImportPackagesMethod.setLength(0);
      classMutatorMethods.setLength(0);
      classObjectHeaderMethods.setLength(0);
      classSelectMethods.setLength(0);
      classSQLContractMethods.setLength(0);
      classVariableMethods.setLength(0);
      classWhereMethods.setLength(0);
      javaFile.setLength(0);
      javaTag.setLength(0);
      table.resetBuffers();
   }//clearBuffers() ENDS

   /**  
    * <code>createJavaFile</code>  
    * <p>  
    * Creates the java file..
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code> 
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------
   private void createJavaFile(){
   //---------------------------------------------------------------------------
      javaFile.append(classObjectHeaderMethods.toString()+newLine);
      javaFile.append(createSectionHeader("Class Constructor"));     
      javaFile.append(classConstructorMethods.toString()+newLine);
      javaFile.append(createSectionHeader("Finalize Method"));
      javaFile.append(classFKWhereMethods.toString()+newLine);
      javaFile.append(classFKChildrenMethods.toString()+newLine);
      javaFile.append(classFKParentMethods.toString()+newLine);
      javaFile.append(classFinalizeMethod.toString()+newLine);
      javaFile.append(createSectionHeader("Public Methods"));
      javaFile.append(classAccessorMethods.toString());
      javaFile.append(classMutatorMethods.toString());
      javaFile.append(classSQLContractMethods.toString());
      javaFile.append(classHelperMethodsBuffer.toString());
      javaFile.append(createSectionHeader("Class Variables"));
      javaFile.append(classVariableMethods.toString());
      javaFile.append("}//"+table.getTableName() +"() ENDS");

      javaTag.append(createSectionHeader("Public Methods")); 
      javaTag.append(tagClassConstructorMethods.toString()+newLine);
      javaTag.append(classWhereMethods.toString()+newLine);
      javaTag.append(classBindMethods.toString()+newLine);
      javaTag.append(classFormatMethods.toString()+newLine);
      javaTag.append(classSelectMethods.toString()+newLine);
      javaTag.append("}//"+table.getTableName() +"_TAG() ENDS");
      String fileName=table.getTableName();
      javaFile.insert(0,getCreationTime(fileName+".java"));
      writeFile(fileName,javaFile);
      javaTag.insert(0,getCreationTime(fileName+"_TAG.java"));
      writeFile(fileName+"_TAG",javaTag);
      classExtraInfoMethods.insert(0,getCreationTime
        (fileName+"_TagExtraInfo.java"));
      writeFile(fileName+"_TagExtraInfo",classExtraInfoMethods);
      clearBuffers();
   }//createJavaFile() ENDS
      
   /**  
    * <code>createSectionHeader</code>  
    * <p>  
    * Creates the section header information.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  name<code>String</code> the section name.
    * @return  buffer<code>String</code> the section header.
    */  
   //---------------------------------------------------------------------------
   private String createSectionHeader(String name){
   //---------------------------------------------------------------------------
     StringBuffer buffer=new StringBuffer();
      buffer.append(section);
      buffer.append("   // "+name+"\n");
      buffer.append(section);
      return buffer.toString();
   }//createSectionHeader() ENDS

   /**  
    * <code>createTableMethods</code>  
    * <p>  
    * Creates the methods which belong to the tables.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code> 
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------
   private void createTableMethods(){
   //---------------------------------------------------------------------------
      //Create table information.
      ClassConstructors classConstructors=new ClassConstructors(table);
      TagClassConstructors tagClassConstructors=new TagClassConstructors(table);
      
      ClassAccessor classAccessor=new ClassAccessor(table);
      ClassMutator classMutator=new ClassMutator(table);
      ClassFKParent classFKParent=new ClassFKParent(table);
      ClassSQLContract classSQLContract=new ClassSQLContract(table);
      ClassHelperMethods classHelperMethods=new ClassHelperMethods(table);
      ClassVariables classVariables=new ClassVariables(table);
      ClassFinalize classFinalize=new ClassFinalize(table);
      ClassImportPackages classImportPackages=new ClassImportPackages(table);
      ClassFKWhere classFKWhere=new ClassFKWhere(table);
      ClassFKChildren classFKChildren=new ClassFKChildren(table);
      ClassBind classBind=new ClassBind(table);
      ClassFormat classFormat=new ClassFormat(table);
      ClassSelect classSelect=new ClassSelect(table);
      ClassWhere classWhere=new ClassWhere(table);
      ClassObjectHeader classObjectHeader=new ClassObjectHeader(table,true);
      ClassObjectHeader classObjectHeaderTag=new ClassObjectHeader(table,false);
      ClassExtraInfo classExtraInfo=new ClassExtraInfo(table);

      //Put the buffers together now.
      classConstructorMethods.append(classConstructors.getClassConstructors());
      tagClassConstructorMethods.append(tagClassConstructors.getClassConstructors());
      
      classAccessorMethods.append(classAccessor.getClassAccessor());
      classMutatorMethods.append(classMutator.getClassMutator());
      classFKParentMethods.append(classFKParent.getClassFKParent());
      classSQLContractMethods.append(classSQLContract.getClassSQLContract());
      classHelperMethodsBuffer.append(
         classHelperMethods.getClassHelperMethods());
      classVariableMethods.append(classVariables.getClassVariablesMethods());
      classFinalizeMethod.append(classFinalize.getClassFinalize());
      classImportPackagesMethod.append(
         classImportPackages.getClassImportPackages());
      classFKWhereMethods.append(classFKWhere.getClassFKWhere());
      classFKChildrenMethods.append(classFKChildren.getClassFKChildren());
      classBindMethods.append(classBind.getClassBind());
      classFormatMethods.append(classFormat.getClassFormat());
      classSelectMethods.append(classSelect.getClassSelect());
      classWhereMethods.append(classWhere.getClassWhere());
      classObjectHeaderMethods.append(classObjectHeader.getClassObjectHeader());
      classExtraInfoMethods.append(classExtraInfo.getClassExtraInfo());
      javaTag.append(classObjectHeaderTag.getClassObjectHeader());
   }//createTableMethods() ENDS
   /**  
    * <code>getCreationTime</code>  
    * <p>  
    * Get the file creation time.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code>
    * @return  time<code>String</code> file creation time.
    */  
   //---------------------------------------------------------------------------  
   private String getCreationTime(String fileName){  
   //---------------------------------------------------------------------------  
      StringBuffer buffer=new StringBuffer();
      buffer.append("// Applied Information Technology Inc.\n");
      buffer.append("// "+fileName+" generated ");
      buffer.append(Calendar.getInstance().getTime().toString()+"\n");  
      return buffer.toString();
   }//getCreationTime() ENDS

   /**  
    * <code>writeFile</code>  
    * <p>  
    * Writes the java file to disk.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param fileName <code>String</code> the file name.
    * @param outputFile <code>StringBuffer</code> the files data.
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
   private void writeFile(String fileName,StringBuffer outputFile){  
   //---------------------------------------------------------------------------  
      String tableName=table.getTableName();
      StringBuffer filePath=
         new StringBuffer(table.getPropertyValue("outputDirectory"));
      filePath.append(java.io.File.separator+fileName+".java");  

      try{  
         FileWriter writer=new FileWriter(filePath.toString());   
         writer.write((outputFile.toString()).toCharArray());  
         writer.close();  
         
         if(GeneratorGUI.getTextArea()!=null)
             table.sendMessageToGUI("          <"+fileName+"/>\n");       
         else
             System.out.println("          <"+fileName+"/>"); 
      }  
      catch(IOException exception){  
         System.out.println("ClassBuilder.writeFile: "+exception+
            " "+filePath.toString());  
      }  
   }  //writeFile() ENDS

   //*************************************************************************** 
   // Class Variables
   //***************************************************************************
   private ClassBuilder builder=null;
   private StringBuffer classAccessorMethods=new StringBuffer();
   private StringBuffer classBindMethods=new StringBuffer();
   private StringBuffer classExtraInfoMethods=new StringBuffer();
   private StringBuffer classFinalizeMethod=new StringBuffer();
   private StringBuffer classConstructorMethods=new StringBuffer();
   private StringBuffer tagClassConstructorMethods=new StringBuffer();
   private StringBuffer classFKChildrenMethods=new StringBuffer();
   private StringBuffer classFKParentMethods=new StringBuffer();
   private StringBuffer classFKWhereMethods=new StringBuffer();
   private StringBuffer classFormatMethods=new StringBuffer();
   private StringBuffer classHelperMethodsBuffer=new StringBuffer();
   private StringBuffer classImportPackagesMethod=new StringBuffer();
   private StringBuffer classMutatorMethods=new StringBuffer();
   private StringBuffer classObjectHeaderMethods=new StringBuffer();
   private StringBuffer classSelectMethods=new StringBuffer();
   private StringBuffer classSQLContractMethods=new StringBuffer();
   private StringBuffer classVariableMethods=new StringBuffer();
   private StringBuffer classWhereMethods=new StringBuffer();
   private String newLine="\n";
   private String section="   //***************************************************************************\n";
   private SQLTagsGeneratorTable table;
   private StringBuffer javaFile=new StringBuffer();
   private StringBuffer javaTag=new StringBuffer();
   private JTextArea textArea=null;
}//ClassBuilder() ENDS
