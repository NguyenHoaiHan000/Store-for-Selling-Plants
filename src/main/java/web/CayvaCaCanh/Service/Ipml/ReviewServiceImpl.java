package web.CayvaCaCanh.Service.Ipml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.CayvaCaCanh.Service.ReviewService;
import web.CayvaCaCanh.model.Product;
import web.CayvaCaCanh.model.Review;
import web.CayvaCaCanh.repository.ProductRepository;
import web.CayvaCaCanh.repository.ReviewRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;


    @Override
    public Review findByProductId(String productId) {
        return reviewRepository.findByProductId(productId);
    }

    public long getTotalReviewedProducts() {
        // Sử dụng Stream API để lọc các sản phẩm đã được đánh giá và đếm chúng
        Set<String> reviewedProductIds = new HashSet<>();
        reviewRepository.findAll().forEach(review -> reviewedProductIds.add(review.getProduct().getId()));
        return reviewedProductIds.size();
    }

}
