package dfzq.model;

import java.util.List;

/* This class is used to provide detail info of a meeting ID with frontend required format*/

public class MeetingDetail {
	//type of the meeting
	private String type;
	//company name + contact name
	private String companyStr;
	//fund name + contact name, multiple str if multiple fund
	private String fundStr;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCompanyStr() {
		return companyStr;
	}
	public void setCompanyStr(String companyStr) {
		this.companyStr = companyStr;
	}
	public String getFundStr() {
		return fundStr;
	}
	public void setFundStr(String fundStr) {
		this.fundStr = fundStr;
	}
}
