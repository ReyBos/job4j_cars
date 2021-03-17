package ru.reybos.model.car;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "car_photo")
public class CarPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CarPhoto carPhoto = (CarPhoto) o;
        return id == carPhoto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}