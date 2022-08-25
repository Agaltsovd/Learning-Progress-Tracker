package tracker;
import java.util.*;
import java.util.regex.Pattern;

public class Student {
    private String first_name;
    private int id;
    private String last_name;
    private String email;

    public Student(String fname, String lname,String email){
        this.first_name = fname;
        this.last_name = lname;
        this.email = email;
    }
    public String getName() {
        return first_name+" "+last_name;
    }
    public String getEmail() {
        return email;
    }


    public static boolean isFirstNameValid(String first_name){
        return first_name.matches("[^'-]\\w*['|-]?\\w*[^'-]");
    }
    public static boolean isLastNameValid(String last_name){
        Pattern regex = Pattern.compile("^[a-z]((?<=[-'])[a-z ]|(?<![-'])[-'a-z ])*[a-z]$", Pattern.CASE_INSENSITIVE);
        return regex.matcher(last_name).matches();
    }

    public static boolean isEmailValid(String email){
            return email.matches("([^@])+@([^@.])+\\.([^@.])+");
    }


}
