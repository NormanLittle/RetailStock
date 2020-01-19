package com.retail.application.stock.rule;

import org.drools.compiler.compiler.DroolsParserException;
import org.drools.compiler.compiler.PackageBuilder;
import org.drools.core.RuleBase;
import org.drools.core.RuleBaseFactory;
import org.drools.core.WorkingMemory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

@Component
public class StockCheckRuleEngineFactory {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public StockCheckRuleEngine create() {
        logger.info("Creating stock check rule engine.");
        WorkingMemory workingMemory = createWorkingMemorySessionForRules();
        if (workingMemory == null) {
            logger.error("Unable to create rule engine.");
        }
        return new StockCheckRuleEngine(workingMemory);
    }

    private WorkingMemory createWorkingMemorySessionForRules() {
        try (Reader reader = new InputStreamReader(getClass().getResourceAsStream("/com/rule/rules.drl"))) {
            PackageBuilder packageBuilder = new PackageBuilder();
            packageBuilder.addPackageFromDrl(reader);

            RuleBase ruleBase = RuleBaseFactory.newRuleBase();
            ruleBase.addPackage(packageBuilder.getPackage());

            return ruleBase.newStatefulSession();

        } catch (IOException e) {
            logger.error("Failed to read rule definition file.");
        } catch (DroolsParserException e) {
            logger.error("Failed to parse rule definition file.");
        }
        return null;
    }
}
