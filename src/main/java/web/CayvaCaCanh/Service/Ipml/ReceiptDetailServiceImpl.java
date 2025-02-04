package web.CayvaCaCanh.Service.Ipml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.CayvaCaCanh.Service.ReceiptDetailService;
import web.CayvaCaCanh.model.ReceiptDetail;
import web.CayvaCaCanh.repository.ReceiptDetailRepository;

import java.util.List;

@Service
public class ReceiptDetailServiceImpl implements ReceiptDetailService {


    @Autowired
    private ReceiptDetailRepository receiptDetailRepository;

    @Override
    public List<ReceiptDetail> findByReceiptId(String receiptId) {
        return receiptDetailRepository.findById_Receipt(receiptId);
    }

    @Override
    public void save(ReceiptDetail receiptDetail) {
        receiptDetailRepository.save(receiptDetail);
    }

}
