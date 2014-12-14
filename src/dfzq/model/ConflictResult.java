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
	private String fundName;
	private String companyName;
    private int conflictStatusCode;
}
