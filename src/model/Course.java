package model;

import customExceptions.OutOfRangeGradeException;
import customExceptions.QuotaEnrollExceedException;
import customExceptions.LateEnrollmentException;
import customExceptions.StudentAlreadyEnrolledException;
import customExceptions.CannotCancelEnrollmentException;

public class Course {
	private double maxGrade;
	private double minGrade;
	private int currentWeek;
	private int totalGradesAmount;
	private int maxQuota;

	private Student[] studentsEnrolled;

	public Course(int tga, double mx, double mn, int mq) {
		currentWeek = 1;
		maxGrade = mx;
		minGrade = mn;
		totalGradesAmount = tga;
		maxQuota = mq;

		studentsEnrolled = new Student[mq];
	}

	public void enroll(String id)
			throws QuotaEnrollExceedException, LateEnrollmentException, StudentAlreadyEnrolledException {
		if (currentWeek > 2) {
			throw new LateEnrollmentException(currentWeek);
		}

		if (searchStudent(id) != -1) {
			throw new StudentAlreadyEnrolledException(id);
		}

		int posNewStudent = searchFirstAvailable();

		if (posNewStudent == -1) {
			throw new QuotaEnrollExceedException(maxQuota);
		} else {
			studentsEnrolled[posNewStudent] = new Student(id, totalGradesAmount);
		}
	}

	public void cancelEnrollment(String id) throws CannotCancelEnrollmentException {
		int posStudent = searchStudent(id);

		if (posStudent != -1) {
			Student student = studentsEnrolled[posStudent];
			int completedGrades = countCompletedGrades(student);

			if (completedGrades > (totalGradesAmount / 2)) {
				throw new CannotCancelEnrollmentException(id, completedGrades, totalGradesAmount);
			}

			studentsEnrolled[posStudent] = null;
		}
	}

	private int countCompletedGrades(Student student) {
		int count = 0;
		for (int i = 0; i < totalGradesAmount; i++) {
			try {
				if (student.getGrade(i + 1) != 0) {
					count++;
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				// Ignorar índices fuera de rango
			}
		}
		return count;
	}

	public void setStudentGrade(String id, int gradeNumber, double grade)
			throws ArrayIndexOutOfBoundsException, OutOfRangeGradeException {
		if (grade < minGrade || grade > maxGrade) {
			throw new OutOfRangeGradeException(grade, maxGrade, minGrade);
		}

		int posStudent = searchStudent(id);
		studentsEnrolled[posStudent].setGrade(gradeNumber, grade);
	}

	public void advanceWeek() {
		currentWeek++;
	}

	private int searchFirstAvailable() {
		int pos = -1;
		for (int i = 0; i < studentsEnrolled.length && pos == -1; i++) {
			Student current = studentsEnrolled[i];
			if (current == null) {
				pos = i;
			}
		}

		return pos;
	}

	private int searchStudent(String id) {
		int pos = -1;

		for (int i = 0; i < studentsEnrolled.length && pos == -1; i++) {
			Student current = studentsEnrolled[i];
			if (current != null) {
				if (id.contentEquals(current.getId())) {
					pos = i;
				}
			}
		}

		return pos;
	}

	public String showEnrolledStudents() {

		String msg = "";

		for (int i = 0; i < studentsEnrolled.length; i++) {

			if (studentsEnrolled[i] != null) {

				msg += studentsEnrolled[i].getId() + "\n";

			}

		}

		return msg;

	}

	public String showStudentGrades(String id) {

		int pos = searchStudent(id);

		if (pos != -1) {

			return studentsEnrolled[pos].showGrades();

		}

		return "Error";

	}
}
