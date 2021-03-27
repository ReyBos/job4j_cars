package ru.reybos.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.reybos.model.City;
import ru.reybos.model.User;
import ru.reybos.model.announcement.Announcement;
import ru.reybos.model.announcement.AnnouncementType;
import ru.reybos.model.car.*;

import java.util.List;
import java.util.function.Function;

public class HbmStore implements Store, AutoCloseable {
    private static final Logger LOG = LoggerFactory.getLogger(HbmStore.class.getName());
    private static final Store INST = new HbmStore();
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private HbmStore() { }

    public static Store instOf() {
        return INST;
    }

    @Override
    public User findUserByLogin(String login) {
        return tx(session -> {
            String sql = "FROM User WHERE login=:login";
            final Query<User> query = session.createQuery(sql);
            query.setParameter("login", login);
            return query.uniqueResult();
        });
    }

    @Override
    public void save(User user) {
        try {
            tx(session -> {
                session.save(user);
                return user;
            });
        } catch (Exception e) {
            LOG.error("Ошибка сохранения нового пользователя");
        }
    }

    @Override
    public void delete(User user) {
        tx(session -> {
            session.delete(user);
            return user;
        });
    }

    @Override
    public List<City> findAllCites() {
        return tx(session -> {
            String sql = "FROM City city ORDER BY city.name ";
            final Query query = session.createQuery(sql);
            return query.list();
        });
    }

    @Override
    public List<CarModel> findAllCarModel() {
        return tx(session -> {
            String sql = "FROM CarModel carModel ORDER BY carModel.name ";
            final Query query = session.createQuery(sql);
            return query.list();
        });
    }

    @Override
    public List<CarBodyType> findAllCarBodyType() {
        return tx(session -> {
            String sql = "FROM CarBodyType body ORDER BY body.name ";
            final Query query = session.createQuery(sql);
            return query.list();
        });
    }

    @Override
    public List<CarEngineType> findAllCarEngineType() {
        return tx(session -> {
            String sql = "FROM CarEngineType engine ORDER BY engine.name ";
            final Query query = session.createQuery(sql);
            return query.list();
        });
    }

    @Override
    public List<CarTransmissionBoxType> findAllCarTransmissionBoxType() {
        return tx(session -> {
            String sql = "FROM CarTransmissionBoxType transmission "
                    + "ORDER BY transmission.name";
            final Query query = session.createQuery(sql);
            return query.list();
        });
    }

    @Override
    public List<AnnouncementType> findAllAnnouncementType() {
        return tx(session -> {
            String sql = "FROM AnnouncementType";
            final Query query = session.createQuery(sql);
            return query.list();
        });
    }

    @Override
    public Announcement findAnnouncementById(int id) {
        return tx(session -> {
            String sql = "SELECT announcement "
                    + "FROM Announcement announcement "
                    + "LEFT JOIN FETCH announcement.user "
                    + "LEFT JOIN FETCH announcement.city "
                    + "LEFT JOIN FETCH announcement.announcementType "
                    + "LEFT JOIN FETCH announcement.car car "
                    + "LEFT JOIN FETCH car.carModel "
                    + "LEFT JOIN FETCH car.carPhotos "
                    + "LEFT JOIN FETCH car.carBodyType "
                    + "LEFT JOIN FETCH car.carEngineType "
                    + "LEFT JOIN FETCH car.carTransmissionBoxType "
                    + "WHERE announcement.id = :aid";
            final Query query = session.createQuery(sql);
            query.setParameter("aid", id);
            Announcement announcement = (Announcement) query.uniqueResult();
            return announcement;
        });
    }

    @Override
    public List<Announcement> findAnnouncementByUserId(int userId) {
        return tx(session -> {
            String sql = "SELECT DISTINCT announcement "
                    + "FROM Announcement announcement "
                    + "LEFT JOIN FETCH announcement.user "
                    + "LEFT JOIN FETCH announcement.city "
                    + "LEFT JOIN FETCH announcement.announcementType "
                    + "LEFT JOIN FETCH announcement.car car "
                    + "LEFT JOIN FETCH car.carModel "
                    + "LEFT JOIN FETCH car.carPhotos "
                    + "LEFT JOIN FETCH car.carBodyType "
                    + "LEFT JOIN FETCH car.carEngineType "
                    + "LEFT JOIN FETCH car.carTransmissionBoxType "
                    + "WHERE announcement.user.id = :uId";
            final Query query = session.createQuery(sql);
            query.setParameter("uId", userId);
            return query.list();
        });
    }

    @Override
    public List<Announcement> findAllAnnouncement() {
        return tx(session -> {
            String sql = "SELECT DISTINCT announcement "
                    + "FROM Announcement announcement "
                    + "LEFT JOIN FETCH announcement.user "
                    + "LEFT JOIN FETCH announcement.city "
                    + "LEFT JOIN FETCH announcement.announcementType "
                    + "LEFT JOIN FETCH announcement.car car "
                    + "LEFT JOIN FETCH car.carModel "
                    + "LEFT JOIN FETCH car.carPhotos "
                    + "LEFT JOIN FETCH car.carBodyType "
                    + "LEFT JOIN FETCH car.carEngineType "
                    + "LEFT JOIN FETCH car.carTransmissionBoxType "
                    + "WHERE announcement.isSold = false";
            final Query query = session.createQuery(sql);
            return query.list();
        });
    }

    @Override
    public void save(Announcement announcement) {
        try {
            tx(session -> {
                User user = session.get(User.class, announcement.getUser().getId());
                AnnouncementType announcementType = session.get(
                        AnnouncementType.class, announcement.getAnnouncementType().getId()
                );
                City city = session.get(City.class, announcement.getCity().getId());
                user.addAnnouncement(announcement);
                announcementType.addAnnouncement(announcement);
                city.addAnnouncement(announcement);
                announcement.getCar().setAnnouncement(announcement);
                return true;
            });
        } catch (Exception e) {
            LOG.error("Ошибка сохранения нового объявления");
        }
    }

    @Override
    public void update(Announcement announcement) {
        tx(session -> {
            session.update(announcement);
            return true;
        });
    }

    @Override
    public void saveCarPhoto(CarPhoto carPhoto, int announcementId) {
        final Announcement announcement = findAnnouncementById(announcementId);
        tx(session -> {
            announcement.getCar().addCarPhoto(carPhoto);
            session.update(announcement);
            return true;
        });
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
