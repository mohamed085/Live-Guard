package com.liveguard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "chip_types")
@Setter
@Getter
@NoArgsConstructor
public class ChipType extends BaseEntity {

    private String name;
    private String alias;

    @Column(length = 1024, nullable = false, name = "short_description")
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
