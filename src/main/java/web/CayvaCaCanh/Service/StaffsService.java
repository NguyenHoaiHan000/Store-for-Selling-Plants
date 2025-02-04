package web.CayvaCaCanh.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import web.CayvaCaCanh.model.Product;
import web.CayvaCaCanh.model.Staffs;

import java.util.Optional;

public interface StaffsService {

    Page<Staffs> findByStatusAndPage(boolean status, Pageable pageable);

    Optional<Staffs> findByIdStaff(String id);

    Staffs findById (String id);
    Staffs saveStaff(Staffs staff);
    Staffs findByUsername(String username);

}
