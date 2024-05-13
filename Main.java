import java.io.*;
import java.util.*;


class Users {
    public String userPath = "C:\\Users\\dhpar\\OneDrive - daiict.ac.in\\SEM 2\\MC 122\\MC122 Project\\User.csv";

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
    String getPassword(){
        return this.password;
    }
    static ArrayList<Users> giveUserList(){
        return user_list;
    }

    int userLogin() {
        if(username.equals("admin") && password.equals("admin")){
            System.out.println("Admin has logged in successfully");
            return 1;
        }
        else if(userExists(username)) {
            if(correctPassword(password)){
                System.out.println("User logged in successfully.");
                return 2;
            }
            else {
                System.out.println("Wrong password please try again: ");
                return 0;
            }
        }
        else{
            System.out.println("Username not found...");
            return 0;
        }
    }
    void userSignUp() throws IOException {
        if(username.equals("admin")){
            System.out.println("Admin cannot sign up...");
        }
        else if(userExists(username)){
            System.out.println("User with same username already exists");
        }
        else{
            user_list.add(this);

            File fp = new File("User.csv");
            FileWriter fw = new FileWriter(fp,true);
            ArrayList<String> data = new ArrayList<>();
            data.add(username);
            data.add(password);
            StringBuilder sb = new StringBuilder();
            for(int i=0;i<data.size();i++){
                sb.append(data.get(i));
                if(i < data.size()-1){
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
    private boolean isAdmin(String u_name) {
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
    static int addFavLocation(String loc,String user){
        ArrayList<Location> locList = Location.getLocList();
        for(Location l : locList){
            if(l.locationName.equals(loc)){
                System.out.println("Hello");
                for(Users u : user_list){
                    if(u.username.equals(user)){
                        u.favLoc.add(l);
                        return 1;
                    }
                }
            }
        }
        return -1;
    }
    static void showFavLoc(String user){
        boolean flag = true;
        for(Users u : user_list){
            if(u.username.equals(user)){
                for(Location l : u.favLoc){
                    System.out.println(l.locationName);
                    flag = false;
                    break;
                }
//                if(isEmpty) System.out.println("The user has not added any location to their favourite List.");
            }
        }
        if(flag){
            System.out.println("You have entered invalid username try again.");
        }
    }
    static int addFavHotels(String hname, String user){
        ArrayList<Hotels> hotelList = Hotels.getHotelList();
        for(Hotels h : hotelList){
            if(h.hotelName.equals(hname)) {
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
    static void showFavHotels(String user){
        boolean b = true;
        for(Users u : user_list){
            if(u.username.equals(user)){
                for(Hotels h : u.favHotels){
                    System.out.println(h.hotelName);
                    b = false;
                }

            }
        }
        if(b){
            System.out.println("You have entered invalid username try again.");
        }
    }
    static int addFavRest(String name, String user){
        ArrayList<Restaurant> restList = Restaurant.getRestList();
        for(Restaurant r : restList){
            if(r.rname.equals(name)){
                for(Users u : user_list){
                    if(u.username.equals(user)){
                        u.favRest.add(r);
                        return 1;
                    }
                }
            }
        }
        return -1;
    }
    static void showFavRest(String user){
        for(Users u : user_list){
            if(u.username.equals(user)){
                for(Restaurant r : u.favRest){
                    System.out.println(r.rname);
                    break;
                }

            }
            else{
                System.out.println("You have entered invalid username try again.");
            }
        }
    }
}
class Cities{
    String cityName;
    String direction;
    public Cities(String name, String dir){
        this.cityName = name;
        this.direction = dir;
    }
    void showCity(){
        System.out.println("The city is "+cityName+" in the "+direction+" direction of India");
    }
}
class Location extends Cities{
    static ArrayList<Location> locList = new ArrayList<>();
    String locationName;
    double rating;
    boolean isTicket;
    public Location(String name, String dir,String loc, double rate, boolean tic) {
        super(name, dir);
        this.locationName = loc;
        this.rating = rate;
        this.isTicket = tic;
    }
    static ArrayList<Location> getLocList(){
        return locList;
    }
    static void showLocations(String city){
        for(Location loc : locList){
            if(loc.cityName.equals(city)){
                System.out.println(loc.locationName);
            }
        }
    }
    static void addLocation(String name, String dir,String loc, double rate, boolean tic){
        Location l = new Location(name,dir,loc,rate,tic);
        l.cityName = name;
        l.direction = dir;
        l.locationName = loc;
        l.rating = rate;
        l.isTicket = tic;
        locList.add(l);
    }
    static double showRating(String l){
        for(Location loc : locList){
            if(l.equals(loc.locationName)){
                return loc.rating;
            }
        }
        return -1;
    }

    static int giveRating(String loc, double r){
        for(Location l : locList){
            if(loc.equals(l.locationName)){
                l.rating = (l.rating + r)/2;
                return 1;
            }
        }
        return 0;
    }

    boolean isTicket(){
        return isTicket;
    }
}
class Hotels extends Cities{
    static ArrayList<Hotels> hotelList = new ArrayList<>();
    String hotelName;
    int star;
    int price;
    double rating;
    public Hotels(String name, String dir, int star, String hname, int price,double rate) {
        super(name, dir);
        this.star = star;
        this.hotelName = hname;
        this.price = price;
        this.rating = rate;
    }
    static void addHotel(String name, String dir, int star, String hname, int price,double rate){
        Hotels h = new Hotels(name,dir, star,hname,price,rate);
        hotelList.add(h);
    }
    static void showHotel(String city){
        for(Hotels h : hotelList){
            if(h.cityName.equals(city)){
                System.out.println(h.hotelName);
            }
        }
    }
    static double showRating(String hotel){
        for(Hotels h : hotelList){
            if(hotel.equals(h.hotelName)){
                return h.rating;
            }
        }
        return -1;
    }
    static int giveRating(String loc, double r){
        for(Hotels h : hotelList){
            if(loc.equals(h.hotelName)){
                h.rating = (h.rating + r)/2;
                return 1;
            }
        }
        return 0;
    }
    static void findHotelPrice(){
        Scanner sc = new Scanner(System.in);
        int price;
        System.out.print("Enter the price upto which you want to stay in hotel: ");
        price = sc.nextInt();
        System.out.print("Enter the city in which you want to find hotel: ");
        sc.nextLine();
        String cityName = sc.nextLine();
        for(Hotels h : hotelList){
            if(h.price<=price && h.cityName.equals(cityName)){
                System.out.println(h.hotelName + " - " + h.price + " Rs.");
            }
        }
    }
    static void findHotelStar(){
        Scanner sc = new Scanner(System.in);
        int star;
        System.out.print("Enter in which star or above hotel you want to stay: ");
        star = sc.nextInt();
        System.out.print("Enter the city in which you want to find hotel: ");
        sc.nextLine();
        String cityName = sc.nextLine();
        for(Hotels h : hotelList){
            if(h.star>=star && h.cityName.equals(cityName)){
                System.out.println(h.hotelName + " - " + h.star + "*");
            }
        }
    }
    static void findHotelRating(){
        Scanner sc = new Scanner(System.in);
        float rating;
        System.out.print("Enter which rating onwards you want to find hotel: ");
        rating = sc.nextFloat();
        System.out.print("Enter the city in which you want to find hotel: ");
        sc.nextLine();
        String cityName = sc.nextLine();
        for(Hotels h : hotelList){
            if(h.rating>=rating && h.cityName.equals(cityName)){
                System.out.println(h.hotelName + " - " + h.rating);
            }
        }
    }

    static ArrayList<Hotels> getHotelList(){
        return hotelList;
    }
}
class Restaurant extends Cities{
    static ArrayList<Restaurant> rest = new ArrayList<>();
    String rname;
    double rating;
    boolean isPureVeg;
    double dist_from_center;
    public Restaurant(String name, String dir, String rname, double rate, boolean b, double dist) {
        super(name, dir);
        this.rname = rname;
        this.rating = rate;
        this.isPureVeg = b;
        this.dist_from_center = dist;
    }
    static void addRest(String name, String dir, String rname, double rate, boolean b, double dist){
        Restaurant r = new Restaurant(name,dir,rname,rate,b,dist);
        rest.add(r);
    }
    static void showRestaurants(String city){
        for(Restaurant r : rest){
            if(r.cityName.equals(city)){
                System.out.println(r.rname);
            }
        }
    }
    static int giveRating(String r, double rate){
        for(Restaurant rest : rest){
            if(r.equals(rest.rname)){
                rest.rating = (rest.rating + rate)/2;
                return 1;
            }
        }
        return 0;
    }
    static ArrayList<Restaurant> getRestList(){
        return rest;
    }

    static double showRating(String r) {
        for(Restaurant rest : rest){
            if(r.equals(rest.rname)){
                return rest.rating;
            }
        }
        return -1;
    }
    static void findResDist(){
//        ArrayList<Restaurant> rest = new ArrayList<>();
//        rest =Restaurant.getRestList();
        Scanner sc = new Scanner(System.in);
        double dist;
        System.out.print("Enter the km upto which you want to find a restaurant: ");
        dist = sc.nextDouble();
        System.out.print("Enter the city in which you want to find restaurant: ");
        sc.nextLine();
        String cityName = sc.nextLine();
        for(Restaurant r : rest){
            if(r.dist_from_center<=dist && r.cityName.equals(cityName)){
                System.out.println(r.rname + " - " + r.dist_from_center +" kms");
            }
        }
    }
    static void findRestIsVeg(){
        Scanner sc = new Scanner(System.in);
        boolean isVeg;
        System.out.print("Do you want to find Vegeterian restaurant?");
        isVeg = sc.nextBoolean();
        System.out.print("Enter the city in which you want to find restaurant: ");
        sc.nextLine();
        String cityName = sc.nextLine();
        if ((isVeg)) {
            System.out.println("Vegeterian Hotels are: ");
        } else {
            System.out.println("Non Vegeterian Hotels are: ");
        }
        for(Restaurant r : rest){
            if(r.cityName.equals(cityName)){
                if(r.isPureVeg && isVeg){
                    System.out.println(r.rname);
                }
                else if(!(r.isPureVeg || isVeg)){
                    System.out.println(r.rname);
                }
            }
        }
    }
    static void findRestRating(){
        Scanner sc = new Scanner(System.in);
        double rating;
        System.out.print("Enter the rating above which you want to find hotel: ");
        rating = sc.nextDouble();
        System.out.print("Enter the city in which you want to find restaurant: ");
        sc.nextLine();
        String cityName = sc.nextLine();
        for(Restaurant r : rest){
            if(r.rating>=rating && r.cityName.equals(cityName)){
                System.out.println(r.rname + " - " + r.rating);
            }
        }
    }
}
class admin{
    public static String userPath = "C:\\Users\\dhpar\\OneDrive - daiict.ac.in\\SEM 2\\MC 122\\MC122 Project\\User.csv";
    public static String locPath = "C:\\Users\\dhpar\\OneDrive - daiict.ac.in\\SEM 2\\MC 122\\MC122 Project\\location.csv";
    public static String hotelPath = "C:\\Users\\dhpar\\OneDrive - daiict.ac.in\\SEM 2\\MC 122\\MC122 Project\\Hotel.csv";
    public static String restPath = "C:\\Users\\dhpar\\OneDrive - daiict.ac.in\\SEM 2\\MC 122\\MC122 Project\\Restaurant.csv";
    static void admin2() throws FileNotFoundException{
        try{
            File fp = new File(userPath);
            Scanner read = new Scanner(fp);
            String u_name;
            String psw;
            while(read.hasNextLine()){
                String s = read.nextLine();
                String[] arr = s.split(",",2);
                u_name = arr[0];
                psw = arr[1];
                Users newUser = new Users(u_name,psw);
                Users.user_list.add(newUser);
            }
            read.close();
        }catch(FileNotFoundException e){
            System.out.println("Cannot found path\n"+e.toString());
            System.exit(0);
        }
    }
    static void admin1() throws FileNotFoundException {
        File fp = new File("location.csv");
        Scanner read = new Scanner(fp);
        String city, dir, name;
        double rate;
        boolean t;
        while(read.hasNextLine()){
            String s = read.nextLine();
            if(s.equals(",,,,,")) continue;
            String[] arr = s.split(",");
//            String[] arr = s.split(",");
            city = arr[0];
            dir = arr[1];
            name = arr[2];
            rate = Double.parseDouble(arr[3]);
            t = Boolean.parseBoolean(arr[4]);
            Location.addLocation(city,dir,name,rate,t);
        }
        read.close();
        fp = new File("Hotel.csv");
        read = new Scanner(fp);
        int price, star;
        while(read.hasNextLine()){
            String s = read.nextLine();
            if(s.equals(",,,,,")) continue;
            String[] arr = s.split(",",6);
            city = arr[0];
            dir = arr[1];
            star = Integer.parseInt(arr[2]);
            name = arr[3];
            price = Integer.parseInt(arr[4]);
            rate = Double.parseDouble(arr[5]);
            Hotels.addHotel(city,dir,star,name,price,rate);
        }
        read.close();
        fp = new File("Restaurant.csv");
        read = new Scanner(fp);
        double dist;
        while(read.hasNextLine()){
            String s = read.nextLine();
            if(s.equals(",,,,,") || s.isEmpty()) continue;
            String[] arr = s.split(",",6);
            city = arr[0];
            dir = arr[1];
            name = arr[2];
            rate = Double.parseDouble(arr[3]);
            t = Boolean.parseBoolean((arr[4]));
            dist = Double.parseDouble(arr[5]);
            Restaurant.addRest(city,dir,name,rate,t,dist);
        }
    }
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
    static void updateRest() throws IOException {
        ArrayList<Restaurant> restlist;
        restlist = Restaurant.getRestList();

        try (PrintWriter pw = new PrintWriter(new File("Restaurant.csv"))) {
            for (Restaurant l : restlist) {
                StringJoiner joiner = new StringJoiner(",");
                joiner.add(l.cityName)
                        .add(l.direction).add(l.rname)
                        .add(String.valueOf(l.rating)).add(String.valueOf(l.isPureVeg)).add(String.valueOf(l.dist_from_center));
                pw.println(joiner.toString());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}


public class Main {
    public static void main(String[] args) throws IOException {
        admin.admin2();
        admin.admin1();
        String locPath = "C:\\Users\\dhpar\\OneDrive - daiict.ac.in\\SEM 2\\MC 122\\MC122 Project\\location.csv";
        String hotelPath = "C:\\Users\\dhpar\\OneDrive - daiict.ac.in\\SEM 2\\MC 122\\MC122 Project\\Hotel.csv";
        String restPath = "C:\\Users\\dhpar\\OneDrive - daiict.ac.in\\SEM 2\\MC 122\\MC122 Project\\Restaurant.csv";
        String userPath = "C:\\Users\\dhpar\\OneDrive - daiict.ac.in\\SEM 2\\MC 122\\MC122 Project\\User.csv";
        Scanner sc = new Scanner(System.in);
        int choice;
        System.out.println("Welcome to Trip Advisor...");
        System.out.println("You can create user or login...");
        do {
            System.out.println("0. Sign Up User\n1. Login User\n2. Exit");
            System.out.println("Enter your choice: ");
            choice = sc.nextInt();
            String u_name,password;
            Users newUser;
            switch (choice) {
                case 0:
                    System.out.print("Enter username: ");
                    u_name = sc.next();
                    System.out.print("Enter password: ");
                    password = sc.next();
                    newUser = new Users(u_name, password);
                    newUser.userSignUp();
                    break;
                case 1:
                    System.out.print("Enter username: ");
                    u_name = sc.next();
                    System.out.print("Enter password: ");
                    password = sc.next();
                    newUser = new Users(u_name, password);
                    int logIn  = newUser.userLogin();
                    if(logIn==1){
                        int c;
                        boolean flag = true;
                        do{
                            System.out.println("1. To display Users.\n2. To delete Users\n3. Exit");
                            c = sc.nextInt();
                            switch (c) {
                                case 1:
                                    Users.displayUsers();
                                    break;
                                case 2:
                                    System.out.print("Enter username to delete: ");
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
                        }while(flag);
                    }
                    else if(logIn==2){
                        int userChoice;
                        boolean flag = true;
                        do{
                            System.out.println("1. Adding Favourite Location\n2. Adding Favourite Hotel\n3. Adding Favourite Restaurants\n4. Show Favourite Location\n5. Show Favourite Hotels\n6. Show Favourite Restaurants\n7. To give Ratings\n8. To find Hotels\n9. To find Restaurants. \n10. Logout");
                            System.out.println("Enter your choice: ");
                            userChoice = sc.nextInt();
                            sc.nextLine();
                            switch (userChoice){
                                case 1:
                                    System.out.print("Enter the Location to be added: ");
                                    String loc = sc.nextLine();
                                    int checkloc = Users.addFavLocation(loc,u_name);
                                    if(checkloc==1){
                                        System.out.println("Location has been added to your Favourite");
                                    }
                                    else{
                                        System.out.println("Sorry the entered Location does not exist");
                                        System.out.println("Do you want to add location to our data?(y/n)");
                                        char ans = sc.next().charAt(0);
                                        if(ans=='y'){
                                            int c;
                                            boolean isTicket;
                                            System.out.print("Enter the name of the city in which this is located: ");
                                            sc.nextLine();
                                            String cityName = sc.nextLine();
                                            System.out.print("Enter the direction of the city: ");
                                            String dir = sc.nextLine();
                                            System.out.print("Enter whether ticket is required or not(1 for yes/0 for no): ");
                                            c = sc.nextInt();
                                            if(c==1){
                                                isTicket = true;
                                            }
                                            else{
                                                isTicket = false;
                                            }
                                            Location.addLocation(cityName, dir, loc, 0, isTicket);
                                            System.out.println("Your given location has been added successfully...Now you can add it as your favourite place");
                                        }
                                    }
                                    break;
                                case 2:
                                    System.out.print("Enter the Hotel to be added: ");
                                    String hotel = sc.nextLine();
                                    int checkhotel = Users.addFavHotels(hotel,u_name);
                                    if(checkhotel==1){
                                        System.out.println("Hotel has been added to your Favourite");
                                    }
                                    else{
                                        System.out.println("Sorry the entered Hotel does not exist");
                                        System.out.println("Do you want to add Hotel to our data?(y/n)");
                                        char ans = sc.next().charAt(0);
                                        if(ans=='y'){
                                            int c;
                                            System.out.print("Enter the name of the city in which this is located: ");
                                            sc.nextLine();
                                            String cityName = sc.nextLine();
                                            System.out.print("Enter the direction of the city: ");
                                            String dir = sc.nextLine();
                                            System.out.print("Enter which star Hotel it is: ");
                                            int star = sc.nextInt();
                                            System.out.print("Enter the price of that Hotel: ");
                                            int price = sc.nextInt();
                                            Hotels.addHotel(cityName, dir, star, hotel, price, 0);
                                            System.out.println("Your given hotel has been added successfully...Now you can add it as your favourite place");
                                        }
                                    }
                                    break;
                                case 3:
                                    System.out.print("Enter the Restaurant to be added: ");
                                    String restaurant = sc.nextLine();
                                    int checkrest = Users.addFavRest(restaurant,u_name);
                                    if(checkrest==1){
                                        System.out.println("Restaurant has been added to your Favourite");
                                    }
                                    else{
                                        System.out.println("Sorry the entered Restaurant does not exist");
                                        System.out.println("Do you want to add Restaurant to our data?(y/n)");
                                        char ans = sc.next().charAt(0);
                                        if(ans=='y'){
                                            int c;
                                            System.out.print("Enter the name of the city in which this is located: ");
                                            sc.nextLine();
                                            String cityName = sc.nextLine();
                                            System.out.print("Enter the direction of the city: ");
                                            String dir = sc.nextLine();
                                            System.out.print("Is the restaurant pure veg? ");
                                            c = sc.nextInt();
                                            boolean b;
                                            if(c==1){
                                                b = true;
                                            }else{
                                                b = false;
                                            }
                                            System.out.println("What is the distance of that restaurant from centre: ");
                                            int dist = sc.nextInt();
                                            Restaurant.addRest(cityName, dir, restaurant, 0, b, dist);
                                            System.out.println("Your given restaurant has been added successfully...Now you can add it as your favourite place");
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
                                    int ratechoice;
                                    boolean f = true;
                                    do{
                                        System.out.println("1. Location \n2. Hotels\n3. Restaurants\n4. Exit");
                                        ratechoice = sc.nextInt();
                                        sc.nextLine();
                                        switch (ratechoice){
                                            case 1:
                                                System.out.print("Enter the name of Location: ");
                                                String l = sc.nextLine();
                                                System.out.println("Give the rating for that location (out of 5)");
                                                double rl = sc.nextDouble();
                                                if(Location.giveRating(l,rl)==1){
                                                    System.out.println("You have successfully given rating for "+l+" .");
                                                    System.out.println(Location.showRating(l));
                                                }
                                                else{
                                                    System.out.println("The location named "+l+" do not exist in our data.");
                                                }
                                                break;
                                            case 2:
                                                System.out.print("Enter the name of Hotel: ");
                                                String h = sc.nextLine();
                                                System.out.println("Give the rating for that hotel (out of 5)");
                                                double rh = sc.nextDouble();
                                                if(Hotels.giveRating(h,rh)==1){
                                                    System.out.println("You have successfully given rating for "+h+" .");
                                                    System.out.println(Hotels.showRating(h));
                                                }
                                                else{
                                                    System.out.println("The hotel named "+h+" do not exist in out data.");
                                                }
                                                break;
                                            case 3:
                                                System.out.print("Enter the name of Restaurant: ");
                                                String r = sc.nextLine();
                                                System.out.println("Give the rating for that hotel (out of 5)");
                                                double rr = sc.nextDouble();
                                                if(Restaurant.giveRating(r,rr)==1){
                                                    System.out.println("You have successfully given rating for "+r+" .");
                                                    System.out.println(Restaurant.showRating(r));
                                                }
                                                else{
                                                    System.out.println("The location named "+r+" do not exist in out data.");
                                                }
                                                break;
                                            case 4:
                                                f = false;
                                        }
                                    }while (f);
                                    break;
                                case 8:
                                    boolean b = true;
                                    do{
                                        System.out.println("1. Find by price\n2. Find by star of hotel\n3. Find by rating\n4. Exit");
                                        int chooseFindHotel = sc.nextInt();
                                        switch (chooseFindHotel){
                                            case 1:
                                                Hotels.findHotelPrice();
                                                break;
                                            case 2:
                                                Hotels.findHotelStar();
                                                break;
                                            case 3:
                                                Hotels.findHotelRating();
                                                break;
                                            case 4:
                                                b = false;
                                        }
                                    }while (b);
                                    break;
                                case 9:
                                    boolean c = true;
                                    do{
                                        System.out.println("1. Find by distance\n2. Find by vegeterian hotel\n3. Find by rating\n4. Exit");
                                        int chooseFindRest = sc.nextInt();
                                        switch (chooseFindRest){
                                            case 1:
                                                Restaurant.findResDist();
                                                break;
                                            case 2:
                                                Restaurant.findRestIsVeg();
                                                break;
                                            case 3:
                                                Restaurant.findRestRating();
                                                break;
                                            case 4:
                                                c = false;
                                        }
                                    }while (c);
                                    break;
                                case 10:
                                    flag = false;
                            }
                        }while(flag);
                    }
                    break;
                case 2:
                    admin.updateLocation();
                    admin.updateHotel();
                    admin.updateRest();
                    System.out.println("Exiting...Thank You");
                    break;

            }
        } while (choice != 2);
    }
}
