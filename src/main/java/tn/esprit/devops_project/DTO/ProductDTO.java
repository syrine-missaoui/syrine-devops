package tn.esprit.devops_project.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.ProductCategory;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    Long idProduct;
    String title;
    float price;
    int quantity;
    ProductCategory category;

public static Product DTOtoEntity(ProductDTO pd){
    Product p = new Product();
    p.setIdProduct(pd.getIdProduct());
    p.setPrice(pd.getPrice());
    p.setTitle(pd.getTitle());
    p.setQuantity(pd.getQuantity());
    p.setCategory(pd.getCategory());
    return p;
}
}
