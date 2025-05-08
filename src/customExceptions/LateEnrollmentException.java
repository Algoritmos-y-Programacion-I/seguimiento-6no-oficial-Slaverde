package customExceptions;

public class LateEnrollmentException extends Exception {
    private int currentWeek;

    public LateEnrollmentException(int week) {
        super("Cannot enroll after week 2. Current week is " + week);
        currentWeek = week;
    }

    public int getCurrentWeek() {
        return currentWeek;
    }
}