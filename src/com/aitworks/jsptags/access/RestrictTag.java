package com.aitworks.jsptags.access;  
import com.aitworks.sqltags.utilities.Utilities;  
import java.util.Enumeration;  
import java.util.Vector;  
import java.util.Hashtable;  
import javax.servlet.jsp.tagext.BodyContent;  
import javax.servlet.jsp.JspWriter;  
import javax.servlet.jsp.tagext.BodyTagSupport;  
import javax.servlet.jsp.JspTagException;  
  
/**  
 * <code>RestrictTag</code>  
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
final public class RestrictTag extends BodyTagSupport{  
//---------------------------------------------------------------------------  
   //***************************************************************************  
   //Constructors  
   //***************************************************************************  
   /**  
    * <code>RestrictTag</code>  
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
   public RestrictTag(){  
   //---------------------------------------------------------------------------  
   }  
  
   //***************************************************************************  
   // Public Methods  
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
      return returnValue;  
   }// dodoAfterBody() ENDS  
  
   /**  
    * <code>doEndBody</code>  
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
   public int doEndTag(){  
   //---------------------------------------------------------------------------  
      pageContext.removeAttribute(getId());  
      return returnValue;  
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
      returnValue=EVAL_BODY_TAG;  
      pageContext.setAttribute(getId(),this);  
      parent=(AuthenticateTag)pageContext.getAttribute(getName());  
      restrictTagPrivileges=utilities.getTokenHash(priv,",");  
      Enumeration enum=restrictTagPrivileges.keys();  
  
      if(!all.equals("")){  
	 if(getAll()){  
	    isPrivilegeAllowed=true;  
	    isPrivilegeDenyed=false;  
	 }  
	 else if(!getAll()){  
	    isPrivilegeAllowed=false;  
	    isPrivilegeDenyed=true;  
	 }  
      }  
      else{  
	 for(;enum.hasMoreElements();){  
	    String privilege=((String)enum.nextElement()).toUpperCase();     
  
	    if(parent.isPrivilegeAuthorized(privilege)){  
	       isPrivilegeAllowed=true;  
	       isPrivilegeDenyed=false;  
	       break;  
	    }  
	    else  
	       isPrivilegeDenyed=true;  
	 }  
      }  
  
      return returnValue;  
   }// doStartTag() ENDS  
  
   /**  
    * <code>isPrivilegeAllowed</code>  
    * <p>  
    * This method states whether the request privilege has been allowed for the user.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>none</code>   
    * @return true <code>boolean</code> If the privilege is allowed  
    */  
   //---------------------------------------------------------------------------  
   public boolean isPrivilegeAllowed(){  
   //---------------------------------------------------------------------------  
      return isPrivilegeAllowed;  
   }// isPrivilegeAllowed() ENDS  
  
   /**  
    * <code>isPrivilegeDenyed</code>  
    * <p>  
    * This method states whether the request privilege has been allowed for the user.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>none</code>   
    * @return true <code>boolean</code> If the privilege is allowed  
    */  
   //---------------------------------------------------------------------------  
   public boolean isPrivilegeDenyed(){  
   //---------------------------------------------------------------------------  
      return isPrivilegeDenyed;  
   }// isPrivilegeDenyed() ENDS  
  
   /**  
    * <code>getAll</code>  
    * <p>  
    * This method returns the true if all is true  
    * </p>  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return boolean <code>boolean</code> all  
    */  
   //---------------------------------------------------------------------------  
   public boolean getAll(){  
   //---------------------------------------------------------------------------  
      boolean value=false;  
  
      if(all.equals("true"))  
         value=true;  
  
      return value;  
   }//getAll() ENDS  
  
   /**  
    * <code>getId</code>  
    * <p>  
    * This method returns the Id.  
    * </p>  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return Id <code>String</code> The Id;  
    */  
   //---------------------------------------------------------------------------  
   public String getId(){   
   //---------------------------------------------------------------------------  
      return id;  
   }//getId() ENDS  
  
   /**  
    * <code>getName</code>  
    * <p>  
    * This method returns the name.  
    * </p>  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return name <code>String</code> The name;  
    */  
   //---------------------------------------------------------------------------  
   public String getName(){  
   //---------------------------------------------------------------------------  
      return name;  
   }//return getName()  
  
   /**  
    * <code>getPriv</code>  
    * <p>  
    * This method returns the name.  
    * </p>  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return priv <code>String</code> The privilege;  
    */  
   //---------------------------------------------------------------------------  
   public String getPriv(){  
   //---------------------------------------------------------------------------  
      return priv;  
   }// getPriv() ENDS  
  
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
    * <code>setAll</code>  
    * <p>  
    * This method sets the all flag to true or false;  
    * </p>  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param all <code>String</code> The state.  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public void setAll(String string){  
   //---------------------------------------------------------------------------  
      string=string.trim().toLowerCase();  
  
      if(!string.equals("true")&&!string.equals("false"))  
	 string="false";  
      else  
	 all=string;  
   }//setAll() ENDS  
  
   /**  
    * <code>setId</code>  
    * <p>  
    * This method sets the name of the scripting variable associated with this   
    * tag.  
    * </p>  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param priv <code>String</code> The privilege  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public void setId(String string){  
   //---------------------------------------------------------------------------  
      id=string;  
   }//setId() ENDS  
  
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
    * @param priv <code>String</code> The privilege  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public void setName(String string){  
   //---------------------------------------------------------------------------  
      name=string;  
   }//setName() ENDS  
  
   /**  
    * <code>setPriv</code>  
    * <p>  
    * This method sets the name of the scripting variable associated with this   
    * tag.  
    * </p>  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param priv <code>String</code> The privilege  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public void setPriv(String string){  
   //---------------------------------------------------------------------------  
      priv=string;  
   }// setPriv() ENDS  
  
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
      StringBuffer buffer=new StringBuffer(cr+"*****RestrictTag: "+cr);  
      buffer.append(tab+"all="+all+cr);  
      buffer.append(tab+"utilities="+utilities+cr);  
      buffer.append(tab+"id="+id+cr);  
      buffer.append(tab+"isPrivilegeAllowed="+isPrivilegeAllowed+cr);  
      buffer.append(tab+"isPrivilegeDenyed="+isPrivilegeDenyed+cr);  
      buffer.append(tab+"name="+name+cr);  
      buffer.append(tab+"parent="+parent+cr);  
      buffer.append(tab+"buffer="+buffer+cr);  
      buffer.append(tab+"restrictTagPrivileges="+restrictTagPrivileges+cr);  
      buffer.append(tab+"returnValue="+returnValue+cr);  
      return buffer.toString();  
   }// toString() ENDS  
  
  
   //******************************************************************************  
   //Protected Methods  
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
  
  
   //******************************************************************************  
   //Private Methods  
   //******************************************************************************  
   /**  
    * <code>putPrivilegeInHash</code>  
    * <p>  
    * This method builds the privilege table.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param priv <code>StringBuffer</code> the privilege.  
    * @return true <code>boolean</code> If there are more records to fetch.  
    */  
   //---------------------------------------------------------------------------  
   private void putPrivilegeInHash(StringBuffer stringBuffer){  
   //---------------------------------------------------------------------------  
      restrictTagPrivileges.put(stringBuffer.toString().toUpperCase(),"");  
      buffer.setLength(0);  
   }//putPrivilegeInHash() ENDS  
  
  
   //***************************************************************************  
   //                      Class variable section  
   //***************************************************************************  
   private String          all="";  
   private StringBuffer    buffer=new StringBuffer();  
   private String          id="";  
   private boolean         isPrivilegeAllowed=false;  
   private boolean         isPrivilegeDenyed=true;  
   private String          name="";  
   private AuthenticateTag parent=null;  
   private String          priv="";  
   private Hashtable       restrictTagPrivileges=null;  
   private int             returnValue=SKIP_BODY;  
   private Utilities       utilities=new Utilities();  
}//RestrictTag() ENDS  
