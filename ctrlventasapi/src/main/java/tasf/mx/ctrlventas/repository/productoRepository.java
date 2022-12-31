/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tasf.mx.ctrlventas.repository;

import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tasf.mx.ctrlventas.model.producto;


/**
 *
 * @author silot
 */
public interface productoRepository extends JpaRepository<producto, Integer> {
 @Transactional
  void deleteByFolioId(int folio_id);

 List<producto> findByFolioNumber(int number);
}
