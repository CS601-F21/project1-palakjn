package cs601.project1.models;

/**
 * Holds the index of each document in ReviewList or QAList object.
 *
 * @author Palak Jain
 */
public class Document implements Comparable<Document> {

    private int index;
    //Number of occurrence of a word in current document
    private int counter;

    public Document(int index) {
        this.index = index;
        counter = 1;
    }

    /**
     * Returns index of a current document.
     * @return Index
     */
    public int getIndex() {
        return index;
    }

    /**
     * Returns the number of occurrence of a word in a current document.
     * @return Positive value holding a count.
     */
    public int getCounter() {
        return counter;
    }

    /**
     * Increments the occurrence of a word in a document by 1.
     */
    public void incrementCounter() {
        counter++;
    }

    @Override
    public int compareTo(Document document) {
        return document.getCounter() - this.counter;
    }
}
