package com.exbarkx.jobms.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/jobs")
public class JobController {
    private JobService jobService;
    @Autowired
    public JobController(JobService jobService){
        this.jobService = jobService;
    }
    @GetMapping
    public ResponseEntity<List<Job>> findAll(){
        return ResponseEntity.ok(jobService.findAll());
    }
    @PostMapping
    public ResponseEntity<String> createJob(@RequestBody Job job){
        jobService.createJob(job);
        return new ResponseEntity<>("Job added sucessfully",HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Long id){
        Job job = jobService.getJobById(id);
        if(job == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(job, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJobById(@PathVariable Long id){

        boolean delete = jobService.deleteJobById(id);
        if (delete)
            return ResponseEntity.ok("Job deleted Sucessfully");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
//    @PutMapping("/jobs/{id}")
    @RequestMapping(value = "/{id}" ,method = RequestMethod.PUT)
    public ResponseEntity<String> updateJob(@PathVariable Long id,@RequestBody Job updatedJob){

        boolean updated = jobService.updateJob(id,updatedJob);
        if (updated){
            return new ResponseEntity<>("Job Updated Sucessfully",HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
