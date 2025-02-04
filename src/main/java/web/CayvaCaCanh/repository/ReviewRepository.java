package web.CayvaCaCanh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.CayvaCaCanh.model.Review;
import web.CayvaCaCanh.model.ReviewId;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, ReviewId> {
    Review findByProductId(String productId);

//    List<Review> findByProductId(String productId);

    int countByProductId(String productId);

}