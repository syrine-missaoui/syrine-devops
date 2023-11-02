package tn.esprit.devops_project.services;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import lombok.extern.slf4j.Slf4j;
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
import tn.esprit.devops_project.entities.Invoice;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.ProductCategory;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.InvoiceRepository;
import tn.esprit.devops_project.utils.Constants;

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
class InvoiceServiceImplTest {

    @Autowired
    private InvoiceServiceImpl invoiceService;
    @Autowired
     private InvoiceRepository invoiceRepository;


/*    @Test
    @DatabaseSetup("/data-set/invoice-data.xml")
  *//*  void retrieveAllInvoices() {
        List<Invoice> Allinvoices = invoiceService.retrieveAllInvoices();
        assertEquals(Allinvoices.size(), 1);
*//*
    }

    @Test
    @DatabaseSetup("/data-set/invoice-data.xml")
    void cancelInvoice() {
      *//*  Invoice invoice=new Invoice();
        invoice.setIdInvoice(1L);
        invoice.setArchived(false);
        invoiceRepository.save(invoice);
        invoiceService.cancelInvoice(1L);
        Invoice retriveInvoice = invoiceRepository.findById(1L).orElseThrow(() -> new NullPointerException("invoice not found"));
        assertNotNull(retriveInvoice);
        assertTrue(retriveInvoice.getArchived());*//*

    }*/



    @Test
    void retrieveInvoice() {

    }

    @Test
    void getInvoicesBySupplier() {
    }

    @Test
    void assignOperatorToInvoice() {
    }

    @Test
    void getTotalAmountInvoiceBetweenDates() {
    }
}