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
import java.util.Scanner;

import java.util.Date;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.text.SimpleDateFormat;

public class Main extends JPanel{
    public static final int WIDTH = 500;
    public static final int HEIGHT = 750;

    User user = new User("John",18,8,new Time(22,0),new Time(8,0));

    static JFrame frame = new JFrame("CozyMammoth");

    //App Pages (JPanels)
    Welcome Welcome = new Welcome(this);
    NewUser NewUser = new NewUser(this);
    SleepHistory SleepHistory = new SleepHistory(this);
    LogSleep LogSleep = new LogSleep(this);
    SleepRecommendation SleepRecs = new SleepRecommendation(this);
    Settings Settings = new Settings(this);
    Home Home = new Home(this);
    //CardLayout to manage JPanel "pages"
    CardLayout cl = new CardLayout();
    Date dateLastOpened;
    Date currentDate;

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

        //*****
        //look into the directory for the file
        // check if the file has a date, if it does, compare with current date

        Main mainInstance = new Main();
        
        //add panels to the cardlayout
        mainInstance.setLayout(mainInstance.cl);
        mainInstance.add(mainInstance.Welcome, "1");
        mainInstance.add(mainInstance.NewUser, "2");
        mainInstance.add(mainInstance.Home, "3");
        mainInstance.add(mainInstance.SleepHistory, "4");
        mainInstance.add(mainInstance.LogSleep, "5");
        mainInstance.add(mainInstance.SleepRecs, "6");
        mainInstance.add(mainInstance.Settings, "7");

        frame.setContentPane(mainInstance); //showing Welcome because it's the first panel added
        frame.pack();
        frame.setVisible(true);


        //testing user and time class
        Time bedTime = new Time(0, 00);
        Time wakeTime = new Time(8, 00);
        User testUser = new User("John Doe", 20, 8, bedTime, wakeTime);
        System.out.println("bedtime goal: " + testUser.getBedTime());
        System.out.println("wake up goal: " + testUser.getWakeTime());
        //commented out to make program compile - tiffany
        // SleepNode testMonday = new SleepNode(bedTime, wakeTime, "I woke up very refreshed.", 5);
        // SleepHistory testHistory = new SleepHistory(testMonday);
        // SleepHistory testhistory = new SleepHistory(testMonday);

        //commented out to make program compile - tiffany
        // SleepRecommendation testRec = new SleepRecommendation(testUser.getWakeTime(), testUser.getBedTime(), testUser.getAge(), testUser.getSleepGoal(), testhistory, testcalendar);

        //System.out.println("Based on your wake up goal, you should sleep at " + testRec.calculateSleepRec());

        // testRec.calculateSleepRec();
        // System.out.println(testRec.getSleepRecsMessages(0) + testRec.getSleepRecs(0));
        // System.out.println(testRec.getSleepRecsMessages(1) + testRec.getSleepRecs(1));
        // System.out.println(testRec.getSleepRecsMessages(2) + testRec.getSleepRecs(2));
        // System.out.println(testRec.getSleepRecsMessages(3) + testRec.getSleepRecs(3));
        // System.out.println(testRec.getSleepRecsMessages(4) + testRec.getSleepRecs(4));
        // System.out.println(testRec.sleepHistorySummary(testMonday));

        //fix
        // System.out.println(testHistory.getAverageDuration());
        SleepNode test = new SleepNode(new Time(0,0),new Time(6,0),"My sleep was ok.",3);
        mainInstance.SleepHistory.addDay(test);
    }
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

    //return only the hours as string (converted from military time)
    public String hoursToString(){
        if(hours == 0) return "" + 12;
        if(hours <= 12)return "" + hours;
        else return "" + (hours-12);
    }

    //return only the minutes as string (converted from military time)
    public String minsToString(){
        if(minutes < 10) return "0" + minutes;
        else return "" + minutes;
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
    @Override
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
    public void mouseExited(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
    public void mouseClicked(MouseEvent e){}
}

//NewUser page
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

                    mainInstance.SleepRecs.update(); //update the user values in SleepRecs
                    mainInstance.SleepRecs.drawPage(); //update the old sleepRecs in the cardlayout
                    
                    mainInstance.Settings = new Settings(mainInstance); //have to "reset" the settings page in mainInstance
                    mainInstance.add(mainInstance.Settings,"7"); //update the old settings in the cardlayout
                    
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

//Home page
class Home extends JPanel{
    Main mainInstance;

    public Home(Main main) {
        mainInstance = main;

        Color bg = new Color(37,44,64);
        this.setBackground(bg);
        this.setBorder(BorderFactory.createEmptyBorder(30,20,40,20));

        //main box
        Box box = Box.createVerticalBox();
        this.add(box);

        if(mainInstance.user != null) {
            //*****
            //create button that runs system.exit()
            // when button is pressed create processfiles object
            // get date, savepointtofile, then call system.exit()

            Box header = Box.createVerticalBox();

            JLabel title = new JLabel("Welcome, " + mainInstance.user.getName());
            header.add(title);

            title.setForeground(Color.WHITE);
            title.setFont(new Font("Arial", Font.PLAIN, 20));
            title.setAlignmentX(Component.CENTER_ALIGNMENT); //for center alignment in boxlayout
            title.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));

            //JFrame date
            Date j = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy");
            String formattedDate = dateFormat.format(j);
            JLabel date = new JLabel();
            date.setText(formattedDate);
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

            JPanel msgPanel = new JPanel(new GridBagLayout()); //placeholder for sleepmsg
            mainWrap.add(msgPanel);

            String msg = "Log your sleep activity to get personalized messages!";

            //making sure there are 7 SleepNodes in sleepHistory to work with, before assigning the message
            int counter = 0;
            for(int i = 0; i < 7; i++) {
                if(mainInstance.SleepHistory.sleepHistory.get(i) != null) counter++;
            }
            if (counter >= 7) {
                msg = mainInstance.SleepRecs.sleepHistorySummary();
            }

            JLabel sleepMsg = new JLabel("<html><p style=\"width:200px\">"+msg+"</p></html>");
            sleepMsg.setForeground(Color.WHITE);
            sleepMsg.setFont(new Font("Arial", Font.PLAIN, 20));
            msgPanel.add(sleepMsg);

            ImageIcon icon = new ImageIcon("graphics/face-sleeping.png");
            sleepMsg.setIcon(icon);
            sleepMsg.setHorizontalTextPosition(JLabel.CENTER);
            sleepMsg.setVerticalTextPosition(JLabel.BOTTOM);
            sleepMsg.setIconTextGap(40);

            //msg panel styling
            msgPanel.setBackground(bg2);
            msgPanel.setBorder(BorderFactory.createEmptyBorder(0,20,0,20));

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
                public void mousePressed(MouseEvent e) {
                    System.out.println("Sleep Recommendations pressed -> SleepRecs");
                    mainInstance.cl.show(mainInstance, "6");
                }
            });
            options[1].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    System.out.println("Log Sleep pressed -> LogSleep");
                    mainInstance.cl.show(mainInstance, "5");
                }
            });
            options[2].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    System.out.println("Sleep History pressed -> SleepHistory");
                    mainInstance.cl.show(mainInstance, "4");
                }
            });
            options[3].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    System.out.println("Settings pressed -> Settings");
                    mainInstance.cl.show(mainInstance, "7");
                }
            });
        }
    }
}

//LogSleep page
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
        //adding box that wraps the continue button to the main box
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
                    mainInstance.SleepHistory.addDay(toAdd); //get's the sleepHistory ArrayList from the SleepHistory instance in 

                    //when done, reset all the textfields/textareas/rating, and clear the LogSleep data (make null)
                    for(int i = 0; i < 4; i++) {
                        logHrsFields[i].setText("");
                    }
                    notesText.setText("");
                    for(int i = 0; i < 5; i++) {
                        faces[i].setIcon(faceIcons[i]);
                    }
                    bedtimeCB.setSelectedItem("AM");
                    wakeupCB.setSelectedItem("AM");
                    mainInstance.LogSleep.clearLog();

                    //go back to Home
                    mainInstance.SleepHistory.drawPage(); //update the sleepHistory page

                    mainInstance.Home = new Home(mainInstance); //have to "reset" the home page in mainInstance
                    mainInstance.add(mainInstance.Home,"3"); //update the old home in the cardlayout
                    
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

class SleepRecommendation extends JPanel{
    //gives recommended time to go to sleep
    private Time wakeTime;
    private Time bedTime;
    private double age;
    private int sleepGoal;
    private SleepHistory sleepHistory;
    //hard-coded in so that it could run... change it back when SleepRec is fully implemented - tiffany
    private Time[] sleepRecs = {new Time(1,0),new Time(2,0),new Time(3,0),new Time(4,0),new Time(5,0)}; /*new Time[5];*/    //contains 5 recommended times.
    private String[] sleepRecsMessages = new String[5];
    String sleepSummary;
    //to reference for graphics for the page within this class
    Main mainInstance;

    public SleepRecommendation(Main main){
        mainInstance = main;

        //user stuff - taken from mainInstance user
        if(mainInstance.user != null) {
            this.wakeTime = mainInstance.user.getWakeTime();
            this.bedTime = mainInstance.user.getBedTime();
            this.age = mainInstance.user.getAge();
            this.sleepGoal = mainInstance.user.getSleepGoal();
        }

        this.sleepHistory = mainInstance.SleepHistory;

        drawPage();
    }

    public void update(){
        this.wakeTime = mainInstance.user.getWakeTime();
        this.bedTime = mainInstance.user.getBedTime();
        this.age = mainInstance.user.getAge();
        this.sleepGoal = mainInstance.user.getSleepGoal();
        //probably have to run calculateSleepRecs/getSleepRecs
    }

    public void drawPage(){
        this.removeAll(); //meant to refresh the Panel

        //-----------------Panel Components/Graphics-----------------
        Color bg = new Color(42, 54, 89);
        this.setBackground(bg);
        this.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        Box mainBox = Box.createVerticalBox();
        this.add(mainBox);

        //exit arrow
        ImageIcon backIcon = new ImageIcon("graphics/angle-left.png");
        JLabel backLabel = new JLabel();
        backLabel.setIcon(backIcon);

        Box backBox = Box.createVerticalBox();
        backBox.add(backLabel);
        mainBox.add(backBox);

        backLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        backLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,440));

        backBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        //title label
        JLabel title = new JLabel("Get some sleep...");
        mainBox.add(title);
        //title styling
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.PLAIN, 25));
        title.setAlignmentX(Component.CENTER_ALIGNMENT); //for center alignment in boxlayout
        title.setBorder(BorderFactory.createEmptyBorder(20,0,40,0));

        ImageIcon icon = new ImageIcon("graphics/sunset.png");
        JLabel iconLabel = new JLabel();
        iconLabel.setIcon(icon);
        mainBox.add(iconLabel);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //subtitle1
        JLabel subtitle1 = new JLabel("Best Bedtime");
        mainBox.add(subtitle1);
        //subtitle1 styling
        subtitle1.setForeground(Color.WHITE);
        subtitle1.setFont(new Font("Arial", Font.PLAIN, 15));
        subtitle1.setAlignmentX(Component.CENTER_ALIGNMENT); //for center alignment in boxlayout
        subtitle1.setBorder(BorderFactory.createEmptyBorder(60,0,20,0));

        //best bedtime
        JTextField rec1 = new JTextField(sleepRecs[0].toString());
        rec1.setForeground(bg);
        rec1.setFont(new Font("Arial", Font.BOLD, 20));
        rec1.setEditable(false);
        rec1.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        rec1.setHorizontalAlignment(JLabel.CENTER);
        Box wrap1 = Box.createVerticalBox();
        wrap1.setBorder(BorderFactory.createEmptyBorder(0,150,0,150));
        
        wrap1.add(rec1);
        mainBox.add(wrap1);

        //subtitle2
        JLabel subtitle2 = new JLabel("Other Suggestions");
        mainBox.add(subtitle2);
        //subtitle2 styling
        subtitle2.setForeground(Color.WHITE);
        subtitle2.setFont(new Font("Arial", Font.PLAIN, 15));
        subtitle2.setAlignmentX(Component.CENTER_ALIGNMENT); //for center alignment in boxlayout
        subtitle2.setBorder(BorderFactory.createEmptyBorder(50,0,20,0));

        //other suggestions
        JTextField rec2 = new JTextField(sleepRecs[1].toString());
        JTextField rec3 = new JTextField(sleepRecs[2].toString());
        rec2.setForeground(bg);
        rec2.setFont(new Font("Arial", Font.BOLD, 20));
        rec2.setEditable(false);
        rec2.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        rec2.setHorizontalAlignment(JLabel.CENTER);
        rec3.setForeground(bg);
        rec3.setFont(new Font("Arial", Font.BOLD, 20));
        rec3.setEditable(false);
        rec3.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        rec3.setHorizontalAlignment(JLabel.CENTER);
        JPanel wrap2 = new JPanel(new GridLayout(1,2,40,0));
        Box wrap3 = Box.createVerticalBox();
        wrap2.setBorder(BorderFactory.createEmptyBorder(0,150,0,150));

        wrap2.setOpaque(false);
        
        wrap2.add(rec2);
        wrap2.add(rec3);
        wrap3.add(wrap2);
        mainBox.add(wrap3);

        //subtitle3
        JLabel subtitle3 = new JLabel("For Less Sleep");
        mainBox.add(subtitle3);
        //subtitle3 styling
        subtitle3.setForeground(Color.WHITE);
        subtitle3.setFont(new Font("Arial", Font.PLAIN, 15));
        subtitle3.setAlignmentX(Component.CENTER_ALIGNMENT); //for center alignment in boxlayout
        subtitle3.setBorder(BorderFactory.createEmptyBorder(50,0,20,0));

        //for less sleep
        JTextField rec4 = new JTextField(sleepRecs[3].toString());
        JTextField rec5 = new JTextField(sleepRecs[4].toString());
        rec4.setForeground(bg);
        rec4.setFont(new Font("Arial", Font.BOLD, 20));
        rec4.setEditable(false);
        rec4.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        rec4.setHorizontalAlignment(JLabel.CENTER);
        rec5.setForeground(bg);
        rec5.setFont(new Font("Arial", Font.BOLD, 20));
        rec5.setEditable(false);
        rec5.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        rec5.setHorizontalAlignment(JLabel.CENTER);
        JPanel wrap4 = new JPanel(new GridLayout(1,2,30,0));
        Box wrap5 = Box.createVerticalBox();
        wrap4.setBorder(BorderFactory.createEmptyBorder(0,150,0,150));

        wrap4.setOpaque(false);
        
        wrap4.add(rec4);
        wrap4.add(rec5);
        wrap5.add(wrap4);
        mainBox.add(wrap5);

        //NAV BUTTON MOUSELISTENERS
        //backLabel
        backLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //go back to home page
                mainInstance.cl.show(mainInstance,"3");
            }
        });
    }

    public Time[] calculateSleepRec(){
        /*returns array of 5 recommended times. [0] = sleepGoalRec (based on inputted goals)
        [1] = highest rec sleep cycles. [2] = 2nd highest rec sleep cycles. 
        [3] = 1 sleep cycle below sleepGoalRec. [4] = 2 sleep cycles below sleepGoalRec */
        
        //calculate goal sleep [0]
        int goalsleepRecMins = (wakeTime.getMinutes() - 15);    //takes 15 mins to fall asleep
        int goalsleepRecHours = (wakeTime.getHour() - sleepGoal);
        // if (goalsleepRecMins < 0){  // make own method or something, generic/extends?
        //     goalsleepRecMins = 60 - Math.abs(goalsleepRecMins);
        //     goalsleepRecHours =- 1;
        // }
        // if (goalsleepRecHours < 0){
        //     goalsleepRecHours = 24 + goalsleepRecHours;
        //     System.out.println("bye" + goalsleepRecHours);

        // }
        int numOfCycles = (sleepGoal * 60) / 105;           
        sleepRecs[0] = new Time(goalsleepRecHours, goalsleepRecMins);   //contains sleep recommendation based on sleep goal
        sleepRecsMessages[0] = "According to your set goals (~" + numOfCycles + " cycles), you should sleep at: ";

        //calculates best sleep times
        int hourSubtractor = 7; //best: 5-6 cycles
        int minSubtractor = 30;
        int bestsleepRecMins1 = 0;  //first best recommendation
        int bestsleepRecHours1 = 0;
        bestsleepRecMins1 = ((wakeTime.getMinutes() - minSubtractor)- 15);
        bestsleepRecHours1 = (wakeTime.getHour() - hourSubtractor);
        if(bestsleepRecHours1 == goalsleepRecHours && bestsleepRecMins1 == goalsleepRecMins){   //if same result as goal sleep
            bestsleepRecMins1 -= 45; //add another sleep cycle (105 mins)
            bestsleepRecHours1 -= 1;
        }
        // if (bestsleepRecMins1 < 0){
        //     bestsleepRecMins1 = 60 - Math.abs(bestsleepRecMins1);
        //     bestsleepRecHours1 =- 1;
        // }
        // else if (bestsleepRecHours1 < 0){
        //     bestsleepRecHours1 = 24 + bestsleepRecHours1;
        // }
        numOfCycles = ((bestsleepRecHours1 * 60) + bestsleepRecMins1) / 105;    // check this is right
        sleepRecs[1] = new Time(bestsleepRecHours1, bestsleepRecMins1); //contains sleep recommendation based on ideal sleep cycles for age
        // if (sleepRecs[1] == sleepRecs[2]){  // if same as goal-based rec, give rec with more sleep cycles
        //     mins += 90;
        //     bestsleepRecHours = mins / 60;
        //     bestsleepRecMins = ((mins % 60) * 60) / 100;
        //     sleepRecs[1] = new Time (bestsleepRecHours, bestsleepRecMins);
        // }
        sleepRecsMessages[1] = "To sleep " + numOfCycles + " cycles, you should sleep at: ";

        //calculate MoreSleepRec
        int bestsleepRecMins2 = ((wakeTime.getMinutes() - (minSubtractor+30)- 15)); //add another cycle (6 cycles recommended)
        int bestsleepRecHours2 = (wakeTime.getHour() - (hourSubtractor+1));

        // if (bestsleepRecMins2 < 0){
        //     bestsleepRecMins2 = 60 - Math.abs(bestsleepRecMins2);
        //     bestsleepRecHours2 =- 1;
        // }
        // else if (bestsleepRecHours2 < 0){
        //     bestsleepRecHours2 = 24 + bestsleepRecHours2;
        // }
        numOfCycles = ((bestsleepRecHours2 * 60) + bestsleepRecMins2) / 90;
        sleepRecs[2] = new Time(bestsleepRecHours2, bestsleepRecMins2); //contains sleep recommendation based on ideal sleep cycles for age
        sleepRecsMessages[2] = "To sleep " + numOfCycles + " cycles, you should sleep at: ";

        //calculates less sleep times
        int hourAdd = 0;
        int minAdd = 0;
        int lessSleepRecMins1 = 0;
        int lessSleepRecHours1 = 0;
        // check if same as 2 and 3. if so, -90 again --> shouldnt be tho? 2 and 3 should be greater than goal?
        lessSleepRecMins1 = goalsleepRecMins - 30;  // one cycle less than goal sleep rec
        lessSleepRecHours1 = goalsleepRecHours - 1;
        // if (lessSleepRecMins1 < 0){
        //     lessSleepRecMins1 = 60 - Math.abs(lessSleepRecMins1);
        //     lessSleepRecHours1 =- 1;
        // }
        // if (lessSleepRecHours1 < 0){
        //     lessSleepRecHours1 = 24 + lessSleepRecHours1;
        // }
        // int mins = (bestsleepRecHours * 60) + bestsleepRecMins;
        numOfCycles = ((lessSleepRecHours1 * 60) + lessSleepRecMins1) / 90;    // check this is right
        sleepRecs[3] = new Time(lessSleepRecHours1, lessSleepRecMins1); //contains sleep recommendation based on ideal sleep cycles for age
        sleepRecsMessages[3] = "To sleep " + numOfCycles + " cycles, you should sleep at: ";
        //calculate less sleep rec
        int lessSleepRecMins2 = lessSleepRecMins1 - 30;  // one cycle less than prev sleep rec
        int lessSleepRecHours2 = lessSleepRecHours1 - 1;
        // if (lessSleepRecMins2 < 0){
        //     lessSleepRecMins2 = 60 - Math.abs(lessSleepRecMins2);
        //     lessSleepRecHours2 =- 1;
        // }
        // if (lessSleepRecHours2 < 0){
        //     lessSleepRecHours2 = 24 + lessSleepRecHours2;
        // }
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

    public String sleepHistorySummary(){
        //if average of durations of past week/7 days is < sleep goal hours,
        double average = 3;   // get history
        if (average <= 1.0){
            this.sleepSummary = "You haven't been meeting your sleep goal. Try to sleep more today!";
        } else if (average > 1.0 && average < 4.0){
            this.sleepSummary = "Good! You met your goal n times over the past 7 days";
        }
        //else if average is sleep goal at least -- counter > 0 && counter < 4:
        else if (average >= 4.0){
            this.sleepSummary = "Great! You met your goal n times in the past 7 days.";
        }
        return this.sleepSummary;
    }
}

class SleepHistory extends JPanel{
    //contains history of sleep from past week
    private int averageSleepDuration;
    ArrayList<SleepNode> sleepHistory = new ArrayList<SleepNode>();

    Main mainInstance;

    public SleepHistory(Main main){
        mainInstance = main;

        //make sure sleepHistory starts off with 7 null items
        for(int i = 0; i < 7; i++) {
            sleepHistory.add(null);
        }

        drawPage();
    }

    public void drawPage() {
        this.removeAll(); //meant to refresh the Panel

        Color bg = new Color(42, 54, 89);
        Color bg2 = new Color(54,75,140);
        Color textbg = new Color(211, 221, 252);
        this.setBackground(bg);
        this.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        Box mainBox = Box.createVerticalBox();
        this.add(mainBox);

        //exit arrow
        ImageIcon backIcon = new ImageIcon("graphics/angle-left.png");
        JLabel backLabel = new JLabel();
        backLabel.setIcon(backIcon);

        Box backBox = Box.createVerticalBox();
        backBox.add(backLabel);
        mainBox.add(backBox);

        backLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        backLabel.setBorder(BorderFactory.createEmptyBorder(0,0,20,440));

        backBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        backBox.add(Box.createHorizontalGlue());

        ImageIcon icon = new ImageIcon("graphics/clouds-moon.png");
        JLabel iconLabel = new JLabel("Sleep History");
        iconLabel.setIcon(icon);
        iconLabel.setBorder(BorderFactory.createEmptyBorder(40,0,40,0));
        iconLabel.setIconTextGap(20);
        iconLabel.setForeground(bg);
        iconLabel.setFont(new Font("Arial", Font.PLAIN, 25));

        //infoPanel will display info form the corresponding DayNodes in SleepHistory
        JPanel infoPanel = new JPanel();
        CardLayout historycl = new CardLayout();
        infoPanel.setLayout(historycl);

        JPanel titlePanel = new JPanel();
        titlePanel.add(iconLabel);
        titlePanel.setBackground(textbg);
        infoPanel.add(titlePanel, "0");

        JPanel noDayPanel = new JPanel(new GridBagLayout());
        noDayPanel.setBackground(textbg);
        JLabel msg = new JLabel("No data for this day.");
        msg.setForeground(bg);
        msg.setFont(new Font("Arial", Font.PLAIN, 15));
        noDayPanel.add(msg);
        infoPanel.add(noDayPanel, "1");

        mainBox.add(infoPanel); //panel containing all the cardlayouts

        JProgressBar[] bars = new JProgressBar[7];
        Box[] wraps = new Box[7];
        JLabel[] texts = new JLabel[7];

        if(mainInstance.user != null) {
            //showing how many hours they got in proportion to their sleepGoal
            int max = mainInstance.user.getSleepGoal();
            for(int i = 0; i < 7; i++) {
                bars[i] = new JProgressBar(SwingConstants.HORIZONTAL,0,max);
                texts[i] = new JLabel("N/A"); //date default
                wraps[i] = Box.createVerticalBox();

                //if this day in sleepHistory isn't null, draw it
                if(sleepHistory.get(i) != null) {
                    bars[i].setValue(sleepHistory.get(i).getDuration()); //progress bar
                    texts[i].setText(sleepHistory.get(i).getDate()); //date
                }
                else {
                    bars[i].setValue(0); //progress bar default
                }
                //combining text + bar into a box wrapper
                wraps[i].add(texts[i]);
                wraps[i].add(bars[i]);
                mainBox.add(wraps[i]);

                //text styling
                texts[i].setForeground(Color.WHITE);
                texts[i].setFont(new Font("Arial", Font.PLAIN, 15));
                texts[i].setBorder(BorderFactory.createEmptyBorder(20,0,0,0));
                texts[i].setAlignmentX(Component.LEFT_ALIGNMENT);
            }
        }

        //EXPAND DAYNODES
        wraps[0].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(sleepHistory.get(0) == null) {
                    historycl.show(infoPanel,"1");
                }
                else {
                    //create new panel and show it
                }
            }
        });
        wraps[1].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(sleepHistory.get(1) == null) {
                    historycl.show(infoPanel,"1");
                }
                else {
                    //create new panel and show it
                    //create new panel and show it
                    JPanel panel = new JPanel(new GridLayout(2,3,10,10));
                    JLabel date = new JLabel(sleepHistory.get(1).getDate());
                    JLabel bedtime = new JLabel("Sleep Time: " + sleepHistory.get(1).getBedTime());
                    JLabel waketime = new JLabel("Wake Time: " + sleepHistory.get(1).getWakeTime());
                    JLabel sleepQuality = new JLabel("Sleep Quality: " + sleepHistory.get(1).getSleepQuality() + "/5");
                    JLabel sleepNotes = new JLabel("Notes: " + sleepHistory.get(1).getSleepNote());

                    panel.setBackground(textbg);
                    panel.add(date);
                    panel.add(bedtime);
                    panel.add(waketime);
                    panel.add(new JLabel());
                    panel.add(sleepQuality);
                    panel.add(sleepNotes);


                    infoPanel.add(panel, "2");
                    historycl.show(infoPanel,"2");
                }
            }
        });
        wraps[2].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(sleepHistory.get(2) == null) {
                    historycl.show(infoPanel,"1");
                }
                else {
                    //create new panel and show it
                    JPanel panel = new JPanel(new GridLayout(2,3,10,10));
                    JLabel date = new JLabel(sleepHistory.get(2).getDate());
                    JLabel bedtime = new JLabel("Sleep Time: " + sleepHistory.get(2).getBedTime());
                    JLabel waketime = new JLabel("Wake Time: " + sleepHistory.get(2).getWakeTime());
                    JLabel sleepQuality = new JLabel("Sleep Quality: " + sleepHistory.get(2).getSleepQuality() + "/5");
                    JLabel sleepNotes = new JLabel("Notes: " + sleepHistory.get(2).getSleepNote());

                    panel.setBackground(textbg);
                    panel.add(date);
                    panel.add(bedtime);
                    panel.add(waketime);
                    panel.add(new JLabel());
                    panel.add(sleepQuality);
                    panel.add(sleepNotes);


                    infoPanel.add(panel, "2");
                    historycl.show(infoPanel,"2");
                }
            }
        });
        wraps[3].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(sleepHistory.get(3) == null) {
                    historycl.show(infoPanel,"1");
                }
                else {
                    //create new panel and show it
                    JPanel panel = new JPanel(new GridLayout(2,3,10,10));
                    JLabel date = new JLabel(sleepHistory.get(3).getDate());
                    JLabel bedtime = new JLabel("Sleep Time: " + sleepHistory.get(3).getBedTime());
                    JLabel waketime = new JLabel("Wake Time: " + sleepHistory.get(3).getWakeTime());
                    JLabel sleepQuality = new JLabel("Sleep Quality: " + sleepHistory.get(3).getSleepQuality() + "/5");
                    JLabel sleepNotes = new JLabel("Notes: " + sleepHistory.get(3).getSleepNote());

                    panel.setBackground(textbg);
                    panel.add(date);
                    panel.add(bedtime);
                    panel.add(waketime);
                    panel.add(new JLabel());
                    panel.add(sleepQuality);
                    panel.add(sleepNotes);


                    infoPanel.add(panel, "2");
                    historycl.show(infoPanel,"2");
                }
            }
        });
        wraps[4].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(sleepHistory.get(4) == null) {
                    historycl.show(infoPanel,"1");
                }
                else {
                    //create new panel and show it
                    JPanel panel = new JPanel(new GridLayout(2,3,10,10));
                    JLabel date = new JLabel(sleepHistory.get(4).getDate());
                    JLabel bedtime = new JLabel("Sleep Time: " + sleepHistory.get(4).getBedTime());
                    JLabel waketime = new JLabel("Wake Time: " + sleepHistory.get(4).getWakeTime());
                    JLabel sleepQuality = new JLabel("Sleep Quality: " + sleepHistory.get(4).getSleepQuality() + "/5");
                    JLabel sleepNotes = new JLabel("Notes: " + sleepHistory.get(4).getSleepNote());

                    panel.setBackground(textbg);
                    panel.add(date);
                    panel.add(bedtime);
                    panel.add(waketime);
                    panel.add(new JLabel());
                    panel.add(sleepQuality);
                    panel.add(sleepNotes);


                    infoPanel.add(panel, "2");
                    historycl.show(infoPanel,"2");
                }
            }
        });
        wraps[5].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(sleepHistory.get(5) == null) {
                    historycl.show(infoPanel,"1");
                }
                else {
                    //create new panel and show it
                    //create new panel and show it
                    JPanel panel = new JPanel(new GridLayout(2,3,10,10));
                    JLabel date = new JLabel(sleepHistory.get(5).getDate());
                    JLabel bedtime = new JLabel("Sleep Time: " + sleepHistory.get(5).getBedTime());
                    JLabel waketime = new JLabel("Wake Time: " + sleepHistory.get(5).getWakeTime());
                    JLabel sleepQuality = new JLabel("Sleep Quality: " + sleepHistory.get(5).getSleepQuality() + "/5");
                    JLabel sleepNotes = new JLabel("Notes: " + sleepHistory.get(5).getSleepNote());

                    panel.setBackground(textbg);
                    panel.add(date);
                    panel.add(bedtime);
                    panel.add(waketime);
                    panel.add(new JLabel());
                    panel.add(sleepQuality);
                    panel.add(sleepNotes);


                    infoPanel.add(panel, "2");
                    historycl.show(infoPanel,"2");
                }
            }
        });
        wraps[6].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(sleepHistory.get(6) == null) {
                    historycl.show(infoPanel,"1");
                }
                else {
                    //create new panel and show it
                    JPanel panel = new JPanel(new GridLayout(2,3,10,10));
                    JLabel date = new JLabel(sleepHistory.get(6).getDate());
                    JLabel bedtime = new JLabel("Sleep Time: " + sleepHistory.get(6).getBedTime());
                    JLabel waketime = new JLabel("Wake Time: " + sleepHistory.get(6).getWakeTime());
                    JLabel sleepQuality = new JLabel("Sleep Quality: " + sleepHistory.get(6).getSleepQuality() + "/5");
                    JLabel sleepNotes = new JLabel("Notes: " + sleepHistory.get(6).getSleepNote());

                    panel.setBackground(textbg);
                    panel.add(date);
                    panel.add(bedtime);
                    panel.add(waketime);
                    panel.add(new JLabel());
                    panel.add(sleepQuality);
                    panel.add(sleepNotes);


                    infoPanel.add(panel, "2");
                    historycl.show(infoPanel,"2");
                }
            }
        });

        //NAV BUTTON MOUSELISTENERS
        //backLabel
        backLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //go back to home page
                mainInstance.cl.show(mainInstance,"3");
                historycl.show(infoPanel,"0");
            }
        });
    }

    //add a new SleepNode to sleepHistory
    public void addDay(SleepNode day){
        if (sleepHistory.size() == 7) {
            this.deleteOldSleepData();
        }
        sleepHistory.add(day);

        drawPage(); //redraw SleepHistory
    }
  
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
    private String sleepNote;
    private int sleepQuality;
    private int dayOfWeek;
    private Date date;

    public SleepNode(Time bed, Time wake, String note, int sleepRating){
        bedTime = bed;
        wakeTime = wake;
        sleepNote = note;
        sleepQuality = sleepRating;
        date = new Date();

        //get the day of the week based on Calendar
        Calendar c = Calendar.getInstance();
        dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        System.out.println(dayOfWeek);

        this.calculateDuration();
    }

    // getters
    public Time getBedTime(){
        return bedTime;
    }
    public Time getWakeTime(){
        return wakeTime;
    }
    public int getDayOfWeek(){
        return dayOfWeek;
    }
    public String getSleepNote(){
        return sleepNote;
    }
    public int getSleepQuality(){
        return sleepQuality;
    }

    public String getDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("EE, MMM d, yyyy");
        String formattedDate = dateFormat.format(date);
        return formattedDate;
    }

    public void calculateDuration(){
        // turn sleeptime and wake time to int, calculate duration
        if(bedTime.hours < wakeTime.hours) {
            duration = wakeTime.hours - bedTime.hours;
        }
        else {
            duration = 24 - bedTime.hours + wakeTime.hours;
        }
    }

    public int getDuration(){
        return duration;
    }
}

//Settings page
class Settings extends JPanel{
    Main mainInstance;

    public Settings(Main main) {
        mainInstance = main;

        Color bg = new Color(37,44,64);
        this.setBackground(bg);
        this.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        Box mainBox = Box.createVerticalBox();
        this.add(mainBox);

        //exit arrow
        ImageIcon backIcon = new ImageIcon("graphics/angle-left.png");
        JLabel backLabel = new JLabel();
        backLabel.setIcon(backIcon);

        Box backBox = Box.createVerticalBox();
        backBox.add(backLabel);
        mainBox.add(backBox);

        backLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        backLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,440));

        backBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        backBox.add(Box.createHorizontalGlue());

        //title label
        JLabel title = new JLabel("Edit User Settings");
        mainBox.add(title);
        //title styling
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.PLAIN, 25));
        title.setAlignmentX(Component.CENTER_ALIGNMENT); //for center alignment in boxlayout
        title.setBorder(BorderFactory.createEmptyBorder(25,0,25,0));

        Box subBox = Box.createVerticalBox();
        mainBox.add(subBox);
        subBox.setBorder(BorderFactory.createEmptyBorder(0,50,50,50));

        //initializing components
        JLabel[] labels = new JLabel[5];
        JTextField[] texts = new JTextField[7];
        Box text4 = Box.createHorizontalBox();
        Box text4wrap = Box.createVerticalBox();
        Box text5 = Box.createHorizontalBox();
        Box text5wrap = Box.createVerticalBox();
        JButton button = new JButton("Done");

        //initializing labels 
        labels[0] = new JLabel("Name");
        labels[1] = new JLabel("Age");
        labels[2] = new JLabel("Sleep Goal");
        labels[3] = new JLabel("Bedtime");
        labels[4] = new JLabel("Wake Up");

        //initializing textfields
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
            subBox.add(wrap);

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
        subBox.add(wrap4);
        wrap4.setAlignmentX(Component.CENTER_ALIGNMENT);
        //wakeup box (label + wakeup box)
        Box wrap5 = Box.createVerticalBox();
        wrap5.add(labels[4]);
        wrap5.add(text5wrap);
        subBox.add(wrap5);
        wrap5.setAlignmentX(Component.CENTER_ALIGNMENT);

        //set the values of the textfields to the user data
        if(mainInstance.user != null) {
            texts[0].setText(mainInstance.user.getName());
            texts[1].setText(""+mainInstance.user.getAge());
            texts[2].setText(""+mainInstance.user.getSleepGoal());
            texts[3].setText(mainInstance.user.getBedTime().hoursToString());
            texts[4].setText(mainInstance.user.getBedTime().minsToString());
            texts[5].setText(mainInstance.user.getWakeTime().hoursToString());
            texts[6].setText(mainInstance.user.getWakeTime().minsToString());
            bedtimeCB.setSelectedItem(mainInstance.user.getBedTime().meridiem);
            wakeupCB.setSelectedItem(mainInstance.user.getWakeTime().meridiem);
        }

        //BUTTON
        //adding box that wraps the done button to the main box
        Box buttonWrap = Box.createVerticalBox();
        buttonWrap.add(button);
        subBox.add(buttonWrap);

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
                    
                    mainInstance.SleepRecs.update(); //update the user values in SleepRecs
                    mainInstance.SleepRecs.drawPage(); //update the old sleepRecs in the cardlayout
                    
                    mainInstance.Settings = new Settings(mainInstance); //have to "reset" the settings page in mainInstance
                    mainInstance.add(mainInstance.Settings,"7"); //update the old settings in the cardlayout
                    
                    mainInstance.cl.show(mainInstance, "3");
                }
			}
            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(buttColor);
            }
		});

        //NAV BUTTON MOUSELISTENERS
        //backLabel
        backLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mainInstance.Settings = new Settings(mainInstance); //have to "reset" the settings page in mainInstance
                mainInstance.add(mainInstance.Settings,"7"); //update the old settings in the cardlayout
                //go back to home
                mainInstance.cl.show(mainInstance,"3");
            }
        });
    }
}


//unimplemented classes...
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

class Event {
    Time start;
    Time end;
    boolean repeating;
    String title;

    public Event(Time s, Time e, boolean r, String t) {

        start = s;
        end = e;
        repeating = r;
        title = t;
    }

    public int compareTo(Event other) {
        if (this.start.meridiem == "AM" && other.start.meridiem == "PM") {
            return -1;
        }
        if (this.start.meridiem == "PM" && other.start.meridiem == "AM") {
            return 1;
        } else {
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
    }
}
