package web.CayvaCaCanh.Controller.Admin;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import web.CayvaCaCanh.Controller.Method.method;
import web.CayvaCaCanh.Service.CategoryService;
import web.CayvaCaCanh.Service.FishAttributeService;
import web.CayvaCaCanh.Service.PlantAttributeService;
import web.CayvaCaCanh.Service.ProductService;
import web.CayvaCaCanh.model.Category;
import web.CayvaCaCanh.model.FishAttributes;
import web.CayvaCaCanh.model.PlantAttributes;
import web.CayvaCaCanh.model.Product;
import web.CayvaCaCanh.repository.FishAttributesRepository;
import web.CayvaCaCanh.repository.PlantAttributesRepository;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/product")

public class AdminProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PlantAttributeService plantAttributeService;
    @Autowired
    private FishAttributeService fishAttributeService;

    @Autowired
    private PlantAttributesRepository plantAttributesRepository;
    @Autowired
    private FishAttributesRepository fishAttributesRepository;
//    @GetMapping("")
//    public String showCategories(Model model, @RequestParam(defaultValue = "0") int page,
//                                 @RequestParam(defaultValue = "6") int size) {
//        Page<Product> productPage = productService.findByStatusAndPage(true, PageRequest.of(page, size));
//        model.addAttribute("products", productPage.getContent());
//        model.addAttribute("currentPage", productPage.getNumber());
//        model.addAttribute("totalPages", productPage.getTotalPages());
//        model.addAttribute("totalItems", productPage.getTotalElements());
//        return "admin/product/list"; // Trả về tên view Thymeleaf (index.html)
//
//
//    }

    @GetMapping("")
    public String showCategories(Model model,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "6") int size,
                                 @RequestParam(required = false) String keyword,
                                 @RequestParam(required = false) String categoryId) {
        // Lấy danh sách tất cả danh mục
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("selectedCategoryId", categoryId);

        // Các phần xử lý tìm kiếm, lọc và phân trang như đã đề cập trước đó...
        if (keyword != null && !keyword.isEmpty()) {
            Page<Product> productPage;
            if (categoryId != null) {
                productPage = productService.searchByKeywordAndCategory(keyword, categoryId, PageRequest.of(page, size));
            } else {
                productPage = productService.searchByKeyword(keyword, PageRequest.of(page, size));
            }
            model.addAttribute("products", productPage.getContent());
            model.addAttribute("currentPage", productPage.getNumber());
            model.addAttribute("totalPages", productPage.getTotalPages());
            model.addAttribute("totalItems", productPage.getTotalElements());
        } else {
            Page<Product> productPage;
            if (categoryId != null) {
                productPage = productService.findByCategory(categoryId, PageRequest.of(page, size));
            } else {
                productPage = productService.findByStatusAndPage(true, PageRequest.of(page, size));
            }
            model.addAttribute("products", productPage.getContent());
            model.addAttribute("currentPage", productPage.getNumber());
            model.addAttribute("totalPages", productPage.getTotalPages());
            model.addAttribute("totalItems", productPage.getTotalElements());
        }

        // Thêm từ khóa tìm kiếm vào model để giữ lại giá trị trên form tìm kiếm
        model.addAttribute("keyword", keyword);

        return "admin/product/list"; // Trả về view danh sách sản phẩm
    }

    @GetMapping("/{page}/{size}")
    public ResponseEntity<Page<Product>> getProducts(@PathVariable int page, @PathVariable int size) {
        Page<Product> products = productService.findAll(PageRequest.of(page, size));
        return ResponseEntity.ok(products);
    }

    //     HIỂN  THỊ FORM  CHỈNH SỬA PRODUCT VÀ CÁC ITEM CATEGORY
    @GetMapping("/edit/{id}")
    public String showEditProductForm(@PathVariable("id") String id, Model model) {
        Product existingProduct = productService.findByID(id);
        if (existingProduct == null) {
            return "redirect:/admin/product";
        }
        List<Category> categories = categoryService.findByStatus(true);
        model.addAttribute("product", existingProduct);
        model.addAttribute("categories", categories);
        return "admin/product/edit";
    }


    // Cập nhật thông tin sản phẩm
    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable("id") String id,
                                @RequestParam("file") MultipartFile file,
                                @ModelAttribute Product product,
                                RedirectAttributes redirectAttributes) throws IOException {

        Product existingProduct = productService.findByID(id);
        if (existingProduct == null) {
            // Xử lý khi không tìm thấy sản phẩm
            return "redirect:/admin/product";
        }

        // Cập nhật thông tin sản phẩm từ form nhập vào
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setNumber(product.getNumber());

        Category existingCategory = categoryService.findById(product.getCategory().getId());
        if (existingCategory != null) {
            existingProduct.setCategory(existingCategory);
        }

        // Xử lý tải lên ảnh mới nếu có
        if (!file.isEmpty()) {
            String imageUrl = uploadImageToFirebaseStorage(file);
            existingProduct.setImage(imageUrl);
        }


        productService.saveProduct(existingProduct);
        // Thông báo thành công
        redirectAttributes.addFlashAttribute("successMessage", "Sản phẩm đã được cập nhật thành công.");

        return "redirect:/admin/product";
    }


    // Hiển thị form thêm sản phẩm mới
    @GetMapping("/add")
    public String showAddProductForm(Model model) {
        List<Category> categories = categoryService.findByStatus(true);
        List<PlantAttributes> plantAttributes = plantAttributeService.findAll();

        List<String> heightOptions = plantAttributes.stream().map(PlantAttributes::getHeight).distinct().collect(Collectors.toList());
        List<String> difficultyOptions = plantAttributes.stream().map(PlantAttributes::getDifficulty).distinct().collect(Collectors.toList());
        List<String> lightOptions = plantAttributes.stream().map(PlantAttributes::getLightRequirement).distinct().collect(Collectors.toList());
        List<String> waterOptions = plantAttributes.stream().map(PlantAttributes::getWaterRequirement).distinct().collect(Collectors.toList());

        model.addAttribute("heightOptions", heightOptions);
        model.addAttribute("difficultyOptions", difficultyOptions);
        model.addAttribute("lightOptions", lightOptions);
        model.addAttribute("waterOptions", waterOptions);
        List<FishAttributes> fishAttributes = fishAttributeService.findAll();

        List<String> speciesOptions = fishAttributes.stream()
                .map(FishAttributes::getSpecies)
                .distinct()
                .collect(Collectors.toList());
        List<String> sizeOptions = fishAttributes.stream()
                .map(FishAttributes::getSize)
                .distinct()
                .collect(Collectors.toList());
        List<String> lifespanOptions = fishAttributes.stream()
                .map(FishAttributes::getLifespan)
                .distinct()
                .collect(Collectors.toList());
        List<Integer> hardnessOptions = fishAttributes.stream()
                .map(FishAttributes::getHardness)
                .distinct()
                .collect(Collectors.toList());
        List<String> foodTypeOptions = fishAttributes.stream()
                .map(FishAttributes::getFoodType)
                .distinct()
                .collect(Collectors.toList());
        model.addAttribute("speciesOptions", speciesOptions);
        model.addAttribute("sizeOptions", sizeOptions);
        model.addAttribute("lifespanOptions", lifespanOptions);
        model.addAttribute("hardnessOptions", hardnessOptions);
        model.addAttribute("foodTypeOptions", foodTypeOptions);

        model.addAttribute("plantAttributes", plantAttributes);
        model.addAttribute("fishAttributes", fishAttributes);
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categories);
        return "admin/product/add"; // Trả về view Thymeleaf để hiển thị form thêm sản phẩm mới
    }


    @PostMapping("/add")
    public String addProduct(@RequestParam("file") MultipartFile file,
                             @ModelAttribute Product product,
                             RedirectAttributes redirectAttributes, Model model,
                             @RequestParam("category.id") String categoryId,
                             @RequestParam(value = "height", required = false) List<String> heightOptions,
                             @RequestParam(value = "difficulty", required = false) List<String> difficultyOptions,
                             @RequestParam(value = "lightRequirement", required = false) List<String> lightOptions,
                             @RequestParam(value = "waterRequirement", required = false) List<String> waterOptions,
                             @RequestParam(value = "speciesOptions", required = false) List<String> speciesOptions,
                             @RequestParam(value = "sizeOptions", required = false) List<String> sizeOptions,
                             @RequestParam(value = "lifespanOptions", required = false) List<String> lifespanOptions,
                             @RequestParam(value = "hardnessOptions", required = false) List<Integer> hardnessOptions,
                             @RequestParam(value = "foodTypeOptions", required = false) List<String> foodTypeOptions) throws IOException {

        // Kiểm tra tên và giá sản phẩm
        if (product.getName() == null || product.getName().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Tên sản phẩm không được để trống.");
            return "redirect:/admin/product/add";
        }

        if (product.getPrice() <= 0) {
            redirectAttributes.addFlashAttribute("errorMessage", "Giá sản phẩm phải lớn hơn 0.");
            return "redirect:/admin/product/add";
        }

        // Xử lý tải lên ảnh mới nếu có
        if (!file.isEmpty()) {
            String imageUrl = uploadImageToFirebaseStorage(file);
            product.setImage(imageUrl);
        }

        // Kiểm tra và thiết lập danh mục sản phẩm
        if (product.getCategory() == null || product.getCategory().getId() == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Danh mục sản phẩm không hợp lệ.");
            return "redirect:/admin/product/add";
        }

        Category category = categoryService.findById(product.getCategory().getId());
        if (category == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Danh mục sản phẩm không tồn tại.");
            return "redirect:/admin/product/add";
        }
        product.setCategory(category);

        // Xử lý thuộc tính cây
        if ("Cây".equalsIgnoreCase(category.getName())) {
            // Xóa các thuộc tính cá nếu có
            product.getFishAttributes().clear();

            if (heightOptions != null) {
                for (String height : heightOptions) {
                    PlantAttributes attr = new PlantAttributes();
                    attr.setHeight(height);
                    attr.setProduct(product);
                    product.getPlantAttributes().add(attr);
                }
            }
            if (difficultyOptions != null) {
                for (String difficulty : difficultyOptions) {
                    PlantAttributes attr = new PlantAttributes();
                    attr.setDifficulty(difficulty);
                    attr.setProduct(product);
                    product.getPlantAttributes().add(attr);
                }
            }
            if (lightOptions != null) {
                for (String light : lightOptions) {
                    PlantAttributes attr = new PlantAttributes();
                    attr.setLightRequirement(light);
                    attr.setProduct(product);
                    product.getPlantAttributes().add(attr);
                }
            }
            if (waterOptions != null) {
                for (String water : waterOptions) {
                    PlantAttributes attr = new PlantAttributes();
                    attr.setWaterRequirement(water);
                    attr.setProduct(product);
                    product.getPlantAttributes().add(attr);
                }
            }

            // Nếu có thuộc tính cây đã được lưu trước đó, loại bỏ những thuộc tính không còn liên quan
            if (product.getId() != null) {
                List<PlantAttributes> currentAttributes = new ArrayList<>(product.getPlantAttributes());
                for (PlantAttributes attr : currentAttributes) {
                    if (!product.getPlantAttributes().contains(attr)) {
                        product.getPlantAttributes().remove(attr);
                    }
                }
            }
        }
        // Xử lý thuộc tính cá
        else if ("Cá".equalsIgnoreCase(category.getName())) {
            // Xóa các thuộc tính cây nếu có
            product.getPlantAttributes().clear();

            if (speciesOptions != null) {
                for (String species : speciesOptions) {
                    FishAttributes attr = new FishAttributes();
                    attr.setSpecies(species);
                    attr.setProduct(product);
                    product.getFishAttributes().add(attr);
                }
            }
            if (sizeOptions != null) {
                for (String size : sizeOptions) {
                    FishAttributes attr = new FishAttributes();
                    attr.setSize(size);
                    attr.setProduct(product);
                    product.getFishAttributes().add(attr);
                }
            }
            if (lifespanOptions != null) {
                for (String lifespan : lifespanOptions) {
                    FishAttributes attr = new FishAttributes();
                    attr.setLifespan(lifespan);
                    attr.setProduct(product);
                    product.getFishAttributes().add(attr);
                }
            }
            if (hardnessOptions != null) {
                for (Integer hardness : hardnessOptions) {
                    FishAttributes attr = new FishAttributes();
                    attr.setHardness(hardness);
                    attr.setProduct(product);
                    product.getFishAttributes().add(attr);
                }
            }
            if (foodTypeOptions != null) {
                for (String foodType : foodTypeOptions) {
                    FishAttributes attr = new FishAttributes();
                    attr.setFoodType(foodType);
                    attr.setProduct(product);
                    product.getFishAttributes().add(attr);
                }
            }

            // Nếu có thuộc tính cá đã được lưu trước đó, loại bỏ những thuộc tính không còn liên quan
            if (product.getId() != null) {
                List<FishAttributes> currentAttributes = new ArrayList<>(product.getFishAttributes());
                for (FishAttributes attr : currentAttributes) {
                    if (!product.getFishAttributes().contains(attr)) {
                        product.getFishAttributes().remove(attr);
                    }
                }
            }
        }

        // Lưu sản phẩm vào cơ sở dữ liệu
        productService.addProduct(product);
        method methods = new method();
        String IdProuct = product.getId();
        String tmp = methods.saveProductRecord(IdProuct);
//			String tmp = "";

        // Thông báo thành công
        redirectAttributes.addFlashAttribute("successMessage", "Sản phẩm đã được thêm mới thành công.");
        return "redirect:/admin/product";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
        Product product = productService.findByID(id);
        if (product == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy sản phẩm để xóa.");
        } else {
            product.setStatus(false);
            productService.deleteProduct(product);
            redirectAttributes.addFlashAttribute("successMessage", "Đã xóa sản phẩm thành công.");
        }
        return "redirect:/admin/product";
    }

    // TẢI ẢNH VÀO FIREBASE

    private String uploadImageToFirebaseStorage(MultipartFile file) throws IOException {
        String fileName = "product/" + UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        BlobId blobId = BlobId.of("projectplants-49b87.appspot.com", fileName);

        // Load service account from resources folder
        InputStream serviceAccount = new FileInputStream(Paths.get("src", "main", "resources", "serviceAccountKey.json").toString());
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

        // Upload file to Firebase Storage
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
        try (InputStream inputStream = file.getInputStream()) {
            storage.create(blobInfo, inputStream);
        }

        // Get public URL of the file from Firebase Storage
        return "https://firebasestorage.googleapis.com/v0/b/projectplants-49b87.appspot.com/o/" +
                URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString()) + "?alt=media";
    }
}
