package com.exbarkx.jobms.job.impl;
import com.exbarkx.jobms.job.Job;
import com.exbarkx.jobms.job.JobRepository;
import com.exbarkx.jobms.job.JobService;
import com.exbarkx.jobms.job.clients.CompanyClient;
import com.exbarkx.jobms.job.clients.ReviewClient;
import com.exbarkx.jobms.job.dto.JobDTO;
import com.exbarkx.jobms.job.external.Company;
import com.exbarkx.jobms.job.external.Review;
import com.exbarkx.jobms.job.mapper.JobMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {
//    private List<Job> jobs = new ArrayList<Job>();
    JobRepository jobRepository;
    int attempt=0;
//    private Long nextId = 1l;
    private ReviewClient reviewClient;
    private CompanyClient companyClient;
    @Autowired
    RestTemplate restTemplate;
    public JobServiceImpl(JobRepository jobRepository,CompanyClient companyClient,ReviewClient reviewClient) {
        this.companyClient = companyClient;
        this.jobRepository = jobRepository;
        this.reviewClient = reviewClient;
    }

    @Override
//    @CircuitBreaker(name="companyBreaker",fallbackMethod = "companyBreakerFallback")
    @Retry(name="companyBreaker",fallbackMethod = "companyBreakerFallback")
    public List<JobDTO> findAll() {
        System.out.println("Attempt"+ ++attempt);
        List<Job> jobs = jobRepository.findAll();

        List<JobDTO> jobDTOS = new ArrayList<>();
        return jobs.stream().map(this::convertToDto).collect(Collectors.toList());
    }
    public List<String> companyBreakerFallback(Exception e){
        List<String> list = new ArrayList<>();
        list.add("Dummy");
        return list;
    }
    private JobDTO convertToDto(Job job){
    //            RestTemplate restTemplate = new RestTemplate();
//                Company company = restTemplate.getForObject("http://COMPANYMS:8082/companies/"+job.getCompanyId(), Company.class);
        Company company = companyClient.getCompany(job.getCompanyId());
//                ResponseEntity<List<Review>> reviewResponse =restTemplate.exchange("http://REVIEWMS:8083/reviews?companyId=" + job.getCompanyId(), HttpMethod.GET, null, new ParameterizedTypeReference<List<Review>>() {
//                });
//                List<Review> reviews = reviewResponse.getBody();
        List<Review> reviews = reviewClient.getReviews(job.getCompanyId());
        JobDTO jobDTO = JobMapper.mapToJobWithCompanyDto(job,company,reviews);
                return jobDTO;
    }
    @Override
    public void createJob(Job job) {
//        job.setId(nextId++);
//        jobs.add(job);
        jobRepository.save(job);
    }

    @Override
    public JobDTO getJobById(Long id) {
//        for (Job job : jobs){
//            if (job.getId().equals(id)) {
//                return job;
//            }
//        }
//        return null;
        Job job =  jobRepository.findById(id).orElse(null);

        return convertToDto(job);

    }

    @Override
    public boolean deleteJobById(Long id) {
//        Iterator<Job> iterator = jobs.iterator();
//        while(iterator.hasNext()){
//            Job job = iterator.next();
//            if (job.getId().equals(id)){
//                iterator.remove();
//                return true;
//            }
//        }
//        return false;
        try {
            jobRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean updateJob(Long id, Job updatedJob) {
//        Iterator<Job> iterator = jobs.iterator();
//        while(iterator.hasNext()){
//            Job job = iterator.next();
//            if (job.getId().equals(id)){
//                job.setDescription(updatedJob.getDescription());
//                job.setLocation(updatedJob.getLocation());
//                job.setTitle(updatedJob.getTitle());
//                job.setMinSalary(updatedJob.getMinSalary());
//                job.setMaxSalary(updatedJob.getMaxSalary());
//                return true;
//            }
//        }
//        return false;
        Optional<Job> jobOptional = jobRepository.findById(id);
        if (jobOptional.isPresent()){
            Job job = jobOptional.get();
            job.setDescription(updatedJob.getDescription());
            job.setLocation(updatedJob.getLocation());
            job.setTitle(updatedJob.getTitle());
            job.setMinSalary(updatedJob.getMinSalary());
            job.setMaxSalary(updatedJob.getMaxSalary());
            jobRepository.save(job);
            return true;
        }
        return false;
    }
}
