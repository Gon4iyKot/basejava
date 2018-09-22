package com.urise.webapp.util;

import com.google.gson.*;

import java.lang.reflect.Type;

public class JsonSectionAdapter<T> implements JsonDeserializer<T>, JsonSerializer<T> {
    private static String CLASSNAME = "CLASSNAME";
    private static String INSTANSE = "INSTANSE";

    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonPrimitive prim = (JsonPrimitive) jsonObject.get(CLASSNAME);
        String className = prim.getAsString();
        try {
            Class clazz = Class.forName(className);
            return context.deserialize(jsonObject.get(INSTANSE), clazz);
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e.getMessage());
        }
    }

    @Override
    public JsonElement serialize(T section, Type type, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(CLASSNAME, section.getClass().getName());
        jsonObject.add(INSTANSE, context.serialize(section));
        return jsonObject;
    }
}
