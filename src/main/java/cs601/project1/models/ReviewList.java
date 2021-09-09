package cs601.project1.models;

import java.util.ArrayList;
import java.util.List;

public class ReviewList {

    private List<Review> allReviews;

    public ReviewList() {
        allReviews = new ArrayList<>();
    }

    public void add(int index, Review review) {
        allReviews.add(index, review);
    }

    public String toString(String asin) {
        StringBuilder stringBuilder = new StringBuilder();
        List<Review> reviews = allReviews.stream().filter(o -> o.getAsin().equals(asin)).toList();
        int bulletNumbers = 0;

        stringBuilder.append(String.format("Asin number: %s. Reviews: \n", asin));

        for (Review review : reviews) {
            stringBuilder.append(String.format("\t %d) %s \n", bulletNumbers, review.toString()));
            bulletNumbers++;
        }

        return stringBuilder.toString();
    }

    public String toString(int index) {
        String output = null;

        if(index >= 0 && index < allReviews.size()) {
            Review review = allReviews.get(index);
            output = String.format("ASIN Number: %s. %s", review.getAsin(), review.toString());
        }

        return output;
    }

    public int getCount() {
        return allReviews.size();
    }
}
