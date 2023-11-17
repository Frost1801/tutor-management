package main.java.BusinessLogic;


public interface Subject {
    public void attach(Observer ob); 
    public void detach(Observer ob);
    public void notifyObservers();
}
