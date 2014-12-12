package dfzq.model;

import java.util.List;

public class FundChangeRow extends Fund {
	
	private int _uid;
	private int _index;
	private String _state;
	private boolean conflict;
	
	public FundChangeRow() {
		
	}
	
	public boolean isConflict() {
		return conflict;
	}

	public void setConflict(boolean conflict) {
		this.conflict = conflict;
	}

	public int get_uid() {
		return _uid;
	}
	public void set_uid(int _uid) {
		this._uid = _uid;
	}
	public int get_index() {
		return _index;
	}
	public void set_index(int _index) {
		this._index = _index;
	}
	public String get_state() {
		return _state;
	}
	public void set_state(String _state) {
		this._state = _state;
	}
	
}


//
//
//public class FundChangeRow {
//
//    private Integer id;
//    private String contactor;
//    private String phoneNumber;
//    private String fundName;
//    private int fundAvailabilityCount;
//    private int priority;
//    private List<OneOnOneMeetingRequest> oneOnOneMeetingRequests;
//	private String conflict;
//	private int _uid;
//	private int _index;
//	private String _state;
//	
//	FundChangeRow(int id, String contactor, String phoneNumber, String fundName, int fundAvailabilityCount, int priority,
//			List<OneOnOneMeetingRequest> oneOnOneMeetingRequests, String conflict, int _uid, int _index, String _state) {
//		this.id = id;
//		this.contactor = contactor;
//		this.phoneNumber = phoneNumber;
//		this.fundName = fundName;
//		this.fundAvailabilityCount = fundAvailabilityCount;
//		this.priority = priority;
//		this.oneOnOneMeetingRequests = oneOnOneMeetingRequests;
//		this.conflict = conflict;
//		this._uid = _uid;
//		this._index = _index;
//		this._state = _state;
//	}
//
//	public Integer getId() {
//		return id;
//	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}
//
//	public String getContactor() {
//		return contactor;
//	}
//
//	public void setContactor(String contactor) {
//		this.contactor = contactor;
//	}
//
//	public String getPhoneNumber() {
//		return phoneNumber;
//	}
//
//	public void setPhoneNumber(String phoneNumber) {
//		this.phoneNumber = phoneNumber;
//	}
//
//	public String getFundName() {
//		return fundName;
//	}
//
//	public void setFundName(String fundName) {
//		this.fundName = fundName;
//	}
//
//	public int getFundAvailabilityCount() {
//		return fundAvailabilityCount;
//	}
//
//	public void setFundAvailabilityCount(int fundAvailabilityCount) {
//		this.fundAvailabilityCount = fundAvailabilityCount;
//	}
//
//	public int getPriority() {
//		return priority;
//	}
//
//	public void setPriority(int priority) {
//		this.priority = priority;
//	}
//
//	public List<OneOnOneMeetingRequest> getOneOnOneMeetingRequests() {
//		return oneOnOneMeetingRequests;
//	}
//
//	public void setOneOnOneMeetingRequests(
//			List<OneOnOneMeetingRequest> oneOnOneMeetingRequests) {
//		this.oneOnOneMeetingRequests = oneOnOneMeetingRequests;
//	}
//
//	public String getConflict() {
//		return conflict;
//	}
//
//	public void setConflict(String conflict) {
//		this.conflict = conflict;
//	}
//
//	public int get_uid() {
//		return _uid;
//	}
//
//	public void set_uid(int _uid) {
//		this._uid = _uid;
//	}
//
//	public int get_index() {
//		return _index;
//	}
//
//	public void set_index(int _index) {
//		this._index = _index;
//	}
//
//	public String get_state() {
//		return _state;
//	}
//
//	public void set_state(String _state) {
//		this._state = _state;
//	}
//	
//}
