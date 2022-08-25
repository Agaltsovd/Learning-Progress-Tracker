package tracker;
import java.util.*;

public class Points {
    private int id;
    private int java = 0;
    private int javaMax = 600;
    private int dsa = 0;
    private int dsaMax = 400;
    private int databases = 0;
    private int dbMax = 480;
    private int spring = 0;
    private int springMax = 550;
    public Points(int id){
        this.id = id;
    }
    public String emailConstructor(String name, String email, String courseName) {
        return String.format("To: %s\nRe: Your Learning Progress\nHello, %s! You have accomplished our %s course!",email,name,courseName);
    }
    Notification n = new Notification();
    public void addPoints(int java, int dsa, int databases,int spring,List<String> messages, HashMap<Integer, Student> students,Counter counter){

        this.java+=java;
        this.dsa += dsa;
        this.databases += databases;
        this.spring += spring;
        notify(messages,students,counter);

    }
    public void notify(List<String> messages,HashMap<Integer, Student> students, Counter counter){
        String name = students.get(id).getName();
        String email = students.get(id).getEmail();
        boolean notified = false;
        if(java >= javaMax && !n.java) {
            messages.add(emailConstructor(name,email,"Java"));
            n.java = true;
            notified = true;
        }
        if(dsa >= dsaMax && !n.dsa) {
            messages.add(emailConstructor(name,email,"DSA"));
            n.dsa = true;
            notified = true;
        }
        if(databases >= dbMax && !n.databases) {
            messages.add(emailConstructor(name,email,"Databases"));
            n.databases = true;
            notified = true;
        }
        if(spring >= springMax && !n.spring) {
            messages.add(emailConstructor(name,email,"Spring"));
            n.spring = true;
            notified = true;
        }
        counter.increment();



    }
    public int getJava(){
        return java;
    }
    public int getDsa(){
        return dsa;
    }
    public int getDatabases(){
        return databases;
    }
    public int getSpring() {
        return spring;
    }
    public int getId() { return id; }
    @Override
    public String toString(){
        return String.format("ID: %d, Java: %d,DSA: %d, Databases: %d, Spring: %d",id,java,dsa,databases,spring);
    }
}
