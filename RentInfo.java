import java.util.Date;

public class RentInfo {
    private Info object;
    private int code;
    private String name;
    private String telNumber;
    private String date;
    private double cost;

    public RentInfo(){}

    public RentInfo(Info object, String name, String telNumber, Date date){
        this.object=object;
        this.name=name;
        this.telNumber=telNumber;
        this.date=date.toString();
    }

    public void setName(String name){
        this.name = name;
    }

    public void setTelNumber(String number){
        this.telNumber = number;
    }

    public void setObject(Info object){
        this.object = object;
    }

    public void setCode(int code){
        this.code=code;
    }

    public void setDate(String date){
        this.date = date;
    }

    public void setCost (double cost){
        this.cost=cost;
    }

    public Info getObject(){
        return object;
    }

    public int getCode(){
        return code;
    }

    public String getName(){
        return name;
    }

    public String getTelNumber(){
        return telNumber;
    }

    public String getDate(){

        return date;
    }

    public double getCost(){
        return cost;
    }


    public String toString() {
        return ( "\nCode: " + getCode() +
                "\nTitle: " + getObject().getTitle() +
                "\nName: " + getName() +
                "\nTelephone Number: " + getTelNumber() +
                "\nDate: " + getDate() + "\n" );
    }

}

