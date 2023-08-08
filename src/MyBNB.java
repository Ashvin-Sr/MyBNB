import java.sql.*;
import java.util.*;

public class MyBNB {
    private static final String dbClassName = "com.mysql.cj.jdbc.Driver";
    private static final String CONNECTION = "jdbc:mysql://localhost:3306/airbnb";

    public static void main(String[] args) throws ClassNotFoundException {
        //Register JDBC driver
        Class.forName(dbClassName);
        Scanner inputInt = new Scanner(System.in);
        while(true){
            String username = userLogin();
            if(username == null)
                break;
            while (true) {
                System.out.println("Choose an Option:" +
                        "\n1. Delete Account" +
                        "\n2. Add Listing" +
                        "\n3. Remove Listing" +
                        "\n4. Change Price of Listing" +
                        "\n5. Put Listing Up For Rent" +
                        "\n6. Remove Listing From Being Rentable" +
                        "\n7. Cancel Booking As Host" +
                        "\n8. Cancel Booking As Tenant" +
                        "\n9. Rent A Listing" +
                        "\n10. Comment About A Tenant" +
                        "\n11. Comment About A Listing. " +
                        "\n12. Logout");
                int choice = inputInt.nextInt();
                if (choice == 1) {
                    deleteAccount(username);
                    username = null;
                    break;
                }
                else if (choice == 2)
                    addListing(username);
                else if (choice == 3)
                    removeListing(username);
                else if (choice == 4)
                    changeRentPrice(username);
                else if (choice == 5)
                    upForRent(username);
                else if (choice == 6)
                    removeForRent(username);
                else if (choice == 7)
                    removeBookingHost(username);
                else if (choice == 8)
                    removeBookingTenant(username);
                else if (choice == 9)
                    rentAListing(username);
                else if (choice == 10)
                    hostCommenting(username);
                else if (choice == 11)
                    tenantCommenting(username);
                else if (choice == 12) {
                    username = null;
                    break;
                }
            }
        }



    }
    public static String userLogin() throws ClassNotFoundException{
        int choice = 0;
        Scanner inputInt = new Scanner(System.in);
        Scanner inputString = new Scanner(System.in);

        while(choice != 1 && choice != 2 && choice != 3){
            System.out.println("Choose an option:" +
                    "\n1. Login With Existing User" +
                    "\n2. Create a New User" +
                    "\n3. Close Application");
            choice = inputInt.nextInt();
            if (choice != 1 && choice != 2 && choice != 3){
                System.out.println("Choose a valid option");
            }
        }
        if(choice == 3)
            return null;

        System.out.println("Enter Username");
        String username = inputString.nextLine();

        System.out.println("Enter Password");
        String password = inputString.nextLine();
        try {
            while(true) {
                Connection conn = DriverManager.getConnection(CONNECTION, "root", "");
                Statement stmt = conn.createStatement();

                if (choice == 1) {//Login

                    String usersPasswords = "SELECT * FROM Users WHERE uname = '" + username + "' " + //Setup Query
                            "AND password = '" + password + "';";
                    ResultSet rs = stmt.executeQuery(usersPasswords); //Return Result Set

                    if (rs.next()) { //Close database Connection if name and password match
                        if (rs.getString("uname").equals(username)
                                && rs.getString("password").equals(password)) {
                            System.out.println("Login Successful");
                            rs.close();
                            stmt.close();
                            conn.close();
                            return username;
                        }
                    } else {
                        System.out.println("Incorrect Username and/or Password \nEnter Username");
                        username = inputString.nextLine();
                        System.out.println("Enter Password");
                        password = inputString.nextLine();
                    }
                }
                else {
                    String usersPasswords = "SELECT * FROM Users WHERE uname = '" + username + "';";
                    ResultSet rs = stmt.executeQuery(usersPasswords);

                    if (rs.next()) {
                        System.out.println("User already exists\nEnter new username");
                        username = inputString.nextLine();
                    } else {

                        System.out.println("Enter Name");
                        String name = inputString.nextLine();
                        System.out.println("Enter Age: Must be 18");
                        int age = inputInt.nextInt();
                        if(age < 18){
                            System.out.println("Age Requirement Not Met. Closing Program.");
                        }
                        System.out.println("Enter SIN Number");
                        int SIN = inputInt.nextInt();
                        System.out.println("Enter CreditCard Number");
                        int creditCard = inputInt.nextInt();
                        System.out.println("Enter Occupation");
                        String occupation = inputString.nextLine();
                        String insertNewUser = "insert into users " +
                                "(uname,password,name,age,SIN,creditCard,occupation) values" +
                                "('" +username+ "', '" +password+ "', '" +name+ "', '" +age+
                                "', '" +SIN + "', '" +creditCard+ "', '" +occupation+"')";
                        stmt.executeUpdate(insertNewUser);
                        System.out.println("User Created Succesfully");
                        rs.close();
                        stmt.close();
                        conn.close();
                        return username;
                    }
                }
            }
        } catch (SQLException e){
            System.out.println("Error With Database");
        }
        return "";
    }
    public static void deleteAccount(String username) throws ClassNotFoundException{
        while (true){
            System.out.println("Are you Sure you want to delete this account alongside all your listings.\n1. Yes\n2. No");
            Scanner inputInt = new Scanner(System.in);
            int choice = inputInt.nextInt();
            if(choice == 1){
                try {
                    Connection conn = DriverManager.getConnection(CONNECTION, "root", "");
                    Statement stmt = conn.createStatement();
                    Statement stmt2 = conn.createStatement();

                    ResultSet forRentToDelete = stmt.executeQuery("Select listingId From listings Where" +
                            " ownerUName = '" + username + "'");
                    while(forRentToDelete.next()){
                        stmt2.executeUpdate("Delete from forrent Where listingId = '" +
                                forRentToDelete.getString("listingId") + "'");
                        stmt2.executeUpdate("Delete from booked Where listingId = '" +
                                forRentToDelete.getString("listingId") + "'");
                        stmt2.executeUpdate("Delete from tenantCommenting Where listingId = '" +
                                forRentToDelete.getString("listingId") + "'");
                    }

                    String removeListings = "Delete from listings Where ownerUName = '" +
                            username + "'";
                    stmt.executeUpdate(removeListings);
                    String removeUser = "Delete from users where uname = '" +
                            username + "'";
                    stmt.executeUpdate(removeUser);
                    String removeComments = "Delete from hostCommenting where host = '" +
                            username + "' or tenant = '" + username + "'";
                    stmt.executeUpdate(removeComments);
                    removeComments = "Delete from tenantCommenting where tenant = '" +
                            username + "' or tenant = '" + username + "'";
                    stmt.executeUpdate(removeComments);
                    System.out.println("Account with Username: " + username +" Has Been Deleted.");
                    forRentToDelete.close();
                    stmt.close();
                    stmt2.close();
                    conn.close();
                    break;

                }catch(SQLException e){
                    System.out.println("Database Error");
                    e.printStackTrace();
                }
            }
            else if(choice == 2){
                System.out.println("Account Not Deleted.");
                break;
            }
            else{
                System.out.println("Not a Valid Choice. Re-enter.");
            }
        }
    }
    public static void addListing(String uname) throws ClassNotFoundException{
        Scanner inputInt = new Scanner(System.in);
        Scanner inputString = new Scanner(System.in);
        Scanner inputDouble = new Scanner(System.in);
        int listingTypeInt = 0;
        String listingTypeName;
        double rentPrice = 0;

        try{
            Connection conn = DriverManager.getConnection(CONNECTION, "root", "");
            Statement stmt = conn.createStatement();
            while(true){
                System.out.println("Enter address of Listing");
                String address = inputString.nextLine();

                System.out.println("Enter postal code of Listing. Postal Codes are numbers.");
                int postalCode = inputInt.nextInt();

                System.out.println("Enter city of Listing");
                String city = inputString.nextLine();

                System.out.println("Enter country of Listing");
                String country = inputString.nextLine();

                listingTypeInt = 0;
                while (listingTypeInt != 1 && listingTypeInt != 2 && listingTypeInt != 3) {
                    System.out.println("The type of listing:\n1. Apartment\n2. Full House\n3. Room");
                    listingTypeInt = inputInt.nextInt();
                    if (listingTypeInt != 1 && listingTypeInt != 2 && listingTypeInt != 3) {
                        System.out.println("Not a Valid Option");
                    }
                }
                int unitNumber = 0;
                if (listingTypeInt == 1) {
                    listingTypeName = "Apartment";
                    System.out.println("Enter the Unit Number.");
                    unitNumber = inputInt.nextInt();

                } else if (listingTypeInt == 2) {
                    listingTypeName = "Full House";

                } else {
                    listingTypeName = "Room";
                    System.out.println("Enter the Unit Number.");
                    unitNumber = inputInt.nextInt();
                }

                System.out.println("Enter Latitude of Listing");
                double latitude = inputDouble.nextDouble();
                System.out.println("Enter Longitude of Listing");
                double longitude = inputDouble.nextDouble();

                ArrayList<String> amenitiesOfListing = new ArrayList<String>();
                ArrayList<String> amenitiesChosen = new ArrayList<String>();
                ArrayList<String> amenities = new ArrayList<String>();
                ArrayList<Double> amenityValue = new ArrayList<Double>();
                ArrayList<Double> amenityValueOfListing = new ArrayList<Double>();
                ResultSet listOfAmenities = stmt.executeQuery("Select * From amenities");
                while(listOfAmenities.next()){
                    System.out.println(listOfAmenities.getInt("amenityId") + ". "
                            + listOfAmenities.getString("amenityName"));
                    amenities.add(listOfAmenities.getString("amenityName"));
                    amenityValue.add(listOfAmenities.getDouble("amenityValue"));

                }
                System.out.println("Type the Number Associated with Amenities you Have as x,y,z");
                amenitiesChosen = filterString(inputString.nextLine());
                for (String id: amenitiesChosen) {
                    if(Integer.parseInt(id) < 1 || Integer.parseInt(id) > amenities.size())
                        break;
                    amenitiesOfListing.add(amenities.get(Integer.parseInt(id) - 1));
                    amenityValueOfListing.add(amenityValue.get(Integer.parseInt(id) - 1));
                }
                String amenitiesForListing = ",";
                for (String s:amenitiesOfListing) {
                    amenitiesForListing = amenitiesForListing.concat(s + ",");
                }

                System.out.println("Would you like a Suggestion On How to Price your Listing. 1/Yes or 2/No");
                int choice = inputInt.nextInt();

                while(true){
                    choice = inputInt.nextInt();
                    if(choice == 1 || choice == 2)
                        break;
                    System.out.println("Invalid Choice.");
                }

                if (choice == 1) {
                    System.out.println("Enter the Current Value of the Property");
                    double listingValue = inputDouble.nextDouble();
                    double amenityValueTotal = 0;
                    for (double value : amenityValueOfListing) {
                        amenityValueTotal+=value;
                    }
                    double suggestedPrice = Math.round((((listingValue) + (listingValue * amenityValueTotal)) * 0.0005) * 100.0) / 100.0;
                    System.out.println("Based on the Amenities of your Listing your Property Should be" +
                            " Listed For $" + suggestedPrice);
                    System.out.println("Enter:\n1. For Suggested Price\n2. Your Own Price");

                    while(true){
                        choice = inputInt.nextInt();
                        if(choice == 1 || choice == 2)
                            break;
                        System.out.println("Invalid Choice.");
                    }
                    if(choice == 1)
                        rentPrice = suggestedPrice;
                }
                if (choice == 2) {
                    System.out.println("Enter the Price for this Listing");
                    rentPrice = inputInt.nextDouble();
                }
                else if(choice != 1) {
                    System.out.println("Not a Valid Choice.");
                }
                System.out.println("Enter 1 If You Would Like the Host ToolKit");
                choice = inputInt.nextInt();
                if(choice == 1){
                    ResultSet amenitySuggested = stmt.executeQuery("Select * " +
                            "From amenities " +
                            "Where amenitySuggestion = '1' " +
                            "Order by amenityValue desc");
                    LinkedHashMap<String, Double> amenitiesToSuggest = new LinkedHashMap<String, Double>();

                    while (amenitySuggested.next()) {
                        amenitiesToSuggest.put(amenitySuggested.getString("amenityName"),
                                amenitySuggested.getDouble("amenityValue"));
                    }

                    for (String str : amenitiesOfListing) {
                        amenitiesToSuggest.remove(str);
                    }
                    if (amenitiesToSuggest.isEmpty()) {
                        System.out.println("You Have Great Amenities. No Suggestions Needed");
                    }
                    System.out.println("If You Add the Amenities: ");
                    int count = 0;
                    double amenityValueTotal = 0;
                    for (Map.Entry<String, Double> mapElement :
                            amenitiesToSuggest.entrySet()) {
                        count++;
                        String key = mapElement.getKey();
                        amenityValueTotal += mapElement.getValue();

                        // Printing the key-value pairs
                        System.out.println(key);
                        if (count == 3)
                            break;
                    }
                    System.out.println("Would Increase Your listing From: " + rentPrice + ", To: "
                            + (rentPrice * (1 + amenityValueTotal)) + "\n");
                }
                System.out.println("Your listing:\n" +
                        "ListingType: " + listingTypeName +
                        "\nAddress: " + address +
                        "\nPostal Code: " + postalCode +
                        "\nCity: " + city +
                        "\nCountry: " + country);
                if(listingTypeInt != 2){
                    System.out.println("Unit Number: " + unitNumber);
                }
                System.out.println("Latitude: " + latitude +
                        "\nLongitude: " + longitude +
                        "\nPrice: " + rentPrice +
                        "\nAmenities: " + amenitiesForListing);
                System.out.println("Correct Information?. 1/Yes 2/No");

                while(true){
                    choice = inputInt.nextInt();
                    if(choice == 1 || choice == 2)
                        break;
                    System.out.println("Invalid Choice.");
                }

                if (choice == 1) {
                    if(listingTypeInt == 2) {//figure when listings already exist
                        ResultSet checkIfListingExists = stmt.executeQuery("Select * " +
                                "From listings Where " +
                                "latitude = '" + latitude + "' AND " +
                                "longitude = '" + longitude + "' AND " +
                                "address = '" + address + "' AND " +
                                "city = '" + city + "' AND " +
                                "country = '" + country + "' ");
                        if(!checkIfListingExists.next()) {
                            String insertNewListing = "insert into listings " +
                                    "(ownerUName,listingtype,latitude,longitude,price,address,postalCode,amenities,city,country) values " +
                                    "('" + uname + "', '" + listingTypeName + "', '" + latitude
                                    + "', '" + longitude + "', '" + rentPrice + "', '" + address + "', '" + postalCode
                                    + "', '" + amenitiesForListing + "', '" + city + "', '" + country + "')";
                            stmt.executeUpdate(insertNewListing); //Return Result Set
                            conn.close();
                            stmt.close();
                            checkIfListingExists.close();

                            return;
                        }
                        else{
                            System.out.println("Listing Not Added as Listing Already Exists");
                        }
                    }
                    else{
                        ResultSet checkIfListingExists = stmt.executeQuery("Select * " +
                                "From listings Where " +
                                "(listingType = 'Full House' OR unitNumber = '" + unitNumber+"')" + " AND " +
                                "latitude = '" + latitude + "' AND " +
                                "longitude = '" + longitude + "' AND " +
                                "address = '" + address + "' AND " +
                                "city = '" + city + "' AND " +
                                "country = '"+ country + "' ");
                        if(!checkIfListingExists.next()) {
                            String insertNewListing = "insert into listings " +
                                    "(ownerUName,listingType,latitude,longitude,price,address,postalCode,amenities" +
                                    ",unitNumber,city,country) values " +
                                    "('" + uname + "', '" + listingTypeName + "', '" + latitude
                                    + "', '" + longitude + "', '" + rentPrice + "', '" + address +
                                    "', '" + postalCode + "', '" + amenitiesForListing + "', '" + unitNumber +
                                    "', '" + city + "', '" + country + "')";
                            stmt.executeUpdate(insertNewListing); //Return Result Set
                            conn.close();
                            stmt.close();
                            checkIfListingExists.close();
                            return;
                        }
                        else{
                            System.out.println("Listing Not Added as Listing Already Exists");
                        }
                    }
                }
            }

        }catch(SQLException e){
            System.out.println("Database Error");
        }
    }
    public static ArrayList<Integer> printUsersListings(String username) throws ClassNotFoundException{
        System.out.println("Your Listings");
        ArrayList<Integer> addressesOfListings = new ArrayList<Integer>();
        try{
            Connection conn = DriverManager.getConnection(CONNECTION, "root", "");
            Statement stmt = conn.createStatement();
            String listingQuery = "Select * From Listings Where ownerUName = '" + username + "' ";
            ResultSet rs = stmt.executeQuery(listingQuery);
            int counter = 1;
            while(rs.next()){
                System.out.println("Listing: " + counter);
                counter++;
                String listingType = rs.getString("listingtype");
                String address = rs.getString("address");
                String postalCode = rs.getString("postalCode");
                String city = rs.getString("city");
                String country = rs.getString("country");
                double price = rs.getDouble("price");
                addressesOfListings.add(rs.getInt("listingId"));
                double longitude = rs.getDouble("longitude");
                double latitude = rs.getDouble("latitude");

                //Display values
                System.out.print("Type: " + listingType);
                System.out.print(", Address: " + address);
                System.out.print(", Postal Code: " + postalCode);
                System.out.print(", City: " + city);
                System.out.print(", Country: " + country);
                if(!listingType.equals("Full House")){
                    System.out.print(", Unit Number: " + rs.getString("unitNumber"));
                }
                System.out.print(", Price: " + price);
                System.out.print(", Latitude: " + latitude);
                System.out.println(", Longitude: " + longitude);
            }
            conn.close();
            stmt.close();
            rs.close();
        }catch(SQLException e){
            System.out.println("Database Error");
        }

        return addressesOfListings;
    }
    public static void upForRent(String username) throws ClassNotFoundException{
        Scanner inputInt = new Scanner(System.in);
        Scanner inputString = new Scanner(System.in);

        ArrayList<Integer> addressOfListings = printUsersListings(username);

        if(addressOfListings.isEmpty()){
            System.out.println("You have no listings.");
        }
        int listingIdForRent;
        while (true){
            System.out.println("Type the Listing Number Associated With The Listing You Want to Put Up for Rent." +
                    "Or the Number 0 to Exit");
            int addressToRemoveInt = inputInt.nextInt();
            if (addressToRemoveInt == 0) {
                return;
            } else if (addressToRemoveInt >= 1 && addressToRemoveInt <= addressOfListings.size()) {
                listingIdForRent = addressOfListings.get(addressToRemoveInt - 1);
                break;
            } else {
                System.out.println("Invalid choice. Please Re-enter");
            }
        }

        try{
            Connection conn = DriverManager.getConnection(CONNECTION, "root", "");
            Statement stmt = conn.createStatement();

            Calendar startCalendar = Calendar.getInstance();
            Calendar endCalendar = Calendar.getInstance();
            while(true){
                System.out.println("Enter the starting year of the you want to rent your listing");
                int startYear = inputInt.nextInt();
                System.out.println("Enter the starting month of the you want to rent your listing");
                int startMonth = inputInt.nextInt();
                System.out.println("Enter the starting day of the you want to rent your listing");
                int startDay = inputInt.nextInt();

                System.out.println("Enter the ending year of the you want to rent your listing");
                int endYear = inputInt.nextInt();
                System.out.println("Enter the ending month of the you want to rent your listing");
                int endMonth = inputInt.nextInt();
                System.out.println("Enter the ending day of the you want to rent your listing");
                int endDay = inputInt.nextInt();
                startCalendar.clear();
                startCalendar.set(startYear, startMonth, startDay);

                endCalendar.clear();
                endCalendar.set(endYear, endMonth, endDay);
                if(startCalendar.compareTo(endCalendar) > 0){
                    System.out.println("Invalid range of Date");
                }
                else{
                    break;
                }
            }
            while(startCalendar.compareTo(endCalendar) != 1){
                String checkIfAlreadyExist = " Select * From forrent Where listingId = '" +
                        listingIdForRent +
                        "' And date = '" + startCalendar.get(Calendar.YEAR) + "-" + startCalendar.get(Calendar.MONTH)
                        + "-" + startCalendar.get(Calendar.DATE) + "'";
                String rentUpdate = "insert into forrent " +
                        "(listingId,date) values" +
                        "('" + listingIdForRent + "', '" + startCalendar.get(Calendar.YEAR)
                        + "-" + startCalendar.get(Calendar.MONTH)
                        + "-" + startCalendar.get(Calendar.DATE) + "')";
                ResultSet rentRS = stmt.executeQuery(checkIfAlreadyExist);
                if (!rentRS.next()) {
                    stmt.executeUpdate(rentUpdate);
                }
                startCalendar.add(Calendar.DATE, 1);
            }
            conn.close();
            stmt.close();
        }catch(SQLException e){
            System.out.println("Database Error");
        }

    }
    public static void removeListing(String username) throws ClassNotFoundException{
        Scanner inputInt = new Scanner(System.in);
        Scanner inputString = new Scanner(System.in);

        ArrayList<Integer> addressOfListings = printUsersListings(username);

        int listingIdToRemove;
        while (true){
            System.out.println("Type the Listing Number Associated With The Listing You to Take Down." +
                    " Or the Number 0 to Exit");
            int addressToRemoveInt = inputInt.nextInt();
            if (addressToRemoveInt == 0) {
                return;
            } else if (addressToRemoveInt >= 1 && addressToRemoveInt <= addressOfListings.size()) {
                listingIdToRemove = addressOfListings.get(addressToRemoveInt - 1);
                break;
            } else {
                System.out.println("Invalid choice. Please Re-enter");
            }
        }
        try{
            Connection conn = DriverManager.getConnection(CONNECTION, "root", "");
            Statement stmt = conn.createStatement();
            String removeListingQuery = "Delete From listings Where ownerUName = '" + username
                    + "' And listingId = '" + listingIdToRemove +"'";
            stmt.executeUpdate(removeListingQuery);
            String removeForRentQuery = "Delete From forrent Where listingId = '" + listingIdToRemove +"'";
            stmt.executeUpdate(removeForRentQuery);
            String removeBookedQuery = "Delete From booked Where listingId = '" + listingIdToRemove +"'";
            stmt.executeUpdate(removeBookedQuery);
            String removeTenantCommentingQuery = "Delete From tenantCommenting Where listingId = '" + listingIdToRemove +"'";
            stmt.executeUpdate(removeBookedQuery);

            conn.close();
            stmt.close();
        }catch(SQLException e){
            System.out.println("Database Error");
        }
    }
    public static void removeForRent(String username) throws ClassNotFoundException{
        Scanner inputInt = new Scanner(System.in);

        ArrayList<Integer> addressOfListings = printUsersListings(username);

        int listingIdToChange;
        while (true){
            System.out.println("Type the Listing Number Associated With The Listing You Want to Take Down for Rent."  +
                    "Or the Number 0 to Exit");
            int listingToRemoveInt = inputInt.nextInt();
            if (listingToRemoveInt == 0) {
                return;
            } else if (listingToRemoveInt >= 1 && listingToRemoveInt <= addressOfListings.size()) {
                listingIdToChange = addressOfListings.get(listingToRemoveInt - 1);
                break;
            } else {
                System.out.println("Invalid choice. Please Re-enter");
            }
        }


        try{
            Connection conn = DriverManager.getConnection(CONNECTION, "root", "");
            Statement stmt = conn.createStatement();

            ResultSet listingsToFilter = stmt.executeQuery("Select * " +
                    "From forrent " +
                    "Where listingId = '" + listingIdToChange + "'");

            ArrayList<String> dates = new ArrayList<String>();

            int counter = 1;
            while(listingsToFilter.next()){
                System.out.println(counter + ": " + listingsToFilter.getString("date"));
                dates.add(listingsToFilter.getString("date"));
                counter++;
            }

            if(counter == 1){
                System.out.println("You have no Listings up for Rent. Returning to Home Screen.");
                return;
            }

            System.out.println("Enter the Number Associated with the Dates you want to Remove as x,y,z.");
            Scanner inputString = new Scanner(System.in);
            ArrayList<String> filteredChoices = filterString(inputString.nextLine());

            for(String dateIndex: filteredChoices){
                if(Integer.parseInt(dateIndex) < 1 || Integer.parseInt(dateIndex) > dates.size())
                    continue;
                String listedForRentQuery = "Delete From forrent Where"
                        + " listingId = '" + listingIdToChange + "' AND date = '" +
                        dates.get(Integer.parseInt(dateIndex) - 1) + "'";
                stmt.executeUpdate(listedForRentQuery);

            }

            conn.close();
            stmt.close();
            listingsToFilter.close();
        }catch(SQLException e){
            System.out.println("Database Error");
        }

    }
    public static void changeRentPrice(String username) throws ClassNotFoundException{
        ArrayList<Integer> addressOfListings = printUsersListings(username);
        Scanner inputInt = new Scanner(System.in);
        Scanner inputDouble = new Scanner(System.in);

        int listingIdToUpdate;
        while (true){
            System.out.println("Type the Listing Number Associated With The Listing You to Take Down." +
                    " Or the Number 0 to Exit");
            int addressToRemoveInt = inputInt.nextInt();
            if (addressToRemoveInt == 0) {
                return;
            } else if (addressToRemoveInt >= 1 && addressToRemoveInt <= addressOfListings.size()) {
                listingIdToUpdate = addressOfListings.get(addressToRemoveInt - 1);
                break;
            } else {
                System.out.println("Invalid choice. Please Re-enter");
            }

        }
        System.out.println("Enter the New Price of the Listing");
        double priceAdjustment = inputDouble.nextDouble();
        try{
            Connection conn = DriverManager.getConnection(CONNECTION, "root", "");
            Statement stmt = conn.createStatement();
            String updatePriceListingQuery = "UPDATE listings " +
                    "SET price = '" + priceAdjustment + "'" +
                    " WHERE ownerUName ='" + username + "' And listingId = '" + listingIdToUpdate +"'";
            stmt.executeUpdate(updatePriceListingQuery);

            conn.close();
            stmt.close();
        }catch(SQLException e){
            System.out.println("Database Error");
        }
    }
    public static void rentAListing(String username){
        Scanner inputDouble = new Scanner(System.in);
        Scanner inputInt = new Scanner(System.in);
        Scanner inputString = new Scanner(System.in);

        ArrayList<String> amenities = new ArrayList<String>();
        ArrayList<String> amenitiesId = new ArrayList<String>();
        String amenitiesChoice = "";

        int startYear = 0;
        int startMonth = 0;
        int startDay = 0;

        int endYear = 0;
        int endMonth = 0;
        int endDay = 0;

        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        
        double searchArea = 10;
        int postalCode = 0;
        String postalCodeChanged = "Anywhere";
        double wantedLatitude = 0;
        double wantedLongitude = 0;
        String longLatChanged = "Anywhere";
        String addressChanged = "Anywhere";
        String dateChanged = "Anytime";
        String priceChanged = "Any Price";
        String sortBy = "distance";
        double priceMin = 0;
        double priceMax = 0;
        try{
            Connection conn = DriverManager.getConnection(CONNECTION, "root", "");
            Statement stmt = conn.createStatement();
            Statement stmt2 = conn.createStatement();
            ArrayList<Integer> filteredListings = new ArrayList<Integer>();
            int counter = 1;
            while(true){
                while (true) {
                    System.out.print("Here is the filter. Enter the Number Associated With the Filter You Would Like to Change." +
                            "\n0. Return to Home Screen" +
                            "\n1. Latitude, Longitude: " + longLatChanged +
                            "\n2. Area of Search: " + searchArea +
                            "\n3. Address: " + addressChanged +
                            "\n4. Postal Code: " + postalCodeChanged +
                            "\n5. Amenities: |");
                    for (String s : amenities) {
                        System.out.print(s + "|");
                    }
                    System.out.println("\n6. Sort by: " + sortBy +
                            "\n7. Date Range: " + dateChanged +
                            "\n8. Price Range: " + priceChanged +
                            "\n9. Reset Filter" +
                            "\n10. Search With This Filter.");
                    int choice = inputInt.nextInt();
                    if(choice == 0) {
                        stmt.close();
                        stmt2.close();
                        conn.close();
                        return;
                    }
                    else if (choice == 1) {
                        System.out.println("Enter the Latitude Where you Would Like to Rent.");
                        wantedLatitude = inputDouble.nextDouble();
                        System.out.println("Enter the Longitude Where you Would Like to Rent.");
                        wantedLongitude = inputDouble.nextDouble();
                        longLatChanged = wantedLatitude + " " + wantedLongitude;
                    } else if (choice == 2) {
                        System.out.println("Enter the Area of Search.");
                        searchArea = inputDouble.nextDouble();
                    } else if (choice == 3) {
                        System.out.println("Enter the Address.");
                        addressChanged = inputString.nextLine();
                    } else if (choice == 4) {
                        System.out.println("Enter the Postal Code.");
                        postalCode = inputInt.nextInt();
                        postalCodeChanged = postalCode + "";
                    } else if (choice == 5) {
                        amenities.removeAll(amenities);
                        ResultSet listOfAmenities = stmt.executeQuery("Select * From amenities");
                        while (listOfAmenities.next()) {
                            System.out.println(listOfAmenities.getInt("amenityId") + ". "
                                    + listOfAmenities.getString("amenityName"));
                        }
                        listOfAmenities.close();
                        System.out.println("Type the Amenities Wanted as x,y,z");
                        amenitiesChoice = inputString.nextLine();
                        amenitiesId = filterString(amenitiesChoice);
                        for (String id : amenitiesId) {
                            ResultSet amenitiesChoosen = stmt.executeQuery("Select * From amenities Where amenityID = '" +
                                    Integer.parseInt(id) + "'");
                            if(amenitiesChoosen.next())
                            {
                                amenities.add(amenitiesChoosen.getString("amenityName"));
                                amenitiesChoosen.close();
                            }
                        }
                        amenitiesId.removeAll(amenitiesId);
                    } else if (choice == 6) {
                        while (true) {
                            System.out.println("Type the Number Associated How to Sort." +
                                    "\n1. Price Ascending" +
                                    "\n2. Price Descending" +
                                    "\n3. Distance");
                            choice = inputInt.nextInt();
                            if (choice == 1) {
                                sortBy = "Price ASC";
                                break;
                            } else if (choice == 2) {
                                sortBy = "Price DESC";
                                break;
                            } else if (choice == 3) {
                                sortBy = "Distance";
                                break;
                            } else {
                                System.out.println("Invalid Input");
                            }
                        }
                    } else if (choice == 7) {
                        while (true) {
                            System.out.println("Enter the starting year of the you want to rent your listing");
                            startYear = inputInt.nextInt();
                            System.out.println("Enter the starting month of the you want to rent your listing");
                            startMonth = inputInt.nextInt();
                            System.out.println("Enter the starting day of the you want to rent your listing");
                            startDay = inputInt.nextInt();

                            System.out.println("Enter the ending year of the you want to rent your listing");
                            endYear = inputInt.nextInt();
                            System.out.println("Enter the ending month of the you want to rent your listing");
                            endMonth = inputInt.nextInt();
                            System.out.println("Enter the ending day of the you want to rent your listing");
                            endDay = inputInt.nextInt();


                            startCalendar.clear();
                            startCalendar.set(startYear, startMonth, startDay);

                            endCalendar.clear();
                            endCalendar.set(endYear, endMonth, endDay);
                            if (startCalendar.compareTo(endCalendar) > 0) {
                                System.out.println("Invalid range of Date");
                            } else {
                                dateChanged = startYear + "-" + startMonth + "-" + startDay +
                                        " to " + endYear + "-" + endMonth + "-" + endDay;
                                break;
                            }
                        }

                    } else if (choice == 8) {
                        System.out.println("Enter the Minimum Price");
                        priceMin = inputDouble.nextDouble();
                        System.out.println("Enter the Maximum Price");
                        priceMax = inputDouble.nextDouble();
                        priceChanged = "From: " + priceMin + ", To: " + priceMax;
                    } else if (choice == 9) {
                        System.out.println("Filter Reset");
                        amenities.removeAll(amenities);
                        searchArea = 10;
                        postalCodeChanged = "Anywhere";
                        longLatChanged = "Anywhere";
                        addressChanged = "Anywhere";
                        dateChanged = "Anytime";
                        priceChanged = "Any Price";
                    } else if (choice == 10) {
                        System.out.println("Filter Applied");
                        break;
                    } else {
                        System.out.println("Invalid Choice.");
                    }

                }

                String listingsUpForRentFiltered = "Select *, (longitude -" + wantedLongitude + ")*(longitude" +
                        " - " + wantedLongitude + ") + (latitude - " + wantedLatitude + ")*(latitude - " + wantedLatitude +
                        ") AS Distance From listings ";
                String where = "Where Not ownerUName = '" + username + "'";
                if (!longLatChanged.equals("Anywhere")) {
                    where = where.concat(" AND latitude between " + (wantedLatitude - searchArea) + " AND " + (wantedLatitude + searchArea)
                            + " AND longitude between " + (wantedLongitude - searchArea) + " AND " + (wantedLongitude + searchArea));
                }
                if (!addressChanged.equals("Anywhere")) {
                    where = where.concat(" AND address = '" + addressChanged + "'");
                }
                if (!postalCodeChanged.equals("Anywhere")) {
                    where = where.concat(" AND postalCode between " + (postalCode - 1) + " AND " + (postalCode + 1));
                }
                if (!priceChanged.equals("Any Price")) {
                    where = where.concat(" AND price between " + (priceMin) + " AND " + (priceMax));
                }


                ResultSet rs = stmt.executeQuery(listingsUpForRentFiltered + where + " ORDER BY " + sortBy);

                counter = 1;

                while (rs.next()) {
                    String checkIfRentable = "Select * From forrent Join listings On forrent.listingId = listings.listingId Where NOT ownerUName = '" + username + "'";
                    boolean amenitiesMissing = false;
                    if (!dateChanged.equals("Anytime")) {
                        checkIfRentable = checkIfRentable.concat(" AND Exists (Select * From forrent Where date " +
                                "Between '" +
                                startCalendar.get(Calendar.YEAR) + "-" + startCalendar.get(Calendar.MONTH)
                                + "-" + startCalendar.get(Calendar.DATE) + "' AND '" +
                                endCalendar.get(Calendar.YEAR) + "-" + endCalendar.get(Calendar.MONTH)
                                + "-" + endCalendar.get(Calendar.DATE) + "' AND listingId = '"
                                + rs.getInt("listingId") + "')");

                        ResultSet filteredByDatesListings = stmt2.executeQuery(checkIfRentable);
                        if (!filteredByDatesListings.next()) {
                            filteredByDatesListings.close();
                            continue;
                        }
                        filteredByDatesListings.close();
                    }
                    for (String s : amenities) {
                        if (!rs.getString("amenities").contains("," + s + ","))
                            amenitiesMissing = true;
                    }
                    if (amenitiesMissing)
                        continue;
                    System.out.println("Listing: " + counter);
                    counter++;
                    filteredListings.add(rs.getInt("listingId"));

                    //Display values
                    System.out.print("Type: " + rs.getString("listingType"));
                    System.out.print(", Address: " + rs.getString("address"));
                    System.out.print(", Postal Code: " + rs.getString("postalCode"));
                    if (!rs.getString("listingType").equals("Full House")) {
                        System.out.print(", Unit Number: " + rs.getString("unitNumber"));
                    }
                    System.out.print(", Address: " + rs.getString("address"));
                    System.out.print(", Price: " + rs.getDouble("price"));
                    System.out.print(", Longitude: " + rs.getDouble("longitude"));
                    System.out.println(", Latitude: " + rs.getDouble("latitude"));

                }
                rs.close();
                if (counter == 1) {
                    System.out.println("No Listings Exist With your Filter. Returning to Filter.");
                    continue;
                }
                break;
            }
            ArrayList<String> datesToChoose = new ArrayList<String>();
            int listingIdChoice;
            int choiceOfListing;
            while(true){
                System.out.println("Enter the Listing Number Associated with the Listing you Want to Rent " +
                        "or 0 to Exit.");
                choiceOfListing = inputInt.nextInt();
                if(choiceOfListing == 0)
                    return;
                if((choiceOfListing >= 1 && choiceOfListing <= filteredListings.size())) {
                    listingIdChoice = filteredListings.get(choiceOfListing - 1);
                    ResultSet datesForListing = stmt.executeQuery("Select date From forrent where listingId = '"
                            + listingIdChoice + "'");
                    counter = 1;
                    while(datesForListing.next()){
                        System.out.println(counter +  ". " + datesForListing.getString("date"));
                        datesToChoose.add(datesForListing.getString("date"));
                        counter++;
                    }
                    if(counter == 1){
                        System.out.println("No Dates Available for This Listing. Returning To Selection.");
                        continue;
                    }
                    datesForListing.close();
                    break;
                }
                System.out.println("Not valid choice.");
            }



            System.out.println("Type the numbers associated with the Dates Wanted as x,y,z");
            String dateChoices = inputString.nextLine();
            ArrayList<String> filteredIntegers = filterString(dateChoices);
            ResultSet priceOfListing = stmt.executeQuery("Select Price From listings Where listingId ='" +
                    listingIdChoice + "'");
            priceOfListing.next();
            double price = priceOfListing.getDouble("price");

            for (String i : filteredIntegers) {
                if(!(Integer.parseInt(i) >= 1  && Integer.parseInt(i) <= datesToChoose.size()))
                    continue;
                stmt.executeUpdate("Delete From forrent Where listingId = '" +
                        listingIdChoice + "' AND date = '" + datesToChoose.get(Integer.parseInt(i) - 1) + "'");
                stmt.executeUpdate("Insert Into booked (listingId,date,bookedBy,reservationUsed,price) Values ('"
                        + listingIdChoice +"','"+ datesToChoose.get(Integer.parseInt(i) - 1)+"','"
                        + username +"','0','" + price + "')");
            }

        stmt.close();
        stmt2.close();
        conn.close();
        priceOfListing.close();
        }catch(SQLException e){
            System.out.println("Database Error");
        }


    }
    public static void removeBookingHost(String username) throws ClassNotFoundException{
        Scanner inputInt = new Scanner(System.in);
        Scanner inputString = new Scanner(System.in);
        try {
            Connection conn = DriverManager.getConnection(CONNECTION, "root", "");
            Statement stmt = conn.createStatement();

            ArrayList<String> datesOfBooking = new ArrayList<String>();
            ArrayList<Integer> listingId = printUsersListings(username);


            if(listingId.isEmpty()){
                    System.out.println("You Have no Listings. Returning To Home Screen.");
                    return;
            }
            int listingIdToRemoveBookings;
            while (true){
                System.out.println("Type the Listing Number Associated With The Listing You To Remove Bookings." +
                        "Or the Number 0 to Exit");
                listingIdToRemoveBookings = inputInt.nextInt();
                if (listingIdToRemoveBookings == 0) {
                    stmt.close();
                    conn.close();
                    return;
                } else if (listingIdToRemoveBookings >= 1 && listingIdToRemoveBookings <= listingId.size()) {
                    listingIdToRemoveBookings = listingId.get(listingIdToRemoveBookings - 1);
                    break;
                } else {
                    System.out.println("Invalid choice. Please Re-enter");
                }
            }

            ResultSet datesToFilter = stmt.executeQuery("Select * From booked Where reservationUsed = '0' AND " +
                    "listingId = '" + listingIdToRemoveBookings + "'");
            int counter = 1;
            while(datesToFilter.next()){
                System.out.println(counter +". " + datesToFilter.getString("date"));
                datesOfBooking.add(datesToFilter.getString("date"));
                counter ++;
            }
            datesToFilter.close();
            if(counter == 1){
                System.out.println("You Have no Listings Booked. Returning To Home Screen.");
                return;
            }
            System.out.println("Type the Number Associated With The Booking You to Take Down As x,y,z.");
            ArrayList<String> filtered = filterString(inputString.nextLine());


            for (String str: filtered) {
                if(!(Integer.parseInt(str) >= 1 && Integer.parseInt(str) <= datesOfBooking.size()))
                    continue;
                stmt.executeUpdate("Update booked Set reservationUsed = '3' Where listingId = '" +
                        listingIdToRemoveBookings + "' And date = '" +
                        datesOfBooking.get(Integer.parseInt(str) - 1) + "'");
            }
            System.out.println("All wanted bookings removed");
            stmt.close();
            conn.close();

        }catch(SQLException e){
            System.out.println("Database Error");
        }
    }

    public static void removeBookingTenant(String username) throws ClassNotFoundException{
        Scanner inputInt = new Scanner(System.in);
        Scanner inputString = new Scanner(System.in);
        try {
            Connection conn = DriverManager.getConnection(CONNECTION, "root", "");
            Statement stmt = conn.createStatement();
            Statement stmt2 = conn.createStatement();

            ArrayList<String> datesOfBooking = new ArrayList<String>();
            ArrayList<Integer> listingIDs = new ArrayList<Integer>();
            ResultSet listingIdsToFilter = stmt.executeQuery(
                    "Select Distinct listingId " +
                            "From booked " +
                            "Where reservationUsed = '0' AND " +
                            "bookedBy = '" + username + "'");
            int counter = 1;
            while(listingIdsToFilter.next()){
                ResultSet listing = stmt2.executeQuery("Select * " +
                        "From listings " +
                        "Where listingId = '" + listingIdsToFilter.getInt("listingId") +"'");
                listing.next();
                System.out.println("Listing " + counter +":");
                listingIDs.add(listing.getInt("listingID"));

                System.out.print("Type: " + listing.getString("listingType"));
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
                counter ++;
            }
            listingIdsToFilter.close();
            int listingIdChoice;
            int listingIdToRemoveBookings;

            if(counter == 1){
                System.out.println("You Have no Bookings. Returning to Home Screen.");
                conn.close();
                stmt.close();
                stmt2.close();
                return;
            }

            while(true){
                System.out.println("Type the Number Associated With The Listing of the Booking You Want to Cancel" +
                        " or 0 to Exit.");
                listingIdChoice = inputInt.nextInt();
                if(listingIdChoice == 0){
                    System.out.println("Returning to Home Screen.");
                    return;
                }
                if((listingIdChoice >= 1 && listingIdChoice <= listingIDs.size())) {
                    listingIdToRemoveBookings = listingIDs.get(listingIdChoice - 1);
                    break;
                }
                System.out.println("Not valid choice.");
            }
            ResultSet datesToFilter = stmt.executeQuery("Select date " +
                    "From Booked " +
                    "Where listingId = '" + listingIdToRemoveBookings +
                    "' AND bookedBy = '" + username + "'" +
                    " AND reservationUsed = '0'");
            counter = 1;
            while (datesToFilter.next()){
                System.out.println(counter + ": " + datesToFilter.getString("date"));
                datesOfBooking.add(datesToFilter.getString("date"));
                counter ++;
            }
            datesToFilter.close();
            System.out.println("Enter the Numbers Associated with the Date you want cancel as x,y,z or 0 to exit.");

            ArrayList<String> datesToRemove = filterString(inputString.nextLine());
            for (String str: datesToRemove) {
                if(Integer.parseInt(str) == 0){
                    stmt.close();
                    stmt2.close();
                    conn.close();
                    return;
                }
                if(!(Integer.parseInt(str) >= 1 && Integer.parseInt(str) <= datesOfBooking.size()))
                    continue;
                stmt.executeUpdate("Update booked Set reservationUsed = '2' Where listingId = '" +
                        listingIdToRemoveBookings + "' And date = '" +
                        datesOfBooking.get(Integer.parseInt(str) - 1) + "'");
            }
            stmt.close();
            stmt2.close();
            conn.close();
            System.out.println("Wanted Bookings Removed.");

        }catch(SQLException e){
            System.out.println("Database Error");
        }
    }
    public static void hostCommenting(String username){
        try {
            Scanner inputInt = new Scanner(System.in);
            Scanner inputString = new Scanner(System.in);
            Connection conn = DriverManager.getConnection(CONNECTION, "root", "");
            Statement stmt = conn.createStatement();

            ResultSet renter = stmt.executeQuery("Select bookedBy " +
                    "From booked join listings on booked.listingID = listings.listingId " +
                    "Where ownerUName = '" + username +
                    "' AND reservationUsed = '1'");

            int counter = 1;
            ArrayList<String> renterNames = new ArrayList<String>();

            while(renter.next()){
                renterNames.add(renter.getString("bookedBy"));
                System.out.println(counter + ": " + renter.getString("bookedBy"));
                counter ++;
            }
            renter.close();

            if(counter == 1){
                System.out.println("No One Has Rented your Listings. Returning to Home Screen.");
                stmt.close();
                conn.close();
                return;
            }

            int renterChoice = 0;
            String renterToComment;
            while(true){
                System.out.println("Type in the Number Associated with the Tenant you Would like to Comment About" +
                        " or 0 to Exit.");
                renterChoice = inputInt.nextInt();
                if(renterChoice == 0){
                    stmt.close();
                    conn.close();
                    return;
                }
                if((renterChoice >= 1 && renterChoice <= renterNames.size())) {
                    renterToComment = renterNames.get(renterChoice - 1);
                    break;
                }
                System.out.println("Not valid choice.");
            }
            ResultSet checkIfCommentExists = stmt.executeQuery("Select *" +
                    "From hostCommenting " +
                    "Where tenant = '" + renterToComment + "'" +
                    " AND host = '" + username + "'");
            if(checkIfCommentExists.next()){
                System.out.println("You have already commented");
                stmt.close();
                checkIfCommentExists.close();
                conn.close();
                return;
            }
            int rating;
            while(true){
                System.out.println("Enter a Rating From 1-5");
                rating = inputInt.nextInt();
                if(rating >= 1  && rating <= 5){
                    break;
                }
                System.out.println("Not Valid.");
            }
            System.out.println("Type your Comment about " + renterToComment);
            String comment = inputString.nextLine();

            stmt.executeUpdate("Insert Into hostCommenting (comment,host,tenant,rating)" +
                    " Values('" + comment + "','" + username + "','" + renterToComment+ "','" + rating + "')");
            stmt.close();
            checkIfCommentExists.close();
            conn.close();

        }catch (SQLException e){
            System.out.println("Datebase Error");
        }

    }
    public static void tenantCommenting(String username){
        try {
            Scanner inputInt = new Scanner(System.in);
            Scanner inputString = new Scanner(System.in);
            Connection conn = DriverManager.getConnection(CONNECTION, "root", "");
            Statement stmt = conn.createStatement();

            ResultSet listingsToFilter = stmt.executeQuery("Select * " +
                    "From booked join listings on booked.listingID = listings.listingId " +
                    "Where bookedBy = '" + username +
                    "' AND reservationUsed = '1'");

            ArrayList<Integer> listingIDs = new ArrayList<Integer>();

            int counter = 1;
            while(listingsToFilter.next()){
                System.out.println("Listing " + counter +":");
                listingIDs.add(listingsToFilter.getInt("listingID"));

                System.out.print("Type: " + listingsToFilter.getString("listingType"));
                System.out.print(", Address: " + listingsToFilter.getString("address"));
                System.out.print(", Postal Code: " + listingsToFilter.getString("postalCode"));
                System.out.print(", City: " + listingsToFilter.getString("city"));
                System.out.print(", Country: " + listingsToFilter.getString("country"));
                if(!listingsToFilter.getString("listingType").equals("Full House")){
                    System.out.print(", Unit Number: " + listingsToFilter.getString("unitNumber"));
                }
                System.out.print(", Price: " + listingsToFilter.getDouble("price"));
                System.out.print(", Longitude: " + listingsToFilter.getDouble("longitude"));
                System.out.println(", Latitude: " + listingsToFilter.getDouble("latitude"));

                counter ++;
            }

            listingsToFilter.close();

            if(counter == 1){
                System.out.println("You have not rented a listing. Returning to Home Screen.");
                stmt.close();
                conn.close();
                return;
            }



            int listingIdChoice = 0;
            int listingIdToComment;
            while(true){
                System.out.println("Type in the Number Associated with the Listing you Would like to Comment About.");
                listingIdChoice = inputInt.nextInt();
                if(listingIdChoice == 0){
                    stmt.close();
                    conn.close();
                    return;
                }
                if((listingIdChoice >= 1 && listingIdChoice <= listingIDs.size())) {
                    listingIdToComment = listingIDs.get(listingIdChoice - 1);
                    break;
                }
                System.out.println("Not valid choice.");
            }
            ResultSet checkIfCommentExists = stmt.executeQuery("Select *" +
                    "From tenantCommenting " +
                    "Where tenant = '" + username + "'" +
                    " AND listingId = '" + listingIdToComment + "'");
            if(checkIfCommentExists.next()){
                System.out.println("You have already commented");
                checkIfCommentExists.close();
                stmt.close();
                conn.close();
                return;
            }
            checkIfCommentExists.close();
            int rating;
            while(true){
                System.out.println("Enter a Rating From 1-5");
                rating = inputInt.nextInt();
                if(rating >= 1  && rating <= 5){
                    break;
                }
                System.out.println("Not Valid.");
            }
            System.out.println("Type your Comment about the Listing you Rented");
            String comment = inputString.nextLine();

            stmt.executeUpdate("Insert Into tenantCommenting (comment,tenant,listingId,rating)" +
                    " Values('" + comment + "', '" + username + "', '" + listingIdToComment+ "', '" + rating + "')");
            stmt.close();
            conn.close();

        }catch (SQLException e){
            System.out.println("Datebase Error");
        }

    }
    public static ArrayList<String> filterString(String str){
        ArrayList<String> filteredInteger = new ArrayList<String>();

        if(str != null){
            String[] spiltStr = str.split(",");
            for (String s : spiltStr) {
                filteredInteger.add(s);
            }
        }
        return filteredInteger;
    }

}
