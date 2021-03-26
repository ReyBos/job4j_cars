package ru.reybos.helper.serialize;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import ru.reybos.model.car.CarPhoto;

import java.lang.reflect.Type;

public class CarPhotoSerializer implements JsonSerializer<CarPhoto> {
    @Override
    public JsonElement serialize(
            CarPhoto carPhoto, Type type, JsonSerializationContext context
    ) {
        JsonObject result = new JsonObject();
        result.addProperty("id", carPhoto.getId());
        return result;
    }
}