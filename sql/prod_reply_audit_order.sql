SELECT
	t1.tid topic_id,
	null task_config_id,
	null task_id,
	null assigned_id,
	t1.replyId reply_id,
	t1.replyPId replier_id,
	null reply_no,
	null reply_standard_id,
	t1.reauditState status,
	null "source",
	t1.reauditLowType reject_types,
	t1.reauditRemark remark,
	t1.reauditUserid auditor_id,
	t1.reauditUserName auditor_name,
	null quality,
	CONVERT(varchar(100), t1.updateTime, 20 ) update_time,
	CONVERT(varchar(100), t1.createOn, 20 ) create_time,
	0 is_batch
FROM
	ReplyAuditResult t1
where EXISTS (select 1 from AuthTopics t2 where t2.tid = t1.tid)
and t1.createOn <= '2021-01-19 10:30:00'