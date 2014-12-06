package dfzq.model;

/**
 * Created with IntelliJ IDEA.
 * User: flai
 * Date: 14-12-4
 * Time: 上午9:45
 * To change this template use File | Settings | File Templates.
 */
public class Availability {

    private Company company;

    private int timeFrameId;

    public Availability() {
    }

    public Availability(Company company, int timeFrameId) {
        this.company = company;
        this.timeFrameId = timeFrameId;
    }


    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public int getTimeFrameId() {
        return timeFrameId;
    }

    public void setTimeFrameId(int timeFrameId) {
        this.timeFrameId = timeFrameId;
    }
}
