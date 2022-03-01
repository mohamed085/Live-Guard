package com.liveguard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "chip_types")
@Setter
@Getter
@NoArgsConstructor
public class ChipType extends BaseEntity {

    private String name;
    private String alias;
    private String shortDescription;
    private String fullDescription;
    private Date createdTime;
    private Date updatedTime;
    private boolean enabled;
    private boolean inStock;
    private float cost;
    private float price;
    private float discountPercent;
    private float length;
    private float width;
    private float height;
    private float weight;
    private String mainImage;

    @OneToMany(mappedBy = "chipType")
    private Set<Chip> chips = new HashSet<>();

    @OneToMany(mappedBy = "chipType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChipTypeDetail> details = new ArrayList<>();

    @OneToMany(mappedBy = "chipType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChipTypeImage> images = new ArrayList<>();

    public void addExtraImage(String imageName) {
        this.images.add(new ChipTypeImage(imageName, this));
    }

    public boolean containsImageName(String imageName) {
        Iterator<ChipTypeImage> iterator = images.iterator();

        while (iterator.hasNext()) {
            ChipTypeImage image = iterator.next();
            if (image.getName().equals(imageName)) {
                return true;
            }
        }

        return false;
    }

    public void addDetail(String name, String value) {
        this.details.add(new ChipTypeDetail(name, value, this));
    }

    public void addDetail(Long id, String name, String value) {
        this.details.add(new ChipTypeDetail(id, name, value, this));
    }

}
