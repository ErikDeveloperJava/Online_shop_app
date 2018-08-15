package onlineShop.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {

    private int id;

    private String image;

    private Product product;
}
