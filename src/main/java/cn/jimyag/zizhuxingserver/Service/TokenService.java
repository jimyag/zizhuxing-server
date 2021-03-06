package cn.jimyag.zizhuxingserver.Service;

import cn.jimyag.zizhuxingserver.Entity.User;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;


@Service("TokenService")
public class TokenService {
    public String getToken(User user) {
        String token = "";
        token = JWT.create().withAudience(String.valueOf(user.getUsername()))// 将 username 保存到 token 里面
                .sign(Algorithm.HMAC256(user.getPassword()));// 以 password 作为 token 的密钥
        return token;
    }
}