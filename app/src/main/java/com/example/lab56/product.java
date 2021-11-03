package com.example.lab56;

public class product {
    private String id;
    private String tenProduct;
    public String anhProduct;
    //    public Date ngayTao=new Date();

    public product() {

    }

    public product(String id, String tenProduct, String anhProduct) {
        this.id = id;
        this.tenProduct = tenProduct;
        this.anhProduct = anhProduct;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenProduct() {
        return tenProduct;
    }

    public void setTenProduct(String tenProduct) {
        this.tenProduct = tenProduct;
    }

    public String getAnhProduct() {
        return anhProduct;
    }

    public void setAnhProduct(String anhProduct) {
        this.anhProduct = anhProduct;
    }
}
