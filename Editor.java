import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.*;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

import javax.swing.text.StyledEditorKit;

/**
 *
 * @author VEDIKA BANSAL, MEGHNA CHAUDHARY
 */
public class Editor extends JFrame implements ActionListener, KeyListener, MouseListener{
    
    String address = "";
    //Creating text components and buttons
    JTextPane e1 = new JTextPane();
    JTextField tf1 = new JTextField();
    
    JLabel l1 = new JLabel("Draw Shapes:");
    
    
    String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    
    public Editor() {
        
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        
        } catch (ClassNotFoundException ex) {
        Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
        Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
        Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
        Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
        }
        // two panels for shape and text editor
        JPanel p1 = new JPanel();
        DrawPanel p2 = new DrawPanel();
        
        setLayout(new GridLayout(1,2));
        //creating menu bar with various menius
        JMenuBar mb = new JMenuBar();
        
        JMenu m1 = new JMenu("Edit");
        JMenu m2 = new JMenu("File");
        JMenu m3 = new JMenu("Find&Replace");
        
        JMenu miFont = new JMenu("Font");
        JMenu miSize = new JMenu("Size");
        JMenu miCase = new JMenu("Case");
        
        //adding menu items
        JMenuItem miCut = new JMenuItem("Cut");
        JMenuItem miCopy = new JMenuItem("Copy");
        JMenuItem miPaste = new JMenuItem("Paste");
        
        JMenuItem miFind = new JMenuItem("Find");
        JMenuItem miReplace = new JMenuItem("Replace");
        JMenuItem miReplaceAll = new JMenuItem("ReplaceAll");
        
        JMenuItem open = new JMenuItem("Open");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem saveAs = new JMenuItem("SaveAs");
        
        JMenuItem miUpper = new JMenuItem("UpperCase");
        JMenuItem miLower = new JMenuItem("LowerCase");
        
        m1.add(miCut);
        m1.add(miCopy);
        m1.add(miPaste);
             
        m2.add(open);
        m2.add(save);
        m2.add(saveAs);
        
        m3.add(miFind);
        m3.add(miReplace);
        m3.add(miReplaceAll);
        
        miCase.add(miUpper);
        miCase.add(miLower);
        
        
        //adding event listeners
        miUpper.addActionListener(this);
        miLower.addActionListener(this);
    
        miCut.addActionListener(this);
        miCopy.addActionListener(this);
        miPaste.addActionListener(this);
        
        miFont.addActionListener(this);
        miSize.addActionListener(this);
        miCase.addActionListener(this);
        
        miFind.addActionListener(this);
        miReplace.addActionListener(this);
        miReplaceAll.addActionListener(this);
        
        open.addActionListener(this);
        save.addActionListener(this);
        saveAs.addActionListener(this);
        
        e1.addMouseListener(this);
       
        mb.add(m1);
        mb.add(m2);
        mb.add(m3);
                
        mb.add(miFont);
        mb.add(miSize);
        mb.add(miCase);
        
        for (int i = 0; i<fonts.length; i++) {
            JMenuItem menuItem = new JMenuItem(fonts[i]);
            // add an action listener to change font
            menuItem.addActionListener(new StyledEditorKit.FontFamilyAction(fonts[i], fonts[i]));
            miFont.add(menuItem);
        }
        for (int i = 48; i >= 8; i -= 10) {
            JMenuItem menuItem = new JMenuItem("" + i);
            // add action listener to change size
            menuItem.addActionListener(new StyledEditorKit.FontSizeAction("myaction-" + i, i));
            miSize.add(menuItem);
        }
        tf1.setEditable(false);
        
        //adding the elements
        p1.setLayout(new BorderLayout());
        p1.add(mb, BorderLayout.NORTH);
        p1.add(e1, BorderLayout.CENTER);
        p1.add(tf1, BorderLayout.SOUTH);
        
        add(p1);
        add(p2);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        //to check which component the action was performed on
        String s = ae.getActionCommand();
        
        //Cut, Copy and Paste
        if(s.equals("Cut")){
           e1.cut();
           String d = tf1.getText();
                    System.out.println(d);
        }
        else if(s.equals("Copy")){
            e1.copy();
        }
        else if(s.equals("Paste")){
            e1.paste();
        }
        
        //Find, Replace and Replace All
        if(s.equals("Find")){
           String f = JOptionPane.showInputDialog("Enter text to find");
           
           int check = JOptionPane.NO_OPTION;
           String ed = (e1.getText()).toLowerCase();
           
          
           int start;
           int l=0;
           do{
                System.out.println(ed);
                start = ed.indexOf(f);
                l = l + start;
                System.out.println(start);
                if(l!=-1){
                    e1.setCaretPosition(l);
                    e1.moveCaretPosition(l+f.length());
                    e1.getCaret().setVisible(true);
                    e1.getCaretColor();
                    check = JOptionPane.showConfirmDialog(null, "Next?", "Confirmation", JOptionPane.YES_NO_OPTION);
                       if((e1.getSelectionEnd()+1)<e1.getText().length()){
                            ed = ed.substring(e1.getSelectionEnd()+1);
                            l = l + f.length() + 1;
                       }
                        else 
                            break;
                       
                }
                else {
               
                    break;
                }
           
           }while(check==JOptionPane.YES_OPTION);
           JOptionPane.showMessageDialog(null, "Instance of the given string Not Found");
            
        }
        
        
        else if(s.equals("Replace")){
           e1.replaceSelection(JOptionPane.showInputDialog("Enter text to be replaced with"));
        }
        else if(s.equals("ReplaceAll")){
           String r = JOptionPane.showInputDialog("Enter text to be replaced");
           String rwith = JOptionPane.showInputDialog("Enter text to be replaced with");
           
           String s1 = e1.getText();
           
            s1=s1.replaceAll(r, rwith);
            e1.setText(s1);
        }
        
        //Save, Save As and Open
        if(s.equals("SaveAs")){
            save();
        }
        else if(s.equals("Open")){
            try {
                JFileChooser j = new JFileChooser("d:");
                /*FileNameExtensionFilter filter = new FileNameExtensionFilter("text/richtext", ".rtf");
                j.setFileFilter(filter);*/
                int r = j.showOpenDialog(null);
                if (r == JFileChooser.APPROVE_OPTION) {
                    address = j.getSelectedFile().getAbsolutePath();
                    File file = new File(address);
                    String s1 = "", s2 = "";
                    FileReader fr = new FileReader(file);
                    BufferedReader br = new BufferedReader(fr);
                    s1 = br.readLine();
                    while((s2 = br.readLine())!=null){
                        s1 = s1 + "\n" + s2;
                    }
                    e1.setText(s1);
                    
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        
        else if(s.equals("Save")){
            if(address.equals("")){
                save();
            }
            else{
                try{
                    File file = new File(address);
                    FileWriter fw = new FileWriter(file, false);
                    BufferedWriter bw = new BufferedWriter(fw);
                    
                    System.out.println(e1.getText());
                    
                    bw.write(e1.getText());
                    System.out.println("Successfully saved");
                    bw.flush();
                    bw.close();
                }
                catch(Exception e){
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
                    
            }
            
        }
        
        //Upper and Lower Case
        if(s.equals("UpperCase")){
            
            try {
                e1.replaceSelection(e1.getSelectedText().toUpperCase());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Select text first");
            }
        }
        else if(s.equals("LowerCase")){
            
            try {
                e1.replaceSelection(e1.getSelectedText().toLowerCase());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Select text first");
            }
        }
        
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }
    
    
    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        
        //to diplay the number of words and characters of the selected text
        try {
            if (me.getComponent().equals(e1)) {
                String s = e1.getSelectedText();
                int words = 0;
                if (s.equals("") == false) {
                    int length = s.length();
                    for (int j = 1; j < length; j++) {
                        if (s.charAt(j) == ' ' && s.charAt(j - 1) != ' ' || s.charAt(j) == '\n' && s.charAt(j-1)!='\n' && j!=(length-1)) {
                            words++;
                        }
                    }
                    if (s.charAt(length - 1) != ' ') {
                        words++;
                    }
                    tf1.setText("Words: " + words + " Characters: " + length);
                    
                }
            }
        } catch (Exception e) {
            tf1.setText("");
        }
        
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }
    
    
    //method for saving file
    public void save(){
        try {
                JFileChooser j = new JFileChooser("d:");
                int r = j.showSaveDialog(null);
                if (r == JFileChooser.APPROVE_OPTION) {
                    //Editor editor = new Editor();
                    address = j.getSelectedFile().getAbsolutePath();
                    File file = new File(address);
                    FileWriter fw = new FileWriter(file, false);
                    BufferedWriter bw = new BufferedWriter(fw);
                    
                    System.out.println(e1.getText());
                    
                    bw.write(e1.getText());
                    System.out.println("Successfully saved");
                    bw.flush();
                    bw.close();
                   
                } else {
                    System.out.println("Cancelled");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
    }
    
    public static void main(String[] args) throws FileNotFoundException {
        Editor f = new Editor();
        f.setSize(900, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        
        
    }
    
}
class DrawPanel extends JPanel implements ActionListener, MouseMotionListener{
    
        JButton clr = new JButton("Clear");
        JPanel shapes = new JPanel();
        String set = "";
              
        int[] ox = {150,150,250,250};
        int[] oy = {300,200,200,300};
        
        int[] tx = {150,200,250};
        int[] ty = {300,200,300};
        
        int[] rx = {150,150,250,250};
        int[] ry = {300,200,200,300};
        
        int[] px = {150,150,200,250,250};
        int[] py = {300,200,100,200,300};
        
        int[] hx = {150,150,200,250,250,200};
        int[] hy = {300,200,100,200,300,400};
        
    public DrawPanel(){
        JMenuBar m = new JMenuBar();
        JMenu ms = new JMenu("Shapes");
        JMenuItem Oval = new JMenuItem("Oval");
        JMenuItem Rectangle = new JMenuItem("Rectangle");
        JMenuItem Triangle = new JMenuItem("Triangle");
        JMenuItem Pentagon = new JMenuItem("Pentagon");
        JMenuItem Hexagon = new JMenuItem("Hexagon");
        
        ms.add(Oval);
        ms.add(Triangle);
        ms.add(Rectangle);
        ms.add(Pentagon);
        ms.add(Hexagon);
        
        
        m.add(ms);
        (this).setLayout(new BorderLayout());
        add(m, BorderLayout.NORTH);
        add(clr, BorderLayout.SOUTH);
        
        Oval.addActionListener(this);
        Rectangle.addActionListener(this);
        Triangle.addActionListener(this);
        Pentagon.addActionListener(this);
        Hexagon.addActionListener(this);
        clr.addActionListener(this);
        
        
    }
    
    public void actionPerformed(ActionEvent ae) {
        if(ae.getActionCommand().equals("Oval")){
                drawing("Oval");   
            }
        else if(ae.getActionCommand().equals("Triangle")){
                drawing("Triangle");   
            }
        else if((ae.getActionCommand()).equals("Rectangle")){
                drawing("Rectangle");                           
            }
        else if((ae.getActionCommand()).equals("Pentagon")){
                drawing("Pentagon");                   
            }
        else if((ae.getActionCommand()).equals("Hexagon")){
                drawing("Hexagon");                   
            }
        addMouseMotionListener(this);
        if(ae.getActionCommand().equals("Clear")){
            (this).updateUI();
            drawing("Clear");
        }
    }
    
    public void drawing(String x){
        repaint();
        set = x;
    }
    
    //drawing the required shape
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(set.equals("Oval")){
            g.setColor(Color.black);
            double w = Math.sqrt(Math.pow(ox[1]-ox[2],2)+Math.pow(oy[1]-oy[2],2));
            double h = Math.sqrt(Math.pow(ox[1]-ox[0],2)+Math.pow(oy[1]-oy[0],2));
            g.fillOval(ox[1], oy[1], (int)w, (int)h);
        }
        else if(set.equals("Triangle")){
            g.setColor(Color.black);
            g.fillPolygon(tx, ty, 3);
        }
        else if(set.equals("Rectangle")){
            g.setColor(Color.black);
            g.fillPolygon(rx, ry, 4);
        }
        else if(set.equals("Pentagon")){
            g.setColor(Color.black);
            g.fillPolygon(px, py, 5);
        }
        else if(set.equals("Hexagon")){
            g.setColor(Color.black);
            g.fillPolygon(hx, hy, 6);
        }
        
    }
   

    //changing coordinates of shapes accoring to mouse actions
        @Override
    public void mouseDragged(MouseEvent me) {
        int i;
        if(set.equals("Oval")){
            if(me.getX()>=(ox[0]-15)&&me.getY()>=(oy[0]-15)){
                ox[0]=me.getX();
                oy[0]=me.getY();
                repaint();
            }
            else if(me.getX()>=(ox[1]-15)&&me.getY()<=(oy[1]+15)){
                ox[1]=me.getX();
                oy[1]=me.getY();
                repaint();
            }
            else if(me.getX()>=(ox[2]-15)&&me.getY()<=(oy[2]+15)){
                ox[2]=me.getX();
                oy[2]=me.getY();
                repaint();
            }
        
            else if(me.getX()>=(ox[3]-15)&&me.getY()<=(oy[3]+15)){
                ox[3]=me.getX();
                oy[3]=me.getY();
                repaint();
            }
        }
        if(set.equals("Triangle")){
            if(me.getX()>=(tx[0]-15)&&me.getY()<=(ty[0]+15)){
                tx[0]=me.getX();
                ty[0]=me.getY();
                repaint();
            }
            else if(me.getX()>=(tx[1]-15)&&me.getY()<=(ty[1]+15)){
                tx[1]=me.getX();
                ty[1]=me.getY();
                repaint();
            }
            else if(me.getX()>=(tx[2]-15)&&me.getY()<=(ty[2]+15)){
                tx[2]=me.getX();
                ty[2]=me.getY();
                repaint();
            }
        }
        if(set.equals("Rectangle")){
            if(me.getX()>=(rx[0]-15)&&me.getY()<=(ry[0]+15)){
                rx[0]=me.getX();
                ry[0]=me.getY();
                rx[1]=me.getX();
                ry[3]=me.getY();
                repaint();
            }
            else if(me.getX()>=(rx[1]-15)&&me.getY()<=(ry[1]+15)){
                rx[1]=me.getX();
                ry[1]=me.getY();
                rx[0]=me.getX();
                ry[2]=me.getY();
                repaint();
            }
            else if(me.getX()>=(rx[2]-15)&&me.getY()<=(ry[2]+15)){
                rx[2]=me.getX();
                ry[2]=me.getY();
                rx[3]=me.getX();
                ry[1]=me.getY();
                repaint();
            }
            else if(me.getX()>=(rx[3]-15)&&me.getY()<=(ry[3]+15)){
                rx[3]=me.getX();
                ry[3]=me.getY();
                rx[2]=me.getX();
                ry[1]=me.getY();
                repaint();
            }
        }
        if(set.equals("Pentagon")){
            if((me.getX()>=(px[0]-15)&&me.getX()<=(px[0]+15))&&(me.getY()<=(py[0]+15)&&me.getY()>=(py[0]-15))){
                px[0]=me.getX();
                py[0]=me.getY();
                repaint();
            }  
            if((me.getX()>=(px[1]-15)&&me.getX()<=(px[1]+15))&&(me.getY()<=(py[1]+15)&&me.getY()>=(py[1]-15))){
                px[1]=me.getX();
                py[1]=me.getY();
                repaint();
            }  
            if((me.getX()>=(px[2]-15)&&me.getX()<=(px[2]+15))&&(me.getY()<=(py[2]+15)&&me.getY()>=(py[2]-15))){
                px[2]=me.getX();
                py[2]=me.getY();
                repaint();
            }  
            if((me.getX()>=(px[3]-15)&&me.getX()<=(px[3]+15))&&(me.getY()<=(py[3]+15)&&me.getY()>=(py[3]-15))){
                px[3]=me.getX();
                py[3]=me.getY();
                repaint();
            }  
            if((me.getX()>=(px[4]-15)&&me.getX()<=(px[4]+15))&&(me.getY()<=(py[4]+15)&&me.getY()>=(py[4]-15))){
                px[4]=me.getX();
                py[4]=me.getY();
                repaint();
            }  
        }
        if(set.equals("Hexagon")){
            if((me.getX()>=(hx[0]-15)&&me.getX()<=(hx[0]+15))&&(me.getY()<=(hy[0]+15)&&me.getY()>=(hy[0]-15))){
                hx[0]=me.getX();
                hy[0]=me.getY();
                repaint();
            }  
            if((me.getX()>=(hx[1]-15)&&me.getX()<=(hx[1]+15))&&(me.getY()<=(hy[1]+15)&&me.getY()>=(hy[1]-15))){
                hx[1]=me.getX();
                hy[1]=me.getY();
                repaint();
            }  
            if((me.getX()>=(hx[2]-15)&&me.getX()<=(hx[2]+15))&&(me.getY()<=(hy[2]+15)&&me.getY()>=(hy[2]-15))){
                hx[2]=me.getX();
                hy[2]=me.getY();
                repaint();
            }  
            if((me.getX()>=(hx[3]-15)&&me.getX()<=(hx[3]+15))&&(me.getY()<=(hy[3]+15)&&me.getY()>=(hy[3]-15))){
                hx[3]=me.getX();
                hy[3]=me.getY();
                repaint();
            }  
            if((me.getX()>=(hx[4]-15)&&me.getX()<=(hx[4]+15))&&(me.getY()<=(hy[4]+15)&&me.getY()>=(hy[4]-15))){
                hx[4]=me.getX();
                hy[4]=me.getY();
                repaint();
            }  
            if((me.getX()>=(hx[5]-15)&&me.getX()<=(hx[5]+15))&&(me.getY()<=(hy[5]+15)&&me.getY()>=(hy[5]-15))){
                hx[5]=me.getX();
                hy[5]=me.getY();
                repaint();
            }  
        }
        
    }

        @Override
    public void mouseMoved(MouseEvent me) {
        
    }
}

