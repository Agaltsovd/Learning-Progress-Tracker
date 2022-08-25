package tracker;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Students {
    private HashMap<Integer, Student> students = new LinkedHashMap<>();
    private Set<String> uniqueEmails = new HashSet<>();
    private HashMap<Integer, Points> pointsTable = new HashMap<>();
    private Course Java = new Course("Java",600);
    private Course DSA = new Course("DSA",400);
    private Course Database = new Course("Databases",480);
    private  Course Spring = new Course("Spring",550);
    private List<Course> courses = new ArrayList<>(List.of(Java,DSA, Database,Spring));
    private int nextId = 10000;
    private Set<Integer> notifiedStudents = new HashSet<>();
    private LinkedList<String> messagesTostudentsToBeNotified = new LinkedList<>();
    public Counter counter = new Counter();
    public boolean addEmail(String email){
        return this.uniqueEmails.add(email);
    }
    public void addStudent(Student student){
        this.students.put(nextId, student);
        this.pointsTable.put(nextId,new Points(nextId));
        nextId++;
    }
    public String addPoints(int id, ArrayList<Integer> scores){
        if(doesStudentExists(id)){
            addStatistics(id,scores);
            this.pointsTable.get(id).addPoints(scores.get(0),scores.get(1),scores.get(2),scores.get(3),messagesTostudentsToBeNotified,students,counter);

            return "Points updated.";

        }
        else{
            return String.format("No student is found for id=%d.",id);
        }

    }
    public void sendNotification() {
        for(String s : messagesTostudentsToBeNotified) {
            System.out.println(s);
        }
        System.out.println(String.format("Total %d students have been notified.",counter.counter));
        counter = new Counter();
        messagesTostudentsToBeNotified = new LinkedList<>();

    }

    public void addStatistics(int id,ArrayList<Integer> scores){
        Points studentPoint = this.pointsTable.get(id);

        for( int i = 0; i < courses.size(); i++){
            if(scores.get(0)>0){
                if(studentPoint.getJava() == 0){
                    Java.addStudent();
                }
                Java.addSubmission();
                Java.addToTotalScore(scores.get(0));
            }
            if(scores.get(1)>0){
                if(studentPoint.getDsa() == 0){
                    DSA.addStudent();
                }
                DSA.addSubmission();
                DSA.addToTotalScore(scores.get(1));
            }
            if(scores.get(2)>0){
                if(studentPoint.getDatabases() == 0){
                    Database.addStudent();
                }
                Database.addSubmission();
                Database.addToTotalScore(scores.get(2));
            }
            if(scores.get(3)>0){
                if(studentPoint.getSpring() == 0){
                    Spring.addStudent();
                }
                Spring.addSubmission();
                Spring.addToTotalScore(scores.get(3));
            }

        }
    }
    public List sortTopLearners(String s) {
        List<Points> learners  = new ArrayList<>(pointsTable.values());
        if(s.equals("java")){
            learners.sort((Comparator.comparing(Points::getJava).reversed().thenComparing(Comparator.comparing(Points::getId))));
        }
        if(s.equals("dsa")){
            learners.sort((Comparator.comparing(Points::getDsa).reversed().thenComparing(Comparator.comparing(Points::getId))));
        }
        if(s.equals("databases")){
            learners.sort((Comparator.comparing(Points::getDatabases).reversed().thenComparing(Comparator.comparing(Points::getId))));
        }
        if(s.equals("spring")){
            learners.sort((Comparator.comparing(Points::getSpring).reversed().thenComparing(Comparator.comparing(Points::getId))));
        }

        return learners;
    }
    public void printListOfStudent(String s) {
        List<Points> learners = sortTopLearners(s);
        String res = "";
        for(Points p : learners) {
            res += (p.toString() + "\n");
        }
        System.out.println(res);
    }
    public void getTopLearners(String s){
        s = s.strip();
        s = s.toLowerCase();
        List<Points> learners = sortTopLearners(s);
        String res = "";
        String format = "%1$-6s%2$-10s%3$-10s\n";
        System.out.println(getStandardCourseName(s));
        System.out.format(format, "id", "points", "completed");

        for(Points p : learners){
            if(getScore(s,p) != 0){
                double completed = (double) getScore(s,p)/getMaxScoreForCourse(s) *100;
//                int integerPart = (int) (completed*100);
//                double decimalPart = (completed*100 - integerPart) * 10;
                BigDecimal b = new BigDecimal(completed).setScale(1, RoundingMode.HALF_UP);


                System.out.format(format, p.getId(), getScore(s,p), ""+b+"%");
            }
        }



    }
    private String getStandardCourseName(String s) {
        if(s.equals("java")) return "Java";
        if(s.equals("dsa")) return "DSA";
        if(s.equals("databases")) return "Databases";
        if(s.equals("spring")) return "Spring";
        else return "invalid course";

    }
    public boolean isValidCourseName(String s){
        s = s.strip();
        s = s.toLowerCase();
        if(s.equals("java") || s.equals("dsa") || s.equals("databases") || s.equals("spring")){
            return true;
        }
        return false;
    }
    private int getMaxScoreForCourse(String s){
        if(s.equals("java")) return Java.getMaxScore();
        if(s.equals("dsa")) return DSA.getMaxScore();
        if(s.equals("databases")) return Database.getMaxScore();
        if(s.equals("spring")) return Spring.getMaxScore();
        return -1;

    }
    private int getScore(String s, Points p){
        if(s.equals("java")) return p.getJava();
        if(s.equals("dsa")) return p.getDsa();
        if(s.equals("databases")) return p.getDatabases();
        if(s.equals("spring")) return p.getSpring();
        else return -1;

    }
    //System.out.format("%32s%10d%16s", string1, int1, string2);
    public String getStatistics() {
        return String.format("Most popular:%s\nLeast popular:%s\nHighest activity:%s\nLowest activity:%s\nEasiest course:%s\nHardest course:%s",getMostPopular(),getLeastPopular(),getHighestActivity(),getLowestActivity(),getEasiest(),getHardest());

    }
    private String getMostPopular(){
        int highest = 0;
        for(Course c: courses) {
            if(c.getNumberOfStudents() > highest){
                highest = c.getNumberOfStudents();
            }
        }
        if (highest == 0){
            return "n/a";
        }
        String s = "";
        for(Course c : courses) {
            if(c.getNumberOfStudents() == highest){
                s+=(" "+ c.getName()+",");

            }
        }
        s = s.substring(0,s.length()-1);
        return s;
    }
    private String getLeastPopular(){
        int lowest = Integer.MAX_VALUE;
        for(Course c: courses) {
            if(c.getNumberOfStudents() < lowest){
                lowest = c.getNumberOfStudents();
            }
        }
        if (lowest == 0){
            return "n/a";
        }
        String s = "";
        for(Course c : courses) {
            if(c.getNumberOfStudents() == lowest){
                s+=(" "+ c.getName()+",");

            }
        }
        s = s.substring(0,s.length()-1);
        if(s.equals(getMostPopular())){
            return "n/a";
        }
        return s;
    }
    private String getHighestActivity() {
        int highest = 0;
        for(Course c: courses) {
            if(c.getNumberOfSubmissions() > highest){
                highest = c.getNumberOfSubmissions();
            }
        }
        if (highest == 0){
            return "n/a";
        }
        String s = "";
        for(Course c : courses) {
            if(c.getNumberOfSubmissions() == highest){
                s+=(" "+ c.getName()+",");

            }
        }
        s = s.substring(0,s.length()-1);
        return s;

    }
    private String getLowestActivity(){
        int lowest = Integer.MAX_VALUE;
        for(Course c: courses) {
            if(c.getNumberOfSubmissions() < lowest){
                lowest = c.getNumberOfSubmissions();
            }
        }
        if (lowest == 0){
            return "n/a";
        }
        String s = "";
        for(Course c : courses) {
            if(c.getNumberOfSubmissions() == lowest){
                s+=(" "+ c.getName()+",");
            }
        }
        s = s.substring(0,s.length()-1);
        if(getHighestActivity().equals(s)){
            return "n/a";
        }
        return s;
    }
    private String getEasiest() {
        double highest = 0;
        for(Course c: courses) {
            if(c.getAverage() > highest){
                highest = c.getAverage();
            }
        }
        if (highest == 0){
            return "n/a";
        }
        String s = "";
        for(Course c : courses) {
            if(c.getAverage() == highest){
                s+=(" "+ c.getName()+",");
            }
        }
        s = s.substring(0,s.length()-1);
        return s;
    }
    private String getHardest(){
        double lowest = Integer.MAX_VALUE;
        for(Course c: courses) {
            if(c.getAverage() < lowest){
                lowest = c.getAverage();
            }
        }
        if (lowest == 0){
            return "n/a";
        }
        String s = "";
        for(Course c : courses) {
            if(c.getAverage() == lowest){
                s+=(" "+ c.getName()+",");
            }
        }
        s = s.substring(0,s.length()-1);
        return s;
    }
    public String find(int id){
        if(doesStudentExists(id)){
            return returnIDAndPoints(id);
        }
        else{
            return String.format("No student is found for id=%d.",id);
        }

    }

    public String returnIDAndPoints(int id){
        Points points = pointsTable.get(id);
        return String.format("%d points: Java=%d; DSA=%d; Databases=%d; Spring=%d",id,points.getJava(),points.getDsa(),points.getDatabases(),points.getSpring());
    }
    public boolean doesStudentExists(int id){
        return this.pointsTable.containsKey(id);
    }
    public Set<Integer> listStudentIDs(){
        return this.students.keySet();
    }
    public int getSize(){
        return students.size();
    }
}
