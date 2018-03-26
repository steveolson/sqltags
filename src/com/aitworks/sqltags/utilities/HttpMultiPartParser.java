/**
 * $Id: HttpMultiPartParser.java,v 1.4 2002/09/03 18:40:27 jpoon Exp $
 * $Log: HttpMultiPartParser.java,v $
 * Revision 1.4  2002/09/03 18:40:27  jpoon
 * fix bug for "\\" indexOf("\\") >= 0 instead of indexOf("\\") > 0
 *
 * Revision 1.3  2002/08/30 18:48:32  solson
 * removed some debugging messages
 *
 * Revision 1.2  2002/08/30 17:45:52  solson
 * fixed setFilename() call
 *
 * Revision 1.1  2002/08/30 17:28:34  solson
 * new file to replace MultiPartMime parser
 *
 * ====================================================================
 * Copyright(c) 2001 iSavvix Corporation (http://www.isavvix.com/)
 *
 *                        All rights reserved
 *
 * Permission to use, copy, modify and distribute this material for
 * any purpose and without fee is hereby granted, provided that the
 * above copyright notice and this permission notice appear in all
 * copies, and that the name of iSavvix Corporation not be used in
 * advertising or publicity pertaining to this material without the
 * specific, prior written permission of an authorized representative of
 * iSavvix Corporation.
 *
 * Modifications by Steve A. Olson/AIT
 *
 * ISAVVIX CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES,
 * EXPRESS OR IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR ANY PARTICULAR PURPOSE, AND THE WARRANTY AGAINST
 * INFRINGEMENT OF PATENTS OR OTHER INTELLECTUAL PROPERTY RIGHTS.  THE
 * SOFTWARE IS PROVIDED "AS IS", AND IN NO EVENT SHALL ISAVVIX CORPORATION OR
 * ANY OF ITS AFFILIATES BE LIABLE FOR ANY DAMAGES, INCLUDING ANY
 * LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL DAMAGES RELATING
 * TO THE SOFTWARE.
 *
 */

package com.aitworks.sqltags.utilities;

import java.io.*;
import java.util.*;
import javax.servlet.*;

/**
  * This class provides methods for parsing a HTML multi-part form.  Each
  * method returns a Hashtable which contains keys for all parameters sent
  * from the web browser.  The corresponding values are either type "String"
  * or "FileInfo" depending on the type of data in the corresponding part.
  * <P>
  * The following is a sample InputStream expected by the methods in this
  * class:<PRE>

    -----------------------------7ce23a18680
    Content-Disposition: form-data; name="SomeTextField1"

    on
    -----------------------------7ce23a18680
    Content-Disposition: form-data; name="LocalFile1"; filename="C:\temp\testit.c"
    Content-Type: text/plain

    #include <stdlib.h>


    int main(int argc, char **argv)
    {
       printf("Testing\n");
       return 0;
    }

    -----------------------------7ce23a18680--
    </PRE>
 * @see com.isavvix.tools.FileInfo
 * @author  Anil Hemrajani
*/
public class HttpMultiPartParser
{
    private final String lineSeparator = System.getProperty("line.separator", "\n");
    private final int ONE_MB=1024*1024*1;

    /** 
     * Parses the InputStream, separates the various parts and returns
     * them as key=value pairs in a Hashtable.  Any incoming files are
     * saved in directory "saveInDir" using the client's file name; the
     * file information is stored as java.io.File object in the Hashtable
     * ("value" part).
     */
    /*
    public Hashtable parseData(ServletInputStream data
                               // ,String boundary
                               // ,String saveInDir
                               )
                     throws IllegalArgumentException, IOException
    {
        // return processData(data, boundary, saveInDir);
        return processData((ServletInputStream)data);
    }
     */
    

    /** 
     * Parses the InputStream, separates the various parts and returns
     * them as key=value pairs in a Hashtable.  Any incoming files are
     * saved as byte arrays; the file information is stored as java.io.File
     * object in the Hashtable ("value" part).
     *
     *Removed boundary from method because boundary is contained within
     * the servlet stream ... can't get to it first
     */
    public Hashtable parseData(ServletInputStream data)
                     throws IllegalArgumentException, IOException
    {
        // return processData(data, boundary, null);
        return processData(data);
    }
    
    
    // Also removed references to boundary and saveInDir ... we'll be
    // using File.createTempFile() ... which selects a tmp dir for us.
    private Hashtable processData(ServletInputStream is)
                             throws IllegalArgumentException, IOException
    {
        String boundary = null;
        if (is == null)
            throw new IllegalArgumentException("ServletInputStream");

        // Eliminated boundary code here ...
        //
        // if (boundary == null || boundary.trim().length() < 1)
        //     throw new IllegalArgumentException("boundary");


        // Each content will begin with two dashes "--" plus the actual boundary string
        // boundary = "--" + boundary; 


        StringTokenizer stLine = null, stFields = null;
        //
        // Also, replaced all references to fileInfo to mimePart
        // 
        // FileInfo fileInfo = null;
        MimePart mimePart = null;
        Hashtable dataTable = new Hashtable(5);
        String line = null, field = null, paramName = null;

        boolean saveFiles=true;
        boolean isFile=false;
        /*
        boolean saveFiles=(saveInDir != null && saveInDir.trim().length() > 0),
                isFile = false;
         */


        // get the first line ... it IS the boundry ... 
        line = getLine(is);
        // added this to fake the boundry ... 
        if(line!=null) {
            boundary = new String(line);
        }
        
        if (line == null || !line.startsWith(boundary))
            throw new IOException("Boundary not found;"
                                 +" boundary = " + boundary
                                 +", line = "    + line);
        

        while (line != null)
        {
            // Process boundary line  ----------------------------------------
            if (line == null || !line.startsWith(boundary))
                return dataTable;


            // Process "Content-Disposition: " line --------------------------
            line = getLine(is);
            if (line == null)
                return dataTable;
                

            // Split line into the following 3 tokens (or 2 if not a file):
            // 1. Content-Disposition: form-data
            // 2. name="LocalFile1"
            // 3. filename="C:\autoexec.bat"  (only present if this is part of a HTML file INPUT tag) */
            stLine = new StringTokenizer(line, ";\r\n");
            if (stLine.countTokens() < 2)
                throw new IllegalArgumentException("Bad data in second line");


            // Confirm that this is "form-data"
            line = stLine.nextToken().toLowerCase();
            if (line.indexOf("form-data") < 0)
                throw new IllegalArgumentException("Bad data in second line");


            // Now split token 2 from above into field "name" and it's "value"
            // e.g. name="LocalFile1"
            stFields = new StringTokenizer(stLine.nextToken(), "=\"");
            if (stFields.countTokens() < 2)
                throw new IllegalArgumentException("Bad data in second line");
                

            // Get field name
            // fileInfo = new FileInfo();
            stFields.nextToken();
            paramName = stFields.nextToken();
            mimePart = new MimePart(paramName);


            // Now split token 3 from above into file "name" and it's "value"
            // e.g. filename="C:\autoexec.bat"
            isFile = false;
            if (stLine.hasMoreTokens())
            {
                field    = stLine.nextToken();
                stFields = new StringTokenizer(field, "=\"");
                if (stFields.countTokens() > 1)
                {
                    if (stFields.nextToken().trim().equalsIgnoreCase("filename"))
                    {
                        // Again, replaced references to fileInfo w/ mimePart
                        // fileInfo.setName(paramName);
                        
                        String value = stFields.nextToken();
                        if (value != null && value.trim().length() > 0)
                        {
                            // mimePart replaces fileInfo ...
                            // fileInfo.setClientFileName(value);
                            // need to strip off path
                            if( value.indexOf("\\")>=0) {
                                mimePart.setFilename(value.substring(value.lastIndexOf("\\")+1));
                            } else {
                                mimePart.setFilename(value);
                            }
                            isFile = true;
                        }
                        else
                        {
                            // An error condition occurred, skip to next boundary
                            line = getLine(is); // Skip "Content-Type:" line
                            line = getLine(is); // Skip blank line
                            line = getLine(is); // Skip blank line
                            line = getLine(is); // Position to boundary line
                            continue;
                        }
                    }
                }
                else
                if (field.toLowerCase().indexOf("filename") >= 0)
                {
                    // An error condition occurred, skip to next boundary
                    line = getLine(is); // Skip "Content-Type:" line
                    line = getLine(is); // Skip blank line
                    line = getLine(is); // Skip blank line
                    line = getLine(is); // Position to boundary line
                    continue;
                }
            }


            // Process "Content-Type: " line ----------------------------------
            // e.g. Content-Type: text/plain
            boolean skipBlankLine = true;
            if (isFile)
            {
                line = getLine(is);
                if (line == null)
                    return dataTable;

                // "Content-type" line not guaranteed to be sent by the browser
                if (line.trim().length() < 1)
                    skipBlankLine = false; // Prevent re-skipping below
                else
                {
                    stLine = new StringTokenizer(line, ": ");
                    if (stLine.countTokens() < 2)
                        throw new IllegalArgumentException("Bad data in third line");

                    stLine.nextToken(); // Content-Type
                    // mimePart replaces fileInfo
                    // fileInfo.setFileContentType(stLine.nextToken());
                    mimePart.setContentType(stLine.nextToken());
                }
            }


            // Skip blank line  -----------------------------------------------
            if (skipBlankLine)  // Blank line already skipped above?
            {
                line = getLine(is);
                if (line == null)
                    return dataTable;
            }
                

            // Process data: If not a file, add to hashtable and continue
            if (!isFile)
            {
                mimePart.isFile(false);
                String contents = "";
                OutputStream os = new ByteArrayOutputStream(ONE_MB);
                
                // used same routine to read non-file parts as file parts
                // readUntilBoundary was chopped from code below and
                // put into separate function
                line = readUntilBoundary(is, os, boundary);
                
                // some support for mimePart ...
                // need a Vector for data ...
                Vector byteVector = new Vector();
                byteVector.addElement(os.toString().getBytes());
                
                // Also .. added ability to deal with multi-select options
                // and input fields on form with same name
                //
                // see if this is a dup ...
                MimePart tmpMimePart = (MimePart)dataTable.get(paramName);
                if( tmpMimePart!=null) {
                    // yep, it's a dup .. just add another element to it's content
                    tmpMimePart.setContentLength(byteVector.size()+tmpMimePart.getContentLength());
                    tmpMimePart.setContents(byteVector);
                } else {
                    // nope, new one .. add the mimePart to hashtable
                    // mimePart.setContentLength( contents.size() );
                    mimePart.setContents(byteVector);
                    dataTable.put(paramName, mimePart);
                }
                
                continue;
            }


            // don't think we allow memory version .. but left it here ..
            // Either save contents in memory or to a local file
            try
            {
                OutputStream os = null;
                File f = null;
                // never false ... 
                if (saveFiles) {
                    f = File.createTempFile("mime",null);
                    f.deleteOnExit();
                    os = new FileOutputStream(f);
                    // not using fileInfo ... 
                    /*
                    os = new FileOutputStream(path = getFileName(saveInDir,
                    fileInfo.getClientFileName()));
                     */
                }
                else
                    // should never get here ... 
                    os = new ByteArrayOutputStream(ONE_MB);
                    
                // Ok, read the contents into "os"
                // ok, pulled out code from that was directly below
                // and stuffed it into readUntilBoundary ...
                line = readUntilBoundary(is,os,boundary);
                
                // always save file ... but ... 
                if (!saveFiles) {
                    // ByteArrayOutputStream baos = (ByteArrayOutputStream)os;
                    // fileInfo.setFileContents(baos.toByteArray());
                    Vector v = new Vector();
                    v.addElement( os.toString() );
                    mimePart.setContents(v);
                }
                else {
                    // using mimePart .. 
                    // fileInfo.setLocalFile(new File(path));
                    mimePart.isFile(true);
                    mimePart.setTmpFile(f);
                    String fname = mimePart.getFilename();
                    String ext = "";
                    if( fname.indexOf(".")>0) {
                        ext = fname.substring( fname.lastIndexOf(".")+1);
                    }
                    mimePart.setFileExt(ext);
                    os = null;
                }
                // Ok .. put mimePart into dataTable !
                // dataTable.put(paramName, fileInfo);
                dataTable.put(paramName, mimePart);
            }//end try
            catch (Exception e) { e.printStackTrace(); }
        }

        return dataTable;
    }
    // Compares boundary string to byte array
    private boolean compareBoundary(String boundary, byte ba[])
    {
        byte b;

        if (boundary == null || ba == null)
            return false;

        for (int i=0; i < boundary.length(); i++)
           if ((byte)boundary.charAt(i) != ba[i])
               return false;

        return true;
    }


    /** Convenience method to read HTTP header lines */
    private synchronized String getLine(ServletInputStream sis)
                                throws IOException
    {
        byte   b[]  = new byte[1024];
        int    read = sis.readLine(b, 0, b.length), index;
        String line = null;

        if (read != -1)
        {
           line = new String(b, 0, read);

           if ((index = line.indexOf('\n')) >= 0) {
               line   = line.substring(0, index-1);
           }
        }

        b = null;
        return line;
    }


    /**
     * Concats the directory and file names.
     */
    private String getFileName(String dir, String fileName)
                   throws IllegalArgumentException
    {
        String path = null;

        if (dir == null || fileName == null)
            throw new IllegalArgumentException("dir or fileName is null");

        int   index = fileName.lastIndexOf('/');
        String name = null;
        if (index >= 0)
            name = fileName.substring(index + 1);
        else
            name = fileName;

        index = name.lastIndexOf('\\');
        if (index >= 0)
            fileName = name.substring(index + 1);

        path = dir + File.separator + fileName;
        if (File.separatorChar == '/')
            return path.replace('\\', File.separatorChar);
        else
            return path.replace('/',  File.separatorChar);
    }
    
    /*
     * reads until boundary
     */
    private String readUntilBoundary(ServletInputStream is, OutputStream os, String boundary)
            throws IOException
    {
        String line = null;
        boolean readingContent = true;
        byte previousLine[] = new byte[2 * ONE_MB];
        byte temp[] = null;
        byte currentLine[] = new byte[2 * ONE_MB];
        int read, read3, totalRead=0;
        
        //read in the first line, break out if eof
        if ( ( read = is.readLine( previousLine, 0, previousLine.length ) ) == -1 ) {
            line = null;
            return line;
        }
        totalRead = read;
        
        //read until next boundary and write the contents to OutputStream
        while (readingContent) {
            if ( ( read3 = is.readLine( currentLine, 0, currentLine.length ) ) == -1 ) {
                line = null;
                return line;
            }
            
            
            //check if current line is a boundary
            if (compareBoundary(boundary, currentLine)) {
                os.write( previousLine, 0, read-2 );
                os.flush();
                line = new String( currentLine, 0, read3 );
                return line;
            }
            else {
                
                //current line is not a boundary, write previous line
                os.write( previousLine, 0, read );
                os.flush();
                //reposition previousLine to be currentLine
                temp = currentLine;
                currentLine = previousLine;
                previousLine = temp;
                read = read3;
                totalRead += read3;
            }//end else
        }//end while
        os.close();
        return line;
    }
}