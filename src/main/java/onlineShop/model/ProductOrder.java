package onlineShop.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductOrder {

    private int id;

    private User user;

    private Product product;

    private int count;
}
