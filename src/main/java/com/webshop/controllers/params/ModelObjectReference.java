package com.webshop.controllers.params;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModelObjectReference {
    private Long objectID;
    private ModelObjectType type;
}
