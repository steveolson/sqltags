/* $Id: SQLTagsRequestTag.java,v 1.2 2002/06/25 21:06:32 jpoon Exp $
 * $Log: SQLTagsRequestTag.java,v $
 * Revision 1.2  2002/06/25 21:06:32  jpoon
 * httprequest to HttpServletRequest
 *
 * Revision 1.1  2002/05/30 17:09:08  jpoon
 * move from utilities
 *
 * Revision 1.1  2002/05/30 16:56:58  jpoon
 * fresh cut
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
import com.aitworks.sqltags.utilities.*;
import java.io.IOException;
import java.io.BufferedReader;
import javax.servlet.ServletInputStream;
import java.util.Enumeration;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletRequest;
import javax.servlet.http.*;

/**
 *
 * @author  johnpoon
 */
public class SQLTagsRequestTag extends SQLTagsRequest {

   //***************************************************************************  
   // Class Constructors  
   //***************************************************************************  
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
    public SQLTagsRequestTag(){
   //---------------------------------------------------------------------------
    }//Constructor ENDS
    
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
   public int doStartTag(){
   //---------------------------------------------------------------------------
       setRequest((HttpServletRequest)pageContext.getRequest());
       setMimeMultiPart();
       setAttribute(getId(), this);
       return EVAL_PAGE;
   }// doStartTag() ENDS

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
      return EVAL_PAGE;
   }// doEndTag() ENDS
}
