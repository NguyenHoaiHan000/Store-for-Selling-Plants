package web.CayvaCaCanh.Controller.Admin;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import web.CayvaCaCanh.Service.AccountService;
import web.CayvaCaCanh.Service.CustomUserDetails;
import web.CayvaCaCanh.Service.StaffsService;
import web.CayvaCaCanh.model.Account;
import web.CayvaCaCanh.model.Staffs;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Controller
@RequestMapping("/admin/staff")
public class AdminStaffController {

    @Autowired
    private StaffsService staffsService;

    @Autowired
    private AccountService accountService;


    @GetMapping("")
    public String listStaffs(Model model,
                             @RequestParam(name = "page", defaultValue = "0") int page,
                             @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Staffs> staffsPage = staffsService.findByStatusAndPage(true, pageable);

        List<Staffs> staffs = staffsPage.getContent();
        for (Staffs staff : staffs) {
            staff.getAccount().getUsername(); // Trigger the load of Account
        }

        model.addAttribute("staffs", staffs);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", staffsPage.getTotalPages());
        model.addAttribute("size", size);
        return "admin/staffs/list";
    }

    @GetMapping("/add")
    public String showAddStaffForm(Model model) {
        model.addAttribute("staff", new Staffs());
        return "admin/staffs/add";
    }

//    @PostMapping("/add")
//    public String addStaff(@ModelAttribute("staff") Staffs staff) {
//        staffsService.addStaff(staff);
//        return "redirect:/admin/staff";
//    }

    @GetMapping("/edit/{id}")
    public String editStaff(@PathVariable("id") String id, Model model) {
        Staffs existingStaff = staffsService.findById(id);
        if (existingStaff != null) {
            model.addAttribute("staff", existingStaff);
            return "admin/staffs/edit";
        } else {
            // Xử lý trường hợp không tìm thấy nhân viên
            return "redirect:/admin/staff";
        }
    }


    @PostMapping("/edit/{id}")
    public String updateStaff(@PathVariable("id") String id,
                              @RequestParam("file") MultipartFile file,
                              @ModelAttribute Staffs staff,
                              RedirectAttributes redirectAttributes) throws IOException {

        // Tìm kiếm nhân viên hiện tại theo ID
        Staffs existingStaff = staffsService.findById(id);
        if (existingStaff == null) {
            // Xử lý khi không tìm thấy nhân viên
            return "redirect:/admin/staff";
        }

        // Cập nhật thông tin nhân viên từ form nhập vào
        existingStaff.setHo(staff.getHo());
        existingStaff.setName(staff.getName());
        existingStaff.setCmnd(staff.getCmnd());
        existingStaff.setAddress(staff.getAddress());
        existingStaff.setGender(staff.getGender());
        existingStaff.setSdt(staff.getSdt());
        existingStaff.setDateOfBirth(staff.getDateOfBirth());

        Account existingAccount = accountService.findByUsername(staff.getAccount().getUsername());
        if (existingAccount != null) {
            existingStaff.setAccount(existingAccount);
        }

        // Trong Service hoặc Controller, sau khi cập nhật thông tin nhân viên
        // Cập nhật thông tin nhân viên

        // Xử lý tải lên ảnh mới nếu có
        if (!file.isEmpty()) {
            String imageUrl = uploadImageToFirebaseStorage(file);
            existingStaff.setImage(imageUrl);
        }
//        DÙNG ĐỂ THAY ĐỔI ẢNH TRONG HEADER
        CustomUserDetails currentUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        currentUserDetails.updateStaffDetails(existingStaff);

        staffsService.saveStaff(existingStaff);
        // Thông báo thành công
        redirectAttributes.addFlashAttribute("successMessage", "Nhân viên đã được cập nhật thành công.");

        return "redirect:/admin/staff";
    }


    private String uploadImageToFirebaseStorage(MultipartFile file) throws IOException {
        String fileName = "staff/" + UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
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

    @PostMapping("/delete/{id}")
    public String deleteStaff(@PathVariable("id") String id) {
        Optional<Staffs> optionalStaff = staffsService.findByIdStaff(id);
        if (optionalStaff.isPresent()) {
            Staffs staff = optionalStaff.get();
            // Update the status in the associated account
            Account account = staff.getAccount();
            if (account != null) {
                // Set status to false (or update as needed)
                account.setStatus(false);
                accountService.saveAccount(account); // Save the updated account
            }

        }
        return "redirect:/admin/staff";
    }
    //PROFILE
// PROFILE
    @GetMapping("/profile")
    public String showProfile(Model model) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Staffs staff = staffsService.findByUsername(username);
        model.addAttribute("staff", staff);
        return "admin/staffs/profile";
    }

    @PostMapping("/profile/edit")
    public String updateProfileStaff(@RequestParam("file") MultipartFile file,
                                     @ModelAttribute Staffs staff,
                                     RedirectAttributes redirectAttributes) throws IOException {

        CustomUserDetails currentUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = currentUserDetails.getUsername();
        Staffs existingStaff = staffsService.findByUsername(username);

        if (existingStaff == null) {
            return "redirect:/admin/staff";
        }

        existingStaff.setHo(staff.getHo());
        existingStaff.setName(staff.getName());
        existingStaff.setCmnd(staff.getCmnd());
        existingStaff.setAddress(staff.getAddress());
        existingStaff.setGender(staff.getGender());
        existingStaff.setSdt(staff.getSdt());
        existingStaff.setDateOfBirth(staff.getDateOfBirth());

        Account existingAccount = accountService.findByUsername(staff.getAccount().getUsername());
        if (existingAccount != null) {
            existingStaff.setAccount(existingAccount);
        }

        if (!file.isEmpty()) {
            String imageUrl = uploadImageToFirebaseStorage(file);
            existingStaff.setImage(imageUrl);
        }

        currentUserDetails.updateStaffDetails(existingStaff);
        staffsService.saveStaff(existingStaff);
        redirectAttributes.addFlashAttribute("successMessage", "Hồ sơ nhân viên đã được cập nhật thành công.");

        return "redirect:/admin/staff/profile";
    }

}
