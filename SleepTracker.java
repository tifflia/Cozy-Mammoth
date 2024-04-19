import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.util.Random;
import java.awt.Graphics2D;

public class SleepTracker{

}
class Time {
    public int militaryTime;
}
class User{

}

class logSleep {

}

class SleepRecommendation{

}

class SleepHistory{
    private int averageSleepDuration;

    public SleepHistory(){
    }
    // contains history of sleep from past week
    /* have a wrapper - something that contains
     * sleep data per day that week*/
    // calculate average sleep time method
    // delete old sleep data method
    // draw method
}

class SleepNode{    // generic? extends?
    // sleep data for a single day
    SleepObject s;
    SleepNode prev;
    SleepNode next;
    public SleepNode(SleepObject s){
        this.s = s;
    }
}

class SleepObject{
    private Time bedtime;
    private Time wakeTime;
    private int duration;
    public SleepObject(Time bedtime, Time wake, int duration){
        this.bedtime = bedtime;
        wakeTime = wake;
        this.duration = duration;
    }
}