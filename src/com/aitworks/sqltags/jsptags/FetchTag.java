/* $Id: FetchTag.java,v 1.7 2002/07/17 19:23:59 solson Exp $
 * $Log: FetchTag.java,v $
 * Revision 1.7  2002/07/17 19:23:59  solson
 * cleaned up the Handler calls.  Modified calls to the handler class and fixed
 * parameters to class ... changed parameter from "Object" to "SQLTags"
 *
 * Revision 1.6  2002/05/23 20:30:26  solson
 * formatting fixes, removed incorrect handler references in first,last,next,
 * previous, etc.
 *
 * Revision 1.5  2002/05/23 15:50:26  solson
 * removed all references to caching implementation
 *
 * Revision 1.4  2002/04/08 10:28:18  booker
 * The FetchTag dose not support HandlerMethods. All
 * code dealing with this has been removed.
 *
 * Revision 1.3  2002/04/04 13:23:26  booker
 * Added HandlerClass Accessor/Mutators
 * Added HandlerID Accessor/Mutators
 * Added handlerClass/handlerID member variables
 *
 * Revision 1.2  2002/03/15 14:28:00  solson
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
import com.aitworks.sqltags.utilities.SQLTags;  
import java.io.IOException;  
import java.util.Calendar;  
import javax.servlet.jsp.JspWriter;  
import javax.servlet.jsp.tagext.BodyContent;  
import javax.servlet.jsp.tagext.BodyTagSupport;  
import java.util.Hashtable;  
import java.util.Vector;  
  
/**  
 * <code>FetchTag</code>  
 * <p>  
 * This class creates a tag which talks to the jsp. It is used to fetch records   
 * form the database.  
 * </p>  
 * @author  Booker Northington II  
 * @version 1.0  
 * @since   1.3  
 * @param none <code>none</code> none.  
 * @see BodyTagSupport <code>BodyTagSupport</code> For more information.  
 * @return none <code>none</code> none.  
 */  
//---------------------------------------------------------------------------  
final public class FetchTag extends BodyTagSupport{  
//---------------------------------------------------------------------------  
   //***************************************************************************  
   //                      Class Constructor section  
   //***************************************************************************  
   /**  
    * <code>FetchTag</code>  
    * <p>  
    * This is the constructor for the FetchTag. All it dose is return an instance   
    * of the class.  
    * </p>  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code></code>   
    */  
   //---------------------------------------------------------------------------  
   public FetchTag(){  
   //---------------------------------------------------------------------------  
   }// Constructor() ENDS  
  
   //**************************************************************************  
   // Finalize  Method  
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
  
   //***************************************************************************  
   // Public Methods  
   //***************************************************************************  
   /**  
    * <code>doAfterBody</code>  
    * <p>  
    * This method is executed after the body has been evaluated. If SKIP_BODY   
    * is returned or the tags body is empty, this method is not called. It is   
    * invoked after the body has been evaluated and is initially invoked by   
    * doStartTag.  
    * </p>  
    * PCM-4  
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
   	returnCode=fetchCleanup();    
      return returnCode;  
   }// doAfterBody() ENDS  
  
   /**  
    * <code>doEndTag</code>  
    * <p>  
    * This method is called when the end tag is hit.  
    * </p>  
    * PCM-8  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------
   public int doEndTag(){
   //---------------------------------------------------------------------------
      int returnCode=EVAL_PAGE;
      
      try{
          if(bodyContent!=null){
              bodyContent.writeOut(bodyContent.getEnclosingWriter());
          }
      }
      catch(Exception exception){
          log("FetchTag.doEndTag(): "+exception);
      }
      return returnCode;
   }// doEndTag() ENDS
  
   /**  
    * <code>doStartTag</code>  
    * <p>  
    * This method is called when the start tag of the jsp is encountered.  We   
    * make the assumptin that all of your mutators have been set prior to entering   
    * this method. The body of the tag has not been processed you this method   
    * is invoked.  
    * </p>  
    * PCM-11  
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
      parent=(SQLTags)pageContext.getAttribute(getName());

      if(parent==null)
          return SKIP_BODY;
      
      parent.setItemDisplayCount(1);
      parent.setHasFetch("true");
      if(!parent.select(parent.getWhere()))
          return SKIP_BODY;
      
      return fetchCleanup();
   }// doStartTag() ENDS  
  
   /**  
    * <code>fetchCleanup</code>  
    * <p>  
    * This method cleans up performs DML cleanup when the fetch has finished.  
    * </p>  
    * PCM-4  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return value <code>int</code> A value indicating how to continue   
    *        processing the tag.  
    */  
   //---------------------------------------------------------------------------
   public int fetchCleanup(){
   //---------------------------------------------------------------------------
       int returnValue=SKIP_BODY;
       pageFull=false;
       if(parent.fetch()) {
           returnValue=EVAL_BODY_TAG;
           if(!(parent.getSQLTagsHandler().afterBody(parent))) {
               parent.close();
               return SKIP_BODY;
           }
       }
       else {
           parent.setLastRecord(true);
       }
       return returnValue;
   }// fetchCleanup() ENDS  
  
   /**  
    * <code>getName</code>  
    * <p>  
    * This method returns the name associated with this tag.  
    * </p>  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return name <code>String</code> The name associated with this tag.  
    */  
   //---------------------------------------------------------------------------  
   public String getName(){  
   //---------------------------------------------------------------------------  
      return name;  
   }// getName() ENDS  
   
   /**  
    * <code>setName</code>  
    * <p>  
    * This method sets the name associated with this tag.  
    * </p>  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param name <code>String</code> The name associated with this tag.  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public void setName(String string){  
   //---------------------------------------------------------------------------  
      name=string;  
      return;  
   }// setName() ENDS  
  
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
      StringBuffer buffer=new StringBuffer("\n*****FetchTag: ");  
      buffer.append("\tid="+id);  
      buffer.append("\tname="+name);  
      buffer.append("\tpageFull="+pageFull);  
      return buffer.toString();  
   }// toString() ENDS  
  
  
   //***************************************************************************  
   // Private Methods  
   //***************************************************************************  
   /**  
    * <code>log</code>  
    * <p>  
    * This method logs errors to netscapes error log.  
    * </p>  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param message <code>String</code> the error to log.  
    * @return true <code>boolean</code> If there are more records to fetch.  
    */  
   //---------------------------------------------------------------------------  
   private void log(String message){  
   //---------------------------------------------------------------------------  
      StringBuffer buffer=new StringBuffer(  
         Calendar.getInstance().getTime().toString()+": "+message);  
      pageContext.getServletContext().log(buffer.toString());  
      return;  
   }// log() ENDS  
  
   //***************************************************************************  
   //                      Class variable section  
   //***************************************************************************  
   protected String id;  
   protected String name;  
   private boolean pageFull=false;  
   private SQLTags parent;  
}// FetchTag() ENDS  
