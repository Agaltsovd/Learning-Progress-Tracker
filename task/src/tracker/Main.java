package tracker;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Learning Progress Tracker");
        Students students = new Students();

        Scanner scanner = new Scanner(System.in);
        while (true){
            String input = scanner.nextLine();
            if (input.strip().equals("exit")){
                System.out.println("Bye!");
                scanner.close();
                break;
            }
            else if(input.equals("add students")) {
                System.out.println("Enter student credentials or 'back' to return");
                while(true) {

                    String s = scanner.nextLine();
                    if(s.matches("back")){
                        System.out.println("Total "+ students.getSize()+" students have been added.");
                        break;
                    }
                    else {
                        String[] words = s.split("\\s");
                        if (words.length < 3) {
                            System.out.println("Incorrect credentials");
                        } else {
                            String fname = words[0];
                            String lname = "";
                            for (int i = 1; i < words.length - 1; i++) {
                                lname += words[i] + " ";
                            }
                            lname = lname.strip();
                            String email = words[words.length - 1];
                            if (!Student.isFirstNameValid(fname)) {
                                System.out.println("Incorrect first name.");
                            } else if (!Student.isLastNameValid(lname)) {
                                System.out.println("Incorrect last name.");
                            } else if (!Student.isEmailValid(email)) {
                                System.out.println("Incorrect email.");
                            } else {
                                if (students.addEmail(email)) {
                                    students.addStudent(new Student(fname, lname, email));
                                    System.out.println("The student has been added.");
                                } else {
                                    System.out.println("This email is already taken.");
                                }
                            }
                        }

                    }
                }

            }
            else if(input.matches("list")){
                Set<Integer> ids = students.listStudentIDs();
                if(ids.isEmpty()){
                    System.out.println("No students found");
                }
                else{
                    System.out.println("Students:");
                    for(Integer i: ids){
                        System.out.println(i);
                    }
                }

            }
            else if(input.equals("add points")){
                System.out.println("Enter an id and points or 'back' to return");
                while(true){
                    String s = scanner.nextLine();
                    if(s.matches("back")){
                        break;
                    }
                    String[] words = s.split("\\s");
                    if(words.length!=5){
                        System.out.println("Incorrect points format.");
                        continue;
                    }

                    else {
                        boolean allMatch = true;
                        for(int i = 1;i < words.length;i++){
                            if(!words[i].matches("^[0-9]\\d*$")){
                                System.out.println("Incorrect points format.");
                                allMatch = false;
                                break;
                            }
                        }
                        if(!words[0].matches("^[0-9]\\d*$")){
                            System.out.println(String.format("No student is found for id=%s.",words[0]));
                            allMatch = false;
                        }

                        if(allMatch){
                            int id = Integer.parseInt(words[0]);
                            int java = Integer.parseInt(words[1]);
                            int dsa = Integer.parseInt(words[2]);
                            int databases = Integer.parseInt(words[3]);
                            int spring = Integer.parseInt(words[4]);
                            ArrayList<Integer> scores = new ArrayList<>(List.of(java,dsa,databases,spring));
                            System.out.println(students.addPoints(id,scores));
                        }
                    }
                }

            }
            else if(input.equals("notify")) {
                students.sendNotification();
            }
            else if(input.equals("find")){
                System.out.println("Enter an id and points or 'back' to return");
                while(true){
                    String s = scanner.next();
                    if(s.matches("back")){
                        break;
                    }
                    if(s.matches("^[0-9]\\d*$")){
                        System.out.println(students.find(Integer.parseInt(s)));
                    }

                }
            }
            else if(input.equals("statistics")) {
                System.out.println("Type the name of a course to see details or 'back' to quit:");
                System.out.println(students.getStatistics());
                while(true) {
                    String s = scanner.next();
                    if (s.matches("back")) {
                        break;
                    }
                    if (!students.isValidCourseName(s)) {
                        System.out.println("Unknown course.");
                    } else {
                        students.getTopLearners(s);
                    }
                }

            }
            else if(input.equals("back")){
                System.out.println("Enter 'exit' to exit the program.");
            }
            else if(input.isBlank()){
                System.out.println("No input");
            }
            else{
                System.out.println("Error: Unknown command!");
            }
        }

    }
}
