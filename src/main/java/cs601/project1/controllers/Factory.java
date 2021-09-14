package cs601.project1.controllers;

import cs601.project1.Constants;
import cs601.project1.models.InvertedIndex;

/**
 * @author Palak Jain
 */
public class Factory {

    /**
     * Gives Inverted Index instance based on the type of instance.
     * @param type Either REVIEW or QA
     * @return Instance of Inverted Index
     */
    public static InvertedIndex getIndex(Constants.Type type) {
        InvertedIndex index = null;

        if(type == Constants.Type.REVIEW) {
            index = IndexCreator.getReviewIndex();
        }
        else if(type == Constants.Type.QA) {
            index = IndexCreator.getQaIndex();
        }

        return  index;
    }
}
