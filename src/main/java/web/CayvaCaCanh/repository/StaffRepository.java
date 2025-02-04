package web.CayvaCaCanh.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import web.CayvaCaCanh.model.Staffs;

import java.util.List;

public interface StaffRepository extends JpaRepository<Staffs, String> {
    @Query("SELECT s FROM Staffs s WHERE s.account.status = :status")
    Page<Staffs> findAllByAccountStatus(@Param("status") boolean status, Pageable pageable);

    Staffs findByAccount_Username(String username);

}
