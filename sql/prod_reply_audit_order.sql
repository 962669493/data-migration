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
and t1.replyId not in (
    172267978,172268143,172268229,172268231,172268316,172268401,172269073,172269158,172269159
    ,172269243,172275897,172275981,172276065,172276300,172276652,172276778,172276820,172276821
    ,172276905,172276911,172276990,172276991,172277245,172277345,172277493,172277494,172277578
    ,172277662,172277773,172277830,172277831,172277915,172277937,172278034,172278085,172278086
    ,172278087,172278171,172278256,172278257,172278344,172278377,172278422,172278431,172278432
    ,172278435,172278519,172278520,172278522,172278524,172278608,172278610,172278697,172278781
    ,172278783
)
and t1.createOn <= '2021-01-19 10:30:00'