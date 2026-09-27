import java.util.ArrayList;
import java.util.Scanner;

class Student {
    String name;
    ArrayList<Double> grades;

    Student(String name) {
        this.name = name;
        this.grades = new ArrayList<>();
    }

    void addGrade(double grade) {
        grades.add(grade);
    }

    double getAverage() {
        double sum = 0;

        for (double grade : grades) {
            sum += grade;
        }

        return grades.isEmpty() ? 0 : sum / grades.size();
    }

    double getHighest() {
        if (grades.isEmpty()) {
            return 0;
        }

        double highest = grades.get(0);

        for (double grade : grades) {
            if (grade > highest) {
                highest = grade;
            }
        }

        return highest;
    }

    double getLowest() {
        if (grades.isEmpty()) {
            return 0;
        }

        double lowest = grades.get(0);

        for (double grade : grades) {
            if (grade < lowest) {
                lowest = grade;
            }
        }

        return lowest;
    }

    void displayReport() {
        System.out.println("\nStudent Name : " + name);
        System.out.println("Grades       : " + grades);
        System.out.printf("Average      : %.2f%n", getAverage());
        System.out.println("Highest      : " + getHighest());
        System.out.println("Lowest       : " + getLowest());
    }
}

public class StudentGradeTracker {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();

        while (true) {

            System.out.println("\n===== STUDENT GRADE TRACKER =====");
            System.out.println("1. Add Student");
            System.out.println("2. Add Grade");
            System.out.println("3. Display Student Report");
            System.out.println("4. Display All Students");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter student name: ");
                    String name = sc.nextLine();

                    students.add(new Student(name));

                    System.out.println("Student added successfully!");
                    break;

                case 2:
                    if (students.isEmpty()) {
                        System.out.println("No students available.");
                        break;
                    }

                    System.out.println("\nStudents:");

                    for (int i = 0; i < students.size(); i++) {
                        System.out.println((i + 1) + ". " + students.get(i).name);
                    }

                    System.out.print("Select student number: ");
                    int studentNumber = sc.nextInt();

                    if (studentNumber < 1 || studentNumber > students.size()) {
                        System.out.println("Invalid student number.");
                        break;
                    }

                    System.out.print("Enter grade: ");
                    double grade = sc.nextDouble();

                    if (grade < 0 || grade > 100) {
                        System.out.println("Grade must be between 0 and 100.");
                        break;
                    }

                    students.get(studentNumber - 1).addGrade(grade);

                    System.out.println("Grade added successfully!");
                    break;

                case 3:
                    if (students.isEmpty()) {
                        System.out.println("No students available.");
                        break;
                    }

                    System.out.print("Enter student name: ");
                    String searchName = sc.nextLine();

                    boolean found = false;

                    for (Student student : students) {
                        if (student.name.equalsIgnoreCase(searchName)) {
                            student.displayReport();
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        System.out.println("Student not found.");
                    }

                    break;

                case 4:
                    if (students.isEmpty()) {
                        System.out.println("No students available.");
                        break;
                    }

                    System.out.println("\n===== SUMMARY REPORT =====");

                    for (Student student : students) {
                        student.displayReport();
                    }

                    break;

                case 5:
                    System.out.println("Thank you for using Student Grade Tracker!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}