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
import ru.reybos.model.User;

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
            String sql = "FROM ru.reybos.model.User WHERE login=:login";
            final Query<User> query = session.createQuery(sql);
            query.setParameter("login", login);
            return query.uniqueResult();
        });
    }

    @Override
    public User save(User user) {
        try {
            tx(session -> {
                session.save(user);
                return user;
            });
        } catch (Exception e) {
            LOG.error("Ошибка сохранения нового пользователя");
        }
        return user;
    }

    @Override
    public User delete(User user) {
        return tx(session -> {
            session.delete(user);
            return user;
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
