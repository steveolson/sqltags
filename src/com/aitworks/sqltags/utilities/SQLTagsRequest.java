/* $Id: SQLTagsRequest.java,v 1.16 2002/07/26 00:47:59 jpoon Exp $
 * $Log: SQLTagsRequest.java,v $
 * Revision 1.16  2002/07/26 00:47:59  jpoon
 * file upload
 *
 * Revision 1.15  2002/07/24 19:16:49  jpoon
 * fix paging
 *
 * Revision 1.14  2002/06/27 15:29:23  solson
 * added overwrite boolean flag to all versions of saveContents
 * exposed some more file methods from mimepart to sqltagsrequest
 *
 * Revision 1.13  2002/06/25 18:57:36  solson
 * changes for SQLTagsRequest (HttpServletRequest instead of ServletRequest)
 * also added pass-thru calls for most of the httpServletRequest calls for
 * convience
 *
 * Revision 1.12  2002/06/21 21:29:39  jpoon
 * fix getAttribute
 *
 * Revision 1.11  2002/06/20 19:35:52  solson
 * fixed getParameter(s,s) ... underlying calls didn't return null ...
 *
 * Revision 1.10  2002/06/20 18:05:50  solson
 * added getParameter(p,default) to return default if p is null ...
 *
 * Revision 1.9  2002/06/17 15:30:26  jpoon
 * initialize request object
 *
 * Revision 1.8  2002/05/31 20:03:50  jpoon
 * change contents to contents Vector to support multiple attribute values
 *
 * Revision 1.7  2002/05/30 19:47:02  jpoon
 * Changes for MimeData
 *
 * Revision 1.6  2002/05/30 16:57:59  jpoon
 * rework from scratch
 *
 * Revision 1.5  2002/05/16 16:38:39  booker
 * Added MIME_MULTI_PART attribute.
 *
 * Revision 1.4  2002/05/15 18:08:09  booker
 * Removed MimeData. Replaced it with MultiMimePart.
 *
 * Revision 1.3  2002/04/11 17:10:22  booker
 * added setSessionAttribute() Method.
 *
 * Revision 1.2  2002/04/10 17:36:52  booker
 * Modifed code to work with the binding of values
 * to the column properties object.
 *
 * Revision 1.1  2002/04/03 14:52:24  booker
 * New class which handles passing of request object
 * data and its associated mime.
 *
 * Revision 1.4  2002/03/20 19:23:37  booker
 * Working on upload and mime
 *
 * Revision 1.3  2002/03/15 14:33:31  solson
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
package com.aitworks.sqltags.utilities;
import java.lang.StringBuffer;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.Enumeration;
import java.util.Vector;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.*;

/**  
 * <code>SQLTagsRequest</code>  
 * <p>  
 * This class holds the request objects data and the mime.
 * </p>  
 * @author Booker Northington II  
 * @version 1.0  
 * @since 1.3 
 */  
public class SQLTagsRequest extends BodyTagSupport{
   //***************************************************************************  
   // Class Constructors  
   //***************************************************************************  
   /**  
    * <code>Default Constructor</code>  
    * <p>  
    * Do Nothing
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code>  
    * @return none 
    */  
   //---------------------------------------------------------------------------  
    public SQLTagsRequest(){
   //---------------------------------------------------------------------------
    }//Constructor ENDS
    
   /**  
    * <code>Constructor</code>  
    * <p>  
    * Sets the requestObject 
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code>  
    * @return none 
    *        to the database.  
    */  
   //---------------------------------------------------------------------------  
    public SQLTagsRequest(HttpServletRequest request){
   //---------------------------------------------------------------------------
        setRequest(request);
        setMimeMultiPart();
    }//Constructor ENDS

   /**  
    * <code>Constructor</code>  
    * <p>  
    * Sets the session and the request.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code>  
    * @return none 
    *        to the database.  
    */  
   //---------------------------------------------------------------------------  
    public SQLTagsRequest(PageContext pageContext){
   //---------------------------------------------------------------------------
        setRequest((HttpServletRequest)pageContext.getRequest());      
        setMimeMultiPart();
    }//Constructor ENDS
   
   /**  
    * <code>getAttribute</code>  
    * <p>  
    * This method returns the field
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param string<code>String</code> the field.
    * @return field <code>String</code> the field value
    */  
   //---------------------------------------------------------------------------  
    public Object getAttribute(String string){
   //---------------------------------------------------------------------------  
        Object object=request.getAttribute(string);
       
        //if we did not find the attribute, check mime data.
        //if(object==null)
        //    object=(Object)mimeMultiPart.getParameter(string);
        
        return object;
    }//getAttribute() ENDS
    
   /**  
    * <code>getAttributeNames</code>  
    * <p>  
    * This method returns attributeNames
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none<code>none</code>
    * @return attributeNames<code>Enumeration</code> 
    */  
   //---------------------------------------------------------------------------  
    public Enumeration getAttributeNames(){
   //---------------------------------------------------------------------------  
        Enumeration object=request.getAttributeNames();
         
        //if we did not find the attribute, check mime data.
        if(object==null)
            object=mimeMultiPart.getMimeParts();
        
        return object;
    }//getAttributeNames() ENDS
    
   /**  
    * <code>getCharacterEncoding</code>  
    * <p>  
    * This method returns the name of the character encoding
    * used in the body of this request
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>none</code>
    * @return string <code>String</code> the remote address.
    */  
   //---------------------------------------------------------------------------  
    public String  getCharacterEncoding(){
   //---------------------------------------------------------------------------  
        return request.getCharacterEncoding();
    }//getCharacterEncoding() ENDS
    
   /**  
    * <code>getContentLength</code>  
    * <p>  
    * This method returns length, in bytes, of the request body and
    * made available by the input Stream, or -1 if the length is not known
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>none</code>
    * @return string <code>String</code> the remote address.
    */  
   //---------------------------------------------------------------------------  
    public int getContentLength(){
   //---------------------------------------------------------------------------  
        return request.getContentLength();
    }//getContentLength() ENDS

   /**  
    * <code>getContentLength</code>  
    * <p>  
    * This method returns length, in bytes, of the request body and
    * made available by the input Stream, or -1 if the length is not known
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>none</code>
    * @return string <code>String</code> the remote address.
    */  
   //---------------------------------------------------------------------------  
    public int getContentLength(String string){
   //---------------------------------------------------------------------------  
        return mimeMultiPart.getContentLength(string);
    }//getContentLength() ENDS
    
   /**  
    * <code>getContents</code>  
    * <p>  
    * This method returns contents, in bytes, of mimeMultiPartBody
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>none</code>
    * @return string <code>String</code> the remote address.
    */  
   //---------------------------------------------------------------------------  
    public byte[] getContents(String string){
   //---------------------------------------------------------------------------  
        return mimeMultiPart.getContents(string);
    }//getContents() ENDS
    
       /**  
    * <code>getContentType</code>  
    * <p>  
    * This method returns contentType
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>none</code>
    * @return string <code>String</code> the remote address.
    */  
   //---------------------------------------------------------------------------  
    public String getContentType(String string){
   //---------------------------------------------------------------------------  
        return mimeMultiPart.getContentType(string);
    }//getContentType() ENDS
    
  /**  
    * <code>getFileExt</code>  
    * <p>  
    * This method returns FileExt for specified item
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>none</code>
    * @return string <code>String</code> the remote address.
    */  
   //---------------------------------------------------------------------------  
    public String getFileExt(String string){
   //---------------------------------------------------------------------------  
        return mimeMultiPart.getFileExt(string);
    }//getFileExt() ENDS
    
  /**  
    * <code>getFilename</code>  
    * <p>  
    * This method returns FileExt for specified item
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>none</code>
    * @return string <code>String</code> the remote address.
    */  
   //---------------------------------------------------------------------------  
    public String getFilename(String string){
   //---------------------------------------------------------------------------  
        return mimeMultiPart.getFilename(string);
    }//getFilename() ENDS
    
  /**  
    * <code>getFilePath</code>  
    * <p>  
    * This method returns FilePath for specified item
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>none</code>
    * @return string <code>String</code> the remote address.
    */  
   //---------------------------------------------------------------------------  
    public String getFilePath(String string){
   //---------------------------------------------------------------------------  
        return mimeMultiPart.getFilePath(string);
    }//getFilePath() ENDS
    
  /**  
    * <code>isFile</code>  
    * <p>  
    * This method returns isFile for specified item
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>none</code>
    * @return string <code>String</code> the remote address.
    */  
   //---------------------------------------------------------------------------  
    public boolean isFile(String string){
   //---------------------------------------------------------------------------  
        return mimeMultiPart.isFile(string);
    }//isFile() ENDS
    
  /**  
    * <code>getContentType</code>  
    * <p>  
    * This method returns contentType of request
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>none</code>
    * @return string <code>String</code> the remote address.
    */  
   //---------------------------------------------------------------------------  
    public String  getContentType(){
   //---------------------------------------------------------------------------  
        return request.getContentType();
    }//getContentType() ENDS

   /**  
    * <code>getId</code>  
    * <p>  
    * This method returns id
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>none</code>
    * @return string <code>String</code> the remote address.
    */  
   //---------------------------------------------------------------------------  
    public String  getId(){
   //---------------------------------------------------------------------------  
        return id;
    }//getCId() ENDS
    
   /**  
    * <code>getInputStream</code>  
    * <p>  
    * This method Retrieves the body of the request as binary 
    * data using a ServletInputStream.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>none</code>
    * @return string <code>String</code> the remote address.
    */  
   //---------------------------------------------------------------------------  
    public ServletInputStream  getInputStream(){
   //---------------------------------------------------------------------------  
        try {
           return request.getInputStream();
        }
        catch(java.io.IOException exception)
        {
            return mimeMultiPart.getInputStream();
        }
    }//getInputStream() ENDS

   /**  
    * <code>getLocale</code>  
    * <p>  
    * This method returns an Enumeration of Locale objects indicating, 
    * in decreasing order starting with the preferred locale, the locales 
    * that are acceptable to the client based on the Accept-Language header.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>none</code>
    * @return string <code>String</code> the remote address.
    */  
   //---------------------------------------------------------------------------  
    public Enumeration getLocales(){
   //---------------------------------------------------------------------------  
        return request.getLocales();
    }//getProtocol() ENDS
        
    /**  
    * <code>getMimeMultiPart</code>  
    * <p>  
    * This method returns the multiPartMime.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>none</code>
    * @param mimeMultiPart<code>MimeMultiPart</code> the mimeMultiPart object
    * @return <code>none</code>
    */  
   //---------------------------------------------------------------------------  
    public MimeMultiPart getMimeMultiPart(){
   //---------------------------------------------------------------------------          
        return this.mimeMultiPart;      
    }//getMimeMultiPart() ENDS
    
   /**  
    * <code>getParameter</code>  
    * <p>  
    * This method returns the field
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param string<code>String</code>
    * @return string<code>String</code> the field value
    */  
   //---------------------------------------------------------------------------  
    public String getParameter(String string){
   //---------------------------------------------------------------------------  
        String object=request.getParameter(string);
        
        //if we did not find the parameter, check mime data.
        if(object==null)
            object=mimeMultiPart.getParameter(string);

        return object;
    }//getParameter() ENDS
    
   /**  
    * <code>getParameter</code>  
    * <p>  
    * This method returns the field
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param string<code>String</code>
    * @return string<code>String</code> the field value
    */  
   //---------------------------------------------------------------------------  
    public String getParameter(String string,String defaultValue){
   //---------------------------------------------------------------------------  
        String value=getParameter(string);
        if(value==null || value.trim().length()==0)
            return defaultValue;
        return value;
    }//getParameter() ENDS
    
    /**  
    * <code>getParameterName</code>  
    * <p>  
    * This method returns the field
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param None<code>None</code>
    * @return parameterNames<code>Enumeration</code> the field value
    */  
   //---------------------------------------------------------------------------  
    public Enumeration getParameterNames(){
   //---------------------------------------------------------------------------
        Vector vector=new Vector();
        
        Enumeration object=request.getParameterNames();
        if(object!=null) {
            while(object.hasMoreElements()) {
                vector.addElement(object.nextElement());
            }
        }
        
        //also get ParameterNames from mimeMultiPart
        object=mimeMultiPart.getParameterNames();
        if(object!=null) {
            while(object.hasMoreElements()) {
                vector.addElement(object.nextElement());
            }
        }    
        
        return (vector.elements());
    }//getParameterNames() ENDS

   /**  
    * <code>getParameterValues</code>  
    * <p>  
    * This method returns ParameterValues from both request and mimeMultiPart
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param string<code>String</code>
    * @return ParameterValues<code>String[]</code> the field value
    */  
   //---------------------------------------------------------------------------  
    public String[] getParameterValues(String string){
   //---------------------------------------------------------------------------  
        String[] object=request.getParameterValues(string);
        MimePart mimePart;
        int contentsVectorSize=0;
        
        if(object==null) {        
            mimePart=mimeMultiPart.getMimePart(string);
            if(mimePart!=null)
               contentsVectorSize=mimePart.getContentsVector().size();
            object=new String[contentsVectorSize];
            for(int i=0; i<contentsVectorSize; i++) {
                if(mimePart.getContents(i)!=null)
                   object[i]=new String(mimePart.getContents(i));
                else 
                   object[i]=new String("");
            }
                
        }
        return object;
    }//getParameterValues() ENDS
    
   /**  
    * <code>getProtocol</code>  
    * <p>  
    * This method returns athe name and version of the protocol 
    * the request uses in the form protocol/majorVersion.minorVersion, 
    * for example, HTTP/1.1.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>none</code>
    * @return string <code>String</code> the remote address.
    */  
   //---------------------------------------------------------------------------  
    public String getProtocol(){
   //---------------------------------------------------------------------------  
        return request.getProtocol();
    }//getProtocol() ENDS

   /**  
    * <code>getReader</code>  
    * <p>  
    * This method Retrieves the body of the request as character 
    * data using a BufferedReader
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>none</code>
    * @return BufferedReader<code>BufferedReader</code>.
    */  
   //---------------------------------------------------------------------------  
    public BufferedReader getReader(){
   //---------------------------------------------------------------------------  
        BufferedReader returnValue=null;
        
        try {
           returnValue=request.getReader();
        }
        catch(IOException exception)
        {
           setException(exception);
           System.out.println("SQLTagsRequest:getReader "+exception);
        }
        
        return returnValue;
    }//getReader() ENDS
    
    /**  
    * <code>getRemoteAddr</code>  
    * <p>  
    * This method returns the field
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>none</code>
    * @return string <code>String</code> the remote address.
    */  
   //---------------------------------------------------------------------------  
    public String  getRemoteAddr(){
   //---------------------------------------------------------------------------  
        return request.getRemoteAddr();
    }//getRemoteAddr() ENDS

    /**  
    * <code>getRemoteHost</code>  
    * <p>  
    * This method returns the field
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>none</code>
    * @return string <code>String</code> the remote host.
    */  
   //---------------------------------------------------------------------------  
    public String getRemoteHost(){
   //---------------------------------------------------------------------------  
        return request.getRemoteHost();
    }//getRemoteHost() ENDS

   /**  
    * <code>getRequestDispatcher</code>  
    * <p>  
    * This method returns a RequestDispatcher object that acts as a wrapper for 
    * the resource located at the given path
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>none</code>
    * @return RequestDispatcher<code>RequestDispatcher</code> the request dispatcher.
    */  
   //---------------------------------------------------------------------------  
    public RequestDispatcher getRequestDispatcher(String path){
   //---------------------------------------------------------------------------  
        return request.getRequestDispatcher(path);
    }//getRequestDispatcher() ENDS

   /**  
    * <code>getScheme</code>  
    * <p>  
    * This method returns the name of the scheme used to make this request, 
    * for example, http, https, or ftp.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>none</code>
    * @return string <code>String</code> the scheme.
    */  
   //---------------------------------------------------------------------------  
    public String getScheme(){
   //---------------------------------------------------------------------------  
        return request.getScheme();
    }//getScheme() ENDS

   /**  
    * <code>getServerName</code>  
    * <p>  
    * This method returns the host name of the server that received the request.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>none</code>
    * @return string <code>String</code> the serverName.
    */  
   //---------------------------------------------------------------------------  
    public String getServerName(){
   //---------------------------------------------------------------------------  
        return request.getServerName();
    }//getServerName() ENDS

   //---------------------------------------------------------------------------  
    public String getAuthType(){return request.getAuthType();}
    public String getContextPath(){return request.getContextPath();}
    public Cookie[] getCookies(){return request.getCookies();}
    public long getDateHeader(String s){return request.getDateHeader(s);}
    public String getHeader(String s){return request.getHeader(s);}
    public Enumeration getHeaderNames(){return request.getHeaderNames();}
    public Enumeration getHeaders(String s){return request.getHeaders(s);}
    public int getIntHeader(String s){return request.getIntHeader(s);}
    public String getMethod(){return request.getMethod();}
    public String getPathInfo(){return request.getPathInfo();}
    public String getPathTranslated(){return request.getPathTranslated();}
    public String getQueryString(){return request.getQueryString();}
    public String getRemoteUser(){return request.getRemoteUser();}
    public String getRequestedSessionId(){return request.getRequestedSessionId();}
    public String getRequestURI(){return request.getRequestURI();}
    // public StringBuffer getRequestURL(){return request.getRequestURL();}
    public String getServletPath(){return request.getServletPath();}
    public HttpSession getSession(){return request.getSession();}
    public HttpSession getSession(boolean b){return request.getSession(b);}
    public java.security.Principal getUserPrincipal(){return request.getUserPrincipal();}
    public boolean isRequestedSessionIdFromCookie(){ return request.isRequestedSessionIdFromCookie();}
    public boolean isRequestedSessionIdFromURL(){ return request.isRequestedSessionIdFromURL();}
    public boolean isRequestedSessionIdValid(){ return request.isRequestedSessionIdValid();}
    public boolean isUserInRole(String r){return request.isUserInRole(r);}
   //---------------------------------------------------------------------------  
    
    /**  
    * <code>getServerPort</code>  
    * <p>  
    * This method returns the port number on which this request was received.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>none</code>
    * @return string <code>String</code> the server Port.
    */  
   //---------------------------------------------------------------------------  
    public int getServerPort(){
   //---------------------------------------------------------------------------  
        return request.getServerPort();
    }//getServerPort() ENDS
    
   /**  
    * <code>isSecure</code>  
    * <p>  
    * This method returns a boolean indicating whether this request was made 
    * using a secure channel, such as HTTPS.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>none</code>
    * @return boolean<code>boolean</code>
    */  
   //---------------------------------------------------------------------------  
    public boolean isSecure(){
   //---------------------------------------------------------------------------  
        return request.isSecure();
    }//isSecure() ENDS
    
   /**  
    * <code>removeAttribute</code>  
    * <p>  
    * This method returns the field
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param string<code>String</code>the key
    * @return <code>none</code>
    */  
   //---------------------------------------------------------------------------  
    public void removeAttribute(String string){
   //---------------------------------------------------------------------------  
        request.removeAttribute(string);
    }//removeAttribute() ENDS
    
   /**  
    * <code>setAttribute</code>  
    * <p>  
    * This method returns the field
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param string<code>String</code> the key
    * @param object<code>Object</code> the value
    * @return <code>none</code>
    */  
   //---------------------------------------------------------------------------  
    public void setAttribute(String string,Object object){
   //---------------------------------------------------------------------------  
        request.setAttribute(string,object);
    }//setAttribute() ENDS

   /**  
    * <code>setId</code>  
    * <p>  
    * This method returns the field
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param string<code>String</code> the id
    * @return <code>none</code>
    */  
   //---------------------------------------------------------------------------  
    public void setId(String string){
   //---------------------------------------------------------------------------  
        this.id=string;
    }//setId() ENDS
   
   /**  
    * <code>saveContents</code>  
    * <p>  
    * This method write the contents to the file
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param string<code>String</code> the key
    * @return boolean<code>Boolean</code>
    */  
   //---------------------------------------------------------------------------  
   public boolean saveContents(String key) {
   //---------------------------------------------------------------------------
       return saveContents(key,false);
   }
   /**  
    * <code>saveContents</code>  
    * <p>  
    * This method write the contents to the file
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param string<code>String</code> the key
    * @return boolean<code>Boolean</code>
    */  
   //---------------------------------------------------------------------------  
   public boolean saveContents(String key, boolean overwrite) {
   //---------------------------------------------------------------------------
      return saveContents(key, null, overwrite);
   }//saveContents() ENDS
   
   /**  
    * <code>saveContents</code>  
    * <p>  
    * This method write the contents to the file
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param string <code>String</code> the key.
    * @param string <code>String</code> the directory.
    * @return boolean<code>Boolean</code> 
    */  
   //---------------------------------------------------------------------------  
   public boolean saveContents(String key, String directory) {
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
    * @param string <code>String</code> the key.
    * @param string <code>String</code> the directory.
    * @return boolean<code>Boolean</code> 
    */  
   //---------------------------------------------------------------------------  
   public boolean saveContents(String key, String directory,boolean overwrite) {
   //---------------------------------------------------------------------------
       try {
          return mimeMultiPart.saveContents(key, directory,overwrite);      
       }
       catch(IOException exception) {
           setException(exception);
           System.out.println("SQLTagsRequest:saveContents "+exception);
           return false;
       }
   }//saveContents() ENDS
   
   /**  
    * <code>saveContents</code>  
    * <p>  
    * This method write the contents to the file
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param string <code>String</code> the key.
    * @param string <code>String</code> the directory.
    * @param string <code>String</code> the filename.
    * @return boolean<code>Boolean</code> 
    */  
   //---------------------------------------------------------------------------  
   public boolean saveContents(String key, String directory, String filename) {
   //---------------------------------------------------------------------------
       return saveContents(key,directory,filename,false);
   }
   
   /**  
    * <code>saveContents</code>  
    * <p>  
    * This method write the contents to the file
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param string <code>String</code> the key.
    * @param string <code>String</code> the directory.
    * @param string <code>String</code> the filename.
    * @return boolean<code>Boolean</code> 
    */  
   //---------------------------------------------------------------------------  
   public boolean saveContents(String key, String directory, String filename,boolean overwrite) {
   //---------------------------------------------------------------------------
       try {
          return mimeMultiPart.saveContents(key, directory, filename,overwrite);      
       }
       catch(IOException exception) {
           setException(exception);
           System.out.println(exception);
           return false;
       }
   }//saveContents() ENDS
   
   //---------------------------------------------------------------------------  
    public Exception getException(){
   //---------------------------------------------------------------------------  
        return this.exception;
    }

    
   //---------------------------------------------------------------------------  
    public void setException(Exception e){
   //---------------------------------------------------------------------------  
        this.exception= e;
    }
  /**  
    * <code>setMimeData</code>  
    * <p>  
    * This method returns the field
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>none</code>
    * @return <code>none</code>
    */  
   //---------------------------------------------------------------------------  
    public void setMimeMultiPart(){
   //---------------------------------------------------------------------------  
        if(request==null)
            return;
        try {
           mimeMultiPart = (MimeMultiPart)request.getAttribute(MIME_MULTI_PART);
           if(mimeMultiPart==null) {
               //dummy code to initialize request
               request.getParameter("SQLTagsRequest");
               
               if(request.getContentLength()>0)
                  mimeMultiPart=new MimeMultiPart(request.getInputStream());
               else
                  mimeMultiPart=new MimeMultiPart();
               request.setAttribute(MIME_MULTI_PART, mimeMultiPart);
           }
           else         
           this.mimeMultiPart=mimeMultiPart;
        }
        catch(IOException exception) {
            setException(exception);
            // exception.printStackTrace();
        }
    }//setMimeMultiPart() ENDS
    
   /**  
    * <code>setRequest</code>  
    * <p>  
    * This method sets request
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>ServletRequest</code>
    * @return <code>none</code>
    */  
   //---------------------------------------------------------------------------  
    public void setRequest(HttpServletRequest request){
   //---------------------------------------------------------------------------  
       this.request=request;
    }//setRequest() ENDS
    
   //***************************************************************************  
   // Class Members
   //***************************************************************************      
    public static final String MIME_MULTI_PART="mimeMultiPart";
    private MimeMultiPart mimeMultiPart;
    private HttpServletRequest request;
    public String id="";
    private Exception exception;
}//SQLTagsRequest
