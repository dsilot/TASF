/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tasf.mx.ctrlventas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 *
 * @author silot
 */
@Entity
@Table(name = "productos")
public class producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "number", nullable = false, unique = true)
    private int number;
    @Column(name = "name", length = 50, nullable = false, unique = false)
    private String name;
    @Column(name = "cantidad", nullable = false, unique = false)
    private int cantidad;
    @Column(name = "precio", nullable = false, unique = false)
    private float precio;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "folio_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private folio folio;
    @Transient
    private float total;

    public producto() {
    }

    public producto(int number, String name, int cantidad, float precio) {
        this.number = number;
        this.name = name;
        this.cantidad = cantidad;
        this.precio = precio;
        updateTotal();
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the number
     */
    public int getNumber() {
        return number;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the cantidad
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        updateTotal();
    }

    /**
     * @return the precio
     */
    public float getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(float precio) {
        this.precio = precio;
        updateTotal();
    }

    /**
     * @return the total
     */
    public float getTotal() {
        updateTotal();
        return total;
    }

    private void updateTotal() {
        total = precio * cantidad;
    }

    /**
     * @return the folio
     */
    public folio getFolio() {
        return folio;
    }

    /**
     * @param folio the folio to set
     */
    public void setFolio(folio folio) {
        this.folio = folio;
    }
}
