/* $Id: MimeMultiPart.java,v 1.14 2002/08/30 17:29:40 solson Exp $
 * $Log: MimeMultiPart.java,v $
 * Revision 1.14  2002/08/30 17:29:40  solson
 * reworked to use new HttpMultiPartParser class (replaces MimeMultiPart's internal
 * parser.  Also added some features to MimePart ... could use some more.
 *
 * Revision 1.13  2002/07/29 18:25:58  jpoon
 * updated
 *
 * Revision 1.12  2002/07/26 00:51:50  jpoon
 * file upload
 *
 * Revision 1.10  2002/07/02 20:41:37  solson
 * fixed dependency on local filesystem separator.  doesn't work w/ uploads from
 * other sources ... have to look for Unix first then DOS seps.
 *
 * Revision 1.9  2002/06/27 15:29:23  solson
 * added overwrite boolean flag to all versions of saveContents
 * exposed some more file methods from mimepart to sqltagsrequest
 *
 * Revision 1.8  2002/05/31 20:03:50  jpoon
 * change contents to contents Vector to support multiple attribute values
 *
 * Revision 1.7  2002/05/30 19:47:03  jpoon
 * Changes for MimeData
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
package com.aitworks.sqltags.utilities;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.servlet.ServletInputStream;
import java.util.StringTokenizer;

/**  
 * <code><b>MimeMultiPart</b></code>  
 * <p>  
 * MimeMultiPart reads a ServletInputStream. Any mime data found on the 
 * stream is placed within its own MimePart Object. The MimePart objects
 * are stored in a collection. The collection can be accessed thru the
 * parameter name.
 * </p>  
 * @author  Booker Northington II  
 * @version 1.0  
 * @see     MimePart
 * @since   1.3  
 */  
//---------------------------------------------------------------------------  
public class MimeMultiPart{
   //***************************************************************************  
   // Class members
   //***************************************************************************
    public static final int LINE_SIZE=1024;
    private Hashtable mimePartHash=new Hashtable(); 
    private ServletInputStream inputStream;

   //***************************************************************************
   // Class Constructors  
   //***************************************************************************  
    // default constructor ... doesn't do anything ... can't do anything ...
   public MimeMultiPart(){}
   
   /**  
    * <code>Class Constructor</code>  
    * <p>  
    * Creates an instance of MimeMultiParts and its values.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @see     MimePart
    * @since   1.3  
    * @param   inputStream<code>ServletInputStream</code> request input stream.
    * @return  <code>MimeMultiPart</code>
    */  
   //---------------------------------------------------------------------------  
   public MimeMultiPart(ServletInputStream servletInputStream){
   //---------------------------------------------------------------------------
       this.inputStream=servletInputStream;
       // parseMimeData();
       HttpMultiPartParser parser = new HttpMultiPartParser();
       try {
           mimePartHash = parser.parseData( servletInputStream );
       }
       catch ( IllegalArgumentException aEx) {
           System.out.println("MimeMultiPart: illegal arguments: "+ aEx );
       }
       catch ( IOException ioEx) {
           System.out.println("MimeMultiPart: IO exception: "+ ioEx);
       }
        
    }//Constructor ENDS
    
   //***************************************************************************  
   // Public Methods
   //***************************************************************************
   /**  
    * <code>getMimePart</code>  
    * <p>  
    * This method return an object of type MimePart.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @see     MimePart
    * @since   1.3  
    * @param string <code>String</code> the desired mime part
    * @return value<code>MimePart</code>the requested mime part or null.
    */  
   //---------------------------------------------------------------------------  
    public MimePart getMimePart(String string){
   //---------------------------------------------------------------------------  
        return ((MimePart)mimePartHash.get(string));
    }//getMimePart() ENDS   
    
   /**  
    * <code>getMimeParts</code>  
    * <p>  
    * This method returns an enumeration of all the parts obtained from 
    * the request input stream.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @see     Enumeration
    * @since   1.3  
    * @param  <code>none</code>
    * @return value<code>Enumeration</code>an enumeration of mime part keys.
    */  
   //---------------------------------------------------------------------------  
    public Enumeration getMimeParts(){
   //---------------------------------------------------------------------------  
       Enumeration enum=null;
       
       if(mimePartHash!=null)
          enum=mimePartHash.keys();
       
       return enum;
    }//getMimeParts() ENDS  
    
   /**  
    * <code>getContentLength</code>  
    * <p>  
    * This method returns the content length of the parameter name sent in.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  string<code>String</code> the mime part name
    * @return value<code>String</code>the content length or an empty string.
    */  
   //---------------------------------------------------------------------------  
    public int getContentLength(String string){
   //---------------------------------------------------------------------------  
       int returnValue=0;
       MimePart mimePart=getMimePart(string);
       
       if(mimePart!=null)
          returnValue=mimePart.getContentLength();
             
       return returnValue;
    }//getContentLength() ENDS
    
   /**  
    * <code>getContentType</code>  
    * <p>  
    * This method returns the content type of the requested mime part.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  string<code>String</code> the mime part name
    * @return value<code>String</code>the content type or an empty string.
    */  
   //---------------------------------------------------------------------------  
    public String getContentType(String string){
   //---------------------------------------------------------------------------  
       String returnValue="";
       MimePart mimePart=getMimePart(string);
       
       if(mimePart!=null)
          returnValue=mimePart.getContentType();
             
       return returnValue;
    }//getContentType() ENDS
    
   /**  
    * <code>getContents</code>  
    * <p>  
    * This method returns the contents of the requested mime part as an
    * array of bytes.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  string<code>String</code> the mime part name
    * @return value<code>String</code>an array of bytes or null.
    */  
   //---------------------------------------------------------------------------  
    public byte[] getContents(String string){
   //---------------------------------------------------------------------------  
       byte[] returnValue=null;
       MimePart mimePart=getMimePart(string);
       
       if(mimePart!=null) {
           returnValue=mimePart.getContents();
       }
       return returnValue;
    }//getContents() ENDS
    
   /**  
    * <code>getFilename</code>  
    * <p>  
    * This method returns the file name
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  string<code>String</code> the mime part name
    * @return value<code>String</code>the file extension.
    */  
   //---------------------------------------------------------------------------  
    public String getFileExt(String string){
   //---------------------------------------------------------------------------  
       String returnValue="";
       MimePart mimePart=getMimePart(string);
       
       if(mimePart!=null)
          returnValue=mimePart.getFileExt();
             
       return returnValue;
    }//getFileExt() ENDS       
    
   /**  
    * <code>getFilename</code>  
    * <p>  
    * This method returns the file name
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  string<code>String</code> the mime part name
    * @return value<code>String</code>the content length or an empty string.
    */  
   //---------------------------------------------------------------------------  
    public String getFilename(String string){
   //---------------------------------------------------------------------------  
       String returnValue="";
       MimePart mimePart=getMimePart(string);
       
       if(mimePart!=null)
          returnValue=mimePart.getFilename();
             
       return returnValue;
    }//getFilename() ENDS   
    
   /**  
    * <code>getFilename</code>  
    * <p>  
    * This method returns the file path
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  string<code>String</code> the file path
    * @return value<code>String</code>the file extension.
    */  
   //---------------------------------------------------------------------------  
    public String getFilePath(String string){
   //---------------------------------------------------------------------------  
       String returnValue="";
       MimePart mimePart=getMimePart(string);
       
       if(mimePart!=null)
          returnValue=mimePart.getFilePath();
             
       return returnValue;
    }//getFilePath() ENDS       
    
    
   /**  
   * <code>getInputStream</code>  
   * <p>  
   * This method returns a new inputstream.
   * </p>  
   * @author  Booker Northington II  
   * @version 1.0  
   * @since   1.3  
   * @param  <code>none</code>  
   * @return returnValue<code>InputStream</code> the cloned inputstream or null.
   */   
   //---------------------------------------------------------------------------  
    public ServletInputStream getInputStream(){
   //---------------------------------------------------------------------------  
       return inputStream;
    }//getInputStream() ENDS
    
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
       MimePart mimePart=getMimePart(string);

       if(mimePart!=null && mimePart.getContents()!=null)
          returnValue=new String(mimePart.getContents());
       
       return returnValue;
    }//getParameter() ENDS
    
   /**  
    * <code>getParameters</code>  
    * <p>  
    * This method returns an enumeration of all the mime parameters
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code>
    * @return value<code>Enumeration</code>an enumeration of mime keys
    */  
   //---------------------------------------------------------------------------  
    public Enumeration getParameterNames(){
   //---------------------------------------------------------------------------  
       Enumeration returnValue=null;

       if(mimePartHash!=null)
          returnValue=mimePartHash.keys();
       
       return returnValue;
    }//getParameters() ENDS

   /**  
    * <code>isFile</code>  
    * <p>  
    * This method returns true if the mime part is a file.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  string<code>String</code> the mime part name
    * @return value<code>boolean</code>true if the mime part is a file.
    */  
   //---------------------------------------------------------------------------  
    public boolean isFile(String string){
   //---------------------------------------------------------------------------  
       boolean returnValue=false;
       MimePart mimePart=getMimePart(string);
       
       if(mimePart!=null)
           returnValue=mimePart.isFile();          

       return returnValue;
    }//isFile() ENDS 
    
   //***************************************************************************  
   // Private Methods
   //***************************************************************************
   /**  
    * <code>saveContents</code>  
    * <p>  
    * This method write the contents to the file
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param filename <code>String</code> full path to the file to write to.
    * @return returnValue<code>String</code> the mime filename.
    */  
   //---------------------------------------------------------------------------  
   public boolean saveContents(String key, boolean overwrite) throws IOException{
   //---------------------------------------------------------------------------
       return saveContents(key,null,overwrite);
   }
   /**  
    * <code>saveContents</code>  
    * <p>  
    * This method write the contents to the file
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param filename <code>String</code> full path to the file to write to.
    * @return returnValue<code>String</code> the mime filename.
    */  
   //---------------------------------------------------------------------------  
   public boolean saveContents(String key) throws IOException{
   //---------------------------------------------------------------------------
       return saveContents(key, null, false);      
   }//saveContents() ENDS
   
   /**  
    * <code>saveContents</code>  
    * <p>  
    * This method writes the contents to the file
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param filename <code>String</code> full path to the file to write to.
    * @return returnValue<code>String</code> the mime filename.
    */  
   //---------------------------------------------------------------------------  
   public boolean saveContents(String key, String directory, String filename) 
          throws IOException
   {
   //---------------------------------------------------------------------------
       return saveContents(key,directory,filename,false);
   }
  /**  
    * <code>saveContents</code>  
    * <p>  
    * This method writes the contents to the file
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param filename <code>String</code> full path to the file to write to.
    * @return returnValue<code>String</code> the mime filename.
    */  
   //---------------------------------------------------------------------------  
   public boolean saveContents(String key, String directory, String filename, boolean overwrite) 
          throws IOException
   {
   //---------------------------------------------------------------------------
       boolean returnValue=false;
       MimePart mimePart=getMimePart(key);
       
       if(mimePart!=null) {
           returnValue=true;
           mimePart.saveContents(directory, filename, overwrite);
       }

       return returnValue;      
   
   }//saveContents() ENDS
   
   /**  
    * <code>saveContents</code>  
    * <p>  
    * This method write the contents to the file
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param filename <code>String</code> full path to the file to write to.
    * @return returnValue<code>String</code> the mime filename.
    */  
   //---------------------------------------------------------------------------  
   public boolean saveContents(String key, String directory) throws IOException{
   //---------------------------------------------------------------------------
       return saveContents(key,directory,false);
   }
   /**  
    * <code>saveContents</code>  
    * <p>  
    * This method write the contents to the file
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param filename <code>String</code> full path to the file to write to.
    * @return returnValue<code>String</code> the mime filename.
    */  
   //---------------------------------------------------------------------------  
   public boolean saveContents(String key, String directory, boolean overwrite) 
          throws IOException
   {
   //---------------------------------------------------------------------------
       boolean returnValue=false;
       MimePart mimePart=getMimePart(key);
       
       if(mimePart!=null) {
           returnValue=true;
           mimePart.saveContents(directory, overwrite);
       }

       return returnValue;      
   }//saveContents() ENDS
   
}//MimeMultiPart() ENDS