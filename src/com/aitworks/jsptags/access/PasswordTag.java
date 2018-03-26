package com.aitworks.jsptags.access;  
import com.aitworks.ldap.FindLDAPUser;
import com.aitworks.sqltags.utilities.SQLTags;  
import com.aitworks.sqltags.utilities.RandomHash;  
import com.aitworks.sqltags.utilities.Utilities;  
import java.io.File;  
import java.io.IOException;  
import java.sql.SQLException;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpSession;  
import javax.servlet.jsp.JspWriter;  
import javax.servlet.jsp.PageContext;  
import javax.servlet.jsp.tagext.BodyContent;  
import javax.servlet.jsp.tagext.BodyTagSupport;  
  
/**  
 * The <b>PasswordTag</b>   
 * @author  Booker Northington II  
 * @version 1.0  
 * @since   JDK1.3  
 * @param   none  
 * @return    
 */  
//---------------------------------------------------------------------------  
final public class PasswordTag extends BodyTagSupport{  
//---------------------------------------------------------------------------  
   //***************************************************************************  
   //Constructors  
   //***************************************************************************  
   /**  
    * The <b>PasswordTag</b> class constructor.  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return    
    */  
   //---------------------------------------------------------------------------  
   public PasswordTag(){  
   //---------------------------------------------------------------------------  
   }//Constructor ENDS()  
  
   //***************************************************************************  
   //Public Methods  
   //***************************************************************************  
   /**  
    * The <b>doAfterBody</b> method is called after the body of the  
    * tag has been processed.  
    * PCM-3  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   name the current field name.  
    * @see     BodyTagSupport  
    * @return  none  
    * @since   JDK1.3  
    */  
   //---------------------------------------------------------------------------  
   public int doAfterBody(){  
   //---------------------------------------------------------------------------  
      int returnCode=SKIP_BODY;  
      return returnCode;  
   }// doAfterBody() ENDS  
  
   /**  
    * This <b>doEndTag</b> is called when the end tag is hit.  
    * PCM-7  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  The loop status.  
    */  
   //---------------------------------------------------------------------------  
   public int doEndTag(){  
   //---------------------------------------------------------------------------  
      pageContext.removeAttribute(getId());  
      int returnCode=SKIP_BODY;  
  
      if(!badPassword)  
         returnCode=EVAL_PAGE;  
  
      return returnCode;  
   }// doEndTag() ENDS  
  
   /**  
    * The <b>doStartTag</b> method is called when the start tag is encountered.  
    8 PCM-8  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the eval state  
    */  
   //---------------------------------------------------------------------------  
   public int doStartTag(){  
   //---------------------------------------------------------------------------  
      int returnCode=EVAL_BODY_TAG;  
      pageContext.setAttribute(getId(),this);  
      HttpSession session=(HttpSession)  
         pageContext.getAttribute(PageContext.SESSION);  
  
      if(session.getAttribute("ldapProperties")==null){  
	   StringBuffer buffer=new StringBuffer();  
	    buffer.append(pageContext.getServletContext().getRealPath("WEB-INF"));  
	    buffer.append(File.separator);  
	    buffer.append("ldap.properties.x");  
  
	 RandomHash randomHash=new RandomHash(buffer.toString());  
	 session.setAttribute("ldapProperties",randomHash.getHashProperties());  
      }  
  
  
  
      // get the user and validate the password  
      HttpServletRequest request=(HttpServletRequest)  
         pageContext.getAttribute(PageContext.REQUEST);  
      String initSrc="";  
      String remoteUser=request.getRemoteUser();  
      badPassword=validateUserPassword(session,remoteUser, initSrc);  
  
      /**  
       * True if the password is expired or expiring. This is done  
       * until passwordValid is placed within the session.  
       */  
      if(badPassword){  
	 //null if we have not validated the users session  
	 if(pageContext.getSession().getAttribute("passwordValid")==null){  
	    msg=expiredOrExpiring; 
  
	    //if this is true, the password has expired.  
	    if(msg.indexOf("19691231185959")>=0){  
	       returnCode=SKIP_BODY;  
	       badPassword=true;  
	    }  
	    else{  
	       /**  
		   * the password has not expired. set the session attribute and  
		   * let the user know if their password is expiring.  
		   */  
		  pageContext.getSession().setAttribute("passwordValid",  
                     "passwordValid");  
		  badPassword=false;  
  
		  try{  
		     pageContext.getOut().println(  
			"<script>statusMessage(\""+msg+"\");</script>");  
		  }catch(IOException exception){}  
  
		  expiring=true;  
	    }  
	 }  
      }  
  
      return returnCode;  
   }// doStartTag() ENDS  
  
   /**  
    * The <b>getAuthType</b>   
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public String getAuthType(){  
   //---------------------------------------------------------------------------  
      return authType;  
   }// getAuthType() ENDS  
  
   /**  
    * The <b>getExpirePage</b>   
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public String getExpirePage(){  
   //---------------------------------------------------------------------------  
      return expirePage;  
   }// getExpirePage() ENDS  
  
   /**  
    * The <b>getId</b> method is used to return the name attribute.  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public String getId(){  
   //---------------------------------------------------------------------------  
      return id;  
   }// getId() ENDS  
  
   /**  
    * The <b>getLoginPage</b>   
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public String getLoginPage(){  
   //---------------------------------------------------------------------------  
      return loginPage;  
   }// getLoginPage() ENDS  
  
   /**  
    * The <b>getMsg</b>   
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public String getMsg(){  
   //---------------------------------------------------------------------------  
      return msg;  
   }// getMsg() ENDS  
   /**  
    * The <b>isPasswordExpiring</b> method is used to return the name attribute.  
    * PCM-1  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public boolean isPasswordExpiring(){  
   //---------------------------------------------------------------------------  
      return expiring;  
   }// isPasswordExpiring() ENDS  
  
   /**  
    * The <b>isPasswordValid</b> method is used to return the name attribute.  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public boolean isPasswordValid(){  
   //---------------------------------------------------------------------------  
      return badPassword;  
   }// isPasswordValid() ENDS  
  
   /**  
    * <code>log</code>  
    * <p>  
    * This method logs errors to netscapes error log.  
    * </p>  
    * PCM-1  
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
      return;  
   }// log() ENDS  
  
   /**  
    * The <b>setAuthType</b>   
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public void setAuthType(String string){  
   //---------------------------------------------------------------------------  
      authType=string;  
   }// setAuthType() ENDS  
  
   /**  
    * The <b>setExpirePage</b>   
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public void setExpirePage(String string){  
   //---------------------------------------------------------------------------  
      expirePage=string;  
   }// setExpirePage() ENDS  
  
  
   /**  
    * The <b>setId</b> method sets the value of the name attribute.  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param name the name value  
    * @return  none  
    */  
   //---------------------------------------------------------------------------  
   public void setId(String string){  
   //---------------------------------------------------------------------------  
      id=string;  
      return;  
   }// setId() ENDS  
  
   /**  
    * The <b>setLoginPage</b>   
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public void setLoginPage(String string){  
   //---------------------------------------------------------------------------  
      loginPage=string;  
   }// setLoginPage() ENDS  
  
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
      StringBuffer buffer=new StringBuffer(cr+"*****PasswordTag: "+cr);  
      buffer.append(tab+"authType="+authType+cr);  
      buffer.append(tab+"badPassword="+badPassword+cr);  
      buffer.append(tab+"expirePage="+expirePage+cr);  
      buffer.append(tab+"expiring="+expiring+cr);  
      buffer.append(tab+"utilities="+utilities+cr);  
      buffer.append(tab+"loginPage="+loginPage+cr);  
      buffer.append(tab+"id="+id+cr);  
      buffer.append(tab+"msg="+msg+cr);  
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
  
   //***************************************************************************  
   //Private Methods  
   //***************************************************************************  
   /**  
    * <code>validateUserPassword</code>  
    * <p>  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code>  
    * @return <code>none</code>   
    */  
   //-------------------------------------------------------------------------  
   private boolean validateUserPassword(HttpSession session,String user,  
      String initSrc){   
   //-------------------------------------------------------------------------  
      boolean badPassword=false;  
      Object propertiesLoaded=session.getAttribute(initSrc);  
      Object passwordValid=session.getAttribute("passwordValid");  
  
      if(passwordValid==null&&propertiesLoaded!=null){  
	 FindLDAPUser findUser=new FindLDAPUser(session,user,initSrc);  
  
	 if(findUser.getTimeToChangePassword()){  
	    badPassword=true;  
	    expiredOrExpiring=findUser.getBrowserMessages();  
         }  
      }  
  
      return badPassword;  
   }  
  
   //***************************************************************************  
   //                      Class variable section  
   //***************************************************************************  
   private String      authType="";  
   private boolean     badPassword=false;  
   private String      expirePage="";  
   private String      expiredOrExpiring="";  
   private boolean     expiring=false;  
   private Utilities   utilities=new Utilities();  
   private String      loginPage="";  
   private String      id="";  
   private String      msg="";  
  
}//PasswordTag() ENDS  
