SELECT
	Id id,
	TaskId task_id,
	TaskName task_name,
	[Type] "type",
	DoctorType doctor_type,
	Specified is_assigned,
	MemberId assigned_id,
	DoctorTag doctor_tag,
	Score score,
	ReplyNo reply_no,
	CONVERT(varchar(100), CreateOn, 20 ) create_on,
	CreateName create_name,
	CONVERT(varchar(100), UpdateTime, 20 ) update_time,
	0 is_deleted
FROM
	TopicTaskConfig
where CreateOn <= '2021-01-19 10:30:00'