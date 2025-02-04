package web.CayvaCaCanh.Controller.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.CayvaCaCanh.Controller.Method.method;
import web.CayvaCaCanh.Service.CategoryService;
import web.CayvaCaCanh.Service.ProductService;
import web.CayvaCaCanh.Service.ReviewService;
import web.CayvaCaCanh.Service.UserService;
import web.CayvaCaCanh.model.Category;
import web.CayvaCaCanh.model.Product;
import web.CayvaCaCanh.model.Review;
import web.CayvaCaCanh.model.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/user/")
public class UserProductController {
    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ReviewService reviewService;

    private Product product;

    @GetMapping("/home")
    public String listProducts(Model model) {
        List<Category> categories = categoryService.getAllCategories(); // Lấy danh sách danh mục từ service
        model.addAttribute("categories", categories);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userService.findByAccountUsername(username);

        String idKH = user.getId();

        System.out.print("mã khách hàng recomend " + idKH);
        method method = new method();
//	    System.out.println("mã khach hang đăng nhập "+ customerId);

        String listMHStr = method.getRecommendation(idKH);
        String tmp = listMHStr.replace("'", "");
        tmp = tmp.replace("[", "");
        tmp = tmp.replace("]", "");
        tmp = tmp.replace(" ", "");

        System.out.print(tmp);
        String[] tmp2 = tmp.split(",");

// ĐỀ XUẤT SẢN PHẨM CHO NGƯỜI DÙNG
        List<Product> listProductRecommend = new ArrayList<>();
        for (String productId : tmp2) {
            Product product = productService.findByID(productId);
            if (product != null) {
                listProductRecommend.add(product);
            }
        }

//         6 SẢN PHÂM BÁN CHẠY
        List<Product> topSellingProducts = productService.getTopSellingProducts(6); // Hiển thị 10 sản phẩm bán chạy nhất
        model.addAttribute("topSellingProducts", topSellingProducts);

        System.out.println(" top sản phẩm bán  chạy" + topSellingProducts);
        // Thêm danh sách sản phẩm vào model
        model.addAttribute("recommendedProducts", listProductRecommend);

        return "user/index";
    }

//GIAO DIỆN SẢN PHẨM CHO KHÁCH HÀNG


    @GetMapping("/products")
    public String getFilteredProducts(
            @RequestParam(required = false) List<String> categoryIds,
            @RequestParam(required = false) List<String> height,
            @RequestParam(required = false) List<String> difficulty,
            @RequestParam(required = false) List<String> lightRequirement,
            @RequestParam(required = false) List<String> waterRequirement,
            @RequestParam(required = false) List<String> species,
            @RequestParam(required = false) List<String> size,
            @RequestParam(required = false) List<String> lifespan,
            @RequestParam(required = false) List<Integer> hardness,
            @RequestParam(required = false) List<String> foodType,
            @RequestParam(required = false) String query,
            @PageableDefault(size = 8) Pageable pageable,
            Model model) {

        // Lấy các tùy chọn thuộc tính
        List<String> heightOptions = productService.getHeightOptions();
        List<String> difficultyOptions = productService.getDifficultyOptions();
        List<String> lightRequirementOptions = productService.getLightRequirementOptions();
        List<String> waterRequirementOptions = productService.getWaterRequirementOptions();
        List<String> speciesOptions = productService.getSpeciesOptions();
        List<String> sizeOptions = productService.getSizeOptions();
        List<String> lifespanOptions = productService.getLifespanOptions();
        List<Integer> hardnessOptions = productService.getHardnessOptions();  // Thêm phương thức để lấy các tùy chọn độ khó
        List<String> foodTypeOptions = productService.getFoodTypeOptions();
        System.out.println("Category IDs: " + categoryIds);
        System.out.println("Height: " + height);
        System.out.println("Difficulty: " + difficulty);
        System.out.println("Light Requirement: " + lightRequirement);
        System.out.println("Water Requirement: " + waterRequirement);
        System.out.println("Species: " + species);
        System.out.println("Size: " + size);
        // Lấy danh sách sản phẩm đã lọc
        List<Category> categories = categoryService.getAllCategories(); // hoặc phương thức thích hợp để lấy danh sách danh mục
        model.addAttribute("categorys", categories);
        Page<Product> products;
        if ((query == null || query.isEmpty()) && categoryIds == null && height == null && difficulty == null &&
                lightRequirement == null && waterRequirement == null &&
                species == null && size == null && lifespan == null) {
            products = productService.getAllProducts(pageable);
        } else {
            // Lấy danh sách sản phẩm đã lọc
            products = productService.getFilteredProducts(
                    query,
                    categoryIds, height, difficulty, lightRequirement, waterRequirement,
                    species, size, lifespan, hardness, foodType, pageable
            );
            System.out.println("sản phẩm lọc: " + products);
        }

        // Thêm dữ liệu vào model để hiển thị trên giao diện
        model.addAttribute("products", products);
        model.addAttribute("heightOptions", heightOptions);
        model.addAttribute("difficultyOptions", difficultyOptions);
        model.addAttribute("lightRequirementOptions", lightRequirementOptions);
        model.addAttribute("waterRequirementOptions", waterRequirementOptions);
        model.addAttribute("speciesOptions", speciesOptions);
        model.addAttribute("sizeOptions", sizeOptions);
        model.addAttribute("lifespanOptions", lifespanOptions);
        model.addAttribute("categoryIds", categoryIds);
        model.addAttribute("height", height);
        model.addAttribute("difficulty", difficulty);
        model.addAttribute("lightRequirement", lightRequirement);
        model.addAttribute("waterRequirement", waterRequirement);
        model.addAttribute("species", species);
        model.addAttribute("size", size);
        model.addAttribute("lifespan", lifespan);
        model.addAttribute("hardness", hardnessOptions);
        model.addAttribute("foodType",foodTypeOptions);
        return "user/product/product";
    }

    @GetMapping("/products/details/{id}")
    public String getProductDetails(@PathVariable String id, Model model) {
        // Lấy thông tin chi tiết sản phẩm từ service và đưa vào model
        Product product = productService.getProductById(id);
        List<Category> categories = categoryService.getAllCategories(); // Lấy danh sách danh mục từ service
        model.addAttribute("categories", categories);
        model.addAttribute("product", product);
        return "user/product/product-details"; // Trả về template cho trang chi tiết sản phẩm
    }
}