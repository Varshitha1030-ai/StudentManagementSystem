package studentmanagement;
public interface StudentOperations {
    void addStudent(Student student);
    void viewStudents();
    void searchStudent(String id);
    void updateStudent(String id, String name,
                       String course, int age, double marks);
    void deleteStudent(String id);
}