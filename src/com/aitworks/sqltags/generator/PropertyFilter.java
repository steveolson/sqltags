/* $Id: PropertyFilter.java,v 1.3 2002/03/15 14:23:45 solson Exp $
 * $Log: PropertyFilter.java,v $
 * Revision 1.3  2002/03/15 14:23:45  solson
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
import javax.swing.filechooser.FileFilter;
import java.util.Hashtable;
import java.io.File;
import java.util.Enumeration;

/**  
 * <code>PropertyFilter</code>  
 * <p>  
 * PropertyFilter filters what files to display in the save and open dialog.   
 * </p>  
 * @author  Booker Northington II  
 * @version 1.0  
 * @since   1.3  
 */  
//------------------------------------------------------------------------------
public class PropertyFilter extends FileFilter{
//------------------------------------------------------------------------------
   //***************************************************************************  
   // Class Constructors  
   //***************************************************************************  
   /**  
    * <code>PropertyFilter</code>  
    * <p>  
    *
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code>   
    * @return true <code>boolean</code> true if the file is to be displayed  
    */  
   //---------------------------------------------------------------------------
   public PropertyFilter(String filter){
   //---------------------------------------------------------------------------
        this.filter=filter;
   }//Constructor ENDS
   /**  
    * <code>PropertyFilter</code>  
    * <p>  
    * 
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code>   
    * @return true <code>boolean</code> true if the file is to be displayed  
    */  
   //---------------------------------------------------------------------------
   public PropertyFilter(){
   //---------------------------------------------------------------------------
   }//Constructor ENDS
      
   /**  
    * <code>accept</code>  
    * <p>  
    * This method determins whether a file will be displayed in the dialog.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code>   
    * @return true <code>boolean</code> true if the file is to be displayed  
    */  
   //---------------------------------------------------------------------------
   public boolean accept(File filename) {
   //---------------------------------------------------------------------------
      String extension=GeneratorGUI.fileExtension(filename);
//      String propertiesExtension=GeneratorGUI.propertiesExtension;
      String propertiesExtension=filter;
      boolean returnValue=false;
      boolean isDir=filename.isDirectory();

      if ((extension!=null&&extension.equals(propertiesExtension))||isDir)
         returnValue=true;

      return returnValue;
   }//accept() ENDS

   /**  
    * <code>getDescription</code>  
    * <p>  
    * The description displayed in the dialog.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code>   
    * @return name <code>String</code> the description to display.  
    */  
   //---------------------------------------------------------------------------
   public String getDescription(){
   //---------------------------------------------------------------------------
      return filter;
   }//getDescription() ENDS   
   
   /**  
    * <code>setFilter</code>  
    * <p>  
    * sets the current filter.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param filter <code>String</code> the filter.
    * @return name <code></code> 
    */  
   //---------------------------------------------------------------------------
   public void setFilter(String filter){
   //---------------------------------------------------------------------------
      this.filter=filter;
   }//setFilter() ENDS   

   private String filter="properties";
}// PropertyFilter() ENDS
