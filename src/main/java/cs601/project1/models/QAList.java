package cs601.project1.models;

import java.util.ArrayList;
import java.util.List;

/**
 * A collection of questions and answers from the QA dataset. The user will have a control on which index to add a new object.
 * The user can get a customized string representation of object fields depending on an input value.
 *
 * @author Palak Jain
 */
public class QAList {

    private List<QA> allQAs;

    public QAList() {
        allQAs = new ArrayList<>();
    }

    /**
     * Adding QA object to a collection at particular index.
     *
     * @param index Index of a list
     * @param qa Distinct QA object
     */
    public void add(int index, QA qa) {
        allQAs.add(index, qa);
    }

    /**
     * Gets customized string representation of object fields whose ASIN number is the given input.
     *
     * @param asin ASIN number of a product
     * @return A formatted string
     */
    public String toString(String asin) {
        StringBuilder stringBuilder = new StringBuilder();
        //Getting those questions and answers whose asin number is equal to arg "asin" value.
        List<QA> queries = allQAs.stream().filter(o -> o.getAsin().equalsIgnoreCase(asin)).toList();
        int bulletNumbers = 0;

        if(queries.size() > 0) {
            stringBuilder.append(String.format("Asin number: %s. Questions & Answers: \n", asin));

            for (QA query : queries) {
                stringBuilder.append(String.format("\t %d) %s \n", bulletNumbers, query.toString()));
                bulletNumbers++;
            }
        }

        return stringBuilder.toString();
    }

    /**
     * Gets customized string representation of field values of review object at a provided index.
     *
     * @param index Index of ArrayList
     * @return A formatted string
     */
    public String toString(int index) {
        String output = null;

        if(index >= 0 && index < allQAs.size()) {
            QA query = allQAs.get(index);
            output = String.format("ASIN Number: %s. %s", query.getAsin(), query.toString());
        }

        return output;
    }

    /**
     * Returns the number of elements in a list
     *
     * @return The number of elements in a list.
     */
    public int getCount() {
        return allQAs.size();
    }
}
