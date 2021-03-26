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

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class HbmStoreTest {
    private static final Logger LOG = LoggerFactory.getLogger(HbmStoreTest.class.getName());
    private static final StandardServiceRegistry REGISTRY = new StandardServiceRegistryBuilder()
            .configure().build();
    private static final SessionFactory SF = new MetadataSources(REGISTRY)
            .buildMetadata().buildSessionFactory();
    private static final City CITY_1 = City.of("Москва");
    private static final City CITY_2 = City.of("Санкт-Петербург");
    private static final City CITY_3 = City.of("Краснодар");
    private static final City CITY_4 = City.of("Казань");
    private static final CarModel CAR_MODEL_1 = CarModel.of("Lada");
    private static final CarModel CAR_MODEL_2 = CarModel.of("BMW");
    private static final CarModel CAR_MODEL_3 = CarModel.of("Toyota");
    private static final CarModel CAR_MODEL_4 = CarModel.of("Honda");
    private static final CarBodyType CAR_BODY_TYPE_1 = CarBodyType.of("Седан");
    private static final CarBodyType CAR_BODY_TYPE_2 = CarBodyType.of("Хетчбэк");
    private static final CarBodyType CAR_BODY_TYPE_3 = CarBodyType.of("Универсал");
    private static final CarEngineType CAR_ENGINE_TYPE_1 = CarEngineType.of("Дизель");
    private static final CarEngineType CAR_ENGINE_TYPE_2 = CarEngineType.of("Бензин");
    private static final CarTransmissionBoxType CAR_TRANSMISSION_BOX_TYPE_1 =
            CarTransmissionBoxType.of("Механика");
    private static final CarTransmissionBoxType CAR_TRANSMISSION_BOX_TYPE_2 =
            CarTransmissionBoxType.of("Автомат");
    private static final CarTransmissionBoxType CAR_TRANSMISSION_BOX_TYPE_3 =
            CarTransmissionBoxType.of("Робот");
    private static final User USER = User.of("user", "userLogin", "123", "123");
    private static final AnnouncementType ANNOUNCEMENT_TYPE = AnnouncementType.of("транспорт");
    private static final Announcement ANNOUNCEMENT = Announcement.of(1000, false);
    private static final Car CAR = Car.of(false, 1000, false, "описание");
    private final Store store = HbmStore.instOf();

    @BeforeClass
    public static void init() {
        CAR_MODEL_1.addCar(CAR);
        CAR_BODY_TYPE_1.addCar(CAR);
        CAR_ENGINE_TYPE_1.addCar(CAR);
        CAR_TRANSMISSION_BOX_TYPE_1.addCar(CAR);
        ANNOUNCEMENT.addCar(CAR);
        CITY_1.addAnnouncement(ANNOUNCEMENT);
        USER.addAnnouncement(ANNOUNCEMENT);
        ANNOUNCEMENT_TYPE.addAnnouncement(ANNOUNCEMENT);

        try (Session session = SF.openSession()) {
            session.beginTransaction();

            session.save(CITY_1);
            session.save(CITY_2);
            session.save(CITY_3);
            session.save(CITY_4);
            session.save(CAR_MODEL_1);
            session.save(CAR_MODEL_2);
            session.save(CAR_MODEL_3);
            session.save(CAR_MODEL_4);
            session.save(CAR_BODY_TYPE_1);
            session.save(CAR_BODY_TYPE_2);
            session.save(CAR_BODY_TYPE_3);
            session.save(CAR_ENGINE_TYPE_1);
            session.save(CAR_ENGINE_TYPE_2);
            session.save(CAR_TRANSMISSION_BOX_TYPE_1);
            session.save(CAR_TRANSMISSION_BOX_TYPE_2);
            session.save(CAR_TRANSMISSION_BOX_TYPE_3);
            session.save(ANNOUNCEMENT_TYPE);
            session.save(USER);

            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.error("Ошибка при заполнении тестовыми данными", e);
        }
    }

    @AfterClass
    public static void clear() {
        try (Session session = SF.openSession()) {
            session.beginTransaction();

            session.createQuery("DELETE FROM Car").executeUpdate();
            session.createQuery("DELETE FROM Announcement").executeUpdate();
            session.createQuery("DELETE FROM AnnouncementType").executeUpdate();
            session.createQuery("DELETE FROM User").executeUpdate();
            session.createQuery("DELETE FROM City").executeUpdate();
            session.createQuery("DELETE FROM CarModel").executeUpdate();
            session.createQuery("DELETE FROM CarBodyType").executeUpdate();
            session.createQuery("DELETE FROM CarEngineType").executeUpdate();
            session.createQuery("DELETE FROM CarTransmissionBoxType").executeUpdate();

            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.error("Ошибка при очистке тестовых данных", e);
        }
    }

    @Test
    public void whenFindUserByLoginThenSuccess() {
        User user = User.of("name", "login", "123", "1234");
        store.save(user);
        User userDb = store.findUserByLogin(user.getLogin());
        assertNotNull(userDb);
        store.delete(userDb);
    }

    @Test
    public void whenFindUserByLoginThenError() {
        User user = User.of("name", "login", "123", "1234");
        store.save(user);
        User userDb = store.findUserByLogin("test");
        assertNull(userDb);
        store.delete(user);
    }

    @Test
    public void whenSaveThenError() {
        User user = User.of("name", "login", "123", "1234");
        User user2 = User.of("name", "login", "123", "1234");
        store.save(user);
        store.save(user2);
        assertThat(user2.getId(), is(0));
        store.delete(user);
    }

    @Test
    public void delete() {
        User user = User.of("name", "login", "123", "1234");
        store.save(user);
        store.delete(user);
        User userDb = store.findUserByLogin(user.getLogin());
        assertNull(userDb);
    }

    @Test
    public void findAllCites() {
        List<City> cities = store.findAllCites();
        List<City> expected = List.of(CITY_4, CITY_3, CITY_1, CITY_2);
        assertThat(cities, is(expected));
    }

    @Test
    public void findAllCarModel() {
        List<CarModel> models = store.findAllCarModel();
        List<CarModel> expected = List.of(CAR_MODEL_2, CAR_MODEL_4, CAR_MODEL_1, CAR_MODEL_3);
        assertThat(models, is(expected));
    }

    @Test
    public void findAllCarBodyType() {
        List<CarBodyType> models = store.findAllCarBodyType();
        List<CarBodyType> expected = List.of(CAR_BODY_TYPE_1, CAR_BODY_TYPE_3, CAR_BODY_TYPE_2);
        assertThat(models, is(expected));
    }

    @Test
    public void findAllCarEngineType() {
        List<CarEngineType> models = store.findAllCarEngineType();
        List<CarEngineType> expected = List.of(CAR_ENGINE_TYPE_2, CAR_ENGINE_TYPE_1);
        assertThat(models, is(expected));
    }

    @Test
    public void findAllCarTransmissionBoxType() {
        List<CarTransmissionBoxType> models = store.findAllCarTransmissionBoxType();
        List<CarTransmissionBoxType> expected = List.of(
                CAR_TRANSMISSION_BOX_TYPE_2,
                CAR_TRANSMISSION_BOX_TYPE_1,
                CAR_TRANSMISSION_BOX_TYPE_3
        );
        assertThat(models, is(expected));
    }

    @Test
    public void findAnnouncementById() {
        Announcement announcement = store.findAnnouncementById(ANNOUNCEMENT.getId());
        assertThat(announcement, is(ANNOUNCEMENT));
        assertThat(announcement.getCity(), is(CITY_1));
        assertThat(announcement.getUser(), is(USER));
        assertThat(announcement.getAnnouncementType(), is(ANNOUNCEMENT_TYPE));
        Car car = announcement.getCar();
        assertThat(car, is(CAR));
        assertThat(car.getCarPhotos().size(), is(0));
        assertThat(car.getCarBodyType(), is(CAR_BODY_TYPE_1));
        assertThat(car.getCarEngineType(), is(CAR_ENGINE_TYPE_1));
        assertThat(car.getCarTransmissionBoxType(), is(CAR_TRANSMISSION_BOX_TYPE_1));
        assertThat(car.getCarModel(), is(CAR_MODEL_1));
    }
}