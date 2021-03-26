package ru.reybos.helper.serialize;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import ru.reybos.model.car.CarEngineType;

import java.lang.reflect.Type;

public class CarEngineTypesSerializer implements JsonSerializer<CarEngineType> {
    @Override
    public JsonElement serialize(
            CarEngineType carEngineType, Type type, JsonSerializationContext context
    ) {
        JsonObject result = new JsonObject();
        result.addProperty("id", carEngineType.getId());
        result.addProperty("name", carEngineType.getName());
        return result;
    }
}