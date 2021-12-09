package cn.jimyag.zizhuxingserver.Controller;

import cn.jimyag.zizhuxingserver.Dao.SectorDao;
import cn.jimyag.zizhuxingserver.Dao.UserDao;
import cn.jimyag.zizhuxingserver.Entity.User;
import cn.jimyag.zizhuxingserver.Service.TokenService;
import cn.jimyag.zizhuxingserver.Utils.ErrorCode;
import cn.jimyag.zizhuxingserver.Utils.ResultModel;

import cn.jimyag.zizhuxingserver.annoation.UserLoginToken;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequestMapping("/zizhuxing")
@RestController
public class UserController {
    @Autowired
    private UserDao userDao;

    @Autowired
    SectorDao sectorDao;

    @Autowired
    TokenService tokenService;


    @PostMapping("/register")
    public ResultModel createUser(@RequestBody String jsonData) {
        System.out.println(jsonData);
        JSONObject jsonObject = JSON.parseObject(jsonData);
        User user = JSON.toJavaObject(jsonObject, User.class);
        ResultModel resultModel = new ResultModel();
        user.setRole(2);

        if (userDao.existsUserByUsername(user.getUsername())) {
            resultModel.setMsg("该用户已存在");
            resultModel.setCode(ErrorCode.USEREXISTE);
        } else {
            userDao.save(user);
            resultModel.setCode(ErrorCode.REQUESTSUCCESS);
            resultModel.setMsg("成功");
        }
        return resultModel;
    }


    /**
     * 登录
     */
    @PostMapping("/login")
    public ResultModel updateUser(@RequestBody String jsonData) {
        JSONObject jsonObject = JSON.parseObject(jsonData);
        User user = JSON.toJavaObject(jsonObject, User.class);
        ResultModel resultModel = new ResultModel();
        if (!userDao.existsUserByUsername(user.getUsername())) {
            resultModel.setMsg("该用户不存在");
            resultModel.setCode(ErrorCode.USERNOTEXISTE);
        } else {
            String token = tokenService.getToken(user);
            user = userDao.findByUsername(user.getUsername());
            Map<String, Object> res = new HashMap<>();
            res.put("token", token);
            res.put("user", user);
            resultModel.setData(res);
            resultModel.setCode(ErrorCode.REQUESTSUCCESS);
            resultModel.setMsg("成功");
        }
        return resultModel;
    }

    @UserLoginToken
    @GetMapping("/user")
    public ResultModel getUserList(@RequestParam(required = false) String username,
                                   @RequestParam(required = false) Integer pagesize,
                                   @RequestParam(required = false) Integer pagenum) {
        ResultModel resultModel = new ResultModel();
        List<User> userList;
        Map<String, Object> res = new HashMap<>();
        // 如果用户名为空 就是所有的用户都要
        if (username == null) {
            userList = userDao.findAll();

        } else {
            userList = userDao.findByUsernameLike(username);
        }
        int resTotal = userList.size();
        if (pagesize != null && pagenum != null) {
            if (pagesize < 1) {
                pagesize = 1;
            }
            if (pagenum < 1) {
                pagenum = 1;
            }
            int totalPage = (int) Math.ceil(resTotal * 1.0 / pagesize);
            int begin = (resTotal / totalPage) * (pagenum - 1);
            int end = resTotal;
            if (begin + pagesize <= end) {
                end = begin + pagesize;
            }
            System.out.println(begin);
            System.out.println(end);
            userList = userList.subList(begin, end);
        }
        res.put("total", resTotal);
        res.put("data", userList);
        resultModel.setCode(ErrorCode.REQUESTSUCCESS);
        resultModel.setMsg("成功");
        resultModel.setData(res);
        return resultModel;
    }

    @UserLoginToken
    @DeleteMapping("/user")
    public ResultModel deleteUser(@RequestParam String username) {
        ResultModel resultModel = new ResultModel();
        User user = userDao.findByUsername(username);
        if (user == null) {
            resultModel.setCode(ErrorCode.USERNOTEXISTE);
            resultModel.setMsg("用户不存在");
            return resultModel;
        }
        int count = userDao.deleteUserByUsername(username);
        if (count == 1) {
            resultModel.setMsg("成功");
            resultModel.setCode(ErrorCode.REQUESTSUCCESS);
            return resultModel;
        }
        resultModel.setCode(ErrorCode.UNKNOWERROE);
        resultModel.setData("失败");
        return resultModel;
    }
}
