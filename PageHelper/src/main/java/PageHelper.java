import java.util.ArrayList;
import java.util.List;

/**
 * 自定义分页器:从总数集中提取指定页的数据集
 *
 * @author felixzh
 */

public class PageHelper<T> {
    //总数据集
    private List<T> allRecordData;
    //当前页数据集
    private List<T> pageRecordData = new ArrayList<>();

    //总数据条数
    private int allRecordCount;
    //每页数据条数
    private int pageRecordCount;

    //总页数
    private int totalPageCount = 0;
    //当前页页码
    private int thisPage = 1;

    /**
     * @param allRecordData   总数据集
     * @param thisPage        当前页页码
     * @param pageRecordCount 每页数据条数
     */
    public PageHelper(List<T> allRecordData, int thisPage, int pageRecordCount) {
        this.allRecordData = allRecordData;
        this.allRecordCount = allRecordData.size();
        this.pageRecordCount = pageRecordCount;

        //计算总页数
        calculateTotalPageCount();
        //计算当前页页码
        calculateThisPage(thisPage);
        //计算当前页数据集
        calculatePageRecordData();
    }

    //计算总页数
    private void calculateTotalPageCount() {
        if (this.allRecordData == null || this.allRecordCount == 0) {
            return;
        }

        //整除页数
        int allRecordPageCountTmp = this.allRecordCount / this.pageRecordCount;
        //能否整除
        this.totalPageCount = this.allRecordCount % this.pageRecordCount == 0 ? allRecordPageCountTmp : ++allRecordPageCountTmp;
    }

    //计算当前页页码
    private void calculateThisPage(int Page) {
        if (Page <= 1) {
            Page = 1;
        } else if (Page >= this.totalPageCount) {
            Page = this.totalPageCount;
        }

        this.thisPage = Page;

    }

    //计算当前页数据集
    private void calculatePageRecordData() {
        //每页开始索引
        int indexStart = (this.thisPage - 1) * this.pageRecordCount;
        //每页结束索引
        int indexEndTmp = this.thisPage * this.pageRecordCount;
        int indexEnd = indexEndTmp <= this.allRecordCount ? indexEndTmp : this.allRecordCount;

        this.pageRecordData.clear();
        for (int i = indexStart; i < indexEnd; ++i) {
            this.pageRecordData.add(this.allRecordData.get(i));
        }
    }

    public List<T> getPageRecordData() {
        return pageRecordData;
    }

    public int getThisPage() {
        return thisPage;
    }
}
