/* $Id: ExceptionTag.java,v 1.1 2002/04/05 22:56:36 solson Exp $
 * $Log: ExceptionTag.java,v $
 * Revision 1.1  2002/04/05 22:56:36  solson
 * moved in from com.aitworks.jsptags.conditional, welcome!
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
import com.aitworks.sqltags.interfaces.IExceptionHandler;  
import java.io.IOException;  
import javax.servlet.jsp.tagext.BodyContent;  
import javax.servlet.jsp.JspWriter;  
import javax.servlet.jsp.tagext.BodyTagSupport;  
import javax.servlet.jsp.JspTagException;  
  
/**  
 * <code>ExceptionTag</code>  
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
final public class ExceptionTag extends BodyTagSupport{  
//---------------------------------------------------------------------------  
   //***************************************************************************  
   //Constructors  
   //***************************************************************************  
   /**  
    * <code>ExceptionTag</code>  
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
   public ExceptionTag(){  
   //---------------------------------------------------------------------------  
   }// ExceptionTag() ENDS  
  
   //******************************************************************************  
   //Finalizer Methods  
   //******************************************************************************  
   /**  
    * This is the finalizer method for this class  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   protected void finalize() throws Throwable{  
   //-------------------------------------------------------------------------  
      super.finalize();  
   }// finalizer() ENDS  
  
  
   //***************************************************************************  
   //Public Methods  
   //***************************************************************************  
   /**  
    * <code>doAfterBody</code>  
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
   public int doAfterBody(){  
   //---------------------------------------------------------------------------  
      returnValue=SKIP_BODY;  
  
      // Object object=(Object)pageContext.getAttribute(getName());  
      // BodyContent bodyContent=getBodyContent();  
  
      try{  
	 JspWriter writer=  getBodyContent().getEnclosingWriter();
//	    ((BodyTagSupport)object).getBodyContent().getEnclosingWriter();   
  
	 /*
         BodyContent body=getBodyContent();   
	 String bodyString="";  
  
	 if(bodyContent!=null)  
	    bodyString=body.getString().trim();   
  
	 if(!bodyString.equals(""))  
          */
         StringBuffer genBody = new StringBuffer("");
         if(useFont){
             genBody.append( "<FONT");
             genBody.append( getFontClass() );
             genBody.append( getFontColor() );
             genBody.append( getFontFace() );
             genBody.append( getFontSize() );
             genBody.append( getFontStyle() );
             genBody.append( ">");
             genBody.append( exception.toString() );
             genBody.append( "</FONT>");
         } else {
             genBody.append( exception.toString() );
         }
         writer.println(genBody.toString());  
         writer.flush();
      }  
      catch(IOException ioException){  
	 log("ExceptionTag.doAfterBody: "+ioException);  
      }  
  
      return returnValue;  
   }// dodoAfterBody() ENDS  
  
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

      // SKIP-PAGE is TRUE and we've got an Excption ... then blow-up page
       if(getSkipPage().equalsIgnoreCase("true") && exception!=null){
          return SKIP_PAGE;
      }
      
       // if the CLEAR-EXCEPTION flag is set and we've got an exception (and handler)
       // then we CLEAR the exception ... 
      if(getClearException().equalsIgnoreCase("true")
                && exception!=null
                && exceptionHandler!=null)
      {
         exceptionHandler.setException(null);  
      }

      return EVAL_PAGE;
      
   }//doEndTag() ENDS  
  
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
      exceptionHandler=(IExceptionHandler)pageContext.getAttribute(getName());  
      exception=exceptionHandler.getException();  

      if(exception!=null)  
         returnValue=EVAL_BODY_TAG;  
      else  
         returnValue=SKIP_BODY;  
  
      return returnValue;  
   }// doStartTag() ENDS  
  
   /**  
    * <code>getOutputSource</code>  
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
   public String getName(){  
   //---------------------------------------------------------------------------  
      return name;  
   }// getName() ENDS  
  
   /**  
    * <code>getOutputSource</code>  
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
   public String getOutputSource(){  
   //---------------------------------------------------------------------------  
      return outputSource;  
   }// getOutputSource() ENDS  
  
   /**  
    * <code>getOutputSource</code>  
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
   public String getSkipPage(){  
   //---------------------------------------------------------------------------  
      return skipPage;  
   }// getSkipPage() ENDS  
  
   /**  
    * <code>log</code>  
    * <p>  
    * This method logs errors to netscapes error log.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param message <code>String</code> the error to log.  
    * @return true <code>boolean</code> If there are more records to fetch.  
    */  
   //---------------------------------------------------------------------------  
   public void log(String string){  
   //---------------------------------------------------------------------------  
      pageContext.getServletContext().log(string);  
   }// log() ENDS  
  
   /**  
    * <code>setName</code>  
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
   public void setName(String string){  
   //---------------------------------------------------------------------------  
      name=string;  
   }//setName() ENDS  
  
   /**  
    * <code>setOutputSource</code>  
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
   public void setOutputSource(String string){  
   //---------------------------------------------------------------------------  
      outputSource=string;  
   }// setOutputSource() ENDS  
  
   /**  
    * <code>setSkipPage</code>  
    * <p>  
    * This method sets the skip page attribute.  
    * tag.  
    * </p>  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param skipPage <code>String</code> The skip page attribute.  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public void setSkipPage(String string){  
   //---------------------------------------------------------------------------  
      skipPage=string.trim().toLowerCase();  
   }// setSkipPage() ENDS  
  
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
      String cr="\n";  
      String tab="\t";  
      StringBuffer buffer=new StringBuffer(cr+"*****ExceptionTag: "+cr);  
      buffer.append(tab+"outputSource="+outputSource+cr);  
      buffer.append(tab+"exception="+exception+cr);  
      buffer.append(tab+"exceptionHandler="+exceptionHandler+cr);  
      buffer.append(tab+"name="+name+cr);  
      buffer.append(tab+"skipPage="+skipPage+cr);  
      buffer.append(tab+"returnValue="+returnValue+cr);  
      return buffer.toString();  
   }
   
   /** Getter for property fontClass.
    * @return Value of property fontClass.
    */
   public String getFontClass() {
       if( this.fontClass.equals(""))
           return "";
       return " CLASS=\"" +this.fontClass +"\"";
   }
   
   /** Setter for property fontClass.
    * @param fontClass New value of property fontClass.
    */
   public void setFontClass(String fontClass) {
       this.fontClass = fontClass;
   }
   
   /** Getter for property fontColor.
    * @return Value of property fontColor.
    */
   public String getFontColor() {
       if( this.fontColor.equals(""))
           return "";
       return " COLOR=\"" +this.fontColor +"\"";
   }
   
   /** Setter for property fontColor.
    * @param fontColor New value of property fontColor.
    */
   public void setFontColor(String fontColor) {
       this.fontColor = fontColor;
   }
   
   /** Getter for property fontFace.
    * @return Value of property fontFace.
    */
   public String getFontFace() {
       if( this.fontFace.equals(""))
           return "";
       return " FACE=\"" +this.fontFace +"\"";
   }
   
   /** Setter for property fontFace.
    * @param fontFace New value of property fontFace.
    */
   public void setFontFace(String fontFace) {
       this.fontFace = fontFace;
   }
   
   /** Getter for property fontSize.
    * @return Value of property fontSize.
    */
   public String getFontSize() {
       if( this.fontSize.equals(""))
           return "";
       return " SIZE=\"" +this.fontSize +"\"";
   }
   
   /** Setter for property fontSize.
    * @param fontSize New value of property fontSize.
    */
   public void setFontSize(String fontSize) {
       this.fontSize = fontSize;
   }
   
   /** Getter for property fontStyle.
    * @return Value of property fontStyle.
    */
   public String getFontStyle() {
       if( this.fontStyle.equals(""))
           return "";
       return " STYLE=\"" +this.fontStyle +"\"";
   }
   
   /** Setter for property fontStyle.
    * @param fontStyle New value of property fontStyle.
    */
   public void setFontStyle(String fontStyle) {
       this.fontStyle = fontStyle;
   }
   
   /** Getter for property clearException.
    * @return Value of property clearException.
    */
   public String getClearException() {
       return this.clearException;
   }
   
   /** Setter for property clearException.
    * @param clearException New value of property clearException.
    */
   public void setClearException(String clearException) {
       this.clearException = clearException;
   }
   
// toString() ENDS  
  
   //***************************************************************************  
   //Variables  
   //***************************************************************************  
   private String            outputSource="display";  
   private Exception         exception=null;  
   private IExceptionHandler exceptionHandler;  
   private String            name="";  
   private String            skipPage="false";  
   private int               returnValue=SKIP_BODY;  
   private boolean           useFont= true;
   private String            fontClass="";
   private String            fontColor="RED";
   private String            fontFace="";
   private String            fontSize="";
   private String            fontStyle="";
   private String            clearException="false";
   
}//ExceptionTag() ENDS  
