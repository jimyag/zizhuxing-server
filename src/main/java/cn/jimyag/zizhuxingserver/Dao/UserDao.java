package cn.jimyag.zizhuxingserver.Dao;


import cn.jimyag.zizhuxingserver.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
import java.util.List;

public interface UserDao extends JpaRepository<User, String> {

    public boolean existsUserByUsername(String username);

    public User findByUsername(String username);

    @Query(value = "select * from User where username like ?1% limit ?2,?3", nativeQuery = true)
    public List<User> findByUsernameLikeSubPage(String username, int limit, int offset);

    @Modifying
    @Transactional
    public Integer deleteUserByUsername(String username);



    @Modifying
    @Transactional
    @Query(value = "update User set username  = ?2 where username =?1", nativeQuery = true)
    public Integer updateUserName(String oldUserName, String NewUsername);


    @Modifying
    @Transactional
    @Query(value = "update User set password = ?2 where id = ?1", nativeQuery = true)
    public Integer updateUserPassword(int id, String password);
}
