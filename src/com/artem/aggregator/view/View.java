package com.artem.aggregator.view;

import com.artem.aggregator.Controller;
import com.artem.aggregator.vo.Vacancy;

import java.util.List;

public interface View {
    void update(List<Vacancy> vacancies);
    void setController(Controller controller);
}
