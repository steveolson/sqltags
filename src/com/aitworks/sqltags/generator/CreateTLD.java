/* $Id: CreateTLD.java,v 1.33 2002/08/08 19:38:38 jpoon Exp $
 * $Log: CreateTLD.java,v $
 * Revision 1.33  2002/08/08 19:38:38  jpoon
 * add hasFetch for cursorTag
 *
 * Revision 1.32  2002/07/10 18:14:23  hongxia
 * Add teiclass to sqlTagRequestTag
 *
 * Revision 1.31  2002/07/05 18:22:05  lindahl
 * Fixed bugs in initializeTLD (as called from SQLTagsGeneratorTable)
 * Converted attributes data structures to vectors do you don't have to specify type every time
 * Made some previously public functions private
 *
 * Revision 1.30  2002/06/28 06:24:28  lindahl
 * compressed all "create___Tag" functions into createAllTags, which calls createTag
 * made Java  1.2 (J2SE) compliant
 * updated initializeTLD to utilize with createTLD
 *
 * Revision 1.29  2002/06/25 16:14:33  solson
 * added startRowParameter to table tags and cursor tag
 *
 * Revision 1.28  2002/06/21 13:35:57  solson
 * fixed bad attribute def for auth and authorize tags
 * added logoutPage to auth and authroize tags
 *
 * Revision 1.27  2002/06/19 22:33:32  solson
 * added AuthorizeTag, AuthorizedTag, DeniedTag, ExpiredTag,
 * and ExpiringTag to the TLD creation.
 *
 * Revision 1.26  2002/06/03 14:46:02  jpoon
 * add SQLTagsRequestTag
 *
 * Revision 1.25  2002/05/30 15:18:51  solson
 * removed extra tags
 * moved initializeTag from aitworks-jsptags to aitworks-sqltags
 *
 * Revision 1.24  2002/05/28 17:28:00  solson
 * removed extraneous tags and tie references
 *
 * Revision 1.23  2002/04/26 17:55:17  booker
 * added attributes to for tbmenu tag
 *
 * Revision 1.22  2002/04/22 12:47:33  booker
 * corrected tag attribute for brookgroup tag.
 *
 * Revision 1.21  2002/04/22 12:31:34  booker
 * Added method to add new tacklebox menu tags to tld.
 *
 * Revision 1.20  2002/04/20 16:40:25  booker
 * added attribute
 *
 * Revision 1.19  2002/04/05 23:45:33  solson
 * updated the tagclass for ExceptionTag to reflect the move from
 * com.aitworks.jsptags.conditional to com.aitworks.sqltags.jsptags
 *
 * Revision 1.18  2002/04/04 18:12:04  booker
 * Removed tei class
 *
 * Revision 1.17  2002/04/04 13:22:22  booker
 * Added Attributes to FetchTag
 * Removed Attributes from CommitTag
 *
 * Revision 1.16  2002/03/15 14:23:45  solson
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
import java.io.*;  
import java.sql.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  
import javax.servlet.jsp.*;  
import javax.servlet.jsp.tagext.*;  
import java.net.*; 
  
/**  
 * This class is used to create a .TLD descriptor file for  
 * each table in the database.  
 * @author  Booker Northington II  
 * @version 1.0  
 * @param   none  
 * @return  none  
 * @since   JDK1.3  
 */  
final class CreateTLD{  
  /**  
    * This constructor is used to initialize the shortname property  
    * within the tld file.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   the new shortname.  
    * @return  none  
    * @since   JDK1.3  
    */  
   //---------------------------------------------------------------------------  
   public CreateTLD(String shortName, String packageName){   
   //---------------------------------------------------------------------------  
      this.packageName=packageName;  
      this.shortName=shortName;
      
      if(jspversion.equals("1.2"))
      {
          dtdhref="http://java.sun.com/dtd/web-jsptaglibrary_1_2.dtd";

          tlibversionText = "tlib-version";
          jspversionText = "jsp-version";
          bodycontentText = "body-content";
          
          tagclassText = "tag-class";
          teiclassText = "tei-class";
          bodycontentText = "body-content";
      }

      initializeHeader();
      createAllTags();
   }
      
   /**  
    * This method calls createTag for each of the tags.  
    * @author  Ben Lindahl
    * @version 1.0  
    * @param   none   
    * @return  none  
    * @since   JDK1.3  
    */  
   //---------------------------------------------------------------------------  
   private void createAllTags(){   
   //---------------------------------------------------------------------------  
      Hashtable properties = new Hashtable();
      Vector    requiredAttributes = new Vector();
      Vector    optionalAttributes = new Vector();
      
      // All the tags below are in alphabetical order
      
      // Authorize tag (also Auth tag... same function, different name)
      //   Add properties:
      properties.put("name","authorize");
      properties.put(tagclassText,"com.aitworks.sqltags.jsptags.AuthorizeTag");
      properties.put(teiclassText,"com.aitworks.sqltags.tagextrainfo.AuthorizeTagExtraInfo");
      properties.put("info","Authorize tag.");
      properties.put(bodycontentText,"JSP");
      //   Add required attributes:
      requiredAttributes.add("id");
      //   Add optional attributes:
      optionalAttributes.add("initSrc");
      optionalAttributes.add("operation");
      optionalAttributes.add("userParameter");
      optionalAttributes.add("passwordParameter");
      optionalAttributes.add("newPasswordParameter");
      optionalAttributes.add("logoutPage");
      optionalAttributes.add("loginPage");
      optionalAttributes.add("expiredPage");
      optionalAttributes.add("initURL");
      optionalAttributes.add("initID");
      optionalAttributes.add("initProperties");
      //   Send to createTag
      createTag(properties, requiredAttributes, optionalAttributes);
      //   Duplicate this tag so it can be called with "auth" keyword in addition to "authorize"
      properties.put("name","auth");
      //   Send to createTag
      createTag(properties, requiredAttributes, optionalAttributes);
      //   Clear Hashtables for the next tag
      properties.clear();
      requiredAttributes.clear();
      optionalAttributes.clear();

      // Authorized tag
      //   Add properties:
      properties.put("name","authorized");
      properties.put(tagclassText,"com.aitworks.sqltags.jsptags.AuthorizedTag");
      properties.put("info","Authorized tag.");
      properties.put(bodycontentText,"JSP");
       //   Add required attributes:
      requiredAttributes.add("name");
     //   Send to createTag
      createTag(properties, requiredAttributes, null);
      //   Clear Hashtables for he next tag
      properties.clear();
      requiredAttributes.clear();

      // Commit tag
      //   Add properties:
      properties.put("name","commit");
      properties.put(tagclassText,"com.aitworks.sqltags.jsptags.CommitTag");
      properties.put("info","Commit tag.");
      properties.put(bodycontentText,"JSP");
      //   Send to createTag
      createTag(properties, null, null);
      //   Clear Hashtables for he next tag
      properties.clear();

      // Connection tag
      //   Add properties:
      properties.put("name","connection");
      properties.put(tagclassText,"com.aitworks.sqltags.jsptags.ConnectionTag");
      properties.put(teiclassText,"com.aitworks.sqltags.tagextrainfo.ConnectionTagExtraInfo");
      properties.put("info","Connection tag.");
      properties.put(bodycontentText,"JSP");
      //   Add required attributes:
      requiredAttributes.add("id");
      //   Add optional attributes:
      optionalAttributes.add("initSrc");
      optionalAttributes.add("autoCommit");
      optionalAttributes.add("readOnly");
      optionalAttributes.add("name");
      optionalAttributes.add("initURL");
      optionalAttributes.add("initID");
      optionalAttributes.add("initProperties");
      //   Send to createTag
      createTag(properties, requiredAttributes, optionalAttributes);
      //   Clear Hashtables for the next tag
      properties.clear();
      requiredAttributes.clear();
      optionalAttributes.clear();

      // Cursor tag
      //   Add properties:
      properties.put("name","cursor");
      properties.put(tagclassText,"com.aitworks.sqltags.jsptags.CursorTag");
      properties.put(teiclassText,"com.aitworks.sqltags.tagextrainfo.CursorTagExtraInfo");
      properties.put("info","Cursor tag.");
      properties.put(bodycontentText,"JSP");
      //   Add required attributes:
      requiredAttributes.add("id");
      //   Add optional attributes:
      optionalAttributes.add("sql");
      optionalAttributes.add("primaryKeys");
      optionalAttributes.add("caching");
      optionalAttributes.add("paging");
      optionalAttributes.add("cacheSize");
      optionalAttributes.add("displaySize");
      optionalAttributes.add("hasFetch");
      //   Send to createTag
      createTag(properties, requiredAttributes, optionalAttributes);
      //   Clear Hashtables for the next tag
      properties.clear();
      requiredAttributes.clear();
      optionalAttributes.clear();

      // Denied tag
      //   Add properties:
      properties.put("name","denied");
      properties.put(tagclassText,"com.aitworks.sqltags.jsptags.DeniedTag");
      properties.put("info","Denied tag.");
      properties.put(bodycontentText,"JSP");
      //   Add required attributes:
      requiredAttributes.add("name");
      //   Send to createTag
      createTag(properties, requiredAttributes, null);
      //   Clear Hashtables for the next tag
      properties.clear();
      requiredAttributes.clear();

      // Exception tag
      //   Add properties:
      properties.put("name","exception");
      properties.put(tagclassText,"com.aitworks.sqltags.jsptags.ExceptionTag");
      properties.put("info","Exception tag.");
      properties.put(bodycontentText,"JSP");
      //   Add required attributes:
      requiredAttributes.add("name");
      //   Add optional attributes:
      optionalAttributes.add("outputSource");
      optionalAttributes.add("skipPage");
      optionalAttributes.add("fontColor");
      optionalAttributes.add("fontClass");
      optionalAttributes.add("fontFace");
      optionalAttributes.add("fontSize");
      optionalAttributes.add("fontStyle");
      optionalAttributes.add("clearException");
      //   Send to createTag
      createTag(properties, requiredAttributes, optionalAttributes);
      //   Clear Hashtables for the next tag
      properties.clear();
      requiredAttributes.clear();
      optionalAttributes.clear();

      // Expired tag
      //   Add properties:
      properties.put("name","expired");
      properties.put(tagclassText,"com.aitworks.sqltags.jsptags.ExpiredTag");
      properties.put("info","Expired tag.");
      properties.put(bodycontentText,"JSP");
      //   Add required attributes:
      requiredAttributes.add("name");
      //   Send to createTag
      createTag(properties, requiredAttributes, null);
      //   Clear Hashtables for the next tag
      properties.clear();
      requiredAttributes.clear();

      // Expiring tag
      //   Add properties:
      properties.put("name","expiring");
      properties.put(tagclassText,"com.aitworks.sqltags.jsptags.ExpiringTag");
      properties.put("info","Expired tag.");
      properties.put(bodycontentText,"JSP");
      //   Add required attributes:
      requiredAttributes.add("name");
      //   Send to createTag
      createTag(properties, requiredAttributes, null);
      //   Clear Hashtables for the next tag
      properties.clear();
      requiredAttributes.clear();

      // Fetch tag
      //   Add properties:
      properties.put("name","fetch");
      properties.put(tagclassText,"com.aitworks.sqltags.jsptags.FetchTag");
      properties.put("info","Local fetch tag.");
      properties.put(bodycontentText,"JSP");
      //   Add required attributes:
      requiredAttributes.add("name");
      //   Send to createTag
      createTag(properties, requiredAttributes, null);
      //   Clear Hashtables for he next tag
      properties.clear();
      requiredAttributes.clear();

      // First tag
      //   Add properties:
      properties.put("name","first");
      properties.put(tagclassText,"com.aitworks.sqltags.jsptags.FirstTag");
      properties.put("info","This tag interacts with the first record fetched.");
      properties.put(bodycontentText,"JSP");
      //   Add required attributes:
      requiredAttributes.add("name");
      //   Send to createTag
      createTag(properties, requiredAttributes, null);
      //   Clear Hashtables for he next tag
      properties.clear();
      requiredAttributes.clear();

      // Initialize tag
      //   Add properties:
      properties.put("name","initialize");
      properties.put(tagclassText,"com.aitworks.sqltags.jsptags.InitializeTag");
      properties.put("info","Initialize tag..");
      properties.put(bodycontentText,"JSP");
      //   Add required attributes:
      requiredAttributes.add("id");
      //   Add optional attributes:
      optionalAttributes.add("initSrc");
      optionalAttributes.add("initEncrypted");
      optionalAttributes.add("scope");
      optionalAttributes.add("initURL");
      optionalAttributes.add("initID");
      optionalAttributes.add("initProperties");
      //   Send to createTag
      createTag(properties, requiredAttributes, optionalAttributes);
      //   Clear Hashtables for he next tag
      properties.clear();
      requiredAttributes.clear();
      optionalAttributes.clear();

      // Last tag
      //   Add properties:
      properties.put("name","last");
      properties.put(tagclassText,"com.aitworks.sqltags.jsptags.LastTag");
      properties.put("info","This tag interacts with the last record fetched.");
      properties.put(bodycontentText,"JSP");
      //   Add required attributes:
      requiredAttributes.add("name");
      //   Send to createTag
      createTag(properties, requiredAttributes, null);
      //   Clear Hashtables for he next tag
      properties.clear();
      requiredAttributes.clear();

      // Next tag
      //   Add properties:
      properties.put("name","next");
      properties.put(tagclassText,"com.aitworks.sqltags.jsptags.NextTag");
      properties.put("info","This tag interacts with record after the current one.");
      properties.put(bodycontentText,"JSP");
      //   Add required attributes:
      requiredAttributes.add("parentName");
      //   Add optional attributes:
      optionalAttributes.add("href");
      optionalAttributes.add("name");
      optionalAttributes.add("noHref");
      optionalAttributes.add("rel");
      optionalAttributes.add("rev");
      optionalAttributes.add("target");
      optionalAttributes.add("title");
      //   Send to createTag
      createTag(properties, requiredAttributes, optionalAttributes);
      //   Clear Hashtables for he next tag
      properties.clear();
      requiredAttributes.clear();
      optionalAttributes.clear();

      // OrderBy tag
      //   Add properties:
      properties.put("name","orderby");
      properties.put(tagclassText,"com.aitworks.sqltags.jsptags.OrderByTag");
      properties.put("info","OrderBy tag.");
      properties.put(bodycontentText,"JSP");
      //   Add required attributes:
      requiredAttributes.add("name");
      //   Send to createTag
      createTag(properties, requiredAttributes, null);
      //   Clear Hashtables for he next tag
      properties.clear();
      requiredAttributes.clear();
    

     // Statement tag
      //   Add properties:
      properties.put("name","statement");
      properties.put(tagclassText,"com.aitworks.sqltags.jsptags.StatementTag");
      properties.put("info","Statement tag.");
      properties.put(bodycontentText,"JSP");
      //   Add required attributes:
      requiredAttributes.add("name");
      //   Send to createTag
      createTag(properties, requiredAttributes, null);
      //   Clear Hashtables for he next tag
      properties.clear();
      requiredAttributes.clear();
    

      // Previous tag
      //   Add properties:
      properties.put("name","previous");
      properties.put(tagclassText,"com.aitworks.sqltags.jsptags.PreviousTag");
      properties.put("info","This tag interacts with record before the current one.");
      properties.put(bodycontentText,"JSP");
      //   Add required attributes:
      requiredAttributes.add("parentName");
      //   Add optional attributes:
      optionalAttributes.add("href");
      optionalAttributes.add("name");
      optionalAttributes.add("noHref");
      optionalAttributes.add("rel");
      optionalAttributes.add("rev");
      optionalAttributes.add("target");
      optionalAttributes.add("title");
      //   Send to createTag
      createTag(properties, requiredAttributes, optionalAttributes);
      //   Clear Hashtables for he next tag
      properties.clear();
      requiredAttributes.clear();
      optionalAttributes.clear();

      // Rollback tag
      //   Add properties:
      properties.put("name","rollback");
      properties.put(tagclassText,"com.aitworks.sqltags.jsptags.RollbackTag");
      properties.put("info","Rollback tag.");
      properties.put(bodycontentText,"JSP");
      //   Send to createTag
      createTag(properties, null, null);
      //   Clear Hashtables for he next tag
      properties.clear();

      // SQLTagsRequest tag
      //   Add properties:
      properties.put("name","sqlTagsRequest");
      properties.put(tagclassText,"com.aitworks.sqltags.jsptags.SQLTagsRequestTag");
      properties.put(teiclassText,"com.aitworks.sqltags.tagextrainfo.SQLTagsRequestTagExtraInfo");
      properties.put("info","SQLTagsRequest tag.");
      properties.put(bodycontentText,"JSP");
      //   Add required attributes:
      requiredAttributes.add("id");
      //   Send to createTag
      createTag(properties, requiredAttributes, null);
      //   Clear Hashtables for he next tag
      properties.clear();
      requiredAttributes.clear();

      // Where tag
      //   Add properties:
      properties.put("name","where");
      properties.put(tagclassText,"com.aitworks.sqltags.jsptags.WhereTag");
      properties.put("info","Local where tag.");
      properties.put(bodycontentText,"JSP");
      //   Add required attributes:
      requiredAttributes.add("name");
      //   Send to createTag
      createTag(properties, requiredAttributes, null);
      //   Clear Hashtables for he next tag
      properties.clear();
      requiredAttributes.clear();

   }
      
    /**  
    * This method creates a tag with the specified properties and attributes tag.  
    * @author  Ben Lindahl
    * @version 1.0  
    * @param   properties is the properties in the TLD that identify this tag and specify how it functions (its name, class, TEI, contents)
    * @param   requiredAttributes is the attributes that the tag must have when called from JSP (id, etc...)
    * @param   optionalAttributes is the attributes that the tag may or may not have when called from JSP
    * @return  none
    * @since   JDK1.3  
    */  
   //---------------------------------------------------------------------------  
   private void createTag(Hashtable properties, Vector requiredAttributes, Vector optionalAttributes){   
   //---------------------------------------------------------------------------  
      buffer.append(cr+openTag("tag"));

      String key;
      String property;

      Enumeration keys = properties.keys();
      while(keys.hasMoreElements())
      {
          key = (String)(keys.nextElement());
          property = (String)(properties.get(key));

          buffer.append(openCloseTag(key,property));
      }
 
      if(requiredAttributes != null)
      {
          keys = requiredAttributes.elements();
          while(keys.hasMoreElements())
          {
              key = (String)(keys.nextElement());
              setTLDAttribute(key,"true","java.lang.String");
          }
      }

      if(optionalAttributes != null)
      {
          keys = optionalAttributes.elements();
          while(keys.hasMoreElements())
          {
              key = (String)(keys.nextElement());
              setTLDAttribute(key,"false","java.lang.String");
          }
      }

      buffer.append(closeTag("tag"));
   }
  
   /**  
    * This method initializes the header format  
    * the JSP.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none   
    * @return  none  
    * @since   JDK1.3  
    */  
   //---------------------------------------------------------------------------  
   private void initializeHeader(){  
   //---------------------------------------------------------------------------  
      buffer=new StringBuffer();
      buffer.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>"+cr);  
      buffer.append("<!DOCTYPE taglib\nPUBLIC \"-//Sun Microsystems, Inc.//");  
      buffer.append("DTD JSP Tag Library "+jspversion+"//EN\"\n");  
      buffer.append("\""+dtdhref+"\">"+cr+cr);
      
      buffer.append(openTag("taglib"));
      buffer.append(openCloseTag(tlibversionText,tlibversion));  
      buffer.append(openCloseTag(jspversionText,jspversion));  
      buffer.append(openCloseTag(shortnameText,shortName));  
      buffer.append(openCloseTag("info",""));
   }// initializeHeaders() ENDS
  
   /**  
    * This method is used to initialize the top header information  
    * contained within the .tld file.  
    * @author  Benjamin Lindahl
    * @version 1.1 
    * @param   table is the table name.  
    * @param   packageName is the class package name.  
    * @param   optionalAttributes is any optional attributes you want to pass in (usually the attributes corresponding to the columns specific to this table) 
    * @return  none  
    * @since   JDK1.3  
    */  
   //---------------------------------------------------------------------------  
   public void initializeTLD(String table, String packageName, Vector optionalAttributes){  
   //---------------------------------------------------------------------------  
      Hashtable properties = new Hashtable();
      Vector requiredAttributes = new Vector();

      // Set up the base table tag
      //   Add properties:
      properties.put("name",table.toLowerCase());
      properties.put(tagclassText,packageName+"."+table+"_TAG");
      properties.put(teiclassText,packageName+"."+table+"_TagExtraInfo");
      properties.put("info","Base table tag.");
      properties.put(bodycontentText,"JSP");
      //   Add required attributes:
      requiredAttributes.add("id");
      //   Add optional attributes:
      optionalAttributes.insertElementAt("where",0);
      optionalAttributes.insertElementAt("startRowParameter",0);
      optionalAttributes.insertElementAt("startPage",0);
      optionalAttributes.insertElementAt("properties",0);
      optionalAttributes.insertElementAt("preUpdateSQL",0);
      optionalAttributes.insertElementAt("preInsertSQL",0);
      optionalAttributes.insertElementAt("paging",0);
      optionalAttributes.insertElementAt("parentName",0);
      optionalAttributes.insertElementAt("output",0);
      optionalAttributes.insertElementAt("orderBy",0);
      optionalAttributes.insertElementAt("operation",0);
      optionalAttributes.insertElementAt("maxRows",0);
      optionalAttributes.insertElementAt("handlerID",0);
      optionalAttributes.insertElementAt("hasFetch",0);
      optionalAttributes.insertElementAt("handlerClass",0);
      optionalAttributes.insertElementAt("foreignKey",0);
      optionalAttributes.insertElementAt("distinct",0);
      optionalAttributes.insertElementAt("displaySize",0);
      optionalAttributes.insertElementAt("currentPage",0);
      optionalAttributes.insertElementAt("columns",0);
      optionalAttributes.insertElementAt("childName",0);
      optionalAttributes.insertElementAt("caching",0);
      optionalAttributes.insertElementAt("cacheSize",0);
      optionalAttributes.insertElementAt("cacheScheme",0);
      optionalAttributes.insertElementAt("buttonName",0);
      //   Send to createTag
      createTag(properties, requiredAttributes, optionalAttributes);
   }  
  
   /**  
    * This method sets the attribute name for the tag.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   attribute the name of the attribute.  
    * @param   required true if the attribute is required.  
    * @return  none  
    * @since   JDK1.3  
    */  
   //---------------------------------------------------------------------------  
   private void setTLDAttribute(String attribute, String required, String objectType){  
   //---------------------------------------------------------------------------  
      buffer.append(openTag("attribute"));  
      buffer.append(openCloseTag("name",attribute));  
      buffer.append(openCloseTag("required",required));  
      buffer.append(openCloseTag("rtexprvalue","true"));  
      buffer.append(openCloseTag("type",objectType));  
      buffer.append(closeTag("attribute"));  
   }  
  
   /**  
    * This method creates the open tag.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   tag the name of the tag were creating.  
    * @return  the new tag.  
    * @since   JDK1.3  
    */  
   //---------------------------------------------------------------------------  
   private String openTag(String tag){  
   //---------------------------------------------------------------------------  
       return tabs(currDepth++)+"<"+tag+">"+cr;
   }  
  
   /**  
    * This method creates an open and a close tag at the same time.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   tag is the tag name.  
    * @param   value is the tags attribute.  
    * @return  the new tag.  
    * @since   JDK1.3  
    */  
   //---------------------------------------------------------------------------  
   private String openCloseTag(String tag, String value){  
   //---------------------------------------------------------------------------  
       return tabs(currDepth)+"<"+tag+">"+value+"</"+tag+">"+cr;  
   }  
  
   /**  
    * This method adds the close tag to the buffer which holds the   
    * file were creating.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   tag the name of the tag were closing.  
    * @return  none  
    * @since   JDK1.3  
    */  
   //---------------------------------------------------------------------------  
   private void closeIt(String tag){  
   //---------------------------------------------------------------------------  
      buffer.append(closeTag("tag"));  
   }  
  
   /**  
    * This method creates a close tag.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   tag is the name of the tag were closing.  
    * @return  none  
    * @since   JDK1.3  
    */  
   //---------------------------------------------------------------------------  
   private String closeTag(String tag){  
   //---------------------------------------------------------------------------
       return tabs(--currDepth)+"</"+tag+">"+cr;  
   }  
  
   /**  
    * This method returns the number of tabs specified as the parameter.  
    * @author  Benjamin Lindahl
    * @version 1.0  
    * @param   numTabs is the number of tabs.   
    * @return  string of number of tabs specified.  
    * @since   JDK1.3  
    */  
   //---------------------------------------------------------------------------  
   private String tabs(int numTabs){  
   //---------------------------------------------------------------------------  
       String rvalue = "";

       for(int i = 0; i <= numTabs; i++)
          rvalue += tab;
       
       return rvalue;
   }
  
   /**  
    * This method closes the taglib tag.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //---------------------------------------------------------------------------  
   public void cleanUp(){  
   //---------------------------------------------------------------------------  
      buffer.append(closeTag("taglib"));  
   }  
  
   /**  
    * This method returns the .TLD file we just created.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //---------------------------------------------------------------------------  
   public StringBuffer getTagLib(){  
   //---------------------------------------------------------------------------  
      return buffer;  
   }   
  
   /**  
    * This method returns the .TLD file we just created.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //---------------------------------------------------------------------------  
   public StringBuffer getExtraInfo(){  
   //---------------------------------------------------------------------------  
      return extraInfo;  
   }   
  
   // createExceptionTag() ENDS  
  
   //***************************************************************************  
   //                      Class variable section  
   //***************************************************************************  
   private boolean brookgroupBuild=false;  
   private String packageName="";  
   private String shortName="";  
   private StringBuffer header=new StringBuffer();  
   private static StringBuffer buffer=new StringBuffer();  
   private StringBuffer extraInfo=new StringBuffer();
   private static int currDepth = -1; // openTag pre-increments... we want the first openTag to print 0 tabs, so we start here with -1 (-1 + 1 == 0)
   private String cr="\n";  
   private String tab="   ";
   
   // The following is to aide in the transition from Java 1.1 to Java 1.2
   // Change jspversion to 1.2, and compatibility will ensue (all variables will automatically be changed to the correct 1.2 values... don't change any variables here but jspversion)
   private String jspversion="1.1";
   // DO NOT CHANGE BELOW
   private String tlibversion="1.0";
   private String dtdhref="http://java.sun.com/j2ee/dtds/web-jsptaglibrary_1_1.dtd";

   private String tlibversionText="tlibversion";
   private String jspversionText="jspversion";
   private String shortnameText="shortname";

   private String tagclassText="tagclass";
   private String teiclassText="teiclass";
   private String bodycontentText="bodycontent";
}// CreateTLD() ENDS  
