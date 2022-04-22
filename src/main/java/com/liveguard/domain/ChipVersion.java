package com.liveguard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "chip_versions")
@Setter
@Getter
@NoArgsConstructor
public class ChipVersion extends BaseEntity {

    private String name;
    private String alias;

    @Column(length = 4096, nullable = false, name = "short_description")
    private String shortDescription;

    @Column(length = 4096, nullable = false, name = "full_description")
    private String fullDescription;

    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    private Boolean enabled;
    private Boolean inStock;

    private Float cost;
    private Float price;
    private Float discountPercent;

    private Float weight;

    private String mainImage;

    private int reviewCount;
    private float averageRating;

    private boolean customerCanReview;
    private boolean reviewedByCustomer;

    @OneToMany(mappedBy = "chipVersion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChipVersionImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "chipVersion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChipVersionDetail> details = new ArrayList<>();

    public float getDiscountPrice() {
        if (discountPercent > 0 || discountPercent == null) {
            return price * ((100 - discountPercent) / 100);
        }
        return this.price;
    }

    @Override
    public String toString() {
        return "ChipVersion{" +
                "id='" + super.toString() + '\'' +
                ", name=" + name +
                ", enabled=" + enabled +
                ", inStock=" + inStock +
                ", cost=" + cost +
                ", price=" + price +
                ", discountPercent=" + discountPercent +
                ", images=" + images.toString() +
                ", details=" + details.toString() +
                '}';
    }
}
