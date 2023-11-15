package main.java.DomainModel;

import java.time.LocalDateTime;

public class Lesson {
    private String subject;
    private Tutor tutor;
    private LocalDateTime dateTime;
    private int maxStudents;
    private LessonStatus status;

    // Constructor
    public Lesson(String subject, Tutor tutor, LocalDateTime dateTime, int maxStudents) {
        this.subject = subject;
        this.tutor = tutor;
        this.dateTime = dateTime;
        this.maxStudents = maxStudents;
        this.status = LessonStatus.SCHEDULED; // Default status is scheduled
    }

    // Getter and Setter methods

    public String getSubject() {
        return subject;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public int getMaxStudents() {
        return maxStudents;
    }

    public LessonStatus getStatus() {
        return status;
    }

    public void setStatus(LessonStatus status) {
        this.status = status;
    }

    // Additional methods if needed

    @Override
    public String toString() {
        return "Lesson{" +
                "subject='" + subject + '\'' +
                ", tutor=" + tutor +
                ", dateTime=" + dateTime +
                ", maxStudents=" + maxStudents +
                ", status=" + status +
                '}';
    }
}