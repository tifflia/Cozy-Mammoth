import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.MouseListener;
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

public class Main extends JPanel implements MouseListener{
    public static final int WIDTH = 500;
    public static final int HEIGHT = 750;

    User user;

    static JFrame frame = new JFrame("CozyMammoth");

    //App Pages
    JPanel Welcome = new JPanel();
    JPanel NewUser = new JPanel();
    JPanel Home = new JPanel();
    JPanel SleepHistory = new JPanel();
    JPanel LogSleep1 = new JPanel();
    JPanel LogSleep2 = new JPanel();
    JPanel SleepRecs = new JPanel();
    JPanel Schedule = new JPanel();
    JPanel Settings = new JPanel();
    //CardLayout to manage JPanel "pages"
    CardLayout cl = new CardLayout();

    //constructor
    public Main(){
        addMouseListener(this);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        //initialize JPanels
        initWelcome();
        initNewUser();
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
        System.out.println("Based on your wake up goal, you should sleep at " + testRec.calculateSleepRec());
        //fix
        // System.out.println(testHistory.getAverageDuration());
    }

    // //do you need this? if we have initWelcome
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

    //Main Welcome page
    private void initWelcome() {
        Color bg = new Color(60, 86, 166);
        this.Welcome.setBackground(bg);

        Box box = Box.createVerticalBox();
        this.Welcome.add(box);

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
        ImageIcon logo = new ImageIcon("logo.png");
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

    //New User page
    private void initNewUser() {
        Color bg = new Color(37,44,64);
        this.NewUser.setBackground(bg);
        this.NewUser.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));

        //main box
        Box box = Box.createVerticalBox();
        this.NewUser.add(box);

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

        button.addMouseListener(new MouseListener() {
			@Override
			public void mousePressed(MouseEvent e) {
                Color clickedButtColor = new Color(51, 77, 156);
                button.setBackground(clickedButtColor);
				System.out.println("button has been pressed");
			}
            public void mouseReleased(MouseEvent e) {
                button.setBackground(buttColor);
            }
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
            public void mouseClicked(MouseEvent e) {
                //check if the values for age, sleep goal, bedtime, and wake up are valid
                // if(Time.checkTime()){}

                //textfield.getText(); = string
                //combobox.getSelectedItem(); = string
            }
		});
    }

    //MOUSELISTENER THINGS
    public void mousePressed(MouseEvent e) {
        System.out.println("Mouse pressed detected on " + e.getComponent().getClass().getName() + ".");

        //if no User has been initialized, pull up the NewUser panel
        if(this.user == null) {
            this.cl.show(this, "2");
        }
        //otherwise, pull up the Home Panel
        else {
            
        }
    }
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
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

    //check if the inputted time is within the appropriate bounds
    public static boolean checkTime(int h, int m) {
        return (h >= 0 && h < 24) && (m >= 0 && m < 60);
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

class LogSleep extends JPanel /*implements MouseListener*/{
    private int wakeTime;
    private int sleepTime;

    public LogSleep(int sleepTime, int wakeTime) {
        this.sleepTime = sleepTime;
        this.wakeTime = wakeTime;
    }

    //Getter and setter methods for sleep time and wake time
    public int getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
    }

    public int getWakeTime() {
        return wakeTime;
    }

    public void setWakeTime(int wakeTime) {
        this.wakeTime = wakeTime;
    }

    public void sleepJournal() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a sleep journal entry: ");
        String s = input.nextLine();

        //maybe easier to just have String parameter instead of user input?
        //alternative
//        public SleepJournal(String journal){
//            this.sleepJournal = journal;
//        }
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

    public SleepRecommendation(Time wakeTime, Time bedTime, double age, int sleepGoal, SleepHistory sleepHistory, Schedule calendar){
        this.wakeTime = wakeTime;
        this.bedTime = bedTime;
        this.age = age;
        this.sleepGoal = sleepGoal;
        // the above is user stuff, but rn not calling it anywhere so placeholder
        this.sleepHistory = sleepHistory;
        this.calendar = calendar;

    }
    //will implement more complex algorithms later (considering rem cycle, etc)
    public String calculateSleepRec(){
        int sleepRecMins = (wakeTime.getMinutes() - 15);    //takes 15 mins to fall asleep
        int sleepRecHours = (wakeTime.getHour() - sleepGoal);
        if (sleepRecMins < 0){
            sleepRecMins = 60 - Math.abs(sleepRecMins);
            sleepRecHours =- 1;
        }
        if (sleepRecHours < 0){
            sleepRecHours = 24 + sleepRecHours;
        }
        Time sleeprec = new Time(sleepRecHours, sleepRecMins);
        //calculates best sleep times
        int bestsleepRecMins = (wakeTime.getMinutes() - 15);    //takes 15 mins to fall asleep
        int bestsleepRecHours = (wakeTime.getHour() - 9);
        if (bestsleepRecMins < 0){
            bestsleepRecMins = 60 - Math.abs(bestsleepRecMins);
            bestsleepRecHours =- 1;
        }
        if (bestsleepRecHours < 0){
            bestsleepRecHours = 24 + bestsleepRecHours;
        }
        Time bestsleeprec = new Time(bestsleepRecHours, bestsleepRecMins);

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
        Time othersleeprec = new Time(othersleepRecHours, othersleepRecMins);
        return "According to your set goals, you should sleep at: " + sleeprec.toString()
        +"\n For sleeping 5-6 cycles, you should sleep at: " + "this aint working" + bestsleeprec.toString()
        + "\n For less cycles, sleep at: " + "will work on this" + othersleeprec.toString();  // change name to goalsleeprec

    }
    //     Time sleeprec = new Time(sleepRecHours, sleepRecMins);
    //     String toreturn = sleeprec.toString();
    //     //now check if best, and calculate other options
    //     int[] userAgeSleepHours = getAgeSleepHours();
    //     boolean matchAge = true;
        
    //     for (int i = 0; i < userAgeSleepHours.length; i++){
    //         if (sleepGoal == userAgeSleepHours[i]){     //goal hours matches recommendation for age
           
    //         }
    //         else{matchAge = false;}
    //     }
    //     if (matchAge = false){  //check other hours in age range
    //         for (int i = 0; i < userAgeSleepHours.length; i++){

    //         }
    //     }
        //Best Recommendation using goal sleep hours, wake up time and sleep time.
        //Checks if fits with recommended sleep hours according to age
            // check duration/hours, ie: if recommendation is 8, make sure fits with recommendation for age)
  //  }

    public int[] getAgeSleepHours(){    
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
}

class SleepHistory extends JPanel /*implements MouseListener*/{
    //contains history of sleep from past week
    private int averageSleepDuration;
    ArrayList<SleepNode> sleepHistory;

    //ArrayList<SleepJournal> sleepNotes = new ArrayList<>();   // sleep journal stuff will be part of log sleep, which will input into sleep history


    public SleepHistory(SleepNode day){
        // set null
        // make it add the day (figure out how to keep day of week in SleepNode)
        sleepHistory = new ArrayList<SleepNode>();
        //sleepNotes.add(note);
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

    public DayNode(){
        ArrayList <String> eventList = new ArrayList<>();
    }
    //ask if they want add event method

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
