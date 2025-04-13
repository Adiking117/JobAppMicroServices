package com.adi.jobms.job.service;


import com.adi.jobms.job.Response.JobCompanyDTO;
import com.adi.jobms.job.dao.JobDAO;
import com.adi.jobms.job.external.Company;
import com.adi.jobms.job.model.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    // private List<Job> jobs;

    private JobDAO jobDAO;

    @Autowired
    public  JobServiceImpl(JobDAO jobDAO){
        // this.jobs = new ArrayList<>();
        this.jobDAO = jobDAO;
    }

    @Override
    public List<JobCompanyDTO> getALlJobs() {
        // return jobs;

//        RestTemplate restTemplate = new RestTemplate();
//        Company company = restTemplate.getForObject(
//                "http://localhost:8081/companies/1",
//                Company.class
//        );

        List<Job> AllJobs = jobDAO.findAll();
        RestTemplate restTemplate = new RestTemplate();

//        List<JobCompanyDTO> JobsList = new ArrayList<>();
//        for(Job j : AllJobs){
//            Company company = restTemplate.getForObject(
//                    "http://localhost:8081/companies/"+j.getCompanyId(),
//                    Company.class
//            );
//            // System.out.println(company);
//            JobsList.add(new JobCompanyDTO(j,company));
//        }
//        return JobsList;
        // N Jobs call

        List<Long> CompanyIds = AllJobs.stream().map(Job::getCompanyId).toList();
        ResponseEntity<List<Company>> CompanyList = restTemplate.exchange(
                "http://localhost:8081/companies/batch?ids=" + String.join(",", CompanyIds.stream().map(String::valueOf).toArray(String[]::new)),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Company>>() {}
        );
        List<Company> companies = CompanyList.getBody();

        Map<Long,Company> CompanyMap = companies.stream().collect(Collectors.toMap(Company::getId,c->c));

        return AllJobs.stream().map(j-> new JobCompanyDTO(j,CompanyMap.get(j.getCompanyId()))).collect(Collectors.toList());
        // 1 Job called
    }

    @Override
    public Job createJob(Job job) {
//        jobs.add(job);
        jobDAO.save(job);
        return job;
    }

    @Override
    public Job getJobById(long id) {
//        for(Job j : jobs){
//            if(j.getId() == id){
//                return j;
//            }
//        }
//        return null;
        return jobDAO.findById(id).orElse(null);
    }

    @Override
    public Job updateJob(long id,Job updatedJob) {
//        for(Job j : jobs) {
//            if (j.getId() == id) {
//                j.setTitle(updatedJob.getTitle());
//                j.setDescription(updatedJob.getDescription());
//                j.setSalary(updatedJob.getSalary());
//                j.setLocation(updatedJob.getLocation());
//                return j;
//            }
//        }
        Optional<Job> jobToUpdate = jobDAO.findById(id);
        if(jobToUpdate.isPresent()){
            Job j = jobToUpdate.get();
            j.setTitle(updatedJob.getTitle());
            j.setDescription(updatedJob.getDescription());
            j.setSalary(updatedJob.getSalary());
            j.setLocation(updatedJob.getLocation());
            jobDAO.save(j);
            return j;
        }
        return null;
    }

    @Override
    public boolean deleteJobById(long id) {
//        for(Job j : jobs) {
//            if (j.getId() == id) {
//                jobs.remove(j);
//                return true;
//            }
//        }
//        return false;
        try {
            jobDAO.deleteById(id);
            return true;
        } catch (Exception e){
            return false;
        }
    }


}
