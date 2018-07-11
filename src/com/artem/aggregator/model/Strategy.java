package com.artem.aggregator.model;

import com.artem.aggregator.vo.Vacancy;

import java.util.List;

public interface Strategy {
    List<Vacancy> getVacancies(String searchString);
}
