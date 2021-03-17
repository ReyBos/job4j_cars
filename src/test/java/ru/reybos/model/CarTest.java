package ru.reybos.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.reybos.model.announcement.Announcement;
import ru.reybos.model.announcement.AnnouncementType;
import ru.reybos.model.car.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class CarTest {
    private static final Logger LOG = LoggerFactory.getLogger(CarTest.class.getName());
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    @Test
    public void whenSaveNewCar() {
        City city = City.of("Москва");
        User user = User.of("Андрей", "test", "test", "12345");
        AnnouncementType announcementType = AnnouncementType.of("транспорт");
        CarModel carModel = CarModel.of("Лада");
        CarBodyType carBodyType = CarBodyType.of("Седан");
        CarEngineType carEngineType = CarEngineType.of("Бензиновый");
        CarTransmissionBoxType carTransmissionBoxType = CarTransmissionBoxType.of("Механическая");

        Car car = Car.of(false, 10000, false, "Не бита не крашена, состояние нового авто.");
        carModel.addCar(car);
        carBodyType.addCar(car);
        carEngineType.addCar(car);
        carTransmissionBoxType.addCar(car);

        Announcement announcement = Announcement.of(1000, false);
        announcement.addCar(car);
        city.addAnnouncement(announcement);
        user.addAnnouncement(announcement);
        announcementType.addAnnouncement(announcement);

        try (Session session = sf.openSession()) {
            session.beginTransaction();

            session.save(city);
            session.save(announcementType);
            session.save(carModel);
            session.save(carBodyType);
            session.save(carEngineType);
            session.save(carTransmissionBoxType);
            session.save(user);

            Announcement announcementDB = session.get(Announcement.class, announcement.getId());
            Car carDb = session.get(Car.class, car.getId());

            assertThat(
                    carDb,
                    is(announcementDB.getCar())
            );
            assertThat(
                    announcementDB,
                    is(carDb.getAnnouncement())
            );
            assertThat(
                    session.get(CarModel.class, carModel.getId()),
                    is(carDb.getCarModel())
            );
            assertThat(
                    session.get(CarModel.class, carModel.getId()).getCars().get(0),
                    is(carDb)
            );
            assertThat(
                    session.get(CarBodyType.class, carBodyType.getId()),
                    is(carDb.getCarBodyType())
            );
            assertThat(
                    session.get(CarBodyType.class, carBodyType.getId()).getCars().get(0),
                    is(carDb)
            );
            assertThat(
                    session.get(CarEngineType.class, carEngineType.getId()),
                    is(carDb.getCarEngineType())
            );
            assertThat(
                    session.get(CarEngineType.class, carEngineType.getId()).getCars().get(0),
                    is(carDb)
            );
            assertThat(
                    session.get(CarTransmissionBoxType.class, carTransmissionBoxType.getId()),
                    is(carDb.getCarTransmissionBoxType())
            );
            assertThat(
                    session.get(
                            CarTransmissionBoxType.class,
                            carTransmissionBoxType.getId()
                    ).getCars().get(0),
                    is(carDb)
            );

            session.getTransaction().commit();
            session.beginTransaction();

            session.delete(user);
            assertNull(session.get(Car.class, car.getId()));

            session.delete(city);
            session.delete(announcementType);
            session.delete(carModel);
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
    public void whenSaveWithPhoto() {
        City city = City.of("Москва");
        User user = User.of("Андрей", "test", "test", "12345");
        AnnouncementType announcementType = AnnouncementType.of("транспорт");
        CarModel carModel = CarModel.of("Лада");
        CarBodyType carBodyType = CarBodyType.of("Седан");
        CarEngineType carEngineType = CarEngineType.of("Бензиновый");
        CarTransmissionBoxType carTransmissionBoxType = CarTransmissionBoxType.of("Механическая");

        Car car = Car.of(false, 10000, false, "Не бита не крашена, состояние нового авто.");
        carModel.addCar(car);
        carBodyType.addCar(car);
        carEngineType.addCar(car);
        carTransmissionBoxType.addCar(car);

        Announcement announcement = Announcement.of(1000, false);
        announcement.addCar(car);
        city.addAnnouncement(announcement);
        user.addAnnouncement(announcement);
        announcementType.addAnnouncement(announcement);

        CarPhoto carPhoto = new CarPhoto();
        CarPhoto carPhoto2 = new CarPhoto();
        car.addCarPhoto(carPhoto);
        car.addCarPhoto(carPhoto2);

        try (Session session = sf.openSession()) {
            session.beginTransaction();

            session.save(city);
            session.save(announcementType);
            session.save(carModel);
            session.save(carBodyType);
            session.save(carEngineType);
            session.save(carTransmissionBoxType);
            session.save(user);

            assertThat(session.get(CarPhoto.class, carPhoto.getId()), is(carPhoto));
            assertThat(session.get(CarPhoto.class, carPhoto2.getId()), is(carPhoto2));

            Car carDb = session.get(Car.class, car.getId());
            assertThat(carDb, is(car));
            assertThat(carDb.getCarPhotos().get(0), is(carPhoto));
            assertThat(carDb.getCarPhotos().get(1), is(carPhoto2));

            session.delete(session.get(User.class, user.getId()));
            assertNull(session.get(Car.class, car.getId()));
            assertNull(session.get(CarPhoto.class, carPhoto.getId()));
            assertNull(session.get(CarPhoto.class, carPhoto2.getId()));

            session.delete(session.get(City.class, city.getId()));
            session.delete(session.get(AnnouncementType.class, announcementType.getId()));
            session.delete(session.get(CarModel.class, carModel.getId()));
            session.delete(session.get(CarBodyType.class, carBodyType.getId()));
            session.delete(session.get(CarEngineType.class, carEngineType.getId()));
            session.delete(
                    session.get(CarTransmissionBoxType.class, carTransmissionBoxType.getId())
            );

            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.error("Ошибка", e);
            fail("Что-то пошло не так");
        }
    }
}