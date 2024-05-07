import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
class Point{
	String name;
	double age;
	int sleepGoal;
	int bedHours;
	int bedMins;
	int wakeHours;
	int wakeMins;

    public Point(String name, double age, int sleepGoal, int bedHours, int bedMins, int wakeHours, int wakeMins){
		this.name = name;
		this.age = age;
		this.sleepGoal = sleepGoal;
		this.bedHours = bedHours;
		this.bedMins = bedMins;
		this.wakeHours = wakeHours;
		this.wakeMins = wakeMins;
    }
}


public class ProcessFiles{
    public static void CopyWithLineNumbers(String inFileName, String outFileName){
	try {
	    File input = new File(inFileName);
	    File output = new File(outFileName);

	    Scanner sc = new Scanner(input);
	    PrintWriter printer = new PrintWriter(output);

	    int lineNumber =0;
	    while(sc.hasNextLine()) {
		lineNumber += 1;
		String s = sc.nextLine();
		printer.write(lineNumber + ": ");
		printer.write(s + "\n");
	    }
	    printer.close();
	}
	catch(FileNotFoundException e) {
	    System.err.println("File not found.");
	    System.err.println(e);
	}
    }
    
    public static void Split(String inFileName, String outWordsFileName, String outNumbersFileName){
	try {
	    File input = new File(inFileName);
	    File outputWords = new File(outWordsFileName);
	    File outputNumbers = new File(outNumbersFileName);
	    
	    Scanner sc = new Scanner(input);
	    PrintWriter wordPrinter = new PrintWriter(outputWords);
	    PrintWriter numberPrinter = new PrintWriter(outputNumbers);

	    while(sc.hasNext()) {
		if (sc.hasNextDouble()){
		    double d = sc.nextDouble();
		    numberPrinter.write(" " +  d);
		}
		else{
		    String s = sc.next();
		    wordPrinter.write(" " + s);
		}
		
	    }
	    wordPrinter.write("\n");
	    numberPrinter.write("\n");
	    wordPrinter.close();
	    numberPrinter.close();
	}
	catch(FileNotFoundException e) {
	    System.err.println("File not found.");
	    System.err.println(e);
	}


    }

    public static Point loadPointFromFile(String fileName){
		String name = null;
		double age = 0;
		int sleepGoal = 0;
		int bedHours = 0;
		int bedMins = 0;
		int wakeHours = 0;
		int wakeMins = 0;

		try{
			Scanner s = new Scanner(new File(fileName));
			name = s.nextLine();
			age = s.nextDouble();
			sleepGoal = s.nextInt();
			bedHours = s.nextInt();
			bedMins = s.nextInt();
			wakeHours = s.nextInt();
			wakeMins = s.nextInt();
		}
		catch (Exception e){
			System.out.println("Badness in loadPointFromFile");
			System.err.println(e);
		}
		System.out.println("Returning!");
		return new Point(name, age, sleepGoal, bedHours, bedMins, wakeHours, wakeMins);
    }

    public static void savePointToFile(Point p, String fileName){
	try{
	    PrintWriter writer = new PrintWriter(fileName);
	    writer.write(p.name +  "\n");
	    writer.write(p.age + "\n");
		writer.write(p.sleepGoal + "\n");
		writer.write(p.bedHours + "\n");
		writer.write(p.bedMins + "\n");
		writer.write(p.wakeHours + "\n");
		writer.write(p.wakeMins + "");
	    writer.close();
	}
	catch (FileNotFoundException e){
	    System.out.println("Badness in savePointToFile");
	    System.err.println(e);
	    }
    }    

    public static void main(String args[]) {
    }
}
