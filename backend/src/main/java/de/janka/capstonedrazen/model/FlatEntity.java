package de.janka.capstonedrazen.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@Getter
@Setter
public class FlatEntity {

    @Id
    @Column(name = "flat_id")
    private Long id;

    @Column(name = "flat_rent")
    private String rent;

    @Column(name = "flat_size")
    private String size;

    @Column(name="flat_image_url")
    private String image;

    @Column(name = "email")
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
