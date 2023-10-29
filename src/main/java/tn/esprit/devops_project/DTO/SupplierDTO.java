package tn.esprit.devops_project.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import tn.esprit.devops_project.entities.Supplier;
import tn.esprit.devops_project.entities.SupplierCategory;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class SupplierDTO {
    Long idSupplier;
    String code;
    String label;
    SupplierCategory supplierCategory;

    public  static Supplier DTOtoEntity(SupplierDTO sd){
        Supplier s = new Supplier();
        s.setIdSupplier(sd.getIdSupplier());
        s.setCode(sd.getCode());
        s.setLabel(sd.getLabel());
        s.setSupplierCategory(sd.getSupplierCategory());
        return s;
    }
}
