import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

class Users {
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
            File fp = new File("C:\\Users\\dhpar\\OneDrive - daiict.ac.in\\SEM 2\\MC 122\\MC122 Project\\location.csv");
            FileWriter fw = new FileWriter(fp,true);
            StringBuilder sb = new StringBuilder();
            sb.append(username);
            sb.append(",");
            sb.append(password);
            fw.write(sb.toString());
            fw.write("\n");
            System.out.println("New user has been added successfully");
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
            if(loc.equals(l.locationName)){
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
        boolean isEmpty = true;
        for(Users u : user_list){
            if(u.username.equals(user)){
                for(Location l : u.favLoc){
                    System.out.println(l.locationName);
                    break;
                }
//                if(isEmpty) System.out.println("The user has not added any location to their favourite List.");
            }
            else{
                System.out.println("You have entered invalid username try again.");
            }
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

        for(Users u : user_list){
            if(u.username.equals(user)){
                for(Hotels h : u.favHotels){
                    System.out.println(h.hotelName);
                    break;
                }

            }
            else{
                System.out.println("You have entered invalid username try again.");
            }
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
    static void findHotel(int price){
        for(Hotels h : hotelList){
            if(h.price<=price){
                System.out.println(h.hotelName);
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
}
class admin{
    static void admin2() throws FileNotFoundException{
        File fp = new File("C:\\Users\\dhpar\\OneDrive - daiict.ac.in\\SEM 2\\MC 122\\MC122 Project\\Users.csv");
        Scanner read = new Scanner(fp);
        String u_name;
        String psw;
        while(read.hasNextLine()){
            String s = read.nextLine();
            String[] arr = s.split(",");
            u_name = arr[0];
            psw = arr[1];
            System.out.print(arr[0]);
            System.out.println(arr[1]);
            Users newUser = new Users(u_name,psw);
            Users.user_list.add(newUser);
        }
        read.close();
    }
    static void admin1() throws FileNotFoundException {
        File fp = new File("C:\\Users\\dhpar\\OneDrive - daiict.ac.in\\SEM 2\\MC 122\\MC122 Project\\location.csv");
        Scanner read = new Scanner(fp);
        String city, dir, name;
        double rate;
        boolean t;
        while(read.hasNextLine()){
            String s = read.nextLine();
//            String[] arr = s.split(",",5);
            String[] arr = s.split(",");
            city = arr[0];
            dir = arr[1];
            name = arr[2];
            rate = Double.parseDouble(arr[3]);
            t = Boolean.parseBoolean(arr[4]);
            Location.addLocation(city,dir,name,rate,t);
        }
        read.close();
        fp = new File("C:\\Users\\dhpar\\OneDrive - daiict.ac.in\\SEM 2\\MC 122\\MC122 Project\\Hotel.csv");
        read = new Scanner(fp);
        int price, star;
        while(read.hasNextLine()){
            String s = read.nextLine();
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
        fp = new File("C:\\Users\\dhpar\\OneDrive - daiict.ac.in\\SEM 2\\MC 122\\MC122 Project\\Restaurant.csv");
        read = new Scanner(fp);
        double dist;
        while(read.hasNextLine()){
            String s = read.nextLine();
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
}


public class Main {
    public static void main(String[] args) throws IOException {
        admin.admin2();
        admin.admin1();
        Scanner sc = new Scanner(System.in);
        int choice;
        System.out.println("Welcome to Trip Advisor...");
        System.out.println("You can create user or login...");
        do {
            System.out.println("0. Sign Up User\n1. Login User\n2. Delete User\n3. Display Users\n4. Exit");
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
                        admin.admin1();
                    }
                    else if(logIn==2){
                        int userChoice;
                        boolean flag = true;
                        do{
                            System.out.println("1. Adding Favourite Location\n2. Adding Favourite Hotel\n3. Adding Favourite Restaurants\n4. Show Favourite Location\n5. Show Favourite Hotels\n6. Show Favourite Restaurants\n7. To give Ratings\n8. To find Hotels\n 9. Go to main menu");
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
                                            File fp = new File("C:\\Users\\dhpar\\OneDrive - daiict.ac.in\\SEM 2\\MC 122\\MC122 Project\\Location.csv");
                                            FileWriter fw = new FileWriter(fp,true);
                                            fw.write("\n");
                                            ArrayList<String> data = new ArrayList<>();
                                            String s;
                                            int c;
                                            System.out.print("Enter the name of the city in which this is located: ");
                                            sc.nextLine();
                                            s = sc.nextLine();
                                            data.add(s);
                                            System.out.print("Enter the direction of the city: ");
                                            s = sc.nextLine();
                                            data.add(s);
                                            data.add(loc);
                                            data.add("0.0");
                                            System.out.print("Enter whether ticket is required or not(1 for yes/0 for no): ");
                                            c = sc.nextInt();
                                            if(c==1){
                                                data.add("TRUE");
                                            }
                                            if(c==0){
                                                data.add("FALSE");
                                            }
                                            StringBuilder sb = new StringBuilder();
                                            for(int i=0;i<data.size();i++){
                                                sb.append(data.get(i));
                                                if(i < data.size()-1){
                                                    sb.append(",");
                                                }
                                            }
                                            fw.write(sb.toString());
                                            fw.close();
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
                                    int price;
                                    price = sc.nextInt();
                                    Hotels.findHotel(price);
                                    break;
                                case 9:
                                    flag = false;
                            }
                        }while(flag);
                    }
                    break;
                case 2:
                    System.out.print("Enter username to delete: ");
                    String deleteUsername = sc.next();
                    Users.deleteUser(deleteUsername);
                    break;
                case 3:
                    Users.displayUsers();
                    break;
                case 4:
                    System.out.println("Exiting...Thank You");
                    break;

            }
        } while (choice != 4);
//        Users.addFavLocation("Saheliyon Ki Bari","dhyey");
//        Users.addFavLocation("Saheliyon Ki Bari","hemang");
//        Users.addFavHotels("The Leela Palace","dhyey");
//        Users.showFavLoc("dhyey");
//        Users.showFavHotels("dhyey");


    }
}
