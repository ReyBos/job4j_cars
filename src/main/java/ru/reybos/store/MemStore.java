package ru.reybos.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.reybos.model.City;
import ru.reybos.model.User;
import ru.reybos.model.announcement.Announcement;
import ru.reybos.model.announcement.AnnouncementType;
import ru.reybos.model.car.*;

import java.util.ArrayList;
import java.util.List;

/**
 * класс для тестирования
 */
public class MemStore implements Store {
    private static final Logger LOG = LoggerFactory.getLogger(MemStore.class.getName());
    private static final Store INST = new MemStore();
    private final List<User> users = new ArrayList<>();
    private int userIds = 1;
    private final List<City> cities = new ArrayList<>();
    private int cityIds = 1;
    private final List<CarModel> carModels = new ArrayList<>();
    private int modelIds = 1;
    private final List<CarBodyType> carBodyTypes = new ArrayList<>();
    private int bodyIds = 1;
    private final List<CarEngineType> carEngineTypes = new ArrayList<>();
    private int engineIds = 1;
    private final List<CarTransmissionBoxType> carTransmissionBoxTypes = new ArrayList<>();
    private int boxIds = 1;
    private final List<Announcement> announcements = new ArrayList<>();
    private int announcementIds = 1;

    private MemStore() { }

    public static Store instOf() {
        return INST;
    }

    @Override
    public User findUserByLogin(String login) {
        for (User user : users) {
            if (user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void save(User user) {
        if (findUserByLogin(user.getLogin()) != null) {
            return;
        }
        user.setId(userIds++);
        users.add(user);
    }

    @Override
    public void delete(User user) {
        users.remove(user);
    }

    @Override
    public List<City> findAllCites() {
        return cities;
    }

    @Override
    public List<CarModel> findAllCarModel() {
        return carModels;
    }

    @Override
    public List<CarBodyType> findAllCarBodyType() {
        return carBodyTypes;
    }

    @Override
    public List<CarEngineType> findAllCarEngineType() {
        return carEngineTypes;
    }

    @Override
    public List<CarTransmissionBoxType> findAllCarTransmissionBoxType() {
        return carTransmissionBoxTypes;
    }

    @Override
    public List<AnnouncementType> findAllAnnouncementType() {
        return null;
    }

    @Override
    public Announcement findAnnouncementById(int id) {
        for (Announcement announcement : announcements) {
            if (announcement.getId() == id) {
                return announcement;
            }
        }
        return null;
    }

    @Override
    public List<Announcement> findAnnouncementByUserId(int userId) {
        return null;
    }

    @Override
    public List<Announcement> findAllAnnouncement() {
        return null;
    }

    public void save(City city) {
        city.setId(cityIds++);
        cities.add(city);
    }

    public void save(CarModel model) {
        model.setId(modelIds++);
        carModels.add(model);
    }

    public void save(CarBodyType carBodyType) {
        carBodyType.setId(bodyIds++);
        carBodyTypes.add(carBodyType);
    }

    public void save(CarEngineType carEngineType) {
        carEngineType.setId(engineIds++);
        carEngineTypes.add(carEngineType);
    }

    public void save(CarTransmissionBoxType carTransmissionBoxType) {
        carTransmissionBoxType.setId(boxIds++);
        carTransmissionBoxTypes.add(carTransmissionBoxType);
    }

    public void save(Announcement announcement) {
        announcement.setId(announcementIds++);
        announcements.add(announcement);
    }

    @Override
    public void update(Announcement announcement) {

    }

    @Override
    public void saveCarPhoto(CarPhoto carPhoto, int announcementId) {

    }
}
