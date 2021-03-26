package ru.reybos.helper.serialize;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import ru.reybos.model.car.CarBodyType;

import java.lang.reflect.Type;

public class CarBodyTypesSerializer implements JsonSerializer<CarBodyType> {
    @Override
    public JsonElement serialize(
            CarBodyType carBodyType, Type type, JsonSerializationContext context
    ) {
        JsonObject result = new JsonObject();
        result.addProperty("id", carBodyType.getId());
        result.addProperty("name", carBodyType.getName());
        return result;
    }
}