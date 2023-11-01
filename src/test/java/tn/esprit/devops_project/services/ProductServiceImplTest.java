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
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.ProductCategory;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.entities.Supplier;
import tn.esprit.devops_project.repositories.StockRepository;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@TestExecutionListeners  ({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@ActiveProfiles("test")
class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;
@Autowired
    private StockRepository stockRepository;

    @Test
    @DatabaseSetups({
            @DatabaseSetup("/data-set/product-data.xml"),
            @DatabaseSetup("/data-set/stock-data.xml")
    })
    void addProduct() {

        Stock stock = stockRepository.findById(1L).orElseThrow(() -> new NullPointerException("stock not found"));
        log.info("object: ----------- " + stock.toString());
        Product product = new Product();
        product.setTitle("prod1");
        product.setPrice(12.0F);
        product.setQuantity(150);
        product.setCategory(ProductCategory.ELECTRONICS);
        product.setStock(stock);
        productService.addProduct(product,1L);

        assertEquals(1,stock.getProducts().size()  );

    }


    @Test
    @DatabaseSetup("/data-set/product-data.xml")
    void retrieveProduct() {

        Product product = productService.retrieveProduct(1L);
        assertEquals("product1", product.getTitle());
        assertEquals(10.0F, product.getPrice());
        assertEquals(100, product.getQuantity());
        assertEquals(ProductCategory.ELECTRONICS, product.getCategory());

    }

    @Test
    @DatabaseSetup("/data-set/product-data.xml")
    void retreiveAllProduct() {
        List<Product> allProducts = productService.retreiveAllProduct();
        assertEquals(allProducts.size(), 2);

    }

    @Test
    @DatabaseSetup("/data-set/product-data.xml")
    void retrieveProductByCategory() {
        List<Product> electronicsProducts = productService.retrieveProductByCategory(ProductCategory.ELECTRONICS);
        assertNotNull(electronicsProducts);
        assertEquals(1, electronicsProducts.size());
    }

    @Test
    @DatabaseSetup("/data-set/product-data.xml")
    void deleteProduct() {
        productService.deleteProduct(1L);
        productService.deleteProduct(2L);
        List<Product> deleteproduct =productService.retreiveAllProduct();
        assertEquals(new ArrayList<>(), deleteproduct);


    }






  /*  @Test
    @DatabaseSetup("/data-set/product-data.xml")
    void retreiveProductStock() {
        List<Product> productsInStock = productService.retreiveProductStock(1L);
        assertNotNull(productsInStock);
        assertEquals(3, productsInStock.size());

    }*/

    @Test
    @DatabaseSetup("/data-set/product-data.xml")
    void retrieveProduct_withnullid() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            Product product = productService.retrieveProduct(200L);
        });
    }
}