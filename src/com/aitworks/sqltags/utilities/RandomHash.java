/* $Id: RandomHash.java,v 1.7 2002/07/17 19:23:58 solson Exp $
 * $Log: RandomHash.java,v $
 * Revision 1.7  2002/07/17 19:23:58  solson
 * cleaned up the Handler calls.  Modified calls to the handler class and fixed
 * parameters to class ... changed parameter from "Object" to "SQLTags"
 *
 * Revision 1.6  2002/03/19 19:14:01  booker
 * checking in sqltags. working on 2nd cut of ojp
 *
 * Revision 1.5  2002/03/15 14:33:30  solson
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
import com.aitworks.sqltags.utilities.Initialize; 
import java.io.BufferedWriter;  
import java.io.FileInputStream;  
import java.io.InputStream;  
import java.io.FileNotFoundException;  
import java.io.FileWriter;  
import java.io.IOException;  
import java.io.PrintWriter;  
import java.util.Enumeration;  
import java.util.Hashtable;  
import java.util.Properties;  
import java.util.Random;  
import javax.servlet.ServletContext;  
import javax.servlet.jsp.PageContext;
  
/**  
 * This class encrypts a properties file. It expects the file to  
 * be in the following format: key=value.  
 * @author  Booker Northington II  
 * @version 1.0  
 * @return  none  
 * @since   JDK1.3  
 */  
public class RandomHash{  
   /**  
    * Default constructor.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public RandomHash(){  
   //-------------------------------------------------------------------------  
   }// RandomHash() END  
  
   /**  
    * Class constructor  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   seedSize valid ranges 0-255   
    * @param   outputFile where to place the encrypted file  
    * @param   inputFile the input file where key=value format.  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public RandomHash(int seedSize,String outputFile,String inputFile){  
   //-------------------------------------------------------------------------  
      realSeed=calculateSeedSize(seedSize);;  
      this.outputFile=outputFile;  
      this.inputFile=inputFile;  
      Initialize init = new Initialize( inputFile );
      properties = init.getProperties();
      createHeader();  
      initializeKHA();  
      createRandomKHA();  
      writeFile();  
      readEncryptedFile(outputFile);  
   }// RandomHash() END  
  
   /**  
    * Class constructor.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   seedSize valid ranges 0-255   
    * @param   hashtable the table to be converted into an encrypted file.  
    * @param   outputFile Absolute path of a fully-qualified filename.  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public RandomHash(int seedSize,  
                     Hashtable hashtable,  
                     String outputFile){  
   //-------------------------------------------------------------------------  
      realSeed=calculateSeedSize(seedSize);;  
      this.outputFile=outputFile;  
      createHeader();  
      initializeKHA();  
      createRandomKHA(hashtable);  
      writeFile();  
   }// RandomHash() END  
  
   /**  
    * Class constructor. Takes an encrypted file and places its content into  
    * a hashtable.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   encryptedFile the file to be read.   
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public RandomHash(String encryptedFile){  
   //-------------------------------------------------------------------------  
      readEncryptedFile(encryptedFile);  
   }// RandomHash() END  
   
   /**  
    * Class constructor. Takes an encrypted file and places its content into  
    * a hashtable.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   encryptedFile the file to be read.   
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public RandomHash(String encryptedFile,ServletContext context){  
   //------------------------------------------------------------------------- 
      this.context=context;
      readEncryptedFile(encryptedFile);  
   }// RandomHash() END  
  
   //***************************************************************************  
   // Finalize Method  
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

   /**  
    * This method reads the parameter values from the encrypted file.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  decryptedHash a hashtable containing the parameter values.  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public  RandomHash(String filename,PageContext pageContext){  
   //-------------------------------------------------------------------------
       context=(ServletContext)pageContext.getServletContext();
       readEncryptedFile(filename);
   }   
   
   /**  
    * This method reads the parameter values from the encrypted file.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  decryptedHash a hashtable containing the parameter values.  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public Properties readEncryptedFile(String filename){  
   //------------------------------------------------------------------------- 
     properties=null;
     boolean DEBUG=false;
     InputStream inputStream=null;
     
     try{
         if(DEBUG)
            inputStream=(InputStream)new FileInputStream(filename+".x");
         else{
            if(context!=null)
                inputStream=context.getResourceAsStream(filename);
            else
                inputStream=Initialize.class.getResourceAsStream(filename);
         }
         
         if(utilities.isFileEncryptedStringPrep(filename,context))
            properties=readEncryptedStream(inputStream);  
         else{
            properties=new Properties();
            properties.load(inputStream);
            decryptedHash=properties;
         }
     }
     catch(IOException e){
         e.printStackTrace();
     }
         
     return properties;
   }//readEncryptedFile() ENDS

   /**  
    * This method reads the parameter values from the encrypted file.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  decryptedHash a hashtable containing the parameter values.  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public Properties readEncryptedStream(InputStream stream){  
   //-------------------------------------------------------------------------   
      try{  
        // create a buffer to store the the encrypted data when its read in.  
        StringBuffer buffer=new StringBuffer();  

        //get the length of the array which holds the valid characters  
        int arraySize=keyArray.length;  
        int aChar=0;  

        // read in each encrypted byte   
        while((aChar=stream.read())!=-1)  
           buffer.append((char)aChar);  

        buffer.setLength(buffer.length()-offset.length());

        // get the total amount of characters in the buffer.  
        int maxLength=buffer.length();  

        // get the key size  
        int position=buffer.toString().indexOf(offset);  


        // position of the first byte of the private key  
        int start=(position+offset.length());    

        // position of the last byte of the private key  
        int stop=((arraySize-1)*(1+position))+start+position;  
        int index=start;  

        // store the private key.  
        for(;index<stop+1;){  
           try{  
              decryptedKey.put(  
            buffer.substring((index+1),(1+index+position)),  
            buffer.substring(index,index+1)  
              );  

              if(DEBUG){                 
            System.out.println("key/value --> "+  
              buffer.substring((index+1),(1+index+position))+  
              " = "+buffer.substring(index,index+1));  
              }  
	    }  
	    catch(StringIndexOutOfBoundsException e){  
	       System.out.println(index);  
	       System.out.println(stop);  
	       System.out.println(buffer.substring((index+1),stop));  
	    }  
  
	    index=1+index+position;  
	 }  
  
	 StringBuffer data=new StringBuffer();  
	 String aByte="";  
  
    //decrypt the file  
	 for(;index<maxLength;index=index+position){  
	    aByte=(String)  
         decryptedKey.get((buffer.substring(index,index+position)));  
            try{  
	       if(!aByte.equals("\n"))  
		  data.append(aByte);  
	       else{  
		  int equalSign=data.toString().indexOf("=");  
		  decryptedHash.put(data.substring(0,equalSign),  
		     data.substring(equalSign+1,data.length())  
		  );  
  
                  if(DEBUG){  
		     System.out.print(data.substring(0,equalSign)+" ");  
		     System.out.println(data.substring(equalSign+1,  
                        data.length()));  
                  }  
  
		  data.setLength(0);  
	       }   
	    }   
	    catch(NullPointerException exception){  
               System.out.println("\n<error>");  
               System.out.print("   I cannot decrypt the file. It has changed");  
               System.out.println(" since encryption.\n   Program terminating!");  
               System.out.println("</error>\n");  
               System.exit(-1);  
	    }   
	 }  
  
	 if(DEBUG){  
	    Enumeration enum=decryptedHash.keys();   
	    System.out.println("\n\n\n*****Display Decrypted values");  
  
	    for(;enum.hasMoreElements();){  
	       String key=(String)enum.nextElement();  
	       System.out.print(key+"=");  
	       System.out.println((String)decryptedHash.get(key));  
	         
	    }  
	 }  
      }  
      catch(FileNotFoundException e){}  
      catch(IOException e){}  
  
      return decryptedHash;  
   }// readEncryptedFile() END  
  
  
   /**  
    * This method returns a hashtable containing the values stored within  
    * the properties file.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public Properties getHashProperties(){  
   //-------------------------------------------------------------------------  
      return decryptedHash;  
   }// getHashProperties() END  
  
     
  
   //-------------------------------------------------------------------------  
   //----------------------------Helpers--------------------------------------  
   //-------------------------------------------------------------------------  
   /**  
    * This methods calculates the random seed.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   private int calculateSeedSize(int seedSize){  
   //-------------------------------------------------------------------------  
      int size=4;  
      int index=0;  
  
      if(seedSize>=0&&seedSize<=255){  
         for(index=0;index<seedSize;index=index+4)  
            ;  
  
         size=index;  
      }  
  
      return size;  
   }// getParameterValues() END  
  
   /**  
    * This methods encodes the header  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   private void createHeader(){  
   //-------------------------------------------------------------------------  
      Random random=new Random();  
      int arraySize=keyArray.length;  
  
      for(int innerLoop=0;innerLoop<realSeed;innerLoop++)  
	      headerBuffer.append(keyArray[random.nextInt(arraySize)]);  
         
      headerBuffer.append(offset);  
   }// createHeader() END  
  
   /**  
    * This methods creates the random key hash.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   private void createRandomKHA(){  
   //-------------------------------------------------------------------------  
      Enumeration enum=properties.propertyNames();  
      String key="";  
      String value="";  
  
      for(;enum.hasMoreElements();){  
         key=(String)enum.nextElement();  
         value=(String)properties.getProperty(key);  
         translateKeyValue(key.trim(),value.trim());  
      }  
   }// createRandomKHA() END  
  
   /**  
    * This methods creates the random key hash.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   private void createRandomKHA(Hashtable hashtable){  
   //-------------------------------------------------------------------------  
      Enumeration enum=hashtable.keys();  
      String key="";  
      String value="";  
  
      for(;enum.hasMoreElements();){  
         key=(String)enum.nextElement();  
         value=(String)hashtable.get(key);  
         translateKeyValue(key,value);  
      }  
   }// createRandomKHA() END  
  
   /**  
    * The <b>getFileName</b> method returns the name of the parameter file.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  the name of the parameter file.  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   private String getFileName(){  
   //-------------------------------------------------------------------------  
      return fileName;  
   }//getFileName() ENDS  
  
   /**  
    * The <b>getFullPath</b> method returns the path to this file.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  the path to the parameter file  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   private String getFullPath(){  
   //-------------------------------------------------------------------------  
      return fullPath;  
   }// getFullPath() ENDS  
  
   /**  
    * This methods initializes the random key hash.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   private void initializeKHA(){  
   //-------------------------------------------------------------------------  
      Random random=new Random();  
      StringBuffer value=new StringBuffer();  
      boolean validValue=false;  
      int arraySize=keyArray.length;  
      String element="";  
      StringBuffer buffer=new StringBuffer();
      DEBUG=true;
      
      // assign each valid character a value.  
      for(int outerLoop=0;outerLoop<arraySize;outerLoop++){  
			element=keyArray[outerLoop]+"";  
  
         // create the associated key.  
			while(!validValue){  
				for(int innerLoop=0;innerLoop<realSeed;innerLoop++)  
					value.append(keyArray[random.nextInt(arraySize)]);  
	      
				if(!randomKHA.containsValue(value.toString())){  
               validValue=true;  
               randomKHA.put(element,value.toString());  
               privateKeyBuffer.append(element);  
               privateKeyBuffer.append(value.toString());  
               if(DEBUG)
                   buffer.append(element+"="+value.toString()+"     ");
				}  
  
				value.setLength(0);   
			}  
  
         validValue=false;  
      }  
  }// initializeKHA() END  
  
   /**  
    * The <b>setFilePathName</b> method set the following items.  
    * <li>input file name</li  
    * <li>input file full path</li>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   private void setFilePathName(String filePath){  
   //-------------------------------------------------------------------------  
      Properties properties=System.getProperties();  
      String fileSeparator=(String)properties.get("file.separator");  
      int cutoff=0;  
      cutoff=filePath.lastIndexOf(fileSeparator);  
      fullPath=filePath.substring(0,cutoff+1);  
      fileName=filePath.substring(cutoff+1,filePath.length());  
   }//setFilePathName()  
  
   /*  
    * <code>toString</code>  
    * <p>  
    * This method sets the column bind.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param bind <code>String</code> the column bind  
    * @return <code>none</code>  
    */  
   //---------------------------------------------------------------------------  
   public String toString(){  
   //---------------------------------------------------------------------------  
      StringBuffer buffer=new StringBuffer("*****RandomHashs:");  
      buffer.append("\tdataBuffer="+dataBuffer);  
      buffer.append("\tDEBUG="+DEBUG);  
      buffer.append("\tdecryptedHash="+decryptedHash);  
      buffer.append("\tdecryptedKey="+decryptedKey);  
      buffer.append("\tfileName="+fileName);  
      buffer.append("\tfullPath="+fullPath);  
      buffer.append("\tinputFile="+inputFile);  
      buffer.append("\tkeyArray="+keyArray);  
      buffer.append("\toffset="+offset);  
      buffer.append("\toutputFile="+outputFile);  
      buffer.append("\tprivateKeyBuffer="+privateKeyBuffer);  
      buffer.append("\tproperties="+properties);  
      buffer.append("\trandomKHA="+randomKHA);  
      buffer.append("\trealSeed="+realSeed);  
      buffer.append("\tseedSize="+seedSize);  
      return buffer.toString();  
   }// toString() ENDS  
  
   /**  
    * This methods encodes the current key value pair.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   private void translateKeyValue(String key,String value){  
   //-------------------------------------------------------------------------  
      int keySize=key.length();  
      int valueSize=value.length();  
  
      for(int index=0;index<keySize;index++)  
         dataBuffer.append(randomKHA.get(key.charAt(index)+""));  
  
      dataBuffer.append(randomKHA.get("="));  
  
      for(int index=0;index<valueSize;index++)  
         dataBuffer.append(randomKHA.get(value.charAt(index)+""));  
  
      dataBuffer.append(randomKHA.get("\n"));  
  
   }// translateKeyValue() END  
  
  
  
   /**  
    * This methods writes the file  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   private void writeFile(){  
   //-------------------------------------------------------------------------  
      try{
        //this tells us whether the file has been encrypted
        StringBuffer marker=new StringBuffer(offset);  
        marker=marker.reverse();
        
        outputFile=outputFile+".x";
        PrintWriter writer=
          new PrintWriter(new BufferedWriter(new FileWriter(outputFile,false)));  
        writer.print(headerBuffer.toString());  
        writer.print(privateKeyBuffer.toString());  
        writer.print(dataBuffer.toString()); 
        writer.print(marker.toString());
        writer.close();  
      }  
      catch(IOException exception){  
      }  
   }// writeFile() END  
  
   
   public static void main(String args[]){  
      RandomHash randomHash=new RandomHash();  
      int returnValue=0;  
      int count=args.length;  

      if(count>2||count<1){  
         System.out.println("\nusage: RandomHash keySize inputFile");  
         System.out.println("   To create the encrypted file:");  
         System.out.println("   keySize - integer from 1-255");  
         System.out.println("   inputFile - full path to the input file");  
         System.out.println("\n");  
         System.out.println("\nusage: RandomHash inputFile");  
         System.out.println("   To read the encrypted file:");  
         System.out.println("   inputFile - full path to the encrypted file");  
         System.out.println("\n");  
         returnValue=-1;  
      }  
      else if(count==1)  
         randomHash.readEncryptedFile(args[0]);  
      else{  
         Utilities utilities=new Utilities();  
         int keySize=utilities.stringToInt(args[0]);  
         randomHash.setFilePathName(args[1]);  
         String fileName=randomHash.getFileName();  
         String fullPath=randomHash.getFullPath();  
         new RandomHash(keySize,fullPath+fileName,fullPath+fileName);  
      }  
  
      System.exit(returnValue);  
   }  
  
   //***************************************************************************  
   // Class Variables  
   //***************************************************************************  
   private StringBuffer         dataBuffer=new StringBuffer();  
   private ServletContext       context=null;
   private boolean              DEBUG=false;  
   private char[]               keyArray={'a','b','c','d','e','f','g',  
                                          'h','i','j','k','l','m','n',  
                                          'o','p','q','r','s','t','u',  
                                          'v','w','x','y','z',' ',  
                                          'A','B','C','D','E','F','G',  
                                          'H','I','J','K','L','M','N',  
                                          'O','P','Q','R','S','T','U',  
                                          'V','W','X','Y','Z',  
                                          '0','1','2','3','4','5','6',  
                                          '7','8','9','`','-','=','~',  
                                          '!','@','#','$','\n','\r', 
                                          '%','^','&','*','(',')','_',  
                                          '+','{','}','|',  
                                          '[',']','\\',';','\'',':','"',  
                                          ',','.','/','<','>','?'};  
  
   private StringBuffer         headerBuffer=new StringBuffer();  
   private String               inputFile="";  
   private static String        encryptionMarker="	1.0";  
   private String               outputFile="";  
   public static String        offset="Applied Information Technology Inc.";  
   private Properties           properties;  
   private Hashtable            randomKHA=new Hashtable();  
   private Hashtable            decryptedKey=new Hashtable();  
   private Properties           decryptedHash=new Properties();  
   private String               fileName="";  
   private String               fullPath="";  
   private int                  realSeed=8;  
   private int                  seedSize=8;  
   private StringBuffer         privateKeyBuffer=new StringBuffer();  
   private Utilities            utilities=new Utilities();
}// RandomHash() ENDS  
