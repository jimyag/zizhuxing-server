package cn.jimyag.zizhuxingserver.Dao;

import cn.jimyag.zizhuxingserver.Entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScheduleDao extends JpaRepository<Schedule,Integer> {

    @Query(value = "select * from Schedule where sectorName=?1",nativeQuery = true)
    public List<Schedule> findBySectorName(String sectorNam);
}
