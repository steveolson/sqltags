package com.aitworks.jsptags.access;  
import java.io.IOException;  
import javax.servlet.jsp.JspWriter;  
import javax.servlet.jsp.tagext.BodyContent;  
import javax.servlet.jsp.tagext.BodyTagSupport;  
         
/**  
 * <code>AllowTag</code>  
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
final public class AllowTag extends BodyTagSupport{  
//---------------------------------------------------------------------------  
   //***************************************************************************  
   //Constructors  
   //***************************************************************************  
   /**  
    * <code>AllowTag</code>  
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
   public AllowTag(){  
   //---------------------------------------------------------------------------  
   }// Constructor() ENDS  
  
   //***************************************************************************  
   //Finalize Method  
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
     try{		   
			if(bodyContent!=null){  
				bodyContent.writeOut(bodyContent.getEnclosingWriter());  
			}  
      }  
      catch(IOException exception){  
			parent.log("AllowTag.doAfterBody(): "+exception);  
      }  
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
      int returnValue=SKIP_BODY;  
      parent=(RestrictTag)pageContext.getAttribute(getName());  
  
      if(parent!=null&&parent.isPrivilegeAllowed())  
         returnValue= EVAL_BODY_TAG;  
  
      return returnValue;  
   }  
  
   /**  
    * <code>getName</code>  
    * <p>  
    * This method gets the name associated with this tag.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @return String   
    */  
   //---------------------------------------------------------------------------  
   public String getName(){  
   //---------------------------------------------------------------------------  
      return name;  
   }// getName() ENDS  
  
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
   public void setName(String string){  
   //---------------------------------------------------------------------------  
      name=string;  
   }//setName() ENDS  
  
  
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
      StringBuffer buffer=new StringBuffer(cr+"*****AllowTag: "+cr);  
      buffer.append(tab+"name="+name+cr);  
      buffer.append(tab+"parent="+parent+cr);  
      return buffer.toString();  
   }// toString() ENDS  
  
  
   //***************************************************************************  
   //Variables  
   //***************************************************************************  
   protected String    name;  
   private RestrictTag parent;  
}// AllowTag() ENDs  
