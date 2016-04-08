package facades;

import entity.Users;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import security.PasswordStorage;

public class UserFacade {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("SemesterSeedPU");
    EntityManager em = emf.createEntityManager();

//    private final Map<String, Users> users = new HashMap<>();
//
//    public UserFacade() {
//        //Test Users
//        Users user = new Users("user", "test");
//        user.AddRole("User");
//        users.put(user.getUserName(), user);
//        Users admin = new Users("admin", "test");
//        admin.AddRole("Admin");
//        users.put(admin.getUserName(), admin);
//
//        Users both = new Users("user_admin", "test");
//        both.AddRole("User");
//        both.AddRole("Admin");
//        users.put(both.getUserName(), both);
//    }
    public Users getUserByUserId(String id) {
        Query query = em.createNamedQuery("Users.findById", Users.class);
        Users user = (Users) query.setParameter("id", id).getSingleResult();
        return user;
    }

    /*
  Return the Roles if users could be authenticated, otherwise null
     */
    public List<String> authenticateUser(String userName, String password) {
        Query query = em.createNamedQuery("Users.findByName", Users.class);
        Users user = (Users) query.setParameter("userName", userName).getSingleResult();

//        Query query = em.createQuery("SELECT c FROM Users c WHERE c.userName LIKE :userName");
//        query.setParameter("userName", userName);
//
//        Users user = (Users) query.getSingleResult();
//
//        System.out.println(user.getPassword() + " " + user.getUserName() + " " +user.getRoles());
        
        
        Boolean b = false;

        System.out.println("ID: " + user.getId());
        System.out.println("username " + user.getUserName());
        System.out.println("password " + user.getPassword());
        System.out.println("roles " + user.getRoles());
        try {
            //tjekker om password og user.getPassword er det samme. Hvis de er så er B true
            if (PasswordStorage.verifyPassword(password, user.getPassword()) == true) {
                b = true;
            }
            //her har vi confirmed at passwords er de samme, og usernames er de samme.
            while (b == true) {
                System.out.println("hej");
                return user != null && PasswordStorage.verifyPassword(password, user.getPassword()) ? user.getRoles() : null;
            }

        } catch (PasswordStorage.CannotPerformOperationException ex) {
            Logger.getLogger(UserFacade.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PasswordStorage.InvalidHashException ex) {
            Logger.getLogger(UserFacade.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

}
