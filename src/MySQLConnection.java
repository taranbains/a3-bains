import java.io.*;
import java.sql.*;
 
/**
 * @author bains
 * 
 * MySQLConnection() Class
 * 
 * MySQL connection connects to the MySQL Student Registration database that has already been created, 
 * initialized and populated and runs queries to get the current values in each table of the database
 * and prints them to the screen. 
 *
 */
class MySQLConnection {
	
	/** 
	 * PrintQuery() method prints a specific to the query in an easy to read format. 
	 * @param rs: ResultSet object (result of a MySql query)
	 * @param columns: int
	 * @throws SQLException
	 */
	public static void PrintQuery(ResultSet rs, int columns) throws SQLException{
	        while (rs.next()) {													// ensures we read all the rows 
	        	System.out.print("  ");
	        	for(int i = 1; i <= columns; i++) {
	        		System.out.print(rs.getString(i) + " 		");
	        	}
	        	System.out.println();
	        }	
	}
	
    /**
     * main() function holds the connection object to the database and contains the queries to get
     * tables. 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        String url = "jdbc:mysql://127.0.0.1:3306/StudentRegistration"; 		// table details
        String username = "name"; 											// MySQL credentials that has privileges to access database  
        String password = "password";
        
        Class.forName("com.mysql.cj.jdbc.Driver"); 								
        
        Connection con = DriverManager.getConnection(url, username, password);	// connection object to mqsyl database 
        
        System.out.println("Connection to Database Successfully.\n");
        Statement st = con.createStatement();									// statement object for the current database connection

        /* ----- QUERY 1 -----*/
        String query = "select * from student"; 								// select all the Students 							

        ResultSet rs = st.executeQuery(query); 								       
        
        System.out.println("Student Table");
        System.out.println("StudentID	|	FirstName |	LastName |	Location"); 
        
        PrintQuery(rs, 4);         
       
        
        /* ----- QUERY 2 -----*/   												// selects all the Courses 
        query = "select * from course";										
        rs = st.executeQuery(query);
        
        System.out.println("\nCourse Table");
        System.out.println("CourseID	| 	CourseName		| 	Course Title");
        PrintQuery(rs, 3);         

        
        /* ----- QUERY 3 -----*/
        query = "select * from registration";									// selects all the Registrations 					
        rs = st.executeQuery(query);
        
        System.out.println("\nRegistration Table");
        System.out.println("RegistrationID	| CourseID	| 	StudentID");
        PrintQuery(rs, 3);         
               
        st.close(); 															// close statement
        con.close(); 															// close connection
        System.out.println("\nConnection Closed.");
    }
}
