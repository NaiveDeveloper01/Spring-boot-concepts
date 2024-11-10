package data_migration.Batch.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "job")
public class JobController {

  @Autowired
  private JobLauncher jobLauncher;

  @Autowired
  private Job job;

  @GetMapping("/startJob")
  public String startJob() {
    try {
      JobParameters jobParameters = new JobParametersBuilder()
              .addLong("startAt", System.currentTimeMillis())
              .toJobParameters();
      jobLauncher.run(job, jobParameters);
      return "Job started";
    } catch (Exception e) {
      e.printStackTrace();
      return "Job failed to start";
    }
  }
}
