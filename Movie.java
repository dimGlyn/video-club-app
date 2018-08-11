public class Movie extends Info{
    private String director;
    private String writer;
    private String actors;
    private int length;

    public Movie(){}

    public Movie(String name, String company, int year, String category, String director, String writer, String actors, int length, int copies, String type){
        super(name,company,year,category,type,copies);
        this.director = director;
        this.writer = writer;
        this.actors = actors;
        this.length = length;
    }


    public void setDirector(String director){
        this.director =director;
    }

    public void setWriter(String writer){
        this.writer =writer;
    }

    public void setActors(String actors){
        this.actors=actors;
    }

    public void setLength(int length){
        this.length=length;
    }

    public String getDirector(){
        return director;
    }

    public String getWriter(){
        return writer;
    }

    public String getActors(){
        return actors;
    }

    public int getLength(){
        return length;
    }

    @Override
    public String toString() {
        return("\nMovie details: \n" +
                super.toString() +
                "\nDirector: " +getDirector() +
                "\nWriter: " +getWriter() +
                "\nActors: " +getActors() +
                "\nLength: " +getLength());
    }

}
