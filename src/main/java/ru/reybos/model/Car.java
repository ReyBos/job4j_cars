package ru.reybos.model;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Date created = new Date(System.currentTimeMillis());

    @Column(name = "price")
    private int price;

    @Column(name = "is_sold")
    private boolean isSold;

    @Column(name = "is_new")
    private boolean isNew;

    @Column(name = "mileage")
    private int mileage;

    @Column(name = "is_broken")
    private boolean isBroken;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne
    @JoinColumn(name = "car_model_id")
    private CarModel carModel;

    @ManyToOne
    @JoinColumn(name = "car_body_type_id")
    private CarBodyType carBodyType;

    @ManyToOne
    @JoinColumn(name = "car_engine_type_id")
    private CarEngineType carEngineType;

    @ManyToOne
    @JoinColumn(name = "car_transmission_box_type_id")
    private CarTransmissionBoxType carTransmissionBoxType;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "car")
    private List<CarPhoto> carPhotos = new ArrayList<>();

    public static Car of(
            int price, boolean isSold, boolean isNew, int mileage, boolean isBroken,
            String description, User user, City city, CarModel carModel, CarBodyType carBodyType,
            CarEngineType carEngineType, CarTransmissionBoxType carTransmissionBoxType
    ) {
        Car car = new Car();
        car.setPrice(price);
        car.setSold(isSold);
        car.setNew(isNew);
        car.setMileage(mileage);
        car.setBroken(isBroken);
        car.setDescription(description);
        car.setUser(user);
        car.setCity(city);
        car.setCarModel(carModel);
        car.setCarBodyType(carBodyType);
        car.setCarEngineType(carEngineType);
        car.setCarTransmissionBoxType(carTransmissionBoxType);
        return car;
    }

    public void addCarPhoto(CarPhoto carPhoto) {
        this.carPhotos.add(carPhoto);
        carPhoto.setCar(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isSold() {
        return isSold;
    }

    public void setSold(boolean sold) {
        isSold = sold;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public boolean isBroken() {
        return isBroken;
    }

    public void setBroken(boolean broken) {
        isBroken = broken;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public CarModel getCarModel() {
        return carModel;
    }

    public void setCarModel(CarModel carModel) {
        this.carModel = carModel;
    }

    public CarBodyType getCarBodyType() {
        return carBodyType;
    }

    public void setCarBodyType(CarBodyType carBodyType) {
        this.carBodyType = carBodyType;
    }

    public CarEngineType getCarEngineType() {
        return carEngineType;
    }

    public void setCarEngineType(CarEngineType carEngineType) {
        this.carEngineType = carEngineType;
    }

    public CarTransmissionBoxType getCarTransmissionBoxType() {
        return carTransmissionBoxType;
    }

    public void setCarTransmissionBoxType(CarTransmissionBoxType carTransmissionBoxType) {
        this.carTransmissionBoxType = carTransmissionBoxType;
    }

    public List<CarPhoto> getCarPhotos() {
        return carPhotos;
    }

    public void setCarPhotos(List<CarPhoto> carPhotos) {
        this.carPhotos = carPhotos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Car car = (Car) o;
        return id == car.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
