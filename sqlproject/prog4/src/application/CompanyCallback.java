package application;


public interface CompanyCallback {
    void onCompanyAdded(company insertedComp);
    void onCompanyUpdated(String name);
}

