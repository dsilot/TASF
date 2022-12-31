/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tasf.mx.ctrlventas.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tasf.mx.ctrlventas.model.folio;
import tasf.mx.ctrlventas.model.producto;
import tasf.mx.ctrlventas.service.folioService;
import tasf.mx.ctrlventas.service.productoService;

/**
 *
 * @author silot
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/folio")
public class folioController {

    @Autowired
    private folioService folioService;

    @Autowired
    private productoService prodService;

    @GetMapping("")
    public List<folio> list() {
        return folioService.listAllFolio();
    }

    @GetMapping("/{id}")
    public ResponseEntity<folio> get(@PathVariable Integer id) {
        try {
            folio folio = folioService.getFolio(id);
            return new ResponseEntity<>(folio, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    public void add(@RequestBody folio folio) {
        folioService.saveFolio(folio);
    }

    @PostMapping("/{id}/producto")
    public void addProducto(@RequestBody producto p, @PathVariable Integer id) {
        folio folio = folioService.getFolio(id);
        producto _p = new producto(p.getNumber(),p.getName(), p.getCantidad(), p.getPrecio());
        _p.setFolio(folio);
        prodService.saveProducto(_p);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody folio folio, @PathVariable Integer id) {
        try {
            folio exist = folioService.getFolio(id);
            folioService.saveFolio(folio);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        folioService.deleteFolio(id);
    }
//ver este método

    @GetMapping("/venta/{number}")
    public ResponseEntity<?> getProd(@PathVariable Integer number) {
        try {
            List<producto> p = prodService.getProductosByFolio(number);
            //List<Object> list = new ArrayList<>();
            Map<String,Object> list = new HashMap<>();
            int totalprod = p.size();
            list.put("totalprod",totalprod);
            int totalventa = 0;
            for (producto pro : p) {
                totalventa += pro.getTotal();
            }
            list.put("totalventa",totalventa);
            list.put("productos",p);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        //  return folioService.getProductList(number);
    }

}
