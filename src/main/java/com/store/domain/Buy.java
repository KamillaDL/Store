package com.store.domain;

import javax.persistence.*;

@Entity
@Table(name="buy")
public class Buy {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "basket", nullable = false)
//    private Basket basket;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "details", nullable = false)
    private Details details;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product", nullable = false)
    private Product product;

    private Long result;

    private Long value;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Long getResult() {
        Integer price = product.getPrice();
//        Long value = basket.getValue();
        result = price * value;
        return result;
    }
}
