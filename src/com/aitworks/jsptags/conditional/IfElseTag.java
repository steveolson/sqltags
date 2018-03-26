package com.aitworks.jsptags.conditional;  
import java.io.IOException;  
import javax.servlet.jsp.JspWriter;  
import javax.servlet.jsp.tagext.BodyContent;  
  
//---------------------------------------------------------------------------  
final public class IfElseTag extends ConditionalStatement{  
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
   }  
    
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
  
      try{  
         if(bodyContent!=null){  
            BodyContent body=getBodyContent();  
            String bodyString=body.getString();  
            JspWriter out=getBodyContent().getEnclosingWriter();  
            out.println(bodyString);  
         }  
      }  
      catch(IOException exception){  
         log("IfElseTag.doAfterBody: " + exception);  
      }  
  
      return SKIP_BODY;  
   }  
  
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
  
      if(parent==null)  
	 log("IfElseTag.doStart: else tag was not nested within an if tag.");  
      else if(!parent.hasCondition())  
	 log("IfElseTag.doStart: I need a condition tag before the else.");  
  
      //is this else tag being used in combination with an else if tag?  
      if(parent!=null&&parent.isElseIfTag()){  
	 if(parent.isElseConditionResolved())  
            setCondition(false);  
         else  
            setCondition(true);  
      }  
  
      if(!parent.getCondition())  
	 returnValue=EVAL_BODY_TAG;  
  
      return returnValue;  
   }//doStartTag() ENDS  
  
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
      StringBuffer buffer=new StringBuffer(cr+"*****IfElseTag: "+cr);  
      buffer.append(tab+"parent="+parent+cr);  
      buffer.append(tab+"returnValue="+returnValue+cr);  
      return buffer.toString();  
   }// toString() ENDS  
   
   public boolean setExceptionString(String message) {  
       return true;  
   }  
     
   public void setException(Exception exception) {  
   }  
     
// finalizer() ENDS  
  
  
   //***************************************************************************  
   //Variables  
   //***************************************************************************  
   private ConditionalStatement parent;  
   private int                  returnValue=SKIP_BODY;  
}// IfElseTag() ENDS  
