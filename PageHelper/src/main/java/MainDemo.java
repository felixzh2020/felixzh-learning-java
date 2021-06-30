import java.util.ArrayList;
import java.util.List;

/**
 * 自定义分页器测试类
 *
 * @author felixzh
 */
public class MainDemo {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 20; ++i) {
            list.add(i);
        }
        PageHelper<Integer> p = new PageHelper(list, 2, 7);

        System.out.println("第" + p.getThisPage() + "页");
        for (Integer integer : p.getPageRecordData()) {
            System.out.println(integer);
        }
    }
}

