package com.edu.fateczl.ticketsales.model;

import java.util.Objects;

/**
 * @author Adriano M Sanchez
 */
public class Ticket {

    private String id;
    private float value;

    public Ticket(String id, float value){
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public float finalValue(float convenienceFee){
        return value + convenienceFee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
