package com.aitworks.jsptags.tagextrainfo;  
import javax.servlet.jsp.tagext.TagData;  
import javax.servlet.jsp.tagext.TagExtraInfo;  
import javax.servlet.jsp.tagext.VariableInfo;  
  
/**  
 * <code>InitializeTagExtraInfo</code>  
 * <p>  
 * This class is used to create the scripting variables which are used within   
 * the jsp and are associated to the tag.  
 * </p>  
 * @author  Booker Northington II  
 * @version 1.0  
 * @since   1.3  
 * @param none <code>none</code> none.  
 * @see TagExtraInfo <code>TagExtraInfo</code> For more information.  
 * @return none <code>none</code> none.  
 */  
//------------------------------------------------------------------------------  
final public class InitializeTagExtraInfo extends TagExtraInfo{  
//------------------------------------------------------------------------------  
   //***************************************************************************  
   //Class Constructor  
   //***************************************************************************  
   /**  
    * <code>InitializeTagExtraInfo</code>  
    * <p>  
    * This is the classes default constructor. There is no need to call this   
    * directly.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return lastRecord <code>boolean</code> true, if this is the last   
    *        record within this result set.  
    */  
   //---------------------------------------------------------------------------  
   public InitializeTagExtraInfo(){  
   //---------------------------------------------------------------------------  
   }// InitializeTagExtraInfo() ENDS  
  
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
    * <code>getVariableInfo</code>  
    * <p>  
    * This method makes the scripting variables available to the jsp.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param value <code>TagData</code> The data associated with the tag.  
    * @see TagExtraInfo <code>TagExtraInfo</code> For more information.  
    * @see VariableInfo <code>VariableInfo</code> For more information.  
    * @see TagData <code>TagData</code> For more information.  
    * @return variableInfo <code>VariableInfo[]</code> An array of variableInfo.  
    */  
   //---------------------------------------------------------------------------  
   public VariableInfo[] getVariableInfo(TagData tagData) {  
   //---------------------------------------------------------------------------  
      return new VariableInfo[]{  
         new VariableInfo(tagData.getId(),"InitializeTag",  
            true,VariableInfo.AT_END)  
      };  
   }// getVariableInfo() ENDS  
  
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
      StringBuffer buffer=new StringBuffer(cr+  
         "*****InitializeTagExtraInfo: "+cr);  
      return buffer.toString();  
   }// toString() ENDS  
}// InitializeTagExtraInfo() ENDS  
