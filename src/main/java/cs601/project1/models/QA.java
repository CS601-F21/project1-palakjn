package cs601.project1.models;

public class QA {

    private String questionType;
    private String asin;
    private String answerTime;
    private long unixTime;
    private String question;
    private String answerType;
    private String answer;

    public QA(String asin, String question, String answer) {
        this.asin = asin;
        this.question = question;
        this.answer = answer;
    }

    public String getAsin() {
        return asin;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public String toString() {
        return String.format("Question: %s, Answer: %s", question, answer);
    }
}
