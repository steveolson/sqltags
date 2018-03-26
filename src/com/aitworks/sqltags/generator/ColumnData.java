/* $Id: ColumnData.java,v 1.2 2002/03/15 14:23:45 solson Exp $
 * $Log: ColumnData.java,v $
 * Revision 1.2  2002/03/15 14:23:45  solson
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
package com.aitworks.sqltags.generator;  
import java.util.*;  
  
final class ColumnData{  
   /**  
    * <code>ColumnData</code>  
    * <p>  
    * Class Constructor. Here we initialize the values of the column.   
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param name <code>String</code> the column name.  
    * @param type <code>String</code> the column type.  
    * @param length <code>String</code> the column size.  
    * @return none <code></code>  
    */  
   //---------------------------------------------------------------------------  
   public ColumnData(String name, String type, String length){  
   //---------------------------------------------------------------------------  
      this.name=name;  
      this.type=type;  
      this.length=length;  
   }// ColumnData() Constructor ENDS  
  
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
  
   /**  
    * <code>getName</code>  
    * <p>  
    * Returns the column name.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>none</code>   
    * @return name <code>String</code> the column name.  
    */  
   //---------------------------------------------------------------------------  
   public String getName(){return name;  
   //---------------------------------------------------------------------------  
   }//getName() ENDS  
  
   /**  
    * <code>getType</code>  
    * <p>  
    * Returns the column type.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>none</code>   
    * @return type <code>String</code> the column type.  
    */  
   //---------------------------------------------------------------------------  
   public String getType(){return type;  
   //---------------------------------------------------------------------------  
   }//getType() ENDS  
  
   /**  
    * <code>getLength</code>  
    * <p>  
    * Returns the column length.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>none</code>   
    * @return length <code>String</code> the column length.  
    */  
   //---------------------------------------------------------------------------  
   public String getLength(){return length;  
   //---------------------------------------------------------------------------  
   }//getLength() ENDS  
  
   /**  
    * <code>setName</code>  
    * <p>  
    * Sets the column name.   
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param name<code>String</code> the column name.  
    * @return <code>none</code>  
    */  
   //---------------------------------------------------------------------------  
   public void setName(String name){  
   //---------------------------------------------------------------------------  
      this.name=name;  
   }//setName() ENDS  
  
   /**  
    * <code>setType</code>  
    * <p>  
    * Sets the column type.   
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param type<code>String</code> the column type.  
    * @return <code>none</code>  
    */  
   //---------------------------------------------------------------------------  
   public void setType(String type){  
   //---------------------------------------------------------------------------  
      this.type=type;  
   }//setType() ENDS  
  
   /**  
    * <code>setLength</code>  
    * <p>  
    * Sets the column length.   
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param length<code>String</code> the column length.  
    * @return <code>none</code>  
    */  
   //---------------------------------------------------------------------------  
   public void setLength(String length){  
   //---------------------------------------------------------------------------  
       this.length=length;  
   }//setLength() ENDS  
  
   //---------------------------------------------------------------------------  
   public String toString(){  
   //---------------------------------------------------------------------------  
      StringBuffer buffer=new StringBuffer();  
      buffer.append("\n************************************************\n");  
      buffer.append(name+"\n");  
      buffer.append(type+"\n");  
      buffer.append(length+"\n");  
      buffer.append("************************************************\n\n");  
  
      return buffer.toString();  
   }// toString() ENDS  
  
   //***************************************************************************  
   //                      Class variable section  
   //***************************************************************************  
   private String length="";  
   private String name="";  
   private String type="";  
}//ColumnData() ENDS  
