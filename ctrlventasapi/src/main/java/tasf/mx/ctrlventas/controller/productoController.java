/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tasf.mx.ctrlventas.controller;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tasf.mx.ctrlventas.model.producto;
import tasf.mx.ctrlventas.service.productoService;

/**
 *
 * @author silot
 */
@RestController
@RequestMapping("/producto")
public class productoController {

    @Autowired
    private productoService prodService;

    @GetMapping("")
    public ResponseEntity<?> list() {
        try {
            List<producto> l = prodService.listAllProducto();
            return new ResponseEntity<>(l, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<producto> get(@PathVariable Integer id) {
        try {
            producto prod = prodService.getProducto(id);
            return new ResponseEntity<>(prod, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @PostMapping("")
//    public ResponseEntity<?> add(@RequestBody producto prod) {
//        try {
//            prodService.saveProducto(prod);
//            return new ResponseEntity<>(HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
//        }
//    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody producto prod, @PathVariable Integer id) {
        try {
            producto exist = prodService.getProducto(id);
            prodService.saveProducto(prod);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            prodService.deleteProducto(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }
}
