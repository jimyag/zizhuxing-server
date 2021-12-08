package cn.jimyag.zizhuxingserver.Dao;

import cn.jimyag.zizhuxingserver.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentDao extends JpaRepository<Student, String> {
}
