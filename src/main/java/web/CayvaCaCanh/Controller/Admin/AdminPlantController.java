package web.CayvaCaCanh.Controller.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.CayvaCaCanh.Service.PlantAttributeService;
import web.CayvaCaCanh.Service.ProductService;
import web.CayvaCaCanh.model.PlantAttributes;
import web.CayvaCaCanh.model.Product;

import java.util.List;

@Controller
@RequestMapping("/admin/plant-attributes")
public class AdminPlantController {
    @Autowired
    private PlantAttributeService plantAttributesService;

    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public String listPlantAttributes(Model model) {
        model.addAttribute("plantAttributes", plantAttributesService.findAll());
        return "admin/ PlantAttributes/plant";
    }

    @GetMapping("/add")
    public String addPlantAttributesForm(Model model) {
        model.addAttribute("plantAttributes", new PlantAttributes());
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products",products);
        return "admin/ PlantAttributes/add";
    }

    @PostMapping("/save")
    public String savePlantAttributes(@ModelAttribute PlantAttributes plantAttributes) {
        plantAttributesService.saveAttributes(plantAttributes);
        return "redirect:/admin/plant-attributes/list";
    }

    @GetMapping("/edit/{id}")
    public String editPlantAttributes(@PathVariable("id") Long id, Model model) {
        PlantAttributes plantAttributes = plantAttributesService.getAttributesById(id);
        model.addAttribute("plantAttributes", plantAttributes);
        return "admin/ PlantAttributes/edit";
    }
}
