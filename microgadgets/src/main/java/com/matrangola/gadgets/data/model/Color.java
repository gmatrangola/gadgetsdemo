package com.matrangola.gadgets.data.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.matrangola.gadgets.data.seralizer.ColorDeserializer;
import com.matrangola.gadgets.data.seralizer.ColorSerializer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "color")
@JsonSerialize(using = ColorSerializer.class)
@JsonDeserialize(using = ColorDeserializer.class)
public class Color {
    @Id
    @GeneratedValue
    private Long id;

    private int red;
    private int green;
    private int blue;

    public Color() {
    }

    public Color(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public Long getId() {
        return id;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }
}
