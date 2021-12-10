package cn.jimyag.zizhuxingserver.Controller;

import cn.jimyag.zizhuxingserver.Dao.SectorDao;
import cn.jimyag.zizhuxingserver.Dao.UserDao;
import cn.jimyag.zizhuxingserver.Entity.User;
import cn.jimyag.zizhuxingserver.Service.TokenService;
import cn.jimyag.zizhuxingserver.Utils.*;

import cn.jimyag.zizhuxingserver.annoation.UserLoginToken;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
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
    private final EmailCodeCache emailCodeCache = new EmailCodeCache();
    private final Email email = new Email();


    @PostMapping("/email/{email}")
    public ResultModel SendEmail(@PathVariable String email) {
        ResultModel resultModel = new ResultModel();
        String cacheCode = this.emailCodeCache.GetCache(email);
        if (cacheCode.equals("")) {
            resultModel.setCode(ErrorCode.UNKNOWERROE);
            resultModel.setMsg("歇会再发");
            return resultModel;
        }
        String code = RandomCode.randomCode();
        try {
            this.email.Send(email, "资助星注册验证码", "<h1>【资助星】欢迎注册资助星，您本次的验证码为" + code + "请保存好不要随意给其他人，有效期5分钟</h1>");
            emailCodeCache.Cache(email, code);
        } catch (UnsupportedEncodingException | MessagingException e) {
            resultModel.setCode(ErrorCode.UNKNOWERROE);
            resultModel.setMsg(e.toString());
            return resultModel;
        }
        resultModel.setCode(ErrorCode.REQUESTSUCCESS);
        resultModel.setMsg("验证码发送成功");
        return resultModel;
    }


    @PostMapping("/register")
    public ResultModel createUser(@RequestBody String jsonData) {
        JSONObject jsonObject = JSON.parseObject(jsonData);
        ResultModel resultModel = new ResultModel();
        String code = jsonObject.getString("code");
        String email = jsonObject.getString("email");

        String cacheCode = this.emailCodeCache.GetCache(email);
        // 判断验证码是否存在
        if (cacheCode.equals("")) {
            resultModel.setCode(ErrorCode.UNKNOWERROE);
            resultModel.setMsg("验证码不存在或已过期");
            return resultModel;
        }

        if (!cacheCode.equals(code)) {
            resultModel.setCode(ErrorCode.UNKNOWERROE);
            resultModel.setMsg("验证码错误");
            return resultModel;
        }
        this.emailCodeCache.removeCache(email);
        User user = JSON.toJavaObject(jsonObject, User.class);

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
        ResultModel resultModel = new ResultModel();
        JSONObject jsonObject = JSON.parseObject(jsonData);
        User user = JSON.toJavaObject(jsonObject, User.class);
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
    @GetMapping("/user/{id}")
    public ResultModel getUser(@PathVariable int id) {
        ResultModel resultModel = new ResultModel();
        User user = userDao.findById(id);
        if (user == null) {
            resultModel.setMsg("用户不存在");
            resultModel.setCode(ErrorCode.USERNOTEXISTE);
            return resultModel;
        }
        resultModel.setCode(ErrorCode.REQUESTSUCCESS);
        resultModel.setMsg("成功");
        resultModel.setData(user);
        return resultModel;
    }


    @PutMapping("/user/{id}/role")
    public ResultModel editUserRole(@PathVariable Integer id, @RequestBody String jsonData) {
        var jsonObject = JSON.parseObject(jsonData);
        User newUser = JSON.toJavaObject(jsonObject, User.class);
        User oldUser = userDao.findById(id);
        ResultModel resultModel = new ResultModel();
        if (oldUser == null) {
            resultModel.setCode(ErrorCode.USERNOTEXISTE);
            resultModel.setData("用户不存在");
            return resultModel;
        }
        int count = userDao.updateRoleById(id, newUser.getRole());
        if (count == 1) {
            resultModel.setCode(ErrorCode.REQUESTSUCCESS);
            resultModel.setMsg("成功");
        } else {
            resultModel.setCode(ErrorCode.UNKNOWERROE);
            resultModel.setData("失败");
        }
        return resultModel;

    }

    @UserLoginToken
    @PutMapping("/user/{id}/username")
    public ResultModel editUserName(@PathVariable Integer id, @RequestBody String jsonData) {
        JSONObject jsonObject = JSON.parseObject(jsonData);
        User newUser = JSON.toJavaObject(jsonObject, User.class);
        User oldUser = userDao.findById(id);
        ResultModel resultModel = new ResultModel();
        if (oldUser == null) {
            resultModel.setCode(ErrorCode.USERNOTEXISTE);
            resultModel.setData("用户不存在");
            return resultModel;
        }
        int count = userDao.updateUserNameById(id, newUser.getUsername());

        if (count == 1) {
            resultModel.setCode(ErrorCode.REQUESTSUCCESS);
            resultModel.setMsg("成功");
        } else {
            resultModel.setCode(ErrorCode.UNKNOWERROE);
            resultModel.setData("失败");
        }
        return resultModel;
    }

    @UserLoginToken
    @GetMapping("/users")
    public ResultModel getUserList(@RequestParam(required = false) String username,
                                   @RequestParam(required = false) Integer pagesize,
                                   @RequestParam(required = false) Integer pagenum) {
        int resTotal = userDao.findAll().size();
        ResultModel resultModel = new ResultModel();
        List<User> userList;
        Map<String, Object> res = new HashMap<>();
        int index = (pagenum - 1) * pagesize;
        if (username == null) {
            username = "";
        }
        userList = userDao.findByUsernameLikeSubPage(username, index, pagesize);
        res.put("total", resTotal);
        res.put("data", userList);
        resultModel.setCode(ErrorCode.REQUESTSUCCESS);
        resultModel.setMsg("成功");
        resultModel.setData(res);
        return resultModel;
    }

    @UserLoginToken
    @DeleteMapping("/user/{id}")
    public ResultModel deleteUser(@PathVariable int id) {
        User user = userDao.findById(id);
        ResultModel resultModel = new ResultModel();
        if (user == null) {
            resultModel.setCode(ErrorCode.USERNOTEXISTE);
            resultModel.setMsg("用户不存在");
            return resultModel;
        }
        int count = userDao.deleteUserById(id);
        if (count == 1) {
            resultModel.setMsg("成功");
            resultModel.setCode(ErrorCode.REQUESTSUCCESS);
            return resultModel;
        }
        resultModel.setCode(ErrorCode.UNKNOWERROE);
        resultModel.setData("失败");
        return resultModel;
    }

    @UserLoginToken
    @PutMapping("/user/{id}/password")
    public ResultModel editUserPassword(@PathVariable Integer id, @RequestBody String jsonData) {
        JSONObject jsonObject = JSON.parseObject(jsonData);
        ResultModel resultModel = new ResultModel();
        User newUser = JSON.toJavaObject(jsonObject, User.class);
        User oldUser = userDao.findById(id);
        if (oldUser == null) {
            resultModel.setCode(ErrorCode.USERNOTEXISTE);
            resultModel.setData("用户不存在");
            return resultModel;
        }
        int count = userDao.updateUserPassword(id, newUser.getPassword());

        if (count == 1) {
            resultModel.setCode(ErrorCode.REQUESTSUCCESS);
            resultModel.setMsg("成功");
        } else {
            resultModel.setCode(ErrorCode.UNKNOWERROE);
            resultModel.setData("失败");
        }
        return resultModel;
    }


    @UserLoginToken
    @PutMapping("/user/{id}/email")
    public ResultModel editUserEmail(@PathVariable Integer id, @RequestBody String jsonData) {
        JSONObject jsonObject = JSON.parseObject(jsonData);
        ResultModel resultModel = new ResultModel();
        User newUser = JSON.toJavaObject(jsonObject, User.class);
        User oldUser = userDao.findById(id);
        if (oldUser == null) {
            resultModel.setCode(ErrorCode.USERNOTEXISTE);
            resultModel.setData("用户不存在");
            return resultModel;
        }
        int count = userDao.updateUserEmail(id, newUser.getEmail());

        if (count == 1) {
            resultModel.setCode(ErrorCode.REQUESTSUCCESS);
            resultModel.setMsg("成功");
        } else {
            resultModel.setCode(ErrorCode.UNKNOWERROE);
            resultModel.setData("失败");
        }
        return resultModel;
    }

}
