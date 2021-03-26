package ru.reybos.helper.serialize;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import ru.reybos.model.car.CarTransmissionBoxType;

import java.lang.reflect.Type;

public class CarTransmissionBoxTypeSerializer
        implements JsonSerializer<CarTransmissionBoxType> {
    @Override
    public JsonElement serialize(
            CarTransmissionBoxType carTransmissionBoxType,
            Type type, JsonSerializationContext context
    ) {
        JsonObject result = new JsonObject();
        result.addProperty("id", carTransmissionBoxType.getId());
        result.addProperty("name", carTransmissionBoxType.getName());
        return result;
    }
}