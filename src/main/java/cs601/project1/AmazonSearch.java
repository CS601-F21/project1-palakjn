package cs601.project1;

import cs601.project1.controllers.DataProcessor;
import cs601.project1.utils.Strings;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * A program to allow searching of a database of Amazon reviews and Q&A content.
 * Allow users to perform below actions on the database:
 *  1) find <asin>
 *  2) reviewsearch <term>
 *  3) qasearch <term>
 *  4) reviewpartialsearch <term>
 *  5) qapartialsearch <term>
 *
 * @author Palak Jain
 * */
public class AmazonSearch {

    private DataProcessor dataProcessor;

    public AmazonSearch(){
        dataProcessor = new DataProcessor();
    }

    public static void main(String[] args) {
        String reviewFileLocation = null;
        String qaFileLocation = null;

        //Reading arguments
        for(int i = 0; i < args.length; i = i + 2) {
            if(args[i].equals("-reviews") && i + 1 < args.length) {
                //Next index if exists in an array will be the value.
                reviewFileLocation = args[i + 1];
            }
            else if(args[i].equals("-qa") && i + 1 < args.length) {
                //Next index if exists in an array will be the value.
                qaFileLocation = args[i + 1];
            }
        }

        AmazonSearch amazonSearch = new AmazonSearch();

        //Validation of arguments whether a file exists at a value specified after -reviews and -qa flag
        boolean fileExists = amazonSearch.validateArguments(reviewFileLocation, qaFileLocation);

        if(fileExists) {
            //Reading review file
            System.out.printf("Parsing review file %s...\n", reviewFileLocation);
            boolean reviewsExist = amazonSearch.getDataProcessor().readReviews(reviewFileLocation);

            //Reading qa file
            System.out.printf("Parsing QA file %s...\n", qaFileLocation);
            boolean qaExist = amazonSearch.getDataProcessor().readQA(qaFileLocation);

            if(reviewsExist && qaExist) {
                //If an application reads both file successfully then, asking user for commands

                amazonSearch.doActions();
            }
            else {
                System.out.println("No reviews or QA datasets found. Either dataset not in JSON format or required values are empty.");
            }
        }
    }

    private DataProcessor getDataProcessor() {
        return dataProcessor;
    }

    /**
     * Allow the user to perform the following actions:
     *  1) find <Asin>
     *  2) reviewsearch <term>
     *  3) qasearch <term>
     *  4) reviewpartialsearch <term>
     *  5) qapartialsearch <term>
     */
    private void doActions() {
        System.out.println(Constants.CHOICES);
        String choice = getChoice();

        while (!Strings.isNullOrEmpty(choice) && !choice.equals(Constants.EXIT)) {
            if(choice.startsWith(Constants.FIND)) {
                String asin = choice.split(" ")[1];
                dataProcessor.findAsin(asin);
            }
            else if(choice.startsWith(Constants.REVIEWSEARCH)) {
                String term = choice.split(" ")[1].toLowerCase();
                dataProcessor.reviewSearch(term, false);
            }
            else if(choice.startsWith(Constants.QASEARCH)) {
                String term = choice.split(" ")[1].toLowerCase();
                dataProcessor.qaSearch(term, false);
            }
            else if(choice.startsWith(Constants.REVIEWPARTIALSEARCH)) {
                String term = choice.split(" ")[1].toLowerCase();
                dataProcessor.reviewSearch(term, true);
            }
            else if(choice.startsWith(Constants.QAPARTIALSEARCH)) {
                String term = choice.split(" ")[1].toLowerCase();
                dataProcessor.qaSearch(term, true);
            }

            choice = getChoice();
        }
    }

    /**
     * Asks user to input a valid choice.
     *
     * @return Choice
     */
    private String getChoice() {
        String choice = null;
        Scanner input = new Scanner(System.in);

        System.out.print("Enter your choice: ");
        choice = input.nextLine();

        if(!validateChoice(choice)) {
            choice = null;
        }

        return choice;
    }

    /**
     * Validates whether given inputs are not null and a file exist at a given file location.
     * @param reviewFileLocation The path to a file
     * @param qaFileLocation The path to a file
     * @return returns true if inputs are not null and a file exist at a given locations else false
     */
    private boolean validateArguments(String reviewFileLocation, String qaFileLocation) {
        boolean flag = true;

        if(Strings.isNullOrEmpty(reviewFileLocation) || Strings.isNullOrEmpty(qaFileLocation)) {
            System.out.println("Passed arguments are incorrect");

            flag = false;
        }
        else {
            if (!Files.exists(Paths.get(reviewFileLocation))) {
                System.out.printf("No review file found at a location %s", reviewFileLocation);
                flag = false;
            }
            if (!Files.exists(Paths.get(qaFileLocation))) {
                System.out.printf("No qa file found at a location %s", qaFileLocation);
                flag = false;
            }
        }

        return flag;
    }

    /**
     * Validates whether the choice given by end user is correct or not
     * @param choice Provided by end user.
     * @return returns true if choice is valid else false.
     * */
    private boolean validateChoice(String choice) {
        boolean isValid = false;

        if(Strings.isNullOrEmpty(choice)){
            //If user pressed enter without typing any command.
            System.out.println("The entered value is null or empty.");
        }
        else if(!Constants.COMMANDS.contains(choice.split(" ")[0])) {
            //If the entered command is wrong.
            System.out.println("Invalid Command");
        }
        else if(!choice.equals(Constants.EXIT) && choice.split(" ").length != 2) {
            //Entered choice must have exact two words.
            System.out.println("Missing search term after the command");
        }
        else if(!choice.equals(Constants.EXIT) && !choice.split(" ")[1].matches("^[a-zA-Z0-9]*$")) {
            //Term or ASIN number should not contain non-alphanumeric characters.
            System.out.println("Search term must be only alphanumeric characters (letters or digits).");
        }
        else {
            isValid = true;
        }

        return isValid;
    }
}
