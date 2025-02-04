package web.CayvaCaCanh.Service.Ipml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import web.CayvaCaCanh.Service.ProductService;
import web.CayvaCaCanh.Service.ReviewService;
import web.CayvaCaCanh.model.Product;
import web.CayvaCaCanh.repository.CategoryRepository;
import web.CayvaCaCanh.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Product> findByStatus(boolean status) {
        return productRepository.findByStatus(status);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable)
                ;
    }

    @Override
    public Product findByID(String id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);
    }

    @Override
    public Product saveProduct(Product product) {
        product.setStatus(true);
        return productRepository.save(product);
    }

    @Override
    public Product deleteProduct(Product product) {
        return productRepository.save(product);
    }


    @Override
    public Page<Product> findByStatusAndPage(boolean status, Pageable pageable) {
        return productRepository.findByStatus(status, pageable);
    }


    @Override
    public void addProduct(Product product) {
        product.setStatus(true);
        product.setNumber(0);
        productRepository.save(product);
    }


    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(String productId) {
        return productRepository.findById(productId).orElse(null);
    }

    @Override
    public void deleteProduct(String productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }


    @Override
    public List<Product> findByCategoryId(String categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    @Override
    public List<Product> getTopSellingProducts(int topN) {
        Pageable pageable = PageRequest.of(0, topN);
        return productRepository.findTopSellingProducts(pageable);
    }

    //    lỌC THEO CÁ VÀ CÂY
    @Override
    public Page<Product> getFilteredProducts(
            String query,
            List<String> categoryIds,
            List<String> height,
            List<String> difficulty,
            List<String> lightRequirement,
            List<String> waterRequirement,
            List<String> species,
            List<String> size,
            List<String> lifespan,
            List<Integer> hardness,  // Thêm loại dữ liệu cho độ khó của cá
            List<String> foodType,   // Thêm loại thức ăn của cá
            Pageable pageable) {

        return productRepository.findFilteredProducts(
                query,
                categoryIds,
                height,
                difficulty,
                lightRequirement,
                waterRequirement,
                species,
                size,
                lifespan,
                hardness,
                foodType,
                pageable);
    }


    @Override
    public List<String> getHeightOptions() {
        return productRepository.findDistinctHeightOptions();
    }

    @Override
    public List<String> getDifficultyOptions() {
        return productRepository.findDistinctDifficultyOptions();
    }

    @Override
    public List<String> getLightRequirementOptions() {
        return productRepository.findDistinctLightRequirementOptions();
    }

    @Override
    public List<String> getWaterRequirementOptions() {
        return productRepository.findDistinctWaterRequirementOptions();
    }

    @Override
    public List<String> getSpeciesOptions() {
        return productRepository.findDistinctSpeciesOptions();
    }

    @Override
    public List<String> getSizeOptions() {
        return productRepository.findDistinctSizeOptions();
    }

    @Override
    public List<String> getLifespanOptions() {
        return productRepository.findDistinctLifespanOptions();
    }
    @Override
    public List<Product> getProductsByCategory(String categoryName) {
        // Giả sử bạn có phương thức trong repository để lấy sản phẩm theo danh mục
        return productRepository.findByCategoryName(categoryName);
    }
    @Override
    public List<Integer> getHardnessOptions() {
        return productRepository.findDistinctHardnessOptions();
    }

    @Override
    public List<String> getFoodTypeOptions() {
        return productRepository.findDistinctFoodTypeOptions();
    }

    //     tổng số sản phẩm
    public long getTotalProducts() {
        return productRepository.count(); // Lấy tổng số sản phẩm
    }

    public Product findByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getAllReviewedProducts() {
        return productRepository.findReviewedProducts();
    }

    @Override
    public Page<Product> getPagedReviewedProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }

    @Override
    public long getReviewCountByProductId(String productId) {
        return productRepository.countReviewsByProductId(productId);
    }

    @Override
    public Page<Product> searchByKeyword(String keyword, Pageable pageable) {
        return productRepository.findByNameContainingIgnoreCase(keyword, pageable);
    }

    @Override
    public Page<Product> searchByKeywordAndCategory(String keyword, String  categoryId, Pageable pageable) {
        return productRepository.findByNameContainingIgnoreCaseAndCategoryId(keyword, categoryId, pageable);
    }

    @Override
    public Page<Product> findByCategory(String categoryId, Pageable pageable) {
        return productRepository.findByCategoryId(categoryId, pageable);
    }
}
