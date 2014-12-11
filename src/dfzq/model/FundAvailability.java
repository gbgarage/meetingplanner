package dfzq.model;

public class FundAvailability {

    private Fund fund;

    private int timeFrameId;
    
    public FundAvailability (Fund fund, int timeFrameId) {
    	this.fund = fund;
    	this.timeFrameId = timeFrameId;
    }

	public Fund getFund() {
		return fund;
	}

	public void setFund(Fund fund) {
		this.fund = fund;
	}

	public int getTimeFrameId() {
		return timeFrameId;
	}

	public void setTimeFrameId(int timeFrameId) {
		this.timeFrameId = timeFrameId;
	}
    
    
}
