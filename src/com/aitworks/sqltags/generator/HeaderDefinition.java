/* $Id: HeaderDefinition.java,v 1.2 2002/03/15 14:23:47 solson Exp $
 * $Log: HeaderDefinition.java,v $
 * Revision 1.2  2002/03/15 14:23:47  solson
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
import java.sql.*;  
import java.util.*;  
import javax.servlet.*;  
import javax.servlet.jsp.*;  
import java.io.*;  
  
/**  
 * This class creates the header information for the method or class.  
 * @author  Booker Northington II  
 * @version 1.0  
 * @param   none  
 * @return  none  
 * @since   JDK1.3  
 */  
final class HeaderDefinition{  
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
  
   //---------------------------------------------------------------------------  
   private void setTab(int howMany){  
   //---------------------------------------------------------------------------  
      if(howMany==1)  
	 tab="   ";  
      else if(howMany==2)  
	 tab="      ";  
      else if(howMany==3)  
	 tab="         ";  
      else if(howMany==4)  
	 tab="            ";  
      else if(howMany==5)  
	 tab="               ";  
      else if(howMany==6)  
	 tab="                  ";  
      else if(howMany==7)  
	 tab="                     ";  
      else if(howMany==8)  
	 tab="                        ";  
      else   
	 tab="";  
   }  
  
   //---------------------------------------------------------------------------  
   private void formatLine(StringBuffer message, int lineSize, int spacer){  
   //---------------------------------------------------------------------------  
      StringBuffer lineBuffer=new StringBuffer();  
      int messageSize=message.length();  
      int index=0;  
      int count=buffer.length();  
      boolean dataAppended=false;  
      setTab(spacer);  
  
      if(!creatingParameters)  
         lineBuffer.append(tab+" * ");  
  
      for(;index<messageSize;index++,count++){  
	 char data=message.charAt(index);  
	 lineBuffer.append(data);  
	   
	 if(count>=lineSize&&data==' '){  
	    buffer.append(lineBuffer.toString()+cr);  
	    lineBuffer.setLength(0);  
	    lineBuffer.append(tab+" * ");  
            int index2=lineBuffer.length()+1;  
  
            if(atTypeSize>0){  
	       for(int index1=0;index1<index2;index1++)  
		  lineBuffer.append(" ");  
  
	       count=lineBuffer.length();  
            }  
            else  
	       count=0;  
  
            dataAppended=true;  
	 }  
         else  
           dataAppended=false;  
      }  
  
      if(lineBuffer.length()>80){  
         int lastSpace=lineBuffer.toString().lastIndexOf(" ");  
         int size=lineBuffer.length();  
         StringBuffer partOne=new StringBuffer(lineBuffer.toString());  
         partOne.setLength(lastSpace);  
         StringBuffer partTwo=new StringBuffer(cr+tab+" *");  
         partTwo.append(lineBuffer.substring(lastSpace,size));  
         lineBuffer.setLength(0);  
         lineBuffer.append(partOne.toString()+partTwo.toString());  
      }  
  
      if(!dataAppended)  
         buffer.append(lineBuffer.toString()+cr);  
   }//formatLine() ENDS  
  
   //---------------------------------------------------------------------------  
   public void createParameters(String type, String name, String atType,  
                                  String description, int spacer){  
   //---------------------------------------------------------------------------  
      atTypeSize=atType.length();  
      buffer.setLength(0);  
      setTab(spacer);  
      StringBuffer storage=new StringBuffer();  
      storage.append(tab+" * @"+atType+" "+name+" <code>");  
      storage.append(type+"</code> ");  
      storage.append(description);  
      int messageSize=storage.length();  
      creatingParameters=true;  
  
      if(messageSize<70)  
	 buffer.append(storage.toString()+cr);  
      else  
	 formatLine(storage,70,spacer);  
  
      creatingParameters=false;  
      messageBuffer.append(buffer.toString());  
   }//createParameters() ENDS  
  
  
   //---------------------------------------------------------------------------  
   public void createParameters(String[] type, String[] name, int spacer,  
                                String[] description){  
   //---------------------------------------------------------------------------  
      buffer.setLength(0);  
      setTab(spacer);  
      int total=type.length;  
  
      for(int index=0;index<total;index++){  
         buffer.append(tab+" * @param "+name[index]+"<code>");  
         buffer.append(type[index]+"</code> ");  
         StringBuffer message=new StringBuffer(description[index]);  
         int messageSize=message.length();  
  
         if(messageSize<35)  
            buffer.append(message.toString()+cr);  
         else  
            formatLine(message,35,spacer);  
      }  
  
      messageBuffer.append(buffer.toString());  
   }//createParameters() ENDS  
  
   //---------------------------------------------------------------------------  
   public HeaderDefinition(String author, String version, String jdk,  
                           String name,   String description, int spacer){  
   //---------------------------------------------------------------------------  
      messageBuffer.setLength(0);  
      buffer.setLength(0);  
      formatLine(new StringBuffer(description),70,spacer);  
      String formattedDescription=buffer.toString();  
      buffer.setLength(0);  
      setTab(spacer);  
  
      buffer.append(tab+"/**"+cr);  
      buffer.append(tab+" * <code>"+name+"</code>"+cr);  
      buffer.append(tab+" * <p>"+cr);  
      buffer.append(formattedDescription);  
      buffer.append(tab+" * </p>"+cr);  
      buffer.append(tab+" * @author  "+author+cr);  
      buffer.append(tab+" * @version "+version+cr);  
      buffer.append(tab+" * @since   "+jdk+cr);  
      messageBuffer.append(buffer.toString());  
   }//HeaderDefinition() ENDS  
  
   //---------------------------------------------------------------------------  
   public String getHeader(){  
   //---------------------------------------------------------------------------  
      messageBuffer.append(tab+" */"+cr);  
      //messageBuffer.append(this.spacer);  
      return messageBuffer.toString();  
   }// getHeader() ENDS  
  
   //---------------------------------------------------------------------------  
   public static void main(String[] args){  
   //---------------------------------------------------------------------------  
      HeaderDefinition header=new HeaderDefinition("Booker Northington II","1.0","1.3","HeaderMethod","This is a very long line that should test the capabilities of what we are doing here and let me know if everything is going to work out fine.", 1);  
      header.createParameters("String[]","charArray","param","This array holds the values which will determine which button to display",1);  
  
      header.createParameters("none","none","return","none.",1);  
      header.createParameters("none","none","param","none.",1);  
  
      System.out.println(header.getHeader());  
   }// getHeader() ENDS  
  
   //***************************************************************************  
   //                      Class variable section  
   //***************************************************************************  
   private StringBuffer buffer=new StringBuffer();  
   private boolean creatingParameters=false;  
   private StringBuffer messageBuffer=new StringBuffer();  
   private String tab="   ";  
   private String cr="\n";  
   private int atTypeSize=0;  
   private String spacer=tab+"//---------------------------------------------------------------------------"+cr;  
}//HeaderDefinition() ENDS  
