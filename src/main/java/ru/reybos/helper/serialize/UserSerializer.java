package ru.reybos.helper.serialize;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import ru.reybos.model.User;

import java.lang.reflect.Type;

public class UserSerializer implements JsonSerializer<User> {
    @Override
    public JsonElement serialize(
            User user, Type type, JsonSerializationContext context
    ) {
        JsonObject result = new JsonObject();
        result.addProperty("id", user.getId());
        result.addProperty("name", user.getName());
        result.addProperty("login", user.getLogin());
        result.addProperty("phone", user.getPhone());
        return result;
    }
}