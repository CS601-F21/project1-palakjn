package cs601.project1.controllers;

import cs601.project1.models.InvertedIndex;

/**
 * Creates Inverted Index Instance only once.
 *
 * @author Palak Jain
 */
public class IndexCreator {

    private static InvertedIndex reviewIndex;
    private static InvertedIndex qaIndex;

    private IndexCreator() { }

    /**
     * Creates new inverted index for reviews dataset if not exist
     * else will return the existing one.
     * @return Instance of InvertedIndex
     */
    public static InvertedIndex getReviewIndex() {
        if(reviewIndex == null) {
            reviewIndex = new InvertedIndex();
        }

        return  reviewIndex;
    }

    /**
     * Creates new inverted index for QA dataset if not exist
     * else will return the existing one.
     * @return Instance of InvertedIndex
     */
    public static InvertedIndex getQaIndex() {
        if(qaIndex == null) {
            qaIndex = new InvertedIndex();
        }

        return qaIndex;
    }
}
