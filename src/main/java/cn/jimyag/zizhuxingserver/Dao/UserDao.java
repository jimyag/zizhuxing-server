package cn.jimyag.zizhuxingserver.Dao;


import cn.jimyag.zizhuxingserver.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserDao extends JpaRepository<User, String> {

    @Query(value = "select * from User where sectorName=?1", nativeQuery = true)
    public List<User> findBySectorName(String sectorName);

    public boolean existsUserByUsername(String username);

    public User findByUsername(String username);

    @Query(value = "select * from User where username like ?1%",nativeQuery = true)
    public List<User> findByUsernameLike(String username);
}
