/* $Id: GeneratorGUI.java,v 1.32 2002/08/28 19:28:25 solson Exp $
 * $Log: GeneratorGUI.java,v $
 * Revision 1.32  2002/08/28 19:28:25  solson
 * added exit_on_close to frame
 * changed JFrame text for title bar
 * rewrote createDirectories to delete files in package directory
 *
 * Revision 1.31  2002/04/15 11:10:16  booker
 * Changed scope of textArea and buildMenu. Added
 * methods to reflect new behavior.
 *
 * Revision 1.30  2002/04/12 12:24:05  booker
 * Added Scrolling ability to the textarea.
 *
 * Revision 1.29  2002/04/11 17:08:31  booker
 * Removed Debugging info.
 *
 * Revision 1.28  2002/04/10 17:39:10  booker
 * Added functionality to read in default values for
 * the column properties type fields.
 *
 * Revision 1.27  2002/03/15 22:34:59  solson
 * fixed several problems w/ Properties.  save didn't save jarFilename because
 * it was looking at the wrong component offsets.  Save under DOS only put
 * the \n char at EOL, not /r /n as is required.  null constructor didn't initialize
 * the properties variable causing NPE in various places; etc.
 *
 * Revision 1.26  2002/03/15 14:23:46  solson
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
import com.aitworks.sqltags.utilities.CreateJar;
// import com.aitworks.sqltags.utilities.Utilities;
import java.util.StringTokenizer; 
import java.io.FileWriter;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.PropertyResourceBundle;
import java.util.Properties;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JSplitPane;
import javax.swing.UIManager;             
import java.awt.event.ActionEvent;               
import java.awt.event.ActionListener;        
import javax.swing.event.MenuListener;    

/**  
 * <code>GeneratorGui</code>  
 * <p>  
 * GeneratorGUI allows a user to generate java file by reverse enginnering
 * the database.   
 * </p>  
 * @author  Booker Northington II  
 * @version 1.0  
 * @since   1.3  
 */  
//------------------------------------------------------------------------------  
public class GeneratorGUI implements ActionListener, Runnable{ 
//------------------------------------------------------------------------------  
   //*************************************************************************** 
   // Class Constructor 
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
   public GeneratorGUI(String propertyFileLocation) { 
   //---------------------------------------------------------------------------  
      this();
      this.propertyFileLocation=propertyFileLocation;
      loadFileProperties(new File(propertyFileLocation));
   }
   
   
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
   public GeneratorGUI() { 
   //---------------------------------------------------------------------------  
      frame=new JFrame("SQLTags - http://SQLTags.org/"); 
      frame.setResizable(true);
      frame.setLocation(100,100);
      
      // Set to exit on close
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      Container container = frame.getContentPane(); 
      container.setLayout(new GridLayout(1,1));
      properties = new Properties();

      try{ 
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
      } 
      catch(Exception ignore){ 
      } 
     
      // create menu bar
      menuBar.setBorder(new BevelBorder(BevelBorder.RAISED));  
      frame.setJMenuBar(menuBar); 

      //create filemenu and its items
      fileMenu.add(loadItem);
      fileMenu.add(saveItem);
      fileMenu.add(exitItem);


      //create Buildmenu and its items
      jdbcItem=new JMenuItem("JDBC Generator");
      rebuildJar=new JMenuItem("Rebuild Jar");
      classesMenuItem=new JCheckBoxMenuItem("add aitworks classes to jar");
      sourceMenuItem=new JCheckBoxMenuItem("add aitworks source to jar");
      generatedMenuItem=new JCheckBoxMenuItem("add generated files to jar");
      buildMenu.add(jdbcItem);
      buildMenu.add(rebuildJar);

      // create Tools menu and its items
      toolsMenu.add(clearTextField);
      toolsMenu.add(clearOutputArea);
      toolsMenu.add(resetDirectories);
      toolsMenu.add(resetJar);
      
      //create Help Menu
      aboutMenuItem=new  JMenuItem("About");
      helpMenu.add(aboutMenuItem);
      
      // create action listeners for menu items
      aboutMenuItem.setActionCommand("About");
      exitItem.setActionCommand("Exit");
      jdbcItem.setActionCommand("JDBC Generator");
      loadItem.setActionCommand("Load");
      rebuildJar.setActionCommand("Rebuild Jar");
      saveItem.setActionCommand("Save");
      clearTextField.setActionCommand("Clear Textfield");
      resetDirectories.setActionCommand("Reset Directories");
      resetJar.setActionCommand("Reset Jar Content");
      clearOutputArea.setActionCommand("Clear Textarea");
      ((JCheckBox)component[6]).setActionCommand("Package Base");
      ((JCheckBox)component[7]).setActionCommand("SQLTags WorkDir");
      ((JCheckBox)component[8]).setActionCommand("SQLTags Jar");
      
      aboutMenuItem.addActionListener(this);  
      clearTextField.addActionListener(this);  
      exitItem.addActionListener(this);  
      jdbcItem.addActionListener(this);  
      loadItem.addActionListener(this);  
      rebuildJar.addActionListener(this);  
      resetDirectories.addActionListener(this);  
      resetJar.addActionListener(this);  
      saveItem.addActionListener(this);  
      clearOutputArea.addActionListener(this);  
      ((JCheckBox)component[6]).addActionListener(this);
      ((JCheckBox)component[7]).addActionListener(this);
      ((JCheckBox)component[8]).addActionListener(this);
      
      //create menu mnemonic
      jdbcItem.setMnemonic('J');
      clearTextField.setMnemonic('T');
      clearOutputArea.setMnemonic('O');
      exitItem.setMnemonic('x');
      loadItem.setMnemonic('L');
      rebuildJar.setMnemonic('R');
      resetDirectories.setMnemonic('D');
      resetJar.setMnemonic('J');
      saveItem.setMnemonic('S');
      aboutMenuItem.setMnemonic('A');

      //add items to menu bar
      menuBar.add(fileMenu);
      menuBar.add(buildMenu);
      menuBar.add(toolsMenu);
      menuBar.add(helpMenu);

      //create borders
      BevelBorder bevelB = new BevelBorder(BevelBorder.LOWERED);
      borderOne=new TitledBorder(bevelB,"Generator Properties");

      MatteBorder matteBorder=new MatteBorder(2,2,0,2,Color.black); //tlbr
      EtchedBorder etchedBorder=new EtchedBorder(EtchedBorder.RAISED,
                                         Color.black,Color.blue);
      borderOne.setTitleFont(titleFont);

      // create properties panel
      JPanel propertiesPanel = new JPanel(new GridBagLayout());     
      propertiesPanel.setBorder(new CompoundBorder(matteBorder,borderOne));
      TextLabel textLabel=
         new TextLabel(labelArray,component,propertiesPanel);

      // create output window
      outputPanel = new JPanel(new GridLayout(1,1)); 
      EtchedBorder etched=new EtchedBorder(Color.black,frame.getBackground());
      CompoundBorder textBorder2=new CompoundBorder(etched,bevelB);
      textAreaBorder=new TitledBorder(textBorder2,"Generator Output");
      textAreaBorder.setTitleFont(titleFont);
      outputPanel.setBorder(textAreaBorder); 
      textArea= new JTextArea(); 
      textArea.setEditable(false);
      textArea.setBorder(BorderFactory.createEtchedBorder(frame.getBackground(),
            Color.black));     
      JScrollPane scrollPane = new JScrollPane();
      scrollPane.getViewport().add(textArea);
      outputPanel.add(scrollPane); 


      // set file chooser dialog
      fileChooser = new JFileChooser();
      propertyFilter=new PropertyFilter("properties");
      fileChooser.setFileFilter(propertyFilter);
      
      checkboxChooser = new JFileChooser();

      // set items initial state
      rebuildJar.setEnabled(false);
      jdbcItem.setEnabled(false);

               
      JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
      propertiesPanel, outputPanel);
      splitPane.setOneTouchExpandable(true);
      splitPane.setDividerLocation(275);
      Dimension minimumSize=new Dimension(100, 50);
      outputPanel.setMinimumSize(minimumSize);
      propertiesPanel.setMinimumSize(minimumSize);
      splitPane.setPreferredSize(new Dimension(400,200));
      container.add(splitPane);
      setToolTipText();
      
      // create fonts
      setFontBackground(propertiesPanel.getBackground());
      aboutSQLTags=new AboutSQLTags(frame);

      frame.pack(); 
      frame.setSize(450,625);//wh
      frame.setVisible(true); 
   } //Constructor ENDS
  
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
   //Public Methods
   //***************************************************************************
   //---------------------------------------------------------------------------  
   public void actionPerformed(ActionEvent actionEvent){
   //---------------------------------------------------------------------------  
      String command=actionEvent.getActionCommand();

      if(command.equals("About"))
         aboutSQLTags.showAbout();  
       if(command.equals("Clear Textarea"))
         textArea.setText("");  
      else if(command.equals("Clear Textfield")){
         ((JTextField) component[0]).setText("");
         ((JTextField) component[1]).setText("");
         ((JTextField) component[2]).setText("");
         ((JTextField) component[3]).setText("");
         ((JTextField) component[4]).setText("");
         ((JTextField) component[5]).setText("");
      }
      else if(command.equals("Reset Directories")){         
         ((JCheckBox) component[6]).setSelected(false);
         ((JCheckBox) component[7]).setSelected(false);
         ((JCheckBox) component[8]).setSelected(false);
         ((JCheckBox) component[6]).setText("");
         ((JCheckBox) component[7]).setText("");
         ((JCheckBox) component[8]).setText("");
         setBuildMenuState();
         setSQLTagsIncludeCB();
      }
      else if(command.equals("Reset Jar Content")){
         ((JCheckBox) component[9]).setSelected(false);
         ((JCheckBox) component[10]).setSelected(false);
         ((JCheckBox) component[11]).setSelected(false);
         setSQLTagsIncludeCB();
      }
      else if(command.equals("Exit")){
         int confirm=JOptionPane.showOptionDialog(frame,
            "Are you sure you want to exit?", "Exit Confirmation",
            JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,
            null, null, null);

         if (confirm == 0){
            frame.dispose();
            System.exit(0);
         }
      }
      else if(command.equals("JDBC Generator")){
          if(properties!=null){
              updateProperties();
              createDirectories();

              Thread workerThread=new Thread(this);  
              workerThread.start();  
          }
      }
      else if(command.equals("Rebuild Jar")){
         buildMenu.setEnabled(false);
         new GeneratorProperties(createHashProperties());
         String jarFilename=properties.getProperty("jarFilename","");

         if(jarFilename.trim().length()>0){       
             new CreateJar();
             setTextArea(properties.getProperty("jarFilename","")+
                " successfully built and placed in "+
                properties.getProperty("whereToPlaceJar")+".\n");
         }
         else{
             StringBuffer buffer=new StringBuffer("You need to specify a ");
             buffer.append("jarfile name before\n");
             buffer.append("using this option. Operation aborted!");
             showInfoMessage(buffer.toString());
         }
         
         buildMenu.setEnabled(true);
      }
      else if(command.equals("Load")){
         try{
            jdbcItem.setEnabled(true);
            rebuildJar.setEnabled(true);
            fileChooser.setDialogTitle("Load Generator Property File");
            int returnVal = fileChooser.showOpenDialog(frame);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
               File file = fileChooser.getSelectedFile();
               loadFileProperties(file);
            }
         }
         catch (Exception e) {
            System.err.println("Error: " + e);
         }
      }
      else if(command.equals("Save")){
         try{
            rebuildJar.setEnabled(true);
            fileChooser.setDialogTitle("Save Generator Property File");
            int returnVal=fileChooser.showSaveDialog(frame);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
               StringBuffer buffer=new StringBuffer();
               buffer.append("databaseDriver="+
                    ((JTextField) component[0]).getText()+"\n");

               buffer.append("urlString="+
                    ((JTextField) component[1]).getText()+"\n");

               buffer.append("userName="+
                    ((JTextField) component[2]).getText()+"\n");

               buffer.append("password="+
                    ((JTextField) component[3]).getText()+"\n");

               buffer.append("metadataSchema="+
                    ((JTextField) component[4]).getText()+"\n");

               buffer.append("packageName="+
                    ((JTextField) component[5]).getText()+"\n");

               buffer.append("aitworksPackageBase="+
                    ((JCheckBox) component[6]).getText()+"\n");

               String propertyValue=((JCheckBox)component[7]).getText();
               if(propertyValue!=null&&propertyValue.trim().length()<=0)
                   propertyValue="";
               buffer.append("tmpWorkDir="+propertyValue+"\n");
               
               buffer.append("includeSource="+
                    ((JCheckBox)component[9]).isSelected()+""+"\n");
               buffer.append("includeClasses="+
                    ((JCheckBox)component[10]).isSelected()+""+"\n");
               buffer.append("generatedClasses="+((JCheckBox) 
                    component[11]).isSelected()+""+"\n");
               
               buffer.append("whereToPlaceJar="+ properties.getProperty("whereToPlaceJar","")+"\n"); 
               buffer.append("jarFilename="+ properties.getProperty("jarFilename","")+"\n"); 
             
               // are we on a dos system?
               if(File.separator.equals("\\")){
                   int bufferSize=buffer.length();
                   StringBuffer newBuffer=new StringBuffer();

                   for(int index=0;index<bufferSize;index++){
                       char currentCharacter=buffer.charAt(index);
                       if(currentCharacter=='\\')
                            newBuffer.append("\\");
                       if(currentCharacter=='\n')
                            newBuffer.append("\r");
                       newBuffer.append(currentCharacter);
                   }
                   buffer=new StringBuffer(newBuffer.toString());
                }

                File file = fileChooser.getSelectedFile();
                FileWriter writer=new FileWriter(file);
                writer.write(buffer.toString().toCharArray());
                writer.close();
            }
         }
         catch (Exception exception) {
            System.err.println("GeneratorGUI.actionPerformed: " + exception);
            setTextArea("GeneratorGUI.actionPerformed: " + exception);
         }
      }
      else if(command.equals("SQLTags Jar")){
         String dir=getFileLocation("SQLTags Jarfile Location",8,"jar",
                            JFileChooser.FILES_ONLY);
         
          properties.put("whereToPlaceJar",dir);      
          ((JCheckBox)component[8]).setSelected(false);
          ((JCheckBox)component[8]).setText(dir + File.separator+ properties.getProperty("jarFilename") );
      }
      else if(command.equals("Package Base")){
         getFileLocation("SQLTags Package Base Directory",6,"",
                JFileChooser.DIRECTORIES_ONLY); 
           String baseDir=((JCheckBox)component[6]).getText();
           
           if(baseDir!=null)
             properties.put("aitworksPackageBase",baseDir);
           
          ((JCheckBox)component[6]).setSelected(false);
      }
      else if(command.equals("SQLTags WorkDir")){
         getFileLocation("SQLTags Build Directory",7,"",
                JFileChooser.DIRECTORIES_ONLY); 
         String workDir=((JCheckBox)component[7]).getText();
         
         if(workDir==null)
             workDir="";
         
          properties.put("tmpWorkDir",workDir);
          ((JCheckBox)component[7]).setSelected(false);
      }
   }// actionPerformed() ENDS

   /**  
    * <code>fileExtension</code>  
    * <p>  
    * This method returns the current file extension
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param file <code>File</code> a file
    * @return  filenameExtension<code>String</code>The files extension.
    */  
   //---------------------------------------------------------------------------  
   public static String fileExtension(File file){
   //---------------------------------------------------------------------------  
      String filenameExtension= null;
      String filename = file.getName();
      int index=filename.lastIndexOf('.');

      if (index>0&&index<filename.length()-1)
         filenameExtension= filename.substring(index+1).toLowerCase();

      return filenameExtension;
   }// fileExtension() ENDS
   /**  
    * <code>getTextArea</code>  
    * <p>  
    * This method sets the text area text data.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
   public static JTextArea getTextArea(){
   //---------------------------------------------------------------------------  
      return textArea;
   }//getTextArea() ENDS
   /**  
    * <code>main</code>  
    * <p>  
    * This method displays the generatorg.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param args <code>String[]</code> command line arguments 
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
   public static void main(String[] args){ 
   //---------------------------------------------------------------------------  
       InputStream inputStream = null;
      if(args.length==0)
         new GeneratorGUI(); 
      else if(args.length==1)
         new GeneratorGUI(args[0]);
      else if(args.length==2){
          if(args[0].trim().equalsIgnoreCase("-nogui")){
                try {
                    inputStream = new FileInputStream(args[1]);
                } catch (Exception e) {
                    System.out.println("Invalid Properties file: "+e);
                }
                if(inputStream!=null)
                    new SQLTagsGeneratorTable(inputStream);
                else
                    new GeneratorGUI();
         }
          else
              new GeneratorGUI();
      }
      else
          new GeneratorGUI();
   } //main() ENDS

   /**  
    * <code>run</code>  
    * <p>  
    * This method starts the thread
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code></code> 
    */  
   //---------------------------------------------------------------------------  
    public void run(){
   //---------------------------------------------------------------------------  
        updateProperties();
        buildMenu.setEnabled(false);
      //  new SQLTagsGeneratorTable(properties,textArea,buildMenu);
        new SQLTagsGeneratorTable(properties);
    }//run() ENDS
    
   
   /**  
    * <code>usage</code>  
    * <p>  
    * This method displays a usage method when main detects an error.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code></code> 
    */  
   //---------------------------------------------------------------------------  
   static void usage(){
   //---------------------------------------------------------------------------  
      System.out.println("usage:\n");
      System.out.println("The first two options will invoke the GUI.\n");
      System.out.println("The third option runs the generator.\n");
      System.out.println("   java GeneratorGUI\n");
      System.out.println("          or\n");
      System.out.println("   java GeneratorGUI propertiesFile\n");
      System.out.println("   java GeneratorGUI propertiesFile -nogui\n");
   }//usage() ENDS
   
   //*************************************************************************** 
   //Private Methods 
   //***************************************************************************   
   /**  
    * <code>createDirectories</code>  
    * <p>  
    * This methods create the package name work directories
    * </p>  
    * @author  Booker Northington II  
    * @param none <code>none</code> none
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
   private void createDirectories(){
   //---------------------------------------------------------------------------  
        String packagePath = properties.getProperty("tmpWorkDir","")
                          + File.separator
                          + properties.getProperty("packageName","").replace('.',File.separatorChar)
                          ;
        File packageFile = new File ( packagePath );
        packageFile.mkdirs();
        
        // Just in case both tmpWorkDir and packageName are empty!!!
        if( !packagePath.equals(File.separator) ) {
            // Cleanup old files from previous run is important when tables are dropped!
            // Maybe a good idea to filter for *.class and *.java ... 
            // may also be a good idea to have a flag in UI that controls this ... 
            File[] fileList = packageFile.listFiles();
            for( int i = 0; i< fileList.length; i++) {
                // System.out.println("Deleting file: " + fileList[i].getName() );
                fileList[i].delete();
            }
        }
        properties.setProperty("outputDirectory",packagePath);
   }//createDirectories() END


   /**  
    * <code>createHashProperties</code>  
    * <p>  
    * This methods sets the current properties into a hashtable..
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
   private Hashtable createHashProperties(){
   //---------------------------------------------------------------------------  
      Hashtable hash=new Hashtable();
      hash.put("databaseDriver",((JTextField) component[0]).getText());
      hash.put("urlString",((JTextField) component[1]).getText());
      hash.put("userName",((JTextField) component[2]).getText()); 
      hash.put( "password",((JTextField) component[3]).getText()); 
      hash.put("metadataSchema",((JTextField) component[4]).getText());
      hash.put("packageName",((JTextField) component[5]).getText()); 
      hash.put("aitworksPackageBase",((JCheckBox) component[6]).getText());      
      String propertyValue=((JCheckBox)component[7]).getText();
      
      if(propertyValue!=null&&propertyValue.trim().length()<=0)
        propertyValue=sqltagsWorkDir;
      
      hash.put("outputDirectory",propertyValue+File.separator+
        ((String)hash.get("packageName")).replace('.',File.separatorChar));
      
      hash.put("tmpWorkDir",propertyValue);    
      propertyValue=((JCheckBox) component[8]).getText();
      int start=propertyValue.lastIndexOf(File.separator);
      
      if(start!=-1){
          String substring=propertyValue.substring(start+1,
                propertyValue.length());
          propertyValue=substring;
      }
 
      hash.put("whereToPlaceJar",properties.getProperty("whereToPlaceJar","")); 

      if(propertyValue==null)
        propertyValue="";

      hash.put("jarFilename",propertyValue);  
      hash.put("includeSource",((JCheckBox)component[9]).isSelected()+"");
      hash.put("includeClasses",((JCheckBox)component[10]).isSelected()+"");       
      hash.put("generatedClasses",(((JCheckBox)component[11]).isSelected()+""));
      hash.put("taglibName",GeneratorProperties.TagLibName); 
      hash.put("textArea",textArea);
      return hash;
   }//createHashProperties() ENDS
   
   /**  
    * <code>directoryWalk</code>  
    * <p>  
    * Check the directory path and see if any java or class file reside there.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param file <code>File[]</code> an array of file objects.
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
   private void directoryWalk(File[] file){
   //---------------------------------------------------------------------------  
    if(file!=null&&file.length>0){
        int fileCount=file.length;

        for(int count=0;count<fileCount;count++){
            File[] innerFile = file[count].listFiles();
            
            if(innerFile!=null){
                int innerFileSize=innerFile.length;

                if(foundClassFile&&foundJavaFile)
                    break;

                for (int index=0;index<innerFileSize;index++){
                  File currentFile=innerFile[index];
                  String name=currentFile.getName();

                  if (currentFile.isFile()){
                     if(name.endsWith(".java"))
                        foundJavaFile=true;
                     if(name.endsWith(".class"))
                         foundClassFile=true;
                  }
                  else if(currentFile.isDirectory()){
                    File[] recursiveFile=new File[1];
                    recursiveFile[0]=currentFile;
                    directoryWalk(recursiveFile);
                  }
               }
            }
        }
    }
  }//directoryWalk() ENDS
   
   /**  
    * <code>getFileLocation</code>  
    * <p>  
    * This method uses a file chooser to obtain a file from the os.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
   private String getFileLocation(String title,int index,String filter,
            int type){
   //---------------------------------------------------------------------------  
      checkboxChooser = new JFileChooser();
      checkboxChooser.setFileSelectionMode(type);
      checkboxChooser.setDialogTitle(title);
      checkboxChooser.setFileFilter(new PropertyFilter(filter));
      String topLevelDirectory="";
      File file=null;
      
        try{       
            int returnValue=checkboxChooser.showOpenDialog(frame);
            boolean validBaseDir=false;
            
            if (returnValue==JFileChooser.APPROVE_OPTION) {
                file = checkboxChooser.getSelectedFile();
                validBaseDir=true;
                
                if(checkboxChooser.isDirectorySelectionEnabled())
                  ((JCheckBox)component[index]).setText(file.getAbsolutePath());
                else{
                  properties.put("jarFilename",file.getName());
                  ((JCheckBox)component[index]).setText(file.getName());
                }
                
                topLevelDirectory=file.getParent();
            }
            else
                topLevelDirectory=(String)properties.getProperty
                    ("whereToPlaceJar","");
            
            if(returnValue!=JFileChooser.CANCEL_OPTION){
                if(index==6){
                    foundJavaFile=false;
                    foundClassFile=false;
                    directoryWalk(file.listFiles());
                    StringBuffer buffer=new StringBuffer();

                    if(!foundJavaFile&&!foundClassFile){
                        buffer.append("No .class or .java files found under\n");
                        buffer.append(((JCheckBox)component[index]).getText());
                        showInfoMessage(buffer.toString());
                    }
                    else if(!foundJavaFile||!foundClassFile){
                        if(!foundJavaFile){
                            buffer.append("No .java files found under\n");
                            buffer.append(((JCheckBox)component[6]).getText());
                        }
                        else{
                            buffer.append("No .class files found under\n");
                            buffer.append(((JCheckBox)component[6]).getText());
                        }

                        showInfoMessage(buffer.toString());
                    }
                    setSQLTagsIncludeCB(); 
                }
                else if(index==7)
                    setBuildMenuState();             
            }
        }
        catch (Exception e) {
            System.err.println("Error: " + e);
        }
      
        ((JCheckBox)component[index]).setSelected(false);
        return topLevelDirectory;
   }//getFileLocation() ENDS

   /**  
    * <code>loadFileProperties</code>  
    * <p>  
    * This method load the property file.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
   private void loadFileProperties(File file){
   //---------------------------------------------------------------------------  
      try{
         String filename=file.getName();
         String newFilename=filename.substring(1,filename.length());
         String firstChar=filename.charAt(0)+"";
         newFilename=firstChar.toUpperCase()+newFilename;
         borderOne.setTitle(newFilename+" Properties");
         textAreaBorder.setTitle(newFilename+" Output");
         properties=new Properties();
         FileInputStream inputStream=new FileInputStream(file);
         PropertyResourceBundle bundle=
            new PropertyResourceBundle(inputStream);
         Enumeration enum=bundle.getKeys();

         for(;enum.hasMoreElements();){
            String key=(String)enum.nextElement();
            properties.setProperty(key,bundle.getString(key));
         }        
         
         if(!runFromCommandLine)
            updateTextFieldValues();
         
        setSQLTagsIncludeCB();         
        setBuildMenuState();                
      }
      catch(Exception exception){
         System.err.println("GeneratorGUI.loadFileProperties: " + exception);
         setTextArea("GeneratorGUI.loadFileProperties: " + exception);
      }
   }//loadProperties() END
   
   /**  
    * <code>setBuildMenuEnabled</code>  
    * <p>  
    * This method controls the state of the build menu item.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param state <code>boolean</code>the state
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
   public static void setBuildMenuEnabled(boolean state){
   //---------------------------------------------------------------------------  
      buildMenu.setEnabled(state);
   }//setBuildMenuEnabled() ENDS

   /**  
    * <code>setBuildMenuState</code>  
    * <p>  
    * This method maintains the select state for the build menu items.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
    private void setBuildMenuState(){
   //---------------------------------------------------------------------------  
     String buildMenu=((JCheckBox)component[7]).getText();
     
     if(buildMenu!=null&&buildMenu.trim().length()>0){
         jdbcItem.setEnabled(true);
         rebuildJar.setEnabled(true);
      }
      else{
         jdbcItem.setEnabled(false);
         rebuildJar.setEnabled(false);
      }

   }//setBuildMenuState() ENDS

   /**  
    * <code>setFontBackground</code>  
    * <p>  
    * This method sets the font and background color.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
   private void setFontBackground(Color pColor){      
      // sync all colors
      Font menuFont=new Font("Helvetica",Font.PLAIN,10);
      aboutMenuItem.setBackground(pColor);
      rebuildJar.setBackground(pColor);
      loadItem.setBackground(pColor);
      saveItem.setBackground(pColor);
      textArea.setBackground(pColor);
      menuBar.setBackground(pColor);
      fileMenu.setBackground(pColor);
      buildMenu.setBackground(pColor);
      exitItem.setBackground(pColor);
      jdbcItem.setBackground(pColor);
      toolsMenu.setBackground(pColor);
      helpMenu.setBackground(pColor);
      clearTextField.setBackground(pColor);
      resetDirectories.setBackground(pColor);
      resetJar.setBackground(pColor);
      clearOutputArea.setBackground(pColor);

      //set fonts
      rebuildJar.setFont(menuFont);
      loadItem.setFont(menuFont);
      saveItem.setFont(menuFont);
      fileMenu.setFont(menuFont);
      exitItem.setFont(menuFont);
      buildMenu.setFont(menuFont);
      toolsMenu.setFont(menuFont);
      helpMenu.setFont(menuFont);
      aboutMenuItem.setFont(menuFont);
      clearTextField.setFont(menuFont);
      resetDirectories.setFont(menuFont);
      resetJar.setFont(menuFont);
      jdbcItem.setFont(menuFont);
      clearOutputArea.setFont(menuFont);
      textArea.setFont(textAreaFont);
      aboutMenuItem.setFont(menuFont);
   }//setFontBackground()
   
   /**  
    * <code>setSQLTagsIncludeCB</code>  
    * <p>  
    * This method maintains the select state for the file check boxes.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
   private void setSQLTagsIncludeCB(){
   //---------------------------------------------------------------------------  
     String baseDir=((JCheckBox)component[6]).getText();

     if(baseDir!=null&&baseDir.length()>0){
         ((JCheckBox)component[9]).setEnabled(true);
         ((JCheckBox)component[10]).setEnabled(true);
      }
      else{
         ((JCheckBox)component[9]).setEnabled(false);
         ((JCheckBox)component[10]).setEnabled(false);
         ((JCheckBox)component[9]).setSelected(false);
         ((JCheckBox)component[10]).setSelected(false);
      }
   }//setSQLTagsIncludeCB() ENDS
   
   /**  
    * <code>setText</code>  
    * <p>  
    * This method sets the text area text data.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
   public static void setTextArea(String data){
   //---------------------------------------------------------------------------  
      textArea.append(data);
      textArea.setCaretPosition(textArea.getText().length());
   }//setText() ENDS
   
   /**  
    * <code>setToolTip</code>  
    * <p>  
    * This method sets the tool tips for each component.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
   private void setToolTipText(){
   //---------------------------------------------------------------------------  
     labelArray[0].setToolTipText("The driver used to talk to the database.");
     labelArray[1].setToolTipText("The physical connection to the database.");
     labelArray[2].setToolTipText("The user name used to connect to the database.");
     labelArray[3].setToolTipText("The user password.");
     labelArray[4].setToolTipText("The database schema name. Use \"null\" when connecting to an Oracle database.");
     labelArray[5].setToolTipText("The package your generated files will be placed under. Normally in the form \"com.CompanyName.DatabaseName\".");
     labelArray[6].setToolTipText("The top level directory where our SQLTag package is located.");
     labelArray[7].setToolTipText("The base directory where your generated SQLTags source is placed.");
     labelArray[8].setToolTipText("The name of the generated jar file and its storage location.");
     labelArray[9].setToolTipText("Selecting this option places our SQLTags source in the jar.");
     labelArray[10].setToolTipText("Selecting this option places our SQLTags classes in the jar.");
     labelArray[11].setToolTipText("Selecting this option places the generated source files in the jar.");
   }//setToolTip() ENDS

   /**  
    * <code>showInfoMessage</code>  
    * <p>  
    * This method display info messages to the user.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
    private void showInfoMessage(String message){
   //---------------------------------------------------------------------------  
        JOptionPane.showMessageDialog(frame.getContentPane(),message);
   }//showInfoMessage() ENDS
   
     
   /**  
    * <code>updateProperties</code>  
    * <p>  
    * Updates the properties object with the current textfield values..
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
   private void updateProperties(){
   //---------------------------------------------------------------------------  
      properties.setProperty("databaseDriver",((JTextField) 
        component[0]).getText());
      properties.setProperty("urlString",((JTextField) component[1]).getText());
      properties.setProperty("userName",((JTextField) component[2]).getText()); 
      properties.setProperty("password",((JTextField) component[3]).getText()); 
      properties.setProperty("metadataSchema",((JTextField) 
        component[4]).getText());
      properties.setProperty("packageName",((JTextField) 
        component[5]).getText());       
      properties.setProperty("aitworksPackageBase",((JCheckBox) 
        component[6]).getText());
      String propertyValue=((JCheckBox)component[7]).getText();
      setBuildMenuState();                

      if(propertyValue!=null&&propertyValue.trim().length()<=0)
        propertyValue="";
      
      properties.setProperty("tmpWorkDir",propertyValue);      
      propertyValue=((JCheckBox) component[8]).getText();     
      int start=propertyValue.lastIndexOf(File.separator);
      
      if(start!=-1){
          String substring=propertyValue.substring(start+1,
            propertyValue.length());
          propertyValue=substring;
      }

      
      if(propertyValue==null)
           propertyValue="";

      properties.setProperty("jarFilename",propertyValue); 
      properties.setProperty("includeSource",((JCheckBox)
        component[9]).isSelected()+"");
      properties.setProperty("includeClasses",((JCheckBox)
        component[10]).isSelected()+"");
      properties.setProperty("generatedClasses",((JCheckBox)
        component[11]).isSelected()+"");
      properties.setProperty("taglibName",GeneratorProperties.TagLibName); 
      return;
   }//updateProperties() ENDS
   
   /**  
    * <code>updateTextFieldValues</code>  
    * <p>  
    * This method update the property text fields on the gui.
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none <code>none</code> none
    * @return  <code>none</code> 
    */  
   //---------------------------------------------------------------------------  
   private void updateTextFieldValues(){
   //---------------------------------------------------------------------------  
      ((JTextField) component[0]).setText(properties.getProperty
        ("databaseDriver",""));
      ((JTextField) component[1]).setText(properties.getProperty
        ("urlString",""));
      ((JTextField) component[2]).setText(properties.getProperty
        ("userName",""));
      ((JTextField) component[3]).setText(properties.getProperty
        ("password",""));
      ((JTextField) component[4]).setText(properties.getProperty
        ("metadataSchema",""));
      ((JTextField) component[5]).setText(properties.getProperty
        ("packageName",""));
      String propertyValue=properties.getProperty("aitworksPackageBase","");
      
      ((JCheckBox)component[6]).setText(propertyValue); 
      propertyValue=properties.getProperty("tmpWorkDir","");            
      ((JCheckBox)component[7]).setText(propertyValue);      
      propertyValue=properties.getProperty("jarFilename","");           
      String whereDir=properties.getProperty("whereToPlaceJar","");
      
      if(!whereDir.endsWith(File.separator))
          whereDir=whereDir+File.separator;
      
      if(propertyValue.length()>0)
        ((JCheckBox)component[8]).setText(whereDir+propertyValue);      
      
      Boolean value=new Boolean(properties.getProperty("includeSource",""));
      ((JCheckBox) component[9]).setSelected(value.booleanValue());
      value=new Boolean(properties.getProperty("includeClasses",""));
      ((JCheckBox) component[10]).setSelected(value.booleanValue());
    }// updateTextFieldValues() ENDS

   
   //*************************************************************************** 
   // Class Variables 
   //***************************************************************************
   private AboutSQLTags aboutSQLTags;
   private JMenu helpMenu = new JMenu("Help");
   private   JMenu fileMenu=new JMenu("File");
   private    JMenuItem exitItem=new JMenuItem("Exit");
   private    JMenuItem loadItem=new JMenuItem("Load");
   private    JMenuItem saveItem=new JMenuItem("Save");
   private JMenuBar menuBar = new JMenuBar(); 
   private static JMenu buildMenu=new JMenu("Build");
   private   JMenu toolsMenu=new JMenu("Tools");
   private   JMenuItem clearOutputArea=new  JMenuItem("Clear Output Area");
   private   JMenuItem clearTextField=new   JMenuItem("Clear Textfield's");
   private   JMenuItem resetDirectories=new JMenuItem("Clear Directory Checkbox's");
   private   JMenuItem resetJar=new         JMenuItem("Clear Jar Content Checkbox's");      
   private Class className=this.getClass();
   private JMenuItem aboutMenuItem;
   private JButton dismiss;
   private JButton execute;
   private final JFileChooser fileChooser;
   private JDialog filesNotFound;
   private boolean foundJavaFile=false;
   private boolean foundClassFile=false;
   private JFrame frame;
   private JCheckBoxMenuItem classesMenuItem;
   private JCheckBoxMenuItem generatedMenuItem;
   private JMenuItem jdbcItem;
   private JLabel[] labelArray={ 
      new JLabel("DB Driver Class",POSITION),
      new JLabel("JDBC Connection ",POSITION),
      new JLabel("User",POSITION), 
      new JLabel("Password",POSITION), 
      new JLabel("Schema",POSITION),
      new JLabel("Package Name ",POSITION), 
      new JLabel("SQLTags Package Directory ",POSITION),
      new JLabel("Build Directory",POSITION),
      new JLabel("SQLTags Jar File",POSITION), 
      new JLabel("Include SQLTags Source",POSITION),
      new JLabel("Include SQLTags Classes",POSITION),
      new JLabel("Include Generated Source",POSITION)        
   };   
   
   private JPanel outputPanel;
   private JFileChooser checkboxChooser;
   private static final int POSITION=JLabel.LEFT;
   private Properties properties;
   public final static String propertiesExtension = "properties";
   public String propertyFileLocation="";
   PropertyFilter propertyFilter;
   private BevelBorder raisedBorder = new BevelBorder(BevelBorder.RAISED);
   private JMenuItem rebuildJar;
   private JCheckBoxMenuItem sourceMenuItem;
   private Boolean testForTrue=new Boolean(true);
   private Boolean testForFalse=new Boolean(false);
   private static JTextArea textArea;
   private TitledBorder textAreaBorder;
   private Component[] component={ 
      new JTextField("",40), 
      new JTextField("",40), 
      new JTextField("",40), 
      new JTextField("",40), 
      new JTextField("",40), 
      new JTextField("",40), 
      new JCheckBox(), 
      new JCheckBox(), 
      new JCheckBox(), 
      new JCheckBox(), 
      new JCheckBox(), 
      new JCheckBox()
    }; 
   private boolean runFromCommandLine=false;
   private String sqltagsWorkDir="."+File.separator;
   private TitledBorder borderOne;
   // private Utilities utilities=new Utilities();
   private Font titleFont=new Font("TimesNewRoman",Font.PLAIN+Font.BOLD,12);
   private Font textAreaFont=new Font("TimesRoman",Font.PLAIN,12);
}//GeneratorGUI() ENDS