/* $Id: SQLTagsTag.java,v 1.23 2002/08/16 15:46:55 jpoon Exp $
 * $Log: SQLTagsTag.java,v $
 * Revision 1.23  2002/08/16 15:46:55  jpoon
 * make sure startRow is not null
 *
 * Revision 1.22  2002/08/07 15:13:10  jpoon
 * re-construct connection manager
 *
 * Revision 1.21  2002/07/24 19:16:49  jpoon
 * fix paging
 *
 * Revision 1.20  2002/07/17 19:23:58  solson
 * cleaned up the Handler calls.  Modified calls to the handler class and fixed
 * parameters to class ... changed parameter from "Object" to "SQLTags"
 *
 * Revision 1.19  2002/07/02 19:10:21  jpoon
 * change SQLTagsRequest to HttpServletRequest
 *
 * Revision 1.18  2002/06/27 13:34:06  jpoon
 * add setPaging(this.paging)
 * to make sure setPaging is called after setStartRowParameter
 *
 * Revision 1.17  2002/06/24 23:17:19  jpoon
 * remove sqlTagsHandler
 *
 * Revision 1.16  2002/05/23 15:51:11  solson
 * removed all references to caching implementation
 *
 * Revision 1.15  2002/04/10 17:36:52  booker
 * Modifed code to work with the binding of values
 * to the column properties object.
 *
 * Revision 1.14  2002/04/03 14:49:24  booker
 * Worked on adding Object to ColumnProperties.
 *
 * Revision 1.13  2002/03/15 14:33:31  solson
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
import com.aitworks.sqltags.jsptags.ConnectionTag;
import java.io.IOException;
import java.util.Enumeration;
import java.sql.Connection;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
/**  
 * <code>SQLTagsTag</code>  
 * <p>  
 * This class handles the Jsp Tag Support for our SQLTagsTables. 
 * </p>  
 * @author  Booker Northington II  
 * @version 1.0       
 * @since   1.3        
 * @param none <code>none</code> none.  
 * @return none <code>none</code> none.  
 */  
//------------------------------------------------------------------------------  
public abstract class SQLTagsTag extends SQLTags{
//------------------------------------------------------------------------------  
   /**
    * <code>doAfterBody</code>
    * <p>
    * This method is executed after the body has been evaluated. If SKIP_BODY 
    * is returned or the tags body is empty, this method is not called. It is 
    * invoked after the body has been evaluated and is initially invoked by 
    * doStartTag.
    * </p>
    * @author  Booker Northington II
    * @version 1.0
    * @since   1.3
    * @param none <code>none</code> none.
    * @return value <code>int</code> Indicates whether to continue evaluating 
    *        the body.
    */
   //---------------------------------------------------------------------------
   public int doAfterBody(){
   //---------------------------------------------------------------------------
      int returnCode=SKIP_BODY;
      /*
      sqlTagHandler=getSQLTagsHandler();     
     
      if(!sqlTagHandler.preAfterBody(this))
         returnCode=SKIP_BODY;
       */
      if(getWhere().equals("")||hasFetch)
         returnCode=SKIP_BODY;
      else if(fetch())
         returnCode=EVAL_BODY_TAG;
      else
         setLastRecord(true);

      /*
      if(!sqlTagHandler.postAfterBody(this))
         returnCode=SKIP_BODY;
       */
      if(!sqlTagHandler.afterBody(this)) {
          setLastRecord(true);
          close();
          returnCode=SKIP_BODY;
      }
      
      return returnCode;
   }// doAfterBody() ENDS

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
      int returnCode=SKIP_BODY;
      /*
      //sqlTagRequest=new SQLTagsRequest(pageContext);
      sqlTagRequest=(HttpServletRequest)pageContext.getRequest();
      sqlTagHandler=getSQLTagsHandler();
       */
      //in order for paging to work, we have to make sure
      //setStartRowParameter is called before set Paging.
      //therefore we will call setPaging in do start
      //setPaging(getPaging());
      if(paging.equalsIgnoreCase("true")){  
            startRow=Utilities.nvl((String)pageContext.getRequest().getParameter(getStartRowParameter()), "0");  
      }     
      else {
          setPageSize("0");
      }
          
      /*
      if(!sqlTagHandler.preStart(this))
         returnCode=SKIP_BODY;
      else
       */
      {
         pageContext.setAttribute(getId(),this);
         connectionTag=(ConnectionTag)findAncestorWithClass(this,ConnectionTag.class);
         
         if( connectionTag == null ) {
             connectionValid=false;
             log( getTableName() +
             ".doStartTag(): no database connection available.");
             returnCode=SKIP_BODY;
         }
         
         
         setColumnTypes();
         setFirstRecord(true);
         setLastRecord(false);
         
         if(properties.toLowerCase().equals("true"))
             setRequestProperties();
         
         if(!parentName.equals("")&&!foreignKey.equals("")){
             Object sqlTag=null;
             sqlTag=(Object)pageContext.getAttribute(getParentName() );
             Utilities util = new Utilities();
             setWhere( util.callFKWhere(sqlTag,getForeignKey() )+
             " "+getWhere() );
         }
         if( !sqlTagHandler.start(this) ) {
             return SKIP_BODY;
         }
         
         if(! doArrayOperations()) {
             // sqlTagHandler.onError(this);
             return EVAL_BODY_TAG;
         }
         
         if(getWhere().equals(""))
             returnCode=EVAL_BODY_TAG;
         else if(!select(getWhere()))
             returnCode=SKIP_BODY;
         else if(fetch())
             returnCode=EVAL_BODY_TAG;
      }

      /*
      if(!sqlTagHandler.postStart(this))
         returnCode=SKIP_BODY;
       */

      return returnCode;
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
      int returnCode=SKIP_BODY;
      /*
      sqlTagHandler=getSQLTagsHandler();
      
      if(!sqlTagHandler.preEnd(this))
         returnCode=SKIP_BODY;
      else
       */
      {
         sqlTagHandler.end(this);
         try{
            if(!connectionValid)
               connectionValid=true;
            else if(bodyContent!=null){
               bodyContent.writeOut(bodyContent.getEnclosingWriter());
            }
         }
         catch(IOException exception){
            log(getTableName()+".doEndTag(): "+exception);
         }

         pageContext.removeAttribute(getId());
      }

      /*
      if(!sqlTagHandler.postEnd(this))
         returnCode=SKIP_BODY;
      else
       */
         returnCode=EVAL_PAGE;

      return returnCode;
   }// doEndTag() ENDS
      
   private boolean connectionValid=true;
}//SQLTagsTag() ENDS
