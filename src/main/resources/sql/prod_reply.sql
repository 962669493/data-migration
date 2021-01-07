SELECT
	t1.ReplyID id,
	t1.ReplyID reply_id,
	t1.tid topic_id,
	t1.ForumID forum_id,
	null sources,
	t1.IP ip,
	null order_id,
	null reply_no,
	null reply_standard_id,
	t1.MainBody reply_content,
	null score,
	null inner_copy_check_result,
	t2.sameRatio outer_copy_ratio,
	null audit_status,
	null is_manual_audit,
	null quality,
	t1.IsDoctor replier_type,
	case
		when t2.isRejected > 0 then 1
		else 0
	end reject_count,
	t1.QcStateID qc_state_id,
	t1.ForumTreeCode forum_tree_code,
	t1.ForumTreeValue forum_tree_value,
	t1.ForumIDV2 forum_id_v2,
	null remark,
	1 reply_version,
	t2.machineAuditStatus machine_audit_status,
	t1.MemberId replier_id,
	t1.NickName replier_name,
	t1.MemberId update_user,
	CONVERT(varchar(100), t1.CreateOn, 20 ) create_on,
	CONVERT(varchar(100), t1.CreateOn, 20 ) update_time
FROM
	IssuePost t1
left join ReplyAuditResult t2 on
	t1.ReplyID = t2.replyId
inner join AuthTopics t3 on
    t1.tid = t3.tid