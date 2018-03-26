/* $Id: LastTag.java,v 1.5 2002/05/23 20:30:26 solson Exp $
 * $Log: LastTag.java,v $
 * Revision 1.5  2002/05/23 20:30:26  solson
 * formatting fixes, removed incorrect handler references in first,last,next,
 * previous, etc.
 *
 * Revision 1.4  2002/05/23 15:50:26  solson
 * removed all references to caching implementation
 *
 * Revision 1.3  2002/03/15 14:28:00  solson
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
import com.aitworks.sqltags.utilities.SQLTagsHandler;  
import java.io.IOException;  
import java.sql.SQLException;  
import java.util.Calendar;  
import javax.servlet.jsp.JspWriter;  
import javax.servlet.jsp.tagext.BodyContent;  
import javax.servlet.jsp.tagext.BodyTagSupport;  
  
/**  
 * The <b>LastTag</b> class lets us perform certain actions  
 * when the last record of a set has been read.  
 * @author  Booker Northington II  
 * @version 1.0  
 * @since   JDK1.3  
 * @param   none  
 * @return    
 */  
//---------------------------------------------------------------------------  
final public class LastTag extends BodyTagSupport{  
//---------------------------------------------------------------------------  
   /**  
    * The <b>LastTag</b> class constructor.  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return    
    */  
   //---------------------------------------------------------------------------  
   public LastTag(){  
   //---------------------------------------------------------------------------  
   }//Constructor ENDS()  
  
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
   protected void finalize() throws Throwable {  
   //---------------------------------------------------------------------------  
      super.finalize();  
   }  
  
   /**  
    * The <b>setName</b> method sets the value of the name attribute.  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param name the name value  
    * @return  none  
    */  
   //---------------------------------------------------------------------------  
   public void setName(String name){  
   //---------------------------------------------------------------------------  
      this.name=name;  
      return;  
   }// setName() ENDS  
  
   /**  
    * The <b>getName</b> method is used to return the name attribute.  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public String getName(){  
   //---------------------------------------------------------------------------  
      return name;  
   }// getName() ENDS  
  
   /**  
    * The <b>doAfterBody</b> method is called after the body of the  
    * tag has been processed.  
    * PCM-7  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   name the current field name.  
    * @see     BodyTagSupport  
    * @return  none  
    * @since   JDK1.3  
    */  
   //---------------------------------------------------------------------------  
   public int doAfterBody(){  
   //---------------------------------------------------------------------------  
      return SKIP_BODY;  
   }// doAfterBody() ENDS  
  
   /**  
    * This <b>doEndTag</b> is called when the end tag is hit.  
    * PCM-7  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  The loop status.  
    */  
   //---------------------------------------------------------------------------  
   public int doEndTag(){  
   //---------------------------------------------------------------------------  
       try{
           if(bodyContent!=null)
               bodyContent.writeOut(bodyContent.getEnclosingWriter());
       }
       catch(IOException exception){
           log("LastTag.doEndTag(): "+exception);
       }
       if(parent!=null){
           parent.setLastRecord(false);
           parent.setLastRecordOnPage(false);
       }
       return EVAL_PAGE;
   }// doEndTag() ENDS  
   
   /**  
    * The <b>doStartTag</b> method is called when the start tag is encountered.  
    * PCM-7  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the eval state  
    */  
   //---------------------------------------------------------------------------  
   public int doStartTag(){  
   //---------------------------------------------------------------------------  
       parent=(SQLTags)pageContext.getAttribute(getName());
       
       if(parent==null || !parent.isLastRecord())
           return SKIP_BODY;
       
       return EVAL_BODY_TAG;
   }// doStartTag() ENDS  

  
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
   public void log(String message){  
   //---------------------------------------------------------------------------  
      StringBuffer buffer=new StringBuffer(
                                Calendar.getInstance().getTime().toString()
                                +": "
                                +message
                                );
      pageContext.getServletContext().log(buffer.toString());
      return;
   }// log() ENDS  
  
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
      buffer.append("\tbody="+body);  
      buffer.append("\tname="+name);  
      buffer.append("\tparent="+parent);  
      return buffer.toString();  
   }// toString() ENDS  
  
   //**************************************************************************  
   // Class Variables   
   //**************************************************************************     
   private BodyContent body;  
   private String name;  
   private SQLTags parent;  
}//LastTag() ENDS  
