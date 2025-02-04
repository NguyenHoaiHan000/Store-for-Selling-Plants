package web.CayvaCaCanh.Service.Ipml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.CayvaCaCanh.Service.PayService;
import web.CayvaCaCanh.model.Pay;
import web.CayvaCaCanh.repository.PayRepository;

@Service
public class PayServiceImpl  implements PayService {

    @Autowired
    private PayRepository paysRepository;

    @Override
    public Pay getPaysById(String payId) {
        return paysRepository.findById(payId).orElse(null);
    }
}
