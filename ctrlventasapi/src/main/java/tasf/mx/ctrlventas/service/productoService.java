/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tasf.mx.ctrlventas.service;

import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tasf.mx.ctrlventas.model.producto;
import tasf.mx.ctrlventas.repository.productoRepository;

/**
 *
 * @author silot
 */
@Service
@Transactional
public class productoService {

    @Autowired
    private productoRepository prodRepo;

    public List<producto> listAllProducto() {
        return prodRepo.findAll();
    }

    public void saveProducto(producto prod) {      
        prodRepo.save(prod);
    }

    public producto getProducto(Integer id) {
        return prodRepo.findById(id).get();
    }

    public void deleteProducto(Integer id) {
        prodRepo.deleteById(id);
    }
      public List<producto> getProductosByFolio(int number){
        return prodRepo.findByFolioNumber(number);
    }
}
