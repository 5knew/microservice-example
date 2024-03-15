package com.embarkx.companyms.company;

import com.embarkx.companyms.company.dto.ReviewMessage;

import java.util.List;

public interface CompanyService {
    List<Company> getAllCompanies();
    Boolean updateCompanies(Company company, Long id);

    void createCompany(Company company);

    boolean deleteCompanyById(Long id);
    Company getCompanyById(Long id);

    void updateCompanyRating(ReviewMessage reviewMessage);


}
