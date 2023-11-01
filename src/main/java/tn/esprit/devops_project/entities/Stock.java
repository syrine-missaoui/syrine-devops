package tn.esprit.devops_project.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Stock implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idStock;
    String title;
    @OneToMany(mappedBy = "stock")
    private Set<Product> products;

    @Override
    public String toString() {
        return "Stock{" +
                "idStock=" + idStock +
                ", title='" + title + '\'' +
                ", products=" + products +
                '}';
    }
}
