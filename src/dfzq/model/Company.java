package dfzq.model;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: flai
 * Date: 14-11-27
 * Time: 下午1:57
 * To change this template use File | Settings | File Templates.
 */
public class Company {

    private Integer id;

    private String name;

    private String contact;

    private int availableMeetingCount;

    private Set<OneOnOneMeetingRequest> oneOnOneMeetingRequestList = new TreeSet<OneOnOneMeetingRequest>();


    public Company() {
    }

    public Company(String name, String contact) {

        this.name = name;
        this.contact = contact;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAvailableMeetingCount() {
        return availableMeetingCount;
    }

    public void setAvailableMeetingCount(int availableMeetingCount) {
        this.availableMeetingCount = availableMeetingCount;
    }

    public Set<OneOnOneMeetingRequest> getOneOnOneMeetingRequestList() {
        return oneOnOneMeetingRequestList;
    }

    public void addOneOnOneMeetingRequest(OneOnOneMeetingRequest request) {
        oneOnOneMeetingRequestList.add(request);
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", oneOnOneMeetingRequestList=" + oneOnOneMeetingRequestList +
                '}';
    }


    public boolean isConflict() {


        return caculateFundCompany(oneOnOneMeetingRequestList) > availableMeetingCount;
    }

    private int caculateFundCompany(Set<OneOnOneMeetingRequest> oneOnOneMeetingRequestList) {
        Set<String> fundCompany = new HashSet<String>();
        for(OneOnOneMeetingRequest request : oneOnOneMeetingRequestList){
            fundCompany.add(request.getFund().getFundName());

        }

        return fundCompany.size();


    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company)) return false;

        Company company = (Company) o;

        if (id != null ? !id.equals(company.id) : company.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
