package ru.reybos.helper.serialize;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import ru.reybos.model.car.CarModel;

import java.lang.reflect.Type;

public class CarModelSerializer implements JsonSerializer<CarModel> {
    @Override
    public JsonElement serialize(
            CarModel carModel, Type type, JsonSerializationContext context
    ) {
        JsonObject result = new JsonObject();
        result.addProperty("id", carModel.getId());
        result.addProperty("name", carModel.getName());
        return result;
    }
}