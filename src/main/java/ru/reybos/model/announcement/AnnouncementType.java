package ru.reybos.model.announcement;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "announcement_type")
public class AnnouncementType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "announcementType")
    private List<Announcement> announcements = new ArrayList<>();

    public static AnnouncementType of(String name) {
        AnnouncementType announcementType = new AnnouncementType();
        announcementType.setName(name);
        return announcementType;
    }

    public void addAnnouncement(Announcement announcement) {
        this.announcements.add(announcement);
        announcement.setAnnouncementType(this);
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

    public List<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(List<Announcement> announcements) {
        this.announcements = announcements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AnnouncementType that = (AnnouncementType) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
