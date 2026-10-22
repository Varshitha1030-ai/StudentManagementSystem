package studentmanagement;

import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    private static String readText(String message) {
        while (true) {
            System.out.print(message);
            String value = sc.nextLine().trim();

            if (!value.isEmpty() && !value.contains("|")) {
                return value;
            }

            System.out.println(
                    "Enter a non-empty value without |.");
        }
    }

    private static int readInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid whole number.");
            }
        }
    }

    private static double readMarks() {
        while (true) {
            try {
                System.out.print("Enter marks (0-100): ");
                double marks =
                        Double.parseDouble(sc.nextLine().trim());

                if (Double.isFinite(marks)
                        && marks >= 0 && marks <= 100) {
                    return marks;
                }
            } catch (NumberFormatException e) {
                // Show the validation message below.
            }

            System.out.println("Marks must be between 0 and 100.");
        }
    }

    private static Student readStudent(String id) {
        String name = readText("Enter name: ");
        String course = readText("Enter course: ");

        int age;
        do {
            age = readInt("Enter age: ");
            if (age <= 0) {
                System.out.println("Age must be positive.");
            }
        } while (age <= 0);

        double marks = readMarks();

        return new Student(id, name, course, age, marks);
    }

    public static void main(String[] args) {
        StudentManager manager = new StudentManager();
        manager.startAutoSave();

        boolean running = true;

        while (running) {
            System.out.println("\n=== STUDENT MANAGEMENT SYSTEM ===");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. View Unique Courses");
            System.out.println("7. Total Students");
            System.out.println("8. Exit");

            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1: {
                    String id = readText("Enter student ID: ");
                    manager.addStudent(readStudent(id));
                    break;
                }
                case 2:
                    manager.viewStudents();
                    break;
                case 3:
                    manager.searchStudent(
                            readText("Enter student ID: "));
                    break;
                case 4: {
                    String id = readText("Enter student ID: ");
                    System.out.println(
                            "Enter the new student details:");
                    Student s = readStudent(id);

                    manager.updateStudent(id, sName(s),
                            s.getCourse(), sAge(s), sMarks(s));
                    break;
                }
                case 5:
                    manager.deleteStudent(
                            readText("Enter student ID: "));
                    break;
                case 6:
                    manager.showCourses();
                    break;
                case 7:
                    System.out.println("Total students: "
                            + StudentManager.getTotalStudents());
                    break;
                case 8:
                    manager.saveToFile();
                    running = false;
                    System.out.println("Thank you for using the system.");
                    break;
                default:
                    System.out.println("Choose a number from 1 to 8.");
            }
        }

        sc.close();
    }
    private static String sName(Student s) {
        return s.getName();
    }

    private static int sAge(Student s) {
        return s.getAge();
    }

    private static double sMarks(Student s) {
        return s.getMarks();
    }
}