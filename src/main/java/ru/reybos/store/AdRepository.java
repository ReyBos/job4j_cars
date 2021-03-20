package ru.reybos.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.reybos.model.announcement.Announcement;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.function.Function;

public class AdRepository implements AutoCloseable {
    public static final AdRepository INST = new AdRepository();
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private AdRepository() { }

    public static AdRepository instOf() {
        return INST;
    }

    public List<Announcement> getLastDayAnnouncement() {
        return tx(session -> {
            Date date = new Date();
            Calendar cal = new GregorianCalendar();
            cal.setTime(date);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            date = cal.getTime();

            return session.createQuery("from Announcement announ "
                    + "where announ.created > :date", Announcement.class
            ).setParameter("date", date).list();
        });
    }

    public List<Announcement> getAnnouncementWithPhoto() {
        return tx(session -> session.createQuery("select distinct announ "
                + "from Announcement announ "
                + "where announ.car.carPhotos.size > 0", Announcement.class
        ).list());
    }

    public List<Announcement> getAnnouncementByModel(int carModelId) {
        return tx(session -> session.createQuery("select distinct announ "
                + "from Announcement announ "
                + "where announ.car.carModel.id = :modelId", Announcement.class
        ).setParameter("modelId", carModelId).list());
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
