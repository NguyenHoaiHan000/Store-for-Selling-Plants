package web.CayvaCaCanh.Controller.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.CayvaCaCanh.Service.ProductService;
import web.CayvaCaCanh.model.Product;

import java.util.List;

@Controller
@RequestMapping("/admin/productStart")
public class AdminListStartController {
    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public String getReviewedProducts(Model model) {
        List<Product> reviewedProducts = productService.getAllReviewedProducts();
        model.addAttribute("reviewedProducts", reviewedProducts);
        return "admin/listStart/list";
    }
}
