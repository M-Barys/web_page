package com.webshop.controllers.params;

import com.webshop.model.ModelObjectReference;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RelationParams {
    private final ModelObjectReference parent;
}
