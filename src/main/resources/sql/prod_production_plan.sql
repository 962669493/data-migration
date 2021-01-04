SELECT
	id,
	termNum term_num,
	demandPosition demand_position,
	topicsNum topics_num,
	'' prod_success,
	doneTime done_time,
	-- zaotieSchedule,
	answerSchedule reply_schedule,
	auditSchedule audit_schedule,
	authorizeSchedule authorize_schedule,
	funderId funder_id,
	funderName funder_name,
	createOn create_on,
	productionStandardsId production_standards_id,
	updateTime update_time,
	0 is_deleted
	-- taskId,
	-- uploadTaskId,
	-- needTask,
FROM
	ProductionPlan