/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tasf.mx.ctrlventas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.List;

/**
 *
 * @author silot
 */
@Entity
@Table(name = "folios")
public class folio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "number", nullable = false, unique = true)
    private int number;
//    @Transient
//    private int totalprod;
//    @Transient
//    private int totalventa;
//    @OneToMany(mappedBy = "folio")
//    private List<producto> productos;

    public folio() {
    }

    public folio(int number) {
        this.number = number;
//        this.totalprod = 0;
//        this.totalventa = 0;
    }

    /**
     * @return the folio number
     */
    public int getNumber() {
        return number;
    }

    /**
     * @param number the number to set
     */
    public void setNumber(int number) {
        this.number = number;
    }

//    /**
//     * @return the totalprod
//     */
//    public int getTotalprod() {
//        return totalprod;
//    }
//
//    /**
//     * @return the totalventa
//     */
//    public int getTotalventa() {
//        return totalventa;
//    }
//
//    /**
//     * @return the productos
//     */
//    public List<producto> getProductos() {
//        return productos;
//    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

//    public void update() {
//        totalprod = productos.size();
//        for (producto p : productos) {
//            totalventa += p.getTotal();
//            //System.out.println("Total producto:" + p.getTotal());
//        }
//    }

}
