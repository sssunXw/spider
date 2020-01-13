package Request;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.plugin.net.OkHttpRequester;
import okhttp3.Request;

/**
 * <pre>
 * 功能说明:
 * </pre>
 *
 * @author sxw
 * @date 2019/6/7
 */

public class GroupReuqest extends OkHttpRequester {

    String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36";
    String cookie = "confSessionId=c1038ab597a92af3; Hm_lvt_41fc030db57d5570dd22f78997dc4a7e=1554085789; fine_login_users=b5f0c2ee-640f-4039-a4d4-918b55354898; fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU5OTA2MjI3LCJleHAiOjE1NTk5MDk4MjcsInN1YiI6ImxpbmRleGlhbmciLCJkZXNjcmlwdGlvbiI6ImxpbmRleGlhbmcobGluZGV4aWFuZykiLCJqdGkiOiJqd3QifQ.SiumGkjra4ujJhmHUqnS_6oIYCTMBFuvFY5GuJdMONY";
    String Authorization = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU5OTA2MjI3LCJleHAiOjE1NTk5MDk4MjcsInN1YiI6ImxpbmRleGlhbmciLCJkZXNjcmlwdGlvbiI6ImxpbmRleGlhbmcobGluZGV4aWFuZykiLCJqdGkiOiJqd3QifQ.SiumGkjra4ujJhmHUqnS_6oIYCTMBFuvFY5GuJdMONY";




    // 每次发送请求前都会执行这个方法来构建请求
    @Override
    public Request.Builder createRequestBuilder(CrawlDatum crawlDatum) {
        // 这里使用的是OkHttp中的Request.Builder
        // 可以参考OkHttp的文档来修改请求头
        System.out.println("request with cookie: " + cookie);
        return super.createRequestBuilder(crawlDatum)
                .header("User-Agent", userAgent)
                .header("Authorization", Authorization)
                .header("Cookie", cookie);
    }


}
