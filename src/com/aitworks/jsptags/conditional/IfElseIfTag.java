package com.aitworks.jsptags.conditional;  
import java.io.IOException;  
import java.util.Hashtable;  
import java.util.Vector;  
import javax.servlet.jsp.JspWriter;  
import javax.servlet.jsp.tagext.BodyContent;  
import javax.servlet.jsp.JspTagException;  
  
//---------------------------------------------------------------------------  
public class IfElseIfTag extends ConditionalStatement{  
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
      parent=(ConditionalStatement)pageContext.getAttribute(getName());  
  
      try {  
         if(bodyContent!=null){  
            BodyContent body=getBodyContent();  
            String bodyString=body.getString();  
            JspWriter out=parent.getBodyContent().getEnclosingWriter();  
            out.println(bodyString);  
         }  
      }  
      catch(IOException exception){  
         log("Error in IfThenTag: " + exception);  
      }  
  
      return SKIP_BODY;  
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
      returnValue=SKIP_BODY;  
      parent=(ConditionalStatement)pageContext.getAttribute(getName());  
      parent.setCondition(getCondition());  
      parent.isElseIfTag(true);  
  
      if(parent==null){  
	 setExceptionString("else tag was not nested within an if tag.");  
	 log("else tag was not nested within an if tag.");  
      }   
      else if(!parent.hasCondition()){  
	 setExceptionString("I need a condition tag before the else.");  
	 log("I need a condition tag before the else.");  
      }  
  
      if(parent.getCondition()){  
         returnValue=EVAL_BODY_TAG;  
         parent.isElseConditionResolved(true);  
      }  
  
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
      String tab="\t";  
      StringBuffer buffer=new StringBuffer(cr+"*****IfElseIfTag: "+cr);  
      buffer.append(tab+"parent="+parent+cr);  
      buffer.append(tab+"returnValue="+returnValue+cr);  
      return buffer.toString();  
   }// toString() ENDS  
  
   //***************************************************************************  
   //Variables  
   //***************************************************************************  
   private ConditionalStatement parent;  
   private int                  returnValue=SKIP_BODY;  
}// IfElseIfTag() ENDS  
