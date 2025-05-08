package customExceptions;

public class CannotCancelEnrollmentException extends Exception {
    private String studentId;
    private int completedGrades;
    private int totalGrades;

    public CannotCancelEnrollmentException(String id, int completed, int total) {
        super("Cannot cancel enrollment for student " + id + ". Student has completed " + completed + " out of " + total
                + " grades");
        studentId = id;
        completedGrades = completed;
        totalGrades = total;
    }

    public String getStudentId() {
        return studentId;
    }

    public int getCompletedGrades() {
        return completedGrades;
    }

    public int getTotalGrades() {
        return totalGrades;
    }
}