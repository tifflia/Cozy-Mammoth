import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.Date;
import java.util.Scanner;

public class Main extends JPanel{
    public static final int WIDTH = 500;
    public static final int HEIGHT = 750;

    User user = new User("John",18,8,new Time(0,0),new Time(8,0));

    static JFrame frame = new JFrame("CozyMammoth");

    //App Pages (JPanels)
    Welcome Welcome = new Welcome(this);
    NewUser NewUser = new NewUser(this);
    Home Home = new Home(this);
    SleepHistory SleepHistory = new SleepHistory();
    LogSleep LogSleep = new LogSleep(this);
    JPanel SleepRecs = new JPanel();
    JPanel Settings = new JPanel();
    //CardLayout to manage JPanel "pages"
    CardLayout cl = new CardLayout();

    //thread to update the current time
    class Runner implements Runnable{
        public void run() {
            while(true){
                //update the currentDate
                //get the "currentDate" Date object from Sleephistory and update it every second (= new Date();)
                try{
                    Thread.sleep(1000); //every second...
                }
                catch(InterruptedException e){}
            }
    
        }
     
    }

    //constructor
    public Main(){
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        //Thread that updates the currentDate
        Thread mainThread = new Thread(new Runner());
        mainThread.start();
    }

    public static void main(String[] args){
        //jframe stuff
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        Main mainInstance = new Main();
        
        //add panels to the cardlayout
        mainInstance.setLayout(mainInstance.cl);
        mainInstance.add(mainInstance.Welcome, "1");
        mainInstance.add(mainInstance.NewUser, "2");
        mainInstance.add(mainInstance.Home, "3");
        mainInstance.add(mainInstance.LogSleep, "4");
        //continue adding panels

        frame.setContentPane(mainInstance); //showing Welcome because it's the first panel added
        frame.pack();
        frame.setVisible(true);


        //testing user and time class
        Time bedTime = new Time(0, 00);
        Time wakeTime = new Time(8, 00);
        User testUser = new User("John Doe", 20, 8, bedTime, wakeTime);
        System.out.println("bedtime goal: " + testUser.getBedTime());
        System.out.println("wake up goal: " + testUser.getWakeTime());
        SleepNode testMonday = new SleepNode(bedTime, wakeTime, "I woke up very refreshed.", 5);
        SleepHistory testHistory = new SleepHistory(testMonday);
        SleepHistory testhistory = new SleepHistory(testMonday);
        Schedule testcalendar = new Schedule();
        SleepRecommendation testRec = new SleepRecommendation(testUser.getWakeTime(), testUser.getBedTime(), testUser.getAge(), testUser.getSleepGoal(), testhistory, testcalendar);
        //System.out.println("Based on your wake up goal, you should sleep at " + testRec.calculateSleepRec());
        testRec.calculateSleepRec();
        System.out.println(testRec.getSleepRecsMessages(0) + testRec.getSleepRecs(0));
        System.out.println(testRec.getSleepRecsMessages(1) + testRec.getSleepRecs(1));
        System.out.println(testRec.getSleepRecsMessages(2) + testRec.getSleepRecs(2));
        System.out.println(testRec.getSleepRecsMessages(3) + testRec.getSleepRecs(3));
        System.out.println(testRec.getSleepRecsMessages(4) + testRec.getSleepRecs(4));
        System.out.println(testRec.sleepHistorySummary(testMonday));

        //fix
        // System.out.println(testHistory.getAverageDuration());

        Date date = new Date();

    }

    // public void paintComponent(Graphics g) {
    //     super.paintComponent(g);

    //     //draw the welcome screen
    //     Color bg = new Color(60, 86, 166);
    //     g.setColor(bg);
    //     g.fillRect(0, 0, WIDTH, HEIGHT);

    //     try {
    //         BufferedImage logo = ImageIO.read(new File("logo.png"));
    //         g.drawImage(logo,100,150,300,300,null);
    //         // Font title = Font.createFont(Font.TRUETYPE_FONT, new File("Rubik.ttf"));
    //         // g.setColor(Color.WHITE);
    //         // g.setFont(title);
    //         // g.drawString("Cozy Mammoth",0,400);
    //         // // FIGURE OUT FONT LATER
    //     }
    //     // catch (FontFormatException e) {
    //     //     e.printStackTrace();
    //     // }
    //     catch (IOException e) { 
    //         e.printStackTrace();
    //     }
    // }
}

class User{
    private String name;
    private double age;
    private int sleepGoal;
    private Time bedTime;
    private Time wakeTime;

    //constructor
    public User(String userName, double userAge, int sleep, Time bed, Time wake) {
        name = userName;
        age = userAge;
        sleepGoal = sleep;
        bedTime = bed;
        wakeTime = wake;
    }
    //field getters and setters
    public void setName(String newName) {
        name = newName;
    }
    public String getName() {
        return name;
    }
    public void setAge(double newAge) {
        age = newAge;
    }
    public double getAge() {
        return age;
    }
    public void setSleepGoal(int newGoal) {
        sleepGoal = newGoal;
    }
    public int getSleepGoal() {
        return sleepGoal;
    }
    public void setBedTime(Time newTime) {
        bedTime = newTime;
    }
    public Time getBedTime() {
        return bedTime;
    }
    public void setWakeTime(Time newTime) {
        wakeTime = newTime;
    }
    public Time getWakeTime() {
        return wakeTime;
    }
}

class Time{
    //calculated in military time
    int hours; //0-24 (excluded)
    int minutes;//0-60 (excluded)
    String meridiem; //AM or PM

    //constructor
    public Time(int h, int m) {
        //don't use checktime here, use it when whatever action is registered
        hours = h;
        minutes = m;
        if(hours <= 12) meridiem = "AM";
        else meridiem = "PM";
    }

    //add getters and setters?
    public int getHour(){
        return hours;
    }
    public int getMinutes(){
        return minutes;
    }

    //user input -> military time
    public static int convertHours(int h, String time) {
        if(time.equals("AM")){
            //if 12am, return 0
            if(h == 12) return 0;
            return h;
        }
        else {
            //if 12pm, return 12
            if(h == 12) return h;
            return h+12;
        }
    }

    //check if the inputted time is within the appropriate bounds
    public static boolean checkTime(int h, int m) {
        return (h > 0 && h <= 12) && (m >= 0 && m < 60);
    }

    //will return as "00:00 PM/AM"
    public String toString() {
        String toReturn = "";
        //hours
        if(hours <= 12) {
            if(hours == 0) toReturn += 12;
            else toReturn += hours;
        }
        else {
            toReturn += (hours-12);
        }
        //mins
        if(minutes < 10) {
            toReturn += ":" + 0 + "" + minutes + " " + meridiem;
        }
        else {
            toReturn += ":" + minutes + " " + meridiem;
        }
        return toReturn;
    }
}

//Welcome page Jpanel
class Welcome extends JPanel implements MouseListener{
    Main mainInstance;

    public Welcome(Main main){
        mainInstance = main;
        addMouseListener(this);

        Color bg = new Color(60, 86, 166);
        this.setBackground(bg);

        Box box = Box.createVerticalBox();
        this.add(box);

        JLabel title = new JLabel("Cozy Mammoth");
        box.add(title);

        //title styling
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 50));
        title.setHorizontalTextPosition(JLabel.CENTER);
        title.setVerticalTextPosition(JLabel.BOTTOM);
        title.setAlignmentX(Component.CENTER_ALIGNMENT); //for center alignment in boxlayout
        title.setBorder(BorderFactory.createEmptyBorder(150,0,100,0));

        //resizing logo
        ImageIcon logo = new ImageIcon("graphics/logo.png");
        Image image = logo.getImage(); //transform it 
        Image newimg = image.getScaledInstance(250, 250, java.awt.Image.SCALE_SMOOTH); //scale down
        logo = new ImageIcon(newimg); //transform back
        title.setIcon(logo);
        title.setIconTextGap(20);

        JLabel subtitle = new JLabel("Click to continue");
        box.add(subtitle);

        //subtitle styling
        subtitle.setForeground(Color.WHITE);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 20));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT); //for center alignment in boxlayout
    }

    //MOUSELISTENER (navigation)
    public void mousePressed(MouseEvent e) {
        System.out.println("Mouse pressed detected on " + e.getComponent().getClass().getName() + ".");

        //if no User has been initialized, pull up the NewUser panel
        if(mainInstance.user == null) {
            mainInstance.cl.show(mainInstance, "2");
        }
        //otherwise, pull up the Home Panel
        else {
            mainInstance.cl.show(mainInstance, "3");
        }
    }
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}

    // public void paintComponent(Graphics g) {
    //     super.paintComponent(g);

    //     //would be nice to add the gradient + random stars (from the last hw) to the background of the welcome page
    // }
}

//NewUser page Jpanel
class NewUser extends JPanel{
    Main mainInstance;

    public NewUser(Main main){
        mainInstance = main;

        Color bg = new Color(37,44,64);
        this.setBackground(bg);
        this.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));

        //main box
        Box box = Box.createVerticalBox();
        this.add(box);

        JLabel title = new JLabel("Welcome!");
        box.add(title);

        //title styling
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.PLAIN, 25));
        title.setAlignmentX(Component.CENTER_ALIGNMENT); //for center alignment in boxlayout
        title.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
        
        JLabel subtitle = new JLabel("Tell us about yourself to get started");
        box.add(subtitle);

        //subtitle styling
        subtitle.setForeground(Color.WHITE);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 15));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT); //for center alignment in boxlayout
        subtitle.setBorder(BorderFactory.createEmptyBorder(0,0,25,0));

        //initializing components
        JLabel[] labels = new JLabel[5];
        JTextField[] texts = new JTextField[7];
        Box text4 = Box.createHorizontalBox();
        Box text4wrap = Box.createVerticalBox();
        Box text5 = Box.createHorizontalBox();
        Box text5wrap = Box.createVerticalBox();
        JButton button = new JButton("Done");

        labels[0] = new JLabel("Name");
        labels[1] = new JLabel("Age");
        labels[2] = new JLabel("Sleep Goal");
        labels[3] = new JLabel("Bedtime");
        labels[4] = new JLabel("Wake Up");

        Color textbg = new Color(104, 121, 170);
        for(int i = 0; i < texts.length; i++) {
            if(i < 3) {
                texts[i] = new JTextField(20);
            }
            else{
                texts[i] = new JTextField(1);
            }

            //textfield styling
            texts[i].setBackground(textbg);
            texts[i].setForeground(Color.WHITE);
            texts[i].setFont(new Font("Arial", Font.PLAIN, 15));
            texts[i].setCaretColor(Color.WHITE);
        }

        //adding to box that wraps the first 3 labels + textfields to the mainbox
        for(int i = 0; i < 3; i++) {
            Box wrap = Box.createVerticalBox();
            wrap.add(labels[i]);
            wrap.add(texts[i]);
            box.add(wrap);

            //textfield styling
            texts[i].setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
            texts[i].setAlignmentX(Component.LEFT_ALIGNMENT);
            wrap.setAlignmentX(Component.CENTER_ALIGNMENT);
        }

        //label styling
        for(int i = 0; i < labels.length; i++) {
            labels[i].setFont(new Font("Arial", Font.PLAIN, 15));
            labels[i].setForeground(Color.WHITE);
            labels[i].setBorder(BorderFactory.createEmptyBorder(25,0,5,0));
            labels[i].setAlignmentX(Component.LEFT_ALIGNMENT);
        }

        String[] meridiems = {"AM", "PM"};
        JComboBox<String> bedtimeCB = new JComboBox<>(meridiems);
        JComboBox<String> wakeupCB = new JComboBox<>(meridiems);

        bedtimeCB.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));
        wakeupCB.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));

        JLabel divider1 = new JLabel(":");
        JLabel divider2 = new JLabel(":");
        divider1.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
        divider1.setFont(new Font("Arial", Font.PLAIN, 15));
        divider1.setForeground(Color.WHITE);
        divider2.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
        divider2.setFont(new Font("Arial", Font.PLAIN, 15));
        divider2.setForeground(Color.WHITE);
        
        // bedtime (text + label + text + combobox)
        text4.add(texts[3]);
        text4.add(divider1);
        text4.add(texts[4]);
        text4.add(bedtimeCB);
        //bedtime styling
        texts[3].setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        texts[4].setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        text4.setAlignmentY(Component.CENTER_ALIGNMENT);
        //text4 (horizontal) --> text4wrap (vertical)
        text4wrap.add(text4);
        text4wrap.setAlignmentX(Component.LEFT_ALIGNMENT);

        // wakeup (text + label + text + combobox)
        text5.add(texts[5]);
        text5.add(divider2);
        text5.add(texts[6]);
        text5.add(wakeupCB);
        //wakeup styling
        texts[5].setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        texts[6].setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        text5.setAlignmentY(Component.CENTER_ALIGNMENT);
        //text5 (horizontal) --> text5wrap (vertical)
        text5wrap.add(text5);
        text5wrap.setAlignmentX(Component.LEFT_ALIGNMENT);

        //bedtime box (label + bedtime box)
        Box wrap4 = Box.createVerticalBox();
        wrap4.add(labels[3]);
        wrap4.add(text4wrap);
        box.add(wrap4);
        wrap4.setAlignmentX(Component.CENTER_ALIGNMENT);
        //wakeup box (label + wakeup box)
        Box wrap5 = Box.createVerticalBox();
        wrap5.add(labels[4]);
        wrap5.add(text5wrap);
        box.add(wrap5);
        wrap5.setAlignmentX(Component.CENTER_ALIGNMENT);

        //BUTTON
        //adding box that wraps the done button to the main box
        Box buttonWrap = Box.createVerticalBox();
        buttonWrap.add(button);
        box.add(buttonWrap);

        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonWrap.setBorder(BorderFactory.createEmptyBorder(50,0,0,0));

        button.setMargin(new Insets(5,25,5,25));
        Color buttColor = new Color(80, 121, 242);
        button.setBackground(buttColor);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.PLAIN, 15));

        //MOUSELISTENER (for button)
        button.addMouseListener(new MouseAdapter() {
            @Override
			public void mousePressed(MouseEvent e) {
                Color clickedButtColor = new Color(51, 77, 156);
                button.setBackground(clickedButtColor);
				System.out.println("button has been pressed");

                //check name
                String name = texts[0].getText();
                boolean validName = (name != null);
                //check age
                double age = Double.parseDouble(texts[1].getText());
                boolean validAge = (age > 0);
                //check sleepGoal
                int sleepGoal = Integer.parseInt(texts[2].getText());
                boolean validSleepGoal = (sleepGoal > 0);
                //check bedtime
                int bedHours = Integer.parseInt(texts[3].getText());
                int bedMins = Integer.parseInt(texts[4].getText());
                boolean validBed = Time.checkTime(bedHours, bedMins);
                String bedMeridiem = bedtimeCB.getSelectedItem().toString();
                System.out.println(validBed);
                //check wakeup
                int wakeHours = Integer.parseInt(texts[5].getText());
                int wakeMins = Integer.parseInt(texts[6].getText());
                boolean validWake = Time.checkTime(wakeHours, wakeMins);
                String wakeMeridiem = wakeupCB.getSelectedItem().toString();
                System.out.println(validWake);

                //initialize the new user to the mainInstance
                if(validName && validAge && validSleepGoal && validBed && validWake) {
                    //User(String userName, double userAge, int sleep, Time bed, Time wake)
                    Time bed = new Time(Time.convertHours(bedHours, bedMeridiem), bedMins);
                    Time wake = new Time(Time.convertHours(wakeHours, wakeMeridiem), wakeMins);

                    System.out.println(bed);
                    System.out.println(wake);

                    mainInstance.user = new User(name, age, sleepGoal, bed, wake);

                    //switch to home page
                    mainInstance.Home = new Home(mainInstance); //have to "reset" the home page in mainInstance
                    mainInstance.add(mainInstance.Home,"3"); //update the old home in the cardlayout
                    mainInstance.cl.show(mainInstance, "3");
                }
			}
            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(buttColor);
            }
		});
    }
}

//Home page JPanel
class Home extends JPanel{
    Main mainInstance;

    public Home(Main main) {
        mainInstance = main;

        Color bg = new Color(37,44,64);
        this.setBackground(bg);
        this.setBorder(BorderFactory.createEmptyBorder(40,20,40,20));

        //main box
        Box box = Box.createVerticalBox();
        this.add(box);

        if(mainInstance.user != null) {
            Box header = Box.createVerticalBox();

            JLabel title = new JLabel("Welcome, " + mainInstance.user.getName());
            header.add(title);

            title.setForeground(Color.WHITE);
            title.setFont(new Font("Arial", Font.PLAIN, 20));
            title.setAlignmentX(Component.CENTER_ALIGNMENT); //for center alignment in boxlayout
            title.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));

            JLabel date = new JLabel("[Date Placeholder]");
            header.add(date);

            date.setForeground(Color.WHITE);
            Color bg2 = new Color(54,75,140);
            date.setBackground(bg2);
            date.setOpaque(true);
            date.setFont(new Font("Arial", Font.PLAIN, 15));
            date.setAlignmentX(Component.CENTER_ALIGNMENT); //for center alignment in boxlayout
            date.setBorder(BorderFactory.createEmptyBorder(10,40,10,40));

            header.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
            box.add(header);

            JPanel mainWrap = new JPanel(new GridLayout(2,1)); //2 rows, 1 col
            box.add(mainWrap);

            JPanel graphPanel = new JPanel(); //placeholder for graph
            mainWrap.add(graphPanel);

            //graph panel styling
            graphPanel.setBackground(bg2);
            graphPanel.setBorder(BorderFactory.createEmptyBorder(130,200,130,200));

            JPanel optionsPanel = new JPanel(new GridLayout(2,2,10,20)); //2 rows, 2 cols
            mainWrap.add(optionsPanel);

            //option panel styling
            optionsPanel.setBackground(bg);
            optionsPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

            //creating the option labels
            JLabel[] options = new JLabel[4];
            options[0] = new JLabel("Sleep Recommendations");
            options[1] = new JLabel("Log Sleep");
            options[2] = new JLabel("Sleep History");
            options[3] = new JLabel("Settings");

            //creating the option icons
            ImageIcon[] icons = new ImageIcon[4];
            icons[0] = new ImageIcon("graphics/tipsIcon.png");
            icons[1] = new ImageIcon("graphics/logIcon.png");
            icons[2] = new ImageIcon("graphics/historyIcon.png");
            icons[3] = new ImageIcon("graphics/settingsIcon.png");

            //adding option labels to options panel
            for(int i = 0; i < 4; i++) {
                optionsPanel.add(options[i]);

                //options label styling
                options[i].setForeground(Color.WHITE);
                options[i].setFont(new Font("Arial", Font.PLAIN, 15));
                options[i].setHorizontalTextPosition(JLabel.CENTER);
                options[i].setVerticalTextPosition(JLabel.BOTTOM);
                options[i].setHorizontalAlignment(JLabel.CENTER);

                //adding option icons to the labels
                options[i].setIcon(icons[i]);
                options[i].setIconTextGap(15);
            }

            //adding nav functionality to the option labels
            options[0].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {System.out.println("Sleep Tips pressed");}
            });
            options[1].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    System.out.println("Log Sleep pressed -> LogSleep");
                    mainInstance.cl.show(mainInstance, "4");
                }
            });
            options[2].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {System.out.println("Sleep History pressed");}
            });
            options[3].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {System.out.println("Settings pressed");}
            });
        }
    }
}

//LogSleep page Jpanel
class LogSleep extends JPanel{
    private Time sleepTime;
    private Time wakeTime;
    private int rating = 0; //sleep quality rating from 1-5
    private String sleepNote;

    Main mainInstance;
    CardLayout logcl = new CardLayout();
    JPanel logPanel1 = new JPanel();
    JPanel logPanel2 = new JPanel();

    public LogSleep(Main main) {
        mainInstance = main;

        //two pages within LogSleep - access through cardlayout
        this.setLayout(logcl);
        this.add(logPanel1, "1");
        this.add(logPanel2, "2");

        //-----------------logPanel1-----------------
        Color bg = new Color(42, 54, 89);
        logPanel1.setBackground(bg);
        logPanel1.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        Box mainBox1 = Box.createVerticalBox();
        logPanel1.add(mainBox1);

        //exit arrow
        ImageIcon exitIcon = new ImageIcon("graphics/cross.png");
        JLabel exitLabel = new JLabel();
        exitLabel.setIcon(exitIcon);

        Box exitBox = Box.createVerticalBox();
        exitBox.add(exitLabel);
        mainBox1.add(exitBox);

        exitLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        exitLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,440));

        exitBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitBox.add(Box.createHorizontalGlue());
        

        //title label
        JLabel title1 = new JLabel("How long did you sleep for?");
        mainBox1.add(title1);
        //title1 styling
        title1.setForeground(Color.WHITE);
        title1.setFont(new Font("Arial", Font.PLAIN, 25));
        title1.setAlignmentX(Component.CENTER_ALIGNMENT); //for center alignment in boxlayout
        title1.setBorder(BorderFactory.createEmptyBorder(100,0,100,0));

        Box sleepBox = Box.createHorizontalBox();
        Box sleepBoxWrap = Box.createVerticalBox();
        Box wakeBox = Box.createHorizontalBox();
        Box wakeBoxWrap = Box.createVerticalBox();

        Color textbg = new Color(211, 221, 252);
        JTextField[] logHrsFields = new JTextField[4];
        for(int i = 0; i < 4; i++) {
            logHrsFields[i] = new JTextField(1);

            //textfield styling
            logHrsFields[i].setBackground(textbg);
            logHrsFields[i].setForeground(bg);
            logHrsFields[i].setFont(new Font("Arial", Font.PLAIN, 25));
            logHrsFields[i].setCaretColor(bg);
        }

        String[] meridiems = {"AM", "PM"};
        JComboBox<String> bedtimeCB = new JComboBox<>(meridiems);
        JComboBox<String> wakeupCB = new JComboBox<>(meridiems);

        bedtimeCB.setBorder(BorderFactory.createEmptyBorder(0,20,0,0));
        wakeupCB.setBorder(BorderFactory.createEmptyBorder(0,20,0,0));

        JLabel divider1 = new JLabel(":");
        JLabel divider2 = new JLabel(":");
        divider1.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
        divider1.setFont(new Font("Arial", Font.PLAIN, 25));
        divider1.setForeground(Color.WHITE);
        divider2.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
        divider2.setFont(new Font("Arial", Font.PLAIN, 25));
        divider2.setForeground(Color.WHITE);
        
        // bedtime (text + label + text + combobox)
        sleepBox.add(logHrsFields[0]);
        sleepBox.add(divider1);
        sleepBox.add(logHrsFields[1]);
        sleepBox.add(bedtimeCB);
        //bedtime styling
        logHrsFields[0].setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        logHrsFields[1].setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        sleepBox.setAlignmentY(Component.CENTER_ALIGNMENT);
        //sleepBox (horizontal) --> sleepBoxWrap (vertical)
        sleepBoxWrap.add(sleepBox);
        sleepBoxWrap.setAlignmentX(Component.CENTER_ALIGNMENT);
        sleepBoxWrap.setBorder(BorderFactory.createEmptyBorder(0,100,0,100));

        // wakeup (text + label + text + combobox)
        wakeBox.add(logHrsFields[2]);
        wakeBox.add(divider2);
        wakeBox.add(logHrsFields[3]);
        wakeBox.add(wakeupCB);
        //wakeup styling
        logHrsFields[2].setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        logHrsFields[3].setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
        wakeBox.setAlignmentY(Component.CENTER_ALIGNMENT);
        //wakeBox (horizontal) --> wakeBoxWrap (vertical)
        wakeBoxWrap.add(wakeBox);
        wakeBoxWrap.setAlignmentX(Component.CENTER_ALIGNMENT);
        wakeBoxWrap.setBorder(BorderFactory.createEmptyBorder(0,100,0,100));

        JLabel toLabel = new JLabel("TO");
        //"to" styling
        toLabel.setForeground(Color.WHITE);
        toLabel.setFont(new Font("Arial", Font.BOLD, 25));
        toLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        toLabel.setBorder(BorderFactory.createEmptyBorder(20,0,20,0));

        mainBox1.add(sleepBoxWrap);
        mainBox1.add(toLabel);
        mainBox1.add(wakeBoxWrap);

        //BUTTON
        //adding box that wraps the done button to the main box
        JButton button1 = new JButton("Continue");
        Box buttonWrap1 = Box.createVerticalBox();
        buttonWrap1.add(button1);
        mainBox1.add(buttonWrap1);

        button1.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonWrap1.setBorder(BorderFactory.createEmptyBorder(100,0,0,0));
        buttonWrap1.setAlignmentX(Component.CENTER_ALIGNMENT);

        button1.setMargin(new Insets(10,50,10,50));
        Color buttColor = new Color(80, 121, 242);
        button1.setBackground(buttColor);
        button1.setOpaque(true);
        button1.setBorderPainted(false);
        button1.setForeground(Color.WHITE);
        button1.setFont(new Font("Arial", Font.PLAIN, 15));

        button1.addMouseListener(new MouseAdapter() {
            @Override
			public void mousePressed(MouseEvent e) {
                Color clickedButtColor = new Color(51, 77, 156);
                button1.setBackground(clickedButtColor);
				System.out.println("button has been pressed");
			}
            @Override
            public void mouseReleased(MouseEvent e) {
                button1.setBackground(buttColor);
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                //check bedtime
                int bedHours = Integer.parseInt(logHrsFields[0].getText());
                int bedMins = Integer.parseInt(logHrsFields[1].getText());
                boolean validBed = Time.checkTime(bedHours, bedMins);
                String bedMeridiem = bedtimeCB.getSelectedItem().toString();
                System.out.println(validBed);
                //check wakeup
                int wakeHours = Integer.parseInt(logHrsFields[2].getText());
                int wakeMins = Integer.parseInt(logHrsFields[3].getText());
                boolean validWake = Time.checkTime(wakeHours, wakeMins);
                String wakeMeridiem = wakeupCB.getSelectedItem().toString();
                System.out.println(validWake);

                //set LogSleep's sleepTime and wakeTime
                if(validBed && validWake) {
                    Time bed = new Time(Time.convertHours(bedHours, bedMeridiem), bedMins);
                    Time wake = new Time(Time.convertHours(wakeHours, wakeMeridiem), wakeMins);
                    mainInstance.LogSleep.setSleepTime(bed);
                    mainInstance.LogSleep.setWakeTime(wake);

                    System.out.println(bed);
                    System.out.println(wake);

                    //switch to logPanel2
                    mainInstance.LogSleep.logcl.show(mainInstance.LogSleep, "2");
                }
            }
		});


        //-----------------logPanel2-----------------
        logPanel2.setBackground(bg);
        logPanel2.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        Box mainBox2 = Box.createVerticalBox();
        logPanel2.add(mainBox2);

        //exit arrow
        ImageIcon backIcon = new ImageIcon("graphics/angle-left.png");
        JLabel backLabel = new JLabel();
        backLabel.setIcon(backIcon);

        Box backBox = Box.createVerticalBox();
        backBox.add(backLabel);
        mainBox2.add(backBox);

        backLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        backLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,440));

        backBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        backBox.add(Box.createHorizontalGlue());
        

        //title label
        JLabel title2 = new JLabel("How would you rate your sleep?");
        mainBox2.add(title2);
        //title1 styling
        title2.setForeground(Color.WHITE);
        title2.setFont(new Font("Arial", Font.PLAIN, 25));
        title2.setAlignmentX(Component.CENTER_ALIGNMENT); //for center alignment in boxlayout
        title2.setBorder(BorderFactory.createEmptyBorder(50,0,80,0));

        //SLEEP QUALITY/RATING
        Box ratingBox = Box.createHorizontalBox();
        Box ratingBoxWrap = Box.createVerticalBox();

        //face icons
        JLabel[] faces = new JLabel[5];
        ImageIcon[] faceIcons = new ImageIcon[5]; //normal faces
        ImageIcon[] facePickedIcons = new ImageIcon[5]; //highlighted faces

        for(int i = 0; i < 5; i++) {
            ImageIcon faceIcon = new ImageIcon("graphics/" + (i+1) + ".png");
            faces[i] = new JLabel();
            faces[i].setIcon(faceIcon);
            faceIcons[i] = faceIcon;
            ImageIcon facePickedIcon = new ImageIcon("graphics/" + (i+1) + "pick.png");
            facePickedIcons[i] = facePickedIcon;

            //face icon styling
            faces[i].setBorder(BorderFactory.createEmptyBorder(0,10,0,10));

            ratingBox.add(faces[i]);
        }

        //styling
        ratingBox.setAlignmentY(Component.CENTER_ALIGNMENT);
        //ratingBox (horizontal) --> ratingBoxWrap (vertical)
        ratingBoxWrap.add(ratingBox);
        ratingBoxWrap.setAlignmentX(Component.CENTER_ALIGNMENT);
        // ratingBoxWrap.setBorder(BorderFactory.createEmptyBorder(0,100,0,100));

        JLabel subtitle1 = new JLabel("Quality");
        subtitle1.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainBox2.add(subtitle1);
        
        mainBox2.add(ratingBoxWrap);

        JLabel subtitle2 = new JLabel("Notes");
        subtitle2.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainBox2.add(subtitle2);

        //subtitle styling
        subtitle1.setForeground(Color.WHITE);
        subtitle1.setFont(new Font("Arial", Font.PLAIN, 15));
        subtitle1.setAlignmentX(Component.CENTER_ALIGNMENT); //for center alignment in boxlayout
        subtitle1.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
        subtitle2.setForeground(Color.WHITE);
        subtitle2.setFont(new Font("Arial", Font.PLAIN, 15));
        subtitle2.setAlignmentX(Component.CENTER_ALIGNMENT); //for center alignment in boxlayout
        subtitle2.setBorder(BorderFactory.createEmptyBorder(40,0,20,0));

        //SLEEP NOTES
        JTextArea notesText = new JTextArea(10,10);
        JScrollPane scrollPane = new JScrollPane(notesText);
        Box notesBox = Box.createVerticalBox();
        notesBox.add(scrollPane);
        mainBox2.add(notesBox); //put it in a box so side borders can be added
        notesText.setLineWrap(true);
        notesText.setForeground(bg);
        notesText.setBackground(textbg);
        notesText.setFont(new Font("Arial", Font.PLAIN, 15));
        notesText.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        notesText.setAlignmentX(Component.CENTER_ALIGNMENT);
        notesBox.setBorder(BorderFactory.createEmptyBorder(0,50,0,50));
        notesBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        //BUTTON
        //adding box that wraps the done button to the main box
        JButton button2 = new JButton("Done");
        Box buttonWrap2 = Box.createVerticalBox();
        buttonWrap2.add(button2);
        mainBox2.add(buttonWrap2);

        button2.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonWrap2.setBorder(BorderFactory.createEmptyBorder(50,0,0,0));
        buttonWrap2.setAlignmentX(Component.CENTER_ALIGNMENT);

        button2.setMargin(new Insets(10,50,10,50));
        button2.setBackground(buttColor);
        button2.setOpaque(true);
        button2.setBorderPainted(false);
        button2.setForeground(Color.WHITE);
        button2.setFont(new Font("Arial", Font.PLAIN, 15));

        button2.addMouseListener(new MouseAdapter() {
            @Override
			public void mousePressed(MouseEvent e) {
                Color clickedButtColor = new Color(51, 77, 156);
                button2.setBackground(clickedButtColor);
				System.out.println("button has been pressed");
			}
            @Override
            public void mouseReleased(MouseEvent e) {
                button2.setBackground(buttColor);
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                //set LogSleep's rating and sleepNote
                if(true) {
                    //set LogSleep's sleepNote (rating should already be set)
                    sleepNote = notesText.getText();

                    //create the sleepNode (call LogSleep's method) and add it to the SleepHistory arrayList(?)
                    SleepNode toAdd = createSleepNode();
                    mainInstance.SleepHistory.sleepHistory.add(toAdd); //get's the sleepHistory ArrayList from the SleepHistory instance in 

                    //when done, reset all the textfields/textareas/rating, and clear the LogSleep data (make null)
                    for(int i = 0; i < 4; i++) {
                        logHrsFields[i].setText("");
                    }
                    notesText.setText("");
                    for(int i = 0; i < 5; i++) {
                        faces[i].setIcon(faceIcons[i]);
                    }
                    mainInstance.LogSleep.clearLog();
                    //go back to Home
                    mainInstance.cl.show(mainInstance, "3");
                    mainInstance.LogSleep.logcl.show(mainInstance.LogSleep,"1");
                }
            }
		});

        //NAV BUTTON MOUSELISTENERS
        //exitLabel
        exitLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //when exiting, reset all the textfields/textareas, and clear the LogSleep data (make null)
                for(int i = 0; i < 4; i++) {
                    logHrsFields[i].setText("");
                }
                notesText.setText("");
                for(int i = 0; i < 5; i++) {
                    faces[i].setIcon(faceIcons[i]);
                }
                mainInstance.LogSleep.clearLog();
                //go back to Home
                mainInstance.cl.show(mainInstance,"3");
            }
        });
        //backLabel
        backLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //go back to logSleep1
                mainInstance.LogSleep.logcl.show(mainInstance.LogSleep,"1");
            }
        });

        //RATING FACES MOUSELISTENERS
        faces[0].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                faces[0].setIcon(facePickedIcons[0]); //change highlighted
                //make sure the other faces are normal
                for(int i = 0; i < 5; i++) {
                    if(i != 0) {
                        faces[i].setIcon(faceIcons[i]);
                    }
                }
                rating = 1;
                System.out.println("rating is: " + rating);
            }
        });
        faces[1].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                faces[1].setIcon(facePickedIcons[1]); //change highlighted
                //make sure the other faces are normal
                for(int i = 0; i < 5; i++) {
                    if(i != 1) {
                        faces[i].setIcon(faceIcons[i]);
                    }
                }
                rating = 2;
                System.out.println("rating is: " + rating);
            }
        });
        faces[2].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                faces[2].setIcon(facePickedIcons[2]); //change highlighted
                //make sure the other faces are normal
                for(int i = 0; i < 5; i++) {
                    if(i != 2) {
                        faces[i].setIcon(faceIcons[i]);
                    }
                }
                rating = 3;
                System.out.println("rating is: " + rating);
            }
        });
        faces[3].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                faces[3].setIcon(facePickedIcons[3]); //change highlighted
                //make sure the other faces are normal
                for(int i = 0; i < 5; i++) {
                    if(i != 3) {
                        faces[i].setIcon(faceIcons[i]);
                    }
                }
                rating = 4;
                System.out.println("rating is: " + rating);
            }
        });
        faces[4].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                faces[4].setIcon(facePickedIcons[4]); //change highlighted
                //make sure the other faces are normal
                for(int i = 0; i < 5; i++) {
                    if(i != 4) {
                        faces[i].setIcon(faceIcons[i]);
                    }
                }
                rating = 5;
                System.out.println("rating is: " + rating);
            }
        });
    }

    //Getter and setter methods for sleep time and wake time
    public Time getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(Time sleep) {
        sleepTime = sleep;
    }

    public Time getWakeTime() {
        return wakeTime;
    }

    public void setWakeTime(Time wake) {
        wakeTime = wake;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int newRating) {
        rating = newRating;
    }

    public String getSleepNote() {
        return sleepNote;
    }

    public void setSleepNote(String note) {
        sleepNote = note;
    }

    //create a SleepNode (that can be added to SleepHistory)
    public SleepNode createSleepNode(){
        return new SleepNode(sleepTime, wakeTime, sleepNote, rating);
    }

    public void clearLog(){
        sleepTime = null;
        wakeTime = null;
        rating = 0;
        sleepNote = null;
    }
}

class SleepRecommendation extends JPanel /*implements MouseListener*/{
    //gives recommended time to go to sleep
    private Time wakeTime;
    private Time bedTime;
    private double age;
    private int sleepGoal;
    private SleepHistory sleepHistory;
    private Schedule calendar;
    private Time[] sleepRecs;
    private String[] sleepRecsMessages;
    //to reference for graphics for the page within this class

    public SleepRecommendation(Time wakeTime, Time bedTime, double age, int sleepGoal, SleepHistory sleepHistory, Schedule calendar){
        this.wakeTime = wakeTime;
        this.bedTime = bedTime;
        this.age = age;
        this.sleepGoal = sleepGoal;
        // the above is user stuff, but rn not calling it anywhere so placeholder
        this.sleepHistory = sleepHistory;
        this.calendar = calendar;
        sleepRecs = new Time[5];    //contains 5 recommended times.
        sleepRecsMessages = new String[5];
    }

    public Time[] calculateSleepRec(){
        /*returns array of 5 recommended times. [0] = sleepGoalRec (based on inputted goals)
        [1] = highest rec sleep cycles. [2] = 2nd highest rec sleep cycles. 
        [3] = 1 sleep cycle below sleepGoalRec. [4] = 2 sleep cycles below sleepGoalRec */
        
        //calculate goal sleep [0]
        int goalsleepRecMins = (wakeTime.getMinutes() - 15);    //takes 15 mins to fall asleep
        int goalsleepRecHours = (wakeTime.getHour() - sleepGoal);
        if (goalsleepRecMins < 0){  // make own method or something, generic/extends?
            goalsleepRecMins = 60 - Math.abs(goalsleepRecMins);
            goalsleepRecHours =- 1;
        }
        if (goalsleepRecHours < 0){
            goalsleepRecHours = 24 + goalsleepRecHours;
        }
        int numOfCycles = goalsleepRecHours
        ((bestsleepRecHours1 * 60) + bestsleepRecMins1) / 90;
        sleepRecs[0] = new Time(goalsleepRecHours, goalsleepRecMins);   //contains sleep recommendation based on sleep goal
        sleepRecsMessages[0] = "According to your set goals ( " + numOfCycles + "cycles), you should sleep at: ";

        //calculates best sleep times
        int hourSubtractor = 0;
        int minSubtractor = 0;
        int bestsleepRecMins1 = 0;
        int bestsleepRecHours1 = 0;
        int[] possibleCycles = getAgeSleepHours((int) age);
        for (int hour : possibleCycles){    //check if same as goalsleeprec
            hourSubtractor = hour;
            if (sleepGoal < hour){
                bestsleepRecMins1 = (wakeTime.getMinutes() - 15);
                bestsleepRecHours1 = (wakeTime.getHour() - hourSubtractor);
            } else{
                hourSubtractor += 1;    //if based on age, still need more sleep
                minSubtractor += 30;
                bestsleepRecMins1 = ((wakeTime.getMinutes() - minSubtractor)- 15);
                bestsleepRecHours1 = (wakeTime.getHour() - hourSubtractor);
            }
        }
        if (bestsleepRecMins1 < 0){
            bestsleepRecMins1 = 60 - Math.abs(bestsleepRecMins1);
            bestsleepRecHours1 =- 1;
        }
        if (bestsleepRecHours1 < 0){
            bestsleepRecHours1 = 24 + bestsleepRecHours1;
        }
        // int mins = (bestsleepRecHours * 60) + bestsleepRecMins;
        numOfCycles = ((bestsleepRecHours1 * 60) + bestsleepRecMins1) / 90;    // check this is right
        sleepRecs[1] = new Time(bestsleepRecHours1, bestsleepRecMins1); //contains sleep recommendation based on ideal sleep cycles for age
        // if (sleepRecs[1] == sleepRecs[2]){  // if same as goal-based rec, give rec with more sleep cycles
        //     mins += 90;
        //     bestsleepRecHours = mins / 60;
        //     bestsleepRecMins = ((mins % 60) * 60) / 100;
        //     sleepRecs[1] = new Time (bestsleepRecHours, bestsleepRecMins);
        // }
        sleepRecsMessages[1] = "To sleep " + numOfCycles + " cycles, you should sleep at: ";

        //calculate MoreSleepRec
        int bestsleepRecMins2 = ((wakeTime.getMinutes() - (minSubtractor+30)- 15)); //add another cycle
        int bestsleepRecHours2 = (wakeTime.getHour() - (hourSubtractor+1));

        if (bestsleepRecMins2 < 0){
            bestsleepRecMins2 = 60 - Math.abs(bestsleepRecMins2);
            bestsleepRecHours2 =- 1;
        }
        if (bestsleepRecHours2 < 0){
            bestsleepRecHours2 = 24 + bestsleepRecHours2;
        }
        numOfCycles = ((bestsleepRecHours2 * 60) + bestsleepRecMins2) / 90;
        sleepRecs[2] = new Time(bestsleepRecHours2, bestsleepRecMins2); //contains sleep recommendation based on ideal sleep cycles for age
        sleepRecsMessages[2] = "To sleep " + numOfCycles + " cycles, you should sleep at: ";

        //calculates less sleep times
        int othersleepRecMins = (wakeTime.getMinutes() - 15);    //takes 15 mins to fall asleep
        int othersleepRecHours = (wakeTime.getHour() - 4);
        if (othersleepRecMins < 0){
            othersleepRecMins = 60 - Math.abs(othersleepRecMins);
            othersleepRecHours =- 1;
        }
        if (othersleepRecHours < 0){
            othersleepRecHours = 24 + othersleepRecHours;
        }
        sleepRecs[4] = new Time(othersleepRecHours, othersleepRecMins); //contains sleep recommendation based on next best sleep cycle
        sleepRecsMessages[4] = "For less cycles, sleep at: ";
        if (othersleepRecHours < 0){
            // validation --> if rec is negative/less than 0 hours, then wrong. don't show/null.
        }
        
        int hourAdd = 0;
        int minAdd = 0;
        int lessSleepRecMins1 = 0;
        int lessSleepRecHours1 = 0;
        // check if same as 2 and 3. if so, -90 again --> shouldnt be tho? 2 and 3 should be greater than goal?
        lessSleepRecMins1 = goalsleepRecMins - 30;  // one cycle less than goal sleep rec
        lessSleepRecHours1 = goalsleepRecHours - 1;
        if (lessSleepRecMins1 < 0){
            lessSleepRecMins1 = 60 - Math.abs(lessSleepRecMins1);
            lessSleepRecHours1 =- 1;
        }
        if (lessSleepRecHours1 < 0){
            lessSleepRecHours1 = 24 + lessSleepRecHours1;
        }
        // int mins = (bestsleepRecHours * 60) + bestsleepRecMins;
        numOfCycles = ((lessSleepRecHours1 * 60) + lessSleepRecMins1) / 90;    // check this is right
        sleepRecs[3] = new Time(lessSleepRecHours1, lessSleepRecMins1); //contains sleep recommendation based on ideal sleep cycles for age
        // if (sleepRecs[1] == sleepRecs[2]){  // if same as goal-based rec, give rec with more sleep cycles
        //     mins += 90;
        //     bestsleepRecHours = mins / 60;
        //     bestsleepRecMins = ((mins % 60) * 60) / 100;
        //     sleepRecs[1] = new Time (bestsleepRecHours, bestsleepRecMins);
        // }
        sleepRecsMessages[3] = "To sleep " + numOfCycles + " cycles, you should sleep at: ";
        //calculate less sleep rec
        int lessSleepRecMins2 = lessSleepRecMins1 - 30;  // one cycle less than prev sleep rec
        int lessSleepRecHours2 = lessSleepRecHours1 - 1;
        if (lessSleepRecMins2 < 0){
            lessSleepRecMins2 = 60 - Math.abs(lessSleepRecMins2);
            lessSleepRecHours2 =- 1;
        }
        if (lessSleepRecHours2 < 0){
            lessSleepRecHours2 = 24 + lessSleepRecHours2;
        }
        numOfCycles = ((lessSleepRecHours2 * 60) + lessSleepRecMins2) / 90;
        sleepRecs[4] = new Time(lessSleepRecHours2, lessSleepRecMins2); //contains sleep recommendation based on ideal sleep cycles for age
        sleepRecsMessages[4] = "To sleep " + numOfCycles + " cycles, you should sleep at: ";


        return sleepRecs;

    }
  
    public int[] getAgeSleepHours(int age){    
        //get recommended hours of sleep by age
        if (age <= 0.25){ //newborn
            return new int[] {14,15,16,17};  //hours recommended
        }
        else if (age > 0.25 && age < 1){ //infant
            return new int[] {12,13,14,15,16};
        }
        else if (age >= 1 && age <= 2){  //toddler
            return new int[] {11,12,13,14};
        }
        else if (age >= 3 && age <= 5){  //preschool
            return new int[] {10,11,12,13};
        }
        else if (age >= 6 && age <= 12){  //school age
            return new int[] {9,10,11,12};
        }
        else if (age >= 13 && age <= 18){  //teen
            return new int[] {8,9,10};
        }
        else if (age >= 18 && age <= 64){  //adult
            return new int[] {7,8,9};
        }
        else if (age >= 65){  //older adult
            return new int[] {7,8};
        }
        return null;
    }

    public Time getSleepRecs(int index){
        return sleepRecs[index];
    }

    public String getSleepRecsMessages(int index){
        return sleepRecsMessages[index];
    }

    public String sleepHistorySummary(SleepNode curr){
        //if average of durations of past week/7 days is < sleep goal hours,
        if (4/2 != 3){
            return "You haven't been meeting your sleep goal. Try to sleep more today!";
        }
        //else if average is sleep goal at least -- counter > 0 && counter < 4:
        return "Good! You met your goal n times over the past 7 days";
        //else if coutner >= 4, return "Great! You met your goal n times in the past 7 days.";
    }

    //graphics for sleeprecommendation.
}

class SleepHistory extends JPanel /*implements MouseListener*/{
    //contains history of sleep from past week
    private int averageSleepDuration;
    ArrayList<SleepNode> sleepHistory = new ArrayList<SleepNode>();

    //ArrayList<SleepJournal> sleepNotes = new ArrayList<>();   // sleep journal stuff will be part of log sleep, which will input into sleep history


    public SleepHistory(){
        // make it add the day (figure out how to keep day of week in SleepNode)
    }

    //add data to sleep history
    public void addDay(SleepNode day){
        sleepHistory.add(day);
    }

    // public void addNote(SleepJournal note){
    //     sleepNotes.add(note);
    // }
  
    //calculate average sleep time
    public void calculateAverageDuration(){
        int total = 0;
        for (SleepNode day : sleepHistory){
            total += day.getDuration();
        }
        averageSleepDuration = total / sleepHistory.size();
    }

    //getter for average sleep
    public int getAverageDuration(){
        return averageSleepDuration;
    }

    // have if-statement in tester after adding day, if (sleepHistory.size() == 8), deleteOldSleepData
    //delete sleep data of oldest day
    public void deleteOldSleepData(){
        sleepHistory.remove(0);
    }
    
    //sort data in order (days of week)


    //draw method


    // have settings where can set which day you want new summary to show. default could be on sunday.)
}

class SleepNode{
    //sleep data for a single day
    private Time bedTime;
    private Time wakeTime;
    private int duration;
    private int dayOfWeek;
    private String sleepNote;
    private int sleepQuality;
    // add sleep journal -- notes, and quality of sleep
    //get the day of the week based on the date

    public SleepNode(Time bed, Time wake, String note, int sleepRating){
        bedTime = bed;
        wakeTime = wake;
        sleepNote = note;
        sleepQuality = sleepRating;
        this.calculateDuration(bed, wake);
    }
    // getters

    public Time getBedTime(){
        return bedTime;
    }

    public Time getWakeTime(){
        return wakeTime;
    }
    
    //getters for dayofweek. sleepnote, sleepquality

    public void calculateDuration(Time sleepTime, Time wakeTime){
        // turn sleeptime and wake time to int, calculate duration
        // duration = 
    }

    public int getDuration(){
        return duration;
    }
}

class Schedule extends JPanel /*implements MouseListener*/{
    ArrayList<Event> calendar;

    public Schedule(){
        ArrayList<Event> calendar = new ArrayList<>();
    }

    public void addEvent(DayNode a, Event e){
        int i=0;
        while(e.compareTo(calendar.get(i))==1){
            i++;
        }
        calendar.add(i, e);
    }
}

class DayNode{
    ArrayList <String> eventList;

    Date date;

    public DayNode(){
        ArrayList <String> eventList = new ArrayList<>();
        date = new Date();
    }
}

class Event{
    Time start;
    Time end;
    boolean repeating;
    String title;

    public Event(Time s, Time e, boolean r, String t){

        start = s;
        end = e;
        repeating = r;
        title = t;
    }

    public int compareTo(Event other){
        if(this.start.meridiem=="AM" && other.start.meridiem == "PM"){
            return -1;
        }
        if(this.start.meridiem=="PM" && other.start.meridiem == "AM"){
            return 1;
        }
        else {
            if (this.start.hours > other.start.hours) {
                return 1;
            }
            if (this.start.hours < other.start.hours) {
                return -1;
            }
            if (this.start.hours == other.start.hours) {
                if (this.start.minutes > other.start.minutes) {
                    return 1;
                }
                if (this.start.minutes < other.start.minutes) {
                    return -1;
                }
            }
            return 0;
        }

        //first check if one is am or pm
        //if other is am and this is pm, return 1
        //if other is pm and this is am return -1
        //if they are both one or the other, go into conditionals within the conditional that checks if they are the same
        //three conditionals within this one, one that checks this.start.hours<other.start.hours, one that checks this.start.hours<other.start.hours
        //and one that checks this.start.hours==other.start.hours
        //if they equal eachother then you want to have three more conditionals within that one, checking the same thing as the other three but this time with minutes
        //for all of these conditions, return -1 for less than, 1 for greater than, and 0 for equals

        }
}
