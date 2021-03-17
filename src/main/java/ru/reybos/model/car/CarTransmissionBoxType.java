package ru.reybos.model.car;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "car_transmission_box_type")
public class CarTransmissionBoxType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "carTransmissionBoxType")
    private List<Car> cars = new ArrayList<>();

    public static CarTransmissionBoxType of(String name) {
        CarTransmissionBoxType carTransmissionBoxType = new CarTransmissionBoxType();
        carTransmissionBoxType.setName(name);
        return carTransmissionBoxType;
    }

    public void addCar(Car car) {
        this.cars.add(car);
        car.setCarTransmissionBoxType(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CarTransmissionBoxType that = (CarTransmissionBoxType) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
