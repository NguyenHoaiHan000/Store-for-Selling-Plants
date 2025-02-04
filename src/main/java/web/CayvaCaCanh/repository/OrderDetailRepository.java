package web.CayvaCaCanh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import web.CayvaCaCanh.model.OrderDetail;
import web.CayvaCaCanh.model.OrderDetailPK;

import java.time.LocalDate;
import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailPK> {

    List<OrderDetail> findByOrderId(Long orderId);

    @Query("SELECT COALESCE(SUM(od.quantity * od.price), 0) FROM OrderDetail od WHERE od.order.date BETWEEN :startDate AND :endDate")
    Double sumTotalMoneySoldByDateRange(LocalDate startDate, LocalDate endDate);
    // Trong OrderDetailRepository

    @Query("SELECT COUNT(od) FROM OrderDetail od WHERE od.order.date BETWEEN :startDate AND :endDate")
    Long countByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT od FROM OrderDetail od WHERE od.order.date BETWEEN :startDate AND :endDate")
    List<OrderDetail> findByOrderDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
