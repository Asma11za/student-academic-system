
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

class User {
    String username;
    String password;

    User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

class Student extends User {
    String name;
    String student_id;
    Map<String, Double> grades;
    Map<String, String> attendance;
    List<String> portfolio;

    Student(String username, String password, String name, String student_id) {
        super(username, password);
        this.name = name;
        this.student_id = student_id;
        this.grades = new HashMap<>();
        this.attendance = new HashMap<>();
        this.portfolio = new ArrayList<>();
        initializeGrades();
    }

    private void initializeGrades() {
        grades.put("English", null);
        grades.put("Music", null);
        grades.put("History", null);
        grades.put("Science", null);
        grades.put("Biology", null);
    }

    void viewGrades() {
        System.out.println("Grades:");
        for (Map.Entry<String, Double> entry : grades.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    void viewAttendance() {
        System.out.println("Attendance:");
        for (Map.Entry<String, String> entry : attendance.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    void viewPortfolio() {
        System.out.println("Portfolio Entries:");
        for (String entry : portfolio) {
            System.out.println(entry);
        }
    }

    void showGrades() {
        System.out.println("Grades:");
        for (Map.Entry<String, Double> entry : grades.entrySet()) {
            System.out.println(entry.getKey() + ": " + gradingCriteria(entry.getValue()));
        }
    }

    private String gradingCriteria(Double totalMarks) {
        if (totalMarks == null) return "Not graded";
        if (totalMarks >= 95) return "A+";
        else if (totalMarks >= 90) return "A";
        else if (totalMarks >= 85) return "B+";
        else if (totalMarks >= 80) return "B";
        else if (totalMarks >= 75) return "C+";
        else if (totalMarks >= 70) return "C";
        else if (totalMarks >= 65) return "D+";
        else if (totalMarks >= 60) return "D";
        else return "F";
    }
}

class Teacher extends User {
    Teacher(String username, String password) {
        super(username, password);
    }

    void addStudent(List<User> users, String name, String student_id, String student_username, String student_password) {
        Student student = new Student(student_username, student_password, name, student_id);
        users.add(student);
        System.out.println("Student added successfully.");
    }

    void addGrade(List<User> users, String student_id, String subject, double grade) {
        for (User user : users) {
            if (user instanceof Student && ((Student) user).student_id.equals(student_id)) {
                ((Student) user).grades.put(subject, grade);
                System.out.println("Grade added successfully.");
                return;
            }
        }
        System.out.println("Student not found.");
    }

    // Other methods...
    void getStudentInformation(List<User> users, String student_id) {
        for (User user : users) {
            if (user instanceof Student && ((Student) user).student_id.equals(student_id)) {
                Student student = (Student) user;
                System.out.println("Name: " + student.name);
                System.out.println("ID: " + student.student_id);
                System.out.println("Grades:");
                for (Map.Entry<String, Double> entry : student.grades.entrySet()) {
                    System.out.println(entry.getKey() + ": " + entry.getValue());
                }
                System.out.println("Attendance:");
                for (Map.Entry<String, String> entry : student.attendance.entrySet()) {
                    System.out.println(entry.getKey() + ": " + entry.getValue());
                }
                System.out.println("Portfolio Entries:");
                for (String portfolioEntry : student.portfolio) {
                    System.out.println(portfolioEntry);
                }
                return;
            }
        }
        System.out.println("Student not found.");
    }

    void distributionOfGrades(List<User> users) {
        System.out.println("Distribution of Grades");
        System.out.println("Available Subjects:");
        // Collecting all subjects from all students
        List<String> allSubjects = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Student) {
                allSubjects.addAll(((Student) user).grades.keySet());
            }
        }

        if (allSubjects.isEmpty()) {
            System.out.println("There are no students registered in the system.");
            return;
        }

        for (int i = 0; i < allSubjects.size(); i++) {
            System.out.println((i + 1) + ". " + allSubjects.get(i));
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter subject number: ");
        int subjectChoice = scanner.nextInt();

        // Convert input to index
        int subjectIndex = subjectChoice - 1;
        if (subjectIndex < 0 || subjectIndex >= allSubjects.size()) {
            System.out.println("Invalid subject number.");
            return;
        }

        String subject = allSubjects.get(subjectIndex);

        boolean studentsFound = false;
        for (User user : users) {
            if (user instanceof Student) {
                if (((Student) user).grades.containsKey(subject)) {
                    studentsFound = true;
                    Scanner inputScanner = new Scanner(System.in);
                    System.out.println("\nStudent: " + ((Student) user).name);
                    System.out.println("Subject: " + subject);
                    System.out.println("Enter total marks for the assignments:");
                    double totalAssignments = inputScanner.nextDouble();
                    System.out.println("Enter total marks for the final exam:");
                    double totalFinalExam = inputScanner.nextDouble();
                    System.out.println("Enter total marks for the midterm exams:");
                    double totalMidtermExams = inputScanner.nextDouble();
                    System.out.println("Enter total marks for the short tests:");
                    double totalShortTests = inputScanner.nextDouble();
                    System.out.println("Enter the project grade:");
                    double projectGrade = inputScanner.nextDouble();

                    // Calculate total marks
                    double totalMarks = totalAssignments + totalFinalExam + totalMidtermExams + totalShortTests + projectGrade;

                    // Store grades
                    ((Student) user).grades.put(subject, totalMarks);
                }
            }
        }

        if (!studentsFound) {
            System.out.println("There are no students registered in this system for the selected subject.");
        }
    }
}

public class  Main {
    public static void main(String[] args) {
        List<User> users = new ArrayList<>();
        users.add(new Teacher("t", "123"));

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nStudent Grade and Academic Performance Tracking System");
            System.out.println("1. Student Login");
            System.out.println("2. Teacher Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            String choice = scanner.next();

            if (choice.equals("1")) {
                System.out.print("Enter username: ");
                String username = scanner.next();
                System.out.print("Enter password: ");
                String password = scanner.next();
                User user = authenticate(username, password, users);
                if (user instanceof Student) {
                    System.out.println("Welcome, Student!");
                    while (true) {
                        System.out.println("\nStudent Menu");
                        System.out.println("1. View Grades");
                        System.out.println("2. View Attendance");
                        System.out.println("3. View Portfolio");
                        System.out.println("4. Show Grades");
                        System.out.println("5. Exit");
                        System.out.print("Enter your choice: ");
                        String studentChoice = scanner.next();
                        if (studentChoice.equals("1")) {
                            ((Student) user).viewGrades();
                        } else if (studentChoice.equals("2")) {
                            ((Student) user).viewAttendance();
                        } else if (studentChoice.equals("3")) {
                            ((Student) user).viewPortfolio();
                        } else if (studentChoice.equals("4")) {
                            ((Student) user).showGrades();
                        } else if (studentChoice.equals("5")) {
                            break;
                        } else {
                            System.out.println("Invalid choice. Please try again.");
                        }
                    }
                } else {
                    System.out.println("Invalid username or password.");
                }
            } else if (choice.equals("2")) {
                System.out.print("Enter username: ");
                String username = scanner.next();
                System.out.print("Enter password: ");
                String password = scanner.next();
                User user = authenticate(username, password, users);
                if (user instanceof Teacher) {
                    System.out.println("Welcome, Teacher!");
                    Teacher teacher = (Teacher) user;
                    while (true) {
                        System.out.println("\nTeacher Menu");
                        System.out.println("1. Add Student");
                        System.out.println("2. Add Grade");
                        System.out.println("3. Update Grade");
                        System.out.println("4. Delete Grade");
                        System.out.println("5. Mark Attendance");
                        System.out.println("6. Add Portfolio Entry");
                        System.out.println("7. List All Students");
                        System.out.println("8. Get Student Information");
                        System.out.println("9. Distribution of Grades");
                        System.out.println("10. Exit");
                        System.out.print("Enter your choice: ");
                        String teacherChoice = scanner.next();
            if (teacherChoice.equals("1")) {
    // Add Student
    System.out.print("Enter student name: ");
    String name = scanner.next();
    System.out.print("Enter student ID: ");
    String student_id = scanner.next();
    System.out.print("Enter student username: ");
    String student_username = scanner.next();
    System.out.print("Enter student password: ");
    String student_password = scanner.next();
    teacher.addStudent(users, name, student_id, student_username, student_password);
} else if (teacherChoice.equals("2")) {
    // Add Grade
    System.out.print("Enter student ID: ");
    String student_id = scanner.next();
    System.out.print("Enter subject (English, Music, History, Science, Biology): ");
    String subject = scanner.next();
    System.out.print("Enter grade: ");
    double grade = scanner.nextDouble();
    teacher.addGrade(users, student_id, subject, grade);
} else if (teacherChoice.equals("3")) {
    // Update Grade
    System.out.print("Enter student ID: ");
    String student_id = scanner.next();
    System.out.print("Enter subject (English, Music, History, Science, Biology): ");
    String subject = scanner.next();
    System.out.print("Enter new grade: ");
    double newGrade = scanner.nextDouble();
    boolean studentFound = false; // Flag to track if the student is found
    for (User studentUser : users) {
        if (studentUser instanceof Student) {
            Student student = (Student) studentUser; // Cast studentUser to Student
            if (student.student_id.equals(student_id)) {
                student.grades.put(subject, newGrade);
                System.out.println("Grade updated successfully.");
                studentFound = true; // Set the flag to true if student is found
                break; // Exit loop once the student is found and grade is updated
            }
        }
    }
    if (!studentFound) {
        System.out.println("Student not found.");
    }
}

 else if (teacherChoice.equals("4")) {
    // Delete Grade
    System.out.print("Enter student ID: ");
    String student_id = scanner.next();
    System.out.print("Enter subject (English, Music, History, Science, Biology): ");
    String subject = scanner.next();
    for (User studentUser : users) {
        if (studentUser instanceof Student && ((Student) studentUser).student_id.equals(student_id)) {
            ((Student) studentUser).grades.put(subject, null);
            System.out.println("Grade deleted successfully.");
            break;
        }
    }
}
 else if (teacherChoice.equals("5")) {
    // Mark Attendance
    System.out.print("Enter student ID: ");
    String student_id = scanner.next();
    System.out.print("Enter date (YYYY-MM-DD): ");
    String date = scanner.next();
    System.out.print("Enter attendance status (Present/Absent): ");
    String status = scanner.next();
    for (User studentUser : users) {
        if (studentUser instanceof Student && ((Student) studentUser).student_id.equals(student_id)) {
            ((Student) studentUser).attendance.put(date, status);
            System.out.println("Attendance marked successfully.");
            break;
        }
    }
}
 else if (teacherChoice.equals("6")) {
    // Add Portfolio Entry
    System.out.print("Enter student ID: ");
    String student_id = scanner.next();
    System.out.print("Enter entry type (Essay, Project, Assignment): ");
    String entryType = scanner.next();
    System.out.print("Enter portfolio entry: ");
    String entry = scanner.next();
    for (User studentUser : users) {
        if (studentUser instanceof Student && ((Student) studentUser).student_id.equals(student_id)) {
            ((Student) studentUser).portfolio.add(entryType + ": " + entry);
            System.out.println("Portfolio entry added successfully.");
            break;
        }
    }
}
 else if (teacherChoice.equals("7")) {
    // List All Students
    for (User studentUser : users) {
        if (user instanceof Student) {
            System.out.println("Name: " + ((Student) studentUser).name + ", ID: " + ((Student) studentUser).student_id);
        }
    }
} else if (teacherChoice.equals("8")) {
    // Get Student Information
    System.out.print("Enter student ID: ");
    String student_id = scanner.next();
    teacher.getStudentInformation(users, student_id);
} else if (teacherChoice.equals("9")) {
    // Distribution of Grades
    teacher.distributionOfGrades(users);
} else if (teacherChoice.equals("10")) {
    // Exit
    break;
} else {
    System.out.println("Invalid choice. Please try again.");
}

                    }
                } else {
                    System.out.println("Invalid username or password.");
                }
            } else if (choice.equals("3")) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static User authenticate(String username, String password, List<User> users) {
        for (User user : users) {
            if (user.username.equals(username) && user.password.equals(password)) {
                return user;
            }
        }
        return null;
    }
}

