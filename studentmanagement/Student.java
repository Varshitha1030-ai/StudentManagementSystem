package studentmanagement;
public class Student extends Person {
    private String id;
    private String course;
    private double marks;
    public Student(String id, String name, String course,
                   int age, double marks) {
        super(name, age);
        this.id = id;
        this.course = course;
        this.marks = marks;
    }
    public String getId() {
        return id;
    }
    public String getCourse() {
        return course;
    }
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getMarks() {
        return marks;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCourse(String course) {
        this.course = course;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setMarks(double marks) {
        this.marks = marks;
    }
    @Override
    public void displayDetails() {
        System.out.println("ID: " + id
                + " | Name: " + name
                + " | Course: " + course
                + " | Age: " + age
                + " | Marks: " + marks);
    }
    public String toFileString() {
        return id + "|" + name + "|" + course
                + "|" + age + "|" + marks;
    }
    public static Student fromFileString(String line) {
        String[] data = line.split("\\|", -1);

        if (data.length != 5) {
            throw new IllegalArgumentException(
                    "Invalid student record");
        }
        return new Student(
                data[0], data[1], data[2],
                Integer.parseInt(data[3]),
                Double.parseDouble(data[4]));
    }
}