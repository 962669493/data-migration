SELECT
	Id id,
	TaskId task_id,
	TaskName task_name,
	[Type] "type",
	DoctorType doctor_type,
	Specified is_assigned,
	MemberId assigned_id,
	DoctorTag doctor_tag,
	Score,
	ReplyNo reply_no,
	CreateOn create_on,
	CreateName create_name,
	UpdateTime update_time,
	0 is_deleted
FROM
	TopicTaskConfig;