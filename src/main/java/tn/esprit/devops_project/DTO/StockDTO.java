package tn.esprit.devops_project.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.Stock;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockDTO {
    long idStock;
    String title;
    public static Stock DTOtoEntity(StockDTO st){
        Stock stock = new Stock();
        stock.setIdStock(st.getIdStock());
        stock.setTitle(st.getTitle());
        return stock;
    }
}
