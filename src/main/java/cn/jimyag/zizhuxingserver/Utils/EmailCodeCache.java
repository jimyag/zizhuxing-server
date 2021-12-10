package cn.jimyag.zizhuxingserver.Utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class EmailCodeCache {

    private final Cache<String, String> loadingCache = CacheBuilder.newBuilder()
            //cache的初始容量
            .initialCapacity(100)
            //cache最大缓存数
            .maximumSize(200)
            //设置写缓存后n秒钟过期
            .expireAfterWrite(300, TimeUnit.SECONDS)
            //设置读写缓存后n秒钟过期,实际很少用到,类似于expireAfterWrite
            //.expireAfterAccess(17, TimeUnit.SECONDS)
            .build();
    private String email;


    public void Cache(String key, String value) {
        loadingCache.put(key, value);
    }

    public String GetCache(String key) {
        String value = loadingCache.getIfPresent(key);
        if (value == null) {
            value = "";
        }
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmailCodeCache)) return false;
        EmailCodeCache that = (EmailCodeCache) o;
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}

class Test {
    private final Cache<String, String> loadingCache = CacheBuilder.newBuilder()
            //cache的初始容量
            .initialCapacity(100)
            //cache最大缓存数
            .maximumSize(200)
            //设置写缓存后n秒钟过期
            .expireAfterWrite(5, TimeUnit.SECONDS)
            //设置读写缓存后n秒钟过期,实际很少用到,类似于expireAfterWrite
            //.expireAfterAccess(17, TimeUnit.SECONDS)
            .build();
    private String email;

    public ResultModel testCache() {
        ResultModel resultModel = new ResultModel();
        loadingCache.put("jimyag@126.com", "1111111");
        resultModel.setData("成功");
        return resultModel;
    }

    public ResultModel testGetCache() throws ExecutionException {
        String value = loadingCache.getIfPresent("jimyag@126.com");
        ResultModel resultModel = new ResultModel();
//        loadingCache.put("jimyag@126.com", "1111111");
        resultModel.setData(value);
        return resultModel;
    }
}
