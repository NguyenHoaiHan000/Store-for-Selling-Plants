package web.CayvaCaCanh.Service;

import web.CayvaCaCanh.model.ReceiptDetail;

import java.util.List;

public interface ReceiptDetailService {

    List<ReceiptDetail> findByReceiptId(String receiptId);
    void save(ReceiptDetail receiptDetail);


}
