package jpa.mainrunner;

import jpa.service.CourseService;
import jpa.service.StudentService;

import java.util.Scanner;

public class SMSRunner {
    private StudentService sService = new StudentService();
    private CourseService cService = new CourseService();


    public static void main(String[] args) {

        SMSRunner smsRun = new SMSRunner();
        Boolean isValid1;
        Scanner input = new Scanner(System.in);
        Integer choice1;
        do {
            isValid1 = true;
            System.out.println("Are you a(n)");
            System.out.printf("1. Student\n2. quit\n");
            System.out.print("Please, enter 1 or 2. ");
            try {
                choice1 = Integer.parseInt(input.next());
                    if (choice1 < 1 || choice1 > 2) {
                        throw new IllegalArgumentException();
                    }
                if (choice1 == 1) {
                    System.out.println("Enter Your Email: ");
                    String inEmail = input.next();
                    System.out.println("Enter Your Password: ");
                    String inPass = input.next();
                    if (smsRun.sService.validateStudent(inEmail, inPass)) {
                        Boolean isValid2 = false;
                        while (isValid2 == false) {
                            smsRun.sService.getStudentCourses(inEmail);
                            Integer choice2;
                            System.out.printf("1. Register to Class\n2. Logout\n");
                            try {
                                choice2 = Integer.parseInt(input.next());
                                if (choice2 < 1 || choice2 > 2) {
                                    throw new IllegalArgumentException();
                                }
                                if (choice2 == 1) {
                                    isValid2 = true;
                                    Boolean isValid3 = false;
                                    while (isValid3 == false) {
                                        smsRun.cService.getAllCourses();
                                        System.out.print("Which Course? ");
                                        Integer courseIdChoice;
                                        try {
                                            courseIdChoice = Integer.parseInt(input.next());
                                            if (smsRun.cService.getCourseById(courseIdChoice) != null && !smsRun.sService.getStudentCourses(inEmail).contains(smsRun.cService.getCourseById(courseIdChoice))) {
                                                isValid3 = true;
                                                smsRun.sService.registerStudentToCourse(inEmail, courseIdChoice);
                                            }
                                            else throw new IllegalArgumentException();
                                        } catch (IllegalArgumentException e) {
                                            System.out.println("Please Enter a valid choice");
                                        }
                                    }
                                } else if (choice2 == 2) {
                                    System.out.println("You have been signed out");
                                    return;}
                            } catch (IllegalArgumentException e) {
                                System.out.println("Please Enter a valid choice");
                            }
                        }
                    }
                } else if (choice1 == 2 ) {
                    System.out.println("You have been signed out");
                    return;
                } else {
                    System.out.println("Please enter a valid choice");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Please enter a valid choice");
                isValid1 = false;
            }
        } while (!isValid1);
    }

    public void work() {
//        sService.getAllStudents();
//        cService.getAllCourses();
//        found
//        sService.getStudentByEmail("htaffley6@columbia.edu");
//        not found
//        sService.getStudentByEmail("htaffley6@columbia.ed");
//        sService.validateStudent("hguerre5@deviantart.com","OzcxzD1PGs");
//        sService.registerStudentToCourse("aiannitti7@is.gd", 5);
//        sService.getStudentCourses("aiannitti7@is.gd");
    }
}
