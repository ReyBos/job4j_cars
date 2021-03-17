package ru.reybos.model.announcement;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.reybos.model.CarTest;
import ru.reybos.model.City;
import ru.reybos.model.User;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class AnnouncementTest {
    private static final Logger LOG = LoggerFactory.getLogger(CarTest.class.getName());
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    @Test
    public void whenAddEmptyAnnouncement() {
        City city = City.of("Москва");
        User user = User.of("Андрей", "test", "test", "12345");
        AnnouncementType announcementType = AnnouncementType.of("транспорт");

        Announcement announcement = Announcement.of(1000, false);
        city.addAnnouncement(announcement);
        user.addAnnouncement(announcement);
        announcementType.addAnnouncement(announcement);

        try (Session session = sf.openSession()) {
            session.beginTransaction();

            session.save(city);
            session.save(announcementType);
            session.save(user);

            Announcement announcementDB = session.get(Announcement.class, announcement.getId());
            assertThat(
                    announcementDB,
                    is(announcement)
            );
            assertThat(
                    announcementDB.getUser(),
                    is(user)
            );
            assertThat(
                    announcementDB.getAnnouncementType(),
                    is(announcementType)
            );
            assertThat(
                    announcementDB.getCity(),
                    is(city)
            );
            assertThat(
                    user.getAnnouncements().get(0),
                    is(announcement)
            );

            session.delete(user);
            assertNull(session.get(Announcement.class, announcement.getId()));
            session.delete(city);
            session.delete(announcementType);

            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.error("Ошибка", e);
            fail("Что-то пошло не так");
        }
    }
}