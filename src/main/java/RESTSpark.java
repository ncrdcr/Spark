import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static spark.Spark.*;

public class RESTSpark {

    static List<TestObject> db = new ArrayList<TestObject>();

    public static void main(String[] args) {

        initDb();

//GET
        get("/db", (request, response) -> {
            response.type("application/json");
            Gson gson = new Gson();
            return gson.toJson(db);
        });

        get("/db/:id", (request, response) -> {
            response.type("application/json");

            int id = -1;
            try {
                id = Integer.parseInt(request.params(":id"));
            }catch(Exception e){
                System.out.println("Exception: " +e);
            }
            TestObject o = getItem(id);
            return o.toJsonString();
        });

//POST
        post("db", (request, response) -> {
            TestObject o = new Gson().fromJson(request.body(), TestObject.class);
            db.add(o);
            return "Neuer User eingetragen";
        });


//DELETE
        delete("db", (request, response) -> {
            TestObject o = new Gson().fromJson(request.body(), TestObject.class);

            int id = o.getId();
            if(deleteItem(id)){
                return "Eintrag gelöscht";
            }
                return "Eintrag nicht gefunden";
        });

//PUT
        put("db", (request, response) -> {
            response.type("application/json");

            TestObject newo = new Gson().fromJson(request.body(), TestObject.class);
            int id = newo.getId();
            if(id != 0){
                TestObject o = changeItem(id, newo);
                if(o != null){
                    System.out.println("Eintrag geändert");
                return o.toJsonString();
                }
            }
            return null;
        });


    }

    private static void initDb(){

        TestObject o1 = new TestObject( 1,"Simon");
        TestObject o2 = new TestObject( 2,"Philipp");
        TestObject o3 = new TestObject( 3,"Cedric");
        TestObject o4 = new TestObject( 4,"Markus");
        TestObject o5 = new TestObject( 5,"Jan");



        db.add(o1);
        db.add(o2);
        db.add(o3);
        db.add(o4);
        db.add(o5);
    }

    private static TestObject getItem(int id){
        for(TestObject o: db){
            if (o.getId() == id){
                return o;
            }
        }
        return null;
    }

    private static boolean deleteItem(int id){
        for(TestObject o: db){
            if (o.getId() == id){
                db.remove(o);
                return true;
            }
        }
        return false;
    }

    private static TestObject changeItem(int id, TestObject newo){
        for(TestObject o: db) {
            if (o.getId() == id) {
                o.setName(newo.getName());
                return o;
            }
        }
        return null;
    }
}
