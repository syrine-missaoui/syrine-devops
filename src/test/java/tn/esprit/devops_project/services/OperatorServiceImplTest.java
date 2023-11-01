package tn.esprit.devops_project.services;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.devops_project.entities.Operator;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.entities.Supplier;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@ActiveProfiles("test")
class OperatorServiceImplTest {
    @Autowired
    private OperatorServiceImpl operatorService;

    @Test
    @DatabaseSetup("/data-set/operator-data.xml")
    void retrieveAllOperators() {
        List<Operator> alloperators = operatorService.retrieveAllOperators();
        assertNotNull(alloperators);
        assertEquals(1, alloperators.size());
    }
    @Test
    @DatabaseSetup("/data-set/operator-data.xml")
    void addOperator() {
        Operator operator=new Operator();
        operator.setFname("ouus");
        operator.setLname("plll");
        operator.setPassword("hkhcvdk");
        Operator resultat = operatorService.addOperator(operator);
        assertNotNull(resultat);
        assertEquals("ouus",resultat.getFname());
        assertEquals("plll" ,resultat.getLname());
        assertEquals("hkhcvdk",resultat.getPassword());
    }
    @Test
    @DatabaseSetup("/data-set/operator-data.xml")
    void deleteOperator() {
        operatorService.deleteOperator(1L);
        List<Operator> deleteOperator =operatorService.retrieveAllOperators();
        assertEquals(new ArrayList<>(), deleteOperator);

    }

    @Test
    @DatabaseSetup("/data-set/operator-data.xml")
    void updateOperator() {
        Operator operator= new Operator();
        operator.setIdOperateur(1L);
        operator.setLname("new Lname");
        operator.setFname("new Fname");
        operator.setPassword("12549");
        Operator result = operatorService.updateOperator(operator);
        Operator fortest = operatorService.retrieveOperator(1L);
        assertEquals(fortest,result);
    }



    @Test
    @DatabaseSetup("/data-set/operator-data.xml")
    void retrieveOperator() {
        Operator operator = operatorService.retrieveOperator(1L);
        assertEquals("saa", operator.getFname());
        assertEquals("bill", operator.getLname());
        assertEquals("1234",operator.getPassword());
    }

    @Test
    @DatabaseSetup("/data-set/operator-data.xml")
    void retrievesOperator_withnullid() {

        Exception exception = assertThrows(NullPointerException.class,() -> { Operator operator= operatorService.retrieveOperator(50L);
        });



    }
}
