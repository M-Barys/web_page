package com.webshop.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ModelObjectReference {
    private final Long objectID;
    private final ModelObjectType type;
}
