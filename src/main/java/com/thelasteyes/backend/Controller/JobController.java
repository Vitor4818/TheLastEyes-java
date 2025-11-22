package com.thelasteyes.backend.Controller;

import com.thelasteyes.backend.Dto.JobFilter;
import com.thelasteyes.backend.Dto.GetJobDto;
import com.thelasteyes.backend.Dto.PostJobDto;
import com.thelasteyes.backend.Dto.PutJobDto;
import com.thelasteyes.backend.Model.Job;
import com.thelasteyes.backend.Service.JobService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    // Retorna todos os empregos
    @GetMapping
    public ResponseEntity<Page<GetJobDto>> getAllJobs(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable page,
            JobFilter filter) {

        return ResponseEntity.ok(jobService.getAllJobs(page, filter));
    }

    // Retorna emprego por id
    @GetMapping("/{id}")
    public ResponseEntity<GetJobDto> getJobById(@PathVariable Long id) {
        return ResponseEntity.ok(jobService.getJobById(id));
    }

    // Cadastra novo emprego
    @PostMapping
    public ResponseEntity<Void> createJob(@RequestBody @Valid PostJobDto dto) {
        Job savedJob = jobService.postJob(dto);
        return ResponseEntity.created(URI.create("/jobs/" + savedJob.getId())).build();
    }

    // Atualiza dados do emprego
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateJob(@PathVariable Long id, @Valid @RequestBody PutJobDto dto) {
        jobService.putJob(id, dto);
        return ResponseEntity.noContent().build();
    }

    // Deleta emprego
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }
}