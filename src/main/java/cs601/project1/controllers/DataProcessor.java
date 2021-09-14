package cs601.project1.controllers;

import cs601.project1.Constants;
import cs601.project1.models.*;
import cs601.project1.utils.Strings;

import javax.print.Doc;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Insert data into Inverted Index and
 * queries it to display specific information.
 *
 * @author Palak Jain
 */
public class DataProcessor {

    private ReviewList reviewList;
    private QAList qaList;

    public DataProcessor() {
        reviewList = new ReviewList();
        qaList = new QAList();
    }

    /**
     * Read and parse the JSON file into review object and
     * insert each term, and it's document index reference to Inverted Index
     * @param fileLocation Cell phones and accessories reviews dataset file location in a disk.
     * @return true if reading is successful else false.
     */
    public boolean readReviews(String fileLocation) {
        boolean flag = false;

        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileLocation), StandardCharsets.ISO_8859_1)){
            int index = 0;
            String line = br.readLine();

            while (line != null) {
                Review review = JsonManager.getReviewObject(line);

                if(review != null && !Strings.isNullOrEmpty(review.getReviewText())) {
                    reviewList.add(index, review);
                    process(review.getReviewText(), index, Constants.Type.REVIEW);
                    index++;
                }

                line = br.readLine();
            }
        }
        catch (IOException io) {
            StringWriter writer = new StringWriter();
            io.printStackTrace(new PrintWriter(writer));

            System.out.printf("An error occurred while accessing file at a location %s. %s", fileLocation, writer);
        }

        flag = reviewList.getCount() > 0;

        return flag;
    }

    /**
     * Read and parse the JSON file into QA object and
     * insert terms, and it's document index reference to Inverted Index
     * @param fileLocation Cell phones and accessories Q/A dataset file location in a disk
     * @return true if reading is successful else false.
     */
    public boolean readQA(String fileLocation) {
        boolean flag = false;

        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileLocation), StandardCharsets.ISO_8859_1)){
            int index = 0;
            String line = br.readLine();

            while (line != null) {
                QA qa = JsonManager.getQAObject(line);

                if(qa != null && !Strings.isNullOrEmpty(qa.getQuestion()) && !Strings.isNullOrEmpty(qa.getAnswer())) {
                    qaList.add(index, qa);
                    process(String.format("%s %s", qa.getQuestion(), qa.getAnswer()), index, Constants.Type.QA);
                    index++;
                }

                line = br.readLine();
            }
        }
        catch (IOException io) {
            StringWriter writer = new StringWriter();
            io.printStackTrace(new PrintWriter(writer));

            System.out.printf("An error occurred while accessing file at a location %s. %s", fileLocation, writer);
        }

        flag = qaList.getCount() > 0;

        return flag;
    }

    /**
     * Given the ASIN number of a specific product, will display a list of all reviews for
     * that product and will display a list of all questions and answers about that product.
     * @param asin ASIN number of a product.
     */
    public void findAsin(String asin) {
        String output = reviewList.toString(asin);

        if(Strings.isNullOrEmpty(output)) {
            System.out.printf("No reviews found for the product with asin number: %s. \n", asin);
        }
        else {
            System.out.println(output);
        }

        output = qaList.toString(asin);

        if(Strings.isNullOrEmpty(output)) {
            System.out.printf("No questions and answers found for the product with asin number: %s. \n", asin);
        }
        else {
            System.out.println(output);
        }
    }

    /**
     * Given a one word term, will display a list of all reviews containing the exact
     * term if "isPartial" value is false else will display a list of all reviews
     * where any word in it contains a partial match of the term.
     * @param term One word term
     * @param isPartial To indicate whether to search for exact term or partial match of the term.
     */
    public void reviewSearch(String term, boolean isPartial) {
        InvertedIndex reviewIndex = Factory.getIndex(Constants.Type.REVIEW);
        List<Document> documentList = reviewIndex.get(term, isPartial);

        if(documentList != null && documentList.size() > 0) {
            if(!isPartial) {
                Collections.sort(documentList);
            }

            for (Document document : documentList) {
                String output = reviewList.toString(document.getIndex());

                if(isPartial) {
                    System.out.printf("Term: %s.  %s. \n\n", term, output);
                }
                else {
                    System.out.printf("Term: %s. Number of occurrences: %d. %s. \n\n", term, document.getCounter(), output);
                }
            }
        }
        else {
            System.out.printf("No term i.e. \"%s\" found. \n", term);
        }
    }

    /**
     * Given a one word term, will display a list of all questions and answers containing the exact
     * term if "isPartial" value is false else will display a list of all questions and answers
     * where any word in it contains a partial match of the term.
     * @param term One word term
     * @param isPartial To indicate whether to search for exact term or partial match of the term.
     */
    public void qaSearch(String term, boolean isPartial) {
        InvertedIndex qaIndex = Factory.getIndex(Constants.Type.QA);
        List<Document> documentList = qaIndex.get(term, isPartial);

        if(documentList != null && documentList.size() > 0) {
                if(!isPartial) {
                    Collections.sort(documentList);
                }

                for (Document document : documentList) {
                    String output = qaList.toString(document.getIndex());

                    if(isPartial) {
                        System.out.printf("Term: %s. %s. \n\n", term, output);
                    }
                    else {
                        System.out.printf("Term: %s. Number of occurrences: %d. %s. \n\n", term, document.getCounter(), output);
                    }
                }
        }
        else {
            System.out.printf("No term i.e.\"%s\" found. \n", term);
        }
    }

    /**
     * Maps each word in the text to the document having it.
     * @param text Collection of words separated by a space.
     * @param index An index of document having the text.
     * @param type Indicated whether text is from review object or from QA object.
     */
    private void process(String text, int index, Constants.Type type) {
        InvertedIndex invertedIndex = Factory.getIndex(type);
        //Making all characters in a string as lowercase.
        text = text.toLowerCase();

        //Splitting text by space
        String[] words = text.split(" ");

        //Sorting words so that all the same words will be together in an array.
        Arrays.sort(words);

        //Replacing all non-alphabetic digits with empty character
        String word = words[0].replaceAll("[^a-z0-9]", "");
        Document document = new Document(index);

        for (int i = 1; i < words.length; i++) {
            //Replacing all non-alphabetic digits with empty character.
            String currentWord = words[i].replaceAll("[^a-z0-9]", "");

            if(!Strings.isNullOrEmpty(currentWord)) {
                if (currentWord.equals(word)) {
                    //Incrementing count by 1 stating the occurrence of a word in a text.
                    document.incrementCounter();
                } else {
                    //If current term is different from the previous term then push the previous word details to Inverted Index and counting the occurrence of new word in a text.
                    invertedIndex.upsert(word, document);
                    word = currentWord;
                    document = new Document(index);
                }
            }
        }
    }
}
