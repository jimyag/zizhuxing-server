package cn.jimyag.zizhuxingserver.Dao;


import cn.jimyag.zizhuxingserver.Entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AttendanceDao extends JpaRepository<Attendance, Integer> {

    @Query(value = "select * from Attendance where studentId=?1", nativeQuery = true)
    public List<Attendance> findByStudentId(String studentId);
}
