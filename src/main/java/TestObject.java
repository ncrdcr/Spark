import com.google.gson.Gson;

public class TestObject {

    private int id;
    private String name;

    public TestObject(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TestObject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public String toJsonString(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
