package ru.reybos.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.reybos.model.City;
import ru.reybos.model.User;
import ru.reybos.model.announcement.Announcement;
import ru.reybos.model.announcement.AnnouncementType;
import ru.reybos.model.car.*;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class AdRepositoryTest {
    private static final Logger LOG = LoggerFactory.getLogger(AdRepositoryTest.class.getName());
    private static final StandardServiceRegistry REGISTRY = new StandardServiceRegistryBuilder()
            .configure().build();
    private static final SessionFactory SF = new MetadataSources(REGISTRY)
            .buildMetadata().buildSessionFactory();
    private static AdRepository adRepository = AdRepository.instOf();

    private static City city;
    private static User user;
    private static AnnouncementType announcementType;
    private static CarModel carModel1;
    private static CarModel carModel2;
    private static CarBodyType carBodyType;
    private static CarEngineType carEngineType;
    private static CarTransmissionBoxType carTransmissionBoxType;
    private static Car car1;
    private static Announcement announcement1;
    private static Car car2;
    private static Announcement announcement2;
    private static Car car3;
    private static Announcement announcement3;
    private static CarPhoto carPhoto1;
    private static CarPhoto carPhoto2;

    @BeforeClass
    public static void setup() {
        city = City.of("Москва");
        user = User.of("Андрей", "test", "test", "12345");
        announcementType = AnnouncementType.of("транспорт");
        carModel1 = CarModel.of("Лада");
        carModel2 = CarModel.of("BMW");
        carBodyType = CarBodyType.of("Седан");
        carEngineType = CarEngineType.of("Бензиновый");
        carTransmissionBoxType = CarTransmissionBoxType.of("Механическая");

        car1 = Car.of(false, 10000, false, "Не бита не крашена, состояние нового авто.");
        carModel1.addCar(car1);
        carBodyType.addCar(car1);
        carEngineType.addCar(car1);
        carTransmissionBoxType.addCar(car1);

        announcement1 = Announcement.of(1000, false);
        announcement1.addCar(car1);
        city.addAnnouncement(announcement1);
        user.addAnnouncement(announcement1);
        announcementType.addAnnouncement(announcement1);

        car2 = Car.of(false, 10000, false, "Не бита не крашена, состояние нового авто.");

        carPhoto1 = new CarPhoto();
        carPhoto2 = new CarPhoto();
        car2.addCarPhoto(carPhoto1);
        car2.addCarPhoto(carPhoto2);

        carModel2.addCar(car2);
        carBodyType.addCar(car2);
        carEngineType.addCar(car2);
        carTransmissionBoxType.addCar(car2);

        announcement2 = Announcement.of(1000, false);
        announcement2.addCar(car2);
        city.addAnnouncement(announcement2);
        user.addAnnouncement(announcement2);
        announcementType.addAnnouncement(announcement2);

        car3 = Car.of(false, 10000, false, "Не бита не крашена, состояние нового авто.");
        carModel2.addCar(car3);
        carBodyType.addCar(car3);
        carEngineType.addCar(car3);
        carTransmissionBoxType.addCar(car3);

        announcement3 = Announcement.of(1000, false);

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(System.currentTimeMillis()));
        cal.add(Calendar.DATE, -2);
        announcement3.setCreated(cal.getTime());

        announcement3.addCar(car3);
        city.addAnnouncement(announcement3);
        user.addAnnouncement(announcement3);
        announcementType.addAnnouncement(announcement3);

        try (Session session = SF.openSession()) {
            session.beginTransaction();

            session.save(city);
            session.save(announcementType);
            session.save(carModel1);
            session.save(carModel2);
            session.save(carBodyType);
            session.save(carEngineType);
            session.save(carTransmissionBoxType);
            session.save(user);

            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.error("Ошибка", e);
            fail("Что-то пошло не так");
        }
    }

    @AfterClass
    public static void clear() {
        try (Session session = SF.openSession()) {
            session.beginTransaction();

            session.delete(user);
            session.delete(city);
            session.delete(announcementType);
            session.delete(carModel1);
            session.delete(carModel2);
            session.delete(carBodyType);
            session.delete(carEngineType);
            session.delete(carTransmissionBoxType);

            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.error("Ошибка", e);
            fail("Что-то пошло не так");
        }
    }

    @Test
    public void getLastDayAnnouncement() {
        List<Announcement> expected = new ArrayList<>(List.of(announcement1, announcement2));
        List<Announcement> rsl = adRepository.getLastDayAnnouncement();
        expected.sort(Comparator.comparingInt(Announcement::getId));
        rsl.sort(Comparator.comparingInt(Announcement::getId));
        assertThat(rsl, is(expected));
    }

    @Test
    public void getAnnouncementWithPhoto() {
        List<Announcement> rsl = adRepository.getAnnouncementWithPhoto();
        assertThat(rsl.get(0), is(announcement2));
    }

    @Test
    public void getAnnouncementByModel() {
        List<Announcement> expected = new ArrayList<>(List.of(announcement2, announcement3));
        List<Announcement> rsl = adRepository.getAnnouncementByModel(carModel2.getId());
        expected.sort(Comparator.comparingInt(Announcement::getId));
        rsl.sort(Comparator.comparingInt(Announcement::getId));
        assertThat(rsl, is(expected));
    }
}