package cn.jimyag.zizhuxingserver.Controller;


import cn.jimyag.zizhuxingserver.Dao.SectorDao;
import cn.jimyag.zizhuxingserver.Dao.StudentDao;
import cn.jimyag.zizhuxingserver.Dao.UserDao;
import cn.jimyag.zizhuxingserver.Entity.Sector;
import cn.jimyag.zizhuxingserver.Entity.Student;
import cn.jimyag.zizhuxingserver.Entity.User;
import cn.jimyag.zizhuxingserver.Utils.ErrorCode;
import cn.jimyag.zizhuxingserver.Utils.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.Optional;

@RestController
public class SectorController {

    @Autowired
    private SectorDao sectorDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private StudentDao studentDao;

    @PostMapping("/zizhuxing/sector/join")
    public ResultModel joinSector(@RequestParam(name = "openid") String openid,
                                  @RequestParam(name = "sectorKey") String sectorKey,
                                  @RequestParam(name = "joinTime") BigInteger joinTime,
                                  @RequestParam(name = "studentName") String studentName,
                                  @RequestParam(name = "studentId") String studentId) {
        ResultModel resultModel = new ResultModel();
        Optional<User> userOptional = userDao.findById(openid);
        if (userOptional.isPresent()) {
            Optional<Student> studentOptional = studentDao.findById(studentId);
            if (studentOptional.isPresent()) {
                if (studentDao.findById(studentId).get().getStudentName().equals(studentName)) {
                    Optional<Sector> sectorOptional = sectorDao.findById(sectorKey);
                    if (sectorOptional.isPresent()) {
                        User user = userOptional.get();
                        Sector sector = sectorOptional.get();
                        //更新数据库
                        resultModel.setData(userDao.save(user));
                        resultModel.setMsg("成功");
                        resultModel.setCode(ErrorCode.REQUESTSUCCESS);

                    } else {
                        resultModel.setCode(ErrorCode.SECTORKEYERROR);
                        resultModel.setMsg("邀请码错误");
                    }
                } else {
                    resultModel.setMsg("学号或姓名错误");
                    resultModel.setCode(ErrorCode.STUDENTIDORNAMEERROR);
                }
            } else {
                resultModel.setCode(ErrorCode.STUDENTNOTEXISTE);
                resultModel.setMsg("该学生不存在");
            }
        } else {
            resultModel.setMsg("该用户不存在");
            resultModel.setCode(ErrorCode.USERNOTEXISTE);
        }
        return resultModel;
    }


}
