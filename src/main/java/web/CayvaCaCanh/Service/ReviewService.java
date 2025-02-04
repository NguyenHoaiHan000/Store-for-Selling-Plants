package web.CayvaCaCanh.Service;

import web.CayvaCaCanh.model.Product;
import web.CayvaCaCanh.model.Review;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface ReviewService {

     Review findByProductId(String productId);
     long getTotalReviewedProducts();
     // tổng sản phẩm đánh giá

}
