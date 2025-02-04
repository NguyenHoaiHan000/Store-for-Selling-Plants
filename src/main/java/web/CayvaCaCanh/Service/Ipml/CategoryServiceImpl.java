package web.CayvaCaCanh.Service.Ipml;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.CayvaCaCanh.model.Category;
import web.CayvaCaCanh.repository.CategoryRepository;
import web.CayvaCaCanh.Service.CategoryService;
import web.CayvaCaCanh.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(String id) {
        return null;
    }


    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(String id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<Category> findByStatus(boolean status) {
        return categoryRepository.findByStatus(status);
    }

    @Override
    public Category findById(String id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.orElse(null);
    }

    @Override
    public void addCategory(Category category) {
        category.setStatus(true);
        categoryRepository.save(category);
    }

    @Override
    public boolean existsByName(String name) {
        return categoryRepository.existsById(name);
    }

    public boolean existsById(String id) {
        return categoryRepository.existsById(id);
    }



    public List<Category> getCategoriesWithProductCount() {
        List<Category> categories = categoryRepository.findAll();
        List<Category> categoryCounts = categories.stream()
                .map(category -> {
                    long count = productRepository.countByCategoryId(category.getId());
                    System.out.println("Category: " + category.getName() + ", Product Count: " + count); // Logging
                    return new Category(category.getId(), category.getName(), count);
                })
                .collect(Collectors.toList());
        return categoryCounts;
    }

    @Override
    public String findMaxId() {
        return categoryRepository.findMaxId();
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
