package ru.reybos.store;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.reybos.model.User;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class StoreTest {
    private static final Logger LOG = LoggerFactory.getLogger(StoreTest.class.getName());
    private Store store;

    public StoreTest(Store store) {
        this.store = store;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> instancesToTest() {
        return Arrays.asList(
                new Object[]{MemStore.instOf()},
                new Object[]{HbmStore.instOf()}
        );
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
}