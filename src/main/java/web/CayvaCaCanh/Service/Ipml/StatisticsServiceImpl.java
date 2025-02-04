package web.CayvaCaCanh.Service.Ipml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.CayvaCaCanh.Service.StatisticsService;
import web.CayvaCaCanh.model.ProductStatistic;
import web.CayvaCaCanh.repository.OrderDetailRepository;
import web.CayvaCaCanh.repository.OrderRepository;
import web.CayvaCaCanh.repository.ReceiptDetailRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    private ReceiptDetailRepository receiptDetailRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;


    @Override
    public int getTotalProductsImported(LocalDate startDate, LocalDate endDate) {
        Long count = receiptDetailRepository.countByDateRange(startDate, endDate);
        return count != null ? count.intValue() : 0;
    }

    @Override
    public int getTotalProductsSold(LocalDate startDate, LocalDate endDate) {
        Long count = orderDetailRepository.countByDateRange(startDate, endDate);
        return count != null ? count.intValue() : 0;
    }

    @Override
    public double getTotalMoneyImported(LocalDate startDate, LocalDate endDate) {
        Double totalMoneyImported = receiptDetailRepository.sumTotalMoneyImportedByDateRange(startDate, endDate);
        return totalMoneyImported != null ? totalMoneyImported : 0.0;
    }

    @Override
    public double getTotalMoneySold(LocalDate startDate, LocalDate endDate) {
        Double totalMoneySold = orderDetailRepository.sumTotalMoneySoldByDateRange(startDate, endDate);
        return totalMoneySold != null ? totalMoneySold : 0.0;
    }

    @Override
    public double getTotalRevenue(LocalDate startDate, LocalDate endDate) {
        double totalMoneySold = getTotalMoneySold(startDate, endDate);
        double totalMoneyImported = getTotalMoneyImported(startDate, endDate);
        return totalMoneySold - totalMoneyImported;
    }


}
