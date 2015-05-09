package dfzq.model;

public class ConflictResult {
    
    public String getFundName() {
		return fundName;
	}
	public void setFundName(String fundName) {
		this.fundName = fundName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public int getConflictStatusCode() {
		return conflictStatusCode;
	}
	public void setConflictStatusCode(int conflictStatusCode) {
		this.conflictStatusCode = conflictStatusCode;
	}
	
	public int getFund_id() {
		return fund_id;
	}
	public void setFund_id(int fund_id) {
		this.fund_id = fund_id;
	}
	public int getCompany_id() {
		return company_id;
	}
	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}



	private String fundName;
	private String companyName;
    private int conflictStatusCode;
    private int fund_id;
    private int company_id;
    
}
