package web.CayvaCaCanh.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import web.CayvaCaCanh.model.Category;
import web.CayvaCaCanh.model.Product;
import web.CayvaCaCanh.model.Supplier;

import java.util.List;


public interface SupplierService {


    List<Supplier> findAll();

    Page<Supplier> findAll(Pageable pageable);

   Supplier findById(String id);

    void save(Supplier supplier);


    Page<Supplier> findByStatusSupplier(boolean status, Pageable pageable);

    void deleteById(String id);

    boolean existsById(String id);

    void addSupplier(Supplier supplier);
   boolean existsByName(String name) ;
}
