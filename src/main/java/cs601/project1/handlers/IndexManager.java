package cs601.project1.handlers;

import cs601.project1.models.InvertedIndex;

public class IndexManager {

    private static InvertedIndex reviewIndex;
    private static InvertedIndex qaIndex;

    private IndexManager() { }

    public static InvertedIndex getReviewIndex() {
        if(reviewIndex == null) {
            reviewIndex = new InvertedIndex();
        }

        return  reviewIndex;
    }

    public static InvertedIndex getQaIndex() {
        if(qaIndex == null) {
            qaIndex = new InvertedIndex();
        }

        return qaIndex;
    }
}
