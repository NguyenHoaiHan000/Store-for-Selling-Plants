package web.CayvaCaCanh.Service;

import java.time.LocalDate;

public interface StatisticsService {


    int getTotalProductsImported(LocalDate startDate, LocalDate endDate);

    // Tổng số sản phẩm bán ra
    int getTotalProductsSold(LocalDate startDate, LocalDate endDate);

    // Tổng số tiền nhập vào
    double getTotalMoneyImported(LocalDate startDate, LocalDate endDate);

    // Tổng số tiền bán ra
    double getTotalMoneySold(LocalDate startDate, LocalDate endDate);

    // Tổng số doanh thu
    double getTotalRevenue(LocalDate startDate, LocalDate endDate);

}
