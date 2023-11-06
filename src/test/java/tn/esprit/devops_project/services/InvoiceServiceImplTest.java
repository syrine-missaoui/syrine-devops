package tn.esprit.devops_project.services;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseSetups;
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
import tn.esprit.devops_project.entities.Operator;
import tn.esprit.devops_project.entities.Supplier;
import tn.esprit.devops_project.repositories.InvoiceRepository;
import tn.esprit.devops_project.repositories.OperatorRepository;
import tn.esprit.devops_project.repositories.SupplierRepository;
import tn.esprit.devops_project.utils.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    private SupplierRepository supplierRepository;
   @Autowired
   private OperatorRepository operatorRepository;
   @Autowired
   private SupplierServiceImpl supplierService;

    @Test
    @DatabaseSetup("/data-set/invoice-data.xml")
    void retrieveAllInvoices() {
        List<Invoice> Allinvoices = invoiceService.retrieveAllInvoices();
        assertEquals(Allinvoices.size(),1);
    }
    @Test
    @DatabaseSetup("/data-set/invoice-data.xml")
    void cancelInvoice() {
        Invoice invoice1=new Invoice();
        Invoice invoice = invoiceRepository.findById(1L).orElseThrow(() -> new NullPointerException(Constants.Invoice_not_found));
        invoiceService.cancelInvoice(1L);
       invoice.setArchived(true);
        invoiceRepository.save(invoice);
        invoiceService.cancelInvoice(1L);
        Invoice retriveInvoice = invoiceRepository.findById(1L).orElseThrow(() -> new NullPointerException("invoice not found"));
        assertNotNull(retriveInvoice);
        assertEquals(true, invoice.getArchived());
    }
    @Test
    @DatabaseSetup("/data-set/invoice-data.xml")
    void cancelinvoiceNullId() {
        assertThrows(NullPointerException.class, () -> {
            invoiceService.cancelInvoice(11L);
        });
    }
    @Test
    @DatabaseSetup("/data-set/invoice-data.xml")
    void retrieveInvoice() {
        Invoice resultat=invoiceService.retrieveInvoice(1l);
        assertNotNull(resultat);
        assertEquals(1L,resultat.getIdInvoice());
        /*  invoiceRepository.findById(1L).orElseThrow(() -> new NullPointerException(Constants.Invoice_not_found));
        Invoice invoice = invoiceService.retrieveInvoice(1L);
        assertEquals(200.0F, invoice.getAmountInvoice());
        assertEquals(100.0F, invoice.getAmountDiscount());
        boolean expected=false;
        assertEquals(expected, invoice.getArchived());*/
    }
    @Test
    @DatabaseSetup("/data-set/invoice-data.xml")
    void nullidinvoice() {
        assertThrows(NullPointerException.class, () -> {
            invoiceService.retrieveInvoice(11L);
        });
    }
    @Test
    @DatabaseSetups({
            @DatabaseSetup("/data-set/invoice-data.xml"),
            @DatabaseSetup("/data-set/supplier-data.xml")
    })
    void getInvoicesBySupplier() {
        Supplier supplier = supplierRepository.findById(1L).orElseThrow(() -> new NullPointerException(Constants.Supplier_not_found));
        Set<Invoice> invoices = supplier.getInvoices();
        Set<Invoice> result = invoiceService.getInvoicesBySupplier(1L);
        assertEquals(invoices,result);

    }
    @Test
    @DatabaseSetup("/data-set/invoice-data.xml")
    void SupllierwithNullId() {
        assertThrows(NullPointerException.class, () -> {
            invoiceService.getInvoicesBySupplier(11L);
        });
    }

    @Test
    @DatabaseSetups({
            @DatabaseSetup("/data-set/invoice-data.xml"),
            @DatabaseSetup("/data-set/operator-data.xml")
    })
    void assignOperatorToInvoice() {
        invoiceService.assignOperatorToInvoice(1L,1L);
        Invoice in = invoiceRepository.findById(1L).orElseThrow(() -> new NullPointerException(Constants.Invoice_not_found));
        Operator op = operatorRepository.findById(1L).orElseThrow(() -> new NullPointerException(Constants.Operator_not_found));
        Set<Invoice> result = op.getInvoices();
        Iterator<Invoice> iterator = result.iterator();
        assertNotNull(op);
        assertNotNull(result);
        assertEquals(in,iterator.next());
        assertThrows(NullPointerException.class, () -> {
            invoiceService.assignOperatorToInvoice(1L, 100L);
        });
        assertThrows(NullPointerException.class, () -> {
            invoiceService.assignOperatorToInvoice(100L, 1L);
        });
    }

    @Test
    @DatabaseSetup("/data-set/invoice-data.xml")
    void getTotalAmountInvoiceBetweenDates() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse("2023-06-01");
        Date endDate = sdf.parse("2023-06-02");
        float totalAmount = invoiceService.getTotalAmountInvoiceBetweenDates(startDate, endDate);
        assertEquals(200.0, totalAmount, 0.01);

    }
}