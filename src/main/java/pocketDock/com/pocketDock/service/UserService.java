package pocketDock.com.pocketDock.service;

import pocketDock.com.pocketDock.entity.Convention;
import pocketDock.com.pocketDock.entity.OurUsers;

import java.util.List;

public interface UserService {
    boolean checkEmail(String email);
    public List<OurUsers> getAllUsers();

    public OurUsers findByIdAndRole(Integer userId, String role);

    public OurUsers addUser(OurUsers user);
    public OurUsers retrieveUser(int userId);
    public void removeUser(int userId);
    public OurUsers modifyUser(OurUsers user);
    public void affecterConventionAUneUserExistante(int userId, Convention convention);
    }
