/* $Id: ConnectionTag.java,v 1.20 2002/09/04 18:36:15 jpoon Exp $
 * $Log: ConnectionTag.java,v $
 * Revision 1.20  2002/09/04 18:36:15  jpoon
 * fix connection bug/clean up
 *
 * Revision 1.19  2002/08/19 20:21:44  jpoon
 * fix bug for setters
 *
 * Revision 1.18  2002/08/16 15:47:50  jpoon
 * added new class SQLTagsConnection
 * ConnectionTag extends SQLTagsConnection
 *
 * Revision 1.17  2002/08/13 17:57:17  jpoon
 * fix bug on getConnection()
 *
 * Revision 1.16  2002/08/12 16:47:39  jpoon
 * remove getTagConnection
 *
 * Revision 1.15  2002/08/07 15:13:11  jpoon
 * re-construct connection manager
 *
 * Revision 1.14  2002/06/20 21:00:49  jpoon
 * fix init id and name
 *
 * Revision 1.13  2002/04/06 18:04:52  booker
 * No exception is thrown in setAutoCommit(). Removed
 * Threw Exception in setReadOnly.
 * Modified structure of doStartTag.
 *
 * Revision 1.12  2002/04/05 01:09:53  solson
 * Added commit to doEnd when autoCommit is false to make sure stuff is
 * committed before releasing the connection.  Also pulled in the
 * connectionManager's exception on failure.
 *
 * Revision 1.11  2002/04/03 19:22:05  solson
 * small fix to setAutoCommit(bool) ... set class variable because JSP is calling
 * the boolean method (not the string) one.
 *
 * Revision 1.10  2002/03/20 12:37:21  booker
 * code cleanup.
 *
 * Revision 1.9  2002/03/15 14:27:59  solson
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
package com.aitworks.sqltags.jsptags;  
import com.aitworks.sqltags.utilities.SQLTagsConnection;  
import com.aitworks.sqltags.utilities.ConnectionManager;  
import javax.servlet.jsp.JspException;  
import javax.servlet.jsp.PageContext;  
import javax.servlet.jsp.tagext.BodyContent;  
import javax.servlet.jsp.tagext.BodyTagSupport;  
import javax.servlet.http.*;  
import javax.servlet.*;  
import java.sql.*;  
import java.util.*;  
import java.io.*;  
  
/**  
 * <code>ConnectionTag</code>  
 * <p>  
 * This class is used to establish a connection to the database.  
 * </p>  
 * @author  Booker Northington II  
 * @version 1.0       
 * @since   1.3        
 * @param none <code>none</code> none.  
 * @see TagSupport <code>TagSupport</code> For more information.  
 * @return none <code>none</code> none.  
 */  
//---------------------------------------------------------------------------  
public class ConnectionTag extends SQLTagsConnection{
//---------------------------------------------------------------------------  
   //**************************************************************************  
   // Constructors   
   //**************************************************************************  
   /**  
    * <code>ConnectionTag</code>  
    * <p>  
    * This constructor is responsible for returning an instance of the class.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public ConnectionTag(){  
   //---------------------------------------------------------------------------  
   }// ConnectionTag Constructor() ENDS   
  
   //**************************************************************************  
   // Public Methods  
   //**************************************************************************  
   /**
    * <code>getConnection</code>  
    * <p>  
    * This method returns the connection for this tag.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return connection <code>Connection</code> The connection for this   
    *        tag.  
    */ 
   /**  
    * <code>doEndTag</code>  
    * <p>  
    * This method is called when the end tag is encountered. Any post processing   
    * can be acomplished here.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return value <code>int</code> flag indicating your done.  
    */  
   //---------------------------------------------------------------------------  
   public int doEndTag(){  
   //---------------------------------------------------------------------------    
       try{
           if(bodyContent!=null)
               bodyContent.writeOut(bodyContent.getEnclosingWriter());
       }
       catch(IOException exception){
           exception.printStackTrace();
       }
       
       if(!getAutoCommit()){
           try {
               commit();
           }
           catch (SQLException e) {
               log("ConnectionTag.doEndTag: SQLException: "+e);
           }
       }
       
       close();
       pageContext.removeAttribute(getId());
       return EVAL_PAGE;
       
   }// doEndTag() ENDS  
  
  
   /**  
    * <code>doStartTag</code>  
    * <p>  
    * This method is called when the start tag of the jsp is encountered.  We   
    * make the assumptin that all of your mutators have been set prior to entering   
    * this method. The body of the tag has not been processed you this method   
    * is invoked.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return value <code>int</code> Indicates whether to prosses the body.  
    */  
   //---------------------------------------------------------------------------  
   public int doStartTag() throws JspException{  
   //---------------------------------------------------------------------------  
       init();
       
       if((connection=getConnection())==null) {
           return SKIP_BODY;
       }
       
       pageContext.setAttribute(getId(),this);
       try{
           setAutoCommit();
           setReadOnly();
       }
       catch(SQLException exception){
           log("ConnectionTag.doStartTag(): "+exception);
       }
                  
       return EVAL_PAGE;  
   }// doStartTag() ENDS  
   
}// ConnectionTag() ENDS  