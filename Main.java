import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.GraphicsEnvironment; //testing font
import java.awt.Dimension;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Date;
import java.util.Scanner;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class Main extends JPanel implements MouseListener{
    public static final int WIDTH = 500;
    public static final int HEIGHT = 750;
    User user;
    //field for each page (kinda like having multiple "Worlds" from the other homeworks)

    //constructor
    public Main(){
        addMouseListener(this);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        //initialize the pages
    }

    public static void main(String[] args){
        //jframe stuff
        JFrame frame = new JFrame("CozyMammoth");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Main mainInstance = new Main();
        frame.setContentPane(mainInstance);
        frame.pack();
        frame.setVisible(true);


        //testing user and time class
        Time bedTime = new Time(0, 00);
        Time wakeTime = new Time(8, 00);
        User testUser = new User("John Doe", 20, 8, bedTime, wakeTime);
        System.out.println("bedtime goal: " + testUser.getBedTime());
        System.out.println("wake up goal: " + testUser.getWakeTime());
        SleepRecommendation testRec = new SleepRecommendation(testUser.getWakeTime(), testUser.getBedTime(), testUser.getAge(), testUser.getSleepGoal());
        System.out.println("Based on your wake up goal, you should sleep at " + testRec.calculateSleepRec());
        SleepNode testMonday = new SleepNode(bedTime, wakeTime);
        SleepJournal testJournal = new SleepJournal();
        System.out.println(testJournal.getJournal());
        SleepHistory testHistory = new SleepHistory(testMonday, testJournal);
        //fix
        System.out.println(testHistory.getAverageDuration());
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //draw the welcome screen
        Color bg = new Color(60, 86, 166);
        g.setColor(bg);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        try {
            BufferedImage logo = ImageIO.read(new File("logo.png"));
            g.drawImage(logo,100,150,300,300,null);
            Font title = Font.createFont(Font.TRUETYPE_FONT, new File("Rubik.ttf"));
            g.setColor(Color.WHITE);
            g.setFont(title);
            g.drawString("Cozy Mammoth",0,400);
            //FIGURE OUT FONT LATER
        }
        catch (FontFormatException e) {
            e.printStackTrace();
        }
        catch (IOException e) { 
            e.printStackTrace();
        }

        //add a buffer of sorts?
        //MousePressed as a buffer? or make it execute a loading animation of sorts
    }

    public void drawNewUser() {
        Graphics g = this.getGraphics();
        Color bg = new Color(37,44,64);
        g.setColor(bg);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        //figure out jframe jcomponent stuff

        // g.setColor(Color.WHITE);
        // JLabel label1, label2, label3, label4, label5;
        // JButton button;
        // JTextField text1, text2, text3, text4, text5;
        // button = new JButton("Done");
        // label1 = new JLabel("Name");
        // label2 = new JLabel("Age");
        // label3 = new JLabel("Sleep Goal");
        // label4 = new JLabel("Bedtime");
        // label5 = new JLabel("Wake Up");
        // text1 = new JTextField(20);
        // text2 = new JTextField(20);
        // text3 = new JTextField(20);
        // text4 = new JTextField(20);
        // text5 = new JTextField(20);
        // label1.setBounds(50,50,100,30);
        // this.add(label1);
        // this.setVisible(true);
    }

    //don't need a run method, but need methods to listen to the buttons that are pressed/mouse clicks
    public void mousePressed(MouseEvent e) {
        System.out.println("Mouse pressed");
        //if no User has been initialized, draw the NewUser page
        if(user == null) {
            drawNewUser();
        }
        //otherwise, continue to home page
    }

    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mouseClicked(MouseEvent e) {
        System.out.println("Mouse clicked (# of clicks: " + e.getClickCount() + ") detected on " + e.getComponent().getClass().getName() + ".");
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
    private int hours; //0-24 (excluded)
    private int minutes;//0-60 (excluded)
    private String meridiem; //AM or PM

    //constructor
    public Time(int h, int m) {
        //don't use checktime here, use it when the click input is registered
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

class LogSleep{
    private int wakeTime;
    private int sleepTime;

    public LogSleep(int sleepTime, int wakeTime) {
        this.sleepTime = sleepTime;
        this.wakeTime = wakeTime;
    }

    //Getter and setter methods for sleep time and wake time
    public int getSleepTime(){
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


    // draw method
}

class SleepJournal{
    private String sleepJournal;
    public SleepJournal(){
        //user can type into console
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a sleep journal entry: ");
        String s = input.nextLine();
        this.sleepJournal = s;
    }
    public void setJournal(String s) {
        sleepJournal = s;
    }
    public String getJournal() {
        return sleepJournal;
    }

        //maybe easier to just have String parameter instead of user input?
        //alternative
//        public SleepJournal(String journal){
//            this.sleepJournal = journal;
//        }
}


class SleepRecommendation{
    //gives recommended time to go to sleep
    private Time wakeTime;
    private Time bedTime;
    private double age;
    private int sleepGoal;

    public SleepRecommendation(Time wakeTime, Time bedTime, double age, int sleepGoal){
        this.wakeTime = wakeTime;
        this.bedTime = bedTime;
        this.age = age;
        this.sleepGoal = sleepGoal;
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
        return sleeprec.toString();
    }

    // public String calculateAgeSleepRec(){   //in progress, updated ver of above
    //     int sleepRecMins = (wakeTime.getMinutes() - 15);    //takes 15 mins to fall asleep
    //     int sleepRecHours = (wakeTime.getHour() - sleepGoal);
    //     if (sleepRecMins < 0){
    //         sleepRecMins = 60 - Math.abs(sleepRecMins);
    //         sleepRecHours =- 1;
    //     }
    //     if (sleepRecHours < 0){
    //         sleepRecHours = 12 + sleepRecHours;
    //     }
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

class SleepHistory{
    //contains history of sleep from past week
    private int averageSleepDuration;
    ArrayList<SleepNode> sleepHistory = new ArrayList<SleepNode>();

    ArrayList<SleepJournal> sleepNotes = new ArrayList<>();



    public SleepHistory(SleepNode day, SleepJournal note){
        // set null
        // make it add the day (figure out how to keep day of week in SleepNode)
        sleepHistory.add(day);
        sleepNotes.add(note);
    }

    //add data to sleep history
    public void addDay(SleepNode day){
        sleepHistory.add(day);
    }

    public void addNote(SleepJournal note){
        sleepNotes.add(note);
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
    //get the day of the week based on the date

    public SleepNode(Time bedtime, Time wake){
        bedTime = bedtime;
        wakeTime = wake;
    }
    // getters

    public Time getBedTime(){
        return bedTime;
    }

    public Time getWakeTime(){
        return wakeTime;
    }

    public void calculateDuration(Time sleepTime, Time wakeTime){
        // turn sleeptime and wake time to int, calculate duration
        // duration = 
    }

    public int getDuration(){
        return duration;
    }
}