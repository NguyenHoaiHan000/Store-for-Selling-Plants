package web.CayvaCaCanh.Controller.Admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import web.CayvaCaCanh.Service.*;
import web.CayvaCaCanh.model.*;
import web.CayvaCaCanh.repository.ProductRepository;
import web.CayvaCaCanh.repository.ReceiptDetailRepository;
import web.CayvaCaCanh.repository.ReceiptRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/receipt")

public class AdminReceiptController {

    @Autowired
    private ReceiptService receiptService;


    @Autowired
    private AccountService accountService;


    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReceiptDetailRepository receiptDetailRepository;

    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private ReceiptDetailService receiptDetailService;

    @GetMapping("")
    public String showCategories(Model model, @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "13") int size) {
        LocalDate recentDate = LocalDate.now().minusDays(2);

        Page<Receipt> receiptPage = receiptService.findAllReceipt(PageRequest.of(page, size));
        // Xác định các phiếu nhập gần đây
        List<Receipt> recentReceipts = receiptPage.getContent().stream()
                .filter(receipt -> receipt.getDate().isAfter(recentDate))
                .collect(Collectors.toList());

        model.addAttribute("recentReceipts", recentReceipts);
        model.addAttribute("receipts",receiptPage.getContent());
        model.addAttribute("currentPage", receiptPage.getNumber());
        model.addAttribute("totalPages",receiptPage.getTotalPages());
        model.addAttribute("totalItems", receiptPage.getTotalElements());
        return "admin/receipt/list"; // Trả về tên view Thymeleaf (index.html)


    }
//    FORM ADD
@GetMapping("/add")
public String showAddReceiptForm(Model model) {
    Receipt receipt = new Receipt();
    List<Supplier> suppliers = supplierService.findAll();
    List<Product> products = productService.getAllProducts(); // Lấy danh sách sản phẩm

    model.addAttribute("receipt", receipt);
    model.addAttribute("suppliers", suppliers);
    model.addAttribute("products", products);


    return "admin/receipt/add";
}

    @PostMapping("/save")
    public String saveReceipt(@ModelAttribute("receipt") Receipt receipt,
                              @RequestParam("receipt.supplier.id") String supplierId,
                              @RequestParam("productId") String[] productIds,
                              @RequestParam("quantity") String[] quantities,
                              @RequestParam("price") String[] prices) {

        // Thêm mã tự động cho phiếu nhập nếu không có
        if (receipt.getId() == null || receipt.getId().isEmpty()) {
            String maPhieu = UUID.randomUUID().toString().substring(0, 10).toUpperCase();
            receipt.setId(maPhieu);
        }

        // Set ngày hiện tại nếu ngày nhập không được cung cấp
        if (receipt.getDate() == null) {
            receipt.setDate(LocalDate.now());
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("Username: " + username);

        Account account = accountService.findByUsername(username);
        if (account != null) {
            Staffs staff = account.getStaff();
            System.out.println("Staff ID: " + staff.getId()); // Assuming getId() returns the ID of the staff
            receipt.setStaff(staff);
        }



        Supplier supplier = supplierService.findById(supplierId);
        if (supplier != null) {
            receipt.setSupplier(supplier);
            System.out.println("Supplier Name: " + supplier.getName()); // Kiểm tra tên nhà cung cấp
        }


        // Lưu phiếu nhập vào cơ sở dữ liệu
        receiptService.saveReceipt(receipt);

        // Lưu chi tiết phiếu nhập từ bảng sản phẩm


        System.out.println("Product IDs: " + Arrays.toString(productIds));
        System.out.println("Quantities: " + Arrays.toString(quantities));
        System.out.println("Prices: " + Arrays.toString(prices));

        if (productIds != null && quantities != null && prices != null) {
            for (int i = 0; i < productIds.length; i++) {
                Product product = productService.getProductById(productIds[i]);

                ReceiptDetail receiptDetail = new ReceiptDetail();
                receiptDetail.setId(new ReceiptDetailPK(receipt.getId(), productIds[i]));
                receiptDetail.setReceipt(receipt);
                receiptDetail.setProduct(product);
                receiptDetail.setQuantity(Integer.parseInt(quantities[i]));
                productRepository.save(product);
                receiptDetail.setPrice(Double.parseDouble(prices[i]));
//                THÊM SỐ LƯỢNG SẢN PHẨM VÀO KHO
                product.setNumber(product.getNumber() + receiptDetail.getQuantity());
                receiptDetailService.save(receiptDetail);
            }
        }

        // Redirect về trang thêm phiếu nhập
        return "redirect:/admin/receipt/add";
    }




//    @GetMapping("/success")
//    public ModelAndView successReceipt(@RequestParam("id") String id) {
//        ModelAndView mav = new ModelAndView();
//
//        // Tìm phiếu nhập theo ID
//        Optional<Receipt> optionalReceipt = receiptService.findById(id);
//
//        if (optionalReceipt.isPresent()) {
//            Receipt receipt = optionalReceipt.get();
//
//            // Cập nhật trạng thái của phiếu nhập
//
//
//            // Cập nhật số lượng sản phẩm dựa trên chi tiết phiếu nhập
//            List<ReceiptDetail> receiptDetails = receipt.getListReceiptDetail();
//            for (ReceiptDetail receiptDetail : receiptDetails) {
//                Product product = productService.findByID(receiptDetail.getProduct().getId());
//                product.setNumber(receiptDetail.getQuantity() + receiptDetail.getProduct().getNumber());
//                productRepository.save(product);
//            }
//
//            // Lưu phiếu nhập đã cập nhật
//            receiptRepository.save(receipt);
//        } else {
//            // Xử lý trường hợp không tìm thấy phiếu nhập
//            mav.setViewName("redirect:/admin/receipt/list?error=notfound");
//            return mav;
//        }
//
//        // Chuyển hướng đến danh sách phiếu nhập
//        mav.setViewName("redirect:/admin/receipt");
//        return mav;
//    }


}
