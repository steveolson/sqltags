/* $Id: XMLToHTML.java,v 1.2 2002/03/15 14:33:30 solson Exp $
 * $Log: XMLToHTML.java,v $
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
import javax.servlet.*;  
import javax.servlet.http.*;  
  
/**  
 * The Class is a helper class for the OJP Project. Methods will be  
 * inserted as needed.  
 * @author  Booker Northington II  
 * @version 1.0  
 * @return  none  
 * @since   JDK1.3  
 */  
public class XMLToHTML{  
   private String cr="\n";  
   private String htmlFileName="";  
   private String tab="   ";  
   private String tab2="      ";  
   private String tab3="         ";  
   private StringBuffer xmlFile=new StringBuffer();  
   private String xmlFileName="";  
   private String xslFileName="";  
   private String xmlRoot="";  
   private Utilities dbUtil=new Utilities();  
  
   /**  
    * Default constructor.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   xmlFileName the xml file name  
    * @param   xslFileName the xsl file name  
    * @param   htmlFileName what were naming our file  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public XMLToHTML(String xmlFileName,String xslFileName,  
                    String htmlFileName, String xmlRoot){  
   //-------------------------------------------------------------------------  
      int xmlStart=xmlFileName.lastIndexOf('/');  
      int xslStart=xmlFileName.lastIndexOf('/');  
      this.xmlRoot=xmlRoot;   
      this.xmlFileName=xmlFileName.substring((xmlStart+1),xmlFileName.length());  
      this.xslFileName=xslFileName.substring((xslStart+1),xslFileName.length());  
      this.htmlFileName=htmlFileName;  
      createFile();  
  
      try{  
         dbUtil.writeFile(htmlFileName,xmlFile.toString());  
      }  
      catch(IOException exception){  
      }  
   }  
  
   /**  
    * Default constructor.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   private void createFile(){  
   //-------------------------------------------------------------------------  
      xmlFile.append("<html>"+cr);  
      xmlFile.append(tab+"<body>"+cr);  
      xmlFile.append(tab2+"<script language=\"javascript\">"+cr);  
      xmlFile.append(tab3+"var xml = new ActiveXObject(\"Microsoft.XMLDOM\")"+cr);  
      xmlFile.append(tab3+"xml.async = false"+cr);  
  
      if(!xmlRoot.equals(""))  
         xmlFile.append(tab3+"xml.load(\""+xmlRoot+"/"+xmlFileName+"\")"+cr);  
      else  
         xmlFile.append(tab3+"xml.load(\""+xmlFileName+"\")"+cr);  
  
      xmlFile.append(tab3+"var xsl = new ActiveXObject(\"Microsoft.XMLDOM\")"+cr);  
      xmlFile.append(tab3+"xsl.async = false"+cr);  
  
      if(!xmlRoot.equals(""))  
         xmlFile.append(tab3+"xsl.load(\""+xmlRoot+"/"+xslFileName+"\")"+cr);  
      else  
         xmlFile.append(tab3+"xsl.load(\""+xslFileName+"\")"+cr);  
  
      xmlFile.append(tab3+"document.write(xml.transformNode(xsl))"+cr);  
      xmlFile.append(tab2+"</script>"+cr);  
      xmlFile.append(tab+"</body>"+cr);  
      xmlFile.append("</html>"+cr);  
  
StringBuffer aspBuffer=new StringBuffer();  
aspBuffer.append("<%"+cr);  
aspBuffer.append(tab+"set xml = Server.CreateObject(\"Microsoft.XMLDOM\")"+cr);  
aspBuffer.append(tab+"xml.async = false"+cr);  
aspBuffer.append(tab+"xml.load(Server.MapPath(\""+xmlFileName+"\"))"+cr);  
aspBuffer.append(tab+"set xsl = Server.CreateObject(\"Microsoft.XMLDOM\")"+cr);  
aspBuffer.append(tab+"xsl.async = false"+cr);  
aspBuffer.append(tab+"xsl.load(Server.MapPath(\""+xslFileName+"\"))"+cr);  
aspBuffer.append(tab+"Response.Write(xml.transformNode(xsl))"+cr);  
aspBuffer.append("%>"+cr);  
try{  
dbUtil.writeFile("/tmp/test.asp",aspBuffer.toString());  
}  
catch(IOException exception){}  
   }  
}  
