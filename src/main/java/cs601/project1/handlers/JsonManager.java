package cs601.project1.handlers;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import cs601.project1.models.QA;
import cs601.project1.models.Review;

public class JsonManager {

    public static Review getReviewObject(String json) {
        Review review = null;

        try {
            Gson gson = new Gson();
            review = gson.fromJson(json, Review.class);
        }
        catch (JsonSyntaxException exception) {
        }

        return review;
    }

    public static QA getQAObject(String json) {
        QA qa = null;

        try {
            Gson gson = new Gson();
            qa = gson.fromJson(json, QA.class);
        }
        catch (JsonSyntaxException exception) {
        }

        return qa;
    }
}
