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

class sleepRecommendation{

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
    // sleep recommendations method??
}

class SleepNode{
    // sleep data for a single day
    private Time sleepTime;
    private Time wakeTime;
    private int duration;
    public SleepNode(Time sleep, Time wake, int duration){
        sleepTime = sleep;
        wakeTime = wake;
        this.duration = duration;
    }
}