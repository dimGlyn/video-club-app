public class Game extends Info {

    public Game(){
        super();
    }

    public Game(String name, String company, int year, String category, String type, int copies){
        super(name,company,year,category,type,copies);
    }


    @Override
    public String toString(){
        return ("\nGame details: \n" + super.toString() + "\n" );
    }

}
