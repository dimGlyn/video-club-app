import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class mainApp {
    private static Date date = new Date();
    private static Scanner input = new Scanner(System.in);
    private static ArrayList<Info> Movies = new ArrayList<Info>();
    private static ArrayList<Info> Games = new ArrayList<Info>();
    private static ArrayList<RentInfo> Rented = new ArrayList<RentInfo>(); //List of rented items
    private static Info obj;
    private static RentInfo rentobj;
    private static int code=0;  //Unique number of each rental. It increases
                                //by 1 each time an object is rented.
    private static final double standard_cost=2;
    private static final double extra_cost = 0.5;   //Price for every additional day or week.
    private static File f = null;
    private static File rents = null;
    private static BufferedReader reader;
    private static Info item;
    private static int count; //will determine wether an item from
                            //the ITEM_LIST is valid to be taken into consideration
    private static int co=0;

    static Frame frame;

    public static void main(String[] args) {
        frame = new Frame();

    }

    /*
    Reads the ITEM_LIST.txt file and initializes the movie,
    the game and if lists.
     */
    static void Initialize(String path) {

        try{
            f = new File(path);
        } catch (NullPointerException e){
            frame.message(e.toString() , "Error!");
        }

        try{
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
        }catch (FileNotFoundException e) {
            frame.message(e.toString() , "Error!");
        }
        try{
            String line;
            line = reader.readLine();
            line = reader.readLine();
            while(line!=null) {
                if(line.equals("{")) {
                    line = reader.readLine();
                }else if(line.trim().startsWith("item")){
                    count=0;
                    if (line.trim().equals("item:movie")) {
                        item = new Movie();
                        while(true) {
                            line = reader.readLine();
                            InfoReading(item,line);
                            InfoReadingMovies(item,line);
                            if(line.trim().equals("}")){
                                line = reader.readLine();
                                break;
                            }
                            if(line.trim().toLowerCase().startsWith("image:")) {
                                String iconPath = line.substring(6);
                                item.setIconPath(iconPath);
                            }

                        }
                        if(count==3) {
                            Movies.add(item);
                        }else{
                            System.out.println("Not enough information for\n" +
                                    item.getTitle()+". Item will cannot be inspected.");
                        }
                    } else if (line.trim().equals("item:game")) {
                        item = new Game();
                        while(true) {
                            line = reader.readLine();
                            InfoReading(item, line);
                            if(line.trim().equals("}")){
                                line = reader.readLine();
                                break;
                            }

                            if(line.trim().toLowerCase().startsWith("image:")) {
                                String iconPath = line.substring(6);
                                item.setIconPath(iconPath);
                            }
                        }
                        if(count==3) {
                            Games.add(item);
                        }else{
                            frame.message("Not enough information for\n" +
                                    item.getTitle()+". Item will cannot be inspected.","Info");
                        }
                    }else{
                        line = reader.readLine();
                        frame.message("Cannot identify the type of some items.\n" +
                                "They will not be taken into consideration in the program.", "Info");
                    }
                }else{
                    line = reader.readLine();
                }
            }
        }catch (Exception e){
            frame.message(e.toString(), "Error!");
        }

        frame.setItemList(Movies);
        frame.setItemList(Games);
    }

    /*
    Reads the RENT_LIST.txt file and initializes the rentlist.
     */
    static void LoadRents(String path){

        try{
            rents = new File(path);
        } catch (NullPointerException e){
            frame.message(e.toString(), "Error!");
        }
        try{
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(rents)));
        }catch (FileNotFoundException e) {
            frame.message(e.toString(), "Error!");
        }
        try{
            String line;
            line = reader.readLine();
            line = reader.readLine();
            while(true){
                Info object;
                if(line.equals("{")){
                    line=reader.readLine();
                }
                if(line.trim().startsWith("item:")){
                    rentobj = new RentInfo();
                    if(line.trim().substring(5).trim().equalsIgnoreCase("Movie")){
                        object = new Movie();
                    }else{
                        object = new Game();
                    }
                    line = reader.readLine();
                    object.setType(line.trim().substring(5).trim());
                    line = reader.readLine();
                    object.setTitle(line.trim().substring(6).trim());
                    rentobj.setObject(object);
                    line = reader.readLine();
                    rentobj.setName(line.trim().substring(5));
                    line = reader.readLine();
                    rentobj.setTelNumber(line.trim().substring(7));
                    line = reader.readLine();
                    rentobj.setDate(line.trim().substring(5));
                    line = reader.readLine();
                    rentobj.setCode(Integer.parseInt(line.trim().substring(5)));
                    line = reader.readLine();
                    line = reader.readLine();
                    Rented.add(rentobj);
                    code=rentobj.getCode();
                }
                if(line.equals("}")){
                    break;
                }

            }
        }catch (Exception e){
            frame.message(e.toString(), "Error!");
        }
        LoadLists();
        frame.setRented(Rented);

    }

    /*
    Reads the info for each line and
    assigns it properly to the item.
    */
    private static void InfoReading(Info item, String line){
        try{

            if (line.trim().startsWith("TYPE")) {
                item.setType(line.trim().substring(5).trim());
                if(line.substring(5)!=null){
                    count++;
                }
            }

            if(line.trim().startsWith("TITLE:")){
                item.setTitle(line.trim().substring(6));
                if(line.substring(6)!=null) {
                    count++;
                }
            }



            if(line.trim().startsWith("COMPANY:")){
                item.setCompany(line.trim().substring(8));
                if(line.substring(8)==null){
                    item.setType("unknown");
                }
            }



            if(line.trim().startsWith("YEAR:")){
                item.setYear(Integer.parseInt(line.trim().substring(5)));
                if(line.trim().substring(5)!=null){
                    count++;
                }
            }

            if(line.trim().startsWith("CATEGORY:")){
                item.setGenre(line.trim().substring(9));
                if(line.trim().substring(9)==null){
                    item.setType("unknown");
                }
            }

            if (line.startsWith("COPIES:")) {
                item.setCopies(Integer.parseInt(line.trim().substring(7)));
                if(line.trim().substring(7)==null){
                    item.setType("unknown");
                }
            }

        }catch (Exception e){
            System.out.println("Error closing file.");
        }
    }

    /*
    Reads the extra info that are required
    for the movie items.
    */
    private static void InfoReadingMovies(Info item, String line){

        try{
            if(line.trim().startsWith("CAST:")){
                ((Movie)item).setActors(line.trim().substring(5));
                if(line.trim().substring(5)==null){
                    item.setType("unknown");
                }
            }


            if(line.trim().startsWith("DIRECTOR:")){
                ((Movie)item).setDirector(line.trim().substring(9));
                if(line.trim().substring(9)==null){
                    item.setType("unknown");
                }
            }


            if(line.trim().startsWith("WRITER:")){
                ((Movie)item).setWriter(line.trim().substring(7));
                if(line.trim().substring(7)==null){
                    item.setType("unknown");
                }
            }




            if(line.trim().startsWith("LENGTH:")){
                ((Movie)item).setLength(Integer.parseInt(line.trim().substring(7)));
                if(line.trim().substring(7)==null){
                    item.setType("unknown");
                }
            }

        }catch (Exception e){
            System.out.println("Error closing file.");
        }
    }

    /*
     Iterates the rentlist and the Movies and Games lists
     and if there are items in the rent list , the
     availability of them is decreased by one.
    */
    static void LoadLists(){
        for(RentInfo x:Rented){
            if(x.getObject().getClass().getName().equalsIgnoreCase("game")){
                for(Info g:Games){
                    if(g.getTitle().equalsIgnoreCase(x.getObject().getTitle())){
                        g.setAvailable(g.getAvailable()-1);
                        x.setObject(g);
                    }
                }
            }else{
                for(Info m:Movies){
                    if(m.getTitle().equalsIgnoreCase(x.getObject().getTitle())){
                        m.setAvailable(m.getAvailable()-1);
                        x.setObject(m);
                    }
                }
            }
        }
    }

    //not used
    static void Menu(){



        String in;
        while(true) {
        	
        	if(co==0){
        		MenuReturn();
        	}

            in = input.next();

            if (in.equals("0")) {
                System.out.println("Exiting...");
                break;

            } else if (in.equals("1")) {
                System.out.println("Entering Movie List...");
                MovieList();

            } else if (in.equals("2")) {
                System.out.println("Entering Game List...");
                GameList();

            } else if (in.equals("3")) {
                System.out.println("Entering Rent List...");
                RentList(Rented);


            } else {
                System.out.println("Invalid Input");
            }
        }
    }

    /*
    The user chooses the type of the movie (DVD or blue ray).
     */
    //not used
    private static void MovieList(){
    	co=1;
        System.out.println("Choose type: ");
        System.out.println("Press 1 for DVD");
        System.out.println("Press 2 for Blue ray");
        String type;
        while(true){

            String temp = input.next();
            if (temp.equals("1")){
                type = "DVD";
                break;
            }else if(temp.equals("2")){
                type = "Blue ray";
                break;
            }else{
                System.out.println("Invalid input. Try again.");
            }
        }

        System.out.println("MOVIE LIST");
        printList(Movies, type);
        System.out.print("Choose Movie: ");
        PrintObject(Movies, type);
        rentCheck(obj);
        MenuReturn();
    }

    /*
    The user chooses the type of the game.
     */
    //not used
    private static void GameList() {
    	co=1;
        System.out.println("Choose type: ");
        System.out.println("Press 1 for Playstation");
        System.out.println("Press 2 for Xbox");
        System.out.println("Press 3 for Nintendo");
        String type;
        while(true){
            String temp = input.next();
            if (temp.equals("1")){
                type = "PS4";
                break;
            }else if(temp.equals("2")) {
                type = "Xbox One";
                break;
            }else if(temp.equals("3")){
                type = "Wii U";
                break;
            }else{
                System.out.println("Invalid input. Try again.");
            }
        }
        System.out.println("GAME LIST");
        printList(Games, type);
        System.out.print("Choose Game: ");
        PrintObject(Games, type);
        rentCheck(obj);
        MenuReturn();
    }
    /*
    Shows the list that contains items(movie or game) of the chosen type.
     */
    //not used
    private static void printList(List<Info> list, String temp) {
        for (Info i: list){
            if (i.getType().equalsIgnoreCase(temp)) {
                System.out.println("Title: " + i.getTitle() + "\t | Available copies: " + i.getAvailable() + "\t | Press " + list.indexOf(i));
            }
        }
    }
    
    /*
    Shows all the information of the chosen item.(title,company etc).
    Additionally it makes the static item, obj, equal to the chosen item.
     */
    //not used
    private static void PrintObject(List<Info> list, String type) {
        while(true){
            int temp2;
            try{
                temp2 = input.nextInt();
                if (temp2<Movies.size() && temp2>=0 && list.get(temp2).getType().equals(type)){
                    obj=list.get(temp2);
                    System.out.println(obj.toString());
                    break;
                }else{
                    System.out.println("Invalid input. Try again.");
                }
            }catch (Exception e){
                System.out.println("Invalid input. Enter an Integer");
                input.next();
            }
        }
    }

    /*
    Asks the user if he wants to rent an item after checking
    if the item has available copies left.
     */
    //not used
    private static void rentCheck(Info obj) {
        int x = obj.getAvailable();
        String temp;
        if (x>0){
            temp=input.nextLine();
            System.out.print("Do you want to rent it? ");
            temp = input.nextLine();
            if (temp.equalsIgnoreCase("yes")) {
                obj.setAvailable(x - 1);
                //RentData(obj);
                System.out.println(rentobj.toString());
            }
        }else{
            frame.message("Info", "No available copies left. Come back another time.");
        }
    }

    /*
    Asks for the users information(username, telephone number).
     */
    public static void RentData(Info obj,String name,String tel) {
        code++;
        obj.setAvailable(obj.getAvailable()-1);
        System.out.println(obj.getAvailable());
        for(Info m : Movies){
            if(m.getTitle().equals(obj.getTitle())){
                m.setAvailable(m.getAvailable()-1);
            }
        }
        for(Info g : Games){
            if(g.getTitle().equals(obj.getTitle())){
                g.setAvailable(g.getAvailable()-1);
            }
        }
        /*
        System.out.print("Insert your Name: ");
        String name=input.nextLine();
        System.out.print("Insert your Telephone's number: ");
        String tel=input.nextLine();
        */
        rentobj = new RentInfo(obj,name,tel,date);
        rentobj.setCode(code);
        Rented.add(rentobj);
        frame.setItemList(Movies);
        frame.setItemList(Games);
        frame.setRented(Rented);


    }

    /*
    Show the rented items and asks the user if he wants
    to return any of his rentals. If so , it calculates the
    final cost that he has to pay
     */
    //not used
    private static void RentList(List<RentInfo> list){
    	co=1;
    	if(list.size()!=0){
	        for (RentInfo i: list) {
	            System.out.println("\nTitle: " + i.getObject().getTitle() + "\t|Name: " + i.getName() + "\t|Code: " + i.getCode());
	        }
	        System.out.println("Type the Code of the rental you want to inspect");
	        int Cod;
	        boolean flag=true;
	        while(flag){
	            try{
	                Cod = input.nextInt();
	
	                for (RentInfo i: list) {
	                    if (Cod==i.getCode()) {
	                        rentobj=i;
	                        System.out.println(rentobj.toString());
	                        flag=false;
	                        System.out.print("Do you want to return the rented item? ");
	                        String temp = input.nextLine();
	                        temp = input.nextLine();
	                        if (temp.equalsIgnoreCase("yes")) {
	                            rentobj.getObject().setAvailable(rentobj.getObject().getAvailable() + 1);
	                            list.remove(rentobj);
	                            int days;
	                            while (true){
	                                System.out.println("Enter the days of possession ");
	                                try{
	                                    days=input.nextInt();
	                                    break;
	                                }catch (Exception e){
	                                    System.out.println("Invalid Input. Enter an Integer");
	                                    input.nextLine();
	                                }
	                            }
	                            CalculateCost(rentobj, days);
	                            System.out.println("Total cost: " + rentobj.getCost()+"\u20AC");
	                        }
	                        break;
	                    }
	                }
	            }catch (Exception e){
	                input.next();
	            }
	        
	            if (flag){
	                System.out.println("Invalid input. Try again.");
	            }
	        }
    	}else{
    		System.out.println("The list is empty");
    	}
        MenuReturn();
    }

    /*
    Calculates the cost of the rent depending on the
    days, type of the item and asks if any discounts
    should be made.
     */
    public static void CalculateCost(RentInfo rentobj, int days) {

        String type = rentobj.getObject().getType();
        int year = date.getYear() + 1900;
        double cost = standard_cost;
        if (type.equals("Blue ray")) {
            cost += days * extra_cost;
        } else if (type.equals("DVD")) {
            if (rentobj.getObject().getYear() >= year-1) {
                cost += days * extra_cost;
            } else {
                cost += (days / 7 + 1) * extra_cost;
            }
        } else {
            cost += (days / 7 + 1) * extra_cost;
        }
        while (true){
            try {
                int dis = Integer.parseInt(frame.input("Discount", "Insert potential % discount"));
                cost = cost - (cost * dis) / 100;
                rentobj.setCost(cost);
                break;
            }catch (Exception e){
                frame.message("Error", "Invalid Input");
            }
        }

    }

    /*
    Writes the RENT_LIST.txt with the items in the
    rentlist, if they exist.
     */
    public static void save(ArrayList<RentInfo> list,String path){
        try {
            FileWriter fw = new FileWriter(path);
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write("RENTS\n{");
            for(RentInfo x:list) {

                writer.write("\n{");
                writer.write("\nitem:" + x.getObject().getClass().getName());
                writer.write("\nTYPE:" + x.getObject().getType());
                writer.write("\nTITLE:" + x.getObject().getTitle());
                writer.write("\nNAME:" + x.getName());
                writer.write("\nNUMBER:" + x.getTelNumber());
                writer.write("\nDATE:" + x.getDate());
                writer.write("\nCODE:" + x.getCode());
                writer.write("\n}");
            }
            writer.write("\n}");
            writer.close();

        } catch (IOException e) {
            System.out.println(e.toString());
        }


    }

    //not used
    private static void MenuReturn(){
    	 System.out.println("\nMENU\n");
         System.out.println("Press 0 to exit menu");
         System.out.println("Press 1 to enter Movie List");
         System.out.println("Press 2 to enter Game List");
         System.out.println("Press 3 to enter Rent List");
    	
    }

}
