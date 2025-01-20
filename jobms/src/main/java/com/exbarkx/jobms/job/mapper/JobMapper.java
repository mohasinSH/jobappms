package com.exbarkx.jobms.job.mapper;

import com.exbarkx.jobms.job.Job;
import com.exbarkx.jobms.job.dto.JobDTO;
import com.exbarkx.jobms.job.external.Company;
import com.exbarkx.jobms.job.external.Review;

import java.util.List;

public class JobMapper {
    public static JobDTO mapToJobWithCompanyDto(
         Job job,
         Company company,
         List<Review> reviews
    ){
        JobDTO jobDTO = new JobDTO();
        jobDTO.setId(job.getId());
        jobDTO.setTitle(job.getTitle());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setMaxSalary(job.getMaxSalary());
        jobDTO.setMinSalary(job.getMinSalary());
        jobDTO.setLocation(job.getLocation());
        jobDTO.setCompany(company);
        jobDTO.setReview(reviews);
        return jobDTO;
    }

}
