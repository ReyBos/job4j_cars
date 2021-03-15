package ru.reybos.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class CarTest {
    private static final Logger LOG = LoggerFactory.getLogger(CarTest.class.getName());
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    @Test
    public void whenSaveAndDeleteCar() {
        Session session;
        City city = City.of("Москва");
        CarModel carModel = CarModel.of("Лада");
        CarBodyType carBodyType = CarBodyType.of("Седан");
        CarEngineType carEngineType = CarEngineType.of("Бензиновый");
        CarTransmissionBoxType carTransmissionBoxType = CarTransmissionBoxType.of("Механическая");
        User user = User.of("Андрей", "test", "test", "12345");
        Car car = Car.of(
                1000, false, false, 10000, false, "Не бита не крашена, состояние нового авто.",
                user, city, carModel, carBodyType, carEngineType, carTransmissionBoxType
        );
        user.addCar(car);
        city.addCar(car);
        carModel.addCar(car);
        carBodyType.addCar(car);
        carEngineType.addCar(car);
        carTransmissionBoxType.addCar(car);

        try {
            session = sf.openSession();
            session.beginTransaction();

            session.save(city);
            session.save(carModel);
            session.save(carBodyType);
            session.save(carEngineType);
            session.save(carTransmissionBoxType);
            session.save(user);
            session.save(car);

            User userDb = session.get(User.class, user.getId());
            Car carDb = session.get(Car.class, car.getId());

            assertThat(
                    List.of(carDb),
                    is(userDb.getCars())
            );
            assertThat(
                    userDb,
                    is(carDb.getUser())
            );
            assertThat(
                    session.get(City.class, city.getId()),
                    is(carDb.getCity())
            );
            assertThat(
                    session.get(City.class, city.getId()).getCars().get(0),
                    is(carDb)
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

            session.delete(car);
            assertNull(session.get(Car.class, car.getId()));
            session.delete(user);
            session.delete(city);
            session.delete(carModel);
            session.delete(carBodyType);
            session.delete(carEngineType);
            session.delete(carTransmissionBoxType);

            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.error("Ошибка", e);
        }
    }

    @Test
    public void whenSaveWithPhoto() {
        Session session;
        City city = City.of("Москва");
        CarModel carModel = CarModel.of("Лада");
        CarBodyType carBodyType = CarBodyType.of("Седан");
        CarEngineType carEngineType = CarEngineType.of("Бензиновый");
        CarTransmissionBoxType carTransmissionBoxType = CarTransmissionBoxType.of("Механическая");
        User user = User.of("Андрей", "test", "test", "12345");
        Car car = Car.of(
                1000, false, false, 10000, false, "Не бита не крашена, состояние нового авто.",
                user, city, carModel, carBodyType, carEngineType, carTransmissionBoxType
        );
        user.addCar(car);
        city.addCar(car);
        carModel.addCar(car);
        carBodyType.addCar(car);
        carEngineType.addCar(car);
        carTransmissionBoxType.addCar(car);

        try {
            session = sf.openSession();
            session.beginTransaction();

            CarPhoto carPhoto = new CarPhoto();
            CarPhoto carPhoto2 = new CarPhoto();
            car.addCarPhoto(carPhoto);
            car.addCarPhoto(carPhoto2);

            session.save(city);
            session.save(carModel);
            session.save(carBodyType);
            session.save(carEngineType);
            session.save(carTransmissionBoxType);
            session.save(user);
            session.save(car);

            assertThat(session.get(CarPhoto.class, carPhoto.getId()), is(carPhoto));
            assertThat(session.get(CarPhoto.class, carPhoto2.getId()), is(carPhoto2));

            session.getTransaction().commit();
            session.close();

            session = sf.openSession();
            session.beginTransaction();

            session.delete(carPhoto);
            assertNull(session.get(CarPhoto.class, carPhoto.getId()));

            session.getTransaction().commit();
            session.close();

            session = sf.openSession();
            session.beginTransaction();

            Car carDb = session.get(Car.class, car.getId());
            assertThat(carDb, is(car));
            assertThat(carDb.getCarPhotos().get(0), is(carPhoto2));

            session.delete(carDb);
            assertNull(session.get(Car.class, car.getId()));
            assertNull(session.get(CarPhoto.class, carPhoto2.getId()));
            session.delete(session.get(User.class, user.getId()));
            session.delete(session.get(City.class, city.getId()));
            session.delete(session.get(CarModel.class, carModel.getId()));
            session.delete(session.get(CarBodyType.class, carBodyType.getId()));
            session.delete(session.get(CarEngineType.class, carEngineType.getId()));
            session.delete(
                    session.get(CarTransmissionBoxType.class, carTransmissionBoxType.getId())
            );

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            LOG.error("Ошибка", e);
        }
    }
}