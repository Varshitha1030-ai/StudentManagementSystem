package studentmanagement;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
public class StudentManager implements StudentOperations {
    private static final Path FILE = Path.of("students.txt");
    private final ArrayList<Student> students = new ArrayList<>();
    private final HashMap<String, Student> studentMap =
            new HashMap<>();
    private final HashSet<String> courses = new HashSet<>();
    private static int totalStudents = 0;
    public StudentManager() {
        loadFromFile();
    }
    private void rebuildCollections() {
        studentMap.clear();
        courses.clear();
        for (Student s : students) {
            studentMap.put(s.getId(), s);
            courses.add(s.getCourse());
        }
        totalStudents = students.size();
    }
    @Override
    public void addStudent(Student student) {
        if (studentMap.containsKey(student.getId())) {
            System.out.println("Student ID already exists.");
            return;
        }
        students.add(student);
        rebuildCollections();
        saveToFile();
        System.out.println("Student added successfully.");
    }
    @Override
    public void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }
        for (Student s : students) {
            s.displayDetails();
        }
    }
    @Override
    public void searchStudent(String id) {
        Student s = studentMap.get(id);
        if (s == null) {
            System.out.println("Student not found.");
        } else {
            s.displayDetails();
        }
    }
    @Override
    public void updateStudent(String id, String name,
                              String course, int age,
                              double marks) {
        Student s = studentMap.get(id);

        if (s == null) {
            System.out.println("Student not found.");
            return;
        }
        s.setName(name);
        s.setCourse(course);
        s.setAge(age);
        s.setMarks(marks);
        rebuildCollections();
        saveToFile();
        System.out.println("Student updated successfully.");
    }
    @Override
    public void deleteStudent(String id) {
        Student s = studentMap.get(id);

        if (s == null) {
            System.out.println("Student not found.");
            return;
        }
        students.remove(s);
        rebuildCollections();
        saveToFile();
        System.out.println("Student deleted successfully.");
    }
    public void showCourses() {
        System.out.println("Unique courses: " + courses);
    }
    public static int getTotalStudents() {
        return totalStudents;
    }
    public synchronized void saveToFile() {
        List<String> lines = new ArrayList<>();

        for (Student s : students) {
            lines.add(s.toFileString());
        }
        try {
            Files.write(FILE, lines,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.out.println("File save error: "
                    + e.getMessage());
        }
    }
    private void loadFromFile() {
        if (!Files.exists(FILE)) {
            return;
        }
        try {
            for (String line : Files.readAllLines(FILE)) {
                try {
                    Student s = Student.fromFileString(line);

                    if (!studentMap.containsKey(s.getId())) {
                        students.add(s);
                        studentMap.put(s.getId(), s);
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Skipping invalid record.");
                }
            }

            rebuildCollections();
            System.out.println("Student records loaded.");
        } catch (IOException e) {
            System.out.println("File read error: "
                    + e.getMessage());
        }
    }

    public void startAutoSave() {
        Thread autoSaveThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(60000);
                    saveToFile();
                    System.out.println(
                            "\n[Background] Records saved.");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        autoSaveThread.setDaemon(true);
        autoSaveThread.start();
    }
}