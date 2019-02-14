package SpringBootFirstPkg;

public class Article {

    private String id;
    private String name;
    private String despriction;

    public Article(){

    }

    public Article(String id, String name, String despriction) {
        this.id = id;
        this.name = name;
        this.despriction = despriction;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDespriction() {
        return despriction;
    }

}
