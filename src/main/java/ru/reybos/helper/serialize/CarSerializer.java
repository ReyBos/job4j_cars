package ru.reybos.helper.serialize;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import ru.reybos.model.car.Car;

import java.lang.reflect.Type;

public class CarSerializer implements JsonSerializer<Car> {
    @Override
    public JsonElement serialize(
            Car car, Type type, JsonSerializationContext context
    ) {
        JsonObject result = new JsonObject();
        result.addProperty("id", car.getId());
        result.addProperty("isNew", car.isNew());
        result.addProperty("mileage", car.getMileage());
        result.addProperty("isBroken", car.isBroken());
        result.addProperty("description", car.getDescription());
        result.add("carModel", context.serialize(car.getCarModel()));
        result.add("carBodyType", context.serialize(car.getCarBodyType()));
        result.add("carEngineType", context.serialize(car.getCarEngineType()));
        result.add("carTransmissionBoxType", context.serialize(car.getCarTransmissionBoxType()));
        result.add("carPhotos", context.serialize(car.getCarPhotos()));
        return result;
    }
}