package spider;

import entity.Group;
import entity.Pack;
import entity.PageInfo;
import entity.TableDetail;
import util.FileUtil;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * <pre>
 * 功能说明:
 * </pre>
 *
 * @author sxw
 * @date 2019/6/8
 */

public class StartMain {
    public static void main(String[] args) throws Exception {
        //解析分组
        GroupCrawler crawler = new GroupCrawler("crawl");
        crawler.start(1);

        List<String> str = Arrays.asList("产品名称维度", "地区数据分析", "分公司维度表", "合同回款事实表", "客户维度表", "合同事实表", "销售员维度表", "销售事实表", "KPI指标项",
                "123", "浏览器占比变化", "南京苏果营业数据", "全国环境监测数据", "搜索词汇统计表", "西安兰特雨量监测数据", "样式数据二", "样式数据一", "RFM分析表", "RFM明细数据",
                "");
        Set set = new HashSet();
        set.addAll(str);

        for (Group group : crawler.getGroups()) {

            for (Group pack : group.getPacks()) {
                PackCrawler packCrawler = new PackCrawler("crawl", pack.getId());
                packCrawler.start(1);
                System.out.println(packCrawler.getPacks().size());
                for (Pack packCrawlerPack : packCrawler.getPacks()) {
                    if (set.contains(packCrawlerPack.getTransferName()))
                        continue;


                    int index = 0;

                    while (true) {
                        TableCrawler tableCrawler = new TableCrawler("crawl", packCrawlerPack.getTransferName(), packCrawlerPack.getName(), String.valueOf(++index));
                        tableCrawler.start(1);
                        if (tableCrawler.pageInfo.getPageIndex() * tableCrawler.pageInfo.getPageSize() > tableCrawler.pageInfo.getTotalRows()) {
                            break;
                        }
                    }
                    FileUtil.writeTableToFile(group.getName() + "/" + pack.getName() + "/", TableCrawler.tableDetail);
                    TableCrawler.tableDetail = new TableDetail();
                    TableCrawler.pageInfo = new PageInfo();


                }

            }
        }

    }
}
