package ru.dynsys.ttfsample;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Cat {
    public Cat(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected Cat() {
    }

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 10, nullable = false)
    private String name;
}
