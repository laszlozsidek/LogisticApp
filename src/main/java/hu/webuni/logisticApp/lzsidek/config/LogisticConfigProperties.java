package hu.webuni.logisticApp.lzsidek.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "logistic")
@Component
public class LogisticConfigProperties {

    private Income income;

    public Income getIncome() {
        return income;
    }

    public void setIncome(Income income) {
        this.income = income;
    }

    public static class Income {
        private Integer limit1;
        private Integer limit2;
        private Integer limit3;
        private Double percent1;
        private Double percent2;
        private Double percent3;

        public Integer getLimit1() {
            return limit1;
        }

        public void setLimit1(Integer limit1) {
            this.limit1 = limit1;
        }

        public Integer getLimit2() {
            return limit2;
        }

        public void setLimit2(Integer limit2) {
            this.limit2 = limit2;
        }

        public Integer getLimit3() {
            return limit3;
        }

        public void setLimit3(Integer limit3) {
            this.limit3 = limit3;
        }

        public Double getPercent1() {
            return percent1;
        }

        public void setPercent1(Double percent1) {
            this.percent1 = percent1;
        }

        public Double getPercent2() {
            return percent2;
        }

        public void setPercent2(Double percent2) {
            this.percent2 = percent2;
        }

        public Double getPercent3() {
            return percent3;
        }

        public void setPercent3(Double percent3) {
            this.percent3 = percent3;
        }
    }

}