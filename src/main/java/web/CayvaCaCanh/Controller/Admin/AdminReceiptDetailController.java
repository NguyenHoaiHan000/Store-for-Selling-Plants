package web.CayvaCaCanh.Controller.Admin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import web.CayvaCaCanh.Service.ProductService;
import web.CayvaCaCanh.Service.ReceiptDetailService;
import web.CayvaCaCanh.Service.ReceiptService;
import web.CayvaCaCanh.model.Product;
import web.CayvaCaCanh.model.Receipt;
import web.CayvaCaCanh.model.ReceiptDetail;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.ui.Model;
import web.CayvaCaCanh.model.ReceiptDetailPK;

@Controller
@RequestMapping("/admin/receiptdetail")
public class AdminReceiptDetailController {


    @Autowired
    private ReceiptDetailService receiptDetailService;

    @Autowired
    private ReceiptService receiptService;

    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public String getReceiptDetailList(@RequestParam("id") String receiptId, Model model) {
        Optional<Receipt> optionalReceipt = receiptService.findById(receiptId);
        if (optionalReceipt.isPresent()) {
            Receipt receipt = optionalReceipt.get();
            List<ReceiptDetail> receiptDetails = receiptDetailService.findByReceiptId(receiptId);
            if (!receiptDetails.isEmpty()) {
                double totalCost = receiptDetails.stream().mapToDouble(ReceiptDetail::getTotalCost).sum();
                model.addAttribute("totalCost", totalCost);
            } else {
                // Xử lý khi không có chi tiết phiếu nhập
            }
            model.addAttribute("receipt", receipt); // Thêm receipt vào model
            model.addAttribute("receiptDetails", receiptDetails);

            return "admin/receipt/receiptDetail/list";
        } else {
            // Xử lý trường hợp không tìm thấy phiếu nhập, có thể chuyển hướng về trang danh sách phiếu nhập hoặc hiển thị thông báo lỗi
            return "redirect:/admin/receiptdetail/list"; // Hoặc hiển thị thông báo lỗi thích hợp
        }
    }




}



