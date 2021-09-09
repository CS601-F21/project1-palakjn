package cs601.project1.models;

import java.util.List;

public class Review {

    private String reviewerID;
    private String asin;
    private String reviewerName;
    private List<Integer> helpful;
    private String reviewText;
    private float overall;
    private String summary;
    private long unixReviewTime;
    private String reviewTime;

    public Review(String asin, String reviewText) {
        this.asin = asin;
        this.reviewText = reviewText;
    }

    public String getAsin() {
        return asin;
    }

    public String getReviewText() {
        return reviewText;
    }

    public String toString() {
        return String.format("ReviewerID: %s, ReviewerName: %s, ReviewerText: %s", reviewerID, reviewerName, reviewText);
    }
}
