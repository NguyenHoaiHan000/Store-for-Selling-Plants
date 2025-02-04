package web.CayvaCaCanh.Service.Ipml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.CayvaCaCanh.Service.OrderDetailService;
import web.CayvaCaCanh.model.OrderDetail;
import web.CayvaCaCanh.model.OrderDetailPK;
import web.CayvaCaCanh.repository.OrderDetailRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {


    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public List<OrderDetail> getOrderDetailsByOrderId(Long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }


    public List<OrderDetail> findOrderDetailsByOrderId(Long id) {
        return orderDetailRepository.findByOrderId(id);
    }

    public void deleteOrderDetail(OrderDetailPK id) {
        if (orderDetailRepository.existsById(id)) {
            orderDetailRepository.deleteById(id);
        }
    }

}
