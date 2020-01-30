package com.retail;

import com.retail.stock.repository.audit.AuditRepository;
import com.retail.stock.repository.product.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PreDestroy;
import java.util.Objects;
import java.util.stream.Stream;

import static com.retail.stock.repository.product.Product.aProduct;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.retail.stock.repository")
@EntityScan(basePackages = "com.retail.stock.repository")
public class RetailApplication implements ApplicationRunner {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ProductRepository productRepository;
    private final AuditRepository auditRepository;

    public RetailApplication(ProductRepository productRepository,
                             AuditRepository auditRepository) {
        this.productRepository = productRepository;
        this.auditRepository = auditRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(RetailApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        // Initialise database with products from the example...
        Stream.of(aProduct().withName("a").withQuantity(5),
                  aProduct().withName("b").withQuantity(8),
                  aProduct().withName("c").withQuantity(2),
                  aProduct().withName("d").withQuantity(0),
                  aProduct().withName("e").withQuantity(1))
              .forEach(productRepository::save);
    }

    @PreDestroy
    public void tearDown() {
        // Display audit for stock check created...
        auditRepository.findAll()
                       .stream()
                       .map(Objects::toString)
                       .forEach(logger::info);
    }
}
