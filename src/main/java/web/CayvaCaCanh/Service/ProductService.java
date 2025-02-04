package web.CayvaCaCanh.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import web.CayvaCaCanh.model.Category;
import web.CayvaCaCanh.model.Product;

import java.util.List;


public interface ProductService {

    List<Product> findByStatus(boolean status);

    Page<Product> findAll(Pageable pageable);

    Product findByID(String id);

    Product saveProduct(Product product);

    Product deleteProduct(Product product);

    void addProduct(Product product);

    Page<Product> findByStatusAndPage(boolean status, Pageable pageable);

    List<Product> getAllProducts();

    Product getProductById(String productId);

    //    void saveProduct(Product product);
    void deleteProduct(String productId);

    Page<Product> getAllProducts(Pageable pageable);


    List<Product> findByCategoryId(String categoryId);

    //    SẢN PHẨM MUA NHIỀU
    List<Product> getTopSellingProducts(int topN);

    // tổng số sản phẩm
    long getTotalProducts();

    //LƯU THUỘC TÍNH
    Product findByName(String name);

    List<Product> getProductsByCategory(String categoryName);
    //lỌC
    Page<Product> getFilteredProducts(
            String query,
            List<String> categoryIds,
            List<String> height,
            List<String> difficulty,
            List<String> lightRequirement,
            List<String> waterRequirement,
            List<String> species,
            List<String> size,
            List<String> lifespan,
            List<Integer> hardness,
            List<String> foodType,
            Pageable pageable);

    List<String> getHeightOptions();

    List<String> getDifficultyOptions();

    List<String> getLightRequirementOptions();

    List<String> getWaterRequirementOptions();

    List<String> getSpeciesOptions();

    List<String> getSizeOptions();

    List<String> getLifespanOptions();

    List<Integer> getHardnessOptions();
    // Thêm phương thức này
    List<String> getFoodTypeOptions();

    //    DANH SÁCH SẢN PHẨM ĐÁNH GIÁ
    List<Product> getAllReviewedProducts();

    Page<Product> getPagedReviewedProducts(int page, int size);

    long getReviewCountByProductId(String productId);

    Page<Product> searchByKeyword(String keyword, Pageable pageable);
    Page<Product> searchByKeywordAndCategory(String keyword, String  categoryId, Pageable pageable);
    Page<Product> findByCategory(String  categoryId, Pageable pageable);

}
