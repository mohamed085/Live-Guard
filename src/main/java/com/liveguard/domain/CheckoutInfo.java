package com.liveguard.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.liveguard.dto.ShoppingCartItemDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutInfo {


    @JsonProperty("product_total")
    private float productTotal;

    @JsonProperty("shipping_total")
    private float shippingTotal;

    @JsonProperty("payment_total")
    private float paymentTotal;
    @JsonProperty("deliver_days")
    private int deliverDays;

    @JsonProperty("cod_supported")
    private boolean codSupported;

    @JsonProperty("deliver_date")
    @JsonFormat(pattern="dd MMMM yyyy")
    public Date getDeliverDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, deliverDays);

        return calendar.getTime();
    }

    private Set<ShoppingCartItemDTO> items;

}
