package web.CayvaCaCanh.Controller.Admin;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import web.CayvaCaCanh.Service.SupplierService;
import web.CayvaCaCanh.model.Supplier;


@Controller
@RequestMapping("/admin/supplier")
public class AdminSupplierController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping("")
    public String showSuppliers(Model model, @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "12") int size) {
        Page<Supplier> suppliers = supplierService.findByStatusSupplier(true, (PageRequest.of(page, size)));

        model.addAttribute("suppliers", suppliers.getContent());
        model.addAttribute("currentPage", suppliers.getNumber());
        model.addAttribute("totalPages", suppliers.getTotalPages());
        model.addAttribute("totalItems", suppliers.getTotalElements());
        return "admin/supplier/list";
    }

    //HIỂN THỊ FORM ADD
    @GetMapping("/add")
    public String showAddSupplierForm(Model model) {
        model.addAttribute("supplier", new Supplier());
        return "admin/supplier/add";
    }

    //CHỨC NĂNNG THÊM KHI THÊM THÌ TRẠNG THÁI SET TỰ ĐỘNG LÀ TRUE
    @PostMapping("/add")
    public String addSupplier(@ModelAttribute("supplier") @Valid Supplier supplier, BindingResult result, RedirectAttributes redirectAttributes) {
        // Kiểm tra các lỗi dữ liệu
        if (result.hasErrors()) {
            return "admin/supplier/add"; // Trở về form thêm nhà cung cấp nếu có lỗi
        }

        // Kiểm tra các trường cần thiết
        if (supplier.getName() == null || supplier.getName().isEmpty()) {
            result.rejectValue("Name", "error.supplier", "Tên không được để trống.");
        } else if (supplierService.existsByName(supplier.getName())) {
            result.rejectValue("Name", "error.supplier", "Tên nhà cung cấp đã tồn tại.");
        }

        if (supplier.getAddress() == null || supplier.getAddress().isEmpty()) {
            result.rejectValue("address", "error.supplier", "Địa chỉ không được để trống.");
        }

        // Kiểm tra số điện thoại
        String sdt = supplier.getSdt();
        if (sdt == null || sdt.isEmpty()) {
            result.rejectValue("sdt", "error.supplier", "Số điện thoại không được để trống.");
        } else if (sdt.length() != 10) {
            result.rejectValue("sdt", "error.supplier", "Số điện thoại phải có đúng 10 số.");
        } else if (!sdt.matches("\\d{10}")) {
            result.rejectValue("sdt", "error.supplier", "Số điện thoại chỉ được chứa các ký tự số.");
        }

        // Nếu có lỗi, quay lại form thêm nhà cung cấp
        if (result.hasErrors()) {
            return "admin/supplier/add";
        }

        try {
            // Thêm nhà cung cấp mới
            supplierService.addSupplier(supplier);
            redirectAttributes.addFlashAttribute("successMessage", "Thêm nhà cung cấp thành công.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Đã xảy ra lỗi khi thêm nhà cung cấp.");
        }

        return "redirect:/admin/supplier";
    }


    @GetMapping("/edit/{id}")
    public String showEditSupplierForm(@PathVariable String id, Model model) {
        Supplier supplier = supplierService.findById(id);
        if (supplier != null) {
            model.addAttribute("supplier", supplier);
            return "admin/supplier/edit";
        }
        return "redirect:/admin/supplier";
    }

    @PostMapping("/edit/{id}")
    public String updateSupplier(@PathVariable String id, @ModelAttribute("supplier") @Valid Supplier supplier, BindingResult result, RedirectAttributes redirectAttributes) {
        // Kiểm tra lỗi dữ liệu
        if (result.hasErrors()) {
            return "admin/supplier/edit";
        }

        if (supplier.getName() == null || supplier.getName().isEmpty()) {
            result.rejectValue("Name", "error.supplier", "Tên không được để trống.");
        }
//        else {
//            // Kiểm tra xem tên mới có giống với tên cũ không
//            Supplier existingSupplier = supplierService.findById(id);
//            if (existingSupplier != null && !existingSupplier.getName().equals(supplier.getName())) {
//                if (supplierService.existsByName(supplier.getName())) {
//                    result.rejectValue("Name", "error.supplier", "Tên nhà cung cấp đã tồn tại.");
//                }
//            }
        if (supplier.getAddress() == null || supplier.getAddress().isEmpty()) {
            result.rejectValue("address", "error.supplier", "Địa chỉ không được để trống.");
        }
        // Kiểm tra số điện thoại
        String sdt = supplier.getSdt();
        if (sdt == null || sdt.isEmpty()) {
            result.rejectValue("sdt", "error.supplier", "Số điện thoại không được để trống.");
        } else if (sdt.length() != 10) {
            result.rejectValue("sdt", "error.supplier", "Số điện thoại phải có đúng 10 số.");
        } else if (!sdt.matches("\\d{10}")) {
            result.rejectValue("sdt", "error.supplier", "Số điện thoại chỉ được chứa các ký tự số.");
        }
        // Nếu có lỗi, quay lại form chỉnh sửa
        if (result.hasErrors()) {
            return "admin/supplier/edit";
        }

        try {
            // Kiểm tra xem nhà cung cấp có tồn tại không
            Supplier existingSupplier = supplierService.findById(id);
            if (existingSupplier == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Nhà cung cấp không tồn tại.");
                return "redirect:/admin/supplier";
            }

            // Cập nhật nhà cung cấp
            supplier.setId(id); // Đảm bảo rằng ID của nhà cung cấp được giữ nguyên
            supplierService.save(supplier);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật nhà cung cấp thành công.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Đã xảy ra lỗi khi cập nhật nhà cung cấp.");
        }

        return "redirect:/admin/supplier";
    }

    @PostMapping("/delete/{id}")
    public String deleteSupplier(@PathVariable String id) {
        Supplier supplier = supplierService.findById(id);
        if (supplier != null) {
            // Thay đổi trạng thái từ true thành false
            supplier.setStatus(false); // Hoặc có thể gán trạng thái khác tùy theo yêu cầu

            supplierService.save(supplier); // Lưu lại nhà cung cấp đã thay đổi trạng thái

            return "redirect:/admin/supplier";
        }
        // Xử lý nếu không tìm thấy nhà cung cấp để xóa
        return "redirect:/admin/supplier";
    }

}
