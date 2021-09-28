package de.janka.capstonedrazen.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "flats_table")
@Getter
@Setter
public class FlatEntity {

    @Id
    @GeneratedValue
    @Column(name = "flat_id", nullable = false)
    private Long id;

    @Column(name = "flat_address", nullable = false)
    private String address;

    @Column(name = "flat_rent", nullable = false)
    private String rent;

    @Column(name = "flat_size", nullable = false)
    private String size;

    @Column(name="flat_image_url")
    private String image;

    @Column(name = "email", nullable = false)
    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlatEntity that = (FlatEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
