package test.java.DomainModelTests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import main.java.DomainModel.Users.Manager;

public class ManagerTest {
    Manager tested; 
    //checks to see if singleton works
    @Test
    public void testSingleton (){
        int originalID = 0; 
        tested = Manager.getInstance(originalID, "firstName", "lastname", "test@gmail.com", "1234", "4321");
        //different ID manager tried to be created
        Manager tested2 = Manager.getInstance(1, "firstName", "lastname", "test@gmail.com", "1234", "4321");
        assertEquals(originalID, tested2.getId());
    }
}
