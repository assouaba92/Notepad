//To change this template, choose Tools | Templates and open the template in the editor

package notepad;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.awt.GraphicsEnvironment;
import java.awt.event.*; 
import javax.swing.filechooser.*;
import javax.swing.text.Highlighter;

public class Notepad extends JFrame {

    public Notepad() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setTitle("Notepad");
        fileName=new String("Untitled");
        
        addWindowListener(new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent e) 
            {
                if(flag)
                  newFile();
            }
        });
        
        createComponents();
    }
    
    void createComponents() {
        createMainWindow();
        createMenubar();
        createToolbar();
    }
    
    void createMainWindow() {
        text=new JTextArea();
        add(new JScrollPane(text), BorderLayout.CENTER);
        text.setLineWrap(true);
        text.getDocument().putProperty("owner", this);
        text.getDocument().addDocumentListener(new MyDocListener()); 
    }
    
    void createMenubar() {
        fileMenu =new JMenu("File");
        newItem=fileMenu.add(new JMenuItem("New"));
        openItem=fileMenu.add(new JMenuItem("Open"));
        fileMenu.add(new JSeparator());
        saveItem=fileMenu.add(new JMenuItem("Save"));
        saveAsItem=fileMenu.add(new JMenuItem("Save As"));
        fileMenu.add(new JSeparator());
        exitItem=fileMenu.add(new JMenuItem("Exit"));
        
        fileMenu.setMnemonic('F');
        newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
        openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        
        newItem.addActionListener(new ActionListener(){
             public void actionPerformed(ActionEvent event)
               {
                newFile();
               }
            });
        openItem.addActionListener(new ActionListener(){
             public void actionPerformed(ActionEvent event)
               {
                openFile();
               }
            });
        saveItem.addActionListener(new ActionListener(){
             public void actionPerformed(ActionEvent event)
               {
                saveFile();
               }
            });
        saveAsItem.addActionListener(new ActionListener(){
             public void actionPerformed(ActionEvent event)
               {
                saveFileAs();
               }
            });
        exitItem.addActionListener(new ActionListener(){
             public void actionPerformed(ActionEvent event)
               {
                exit();
               }
            });
        
        editMenu=new JMenu("Edit");
        cutItem=editMenu.add(new JMenuItem("Cut"));
        copyItem=editMenu.add(new JMenuItem("Copy"));
        pasteItem=editMenu.add(new JMenuItem("Paste"));
        editMenu.add(new JSeparator());
        findItem=editMenu.add(new JMenuItem("Find"));
        findNextItem=editMenu.add(new JMenuItem("Find Next"));
        
        editMenu.setMnemonic('E');
        cutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
        copyItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
        pasteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
        findItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
        findNextItem.setAccelerator(KeyStroke.getKeyStroke("F3"));
        
        cutItem.addActionListener(new ActionListener(){
             public void actionPerformed(ActionEvent event)
               {
                cut();
               }
            });
        copyItem.addActionListener(new ActionListener(){
             public void actionPerformed(ActionEvent event)
               {
                copy();
               }
            });
        pasteItem.addActionListener(new ActionListener(){
             public void actionPerformed(ActionEvent event)
               {
                paste();
               }
            });
        findItem.addActionListener(new ActionListener(){
             public void actionPerformed(ActionEvent event)
               {
                find();
               }
            });
        findNextItem.addActionListener(new ActionListener(){
             public void actionPerformed(ActionEvent event)
               {
                findNext();
               }
            });
        
        helpMenu =new JMenu("Help");
        aboutItem=helpMenu.add(new JMenuItem("About"));
        
        helpMenu.setMnemonic('H');
        
        aboutItem.addActionListener(new ActionListener(){
             public void actionPerformed(ActionEvent event)
               {
                about();
               }
            });
        
        JMenuBar menu=new JMenuBar();
        setJMenuBar(menu);
        menu.add(fileMenu);
        menu.add(editMenu);
        menu.add(helpMenu);
    }
    
    void createToolbar() {
        try {
            BufferedImage newImage = ImageIO.read(new File("New.png"));
            newButton = new JButton(new ImageIcon(newImage));
            newButton.setPreferredSize(new Dimension(newImage.getWidth() + TOOLBAR_BUTTON_MARGIN, newImage.getHeight() + TOOLBAR_BUTTON_MARGIN));
            newButton.setToolTipText("New");
            newButton.addActionListener(new ActionListener(){
             public void actionPerformed(ActionEvent event)
               {
                newFile();
               }
            });
            
            BufferedImage openImage = ImageIO.read(new File("Open.png"));
            openButton = new JButton(new ImageIcon(openImage));
            openButton.setPreferredSize(new Dimension(openImage.getWidth() + TOOLBAR_BUTTON_MARGIN, openImage.getHeight() + TOOLBAR_BUTTON_MARGIN));
            openButton.setToolTipText("Open");
            openButton.addActionListener(new ActionListener(){
             public void actionPerformed(ActionEvent event)
               {
                openFile();
               }
            });
            
            BufferedImage saveImage = ImageIO.read(new File("Save.png"));
            saveButton = new JButton(new ImageIcon(saveImage));
            saveButton.setPreferredSize(new Dimension(saveImage.getWidth() + TOOLBAR_BUTTON_MARGIN, saveImage.getHeight() + TOOLBAR_BUTTON_MARGIN));
            saveButton.setToolTipText("Save");
            saveButton.addActionListener(new ActionListener(){
             public void actionPerformed(ActionEvent event)
               {
                saveFile();
               }
            });
            
            BufferedImage copyImage = ImageIO.read(new File("Copy.png"));
            copyButton = new JButton(new ImageIcon(copyImage));
            copyButton.setPreferredSize(new Dimension(copyImage.getWidth() + TOOLBAR_BUTTON_MARGIN, copyImage.getHeight() + TOOLBAR_BUTTON_MARGIN));
            copyButton.setToolTipText("Copy");
            copyButton.addActionListener(new ActionListener(){
             public void actionPerformed(ActionEvent event)
               {
                copy();
               }
            });
                       
            BufferedImage cutImage = ImageIO.read(new File("Cut.png"));
            cutButton = new JButton(new ImageIcon(cutImage));
            cutButton.setPreferredSize(new Dimension(cutImage.getWidth() + TOOLBAR_BUTTON_MARGIN, cutImage.getHeight() + TOOLBAR_BUTTON_MARGIN));
            cutButton.setToolTipText("Cut");
            cutButton.addActionListener(new ActionListener(){
             public void actionPerformed(ActionEvent event)
               {
                cut();
               }
            });
            
            BufferedImage pasteImage = ImageIO.read(new File("Paste.png"));
            pasteButton = new JButton(new ImageIcon(pasteImage));
            pasteButton.setPreferredSize(new Dimension(pasteImage.getWidth() + TOOLBAR_BUTTON_MARGIN, pasteImage.getHeight() + TOOLBAR_BUTTON_MARGIN));
            pasteButton.setToolTipText("Paste");
            pasteButton.addActionListener(new ActionListener(){
             public void actionPerformed(ActionEvent event)
               {
                paste();
               }
            });
            
            BufferedImage findImage = ImageIO.read(new File("Find.png"));
            findButton = new JButton(new ImageIcon(findImage));
            findButton.setPreferredSize(new Dimension(findImage.getWidth() + TOOLBAR_BUTTON_MARGIN, findImage.getHeight() + TOOLBAR_BUTTON_MARGIN));
            findButton.setToolTipText("Find");
            findButton.addActionListener(new ActionListener(){
             public void actionPerformed(ActionEvent event)
               {
                find();
               }
            });
            
            fontCombo = new JComboBox();
            fontCombo.setEditable(true);
            String fonts[] = 
            GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

            for (int i=0;i<fonts.length;i++ )
             {
                 fontCombo.addItem(fonts[i]);
             }
            fontCombo.setSelectedItem("Serif");
            
            fontSizeCombo = new JComboBox();
            fontSizeCombo.setEditable(true);
            fontSizeCombo.addItem("8");
            fontSizeCombo.addItem("9");
            fontSizeCombo.addItem("10");
            fontSizeCombo.addItem("11");
            fontSizeCombo.addItem("12");
            fontSizeCombo.addItem("14");
            fontSizeCombo.addItem("16");
            fontSizeCombo.addItem("18");
            fontSizeCombo.addItem("22");
            fontSizeCombo.addItem("24");
            fontSizeCombo.addItem("28");
            fontSizeCombo.addItem("32");
            fontSizeCombo.addItem("36");
            fontSizeCombo.setSelectedItem("10");
            
            bold = new JCheckBox("Bold");
            italic = new JCheckBox("Italic");
        }
        catch(Exception e){ }
           
        toolbarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        toolbarPanel.add(newButton);
        toolbarPanel.add(openButton);
        toolbarPanel.add(saveButton);
        toolbarPanel.add(copyButton);
        toolbarPanel.add(cutButton);
        toolbarPanel.add(pasteButton);
        toolbarPanel.add(findButton);
        toolbarPanel.add(fontCombo);
        toolbarPanel.add(fontSizeCombo);
        toolbarPanel.add(bold);
        toolbarPanel.add(italic);
        
        getContentPane().add(toolbarPanel, BorderLayout.NORTH);
        changeFont();
    }
    
    void newFile()
    {
        if(flag)
         {       
          int choice=JOptionPane.showOptionDialog(null,"Do you want to save the current document?",
                  "Save",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,null,null);

          if(choice==JOptionPane.YES_OPTION)
          {
             saveFile();
             text.setText(null);
             fileName="Untitled";
             flag=false;
          }

          if(choice==JOptionPane.NO_OPTION)
          {
             text.setText(null);
             fileName="Untitled";
             flag=false;
          }
         }

        else if(flag==false)
         {
             text.setText(null);
             fileName="Untitled";
         }  
    }
    
    void openFile()
    {    
        JFileChooser fileChooser=new JFileChooser();
        FileNameExtensionFilter filter=new FileNameExtensionFilter("Text Files","txt");
        fileChooser.setFileFilter(filter);
        int fileChoice=fileChooser.showOpenDialog(this);
        
        if(flag)
        {
            int choice=JOptionPane.showOptionDialog(null,"Do you want to save the current document?",
                        "Save",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,null,null);

                if(choice==JOptionPane.YES_OPTION)
                {
                   saveFile();
                   BufferedReader reader;
                   StringBuilder stringBuilder = new StringBuilder(); 
                    try 
                    {
                        reader=new BufferedReader(new FileReader(fileChooser.getSelectedFile()));
                        while(reader.ready()) 
                        {
                            stringBuilder.append(reader.readLine()+"\n");
                        }
                        reader.close();
                        text.setText(stringBuilder.toString());
                        this.fileName=fileChooser.getSelectedFile().getAbsolutePath();
                    }
                    catch (IOException ioe) 
                    {
                        System.out.println("Unable to open file");
                    }
                }

                if(choice==JOptionPane.NO_OPTION)
                {
                   BufferedReader reader;
                    StringBuilder stringBuilder = new StringBuilder(); 
                    try 
                    {
                        reader=new BufferedReader(new FileReader(fileChooser.getSelectedFile()));
                        while(reader.ready()) 
                        {
                            stringBuilder.append(reader.readLine()+"\n");
                        }
                        reader.close();
                        text.setText(stringBuilder.toString());
                        this.fileName=fileChooser.getSelectedFile().getAbsolutePath();
                    }
                    catch (IOException ioe) 
                    {
                        System.out.println("Unable to open file");
                    }
                }
        }
        
        else if(flag==false)
        {             
              if (fileChoice==JFileChooser.APPROVE_OPTION) 
              {
                BufferedReader reader;
                StringBuilder stringBuilder = new StringBuilder(); 
                try 
                {
                    reader=new BufferedReader(new FileReader(fileChooser.getSelectedFile()));
                    while(reader.ready()) 
                    {
                        stringBuilder.append(reader.readLine()+"\n");
                    }
                    reader.close();
                    text.setText(stringBuilder.toString());
                    this.fileName=fileChooser.getSelectedFile().getAbsolutePath();
                }
                catch (IOException ioe) 
                {
                    System.out.println("Unable to open file");
                }
              }
        }
    }
    
    void saveFile()
    {
        if(flag==false)
            return;
        
        if(fileName.equals("Untitled"))
            saveFileAs();
        
        else
        {
            BufferedWriter writer;
            try 
            {
              writer = new BufferedWriter(new FileWriter(fileName,false));
              text.write(writer);
              writer.close();
              JOptionPane.showMessageDialog(null, "File has been saved","File Saved",
                         JOptionPane.INFORMATION_MESSAGE);
            } 

            catch (IOException ioe) 
            {
              System.out.println("Unable to save file");
            }
            flag=false;
        }      
    }
    
    void saveFileAs()
    {   
        JFileChooser fileChooser = new JFileChooser();
        int choice=fileChooser.showSaveDialog(fileChooser);
        BufferedWriter writer;
        if(choice == JFileChooser.APPROVE_OPTION)
        {
            try {
                fileName = fileChooser.getSelectedFile().getAbsolutePath()+".txt";
                writer=new BufferedWriter(new FileWriter(fileName,false));
                text.write(writer);
                writer.close();
                JOptionPane.showMessageDialog(null, "File has been saved","File Saved",
                        JOptionPane.INFORMATION_MESSAGE);
            } 

            catch (IOException ioe) 
            {
              System.out.println("Unable to save file");
            }
            flag=false;
        }      
    }
    
    void exit()
    {   
       if(flag)
           newFile();
       
       else if(flag==false)
       System.exit(0);
    }
    
    void copy()
    {
       text.copy();
    }
    
    void cut()
    {
       text.cut();
    }
    
    void paste()
    {
       text.paste();
    }
    
    void find()
    {
        input=JOptionPane.showInputDialog("Find:").toLowerCase();
        myHighliter=new MyHighlightPainter(Color.YELLOW);
        hilite=text.getHighlighter();
        hilite.removeAllHighlights();
        
        if(input!=null)
        {         
            strOrig=text.getText().toLowerCase();
            intIndex = strOrig.indexOf(input);
            if(intIndex==- 1)
            {
               JOptionPane.showMessageDialog(null,input+"not found",
                  "Find",JOptionPane.INFORMATION_MESSAGE);
               System.out.println(input+" not found");
            }
            else
            {
               try{ 
                hilite.addHighlight(intIndex,intIndex+input.length(),myHighliter);
                System.out.println("Found "+input+" at index "+intIndex);
               }
               catch(Exception e){}
            }
        }
    }
    
    void findNext()
    {
       strOrig=text.getText().substring(intIndex+input.length()).toLowerCase();
       intIndex+=input.length()+strOrig.indexOf(input);
       if(strOrig.contains(input)==false)
        {
           JOptionPane.showMessageDialog(null,input+" not found",
              "Find",JOptionPane.INFORMATION_MESSAGE); 
           System.out.println(input+" not found");
        }
        else
        {
           try{ 
                hilite.addHighlight(intIndex,intIndex+input.length(),myHighliter);
                System.out.println("Found "+input+" at index "+intIndex);
               }
               catch(Exception e){}
        }
    }
    
    void changeFont()
    {    
        ActionListener listener=new ActionListener()
        { 
           public void actionPerformed(ActionEvent event)
            {
            int mode=0;
            if (bold.isSelected()) mode |=Font.BOLD;
            if (italic.isSelected()) mode |=Font.ITALIC;
            
            text.setFont(new Font((String) fontCombo.getSelectedItem(), mode, 
                    Integer.parseInt((String) fontSizeCombo.getSelectedItem())));
            }
        };
        bold.addActionListener(listener);
        italic.addActionListener(listener);
        fontCombo.addActionListener(listener);
        fontSizeCombo.addActionListener(listener);
    }
    
    void about()
    {   
      JOptionPane.showMessageDialog(null,"Name: Adil Assouab \nProgram Name: Notepad Project",
           "About",JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void main(String[] args) {
        Notepad notepad = new Notepad();
        notepad.setVisible(true);
    }
    
    static final int TOOLBAR_BUTTON_MARGIN = 5;
    public boolean flag=false;
    
    private String fileName;
    private JTextArea text;
    
    private JMenu fileMenu;
    private JMenuItem newItem;
    private JMenuItem openItem;
    private JMenuItem saveItem;
    private JMenuItem saveAsItem;
    private JMenuItem exitItem;
        
    private JMenu editMenu;
    private JMenuItem cutItem;
    private JMenuItem copyItem;
    private JMenuItem pasteItem;
    private JMenuItem findItem;
    private JMenuItem findNextItem; 
    
    private JMenu helpMenu;
    private JMenuItem aboutItem;
        
    private JPanel toolbarPanel;
    private JButton newButton;
    private JButton openButton;
    private JButton saveButton;
    private JButton copyButton;
    private JButton cutButton;
    private JButton pasteButton;
    private JButton findButton;
    private JComboBox fontCombo;
    private JComboBox fontSizeCombo;
    private JCheckBox bold;
    private JCheckBox italic;
    
    private String input;
    private String strOrig;
    private int intIndex; 
    private Highlighter.HighlightPainter myHighliter;
    private Highlighter hilite;
}