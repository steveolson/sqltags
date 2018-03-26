package com.aitworks.jsptags.conditional;  
import java.io.IOException;  
import java.util.Hashtable;  
import java.util.Vector;  
import javax.servlet.jsp.JspWriter;  
import javax.servlet.jsp.tagext.BodyContent;  
import javax.servlet.jsp.JspTagException;  
  
//---------------------------------------------------------------------------  
final public class IfThenTag extends ConditionalStatement{  
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
      BodyContent bodyContent=getBodyContent();  
      parent=(ConditionalStatement)pageContext.getAttribute(getName());  
  
      try {  
	 if(bodyContent!=null){  
	    BodyContent body=getBodyContent();   
	    String bodyString=body.getString();   
	    JspWriter out=getBodyContent().getEnclosingWriter();   
	    out.println(bodyString);   
	 }   
      }   
      catch(IOException exception){  
	 log("Error in IfThenTag: " + exception);  
      }  
  
      return SKIP_BODY;  
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
      int returnValue=SKIP_BODY;  
      parent=(ConditionalStatement)pageContext.getAttribute(getName());  
  
      if(parent==null)  
	 setExceptionString("then must be inside of an if tag.");  
  
      else if(!parent.hasCondition()){  
	 log("IfThenTag.doStart: A condition has not been set.");  
	 parent.setExceptionString("A condition has not been set.");  
      }  
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
      StringBuffer buffer=new StringBuffer(cr+"*****IfThenTag: "+cr);  
      buffer.append(tab+"parent="+parent+cr);  
      return buffer.toString();  
   }// toString() ENDS  
  
   //***************************************************************************  
   //Variables  
   //***************************************************************************  
   private ConditionalStatement parent;  
}// IfThenTag() ENDS  
