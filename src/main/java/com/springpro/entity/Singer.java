package com.springpro.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@ToString(exclude = {"albums","instruments"})
@Entity
@Table(name = "singer")
@NoArgsConstructor
@AllArgsConstructor
@SqlResultSetMapping(name = "implicit", entities = @EntityResult(entityClass = Singer.class))
@NamedQueries({
        @NamedQuery(name = "Singer.findAllWithAlbum",
        query = " select distinct s from Singer s " +
                " left join fetch s.albums a " +
                " left join fetch s.instruments i ")
})
public class Singer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    @Version
    private int version;

    @OneToMany(mappedBy = "singer", cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    private Set<Album> albums = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "singer_instrument",
            joinColumns = @JoinColumn(name = "singer_id"),
            inverseJoinColumns = @JoinColumn(name = "instrument_id"))
    @EqualsAndHashCode.Exclude
    private Set<Instrument> instruments = new HashSet<>();

    public boolean addAlbum(Album album) {
        album.setSinger(this);
        return albums.add(album);
    }

    public void removeAlbum(Album album) {
        albums.remove(album);
    }
}
