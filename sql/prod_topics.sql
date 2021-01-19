SELECT
	t1.id,
	t1.tid,
	t1.pageForum page_forum,
	null page_forum_tree_code,
	null page_forum_name,
	t1.assignForum assign_forum,
	null assign_forum_tree_code,
	null assign_forum_name,
    t1.replyStatus reply_status,
	t1.auditStatus audit_status,
	t1.authStatus auth_status,
	t1.planId plan_id,
    t2.productionStandardsId production_standards_id,
	t1.termNum term_num,
	t1.query,
	t1.title,
    '' "desc",
    '' sex,
    '' age,
    t1.status,
	CONVERT(varchar(100), t1.createOn, 20 ) create_on,
	CONVERT(varchar(100), t1.updateTime, 20 ) update_time,
	'' title_hash,
	0 is_deleted,
	t1.feedbackStateId feedback_count,
	-- t1.zaotieStatus zaotie_status,
	-- t1.commitStatus commit_status,
	t1.taskId
FROM
	AuthTopics t1
left join ProductionPlan t2
on t1.planId = t2.id
where t1.createOn <= '2021-01-19 10:30:00'