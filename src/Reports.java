import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Reports {
    private static final String dbClassName = "com.mysql.cj.jdbc.Driver";
    private static final String CONNECTION = "jdbc:mysql://localhost:3306/airbnb";

    static final Pattern NOUN_PHRASE = Pattern.compile("(?:(?:the|a|an|this|that|these|those|my|your|his|her|our|their|a few|a little|much|many|a lot of|most|some|any|enough|all|both|half|either|neither|each|every|other|another|such|what|rather|quite) )*(\\w+)", Pattern.CASE_INSENSITIVE);

    public static void main(String[] args) throws ClassNotFoundException{
        Scanner inputInt = new Scanner(System.in);


        Class.forName(dbClassName);
        while(true){
            System.out.println("Choose an Option:" +
                    "\n1. Bookings Per Area" +
                    "\n2. Listings Per Area" +
                    "\n3. Rank Hosts" +
                    "\n4. Commerical Hosts" +
                    "\n5. Cancellations" +
                    "\n6. Rank Bookings" +
                    "\n7. Common Noun Phrases" +
                    "\n8. Exit");
            int choice = inputInt.nextInt();
            if (choice == 1)
                runReportsBookingInAreas();
            else if (choice == 2)
                runReportsListingInAreas();
            else if (choice == 3)
                runReportsRankingHosts();
            else if (choice == 4)
                runReportsCommercialHosts();
            else if (choice == 5)
                runReportsCancellation();
            else if (choice == 6)
                runReportsRankBookings();
            else if (choice == 7)
                runReportsNoun();
            else if (choice == 8)
                break;
            else
                System.out.println("Invalid Choice");
        }

    }
    public static void runReportsBookingInAreas() throws ClassNotFoundException{//done
        Scanner inputString = new Scanner(System.in);
        Scanner inputInt = new Scanner(System.in);
        String city = "";
        String groupBy = "";
        String datesToFilter = "";
        while(true){
            System.out.println("Enter " +
                    "\n1. For Reports About Postal Codes Within A City." +
                    "\n2. For Reports About Cities." +
                    "\n3. Exit");

            int choice = inputInt.nextInt();

            if (choice == 1) {
                System.out.println("Enter City.");
                datesToFilter = "Where city = '" + inputString.nextLine() + "' AND";
                groupBy = "postalCode";
                break;
            } else if (choice == 2) {
                datesToFilter = "Where ";
                groupBy = "city";
                break;
            } else if (choice == 3) {
                return;
            }
            System.out.println("Invalid Choice");
        }

        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        while(true){
            System.out.println("Enter the starting year for your Report.");
            int startYear = inputInt.nextInt();
            System.out.println("Enter the starting month for your Report");
            int startMonth = inputInt.nextInt();
            System.out.println("Enter the starting day for your Report");
            int startDay = inputInt.nextInt();

            System.out.println("Enter the ending year for your Report");
            int endYear = inputInt.nextInt();
            System.out.println("Enter the ending month for your Report");
            int endMonth = inputInt.nextInt();
            System.out.println("Enter the ending day for your Report");
            int endDay = inputInt.nextInt();
            startCalendar.clear();
            startCalendar.set(startYear, startMonth, startDay);

            endCalendar.clear();
            endCalendar.set(endYear, endMonth, endDay);
            if(startCalendar.compareTo(endCalendar) > 0){
                System.out.println("Invalid range of Date");
            }
            else{
                datesToFilter = datesToFilter.concat(" date Between '" + startYear +"-"+startMonth+"-"+startDay + "'"
                        + " And '" + endYear + "-" + endMonth + "-" + endDay + "'");
                break;
            }
        }

        String runReport = "Select " + groupBy + ", count(listings.listingId) As bookings " +
                "From listings join booked on listings.listingId = booked.listingId " +
                datesToFilter +
                " group by " + groupBy +
                " order by count(listings.listingId) DESC";
        try {
            Connection conn = DriverManager.getConnection(CONNECTION, "root", "");
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(runReport);
            int counter = 0;
            while (rs.next()){
                System.out.println(rs.getString(groupBy) + ": " + rs.getInt("bookings"));
                counter++;
            }
            if(counter == 0)
                System.out.println("No Booking Made With Your Filters.");
            rs.close();
            stmt.close();
            conn.close();
        }catch (SQLException e){
            System.out.println("Database Error.");
        }

    }
    public static void runReportsListingInAreas() throws ClassNotFoundException{//done
        Scanner inputString = new Scanner(System.in);
        Scanner inputInt = new Scanner(System.in);
        String groupBy = "";
        int choice;
        while(true){
            System.out.println("Enter " +
                    "\n1. For Reports About Countries." +
                    "\n2. For Reports About Countries and Cities." +
                    "\n3. For Reports About Countries, Cities and Postal Codes." +
                    "\n4. Exit");

            choice = inputInt.nextInt();

            if (choice == 1 || choice == 2 || choice == 3) {
                groupBy = groupBy.concat(" country");
                System.out.print("Country");
                if (choice == 2 || choice == 3) {
                    groupBy = groupBy.concat(", city");
                    System.out.print(", City");
                    if (choice == 3) {
                        groupBy = groupBy.concat(", postalCode");
                        System.out.print(", Postal Code");
                    }
                }
                System.out.println(": Listings");
                break;
            }
            if(choice == 4)
                return;
            System.out.println("Invalid Choice");
        }

        String runReport = "Select" + groupBy + ", count(listingId) As numberOfListings " +
                "From listings" +
                " group by" + groupBy +
                " order by count(listingId) DESC";
        try {
            Connection conn = DriverManager.getConnection(CONNECTION, "root", "");
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(runReport);
            int counter = 0;
            while (rs.next()){
                System.out.print(rs.getString("country"));
                if (choice == 2 || choice == 3) {
                    System.out.print(", " + rs.getString("city"));
                    if (choice == 3) {
                        System.out.print(", " + rs.getString("postalCode"));
                    }
                }
                counter++;
                System.out.println(": " + rs.getInt("numberOfListings"));
            }
            if(counter == 0)
                System.out.println("No Listings With Your Filters.");


            rs.close();
            stmt.close();
            conn.close();
        }catch (SQLException e){
            System.out.println("Database Error.");
        }

    }
    public static void runReportsRankingHosts() throws ClassNotFoundException{// Not done
        Scanner inputInt = new Scanner(System.in);
        String groupBy = "";
        int choice;
        while(true){
            System.out.println("Enter " +
                    "\n1. Report by Countries." +
                    "\n2. Report by Cities." +
                    "\n3. Exit");

            choice = inputInt.nextInt();

            if (choice == 1) {
                groupBy = "country";
                System.out.println("Country, Username, NumberOfListings");
                break;
            }
            else if(choice == 2){
                groupBy = "city";
                System.out.println("City, Username, NumberOfListings");
                break;
            }
            else if(choice == 3)
                return;
            System.out.println("Invalid Choice");
        }

        String runReport = "Select " + groupBy + ", ownerUName, count(listingId) as numberOfListings " +
                "From listings " +
                " group by " + groupBy + ", ownerUName" +
                " order by " + groupBy + " asc, numberOfListings desc";

        try {
            Connection conn = DriverManager.getConnection(CONNECTION, "root", "");
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(runReport);
            int counter = 0;
            String currentC = "";
            while (rs.next()){
                if(!rs.getString(groupBy).equals(currentC)){
                    System.out.println(rs.getString(groupBy));
                    currentC = rs.getString(groupBy);
                }
                System.out.println("\t" + rs.getString("ownerUName") + ", " +
                        rs.getString("numberOfListings"));
                counter++;
            }
            if(counter == 0)
                System.out.println("No Listings.");


            rs.close();
            stmt.close();
            conn.close();
        }catch (SQLException e){
            System.out.println("Database Error.");
        }

    }

    public static void runReportsCommercialHosts() throws ClassNotFoundException{//Done
        Scanner inputString = new Scanner(System.in);
        Scanner inputInt = new Scanner(System.in);
        String groupBy = "";
        int choice;
        while(true){
            System.out.println("Enter " +
                    "\n1. Rank by Countries." +
                    "\n2. Rank by Cities." +
                    "\n3. Exit");

            choice = inputInt.nextInt();

            if (choice == 1) {
                groupBy = "country";
                break;
                }
            else if(choice == 2){
                groupBy = "city";
                break;
            }
            else if(choice == 3)
                return;
            System.out.println("Invalid Choice");
        }

        String runReport = "Select ownerUName, listings." + groupBy + ", count(ownerUName) as numberOfListings, listingPerC " +
                "From listings join (Select " + groupBy + ", count(listingId) as listingPerC " +
                                    "From listings group by " + groupBy + ") as listingsPer " +
                                    "on listings." + groupBy + " = listingsPer." + groupBy + " " +
                " group by ownerUName, " + groupBy +
                " having listingPerC < count(ownerUName)*10" +
                " order by " + groupBy + " ASC, numberOfListings DESC";

        try {
            Connection conn = DriverManager.getConnection(CONNECTION, "root", "");
            Statement stmt = conn.createStatement();
            String currentC = "";
            ResultSet rs = stmt.executeQuery(runReport);
            int counter = 0;
            while (rs.next()){
                if(!rs.getString(groupBy).equals(currentC)){
                    System.out.println(rs.getString(groupBy));
                    currentC = rs.getString(groupBy);
                }
                counter++;
                System.out.println("\t" + rs.getString("ownerUName"));
            }
                if(counter == 0){
                    System.out.println("No Commercial Hosts.");
                }

            rs.close();
            stmt.close();
            conn.close();
        }catch (SQLException e){
            System.out.println("Database Error.");
        }

    }
    public static void runReportsRankBookings() throws ClassNotFoundException{//Done
        Scanner inputString = new Scanner(System.in);
        Scanner inputInt = new Scanner(System.in);
        String datesToFilter = "";
        String groupBy = "";
        String groupByComma = " ";
        String groupByASC = "";
        int choice;
        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        while(true){
            System.out.println("Enter the starting year for your Report.");
            int startYear = inputInt.nextInt();
            System.out.println("Enter the starting month for your Report");
            int startMonth = inputInt.nextInt();
            System.out.println("Enter the starting day for your Report");
            int startDay = inputInt.nextInt();

            System.out.println("Enter the ending year for your Report");
            int endYear = inputInt.nextInt();
            System.out.println("Enter the ending month for your Report");
            int endMonth = inputInt.nextInt();
            System.out.println("Enter the ending day for your Report");
            int endDay = inputInt.nextInt();
            startCalendar.clear();
            startCalendar.set(startYear, startMonth, startDay);

            endCalendar.clear();
            endCalendar.set(endYear, endMonth, endDay);
            if(startCalendar.compareTo(endCalendar) > 0){
                System.out.println("Invalid range of Date");
            }
            else{
                datesToFilter = datesToFilter.concat(" date Between '" + startYear +"-"+startMonth+"-"+startDay + "'"
                        + " And '" + endYear + "-" + endMonth + "-" + endDay + "'");
                break;
            }
        }
        while(true){
            System.out.println("Enter " +
                    "\n1. Rank by Cities." +
                    "\n2. Do Not Rank by Cities." +
                    "\n3. Exit");

            choice = inputInt.nextInt();

            if (choice == 1) {
                groupBy = "city";
                groupByASC = " city ASC,";
                groupByComma = " city, ";
                System.out.println("City, Username, NumberOfListings");
                break;
            }
            else if(choice == 2){
                System.out.println("Username, NumberOfListings");
                break;
            }
            else if(choice == 3)
                return;
            System.out.println("Invalid Choice");
        }



        String runReport = "Select" + groupByComma + "bookedBy, count(bookedBy) as numberOfBookings " +
                "From booked join listings on listings.listingId = booked.listingId" +
                " Where" + datesToFilter +
                " Group by" + groupByComma + "bookedBy " +
                " Order by" + groupByASC + " numberOfBookings desc;";
        try {
            Connection conn = DriverManager.getConnection(CONNECTION, "root", "");
            Statement stmt = conn.createStatement();
            String currentC = "";
            ResultSet rs = stmt.executeQuery(runReport);
            int counter = 0;
            while (rs.next()){
                if(!groupBy.equals("")){
                    if (!rs.getString(groupBy).equals(currentC)) {
                        System.out.println(rs.getString(groupBy));
                        currentC = rs.getString(groupBy);
                    }
                }
                counter++;
                System.out.println("\t" + rs.getString("bookedBy") + ", " +
                        rs.getInt("numberOfBookings"));
            }
            if(counter == 0){
                System.out.println("No Bookings In Date Range.");
            }

            rs.close();
            stmt.close();
            conn.close();
        }catch (SQLException e){
            System.out.println("Database Error.");
        }

    }

    public static void runReportsCancellation() throws ClassNotFoundException{//Done

        Calendar calendar = Calendar.getInstance();
        String todayDate = "'" + calendar.get(Calendar.YEAR) + "-"
                + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DATE) + "'";
        String yearAfterDate = "'" + (calendar.get(Calendar.YEAR) + 1) + "-" +
                calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DATE) +"'";
        String runReportTenant = "Select bookedBy, count(bookedBy) As cancellations " +
                "From booked " +
                "Where date Between " + todayDate + " AND " + yearAfterDate + " AND reservationUsed = '2' " +
                "group by bookedBy " +
        "HAVING count(bookedBy)=(" +
                "SELECT MAX(cancellations) " +
        "FROM (SELECT bookedBy, count(bookedBy) as cancellations " +
                "FROM booked " +
                "Where date Between " + todayDate + " AND " + yearAfterDate + " AND reservationUsed = '2' " +
                "GROUP BY bookedBy) as newSet)";

        String runReportHost =  "Select ownerUName, count(ownerUName) As cancellations " +
                "From booked join listings on listings.listingId = booked.listingId " +
                "Where date Between " + todayDate + " AND " + yearAfterDate + " AND reservationUsed = '3' " +
                "group by ownerUName " +
                "HAVING count(ownerUName)=( " +
                "SELECT MAX(cancellations) " +
                "FROM (" +
                "SELECT ownerUName, count(ownerUName) as cancellations " +
                "From booked join listings on listings.listingId = booked.listingId " +
                "Where date Between " + todayDate + " AND " + yearAfterDate + " AND reservationUsed = '3' " +
                "GROUP BY ownerUName) as newSet)";
        try {
            Connection conn = DriverManager.getConnection(CONNECTION, "root", "");
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(runReportTenant);
            System.out.println("Tenant/s With Most Cancellations: " );
            int counter = 0;
            while(rs.next()){
                System.out.println(rs.getString("bookedBy") + ": " + rs.getInt("cancellations"));
                counter++;
            }
            rs.close();
            if(counter == 0){
                System.out.println("No Tenant Cancellations");
            }

            rs = stmt.executeQuery(runReportHost);
            System.out.println("Host/s With Most Cancellations: ");
            counter = 0;
            while(rs.next()){
                System.out.println(rs.getString("ownerUName") + ": " + rs.getInt("cancellations"));
                counter++;
            }
            if(counter == 0){
                System.out.println("No Tenant Cancellations");
            }
            rs.close();
            stmt.close();
            conn.close();
        }catch (SQLException e){
            System.out.println("Database Error.");
        }

    }
    public static void runReportsNoun(){
        String runReport = "Select * " +
                "From tenantCommenting " +
                " Order By listingId";

        try {
            Connection conn = DriverManager.getConnection(CONNECTION, "root", "");
            Statement stmt = conn.createStatement();
            Statement stmt2 = conn.createStatement();

            ResultSet rs = stmt.executeQuery(runReport);
            int currentListingId = -1;
            Map<String, Integer> nounCount = new LinkedHashMap<>();

            while(true){
                boolean rsNext = rs.next();
                if(!rsNext || currentListingId != rs.getInt("listingId")){
                    Stream<Map.Entry<String, Integer>> sorted = nounCount.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()));
                    int count = 0;
                    int counter = 1;
                    List<Map.Entry<String, Integer>> list = sorted.collect(Collectors.toList());
                    while (count < list.size() && count < 3) {
                        Map.Entry<String, Integer> entry = list.get(count);
                        System.out.println("Phrase: " + entry.getKey() + ", count: " + entry.getValue() + ".");
                        count++;
                    }
                    if(rsNext){
                        currentListingId = rs.getInt("listingId");
                        ResultSet listing = stmt2.executeQuery("Select * " +
                                "From listings " +
                                "Where listingId = '" + currentListingId +"'");
                        listing.next();
                        System.out.println("Listing " + counter +":");

                        System.out.print("\tType: " + listing.getString("listingType"));
                        System.out.print(", Address: " + listing.getString("address"));
                        System.out.print(", Postal Code: " + listing.getString("postalCode"));
                        System.out.print(", City: " + listing.getString("city"));
                        System.out.print(", Country: " + listing.getString("country"));
                        if(!listing.getString("listingType").equals("Full House")){
                            System.out.print(", Unit Number: " + listing.getString("unitNumber"));
                        }
                        System.out.print(", Price: " + listing.getDouble("price"));
                        System.out.print(", Longitude: " + listing.getDouble("longitude"));
                        System.out.println(", Latitude: " + listing.getDouble("latitude"));
                        listing.close();
                    }
                    nounCount.clear();
                }
                if(!rsNext)
                    break;

                Matcher matcher = Reports.NOUN_PHRASE.matcher(rs.getString("comment"));
                while (matcher.find()) {
                    String match = matcher.group();
                    nounCount.put(match, nounCount.getOrDefault(match, 0) + 1);
                    //System.out.println(match);
                }


            }
            rs.close();
            stmt.close();
            conn.close();
        }catch (SQLException e){
            System.out.println("Database Error.");
            e.printStackTrace();
        }
    }
}
