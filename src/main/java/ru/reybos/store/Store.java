package ru.reybos.store;

import ru.reybos.model.City;
import ru.reybos.model.User;
import ru.reybos.model.announcement.Announcement;
import ru.reybos.model.announcement.AnnouncementType;
import ru.reybos.model.car.*;

import java.util.List;

public interface Store {
    User findUserByLogin(String login);

    void save(User user);

    void delete(User user);

    List<City> findAllCites();

    List<CarModel> findAllCarModel();

    List<CarBodyType> findAllCarBodyType();

    List<CarEngineType> findAllCarEngineType();

    List<CarTransmissionBoxType> findAllCarTransmissionBoxType();

    List<AnnouncementType> findAllAnnouncementType();

    Announcement findAnnouncementById(int id);

    List<Announcement> findAnnouncementByUserId(int userId);

    List<Announcement> findAllAnnouncement();

    void save(Announcement announcement);

    void update(Announcement announcement);

    void saveCarPhoto(CarPhoto carPhoto, int announcementId);
}