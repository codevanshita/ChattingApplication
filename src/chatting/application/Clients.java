
package chatting.application;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*; //calendar foramt aayega yaha se
import java.text.*; // date format ke liye
import java.net.*;
import java.io.*;


public class Clients implements ActionListener {
    
    JTextField text; //Globa;;y declared for all this chat
    static JPanel a1;
    static Box vertical = Box.createVerticalBox(); //side me jake vertical box create karne ke liye
    
    static JFrame f = new JFrame();
    
     static DataOutputStream dout;
    
    Clients(){  //constructor
       f.setLayout(null);
       
       JPanel p1 = new JPanel();
       p1.setBackground(new Color(7, 94, 84));
       p1.setBounds(0, 0, 450, 70);
       p1.setLayout(null);
       f.add(p1);
       //top left icon arrow
       ImageIcon i1 = new ImageIcon(getClass().getResource("/icons/3.png"));
       Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
       ImageIcon i3 = new ImageIcon(i2);  //arror at the left corner
       JLabel back = new JLabel(i3);
       back.setBounds(5, 20, 25, 25);
       p1.add(back);
       
       back.addMouseListener(new MouseAdapter(){
           public void mouseClicked(MouseEvent ae){
               System.exit(0);
           }
       });
      ImageIcon myPhotoIcon = new ImageIcon(getClass().getResource("/icons/myphoto.jpeg"));
        Image myPhotoImage = myPhotoIcon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon myPhotoFinal = new ImageIcon(myPhotoImage);

        // Add label with image
        JLabel myPhotoLabel = new JLabel(myPhotoFinal);
        myPhotoLabel.setBounds(40, 10, 50, 50);
        p1.add(myPhotoLabel);

       ImageIcon i7 = new ImageIcon(getClass().getResource("/icons/video.png"));
       Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
       ImageIcon i9 = new ImageIcon(i8);  //arror at the left corner
       JLabel video = new JLabel(i9);
       video.setBounds(300, 20, 25, 30);
       p1.add(video);
       
       ImageIcon i10 = new ImageIcon(getClass().getResource("/icons/phone.png"));
       Image i11 = i10.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
       ImageIcon i12 = new ImageIcon(i11);  //arror at the left corner
       JLabel phone = new JLabel(i12);
       phone.setBounds(360, 20, 35, 30);
       p1.add(phone);
       
       ImageIcon i13 = new ImageIcon(getClass().getResource("/icons/3icon.png"));
       Image i14 = i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
       ImageIcon i15 = new ImageIcon(i14);  //arror at the left corner
       JLabel more = new JLabel(i15);
       more.setBounds(410, 20, 10, 25);
       p1.add(more);
       
       JLabel name = new JLabel("Khushi");
       name.setBounds(110, 15, 100, 18);
       name.setForeground(Color.WHITE);
       name.setFont(new Font ("SAN_SERIF", Font.BOLD, 18));
       p1.add(name);
       
       JLabel status = new JLabel("Active Now");
       status.setBounds(110, 35, 100, 18);
       status.setForeground(Color.WHITE);
       status.setFont(new Font ("SAN_SERIF", Font.BOLD, 14));
       p1.add(status);
       
       a1 = new JPanel();
       a1.setBounds(5, 75, 440, 570);
       f.add(a1);
       
       text = new JTextField();
       text.setBounds(5, 655, 310, 40);
       text.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
       f.add(text);
       
       JButton send = new JButton ("Send");
       send.setBounds(320, 655, 123, 40);
       send.setForeground(new Color(7, 94, 84));
       send.setForeground(Color.WHITE);
       send.addActionListener(this);
       send.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
       f.add(send);
       
       f.setSize(450, 700);
       f.setLocation(800,50);
       f.setUndecorated(true);
       f.getContentPane().setBackground(Color.WHITE);
       f.setVisible(true);
    }
    public void actionPerformed(ActionEvent ae){
        try{
     String out = text.getText();
     
     JPanel p2 = formatLabel(out);
     
     
     a1.setLayout(new BorderLayout());
     
     JPanel right = new JPanel(new BorderLayout());
     right.add(p2, BorderLayout.LINE_END);
     vertical.add(right);
     vertical.add(Box.createVerticalStrut(15));
     a1.add(vertical, BorderLayout.PAGE_START);
     // yaha se text aa rha tha
     //ab tetx likhne ke baad it should be empty
     dout.writeUTF(out);
     text.setText("");
     f.repaint();
     f.invalidate();
     f.validate();
     
    }catch (Exception e){
        e.printStackTrace();
    }
    }
    public static JPanel formatLabel(String out){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel output = new JLabel("<html><p style=\"width: 150px\">" + out + "</html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(new Color(37, 211, 102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 50)); //Padding function
        
        panel.add(output);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);
        return panel;
            
    }
        
    
    public static void main(String[] args){
        new Clients(); //class object
         try{
            Socket s = new Socket("127.0.0.1", 6001);
            DataInputStream din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
            while(true){
                a1.setLayout(new BorderLayout());
                    String msg = din.readUTF(); //what the msg clinet had sent
                    JPanel panel = formatLabel(msg);
                    JPanel left = new JPanel(new BorderLayout()); // the recieved msg
                    left.add(panel, BorderLayout.LINE_START); //received msg willl be shown at the left corner
                    vertical.add(left);
                    vertical.add(Box.createVerticalStrut(15));
                    a1.add(vertical, BorderLayout.PAGE_START);
                    
                    f.validate();
                 }
         } catch(Exception e){
             e.printStackTrace();
         }
        
    }
    
}

