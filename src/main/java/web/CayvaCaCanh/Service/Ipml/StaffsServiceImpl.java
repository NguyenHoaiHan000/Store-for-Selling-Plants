package web.CayvaCaCanh.Service.Ipml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import web.CayvaCaCanh.Service.StaffsService;

import web.CayvaCaCanh.model.Product;
import web.CayvaCaCanh.model.Staffs;
import web.CayvaCaCanh.repository.StaffRepository;

import java.util.Optional;

@Service
public class StaffsServiceImpl   implements StaffsService {

    @Autowired
    private StaffRepository staffRepository ;

    //DÙNG ĐỂ TÌM KIẾM NHÂN VIÊN CÓ TRẠNG THÁI LÀ TRUE Ở TÀI KHOẢN
    @Override
    public Page<Staffs> findByStatusAndPage(boolean status, Pageable pageable) {
        return staffRepository.findAllByAccountStatus(status,pageable);
    }

    @Override
    public Optional<Staffs> findByIdStaff(String id) {
        return staffRepository.findById(id);
    }

//    @Override
//    public Optional<Staffs> findById(String id) {
//        return staffRepository.findById(id);
//    }

    public Staffs findById(String id) {
        Optional<Staffs> staffs = staffRepository.findById(id);
        return  staffs.orElse(null);
    }

    @Override
    public Staffs saveStaff(Staffs staff) {
        return  staffRepository.save(staff);
    }

    @Override
    public Staffs findByUsername(String username) {
        return staffRepository.findByAccount_Username(username);
    }

}
