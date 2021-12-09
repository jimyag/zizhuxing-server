package cn.jimyag.zizhuxingserver.Dao;


import cn.jimyag.zizhuxingserver.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface UserDao extends JpaRepository<User, String> {

    boolean existsUserByUsername(String username);

    User findByUsername(String username);

    User findById(int id);

    @Query(value = "select * from User where username like %?1% limit ?2,?3", nativeQuery = true)
    List<User> findByUsernameLikeSubPage(String username, int limit, int offset);

    @Modifying
    @Transactional
    Integer deleteUserById(int id);


    @Modifying
    @Transactional
    @Query(value = "update User set username  = ?2 where id =?1", nativeQuery = true)
    Integer updateUserNameById(int id, String NewUsername);

    @Modifying
    @Transactional
    @Query(value = "update User set role  = ?2 where id =?1", nativeQuery = true)
    Integer updateRoleById(int id, int role);


    @Modifying
    @Transactional
    @Query(value = "update User set password = ?2 where id = ?1", nativeQuery = true)
    Integer updateUserPassword(int id, String password);


    @Modifying
    @Transactional
    @Query(value = "update User set email = ?2 where id = ?1", nativeQuery = true)
    Integer updateUserEmail(int id, String email);
}
