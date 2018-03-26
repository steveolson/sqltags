/* $Id: ExtraInfo.java,v 1.9 2002/08/12 16:48:23 jpoon Exp $
 * $Log: ExtraInfo.java,v $
 * Revision 1.9  2002/08/12 16:48:23  jpoon
 * change getTagConnection to getConnection
 *
 * Revision 1.8  2002/05/23 14:54:26  solson
 * removed all references to caching implementation
 *
 * Revision 1.7  2002/03/15 14:23:46  solson
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
// import javax.net.ssl.*;  
// import com.sun.net.ssl.*;  
  
/**  
 * This class is used to create the TagExtraInfor class   
 * which is associated with each table.  
 * @author  Booker Northington II  
 * @version 1.0  
 * @param   none  
 * @return  none  
 * @since   JDK1.3  
 */  
final class ExtraInfo{  
   public ExtraInfo(String tableName, String packageName){  
      this.tableName=tableName;  
      this.packageName=packageName;  
      extraInfoFile.append(setClassHeader());  
      extraInfoFile.append(setDefaultConstructor());  
      extraInfoFile.append(setMethodHeader());  
   }  
   //***************************************************************************  
   //Finalize Method  
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
  
   public StringBuffer createTagFile(Hashtable hash){  
      this.hash=hash;  
      tagFile.setLength(0);  
      tagFile.append("package "+packageName+";"+cr);  
      tagFile.append("import com.aitworks.sqltags.utilities.*;\n");  
      tagFile.append("import java.io.*;"+cr);  
      tagFile.append("import java.sql.*;"+cr);  
      tagFile.append("import com.aitworks.sqltags.jsptags.*;"+cr);  
      tagFile.append("import javax.servlet.jsp.tagext.*;"+cr+cr);  
header=new HeaderDefinition("Booker Northington II","1.0","1.3",  
tableName+"_TAG","This is our tag class. It gives the jsp the ablity to work with the "+tableName+" table. You also have full DML acces to "+tableName+".", 0);  
header.createParameters("none","none","param","none.",0);  
header.createParameters("none","none","return","none.",0);  
tagFile.append(header.getHeader());  
      tagFile.append("public class "+tableName);  
      tagFile.append("_TAG extends "+tableName+"{"+cr);  
  
      tagFile.append(tab+"private boolean connectionValid=true;"+cr);  
      tagFile.append(tab+"protected String id=\"\";"+cr);  
//      tagFile.append(tab+"protected String where=\"\";"+cr);  
//tagFile.append(tab+"protected String depth=\"1\";"+cr);  
//      tagFile.append(tab+"protected boolean hasFetch=false;"+cr);  
      tagFile.append(tab+"protected Object resultBean=null;"+cr);  
      tagFile.append(tab+"protected boolean load=false;"+cr);  
//tagFile.append(tab+"protected String properties=\"false\";"+cr+cr);  
  
      //now create the doAfterBody() method.  
header=new HeaderDefinition("Booker Northington II","1.0","1.3",  
"doAfterBody","This method is executed after the body has been evaluated. If SKIP_BODY is returned or the tags body is empty, this method is not called. It is invoked after the body has been evaluated and is initially invoked by doStartTag.", 1);  
header.createParameters("none","none","param","none.",1);  
header.createParameters("int","value","return","Indicates whether to continue evaluating the body.",1);  
tagFile.append(header.getHeader());  
      tagFile.append(spacer);  
      tagFile.append(tab+"public int doAfterBody(){"+cr);  
      tagFile.append(spacer);  
      tagFile.append(tab2+"int returnCode=SKIP_BODY;"+cr);  
      tagFile.append(tab2+"SQLTagsHandler sqlTagHandler=getSQLTagsHandler();"+cr+cr);  
      tagFile.append(tab2+"if(!sqlTagHandler.preAfterBody(this))"+cr);  
      tagFile.append(tab3+"returnCode=SKIP_BODY;"+cr);  
      tagFile.append(tab2+"else if(getWhere().equals(\"\")||hasFetch)"+cr);  
      tagFile.append(tab3+"returnCode=SKIP_BODY;"+cr);  
      tagFile.append(tab2+"else if(fetch())"+cr);  
      tagFile.append(tab3+"returnCode=EVAL_BODY_TAG;"+cr);  
      tagFile.append(tab2+"else"+cr);  
      tagFile.append(tab2+"setLastRecord(true);"+cr+cr);  
      tagFile.append(tab2+"if(!sqlTagHandler.postAfterBody(this))"+cr);  
      tagFile.append(tab3+"returnCode=SKIP_BODY;"+cr+cr);  
      tagFile.append(tab2+"return returnCode;"+cr);  
      tagFile.append(tab+"}// doAfterBody() ENDS"+cr+cr);  
  
      // now create the doStartTag() method.  
header=new HeaderDefinition("Booker Northington II","1.0","1.3",  
"doStartTag","This method is called when the start tag of the jsp is encountered.  We make the assumptin that all of your mutators have been set prior to entering this method. The body of the tag has not been processed you this method is invoked.", 1);  
header.createParameters("none","none","param","none.",1);  
header.createParameters("int","value","return","Indicates whether to prosses the body.",1);  
tagFile.append(header.getHeader());  
      tagFile.append(spacer);  
      tagFile.append(tab+"public int doStartTag(){"+cr);  
      tagFile.append(spacer);  
  
      tagFile.append(tab2+"int returnCode=SKIP_BODY;"+cr); 
      tagFile.append(tab2+"Connection connection=null;"+cr);
      tagFile.append(tab2+"SQLTagsHandler sqlTagHandler=getSQLTagsHandler();"+cr+cr);  
      tagFile.append(tab2+"if(!sqlTagHandler.preStart(this))"+cr);  
      tagFile.append(tab3+"returnCode=SKIP_BODY;"+cr);  
      tagFile.append(tab2+"else{"+cr);  
  
      tagFile.append(tab3+"pageContext.setAttribute(getId(),this);"+cr);  
      tagFile.append(tab3+"ConnectionTag connectionObject=(ConnectionTag)"+cr);  
      tagFile.append(tab4+"findAncestorWithClass(this,ConnectionTag.class);"+cr);  
      tagFile.append(tab3+"if(connectionObject!=null)"+cr);
      tagFile.append(tab4+"connection=connectionObject.getConnection();"+cr);  
      tagFile.append(tab3+"else {"+cr);
      tagFile.append(tab4+"SimpleConnectionTag simpleConnectionTag=(SimpleConnectionTag)findAncestorWithClass(this,SimpleConnectionTag.class);"+cr);
      tagFile.append(tab4+"if(simpleConnectionTag!=null)"+cr);
      tagFile.append(tab5+"connection=simpleConnectionTag.getConnection();"+cr);
      tagFile.append(tab3+"}"+cr);
      tagFile.append(cr+tab3+"if(connection==null){"+cr);  
      tagFile.append(tab4+"connectionValid=false;"+cr);  
      tagFile.append(tab4+"log(\""+tableName+  
         "_TAG.doStartTag(): no database connection available.\");"+cr);  
      tagFile.append(tab4+"returnCode=SKIP_BODY;"+cr);  
      tagFile.append(tab3+"}"+cr);  
      tagFile.append(tab3+"else{"+cr);  
      tagFile.append(tab4+"setConnectionObject(connection);"+cr);  
      tagFile.append(tab4+"setFirstRecord(true);"+cr);  
      tagFile.append(tab4+"setLastRecord(false);"+cr+cr);  
      tagFile.append(tab4+"if(properties.toLowerCase().");  
      tagFile.append("equals(\"true\"))"+cr+cr);  
      tagFile.append(tab5+"setRequestProperties();"+cr+cr);  
      tagFile.append(tab4+"if(!parentName.equals(\"\")&&!foreignKey.equals(\"\")){"+cr);  
      tagFile.append(tab5+"Object sqlTag=null;"+cr);
      tagFile.append(tab5+"sqlTag=(Object)pageContext.getAttribute(getParentName() );"+cr);
      tagFile.append(tab5+"Utilities util = new Utilities();"+cr);
      tagFile.append(tab5+"setWhere( util.callFKWhere(sqlTag,getForeignKey() ) + \" \" +getWhere() );"+cr);
      tagFile.append(tab4+"}"+cr);
      /**
      tagFile.append(tab5+"String parentName=getParentName();"+cr);  
      tagFile.append(tab5+"String foreignKey=getForeignKey();"+cr);  
//tagFile.append(tab5+"String parentMethod=\"\";"+cr);  
      tagFile.append(tab5+"String whereMethod=\"\";"+cr);  
      tagFile.append(tab5+"Object sqlTag=null;"+cr);  
      tagFile.append(tab5+"Utilities dbUtil=new Utilities();"+cr);  
//tagFile.append(tab5+"sqlTag=(Object)pageContext.getAttribute(getId());"+cr);  
      tagFile.append(tab5+"sqlTag=(Object)pageContext.getAttribute(parentName);"+cr);  
//tagFile.append(tab5+"parentMethod=\"get\"+foreignKey+\"_PARENT\";"+cr);  
      tagFile.append(tab5+"whereMethod=\"get\"+foreignKey+\"_WHERE\";"+cr);  
//tagFile.append(tab5+"Object object=dbUtil.invokeMethod(sqlTag,parentMethod.toString(),null);"+cr);  
//tagFile.append(tab5+"object=dbUtil.invokeMethod(object,whereMethod,null);"+cr);  
      tagFile.append(tab5+"Object object=dbUtil.invokeMethod(sqlTag,whereMethod,null);"+cr);  
      tagFile.append(tab5+"setWhere((String)object+\" \"+getWhere());"+cr);  
      tagFile.append(tab4+"}"+cr+cr);  
       */
      // tagFile.append(tab4+"if(getCaching().equals(\"true\"))"+cr);  
      // tagFile.append(tab5+"setBaseTableCacheItemQuery();"+cr+cr);  
      /*
      tagFile.append(tab4+"if(operation.toLowerCase().equals(\"select\")||"+cr);  
      tagFile.append(tab5+"buttonName.toLowerCase().equals(\"select\"))"+cr);  
      tagFile.append(tab6+"initialize(1);"+cr);  
      tagFile.append(tab4+"else ");  
      tagFile.append("if(operation.toLowerCase().equals(\"insert\")||"+cr);  
      tagFile.append(tab5+"buttonName.toLowerCase().equals(\"insert\"))"+cr);  
      tagFile.append(tab6+"insert();"+cr);  
      tagFile.append(tab4+"else ");  
      tagFile.append("if(operation.toLowerCase().equals(\"update\")||"+cr);  
      tagFile.append(tab5+"buttonName.toLowerCase().equals(\"update\"))"+cr);  
      tagFile.append(tab6+"update();"+cr);  
      tagFile.append(tab4+"else ");  
      tagFile.append("if(operation.toLowerCase().equals(\"delete\")||"+cr);  
      tagFile.append(tab5+"buttonName.toLowerCase().equals(\"delete\"))"+cr);  
      tagFile.append(tab6+"delete();"+cr+cr);  
       */
      tagFile.append(tab4+"if(!doArrayOperations()){"+cr);
      tagFile.append(tab5+"sqlTagHandler.onError(this);"+cr);
      tagFile.append(tab5+"return EVAL_BODY_TAG;"+cr);
      tagFile.append(tab4+"}"+cr);
  
      tagFile.append(tab4+"if(getWhere().equals(\"\"))"+cr);  
      tagFile.append(tab5+"returnCode=EVAL_BODY_TAG;"+cr);  
      tagFile.append(tab4+"else if(!select(getWhere()))"+cr);  
      tagFile.append(tab5+"returnCode=SKIP_BODY;"+cr);  
      tagFile.append(tab4+"else if(fetch())"+cr);  
      tagFile.append(tab5+"returnCode=EVAL_BODY_TAG;"+cr);  
      tagFile.append(tab3+"}"+cr);  
  
      tagFile.append(tab2+"}"+cr+cr);  
      tagFile.append(tab2+"if(!sqlTagHandler.postStart(this))"+cr);  
      tagFile.append(tab3+"returnCode=SKIP_BODY;"+cr+cr);  
      tagFile.append(tab2+"return returnCode;"+cr);  
      tagFile.append(tab+"}// doStart() ENDS"+cr+cr);  
  
      // now create the doEndTag() method.  
header=new HeaderDefinition("Booker Northington II","1.0","1.3",  
"doEndTag","This method is called when the end tag is encountered. Any post processing can be acomplished here.", 1);  
header.createParameters("none","none","param","none.",1);  
header.createParameters("int","value","return","flag indicating your done.",1);  
tagFile.append(header.getHeader());  
      tagFile.append(spacer);  
      tagFile.append(tab+"public int doEndTag(){"+cr);  
      tagFile.append(spacer);  
      tagFile.append(tab2+"int returnCode=SKIP_BODY;"+cr);  
      tagFile.append(tab2+"SQLTagsHandler sqlTagHandler=getSQLTagsHandler();"+cr+cr);  
      tagFile.append(tab2+"if(!sqlTagHandler.preEnd(this))"+cr);  
tagFile.append(tab3+"returnCode=SKIP_BODY;"+cr);  
tagFile.append(tab2+"else{"+cr);  
  
  
  
      tagFile.append(tab3+"try{"+cr);  
  
      tagFile.append(tab4+"if(!connectionValid){"+cr);  
      tagFile.append(tab5+"connectionValid=true;"+cr);  
      tagFile.append(tab5+"log(\""+tableName+  
	 "_TAG.doEndTag(): no database connection available.\");"+cr);  
      tagFile.append(tab4+"}"+cr);  
      tagFile.append(tab4+"else if(bodyContent!=null)"+cr);  
      tagFile.append(tab5+"bodyContent.writeOut(bodyContent.getEnclosingWriter());"+cr);  
      tagFile.append(tab3+"}"+cr+tab3+"catch(IOException exception){"+cr);  
  
      tagFile.append(tab4+"log(\""+tableName+"_TAG.doEndTag(): \"+exception);"+cr);  
  
      tagFile.append(tab3+"}"+cr+cr);  
      tagFile.append(tab3+"pageContext.removeAttribute(getId());"+cr);  
      tagFile.append(tab2+"}"+cr+cr);  
      tagFile.append(tab2+"if(!sqlTagHandler.postEnd(this))"+cr);  
      tagFile.append(tab3+"returnCode=SKIP_BODY;"+cr);  
      tagFile.append(tab2+"else"+cr);  
      tagFile.append(tab3+"returnCode=EVAL_PAGE;"+cr+cr);  
      tagFile.append(tab2+"return returnCode;"+cr);  
      tagFile.append(tab+"}// doEndTag() ENDS"+cr+cr);  
  
      // getResultBean() Method  
header=new HeaderDefinition("Booker Northington II","1.0","1.3",  
"getResultBean","This method returns the results obtained from our reflection call.", 1);  
header.createParameters("none","none","param","none.",1);  
header.createParameters("Object","resultBean","return","The results from our reflection call.",1);  
tagFile.append(header.getHeader());  
      tagFile.append(spacer);  
      tagFile.append(tab+"public Object getResultBean(){"+cr);  
      tagFile.append(spacer);  
      tagFile.append(tab2+"return resultBean;"+cr);  
      tagFile.append(tab+"}// getResultBean() ENDS"+cr+cr);  
  
  
/**  
      // create access methods.  
header=new HeaderDefinition("Booker Northington II","1.0","1.3",  
"getID","This method returns the scripting variable associated with the "+tableName+" table.", 1);  
header.createParameters("none","none","param","none.",1);  
header.createParameters("String","id","return","The scripting variable name.",1);  
tagFile.append(header.getHeader());  
      tagFile.append(spacer);  
      tagFile.append(tab+"public String getId(){"+cr);  
      tagFile.append(spacer);  
      tagFile.append(tab2+"return id;"+cr);  
      tagFile.append(tab+"}// getId() ENDS"+cr+cr);  
   
      //create getWhere() method  
header=new HeaderDefinition("Booker Northington II","1.0","1.3",  
"getWhere","This method returns the where clause associated with the "+tableName+" table.", 1);  
header.createParameters("none","none","param","none.",1);  
header.createParameters("String","where","return","The current where clause.",1);  
      tagFile.append(header.getHeader());  
      tagFile.append(spacer);  
      tagFile.append(tab+"public String getWhere(){"+cr);  
      tagFile.append(spacer);  
      tagFile.append(tab2+"return where;"+cr);  
      tagFile.append(tab+"}// getWhere() ENDS"+cr+cr);  
  
   
  
      // setWhere() Method  
header=new HeaderDefinition("Booker Northington II","1.0","1.3",  
"setWhere","This method sets the where clause for the "+tableName+" table.", 1);  
header.createParameters("String","where","param","The new where clause.",1);  
header.createParameters("none","none","return","none.",1);  
      tagFile.append(header.getHeader());  
      tagFile.append(spacer);  
      tagFile.append(tab+"public void setWhere(String where){"+cr);  
      tagFile.append(spacer);  
      tagFile.append(tab2+"this.where=where;"+cr);  
      tagFile.append(tab2+"pageContext.setAttribute(\"where\",where);"+cr);  
      tagFile.append(tab+"}// setWhere() ENDS"+cr+cr);  
      tagFile.append(setSelectWhere());  
  
header=new HeaderDefinition("Booker Northington II","1.0","1.3",  
"setID","This method sets the name of the scripting variable associated with this tag.", 1);  
header.createParameters("String","id","param","The name of the scripting variable.",1);  
header.createParameters("none","none","return","none.",1);  
tagFile.append(header.getHeader());  
      tagFile.append(spacer);  
      tagFile.append(tab+"public void setId(String id){"+cr);  
      tagFile.append(spacer);  
      tagFile.append(tab2+"this.id=id;"+cr);  
      tagFile.append(tab+"}// setId() ENDS"+cr+cr);  
  
      // now create the setProperties() method.  
header=new HeaderDefinition("Booker Northington II","1.0","1.3",  
"setProperties","This method set the properties object. If the value of this variable is true, all the parameters currently within the request object are read and if any values read from the request object are within the "+tableName+" table, "+tableName+" will initialize itself to those values.", 1);  
header.createParameters("String","properties","param","if true, the "+tableName+" initializes itself from the request object.",1);  
header.createParameters(tableName,"table","see","The table initialized.",1);  
header.createParameters("none","none","return","none.",1);  
tagFile.append(header.getHeader());  
      tagFile.append(spacer);  
      tagFile.append(tab+"public void setProperties(String ");  
      tagFile.append("properties){"+cr);  
      tagFile.append(spacer);  
      tagFile.append(tab2+"this.properties=properties;"+cr);  
      tagFile.append(tab+"}// setProperties() ENDS"+cr+cr);  
  
      // now create the getProperties() method.  
header=new HeaderDefinition("Booker Northington II","1.0","1.3",  
"getProperties","This method returns the value of the properties field.", 1);  
header.createParameters("none","none","param","none.",1);  
header.createParameters("String","properties","return","true, if initializing off the request object..",1);  
tagFile.append(header.getHeader());  
      tagFile.append(spacer);  
      tagFile.append(tab+"public String getProperties(){"+cr);  
      tagFile.append(spacer);  
      tagFile.append(tab2+"return properties;"+cr);  
      tagFile.append(tab+"}// getProperties() ENDS"+cr+cr);  
  
      // create the setHasFetch() method.  
header=new HeaderDefinition("Booker Northington II","1.0","1.3",  
"setHasFetch","This method sets the value of the hasFetch property. If set to true, a fetch has already been done.", 1);  
header.createParameters("String","value","param","The new value of the hasFetch property.",1);  
header.createParameters("none","none","return","none.",1);  
tagFile.append(header.getHeader());  
      tagFile.append(spacer);  
      tagFile.append(tab+"public void setHasFetch(String ");  
      tagFile.append("value){"+cr);  
      tagFile.append(spacer);  
      tagFile.append(tab2+"hasFetch=false;"+cr+cr);  
      tagFile.append(tab2+"if(value.equals(\"true\"))"+cr);  
      tagFile.append(tab3+"hasFetch=true;"+cr);  
      tagFile.append(tab+"}// setHasFetch() ENDS"+cr+cr);  
  
      // create the isFetch()() method.  
header=new HeaderDefinition("Booker Northington II","1.0","1.3",  
"isFetch()","This method returns the value of the hasFetch property.", 1);  
header.createParameters("none","none","param","none.",1);  
header.createParameters("String","hasFetch","return","true, if a fetch has already been done.",1);  
tagFile.append(header.getHeader());  
      tagFile.append(spacer);  
      tagFile.append(tab+"public boolean isFetch(){"+cr);  
      tagFile.append(spacer);  
      tagFile.append(tab2+"return hasFetch;"+cr);  
      tagFile.append(tab+"}// isFetch() ENDS"+cr+cr);  
  
      // setDepth() Method  
header=new HeaderDefinition("Booker Northington II","1.0","1.3",  
"setDepth","This method sets the depth used when construction objects. For instance, if the depth is set to '2', the constructor, will go out 2 layers when constructing itself and its children.", 1);  
header.createParameters("String","value","param","The new depth.",1);  
header.createParameters("none","none","return","none.",1);  
tagFile.append(header.getHeader());  
      tagFile.append(spacer);  
      tagFile.append(tab+"public void setDepth(String value){"+cr);  
      tagFile.append(spacer);  
      tagFile.append(tab2+"setDepth((new Integer(value)).intValue());"+cr);  
      tagFile.append(tab+"}// setDepth() ENDS"+cr+cr);  
  
      // create writeErrorToBrowser method  
header=new HeaderDefinition("Booker Northington II","1.0","1.3",  
"writeErrorToBrowser","This method is used to send error messages to the browser.", 1);  
header.createParameters("Object","object","param","The object which generated the exception.",1);  
header.createParameters("Exception","exception","param","The exception generated.",1);  
header.createParameters("none","none","return","none.",1);  
tagFile.append(header.getHeader());  
      tagFile.append(spacer);  
      tagFile.append(tab+"public void writeErrorToBrowser(Object object, Exception exception){"+cr);  
      tagFile.append(spacer);  
      tagFile.append(tab2+"try{"+cr);  
      tagFile.append(tab3+"pageContext.getOut().println(\"<!\"+object+\": \"+exception+\">\");"+cr);  
      tagFile.append(tab2+"}"+cr+tab+"catch(IOException ioException){}"+cr);  
      tagFile.append(tab+"}// writeErrorToBrowser() ENDS"+cr+cr);  
**/  
      tagFile.append(setSelectWhere());  
      tagFile.append("}//class "+tableName+"_TAG ENDS");  
      return tagFile;  
   }  
  
   public StringBuffer getExtraTagInfo(){return extraInfoFile;}  
  
   private String setClassHeader(){  
      buffer.setLength(0);  
      buffer.append("package "+packageName+";"+cr);  
      buffer.append("import java.io.*;"+cr);  
      buffer.append("import javax.servlet.jsp.tagext.*;"+cr);  
      buffer.append("import com.aitworks.sqltags.jsptags.*;"+cr+cr);  
header=new HeaderDefinition("Booker Northington II","1.0","1.3",  
tableName+"_TagExtraInfo","This class is used to create the scripting variables associated with the tags.", 0);  
header.createParameters("none","none","param","none.",0);  
header.createParameters("BodyTagSupport","BodyTagSupport","see","For more information.",0);  
header.createParameters("none","none","return","none.",0);  
buffer.append(header.getHeader());  
      buffer.append(spacer);  
      buffer.append("public class "+tableName);  
      buffer.append("_TagExtraInfo extends TagExtraInfo{"+cr);  
      buffer.append(spacer);  
      return buffer.toString();  
   }  
  
   private String setDefaultConstructor(){  
      buffer.setLength(0);  
header=new HeaderDefinition("Booker Northington II","1.0","1.3",  
tableName+"_TagExtraInfo","This is the class constructor. There really is no need to actually call this directly.", 1);  
header.createParameters("none","none","param","none.",1);  
header.createParameters("none","none","return","none.",1);  
buffer.append(header.getHeader());  
      buffer.append(spacer);  
      buffer.append(tab+"public "+tableName);  
      buffer.append("_TagExtraInfo(){"+cr);  
      buffer.append(spacer);  
      buffer.append(tab+"}"+cr+cr);  
      return buffer.toString();  
   }  
  
   private String setMethodHeader(){  
      buffer.setLength(0);  
header=new HeaderDefinition("Booker Northington II","1.0","1.3",  
"getVariableInfo","This method makes the scripting variables available to the jsp.", 1);  
header.createParameters("TagData","value","param","The data associated with the tag.",1);  
header.createParameters("TagExtraInfo","TagExtraInfo","see","For more information.",1);  
header.createParameters("VariableInfo","VariableInfo","see","For more information.",1);  
header.createParameters("TagData","TagData","see","For more information.",1);  
header.createParameters("VariableInfo[]","variableInfo","return","An array of variableInfo.",1);  
buffer.append(header.getHeader());  
      buffer.append(spacer);  
      buffer.append(tab+"public VariableInfo[] getVariableInfo");  
      buffer.append("(TagData value) {"+cr);  
      buffer.append(spacer);  
  
      buffer.append(tab2+"String objectType=value.getAttributeString(\"handlerClass\");"+cr);  
      buffer.append(tab2+"String handlerType=value.getAttributeString(\"handlerID\");"+cr+cr);  
  
      buffer.append(tab2+"if(objectType!=null&&handlerType!=null){"+cr);  
      buffer.append(tab3+"return new VariableInfo[]{"+cr);  
      buffer.append(tab4+"new VariableInfo(value.getId(),\""+tableName);  
      buffer.append("_TAG\",true,VariableInfo.NESTED),"+cr);  
      buffer.append(tab4+"new VariableInfo(handlerType,objectType,true,VariableInfo.NESTED)"+cr);  
      buffer.append(tab3+"};"+cr);  
      buffer.append(tab2+"}"+cr);  
      buffer.append(tab2+"else{"+cr);  
      buffer.append(tab3+"return new VariableInfo[]{"+cr);  
      buffer.append(tab4+"new VariableInfo(value.getId(),\""+tableName);  
      buffer.append("_TAG\",");  
      buffer.append("true,VariableInfo.NESTED)"+cr);  
      buffer.append(tab3+"};"+cr);  
      buffer.append(tab2+"}"+cr);  
      buffer.append(tab+"}"+cr);  
      buffer.append("}"+cr);  
      return buffer.toString();  
   }  
  
  
   public String setSelectWhere(){  
      StringBuffer setRequest=new StringBuffer();  
      buffer.setLength(0);  
      String name="";  
  
      for(Enumeration enum=hash.keys();enum.hasMoreElements();){  
         name=(String)enum.nextElement();   
  
         //now create the setSelect methods.  
	 header=new HeaderDefinition("Booker Northington II","1.0","1.3",  
	 "set"+name+"_SELECT","This method allows you to set the select format for "+name+".", 1);  
	 header.createParameters("String","selectFormat","param","The new format for the select clause.",1);  
	 header.createParameters("none","none","return","none.",1);  
	 buffer.append(header.getHeader());  
         buffer.append(spacer);  
         buffer.append(tab+"public void set"+name+"_SELECT(String selectFormat){"+cr);  
         buffer.append(spacer);  
         buffer.append(tab2+"setColumnProperty(\"selectFormat\",\""+name+"\",selectFormat);"+cr);  
         //buffer.append(tab2+"setSelectHash(\""+name+"\",selectFormat);"+cr);  
         buffer.append(tab+"}//set"+name+"_SELECT() ENDS"+cr+cr);  
  
         //now create the setWhere methods.  
header=new HeaderDefinition("Booker Northington II","1.0","1.3",  
"set"+name+"_WHERE","This method allows you to set the where format for "+name+".", 1);  
header.createParameters("String","whereFormat","param","The new format for the where clause.",1);  
header.createParameters("none","none","return","none.",1);  
buffer.append(header.getHeader());  
         buffer.append(spacer);  
         buffer.append(tab+"public void set"+name+"_WHERE(String whereFormat){"+cr);  
         buffer.append(spacer);  
         buffer.append(tab2+"setColumnProperty(\"bind\",\""+name+"\",whereFormat);"+cr);  
         //buffer.append(tab2+"setSaveHash(\""+name+"\",whereFormat);"+cr);  
         buffer.append(tab+"}//set"+name+"_WHERE() ENDS"+cr+cr);  
  
         //now create the setFormat methods.  
header=new HeaderDefinition("Booker Northington II","1.0","1.3",  
"set"+name+"_FORMAT","This method sets the format for "+name+" within the "+tableName+" table.", 1);  
header.createParameters("String","format","param","The new format.",1);  
header.createParameters("none","none","return","none.",1);  
buffer.append(header.getHeader());  
         buffer.append(spacer);  
         buffer.append(tab+"public void set"+name+"_FORMAT(String format){"+cr);  
         buffer.append(spacer);  
         buffer.append(tab2+"setColumnProperty(\"selectFormat\",\""+name+"\",format);"+cr);  
         //buffer.append(tab2+"setSelectHash(\""+name+"\",format);"+cr);  
         buffer.append(tab+"}//set"+name+"_FORMAT() ENDS"+cr+cr);  
  
         //now create the setBind methods.  
	 header=new HeaderDefinition("Booker Northington II","1.0","1.3",  
	 "set"+name+"_BIND","This method sets the bind format for "+name+" within the "+tableName+" table.", 1);  
	 header.createParameters("String","bindFormat","param","The new bind format.",1);  
	 header.createParameters("none","none","return","none.",1);  
	 buffer.append(header.getHeader());  
         buffer.append(spacer);  
         buffer.append(tab+"public void set"+name+"_BIND(String bindFormat){"+cr);  
         buffer.append(spacer);  
         buffer.append(tab2+"setColumnProperty(\"bind\",\""+name+"\",bindFormat);"+cr);  
         //buffer.append(tab2+"setSaveHash(\""+name+"\",bindFormat);"+cr);  
         buffer.append(tab+"}//set"+name+"_BIND() ENDS"+cr+cr);  
  
         // now create items for setRequest  
	 // setRequest.append(tab2+"if((String)"+cr+tab3+"pageContext.getRequest().getParameter(\""+name+"\")!=null)"+cr);  
	 // setRequest.append(tab4+"set"+name+"((String)"+cr+tab6+"pageContext.getRequest().getParameter("+cr+tab7+"\""+name+"\")"+cr+tab4+");"+cr+cr);  
      }  
  /**
      // setRequestProperties() Method  
      header=new HeaderDefinition("Booker Northington II","1.0","1.3",  
      "setRequestProperties","This method initializes the columns within the "+tableName+" tables with the values contained within the request object.", 1);  
      header.createParameters("none","none","param","none.",1);  
      header.createParameters("none","none","return","none.",1);  
      buffer.append(header.getHeader());  
      buffer.append(spacer);  
      buffer.append(tab+"public void setRequestProperties(){"+cr);  
      buffer.append(spacer);  
      buffer.append(tab2+"if(getPaging().equals(\"true\")&&"+cr+tab3+"(String)pageContext.getRequest().getParameter(\"startRow\")!=null)"+cr);  
      buffer.append(tab4+"setStartRow((String)pageContext.getRequest().getParameter("+cr+tab5+"\"startRow\"));"+cr+cr);  
      buffer.append(setRequest.toString());  
  
      buffer.append(tab2+"setFKParent();"+cr);  
      buffer.append(tab+"}// setRequestProperties() ENDS"+cr+cr);  
   */
      return buffer.toString();
   }  
  
   //***************************************************************************  
   //                      Class variable section  
   //***************************************************************************  
   private String shortName="";  
   private StringBuffer extraInfoFile=new StringBuffer();  
   private StringBuffer buffer=new StringBuffer();  
   private StringBuffer childBuffer=new StringBuffer();  
   private HeaderDefinition header=null;  
   private StringBuffer tagFile=new StringBuffer();  
   private Hashtable hash;  
   private String tableName="";  
   private String packageName="";  
   private String tab="   ";  
   private String tab2="      ";  
   private String tab3="         ";  
   private String tab4="            ";  
   private String tab5="               ";  
   private String tab6="                  ";  
   private String tab7="                     ";  
   private String cr="\n";  
   private String spacer=tab+"//---------------------------------------------------------------------------"+cr;  
}//ExtraInfo() ENDS  
