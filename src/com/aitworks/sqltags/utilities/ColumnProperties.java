/* $Id: ColumnProperties.java,v 1.5 2002/04/23 21:46:02 booker Exp $
 * $Log: ColumnProperties.java,v $
 * Revision 1.5  2002/04/23 21:46:02  booker
 * Format changes
 *
 * Revision 1.4  2002/04/03 14:49:23  booker
 * Worked on adding Object to ColumnProperties.
 *
 * Revision 1.3  2002/03/15 14:33:31  solson
 * added License, ID, and Log
 *
 * ====================================================================
 *
 * Applied Information Technologies, Inc.
 * Steve A. Olson
 *
 * Copyright (c) 2002 Applied Information Technologies, Inc.  
 * Copyright (c) 2002 Steve A. Olson
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by 
 *    Applied Information Technologies, Inc. (http://www.ait-inc.com/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "Applied Information Technologies, Inc.", "AIT", "AITWorks", 
 *    "SQLTags", and "<SQLTags:>" must not be used to endorse or promote 
 *    products derived from this software without prior written permission. 
 *    For written permission, please contact support@ait-inc.com.
 *
 * 5. Products derived from this software may not be called "SQLTags" or
 *    "<SQLTags:>" nor may "SQLTags" or "<SQLTags:>" appear in their 
 *    names without prior written permission of the Applied Information 
 *    Technologies, Inc..
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL APPLIED INFORMATION TECHNOLOGIES, 
 * INC. OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of Applied Information Technologies, Inc.  For more
 * information on Applied Information Technologies, Inc., please see
 * <http://www.ait-inc.com/>.
 *
 */
package com.aitworks.sqltags.utilities;  
import java.util.Hashtable;  
import java.util.Enumeration;
/**  
 * <code>ColumnProperties</code>  
 * <p>  
 * This class holds the various values related to a column.  
 * </p>  
 * @author  Booker Northington II  
 * @version 1.0  
 * @since   1.3  
 * @param none <code>none</code> none.  
 * @return none <code>none</code> none.  
 */  
public class ColumnProperties{  
   /**  
    * <code>ColumnProperties</code>  
    * <p>  
    * This constructor initiates the class.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public ColumnProperties(String name,String size,String selectFormat,  
                           String type,String bind,String value,Object object){  
   //---------------------------------------------------------------------------  
      setName(name);
      setSize(size);
      setSelectFormat(selectFormat);
      setType(type);
      setValue(value);
      setBind(bind);
      setObject(object);
   }// Constructor() ENDs  
  
   //***************************************************************************  
   // Finalize Method  
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
   // Public Method  
   //***************************************************************************  
   /**  
    * <code>getBind</code>  
    * <p>  
    * This method returns the column bind.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code>   
    * @return bind <code>String</code> the column bind.  
    */  
   //---------------------------------------------------------------------------  
   public String getBind(){  
   //---------------------------------------------------------------------------  
      return bind;  
   }// getBind() ENDS  
   
   public String getInClause(){
       boolean isIn = true;
       StringBuffer inClause = new StringBuffer();
       String sep = "";
       String quote = "'";
       if(   getType().equals("NUMBER") 
          || getType().equals("INTEGER")
         )
       {
            quote="";  // types that don't need quoting
       }
       Enumeration values = valueHash.keys();
       if(isIn){
           inClause.append("IN(");
       }
       while( values.hasMoreElements() ) {
           String hashValue = (String)valueHash.get((String) values.nextElement());
           if(hashValue==null) 
               continue;
           if(!hashValue.equals("")) {
               inClause.append(sep);
               inClause.append(quote);
               inClause.append(hashValue);
               inClause.append(quote);

               sep = ",";  // OK, now that we've got a value, let's put commas
           }
       }
       if(sep.equals("")) {
           inClause.append("null");
       }
       if(isIn) {
           inClause.append(")");
       }
       
       return( inClause.toString() );
   }
     
   /**  
    * <code>getName</code>  
    * <p>  
    * This method returns the column name.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code>   
    * @return name <code>String</code> the column name.  
    */  
   //---------------------------------------------------------------------------  
   public String getName(){  
   //---------------------------------------------------------------------------  
      return name;  
   }// getName() ENDS  

   /**  
    * <code>getObject</code>  
    * <p>  
    * This method columns value as an java.lang.Object/
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code>   
    * @return name <code>String</code> the column name.  
    */  
   //---------------------------------------------------------------------------  
   public Object getObject(){  
   //---------------------------------------------------------------------------  
      return object;  
   }//getObject() ENDS  
   
   /**  
    * <code>getSize</code>  
    * <p>  
    * This method returns the column size.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code>   
    * @return size <code>String</code> the column size.  
    */  
   //---------------------------------------------------------------------------  
   public String getSize(){  
   //---------------------------------------------------------------------------  
      return size;  
   }// getSize() ENDS  
  
   /**  
    * <code>getSelectFormat</code>  
    * <p>  
    * This method returns the column select format.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code>   
    * @return selectFormat <code>String</code> the column selectFormat.  
    */  
   //---------------------------------------------------------------------------  
   public String getSelectFormat(){  
   //---------------------------------------------------------------------------  
      return selectFormat;  
   }// getSelectFormat() ENDS  
  
   /**  
    * <code>getType</code>  
    * <p>  
    * This method returns the column type.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code>   
    * @return type <code>String</code> the column type.  
    */  
   //---------------------------------------------------------------------------  
   public String getType(){  
   //---------------------------------------------------------------------------  
      return type;  
   }// getType() ENDS  
  
   /**  
    * <code>getValue</code>  
    * <p>  
    * This method returns the column value.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code>   
    * @return value <code>String</code> the column value.  
    */  
   //---------------------------------------------------------------------------  
   public String getValue(){  
   //---------------------------------------------------------------------------  
      return getValue( getArrayIndex() );  
   }// getValue() ENDS  
   
   public String getValue(String arrayIndex) {
       return (String) valueHash.get( arrayIndex );
   }// getValue() ENDS
   
  
   /**  
    * <code>setBind</code>  
    * <p>  
    * This method sets the column bind.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param bind <code>String</code> the column bind  
    * @return <code>none</code>  
    */  
   //---------------------------------------------------------------------------  
   public void setBind(String bind){  
   //---------------------------------------------------------------------------  
      this.bind=bind;  
   }// setBind() ENDS  
  
  
   /**  
    * <code>setName</code>  
    * <p>  
    * This method sets the column name.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param name <code>String</code> the column name  
    * @return <code>none</code>  
    */  
   //---------------------------------------------------------------------------  
   public void setName(String name){  
   //---------------------------------------------------------------------------  
      this.name=name;  
   }// setName() ENDS  
   
   /**  
    * <code>setObject</code>  
    * <p>  
    * This method columns object.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param object <code>Object</code>  The object value. 
    * @return  <code>none</code>
    */  
   //---------------------------------------------------------------------------  
   public void setObject(Object object){  
   //---------------------------------------------------------------------------  
      this.object=object;  
   }//setObject() ENDS  
  
   /**  
    * <code>setSize</code>  
    * <p>  
    * This method sets the column size.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param size <code>String</code> the column size  
    * @return <code>none</code>  
    */  
   //---------------------------------------------------------------------------  
   public void setSize(String size){  
   //---------------------------------------------------------------------------  
      this.size=size;  
   }// setSize() ENDS  
  
   /**  
    * <code>setSelectFormat</code>  
    * <p>  
    * This method sets the column select format.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param selectFormat <code>String</code> the column selectFormat  
    * @return <code>none</code>  
    */  
   //---------------------------------------------------------------------------  
   public void setSelectFormat(String selectFormat){  
   //---------------------------------------------------------------------------  
      this.selectFormat=selectFormat;  
   }// setSelectFormat() ENDS  
  
   /**  
    * <code>setType</code>  
    * <p>  
    * This method sets the column type.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param type <code>String</code> the column type  
    * @return <code>none</code>  
    */  
   //---------------------------------------------------------------------------  
   public void setType(String type){  
   //---------------------------------------------------------------------------  
      this.type=type.toUpperCase();  
   }// setType() ENDS  
  
   /**  
    * <code>setValue</code>  
    * <p>  
    * This method sets the column value.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param value <code>String</code> the column value  
    * @return <code>none</code>  
    */  
   //---------------------------------------------------------------------------  
   public void setValue(String value){  
   //---------------------------------------------------------------------------  
       setValue(value, getArrayIndex());
   }// setValue() ENDS  
   
   public void setValue(String value, String arrayIndex) {
       // this.value=value;
       if(value==null)
           value="";
       valueHash.put(arrayIndex,value);
   }// setValue() ENDS
  
   /**  
    * <code>toString</code>  
    * <p>  
    * This method sets the column bind.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param bind <code>String</code> the column bind  
    * @return <code>none</code>  
    */  
   //---------------------------------------------------------------------------  
   public String toString(){  
   //---------------------------------------------------------------------------  
      StringBuffer buffer=new StringBuffer("\nColumnProperties\n   ");  
      buffer.append("Bind         = "+getBind()+"\n   ");  
      buffer.append("Name         = "+getName()+"\n   ");  
      buffer.append("Size         = "+getSize()+"\n   ");  
      buffer.append("Type         = "+getType()+"\n   ");  
      buffer.append("Value        = "+valueHash+"\n   ");  
      buffer.append("SelectFormat = "+getSelectFormat()+"\n   ");  
      buffer.append("Object       = "+getObject().getClass()+"\n   ");  
      buffer.append("ArrayIndex   = "+getArrayIndex()+"\n");
      buffer.append("*****\n\n");  
  
      if(DEBUG)  
         System.out.println(buffer.toString());  
  
      return buffer.toString();  
   }
   
   /** Getter for property arrayIndex.
    * @return Value of property arrayIndex.
    */
   public String getArrayIndex() {
       return this.arrayIndex;
   }
   
   /** Setter for property arrayIndex.
    * @param arrayIndex New value of property arrayIndex.
    */
   public void setArrayIndex(String arrayIndex) {
       this.arrayIndex = arrayIndex;
   }
   
  
   //***************************************************************************  
   // Class Variables  
   //***************************************************************************  
   protected String bind="";  
   protected String name="";  
   protected Object object=new Object();
   protected String selectFormat="";  
   protected String size="";  
   protected String type="";  
   protected Hashtable valueHash = new Hashtable();
   protected boolean DEBUG=false;  
   private String arrayIndex = "0";
}// ColumnProperties() END 
