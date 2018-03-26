/* $Id: CursorTag.java,v 1.9 2002/08/13 17:58:38 jpoon Exp $
 * $Log: CursorTag.java,v $
 * Revision 1.9  2002/08/13 17:58:38  jpoon
 * fixed bug sql declare "" instead of null
 *
 * Revision 1.8  2002/08/08 19:38:38  jpoon
 * add hasFetch for cursorTag
 *
 * Revision 1.7  2002/08/08 18:13:14  jpoon
 * fix bug, sql="..." will loop
 *
 * Revision 1.6  2002/08/07 15:13:11  jpoon
 * re-construct connection manager
 *
 * Revision 1.5  2002/07/17 19:23:59  solson
 * cleaned up the Handler calls.  Modified calls to the handler class and fixed
 * parameters to class ... changed parameter from "Object" to "SQLTags"
 *
 * Revision 1.4  2002/05/23 15:50:26  solson
 * removed all references to caching implementation
 *
 * Revision 1.3  2002/03/15 14:27:59  solson
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
import com.aitworks.sqltags.utilities.Cursor;  
import com.aitworks.sqltags.utilities.SQLTags;  
import com.aitworks.sqltags.utilities.SQLTagsHandler;  
import java.io.*;  
import javax.servlet.jsp.*;  
import javax.servlet.jsp.tagext.*;  
  
/**  
 * <code>CursorTag</code>  
 * <p>  
 * This class creates a tag which talks to the jsp. It is used to create the   
 * actual cursor tag which communicates with the database.  
 * </p>  
 * @author  Booker Northington II  
 * @version 1.0  
 * @since   1.3  
 * @param none <code>none</code> none.  
 * @see Cursor <code>Cursor</code> For more information.  
 * @return none <code>none</code> none.  
 */  
//---------------------------------------------------------------------------  
final public class CursorTag extends Cursor{  
//---------------------------------------------------------------------------  
   //**************************************************************************  
   // Constructor  
   //**************************************************************************  
   /**  
    * <code>CursorTag</code>  
    * <p>  
    * This is the default class constructor and is used to return an instance   
    * of the class.  
    * </p>  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public CursorTag(){  
   //---------------------------------------------------------------------------  
   }  
  
   //**************************************************************************  
   // Finalize Method  
   //**************************************************************************  
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
   protected void finalize() throws Throwable{  
   //---------------------------------------------------------------------------  
      super.finalize();  
   }// finalize() ENDS  
  
  
   //**************************************************************************  
   // Public Methods  
   //**************************************************************************  
   /**  
    * <code>setSql</code>  
    * <p>  
    * This method sets sql statement.  
    * </p>  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param item <code>String</code> The new sql statement.  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public void setSql(String string){  
   //---------------------------------------------------------------------------  
      sql=string;  
   }  
  
   /**  
    * <code>getSql</code>  
    * <p>  
    * This method returns the sql statement associated with this instance..  
    * </p>  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return sql <code>String</code> The current sql statement.  
    */  
   //---------------------------------------------------------------------------  
   public String getSql(){  
   //---------------------------------------------------------------------------  
      return sql;  
   }  
  
   /**  
    * <code>setID</code>  
    * <p>  
    * This method sets the name of the scripting variable associated with this   
    * tag.  
    * </p>  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param id <code>String</code> The name of the scripting variable.  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public void setId(String string){  
   //---------------------------------------------------------------------------  
      id=string;  
   }// setId() ENDS  
  
   /**  
    * <code>getID</code>  
    * <p>  
    * This method returns the scripting variable associated with the class.  
    * </p>  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return id <code>String</code> The scripting variable name.  
    */  
   //---------------------------------------------------------------------------  
   public String getId(){  
   //---------------------------------------------------------------------------  
      return id;  
   }// getId() ENDS  
  
   /**  
    * <code>doEndTag</code>  
    * <p>  
    * This method is called when the end tag is encountered. Any post processing   
    * can be acomplished here.  
    * </p>  
    * PCM-8  
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
      // parent=(SQLTags)pageContext.getAttribute(getId());  
      /*
      SQLTagsHandler sqlTagHandler=parent.getSQLTagsHandler();  
  
      if(!sqlTagHandler.preEnd(this))  
         returnCode=SKIP_BODY;  
      else{  
       */
      sqlTagHandler.end(this);
      {
	 try{  
	    if(bodyContent!=null);  
	       bodyContent.writeOut(bodyContent.getEnclosingWriter());  
	 }  
	 catch(IOException exception){  
	    log("CursorTag.doEndTag: "+exception);  
	 }  
  
	 pageContext.removeAttribute(getId());  
	 close();  
  
         /*
	 if(!sqlTagHandler.postEnd(this))  
	    returnCode=SKIP_BODY;  
	 else  
          */
	    returnCode=EVAL_PAGE;  
  
      }  
  
      return returnCode;  
   }// doEndTag() ENDS  
  
   /**  
    * <code>doStartTag</code>  
    * <p>  
    * This method is called when the start tag of the jsp is encountered.  We   
    * make the assumption that all of your mutators have been set prior   
    * to entering  this method. The body of the tag has not been   
    * processed you this method   
    * is invoked.  
    * </p>  
    * PCM-4  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return value <code>int</code> Indicates whether to prosses the body.  
    */  
   //---------------------------------------------------------------------------  
   public int doStartTag(){  
   //---------------------------------------------------------------------------  
      int returnCode=EVAL_BODY_TAG;  
      pageContext.setAttribute(getId(),this);
      
      connectionTag=(ConnectionTag)findAncestorWithClass(this,ConnectionTag.class);
      setColumnTypes();
      
      if(connectionTag==null)
          return SKIP_BODY;
      
      if(!sqlTagHandler.start(this))
          returnCode=SKIP_BODY;
      
      if(getSql().equals(""))
          returnCode=EVAL_BODY_TAG;
      else if(!select(getSql()))
          returnCode=SKIP_BODY;
      else if(fetch())
          returnCode=EVAL_BODY_TAG;
      
      return returnCode;  
   }// doStartTag() ENDS  

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
      if(getSql().equals("")||hasFetch)
         returnCode=SKIP_BODY;
      else if(fetch())
         returnCode=EVAL_BODY_TAG;
      else
         setLastRecord(true);

      if(!sqlTagHandler.afterBody(this)) {
          setLastRecord(true);
          close();
      }
      
      return returnCode;
   }// doAfterBody() ENDS
   
   /**  
    * <code>toString</code>  
    * <p>  
    * This method provides a default print for the class.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>none</code>  
    */  
   //---------------------------------------------------------------------------  
   public String toString(){  
   //---------------------------------------------------------------------------  
      StringBuffer buffer=new StringBuffer("\n*****CursorTag: ");  
      buffer.append("\tid="+id);  
      buffer.append("\tsql="+sql);  
      // buffer.append("\tconnection+="+parent);  
      return buffer.toString();  
   }// toString() ENDS  
  
   //**************************************************************************  
   // Class Variables   
   //**************************************************************************  
   private String sql="";  
   private String id;  
   // private SQLTags parent;  
}//CursorTag() ENDS  
