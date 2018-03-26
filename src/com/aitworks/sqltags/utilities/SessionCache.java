/* $Id: SessionCache.java,v 1.2 2002/03/15 14:33:32 solson Exp $
 * $Log: SessionCache.java,v $
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
import java.util.Hashtable;  
import javax.servlet.http.HttpSessionBindingListener;  
import javax.servlet.http.HttpSessionBindingEvent;  
/**  
 * <code>SessionCache</code>  
 * <p>  
 * This class creates the scripting variables for the jsp.  
 * </p>  
 * @author  Booker Northington II  
 * @version 1.0  
 * @since   1.3  
 * @param none <code>none</code> none.  
 * @return none <code>none</code> none.  
 */  
//---------------------------------------------------------------------------  
/**  
 */  
public class SessionCache implements HttpSessionBindingListener{  
//---------------------------------------------------------------------------  
   private int      cacheDisplayCounter=0;  
   private boolean  cacheNextLink=true;  
   private boolean  cachePreviousLink=true;  
   private Hashtable cacheVectorHash=new Hashtable();  
   private boolean  DEBUG=false;  
   private Utilities utilities=new Utilities();  
   private int      lastCacheIndexRead=0;  
   private int      refreshCacheCounter=0;  
   private int      rowOffset=0;  
   private int      runningCount=0;  
   private int      sizeOfCursor=0;  
   private int      totalReadFromCache=0;  
   private boolean  validStartRow=true;  
   private Object[] pkValueArray=null;  
  
   /**  
    * <code>SessionCache</code>  
    * <p>  
    * Class default constructor. Do not call this directly.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none.  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public SessionCache(){  
   //---------------------------------------------------------------------------  
   }  
  
   //---------------------------------------------------------------------------  
   public SessionCache(SessionCache cache){  
   //---------------------------------------------------------------------------  
      this.cacheDisplayCounter=cache.getCacheDisplayCounter();  
      this.refreshCacheCounter=cache.getRefreshCacheCounter();  
      this.rowOffset=cache.getRowOffset();  
      this.runningCount=cache.getRunningCount();  
      this.validStartRow=cache.getValidStartRow();  
      this.pkValueArray=cache.getPKValueArray();  
   }  
  
   /**  
    * <code>getRefreshCacheCounter</code>  
    * <p>  
    * This method returns the current row offset.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param rowOffset <code>String</code> the row offset.  
    * @return <code>none</code>  
    */  
   //---------------------------------------------------------------------------  
   public int getRefreshCacheCounter(){  
   //---------------------------------------------------------------------------  
      return refreshCacheCounter;  
   }// getRefreshCacheCounter() ENDS  
  
   /**  
    * <code>getRowOffset</code>  
    * <p>  
    * This method returns the current row offset.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param rowOffset <code>String</code> the row offset.  
    * @return <code>none</code>  
    */  
   //---------------------------------------------------------------------------  
   public int getRowOffset(){  
   //---------------------------------------------------------------------------  
      return rowOffset;  
   }// getRowOffset() ENDS  
  
   /**  
    * <code>getValidStartRow</code>  
    * <p>  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code></code>   
    * @return <code>none</code>  
    */  
   //---------------------------------------------------------------------------  
   public boolean getValidStartRow(){  
   //---------------------------------------------------------------------------  
      return validStartRow;  
   }// getValidStartRow() ENDS  
  
   /**  
    * <code>isTimeToUpdateCache</code>  
    * <p>  
    *   
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param value <code></code>  
    * @return int <code>runningCount</code> the absolute record number  
    */  
   //---------------------------------------------------------------------------  
   public int getRunningCount(){  
   //---------------------------------------------------------------------------  
      return runningCount;  
   }// getRunningCount() ENDS  
  
   /**  
    * <code>getSizeOfCursor</code>  
    * <p>  
    * This method returns the size of the current cursor.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>none</code>  
    * @return value <code>int</code> The size of the current cursor.  
    */  
   //---------------------------------------------------------------------------  
   public int getSizeOfCursor(){  
   //---------------------------------------------------------------------------  
      return sizeOfCursor;  
   }// getSizeOfCursor() ENDS  
  
   /**  
    * <code>getTotalReadFromCache</code>  
    * <p>  
    * This method returns the size of the current cursor.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>none</code>  
    * @return value <code>int</code> The size of the current cursor.  
    */  
   //---------------------------------------------------------------------------  
   public int getTotalReadFromCache(){  
   //---------------------------------------------------------------------------  
      return totalReadFromCache;  
   }// getTotalReadFromCache() ENDS  
  
   /**  
    * <code>setRefreshCacheCounter</code>  
    * <p>  
    * This method returns the current row offset.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param rowOffset <code>String</code> the row offset.  
    * @return <code>none</code>  
    */  
   //---------------------------------------------------------------------------  
   public void setRefreshCacheCounter(int refreshCacheCounter){  
   //---------------------------------------------------------------------------  
      this.refreshCacheCounter=refreshCacheCounter;  
   }// getRefreshCacheCounter() ENDS  
  
   /**  
    * <code>setRunningCount</code>  
    * <p>  
    *   
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param runningCount <code>int</code> record number  
    * @return <code>none</code>   
    */  
   //---------------------------------------------------------------------------  
   public void setRunningCount(int runningCount){  
   //---------------------------------------------------------------------------  
      this.runningCount=runningCount;  
   }// setRunningCount() ENDS  
  
   /**  
    * <code>setSizeOfCursor</code>  
    * <p>  
    * This method returns the size of the current cursor.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>none</code>  
    * @return value <code>int</code> The size of the current cursor.  
    */  
   //---------------------------------------------------------------------------  
   public void setSizeOfCursor(int sizeOfCursor){  
   //---------------------------------------------------------------------------  
      this.sizeOfCursor=sizeOfCursor;  
   }// setSizeOfCursor() ENDS  
  
   /**  
    * <code>setTotalReadFromCache</code>  
    * <p>  
    * This method returns the size of the current cursor.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>none</code>  
    * @return value <code>int</code> The size of the current cursor.  
    */  
   //---------------------------------------------------------------------------  
   public void setTotalReadFromCache(int totalReadFromCache){  
   //---------------------------------------------------------------------------  
      this.totalReadFromCache=totalReadFromCache;  
   }// setTotalReadFromCache() ENDS  
  
   /**  
    * <code>setRowOffset</code>  
    * <p>  
    * This method sets the initial row which will be read into the cache vector.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param rowOffset <code>String</code> the row offset.  
    * @return <code>none</code>  
    */  
   //---------------------------------------------------------------------------  
   public void setRowOffset(int rowOffset){  
   //---------------------------------------------------------------------------  
      this.rowOffset=rowOffset;  
   }// setRowOffset() ENDS  
  
   /**  
    * <code>setValidStartRow</code>  
    * <p>  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code></code>   
    * @return <code>none</code>  
    */  
   //---------------------------------------------------------------------------  
   public void setValidStartRow(boolean validStartRow){  
   //---------------------------------------------------------------------------  
      this.validStartRow=validStartRow;  
   }// setValidStartRow() ENDS  
  
   //---------------------------------------------------------------------------  
   public void setCacheDisplayCounter(int value){  
   //---------------------------------------------------------------------------  
      cacheDisplayCounter=cacheDisplayCounter+value;  
   }// setCacheDisplayCounter() ENDS  
  
   //---------------------------------------------------------------------------  
   public void setCacheVectorHash(Hashtable cacheVectorHash){  
   //---------------------------------------------------------------------------  
      this.cacheVectorHash=cacheVectorHash;  
   }// setCacheVectorHash() ENDS  
  
   //---------------------------------------------------------------------------  
   public void setLastCacheIndexRead(int lastCacheIndexRead){  
   //---------------------------------------------------------------------------  
      this.lastCacheIndexRead=lastCacheIndexRead;  
   }// setLastCacheIndexRead() ENDS  
  
  
   //---------------------------------------------------------------------------  
   public void hasCacheNextLink(boolean cacheNextLink){  
   //---------------------------------------------------------------------------  
      this.cacheNextLink=cacheNextLink;  
   }// hasCacheNextLink() ENDS  
  
   //---------------------------------------------------------------------------  
   public void hasCachePreviousLink(boolean cachePreviousLink){  
   //---------------------------------------------------------------------------  
      this.cachePreviousLink=cachePreviousLink;  
   }// hasCachePreviousLink() ENDS  
  
  
   //---------------------------------------------------------------------------  
   public boolean isCacheNextLink(){  
   //---------------------------------------------------------------------------  
      return cacheNextLink;  
   }// isCacheNextLink() ENDS  
  
   //---------------------------------------------------------------------------  
   public boolean isCachePreviousLink(){  
   //---------------------------------------------------------------------------  
      return cachePreviousLink;  
   }// isCachePreviousLink() ENDS  
  
  
   //---------------------------------------------------------------------------  
   public int getNextDisplayCounter(){  
   //---------------------------------------------------------------------------  
      return cacheDisplayCounter;  
   }// getNextDisplayCounter() ENDS  
  
   //---------------------------------------------------------------------------  
   public int getCacheDisplayCounter(){  
   //---------------------------------------------------------------------------  
      return cacheDisplayCounter;  
   }// getCacheDisplayCounter() ENDS  
  
   //---------------------------------------------------------------------------  
   public Hashtable getCacheVectorHash(){  
   //---------------------------------------------------------------------------  
      return cacheVectorHash;  
   }// getCacheVectorHash() ENDS  
  
   //---------------------------------------------------------------------------  
   public int getCacheVectorHashSize(){  
   //---------------------------------------------------------------------------  
      return cacheVectorHash.size();  
   }// getCacheVectorHash() ENDS  
  
   //---------------------------------------------------------------------------  
   public int getLastCacheIndexRead(){  
   //---------------------------------------------------------------------------  
      return lastCacheIndexRead;  
   }// getLastCacheIndexRead() ENDS  
  
   //---------------------------------------------------------------------------  
   public Object[] getPKValueArray(){  
   //---------------------------------------------------------------------------  
      return pkValueArray;  
   }// getPKVValueArray() ENDS  
  
   //---------------------------------------------------------------------------  
   public void setPKValueArray(Object[] pkValueArray){  
   //---------------------------------------------------------------------------  
      this.pkValueArray=pkValueArray;  
   }// getCacheDisplayCounter() ENDS  
  
   //---------------------------------------------------------------------------  
   public void resetCacheDisplayCounter(int cacheDisplayCounter){  
   //---------------------------------------------------------------------------  
      this.cacheDisplayCounter=cacheDisplayCounter;  
   }// resetCacheDisplayCounter() ENDS  
  
   //---------------------------------------------------------------------------  
   public int getPreviousDisplayCounter(){  
   //---------------------------------------------------------------------------  
      int value=0;  
  
      if(cacheDisplayCounter>=2){  
         value=cacheDisplayCounter-1;  
      }  
      return value;  
   }// getCacheDisplayCounter() ENDS  
  
   //-------------------------------------------------------------------------  
   public void valueBound(HttpSessionBindingEvent event){  
   //-------------------------------------------------------------------------  
      if(DEBUG)  
         System.out.println(event.getName()+" bounded to session "+  
            utilities.parseObjectName(event.getSession().toString()));  
   }// valueBound() ENDS  
  
   //-------------------------------------------------------------------------  
   public void valueUnbound(HttpSessionBindingEvent event){  
   //-------------------------------------------------------------------------  
      if(DEBUG)  
         System.out.println(event.getName()+" unbounded from session "+  
            utilities.parseObjectName(event.getSession().toString()));  
   }// valueBound() ENDS  
  
}//SessionCache() ENDS  
