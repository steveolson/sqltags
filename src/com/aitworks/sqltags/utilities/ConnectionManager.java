/* $Id: ConnectionManager.java,v 1.15 2002/09/04 18:36:14 jpoon Exp $
 * $Log: ConnectionManager.java,v $
 * Revision 1.15  2002/09/04 18:36:14  jpoon
 * fix connection bug/clean up
 *
 * Revision 1.14  2002/08/28 20:50:50  jpoon
 * set connection to null in destroy
 *
 * Revision 1.13  2002/08/07 15:13:10  jpoon
 * re-construct connection manager
 *
 * Revision 1.12  2002/04/06 23:47:25  booker
 * Change scope of areZombieConnectionClosed(), closeZombieConnection(), setZombieConnections() and log().
 *
 * Revision 1.11  2002/04/06 23:32:35  booker
 * Added check to remove zombie connections from
 * releaseTheConnection(Connection)
 *
 * Revision 1.10  2002/04/06 22:16:50  booker
 * Removed Debugging
 * Modifed structure of doStart and getPoolVectorConnection
 *
 * Revision 1.9  2002/04/05 01:15:25  solson
 * added global exception (gException) to catch database error on
 * newConnection.  changed a system.out to log.  added test for null in the
 * zombie method to avoid NPE error and return more meaningful exception
 * to user
 *
 * Revision 1.8  2002/03/15 14:33:30  solson
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
import java.sql.Connection;  
import java.sql.SQLException;  
import java.sql.DriverManager;  
import java.util.*;  
import javax.servlet.ServletContext;  
  
/**  
 * This class manages the ConnectionManager object.  
 * @author  John Poon  
 * @version 1.0  
 * @return  none  
 * @since   JDK1.3  
 * @param none  
 * @return none  
 */  
public class ConnectionManager {      
   //***************************************************************************  
   // Class Variables
   //***************************************************************************      
    private static               ConnectionManager connectionManager=null;
    private int                  connectionPoolSize;
    private String               databaseDriver;
    private String               id="";
    private int                  maxConnections;
    private String               maxPoolSize;
    private Vector               connectionPool=new Vector();
    private String               poolSize;
    private String               url;
    private String               user;
    private String               password;
    private Hashtable            zombieConnections=new Hashtable();
    private Properties           properties=new Properties();
   
   //***************************************************************************  
   // Class Constructors  
   //***************************************************************************  
   /**  
    * Singleton class constructor  
    * @author  John Poon
    * @version 1.0  
    * @return  none  
    * @since   JDK1.3  
    * @param   none  
    * @return  none  
    */  
   //-------------------------------------------------------------------------  
   private ConnectionManager(){  
   //-------------------------------------------------------------------------  
   }  
   
   //-------------------------------------------------------------------------  
   private ConnectionManager(Properties properties){  
   //-------------------------------------------------------------------------        
       this.properties=properties;
   } 
   
   /**  
    * This method returns the connection manager to the caller then bumps   
    * the client connection count.  
    * @author  John Poon  
    * @version 1.0  
    * @return  none  
    * @since   JDK1.3  
    * @param none  
    * @return the ConnectionManager classInstance.  
    */  
   //-------------------------------------------------------------------------  
   static synchronized private ConnectionManager getInstance(Properties properties) throws SQLException, ClassNotFoundException{  
   //-------------------------------------------------------------------------  
       if(connectionManager== null)  {
           connectionManager=new ConnectionManager(properties);
           
           //create connection pool
           connectionManager.createConnectionPool(properties);
      }
       return connectionManager;  
   }// getInstance() ENDS 

   //-------------------------------------------------------------------------  
   private synchronized void createConnectionPool(Properties properties) throws SQLException, ClassNotFoundException{
   //-------------------------------------------------------------------------
       this.properties=properties;
       createConnectionPool();
   }
   
   //-------------------------------------------------------------------------  
   private synchronized void createConnectionPool() throws SQLException, ClassNotFoundException{
   //-------------------------------------------------------------------------
         user=(String)properties.get("userName");  
         password=(String)properties.get("password");  
         url=(String)properties.get("connectionUrl");  
         maxPoolSize=(String)properties.get("maxPoolSize");  
         poolSize=(String)properties.get("poolSize");  
         databaseDriver=(String)properties.get("databaseDriver");
         
         try {
             connectionPoolSize=Integer.parseInt(poolSize);
             maxConnections=Integer.parseInt(maxPoolSize);
         }
         catch(NumberFormatException exception){
             System.out.println("ConnectionManager.createConnectionPool(): "+exception);
             connectionPoolSize=0;
             maxConnections=0;
         }
         Class.forName(databaseDriver);  

         //Create connection pool  
         for(int index=0;index<connectionPoolSize;index++){  
            Connection connection=newConnection();
            
            //add connection to pool if it's not null
            if(connection!=null)
	         addConnectionToConnectionPool(connection);  
         }     
   }
   
   //-------------------------------------------------------------------------     
   private synchronized int addConnectionToConnectionPool(Connection connection) throws SQLException{
   //-------------------------------------------------------------------------  
       //if it hasn't reach the max connection size, add connection to pool
       if(!isMaxConnections()) {                    
           if(connection==null)
               connection=newConnection();
           connectionPool.addElement(connection);
       }
       return connectionPool.size();
   }
       
   //-------------------------------------------------------------------------     
   public static Connection getConnection(Properties properties) throws SQLException, ClassNotFoundException{
   //-------------------------------------------------------------------------  
      connectionManager=getInstance(properties);
      return connectionManager.getConnection();
   }
   
       
   //-------------------------------------------------------------------------     
   public synchronized Connection getConnection() throws SQLException{
   //-------------------------------------------------------------------------  
       Connection connection=null;

       //get an open connection from the pool
       if(connectionPool.size()>0) {
           connection=(Connection)connectionPool.remove(0);
       }     
       
       //no more connection, try increasing pool size
       else {
           if(increaseConnectionPoolSize()) {
               return getConnection();
           }
           
           //max pool size has reached, try again later
           else {
               try {
                   wait(300000);

               }
               catch(InterruptedException ex){
                   //doNothing
               }
               return getConnection();
           }

       }
       
       //if the connection is closed get new Connection
       if(connection==null || connection.isClosed())
       {
           return getConnection();
       }
       zombieConnections.put(connection, connection);

       return connection;
   }

   //-------------------------------------------------------------------------     
   public static synchronized void releaseConnection(Connection connection) throws SQLException{
   //------------------------------------------------------------------------- 
       if(connectionManager!=null)
           connectionManager.releaseTheConnection(connection);
   }
       
   //-------------------------------------------------------------------------     
   private synchronized void releaseTheConnection(Connection connection) throws SQLException{
   //------------------------------------------------------------------------- 
       if(connection==null)
           return;

      Connection releasedConnection=(Connection)zombieConnections.remove(connection);
      if(connectionPool.size()>connectionPoolSize)
          destroyConnection(releasedConnection);
      else {
         addConnectionToConnectionPool(releasedConnection);
         notify(); 
      }    
   }

   //-------------------------------------------------------------------------            
   private synchronized boolean decreaseConnectionPoolSize() throws SQLException {
   //-------------------------------------------------------------------------  
       if(connectionPool.size()<0)
           return false;
       
       destroyConnection((Connection)connectionPool.remove(0));
       return true;
   }
   
   //-------------------------------------------------------------------------            
   private synchronized boolean increaseConnectionPoolSize() throws SQLException{
   //-------------------------------------------------------------------------  
       if(!isMaxConnections()) {
           addConnectionToConnectionPool(newConnection());
           return true;
       }
       return false;
   }
   /**  
    * This method creates a new connection, using a userid and password  
    * @author  John Poon  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   private synchronized Connection newConnection() throws SQLException{  
   //-------------------------------------------------------------------------  
      Connection connection = null;  
      if(user==null)
          connection=DriverManager.getConnection(url);
      else
          connection=DriverManager.getConnection(url, user, password);
  
      return connection;  
   }// newConnection() ENDS         

   /**  
    * This method closes all open connections.  
    * @author  John Poon  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public synchronized void releaseAllConnections() throws SQLException{  
   //-------------------------------------------------------------------------  
       //Put all the zombie connection back to connection Pool
       Enumeration keys=zombieConnections.keys();
       for(;keys.hasMoreElements();)            
           releaseTheConnection((Connection)keys.nextElement());
       
       //close every connection in the connectionPool
      Enumeration allConnections=connectionPool.elements();  
  
      for(;allConnections.hasMoreElements();){  
         destroyConnection((Connection)allConnections.nextElement());  
      }  
  
      connectionPool.removeAllElements();
   }// release() ENDS  
   
   //-------------------------------------------------------------------------    
   private synchronized int totalConnections() {
   //-------------------------------------------------------------------------        
       return connectionPool.size()+zombieConnections.size();
   }
   
   //-------------------------------------------------------------------------    
   private synchronized boolean isMaxConnections() {
   //-------------------------------------------------------------------------       
       if(maxConnections==0)
           return false;
       if(totalConnections()>=maxConnections)
           return true;
       return false;
   }
      
   //-------------------------------------------------------------------------       
   private void destroyConnection(Connection connection) throws SQLException{
   //------------------------------------------------------------------------- 
       if(connection==null)
           return;

       if(!connection.isClosed()) 
           connection.close();
       
       connection=null;
   }       
}
