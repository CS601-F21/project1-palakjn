package cs601.project1.handlers;

import cs601.project1.Constants;
import cs601.project1.models.InvertedIndex;

public class Factory {
    public static InvertedIndex getIndex(Constants.Type type) {
        InvertedIndex index = null;

        if(type == Constants.Type.REVIEW) {
            index = IndexManager.getReviewIndex();
        }
        else if(type == Constants.Type.QA) {
            index = IndexManager.getQaIndex();
        }

        return  index;
    }
}
