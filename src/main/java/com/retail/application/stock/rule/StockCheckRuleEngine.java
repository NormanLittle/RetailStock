package com.retail.application.stock.rule;

import com.retail.application.stock.StockApi.StockCheck;
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

    public void execute(List<StockCheck> stockChecks) {
        logger.info("Executing stock check rules.");
        if (workingMemory == null) {
            logger.error("Unable to execute stock check rules.");
            return;
        }
        executeAllRules(stockChecks);
    }

    private void executeAllRules(List<StockCheck> stockChecks) {
        for (StockCheck stockCheck : stockChecks) {
            workingMemory.insert(stockCheck);
        }
        workingMemory.fireAllRules();
    }
}
