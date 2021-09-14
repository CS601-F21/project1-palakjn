package cs601.project1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The class holding constant values. If there is a change in hardcoded strings, there will be only one place to change it.
 *
 * @author Palak Jain
 * */

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

    public static String CHOICES =  "Commands: \n \t 1) find <Asin> \n \t 2) reviewsearch <term> \n \t 3) qasearch <term> " +
            "\n \t 4) reviewpartialsearch <term> \n \t 5) qapartialsearch <term> \n \t 6) exit";
}
