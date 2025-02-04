package web.CayvaCaCanh.Controller.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.CayvaCaCanh.Service.FishAttributeService;
import web.CayvaCaCanh.Service.ProductService;
import web.CayvaCaCanh.model.FishAttributes;
import web.CayvaCaCanh.model.Product;

import java.util.List;

@Controller
@RequestMapping("/admin/fish-attributes")
public class AdminFishController {

    @Autowired
    private FishAttributeService fishAttributesService;

    @Autowired

    private ProductService productService;
    @GetMapping("/list")
    public String listFishAttributes(Model model) {
        model.addAttribute("fishAttributes", fishAttributesService.getAllAttributes());
        return "admin/fishAttributes/list";
    }
    @GetMapping("/add")
    public String showAddForm(Model model) {

        model.addAttribute("fishAttributes", new FishAttributes());
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products",products);
        return "admin/fishAttributes/add";
    }

    @PostMapping("/save")
    public String saveFishAttributes(@ModelAttribute FishAttributes fishAttributes) {
        fishAttributesService.saveAttributes(fishAttributes);
        return "redirect:/admin/fish-attributes/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        FishAttributes fishAttributes = fishAttributesService.getAttributesById(id);
        if (fishAttributes == null) {
            return "redirect:/admin/fish-attributes/list";
        }
        model.addAttribute("fishAttributes", fishAttributes);
        model.addAttribute("products", productService.getAllProducts());
        return "admin/fishAttributes/edit";
    }


    @GetMapping("/delete/{id}")
    public String deleteFishAttributes(@PathVariable("id") Long id) {
        fishAttributesService.deleteAttributesById(id);
        return "redirect:/admin/fish-attributes/list";
    }
}
