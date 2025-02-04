package web.CayvaCaCanh.Service;

import web.CayvaCaCanh.model.OrderDetail;
import web.CayvaCaCanh.model.OrderDetailPK;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface OrderDetailService {

    List<OrderDetail> getOrderDetailsByOrderId(Long orderId);


    List<OrderDetail> findOrderDetailsByOrderId(Long id) ;

    void deleteOrderDetail(OrderDetailPK id);
}
