package cn.jimyag.zizhuxingserver.Utils;

public interface ErrorCode {
    /**
     * 0        请求成功
     * 10001    该用户已存在
     * 10002    更新用户信息失败
     * 10003    该用户不存在
     * 10004    邀请码错误
     * 10005    该学生不存在
     * 10006    学号或姓名错误
     * 11111    未知错误
     */
    int REQUESTSUCCESS = 0;
    int USEREXISTE = 10001;
    int UPDATEUSERFAIL = 10002;
    int USERNOTEXISTE = 10003;
    int SECTORKEYERROR = 10004;
    int STUDENTNOTEXISTE = 10005;
    int STUDENTIDORNAMEERROR = 10006;
    int UNKNOWERROE = 11111;
}
