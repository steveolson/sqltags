/* $Id: AboutSQLTags.java,v 1.4 2002/03/16 03:04:37 solson Exp $
 * $Log: AboutSQLTags.java,v $
 * Revision 1.4  2002/03/16 03:04:37  solson
 * Image filenames were not correct; sql != SQL :(( case matters for calls to
 * getResourceAsStream within a Jar file.
 *
 * Revision 1.3  2002/03/15 22:30:09  solson
 * moved ImageIcon stuff from Utilities to this class in an attempt to
 * fix loading the IconImages from the executable Jar file.
 * Still cannot pull images from the Jar file; however, the generator still
 * runs but with errors on the "about" page.
 *
 * Revision 1.2  2002/03/15 14:23:46  solson
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
import com.aitworks.sqltags.utilities.Utilities;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.*;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;

/**  
 * <code>AboutSQL</code>  
 * <p>  
 * This class displays/creates the about dialog.
 * </p>  
 * @author  Booker Northington II  
 * @version 1.0  
 * @since   1.3  
 */  
//------------------------------------------------------------------------------  
public class AboutSQLTags{
//------------------------------------------------------------------------------  
   //*************************************************************************** 
   // Constructors
   //***************************************************************************
   /**  
    * <code>Class Constructor</code>  
    * <p>  
    * Builds and displays the GUI
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------
   public AboutSQLTags(JFrame frame){
   //---------------------------------------------------------------------------
       dialog=new JDialog(frame,true);
       dialog.setTitle("About");
       dialog.setResizable(false);
       Component component=setGraphicPanel();         
       dialog.getContentPane().add(component);
       dialog.setSize(560,359); 
       dialog.setLocation(300,300);
       dialog.setVisible(false); 
   }//AboutSQL() ENDS
       
   //*************************************************************************** 
   // Public Methods
   //***************************************************************************
   /**  
    * <code>main</code>  
    * <p>  
    * This method is used for testing.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------
   public static void main(String [] args) {
      new AboutSQLTags(new JFrame());
   }//main() ENDS
   
   /**  
    * <code>main</code>  
    * <p>  
    * This method is used for testing.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------
   public void showAbout(){
      tabbedPane.setSelectedIndex(0);
      dialog.show();
   }//main() ENDS
   
    
   //*************************************************************************** 
   // Private Methods
   //***************************************************************************
   /**  
    * <code>setGraphicPanel</code>  
    * <p>  
    * This method creates the graphic panel.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none
    * @return  component <code>Component</code> the tabbed pane.
    */  
   //---------------------------------------------------------------------------
   private Component setGraphicPanel(){
   //---------------------------------------------------------------------------
      tabbedPane=new JTabbedPane();
      JPanel detailPanel=new JPanel(new GridBagLayout());
      productVersion.setForeground(Color.black);
      productVersionLabel.setForeground(Color.black);
      vendor.setForeground(Color.black);
      vendorLabel.setForeground(Color.black);      
      productVersion.setFont(textFont);
      productVersionLabel.setFont(labelFont);
      vendor.setFont(textFont);
      vendorLabel.setFont(labelFont);
      ImageIcon iconImage=getIconImage(className,sqltagsIcon);      
      JPanel graphicPanel=null;


      if(iconImage!=null){
          graphicPanel=new JPanel(){
             public void paintComponent(Graphics graphics){
                ImageIcon image=getIconImage(className,sqltagsImage);      
                if(image!=null) {
                    graphics.drawImage(image.getImage(),0,0,null);
                    super.paintComponent(graphics);         
                } else System.out.println("image is null");
             }  
          };  
      }
      
      JPanel titlePanel=new JPanel(new GridBagLayout());
      titlePanel.setBackground(Color.white);
      graphicPanel.setOpaque(false);   
      productLine=new JLabel("SQLTags Product Information",iconImage,JLabel.LEFT);
      productLine.setBackground(Color.white);
      productLine.setForeground(Color.black);
      productLine.setFont(headingFont);      
      setBagElement(productLine,         0,0,1,1,100,0,titlePanel);
      setBagElement(new JLabel(" "),     0,1,1,1,100,0,detailPanel);
      setBagElement(new JLabel(" "),     1,1,1,1,100,0,detailPanel);
      setBagElement(productVersionLabel, 0,2,1,1,100,0,detailPanel);
      setBagElement(productVersion,      1,2,1,1,100,0,detailPanel);
      setBagElement(vendorLabel,         0,3,1,1,100,0,detailPanel);
      setBagElement(vendor,              1,3,1,1,100,0,detailPanel);      
      JPanel detailPanelTopLevel=new JPanel(new GridBagLayout());
      setBagElement(titlePanel, 0,0,1,1,.0,.0,detailPanelTopLevel);
      setBagElement(detailPanel,0,1,1,1,.0,.0,detailPanelTopLevel);
      tabbedPane.add(detailPanelTopLevel,0);
      detailPanelTopLevel.setBackground(Color.white);
      detailPanel.setBackground(Color.white);
      detailPanel.setForeground(Color.black);
      tabbedPane.setTitleAt(0,"Detail");
      return tabbedPane; 
   }//setGraphicPanel() ENDS
   
   /**  
    * <code>setBagElement</code>  
    * <p>  
    * This method sets the text label's constraints
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param component <code>JLabel[]</code> an array of JLabels
    * @param x <code>int</code> x cord
    * @param y <code>int</code> y cord
    * @param w <code>int</code> w cord
    * @param h <code>int</code> h cord
    * @param wx<code>double</code> wx cord
    * @param wy <code>double</code> wy cord
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
   private void setBagElement(Component component,
       int x, int y, int w, int h, double wx, double wy,Container container){
   //---------------------------------------------------------------------------  
      GridBagLayout gridBag=(GridBagLayout)container.getLayout();
      GridBagConstraints constraints=new GridBagConstraints();
      constraints.fill=GridBagConstraints.BOTH;
      constraints.gridx=x;
      constraints.gridy=y;
      constraints.gridwidth=w;
      constraints.gridheight=h;
      constraints.weightx=wx;
      constraints.weighty=wy;
      container.add(component);
      gridBag.setConstraints(component,constraints);
   }//setBagElement() ENDS
   
   /**  
    * <code>loadImage</code>  
    * <p>  
    * This method sets the text label's constraints
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
   public ImageIcon getIconImage(Class object,String imageName){
   //---------------------------------------------------------------------------  
        byte [] bIcon = getSystemResourceAsByteArray(object,imageName);
        if( bIcon!=null && bIcon.length>0)
            return new ImageIcon( bIcon );
        return new ImageIcon();
   }//getIconImage() ENDS
   

   /**  
    * <code>loadImage</code>  
    * <p>  
    * This method sets the text label's constraints
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
   public byte[] getSystemResourceAsByteArray(Class object,String imageName){
   //---------------------------------------------------------------------------  
        byte[] buffer=new byte[2048];
        int aChar;
        
        try{
          DataInputStream in=new DataInputStream(getClass().getResourceAsStream(imageName));
          // why does this fail when within a jar?
          byte[] data=new byte[in.available()];
          in.readFully(data);
          in.close();
          return data;
          
/*
            InputStream inputStream=getClass().getResourceAsStream(imageName);
            System.out.println("InputStream("+imageName+") is: "+ inputStream);
            if(inputStream==null)
                return null;
            
            BufferedInputStream bufferedInputStream=new BufferedInputStream(inputStream);
            System.out.println("BufferedInputStream is: "+bufferedInputStream);
            ByteArrayOutputStream byteArrayOutputStream =new ByteArrayOutputStream(2048);

            while ((aChar=bufferedInputStream.read(buffer))!=-1)
                byteArrayOutputStream.write(buffer, 0, aChar);

            bufferedInputStream.close();
            byteArrayOutputStream.flush();
            buffer=byteArrayOutputStream.toByteArray();            
 */
        }
        catch(Exception e){
            buffer=null;
            e.printStackTrace();
        }
        return buffer;
   }//getSystemResourceAsByteArray() ENDS
   
   
   //*************************************************************************** 
   // Class Variables 
   //***************************************************************************   
   private String sqltagsImage="SQLtagsAbout.jpg";
   private String sqltagsIcon="SQLtagsAboutIcon.jpg";
   private Class className=this.getClass();
   private JDialog dialog;
   private JTabbedPane tabbedPane;
   private JFrame frame;
   private JLabel productLine;
   private JLabel productLineSpacer=new JLabel("");
   private JLabel closeSpacer=new JLabel("");
   private JLabel productVersion=new JLabel("SQLTags Version 0.0.1 Beta");
   private JLabel productVersionLabel=new JLabel("Product Version:");
   private JLabel vendor=new JLabel("Applied Information Technology Inc.");
   private JLabel vendorLabel=new JLabel("Vendor:");
   private Font headingFont=new Font("TimesNewRoman",Font.PLAIN+Font.BOLD,18);
   private Font labelFont=new Font("TimesRoman",Font.PLAIN+Font.BOLD,12);
   private Font textFont=new Font("TimesRoman",Font.PLAIN,12);
   // private Utilities utilities=new Utilities();
}//AboutSQLTags() ENDS