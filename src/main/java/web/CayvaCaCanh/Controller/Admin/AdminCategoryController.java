package web.CayvaCaCanh.Controller.Admin;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import web.CayvaCaCanh.Service.CategoryService;
import web.CayvaCaCanh.Service.ProductService;
import web.CayvaCaCanh.model.Category;
import web.CayvaCaCanh.model.Product;

import java.util.List;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    // Hiển thị danh sách danh mục
    @GetMapping("")
    public String showCategories(Model model) {
        List<Category> categories = categoryService.findByStatus(true);
        model.addAttribute("categories", categories);
        return "admin/categories/index"; // Trả về tên view Thymeleaf (index.html)
    }

    // Hiển thị form thêm mới danh mục
    @GetMapping("/add")
    public String showAddCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "admin/categories/add";
    }

    // Xử lý thêm mới danh mục
    @PostMapping("/add")
    public String addCategory(@Valid @ModelAttribute("category") Category category, BindingResult bindingResult) {
        if (category.getName().isEmpty()) {
            return "/admin/categories?error=Tên danh mục trống";
        }

        if (bindingResult.hasErrors()) {
            return "/admin/categories?error=Dữ liệu không hợp lệ";
        }

        if (categoryService.existsByName(category.getName())) {
            return "/admin/categories?error=Tên danh mục đã tồn tại";
        }

        String maxId = categoryService.findMaxId();
        int newId = (maxId != null) ? Integer.parseInt(maxId) + 1 : 1;

        System.out.println("mã danh mục"+newId);
        category.setId(String.valueOf(newId));
        categoryService.addCategory(category);

        return  "redirect:/admin/categories"; // Trả về URL để tải lại trang danh mục
    }

    // Hiển thị form sửa đổi danh mục
    @GetMapping("/edit/{id}")
    public String showEditCategoryForm(@PathVariable("id") String id, Model model) {
        Category category = categoryService.findById(id);
        if (category == null) {
            return "redirect:/admin/categories";
        }
        model.addAttribute("category", category);
        return "admin/categories/edit";
    }

    // Xử lý cập nhật danh mục
    @PostMapping("/edit")
    public String updateCategory(@RequestParam("id") String id,
                                 @RequestParam("name") String name                              ,
                                 Model model) {
        Category category = categoryService.findById(id);
        if (category != null) {
            category.setName(name);// Assuming Category has a setStatus method
            categoryService.saveCategory(category);
        }
        return "redirect:/admin/categories";
    }


    //    XÓA DANH MỤC
    @PostMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
        Category category = categoryService.findById(id);
        if (category != null) {
            // Kiểm tra nếu danh mục đã có sản phẩm
            List<Product> products = productService.findByCategoryId(id);
            if (!products.isEmpty()) {
                // Thêm thông báo lỗi khi danh mục có sản phẩm
                redirectAttributes.addFlashAttribute("errorMessage", "Không thể xóa danh mục này vì đã có sản phẩm thuộc danh mục.");
                return "redirect:/admin/categories";
            } else {
                // Đổi trạng thái của danh mục thành không hoạt động
                category.setStatus(false);
                categoryService.saveCategory(category);
                redirectAttributes.addFlashAttribute("successMessage", "Danh mục đã được đổi trạng thái thành không hoạt động.");
            }
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy danh mục cần xóa.");
        }
        return "redirect:/admin/categories";
    }

}
