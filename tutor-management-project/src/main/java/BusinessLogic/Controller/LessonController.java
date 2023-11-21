package main.java.BusinessLogic.Controller;

import main.java.BusinessLogic.Observer;
import main.java.DataAccess.LessonDAO;
import main.java.DataAccess.LessonStudentDAO;
import main.java.DomainModel.Lesson;
import main.java.DomainModel.LessonStatus;
import main.java.DomainModel.Users.Tutor;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LessonController  {

    private LessonDAO lessonDAO;
    //the integer signifies the lesson id
    private Map<Integer, List<Observer>> lessonObserversMap;
    private LessonStudentDAO lessonStudentDAO;

    public LessonController(LessonDAO lessonDAO, LessonStudentDAO lessonStudentDAO) {
        this.lessonDAO = lessonDAO;
        this.lessonStudentDAO = lessonStudentDAO;   
        this.lessonObserversMap = new HashMap<>();
    }


    public void addLesson(LocalDateTime time, String subject, Tutor tutor) {
        try {
            Lesson newLesson = new Lesson(getNextLessonId(), subject, tutor, time, 10); // Assuming a max of 10 students
            lessonDAO.insert(newLesson);
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
        }
    }
    

    public void deleteLesson(int id) {
        try {
            lessonDAO.delete(id);
            notifyObservers(id); // Notify observers about the deleted lesson
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
        }
    }
    
    public void setDate(int id, LocalDateTime date) {
        try {
            Lesson lesson = lessonDAO.get(id);
            if (lesson != null) {
                lesson.setDateTime(date);
                lessonDAO.update(lesson);
                notifyObservers(id); // Notify observers about the updated lesson
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
        }
    }
    
    public void setTime(int id, LocalDateTime time) {
        try {
            Lesson lesson = lessonDAO.get(id);
            if (lesson != null) {
                lesson.setDateTime(time);
                lessonDAO.update(lesson);
                notifyObservers(id); // Notify observers about the updated lesson
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
        }
    }
    
    public void changeStatus(int id, LessonStatus status) {
        try {
            Lesson lesson = lessonDAO.get(id);
            if (lesson != null) {
                lesson.setStatus(status);
                lessonDAO.update(lesson);
                notifyObservers(id); // Notify observers about the updated lesson
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
        }
    }
    

    public List<Lesson> getLessonByDate(LocalDateTime date) {
        return lessonDAO.getLessonsByDate(date);
    }

    public List<Lesson> getLessonBySubject(String subject) {
        return lessonDAO.getLessonsBySubject(subject);
    }

    private int getNextLessonId() {
        try {
            return lessonDAO.getNextId();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
        return -1; // Return a default value or handle the error
    }

    private void notifyObservers(int lessonId) {
        List<Observer> observers = lessonObserversMap.get(lessonId);
        if (observers != null) {
            for (Observer ob : observers) {
                ob.update(); // Notify each observer
            }
        }
    }

    public void attach(Observer ob, int lessonId) {
        lessonObserversMap.computeIfAbsent(lessonId, k -> new ArrayList<>()).add(ob);
    }

    public void detach(Observer ob, int lessonId) {
        List<Observer> observers = lessonObserversMap.get(lessonId);
        if (observers != null) {
            observers.remove(ob);
        }
    }
    public boolean bookLesson(int studentId, int lessonId) {
        try {
            Lesson lesson = lessonDAO.get(lessonId);

            if (lesson != null  && lesson.getStatus() == LessonStatus.SCHEDULED) {
                int remainingSpots = getRemainingSpots(lessonId);
                if (remainingSpots > 0) {
                    lessonStudentDAO.bookLesson(studentId, lessonId);
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
        }
        return false;
    }

    public boolean cancelBooking(int studentId, int lessonId) {
        try {
            if (lessonStudentDAO.cancelBooking(studentId, lessonId)) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
        }
        return false;
    }

    public int getRemainingSpots(int lessonId) {
        try{
            return lessonStudentDAO.getRemainingSpots(lessonId);
        }
        catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
        }
        return -1;
    }
}