/* $Id: ReadFile.java,v 1.2 2002/03/15 14:23:46 solson Exp $
 * $Log: ReadFile.java,v $
 * Revision 1.2  2002/03/15 14:23:46  solson
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
import java.io.FileInputStream;  
import java.io.IOException;  
  
/**  
 * This class will read a file from the disk.  
 * @author Shellie B. Northington II  
 * @version 1.0  
 * @since JDK 1.2  
 */  
final class ReadFile{  
   /**  
    * This constructor creates a FileInputStream handle.  
    * @param fileName the name of the file to read.  
    * @return none  
    */  
   public ReadFile(String fileName){  
      try {  
         fileInputStream = new FileInputStream(fileName);  
      }  
      catch (IOException ioException) {  
         System.err.println("ReadFile.ReadFile(): "+ioException);  
      }  
   }// ReadFile() Constructor ENDS  
  
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
  
  
   /**  
    * This method closes the file input stream.  
    * @param none  
    * @return none  
    */  
   public void closeFileInputStream(){  
      try{  
         fileInputStream.close();  
      }  
      catch(IOException ioException){  
         System.err.println("ReadFile.closeFileInputStream(): "+ioException);  
      }	  
   }// closeFileInputStream ENDS()  
  
  
   /**  
    * This method returns the FileInputStreams handle.  
    * @param none  
    * @return the handle to the input stream.  
    */  
   public FileInputStream getFileInputStream(){  
      return fileInputStream;	  
   }// getFileInputStream() ENDS  
  
  
   /**  
    * This method reads the next byte from the  
    * FileInputStream().  
    * @param none.  
    * @return nextByte the next byte in the input stream.  
    */  
   public int getNextByte(){  
      int nextByte=-1;  
  
      try {  
        nextByte=fileInputStream.read();  
      }  
      catch (IOException ioException) {  
         System.err.println("ReadFile.getNextByte(): "+ioException);  
      }  
      return nextByte;  
   }// getNextByte() ENDS  
  
   //***************************************************************************  
   //                      Class variable section  
   //***************************************************************************  
   private FileInputStream fileInputStream;  
}// ReadFile() ENDS  
