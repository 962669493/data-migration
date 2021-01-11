SELECT
	Id id,
	TaskName task_name,
	Precedence precedence,
	State state,
	[Type] "type",
	TopicCondition topic_condition,
	TopicAmount topic_amount,
	CompleteAmount complete_amount,
	CompletionRate completion_rate,
	IsStart is_start,
	CONVERT(varchar(100), CreateOn, 20 ) create_on,
	CreateName create_name,
	CONVERT(varchar(100), UpdateTime, 20 ) update_time,
	UpdateName update_name,
	0 is_deleted
FROM
	TopicContentTask