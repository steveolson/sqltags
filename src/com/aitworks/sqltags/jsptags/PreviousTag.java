/* $Id: PreviousTag.java,v 1.5 2002/06/25 16:05:53 jpoon Exp $
 * $Log: PreviousTag.java,v $
 * Revision 1.5  2002/06/25 16:05:53  jpoon
 * fix paging code
 *
 * Revision 1.4  2002/05/23 20:30:26  solson
 * formatting fixes, removed incorrect handler references in first,last,next,
 * previous, etc.
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
import com.aitworks.sqltags.utilities.Utilities;  
import com.aitworks.sqltags.utilities.SQLTags;  
import com.aitworks.sqltags.utilities.SQLTagsHandler;  
import java.io.IOException;  
import java.sql.SQLException;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.jsp.JspWriter;  
import javax.servlet.jsp.tagext.BodyContent;  
import javax.servlet.jsp.tagext.BodyTagSupport;  
  
/**  
 * The <b>PreviousTag</b> class displays a link which references  
 * a previous page within a set of pages.  
 * @author  Booker Northington II  
 * @version 1.0  
 * @since   JDK1.3  
 * @param   none  
 * @return    
 */  
//------------------------------------------------------------------------------  
final public class PreviousTag extends BodyTagSupport{  
//------------------------------------------------------------------------------  
   //***************************************************************************  
   //                      Constructor section  
   //***************************************************************************  
   /**  
    * The <b>PreviousTag</b> class constructor.  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return    
    */  
   //---------------------------------------------------------------------------  
   public PreviousTag(){  
   //---------------------------------------------------------------------------  
   }//Constructor ENDS()  
  
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
   //                      Class method section  
   //***************************************************************************  
   /**  
    * The <b>doAfterBody</b> method is called after the body of the  
    * tag has been processed.  
    * PCM-3  
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
       if(bodyContent!=null){
           String prev=bodyContent.getString();
           if(prev.trim().length()==0)
               prev="Previous";
           if(parent!=null) {
               int pageSize=parent.getPageSize();
               int startRow=utilities.stringToInt(parent.getStartRow());
               if(startRow>=pageSize)
                   writeToBrowser(prevLink.toString()+prev+"</a>");
           }
       }
        return EVAL_PAGE;
   }// doEndTag() ENDS  
  
   /**  
    * The <b>doStartTag</b> method is called when the start tag is encountered.  
    * PCM-8  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the eval state  
    */  
   //---------------------------------------------------------------------------  
   public int doStartTag(){  
   //---------------------------------------------------------------------------  
        int previousPageCount=0;
        parent=(SQLTags)pageContext.getAttribute(getParentName());
        if(parent==null)
            return SKIP_BODY;
        
        previousPageCount=utilities.stringToInt(parent.getStartRow());
        prevLink.setLength(0);
        
        if(previousPageCount==0){
            return SKIP_BODY;
        }
        
        int pageSize=parent.getPageSize();
        startRow=utilities.stringToInt(parent.getStartRow())-pageSize;
        if(href.indexOf("?")<0)
            prevLink.append("<a href=\""+href+"?"+parent.getStartRowParameter()+"="+startRow);
        else
            prevLink.append("<a href=\""+href+"&"+parent.getStartRowParameter()+"="+startRow);
        prevLink.append("\">");
        
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
      pageContext.getServletContext().log(message);  
      return;  
   }// log() ENDS  
  
   /**  
    * <code>writeToBrowser</code>  
    * <p>  
    * This method writes to the browser  
    * </p>  
    * PCM-3  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param message <code>String</code> the message to write.  
    * @return <code>none</code>   
    */  
   //---------------------------------------------------------------------------  
   public void writeToBrowser(String message){  
   //---------------------------------------------------------------------------  
      try{  
	      pageContext.getOut().println(message);  
      }  
      catch(IOException exception){  
         log("PreviousTag.writeToBrowser: "+exception);  
      }  
  
      return;  
   }// writeToBrowser() ENDS  
  
   //***************************************************************************  
   //                      Accessor section  
   //***************************************************************************  
   /**  
    * The <b>getHref</b> method return the href.  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public String getHref(){  
   //---------------------------------------------------------------------------  
      return href;  
   }// getHref() ENDS  
  
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
    * The <b>getNoHref</b> method is used to return the noHref attribute.  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public String getNoHref(){  
   //---------------------------------------------------------------------------  
      return noHref;  
   }// getNoHref() ENDS  
  
   /**  
    * The <b>getParentName</b> method is used to return the parentName attribute.  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public String getParentName(){  
   //---------------------------------------------------------------------------  
      return parentName;  
   }// getParentName() ENDS  
  
   /**  
    * The <b>getRel</b> method is used to return the rel attribute.  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public String getRel(){  
   //---------------------------------------------------------------------------  
      return rel;  
   }// getRel() ENDS  
  
   /**  
    * The <b>getRev</b> method is used to return the rev attribute.  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public String getRev(){  
   //---------------------------------------------------------------------------  
      return rev;  
   }// getRev() ENDS  
  
   /**  
    * The <b>getTarget</b> method is used to return the target attribute.  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public String getTarget(){  
   //---------------------------------------------------------------------------  
      return target;  
   }// getTarget() ENDS  
  
   /**  
    * The <b>getTitle</b> method is used to return the title attribute.  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public String getTitle(){  
   //---------------------------------------------------------------------------  
      return title;  
   }// getTitle() ENDS  
  
   //***************************************************************************  
   //                      Mutator section  
   //***************************************************************************  
   /**  
    * The <b>setHref</b> method is used to set the href attribute.  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public void setHref(String href){  
   //---------------------------------------------------------------------------  
      this.href=href;  
      return;  
   }// setHref() ENDS  
  
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
    * The <b>setNoHref</b> method is used to set the noHref attribute.  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public void setNoHref(String noHref){  
   //---------------------------------------------------------------------------  
      this.noHref=noHref;  
      return;  
   }// setNoHref() ENDS  
  
   /**  
    * The <b>setParentName</b> method is used to set the parentName attribute.  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public void setParentName(String parentName){  
   //---------------------------------------------------------------------------  
      this.parentName=parentName;  
      return;  
   }// setParentName() ENDS  
  
   /**  
    * The <b>setRel</b> method is used to set the rel attribute.  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public void setRel(String rel){  
   //---------------------------------------------------------------------------  
      this.rel=rel;  
      return;  
   }// setRel() ENDS  
  
   /**  
    * The <b>setRev</b> method is used to set the rev attribute.  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public void setRev(String rev){  
   //---------------------------------------------------------------------------  
      this.rev=rev;  
      return;  
   }// setRev() ENDS  
  
   /**  
    * The <b>setTarget</b> method is used to return the target attribute.  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public void setTarget(String target){  
   //---------------------------------------------------------------------------  
      this.target=target;  
      return;  
   }// setTarget() ENDS  
  
   /**  
    * The <b>setTitle</b> method is used to set the title attribute.  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public void setTitle(String title){  
   //---------------------------------------------------------------------------  
      this.title=title;  
      return;  
   }// setTitle() ENDS  
  
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
      StringBuffer buffer=new StringBuffer("\n*****PreviousTag: ");  
      buffer.append("\tbody="+body);  
      buffer.append("\thref="+href);  
      buffer.append("\tutilities="+utilities);  
      buffer.append("\tname="+name);  
      buffer.append("\tnoHref="+noHref);  
      buffer.append("\tparent="+parent);  
      buffer.append("\tparentName="+parentName);  
      buffer.append("\tprevLink="+prevLink);  
      buffer.append("\trel="+rel);  
      buffer.append("\trev="+rev);  
      buffer.append("\tstartRow="+startRow);  
      buffer.append("\ttarget="+target);  
      buffer.append("\ttitle="+title);  
      return buffer.toString();  
   }// toString() ENDS  
  
   //***************************************************************************  
   //                      Class variable section  
   //***************************************************************************  
   private BodyContent  body;  
   private String       href="";  
   private Utilities    utilities=new Utilities();  
   private String       name="";  
   private String       noHref="";  
   private SQLTags      parent=null;  
   private String       parentName="";  
   private StringBuffer prevLink=new StringBuffer();  
   private String       rel="";  
   private String       rev="";  
   private int          startRow=0;  
   private String       target="";  
   private String       title="";  
}//PreviousTag() ENDS  
