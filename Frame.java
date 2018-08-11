import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;


public class Frame extends JFrame implements ActionListener, MouseListener{
    private JButton rentMovie;
    private JButton rentGame;
    private JButton returnItem;
    JMenuItem initialize = new JMenuItem("Initialize item list");
    JMenuItem load = new JMenuItem("Load current rents");
    JMenuItem save = new JMenuItem("Save current rents");
    JTabbedPane tabs;
    private static mainApp methods = new mainApp();
    private static JMenuBar menu = new JMenuBar();
    private static JFileChooser explorer = new JFileChooser();
    private static ArrayList<Info> Movies = new ArrayList<>();
    private static ArrayList<Info> Games = new ArrayList<>();
    private static ArrayList<RentInfo> Rented = new ArrayList<>();
    private static DefaultListModel movieList = new DefaultListModel();
    private static DefaultListModel gameList = new DefaultListModel();
    private DefaultListModel rentList = new DefaultListModel();
    JPanel moviesTab = new JPanel();
    JPanel gamesTab = new JPanel();
    JPanel rentsTab = new JPanel();
    private static String path;
    private static String rentalsPath;
    private static Info currentMovie;
    private static Info currentGame;
    private static RentInfo currentRent;
    private JList mList;    // movie titles
    private JList gList;    // game titles
    private JList rList;    // rental titles

    /*
    Initializes the Jlists and the DefaultListModel objects for the items
     */

    public void setItemList(ArrayList<Info> list){
        try {
            if (list.get(0).getClass().getName().equalsIgnoreCase("movie")) {
                this.Movies = list;
                for(Info i:Movies){
                    movieList.addElement(i.getTitle());
                }

            } else if (list.get(0).getClass().getName().equalsIgnoreCase("game")) {
                this.Games = list;
                for(Info i:Games){
                    gameList.addElement(i.getTitle());
                }

            }
        }catch(IndexOutOfBoundsException e) {
            message("Error", e.toString());
        }
    }

     /*
    Initializes the Jlists and the DefaultListModel objects for the rentals
     */

    public void setRented(ArrayList<RentInfo> list){
        this.Rented = list;

        for(RentInfo i:Rented){
            rentList.addElement(i.getObject().getTitle());
        }

    }

    public Frame(){
        super("Video club");
        setDefaultLookAndFeelDecorated(true);
        setBounds(150, 150, 500, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        addMenu();

        addTabs();

        setVisible(true);


    }

    /*
    Adds the JMenu object to the mainframe
     */

    private void addMenu(){

        this.setJMenuBar(menu);

        JMenu items = new JMenu("Items");
        menu.add(items);

        initialize.addActionListener(this);
        items.add(initialize);
        
        items.addSeparator();

        JMenu rents = new JMenu("Rents");
        menu.add(rents);

        load.addActionListener(this);
        rents.add(load);

        rents.addSeparator();
        save.addActionListener(this);

        rents.add(save);

    }

    /*
    Adds the tabs to the mainframe
     */

    private void addTabs(){
    	
        tabs = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT);

        gamesTab.setLayout(new FlowLayout());
        tabs.addTab("Movies", moviesTab);
        tabs.addTab("Games",gamesTab);
        
        moviesTab.setLayout(new BorderLayout());
        gamesTab.setLayout(new BorderLayout());
        
        rentMovie = new JButton("Rent Movie");
        moviesTab.add(rentMovie, BorderLayout.EAST);
        rentMovie.setEnabled(false);
        rentGame = new JButton("Rent Game");
        gamesTab.add(rentGame, BorderLayout.EAST);
        rentGame.setEnabled(false);
        returnItem = new JButton("Return");
        rentsTab.add(returnItem,BorderLayout.EAST);
        returnItem.setEnabled(false);


        for(Info i:Movies){
            movieList.addElement(i.getTitle());
        }

        mList = new JList(movieList);
        moviesTab.add(mList);
        mList.addListSelectionListener(lse -> {});
        JScrollPane mscroller = new JScrollPane(mList);
        moviesTab.add(mscroller);
        mList.addMouseListener(this);

        for(Info i:Games){
            gameList.addElement(i.getTitle());
        }

        gList = new JList(gameList);
        gamesTab.add(gList);
        JScrollPane gscroller = new JScrollPane(gList);
        gamesTab.add(gscroller);
        gList.addMouseListener(this);


        setRentTab();

        setVisible(true);

        add(tabs);
    }

     /*
     Opens the JFileChooser so the user can select a file
     */

    private  String FileExplorer(){

        

        explorer.setCurrentDirectory(new java.io.File("."));
        explorer.setDialogTitle("Choose file.");
        explorer.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        if (explorer.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            path =explorer.getSelectedFile().getPath().toString();
        } else {
            message("Error", "No Selection!");
            path=" ";
        }

        return path;

    }

    /*
    Prints a message.
     */

    public void message(String title, String text){
        JOptionPane.showMessageDialog(null, text, title, JOptionPane.INFORMATION_MESSAGE);
    }

    /*
    Gets input from the user.
    */

    public String input(String title, String message){
        return  JOptionPane.showInputDialog(this,message,title,JOptionPane.INFORMATION_MESSAGE);
    }

    /*
    Rents an item.
     */

    public void Rent(Info current){
        try {

            int response = JOptionPane.showConfirmDialog(this, current.toString() + "\nDo you want to rent this item?\n", "Rent", 2, 3, new ImageIcon(current.getIconPath()));
            if (response == JOptionPane.OK_OPTION && current.getAvailable() > 0) {
                String name = JOptionPane.showInputDialog(this, "Insert your Username:");
                if (name.length() > 0) {
                    String phone = JOptionPane.showInputDialog(this, "Insert your phone:");
                    if(phone.length()>=10){
                        methods.RentData(current, name , phone);
                    }else{
                        message("Error","Wrong phone format!");
                    }
                }
            }else if(response == JOptionPane.OK_OPTION){
                message("Cannot complete purchase", "There are no available copies left ");
            }
           refreshList();
        }catch(Exception e){
            message("Information","Go to item and click refresh to continue!");
        }
        refreshList();
    }

    /*
    Returns a rented Item.
     */

    public void Return(RentInfo current){

        int response = JOptionPane.showConfirmDialog(this, current.toString() + "\nDo you want to return this rent?\n", "Rent", 2);
        if(response==JOptionPane.OK_OPTION) {
            Rented.remove(current);
            if(current.getObject().getClass().getName().equalsIgnoreCase("movie")) {
                for(Info i: Movies){
                    if(current.getObject().getTitle().equals(i.getTitle())){
                        i.setAvailable(i.getAvailable()+1);
                    }
                }
            }else{
                for(Info i: Games){
                    if(current.getObject().getTitle().equals(i.getTitle())){
                        i.setAvailable(i.getAvailable()+1);
                    }
                }
            }

            while (true) {
                try {
                    int days = Integer.parseInt(input("Days", "Enter the days of possecion"));
                    methods.CalculateCost(current, days);
                    break;
                } catch (Exception e) {
                    message("Error", "Invalid input!");
                }
            }
            message("Congratulations", "Your payment is done. The final cost is: " + current.getCost());
            
            Rented.remove(current);
            //methods.save(Rented,rentalsPath);
            refreshList();
        }
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource()==rentMovie){
            Rent(currentMovie);
            rentMovie.setEnabled(false);
        }else if(e.getSource()==rentGame){
            Rent(currentGame);
            rentGame.setEnabled(false);
        }else if(e.getSource()==returnItem){
            Return(currentRent);
            returnItem.setEnabled(false);
        }
        if(e.getSource()==initialize){
            methods.Initialize(FileExplorer());
            initialize.setEnabled(false);
        }else if(e.getSource()==load){
            rentalsPath = FileExplorer();
            methods.LoadRents(rentalsPath);
             load.setEnabled(false);
        }else if(e.getSource()==save){
            methods.save(Rented,FileExplorer());
        }

    }

    @Override
    public void mouseClicked(MouseEvent e){
        int i;
        int y;
        int z;
        if (mList.getSelectedIndex()!=-1){
            rentMovie.setEnabled(true);
            i=mList.getSelectedIndex();
            currentMovie = Movies.get(i);
            if(e.getClickCount()==2){
                Rent(currentMovie);
            }
        }else if (gList.getSelectedIndex()!=-1){
            rentGame.setEnabled(true);
            y=gList.getSelectedIndex();
            currentGame=Games.get(y);
            if(e.getClickCount()==2){
                Rent(currentGame);
            }
        }else if (rList.getSelectedIndex()!=-1){
            returnItem.setEnabled(true);
            z=rList.getSelectedIndex();
            currentRent = Rented.get(z);
            if(e.getClickCount()==2){
                Return(currentRent);
            }
        }
    }

    /*
    Deletes the previous JLists and Models and initializes
    them again with the changes that have been made to them.
     */

    public void refreshList(){
    	rentGame.setEnabled(false);
    	rentMovie.setEnabled(false);
        returnItem.setEnabled(false);
    	movieList.clear();
        mList.removeAll();
        gameList.clear();
        gList.removeAll();
        rentList.clear();
        rList.removeAll();
        rentsTab.removeAll();
        if(path.endsWith("ITEM_LIST.txt")){
            methods.Initialize(path.replace("ITEM_LIST.txt","Refresh.txt"));
        }else if(path.endsWith("RENT_LIST.txt")){
            methods.Initialize(path.replace("RENT_LIST.txt","Refresh.txt"));

        }
        setRentTab();
    }

    /*
    Sets the Rentals tab.
     */

    public void setRentTab(){
    	 tabs.addTab("Rentals",rentsTab);
         rentsTab.setLayout(new BorderLayout());
         returnItem = new JButton("Return");
        rentsTab.add(returnItem,BorderLayout.EAST);
         returnItem.setEnabled(false);
         for(RentInfo i:Rented){
             rentList.addElement(i.getObject().getTitle());
         }

         rList = new JList(rentList);
        rentsTab.add(rList);
         JScrollPane rscroller = new JScrollPane(rList);
        rentsTab.add(rscroller);
        rList.addMouseListener(this);
         returnItem.addActionListener(this);
        setVisible(true);
    }
    
    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
