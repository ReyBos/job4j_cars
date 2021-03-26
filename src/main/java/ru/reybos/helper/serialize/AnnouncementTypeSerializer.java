package ru.reybos.helper.serialize;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import ru.reybos.model.announcement.AnnouncementType;

import java.lang.reflect.Type;

public class AnnouncementTypeSerializer implements JsonSerializer<AnnouncementType> {
    @Override
    public JsonElement serialize(
            AnnouncementType announcementType, Type type, JsonSerializationContext context
    ) {
        JsonObject result = new JsonObject();
        result.addProperty("id", announcementType.getId());
        result.addProperty("name", announcementType.getName());
        return result;
    }
}