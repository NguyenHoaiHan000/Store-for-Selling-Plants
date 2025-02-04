package web.CayvaCaCanh.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import web.CayvaCaCanh.model.Product;
import web.CayvaCaCanh.model.Supplier;

public interface SupplierRepository   extends JpaRepository<Supplier, String> {
    Page<Supplier> findByStatus(boolean status, Pageable pageable);
    boolean existsByName(String name);
}
