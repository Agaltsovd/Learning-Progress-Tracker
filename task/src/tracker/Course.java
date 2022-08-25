package tracker;

public class Course {
    private int maxScore;
    private int totalScore = 0;
    private int numberOfSubmissions = 0;
    private int students = 0;
    private String name;

    public Course(String name,int maxScore) {
        this.name = name;
        this.maxScore = maxScore;

    }
    public void addStudent() {
        this.students+=1;
    }
    public void addToTotalScore(int score) {
            this.totalScore += score;

    }
    public void addSubmission() {
        this.numberOfSubmissions+=1;
    }
    public double getAverage() {
        if (numberOfSubmissions == 0){
            return 0;
        }
        return (double) totalScore/numberOfSubmissions;
    }
    public int getNumberOfStudents() {
        return students;
    }
    public int getNumberOfSubmissions() {
        return numberOfSubmissions;
    }
    public String getName() {
        return name;
    }
    public int getMaxScore() {
        return maxScore;
    }




}
