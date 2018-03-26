package com.aitworks.jsptags.conditional;  
import java.util.Hashtable;  
import java.util.Vector;  
import javax.servlet.jsp.*;  
import javax.servlet.jsp.tagext.*;  
  
//---------------------------------------------------------------------------  
final public class IfConditionTag extends ConditionalStatement{  
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
   public int doAfterBody() {  
   //---------------------------------------------------------------------------  
      parent=(ConditionalStatement)pageContext.getAttribute(getName());  
      String bodyString = (getBodyContent().getString()).trim();  
  
      if(bodyString.equals("true"))  
	 parent.setCondition(true);  
      else  
	 parent.setCondition(false);  
  
      return SKIP_BODY;  
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
   public int doEndTag() {  
   //---------------------------------------------------------------------------  
      return EVAL_PAGE;  
   }//doEndTag() ENDS  
  
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
      parent=(ConditionalStatement)pageContext.getAttribute(getName());  
  
      if(parent==null){  
	 parent.setExceptionString("condition must be nested within a if tag.");  
	 return SKIP_BODY;  
      }   
  
      return EVAL_BODY_TAG;  
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
      StringBuffer buffer=new StringBuffer(cr+"*****IfConditionTag: "+cr);  
      buffer.append(tab+"parent="+parent+cr);  
      return buffer.toString();  
   }// toString() ENDS  
  
   //***************************************************************************  
   //Variables  
   //***************************************************************************  
   private ConditionalStatement parent;  
}// IfConditionTag() ENDS  
