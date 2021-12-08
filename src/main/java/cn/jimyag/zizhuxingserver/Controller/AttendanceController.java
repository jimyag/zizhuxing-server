package cn.jimyag.zizhuxingserver.Controller;

import cn.jimyag.zizhuxingserver.Dao.AttendanceDao;
import cn.jimyag.zizhuxingserver.Dao.StudentDao;
import cn.jimyag.zizhuxingserver.Entity.Attendance;
import cn.jimyag.zizhuxingserver.Entity.Student;
import cn.jimyag.zizhuxingserver.Utils.ErrorCode;
import cn.jimyag.zizhuxingserver.Utils.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class AttendanceController {

    @Autowired
    private AttendanceDao attendanceDao;

    @Autowired
    private StudentDao studentDao;


    @PostMapping("/zizhuxing/detail/get")
    public ResultModel getDetail(@RequestParam(name = "studentId") String studentId) {
        ResultModel resultModel = new ResultModel();
        Optional<Student> studentOptional = studentDao.findById(studentId);
        if (studentOptional.isPresent()) {
            List<Attendance> attendances = attendanceDao.findByStudentId(studentId);
            resultModel.setMsg("成功");
            resultModel.setCode(ErrorCode.REQUESTSUCCESS);
            resultModel.setData(attendances);
        } else {
            resultModel.setCode(ErrorCode.STUDENTNOTEXISTE);
            resultModel.setMsg("该学生不存在");
        }
        return resultModel;
    }
}
