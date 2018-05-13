package com.webshop.controllers.params;

import com.webshop.model.ModelObjectReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RelationParams {
    private ModelObjectReference parent;
}
