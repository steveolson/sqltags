/* $Id: OracleLobSupport.java,v 1.4 2002/07/17 19:23:59 solson Exp $
 * $Log: OracleLobSupport.java,v $
 * Revision 1.4  2002/07/17 19:23:59  solson
 * cleaned up the Handler calls.  Modified calls to the handler class and fixed
 * parameters to class ... changed parameter from "Object" to "SQLTags"
 *
 * Revision 1.3  2002/07/11 20:10:00  jpoon
 * fixed bug for lob
 *
 * Revision 1.2  2002/07/09 20:51:06  jpoon
 * updated
 *
 * Revision 1.1  2002/07/09 15:05:33  jpoon
 * support method for lob
 *
 * Revision 1.2  2002/04/05 23:42:27  solson
 * added setException() call when postInsert or postUpdate encountered
 * an exception so that exception tag would "see" the error.
 *
 * Revision 1.1  2002/04/05 22:45:03  solson
 * new file.  Adds support for Oracle CLOB and BLOB datatypes
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
package com.aitworks.sqltags.contrib;
import com.aitworks.sqltags.utilities.*;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Vector;
import java.sql.*;
import java.io.*;
import oracle.sql.CLOB;
import oracle.sql.BLOB;

/**
 *
 * @author  john Poon
 */
public class OracleLobSupport extends SQLTagsHandler{

    /** Creates a new instance of OracleLobSupport */
    public OracleLobSupport() {
    }

   /**  
    * The postInsert method is used to process any information after the insert.  
    * @author  John Poon  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   object the object were changing.  
    * @return  true if no errors were encountered  
    */  
   //---------------------------------------------------------------------------  
   public Vector findLob(SQLTags sqltags){  
   //--------------------------------------------------------------------------- 
      Vector lobVector=new Vector();
      Hashtable hash=sqltags.getColumnPropertiesHash();
      Enumeration enum=hash.keys();
      
      while(enum.hasMoreElements())
      {
          String key=((String)enum.nextElement()).toUpperCase();  
          ColumnProperties columnProperties=(ColumnProperties)hash.get(key);
          String type=columnProperties.getType().toUpperCase();
          
          if(type.equalsIgnoreCase("CLOB") || type.equalsIgnoreCase("BLOB"))
          { 
             lobVector.addElement(columnProperties);
          }
      }
      
      return lobVector;
     
   }// findLob() ENDS  
      
      /**  
    * The postUpdate method is used to process any information after the update.  
    * @author  John Poon  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   object the object were changing.  
    * @return  true if no errors were encountered  
    */  
   //---------------------------------------------------------------------------  
   public boolean retrieveLobData(SQLTags sqltags){  
   //--------------------------------------------------------------------------- 
      if(preInsertVector.size()==0)
          return true;
       
      int postInsertVectorSize=0;
      OutputStream outputStream=null;
      CLOB clob=null;
      BLOB blob=null;
      
      if(sqltags.getException()!=null)
          return false;
      
      postInsertVector=findLob(sqltags);
      postInsertVectorSize=postInsertVector.size();

      sqltags.initialize(0);           
                  
      try
      {
         for(int index=0; index<postInsertVectorSize; index++)
         {
            ColumnProperties postInsertColumnProperties=(ColumnProperties)postInsertVector.elementAt(index);
            postInsertColumnProperties.setValue(lobHash.get(((ColumnProperties)preInsertVector.elementAt(index)).getName()).toString());
            if(postInsertColumnProperties.getType().equalsIgnoreCase("CLOB"))
            {
               clob=(CLOB)postInsertColumnProperties.getObject();
               outputStream=clob.getAsciiOutputStream();
            }
            else if(postInsertColumnProperties.getType().equalsIgnoreCase("BLOB"))
            {
               blob=(BLOB)postInsertColumnProperties.getObject();
               outputStream=blob.getBinaryOutputStream();
            }
            else
                continue;

            outputStream.write(lobHash.get(((ColumnProperties)preInsertVector.elementAt(index)).getName()).toString().getBytes());
            outputStream.flush();
            outputStream.close();
            lobHash.remove(((ColumnProperties)preInsertVector.elementAt(index)).getName());
         }
      }
      catch(SQLException sqlException)
      {
         sqltags.log("Handler.postInsert: "+ sqlException);
         sqltags.setException(sqlException);
         return false;
      }
      catch(IOException ioException)
      {
         sqltags.log("Handler.postInsert: "+ ioException);
         sqltags.setException(ioException);
         return false;
      }

      return true;
   } //retrieveLobData() ENDS
   
      /**  
    * The postInsert method is used to process any information after the insert.  
    * @author  John Poon  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   object the object were changing.  
    * @return  true if no errors were encountered  
    */  
   //---------------------------------------------------------------------------  
   public void saveLobData(SQLTags sqltags){  
   //--------------------------------------------------------------------------- 
     lobHash=new Hashtable();
      
     //get clob/blob columnproperties vector 
     preInsertVector=findLob(sqltags);
     
     //store name and value off columnproperties into hash
     for(int index=0; index<preInsertVector.size(); index++)
     {
         ColumnProperties cp = (ColumnProperties)preInsertVector.elementAt(index);
         cp.setBind("EMPTY_"+ cp.getType()+"()");
         lobHash.put(cp.getName(),cp.getValue());
     }
   }//saveLobData() ENDS
   
      /**  
    * The postInsert method is used to process any information after the insert.  
    * @author  John Poon  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   object the object were changing.  
    * @return  true if no errors were encountered  
    */  
   //---------------------------------------------------------------------------  
   public void setLobBind(SQLTags sqltags){  
   //--------------------------------------------------------------------------- 
     lobHash=new Hashtable();
      
     //get clob/blob columnproperties vector 
     preInsertVector=findLob(sqltags);
     
     //store name and value off columnproperties into hash
     for(int index=0; index<preInsertVector.size(); index++)
     {
         ColumnProperties cp = (ColumnProperties)preInsertVector.elementAt(index);
         cp.setBind("EMPTY_"+ cp.getType()+"()");
     }
   }//saveLobData() ENDS
   //***************************************************************************  
   // Class Variables
   //*************************************************************************** 
   private boolean   DEBUG=true;  
   private Hashtable lobHash;
   private Vector    preInsertVector=new Vector();
   private Vector    postInsertVector=new Vector();
}
