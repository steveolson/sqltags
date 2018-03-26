/* $Id: GenerateTLDFile.java,v 1.3 2002/03/15 14:23:46 solson Exp $
 * $Log: GenerateTLDFile.java,v $
 * Revision 1.3  2002/03/15 14:23:46  solson
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
import com.aitworks.sqltags.utilities.CreateJar;
import java.io.FileWriter;  
import java.io.IOException;  
  
final class GenerateTLDFile{  
   //***************************************************************************  
   //Finalize Method  
   //***************************************************************************  
   /**  
    * This is the finalizer method for this class  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   protected void finalize() throws Throwable{  
   //-------------------------------------------------------------------------  
      super.finalize();  
   }// finalizer() ENDS  
  
   public static void main(String args[]){  
      GenerateTLDFile selfPointer=new GenerateTLDFile();  
      StringBuffer msg=new StringBuffer();  
      int argumentCount=args.length;  
  
      if(argumentCount<2||argumentCount>3){  
         msg.append("\nUsage: ");  
         msg.append("GenerateTLDFile tagsShortName fileName");  
         msg.append("\n\ttagsShortName - the shortname for the tag library.");  
         msg.append("\n\toutputDirectory - the full path ");  
         msg.append("name for the generated file.");  
         msg.append("\nOptional parameters:");  
         msg.append("\n\tjarFileName - name to give to the jar file.");  
         System.out.println(msg.toString());  
      }  
      else{  
         CreateTLD createTLD=new CreateTLD(args[0],"");  
         StringBuffer library=createTLD.getTagLib();  
         library.append("</"+args[0]+">");  
  
         if(selfPointer.writeFile(args[1],createTLD.getTagLib()))  
            msg.append("\n**taglibrary successfully generated.");  
         else  
            msg.append("\n**Errors encountered while building taglibrary!");  
      }  
  
      //now create Jar tablib  
      if(argumentCount==3){  
         new CreateJar();
      }        
  
      System.out.println(msg.toString());  
   }  
 
    
   /**  
    * method: writeFile  
    * usage: this method writes the generated java file to  
    * disk.  
    * @param fileName the name of the file were writing.  
    * @param outputFile the actual data were writing.   
    * @return true if everything went ok   
    */   
   //---------------------------------------------------------------------------  
   public boolean writeFile(String fileName, StringBuffer outputFile){  
   //---------------------------------------------------------------------------  
      boolean status=false;  
  
      try{  
         FileWriter writer=new FileWriter(fileName);   
         writer.write((outputFile.toString()).toCharArray());  
         writer.close();  
         status=true;  
      }  
      catch(IOException exception){  
         System.out.println("DBConnection.writeFile: "+exception);  
      }  
  
      return status;  
   }  
}//GenerateTLDFile() ENDS  
