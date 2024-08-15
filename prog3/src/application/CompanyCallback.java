package application;

@FunctionalInterface
public interface CompanyCallback {
    void onCompanyAdded(company insertedComp);
}

