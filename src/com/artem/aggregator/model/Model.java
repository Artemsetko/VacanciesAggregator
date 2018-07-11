package com.artem.aggregator.model;

import com.artem.aggregator.view.View;
import com.artem.aggregator.vo.Vacancy;

import java.util.ArrayList;
import java.util.List;

public class Model {
    View view;
    Provider[] providers;

    public Model(View view, Provider... providers) {
        if (providers == null || view == null || providers.length == 0)
            throw new IllegalArgumentException();
        this.view = view;
        this.providers = providers;
    }

    public void selectCity(String city) {
        List<Vacancy> vacancies = new ArrayList<>();
        for (Provider provider : providers) {
            vacancies.addAll(provider.getJavaVacancies(city));
        }
        view.update(vacancies);
    }
}
