SELECT
	id,
	termNum term_num,
	demandPosition demand_position,
	topicsNum topics_num,
	1 prod_success,
	CONVERT(varchar(100), doneTime, 20 ) done_time,
	-- zaotieSchedule,
	answerSchedule reply_schedule,
	auditSchedule audit_schedule,
	authorizeSchedule authorize_schedule,
	funderId funder_id,
	funderName funder_name,
	CONVERT(varchar(100), createOn, 20 ) create_on,
	productionStandardsId production_standards_id,
	CONVERT(varchar(100), updateTime, 20 ) update_time,
	0 is_deleted
	-- taskId,
	-- uploadTaskId,
	-- needTask,
FROM
	ProductionPlan