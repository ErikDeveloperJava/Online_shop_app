package onlineShop.model;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reviews {

    private int id;

    private String reviewText;

    private double rating;

    private Date sendDate;

    private User user;

    private Product product;
}
