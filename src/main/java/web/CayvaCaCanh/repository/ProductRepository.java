package web.CayvaCaCanh.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import web.CayvaCaCanh.model.Product;

import java.time.LocalDate;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findByStatus(boolean b);

    Page<Product> findByStatus(boolean status, Pageable pageable);

    //    DÙNG ĐỂ HIỆN THỊ BỘ LỌC DANH MỤC SẢN PHẨM
    @Query("SELECT COUNT(p) FROM Product p WHERE p.category.id = :categoryId")
    long countByCategoryId(String categoryId);

    // TOP 5 SẢN PHẨM ĐÁNH GIÁ CAO
    @Query("SELECT p FROM Product p ORDER BY p.number DESC")
    List<Product> findTopSellingProducts(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.status = true AND p.number > 0")
    Page<Product> findAll(Pageable pageable);

//    Page<Product> findByNumberGreaterThan(int number, Pageable pageable);
//
//    Page<Product> findByCategoryIdInAndNumberGreaterThan(List<String> categoryIds, int number, Pageable pageable);

    List<Product> findByCategoryId(String categoryId);

    @Query("SELECT p FROM Product p WHERE p.category.id IN :categoryIds")
    Page<Product> findByCategoryIds(@Param("categoryIds") List<String> categoryIds, Pageable pageable);

    //LỌC
    @Query("SELECT DISTINCT p.height FROM PlantAttributes p")
    List<String> findDistinctHeightOptions();

    @Query("SELECT DISTINCT p.difficulty FROM PlantAttributes p")
    List<String> findDistinctDifficultyOptions();

    @Query("SELECT DISTINCT p.lightRequirement FROM PlantAttributes p")
    List<String> findDistinctLightRequirementOptions();

    @Query("SELECT DISTINCT p.waterRequirement FROM PlantAttributes p")
    List<String> findDistinctWaterRequirementOptions();

    @Query("SELECT DISTINCT f.species FROM FishAttributes f")
    List<String> findDistinctSpeciesOptions();

    @Query("SELECT DISTINCT f.size FROM FishAttributes f")
    List<String> findDistinctSizeOptions();

    @Query("SELECT DISTINCT f.lifespan FROM FishAttributes f")
    List<String> findDistinctLifespanOptions();

    @Query("SELECT DISTINCT f.hardness FROM FishAttributes f")
    List<Integer> findDistinctHardnessOptions();  // Thêm phương thức này

    @Query("SELECT DISTINCT f.foodType FROM FishAttributes f")
    List<String> findDistinctFoodTypeOptions();   // Thêm phương thức này

    @Query("SELECT p FROM Product p WHERE p.category.name = :categoryName")
    List<Product> findByCategoryName(@Param("categoryName") String categoryName);
// số lượng sản phẩm trong kho

    @Query("SELECT p FROM Product p " +
            "LEFT JOIN p.plantAttributes pa " +
            "LEFT JOIN p.fishAttributes fa " +
            "WHERE p.status = true " +
            "AND p.number > 0 " +
            "AND (:query IS NULL OR p.name LIKE %:query% OR p.description LIKE %:query%) " +
            "AND (:categoryIds IS NULL OR p.category.id IN :categoryIds) " +
            "AND (:height IS NULL OR pa.height IN :height) " +
            "AND (:difficulty IS NULL OR pa.difficulty IN :difficulty) " +
            "AND (:lightRequirement IS NULL OR pa.lightRequirement IN :lightRequirement) " +
            "AND (:waterRequirement IS NULL OR pa.waterRequirement IN :waterRequirement) " +
            "AND (:species IS NULL OR fa.species IN :species) " +
            "AND (:size IS NULL OR fa.size IN :size) " +
            "AND (:lifespan IS NULL OR fa.lifespan IN :lifespan) " +
            "AND (:hardness IS NULL OR fa.hardness IN :hardness) " +
            "AND (:foodType IS NULL OR fa.foodType IN :foodType)")
    Page<Product> findFilteredProducts(
            @Param("query") String query,
            @Param("categoryIds") List<String> categoryIds,
            @Param("height") List<String> height,
            @Param("difficulty") List<String> difficulty,
            @Param("lightRequirement") List<String> lightRequirement,
            @Param("waterRequirement") List<String> waterRequirement,
            @Param("species") List<String> species,
            @Param("size") List<String> size,
            @Param("lifespan") List<String> lifespan,
            @Param("hardness") List<Integer> hardness,
            @Param("foodType") List<String> foodType,
            Pageable pageable);


    //    LƯU THUỘC TÍNH
    Product findByName(String name);

    //    DANH SÁCH CÁC SẢN PHẨM ĐƯỢC ĐÁNH GIÁ
    @Query("SELECT p FROM Product p WHERE SIZE(p.listReview) > 0")
    List<Product> findReviewedProducts();


    @Query("SELECT COUNT(r) FROM Review r WHERE r.product.id = :productId")
    long countReviewsByProductId(@Param("productId") String productId);

    // Tìm kiếm sản phẩm theo tên với trạng thái là true
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) AND p.status = true")
    Page<Product> findByNameContainingIgnoreCase(@Param("keyword") String keyword, Pageable pageable);

    // Tìm kiếm sản phẩm theo tên và danh mục với trạng thái là true
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) AND p.category.id = :categoryId AND p.status = true")
    Page<Product> findByNameContainingIgnoreCaseAndCategoryId(@Param("keyword") String keyword, @Param("categoryId") String categoryId, Pageable pageable);

    // Tìm kiếm sản phẩm theo danh mục với trạng thái là true
    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId AND p.status = true")
    Page<Product> findByCategoryId(@Param("categoryId") String categoryId, Pageable pageable);

}
