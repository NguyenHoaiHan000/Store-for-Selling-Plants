package web.CayvaCaCanh.Service;

import web.CayvaCaCanh.model.CartDetail;
import web.CayvaCaCanh.model.CartDetailPk;

public interface CartDetailService {

    CartDetail getCartDetailByIds(CartDetailPk cartDetailPK);

    void save(CartDetail cartDetail);
    void delete(CartDetail cartDetail);
    CartDetail findByProductIdAndCartId(String productId, String cartId);
    CartDetail findById(CartDetailPk id);
}
