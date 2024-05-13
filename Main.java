import java.io.*;
import java.util.*;

interface Data_Structure{
    void showItem(String l);
    double showRating(String s);
    int giveRating(String s, double r);
}

class Users {
    public static String userPath = System.getProperty("user.dir") + "\\" + "User.csv";
    public static ArrayList<Users> user_list = new ArrayList<>();
    ArrayList<Location> favLoc = new ArrayList<>();
    ArrayList<Hotels> favHotels = new ArrayList<>();
    ArrayList<Restaurant> favRest = new ArrayList<>();
    String username;
    private final String password;

    Users(String u_name, String passkey) {
        this.username = u_name;
        this.password = passkey;
    }

    String getPassword() {
        return this.password;
    }

    static ArrayList<Users> giveUserList() {
        return user_list;
    }

    int userLogin() {
        if (username.equals("admin") && password.equals("admin")) {
            System.out.println("\u001B[32m" + "Admin has logged in successfully" + "\u001B[0m");
            return 1;
        } else if (userExists(username)) {
            if (correctPassword(password)) {
                System.out.println("\u001B[32m" + "User logged in successfully." + "\u001B[0m");
                return 2;
            } else {
                System.out.println("Wrong password please try again: ");
                return 0;
            }
        } else {
            System.out.println("Username not found...");
            return 0;
        }
    }

    void userSignUp() throws IOException {
        if (username.equals("admin")) {
            System.out.println("Admin cannot sign up...");
        } else if (userExists(username)) {
            System.out.println("User with same username already exists");
        } else {
            user_list.add(this);

            File fp = new File("User.csv");
            FileWriter fw = new FileWriter(fp, true);
            ArrayList<String> data = new ArrayList<>();
            data.add(username);
            data.add(password);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < data.size(); i++) {
                sb.append(data.get(i));
                if (i < data.size() - 1) {
                    sb.append(",");
                }
            }
            fw.write(sb.toString());
            fw.write("\n");
            fw.close();
        }
    }

    static void deleteUser(String u_name) {
        for (Users user : user_list) {
            if (user.username.equals(u_name)) {
                user_list.remove(user);
                System.out.println("User deleted successfully.");
                return;
            }
        }
        System.out.println("User not found.");
    }

    static void displayUsers() {
        int size = user_list.size();
        for (Users users : user_list) {
            System.out.println(users.username);
        }
    }

    boolean isAdmin(String u_name) {
        for (Users user : user_list) {
            if (user.username.equals("admin") && user.password.equals("password")) {
                return true;
            }
        }
        return false;
    }

    private boolean userExists(String u_name) {
        for (Users user : user_list) {
            if (Objects.equals(user.username, u_name)) {
                return true;
            }
        }
        return false;
    }

    private boolean correctPassword(String pass) {
        for (Users user : user_list) {
            if (user.password.equals(pass)) {
                return true;
            }
        }
        return false;
    }

    static int addFavLocation(String loc, String user) {
        ArrayList<Location> locList = Location.getLocList();
        for (Location l : locList) {
            if (l.locationName.equals(loc)) {
                for (Users u : user_list) {
                    if (u.username.equals(user)) {
                        u.favLoc.add(l);
                        return 1;
                    }
                }
            }
        }
        return -1;
    }

    static void showFavLoc(String user) {
        boolean flag = true;
        for (Users u : user_list) {
            if (u.username.equals(user)) {
                for (Location l : u.favLoc) {
                    System.out.println(l.locationName);
                    flag = false;

                }
                // if(isEmpty) System.out.println("The user has not added any location to their
                // favourite List.");
            }
        }
        if (flag) {
            System.out.println("Your favourite list is empty.");
        }
    }

    static int addFavHotels(String hname, String user) {
        ArrayList<Hotels> hotelList = Hotels.getHotelList();
        for (Hotels h : hotelList) {
            if (h.hotelName.equals(hname)) {
                for (Users u : user_list) {
                    if (u.username.equals(user)) {
                        u.favHotels.add(h);
                        return 1;
                    }
                }
            }
        }
        return -1;
    }

    static void showFavHotels(String user) {
        boolean b = true;
        for (Users u : user_list) {
            if (u.username.equals(user)) {
                for (Hotels h : u.favHotels) {
                    System.out.println(h.hotelName);
                    b = false;
                }

            }
        }
        if (b) {
            System.out.println("Your favourite list is empty.");
        }
    }

    static int addFavRest(String name, String user) {
        ArrayList<Restaurant> restList = Restaurant.getRestList();
        for (Restaurant r : restList) {
            if (r.rname.equals(name)) {
                for (Users u : user_list) {
                    if (u.username.equals(user)) {
                        u.favRest.add(r);
                        return 1;
                    }
                }
            }
        }
        return -1;
    }

    static void showFavRest(String user) {
        boolean b = true;
        for (Users u : user_list) {
            if (u.username.equals(user)) {
                for (Restaurant r : u.favRest) {
                    System.out.println(r.rname);
                    b = false;
                }

            }
        }
        if (b) {
            System.out.println("Your favourite list in empty.");
        }
    }

    ArrayList<Location> getFavLoc() {
        return this.favLoc;
    }

    ArrayList<Hotels> getFavHotel() {
        return this.favHotels;
    }

    ArrayList<Restaurant> getFavRest() {
        return this.favRest;
    }
}

// Class representing cities
class Cities {
    // Instance variables to store city name and direction
    String cityName;
    String direction;

    // Constructor to initialize city name and direction
    public Cities(String name, String dir) {
        this.cityName = name;
        this.direction = dir;
    }

    // Method to display city information
    void showCity() {
        System.out.println("The city is " + cityName + " in the " + direction + " direction of India");
    }
}

// Class representing locations, inherits from Cities
class Location extends Cities implements Data_Structure {
    // Static ArrayList to hold the list of locations
    static ArrayList<Location> locList = new ArrayList<>();
    // Instance variables for location name, rating, and ticket availability
    String locationName;
    double rating;
    boolean isTicket;

    // Constructor to initialize location details
    public Location(String name, String dir, String loc, double rate, boolean tic) {
        // Calling the constructor of the super class Cities
        super(name, dir);
        // Initializing instance variables
        this.locationName = loc;
        this.rating = rate;
        this.isTicket = tic;
    }

    // Static method to provide the list of locations
    static ArrayList<Location> getLocList() {
        return locList;
    }

    // Static method to display locations in a specific city
    public void showItem(String city) {
        for (Location loc : locList) {
            if (loc.cityName.equals(city)) {
                System.out.println(loc.locationName);
            }
        }
    }
    public Location getItem(String city){
        for (Location loc : locList) {
            if (loc.locationName.equals(city)) {
                return loc;
            }
        }
        return null;
    }

    // Static method to add a new location
    static void addLocation(String name, String dir, String loc, double rate, boolean tic) {
        // Creating a new Location object
        Location l = new Location(name, dir, loc, rate, tic);
        // Setting location details
        l.cityName = name;
        l.direction = dir;
        l.locationName = loc;
        l.rating = rate;
        l.isTicket = tic;
        // Adding the location to the list
        locList.add(l);
    }

    // Static method to display rating of a location
    public double showRating(String l) {
        for (Location loc : locList) {
            if (l.equals(loc.locationName)) {
                return loc.rating;
            }
        }
        return -1;// Return -1 if location not found
    }

    // Static method to provide rating for a location
    public int giveRating(String loc, double r) {
        for (Location l : locList) {
            if (loc.equals(l.locationName)) {
                // Calculating new rating by averaging old rating and provided rating
                l.rating = (l.rating + r) / 2;
                return 1; // Return 1 if rating given successfully
            }
        }
        return 0; // Return 0 if location not found
    }

    // Method to check if ticket is required for the location
    boolean isTicket() {
        return isTicket;
    }
}

// Class representing Hotels, extending from Cities
class Hotels extends Cities implements Data_Structure {
    // Static list to hold all hotel instances
    static ArrayList<Hotels> hotelList = new ArrayList<>();
    // Attributes of Hotels
    String hotelName;
    int star;
    int price;
    double rating;

    // Constructor
    public Hotels(String name, String dir, int star, String hname, int price, double rate) {
        super(name, dir);// Call to super class constructor (Cities)
        this.star = star;
        this.hotelName = hname;
        this.price = price;
        this.rating = rate;
    }

    // Method to add a hotel to the hotelList
    static void addHotel(String name, String dir, int star, String hname, int price, double rate) {
        Hotels h = new Hotels(name, dir, star, hname, price, rate);
        hotelList.add(h);
    }

    // Method to display hotels in a given city
    public void showItem(String city) {
        for (Hotels h : hotelList) {
            if (h.cityName.equals(city)) {
                System.out.println(h.hotelName);
            }
        }
    }

    // Method to retrieve rating of a given hotel
    public double showRating(String hotel) {
        for (Hotels h : hotelList) {
            if (hotel.equals(h.hotelName)) {
                return h.rating;
            }
        }
        return -1;
    }

    // Method to update rating of a hotel
    public int giveRating(String loc, double r) {
        for (Hotels h : hotelList) {
            if (loc.equals(h.hotelName)) {
                h.rating = (h.rating + r) / 2;
                return 1;
            }
        }
        return 0;// If hotel not found
    }

    // Method to find hotels within a price range in a given city
    static void findHotelPrice() {
        Scanner sc = new Scanner(System.in);
        int price;
        System.out.print("Enter the price upto which you want to stay in hotel: ");
        price = sc.nextInt();
        System.out.print("Enter the city in which you want to find hotel: ");
        sc.nextLine();
        String cityName = sc.nextLine();
        for (Hotels h : hotelList) {
            if (h.price <= price && h.cityName.equals(cityName)) {
                System.out.println(h.hotelName + " - " + h.price + " Rs.");
            }
        }
    }

    // Method to find hotels with a certain star rating or above in a given city
    static void findHotelStar() {
        Scanner sc = new Scanner(System.in);
        int star;
        System.out.print("Enter in which star or above hotel you want to stay: ");
        star = sc.nextInt();
        System.out.print("Enter the city in which you want to find hotel: ");
        sc.nextLine();
        String cityName = sc.nextLine();
        for (Hotels h : hotelList) {
            if (h.star >= star && h.cityName.equals(cityName)) {
                System.out.println(h.hotelName + " - " + h.star + "*");
            }
        }
    }

    // Method to find hotels with a certain rating or above in a given city
    static void findHotelRating() {
        Scanner sc = new Scanner(System.in);
        float rating;
        System.out.print("Enter which rating onwards you want to find hotel: ");
        rating = sc.nextFloat();
        System.out.print("Enter the city in which you want to find hotel: ");
        sc.nextLine();
        String cityName = sc.nextLine();
        for (Hotels h : hotelList) {
            if (h.rating >= rating && h.cityName.equals(cityName)) {
                System.out.println(h.hotelName + " - " + h.rating);
            }
        }
    }
    // Method to retrieve the hotel list
    static ArrayList<Hotels> getHotelList() {
        return hotelList;
    }
}

// Class representing Restaurants, extending from Cities
class Restaurant extends Cities implements Data_Structure {
    // Static list to hold all restaurant instances
    static ArrayList<Restaurant> rest = new ArrayList<>();
    // Attributes of Restaurants
    String rname;
    double rating;
    boolean isPureVeg;
    double dist_from_center;

    // Constructor
    public Restaurant(String name, String dir, String rname, double rate, boolean b, double dist) {
        super(name, dir);// Call to super class constructor (Cities)
        this.rname = rname;
        this.rating = rate;
        this.isPureVeg = b;
        this.dist_from_center = dist;
    }

    // Method to add a restaurant to the rest list
    static void addRest(String name, String dir, String rname, double rate, boolean b, double dist) {
        Restaurant r = new Restaurant(name, dir, rname, rate, b, dist);
        rest.add(r);
    }

    // Method to display restaurants in a given city
    public void showItem(String city) {
        for (Restaurant r : rest) {
            if (r.cityName.equals(city)) {
                System.out.println(r.rname);
            }
        }
    }

    // Method to update rating of a restaurant
    public int giveRating(String r, double rate) {
        for (Restaurant rest : rest) {
            if (r.equals(rest.rname)) {
                rest.rating = (rest.rating + rate) / 2;
                return 1;
            }
        }
        return 0;
    }

    // Method to retrieve the restaurant list
    static ArrayList<Restaurant> getRestList() {
        return rest;
    }

    // Method to retrieve rating of a given restaurant
    public double showRating(String r) {
        for (Restaurant rest : rest) {
            if (r.equals(rest.rname)) {
                return rest.rating;
            }
        }
        return -1;// If restaurant not found
    }

    // Method to find restaurants within a certain distance in a given city
    static void findResDist() {
        // ArrayList<Restaurant> rest = new ArrayList<>();
        // rest =Restaurant.getRestList();
        Scanner sc = new Scanner(System.in);
        double dist;
        System.out.print("Enter the km upto which you want to find a restaurant: ");
        dist = sc.nextDouble();
        System.out.print("Enter the city in which you want to find restaurant: ");
        sc.nextLine();
        String cityName = sc.nextLine();
        for (Restaurant r : rest) {
            if (r.dist_from_center <= dist && r.cityName.equals(cityName)) {
                System.out.println(r.rname + " - " + r.dist_from_center + " kms");
            }
        }
    }

    // Method to find restaurants based on whether they are vegetarian or
    // non-vegetarian in a given city
    static void findRestIsVeg() {
        Scanner sc = new Scanner(System.in);
        boolean isVeg;
        System.out.print("Do you want to find Vegeterian restaurant?(True/False)");
        isVeg = sc.nextBoolean();
        System.out.print("Enter the city in which you want to find restaurant: ");
        sc.nextLine();
        String cityName = sc.nextLine();
        if ((isVeg)) {
            System.out.println("Vegeterian Hotels are: ");
        } else {
            System.out.println("Non Vegeterian Hotels are: ");
        }
        for (Restaurant r : rest) {
            if (r.cityName.equals(cityName)) {
                if (r.isPureVeg && isVeg) {
                    System.out.println(r.rname);
                } else if (!(r.isPureVeg || isVeg)) {
                    System.out.println(r.rname);
                }
            }
        }
    }

    // Method to find restaurants with a certain rating or above in a given city
    static void findRestRating() {
        Scanner sc = new Scanner(System.in);
        double rating;
        System.out.print("Enter the rating above which you want to find hotel: ");
        rating = sc.nextDouble();
        System.out.print("Enter the city in which you want to find restaurant: ");
        sc.nextLine();
        String cityName = sc.nextLine();
        for (Restaurant r : rest) {
            if (r.rating >= rating && r.cityName.equals(cityName)) {
                System.out.println(r.rname + " - " + r.rating);
            }
        }
    }
}

// Class representing the admin functionalities
class admin {
    static void admin2() throws FileNotFoundException {
        try {
            File fp = new File("User.csv");
            if (!fp.exists()) {
                fp.createNewFile(); // Create the file if it doesn't exist
                System.out.println("File created successfully.");
            }
            Scanner read = new Scanner(fp);
            String u_name;
            String psw;
            while (read.hasNextLine()) {
                String s = read.nextLine();
                String[] arr = s.split(",", 2);
                u_name = arr[0];
                psw = arr[1];
                Users newUser = new Users(u_name, psw);
                Users.user_list.add(newUser);
            }
            read.close();
        } catch (IOException e) {
            System.out.println("Cannot found path\n" + e.toString());
            System.exit(0);
        }
    }

    // Method to read location data from the "location.csv" file and populate the
    // Location list,
    // and read hotel data from the "Hotel.csv" file and populate the Hotels list,
    // and read restaurant data from the "Restaurant.csv" file and populate the
    // Restaurant list
    static void admin1() throws FileNotFoundException {
        File fp = new File("location.csv");
        Scanner read = null;
        String city, dir, name;
        double rate;
        boolean t;
        if(fp.exists()){
            read = new Scanner(fp);
            while (read.hasNextLine()) {
                String s = read.nextLine();
                if (s.trim().isEmpty()) // Skip empty lines
                    continue;
                String[] arr = s.split(",");
                if (arr.length < 5) {
                    System.out.println("Invalid input format: " + s);
                    continue;
                }
                city = arr[0];
                dir = arr[1];
                name = arr[2];
                try {
                    rate = Double.parseDouble(arr[3]);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid rate format: " + arr[3]);
                    continue;
                }
                t = Boolean.parseBoolean(arr[4]);
                Location.addLocation(city, dir, name, rate, t);
            }
        }else{
            System.out.println("File named location.csv not found.");
            System.exit(0);
        }
        if(read!=null){
            read.close(); // Close the scanner
        }

        fp = new File("Hotel.csv");
        int price, star;
        if(fp.exists()){
            read = new Scanner(fp);
            while (read.hasNextLine()) {
                String s = read.nextLine();
                if (s.equals(",,,,,"))
                    continue;
                String[] arr = s.split(",", 6);
                city = arr[0];
                dir = arr[1];
                star = Integer.parseInt(arr[2]);
                name = arr[3];
                price = Integer.parseInt(arr[4]);
                rate = Double.parseDouble(arr[5]);
                Hotels.addHotel(city, dir, star, name, price, rate);
            }
        }else{
            System.out.println("File named Hotel.csv not found.");
            System.exit(0);
        }
        if(read!=null){
            read.close();
        }
        fp = new File("Restaurant.csv");
        double dist;
        if(fp.exists()){
            read = new Scanner(fp);
            while (read.hasNextLine()) {
                String s = read.nextLine();
                if (s.equals(",,,,,") || s.isEmpty())
                    continue;
                String[] arr = s.split(",", 6);
                city = arr[0];
                dir = arr[1];
                name = arr[2];
                rate = Double.parseDouble(arr[3]);
                t = Boolean.parseBoolean((arr[4]));
                dist = Double.parseDouble(arr[5]);
                Restaurant.addRest(city, dir, name, rate, t, dist);
            }
        }else{
            System.out.println("File names Restaurant.csv npt found.");
            System.exit(0);
        }
        if(read!=null){
            read.close();
        }
    }

    // Method to update the "location.csv" file with the latest location data
    static void updateLocation() throws IOException {
        ArrayList<Location> loclist;
        loclist = Location.getLocList();

        try (PrintWriter pw = new PrintWriter(new File("location.csv"))) {
            for (Location l : loclist) {
                StringJoiner joiner = new StringJoiner(",");
                joiner.add(l.cityName)
                        .add(l.direction)
                        .add(l.locationName).add(String.valueOf(l.rating)).add(String.valueOf(l.isTicket));
                pw.println(joiner.toString());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    // Method to update the "Hotel.csv" file with the latest hotel data
    static void updateHotel() throws IOException {
        ArrayList<Hotels> hotellist;
        hotellist = Hotels.getHotelList();

        try (PrintWriter pw = new PrintWriter(new File("Hotel.csv"))) {
            for (Hotels l : hotellist) {
                StringJoiner joiner = new StringJoiner(",");
                joiner.add(l.cityName)
                        .add(l.direction).add(String.valueOf(l.star)).add(l.hotelName)
                        .add(String.valueOf(l.price)).add(String.valueOf(l.rating));
                pw.println(joiner.toString());
            }
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Method to update the "Restaurant.csv" file with the latest restaurant data
    static void updateRest() throws IOException {
        ArrayList<Restaurant> restlist;
        restlist = Restaurant.getRestList();

        try (PrintWriter pw = new PrintWriter(new File("Restaurant.csv"))) {
            for (Restaurant l : restlist) {
                StringJoiner joiner = new StringJoiner(",");
                joiner.add(l.cityName)
                        .add(l.direction).add(l.rname)
                        .add(String.valueOf(l.rating)).add(String.valueOf(l.isPureVeg))
                        .add(String.valueOf(l.dist_from_center));
                pw.println(joiner.toString());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}

public class Main {
    // Main class representing the entry point of the application
    public static void main(String[] args) throws IOException {
        Restaurant dummyRest = new Restaurant(" ", " ", " ", 0.0, Boolean.parseBoolean("True"), 0.0);
        Hotels dummyHotel = new Hotels(" ", " ", 0, " ", 0, 0.0);
        Location dummyOb = new Location(" "," "," ",0.0, Boolean.parseBoolean("True"));

        // Initialize user data and location data from CSV files
        admin.admin2();
        System.out.println("Here");
        admin.admin1();
        // ANSI escape codes for color formatting
        String Reset = "\u001B[0m";
        String Red = "\u001B[31m";
        String Green = "\u001B[32m";
        String Yellow = "\u001B[33m";
        String Blue = "\u001B[34m";
        String Purple = "\u001B[35m";
        String Cyan = "\u001B[36m";
        String White = "\u001B[37m";
        // Scanner for user input
        Scanner sc = new Scanner(System.in);
        int choice;
        // Display welcome message and menu options
        System.out.println(Red + "Welcome to Trip Advisor..." + Reset);
        System.out.println(Green + "You can create user or login..." + Reset);
        // Main menu loop
        do {
            System.out.println(Cyan + "0. Sign Up User\n1. Login User\n" + Red + "2. Exit" + Reset);
            System.out.println(Yellow + "Enter your choice: " + Reset);
            try {
                choice = sc.nextInt();
                String u_name, password;
                Users newUser;
                switch (choice) {
                    case 0:
                        System.out.print(Yellow + "Enter username: " + Reset);
                        u_name = sc.next();
                        System.out.print(Yellow + "Enter password: " + Reset);
                        password = sc.next();
                        newUser = new Users(u_name, password);
                        newUser.userSignUp();
                        break;
                    case 1:
                        System.out.print(Yellow + "Enter username: " + Reset);
                        u_name = sc.next();
                        System.out.print(Yellow + "Enter password: " + Reset);
                        password = sc.next();
                        newUser = new Users(u_name, password);
                        int logIn = newUser.userLogin();
                        if (logIn == 1) {
                            int c;
                            boolean flag = true;
                            do {
                                System.out.println(Blue + "1. To display Users.\n2. To delete Users\n3. Exit" + Reset);
                                try {
                                    c = sc.nextInt();
                                    switch (c) {
                                        case 1:
                                            Users.displayUsers();
                                            break;
                                        case 2:
                                            System.out.print(Yellow + "Enter username to delete: " + Reset);
                                            String deleteUsername = sc.next();
                                            Users.deleteUser(deleteUsername);
                                            break;
                                        case 3:
                                            try (PrintWriter pw = new PrintWriter(new File("User.csv"))) {
                                                for (Users l : Users.user_list) {
                                                    StringJoiner joiner = new StringJoiner(",");
                                                    joiner.add(l.username)
                                                            .add(l.getPassword());
                                                    pw.println(joiner.toString());
                                                }
                                            } catch (FileNotFoundException e) {
                                                e.printStackTrace();
                                            }
                                            flag = false;
                                            break;
                                    }
                                } catch (InputMismatchException e) {
                                    c = 3;
                                    System.out.println("Please enter valid input.");
                                    sc.nextLine();
                                }
                            } while (flag);

                        } else if (logIn == 2) {// User logged in as a regular user
                            // Regular user-specific menu options
                            int userChoice;
                            boolean flag = true;
                            // Display regular user menu options
                            do {
                                System.out.println(Cyan
                                        + "1. Adding Favourite Location\n2. Adding Favourite Hotel\n3. Adding Favourite Restaurants\n4. Show Favourite Location\n5. Show Favourite Hotels\n6. Show Favourite Restaurants\n7. To give Ratings\n8. To find Hotels\n9. To find Restaurants. \n10. Give me suggestions.\n11. To know about Location\n12. Logout"
                                        + Reset);
                                System.out.println(Red + "Enter your choice: " + Reset);
                                try {
                                    userChoice = sc.nextInt();
                                    sc.nextLine();
                                    switch (userChoice) {
                                        // Switch cases for various user operations
                                        case 1:// Add Favorite Location
                                               // Prompt user for location and add to favorites
                                            System.out.print("Enter the Location to be added: ");
                                            String loc = sc.nextLine();
                                            int checkloc = Users.addFavLocation(loc, u_name);
                                            if (checkloc == 1) {
                                                System.out.println("Location has been added to your Favourite");
                                            } else {
                                                // If location not found, prompt user to add it to the database
                                                System.out.println("Sorry the entered Location does not exist");
                                                System.out.println(
                                                        "Do you want to add location to our data?(" + Green + "y"
                                                                + Reset + "/" + Red + "n" + Reset + ")");
                                                char ans = sc.next().charAt(0);
                                                if (ans == 'y') {
                                                    int c;
                                                    boolean isTicket;
                                                    System.out.print(
                                                            "Enter the name of the city in which this is located: ");
                                                    sc.nextLine();
                                                    String cityName = sc.nextLine();
                                                    System.out.print("Enter the direction of the city: ");
                                                    String dir = sc.nextLine();
                                                    System.out.print(
                                                            "Enter whether ticket is required or not(1 for yes/0 for no): ");
                                                    c = sc.nextInt();
                                                    if (c == 1) {
                                                        isTicket = true;
                                                    } else {
                                                        isTicket = false;
                                                    }
                                                    Location.addLocation(cityName, dir, loc, 0, isTicket);
                                                    System.out.println(
                                                            "Your given location has been added " + Green
                                                                    + "successfully"
                                                                    + Reset
                                                                    + "...Now you can add it as your favourite place");
                                                }
                                            }
                                            break;
                                        case 2:
                                            // User wants to add a hotel to their favorites
                                            System.out.print("Enter the Hotel to be added: ");
                                            String hotel = sc.nextLine();
                                            // Check if the hotel exists in the database
                                            int checkhotel = Users.addFavHotels(hotel, u_name);
                                            if (checkhotel == 1) {
                                                // Hotel added successfully
                                                System.out.println("Hotel has been added to your Favourite");
                                            } else {
                                                // Hotel doesn't exist in the database
                                                System.out.println("Sorry the entered Hotel does not exist");
                                                // Prompt user to add the hotel to the database
                                                System.out
                                                        .println("Do you want to add hotel to our data?(" + Green + "y"
                                                                + Reset + "/" + Red + "n" + Reset + ")");
                                                char ans = sc.next().charAt(0);
                                                if (ans == 'y') {
                                                    // User wants to add the hotel to the database
                                                    int c;
                                                    System.out.print(
                                                            "Enter the name of the city in which this is located: ");
                                                    sc.nextLine();
                                                    String cityName = sc.nextLine();
                                                    System.out.print("Enter the direction of the city: ");
                                                    String dir = sc.nextLine();
                                                    System.out.print("Enter which star Hotel it is: ");
                                                    int star = sc.nextInt();
                                                    System.out.print("Enter the price of that Hotel: ");
                                                    int price = sc.nextInt();
                                                    // Add the hotel to the database
                                                    Hotels.addHotel(cityName, dir, star, hotel, price, 0);
                                                    System.out
                                                            .println("Your given hotel has been added " + Green
                                                                    + "successfully"
                                                                    + Reset
                                                                    + "...Now you can add it as your favourite place");
                                                }
                                            }
                                            break;
                                        case 3:
                                            // User wants to add a restaurant to their favorites
                                            System.out.print("Enter the Restaurant to be added: ");
                                            String restaurant = sc.nextLine();
                                            // Check if the restaurant exists in the database
                                            int checkrest = Users.addFavRest(restaurant, u_name);
                                            if (checkrest == 1) {
                                                // Restaurant added successfully
                                                System.out.println("Restaurant has been added to your Favourite");
                                            } else {
                                                // Restaurant doesn't exist in the database
                                                System.out.println("Sorry the entered Restaurant does not exist");
                                                // Prompt user to add the restaurant to the database
                                                System.out.println(
                                                        "Do you want to add restaurant to our data?(" + Green + "y"
                                                                + Reset + "/" + Red + "n" + Reset + ")");
                                                char ans = sc.next().charAt(0);
                                                if (ans == 'y') {
                                                    // User wants to add the restaurant to the database
                                                    int c;
                                                    System.out.print(
                                                            "Enter the name of the city in which this is located: ");
                                                    sc.nextLine();
                                                    String cityName = sc.nextLine();
                                                    System.out.print("Enter the direction of the city: ");
                                                    String dir = sc.nextLine();
                                                    System.out.print("Is the restaurant " + Green + "pure veg" + Reset
                                                            + "? 1 for Yes/ 0 for No");
                                                    c = sc.nextInt();
                                                    boolean b;
                                                    if (c == 1) {
                                                        b = true;
                                                    } else {
                                                        b = false;
                                                    }
                                                    System.out.println(
                                                            "What is the distance of that restaurant from centre: ");
                                                    double dist = sc.nextDouble();
                                                    // Add the restaurant to the database
                                                    Restaurant.addRest(cityName, dir, restaurant, 0, b, dist);
                                                    System.out.println(
                                                            "Your given restaurant has been added " + Green
                                                                    + "successfully"
                                                                    + Reset
                                                                    + "...Now you can add it as your favourite place");
                                                }
                                            }
                                            break;
                                        case 4:
                                            Users.showFavLoc(u_name);
                                            break;
                                        case 5:
                                            Users.showFavHotels(u_name);
                                            break;
                                        case 6:
                                            Users.showFavRest(u_name);
                                            break;
                                        case 7:
                                            // User wants to rate locations, hotels, or restaurants
                                            int ratechoice;
                                            boolean f = true;
                                            do {
                                                // Display rating options
                                                System.out.println(
                                                        Green + "1. Location \n2. Hotels\n3. Restaurants\n4. Exit"
                                                                + Reset);
                                                try {
                                                    ratechoice = sc.nextInt();
                                                    sc.nextLine();
                                                    switch (ratechoice) {
                                                        case 1:
                                                            // User wants to rate a location
                                                            System.out.print("Enter the name of Location: ");
                                                            String l = sc.nextLine();
                                                            System.out.println(
                                                                    "Give the rating for that location (out of 5)");
                                                            double rl;
                                                            rl = sc.nextDouble();
                                                            if (rl < 0 || rl > 5) {
                                                                // Ensure rating is between 0 and 5
                                                                System.out
                                                                        .println(
                                                                                "Please give appropriate rating between 0 to 5.");
                                                                continue;
                                                            }
                                                            if (dummyOb.giveRating(l, rl) == 1) {
                                                                // Location rating updated successfully
                                                                System.out.println(
                                                                        "You have " + Green + "successfully " + Reset
                                                                                + "given rating for " + l + " .");
                                                            } else {
                                                                // Location does not exist in the database
                                                                System.out.println(
                                                                        "The location named " + l
                                                                                + " do not exist in our data.");
                                                            }
                                                            break;
                                                        case 2:
                                                            // User wants to add a hotel to their favorites
                                                            System.out.print("Enter the name of Hotel: ");
                                                            String h = sc.nextLine();
                                                            System.out.println(
                                                                    "Give the rating for that hotel (out of 5)");
                                                            double rh = sc.nextDouble();
                                                            if (rh < 0 || rh > 5) {
                                                                // Ensure rating is between 0 and 5
                                                                System.out
                                                                        .println(
                                                                                "Please give appropriate rating between 0 to 5.");
                                                                continue;
                                                            }
                                                            if (dummyHotel.giveRating(h, rh) == 1) {
                                                                // Hotel rating updated successfully
                                                                System.out.println(
                                                                        "You have " + Green + "successfully " + Reset
                                                                                + "given rating for " + h + " .");
                                                                System.out.println(dummyHotel.showRating(h));
                                                            } else {
                                                                // Hotel does not exist in the database
                                                                System.out.println(
                                                                        "The hotel named " + h
                                                                                + " do not exist in out data.");
                                                            }
                                                            break;
                                                        case 3:
                                                            // User wants to rate a restaurant
                                                            System.out.print("Enter the name of Restaurant: ");
                                                            String r = sc.nextLine();
                                                            System.out.println(
                                                                    "Give the rating for that hotel (out of 5)");
                                                            double rr = sc.nextDouble();
                                                            if (rr < 0 || rr > 5) {
                                                                // Ensure rating is between 0 and 5
                                                                System.out
                                                                        .println(
                                                                                "Please give appropriate rating between 0 to 5.");
                                                                continue;
                                                            }
                                                            if (dummyRest.giveRating(r, rr) == 1) {
                                                                // Restaurant rating updated successfully
                                                                System.out.println(
                                                                        "You have " + Green + "successfully " + Reset
                                                                                + "given rating for " + r + " .");
                                                                System.out.println(dummyRest.showRating(r));
                                                            } else {
                                                                // Restaurant does not exist in the database
                                                                System.out.println(
                                                                        "The location named " + r
                                                                                + " do not exist in out data.");
                                                            }
                                                            break;
                                                        case 4:
                                                            f = false;// Exit the rating loop
                                                    }
                                                } catch (InputMismatchException e) {
                                                    ratechoice = 5;
                                                    System.out.println("Please enter valid input.");
                                                    sc.nextLine();
                                                }
                                            } while (f);
                                            break;
                                        case 8:
                                            // User wants to find hotels based on various criteria
                                            boolean b = true;
                                            do {
                                                // Display hotel search options
                                                int chooseFindHotel;
                                                System.out.println(Green
                                                        + "1. Find by price\n2. Find by star of hotel\n3. Find by rating\n4. Exit"
                                                        + Reset);
                                                try {
                                                    chooseFindHotel = sc.nextInt();
                                                    switch (chooseFindHotel) {
                                                        case 1:
                                                            Hotels.findHotelPrice();// Search hotels by price
                                                            break;
                                                        case 2:
                                                            Hotels.findHotelStar();// Search hotels by star rating
                                                            break;
                                                        case 3:
                                                            Hotels.findHotelRating();// Search restaurants by user
                                                                                     // rating
                                                            break;
                                                        case 4:
                                                            b = false;// Exit the hotel search loop
                                                    }
                                                } catch (InputMismatchException e) {
                                                    chooseFindHotel = 10;
                                                    System.out.println("Please enter valid input.");
                                                    sc.nextLine();
                                                }
                                            } while (b);
                                            break;
                                        case 9:
                                            // User wants to find restaurants based on various criteria
                                            boolean c = true;
                                            do {
                                                try {
                                                    // Display restaurant search options
                                                    System.out.println(Purple
                                                            + "1. Find by distance\n2. Find by vegeterian hotel\n3. Find by rating\n4. Exit"
                                                            + Reset);
                                                    int chooseFindRest = sc.nextInt();
                                                    switch (chooseFindRest) {
                                                        case 1:
                                                            Restaurant.findResDist();// Search restaurants by distance
                                                            break;
                                                        case 2:
                                                            Restaurant.findRestIsVeg();// Search vegetarian restaurants
                                                            break;
                                                        case 3:
                                                            Restaurant.findRestRating();// Search restaurants by user
                                                                                        // rating
                                                            break;
                                                        case 4:
                                                            c = false;// Exit the restaurant search loop
                                                    }
                                                } catch (InputMismatchException e) {
                                                    System.out.println("Invalid input. Please enter a number.");
                                                    sc.nextLine(); // Clear the input buffer
                                                }
                                            } while (c);
                                            break;
                                        case 10:
                                            int chooseSuggestion;
                                            boolean mark = true;
                                            do {
                                                try {
                                                    System.out.println("1. Places\n2. Hotels\n3. Restaurants\n4. Exit");
                                                    chooseSuggestion = sc.nextInt();

                                                    switch (chooseSuggestion) {
                                                        case 1:
                                                            System.out.println("Enter the name of the city: ");
                                                            sc.nextLine(); // Consume newline character
                                                            String city1 = sc.nextLine();
                                                            dummyOb.showItem(city1);
                                                            break;

                                                        case 2:
                                                            System.out.println("Enter the name of the city: ");
                                                            sc.nextLine(); // Consume newline character
                                                            String city2 = sc.nextLine();
                                                            dummyHotel.showItem(city2);
                                                            break;
                                                        case 3:
                                                            System.out.println("Enter the name of the city: ");
                                                            sc.nextLine(); // Consume newline character
                                                            String city3 = sc.nextLine();
                                                            dummyRest.showItem(city3);
                                                            break;

                                                        case 4:
                                                            flag = false;
                                                            break;

                                                        default:
                                                            System.out.println(
                                                                    "Invalid choice. Please choose from 1 to 4.");
                                                    }
                                                } catch (InputMismatchException e) {
                                                    System.out.println("Invalid input. Please enter a number.");
                                                    sc.nextLine(); // Clear the input buffer
                                                }

                                            } while (flag);
                                            break;
                                        case 11:
                                        int chooseOption;
                                        boolean Locbool = true;
                                        do{
                                            System.out.println("1. For all cities\n2. For locations in a particular city\n3. To know more about a Location\n4. Exit");
                                            System.out.println("Enter choice");
                                            chooseOption = sc.nextInt();
                                            sc.nextLine();
                                                switch (chooseOption) {
                                                    case 1:
                                                        System.out.println("Enter the rating above which you want to find locations");
                                                        double locRating = sc.nextDouble();
                                                        System.out.println("Filtered locations are:");
                                                        for(Location l : Location.getLocList()){
                                                            if(l.rating >= locRating){
                                                                System.out.println(l.locationName);
                                                            }
                                                        }
                                                        break;
                                                    case 2:
                                                        System.out.println("Enter the city name: ");
                                                        
                                                        String cname = sc.nextLine();
                                                        System.out.println("Enter the rating above which you want to find locations");
                                                        locRating = sc.nextDouble();
                                                        System.out.println("Filtered locations are:");
                                                        for(Location l : Location.getLocList()){
                                                            if(l.cityName.equals(cname) && l.rating>=locRating){
                                                                System.out.println(l.locationName);
                                                            }
                                                        }
                                                        break;
                                                    case 3:
                                                        System.out.println("Enter the name of Location: ");
                                                        String city_name = sc.nextLine();
                                                        Location newLoc = dummyOb.getItem(city_name);
                                                        if(newLoc!=null){
                                                            System.out.printf("%-20s %s\n", "Located in:", newLoc.cityName);
                                                            System.out.printf("%-20s %s\n", "Direction:", newLoc.direction);
                                                            System.out.printf("%-20s %s\n", "Location name:", newLoc.locationName);
                                                            System.out.printf("%-20s %s\n", "Rating:", dummyOb.showRating(city_name));
                                                            System.out.printf("%-20s %s\n", "Ticket required:", newLoc.isTicket());

                                                        }
                                                        else{
                                                            System.out.println("Sorry...Location does exist in our data.");
                                                        }
                                                        break;
                                                    case 4:
                                                        Locbool = false;
                                                        break;
                                                }
                                            }while(Locbool);
                                            break;
                                        case 12:
                                            // User wants to export favorites to a file
                                            System.out.println(Cyan
                                                    + "Do you want to export your favourites to a file?(1 for yes/ 0 for no)"
                                                    + Reset);
                                            int choose = sc.nextInt();
                                            if (choose == 1) {
                                                // Export favorites to a file
                                                String fileName = u_name + ".txt";
                                                File fp = new File(fileName);
                                                PrintWriter pw = new PrintWriter(fileName);
                                                for (Users u : Users.user_list) {
                                                    if (u.username.equals(u_name)) {
                                                        newUser = u;
                                                    }
                                                }
                                                pw.println("Favourite Locations:");
                                                pw.println();
                                                for (Location l : newUser.getFavLoc()) {
                                                    pw.println(l.locationName + "------->" + l.cityName);
                                                }
                                                pw.println();
                                                pw.println();
                                                pw.println("Favourite Hotels:");
                                                pw.println();
                                                for (Hotels h : newUser.getFavHotel()) {
                                                    pw.println(h.hotelName + "------->" + h.cityName);
                                                }
                                                pw.println();
                                                pw.println();
                                                pw.println("Favourite Restaurants:");
                                                pw.println();
                                                for (Restaurant r : newUser.getFavRest()) {
                                                    pw.println(r.rname + "------->" + r.cityName);
                                                }
                                                pw.close();
                                                System.out.println(
                                                        "File named " + fp.getName() + " is created successfully.");
                                            }
                                            flag = false;// Exit the loop
                                    }
                                } catch (InputMismatchException e) {
                                    userChoice = 3;
                                    System.out.println("Please enter valid input.");
                                    sc.nextLine();
                                }
                            } while (flag);
                        }
                        break;
                    case 2:
                        // Admin wants to update location, hotel, and restaurant data
                        admin.updateLocation();// Call method to update location
                        admin.updateHotel();// Call method to update hotel
                        admin.updateRest();// Call method to update restaurant
                        System.out.println();
                        System.out.println();
                        System.out.println(
                                "\u001B[1m" + "\u001B[31m" + "Exiting..." + "\u001B[36m" + "Thank You" + Reset);
                        break;
                }
            } catch (InputMismatchException e) {
                choice = 3;
                sc.nextLine();
                System.out.println("Please enter valid input.");
            }
        } while (choice != 2);
    }
}
