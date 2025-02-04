package web.CayvaCaCanh.Service.Ipml;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.CayvaCaCanh.Service.CartDetailService;
import web.CayvaCaCanh.model.CartDetail;
import web.CayvaCaCanh.model.CartDetailPk;
import web.CayvaCaCanh.repository.CartDetailRepository;

@Service
public class CartDetailServiceImpl implements CartDetailService {

    @Autowired
    private CartDetailRepository cartDetailRepository;

    @Override
    public CartDetail getCartDetailByIds(CartDetailPk cartDetailPK) {
        return cartDetailRepository.findById(cartDetailPK).orElse(null);
    }

    @Override
    public void save(CartDetail cartDetail) {
        cartDetailRepository.save(cartDetail);
    }

    @Override
    public void delete(CartDetail cartDetail) {
        System.out.println("Đang xóa: " + cartDetail);
        cartDetailRepository.delete(cartDetail);
    }
    @Override
    public CartDetail findByProductIdAndCartId(String productId,String cartId ) {
        return cartDetailRepository.findByProductIdAndCartId ( productId,cartId);
    }

    public CartDetail findById(CartDetailPk id) {
        return cartDetailRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy CartDetail với id: " + id));
    }
}
