/* $Id: StoredProcedure.java,v 1.2 2002/03/15 14:33:30 solson Exp $
 * $Log: StoredProcedure.java,v $
 * Revision 1.2  2002/03/15 14:33:30  solson
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
import java.io.*;  
import java.sql.CallableStatement;  
import java.sql.Connection;  
import java.sql.ResultSet;  
import java.sql.SQLException;  
import java.sql.Statement;  
import java.util.*;  
  
/**  
 * class.  
 * @author  Booker Northington II  
 * @version 1.0  
 * @since   JDK1.3  
 */  
public class StoredProcedure{  
  
  
   /**  
    * Class constructor  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   private StoredProcedure(){  
   //-------------------------------------------------------------------------  
   }  
  
   /**  
    * This method returns the Singleton instance.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  the class instance.  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   static synchronized public StoredProcedure getInstance(){  
   //-------------------------------------------------------------------------  
      classInstance=new StoredProcedure();  
      return classInstance;  
   }// getInstance() ENDS  
  
   /**  
    * This method returns the current properties  
    * object.  
    * @author  Booker Northington II  
    * @param none  
    * @version 1.0  
    * @return  the current properties object.  
    * @since   JDK1.3  
    */   
   //------------------------------------------------------------------------  
   public ResultSet getStoredProcedure(Connection connection,   
		     String procedureName, boolean compoundQuery){  
   //------------------------------------------------------------------------  
      ResultSet resultSet=null;  
  
      try{  
         CallableStatement callableStatement=  
	    connection.prepareCall("{call "+procedureName+"}");  
  
         if(!compoundQuery)  
            resultSet=callableStatement.executeQuery();  
         else  
            callableStatement.execute();  
      }  
      catch(SQLException exception){  
      }  
  
      return resultSet;  
   }// getStoredProcedure() END  
  
   /**  
    * @author  Booker Northington II   
    * @param name the property key.  
    * @param value the value for the property.  
    * @version 1.0  
    * @return  none  
    * @since   JDK1.3  
    */  
   //------------------------------------------------------------------------  
   public void setStoredProcedure(Connection connection, String query,   
					  String procedureName){  
   //------------------------------------------------------------------------  
      try{  
         Statement stmt=connection.createStatement();  
         stmt.executeUpdate("create procedure "+procedureName+" as "+query);  
      }  
      catch(SQLException exception){   
      }  
   }// setProperty() ENDS  
     
   private static StoredProcedure classInstance;  
}// StoredProcedure() ENDS  
