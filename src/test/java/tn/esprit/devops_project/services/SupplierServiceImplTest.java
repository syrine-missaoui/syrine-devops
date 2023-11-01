package tn.esprit.devops_project.services;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.CharSetUtils;
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
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.entities.Supplier;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Slf4j
@Transactional
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@ActiveProfiles("test")
 class SupplierServiceImplTest {

    @Autowired
    private SupplierServiceImpl supplierService;

    @Test
    @DatabaseSetup("/data-set/supplier-data.xml")
    void addSupplier() {
        Supplier supplier = new Supplier();
        supplier.setCode("aa01");
        supplier.setLabel("test label");
        Supplier result = supplierService.addSupplier(supplier);
        assertNotNull(result);
        assertEquals("aa01", result.getCode());
        assertEquals("test label", result.getLabel());
    }

    @Test
    @DatabaseSetup("/data-set/supplier-data.xml")
    void retrieveAllSuppliers() {
        List<Supplier> allSuppliers = supplierService.retrieveAllSuppliers();
        assertNotNull(allSuppliers);
        for (Supplier s : allSuppliers) {
            log.info("object: ----------- " + s.toString());
        }

        assertEquals(1, allSuppliers.size());
        assertTrue(allSuppliers.stream().anyMatch(s -> "a002".equals(s.getCode())));
    }

    @Test
    @DatabaseSetup("/data-set/supplier-data.xml")
    void updateSupplier() {
        Supplier supplier = new Supplier();
        supplier.setIdSupplier(1L);
        supplier.setCode("55241585");
        supplier.setLabel("updated success");
        Supplier result = supplierService.updateSupplier(supplier);
        Supplier fortesting = supplierService.retrieveSupplier(1L);
        assertEquals(fortesting, result);
    }

    @Test
    @DatabaseSetup("/data-set/supplier-data.xml")
    void deleteSupplier() {
        supplierService.deleteSupplier(1L);
        List<Supplier> deleteSupplier = supplierService.retrieveAllSuppliers();

        assertEquals(new ArrayList<>(), deleteSupplier);
    }


    @Test
    @DatabaseSetup("/data-set/supplier-data.xml")
    void retrieveSupplier() {
        Supplier supplier = supplierService.retrieveSupplier(1L);
        assertEquals(1L, supplier.getIdSupplier());
        assertEquals("a002", supplier.getCode());
        assertEquals("test label", supplier.getLabel());
    }


    @Test
    @DatabaseSetup("/data-set/supplier-data.xml")
    void retrievesSupplier_withnullid() {
        Exception exception = assertThrows(IllegalArgumentException.class,() -> { Supplier supplier = this.supplierService.retrieveSupplier(100L);
            });
    }
}















