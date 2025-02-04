package web.CayvaCaCanh.Service;

import web.CayvaCaCanh.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();

    Category getCategoryById(String id);

    Category saveCategory(Category category);

    void deleteCategory(String id);

    List<Category> findByStatus(boolean status);

    Category findById(String id);

    boolean existsById(String id);
    boolean existsByName(String name);

    void addCategory(Category category);

// LỌC DANH MỤC SẢN PHẨM

    List<Category> getCategoriesWithProductCount();

    String findMaxId();
    List<Category> findAll();
}
