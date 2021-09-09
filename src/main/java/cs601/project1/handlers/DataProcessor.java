package cs601.project1.handlers;

import cs601.project1.Constants;
import cs601.project1.models.*;
import cs601.project1.utils.Strings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DataProcessor {

    private ReviewList reviewList;
    private QAList qaList;

    public DataProcessor() {
        reviewList = new ReviewList();
        qaList = new QAList();
    }

    public boolean readReviews(String fileLocation) {
        boolean flag = false;

        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileLocation), StandardCharsets.ISO_8859_1)){
            int index = 0;
            String line = br.readLine();

            while (line != null) {
                Review review = JsonManager.getReviewObject(line);

                if(review != null) {
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

    public boolean readQA(String fileLocation) {
        boolean flag = false;

        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileLocation), StandardCharsets.ISO_8859_1)){
            int index = 0;
            String line = br.readLine();

            while (line != null) {
                QA qa = JsonManager.getQAObject(line);

                if(qa != null) {
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

    public void reviewSearch(String term, boolean isPartial) {
        InvertedIndex reviewIndex = Factory.getIndex(Constants.Type.REVIEW);
        List<Document> documentList = reviewIndex.get(term, isPartial);

        if(documentList != null && documentList.size() > 0) {
            Collections.sort(documentList);

            for(Document document : documentList) {
                String output = reviewList.toString(document.getIndex());

                System.out.printf("Term: %s. Number of occurrences: %d. %s. \n", term, document.getCounter(), output);
            }
        }
        else {
            System.out.printf("No term i.e. \"%s\" found. \n", term);
        }
    }

    public void qaSearch(String term, boolean isPartial) {
        InvertedIndex qaIndex = Factory.getIndex(Constants.Type.QA);
        List<Document> documentList = qaIndex.get(term, isPartial);

        if(documentList != null && documentList.size() > 0) {
            Collections.sort(documentList);

            for(Document document : documentList) {
                String output = qaList.toString(document.getIndex());

                System.out.printf("Term: %s. Number of occurrences: %d. %s. \n", term, document.getCounter(), output);
            }
        }
        else {
            System.out.printf("No term i.e.\"%s\" found. \n", term);
        }
    }

    private void process(String text, int index, Constants.Type type) {
        InvertedIndex invertedIndex = Factory.getIndex(type);
        String[] words = text.split(" ");

        Arrays.sort(words);

        String word = words[0].replaceAll("[^a-zA-Z0-9]", "");;
        Document document = new Document(index);

        for (int i = 1; i < words.length; i++) {
            String currentWord = words[i].replaceAll("[^a-zA-Z0-9]", "");

            if(currentWord.equals(word)) {
                document.incrementCounter();
            }
            else {
                invertedIndex.upsert(word, document);
                word = currentWord;
                document.reset();
            }
        }
    }
}
