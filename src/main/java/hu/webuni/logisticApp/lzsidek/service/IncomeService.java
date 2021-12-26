package hu.webuni.logisticApp.lzsidek.service;

import hu.webuni.logisticApp.lzsidek.config.LogisticConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IncomeService {

    @Autowired
    LogisticConfigProperties config;

    private Double getReduceInPercent(int delayInMinutes) {
        if (delayInMinutes < config.getIncome().getLimit1()) {
            return 0.0;
        }
        if (delayInMinutes < config.getIncome().getLimit2() && delayInMinutes >= config.getIncome().getLimit1()) {
            return config.getIncome().getPercent1();
        }
        if (delayInMinutes < config.getIncome().getLimit3() && delayInMinutes >= config.getIncome().getLimit2()) {
            return config.getIncome().getPercent2();
        }
        return config.getIncome().getPercent3();
    }

    public Double getReducedIncome(Double plannedIncome, int delayInMinutes) {
        return plannedIncome * (100 - getReduceInPercent(delayInMinutes)) / 100;
    }
}