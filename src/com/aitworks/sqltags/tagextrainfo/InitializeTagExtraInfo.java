/* $Id: InitializeTagExtraInfo.java,v 1.1 2002/05/28 19:39:24 solson Exp $
 * $Log: InitializeTagExtraInfo.java,v $
 * Revision 1.1  2002/05/28 19:39:24  solson
 * migrated from aitworks-jsptags
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
package com.aitworks.sqltags.tagextrainfo;
import javax.servlet.jsp.tagext.TagData;  
import javax.servlet.jsp.tagext.TagExtraInfo;  
import javax.servlet.jsp.tagext.VariableInfo;  
import com.aitworks.sqltags.jsptags.*;  
  
//------------------------------------------------------------------------------  
final public class InitializeTagExtraInfo extends TagExtraInfo{  
//------------------------------------------------------------------------------  
   //***************************************************************************  
   //Constructors  
   //***************************************************************************  
   public InitializeTagExtraInfo(){}

   /**  
    * <code>getVariableInfo</code>  
    * <p>  
    * This method makes the scripting variables available to the jsp.  
    * </p>  

    * @param value <code>TagData</code> The data associated with the tag.
    * @see TagExtraInfo <code>TagExtraInfo</code> For more information.
    * @see VariableInfo <code>VariableInfo</code> For more information.
    * @see TagData <code>TagData</code> For more information.
    * @return variableInfo <code>VariableInfo[]</code> An array of variableInfo.
    */
   public VariableInfo[] getVariableInfo(TagData value){
      return new VariableInfo[]{
         new VariableInfo(value.getId(),
            "InitializeTag",true,VariableInfo.AT_END)
      };
   }
}// ConnectionTagExtraInfo() ENDS  
