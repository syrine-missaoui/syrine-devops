package tn.esprit.devops_project.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.devops_project.entities.Operator;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OperatorDTO {
    Long idOperateur;
    String fname;
    String lname;
    String password;

    public static Operator DTOtoEntity(OperatorDTO op){
        Operator operator = new Operator();
        operator.setIdOperateur(op.getIdOperateur());
        operator.setFname(op.getFname());
        operator.setPassword(op.getPassword());
        operator.setLname(op.getLname());
        return operator;
    }
}

