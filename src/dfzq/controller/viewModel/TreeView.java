package dfzq.controller.viewModel;

/**
 * Created with IntelliJ IDEA.
 * User: flai
 * Date: 14-6-9
 * Time: 下午2:22
 * To change this template use File | Settings | File Templates.
 */
public class TreeView {
    private int index;

    private String name;

    private int totalNum;

    private double averageTime;

    private int sotpIndex;

    private double radio;

    private Integer type;

    private int successCount;

    private int failCount;

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public int getFailCount() {
        return failCount;
    }

    public void setFailCount(int failCount) {
        this.failCount = failCount;
    }

    public TreeView(int index, String name, int totalNum, double averageTime, int sotpIndex,double radio,Integer type,int successCount, int failCount) {
        this.index = index;
        this.name = name;
        this.totalNum = totalNum;
        this.averageTime = averageTime;
        this.sotpIndex = sotpIndex;
        this.radio=radio;
        this.type= type;
        this.successCount=successCount;
        this.failCount=failCount;

    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public double getAverageTime() {
        return averageTime;
    }

    public void setAverageTime(double averageTime) {
        this.averageTime = averageTime;
    }

    public int getSotpIndex() {
        return sotpIndex;
    }

    public void setSotpIndex(int sotpIndex) {
        this.sotpIndex = sotpIndex;
    }

    public double getRadio() {
        return radio;
    }

    public void setRadio(double radio) {
        this.radio = radio;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public TreeView() {
    }
}
