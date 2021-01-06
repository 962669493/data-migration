SELECT
	tid topic_id,
	null task_config_id,
	null task_id,
	null assigned_id,
	replyId reply_id,
	replyPId replier_id,
	null reply_no,
	null reply_standard_id,
	reauditState status,
	null "source",
	reauditLowType reject_types,
	reauditRemark remark,
	reauditUserid auditor_id,
	reauditUserName auditor_name,
	null quality,
	CONVERT(varchar(100), t1.updateTime, 20 ) update_time,
	CONVERT(varchar(100), t1.createOn, 20 ) create_time,
	0 is_batch
FROM
	ReplyAuditResult