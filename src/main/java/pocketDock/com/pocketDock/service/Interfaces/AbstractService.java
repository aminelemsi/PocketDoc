package pocketDock.com.pocketDock.service.Interfaces;

import java.util.List;

public interface AbstractService <T>{
    Integer save(T dto) throws Exception;
    List<T> findAll ();
    T findById (Integer id);
    void delete (Integer id);
}
