package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;

public class Main {
    public static void main(String[] args) {
        UserDaoHibernateImpl dao = new UserDaoHibernateImpl();
        dao.createUsersTable();
        dao.saveUser("Rafshan", "Ragimow", (byte)19);
        dao.saveUser("Danil", "Shtok", (byte)19);
        dao.saveUser("Vera", "Ragimowa", (byte)13);
        dao.saveUser("Ainura", "Ragimova", (byte)27);
        for(User user : dao.getAllUsers()){
            System.out.println(user);
        }
        dao.dropUsersTable();
        dao.close();
    }
}
