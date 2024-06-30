package carato.carato_backend.Models.QUARTZ;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "qrtz_triggers")
public class QuartzTriggers implements Serializable {

    @Id
    @Column(name = "sched_name", length = 120, nullable = false)
    private String schedName;

    @Id
    @Column(name = "trigger_name", length = 200, nullable = false)
    private String triggerName;

    @Id
    @Column(name = "trigger_group", length = 200, nullable = false)
    private String triggerGroup;

    @Column(name = "job_name", length = 200, nullable = false)
    private String jobName;

    @Column(name = "job_group", length = 200, nullable = false)
    private String jobGroup;

    @Column(name = "description", length = 250)
    private String description;

    @Column(name = "next_fire_time")
    private Long nextFireTime;

    @Column(name = "prev_fire_time")
    private Long prevFireTime;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "trigger_state", length = 16, nullable = false)
    private String triggerState;

    @Column(name = "trigger_type", length = 8, nullable = false)
    private String triggerType;

    @Column(name = "start_time", nullable = false)
    private Long startTime;

    @Column(name = "end_time")
    private Long endTime;

    @Column(name = "calendar_name", length = 200)
    private String calendarName;

    @Column(name = "misfire_instr")
    private Short misfireInstr;

    @Lob
    @Column(name = "job_data")
    private byte[] jobData;
}