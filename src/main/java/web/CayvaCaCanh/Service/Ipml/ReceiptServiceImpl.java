package web.CayvaCaCanh.Service.Ipml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import web.CayvaCaCanh.Service.ReceiptService;
import web.CayvaCaCanh.model.*;
import web.CayvaCaCanh.repository.OrderDetailRepository;
import web.CayvaCaCanh.repository.ProductRepository;
import web.CayvaCaCanh.repository.ReceiptDetailRepository;
import web.CayvaCaCanh.repository.ReceiptRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReceiptServiceImpl implements ReceiptService {

    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private ReceiptDetailRepository detailRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;


    public Page<Receipt> findAllReceipt(Pageable pageable) {
        return receiptRepository.findAll(pageable);
    }

    @Override
    public Receipt saveReceipt(Receipt receipt) {
        // Kiểm tra nếu danh sách chi tiết phiếu nhập của phiếu nhập hiện tại không null
        if (receipt.getListReceiptDetail() != null) {
            // Lặp qua từng chi tiết phiếu nhập để xử lý
            for (ReceiptDetail rd : receipt.getListReceiptDetail()) {
                // Lấy thông tin sản phẩm từ id sản phẩm của chi tiết phiếu nhập
                Optional<Product> optionalProduct = productRepository.findById(rd.getProduct().getId());
                if (optionalProduct.isPresent()) {
                    Product product = optionalProduct.get();

                    // Tạo khóa chính cho chi tiết phiếu nhập
                    ReceiptDetailPK receiptDetailPk = new ReceiptDetailPK(receipt.getId(), product.getId());
                    rd.setId(receiptDetailPk);
                    rd.setReceipt(receipt);
                    rd.setProduct(product);

                    // Tính tổng chi phí cho chi tiết phiếu nhập
                    rd.setTotalCost(rd.getQuantity() * rd.getPrice());
                } else {
                    // Xử lý khi không tìm thấy sản phẩm
                    throw new RuntimeException("Product not found with ID: " + rd.getProduct().getId());
                }
            }
        }

        // Lưu phiếu nhập và chi tiết phiếu nhập đã được cập nhật vào cơ sở dữ liệu
        return receiptRepository.save(receipt);
    }





    public Optional<Receipt> findById(String id) {
        return receiptRepository.findById(id);
    }

    @Override
    public Receipt getReceiptById(String receiptId) {
        return receiptRepository.findById(receiptId).orElse(null);
    }

    @Override
    public List<Receipt> getAllReceipts() {
        return receiptRepository.findAll();
    }



    @Override
    public void updateTotalCost(Receipt receipt) {

    }

    @Override
    public void addReceiptAndDetail(Receipt receipt, ReceiptDetail detail) {
        // Thêm chi tiết vào danh sách chi tiết của phiếu nhập
        receipt.getListReceiptDetail().add(detail);

        // Cập nhật chi tiết phiếu nhập cho từng chi tiết
        detail.setReceipt(receipt);

        // Lưu lại chi tiết phiếu nhập vào cơ sở dữ liệu
        receiptRepository.save(receipt);
    }
@Override
    public List<ProductStatistic> getProductStatistics(LocalDate startDate, LocalDate endDate) {
        // Lấy tất cả chi tiết hóa đơn trong khoảng thời gian
        List<ReceiptDetail> receiptDetails = detailRepository.findByReceiptDateBetween(startDate, endDate);
        // Lấy tất cả chi tiết đơn hàng trong khoảng thời gian
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderDateBetween(startDate, endDate);

        // Tạo một bản đồ để lưu trữ các thống kê theo tên sản phẩm
        Map<String, ProductStatistic> statisticsMap = new HashMap<>();
        // Lấy tất cả sản phẩm để lấy số lượng tồn kho
            List<Product> products = productRepository.findAll();
            Map<String, Integer> stockMap = new HashMap<>();
            for (Product product : products) {
                stockMap.put(product.getName(), product.getNumber()); // Giả sử 'stock' là thuộc tính số lượng tồn kho của sản phẩm
            }
    // Tính toán số liệu cho chi tiết hóa đơn
        for (ReceiptDetail rd : receiptDetails) {
            String productName = rd.getProduct().getName();
            ProductStatistic statistic = statisticsMap.getOrDefault(productName, new ProductStatistic());

            statistic.setTenSanPham(productName);
            statistic.setGiaNhap(rd.getPrice());
            statistic.setSoLuongNhap(statistic.getSoLuongNhap() + rd.getQuantity());
            statistic.setThanhTienNhap(statistic.getThanhTienNhap() + rd.getQuantity() * rd.getPrice());

            statisticsMap.put(productName, statistic);
        }

        // Tính toán số liệu cho chi tiết đơn hàng
        for (OrderDetail od : orderDetails) {
            if (od.getOrder().getStatus() == 6) { // Chỉ tính những đơn hàng đã hoàn tất
                String productName = od.getProduct().getName();
                ProductStatistic statistic = statisticsMap.get(productName);

                if (statistic != null) {
                    statistic.setGiaBan(od.getProduct().getPrice());
                    statistic.setSoLuongBan(statistic.getSoLuongBan() + od.getQuantity());
                    statistic.setThanhTienBan(statistic.getSoLuongBan() * od.getProduct().getPrice());
                    statistic.setDoanhThu(statistic.getThanhTienBan() - statistic.getThanhTienNhap());
                }
            }
        }
    for (ProductStatistic statistic : statisticsMap.values()) {
        Integer stock = stockMap.get(statistic.getTenSanPham());
        if (stock != null) {
            statistic.setSoLuongTon(stock);
        }
    }


    // Trả về danh sách các thống kê
        return new ArrayList<>(statisticsMap.values());
    }
    @Override
    public int getTotalInventory(LocalDate startDate, LocalDate endDate) {
        // Lấy tất cả sản phẩm từ bảng sản phẩm
        List<Product> products = productRepository.findAll();

        // Tính tổng số lượng tồn kho
        int totalInventory = products.stream()
                .mapToInt(Product::getNumber) // Giả sử 'getStock' trả về số lượng tồn kho
                .sum();

        return totalInventory;
    }


}
