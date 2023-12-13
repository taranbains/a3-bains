import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Scanner;
import java.io.*;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/** 
 * @author bains
 * 
 * TicketGenerator() Class
 * 
 * Connects a MySQL database and allows the user to choose start and end date, as well as number of 
 * tickets to generate and randomly generates tickets and write them all to database. 
 *
 */
public class TicketGenerator {
	
	/**
	 * generateRandomNumber() method generates a random number between two values
	 * @param min: int
	 * @param max: int
	 * @return random number, int
	 */
	public static int generateRandomNumber(int min, int max) {
		Random random = new Random();
	    return random.nextInt(max - min) + min;
	}
	
	/**
	 * generateRandomDate() method generates a random date between two dates. 
	 * @param startDate: LocalDate
	 * @param endDate: LocalDate 
	 * @return LocalDate between the two dates 
	 */
	public static LocalDate generateRandomDate(LocalDate startDate, LocalDate endDate) {
		long startEpochDay = startDate.toEpochDay();
        long endEpochDay = endDate.toEpochDay();		// convert to EpochDay so we can take advantage of the random date generation  

        long randomEpochDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay + 1);

        return LocalDate.ofEpochDay(randomEpochDay);        
	}
	
	/** 
	 * calculatePriority() method takes the impact and urgency and returns the priority 
	 * @param impact: int
	 * @param urgency: int
	 * @return priority, int
	 */
	public static int calculatePriority(int impact, int urgency) {
        int[][] priority = {{1, 2, 3}, 					// priority matrix 
        					{2, 3, 4}, 
        					{3, 4, 5}};
		return priority[impact-1][urgency-1];
    }
	
	
	/**
	 * main() function holds the connection for MySQL database and writes the tickets to the database. 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception{
		// ------------------- Connect to SQL database --------------------//
		
		String url = "jdbc:mysql://127.0.0.1:3306/ServiceTickets"; 				// table details
        String username = "name"; 											// MySQL credentials
        String password = "password";
        
        Class.forName("com.mysql.cj.jdbc.Driver"); 								// Driver name
        
        Connection con = DriverManager.getConnection(url, username, password);
        
        System.out.println("Connection to Service Tickets Database Successfully.\n");        
        
		// ---------------------- User Input -----------------------// 
		Scanner input = new Scanner(System.in);									// create an object of Scanner to read user input
        System.out.print("Enter the number of tickets to generate: "); 
        int numTickets = input.nextInt();
		
        String regex = "[0-9][0-9]/[0-9][0-9]/[0-9][0-9][0-9][0-9]";			// string format to ensure user input for date matches 
        
        System.out.print("Enter the start date (DD/MM/YYYY): "); 				// Start Date 
        String startD = input.next(); 
		
        while(!startD.matches(regex)) {											
        	System.out.print("Please enter start date in the provided format (DD/MM/YYYY): "); 
            startD = input.next();
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        LocalDate serviceStartDate = LocalDate.parse(startD,formatter);
        
        System.out.print("Enter the end date (DD/MM/YYYY): "); 					// End Date 
        String endD = input.next(); 
		
        while(!endD.matches(regex)) {										
        	System.out.print("Please enter end date in the provided format (DD/MM/YYYY): "); 
            endD = input.next();
        }
        LocalDate serviceEndDate = LocalDate.parse(endD,formatter);
        
        input.close();															// close scanner object 
        
        // ------------------ Generate and Write Tickets -------------------// 
        String[] eventActivity = {"Design", "Construction", "Test", "Password Reset"};				// arrays to hold different event options 
        String[] eventOrigin = {"Joe S", "George E", "Rona E", "Bill B", "Achmed M"};
        String[] eventStatus = {"Open", "On Hold", "In Process", "Deployed", "Deployed Failed"};
        String[] eventClass = {"Change", "Incident", "Problem", "SR (Service Request)"};
        
        int ea, eo, es, ec; 													// value to access each event option in string array
        int csNumber;															// cs id number 
        int urgency;
        int impact;
        int priority;        
        Period duration;
        int days; 																// Convert the duration to days
        LocalDate startDate;
        LocalDate endDate; 
        LocalDateTime now = LocalDateTime.now(); 								// use "now" for UpdateDateTime attribute 
        
        String insertQuery;
        Statement st = con.createStatement();          
           
        for(int i = 0; i < numTickets; i++) {									// loop to randomly generate the tickets 
        	csNumber = generateRandomNumber(1, 10000);
        	
        	ea = generateRandomNumber(0, 3+1);									// random number for the event tables 
        	eo = generateRandomNumber(0, 4+1);
        	es = generateRandomNumber(0, 4+1);
        	ec = generateRandomNumber(0, 3+1);
        	
        	urgency = generateRandomNumber(1, 3+1);
        	impact = generateRandomNumber(1, 3+1);
        	priority = calculatePriority(impact, urgency);
        	
        	startDate = generateRandomDate(serviceStartDate, serviceEndDate);	// random date variables 
        	endDate = generateRandomDate(startDate, serviceEndDate);
        	duration = startDate.until(endDate); 
        	days = (int)ChronoUnit.DAYS.between(startDate, endDate);	
        	
        	// update Event Activity table
            insertQuery = "INSERT INTO EventActivity(ActivityName) VALUES ('" + eventActivity[ea] + "')";
            st.executeUpdate(insertQuery);
            
            // update Event Origin table
            insertQuery = "INSERT INTO EventOrigin(ActivityName) VALUES ('" + eventOrigin[eo] + "')";
            st.executeUpdate(insertQuery);
            
            // update Event Status table
            insertQuery = "INSERT INTO EventStatus(Status) VALUES ('" + eventStatus[es] + "')";
            st.executeUpdate(insertQuery);
            
            // update Event Class table
            insertQuery = "INSERT INTO EventClass(Class) VALUES ('" + eventClass[ec] + "')";
            st.executeUpdate(insertQuery);
            
            // update Event Log table 
            insertQuery = "INSERT INTO EventLog("
            		+ "CaseID, Activity, Urgency, Impact, Priority, StartDate, EndDate, TicketStatus, UpdateDateTime, Duration, Origin, Class)"
            		+ " VALUES"
            		+ "('CS_" + csNumber + "', "
            		+ "'" + eventActivity[ea] + "', "
            		+ "'" + urgency + "', "
            		+ "'" + impact + "', "
            		+ "'" + priority + "', "
            		+ "'" + startDate + "', "
            		+ "'" + endDate + "', "
            		+ "'" + eventStatus[es] + "', "
               		+ "'" + now + "', "
               		+ "'" + days + "', "
            		+ "'" + eventOrigin[eo] + "', "
               		+ "'" + eventClass[ec] + "')";                     
            st.executeUpdate(insertQuery);
        }	
        
        System.out.println(numTickets + " random tickets added to database.");
        
        st.close(); 															// close statement
        con.close(); 															// close connection
        System.out.println("\nConnection to Service Tickets Database Closed.");
        
	}

}
