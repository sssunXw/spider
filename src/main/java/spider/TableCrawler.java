package spider;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.net.OkHttpRequester;
import cn.edu.hfut.dmic.webcollector.plugin.rocks.BreadthCrawler;
import entity.Constants;
import entity.PageInfo;
import entity.TableDetail;
import lombok.Data;
import okhttp3.Request;
import util.FastjsonUtils;

import java.util.*;
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
public class TableCrawler extends BreadthCrawler {

    public static TableDetail tableDetail = new TableDetail();

    public static PageInfo pageInfo = new PageInfo();

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

    //分页
    public TableCrawler(String crawlPath, String transferName, String tableName, String pageIndex) {
        super(crawlPath, true);

        // 设置请求插件
        setRequester(new MyRequester());

        tableDetail.setTableName(tableName);

        tableDetail.setTransferName(transferName);

        // 爬取github about下面的网页
        addSeed("http://localhost:37799/webroot/decision/v5/conf/tables/" +
                tableName + "/fields/page?pageIndex=" + pageIndex + "&limit=1000000&_=" + System.currentTimeMillis());
    }

    public void visit(Page page, CrawlDatums crawlDatums) {

        Map body = (HashMap)FastjsonUtils.convertJsonToObject(page.doc().body().text());

        Map data = ((Map)body.get("data"));
        if (body.get("data") instanceof ArrayList) {
            return;
        }

        List<String> fields = ((List<Map>)data.get("fields")).stream().map(e ->
                (String)e.get("transferName")).collect(Collectors.toList());

        tableDetail.setFields(fields);

        tableDetail.getData().addAll((Collection<? extends List<String>>) data.get("data"));

        pageInfo.setPageIndex((Integer) ((Map)data.get("pageInfo")).get("pageIndex"));

        pageInfo.setPageSize((Integer) ((Map)data.get("pageInfo")).get("pageSize"));

        pageInfo.setTotalRows((Integer) ((Map)data.get("pageInfo")).get("totalRows"));

        System.out.println(pageInfo);

    }

    private String encodeString(String data) {
        return java.net.URLEncoder.encode(data);
    }

    public static void main(String[] args) throws Exception {

//        int index = 0;
//
//        while (true) {
//            TableCrawler crawler = new TableCrawler("crawl", "会员数据", String.valueOf(++index));
//            crawler.start(1);
//            if (pageInfo.getPageIndex() * pageInfo.getPageSize() > pageInfo.getTotalRows()) {
//                break;
//            }
//        }
//        FileUtil.writeTableToFile(tableDetail);
    }
}
