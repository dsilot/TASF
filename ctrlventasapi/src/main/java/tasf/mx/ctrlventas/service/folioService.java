/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tasf.mx.ctrlventas.service;

import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tasf.mx.ctrlventas.model.folio;
import tasf.mx.ctrlventas.repository.folioRepository;

/**
 *
 * @author silot
 */
@Service
@Transactional
public class folioService {

    @Autowired
    private folioRepository folioRepo;

    public List<folio> listAllFolio() {
        return folioRepo.findAll();
    }

    public void saveFolio(folio folio) {
        folioRepo.save(folio);
    }

    public folio getFolio(int id) {
        return folioRepo.findById(id).get();
    }

    public void deleteFolio(int id) {
        folioRepo.deleteById(id);
    }

}
