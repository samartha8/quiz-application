package coursework_final;

public class Question {
    private int id;
    private String text;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String correctAnswer;

    public Question(int id, String text, String option1, String option2, String option3, String option4, String correctAnswer) {
        this.id = id;
        this.text = text;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.correctAnswer = correctAnswer;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getOption4() {
        return option4;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
