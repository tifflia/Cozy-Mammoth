import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JFrame;

import java.util.ArrayList;
import java.util.Random;
import java.awt.Graphics2D;

public class Main{
    public static void main(String[] args){
        //testing user and time class
        Time sleepGoal = new Time(0, 30);
        Time wakeGoal = new Time(8, 30);
        User testUser = new User("John Doe", 20, sleepGoal, wakeGoal);
        System.out.println("sleep goal: " + sleepGoal);
        System.out.println("wake goal: " + wakeGoal);
    }
}

class User{
    private String name;
    private int age;
    private Time sleepGoal;
    private Time wakeGoal;

    //constructor
    public User(String userName, int userAge, Time sleepTime, Time wakeTime) {
        name = userName;
        age = userAge;
        sleepGoal = sleepTime;
        wakeGoal = wakeTime;
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
    public void setSleepGoal(Time newTime) {
        sleepGoal = newTime;
    }
    public Time getSleepGoal() {
        return sleepGoal;
    }
    public void setWakeGoal(Time newTime) {
        wakeGoal = newTime;
    }
    public Time getWakeGoal() {
        return wakeGoal;
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

class logSleep{
    private int wakeTime;
    private int sleepTime;

    public logSleep(int sleepTime, int wakeTime) {
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

class sleepRecommendation {

}

class SleepHistory{
    //contains history of sleep from past week
    private int averageSleepDuration;
    ArrayList<SleepNode> sleepHistory = new ArrayList<SleepNode>();

    public SleepHistory(SleepNode day){
    }

    //add data to sleep history
    public void addDay(SleepNode day){
        this.sleepHistory.add(day);
    }
  
    //calculate average sleep time
    public void getAverageDuration(){
        int total = 0;
        for (SleepNode day: sleepHistory){
            total += day.getDuration();
        }
        averageSleepDuration = total / sleepHistory.size();
    }

    //getter for average sleep
    public int getAverageSleepDuration(){
        return averageSleepDuration;
    }

    // delete old sleep data
    public void oldSleepData(){

    }
    // draw method
}

class SleepNode{
    //sleep data for a single day
    private Time bedTime;
    private Time wakeTime;
    private int duration;
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