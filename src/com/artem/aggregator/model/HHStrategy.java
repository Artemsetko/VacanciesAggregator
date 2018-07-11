package com.artem.aggregator.model;

import com.artem.aggregator.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HHStrategy implements Strategy {
    Document document;
    private static final String URL_FORMAT = "http://hh.ru/search/vacancy?text=java+%s&page=%d";
   // private static final String URL_FORMAT = "https://javarush.ru/testdata/big28data.html";

    @Override
    public List<Vacancy> getVacancies(String searchString) {
        List<Vacancy> vacancies = new ArrayList<>();
        if (searchString == null)
            return Collections.EMPTY_LIST;
        int j = 0;
        while (true) {
            try {
                document = getDocument(searchString, j++);
                Elements elements = document.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy");
                if (elements.size() > 1) {
                    for (int i = 0; i < elements.size(); i++) {
                        Vacancy vacancy = new Vacancy();

                        String title = elements.get(i).getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-title").text();
                        vacancy.setTitle(title);

                        String salary = elements.get(i).getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-compensation").text();
                        if (salary != null)
                            vacancy.setSalary(salary);
                        else vacancy.setSalary("");

                        String city = elements.get(i).getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-address").text();
                        vacancy.setCity(city);

                        String companyName = elements.get(i).getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-employer").text();
                        vacancy.setCompanyName(companyName);

                        String url = elements.get(i).getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-title").attr("href");
                        vacancy.setUrl(url);

                        String siteName = "http://hh.ua";
                        vacancy.setSiteName(siteName);

                        vacancies.add(vacancy);
                    }
                } else break;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return vacancies;
    }

    protected Document getDocument(String searchString, int page) throws IOException {
        String url = String.format(URL_FORMAT, searchString, page);
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.79 Safari/537.36")
                .referrer("http://google.com.ua").get();
        return doc;
    }
}
