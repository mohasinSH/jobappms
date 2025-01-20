package com.embarkx.companyms.company;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }
    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies(){
        return ResponseEntity.ok(companyService.getAllCompanies());
    }
    @PostMapping
    public ResponseEntity<String>addCompany(@RequestBody Company company){
        companyService.createCompany(company);
        return new ResponseEntity<>("Company Added SucessFully",HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<String>updateCompany(@PathVariable Long id , @RequestBody Company company ){
        boolean update = companyService.updateCompany(company,id);
        if (update){
            return  ResponseEntity.ok("Company Updated SucessFully");
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long id){
        boolean delete =companyService.deleteCompany(id);
        if (delete){
            return ResponseEntity.ok("Company Deleted Sucessfully");
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id) {
        Company company = companyService.getCompanyById(id);
        if (company == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return  ResponseEntity.ok(company);
    }
}
