package com.retail.stock.application.rule;

import org.drools.core.WorkingMemory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class StockCheckRuleEngine {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final WorkingMemory workingMemory;

    public StockCheckRuleEngine(WorkingMemory workingMemory) {
        this.workingMemory = workingMemory;
    }

    public void execute(List<StockCheckFact> stockCheckFacts) {
        logger.info("Executing stock check rules.");
        if (workingMemory == null) {
            logger.error("Unable to execute stock check rules.");
            return;
        }
        executeAllRules(stockCheckFacts);
    }

    private void executeAllRules(List<StockCheckFact> stockCheckFacts) {
        for (StockCheckFact stockCheckFact : stockCheckFacts) {
            workingMemory.insert(stockCheckFact);
        }
        workingMemory.fireAllRules();
    }
}
