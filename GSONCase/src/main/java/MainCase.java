import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class MainCase {
    public static void main(String[] args) {
        String jsonStr = "{\"key\":\"value\"}";

        Gson gson = new Gson();
        JsonElement element = gson.fromJson(jsonStr, JsonElement.class);
        JsonObject jsonObj = element.getAsJsonObject();
        System.out.println(jsonObj.get("key"));
    }
}
