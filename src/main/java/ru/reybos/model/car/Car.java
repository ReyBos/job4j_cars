package ru.reybos.model.car;

import ru.reybos.model.announcement.Announcement;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "is_new")
    private boolean isNew;

    @Column(name = "mileage")
    private int mileage;

    @Column(name = "is_broken")
    private boolean isBroken;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_model_id")
    private CarModel carModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_body_type_id")
    private CarBodyType carBodyType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_engine_type_id")
    private CarEngineType carEngineType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_transmission_box_type_id")
    private CarTransmissionBoxType carTransmissionBoxType;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "car")
    private List<CarPhoto> carPhotos = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "announcement_id")
    private Announcement announcement;

    public static Car of(boolean isNew, int mileage, boolean isBroken, String description) {
        Car car = new Car();
        car.setNew(isNew);
        car.setMileage(mileage);
        car.setBroken(isBroken);
        car.setDescription(description);
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

    public Announcement getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(Announcement announcement) {
        this.announcement = announcement;
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
