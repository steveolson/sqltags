package com.aitworks.jsptags.access;  
import com.aitworks.sqltags.utilities.ProjectProperties;  
import com.aitworks.sqltags.utilities.RandomHash;  
import com.aitworks.sqltags.utilities.Initialize;  
import com.aitworks.sqltags.jsptags.ConnectionTag;
import java.io.File;  
import java.io.IOException;  
import java.util.Properties;  
import java.sql.SQLException;  
import java.util.Hashtable;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpSession;  
import javax.servlet.jsp.JspException;  
import javax.servlet.jsp.JspWriter;  
import javax.servlet.jsp.PageContext;  
import javax.servlet.jsp.tagext.BodyContent;  
import javax.servlet.jsp.tagext.BodyTagSupport;  



/**  
 * The <b>InitializeTag</b>   
 * @author  Booker Northington II  
 * @version 1.0  
 * @since   JDK1.3  
 * @param   none  
 * @return    
 */  
//---------------------------------------------------------------------------  
public class InitializeTag extends BodyTagSupport{  
//---------------------------------------------------------------------------  
   //***************************************************************************  
   //Constructors   
   //***************************************************************************  
   /**  
    * The <b>InitializeTag</b> class constructor.  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return    
    */  
   //---------------------------------------------------------------------------  
   public InitializeTag(){  
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
       return SKIP_BODY;
/**      int returnCode=SKIP_BODY;  
      return returnCode;  
 */
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
       return EVAL_PAGE;
       /**
      int returnCode=EVAL_PAGE;  
      return returnCode;  
        */
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
   public int doStartTag() throws JspException{  
   //---------------------------------------------------------------------------  
      setInitializationProperties();
      return EVAL_BODY_TAG;

      /*
       InitializeTag initializeTag=
        (InitializeTag)pageContext.getAttribute(getId());  
      int returnCode=EVAL_BODY_TAG;  
  
      // if the properties have been initialized, get the hashtable.  
      if(initializeTag!=null){  
         initializationProperties=initialize.getProperties();  
      }  
      else{  
        initialize=new Initialize();
        
        if(!initSrc.trim().equals(""))
            initializationProperties= initialize.getInitSrcProperties
              (initSrc,pageContext.getServletContext());  
        else if(!initURL.trim().equals(""))
            initializationProperties=initialize.urlReader(initURL);
        else if(!initProperties.trim().equals(""))
            initializationProperties= initialize.getInitSrcProperties
              (initSrc,pageContext.getServletContext());  
                   
         if(initializationProperties==null){  
            exceptionString="InitializeTag could not locate properties file.";  
            pageContext.getSession().setAttribute("exceptionString",exceptionString);  
            throw new JspException("No connection properties file was specified");  
         }  
         else  
	         pageContext.getSession().setAttribute(getId(),this);  
      }  
  
      return returnCode;  

        */
   }// doStartTag() ENDS  

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
    * The <b>getInitialize</b>   
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public Initialize getInitialize(){  
   //---------------------------------------------------------------------------  
      return initialize;  
   }// getInitialize() ENDS  
  
   /**  
    * The <b>getInitializationProperties</b> method is return the hash properties.  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public Properties getInitializationProperties(){  
   //---------------------------------------------------------------------------  
      return initializationProperties;  
   }// getId() ENDS  
  
   /**  
    * The <b>getInitSrc</b>   
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public String getInitSrc(){  
   //---------------------------------------------------------------------------  
      return initSrc;  
   }// getInitSrc() ENDS  
   
   /**  
    * The <b>getInitURL</b> 
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param name the name value  
    * @return  none  
    */  
   //---------------------------------------------------------------------------  
   public String getInitURL(){  
   //---------------------------------------------------------------------------  
      return initURL;  
   }// getInitURL() ENDS  

   /**  
    * The <b>getInitID</b>
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param name the name value  
    * @return  none  
    */  
   //---------------------------------------------------------------------------
   public String getInitId(){return getInitID();}
   public String getInitID(){  
   //---------------------------------------------------------------------------  
      //return initID;  
       return getId();
   }// setInitID() ENDS  

   /**  
    * The <b>getInitProperties</b>  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param name the name value  
    * @return  none  
    */  
   //---------------------------------------------------------------------------  
   public String getInitProperties(){  
   //---------------------------------------------------------------------------  

      return initProperties;  
   }// getInitProperties() ENDS  
   /**
    * <code>getProperty</code>  
    * <p>  
    * This method returns a initialization property.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param string <code>String</code> the property key.  
    * @return string <code>String</code> The property value
    */  
   //---------------------------------------------------------------------------  
   public String getProperty(String string){  
   //--------------------------------------------------------------------------- 
       if(initializationProperties==null)
           return null;
      return initializationProperties.getProperty(string);
   }// getConnection() ENDS  
   
   /**
    * <code>getProperty</code>  
    * <p>  
    * This method returns a initialization property or the specified default
    * value if the property dose not exist.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param string <code>String</code> the property key.  
    * @param defaultString <code>String</code> the default to return on null. 
    * @return string <code>String</code> The property value
    */  
   //---------------------------------------------------------------------------  
   public String getProperty(String string,String defaultString){  
   //---------------------------------------------------------------------------  
    if(initializationProperties==null)
        return defaultString;

      return initializationProperties.getProperty(string,defaultString);
   }// getProperty() ENDS  

   /**  
    * The <b>getScope</b> method is used to return the name attribute.  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public String getScope(){  
   //---------------------------------------------------------------------------  
      return scope;  
   }// getScope() ENDS  
  
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
    * The <b>setInitEncrypted</b> depreciated!  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param name the name value  
    * @return  none  
    */  
   //---------------------------------------------------------------------------  
     public void setInitEncrypted(String s){
        // do nothing!
     }

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
        // System.out.println("id="+id);
      id=string;  
      return;  
   }// setId() ENDS  
   
   /**  
    * The <b>setInitURL</b> 
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param name the name value  
    * @return  none  
    */  
   //---------------------------------------------------------------------------  
   public void setInitURL(String string){  
   //---------------------------------------------------------------------------  
      initURL=string;  
      return;  
   }// setInitURL() ENDS  

      /**  
    * The <b>setInitID</b>
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param name the name value  
    * @return  none  
    */  
   //---------------------------------------------------------------------------  
   public void setInitId(String string){ setInitID(string);}
   public void setInitID(String string){  
   //---------------------------------------------------------------------------  
      initID=string;  
      return;  
   }// setInitID() ENDS  

   /**  
    * The <b>setInitProperties</b>  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param name the name value  
    * @return  none  
    */  
   //---------------------------------------------------------------------------  
   public void setInitProperties(String string){  
   //---------------------------------------------------------------------------  
      initProperties=string;  
      return;  
   }// setInitProperties() ENDS  
    
   /**  
    * The <b>setInitSrc</b>   
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param name the name value  
    * @return  none  
    */  
   //---------------------------------------------------------------------------  
   public void setInitSrc(String string){  
   //---------------------------------------------------------------------------  
       // System.out.println("initSrc="+initSrc);
      initSrc=string;  
      return;  
   }// setInitSrc() ENDS  
  
   /**  
    * The <b>setScope</b> method is used to return the name attribute.  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public void setScope(String string){  
   //---------------------------------------------------------------------------  
      scope=string;  
   }// setScope() ENDS  
  
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
      StringBuffer buffer=new StringBuffer(cr+"*****InitializeTag: "+cr);  
      // buffer.append(tab+"defaultPath="+defaultPath+cr);  
      buffer.append(tab+"exceptionString="+exceptionString+cr);  
      buffer.append(tab+"id="+id+cr);  
      buffer.append(tab+"initialize="+initialize+cr);  
      buffer.append(tab+"initSrc="+initSrc+cr);  
      buffer.append(tab+"initializationProperties="+initializationProperties+cr);  
      buffer.append(tab+"scope="+scope+cr);  
      return buffer.toString();  
   }// toString() ENDS  
  
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
  
   public void setInitializationProperties() throws JspException {
    // First check the Session

       initializationProperties = (Properties) pageContext.getSession().getAttribute(getInitId());
       if(initializationProperties!=null)
           return;

       initialize=new Initialize();
        
      if(!initSrc.trim().equals(""))
            initializationProperties= initialize.getInitSrcProperties(initSrc,pageContext.getServletContext());  
      else if(!initURL.trim().equals(""))
            initializationProperties=initialize.urlReader(initURL);
      else if(!initProperties.trim().equals(""))
            initializationProperties= initialize.getInitSrcProperties(initSrc,pageContext.getServletContext());  
                   
      if(initializationProperties==null){  
        exceptionString="InitializeTag could not locate properties file.";  
        pageContext.getSession().setAttribute("exceptionString",exceptionString);  
        throw new JspException("No connection properties file was specified");  
      }  
      else
	 pageContext.getSession().setAttribute(getInitId(),initializationProperties);

      return;
     }
  
   //***************************************************************************  
   //                      Class variable section  
   //***************************************************************************  
   // private String      defaultPath=".";  
   protected String      exceptionString="";  
   protected String      id="";  
   private Initialize  initialize;  
   protected String      initSrc="";  
   protected Properties  initializationProperties=null;  
   protected String      scope="session";
   protected String      initURL="";
   protected String      initProperties="";
   protected String      initID="InitializeTag";
   
}//InitializeTag() ENDS  
