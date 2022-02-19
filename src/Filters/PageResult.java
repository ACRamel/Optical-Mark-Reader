package Filters;

import java.util.ArrayList;

public class PageResult {
    private String studentId;
    private ArrayList<String> answers;

    public PageResult() {
        this.answers = new ArrayList<>();
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public void addAnswer(String a) {
        // TODO: add to answer
    }
}
