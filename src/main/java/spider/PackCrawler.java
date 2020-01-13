package spider;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.net.OkHttpRequester;
import cn.edu.hfut.dmic.webcollector.plugin.rocks.BreadthCrawler;
import entity.Constants;
import entity.Pack;
import lombok.Data;
import okhttp3.Request;
import util.FastjsonUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <pre>
 * 功能说明:
 * </pre>
 *
 * @author sxw
 * @date 2019/6/7
 */

@Data
public class PackCrawler extends BreadthCrawler {

    private List<Pack> packs;

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

    public PackCrawler(String crawlPath, String groupId) {
        super(crawlPath, true);

        // 设置请求插件
        setRequester(new MyRequester());

        // 爬取github about下面的网页
        addSeed("http://localhost:37799/webroot/decision/v5/conf/packs/" + groupId + "?_=" + System.currentTimeMillis());

    }

    public void visit(Page page, CrawlDatums crawlDatums) {

        String body = page.doc().body().text();

        Map data = (HashMap)FastjsonUtils.convertJsonToObject(body);

        List<HashMap> temps = (List)(((Map)data.get("data")).get("tables"));
        packs = temps.stream().map(e -> {
            Pack pack = new Pack();
            pack.setName((String) e.get("name"));
            pack.setPack((String) e.get("pack"));
            pack.setTableName((String) e.get("tableName"));
            pack.setType((Integer)e.get("type"));
            pack.setTransferName((String) e.get("transferName"));
            return pack;
        }).collect(Collectors.toList());

        System.out.println(packs.size());


    }

    public static void main(String[] args) throws Exception {
        PackCrawler crawler = new PackCrawler("crawl", "da54083883b34b65983e5f572ff76ea6");
        crawler.start(1);

    }
}
