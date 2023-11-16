package main.java.DomainModel;

import java.time.LocalDateTime;
import main.java.DomainModel.Users.Tutor;

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

}