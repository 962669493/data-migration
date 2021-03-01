SELECT
	t1.ReplyID id,
	t1.ReplyID reply_id,
	t1.tid topic_id,
	t1.ForumID forum_id,
	null sources,
	t1.IP ip,
	null order_id,
	t1.VoteNum reply_no,
	null reply_standard_id,
	t1.MainBody reply_content,
	null score,
	null inner_copy_check_result,
	null outer_copy_ratio,
	null audit_status,
	null is_manual_audit,
	null quality,
	t1.IsDoctor replier_type,
	0 reject_count,
	t1.QcStateID qc_state_id,
	t1.ForumTreeCode page_forum_tree_code,
	t1.ForumTreeValue page_forum_tree_value,
	t1.ForumIDV2 page_forum,
	null remark,
	1 reply_version,
	null machine_audit_status,
	t1.MemberId replier_id,
	t1.NickName replier_name,
	t1.MemberId update_user,
	CONVERT(varchar(100), t1.CreateOn, 20 ) create_on,
	CONVERT(varchar(100), t1.CreateOn, 20 ) update_time,
	0 is_delete
FROM
	IssuePost t1
where t1.IsTopic != 1 and t1.VoteNum != 0
and t1.ReplyID not in (
    172267978,172268143,172268229,172268231,172268316,172268401,172269073,172269158,172269159
    ,172269243,172275897,172275981,172276065,172276300,172276652,172276778,172276820,172276821
    ,172276905,172276911,172276990,172276991,172277245,172277345,172277493,172277494,172277578
    ,172277662,172277773,172277830,172277831,172277915,172277937,172278034,172278085,172278086
    ,172278087,172278171,172278256,172278257,172278344,172278377,172278422,172278431,172278432
    ,172278435,172278519,172278520,172278522,172278524,172278608,172278610,172278697,172278781
    ,172278783
)
-- and t1.CreateOn <= '2021-01-19 10:30:00'
