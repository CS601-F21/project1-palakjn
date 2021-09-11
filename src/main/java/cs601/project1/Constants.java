package cs601.project1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Constants {

    public enum Type {
        REVIEW,
        QA
    }

    //Choices from End User
    public static String FIND = "find";
    public static String REVIEWSEARCH = "reviewsearch";
    public static String QASEARCH = "qasearch";
    public static String REVIEWPARTIALSEARCH = "reviewpartialsearch";
    public static String QAPARTIALSEARCH = "qapartialsearch";
    public static String EXIT = "exit";

    public static List<String> COMMANDS = new ArrayList<>(Arrays.asList(FIND,
                                                                        REVIEWSEARCH,
                                                                        QASEARCH,
                                                                        REVIEWPARTIALSEARCH,
                                                                        QAPARTIALSEARCH,
                                                                        EXIT));

    public static String CHOICES;
}
