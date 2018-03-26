/* $Id: MimePart.java,v 1.14 2002/08/30 17:29:40 solson Exp $
 * $Log: MimePart.java,v $
 * Revision 1.14  2002/08/30 17:29:40  solson
 * reworked to use new HttpMultiPartParser class (replaces MimeMultiPart's internal
 * parser.  Also added some features to MimePart ... could use some more.
 *
 * Revision 1.13  2002/07/29 18:25:57  jpoon
 * updated
 *
 * Revision 1.12  2002/07/26 00:47:59  jpoon
 * file upload
 *
 * Revision 1.11  2002/06/27 15:29:23  solson
 * added overwrite boolean flag to all versions of saveContents
 * exposed some more file methods from mimepart to sqltagsrequest
 *
 * Revision 1.10  2002/05/31 20:03:50  jpoon
 * change contents to contents Vector to support multiple attribute values
 *
 * Revision 1.9  2002/05/30 19:47:02  jpoon
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
import java.io.*;
import java.util.Vector;

public class MimePart {
   //***************************************************************************  
   // Class Members
   //***************************************************************************  
    private Vector contentsVector=new Vector();
    private int contentLength=0;
    private String contentType="text/";
    private String fileExt="";
    private String filename="";
    private String filePath="";
    private boolean isFile=false;
    private int currentIndex=0;

    /** Holds value of property name. */
    private String name;
    
    /** Holds value of property tmpFile. */
    private File tmpFile;
    
   //***************************************************************************  
   // Class Constructors  
   //***************************************************************************  
   /**  
    * <code>Default Constructor</code>  
    * <p>  
    * Creates a class instance.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code>  
    * @return  <code></code> 
    */  
   //---------------------------------------------------------------------------  
    public MimePart(String name) {
   //---------------------------------------------------------------------------  
        setName(name);
    }//MimePart() ENDS
    
   //***************************************************************************  
   // Public Methods
   //***************************************************************************  
   /**  
    * <code>getContentLength</code>  
    * <p>  
    * This method returns the content length.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code>  
    * @return  contentLength<code>String</code> the length of the content.
    */  
   //---------------------------------------------------------------------------  
    public int getContentLength(){
   //---------------------------------------------------------------------------  
       return contentLength;
    }//getContentLength() ENDS

   /**  
    * <code>getContents</code>  
    * <p>  
    * This method returns the content.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code>  
    * @return  contents<code>byte[]</code> an array of bytes representing the content.
    */  
   //---------------------------------------------------------------------------  
    public byte[] getContents(int vectorIndex){
   //---------------------------------------------------------------------------  
       Vector byteVector=new Vector();
       byte[] returnValue=null;
       int size=0;
       int counter=0;
       try {
           if(!isFile()) {
               byteVector=(Vector)contentsVector.elementAt(vectorIndex);
               for(int i=0; i<byteVector.size(); i++)
                   size+=((byte[])byteVector.elementAt(i)).length;
               returnValue=new byte[size];
               
               for(int i=0; i<byteVector.size(); i++) { 
                   byte[] bytes=(byte[])byteVector.elementAt(i);
                   for(int j=0; j<bytes.length; j++) 
                      returnValue[counter++]=bytes[j];        
               }
           }
       }
       catch(Exception exception) {
           System.out.println("MimePart:getContents "+exception);
           return null;
       }
       
       return returnValue;
    }//getContents() ENDS
    
   /**  
    * <code>getContents</code>  
    * <p>  
    * This method returns the content.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code>  
    * @return  contents<code>byte[]</code> an array of bytes representing the content.
    */  
   //---------------------------------------------------------------------------  
    public byte[] getContents(){
   //---------------------------------------------------------------------------  
       return getContents(getCurrentIndex());
    }//getContents() ENDS
    

   /**  
    * <code>getCurrentIndex</code>  
    * <p>  
    * This method returns the current index into the "contents vector".
    * </p>  
    * @author  Steve Olson
    * @param  <code>none</code>  
    * @return  current index into the "contents vector"
    */  
   //---------------------------------------------------------------------------  
    public int getCurrentIndex(){
        return currentIndex;
    }
    
   /**  
    * <code>getContentsVector</code>  
    * <p>  
    * This method returns the content.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code>  
    * @return  contents<code>byte[]</code> an array of bytes representing the content.
    */  
   //---------------------------------------------------------------------------  
    public Vector getContentsVector(){
   //---------------------------------------------------------------------------  
       return contentsVector;
    }//getContentsVector() ENDS
    
   /**  
    * <code>getContentType</code>  
    * <p>  
    * This method returns the content type.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code>  
    * @return  contentType<code>String</code> the content type.
    */  
   //---------------------------------------------------------------------------  
    public String getContentType(){
   //---------------------------------------------------------------------------  
       return contentType;
    }//getContentType() ENDS
    
   /**  
    * <code>getFileExt</code>  
    * <p>  
    * This method returns the file extension.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code>  
    * @return  fileExt<code>String</code> the file extension
    */  
   //---------------------------------------------------------------------------  
    public String getFileExt(){
   //---------------------------------------------------------------------------  
       return fileExt;
    }//getFileExt() ENDS
    
   /**  
    * <code>getFilename</code>  
    * <p>  
    * This method returns the filename.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code>  
    * @return  filename<code>String</code> the filename.
    */  
   //---------------------------------------------------------------------------  
    public String getFilename(){
   //---------------------------------------------------------------------------  
       return filename;
    }//getFilename() ENDS
    
   /**  
    * <code>getFilePath/code>  
    * <p>  
    * This method returns the path to the file.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code>  
    * @return  filePath<code>String</code> the file path.
    */  
   //---------------------------------------------------------------------------  
    public String getFilePath(){
   //---------------------------------------------------------------------------  
       return filePath;
    }//getFilePath() ENDS     
         
   /**  
    * <code>getValue</code>  
    * <p>  
    * This method returns the content value as a string.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code>  
    * @return  <code>String</code> 
    */  
   //---------------------------------------------------------------------------  
    public String getValue(){
   //---------------------------------------------------------------------------  
       String returnValue="";
       
       if(getContents(getCurrentIndex())!=null&&getContentType().startsWith("text/")) 
          returnValue=new String(getContents(getCurrentIndex()));
 
       return returnValue;
    }//getValue() ENDS
    
    
   /**  
    * <code>isFile</code>  
    * <p>  
    * This method returns true if this mime part is a file.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code>  
    * @return isFile<code>boolean</code> false if it is not a file.
    */  
   //---------------------------------------------------------------------------  
    public boolean isFile(){
   //---------------------------------------------------------------------------  
       return isFile;
    }//isFile() ENDS
    
   /**  
    * <code>isFile</code>  
    * <p>  
    * This method sets the state of the isFile paramater
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  booleanValue<code>boolean</code>  true if this mime part is a file.
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
    public boolean isFile(boolean booleanValue){
   //---------------------------------------------------------------------------  
       isFile=booleanValue;
       
       return isFile;
    }//isFile() ENDS    
    
   /**  
    * <code>setContentLength</code>  
    * <p>  
    * This method sets the content length
    * </p>  
    * @author  Booker Northington II  
    * @param contentLength<code>String</code> the new content length
    * @return <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
    void setContentLength(int contentLength){
   //---------------------------------------------------------------------------  
       this.contentLength=contentLength;
    }//setContentLength() ENDS

   /**  
    * <code>setContentType</code>  
    * <p>  
    * This method sets the content type.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  string<code>boolean</code>the content type
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
    public void setContentType(String string){
   //---------------------------------------------------------------------------  
       contentType=string;
    }//setContentType() ENDS
       
   /**  
    * <code>setContents</code>  
    * <p>  
    * This method sets the contents
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  byteArray<code>byte[]</code>  the new contents
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
    public void setContents(Vector byteVector){
   //---------------------------------------------------------------------------  
       contentsVector.addElement(byteVector);
    }//setContents() ENDS
    
   /**  
    * <code>setCurrentIndex</code>  
    * <p>  
    * This method sets the current Index into the "contents vector"
    * </p>  
    * @author  Steve Olson
    * @param  newIndex
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
    public void setCurrentIndex(int newIndex){
   //---------------------------------------------------------------------------  
       currentIndex = newIndex;
    }//setCurrentIndex ENDS 
   /**  
    * <code>setFileExt</code>  
    * <p>  
    * This method sets the file Extension.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  string<code>String</code> the file extension.
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
    public void setFileExt(String string){
   //---------------------------------------------------------------------------  
       fileExt=string;
    }//setFileExt() ENDS
    
   /**  
    * <code>setFilename</code>  
    * <p>  
    * This method sets the filename
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  string<code>String</code> the filename.
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
    public void setFilename(String string){
   //---------------------------------------------------------------------------  
       filename=string;
    }//setFilename() ENDS
    
   /**  
    * <code>setFilePath</code>  
    * <p>  
    * This method sets the file path.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  string<code>String</code> the file path.
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
    public void setFilePath(String string){
   //---------------------------------------------------------------------------  
       filePath=string;
    }//setFilePath() ENDS
           
   /**  
    * <code>toString</code>  
    * <p>  
    * This is the class toString method
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code> 
    * @return string<code>String</code> The class content.
    */  
    /*
   //---------------------------------------------------------------------------  
    public String toString(){
   //---------------------------------------------------------------------------  
       String returnValue="";

       if(getContents()!=null){
          try{
            returnValue=java.net.URLDecoder.decode(new String(getContents()));
          }
          catch(Exception exception){
             returnValue=new String(getContents());
          }
       }
       return returnValue;
    }//toString() ENDS
     */

    
   /**  
    * <code>toString</code>  
    * <p>  
    * This methods write the content to file
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code> 
    * @return string<code>String</code> The class content.
    */  
   //--------------------------------------------------------------------------- 
    public void saveContents(String directory, String filename,boolean overwrite) 
        throws IOException 
    {
   //--------------------------------------------------------------------------- 
        if(directory==null)
            directory=".";

        if(!directory.endsWith(java.io.File.separator))
            directory+=java.io.File.separator;
        
        File d = new File(directory);
        if( !d.exists() ){
            throw new IOException("Directory does not exist: " +directory+ ".");
        }
        
        File f = new File(directory+filename);
        if( f.exists() && !overwrite ){
            throw new IOException("Attempted to overwrite file without Overwrite flag set.");
        }
        
        File newFile = new File ( directory+filename);
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(getTmpFile()));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(newFile));
        
        final int ONE_MB=1024*1024*1;
        byte b[]  = new byte[ONE_MB];
        int n;
        while( (n=bis.read(b,0,b.length)) != -1) {
            bos.write(b,0,n);
        }
        bos.close();
        bis.close();

    }//saveContents()ENDS

   /**  
    * <code>toString</code>  
    * <p>  
    * This methods write the content to file
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code> 
    * @return string<code>String</code> The class content.
    */  
   //--------------------------------------------------------------------------- 
    public void saveContents(String directory) throws IOException {
   //--------------------------------------------------------------------------- 
        saveContents(directory,getFilename(),false);
    }//saveContents()ENDS
    
   //--------------------------------------------------------------------------- 
    public void saveContents(String directory,boolean overwrite) throws IOException {
   //--------------------------------------------------------------------------- 
        saveContents(directory,getFilename(),overwrite);
    }//saveContents()ENDS
    
    /*
     *
    public Vector getByteVector() {
        return (Vector)contentsVector.elementAt(0);
    }
    
        
    public static void writeByteVectorToFile(Vector byteVector, String filename)throws IOException {
        DataOutputStream dataOutputStream=new DataOutputStream(
        new BufferedOutputStream(new FileOutputStream(filename)));
        
        try {
            for(int i=0; i<byteVector.size(); i++) {
                byte[] bytes=(byte[])byteVector.elementAt(i);
                dataOutputStream.write(bytes,0,bytes.length );
            }
        }
        catch (OutOfMemoryError e) {
            System.gc();
            System.out.println("MimeMulitPart.convertFileToBytes: File Too large, out of memeory." +e);
            e.printStackTrace();
        }
        dataOutputStream.close();
    }
     *
     */
    
    /** Getter for property name.
     * @return Value of property name.
     */
    public String getName() {
        return this.name;
    }
    
    /** Setter for property name.
     * @param name New value of property name.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /** Getter for property tmpFile.
     * @return Value of property tmpFile.
     */
    public File getTmpFile() {
        return this.tmpFile;
    }
    
    /** Setter for property tmpFile.
     * @param tmpFile New value of property tmpFile.
     */
    public void setTmpFile(File tmpFile) {
        this.tmpFile = tmpFile;
    }
    
//writeByteVectorToFile() ENDS       
    
}//mimePart() ENDS
