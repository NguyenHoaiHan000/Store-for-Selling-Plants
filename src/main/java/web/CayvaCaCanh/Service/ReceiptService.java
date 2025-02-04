package web.CayvaCaCanh.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import web.CayvaCaCanh.model.ProductStatistic;
import web.CayvaCaCanh.model.Receipt;
import web.CayvaCaCanh.model.ReceiptDetail;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReceiptService {


    Page<Receipt> findAllReceipt(Pageable pageable);
//    Receipt saveReceipt(Receipt receipt);


    Optional<Receipt> findById(String id);

    Receipt saveReceipt(Receipt receipt);


    Receipt getReceiptById(String receiptId);

    List<Receipt> getAllReceipts();


    void updateTotalCost(Receipt receipt);

    void addReceiptAndDetail(Receipt receipt, ReceiptDetail detail);


    List<ProductStatistic> getProductStatistics(LocalDate startDate, LocalDate endDate);

    int getTotalInventory(LocalDate startDate, LocalDate endDate);
}
