package ru.reybos.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.json.JSONObject;
import org.junit.*;
import org.junit.rules.TemporaryFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.reybos.helper.serialize.*;
import ru.reybos.model.City;
import ru.reybos.model.User;
import ru.reybos.model.announcement.Announcement;
import ru.reybos.model.announcement.AnnouncementType;
import ru.reybos.model.car.*;
import ru.reybos.store.HbmStore;
import ru.reybos.store.Store;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AnnouncementServiceTest {
    @ClassRule
    public static TemporaryFolder folder = new TemporaryFolder();
    private static final Logger LOG = 
            LoggerFactory.getLogger(AnnouncementServiceTest.class.getName());
    private static final StandardServiceRegistry REGISTRY = new StandardServiceRegistryBuilder()
            .configure().build();
    private static final SessionFactory SF = new MetadataSources(REGISTRY)
            .buildMetadata().buildSessionFactory();
    private static final Gson GSON = new GsonBuilder()
            .setDateFormat("dd-MM-yyyy HH:mm:ss")
            .registerTypeAdapter(City.class, new CitySerializer())
            .registerTypeAdapter(CarModel.class, new CarModelSerializer())
            .registerTypeAdapter(CarBodyType.class, new CarBodyTypesSerializer())
            .registerTypeAdapter(CarEngineType.class, new CarEngineTypesSerializer())
            .registerTypeAdapter(CarPhoto.class, new CarPhotoSerializer())
            .registerTypeAdapter(
                    CarTransmissionBoxType.class, new CarTransmissionBoxTypeSerializer()
            )
            .registerTypeAdapter(Car.class, new CarSerializer())
            .registerTypeAdapter(User.class, new UserSerializer())
            .registerTypeAdapter(AnnouncementType.class, new AnnouncementTypeSerializer())
            .create();
    private static final List<City> CITIES = new ArrayList<>();
    private static final List<CarModel> CAR_MODELS = new ArrayList<>();
    private static final List<CarBodyType> CAR_BODY_TYPES = new ArrayList<>();
    private static final List<CarEngineType> CAR_ENGINE_TYPES = new ArrayList<>();
    private static final List<CarTransmissionBoxType> CAR_TRANSMISSION_BOX_TYPES =
            new ArrayList<>();
    private static Announcement announcement;
    private static User user;
    private static User user2;
    private static AnnouncementType announcementType;
    private static HttpServletRequest request;
    private static HttpSession session;
    private static File source;
    private static BufferedReader in;
    private final Store store = HbmStore.instOf();

    @BeforeClass
    public static void init() throws IOException {
        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);

        City city1 = City.of("Краснодар");
        City city2 = City.of("Москва");
        City city3 = City.of("Воронеж");
        CITIES.add(city3);
        CITIES.add(city1);
        CITIES.add(city2);

        CarModel carModel1 = CarModel.of("Лада");
        CarModel carModel2 = CarModel.of("Хонда");
        CAR_MODELS.add(carModel1);
        CAR_MODELS.add(carModel2);

        CarBodyType carBodyType1 = CarBodyType.of("седан");
        CarBodyType carBodyType2 = CarBodyType.of("универсал");
        CAR_BODY_TYPES.add(carBodyType1);
        CAR_BODY_TYPES.add(carBodyType2);

        CarEngineType carEngineType1 = CarEngineType.of("бензин");
        CarEngineType carEngineType2 = CarEngineType.of("дизель");
        CAR_ENGINE_TYPES.add(carEngineType1);
        CAR_ENGINE_TYPES.add(carEngineType2);

        CarTransmissionBoxType carTransmissionBoxType1 = CarTransmissionBoxType.of("автомат");
        CarTransmissionBoxType carTransmissionBoxType2 = CarTransmissionBoxType.of("механика");
        CAR_TRANSMISSION_BOX_TYPES.add(carTransmissionBoxType1);
        CAR_TRANSMISSION_BOX_TYPES.add(carTransmissionBoxType2);

        Car car = Car.of(false, 10000, false, "description");
        carModel1.addCar(car);
        carBodyType1.addCar(car);
        carEngineType1.addCar(car);
        carTransmissionBoxType1.addCar(car);

        announcementType = AnnouncementType.of("транспорт");
        announcement = Announcement.of(1000, true);
        announcement.addCar(car);
        city1.addAnnouncement(announcement);
        user = User.of("name", "login", "pass", "12345");
        user.addAnnouncement(announcement);
        announcementType.addAnnouncement(announcement);
        user2 = User.of("test", "test", "pass", "54321");

        try (Session session = SF.openSession()) {
            session.beginTransaction();

            session.save(city1);
            session.save(city2);
            session.save(city3);
            session.save(carModel1);
            session.save(carModel2);
            session.save(carBodyType1);
            session.save(carBodyType2);
            session.save(carEngineType1);
            session.save(carEngineType2);
            session.save(carTransmissionBoxType1);
            session.save(carTransmissionBoxType2);
            session.save(announcementType);
            session.save(user);
            session.save(user2);

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
            LOG.error("Ошибка", e);
            fail("Что-то пошло не так");
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenGetThenError() {
        AnnouncementService service = AnnouncementService.getInstance();
        when(request.getParameter("action")).thenReturn("test");
        service.execute(request);
    }

    @Test
    public void whenGetFields() {
        AnnouncementService service = AnnouncementService.getInstance();
        when(request.getParameter("action")).thenReturn("get-form-fields");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        Optional<String> rsl = service.execute(request);
        assertTrue(rsl.isPresent());

        Map<String, Object> expectedFieldsMap = new LinkedHashMap<>();
        expectedFieldsMap.put("cities", CITIES);
        expectedFieldsMap.put("carModels", CAR_MODELS);
        expectedFieldsMap.put("carBodyTypes", CAR_BODY_TYPES);
        expectedFieldsMap.put("carEngineTypes", CAR_ENGINE_TYPES);
        expectedFieldsMap.put("carTransmissionBoxTypes", CAR_TRANSMISSION_BOX_TYPES);
        expectedFieldsMap.put("announcementType", announcementType);
        Map<String, Object> expectedMap = new LinkedHashMap<>();
        expectedMap.put("fields", expectedFieldsMap);
        expectedMap.put("user", user);

        assertThat(rsl.get(), is(GSON.toJson(expectedMap)));
    }

    /**
     * JSONObject announcementFromForm - json который придет с фронта, сохраняем его в файл
     * что бы это сэмитировать
     */
    @Test
    public void whenSaveThenSuccess() throws IOException {
        source = folder.newFile("source.txt");
        in = new BufferedReader(new FileReader(source));

        JSONObject carBodyType = new JSONObject();
        carBodyType.put("id", CAR_BODY_TYPES.get(0).getId());
        JSONObject carEngineType = new JSONObject();
        carEngineType.put("id", CAR_ENGINE_TYPES.get(0).getId());
        JSONObject carModel = new JSONObject();
        carModel.put("id", CAR_MODELS.get(0).getId());
        JSONObject carTransmissionBoxType = new JSONObject();
        carTransmissionBoxType.put("id", CAR_TRANSMISSION_BOX_TYPES.get(0).getId());

        JSONObject car = new JSONObject();
        car.put("description", "описание");
        car.put("isBroken", false);
        car.put("isNew", false);
        car.put("mileage", "10000");
        car.put("carBodyType", carBodyType);
        car.put("carEngineType", carEngineType);
        car.put("carModel", carModel);
        car.put("carTransmissionBoxType", carTransmissionBoxType);

        JSONObject city = new JSONObject();
        city.put("id", CITIES.get(0).getId());
        JSONObject userJson = new JSONObject();
        userJson.put("id", user.getId());
        JSONObject announcementTypeJson = new JSONObject();
        announcementTypeJson.put("id", announcementType.getId());

        JSONObject announcementFromForm = new JSONObject();
        announcementFromForm.put("isSold", false);
        announcementFromForm.put("price", "100000");
        announcementFromForm.put("city", city);
        announcementFromForm.put("car", car);
        announcementFromForm.put("user", userJson);
        announcementFromForm.put("announcementType", announcementTypeJson);

        try (PrintWriter out = new PrintWriter(source)) {
            out.write(announcementFromForm.toString());
        }

        AnnouncementService service = AnnouncementService.getInstance();
        when(request.getParameter("action")).thenReturn("save");
        when(request.getReader()).thenReturn(in);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);

        Optional<String> rsl = service.execute(request);
        assertTrue(rsl.isPresent());

        Announcement announcementFromService = GSON.fromJson(rsl.get(), Announcement.class);
        Announcement announcementFromDb = store.findAnnouncementById(
                announcementFromService.getId()
        );

        assertThat(announcementFromDb.getAnnouncementType(), is(announcementType));
        assertThat(announcementFromDb.getCity(), is(CITIES.get(0)));
        assertThat(announcementFromDb.getUser(), is(user));
        assertNotNull(announcementFromDb.getCar());

        try (Session session = SF.openSession()) {
            session.beginTransaction();
            session.delete(announcementFromDb);
            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.error("Ошибка", e);
            fail("Что-то пошло не так");
        }
    }

    @Test
    public void whenUpdateThenSuccess() throws IOException {
        source = folder.newFile("source2.txt");
        in = new BufferedReader(new FileReader(source));

        JSONObject jsonFromForm = new JSONObject();
        jsonFromForm.put("announcementId", announcement.getId());
        jsonFromForm.put("isSold", !announcement.isSold());

        try (PrintWriter out = new PrintWriter(source)) {
            out.write(jsonFromForm.toString());
        }

        AnnouncementService service = AnnouncementService.getInstance();
        when(request.getParameter("action")).thenReturn("update");
        when(request.getReader()).thenReturn(in);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);

        Optional<String> rsl = service.execute(request);
        assertTrue(rsl.isPresent());
        Announcement announcementFromDb = store.findAnnouncementById(announcement.getId());
        assertTrue(announcementFromDb.isSold() ^ announcement.isSold());
    }

    @Test
    public void whenUpdateAndAnotherUserThenError() throws IOException {
        source = folder.newFile("source3.txt");
        in = new BufferedReader(new FileReader(source));

        JSONObject jsonFromForm = new JSONObject();
        jsonFromForm.put("announcementId", announcement.getId());
        jsonFromForm.put("isSold", !announcement.isSold());

        try (PrintWriter out = new PrintWriter(source)) {
            out.write(jsonFromForm.toString());
        }

        AnnouncementService service = AnnouncementService.getInstance();
        when(request.getParameter("action")).thenReturn("update");
        when(request.getReader()).thenReturn(in);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user2);

        Optional<String> rsl = service.execute(request);
        assertFalse(rsl.isPresent());
    }

    @Test
    public void whenGetUserAnnouncementThenSuccess() {
        AnnouncementService service = AnnouncementService.getInstance();
        when(request.getParameter("action")).thenReturn("get-user-announcement");
        when(request.getParameter("id")).thenReturn(String.valueOf(user.getId()));
        Optional<String> rsl = service.execute(request);
        assertTrue(rsl.isPresent());

        List<Announcement> expected = List.of(announcement);
        assertThat(rsl.get(), is(GSON.toJson(expected)));
    }
}