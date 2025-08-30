package hu.tomi.logistic.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "delay.income.decrease")
public class DelayConfig {

    private List<Integer> thresholds;
    private List<Integer> percentages;

    public List<Integer> getThresholds() {
        return thresholds;
    }

    public void setThresholds(List<Integer> thresholds) {
        this.thresholds = thresholds;
    }

    public List<Integer> getPercentages() {
        return percentages;
    }

    public void setPercentages(List<Integer> percentages) {
        this.percentages = percentages;
    }

    public int getDecreasePercentForDelay(int delayMinutes) {
        for (int i = thresholds.size() - 1; i >= 0; i--) {
            if (delayMinutes >= thresholds.get(i)) {
                return percentages.get(i);
            }
        }
        return 0;
    }
}
