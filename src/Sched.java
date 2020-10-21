//Scheduling Algorithms - calculates average wait time and average turnaround time

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Comparator;

public class Sched {

	public static void main(String[] args) {
		id();

		// P/p stands for process
		P[] pArray = new P[50];

		// creates arrayList to store array containing quadruplet of data
		ArrayList<P> data = new ArrayList<P>();

		int lines = 0;
		
		String fName ="cpu_data.text";

		try {
			Scanner file = new Scanner(new File(fName));
	
			// Reads in each variable by looking at next character/int without paying
			// attention to amount of spaces
			while (file.hasNext()) {
				String process = file.next();
				int duration = Integer.parseInt(file.next());
				int priority = Integer.parseInt(file.next());
				double arrival = Double.parseDouble(file.next());
	
				lines++;
	
				// Creates new array item and adds to list
				pArray[lines] = (new P(process, duration, priority, arrival));
	
				data.add(pArray[lines]);
			}
			
			file.close();
		}
		catch (FileNotFoundException ex) {
			System.err.println("Error: The file was not found. The program will now end.");
			System.exit(-1);
		}

		System.out.printf("Read %d lines from file '%s'. \n", lines, fName);

		////////////////////////////////////

		String label;
		double awt;
		double att;

		// sort based on first come first serve vs shortest job first vs priority
		data.sort(Comparator.comparing(P::getProcess));
		//data.sort(Comparator.comparing(P::getArrival));
		label = "fcfs";
		awt = AWT(data);
		att = ATT(data);
		display_result(label, awt, att);

		data.sort(Comparator.comparing(P::getDuration));
		label = "sjf";
		awt = AWT(data);
		att = ATT(data);
		display_result(label, awt, att);

		data.sort(Comparator.comparing(P::getPriority));
		label = "pri";
		awt = AWT(data);
		att = ATT(data);
		display_result(label, awt, att);

		id();		
	}

	/////////////////////////////////////////

	public static void id() {
		System.out.println("William Dunn: Scheduling Algo assignment");
	}

	/////////////////////////////////////////

	private static void display_result(String label, double awt, double att) {
		// label - pri/fcfs/sjf only
		// awt - average waiting time
		// att - average turnaround time

		label = label.toUpperCase();
		boolean label_ok = label.equals("PRI") || label.equals("FCFS") || label.equals("SJF");

		if (label_ok) {
			System.out.println(label);
			System.out.println("========================");
			System.out.printf("Avg Waiting Time (%s): %6.2f\n", label, awt);
			System.out.printf("Avg TurnArd Time (%s): %6.2f\n\n", label, att);
		} else
			System.err.printf("Unrecognized label '%s' (valid: PRI, FCFS, SJF)\n", label);
	}

	/////////////////////////////////////////

	public static double AWT(ArrayList<P> data) {
		int wait = 0;
		int arrival = 0;
		int sum = 0;
		int numLines = 0;
		double averageWait;

		for (int i = 0; i < data.size() - 1; i++) {
			wait = data.get(i).getDuration() + arrival;
			arrival = wait;
			sum += wait;
			numLines++;
		}
		averageWait = sum / numLines;
		return averageWait;
	}

	/////////////////////////////////////////

	// computes average turnaround time
	public static double ATT(ArrayList<P> data) {
		int turnaround = 0;
		int arrival = 0;
		int sum = 0;
		int numLines = 0;
		double averageTurnaround;

		for (int i = 0; i < data.size(); i++) {
			turnaround = data.get(i).getDuration() + arrival;
			arrival = turnaround;
			sum += turnaround;

			numLines++;
		}
		averageTurnaround = sum / numLines;
		return averageTurnaround;
	}

}

//////////////////////////////////////////////////////////////////////////////////////////////

// class created to hold the four data variables
class P {
	private String process;
	private int duration;
	private int priority;
	private double arrival;

	public P(String process, int duration, int priority, double arrival) {
		this.process = process;
		this.duration = duration;
		this.priority = priority;
		this.arrival = arrival;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public String getProcess() {
		return process;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getDuration() {
		return duration;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getPriority() {
		return priority;
	}

	public void setArrival(double arrival) {
		this.arrival = arrival;
	}

	public double getArrival() {
		return arrival;
	}
}
