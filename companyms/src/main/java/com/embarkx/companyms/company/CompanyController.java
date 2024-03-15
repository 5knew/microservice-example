package com.embarkx.companyms.company;

import com.embarkx.companyms.company.messages.ReviewMessageConsumer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;



    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;

    }


    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {
        return new ResponseEntity<>(companyService.getAllCompanies(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCompany(@PathVariable Long id,
                                                @RequestBody Company company) {
        boolean update = companyService.updateCompanies(company, id);
        if (update) return new ResponseEntity<>("Company update successfully", HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<String> addCompany(@RequestBody Company company) {
        companyService.createCompany(company);
        return new ResponseEntity<>("Company created successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompanyById(@PathVariable Long id){
        var option = companyService.deleteCompanyById(id);
        if(option) return ResponseEntity.ok("Company deleted successfully");

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id){
        if(companyService.getCompanyById(id) != null) return ResponseEntity.ok(companyService.getCompanyById(id));

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }



}
