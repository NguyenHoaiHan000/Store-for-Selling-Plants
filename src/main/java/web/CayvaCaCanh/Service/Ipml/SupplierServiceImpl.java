package web.CayvaCaCanh.Service.Ipml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import web.CayvaCaCanh.Service.SupplierService;
import web.CayvaCaCanh.repository.SupplierRepository;
import web.CayvaCaCanh.model.Supplier;
import java.util.List;


@Service
public class SupplierServiceImpl  implements SupplierService {
    private SupplierRepository supplierRepository;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public List<Supplier> findAll() {
        return supplierRepository.findAll();
    }

    @Override
    public Page<Supplier> findAll(Pageable pageable) {
        return supplierRepository.findAll(pageable);
    }

    @Override
    public Supplier findById(String id) {
        return supplierRepository.findById(id).orElse(null);
    }


    @Override
    public void save(Supplier supplier) {
        supplierRepository.save(supplier);
    }

    @Override
    public Page<Supplier> findByStatusSupplier(boolean status, Pageable pageable) {
        return supplierRepository.findByStatus(status, pageable);
    }

    @Override
    public void deleteById(String id) {
        supplierRepository.deleteById(id);
    }

    @Override
    public boolean existsById(String id) {
        return supplierRepository.existsById(id);
    }
// THÊM NHÀ CUNG CẤP VỚI TRẠNG THÁI LÀ TRUE
    @Override
    public void addSupplier(Supplier supplier) {
        supplier.setStatus(true);
       supplierRepository.save(supplier);
    }

    public boolean existsByName(String name) {
        return supplierRepository.existsByName(name);
    }
}
