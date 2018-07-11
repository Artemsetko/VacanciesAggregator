package com.artem.aggregator.view;

import com.artem.aggregator.Controller;
import com.artem.aggregator.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class HtmlView implements View {
    private Controller controller;
    private final String filePath = "./4.JavaCollections/src/" + this.getClass().getPackage().getName().replaceAll("\\.", "/") + "/" + "vacancies.html";

    @Override
    public void update(List<Vacancy> vacancies) {
        String updatedFileContent = getUpdatedFileContent(vacancies);
        updateFile(updatedFileContent);
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void userCitySelectEmulationMethod() {
        controller.onCitySelect("Odessa");
    }

    private String getUpdatedFileContent(List<Vacancy> vacancyList) {
        Document doc;
        try {
            doc = getDocument();

            Elements elements = doc.getElementsByAttributeValue("class", "vacancy");
            for (int i = 0; i < elements.size(); i++) {
                 elements.get(i).remove();
            }

            Element templateElement = doc.getElementsByClass("template").first();
            Element cloneTemplateElement = templateElement.clone();
            cloneTemplateElement.removeClass("template");
            cloneTemplateElement.removeAttr("style");


            for (Vacancy vacancy : vacancyList) {
                Element e = cloneTemplateElement.clone();

                e.getElementsByClass("city").first().text(vacancy.getCity());
                e.getElementsByClass("companyName").first().text(vacancy.getCompanyName());
                e.getElementsByClass("salary").first().text(vacancy.getSalary());

                Element linkElement = e.getElementsByClass("title").first();
                linkElement.text(vacancy.getTitle());
                linkElement.attr("href", vacancy.getUrl());

                templateElement.before(e.outerHtml());
            }

        } catch (IOException e) {
            e.printStackTrace();
            return "Some exception occurred";
        }
        return doc.toString();
    }

    private void updateFile(String str) {
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            outputStream.write(str.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Document getDocument() throws IOException {
        Document document;
        document = Jsoup.parse(new File(filePath), "UTF-8");
        return document;
    }
}
