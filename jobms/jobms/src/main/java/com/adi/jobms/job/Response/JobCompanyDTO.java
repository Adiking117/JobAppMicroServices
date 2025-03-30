package com.adi.jobms.job.Response;

import com.adi.jobms.job.external.Company;
import com.adi.jobms.job.model.Job;

public class JobCompanyDTO {
    private Job job;
    private Company company;

    public JobCompanyDTO(Job job, Company company) {
        this.job = job;
        this.company = company;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "JobCompanyDTO{" +
                "job=" + job +
                ", company=" + company +
                '}';
    }
}
