import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.MouseInputAdapter;
import java.util.*;
import java.text.*;
import java.io.*;
import java.net.*;


import java.awt.event.*;

public class ClientChat extends JFrame implements ActionListener{
    static JPanel p1,p2;
    JLabel send;
    static Box vertical =Box.createVerticalBox();
    static  DataOutputStream dop;
    ClientChat(){

        // header
        setLayout(null);
         p1=new JPanel();
        p1.setBounds(0,0,450,70);
        p1.setBackground(new Color(7,94,84));
        p1.setLayout(null);
        add(p1);

        // arrow 
        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("image/left-arrow.png"));
        Image img=i1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i2=new ImageIcon(img);
        JLabel l1=new JLabel(i2);
        l1.setBounds(10,25,25,25);
        p1.add(l1);

        l1.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent ev){
                System.exit(0);
            }
        });

    
       



         // profile 
         ImageIcon i3=new ImageIcon(ClassLoader.getSystemResource("image/profile.png"));
         Image img1=i3.getImage().getScaledInstance(35,35,Image.SCALE_DEFAULT);
         ImageIcon i4=new ImageIcon(img1);
         JLabel l2=new JLabel(i4);
         l2.setBounds(45,20,35,35);
         p1.add(l2);

        // dots 
        ImageIcon i5=new ImageIcon(ClassLoader.getSystemResource("image/dots.png"));
        Image img3=i5.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i6=new ImageIcon(img3);
        JLabel l3=new JLabel(i6);
        l3.setBounds(400,25,25,25);
        p1.add(l3);

         // phone 
         ImageIcon i7=new ImageIcon(ClassLoader.getSystemResource("image/phone.png"));
         Image img4=i7.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
         ImageIcon i8=new ImageIcon(img4);
         JLabel l4=new JLabel(i8);
         l4.setBounds(365,25,25,25);
         p1.add(l4);

          // camera 
          ImageIcon i9=new ImageIcon(ClassLoader.getSystemResource("image/video-camera.png"));
          Image img5=i9.getImage().getScaledInstance(35,35,Image.SCALE_DEFAULT);
          ImageIcon i10=new ImageIcon(img5);
          JLabel l5=new JLabel(i10);
          l5.setBounds(310,20,35,35);
          p1.add(l5);

          JLabel name=new JLabel("Akash Gupta");
          name.setBounds(90,20,150,25);
          name.setForeground(Color.WHITE);
          name.setFont(new Font("SEN_SERIF",Font.BOLD,20));
          p1.add(name);

          JLabel status=new JLabel("Active Now");
          status.setBounds(90,47,100,20);
          status.setForeground(Color.WHITE);
          status.setFont(new Font("SEN_SERIF",Font.BOLD,14));
          p1.add(status);


                            // body

         p2=new JPanel();
        p2.setBounds(5,75,425,530);

        // p2.setBackground(new Color(255,255,255));
        // p2.setBackground(new Color(7,94,84));
        p2.setLayout(new BorderLayout());

        add(p2);

         // adding scroll pane
         JScrollPane scrollPane = new JScrollPane(p2);
         scrollPane.setBounds(5,75, 425,530);  
         add(scrollPane);
         
        // text field
        TextArea t1=new TextArea();
        t1.setBounds(0,600,360,90);
        t1.setFont(new Font("SEN_SERIF",Font.BOLD,20));
        add(t1);

         // send 
         ImageIcon i11=new ImageIcon(ClassLoader.getSystemResource("image/send.png"));
         Image img6=i11.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
         ImageIcon i12=new ImageIcon(img6);
          send=new JLabel(i12);
         send.setBounds(365,610,50,50);
         add(send);

         send.addMouseListener(new MouseInputAdapter() {
            public void mouseClicked(MouseEvent ev){
                try{
                String output=t1.getText();
                // System.out.println(output);
            // if(output!=""){
                t1.setText("");
                // p2.setLayout(new BorderLayout());

                JPanel p3=formatPanel(output);

                JPanel p4=new JPanel(new BorderLayout());
                p4.add(p3,BorderLayout.LINE_END);
                vertical.add(p4);
                vertical.add(Box.createVerticalStrut(10));

                p2.add(vertical,BorderLayout.PAGE_START);
                dop.writeUTF(output);
                dop.flush();
                repaint();
                invalidate();
                validate();
            // }
        }catch(Exception e){

        }
            }
         });
        
        setUndecorated(true);
        setLocation(800,50);
        setSize(435,670);
        setVisible(true);
        
        getContentPane().setBackground(Color.WHITE);
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e)
            {
                dispose();
            }

            });
    }

    public static JPanel formatPanel(String output){
        JPanel p=new JPanel();
        JLabel label1=new JLabel("<html> <p style='width:150'>"+output+" </p> </html>");
        label1.setFont(new Font("Tahuma",Font.PLAIN,16));
        label1.setBackground(new Color(37,211,102));
        label1.setOpaque(true);
        label1.setBorder(new EmptyBorder(15,15,15,50));
        p.add(label1);

        Calendar c1=Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");

        JLabel date=new JLabel();
        date.setText(sdf.format(c1.getTime()));
        p.add(date);
        
        return p;
    }

    public void actionPerformed(ActionEvent e){

    }

    public static void main(String args[])throws Exception{
        ClientChat c1=new ClientChat();

        Socket s=new Socket("localhost",4444);
        DataInputStream dip=new DataInputStream(s.getInputStream());
        dop=new DataOutputStream(s.getOutputStream());
        Scanner sc=new Scanner(System.in);
        String str1="",str2="";
        while(!str1.equals("bye"))
        {
            // System.out.print("Reply : ");
            // str1=sc.nextLine();
            // dop.writeUTF(str1);
            // dop.flush();
            str1=dip.readUTF();
            System.out.println("server say : "+str1);

            JPanel panel=formatPanel(str1);
            JPanel left=new JPanel(new BorderLayout());
            left.add(panel,BorderLayout.LINE_START);
            vertical.add(left);
            vertical.add(Box.createVerticalStrut(15));
            p2.add(vertical,BorderLayout.PAGE_START);

            c1.repaint();
            c1.invalidate();
            c1.validate();

           
        }
    }
}
