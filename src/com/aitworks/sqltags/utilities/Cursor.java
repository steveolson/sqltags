/* $Id: Cursor.java,v 1.7 2002/06/20 19:32:44 solson Exp $
 * $Log: Cursor.java,v $
 * Revision 1.7  2002/06/20 19:32:44  solson
 * added check for resultSet within open()
 *
 * Revision 1.6  2002/06/20 18:04:28  solson
 * removed primary key stuff ... was only needed for caching which has
 * been deleted
 *
 * Revision 1.5  2002/05/23 15:51:11  solson
 * removed all references to caching implementation
 *
 * Revision 1.4  2002/05/21 12:36:06  booker
 * final checkin of sqltags
 *
 * Revision 1.3  2002/04/03 14:49:24  booker
 * Worked on adding Object to ColumnProperties.
 *
 * Revision 1.2  2002/03/15 14:33:32  solson
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
import java.io.InputStream;  
import java.io.IOException;  
import java.sql.ResultSet;  
import java.sql.ResultSetMetaData;  
import java.sql.SQLException;  
import java.util.Enumeration;  
import java.util.Hashtable;  
import java.util.Vector;  
  
/**  
 * <code>Cursor</code>  
 * <p>  
 * The Cursor class is used to make generic calls to the database.  
 * </p>  
 * @author  Booker Northington II  
 * @version 1.0  
 * @since   1.3  
 * @param none <code>none</code> none.  
 * @see BodyTagSupport <code>BodyTagSupport</code> For more information.  
 * @return none <code>none</code> none.  
 */  
public class Cursor extends SQLTags{  
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
  
   /**  
    * <code>open</code>  
    * <p> 
    * This method executes an sql statement.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param sql <code>String</code> The sql statement to execute.  
    * @return true <code>boolean</code> true if no errors were encountered.
    */  
   //---------------------------------------------------------------------------  
   public boolean open(String sql){  
   //---------------------------------------------------------------------------  
      boolean returnValue=true;  

      try{  
         if(sql!=null){  
            executeSQL(sql,true);  
            if(resultSet==null){
                setException(new Exception("Cursor:open: No ResultSet"));
                return false;
            }
            returnValue=resultSet.first();  
            columnPropertiesHash=new Hashtable();  

            if(returnValue){  
               ResultSetMetaData metaData=resultSet.getMetaData();  
               int numberOfColumns=metaData.getColumnCount();  
               int index=1;  
               // primaryKeyVector=utilities.getStringTokenVector(getPrimaryKeys(),",");  

               while(index<=numberOfColumns){   
                  String columnBind="?";  
                  String columnName=metaData.getColumnName(index);  
                  String columnType=metaData.getColumnTypeName(index);  
                  String columnSize=metaData.getColumnDisplaySize(index)+"";  
                  String columnValue="";  
                  Object object=resultSet.getObject(columnName);

                  if(columnType.equals("LONG")||columnType.equals("CLOB")||  
                                                columnType.equals("BLOB")){  
                     if(resultSet.getAsciiStream(columnName)!=null)  
                        columnValue=readAsciiStream(columnName,resultSet);  
                  }  
                  else   
                     columnValue=resultSet.getString(columnName);  

                  ColumnProperties item=new ColumnProperties(   
                  columnName,columnSize,columnName,  
                  columnType,columnBind,columnValue,object);  
                  getColumnPropertiesHash().put(columnName,item);  
                  index++;  
               }

               /*
               StringBuffer primaryKeyBuffer=  
               new StringBuffer(disectQueryString(sql,"where",true)+" where ");  

               for(int item=0;item<primaryKeyVector.size();item++){  
                  String name=(String)primaryKeyVector.elementAt(item);  
                  primaryKeyBuffer.append(name+"=:"+name+",");  
               }  

               primaryKeyBuffer.setLength(primaryKeyBuffer.length()-1);  
                */
               resultSet.beforeFirst();  
            }  
         }
      }  
      catch(SQLException exception){  
         log("Cursor.open(String): "+exception.toString());  
      }  

      return returnValue;  
   }// open() ENDS  
  
   /**  
    * <code>open</code>  
    * <p>  
    * This method performs a select.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return true <code>boolean</code> true if select was ok.  
    */  
   //---------------------------------------------------------------------------  
   public boolean open(){  
   //---------------------------------------------------------------------------  
      return select(getSql());  
   }// getOpen() ENDS  
  
   /**  
    * <code>readAsciiStream</code>  
    * <p>  
    * This method reads the ascii stream of a LOB.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  columnName<code>String</code> the column we are reading.
    * @param  resultSet<code>ResultSet</code> the set to read from.
    * @return string<code>String</code> the data read. 
    */  
   //---------------------------------------------------------------------------  
   public String readAsciiStream(String columnName,ResultSet resultSet){  
      StringBuffer buffer=new StringBuffer();  

      try{  
         InputStream is=null;  
         is=resultSet.getAsciiStream(columnName);  
         int charByte=0;  

         while((charByte=is.read())!=-1)  
            buffer.append((char)charByte);  
      }  
      catch(IOException exception){  
         log("Cursor.readAsciiStream: "+exception.toString());  
      }  
      catch(SQLException exception){  
         log("Cursor.readAsciiStream: "+exception.toString());  
      }  

      return buffer.toString();  
   }//readAsciiStream() ENDS  
  
   /**  
    * <code>getPrimaryKeyVector</code>  
    * <p>  
    * This method is used to see the primary keys associated with the query.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code></code>  
    * @return primaryKeyVector <code>Vector</code> A vector of primary keys.
    */  
   //---------------------------------------------------------------------------  
   public Vector getPrimaryKeyVector(){  
   //---------------------------------------------------------------------------  
      return primaryKeyVector;  
   }//primaryKeyVector() ENDS
  
   /**  
    * <code>getWhere</code>  
    * <p>  
    * This method returns the complete sql statement.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return where <code>String</code> The current sql statement.  
    */  
   //---------------------------------------------------------------------------  
   public String getWhere(){  
   //---------------------------------------------------------------------------  
      return getSql();  
   }// getWhere() ENDS  
  
   /**  
    * <code>setWhere</code>  
    * <p>  
    * This method sets the sql statement.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param where <code>String</code> the sql statement.
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public void setWhere(String sql){  
   //---------------------------------------------------------------------------  
      setSql(sql);  
   }//setWhere() ENDS
  
   /**  
    * <code>toString</code>  
    * <p>  
    * This is the class toString method. It should be used for debugging only
    * since it returns a formatted list of values related to this class.
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
      StringBuffer buffer=new StringBuffer("*****Cursor:");  
      buffer.append("\tcolumnPropertiesHash="+columnPropertiesHash);  
      buffer.append("\tDEBUG="+DEBUG);  
      buffer.append("\tid="+id);  
      buffer.append("\tname="+name);  
      buffer.append("\ttableName="+tableName);  
      buffer.append("\tutilities="+utilities);  
      return buffer.toString();  
   }// toString() ENDS  
  
   /**  
    * <code>getID</code>  
    * <p>  
    * This method returns the id associated with the cursor tag.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return id <code>String</code> the id name.
    */  
   //---------------------------------------------------------------------------  
   public String getId(){  
   //---------------------------------------------------------------------------  
      return id;  
   }//getId() ENDS
  
   /**  
    * <code>getName</code>  
    * <p>  
    * This method returns the value of the attribute, name.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return string <code>String</code> the value for the name attribute
    */  
   //---------------------------------------------------------------------------  
   public String getName(){  
   //---------------------------------------------------------------------------  
      return name;  
   }//getName() ENDS
  
   /**  
    * <code>setName</code>  
    * <p>  
    * This method sets the value of the name attribute.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param string <code>String</code>the name attribute
    * @return <code>none</code>
    */  
   //---------------------------------------------------------------------------  
   public void setName(String name){  
   //---------------------------------------------------------------------------  
      this.name=name;  
   }//setName() ENDS
  
   /**  
    * <code>select</code>  
    * <p>  
    * This method performs a select. It expects the sql statement to have 
    * already been set.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return true <code>boolean</code> true, if everything went as expceted.  
    */  
   //---------------------------------------------------------------------------  
   public boolean select(){  
   //---------------------------------------------------------------------------  
      return open();  
   }//select() ENDS
  
   /**  
    * <code>select</code>  
    * <p>  
    * This method performs a sql statement based on a query passed in.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param sql <code>String</code> The sql statement to execute.  
    * @return true <code>boolean</code> true, if everything went as expceted.  
    */  
   //---------------------------------------------------------------------------  
   public boolean select(String sql){  
   //---------------------------------------------------------------------------  
      return open(sql);  
   }//select() ENDS
  
   /**  
    * <code>getOrderBy</code>  
    * <p>  
    * This method returns the orderBy clause for this tag..  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return orderBy <code>String</code> The order by clause.  
    */  
   //---------------------------------------------------------------------------  
   public String getOrderBy(){  
   //---------------------------------------------------------------------------  
      return getWhere();  
   }// getOrderBy() ENDS  
  
   /**  
    * <code>setOrderBy</code>  
    * <p>  
    * This method sets the order by clause for this tag.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param value <code>String</code> The order by clause value.  
    * @return none <code>none</code> none  
    */  
   //---------------------------------------------------------------------------  
   public void setOrderBy(String value){  
   //---------------------------------------------------------------------------  
      setWhere(value);  
   }//setOrderBy() ENDS
  
   /**  
    * <code>insert</code>  
    * <p>  
    * This method inserts a record into the database  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return true <code>boolean</code> true, if everything went ok, otherwise   
    *        false.  
    */  
   //---------------------------------------------------------------------------  
   public boolean insert(){   
   //---------------------------------------------------------------------------  
      return true;  
   }//insert() ENDS
  
   /**  
    * <code>update</code>  
    * <p>  
    * This method updates a record in the database  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return true <code>boolean</code> true, if everything went ok, otherwise   
    *        false.  
    */  
   //---------------------------------------------------------------------------  
   public boolean update(){  
   //---------------------------------------------------------------------------  
      return true;  
   }// update() ENDS  
  
   /**  
    * <code>delete</code>  
    * <p>  
    * This method removes a record from the database  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return true <code>boolean</code> true, if everything went ok, otherwise   
    *        false.  
    */  
   //---------------------------------------------------------------------------  
   public boolean delete(){  
   //---------------------------------------------------------------------------  
      return true;  
   }//delete() ENDS
  
   /**  
    * <code>getString</code>  
    * <p>  
    * This method returns the value of the specified column.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param key <code>String</code> The column name.  
    * @return value <code>String</code> The value of the column.  
    */  
   //---------------------------------------------------------------------------  
   public String getString(String key){  
   //---------------------------------------------------------------------------  
      return getColumnValue(key);  
   }//getString() ENDS
  
   /**  
    * <code>getTableName</code>  
    * <p>  
    * This method returns the table name.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>none</code>   
    * @return tableName <code>String</code> The name of the table.  
    */  
   //---------------------------------------------------------------------------  
   public String getTableName(){  
   //---------------------------------------------------------------------------  
      return tableName;  
   }//getTableName() ENDS  
  
   /**  
    * <code>setTableName</code>  
    * <p>  
    * This method sets the table name.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param string<code>String</code> the table name. 
    * @return <code>none</code>
    */  
   //---------------------------------------------------------------------------  
   public void setTableName(String tableName){  
   //---------------------------------------------------------------------------  
      this.tableName=tableName;  
   }//setTableName() ENDS
  
   /**  
    * <code>getColumnPropertiesHash</code>  
    * <p>  
    * This method returns the table name.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>none</code>   
    * @return columnPropertiesHash<code>Hashtable</code> The column properties.  
    */  
   //---------------------------------------------------------------------------  
   public Hashtable getColumnPropertiesHash(){  
   //---------------------------------------------------------------------------  
      return columnPropertiesHash;  
   }// getColumnPropertiesHash() ENDS  
  
   /**  
    * <code>setFKParent</code>  
    * <p>  
    * This method sets the depth used when initializing the foreign keys parent.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param depth<code>int</code> sets the foreign key depth  
    * @return <code>none</code>  
    */  
   //---------------------------------------------------------------------------  
   public void setFKParent(int depth){  
   //---------------------------------------------------------------------------  
      this.depth=depth;  
   }//setFKParent() ENDS 
  
   //***************************************************************************  
   // Class Variables  
   //***************************************************************************     
   private Hashtable columnPropertiesHash=new Hashtable();  
   private boolean   DEBUG=false;  
   protected String  id;  
   protected String  name;  
   private String    tableName="CURSOR";  
   private Utilities utilities=new Utilities();    
}// Cursor() ENDS  
