package cn.jimyag.zizhuxingserver.Controller;

import cn.jimyag.zizhuxingserver.Dao.ScheduleDao;
import cn.jimyag.zizhuxingserver.Entity.Schedule;
import cn.jimyag.zizhuxingserver.Utils.ErrorCode;
import cn.jimyag.zizhuxingserver.Utils.ResultModel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ScheduleController {

    @Autowired
    private ScheduleDao scheduleDao;


    @PostMapping("/zizhuxing/schedule/get")
    public ResultModel get(@RequestBody String jsonData) {
        JSONObject jsonObject = JSONObject.parseObject(jsonData);
        System.out.println(jsonObject);
        String sectorName = (String) jsonObject.get("sectorName");
        System.out.println(sectorName);
        return new ResultModel(ErrorCode.REQUESTSUCCESS, "成功", scheduleDao.findBySectorName(sectorName));
    }
}
