/* $Id: WhereTag.java,v 1.3 2002/05/23 20:30:26 solson Exp $
 * $Log: WhereTag.java,v $
 * Revision 1.3  2002/05/23 20:30:26  solson
 * formatting fixes, removed incorrect handler references in first,last,next,
 * previous, etc.
 *
 * Revision 1.2  2002/03/15 14:27:59  solson
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
import javax.servlet.jsp.JspWriter;  
import javax.servlet.jsp.tagext.BodyContent;  
import javax.servlet.jsp.tagext.BodyTagSupport;  
  
/**  
 * <code>WhereTag</code>  
 * <p>  
 * This class creates a tag which talks to the jsp. It is used to create the   
 * where instruction which is sent to the database.  
 * </p>  
 * @author  Booker Northington II  
 * @version 1.0  
 * @since   1.3  
 * @param none <code>none</code> none.  
 * @see BodyTagSupport <code>BodyTagSupport</code> For more information.  
 * @return none <code>none</code> none.  
 */  
//---------------------------------------------------------------------------  
final public class WhereTag extends BodyTagSupport{  
//---------------------------------------------------------------------------  
   /**  
    * <code>WhereTag</code>  
    * <p>  
    * This is the class default constructor and is used to create an instance   
    * of the class.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public WhereTag(){  
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
      BodyContent body=getBodyContent();
      if(body!=null && parent!=null) {
          String data=body.getString();
          if(data!=null)
              parent.setWhere(data.trim());
          
          if(parent.getTableName().equals("CURSOR"))
              parent.open(data.trim());
      }
      return SKIP_BODY;
   }// doAfterBody() ENDS  
  
   /**  
    * <code>setName</code>  
    * <p>  
    * This method sets the name associated with this tag.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param name <code>String</code> The name associated with this tag.  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public void setName(String name){  
   //---------------------------------------------------------------------------  
      this.name=name;  
   }//setName() ENDS  
   //---------------------------------------------------------------------------  
   public String getName(){  
   //---------------------------------------------------------------------------  
      return name;  
   }// getName() ENDS  
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
      parent=(SQLTags)pageContext.getAttribute(getName());  
  
      if(parent==null)  
         return SKIP_BODY;  
  
      return EVAL_BODY_TAG;  
   }  
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
      StringBuffer buffer=new StringBuffer("\n*****WhereTag: ");  
      buffer.append("\tname="+name);  
      buffer.append("\tparent="+parent);  
      return buffer.toString();  
   }// toString() ENDS  
   //***************************************************************************  
   //                      Class variable section  
   //***************************************************************************  
   protected String name;  
   private SQLTags parent;  
}// WhereTag() ENDS  
