package cs601.project1.models;

public class Document implements Comparable<Document> {

    private int index;
    private int counter;

    public Document(int index) {
        this.index = index;
        counter = 1;
    }

    public int getIndex() {
        return index;
    }

    public int getCounter() {
        return counter;
    }

    public void incrementCounter() {
        counter++;
    }

    public void reset() {
        counter = 1;
    }

    @Override
    public int compareTo(Document document) {
        return document.getCounter() - this.counter;
    }
}
