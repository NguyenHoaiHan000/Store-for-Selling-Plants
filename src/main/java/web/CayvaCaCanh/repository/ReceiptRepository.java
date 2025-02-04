package web.CayvaCaCanh.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import web.CayvaCaCanh.model.Product;
import web.CayvaCaCanh.model.Receipt;

public interface ReceiptRepository  extends JpaRepository<Receipt, String> {


}
