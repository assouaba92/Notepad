
package notepad;

import javax.swing.event.*;

public class MyDocListener implements DocumentListener{
    
    public void insertUpdate(DocumentEvent e)
    {
        Notepad note = (Notepad) e.getDocument().getProperty("owner");
        note.flag=true;
    }
    
    public void removeUpdate(DocumentEvent e)
    {
       Notepad note = (Notepad) e.getDocument().getProperty("owner");
       note.flag=true;
    }
    
    public void changedUpdate(DocumentEvent e)
    {
       Notepad note = (Notepad) e.getDocument().getProperty("owner");
       note.flag=true;
    }
}
