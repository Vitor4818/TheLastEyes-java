package com.thelasteyes.backend.Service;

import com.thelasteyes.backend.Dto.JobFilter;
import com.thelasteyes.backend.Dto.GetJobDto;
import com.thelasteyes.backend.Dto.PostJobDto;
import com.thelasteyes.backend.Dto.PutJobDto;
import com.thelasteyes.backend.Exceptions.ResourceNotFoundException;
import com.thelasteyes.backend.Model.Company;
import com.thelasteyes.backend.Model.Job;
import com.thelasteyes.backend.Repository.CompanyRepository;
import com.thelasteyes.backend.Repository.JobRepository;
import com.thelasteyes.backend.Specification.JobSpecification;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CompanyRepository companyRepository;

    // Retorna todos os empregos
    @Cacheable(value = "jobsList", key = "#filter.hashCode() + #page.pageNumber")
    public Page<GetJobDto> getAllJobs(Pageable page, JobFilter filter) {
        Specification<Job> spec = JobSpecification.withFilter(filter);
        return jobRepository.findAll(spec, page).map(GetJobDto::new);
    }

    // Retorna emprego por ID
    @Cacheable(value = "jobById", key = "#id")
    public GetJobDto getJobById(Long id) {
        return jobRepository.findById(id).map(GetJobDto::new)
                .orElseThrow(() -> new ResourceNotFoundException("Emprego com o id " + id + " não encontrado"));
    }

    // Cadastra um novo emprego
    @Transactional
    @CacheEvict(value = "jobsList", allEntries = true)
    public Job postJob(PostJobDto dto) {


        Company company = companyRepository.findById(dto.companyId())
                .orElseThrow(() -> new ResourceNotFoundException("Empresa com o id " + dto.companyId() + " não encontrada para vincular o emprego."));

        Job job = new Job();
        job.setCompany(company);
        job.setHireDate(dto.hireDate());
        job.setPosition(dto.position());
        job.setContractType(dto.contractType());
        job.setWeeklyHours(dto.weeklyHours());
        job.setWorkModel(dto.workModel());
        job.setJobSatisfactionScore(dto.jobSatisfactionScore());

        return jobRepository.save(job);
    }

    // Atualiza dados do emprego
    @Transactional
    @CacheEvict(value = "jobsList", allEntries = true)
    @CachePut(value = "jobById", key = "#id")
    public Job putJob(Long id, PutJobDto dto) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Emprego com o id " + id + " não encontrado"));

        // Se o DTO tem um novo ID de empresa, verifica e atualiza o relacionamento
        if (dto.companyId() != null && !dto.companyId().equals(job.getCompany().getId())) {
            Company newCompany = companyRepository.findById(dto.companyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Nova Empresa com o id " + dto.companyId() + " não encontrada."));
            job.setCompany(newCompany);
        }

        job.updateData(dto);

        return jobRepository.save(job);
    }

    // Deleta dados do emprego
    @Transactional
    @CacheEvict(value = {"jobsList", "jobById"}, allEntries = true)
    public void deleteJob(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Emprego com o id " + id + " não encontrado"));
        jobRepository.delete(job);
    }

}