/* $Id: SQLTagsHandler.java,v 1.8 2002/07/17 19:23:58 solson Exp $
 * $Log: SQLTagsHandler.java,v $
 * Revision 1.8  2002/07/17 19:23:58  solson
 * cleaned up the Handler calls.  Modified calls to the handler class and fixed
 * parameters to class ... changed parameter from "Object" to "SQLTags"
 *
 * Revision 1.7  2002/06/13 19:20:46  jpoon
 * add pageContext to SQLTagsHandler
 *
 * Revision 1.6  2002/04/23 21:39:20  booker
 * menuing changes
 *
 * Revision 1.5  2002/04/03 14:49:23  booker
 * Worked on adding Object to ColumnProperties.
 *
 * Revision 1.4  2002/03/28 18:23:10  booker
 * Added try catch block to getException()
 *
 * Revision 1.3  2002/03/28 18:21:03  booker
 * Added getException() Method.
 *
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

/**  
 * This abstract class is used to define the contract between the user and  
 * the DBTable interface.  
 * @author  Booker Northington II  
 * @version 1.0  
 * @param   none  
 * @return  none  
 * @since   JDK1.3  
 */  
public class SQLTagsHandler{  
   //***************************************************************************  
   // Public Methods  
   //*************************************************************************** 
   public static String getSuper(SQLTags tag){
       return tag.getClass().getSuperclass().getName();
   }
   
   /*
   public void setPageContext(PageContext pageContext){  
      this.pageContext=pageContext;
   }
    */
   
   public Exception getException(){return exception;}
   
   public void onError(SQLTags tag){return;}
   // public boolean init(Object object){ return true;}
   
   // Tag-Type Events ... 
   public boolean afterBody(SQLTags tag){return true;}
   public boolean start(SQLTags tag){return true;}
   public boolean end(SQLTags tag){return true;}
   
   /*
   public boolean preStart(SQLTags tag){return true;}
   public boolean postStart(SQLTags tag){return true;}
   public boolean preAfterBody(SQLTags tag){return true;}
   public boolean postAfterBody(SQLTags tag){return true;}
   public boolean preEnd(SQLTags tag){return true;}     
   public boolean postEnd(SQLTags tag){return true;}
    */
   
   // SQL Events
   public boolean preInsert(SQLTags tag){return true;}
   public boolean postInsert(SQLTags tag){return true;}
   public boolean preUpdate(SQLTags tag){return true;}  
   public boolean postUpdate(SQLTags tag){return true;}
   public boolean preDelete(SQLTags tag){return true;}
   public boolean postDelete(SQLTags tag){return true;}
   public boolean preQuery(SQLTags tag){return true;}
   public boolean postFetch(SQLTags tag){return true;}
   public boolean postQuery(SQLTags tag){return true;}
   
   

   //***************************************************************************  
   // Class Variables
   //*************************************************************************** 
   private Exception exception;  
   // protected PageContext pageContext;
}//SQLTagsHandler() ENDS