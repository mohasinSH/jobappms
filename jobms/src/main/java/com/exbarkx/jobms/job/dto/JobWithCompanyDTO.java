package com.exbarkx.jobms.job.dto;

import com.exbarkx.jobms.job.Job;
import com.exbarkx.jobms.job.external.Company;

public class JobWithCompanyDTO {
    private Job job;
    private Company company;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
}
