package web.CayvaCaCanh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.CayvaCaCanh.model.Pay;
import web.CayvaCaCanh.model.PlantAttributes;
import web.CayvaCaCanh.model.ReceiptDetail;
import web.CayvaCaCanh.model.ReceiptDetailPK;

public interface PlantAttributesRepository  extends JpaRepository<PlantAttributes, Long> {
}
