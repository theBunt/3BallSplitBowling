package db;

import oracle.jdbc.pool.OracleDataSource;

import java.sql.*;
import java.util.Scanner;

/**
 * Created by Peter on 06/03/2015.
 */
public class SetupOperations {


    private Connection conn = null;
    private PreparedStatement pStmt = null;
    private Statement stmt = null;
    private ResultSet rSet;

    public SetupOperations()
    {
        conn = openDB();
    }

    public Connection openDB() {
        Scanner in = new Scanner(System.in);
        try {
            // Load the Oracle JDBC driver
            OracleDataSource ods = new OracleDataSource();

            System.out.println("Type:\t1 for College\n\t\t2 for Home");
            int val = in.nextInt();

            // Tallaght College Connection
            if (val == 1) {
                ods.setURL("jdbc:oracle:thin:@//10.10.2.7:1521/global1");
                ods.setUser("x00115070");
                ods.setPassword("db29Jun84");

                // Peter's Laptop Connection
            } else if (val == 2) {
                ods.setURL("jdbc:oracle:thin:hr/hr@localhost:1521/XE");
                ods.setUser("Peter");
                ods.setPassword("database");
            }
            conn = ods.getConnection();
            System.out.println("connected.");
        } catch (Exception e) {
            System.out.print("Unable to load driver " + e);
            System.exit(1);
        }
        return conn;
    }

    public void closeDB() {
        try {
            conn.close();
            System.out.print("Connection closed");
        } catch (SQLException e) {
            System.out.print("Could not close connection ");
            e.printStackTrace();
        }
    }

    public void dropTables() {
        System.out.println("Checking for existing tables.");

        try {
            // Get a Statement object.
            stmt = conn.createStatement();

            try {
                // Drop the Bookings table
                stmt.execute("DROP TABLE bookings");
                System.out.println("Bookings table dropped.");
                stmt.execute("DROP SEQUENCE bookingId_seq");
                System.out.println("Booking Sequence dropped");

                // Drop the Stock table.
                stmt.execute("DROP TABLE stock");
                System.out.println("Stock table dropped.");
                stmt.execute("DROP SEQUENCE stockId_seq");
                System.out.println("Stock Sequence dropped.");

                // Drop the Lane table.
                stmt.execute("DROP TABLE lanes");
                System.out.println("Lanes table dropped.");
                stmt.execute("DROP SEQUENCE laneId_seq");
                System.out.println("Lane Sequence dropped.");

                // Drop the Staff table.
                stmt.execute("DROP TABLE staff");
                System.out.println("Staff table dropped.");
                stmt.execute("DROP SEQUENCE staffId_seq");
                System.out.println("Staff Sequence dropped.");

                // Drop the Members table.
                stmt.execute("DROP TABLE members");
                System.out.println("Members table dropped.");
                stmt.execute("DROP SEQUENCE memId_seq");
                System.out.println("Member Sequence dropped.");

            } catch (SQLException ex) {
                // No need to report an error.
                // The table simply did not exist.
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void createTables() {
        createMembers();
        createStaff();
        createLanes();
        createStock();
        createBookings();
    }

    public void createMembers() {
        try
        {
            System.out.println("Inside Create Members Method");
            // Create a Table
            String create = "CREATE TABLE members " +
                    "(memId NUMBER PRIMARY KEY, lName VARCHAR(40), fName VARCHAR(40), " +
                    "gender CHAR, phone VARCHAR(40), email VARCHAR(50), address VARCHAR(50), " +
                    "town VARCHAR(50), county VARCHAR(20), numVisits NUMBER)";
            pStmt = conn.prepareStatement(create);
            pStmt.executeUpdate(create);

            // Creating a sequence
            String createseq = "create sequence memId_seq increment by 1 start with 1";
            pStmt = conn.prepareStatement(createseq);
            pStmt.executeUpdate(createseq);

            // Insert data into table
            String insertString = "INSERT INTO members(memId, lName, fName, gender, phone, email," +
                    "address, town, county, numVisits) values(memId_seq.nextVal, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pStmt = conn.prepareStatement(insertString);

            // Member 1
            pStmt.setString(1, "McGuinness");
            pStmt.setString(2, "Jo");
            pStmt.setString(3, "F");
            pStmt.setString(4, "045899304");
            pStmt.setString(5, "jmcguinness@email.com");
            pStmt.setString(6, "29 Oak Lawn");
            pStmt.setString(7, "Tullamore");
            pStmt.setString(8, "Offaly");
            pStmt.setInt(9, 5);
            pStmt.executeQuery();

            // Member 2
            pStmt.setString(1, "Cummins");
            pStmt.setString(2, "Wesley");
            pStmt.setString(3, "M");
            pStmt.setString(4, "040429020");
            pStmt.setString(5, "wcummins@email.com");
            pStmt.setString(6, "Ballin Hill");
            pStmt.setString(7, "The Curragh");
            pStmt.setString(8, "Kildare");
            pStmt.setInt(9, 2);
            pStmt.executeQuery();

            // Member 3
            pStmt.setString(1, "Moran");
            pStmt.setString(2, "Thomas");
            pStmt.setString(3, "M");
            pStmt.setString(4, "045481127");
            pStmt.setString(5, "tmoran@email.com");
            pStmt.setString(6, "3 Stone Wall");
            pStmt.setString(7, "Ardee");
            pStmt.setString(8, "Louth");
            pStmt.setInt(9, 1);
            pStmt.executeQuery();

            // Member 4
            pStmt.setString(1, "Glennon");
            pStmt.setString(2, "Eamonn");
            pStmt.setString(3, "M");
            pStmt.setString(4, "04784054");
            pStmt.setString(5, "eglennon@email.com");
            pStmt.setString(6, "38 River Place");
            pStmt.setString(7, "Clondalkin");
            pStmt.setString(8, "Dublin 22");
            pStmt.setInt(9, 7);
            pStmt.executeQuery();

            // Member 5
            pStmt.setString(1, "Brady");
            pStmt.setString(2, "Peter");
            pStmt.setString(3, "M");
            pStmt.setString(4, "0871234567");
            pStmt.setString(5, "pbrady@email.com");
            pStmt.setString(6, "Oldcourt");
            pStmt.setString(7, "Blessington");
            pStmt.setString(8, "Wicklow");
            pStmt.setInt(9, 9);
            pStmt.executeQuery();

            // Member 6
            pStmt.setString(1, "Brannigan");
            pStmt.setString(2, "Dinny");
            pStmt.setString(3, "M");
            pStmt.setString(4, "0871852865");
            pStmt.setString(5, "dbrannigan@email.com");
            pStmt.setString(6, "River Side");
            pStmt.setString(7, "Portarlington");
            pStmt.setString(8, "Laois");
            pStmt.setInt(9, 2);
            pStmt.executeQuery();

            // Member 7
            pStmt.setString(1, "McDonnell");
            pStmt.setString(2, "Maureen");
            pStmt.setString(3, "F");
            pStmt.setString(4, "0876546567");
            pStmt.setString(5, "mmcdonnell@email.com");
            pStmt.setString(6, "6 Carrig");
            pStmt.setString(7, "Gort");
            pStmt.setString(8, "Galway");
            pStmt.setInt(9, 1);
            pStmt.executeQuery();

            // Member 8
            pStmt.setString(1, "O'Sullivan");
            pStmt.setString(2, "Nora");
            pStmt.setString(3, "F");
            pStmt.setString(4, "0879856786");
            pStmt.setString(5, "nosullivan@email.com");
            pStmt.setString(6, "102 Any Rd");
            pStmt.setString(7, "Bray");
            pStmt.setString(8, "Wicklow");
            pStmt.setInt(9, 5);
            pStmt.executeQuery();

            // Member 9
            pStmt.setString(1, "Lynch");
            pStmt.setString(2, "Sean");
            pStmt.setString(3, "M");
            pStmt.setString(4, "0873956774");
            pStmt.setString(5, "slynch@email.com");
            pStmt.setString(6, "34 Sea View");
            pStmt.setString(7, "Bray");
            pStmt.setString(8, "Wicklow");
            pStmt.setInt(9, 9);
            pStmt.executeQuery();

            // Member 10
            pStmt.setString(1, "Fallon");
            pStmt.setString(2, "Majella");
            pStmt.setString(3, "F");
            pStmt.setString(4, "0871986334");
            pStmt.setString(5, "mfallon@email.com");
            pStmt.setString(6, "Skibber");
            pStmt.setString(7, "Camolin");
            pStmt.setString(8, "Wexford");
            pStmt.setInt(9, 9);
            pStmt.executeQuery();
        }
        catch (SQLException e)
        {
            System.out.print("SQL Exception " + e);
            System.exit(1);
        }
    }

    public void createStaff() {
        try
        {
            System.out.println("Inside Create Staff Method");
            // Create a Table
            String create = "CREATE TABLE staff " +
                    "(staffId NUMBER PRIMARY KEY, lname VARCHAR(40), fname VARCHAR(40), phone VARCHAR(40)," +
                    "login VARCHAR(40), password VARCHAR(40), secQuestion VARCHAR(50), secAnswer VARCHAR(40))";
            pStmt = conn.prepareStatement(create);
            pStmt.executeUpdate(create);

            // Creating a sequence
            String createseq = "create sequence staffId_seq increment by 1 start with 1";
            pStmt = conn.prepareStatement(createseq);
            pStmt.executeUpdate(createseq);

            // Insert data into table
            String insertString = "INSERT INTO staff(staffId, lname, fname, phone, login," +
                    "password, secQuestion, secAnswer) values(staffId_seq.nextVal, ?, ?, ?, ?, ?, ?, ?)";
            pStmt = conn.prepareStatement(insertString);

            // Staff 1
            pStmt.setString(1, "Byrne");
            pStmt.setString(2, "Joe");
            pStmt.setString(3, "0874569813");
            pStmt.setString(4, "user");
            pStmt.setString(5, "password");
            pStmt.setString(6, "Mothers maiden name");
            pStmt.setString(7, "Brady");
            //pStmt.setTime(6, Time.valueOf("14.30"));
            pStmt.executeQuery();

            // Staff 2
            pStmt.setString(1, "Hummins");
            pStmt.setString(2, "Lesley");
            pStmt.setString(3, "0832518754");
            pStmt.setString(4, "user");
            pStmt.setString(5, "password");
            pStmt.setString(6, "Mothers maiden name");
            pStmt.setString(7, "Brady");
            //pStmt.setTime(6, Time.valueOf("12.30"));
            pStmt.executeQuery();

            // Staff 3
            pStmt.setString(1, "Horan");
            pStmt.setString(2, "Thomas");
            pStmt.setString(3, "016584215");
            pStmt.setString(4, "user");
            pStmt.setString(5, "password");
            pStmt.setString(6, "Mothers maiden name");
            pStmt.setString(7, "Brady");
            // pStmt.setTime(6, Time.valueOf("12.50"));
            pStmt.executeQuery();

            // Staff 4
            pStmt.setString(1, "Farrell");
            pStmt.setString(2, "Harry");
            pStmt.setString(3, "019856245");
            pStmt.setString(4, "user");
            pStmt.setString(5, "password");
            pStmt.setString(6, "Mothers maiden name");
            pStmt.setString(7, "Brady");
            //pStmt.setTime(6, Time.valueOf("1.10"));
            pStmt.executeQuery();

            // Staff 5
            pStmt.setString(1, "Brady");
            pStmt.setString(2, "Peter");
            pStmt.setString(3, "0871234567");
            pStmt.setString(4, "peter");
            pStmt.setString(5, "brady");
            pStmt.setString(6, "First pets name");
            pStmt.setString(7, "Scobby");
            //pStmt.setTime(6, Time.valueOf("2.00"));
            pStmt.executeQuery();
        }
        catch (SQLException e)
        {
            System.out.print("SQL Exception " + e);
            System.exit(1);
        }
    }

    public void createLanes() {
        try
        {
            System.out.println("Inside Create Lanes Method");
            // Create a Table
            String create = "CREATE TABLE lanes " +
                    "(laneId NUMBER PRIMARY KEY NOT NULL, laneName VARCHAR(10), maxPlayers NUMBER(1))";
            pStmt = conn.prepareStatement(create);
            pStmt.executeUpdate(create);

            // Creating a sequence
            String createseq = "create sequence laneId_seq increment by 1 start with 1";
            pStmt = conn.prepareStatement(createseq);
            pStmt.executeUpdate(createseq);

            // Insert data into table
            String insertString = "INSERT INTO lanes (laneId, laneName, maxPlayers)" +
                    "VALUES (laneId_seq.nextVal, ?, ?)";
            pStmt = conn.prepareStatement(insertString);

            // Create 16 Lanes
            for (int i = 0; i < 16; i ++) {
                try {
                    String lane = "Lane " + (i + 1);
                    pStmt.setString(1, lane);
                    pStmt.setInt(2, 6);
                    pStmt.executeQuery();
                }
                catch (SQLException e) {
                    System.out.print("SQL Exception " + e);
                    System.exit(1);
                }
            }
        }
        catch (SQLException e)
        {
            System.out.print("SQL Exception " + e);
            System.exit(1);
        }
    }

    public void createStock() {
        try {
            System.out.println("Inside Create Stock Method");
            // Create a Table
            String create = "CREATE TABLE stock " +
                    "(stockId NUMBER PRIMARY KEY, shoeSize VARCHAR2(30), quantity NUMBER, details VARCHAR2(30))";
            pStmt = conn.prepareStatement(create);
            pStmt.executeUpdate(create);

            // Creating a sequence
            String createseq = "create sequence stockId_seq increment by 1 start with 1";
            pStmt = conn.prepareStatement(createseq);
            pStmt.executeUpdate(createseq);

            // Insert data into table
            String insertString = "INSERT INTO stock(stockId, shoeSize, quantity, details)" +
                    "values(stockId_seq.nextVal, ?, ?, ?)";
            pStmt = conn.prepareStatement(insertString);

            // Stock 1
            pStmt.setString(1, "Size 3");
            pStmt.setInt(2, 20);
            pStmt.setString(3, "Male");
            pStmt.executeQuery();

            // Stock 2
            pStmt.setString(1, "Size 4");
            pStmt.setInt(2, 20);
            pStmt.setString(3, "Male");
            pStmt.executeQuery();

            // Stock 3
            pStmt.setString(1, "Size 5");
            pStmt.setInt(2, 20);
            pStmt.setString(3, "Male");
            pStmt.executeQuery();

            // Stock 4
            pStmt.setString(1, "Size 6");
            pStmt.setInt(2, 20);
            pStmt.setString(3, "Male");
            pStmt.executeQuery();

            // Stock 5
            pStmt.setString(1, "Size 7");
            pStmt.setInt(2, 20);
            pStmt.setString(3, "Male");
            pStmt.executeQuery();

            // Stock 6
            pStmt.setString(1, "Size 8");
            pStmt.setInt(2, 20);
            pStmt.setString(3, "Male");
            pStmt.executeQuery();

            // Stock 7
            pStmt.setString(1, "Size 9");
            pStmt.setInt(2, 20);
            pStmt.setString(3, "Male");
            pStmt.executeQuery();

            // Stock 8
            pStmt.setString(1, "Size 10");
            pStmt.setInt(2, 20);
            pStmt.setString(3, "Male");
            pStmt.executeQuery();

            // Stock 9
            pStmt.setString(1, "Size 11");
            pStmt.setInt(2, 20);
            pStmt.setString(3, "Male");
            pStmt.executeQuery();

            // Stock 10
            pStmt.setString(1, "Size 12");
            pStmt.setInt(2, 20);
            pStmt.setString(3, "Male");
            pStmt.executeQuery();

            // Stock 11
            pStmt.setString(1, "Size 3");
            pStmt.setInt(2, 20);
            pStmt.setString(3, "Female");
            pStmt.executeQuery();

            // Stock 12
            pStmt.setString(1, "Size 4");
            pStmt.setInt(2, 20);
            pStmt.setString(3, "Female");
            pStmt.executeQuery();

            // Stock 13
            pStmt.setString(1, "Size 5");
            pStmt.setInt(2, 20);
            pStmt.setString(3, "Female");
            pStmt.executeQuery();

            // Stock 14
            pStmt.setString(1, "Size 6");
            pStmt.setInt(2, 20);
            pStmt.setString(3, "Female");
            pStmt.executeQuery();

            // Stock 15
            pStmt.setString(1, "Size 7");
            pStmt.setInt(2, 20);
            pStmt.setString(3, "Female");
            pStmt.executeQuery();

            // Stock 16
            pStmt.setString(1, "Size 8");
            pStmt.setInt(2, 20);
            pStmt.setString(3, "Female");
            pStmt.executeQuery();

            // Stock 17
            pStmt.setString(1, "Size 9");
            pStmt.setInt(2, 20);
            pStmt.setString(3, "Female");
            pStmt.executeQuery();

            // Stock 18
            pStmt.setString(1, "Size 10");
            pStmt.setInt(2, 20);
            pStmt.setString(3, "Female");
            pStmt.executeQuery();

            // Stock 19
            pStmt.setString(1, "Size 11");
            pStmt.setInt(2, 20);
            pStmt.setString(3, "Female");
            pStmt.executeQuery();

            // Stock 20
            pStmt.setString(1, "Size 12");
            pStmt.setInt(2, 20);
            pStmt.setString(3, "Female");
            pStmt.executeQuery();

        } catch (SQLException e) {
            System.out.print("SQL Exception " + e);
            System.exit(1);
        }
    }

    public void createBookings() {
        try {
            System.out.println("Inside Create Bookings Method");
            // Create a Table
            String create = "CREATE TABLE bookings " +
                    "(bookingId NUMBER(3) PRIMARY KEY NOT NULL, memId NUMBER(5), laneId NUMBER(3)," +
                    "fromDateTime TIMESTAMP, toDateTime TIMESTAMP, FOREIGN KEY (memId) REFERENCES members (memId)," +
                    "FOREIGN KEY (laneId) REFERENCES lanes (laneId))";
            pStmt = conn.prepareStatement(create);
            pStmt.executeUpdate(create);

            // Creating a sequence
            String createseq = "create sequence bookingId_seq increment by 1 start with 1";
            pStmt = conn.prepareStatement(createseq);
            pStmt.executeUpdate(createseq);

            // Insert data into table
            String insertString = "INSERT INTO bookings (bookingId, memId, laneId, fromDateTime, toDateTime)" +
                    "VALUES (bookingId_seq.nextVal, ?, ?, ?, ?)";
            pStmt = conn.prepareStatement(insertString);

            // Booking 1
            pStmt.setInt(1, 5);
            pStmt.setInt(2, 1);
            pStmt.setString(3, "20-MAR-15 10:00:00");
            pStmt.setString(4, "20-MAR-15 12:00:00");
            pStmt.executeQuery();

            // Booking 2
            pStmt.setInt(1, 4);
            pStmt.setInt(2, 1);
            pStmt.setString(3, "20-MAR-15 13:00:00");
            pStmt.setString(4, "20-MAR-15 15:00:00");
            pStmt.executeQuery();

            // Booking 3
            pStmt.setInt(1, 2);
            pStmt.setInt(2, 3);
            pStmt.setString(3, "20-MAR-15 10:00:00");
            pStmt.setString(4, "20-MAR-15 12:00:00");
            pStmt.executeQuery();

            // Booking 4
            pStmt.setInt(1, 5);
            pStmt.setInt(2, 4);
            pStmt.setString(3, "20-MAR-15 18:00:00");
            pStmt.setString(4, "20-MAR-15 20:00:00");
            pStmt.executeQuery();

            // Booking 5
            pStmt.setInt(1, 3);
            pStmt.setInt(2, 3);
            pStmt.setString(3, "20-MAR-15 22:00:00");
            pStmt.setString(4, "20-MAR-15 23:00:00");
            pStmt.executeQuery();
        }
        catch (SQLException e)
        {
            System.out.print("SQL Exception " + e);
            System.exit(1);
        }
    }

    public void queryTables() {
        queryMembers();
        queryStaff();
        queryLanes();
        queryStock();
        queryBookings();
    }

    public void queryMembers() {
        try {
            String queryString = "SELECT * FROM members";
            pStmt = conn.prepareStatement(queryString);
            rSet = pStmt.executeQuery();
            System.out.println("Members Table");
            while (rSet.next()){
                System.out.println(rSet.getInt(1) + " " + rSet.getString(2) + " " + rSet.getString(3)
                        + " " + rSet.getString(4) + " " + rSet.getString(5) + " " + rSet.getString(6)
                        + " " + rSet.getString(7) + " " + rSet.getString(8) + " " + rSet.getString(9)
                        + " " + rSet.getInt(10));
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    public void queryStaff() {
        try {
            String queryString = "SELECT * FROM staff";
            pStmt = conn.prepareStatement(queryString);
            rSet = pStmt.executeQuery();
            System.out.println("Staff Table");
            while (rSet.next()){
                System.out.println(rSet.getInt(1) + " " + rSet.getString(2) + " " + rSet.getString(3)
                        + " " + rSet.getString(4) + " " + rSet.getString(5) + " " + rSet.getString(6)
                        + " " + rSet.getString(7) + " " + rSet.getString(8));
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    public void queryLanes() {
        try {
            String queryString = "SELECT * FROM lanes";
            pStmt = conn.prepareStatement(queryString);
            rSet = pStmt.executeQuery();
            System.out.println("Lanes Table");
            while (rSet.next()){
                System.out.println(rSet.getInt(1) + " " + rSet.getString(2) + " " + rSet.getInt(3));
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    public void queryStock() {
        try {
            String queryString = "SELECT * FROM stock";
            pStmt = conn.prepareStatement(queryString);
            rSet = pStmt.executeQuery();
            System.out.println("Stock Table");
            while (rSet.next()){
                System.out.println(rSet.getInt(1) + " " + rSet.getString(2) + " " + rSet.getInt(3)
                        + " " + rSet.getString(4));
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public void queryBookings() {
        try {
            String queryString = "SELECT * FROM bookings";
            pStmt = conn.prepareStatement(queryString);
            rSet = pStmt.executeQuery();
            System.out.println("Bookings Table");
            while (rSet.next()){
                System.out.println(rSet.getInt(1) + " " + rSet.getInt(2) + " " + rSet.getInt(3)
                        + " " + rSet.getString(4) + " " + rSet.getString(5));
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public static void main(String args[]) {
        SetupOperations setup = new SetupOperations();
        setup.dropTables();
        setup.createTables();
        setup.queryTables();
        setup.closeDB();
    }
}