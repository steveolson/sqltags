package com.aitworks.jsptags.conditional;  
import java.io.IOException;  
import javax.servlet.jsp.JspWriter;  
import javax.servlet.jsp.tagext.BodyContent;  
  
//---------------------------------------------------------------------------  
final public class DoTag extends ConditionalStatement{  
//---------------------------------------------------------------------------  
   //***************************************************************************  
   //Protected Methods  
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
   //Public Method  
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
   public int doAfterBody() {  
   //---------------------------------------------------------------------------  
      parent=(ConditionalStatement)pageContext.getAttribute(getName());  
      body=getBodyContent();  
      String bodyString = body.getString();  
  
      if(parent.getCondition()){  
	 try{  
	    if(body!=null){  
	       JspWriter out=parent.getBodyContent().getEnclosingWriter();   
	       out.println(bodyString);   
	       body.clearBody();  
	    }   
	 }   
	 catch(IOException exception){  
	    log("Error in DoTag: " + exception);  
	 }  
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
      int returnValue=SKIP_BODY;  
      parent=(ConditionalStatement)pageContext.getAttribute(getName());  
      parent.setHasDo("true");  
  
      if(parent==null)  
         parent.setExceptionString("DoTag.doStartTag() parent is null.");  
      else if(parent.getCondition())  
         returnValue=EVAL_BODY_TAG;  
  
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
      StringBuffer buffer=new StringBuffer(cr+"*****DoTag: "+cr);  
      buffer.append(tab+"body="+body+cr);  
      buffer.append(tab+"parent="+parent+cr);  
      return buffer.toString();  
   }// toString() ENDS  
  
   //***************************************************************************  
   //Variables  
   //***************************************************************************  
   private BodyContent          body;  
   private ConditionalStatement parent;  
}// DoTag() ENDS  
