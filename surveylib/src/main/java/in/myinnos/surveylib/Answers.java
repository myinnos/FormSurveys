package in.myinnos.surveylib;

import com.google.gson.Gson;

import java.util.LinkedHashMap;

//Singleton Answers ........

public class Answers {
    private volatile static Answers uniqueInstance;
    private final LinkedHashMap<String, Object> answered_hashmap = new LinkedHashMap<>();


    private Answers() {
    }

    public void put_answer(String key, Object value) {
        //RealmObjectFlow.set(key, String.valueOf(value));
        answered_hashmap.put(key, value);
    }

    public void clear_answer() {
        //RealmObjectFlow.set(key, String.valueOf(value));
        answered_hashmap.clear();
    }

    public String get_json_object() {
        Gson gson = new Gson();
        return gson.toJson(answered_hashmap,LinkedHashMap.class);
    }

    @Override
    public String toString() {
        return String.valueOf(answered_hashmap);
    }

    public static Answers getInstance() {
        if (uniqueInstance == null) {
            synchronized (Answers.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new Answers();
                }
            }
        }
        return uniqueInstance;
    }
}
