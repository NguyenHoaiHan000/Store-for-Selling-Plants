package web.CayvaCaCanh.model;

import jakarta.persistence.Column;

import java.io.Serializable;
import java.util.Objects;

public class ReceiptDetailPK  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "MAPHIEUNHAP",length = 10)
    private String  receipt;

    @Column(name = "MASANPHAM", length = 10)
    private String product;

    public ReceiptDetailPK() {
    }

    public ReceiptDetailPK(String receipt,String product) {
        super();
        this.receipt = receipt;
        this.product = product;
    }


    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, receipt);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ReceiptDetailPK other = (ReceiptDetailPK)  obj;
        return Objects.equals(product, other.product) && Objects.equals(receipt, other.receipt);
    }
}
