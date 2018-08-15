package onlineShop.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryAttribute {

    private int id;

    private String name;

    private Category category;
}
