package main.java.DomainModel;

import java.time.LocalDateTime;
import main.java.DomainModel.Users.Tutor;

public class Lesson {
    private int id;
    private String subject;
    private Tutor tutor;
    private LocalDateTime dateTime;
    private int maxStudents;
    private LessonStatus status;

    // Constructor
    public Lesson(int id, String subject, Tutor tutor, LocalDateTime dateTime, int maxStudents) {
        this.id = id;
        this.subject = subject;
        this.tutor = tutor;
        this.dateTime = dateTime;
        this.maxStudents = maxStudents;
        this.status = LessonStatus.SCHEDULED; // Default status is scheduled
    }

    public int getId() {
        return id;
    }
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
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}