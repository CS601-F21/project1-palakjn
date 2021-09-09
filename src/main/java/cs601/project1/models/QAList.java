package cs601.project1.models;

import java.util.ArrayList;
import java.util.List;

public class QAList {

    private List<QA> allQAs;

    public QAList() {
        allQAs = new ArrayList<>();
    }

    public void add(int index, QA qa) {
        allQAs.add(index, qa);
    }

    public String toString(String asin) {
        StringBuilder stringBuilder = new StringBuilder();
        List<QA> queries = allQAs.stream().filter(o -> o.getAsin().equals(asin)).toList();
        int bulletNumbers = 0;

        stringBuilder.append(String.format("Asin number: %s. Questions & Answers: \n", asin));

        for (QA query : queries) {
            stringBuilder.append(String.format("\t %d) %s \n", bulletNumbers, query.toString()));
            bulletNumbers++;
        }

        return stringBuilder.toString();
    }

    public String toString(int index) {
        String output = null;

        if(index >= 0 && index < allQAs.size()) {
            QA query = allQAs.get(index);
            output = String.format("ASIN Number: %s. %s", query.getAsin(), query.toString());
        }

        return output;
    }

    public int getCount() {
        return allQAs.size();
    }
}
