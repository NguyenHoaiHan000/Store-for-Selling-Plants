package web.CayvaCaCanh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import web.CayvaCaCanh.model.Category;

import java.util.List;

public interface CategoryRepository  extends JpaRepository<Category, String> {

    List<Category> findByStatus(boolean b);

    List<Category> findByNameContaining(String name);

    @Query("SELECT MAX(c.id) FROM Category c")
    String findMaxId();
}
