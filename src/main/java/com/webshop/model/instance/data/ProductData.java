package com.webshop.model.instance.data;

import com.webshop.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductData {

    private String slug;
    private Status status;
    private Map<Currency,BigDecimal> prices;

}
