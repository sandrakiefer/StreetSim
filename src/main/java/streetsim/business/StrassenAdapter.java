package streetsim.business;

import com.google.gson.*;
import java.lang.reflect.Type;

public class StrassenAdapter implements JsonSerializer<Strassenabschnitt>, JsonDeserializer<Strassenabschnitt> {

    public static StrassenAdapter instance;
    private StrassenAdapter(){}

    public static StrassenAdapter getInstance() {
        if (instance == null) instance = new StrassenAdapter();
        return instance;
    }

    @Override
    public JsonElement serialize(Strassenabschnitt src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("type", new JsonPrimitive(src.getClass().getSimpleName()));
        result.add("properties", context.serialize(src, src.getClass()));
        return result;
    }
    @Override
    public Strassenabschnitt deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        JsonElement element = jsonObject.get("properties");
        String fullName = typeOfT.getTypeName();
        String packageText = fullName.substring(0, fullName.lastIndexOf(".") + 1);
        try {
            return context.deserialize(element, Class.forName(packageText+ "abschnitte." + type));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }

}
