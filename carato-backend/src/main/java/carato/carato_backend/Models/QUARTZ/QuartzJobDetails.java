package carato.carato_backend.Models.QUARTZ;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "qrtz_job_details")
public class QuartzJobDetails implements Serializable {

    @Id
    @Column(name = "sched_name", length = 120, nullable = false)
    private String schedName;

    @Id
    @Column(name = "job_name", length = 200, nullable = false)
    private String jobName;

    @Id
    @Column(name = "job_group", length = 200, nullable = false)
    private String jobGroup;

    @Column(name = "description", length = 250)
    private String description;

    @Column(name = "job_class_name", length = 250, nullable = false)
    private String jobClassName;

    @Column(name = "is_durable", nullable = false)
    private Boolean isDurable;

    @Column(name = "is_nonconcurrent", nullable = false)
    private Boolean isNonConcurrent;

    @Column(name = "is_update_data", nullable = false)
    private Boolean isUpdateData;

    @Column(name = "requests_recovery", nullable = false)
    private Boolean requestsRecovery;

    @Lob
    @Column(name = "job_data")
    private byte[] jobData;
}