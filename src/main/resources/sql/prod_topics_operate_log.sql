SELECT
	id,
	tid topic_id,
	replyId reply_id,
	CONVERT(varchar(100), createOn, 20 ) create_time,
	handlerId create_user,
	handlerName create_user_name,
	handleType operate_type,
	handleResult operate_result,
	logSource "source"
	-- reauditLowType
FROM
	AuthTopicsHandleLog