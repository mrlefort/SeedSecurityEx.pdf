/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;


import facades.UserFacade;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import rest.User;
import security.PasswordStorage;

/**
 *
 * @author Steffen
 */
public class testRun {

    public static void main(String[] args) throws PasswordStorage.CannotPerformOperationException {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SemesterSeedPU");
        EntityManager em = emf.createEntityManager();

        Persistence.generateSchema("SemesterSeedPU", null);

        Users user = new Users("user1", PasswordStorage.createHash("test"));
        user.AddRole("User");

        Users user2 = new Users("admin1", PasswordStorage.createHash("test"));
        user2.AddRole("Admin");

        
        
        em.getTransaction().begin();
        em.persist(user);
        em.persist(user2);
        em.getTransaction().commit();
        em.close();
        
        UserFacade u = new UserFacade();
        u.authenticateUser("user1", "test");
        

    }
}
