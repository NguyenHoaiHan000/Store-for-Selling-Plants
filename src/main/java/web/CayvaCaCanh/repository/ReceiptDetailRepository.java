package web.CayvaCaCanh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import web.CayvaCaCanh.model.ReceiptDetail;
import web.CayvaCaCanh.model.ReceiptDetailPK;

import java.time.LocalDate;
import java.util.List;

public interface ReceiptDetailRepository extends JpaRepository<ReceiptDetail, ReceiptDetailPK> {
    List<ReceiptDetail> findById_Receipt(String receiptId);

    void deleteById(ReceiptDetailPK receiptDetailId);

    @Query("SELECT COUNT(rd) FROM ReceiptDetail rd WHERE rd.receipt.date BETWEEN :startDate AND :endDate")
    Long countByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    // Trong ReceiptDetailRepository
    @Query("SELECT SUM(rd.price * rd.quantity) FROM ReceiptDetail rd WHERE rd.receipt.date BETWEEN :startDate AND :endDate")
    Double sumTotalMoneyImportedByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT rd FROM ReceiptDetail rd WHERE rd.receipt.date BETWEEN :startDate AND :endDate")
    List<ReceiptDetail> findByReceiptDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}