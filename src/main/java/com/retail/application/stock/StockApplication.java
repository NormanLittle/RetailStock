package com.retail.application.stock;

import com.retail.application.stock.rule.StockCheckRuleEngine;
import com.retail.application.stock.rule.StockCheckRuleEngineFactory;
import com.retail.repository.audit.Audit;
import com.retail.repository.audit.AuditRepository;
import com.retail.repository.product.Product;
import com.retail.repository.product.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Component
public class StockApplication implements StockApi {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ProductRepository productRepository;
    private final AuditRepository auditRepository;
    private final StockCheckRuleEngineFactory stockCheckRuleEngineFactory;

    @Autowired
    public StockApplication(ProductRepository productRepository,
                            AuditRepository auditRepository,
                            StockCheckRuleEngineFactory stockCheckRuleEngineFactory) {
        this.productRepository = productRepository;
        this.auditRepository = auditRepository;
        this.stockCheckRuleEngineFactory = stockCheckRuleEngineFactory;
    }

    private static LocalDate today() {
        return LocalDate.now();
    }

    @Override
    public void executeStockCheck() {
        List<Product> products = readAllProducts();
        if (isNotEmpty(products)) {
            List<StockCheck> stockChecks = processStockCheckForProducts(products);
            writeAuditForStockChecks(stockChecks);
        }
    }

    private List<Product> readAllProducts() {
        logger.info("Reading all products for stock check.");
        List<Product> products = productRepository.findAll();
        if (isEmpty(products)) {
            logger.warn("No products are available for stock check.");
            return newArrayList();
        }

        logger.info("Read {} product(s) for stock check.", products.size());
        return products;
    }

    private List<StockCheck> processStockCheckForProducts(List<Product> products) {
        logger.info("Processing stock check for {} product(s).", products.size());
        List<StockCheck> stockChecks = products.stream()
                                               .map(StockCheck::new)
                                               .collect(toList());

        StockCheckRuleEngine stockCheckRuleEngine = stockCheckRuleEngineFactory.create();
        stockCheckRuleEngine.execute(stockChecks);

        return stockChecks;
    }

    private void writeAuditForStockChecks(List<StockCheck> stockChecks) {
        logger.info("Writing audit entry for {} product stock check(s).", stockChecks.size());
        stockChecks.stream()
                   .map(stockCheck -> new Audit(today(),
                                                stockCheck.getProduct().getName(),
                                                stockCheck.getQuantityRequired()))
                   .forEach(auditRepository::save);
    }
}
