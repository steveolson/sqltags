package com.aitworks.jsptags.conditional;  
import java.io.IOException;  
import java.util.Enumeration;  
import javax.servlet.jsp.JspWriter;  
  
//---------------------------------------------------------------------------  
final public class BreakAfterBodyTag extends ConditionalStatement{  
//---------------------------------------------------------------------------  
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
  
      if(parent==null)  
	 setExceptionString("BreakAfterBodyTag's Parent was null.");  
      else  
	 parent.isBreakRequested(true);  
  
      return SKIP_BODY;  
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
      StringBuffer buffer=new StringBuffer(cr+"*****BreakAfterBodyTag: "+cr);  
      buffer.append(tab+"parent="+parent+cr);  
      return buffer.toString();  
   }// toString() ENDS  
  
   //***************************************************************************  
   //Protected Methods  
   //***************************************************************************  
     
   public void setException(Exception exception) {  
   }  
     
   public boolean setExceptionString(String message) {  
       return true;  
   }  
     
   //***************************************************************************  
   //Variables  
   //***************************************************************************  
   ConditionalStatement parent;  
}// BreakAfterBodyTag() ENDS  
