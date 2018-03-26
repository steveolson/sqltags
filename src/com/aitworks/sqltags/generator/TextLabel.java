/* $Id: TextLabel.java,v 1.12 2002/03/15 14:23:45 solson Exp $
 * $Log: TextLabel.java,v $
 * Revision 1.12  2002/03/15 14:23:45  solson
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
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.JLabel; 
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;

/**  
 * <code>TextLabel</code>  
 * <p>  
 * This class places the properties key-value on the gui   
 * </p>  
 * @author  Booker Northington II  
 * @version 1.0  
 * @since   1.3  
 */  
//------------------------------------------------------------------------------  
public class TextLabel extends JPanel{ 
//------------------------------------------------------------------------------  
   //*************************************************************************** 
   // Class Constructor 
   //***************************************************************************
   /**  
    * <code>Class Constructor</code>  
    * <p>  
    * Builds and displays key-value area of the gui
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param label <code>JLabel[]</code> an array of JLabels
    * @param text <code>JTextField[]</code> an array of JTextField's
    * @param container <code>Container]</code> the java container
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
   public TextLabel(JLabel label[],Component component[],Container container){ 
   //---------------------------------------------------------------------------  
      super(new GridLayout(1,1)); 
      int arraySize=label.length; 
      int counterOne=0;
      int counterTwo=0;
      Color bColor=container.getBackground();
      Font font=new Font("TimesNewRoman",Font.PLAIN,10);
      JScrollPane scrollPane=new 
        JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_NEVER,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      JPanel checkboxPanel=new JPanel(new GridBagLayout());
      JPanel panel=new JPanel(new GridBagLayout());
      JPanel jarPanel=new JPanel(new GridBagLayout());
      JPanel textPanel=new JPanel(new GridBagLayout());
      BevelBorder bevelB=new BevelBorder(BevelBorder.LOWERED);
      EtchedBorder etched=new EtchedBorder(Color.black,bColor);
      CompoundBorder directoryBorder=new CompoundBorder(etched,bevelB);
      TitledBorder borderOne=new TitledBorder(directoryBorder,"Directories");
      MatteBorder matteBorder=new MatteBorder(2,2,0,2,Color.black); //tlbr
      BevelBorder jarBevel = new BevelBorder(BevelBorder.LOWERED);
      TitledBorder jarBorder=new TitledBorder(directoryBorder,"Jar Content");
      MatteBorder jarMatteBorder=new MatteBorder(2,2,0,2,Color.black);
      checkboxPanel.setBorder(new CompoundBorder(matteBorder,borderOne));
      borderOne.setTitleFont(titleFont);
      jarPanel.setBorder(new CompoundBorder(jarMatteBorder,jarBorder));
      jarBorder.setTitleFont(titleFont);

      for(int index=0;index<arraySize; index++){
         label[index].setForeground(Color.black);
         label[index].setFont(font);
         component[index].setForeground(Color.black);
         component[index].setFont(font);
         component[index].setBackground(bColor);
         
         if(index<6){
             setTextLabel(((Component)label[index]),counterOne,counterTwo,1,1,
                0.0,0.0,textPanel);
             counterOne++;
             setTextLabel((Component)component[index],counterOne,counterTwo,
                1,1,1.0,1.0,textPanel);
             
            if(index==5){
                 counterOne=1;
                 counterTwo=-1;
             }
         }
         else if(index>=6&&index<9){
             setTextLabel(((Component)label[index]),counterOne,counterTwo,1,1,
                0.0,0.0,checkboxPanel);
             counterOne++;
             setTextLabel((Component)component[index],counterOne,counterTwo,
                1,1,1.0,1.0,checkboxPanel);

             if(index==8){
                counterOne=1;
                counterTwo=-1;
            }
         }
         else{
             setTextLabel(((Component)label[index]),counterOne,counterTwo,1,1,
                0.0,0.0,jarPanel);
             counterOne++;
             setTextLabel((Component)component[index],counterOne,counterTwo,
                1,1,1.0,1.0,jarPanel);
        }
         
         counterOne--;
         counterTwo++;
         
         if(index+1==arraySize){
            scrollPane.getViewport().add(checkboxPanel);
            setTextLabel(scrollPane,0,0,1,1,100.0,.0,panel);        
            setTextLabel(jarPanel,1,0,1,1,1.0,1.0,panel);        
            setTextLabel(textPanel,0,0,1,1,90.,90.,container);        
            setTextLabel(panel,0,1,1,1,0.0,40.0,container);        
         }
      }
   }//Constructor ENDS 
  
   //*************************************************************************** 
   //Finalizer Method
   //***************************************************************************
   /**  
    * This is the finalizer method for this class  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //---------------------------------------------------------------------------  
   protected void finalize() throws Throwable{  
   //---------------------------------------------------------------------------  
      super.finalize();  
   }// finalizer() ENDS  

  
   //*************************************************************************** 
   // Public Methods
   //***************************************************************************
   /**  
    * <code>setTextLabel</code>  
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
   public void setTextLabel(Component component,
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
   }//setTextLabel() ENDS 

   //*************************************************************************** 
   //Class Variables
   //***************************************************************************
   private Font titleFont=new Font("TimesNewRoman",Font.PLAIN+Font.BOLD,12);
}//TextLabel() ENDS
