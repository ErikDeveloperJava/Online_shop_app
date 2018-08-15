package onlineShop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttributeValue {

    private int id;

    private String value;

    private CategoryAttribute categoryAttribute;

    private Product product;
}
