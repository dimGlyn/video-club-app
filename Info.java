import java.awt.*;

public class Info {
    private String title;
    private String type;
    private String genre;
    private String company;
    private int year;
    private int copies;
    private int available;
    private String iconPath;

    public Info(){}

    public Info(String name, String company, int year, String category, String type, int copies){
        this.title = name;
        this.company = company;
        this.year = year;
        this.genre = category;
        this.copies = copies;
        setAvailable(copies);
        this.type = type;
    }

    public void setTitle(String title){
        this.title=title;
    }

    public void setType(String type){
        this.type = type;
    }

    public void setGenre(String genre){
        this.genre = genre;
    }

    public void setCompany(String company){
        this.company = company;
    }

    public void setYear(int year){
        this.year = year;
    }

    public void setCopies(int copies){
        this.copies=copies;
        setAvailable(copies);
    }

    public void setAvailable(int available){
        this.available =available;
    }

    public void setIconPath(String iconPath){
        this.iconPath = iconPath;
    }

    public String getTitle(){
        return title;
    }

    public String getType(){
        return type;
    }

    public String getGenre(){
        return genre;
    }

    public String getCompany(){
        return company;
    }

    public int getYear(){
        return year;
    }

    public int getAvailable(){
        return available;
    }

    public String getIconPath(){
        return iconPath;
    }

    public String toString(){
        return ("Title: "+getTitle() +
                "\nType: " +getType() +
                "\nGenre: " +getGenre() +
                "\nCompany: " +getCompany() +
                "\nYear: " +getYear()+
                "\nAvailable copies: "+getAvailable());
    }

}
