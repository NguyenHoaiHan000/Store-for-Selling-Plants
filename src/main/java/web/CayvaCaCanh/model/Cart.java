package web.CayvaCaCanh.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name ="GIOHANG")
public class Cart {
    @Id
    @Column(name ="MAGIOHANG",length = 10)
    private String id;

    @ManyToOne
    @JoinColumn(name ="MAKHACHHANG")
    private User user;


    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<CartDetail> listCartDetail = new ArrayList<>();



    public Cart() {
        // TODO Auto-generated constructor stub
        this.id = generateCartId();

    }

    private String generateCartId() {
        String id;
        do {
            // Tạo mã giỏ hàng ngẫu nhiên
            id = UUID.randomUUID().toString().substring(0, 10); // Lấy 10 ký tự đầu của UUID
            // Kiểm tra xem mã giỏ hàng đã tồn tại trong hệ thống chưa
        } while (isCartIdExists(id)); // Kiểm tra mã giỏ hàng đã tồn tại

        return id;
    }
    private boolean isCartIdExists(String id) {
        // Viết logic kiểm tra trong cơ sở dữ liệu hoặc trong danh sách các giỏ hàng đã tồn tại
        // Trong ví dụ này, giả sử không có mã giỏ hàng nào trùng lặp
        return false;
    }

//    public void addToCart(Product product, int quantity) {
//        if (listCartDetail == null) {
//            listCartDetail = new ArrayList<>();
//        }
//
//        // Check if the product is already in the cart
//        for (CartDetail cartDetail : listCartDetail) {
//            if (cartDetail.getProduct().getId().equals(product.getId())) {
//                // If found, update the quantity
//                cartDetail.setQuantity(cartDetail.getQuantity() + quantity);
//                return;
//            }
//        }
//
//        // If not found, create a new CartDetail
//        CartDetail cartDetail = new CartDetail();
//        cartDetail.setCart(this);
//        cartDetail.setProduct(product);
//        cartDetail.setQuantity(quantity);
//
//        // Add the new CartDetail to the list
//        listCartDetail.add(cartDetail);
//    }

//
//    private CartDetail findCartDetailById(CartDetailPk pk) {
//        for (CartDetail detail : listCartDetail) {
//            if (detail.getId().equals(pk)) {
//                return detail;
//            }
//        }
//        return null;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartDetail> getListCartDetail() {
        return listCartDetail;
    }

    public void setListCartDetail(List<CartDetail> listCartDetail) {
        this.listCartDetail = listCartDetail;
    }

//    public void addToCart(Product product, int quantity) {
//
//        if (listCartDetail == null) {
//            listCartDetail = new ArrayList<>();
//        }
//
//        for (CartDetail item : listCartDetail) {
//            if (item.getProduct().getId().equals(product.getId())) {
//                item.setQuantity(item.getQuantity() + quantity);
//                return;
//            }
//        }
//        CartDetail newCartDetail = new CartDetail(product, quantity);
//        newCartDetail.setCart(this); // Set bi-directional relationship
//        listCartDetail.add(newCartDetail);
//    }

    public double getTotal() {
        double total = 0;
        if (listCartDetail != null) {
            for (CartDetail detail : listCartDetail) {
                total += detail.getProduct().getPrice() * detail.getQuantity();
            }
        }
        return total;
    }
}
