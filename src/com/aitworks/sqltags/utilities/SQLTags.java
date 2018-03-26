/* $Id: SQLTags.java,v 1.51 2002/08/16 15:46:13 jpoon Exp $
 * $Log: SQLTags.java,v $
 * Revision 1.51  2002/08/16 15:46:13  jpoon
 * little fix for start row
 *
 * Revision 1.50  2002/08/13 18:00:20  jpoon
 * fix bug in fetch,
 * check startRow for null and "" first before parseInt
 *
 * Revision 1.49  2002/08/07 15:13:10  jpoon
 * re-construct connection manager
 *
 * Revision 1.48  2002/07/24 19:16:48  jpoon
 * fix paging
 *
 * Revision 1.47  2002/07/17 19:23:58  solson
 * cleaned up the Handler calls.  Modified calls to the handler class and fixed
 * parameters to class ... changed parameter from "Object" to "SQLTags"
 *
 * Revision 1.46  2002/07/03 15:04:43  jpoon
 * comment out
 * System.out.println("Skipping bind: "+key);
 *
 * Revision 1.45  2002/07/02 19:10:21  jpoon
 * change SQLTagsRequest to HttpServletRequest
 *
 * Revision 1.44  2002/06/27 13:33:17  jpoon
 * take getID() from select(String )
 *
 * Revision 1.43  2002/06/26 17:41:54  solson
 * added getId() after tableName in select(where) so alias name can be
 * used in _SELECT, _BIND, and where definitions
 *
 * Revision 1.42  2002/06/25 18:57:36  solson
 * changes for SQLTagsRequest (HttpServletRequest instead of ServletRequest)
 * also added pass-thru calls for most of the httpServletRequest calls for
 * convience
 *
 * Revision 1.41  2002/06/25 16:05:53  jpoon
 * fix paging code
 *
 * Revision 1.40  2002/06/24 23:17:04  jpoon
 * add getConnectionTag
 *
 * Revision 1.39  2002/06/20 19:34:54  solson
 * added exception to setException, added exception to setException(event)
 *
 * Revision 1.38  2002/06/13 19:20:46  jpoon
 * add pageContext to SQLTagsHandler
 *
 * Revision 1.37  2002/05/30 19:47:02  jpoon
 * Changes for MimeData
 *
 * Revision 1.36  2002/05/23 15:51:11  solson
 * removed all references to caching implementation
 *
 * Revision 1.35  2002/05/21 12:36:06  booker
 * final checkin of sqltags
 *
 * Revision 1.34  2002/05/16 18:01:12  booker
 * format change.
 *
 * Revision 1.33  2002/05/16 14:23:03  booker
 * added method getSQLTagRequesParameter() to retrieve
 * a parameter from the SQLTagsRequest object.
 *
 * Revision 1.32  2002/04/23 21:39:20  booker
 * menuing changes
 *
 * Revision 1.31  2002/04/20 16:44:44  booker
 * added method
 *
 * Revision 1.30  2002/04/15 18:07:16  booker 
 * Removed the following methods:
 * 	setPreparedStatementBindColumns()
 * 	haveLOBS()
 * Changed the following methods to private:
 * 	getBindColumns()
 * 	getColumnNameSelectList()
 * 	getCommaString()
 * 	isSelectStatement()
 * 	getPreviousStartPosition()
 * 	getPreviousRow()
 * 	readClobBlobLong()
 * 	setStopRow()
 * 	setCurrentRow()
 * 	updateColumnPropertiesHash()
 * 	updateArray()
 *
 * Revision 1.29  2002/04/15 17:03:41  booker
 * Removed the following methods:
 * 	writeErrorToSDTOUD()
 * 	getCurrentVectorKeysSize()
 * 	getDebug()
 * 	getDefaultDateFormat()
 * 	getOutput()
 * 	getSaveColumn()
 * 	setDefaultDateFormat()
 * 	setOutput()
 * 	setSaveHash()
 * 	setSelectHash()
 * Changed the sope of the following methods to private:
 * 	isColumnTypeValid()
 * 	isData()
 * 	getColonString()
 *
 * Revision 1.28  2002/04/10 17:36:52  booker
 * Modifed code to work with the binding of values
 * to the column properties object.
 *
 * Revision 1.27  2002/04/05 18:52:59  booker
 * Modified creation of sqlTagHandler within the
 * setHandlerClass() and getSQLTagsHandler() methods.
 *
 * Revision 1.26  2002/04/05 00:05:02  solson
 * removed debugging output in two places
 *
 * Revision 1.25  2002/04/04 22:36:00  solson
 * removed requirement to have a '?' character in the _BIND string.  If the '?' is
 * missing, then we don't attempt to bind the value for that column-- we assume
 * that a constant expression like SYSDATE or EMPTY_CLOB() is used instead.
 *
 * Revision 1.24  2002/04/03 16:55:36  booker
 * Added a getObject(String) wrapper for the
 * columnPropertiesHash.
 *
 * Revision 1.23  2002/04/03 14:49:23  booker
 * Worked on adding Object to ColumnProperties.
 *
 * Revision 1.22  2002/03/28 18:09:39  booker
 * Added getException(JspWriter) method
 *
 * Revision 1.21  2002/03/15 14:33:31  solson
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
import  com.aitworks.sqltags.jsptags.ConnectionTag;
import com.aitworks.sqltags.interfaces.IExceptionHandler;  
import java.util.Arrays;  
import java.util.ArrayList;  
import java.util.Hashtable;  
import java.util.Vector;  
import java.util.Enumeration;  
import java.util.Properties;
import java.io.ByteArrayInputStream;  
import java.io.InputStream;  
import java.io.IOException;  
import java.io.StringReader;
import java.io.Reader;
import java.sql.Blob;  
import java.sql.Clob;  
import java.sql.ResultSet;  
import java.sql.Statement;  
import java.sql.Connection;  
import java.sql.PreparedStatement;  
import java.sql.SQLException;  
import java.sql.ResultSetMetaData;  
import javax.servlet.ServletContext;  
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.JspWriter;
import javax.servlet.ServletException;
import javax.servlet.jsp.JspException;
  
/**  
 * <code>SQLTags</code>  
 * <p>  
 * This abstract class provides the contract which is  
 * signed by all the base tables.  
 * </p>  
 * @author Booker Northington II  
 * @version 1.0  
 * @since 1.3 */  
//------------------------------------------------------------------------------  
public abstract class SQLTags extends BodyTagSupport implements   
                                      IExceptionHandler{  
//------------------------------------------------------------------------------     
  //***************************************************************************  
   // Class Constructors  
   //***************************************************************************  
   /**  
    * <code>Default Constructor</code>  
    * <p>  
    * This method establishes a connection to the database.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code>  
    * @return <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
   public SQLTags(){  
   //---------------------------------------------------------------------------  
       // sqlEvent.addListener(this);
   }//sqlTags() ENDS
     
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
   // Public Methods  
   //*************************************************************************** 
   /**  
    * <code>addArrayIndex</code>  
    * <p>  
    * This method is used to process batch operations
    * </p>  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return value <code>int</code> A value indicating how to continue  
    *        processing the tag.  
    */  
   //---------------------------------------------------------------------------  
   public void addArrayIndex(String arrayIndex){
   //---------------------------------------------------------------------------  
       arrayIndexHash.put(arrayIndex,arrayIndex);
   }//addArrayIndex() ENDS
   
   /**  
    * <code>clear</code>  
    * <p>  
    * This method resets the columns to empty strings.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code>  
    * @return <code>none</code>  
    */  
   //---------------------------------------------------------------------------  
   public void clear(){  
      //---------------------------------------------------------------------------  
      Hashtable hash=getColumnPropertiesHash();  
      Enumeration enum=hash.keys();  
        
      for(;enum.hasMoreElements();){  
         String key=(String)enum.nextElement();  
         setColumnProperty( "value", key, "" );
      }  
   }// clear() ENDS  
     
   /**  
    * <code>close</code>  
    * <p>  
    * This method closes the result set and the statement.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code>  
    * @return none <code>none</code> none  
    */  
   //---------------------------------------------------------------------------  
   public void close(){  
   //---------------------------------------------------------------------------  
      try{  
         isOpen(false);             
         // sqlTagHandler=getSQLTagsHandler();  
           
         if(!sqlTagHandler.postQuery(this)){  
            ;  
         }  
         
         if(selectPreparedStatement!=null)  
            selectPreparedStatement.close();  
           
         if(ps!=null)  
            ps.close();  
           
         if(stmt!=null)  
            stmt.close();  
      }  
      catch(SQLException exception){  
         setException(exception);  
         log(getTableName()+".close(): "+getSql()+"\n"+exception);  
      }          
   }// close() ENDS  
   
   /**  
    * <code>delete</code>  
    * <p>  
    * This method will remove records from the database.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code></code>  
    * @return true <code>boolean</code> true, if everything went ok.  
    */  
   //---------------------------------------------------------------------------  
   public boolean delete(){  
   //---------------------------------------------------------------------------  
       return delete("where "+ getColonString("delete",","));  
   }
   
   /**  
    * <code>delete</code>  
    * <p>  
    * This method will remove records from the database based on a where
    * supplied by the user.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none where<code>String</code>  the where clause.
    * @return true <code>boolean</code> true, if everything went ok.  
    */  
   //---------------------------------------------------------------------------  
   public boolean delete(String where){
   //---------------------------------------------------------------------------  
      boolean returnValue=true;  
      // sqlTagHandler=getSQLTagsHandler();  
      String sqlQuery = "delete from " +getTableName() + " " + where;
        
      if(!sqlTagHandler.preDelete(this))  
         returnValue=false;  
      else{  
         returnValue = executeSQL(sqlQuery,true);  
         if(returnValue){
             if(!sqlTagHandler.postDelete(this))  
                returnValue=false;  
         }
         if(returnValue)
            clear();  
      }  
        
      return returnValue;  
   }// delete() ENDS  
   
   /**  
    * <code>deleteArray</code>  
    * <p>  
    * This method will remove records within the arrayIndex.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none where<code>String</code>  the where clause.
    * @return true <code>boolean</code> true, if everything went ok.  
    */  
   //---------------------------------------------------------------------------  
   public boolean deleteArray(){
   //---------------------------------------------------------------------------  
       Enumeration arrayIndexes = getArrayIndexes();
       
       while(arrayIndexes.hasMoreElements()){
           String arrayIndex = (String) arrayIndexes.nextElement();
           setArrayIndex( arrayIndex );
           
           if(!delete()){
               log("DeleteArray Failed on index="+arrayIndex);
               return false;
           }
       }
       
       return true;
   }//deleteArray ENDS
           
   /**  
    * <code>getArrayIndexes</code>  
    * <p>  
    * This method returns the current array index.
    * </p>  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return value <code>String</code> the array index.
    */  
   //---------------------------------------------------------------------------  
   public String getArrayIndex(){
   //---------------------------------------------------------------------------  
       return arrayIndex;
   }//getArrayIndex() ENDS   
   
   /**  
    * <code>getArrayIndexes</code>  
    * <p>  
    * This method is used in conjunction with batch processing.
    * </p>  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return enum <code>Enumeration</code>an enumeration of the current index.  
    */  
   //---------------------------------------------------------------------------  
   public Enumeration getArrayIndexes(){
   //---------------------------------------------------------------------------  
       return arrayIndexHash.keys();
   }//getArrayIndexes() ENDS
   
   /**  
    * <code>getArrayInputName</code>  
    * <p>  
    * This method return the array input name.
    * </p>  
    * @version 1.0  
    * @since   1.3  
    * @param  columnName<code>String</code> the column name.
    * @return columnName<code>String</code> the array input name
    */  
   //---------------------------------------------------------------------------  
   public String getArrayInputName(String columnName){
   //---------------------------------------------------------------------------  
       return getArrayInputName(columnName,getArrayIndex());
   }//getArrayInputName() ENDS
   
   
   /**  
    * <code>getArrayInputName</code>  
    * <p>  
    * This method return the array input name formatted.
    * </p>  
    * @version 1.0  
    * @since   1.3  
    * @param  columnName<code>String</code> the column name.
    * @param  arrayIndex<code>String</code> the array index.
    * @return columnName<code>String</code> the formatted column name.
    */  
   //---------------------------------------------------------------------------  
   public String getArrayInputName(String columnName,String arrayIndex){
   //---------------------------------------------------------------------------  
       return columnName + "[" + arrayIndex + "]";
   }//getArrayInputName() ENDS

   /**  
    * <code>disectQuerySting</code>  
    * <p>  
    * This method returns a portion of the query string.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  query<code>String</code>the query to disect.  
    * @param  keyWord<code>String</code>the word were looking for.  
    * @param  quotes<code>boolean</code>true if quotes are to be considered.,  
    * @return results <code>String</code>a portion of the query string.  
    */  
   //---------------------------------------------------------------------------  
   public String disectQueryString(String query,String keyWord,boolean quotes){  
      //---------------------------------------------------------------------------  
      String results="";  
      int index=0;  
      int start=0;  
      int stop=query.length();  
      boolean positionFound=false;  
        
      while(!positionFound){  
         index=query.indexOf(keyWord,start);  
           
         if(index!=-1){  
            if(quotes){  
               int beginQuote=index-1;  
               int endQuote=index+keyWord.length();  
                 
               while(query.charAt(beginQuote)==' '&&beginQuote>=0)  
                  beginQuote=beginQuote-1;  
                 
               if(beginQuote>=0&&query.charAt(beginQuote)!='\'')  
                  positionFound=true;  
                 
               if(positionFound){  
                  positionFound=false;  
                    
                  while(query.charAt(endQuote)==' '&&endQuote<stop)  
                     endQuote=endQuote+1;  
                    
                  if(endQuote<stop&&query.charAt(endQuote)!='\''){  
                     positionFound=true;  
                       
                     if(keyWord.equalsIgnoreCase("where"))  
                        results=query.substring(0,index);  
                     else  
                        results=query.substring(index,query.length());  
                  }  
               }  
            }  
            else{  
               results=query.substring(index,query.length());  
               positionFound=true;  
            }  
         }  
         else  
            positionFound=true;  
           
         start=index+1;  
      }  
        
      return results;  
   }// disectQueryString() ENDS  
   
   public boolean doArrayOperations() {
       Enumeration arrayLoop = operationHash.keys();
       while( arrayLoop.hasMoreElements() ){
           String arrayIndex = (String)arrayLoop.nextElement();
           setArrayIndex(arrayIndex);
           String operation = getOperation(arrayIndex);
           
           if( operation.equalsIgnoreCase("insert")) {
               if( !insert()) {
                   log("doArrayOperations: insert failed at "+arrayIndex+", aborting operation.");
                   return false;
               }
           } 
           else if( operation.equalsIgnoreCase("update")){
               if(!update()) {
                   log("doArrayOperations: update failed at "+arrayIndex+", aborting operation.");
                   return false;
               }
           }
           else if( operation.equalsIgnoreCase("delete")){
               if(!delete()) {
                   log("doArrayOperations: delete failed at "+arrayIndex+", aborting operation.");
                   return false;
               }
           }
           else if( operation.equalsIgnoreCase("select")){
               initialize(getDepth());
           }
           else {
                log("doArrayOperations: unknown-op at "+arrayIndex+" operation="+operation);
           }
       }
       return true;
   }// doArrayOperations() ENDS
     
   /**  
    * <code>executeSQL</code>  
    * <p>  
    * This method sets the current sql statement associated with this object.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param sql <code>String</code> the sql statement.  
    * @return none <code></code>  
    */  
   //---------------------------------------------------------------------------  
   public boolean executeSQL(String sql,boolean autoCommit) {  
   //---------------------------------------------------------------------------  
      String key="";  
      setSql(sql);  
      int vectorSize=0;  
      boolean openValue=true;  
      boolean selectStatement=false;  
      Connection connection=null;
      // first we have to bind our columns to their respective formats.
      sql=getBindColumns(sql);  
      
      // if we dont have a connection. bail out.
      try{
          connection=getConnection();
      } 
      catch(JspException exception){
          setException(exception);
          log("ExecuteSQL: Failed to obtain connection:" +exception);
          return false;
      }
      
      //make sure we know how how to read the various LOB types.
      setColumnTypes();      

      try{
          if(connection.isClosed()){
             // set the exception and let it blow out on the page.
              Exception exception=
                  new Exception("Failed to obtain open connection.");
              setException(exception);
          }
      } 
      catch(SQLException exception){
          //set the exception and let it blow out on the page.
          setException(exception);
      }

      try{ 
         //turn auto-commit off if thats what the tag requested.
         if(!autoCommit)  
            connection.setAutoCommit(false);  
 
         //are we doing a select?
         if(isSelectStatement(sql)){  
            selectStatement=true;  
            selectPreparedStatement=connection.prepareStatement(sql,  
               ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE  
            );                
            ps=selectPreparedStatement;  
            
            //a flag to prevent multiple opens of the same cursor.
            isOpen(true);  
         }  
         else  
            ps=connection.prepareStatement(sql,  
               ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);  
           
         //do we have any bind columns?
         if(bindColumnVector!=null)  
            vectorSize=bindColumnVector.size();  
         
         int position=0;
         
         //place the bind values in the prepared statement.
         for(int index=0;index<vectorSize;index++){  
            key=((String)bindColumnVector.elementAt(index)).toUpperCase();  
            String type = getColumnProperty("type",key);
            String bind = getColumnProperty("bind",key);
            
            if( bind.indexOf("?") == -1) {
                //System.out.println("Skipping bind: "+key);
                continue;
            } 
            
            // only increment position after seeing the ? ... 
            position++;
            // System.out.println("bind: "+key+" @position: "+position);

            if(isColumnTypeValid(characterStreamColumns,type))
                setCharacterStream(ps,getColumnValue(key),position);
            else if(isColumnTypeValid(binaryStreamColumns,type))
                setBinaryStream(ps,getColumnValue(key).getBytes(),position);
            else if(isColumnTypeValid(asciiStreamColumns,type))
                setAsciiStream(ps,getColumnValue(key).getBytes(),position);
            else  
                setString(ps,getColumnValue(key),position);  
         }  
           
         if(selectStatement){
            resultSet=ps.executeQuery();  
            resultSet.last();
            resultSetSize=resultSet.getRow();
            resultSet.beforeFirst();
         }
         else  
            ps.executeQuery();  
         
         if(sql.indexOf("insert")==0||  
            sql.indexOf("update")==0||sql.indexOf("delete")==0){  
            ps.close();  
         }  
      }  
      catch(SQLException exception){  

         System.out.println(getTableName()+".executeSQL(String): "+
                            sql+"\n"+exception+" currentBind="+key+"\n"+columnPropertiesHash);
         close();
         setException(exception);
         log(getTableName()+".executeSQL(String): "+  
         sql+"\n"+exception+" currentBind="+key);  
         openValue=false;  
      }  
        
      return openValue;  
   }// executeSQL() ENDS  
     
   /**  
    * <code>executeSQL</code>  
    * <p>  
    * This method sets the current sql statement associated with this object.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param sql <code>String</code> the sql statement.  
    * @return none <code></code>  
    */  
   //---------------------------------------------------------------------------  
   public boolean executeSQL(){  
   //---------------------------------------------------------------------------  
      return executeSQL(sql,true);  
   }// executeSQL() ENDS  
     
   /**  
    * <code>fetch</code>  
    * <p>  
    * This method fetches records returned from the database.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return true <code>boolean</code> if there are more records.  
    */  
   //---------------------------------------------------------------------------  
   public boolean fetch(){  
   //---------------------------------------------------------------------------  
        
      int begin=0;  
      int mod=0;  
      int lastItemInCursor=maxInt;  
      boolean oneMoreRecord=false;  
      boolean hasMore=false;  
      isData(false);  
      setColumnTypes();

      // get the row to display.  
      try{  
          if(getStartRow()!=null && !getStartRow().equals(""))
              begin=Integer.parseInt(getStartRow());
          else {
              begin=0;
              startRow=0+"";
          }
      }  
      catch(NumberFormatException exception){  
         begin=0;  
         startRow=0+"";  
         log(getTableName()+".fetch(): "+exception+"\n");  
      }  
        
      if(!isOpen()){  
         hasMore=false;  
      }  
      else{  
         try{  
            // set the cursor on the row to display.  
             currentRow=resultSet.getRow(); // == 0 before first fetch
             if(currentRow<begin){
                 do {
                     resultSet.next();
                     currentRow=resultSet.getRow();
                 } while(currentRow<begin && currentRow>0);
             }

           /**  
             *  Since we fetch the record before we can process it, I need  
             * to be able to know, before hand, whether or not the current  
             * record is the last record.  
             */  
            if(currentRow!=0){  
               resultSet.last();  
               lastItemInCursor=resultSet.getRow()-1;  
               resultSet.absolute(currentRow);  
            }  
              
            // indicate whether this is the first/last record in the set.  
            setLastRecord(resultSet.isLast());  
            setFirstRecord(resultSet.isFirst());  
              
            if(currentRow!=0){  
               isData(true);  
               mod=(currentRow%utilities.stringToInt(pageSize));  
                 
               if(mod==0){  
                  oneMoreRecord=true;  
                  setFirstRecord(true);  
               }  
               else  
                  setFirstRecord(false);  
            }  
            else  
               setFirstRecord(true);  
              
            if(resultSet.next()){  
               isData(true);  
               setLastRecord(resultSet.isLast());  
               hasMore=true;  
               clear();  
               String value="";  
               Enumeration enum=columnPropertiesHash.keys();  
                 
               // update the column properties  
               for(;enum.hasMoreElements();){  
                  String key=((String)enum.nextElement()).toUpperCase();  
                  String columnType=getColumnProperty("type",key).toUpperCase();
                  ColumnProperties temp=(ColumnProperties)
                        columnPropertiesHash.get(key);
                  updateColumnPropertiesObject(resultSet,temp,columnType);
                 
                  if(isColumnTypeValid(asciiStreamColumns,columnType))
                     readClobBlobLong(key);  
                  else if(isColumnTypeValid(binaryStreamColumns,columnType))
                     readClobBlobLong(key);  
                  else if(isColumnTypeValid(characterStreamColumns,columnType))
                     readClobBlobLong(key);  
                  else{  
                     try{  
                        if(resultSet.getString(key)!=null){  
                           value=resultSet.getString(key);  
                           setColumnValue(key,value);  
                        }  
                     }  
                     catch(SQLException exception){  
                        log(getTableName()+".fetch(): "+getSql()+"\n"+exception);  
                        close();  
                     }  
                       
                  }  
               }  
                 
               setFKParent();  
                 
               if(oneMoreRecord)  
                  setLastRecord(true);  
                 
               int intDisplaySize=getPageSize();  
               int stopRow=begin+intDisplaySize;  
                 
               if(currentRow==stopRow-1)  
                  setLastRecord(true);  
               else  
                  setLastRecord(false);  
                 
               if(currentRow==stopRow&&paging.equalsIgnoreCase("true")){  
                  setLastRecord(true);  
                    
                    
                  while((currentRow%intDisplaySize)!=0)  
                     currentRow++;  
                    
                  setCurrentRow(currentRow);  
                  startRow=currentRow+"";  
                    
                  if(DEBUG)  
                     log("setting startRow="+currentRow);  
                    
                  hasMore=false;  
                  close();  
               }  
            }  
            else  
               close();  
              
            if(currentRow>=lastItemInCursor){  
               setLastRecord(true);  
               isData(false);  
            }  
              
            // sqlTagHandler=getSQLTagsHandler();  
              
            if(!sqlTagHandler.postFetch(this))  
               hasMore=false;  

         }  
         catch(SQLException exception){  
            close();  
            setException(exception);  
            log(getTableName()+".fetch(): "+getSql()+"\n"+exception);  
            hasMore=false;  
         }  
      }  
        
      return hasMore;  
   }// fetch() ENDS
     
   /**  
    * <code>initialize</code>  
    * <p>  
    * The method dose a select, fetch, and then a close. It also ensures  
    * the the foreign keys have been initialize to the appropriate depth.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param depth <code>int</code> The number of layers to be initialize.  
    * @return none <code></code>  
    */  
   //---------------------------------------------------------------------------  
   public void initialize(int depth){  
   //---------------------------------------------------------------------------  
         select();  
         fetch();  
         close();  
         setFKParent(depth);  
   }// initialize() ENDS  
     
   /**  
    * <code>insert</code>  
    * <p>  
    * This method inserts records into the database.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code></code>  
    * @return true <code>boolean</code> true, if everything went ok.  
    */  
   //---------------------------------------------------------------------------  
   public boolean insert(){  
   //---------------------------------------------------------------------------  
      haveLob=false;  
      boolean returnValue=true;  
      preInsertSQL(getPreInsertSQL());  
      String colStr=getCommaString();  
      StringBuffer psQuery=new StringBuffer();  
      psQuery.append("insert into "+getTableName()+"("+colStr+") values(");  
      psQuery.append(getColonString("insert",",")+")");  
      // sqlTagHandler=getSQLTagsHandler();  
        
      if(!sqlTagHandler.preInsert(this))  
         returnValue=false;  
      else{  
         returnValue = executeSQL(psQuery.toString(),true);  
         if(returnValue) {
             if(!sqlTagHandler.postInsert(this))  
                returnValue=false;  
         }
         if(returnValue) {
            setFKParent();  
         }
      }  
        
      return returnValue;  
   }// insert() ENDS  
   
   public boolean insertArray(){
       Enumeration arrayIndexes = getArrayIndexes();
       while(arrayIndexes.hasMoreElements()){
           String arrayIndex = (String) arrayIndexes.nextElement();
           setArrayIndex( arrayIndex );
           
           if(!insert()){
               log("insertArray Failed on index="+arrayIndex);
               return false;
           }
       }
       return true;
   }//insertArray ENDS

   /**  
    * <code>isColumnTypeValid</code>  
    * <p>  
    * This method keeps track of whether there is any data.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param state <code>boolean</code> True if there are any records.  
    * @return none <code>none</code> none  
    */  
   //---------------------------------------------------------------------------  
   private boolean isColumnTypeValid(Vector vector,String string){
   //---------------------------------------------------------------------------  
      boolean isColumnValid=false;
      Enumeration enum=vector.elements();
   
      for(;enum.hasMoreElements();){
          String column=(String)enum.nextElement();
          
          if(column.equalsIgnoreCase(string)){
              isColumnValid=true;
              break;
          }
      }
      
      return isColumnValid;
   }// isColumnInVector() ENDS  
     
   /**  
    * <code>isData</code>  
    * <p>  
    * This method keeps track of whether there is any data.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param state <code>boolean</code> True if there are any records.  
    * @return none <code>none</code> none  
    */  
   //---------------------------------------------------------------------------  
   private void isData(boolean state){  
   //---------------------------------------------------------------------------  
      anyRecords=state;  
   }// isData() ENDS  
     
   /**  
    * <code>isData</code>  
    * <p>  
    * This method returns true if there is any data.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return anyRecords <code>boolean</code> True, if there are any records.  
    */  
   //---------------------------------------------------------------------------  
   private boolean isData(){  
      //---------------------------------------------------------------------------  
      return anyRecords;  
   }// isData() ENDS  
     
   /**  
    * <code>isFetch()</code>  
    * <p>  
    * This method returns the value of the hasFetch property.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return hasFetch <code>String</code> true, if a fetch has already  
    *        been done.  
    */  
   //---------------------------------------------------------------------------  
   public boolean isFetch(){  
   //---------------------------------------------------------------------------  
      return hasFetch;  
   }// isFetch() ENDS  
     
     
     
   /**  
    * <code>isFirstRecord</code>  
    * <p>  
    * This method indicates whether this is the first record of the result set.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return true <code>boolean</code> if this is the first record of  
    *        the result set.  
    */  
   //---------------------------------------------------------------------------  
   public boolean isFirstRecord(){  
   //---------------------------------------------------------------------------  
      return firstRecord;  
   }// isFirstRecord() ENDS  
     
   /**  
    * <code>isLastRecord</code>  
    * <p>  
    * This method indicates whether this is the last record within this result  
    * set.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return lastRecord <code>boolean</code> true, if this is the last  
    *        record within this result set.  
    */  
   //---------------------------------------------------------------------------  
   public boolean isLastRecord(){  
      //---------------------------------------------------------------------------  
      return lastRecord;  
   }// isLastRecord() ENDS  
     
   /**  
    * <code>isLastRecordOnPage</code>  
    * <p>  
    * This method indicates whether this is the first record of the result set.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return true <code>boolean</code> if this is the first record of  
    *        the result set.  
    */  
   //---------------------------------------------------------------------------  
   public boolean isLastRecordOnPage(){  
   //---------------------------------------------------------------------------  
      return lastRecordOnPage;  
   }// isLastRecordPage() ENDS  
     
   /**  
    * <code>isNextPage</code>  
    * <p>  
    * This method checks to see if we have any records left to place on the next  
    * page.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return haveNextPage <code>boolean</code> True, if there is data  
    *        to display.  
    */  
   //---------------------------------------------------------------------------  
   public boolean isNextPage(){  
   //---------------------------------------------------------------------------  
      int pageSize=getPageSize();  
      boolean haveNextPage=true;  
      int lastRecordDisplayed=0;  
      boolean validStartRow=false;  
      int displayCount=0;  
      
      if(!isData()||pageSize==maxInt)  
         haveNextPage=false;  
        
      return haveNextPage;  
   }// isNextPage() ENDS  
     
     
   /**  
    * <code>isPreviousPage</code>  
    * <p>  
    * This method checks to see if we have any records left  
    * to place on the previous  
    * page.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return havePreviousPage <code>boolean</code> True, if there is data  
    *        to display.  
    */  
   //---------------------------------------------------------------------------  
   public boolean isPreviousPage(){  
   //---------------------------------------------------------------------------  
      boolean havePreviousPage=false;  
      boolean validStartRow=false;  
        
      // get the display page size and the current row were on  
      int pageSize=getPageSize();  
      int lastRecordDisplayed=utilities.stringToInt(getStartRow());  
        
      /**  
       * if the total amount of records we have already displayed is  
       * greater than the page size, we know that there is a previous page,  
       * therefore we need to calculate its starting position.  
       */  
      if(lastRecordDisplayed>pageSize&&pageSize!=maxInt){  
         havePreviousPage=true;  
         previousStartPosition=lastRecordDisplayed-(pageSize*2);  
           
         /**  
          * There is a possible situation where the page size is greater  
          * the total amount of records retrieved. In this case their will  
          * be no previous page. Lets handle that condition now.  
          */  
         if(previousStartPosition<pageSize){  
            previousStartPosition=0;  
            havePreviousPage=false;  
         }  
      }  
        
      return havePreviousPage;  
   }// isPreviousPage() ENDS  
     
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
   public void log(String message){  
   //---------------------------------------------------------------------------  
      if(pageContext!=null)  
         pageContext.getServletContext().log(message);  
      else{  
         System.out.println(message);  
      }  
   }// log() ENDS  
  
   /**  
    * <code>logException</code>  
    * <p>  
    * This method logs exceptions.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return anyRecords <code>boolean</code> True, if there are any records.  
    */  
    //---------------------------------------------------------------------------  
   public void logException(Exception e){
       // SQLActionEvent object=(SQLActionEvent)event.getSource();
       log(new java.util.Date(System.currentTimeMillis())+": "+ e);
       System.out.println(new java.util.Date(System.currentTimeMillis())+": "+ e);
   }//logException() ENDS

   /**  
    * <code>next</code>  
    * <p>  
    * This method dose a fetch.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return true <code>boolean</code> If there are more records to fetch.  
    */  
   //---------------------------------------------------------------------------  
   public boolean next(){  
   //---------------------------------------------------------------------------  
      return fetch();  
   }// next() ENDS  
     
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
      return select();  
   }// open() ENDS  
     
   /**  
    * <code>open</code>  
    * <p>  
    * This method dose a select based on a where clause which is passed in.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param whereClause <code>String</code> The where clause.  
    * @return true <code>boolean</code> If everything went ok.  
    */  
   //---------------------------------------------------------------------------  
   public boolean open(String whereClause){  
   //---------------------------------------------------------------------------  
      return select(whereClause);  
   }// open() ENDS  
     
   /**  
    * <code>preInsertSQL</code>  
    * <p>  
    * This method processes the pre-insert instruction.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param insertString <code>String</code> The instruction to process.  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public void preInsertSQL(String insertString){  
   //---------------------------------------------------------------------------  
      ResultSet resultSet=null;  
      if(insertString==null||insertString.equals(""))  
         return;  
        
      try{  
         Connection connection=getConnection();
         stmt=connection.createStatement(  
         ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);  
         resultSet=stmt.executeQuery(insertString);  
         sqlFetch(resultSet);  
         stmt.close();  
      }  
      catch(JspException e) {
          log(getTableName()+".preInsertSQL(): Invalid connection: "+e);
      }
      catch(SQLException exception){  
         log(getTableName()+".preInsertSQL(): "+insertString+"\n"+exception);  
         close();  
      }  
   }// preInsertSQL() ENDS  
     
   /**  
    * <code>select</code>  
    * <p>  
    * This method will select records from the database based upon the primary  
    * key.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code></code> .  
    * @return true <code>boolean</code> true, if everything went ok.  
    */  
   //---------------------------------------------------------------------------  
   public boolean select(){  
   //---------------------------------------------------------------------------  
      if(where.trim().equals(""))  
         return select("where "+getColonString("primaryKey","and"));  
      else  
         return select(where);  
   }// select() ENDS  
     
   /**  
    * <code>select</code>  
    * <p>  
    * This method will select records from the database based upon a where clause  
    * which is passed into the method.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param where <code>String</code> The where condition.  
    * @return true <code>boolean</code> true, if everything went ok.  
    */  
   //---------------------------------------------------------------------------  
   public boolean select(String where){  
   //---------------------------------------------------------------------------  
      StringBuffer selectQuery=new StringBuffer("select ");  
      // sqlTagHandler=getSQLTagsHandler();  
      
      if(distinct.equalsIgnoreCase("true"))
         selectQuery.append("distinct ");
       
      if(!sqlTagHandler.preQuery(this))  
         return false;  
        
      if(isOpen())
         log(this+", cursor already open: "+ where);  
        
      Enumeration enum=columnPropertiesHash.keys();  
        
      for(;enum.hasMoreElements();){  
         String key=((String)enum.nextElement()).toUpperCase();  
         selectQuery.append(getColumnProperty("selectFormat",key)+" as "+key+",");  
      }  
        
      selectQuery.setLength(selectQuery.toString().lastIndexOf(","));  
      selectQuery.append(" from "+getTableName()+" "+where); 
      executeSQL(selectQuery.toString(),true);  
        
      return true;  
   }// select() ENDS  
   
   /**  
    * <code>setArrayIndex</code>  
    * <p>  
    * This method sets the array index.
    * </p>  
    * @version 1.0  
    * @since   1.3  
    * @param  arrayIndex<code>String</code> the array index
    * @return <code>none</code>
    */  
   //---------------------------------------------------------------------------  
   public void setArrayIndex(String arrayIndex){
   //---------------------------------------------------------------------------  
       this.arrayIndex=arrayIndex;
   }//setArrayIndex() ENDS
     
   /**  
    * <code>sqlFetch</code>  
    * <p>  
    * This method stores the values obtained from the query into the 
    * columnProperties object.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param resultSet <code>ResultSet</code> The results obtained from  
    *        the query..  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public boolean sqlFetch(ResultSet resultSet){  
   //---------------------------------------------------------------------------  
      boolean hasMore=false;  
      String columnName="";  
      setColumnTypes();
        
      try{  
         if(resultSet.next()){  
            // how many columns do we have  
            ResultSetMetaData metaData=resultSet.getMetaData();  
            int size=metaData.getColumnCount()+1;  
              
            // reset the columnPropertiesHash with the columns retrieved  
            for(int index=1;index<size;index++){  
               Enumeration enum=columnPropertiesHash.keys();  
               columnName=metaData.getColumnName(index);  
               hasMore=true;  
                 
               for(;enum.hasMoreElements();){  
                  String key=((String)enum.nextElement()).toUpperCase();  
                    
                  if(key.equalsIgnoreCase(columnName)){  
                     String columnType=
                        getColumnProperty("type",key).toUpperCase();                       

                     if(
                        isColumnTypeValid(asciiStreamColumns,columnType)||
                        isColumnTypeValid(binaryStreamColumns,columnType)||
                        isColumnTypeValid(characterStreamColumns,columnType))
                        readClobBlobLong(key);  
                     else  
                        setColumnValue(key,resultSet.getString(columnName));  
                       
                     break;  
                  }  
               }  
            }  
         }  
      }  
      catch(SQLException exception){  
         close();  
         setException(exception);  
         log(getTableName()+".sqlFetch(): "+getSql()+"\n"+exception);  
      }  
        
      return hasMore;  
   }// sqlFetch() ENDS  
     
   /*  
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
      StringBuffer buffer=new StringBuffer("*****SQLTags:");  
      buffer.append("\tanyRecords="+anyRecords);  
      buffer.append("\tbindColumnVector="+bindColumnVector);  
      buffer.append("\tblob="+blob);  
      buffer.append("\tchildName="+childName);  
      buffer.append("\tclob="+clob);  
      buffer.append("\tcolumnPropertiesHash="+columnPropertiesHash);  

      return buffer.toString();  
   }//toString() ENDS  
  
   /**  
    * <code>update</code>  
    * <p>  
    * This method updates records that are currently within the database.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code></code>  
    * @return true <code>boolean</code> true, if everything went ok.  
    */  
   //---------------------------------------------------------------------------  
   public boolean update(){  
   //---------------------------------------------------------------------------  
       String whereClause = "where " + getColonString("primaryKey","and");
       return update(whereClause);
   }// update() ENDS  
   
   //---------------------------------------------------------------------------  
   public boolean update(String where){
   //---------------------------------------------------------------------------  
      boolean returnValue=true;  
      preInsertSQL(getPreUpdateSQL());  
        
      StringBuffer psQuery=new StringBuffer("update "+  
      getTableName().toUpperCase()+" set ");  
      psQuery.append(getColonString("columns",","));  
      Vector saveVector=new Vector(bindColumnVector);  
      psQuery.append(" "); // just in case ... 
      psQuery.append(where);  
      int size=bindColumnVector.size();  
        
      if(size>0)  
         for(int index=0;index<size;index++)  
            saveVector.addElement(bindColumnVector.elementAt(index));  
        
      bindColumnVector=new Vector(saveVector);  
      // sqlTagHandler=getSQLTagsHandler();  
        
      if(!sqlTagHandler.preUpdate(this))  
         returnValue=false;  
      else{  
         returnValue = executeSQL(psQuery.toString(),true);  
         if( returnValue) {
             if(!sqlTagHandler.postUpdate(this))  
                returnValue=false;  
         }
         if(returnValue)
            setFKParent();  
      }  
      return returnValue;  
   }//update() ENDS

   private boolean updateArray(){
       Enumeration arrayIndexes = getArrayIndexes();
       while( arrayIndexes.hasMoreElements() ) {
           String arrayIndex = (String) arrayIndexes.nextElement();
           setArrayIndex( arrayIndex );
           if( ! update() ) {
               log("UpdateArray Failed on index="+arrayIndex);
               return false;
           }
       }
       return true;
   }//updateArray ENDS
     
   /**  
    * <code>updateColumnPropertiesHash</code>  
    * <p>  
    * This method will adjust the columnPropertiesHash table  in accordance with  
    * the columns attribute.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code></code>  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   private void updateColumnPropertiesHash(){  
   //---------------------------------------------------------------------------  
      Vector columnVector=new Vector();  
      Vector clearVector=new Vector();  
      columns=getColumns();  
      int start=0;  
      int stop=columns.indexOf(",",start);  
      String key="";  
        
      if(stop!=-1){  
         while(stop!=-1){  
            columnVector.add(columns.substring(start,stop).trim());  
            start=stop+1;  
            stop=columns.indexOf(",",start);  
         }  
         columnVector.add(columns.substring(start,columns.length()).trim());  
      }  
      else  
         columnVector.add(columns);  
        
      columnPropertiesHash=getColumnPropertiesHash();  
      Enumeration enum=columnPropertiesHash.keys();  
        
      for(;enum.hasMoreElements();){  
         key=(String)enum.nextElement();  
                    
         if(!columnVector.contains(key))  
            clearVector.add(key);  
      }  
        
      enum=clearVector.elements();  
        
      // make sure the primary key was submitted with the columns  
      primaryKeyVector=getPrimaryKeyVector();  
      int size=primaryKeyVector.size();  
        
      for(int index=0;index<size;index++){  
         String column=(String)primaryKeyVector.get(index);  
           
         if(clearVector.contains(column.toUpperCase()))  
            clearVector.remove(column.toUpperCase());  
      }  
        
      // update the column hash to the requested values  
      for(;enum.hasMoreElements();){  
         key=(String)enum.nextElement();  
         columnPropertiesHash.remove(key);  
      }  
   }// updateColumnPropertiesHash() ENDS   
     
   //***************************************************************************  
   //                      Class accessor section  
   //***************************************************************************  
   /**  
    * <code>getBindColumns</code>  
    * <p>  
    * This method parses a where clause which contains colon  
    * variables and returns the bind compatiable version. This  
    * method sets the bind column vector.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param type <code>String</code> the type your creating.  
    * @return colonString <code>String</code> the new colon string.  
    */  
   //---------------------------------------------------------------------------  
   private String getBindColumns(String where){  
   //---------------------------------------------------------------------------  
      where+=" ";  
      
      /**
       * this vector holds the names of all of the bind columns for this table.
       * we need to start from scratch.
       **/
      bindColumnVector.setSize(0);  
                  
      //get the index of the first bind.
      int columnNameStarts=where.indexOf(":");  
      
      //do we have any bind variables?
      while(columnNameStarts!=-1){ 
          /**
           * need to push the index to the first character beyond the
           * : variable. this is where the column name really ends.
           */
          int columnNameEnds=1+columnNameStarts;
          StringBuffer bindName=new StringBuffer();
          
          // now we can get the bind name.
          for(;columnNameEnds<where.length();columnNameEnds++){
              char aCharacter=where.charAt(columnNameEnds);
              
              if(Character.isJavaIdentifierPart(aCharacter)||
                 Character.isJavaIdentifierStart(aCharacter)){
                  bindName.append(aCharacter);
              } 
              else
                  break;
          }
          
          //convert the bind name to upper case. that's our format.
          String bindNameString=bindName.toString().toUpperCase();
          
          /**
           * the bind name should must be an entry within the 
           * columnPropertiesHash. get it now.
           */
          ColumnProperties item=(ColumnProperties)
                                 columnPropertiesHash.get(bindNameString);

          /**
           * was the bind name valid?
           */
          if(item!=null){
              //store the bind name in our bindColumnVector for later use.
              bindColumnVector.addElement(bindNameString);
              
              /**
               * take the bind format from the columnProperties object and
               * palace it in the where clause.
               */
              StringBuffer tmp=new StringBuffer(where);
              tmp.replace(columnNameStarts,columnNameEnds, 
                          getColumnProperty("bind",bindNameString));              
              where=tmp.toString(); 
              
              /**
               * finally figure out where to start looking for the next 
               * colon variable.
               */
              columnNameEnds=columnNameStarts+ 
                             getColumnProperty("bind",bindNameString).length();
          }
          
         // grab the next colon variable, if were not done.
         columnNameStarts=where.indexOf(":",columnNameEnds);  
      }
      
      // done.
      return where;  
   } // getBindColumns() ENDS  
     
     
   /**  
    * <code>getButtonName</code>  
    * <p>  
    * This method returns the name of the current operation we are doing. For  
    * example, select.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return buttonName <code>String</code> The name of the selected button.  
    */  
   //---------------------------------------------------------------------------  
   public String getButtonName(){  
   //---------------------------------------------------------------------------  
      return operationName;  
   }// getButtonName() ENDS  
     
     
     
     
   
   /**  
    * <code>getChildName</code>  
    * <p>  
    * This method gets the tables childName.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return parentName <code>String</code> The parent name.  
    */  
   //---------------------------------------------------------------------------  
   public String getChildName(){  
   //---------------------------------------------------------------------------  
      return childName;  
   }// getChildName() ENDS  
     
   /**  
    * <code>getColonString</code>  
    * <p>  
    * This method will create a colon string based on the columns or primary  
    * key.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param type <code>String</code> the type your creating.  
    * @return colonString <code>String</code> the new colon string.  
    */  
   //---------------------------------------------------------------------------  
   private String getColonString(String type, String separator){  
   //---------------------------------------------------------------------------  
      primaryKeyVector=getPrimaryKeyVector();  
      StringBuffer buffer=new StringBuffer();  
        
      if(type.equalsIgnoreCase("columns")){  
         Enumeration enum=columnPropertiesHash.keys();  
         
         for(;enum.hasMoreElements();){  
            String key=((String)enum.nextElement()).toUpperCase();  
            buffer.append(key+"=:");  
            buffer.append(key+" "+separator+" ");  
         }  
           
         buffer.setLength(buffer.toString().lastIndexOf(separator));  
      }  
      else if(type.equalsIgnoreCase("primaryKey")){  
         int sizeOfVector=primaryKeyVector.size();  
         for(int index=0;index<sizeOfVector;index++){  
            String key=((String)primaryKeyVector.elementAt(index)).toUpperCase();  
            buffer.append(key+"=:");  
            buffer.append(key+" and ");  
         }  
           
         buffer.setLength(buffer.toString().lastIndexOf(" and"));  
      }  
      else if(type.equalsIgnoreCase("insert")){  
         Enumeration enum=columnPropertiesHash.keys();  
           
         for(;enum.hasMoreElements();){  
            String key=((String)enum.nextElement()).toUpperCase();  
            String variableType=getColumnProperty("type",key);  
            buffer.append(":"+key+" "+separator+" ");  
         }  
           
         buffer.setLength(buffer.toString().lastIndexOf(separator));  
      }  
      else if(type.equalsIgnoreCase("delete")){  
         int sizeOfVector=primaryKeyVector.size();  
         for(int index=0;index<sizeOfVector;index++){  
            String key=((String)primaryKeyVector.elementAt(index)).toUpperCase();  
            buffer.append(key+"='");  
            buffer.append(getColumnValue(key)+"' and ");  
         }  
           
         buffer.setLength(buffer.toString().lastIndexOf(" and"));  
      }  
        
      return buffer.toString();  
   }//getColonString() ENDS  
     
   /**  
    * <code>getColumnNameSelectList</code>  
    * <p>  
    * This method return the value of the requested column.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param name <code>String</code> valid values are:  
    * <li>bind</li>  
    * <li>selectFormat</li>  
    * <li>value</li>  
    * @param key <code>String</code> the column name.  
    * @return propertyValue <code>String</code> the column's associated value.  
    */  
   //---------------------------------------------------------------------------  
   private String getColumnNameSelectList(){  
   //---------------------------------------------------------------------------  
      Enumeration enum=columnPropertiesHash.keys();  
      StringBuffer buffer=new StringBuffer();  
        
      for(;enum.hasMoreElements();){  
         String key=((String)enum.nextElement()).toUpperCase();  
         buffer.append(getColumnProperty("selectFormat",key)+" as "+key+",");  
      }  
        
      buffer.setLength(buffer.toString().lastIndexOf(","));  
        
      return buffer.toString();  
   }  
     
   /**  
    * <code>getColumnProperty</code>  
    * <p>  
    * This method return the value of the requested column.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param name <code>String</code> valid values are:  
    * <li>bind</li>  
    * <li>selectFormat</li>  
    * <li>value</li>  
    * @param key <code>String</code> the column name.  
    * @return propertyValue <code>String</code> the column's associated value.  
    */  
   //---------------------------------------------------------------------------  
   public String getColumnProperty(String name, String key){  
   //---------------------------------------------------------------------------  
       return getColumnProperty(name,key,getArrayIndex());
   }// getColumnProperty() ENDS  
   
   //---------------------------------------------------------------------------
   public String getColumnProperty(String name, String key, String arrayIndex){
   //---------------------------------------------------------------------------
      columnPropertiesHash=getColumnPropertiesHash();  
      key=key.toUpperCase();  
      ColumnProperties item=(ColumnProperties)columnPropertiesHash.get(key);  
      String propertyValue="";  
        
      if(item!=null){  
         if(name.equalsIgnoreCase("selectFormat"))  
            propertyValue=item.getSelectFormat();  
         else if(name.equalsIgnoreCase("bind"))  
            propertyValue=item.getBind();  
         else if(name.equalsIgnoreCase("value"))  
            propertyValue=item.getValue(arrayIndex);  
         else if(name.equalsIgnoreCase("inclause"))
             propertyValue=item.getInClause();
         else if(name.equalsIgnoreCase("type"))
             propertyValue=item.getType();
      }  
      return propertyValue;  
   }// getColumnProperty() ENDS
     
   /**  
    * <code>getColumns</code>  
    * <p>  
    * This method returns the value of columns. This value represents the columns  
    * to be returned from the db.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code></code>  
    * @return columns <code>String</code> the database columns to return  
    *        from the database.  
    */  
   //---------------------------------------------------------------------------  
   private String getColumns(){  
   //---------------------------------------------------------------------------  
      return columns;  
   }// getColumns() ENDS  
     
   /**  
    * <code>getColumnValue</code>  
    * <p>  
    * This method returns the specified value of the requested column.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param key <code>String</code> The column were looking up.  
    * @return key <code>String</code> The value of this key.  
    */  
   //---------------------------------------------------------------------------  
   public String getColumnValue(String key){  
   //---------------------------------------------------------------------------  
       return getColumnValue(key,getArrayIndex());
   }// getColumnValue() ENDS  
   
   public String getColumnValue(String key, String arrayIndex){
       return getColumnProperty("value",key,arrayIndex);
   }//getColumnValue() ENDS
     
   /**  
    * <code>getCommaString</code>  
    * <p>  
    * This method will create a colon string based on the columns or primary  
    * key.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code></code>  
    * @return colonString <code>String</code> a comma separated list of  
    *        columns  
    */  
   //---------------------------------------------------------------------------  
   private String getCommaString(){  
   //---------------------------------------------------------------------------  
      StringBuffer buffer=new StringBuffer();  
      Enumeration enum=columnPropertiesHash.keys();  
        
      for(;enum.hasMoreElements();){  
         String key=((String)enum.nextElement()).toUpperCase();  
         buffer.append(key+", ");  
      }  
        
      buffer.setLength(buffer.toString().lastIndexOf(","));  
      return buffer.toString();  
   }//getCommaString() ENDS  
     
   /**  
    * <code>getConnectionObject</code>  
    * <p>  
    * This method returns the current connection.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return connection <code>Connection</code> The current connection.  
    */  
   /*
   //---------------------------------------------------------------------------  
   public Connection getConnectionObject(){  
   //---------------------------------------------------------------------------  
      return ConnectionManager.getInstance().getConnectionObject(connection);  
   }// getConnectionObject() ENDS  
    */     
   //---------------------------------------------------------------------------  
   protected Connection getConnection() throws JspException {
   //---------------------------------------------------------------------------  
       if(connectionTag==null) {
           throw new JspException("No database connection available."); 
       }
       return connectionTag.getConnection();
   }
   
   /**  
    * <code>getCurrentPage</code>  
    * <p>  
    * This method returns the current page number.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return currentPage <code>String</code> The current page number.  
    */  
   //---------------------------------------------------------------------------  
   public String getCurrentPage(){  
   //---------------------------------------------------------------------------  
      int pageSize=utilities.stringToInt(utilities.nvl(this.pageSize,maxInt+""));  
      int startRow=utilities.stringToInt(utilities.nvl(this.startRow,"1"));  
      this.currentPage=(startRow/pageSize)+"";  
      return currentPage;  
   }// getCurrentPage() ENDS  
     
   /**  
    * <code>getCurrentRow</code>  
    * <p>  
    * This method returns the current row of the cursor  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return currentRow <code>int</code> The current row of the cursor.  
    */  
   //---------------------------------------------------------------------------  
   public int getCurrentRow(){  
   //---------------------------------------------------------------------------  
      return currentRow-1;  
   }// getCurrentRow() ENDS  
     
   /**  
    * <code>getDepth</code>  
    * <p>  
    * This method returns the current depth associated with this instance..  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return depth <code>int</code> the current depth  
    */  
   //---------------------------------------------------------------------------  
   public int getDepth(){  
   //---------------------------------------------------------------------------  
      return depth;  
   }// getDepth() ENDS  
   
   /**  
    * <code>getDisplayPageSize</code>  
    * <p>  
    * This method returns how many items are to be displayed on this page.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return pageSize <code>int</code> The amount of items to be displayed.  
    */  
   //---------------------------------------------------------------------------  
   public String getDisplaySize(){  
   //---------------------------------------------------------------------------  
      return pageSize;  
   }// getDisplaySize() ENDS  

   /**  
    * <code>getDisplaySize</code>  
    * <p>  
    * This method obtains the current display size for this column.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param key <code>String</code> The column to look up.  
    * @return size <code>int</code> The default size for this column.  
    */  
   //---------------------------------------------------------------------------  
   public int getDisplaySize(String key){  
   //---------------------------------------------------------------------------  
      int size=utilities.stringToInt(getColumnProperty("size",key),0);          
      return size;  
   }// getDisplaySize() ENDS  
     
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
   public void getException(JspWriter out){  
   //---------------------------------------------------------------------------
      try{
          if(exception!=null)
              out.println(exception);
      }
      catch(IOException exception){
          exception.printStackTrace();
      }
   }// getException() ENDS  
     
   /**  
    * <code>getForeignKey</code>  
    * <p>  
    * This method gets the tables foreign key name.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return foreignKey <code>String</code> The foreign key.  
    */  
   //---------------------------------------------------------------------------  
   public String getForeignKey(){  
   //---------------------------------------------------------------------------  
      return foreignKey;  
   }// getForeignKey() ENDS  
     
   /**  
    * <code>getHandlerClass</code>  
    * <p>  
    * This method returns the handler class.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none  
    * @return handlerClass <code>String</code> The class of the handler.  
    */  
   //---------------------------------------------------------------------------  
   public String getHandlerClass(){  
      //---------------------------------------------------------------------------  
      return handlerClass;  
   }// getHandlerClass() ENDS  
     
     
   /**  
    * <code>getHandlerID</code>  
    * <p>  
    * This method returns the handler ID for this object.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return handlerID <code>String</code> The handlerID name.  
    */  
   //---------------------------------------------------------------------------  
   public String getHandlerID(){  
   //---------------------------------------------------------------------------  
      return handlerID;  
   }// getHandlerID() ENDS  
     
   /**  
    * <code>getID</code>  
    * <p>  
    * This method returns the scripting variable associated with the TB_BLURBS  
    * table.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return id <code>String</code> The scripting variable name.  
    */  
   //---------------------------------------------------------------------------  
   public String getId(){  
      //---------------------------------------------------------------------------  
      return id;  
   }// getId() ENDS  
     
   
   public String getInClause(String columnName) {
       return getColumnProperty("inclause",columnName);
   }
   
   /**  
    * <code>getMaxRows</code>  
    * <p>  
    * This method returns the max rows to be retrieved.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return maxRows <code>String</code> The value of maxRows.  
    */  
   //---------------------------------------------------------------------------  
   public String getMaxRows(){  
   //---------------------------------------------------------------------------  
      return maxRows;  
   }// getMaxRows() ENDS  
     
   /**  
    * <code>getObject</code>  
    * <p>  
    * This method returns the object associated with the value.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return object <code>Object</code>the column value as an object.  
    */  
   //---------------------------------------------------------------------------  
   public Object getObject(String string){  
   //---------------------------------------------------------------------------  
       ColumnProperties columnProperties=(ColumnProperties)
            columnPropertiesHash.get(string);      
       return columnProperties.getObject();
   }//getObject() ENDS  

   /**  
    * <code>getOperation</code>  
    * <p>  
    * This method return the current type of operation were doing.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return operation <code>String</code> The operation type.  
    */  
   //---------------------------------------------------------------------------  
   public String getOperation(){  
   //---------------------------------------------------------------------------  
      return getOperation( getArrayIndex() );  
   }// getOperation() ENDS  
    
   public String getOperation(String arrayIndex){
       String operation = (String) operationHash.get(arrayIndex);
       if (operation==null)
           operation="";
       return operation;
   }
     
   /**  
    * <code>getOrderBy</code>  
    * <p>  
    * This method returns the orderBy clause for this instance.  
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
      return orderBy;  
   }// getOrderBy() ENDS  
     
   /**  
    * <code>getPaging</code>  
    * <p>  
    * This method returns whether or not paging has been enabled.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return true <code>String</code> if paging has been enabled.  
    */  
   //---------------------------------------------------------------------------  
   public String getPaging(){  
   //---------------------------------------------------------------------------  
      return paging.toLowerCase();  
   }// getPaging() ENDS  
     
   /**  
    * <code>getPageSize</code>  
    * <p>  
    * This method returns how many items are to be displayed on this page.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return pageSize <code>int</code> The amount of items to be displayed.  
    */  
   //---------------------------------------------------------------------------  
   public int getPageSize(){  
   //---------------------------------------------------------------------------  
      return utilities.stringToInt(pageSize);  
   }// getPageSize() ENDS  

   /**  
    * <code>getParentName</code>  
    * <p>  
    * This method gets the tables parentName.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return parentName <code>String</code> The parent name.  
    */  
   //---------------------------------------------------------------------------  
   public String getParentName(){  
   //---------------------------------------------------------------------------  
      return parentName;  
   }// getParentName() ENDS  
     
   /**  
    * <code>getPreInsertSQL</code>  
    * <p>  
    * This method return the insert instruction.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return preInsertSQL <code>String</code> The instruction to run before  
    *        the insert statement.  
    */  
   //---------------------------------------------------------------------------  
   public String getPreInsertSQL(){  
      //---------------------------------------------------------------------------  
      return preInsertSQL;  
   }// getPreInsertSQL() ENDS  
     
   /**  
    * <code>getPreUpdateSQL</code>  
    * <p>  
    * This method gets the instruction to process before the update statement.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return preUpdateSQL <code>String</code> The instruction to run before  
    *        doing a update.  
    */  
   //---------------------------------------------------------------------------  
   public String getPreUpdateSQL(){  
   //---------------------------------------------------------------------------  
      return preUpdateSQL;  
   }// getPreUpdateSQL() ENDS  
     
   /**  
    * <code>getPreviousRow</code>  
    * <p>  
    * This method returns the number for the previous start.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return previousStart <code>int</code> The start location for the  
    *        previous page of results which were displayed.  
    */  
   //---------------------------------------------------------------------------  
   private int getPreviousRow(){  
   //---------------------------------------------------------------------------  
      int size=getPageSize();  
      int previousStart=currentRow-(size*2);  
        
      if(previousStart<size)  
         previousStart=0;  
        
      return previousStart;  
   }// getPreviousRow() ENDS  
     
   /**  
    * <code>getPreviousStartPosition</code>  
    * <p>  
    * This method returns the previous start position associated with the previous  
    *  
    * page  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return previousStartPosition <code>int</code> The start position  
    *        of the previous pagegetPreviousStartPosition.  
    */  
   //---------------------------------------------------------------------------  
   private int getPreviousStartPosition(){  
   //---------------------------------------------------------------------------  
      return previousStartPosition;  
   }// getPreviousStartPosition() ENDS  
     
   /**  
    * <code>getPrimaryKeys</code>  
    * <p>  
    * This method returns whether or not paging has been enabled.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return true <code>String</code> if paging has been enabled.  
    */  
   //---------------------------------------------------------------------------  
   public String getPrimaryKeys(){  
   //---------------------------------------------------------------------------  
      return primaryKeys;  
   }// getPrimaryKeys() ENDS  
     
  
   public String getProperty(String p){
       if( connectionTag!=null){
           return connectionTag.getProperty(p);
       }
       return null;
   }
   
   public String getProperty(String p, String d){
       if( connectionTag!=null){
           return connectionTag.getProperty(p,d);
       }
       return d;       
   }
   
   /**  
    * <code>getResultSetSize</code>  
    * <p>  
    * This method returns the size of the result set.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code> 
    * @return value <code>int</code> The size of the result set. 
    *        column.  
    */  
   //---------------------------------------------------------------------------  
   public int getResultSetSize(){  
   //---------------------------------------------------------------------------  
      return resultSetSize; 
   }// getResultSetSize() ENDS  
     
   /**  
    * <code>getSql</code>  
    * <p>  
    * This method returns the current sql statement associated with this object.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return sql <code>String</code> The current sql statement.  
    */  
   //---------------------------------------------------------------------------  
   public String getSql(){  
      //---------------------------------------------------------------------------  
      return sql;  
   }// getSql() ENDS  
     
   /**  
    * <code>getStartPage</code>  
    * <p>  
    * This method get the next start page.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return startPage <code>Strign</code> The next page to display.  
    */  
   //---------------------------------------------------------------------------  
   public SQLTagsHandler getSQLTagsHandler(){  
   //---------------------------------------------------------------------------  
      return sqlTagHandler;  
   }// getSQLTagsHandler()  
   
   /**  
    * <code>getSQLTagsRequest</code>  
    * <p>  
    * This method returns the sqlTagRequest
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return sqlTagRequest<code>SQLTagsRequest</code> a SQLTagsRequest  object.
    */  
   //---------------------------------------------------------------------------  
   public HttpServletRequest getSQLTagsRequest(){  
   //---------------------------------------------------------------------------  
      return sqlTagRequest;
   }// getSQLTagsRequest() ENDS  
   
   /**  
    * <code>getSQLTagsRequestParameter</code>  
    * <p>  
    * This method returns a parameter from the SQLTagsRequest.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param string <code>String</code> the parameterName.  
    * @return value<code>String</code>the parameter's value.
    */  
   //---------------------------------------------------------------------------  
   public String getSQLTagsRequestParameter(String string){  
   //---------------------------------------------------------------------------  
      return sqlTagRequest.getParameter(string);
   }// getSQLTagsRequestParameter() ENDS  
   
   /**  
    * <code>getStartPage</code>  
    * <p>  
    * This method get the next start page.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return startPage <code>Strign</code> The next page to display.  
    */  
   //---------------------------------------------------------------------------  
   public String getStartPage(){  
      //---------------------------------------------------------------------------  
      return startPage;  
   }// getStartPage() ENDS  
     
   /**  
    * <code>getStartRow</code>  
    * <p>  
    * This method returns the current start row associated with the paging of  
    * this object.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return startRow <code>String</code> The first row in the next iteration.  
    */  
   //---------------------------------------------------------------------------  
   public String getStartRow(){  
   //---------------------------------------------------------------------------  
      return startRow;  
   }// getStartRow() ENDS  
     
   /**  
    * <code>getWhere</code>  
    * <p>  
    * This method gets the current where clause.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return where <code>String</code> The current where clause.  
    */  
   //---------------------------------------------------------------------------  
   public String getWhere(){  
   //---------------------------------------------------------------------------  
      return where;  
   }// getWhere() ENDS  
     
     
   //***************************************************************************  
   //                      Class mutator section  
   //***************************************************************************  
   /**  
    * <code>setButtonName</code>  
    * <p>  
    * This method sets the current operation type.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param buttonName <code>String</code> The current operation type.  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public void setButtonName(String buttonName){  
   //---------------------------------------------------------------------------  
       operationName=buttonName;
   }// setButtonName() ENDS  
     
     
     
   public void setCacheScheme(String c){ cacheScheme=c;}
   public void setCacheSize(String c){ cacheSize=c;} 
   public void setCaching(String c){ caching=c.trim().toLowerCase();}
     
   /**  
    * <code>setChildName</code>  
    * <p>  
    * This method sets the child name.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return parentName <code>String</code> The parent name.  
    */  
   //---------------------------------------------------------------------------  
   public void setChildName(String childName){  
   //---------------------------------------------------------------------------  
      this.childName=childName;  
   }// setChildName() ENDS  
     
   /**  
    * <code>setColumnProperties</code>  
    * <p>  
    * This method will update the value within the ColumnProperties object. This  
    * object is stored within a hashtable.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param name <code>String</code> The name of the property.  
    * @param key <code>String</code> The the column name.  
    * @param value <code>String</code> The the column value.  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public void setColumnProperty(String name, String key, String value){  
   //---------------------------------------------------------------------------  
       setColumnProperty(name,key,value,getArrayIndex());
   }// setColumnProperty() ENDS  
   
   //---------------------------------------------------------------------------  
   protected void setColumnPropertiesHash(Hashtable columnPropertiesHash){
   //---------------------------------------------------------------------------   
      this.columnPropertiesHash=columnPropertiesHash;
   }
   
   //---------------------------------------------------------------------------  
   public void setColumnProperty( String name,String key,String value
                                 ,String ArrayIndex){
   //---------------------------------------------------------------------------   
      columnPropertiesHash=getColumnPropertiesHash();  
      key=key.toUpperCase();  
      ColumnProperties item=(ColumnProperties)columnPropertiesHash.get(key);  
        
      if(item!=null){  
         if(name.equalsIgnoreCase("selectFormat"))  
            item.setSelectFormat(value);  
         else if(name.equalsIgnoreCase("bind"))  
            item.setBind(value);  
         else if(name.equalsIgnoreCase("value")){
            item.setValue(value,arrayIndex);       
         }
         columnPropertiesHash.put(key,item);  
      }  
   }// setColumnProperty() ENDS  
     
   /**  
    * <code>setColumns</code>  
    * <p>  
    * This method sets the columns to be retrieved from the database.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param columns <code>String</code> a comma demilited list of columns.  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public void setColumns(String columns){  
   //---------------------------------------------------------------------------  
      this.columns=columns.toUpperCase();  
      updateColumnPropertiesHash();  
   }// setColumns() ENDS  
     
   /**  
    * <code>setColumnValue</code>  
    * <p>  
    * This method sets the current on an individual table colun.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param key <code>String</code> The key to the column.  
    * @param value <code>String</code> The value of the column.  
    * @return none <code>none</code> none  
    */  
   //---------------------------------------------------------------------------  
   public void setColumnValue(String key, String value){  
   //---------------------------------------------------------------------------  
       setColumnValue(key,value,getArrayIndex());
   }// setColumnValue() ENDS  
   
   //---------------------------------------------------------------------------  
   public void setColumnValue(String key, String value, String arrayIndex){  
   //---------------------------------------------------------------------------  
      setColumnProperty("value",key,value,arrayIndex);  
      addArrayIndex(arrayIndex); // keeps track of all arrayIndex values
   }// setColumnValue() ENDS
     
   //---------------------------------------------------------------------------  
   public void setConnectionTag(ConnectionTag connectionTag){
   //---------------------------------------------------------------------------  
       this.connectionTag=connectionTag;
   }     
     
   //---------------------------------------------------------------------------  
   public ConnectionTag getConnectionTag(){
   //---------------------------------------------------------------------------  
       return this.connectionTag;
   } 
   /**  
    * <code>setCurrentPage</code>  
    * <p>  
    * This method sets the value for the current page.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param value <code>String</code> The value for the current page.  
    * @return none <code>none</code> none  
    */  
   //---------------------------------------------------------------------------  
   public void setCurrentPage(String value){  
   //---------------------------------------------------------------------------  
      this.currentPage=value;  
      int currentPage=utilities.stringToInt(utilities.nvl(value,"1"));  
      int pageSize=utilities.stringToInt(utilities.nvl(this.pageSize,maxInt+""));  
      startRow=(currentPage*pageSize)+"";  
   }// setCurrentPage() ENDS  
     
   /**  
    * <code>setCurrentRow</code>  
    * <p>  
    * Sets the current row.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param currentRow <code>int</code> The current row.  
    * @return nonecurrentRow <code></code>  
    */  
   //---------------------------------------------------------------------------  
   private void setCurrentRow(int currentRow){  
      //---------------------------------------------------------------------------  
      this.currentRow=currentRow;  
   }// setCurrentRow() ENDS  
     
     
   /**  
    * <code>setDepth</code>  
    * <p>  
    * This method sets the depth.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param depth <code>int</code> The new depth.  
    * @return none <code>none</code> none  
    */  
   //---------------------------------------------------------------------------  
   public void setDepth(String depth){  
   //---------------------------------------------------------------------------  
      this.depth=utilities.stringToInt(depth);  
   }// setDepth() ENDS
     
   
   /**  
    * <code>setDistinct</code>  
    * <p>  
    * This method enables you to select distinct records
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param exception <code>Exception</code> The exception generated.  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public void setDistinct(String distinct){
      this.distinct=distinct;
   }
   
   /*
    *
   //ISQLListener implementation
   public void setException(SQLEvent event){
       setException( new Exception(event.toString()) );
   }
    *
    */
   
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
      this.exception=exception;  
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
   public boolean setExceptionString(String message){  
      boolean returnValue=true;  
        
      try{  
         Exception exception=new Exception(message);  
         setException(exception);  
      }  
      catch(Exception exception){  
         returnValue=false;  
      }  
        
      return returnValue;  
   }// setException() ENDS  
   
   /**  
    * <code>setFirstRecord</code>  
    * <p>  
    * This method indicates whether this is the first record read.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param firstRecord <code>boolean</code> true if this is the first  
    *        record.  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public void setFirstRecord(boolean firstRecord){  
   //---------------------------------------------------------------------------  
      this.firstRecord=firstRecord;  
   }// setFirstRecord() ENDS  
     
   /**  
    * <code>setForeignKey</code>  
    * <p>  
    * This method tables foreign key.  
    * object.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param foreignKey <code>String</code> the foreign key.  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public void setForeignKey(String foreignKey){  
   //---------------------------------------------------------------------------  
      this.foreignKey=foreignKey;  
   }// setForeignKey() ENDS  
     
   /**  
    * <code>setLastRecordOnPage</code>  
    * <p>  
    * This method indicates whether this is the first record read.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param firstRecord <code>boolean</code> true if this is the first  
    *        record.  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public void setLastRecordOnPage(boolean lastRecordOnPage){  
      //---------------------------------------------------------------------------  
      this.lastRecordOnPage=lastRecordOnPage;  
   }// setLastRecordOnPage() ENDS  
     
   /**  
    * The <b><code>setFKParent</code></b> method is overloaded, its used to call  
    * the setFKParent(int depth) method passing in a depth of  
    * one.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none.  
    * @return  none  
    * @since   JDK1.3  
    */  
   //---------------------------------------------------------------------------  
   public void setFKParent(){  
   //---------------------------------------------------------------------------  
      setFKParent(depth);  
      return;  
   }//setFKParent() ENDS  
     
   /**  
    * <code>setHandlerClass</code>  
    * <p>  
    * This method sets the handler class.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param handlerClass <code>String</code> The handler class.  
    * @return none <code>none</code> none  
    */  
   //---------------------------------------------------------------------------  
   public void setHandlerClass(String handlerClass){  
   //---------------------------------------------------------------------------  
     sqlTagHandler=(SQLTagsHandler) utilities.factory(handlerClass);
     // sqlTagHandler.setPageContext(pageContext);

     if(sqlTagHandler==null)       
         sqlTagHandler=new SQLTagsHandler();
     
     this.handlerClass=handlerClass;  
   }// setHandlerClass() ENDS  
     
     
   /**  
    * <code>setHandler</code>  
    * <p>  
    * This method sets the handler id.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param handlerID <code>String</code> The handler id.  
    * @return none <code>none</code> none  
    */  
   //---------------------------------------------------------------------------  
   public void setHandlerID(String handlerID){  
   //---------------------------------------------------------------------------  
      this.handlerID=handlerID;  
   }// setHandlerID() ENDS  
     
   /**  
    * <code>setHasFetch</code>  
    * <p>  
    * This method sets the value of the hasFetch property. If set to true, a  
    * fetch has already been done.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param value <code>String</code> The new value of the hasFetch property.  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public void setHasFetch(String value){  
   //---------------------------------------------------------------------------  
      hasFetch=false;  
        
      if(value.equalsIgnoreCase("true"))  
         hasFetch=true;  
   }// setHasFetch() ENDS  
     
   /**  
    * <code>setID</code>  
    * <p>  
    * This method sets the name of the scripting variable associated with this  
    * tag.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param id <code>String</code> The name of the scripting variable.  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public void setId(String id){  
   //---------------------------------------------------------------------------  
      this.id=id;  
   }// setId() ENDS  
     
   /**  
    * <code>setItemDisplayCount</code>  
    * <p>  
    * This method set the number of items currently displayed when caching.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param itemDisplayCount <code>int</code> the value.  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public void setItemDisplayCount(int itemDisplayCount){  
   //---------------------------------------------------------------------------  
      this.itemDisplayCount=itemDisplayCount;  
   }//setItemDisplayCount() ENDS  
     
   /**  
    * <code>setLastRecord</code>  
    * <p>  
    * This method indicates whether this is the last record within the result  
    * set.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param lastRecord <code>boolean</code> true, if this is the last  
    *        record within this result set.  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public void setLastRecord(boolean lastRecord){  
   //---------------------------------------------------------------------------  
      this.lastRecord=lastRecord;  
   }// setLastRecord() ENDS  
     
   /**  
    * <code>setMaxRows</code>  
    * <p>  
    * This method sets the value of the maxRow attribute.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param maxRows <code>String</code> The value for maxRows.  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public void setMaxRows(String maxRows){  
   //---------------------------------------------------------------------------  
      this.maxRows=maxRows;  
   }// setMaxRows() ENDS  
     
     
   /**  
    * <code>setOperation</code>  
    * <p>  
    * This method sets the current operation type.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param operation <code>String</code> The operation type.  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public void setOperation(String operation){  
      //---------------------------------------------------------------------------  
      setOperation(operation, getArrayIndex() );  
   }// setOperation() ENDS  
   public void setOperation(String operation, String arrayIndex){
       operationHash.put(arrayIndex,operation);
   }
     
   /**  
    * <code>setOrderBy</code>  
    * <p>  
    * This method sets the order by clause of the current select statement.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param value <code>String</code> The order by value.  
    * @return none <code>none</code> none  
    */  
   //---------------------------------------------------------------------------  
   public void setOrderBy(String orderBy){  
   //---------------------------------------------------------------------------  
      setWhere(orderBy);  
   }// setOrderBy() ENDS  
     
   /**  
    * <code>setPageSize</code>  
    * <p>  
    * This method set the current page size.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param pageSize <code>String</code> The size for the current page.  
    * @return none <code>none</code> none  
    */  
   //---------------------------------------------------------------------------  
   public void setPageSize(String pageSize){
   //---------------------------------------------------------------------------  
      int size=utilities.stringToInt(pageSize,0);  
      this.pageSize=size+"";  
        
      if(this.pageSize.equals("0"))  
         this.pageSize=Integer.MAX_VALUE+"";  
   }// setPageSize() ENDS  
   
   /**  
    * <code>setPageSize</code>  
    * <p>  
    * This method set the current page size.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param pageSize <code>String</code> The size for the current page.  
    * @return none <code>none</code> none  
    */  
   //---------------------------------------------------------------------------  
   public void setDisplaySize(String displayPageSize){
   //---------------------------------------------------------------------------  
      this.displayPageSize=displayPageSize;
       setPageSize(displayPageSize);
   }// setMyPageSize() ENDS  
     
   /**  
    * <code>setPaging</code>  
    * <p>  
    * This method set the paging attribute.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param paging <code>String</code> true if paging is enabled.  
    * @return none <code>none</code> none  
    */  
   //---------------------------------------------------------------------------  
   public void setPaging(String paging){  
   //---------------------------------------------------------------------------  
      if(!paging.equalsIgnoreCase("true")&&!paging.equalsIgnoreCase("false"))  
         paging="false";  
        
      this.paging=paging;  
        /*
      if(paging.equalsIgnoreCase("true")){  
         if((String)pageContext.getRequest().getParameter("pageSize")!=null)  
            setPageSize(  
                (String)pageContext.getRequest().getParameter("pageSize"));  
           
         if((String)pageContext.getRequest().getParameter(getStartRowParameter())!=null)  
            startRow=(String)pageContext.getRequest().getParameter(getStartRowParameter());  
      }  
         **/
   }// setPaging() ENDS  
     
   /**  
    * <code>setParent</code>  
    * <p>  
    * This method tables foreign key.  
    * object.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param parentName <code>String</code> the parentName.  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public void setParentName(String parentName){  
      //---------------------------------------------------------------------------  
      this.parentName=parentName;  
   }// setParentName() ENDS  
     
   /**  
    * <code>setPreInsertSQL</code>  
    * <p>  
    * This method defines the instructions done before a insert.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param preInsertSQL <code>String</code> The instruction to run.  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public void setPreInsertSQL(String preInsertSQL){  
   //---------------------------------------------------------------------------  
      this.preInsertSQL=preInsertSQL;  
   }// setPreInsertSQL() ENDS  
     
   /**  
    * <code>setPreUpdateSQL</code>  
    * <p>  
    * This method sets the instruction to process before an update is done.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param preUpdateSQL <code>String</code> The instruction to process.  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public void setPreUpdateSQL(String preUpdateSQL){  
   //---------------------------------------------------------------------------  
      this.preUpdateSQL=preUpdateSQL;  
   }// setPreUpdateSQL() ENDS  
     
   /**  
    * <code>setPrimaryKeys</code>  
    * <p>  
    * This method returns whether or not paging has been enabled.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return true <code>String</code> if paging has been enabled.  
    */  
   //---------------------------------------------------------------------------  
   public void setPrimaryKeys(String primaryKeys){  
   //---------------------------------------------------------------------------  
      this.primaryKeys=primaryKeys;  
   }// setPrimaryKeys() ENDS  
     
   /**  
    * <code>setProperties</code>  
    * <p>  
    * This method set the properties object. If the value of this variable is  
    * true, all the parameters currently within the request object are read  
    * and if any values read from the request object are within the TB_BLURBS  
    * table, TB_BLURBS will initialize itself to those values.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param properties <code>String</code> if true, the TB_BLURBS initializes  
    *        itself from the request object.  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public void setProperties(String properties){  
  //---------------------------------------------------------------------------  
      this.properties=properties;  
   }// setProperties() ENDS  
     
   /**  
    * <code>setRequestProperties</code>  
    * <p>  
    * This method set the column properties based on the request object.
    * </p>  
    * @author  Steve Olson
    * @param none <code>none</code>none.
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------
   public void setRequestProperties(){
   //---------------------------------------------------------------------------
       if(pageContext!=null){
           HttpServletRequest servletRequest = (HttpServletRequest)pageContext.getRequest();
           
           if(servletRequest!=null){
               // startRow is a special case parameter ...
               if( Utilities.nvl(servletRequest.getParameter("paging"), "").equalsIgnoreCase("true")) {
                   setStartRow(Utilities.nvl(servletRequest.getParameter(getStartRowParameter()), "0"));
               }
               
               Enumeration enum=servletRequest.getParameterNames();
               
               while( enum.hasMoreElements()){
                   String parameterName = (String)enum.nextElement();
                   String columnName = parameterName.toUpperCase();
                   
                   String arrayIndex = "0";
                   int arrayStart=parameterName.indexOf('[');
                   int arrayEnd=parameterName.indexOf(']');
                   
                   if( arrayStart<arrayEnd && arrayStart != -1) {
                       arrayIndex = 
                        parameterName.substring(arrayStart+1,arrayEnd);
                       columnName = parameterName.substring(0,arrayStart);
                   }

                   if(columnName.equalsIgnoreCase(operationName)){
                       setOperation(servletRequest.getParameter(parameterName), 
                            arrayIndex);
                       // may want to double check that OPERATION is not 
                       // a valid column in the currnet
                       // table ... would be nice to give an error ... 
                       continue;
                   }

                   ColumnProperties columnProperties =(ColumnProperties) 
                      columnPropertiesHash.get(columnName);
                   
                   if( columnProperties != null ) {
                       if( columnName.length() != parameterName.length() ) {
                           // This means that we got an Array formated variable
                           // Therefore we only need to get that single Value
                           String parameterValue=
                            servletRequest.getParameter( parameterName );
                           columnProperties.setValue(parameterValue,arrayIndex);
                           addArrayIndex(arrayIndex);
                       } 
                       else {
                           // OK, no Array format; however, w/ 
                           // multi-selectors out there
                           // there could be a list of values.
                           String parameterValues[]=
                            servletRequest.getParameterValues( parameterName );
                           int index = 0;
                           
                           for (;index <parameterValues.length;index++){
                               columnProperties.setValue( parameterValues[index]
                                           ,Integer.toString(index)
                                           );
                               addArrayIndex(Integer.toString(index));
                           }
                       }
                   }
               }
           }
       }
       
   }// setRequestProperties ENDS
     
   /**  
    * <code>setSql</code>  
    * <p>  
    * This method sets the current sql statement associated with this object.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param sql <code>String</code> the sql statement.  
    * @return none <code></code>  
    */  
   //---------------------------------------------------------------------------  
   public void setSql(String sql){  
   //---------------------------------------------------------------------------  
      this.sql=sql;  
   }  
     
   //---------------------------------------------------------------------------  
   private boolean isSelectStatement(String sql){  
   //---------------------------------------------------------------------------  
      String select="";  
      boolean returnValue=false;  
        
      if(sql!=null){  
         select=sql.trim().toLowerCase().substring(0,6);  
           
         if(select.equalsIgnoreCase("select")&&!isOpen())  
            returnValue=true;  
      }  
        
      return returnValue;  
   }  
     
   /**  
    * <code>setStartPage</code>  
    * <p>  
    * This method sets the next start page.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param value <code>String</code> The value of the next start page.  
    * @return none <code>none</code> none  
    */  
   //---------------------------------------------------------------------------  
   public void setStartPage(String value){  
   //---------------------------------------------------------------------------  
      this.startPage=value;  
      int startPage=utilities.stringToInt(utilities.nvl(this.startPage,maxInt+""));  
      int startRow=utilities.stringToInt(utilities.nvl(this.startRow,"1"));  
      this.startRow=(startRow/startPage)+"";  
   }// setStartPage() ENDS  
     
     
     
   /**  
    * <code>setStartRow</code>  
    * <p>  
    * This method sets the start row for the next iteration.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param startRow <code>String</code> The first row to display when  
    *        the next link is pressed.  
    * @return none <code>none</code> none  
    */  
   //---------------------------------------------------------------------------  
   public void setStartRow(String startRow){  
      //---------------------------------------------------------------------------  
      this.startRow=Utilities.nvl(startRow, "0");  
      boolean validStartRow=true;  
      int startValue=utilities.stringToInt(startRow);  
      int pageSize=utilities.stringToInt(this.pageSize);  
      nextLink=startValue+pageSize;  
      prevLink=startValue-pageSize;  

      if(prevLink<0)  
         prevLink=0;  
        
   }// setStartRow() ENDS  
     
   /**  
    * <code>setStopRow</code>  
    * <p>  
    * sets how many records will be displayed on the current page.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param stopRow <code>int</code> the last row to display.  
    * @return  <code>none</code>  
    */  
   //---------------------------------------------------------------------------  
   private void setStopRow(int stopRow){  
   //---------------------------------------------------------------------------  
      this.stopRow=stopRow;  
   }  
     
   /**  
    * <code>setWhere</code>  
    * <p>  
    * This method sets the where clause.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param where <code>String</code> The where clause.  
    * @return none <code>none</code> none  
    */  
   //---------------------------------------------------------------------------  
   public void setWhere(String where){  
   //---------------------------------------------------------------------------  
      if(where!=null)  
         this.where=where;  
      else  
         where="";  
   }// setWhere() ENDS  
     
     
   //***************************************************************************  
   //                      Abstract Methods  
   //***************************************************************************  
   public abstract Hashtable getColumnPropertiesHash();  
   public abstract Vector    getPrimaryKeyVector();  
   public abstract String    getTableName();  
   public abstract void      setFKParent(int depth);  
     
   private void readClobBlobLong(String key){  
      String type=getColumnProperty("type",key);  
      StringBuffer buffer=new StringBuffer();  
      String data=null;
      setColumnTypes();
      
      try{  
        if(isColumnTypeValid(asciiStreamColumns,type))
            data=getAsciiStream(resultSet,key);
        else if(isColumnTypeValid(characterStreamColumns,type))
            data=getCharacterStream(resultSet,key);
        else if(isColumnTypeValid(binaryStreamColumns,type))
            data=getBinaryStream(resultSet,key);
         
        if(data!=null)
           setColumnValue(key,data);  
      }  
      catch(IOException exception){  
         log(getTableName()+".readClobBlobLong: "+exception);  
      }  
      catch(SQLException exception){  
         log(getTableName()+".readClobBlobLong: "+exception);  
      }  
   }//readClobBlobLong() ENDS  
     
   //***************************************************************************  
   // Private Methods  
   //***************************************************************************  
   /**  
    * <code>bumpItemDisplayCount</code>  
    * <p>  
    * This method increments the number of items displayed while caching.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>none</code>  
    * @return <code>none</code>  
    */  
   //---------------------------------------------------------------------------  
   private void bumpItemDisplayCount(){  
   //---------------------------------------------------------------------------  
      itemDisplayCount=itemDisplayCount+1;  
   }  
   
   /**  
    * <code>getAsciiStream</code>  
    * <p>  
    * 
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @return results <code>String</code> data read.  
    */  
   //--------------------------------------------------------------------------- 
   private String getAsciiStream(ResultSet resultSet,String key)
            throws SQLException,IOException{
   //--------------------------------------------------------------------------- 
        InputStream inputStream=null;
        StringBuffer buffer=new StringBuffer();
        String results=null;
        int charByte=0;
     
        if(resultSet.getAsciiStream(key)!=null){
            inputStream=resultSet.getAsciiStream(key);  

             if(inputStream!=null){  
                while((charByte=inputStream.read())!=-1)  
                   buffer.append((char)charByte);  

                results=buffer.toString();
             }
        }
        return results;
   }//getAsciiStream() ENDS
   
   /**  
    * <code>getBinaryStream</code>  
    * <p>  
    * 
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @return results <code>String</code> data read.  
    */  
   //--------------------------------------------------------------------------- 
   private String getBinaryStream(ResultSet resultSet,String key)
            throws SQLException,IOException{
   //--------------------------------------------------------------------------- 
        InputStream inputStream=null;
        StringBuffer buffer=new StringBuffer();
        String results=null;
        int charByte=0;
        
        if(resultSet.getBinaryStream(key)!=null){
            inputStream=resultSet.getBinaryStream(key);  

             if(inputStream!=null){  
                while((charByte=inputStream.read())!=-1)  
                   buffer.append((char)charByte);  

                results=buffer.toString();
             }
        }
        return results;
   }//getBinaryStream() ENDS
   
   /**  
    * <code>getCharacterStream</code>  
    * <p>  
    * 
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @return results <code>String</code> data read.  
    */  
   //--------------------------------------------------------------------------- 
   private String getCharacterStream(ResultSet resultSet,String key)
        throws SQLException,IOException{
   //---------------------------------------------------------------------------  
       Reader reader=null;
       StringBuffer buffer=new StringBuffer();
       String results=null;
       int aCharacter=0;
       
        if(resultSet.getCharacterStream(key)!=null)
           reader=resultSet.getCharacterStream(key);  
       
       if(reader!=null){
           while((aCharacter=reader.read())!=-1)
               buffer.append((char)aCharacter);

            results=buffer.toString();
           
        }
       
       return results;
    }//getCharacterStream() ENDS
        
   //---------------------------------------------------------------------------  
   private boolean isLOBS(){  
   //---------------------------------------------------------------------------  
      boolean lobs=false;  
      Enumeration enum=columnPropertiesHash.keys();  
        
      // update the column properties  
      for(;enum.hasMoreElements();){  
         String key=((String)enum.nextElement()).toUpperCase();            
         String columnType=getColumnProperty("type",key).toUpperCase();  
           
         if(characterStreamColumns.contains(columnType)){  
            lobs=true;  
            break;  
         }  
         else if(binaryStreamColumns.contains(columnType)){  
            lobs=true;  
            break;  
         }  
      }  
        
      return lobs;  
   }// isLOBS() ENDS  
     
   /**  
    * <code>isOpen</code>  
    * <p>  
    * This method returns the state of the cursor.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return mutipleOpen <code>boolean</code> true, if the cursor is open.  
    */  
   //---------------------------------------------------------------------------  
   private boolean isOpen(){  
   //---------------------------------------------------------------------------  
      return multipleOpen;  
   }// isOpen() ENDS  
     
   /**  
    * <code>isOpen</code>  
    * <p>  
    * This method sets the cursor state. If it is set to true, the result set  
    * is currently open.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param mutipleOpen <code>boolean</code> true, if the cursor is currently  
    *        opened.  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   private void isOpen(boolean multipleOpen){  
   //---------------------------------------------------------------------------  
      this.multipleOpen=multipleOpen;  
   }// isOpen() ENDS  
     
   /**  
    * <code>setAsciiStream</code>  
    * <p>  
    *  Sets the prepared statements ascii stream
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @return results <code>String</code> data read.  
    */  
   //--------------------------------------------------------------------------- 
   private void setAsciiStream(PreparedStatement ps, byte[] data, int index)
        throws SQLException{
   //---------------------------------------------------------------------------  
       int size=data.length;  
       ByteArrayInputStream byteStream=new ByteArrayInputStream(data);  
       ps.setAsciiStream(index,byteStream,size);  
    }//setAsciiStream() ENDS
   
   /**  
    * <code>setBinaryStream</code>  
    * <p>  
    *  Sets the prepared statements binary stream
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @return results <code>String</code> data read.  
    */  
   //--------------------------------------------------------------------------- 
   private void setBinaryStream(PreparedStatement ps, byte[] data, int index)
        throws SQLException{
   //---------------------------------------------------------------------------  
     int size=data.length;
     InputStream inputStream=new ByteArrayInputStream(data);
     ps.setBinaryStream(index,inputStream,size);
    }//setBinaryStream() ENDS
     
   /**  
    * <code>setCharacterStream</code>  
    * <p>  
    *   
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @return results <code>String</code> data read.  
    */  
   //--------------------------------------------------------------------------- 
   private void setCharacterStream(PreparedStatement ps, String data, int index)
        throws SQLException{
   //---------------------------------------------------------------------------  
       int size=data.length();
       StringReader reader=new StringReader(data);
       ps.setCharacterStream(index,(Reader)reader,size);
    }//setCharacterStream() ENDS
   

   /**  
    * <code>setLobHash</code>  
    * <p>  
    * This method allows set the lob hashtable.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code>  
    * @return <code>none</code>  
    */  
   //---------------------------------------------------------------------------  
   private void setLobHash(PreparedStatement preparedStatement){  
      //---------------------------------------------------------------------------  
      String columnName="";  
      int size=0;  
      long byteOffset=1;  
      lobHash=new Hashtable();
        
      try{  
         ResultSet resultSet=preparedStatement.getResultSet();  
         resultSet.next();  
         ResultSetMetaData metaData=resultSet.getMetaData();  
         size=metaData.getColumnCount()+1;  
           
         for(int index=1;index<size;index++){  
            Enumeration enum=columnPropertiesHash.keys();  
            columnName=metaData.getColumnName(index);  
              
            for(;enum.hasMoreElements();){  
               String key=((String)enum.nextElement()).toUpperCase();  
                 
               if(key.equalsIgnoreCase(columnName)){  
                  String columnType=getColumnProperty("type",key).toUpperCase();  
                  
                  if(characterStreamColumns.contains(columnType)){
                     clob=(Clob)resultSet.getClob(columnName);  
                     setLobObject(resultSet,key,columnName);
                     String data=getColumnValue(columnName);                    
                     setCharacterStream(preparedStatement,data,index);
                     lobHash.put(columnName,clob);  
                  }  
                  else if(binaryStreamColumns.contains(columnType)){
                     blob=(Blob)resultSet.getBlob(columnName);  
                     setLobObject(resultSet,key,columnName);
                     byte[] blobData=getColumnValue(columnName).getBytes();
                     setBinaryStream(preparedStatement,blobData,index);
                     lobHash.put(columnName,blob);  
                  }  
               }  
            }  
         }  
           
         resultSet.close();  
      }  
      catch(SQLException exception){  
         log(getTableName()+".setLobHash(): "+exception);  
      }  
   }// setLobHash() ENDS  
   
   /**  
    * <code>setColumnTypes</code>  
    * <p>  
    *  Sets the prepared statements ascii stream
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @return results <code>String</code> data read.  
    */  
   //--------------------------------------------------------------------------- 
   public void setColumnTypes(){
   //--------------------------------------------------------------------------- 
      /**
       * these properties are read from the ini file. they tell us how to
       * read various LOB types.
       */
      asciiStreamColumns=utilities.getStringTokenVector(
                           getProperty("asciiStreamTypes","long"),",");
      characterStreamColumns=utilities.getStringTokenVector(
                           getProperty("characterStreamTypes","clob"),",");
      binaryStreamColumns=utilities.getStringTokenVector(
                           getProperty("binaryStreamTypes","blob,image"),",");
   }//setColumnTypes() ENDS

    /**  
    * <code>setLobObject</code>  
    * <p>  
    * This method places a lob reference within the columnProperties
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param resultSet <code>ResultSet</code>the result set 
    * @param key <code>String</code>the key were looking up.
    * @param columnName <code>String</code>the name of the column we want.
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   private void setLobObject(ResultSet resultSet, String key,
                             String columnName){  
   //---------------------------------------------------------------------------
        try{
            ColumnProperties columnProperties=(ColumnProperties)
                columnPropertiesHash.get(key);
            Object object=resultSet.getObject(columnName);
            columnProperties.setObject(object);
        }
        catch(Exception exception){
            exception.printStackTrace();
        }
   }//setLobObject() ENDS
   
   /**  
    * <code>setString</code>  
    * <p>  
    *  Sets the prepared statements ascii stream
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @return results <code>String</code> data read.  
    */  
   //--------------------------------------------------------------------------- 
   private void setString(PreparedStatement ps, String data, int index)
        throws SQLException{
   //---------------------------------------------------------------------------  
       ps.setString(index,data);  
    }//setString() ENDS

   /**  
    * <code>updateColumnPropertiesObject</code>  
    * <p>  
    * This method allows set the lob hashtable.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code>  
    * @return <code>none</code>  
    */  
   //---------------------------------------------------------------------------  
   private void updateColumnPropertiesObject(ResultSet resultSet,
            ColumnProperties columnProperties,String columnType){  
  //---------------------------------------------------------------------------  
        try{                             
            if(isColumnTypeValid(asciiStreamColumns,columnType)||
               isColumnTypeValid(binaryStreamColumns,columnType)||
               isColumnTypeValid(characterStreamColumns,columnType)){
                   
                if(columnProperties!=null){
                    Object object=resultSet.getObject(columnProperties.getName());
                    
                    if(object!=null)
                        columnProperties.setObject(object);
                }
            }
        }
        catch(Exception exception){
            exception.printStackTrace();
        }
   }
   
   /** Getter for property startRowParameter.
    * @return Value of property startRowParameter.
    */
   public String getStartRowParameter() {
       return this.startRowParameter;
   }
   
   /** Setter for property startRowParameter.
    * @param startRowParameter New value of property startRowParameter.
    */
   public void setStartRowParameter(String startRowParameter) {
       this.startRowParameter = startRowParameter;
   }
   
// updateColumnPropertiesObject() ENDS
   
   //***************************************************************************  
   //                      Class variable section  
   //***************************************************************************  
   protected Hashtable          arrayIndexHash=new Hashtable();
   protected String             arrayIndex="0";
   private Vector               asciiStreamColumns=new Vector();
   private Hashtable            asciiStreamTypes=new Hashtable();
   protected boolean            anyRecords=false;  
   protected Vector             bindColumnVector=new Vector();  
   private Vector               binaryStreamColumns=new Vector();
   private Hashtable            binaryStreamTypes=new Hashtable();
   private   Blob               blob=null;  
   protected String             caching="false";  
   // private Hashtable            cacheHashtable=new Hashtable();  
   // private String               cacheItemQuery="";  
   // protected StringBuffer       cacheQuery=new StringBuffer();  
   protected String             cacheSize="0";  
   protected String             cacheScheme="default";  
   // private Hashtable            cacheVectorHash=null;  
   // private String               cacheVectorQuery="";  
   private Vector               characterStreamColumns=new Vector();
   private Hashtable            characterStreamTypes=new Hashtable();
   protected String             childName="";  
   private Clob                 clob=null;  
   protected Hashtable          columnPropertiesHash=new Hashtable();  
   protected String             columns="";  
   //protected Connection         connection=null;  //Cursor needs this!
   protected ConnectionTag      connectionTag=null;
   protected String             currentPage="1";  
   private int                  currentRow=0;  
   private ArrayList            currentVectorKeys=new ArrayList();  
   private boolean              DEBUG=false;  
   private final static int     DEFAULT=1;  
   private final static int     DISK=3;  
   protected String             displayPageSize="";
   protected String             distinct="false";
   protected String             debug="1";  
   protected int                depth=0;  
   protected StringBuffer       emptyLobs=new StringBuffer();  
   private   Exception          exception;  
   protected boolean            firstRecord=false;  
   protected boolean            firstRecordOnPage=false;  
   protected String             foreignKey="";  
   protected String             handlerClass="";  
   protected String             handlerID="";  
   protected boolean            hasFetch=false;  
   private   Utilities          utilities=new Utilities();  
   protected boolean            haveLob=false;  
   protected String             id="";  
   protected boolean            insertingRecords=false;  
   private int                  itemDisplayCount=0;  
   protected StringBuffer       keyQuery=new StringBuffer();  
   protected StringBuffer       knownLobs=new StringBuffer();  
   protected boolean            lastRecord=false;  
   protected boolean            lastRecordOnPage=false;  
   private Hashtable            lobHash=new Hashtable();  
   protected int                maxInt=Integer.MAX_VALUE;  
   protected String             maxRows=Integer.MAX_VALUE+"";  
   private   boolean            multipleOpen=false;  
   private int                  nextLink=0;  
   protected Hashtable          operationHash=new Hashtable();
   protected String             operationName="OPERATION";
   protected String             orderBy="";  
   protected String             output="";  
   protected String             pageSize="";  
   protected String             paging="false";  
   protected String             parentName="";  
   protected Object[]           pkValueArray=null;  
   protected String             preInsertSQL="";  
   protected PreparedStatement  ps=null;  
   protected String             preUpdateSQL="";  
   // private   int                previousCacheIndex=0;  
   protected int                previousStartPosition=0;  
   private int                  prevLink=0;  
   protected Vector             primaryKeyVector=new Vector();  
   protected String             primaryKeys="";  
   protected String             properties="false";  
   private final static int     RAM=2;  
   protected ResultSet          resultSet;  
   private int                  resultSetSize=0;
   protected PreparedStatement  selectPreparedStatement;  
   // private SessionCache         sessionCache=null;  
   protected String             sql="";  
   // private SQLActionEvent       sqlEvent=new SQLActionEvent();
   protected SQLTagsHandler     sqlTagHandler=new SQLTagsHandler();  
   protected HttpServletRequest     sqlTagRequest;
   private int                  stopRow=4;  
   protected String             startPage="1";  
   protected String             startRow="0";  
   protected Statement          stmt;  
   // private int                  totalReadFromCache=0;  
   protected String             where="";  
   protected static int         totalSelected=0;  
   
   /** Holds value of property startRowParameter. */
   private String startRowParameter="startRow";
   
 }// SQLTags() ENDS  
