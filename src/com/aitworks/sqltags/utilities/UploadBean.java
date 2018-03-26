package com.aitworks.sqltags.utilities;  
/* $Id: UploadBean.java,v 1.7 2002/05/21 12:36:07 booker Exp $
 * $Log: UploadBean.java,v $
 * Revision 1.7  2002/05/21 12:36:07  booker
 * final checkin of sqltags
 *
 * Revision 1.6  2002/04/03 14:49:22  booker
 * Worked on adding Object to ColumnProperties.
 *
 * Revision 1.5  2002/03/20 19:23:37  booker
 * Working on upload and mime
 *
 * Revision 1.4  2002/03/15 14:33:31  solson
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
import java.net.MalformedURLException;  
import java.net.URL;  
import java.util.Dictionary;
import java.util.Enumeration;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.ServletInputStream;  

public class UploadBean{  
   //***************************************************************************  
   // Class Constructors  
   //***************************************************************************  
   /**  
    * <code>Default Constructor</code>  
    * <p>  
    * This method establishes a connection to the database.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code>  
    * @return none
    */  
   //---------------------------------------------------------------------------  
   public UploadBean(HttpServletRequest httpServletRequest){  
   //--------------------------------------------------------------------------- 
      this.request=httpServletRequest;

      try{    
         if(request.getContentLength()>0){
            mimeMultiPart=new MimeMultiPart(request.getInputStream());
         }
      }
      catch(IOException ioException){
          ioException.printStackTrace();
      }
   }//Constructor ENDS
   
   /**  
    * <code>Default Constructor</code>  
    * <p>  
    * 
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code>  
    * @return none
    */  
   //---------------------------------------------------------------------------  
   public UploadBean(HttpServletRequest httpServletRequest,String file,
                     String path){  
   //--------------------------------------------------------------------------- 
      try{
          this.request=httpServletRequest;
          mimeMultiPart=new MimeMultiPart(request.getInputStream());
          writeFile(path,file);
      }
      catch(IOException ioException){
          ioException.printStackTrace();
      }
   }//Constructor ENDS
   
   //***************************************************************************  
   // Public Methods
   //***************************************************************************  
   /**  
    * This method returns the contents.
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  the content type of the file which was uploaded.  
    * @since   JDK1.3  
    */  
   //---------------------------------------------------------------------------  
    public byte[] getContents(String string){
   //---------------------------------------------------------------------------  
      return mimeMultiPart.getContents(string);  
   }//getContents() ENDS    
    
   /**  
    * This method looks up the values read from the request object.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   fieldName the field you want to know the value of.  
    * @return  the value of the field.  
    * @since   JDK1.3  
    */  
   //---------------------------------------------------------------------------  
   public MimePart getMimePart(String string) {  
   //---------------------------------------------------------------------------  
      return mimeMultiPart.getMimePart(string);
   }//getFieldValue() ENDS   

   /**  
    * This method returns the data the was contained within the file.   
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  the file data.  
    * @since   JDK1.3  
    */  
   //---------------------------------------------------------------------------  
   public byte[] getFile(String string){  
   //--------------------------------------------------------------------------- 
      MimePart mimePart=getMimePart(string);
      return mimePart.getContents();
   }//getFile() ENDS  
  
   /**  
    * <code>getParameter</code>  
    * <p>  
    * This method returns the contents of the requested mime part
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  string<code>String</code> the mime part name
    * @return value<code>String</code>an array of bytes or null.
    */  
   //---------------------------------------------------------------------------  
    public String getParameter(String string){
   //---------------------------------------------------------------------------  
       String returnValue="";
            
       if(mimeMultiPart!=null){
          MimePart mimePart=getMimePart(string);
          
          if(mimePart!=null&&mimePart.getContentType().startsWith("text/"))
            returnValue=new String(mimePart.getContents());
       }
       
       return returnValue;
    }//getParameter() ENDS
       
                
   /**  
    * <code>uploadURL</code>   
    * <p>  
    * This method takes a url and reads its data. 
    * <p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param url <code>String</code> the url.  
    * @return string <code>Staring</code> the content of the url.  
    */  
   //--------------------------------------------------------------------------- 
   public String uploadURL(String urlString){  
   //---------------------------------------------------------------------------
      StringBuffer buffer=new StringBuffer();
      int aChar=0;
      
      try{  
        URL url=new URL(urlString.trim());  
        InputStream inputStream=url.openStream();
           
        while((aChar=inputStream.read())!=-1)
            buffer.append((char)aChar);
        
      }  
     catch (MalformedURLException exception) {
        System.out.println("Exception "+exception);  
      }  
      catch(IOException exception){  
        System.out.println("Exception "+exception);  
      }  
      
      return buffer.toString();
   }//uploadURL() ENDS  
   
   /**  
    * This method writes the file to the disk.   
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void writeFile(String path,String file)throws IOException{  
   //------------------------------------------------------------------------- 
      if(!path.endsWith(File.separator))
          path=path+File.separator;
      
      path=path+file;
      byte[] data=getFile(file);
      File newFile=new File(path);
      FileOutputStream fos=new FileOutputStream(newFile);
      BufferedOutputStream out=new BufferedOutputStream(fos,8*1024);
      out.write(data,0,data.length);     
      out.flush();
      out.close();
      fos.close();   
   }// writeMimeFile() ENDS  
   
   //***************************************************************************  
   // Class Variables
   //***************************************************************************  
   private String fileName="";   
   private Dictionary fields;
   private MimeMultiPart mimeMultiPart;
   private HttpServletRequest request;  
   private ServletInputStream inputStream;
}//UploadBean() ENDS