package dfzq.model;

public class CompanyChangeRow extends Company {

	private int _uid;
	private int _index;
	private String _state;
	private boolean conflict;
	
	public CompanyChangeRow() {
		
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
