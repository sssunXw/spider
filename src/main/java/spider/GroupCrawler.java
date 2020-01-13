package spider;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.net.OkHttpRequester;
import cn.edu.hfut.dmic.webcollector.plugin.rocks.BreadthCrawler;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import entity.Constants;
import entity.Group;
import lombok.Data;
import okhttp3.Request;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 功能说明:
 * </pre>
 *
 * @author sxw
 * @date 2019/6/7
 */

@Data
public class GroupCrawler extends BreadthCrawler {

    private List<Group> groups;

    // 自定义的请求插件
    // 可以自定义User-Agent和Cookie
    public static class MyRequester extends OkHttpRequester {

        // 每次发送请求前都会执行这个方法来构建请求
        @Override
        public Request.Builder createRequestBuilder(CrawlDatum crawlDatum) {
            // 这里使用的是OkHttp中的Request.Builder
            // 可以参考OkHttp的文档来修改请求头
            System.out.println("request with cookie: " + Constants.COOKIE);
            return super.createRequestBuilder(crawlDatum)
                    .header("User-Agent", Constants.USER_AGENT)
                    .header("Authorization", Constants.AUTHORIZATION)
                    .header("Cookie", Constants.COOKIE);
        }

    }

    public GroupCrawler(String crawlPath) {
        super(crawlPath, true);

        // 设置请求插件
        setRequester(new MyRequester());

        // 爬取github about下面的网页
        addSeed("http://localhost:37799/webroot/decision/v5/conf/groups?_=" + System.currentTimeMillis());
//        addRegex("https://github.com/about/.*");

    }

    public void visit(Page page, CrawlDatums crawlDatums) {

        String body = page.doc().body().text();

        JSONArray data = (JSONArray)JSON.parseObject(body, Map.class).get("data");

        groups = data.toJavaList(Group.class);

        groups.remove(0);

    }

    public static void main(String[] args) throws Exception {
        GroupCrawler crawler = new GroupCrawler("crawl");
        crawler.start(1);

    }
}
