package ru.reybos.model.announcement;

import ru.reybos.model.City;
import ru.reybos.model.User;
import ru.reybos.model.car.Car;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "announcement")
public class Announcement {
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "announcement_type_id")
    private AnnouncementType announcementType;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "announcement", fetch = FetchType.LAZY)
    private Car car;

    public static Announcement of(int price, boolean isSold) {
        Announcement announcement = new Announcement();
        announcement.setPrice(price);
        announcement.setSold(isSold);
        return announcement;
    }

    public void addCar(Car car) {
        this.setCar(car);
        car.setAnnouncement(this);
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

    public AnnouncementType getAnnouncementType() {
        return announcementType;
    }

    public void setAnnouncementType(AnnouncementType announcementType) {
        this.announcementType = announcementType;
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
        Announcement that = (Announcement) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
