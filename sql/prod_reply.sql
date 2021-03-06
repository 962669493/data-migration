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
	null reject_count,
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
where t1.IsTopic != 1 and t1.VoteNum != 0 and t1.CreateOn <= '2021-01-19 10:30:00'
