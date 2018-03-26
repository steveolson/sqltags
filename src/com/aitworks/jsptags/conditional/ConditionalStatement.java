package com.aitworks.jsptags.conditional;  
import com.aitworks.sqltags.interfaces.IExceptionHandler;  
import java.util.Enumeration;  
import java.util.Vector;  
import javax.servlet.jsp.JspWriter;  
import javax.servlet.jsp.tagext.BodyTagSupport;  
import javax.servlet.jsp.JspTagException;  
  
//---------------------------------------------------------------------------  
public class ConditionalStatement extends BodyTagSupport implements   
                                                  IExceptionHandler{  
//---------------------------------------------------------------------------  
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
    * <code>decrementCount</code>  
    * <p>  
    * This method sets the timeToBreak  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param string <code>string</code> timeToBreak  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public int decrementCount(){  
   //---------------------------------------------------------------------------  
      count=count-1;  
      return count;  
   }// decrementCount() ENDS  
  
   /**  
    * <code>getCondition</code>  
    * <p>  
    * This method sets the timeToBreak  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param string <code>string</code> timeToBreak  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public boolean getCondition(){  
   //---------------------------------------------------------------------------  
      return condition;  
   }// getCondition() ENDS  
  
   /**  
    * <code>getCount</code>  
    * <p>  
    * This method sets the timeToBreak  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param string <code>string</code> timeToBreak  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public int getCount(){  
   //---------------------------------------------------------------------------  
      return count;  
   }// getCount() ENDS  
  
   /**  
    * <code>getDisplayOnBreak</code>  
    * <p>  
    * This method sets the timeToBreak  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param string <code>string</code> timeToBreak  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public String getDisplayOnBreak(){  
   //---------------------------------------------------------------------------  
      return displayOnBreak;  
   }// getDisplayOnBreak() ENDS  
  
   /**  
    * <code>getEnum</code>  
    * <p>  
    * This method sets the timeToBreak  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param string <code>string</code> timeToBreak  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public Enumeration getEnum(){  
   //---------------------------------------------------------------------------  
      return enum;  
   }// getEnum() ENDS  
  
   /**  
    * <code>getException</code>  
    * <p>  
    * This method sets the encountered exception for later retrival.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param exception <code>Exception</code> The exception generated.  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public Exception getException(){  
   //---------------------------------------------------------------------------  
      return exception;  
   }// getException() ENDS  
  
   /**  
    * <code>getHasDo</code>  
    * <p>  
    * This method sets the timeToBreak  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param string <code>string</code> timeToBreak  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public String getHasDo(){  
   //---------------------------------------------------------------------------  
      return hasDo;  
   }// getHasDo() ENDS  
  
   /**  
    * <code>getHasThen</code>  
    * <p>  
    * This method sets the timeToBreak  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param string <code>string</code> timeToBreak  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public String getHasThen(){  
   //---------------------------------------------------------------------------  
      return hasThen;  
   }// getHasThen() ENDS  
  
   /**  
    * <code>getId</code>  
    * <p>  
    * This method sets the timeToBreak  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param string <code>string</code> timeToBreak  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public String getId(){  
   //---------------------------------------------------------------------------  
      return id;  
   }// getId() ENDS  
  
   /**  
    * <code>getIfTagResults</code>  
    * <p>  
    * This method sets the timeToBreak  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param string <code>string</code> timeToBreak  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public String getIfTagResults(){  
   //---------------------------------------------------------------------------  
      return ifTagResults.toString();  
   }// getIfTagResults() ENDS  
  
   /**  
    * <code>getName</code>  
    * <p>  
    * This method sets the timeToBreak  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param string <code>string</code> timeToBreak  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public String getName(){  
   //---------------------------------------------------------------------------  
      return name;  
   }// getName() ENDS  
  
   /**  
    * <code>getParentName</code>  
    * <p>  
    * This method sets the timeToBreak  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param string <code>string</code> timeToBreak  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public String getParentName(){  
   //---------------------------------------------------------------------------  
      return parentName;  
   }// getParentName() ENDS  
  
   /**  
    * <code>isBreakRequested</code>  
    * <p>  
    * This method sets the timeToBreak  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param string <code>string</code> timeToBreak  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public boolean isBreakRequested(){  
   //---------------------------------------------------------------------------  
      return breakRequested;  
   }// isBreakRequested() ENDS  
  
   /**  
    * <code>isBreakRequested</code>  
    * <p>  
    * This method sets the timeToBreak  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param string <code>string</code> timeToBreak  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public void isBreakRequested(boolean value){  
   //---------------------------------------------------------------------------  
      breakRequested=value;  
   }// isBreakRequested() ENDS  
  
   /**  
    * <code>isElseConditionResolved</code>  
    * <p>  
    * This method sets the timeToBreak  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param string <code>string</code> timeToBreak  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public boolean isElseConditionResolved(){  
   //---------------------------------------------------------------------------  
      return elseConditionResolved;  
   }// isElseConditionResolved() ENDS  
  
   /**  
    * <code>isElseConditionResolved</code>  
    * <p>  
    * This method sets the timeToBreak  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param string <code>string</code> timeToBreak  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public void isElseConditionResolved(boolean value){  
   //---------------------------------------------------------------------------  
      elseConditionResolved=value;  
   }// isElseConditionResolved() ENDS  
  
   /**  
    * <code>isElseIfTag</code>  
    * <p>  
    * This method sets the timeToBreak  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param string <code>string</code> timeToBreak  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public boolean isElseIfTag(){  
   //---------------------------------------------------------------------------  
      return elseIfTag;  
   }// isElseIfTag() ENDS  
  
   /**  
    * <code>isElseIfTag</code>  
    * <p>  
    * This method sets the timeToBreak  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param string <code>string</code> timeToBreak  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public void isElseIfTag(boolean value){  
   //---------------------------------------------------------------------------  
      elseIfTag=value;  
   }// isElseIfTag() ENDS  
  
   /**  
    * <code>isTimeToBreak</code>  
    * <p>  
    * This method returns timeToBreak  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return boolean <code>boolean</code> boolean.  
    */  
   //---------------------------------------------------------------------------  
   public boolean isTimeToBreak(){  
   //---------------------------------------------------------------------------  
      return timeToBreak;  
   }// isTimeToBreak() ENDS  
  
   /**  
    * <code>isTimeToBreak</code>  
    * <p>  
    * This method sets the timeToBreak  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param string <code>string</code> timeToBreak  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public void isTimeToBreak(boolean value){  
   //---------------------------------------------------------------------------  
      timeToBreak=value;  
   }// isTimeToBreak() ENDS  
  
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
    * <code>setCount</code>  
    * <p>  
    * This method sets the count  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param string <code>string</code> count  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public void setCount(String string){  
   //---------------------------------------------------------------------------  
      try{  
	 count=Integer.parseInt(string);  
      }   
      catch(NumberFormatException exception) {  
	 count=1;  
      }  
   }// setCount() ENDS  
  
   /**  
    * <code>setDisplayOnBreak</code>  
    * <p>  
    * This method sets the displayOnBreak  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param string <code>string</code> displayOnBreak  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public void setDisplayOnBreak(String string){  
   //---------------------------------------------------------------------------  
      displayOnBreak=string;  
   }// setDisplayOnBreak() ENDS  
  
   /**  
    * <code>setEnum</code>  
    * <p>  
    * This method sets the enum  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param string <code>string</code> enum  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public void setEnum(Enumeration enumeration){  
   //---------------------------------------------------------------------------  
      enum=enumeration;  
   }// setEnum() ENDS  
  
   /**  
    * <code>setException</code>  
    * <p>  
    * This method returns true if there is a condition  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return boolean <code>boolean</code> boolean.  
    */  
   //---------------------------------------------------------------------------  
   public boolean hasCondition(){  
   //---------------------------------------------------------------------------  
      return hasCondition;  
   }// hasCondition() ENDS  
  
   /**  
    * <code>setException</code>  
    * <p>  
    * This method sets the Condition  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param string <code>string</code> The exception generated.  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public void setCondition(boolean value){  
   //---------------------------------------------------------------------------  
      condition = value;  
      hasCondition = true;  
   }// setCondition() ENDS  
  
   /**  
    * <code>setException</code>  
    * <p>  
    * This method sets the encountered exception for later retrival.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param exception <code>Exception</code> The exception generated.  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public void setException(Exception exception){  
   //---------------------------------------------------------------------------  
      exception=exception;  
   }// setException() ENDS  
  
   /**  
    * <code>setExceptionString</code>  
    * <p>  
    * This method creates a user defined exception and sets it as the current  
    * exception.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param message <code>String</code> The exception message.  
    * @return true <code>boolean</code> true if exception successfully set.  
    */  
   //---------------------------------------------------------------------------  
   public boolean setExceptionString(String string){  
   //---------------------------------------------------------------------------  
      boolean returnValue=true;  
  
      try{  
         Exception exception=new Exception(string);  
         setException(exception);  
      }  
      catch(Exception exception){  
         returnValue=false;  
      }  
  
      return returnValue;  
   }// setException() ENDS  
  
   /**  
    * <code>setHasCondition</code>  
    * <p>  
    * This method sets the hasDo  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param string <code>string</code> hasDo  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public void setHasCondition(boolean value){  
   //---------------------------------------------------------------------------  
      hasCondition = value;  
   }//setHasCondition() ENDS  
  
   /**  
    * <code>setHasDo</code>  
    * <p>  
    * This method sets the hasDo  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param string <code>string</code> hasDo  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public void setHasDo(String string ){  
   //---------------------------------------------------------------------------  
      hasDo=string.trim().toLowerCase();  
   }// setHasDo() ENDS  
  
   /**  
    * <code>setHasThen</code>  
    * <p>  
    * This method sets the hasThen  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param string <code>string</code> hasThen  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public void setHasThen(String string ){  
   //---------------------------------------------------------------------------  
      hasThen=string.toLowerCase();  
   }// setHasThen() ENDS  
  
   /**  
    * <code>setId</code>  
    * <p>  
    * This method sets the Id  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param string <code>string</code> id  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public void setId(String string){  
   //---------------------------------------------------------------------------  
      id=string;  
   }// setID() ENDS  
  
   /**  
    * <code>setIfTagResults</code>  
    * <p>  
    * This method sets the ifTagResults  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param string <code>string</code> the ifTagResults  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public void setIfTagResults(String string){  
   //---------------------------------------------------------------------------  
      ifTagResults.append(string);  
   }// setIfTagResults() ENDS  
  
   /**  
    * <code>setName</code>  
    * <p>  
    * This method sets the name  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param string <code>string</code> The name  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public void setName(String string){  
   //---------------------------------------------------------------------------  
      name=string;  
   }// setName() ENDS  
  
   /**  
    * <code>setParentName</code>  
    * <p>  
    * This method sets the parentName  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param string <code>string</code> The exception generated.  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public void setParentName(String string){  
   //---------------------------------------------------------------------------  
      parentName=string;  
   }// setParentName() ENDS  
  
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
      StringBuffer buffer=new StringBuffer(cr+"*****ConditionalStatement: "+cr);  
      buffer.append(tab+"breakRequested="+breakRequested+cr);  
      buffer.append(tab+"condition="+condition+cr);  
      buffer.append(tab+"count="+count+cr);  
      buffer.append(tab+"displayOnBreak="+displayOnBreak+cr);  
      buffer.append(tab+"elseConditionResolved="+elseConditionResolved+cr);  
      buffer.append(tab+"enum="+enum+cr);  
      buffer.append(tab+"elseIfTag="+elseIfTag+cr);  
      buffer.append(tab+"exception="+exception+cr);  
      buffer.append(tab+"hasCondition="+hasCondition+cr);  
      buffer.append(tab+"hasDo="+hasDo+cr);  
      buffer.append(tab+"hasThen="+hasThen+cr);  
      buffer.append(tab+"id="+id+cr);  
      buffer.append(tab+"ifTagResults="+ifTagResults+cr);  
      buffer.append(tab+"name="+name+cr);  
      buffer.append(tab+"parentName="+parentName+cr);  
      buffer.append(tab+"timeToBreak="+timeToBreak+cr);  
      return buffer.toString();  
   }// toString() ENDS  
  
   //***************************************************************************  
   //Variables  
   //***************************************************************************  
   private boolean      breakRequested=false;  
   private boolean      condition;  
   private int          count;  
   private String       displayOnBreak="false";  
   private boolean      elseConditionResolved=false;  
   private Enumeration  enum;  
   private boolean      elseIfTag=false;  
   private Exception    exception;  
   private boolean      hasCondition=false;  
   private String       hasDo="false";  
   private String       hasThen="";  
   private String       id;  
   private StringBuffer ifTagResults=new StringBuffer();  
   private String       name;  
   private String       parentName="";  
   private boolean      timeToBreak=false;  
}// ConditionalStatement() ENDS  
