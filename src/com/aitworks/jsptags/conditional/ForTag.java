package com.aitworks.jsptags.conditional;  
import javax.servlet.jsp.*;  
import javax.servlet.jsp.tagext.*;  
import java.io.*;  
  
//---------------------------------------------------------------------------  
final public class ForTag extends ConditionalStatement{  
//---------------------------------------------------------------------------  
   //***************************************************************************  
   //Finalizer Method  
   //***************************************************************************  
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
    * This method is executed after the body has been evaluated. If SKIP_BODY  
    * is returned or the tags body is empty, this method is not called. It is  
    * invoked after the body has been evaluated.  
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
      int returnValue=SKIP_BODY;  
  
      if(decrementCount()>=1){  
	 try{  
	    if(bodyContent!=null)  
	       bodyContent.writeOut(bodyContent.getEnclosingWriter());  
  
	    bodyContent.clearBody();   
	 }   
	 catch(IOException exception){  
	    log("Error in RepeatTag: " + exception);  
	 }  
  
	 returnValue=EVAL_BODY_TAG;  
      }   
  
      if(isBreakRequested()){  
         isBreakRequested(false);  
         returnValue=SKIP_BODY;  
      }  
  
      return returnValue;  
   }// doAfterBody() ENDS  
  
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
   public int doStartTag(){  
   //---------------------------------------------------------------------------  
      int returnValue=EVAL_BODY_TAG;  
      pageContext.setAttribute(getId(),this);  
      return returnValue;  
   }// doStartTag() ENDS  
  
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
      StringBuffer buffer=new StringBuffer(cr+"*****ForTag: "+cr);  
      return buffer.toString();  
   }// toString() ENDS  
}// ForTag() ENDS  
