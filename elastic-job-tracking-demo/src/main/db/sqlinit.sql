create table job_execution_log
(
	id varchar(40) not null
		primary key,
	job_name varchar(100) not null,
	task_id varchar(255) not null,
	hostname varchar(255) not null,
	ip varchar(50) not null,
	sharding_item int not null,
	execution_source varchar(20) not null,
	failure_cause varchar(4000) null,
	is_success int not null,
	start_time timestamp null,
	complete_time timestamp null
)
;

create table job_status_trace_log
(
	id varchar(40) not null
		primary key,
	job_name varchar(100) not null,
	original_task_id varchar(255) not null,
	task_id varchar(255) not null,
	slave_id varchar(50) not null,
	source varchar(50) not null,
	execution_type varchar(20) not null,
	sharding_item varchar(100) not null,
	state varchar(20) not null,
	message varchar(4000) null,
	creation_time timestamp null
)
;

create index task_id_state_index
	on job_status_trace_log (task_id, state)
;
