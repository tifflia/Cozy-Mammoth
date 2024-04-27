import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Graphics2D;
import java.util.Date;

public class Main{
    public static void main(String[] args){
        //testing user and time class
        Time bedTime = new Time(0, 30);
        Time wakeTime = new Time(8, 30);
        User testUser = new User("John Doe", 20, 8, bedTime, wakeTime);
        System.out.println("bedtime goal: " + testUser.getBedTime());
        System.out.println("wake up goal: " + testUser.getWakeTime());
        SleepRecommendation testRec = new SleepRecommendation(testUser.getWakeTime(), testUser.getBedTime(), testUser.getAge(), testUser.getSleepGoal());
        System.out.println("Based on your wake up goal, you should sleep at " + testRec.calculateSleepRec());
    }
}

class User{
    private String name;
    private int age;
    private int sleepGoal;
    private Time bedTime;
    private Time wakeTime;

    //constructor
    public User(String userName, int userAge, int sleep, Time bed, Time wake) {
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
    public void setAge(int newAge) {
        age = newAge;
    }
    public int getAge() {
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

class SleepRecommendation{
    //gives recommended time to go to sleep
    private Time wakeTime;
    private Time bedTime;
    private int age;
    private int sleepGoal;

    public SleepRecommendation(Time wakeTime, Time bedTime, int age, int sleepGoal){
        this.wakeTime = wakeTime;
        this.bedTime = bedTime;
        this.age = age;
        this.sleepGoal = sleepGoal;
    }
    //will implement more complex algorithms later (considering rem cycle, etc)
    public String calculateSleepRec(){
        int sleepRecMins = (wakeTime.getMinutes() - 15);    // takes 15 mins to fall asleep
        int sleepRecHours = (wakeTime.getHour() - sleepGoal);
        Time sleeprec = new Time(sleepRecHours, sleepRecMins);
        return sleeprec.toString();
    }
}

class SleepHistory{
    //contains history of sleep from past week
    private int averageSleepDuration;
    ArrayList<SleepNode> sleepHistory = new ArrayList<SleepNode>();

    public SleepHistory(SleepNode day){
        // set null
    }

    //add data to sleep history
    public void addDay(SleepNode day){
        sleepHistory.add(day);
    }
  
    //calculate average sleep time
    public void getAverageDuration(){
        int total = 0;
        for (SleepNode day : sleepHistory){
            total += day.getDuration();
        }
        averageSleepDuration = total / sleepHistory.size();
    }

    //getter for average sleep
    public int getAverageSleepDuration(){
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
        // turn sleeptime and wake time to int, calcualte duration
        // duration = 
    }

    public int getDuration(){
        return duration;
    }
}