package customExceptions;

public class StudentAlreadyEnrolledException extends Exception {
    private String studentId;

    public StudentAlreadyEnrolledException(String id) {
        super("Student with ID " + id + " is already enrolled in the course");
        studentId = id;
    }

    public String getStudentId() {
        return studentId;
    }
}