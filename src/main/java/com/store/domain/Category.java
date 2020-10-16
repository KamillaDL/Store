package com.store.domain;

import javax.persistence.*;

@Entity
@Table(name = "categories")

public class Category {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String name_of_pic;

    private String image;

    public String getName_of_pic() {
        return name_of_pic;
    }

    public String getImage() {
        return image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName_of_pic(String name_of_pic) {
        this.name_of_pic = name_of_pic;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
