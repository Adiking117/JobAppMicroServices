package com.adi.jobms.job.service;


import com.adi.jobms.job.Response.JobCompanyDTO;
import com.adi.jobms.job.model.Job;

import java.util.List;

public interface JobService {
    List<JobCompanyDTO> getALlJobs();
    Job createJob(Job job);
    Job getJobById(long id);
    Job updateJob(long id,Job updatedJob);
    boolean deleteJobById(long id);
}
