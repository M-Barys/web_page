package com.webshop.model.instance.data;

import com.webshop.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryData {

    private String slug;
    private Status status;

}
