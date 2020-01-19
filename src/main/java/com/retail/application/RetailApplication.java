package com.retail.application;

import com.retail.application.stock.StockApi;
import com.retail.repository.audit.AuditRepository;
import com.retail.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PreDestroy;
import java.util.stream.Stream;

import static com.retail.repository.product.Product.aProduct;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.retail.repository")
@EntityScan(basePackages = "com.retail.repository")
public class RetailApplication implements ApplicationRunner {

    private final ProductRepository productRepository;
    private final AuditRepository auditRepository;

    @Autowired
    public RetailApplication(ProductRepository productRepository,
                             AuditRepository auditRepository) {
        this.productRepository = productRepository;
        this.auditRepository = auditRepository;
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(RetailApplication.class, args);

        StockApi stockApi = applicationContext.getBean(StockApi.class);
        stockApi.executeStockCheck();
    }

    @Override
    public void run(ApplicationArguments args) {
        // Initialise database with products from the example.
        Stream.of(aProduct().withName("a").withQuantity(5),
                  aProduct().withName("b").withQuantity(8),
                  aProduct().withName("c").withQuantity(2),
                  aProduct().withName("d").withQuantity(0),
                  aProduct().withName("e").withQuantity(1))
              .forEach(productRepository::save);
    }

    @PreDestroy
    public void tearDown() {
        // Display audit for stock check(s) performed.
        auditRepository.findAll()
                       .forEach(System.out::println);
    }
}
