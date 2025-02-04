package web.CayvaCaCanh.Controller.Admin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.CayvaCaCanh.Service.OrderService;
import web.CayvaCaCanh.Service.ReceiptService;
import web.CayvaCaCanh.Service.StatisticsService;
import web.CayvaCaCanh.model.ProductStatistic;

import java.time.LocalDate;
import java.util.List;
@Controller
@RequestMapping("/admin")
public class AdminStatisticsController {

    @Autowired
    private ReceiptService receiptService;

    @Autowired
    private StatisticsService statisticsService;


    @GetMapping("/Statistic")
    public String getProductStatistics(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Model model) {

        // Nếu ngày kết thúc không được cung cấp, đặt nó là ngày hiện tại
        if (endDate == null) {
            endDate = LocalDate.now();
        }

        // Nếu ngày bắt đầu không được cung cấp, đặt nó là ngày 30 ngày trước ngày hiện tại
        if (startDate == null) {
            startDate = endDate.minusDays(30);
        }


        List<ProductStatistic> statistics = receiptService.getProductStatistics(startDate, endDate);

        // Tính toán tổng các giá trị
        model.addAttribute("statistics", statistics);
//
        return "admin/Statistis/statistics";
    }

}
