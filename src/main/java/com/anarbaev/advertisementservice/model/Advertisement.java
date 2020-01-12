package com.anarbaev.advertisementservice.model;

import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Advertisement")
@EnableJpaAuditing
public class Advertisement implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @CreationTimestamp
    private Date created;

    @JsonView(View.MainFields.class)
    @Column(length = 200)
    private String title;

    @JsonView(View.MainFields.class)
    private double price;

    @JsonView(View.MainFields.class)
    private String mainPhoto;

    @Column(length = 1000)
    @JsonView(View.MainFieldsWithDesc.class)
    private String description;

    @Size(max = 3)
    @JsonView({View.MainFieldsWithPhotos.class, View.AllFields.class})
    private String[] photos;


    public Advertisement() {
        super();
    }

    public Advertisement(Long id, String title, String description, double price, String[] photos) {
        super();
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.photos = photos;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String[] getPhotos() {
        return photos;
    }

    public void setPhotos(String[] photos) {
        this.photos = photos;
    }

    public String getMainPhoto() {
        return photos[0];
    }

    public void setMainPhoto(String mainPhoto) {
        this.mainPhoto = mainPhoto;
    }


}
