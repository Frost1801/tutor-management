package main.java.BusinessLogic.Controller;
import main.java.BusinessLogic.Subject;
import main.java.BusinessLogic.Observer;


import main.java.DataAccess.LessonDAO; 
import main.java.DomainModel.Lesson;
import main.java.DomainModel.Users.Student;
import main.java.DomainModel.Users.Tutor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class LessonController implements Subject {

    private LessonDAO lessonDAO;
    private List<Observer> observers;

    public LessonController(LessonDAO lessonDAO) {
        this.lessonDAO = lessonDAO;
        this.observers = new ArrayList<>();
    }

    // Other methods for LessonController

    public void addLesson(LocalDateTime time, String subject, Tutor tutor) {
        // Implementation for adding a new lesson
        Lesson newLesson = new Lesson(subject, tutor , time, 10); // Assuming a max of 10 students
        lessonDAO.insert(newLesson);
        notifyObservers(); // Notify observers about the new lesson
    }

    public void deleteLesson(int id) {
        // Implementation for deleting a lesson
        lessonDAO.delete(lessonDAO.get(id));
        notifyObservers(); // Notify observers about the deleted lesson
    }


    @Override
    public void attach(Observer ob) {
        observers.add(ob);
    }

    @Override
    public void detach(Observer ob) {
        observers.remove(ob);
    }

    @Override
    public void notifyObservers() {
        for (Observer ob : observers) {
            ob.update(); // Notify each observer
        }
    }
}
