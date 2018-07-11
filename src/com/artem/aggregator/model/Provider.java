package com.artem.aggregator.model;

import com.artem.aggregator.vo.Vacancy;

import java.util.Collections;
import java.util.List;

public class Provider {
    Strategy strategy;

    public Provider() {

    }

    public Provider(Strategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public List<Vacancy> getJavaVacancies(String searchString) {
        if (searchString == null)
            return Collections.EMPTY_LIST;
        else
            return strategy.getVacancies(searchString);
    }
}
