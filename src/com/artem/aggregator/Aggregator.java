package com.artem.aggregator;

import com.artem.aggregator.model.HHStrategy;
import com.artem.aggregator.model.Model;
import com.artem.aggregator.model.MoikrugStrategy;
import com.artem.aggregator.model.Provider;
import com.artem.aggregator.view.HtmlView;
import com.artem.aggregator.view.View;

public class Aggregator {
    public static void main(String[] args) {
        View view = new HtmlView();
        Provider provider = new Provider();
        provider.setStrategy(new HHStrategy());
        Provider providerNew = new Provider();
        providerNew.setStrategy(new MoikrugStrategy());
        Model model = new Model(view, provider, providerNew);
        Controller controller = new Controller(model);
        view.setController(controller);
        ((HtmlView) view).userCitySelectEmulationMethod();
    }
}
