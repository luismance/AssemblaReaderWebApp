/*	NEEDS WORKFLOW
	STEP 1 : NEW UNASSIGNED
		|- STEP 2 : NEW ASSIGNED
			|- STEP 3 : ACCEPTED
				|- STEP 4 : REVIEW
					|- STEP 5 : FIXED
					|- STEP 5 : BLOCKED
						|- STEP 6 : NEW ASSIGNED
*/

/*NEED========NEED========NEED========NEED========NEED========NEED========NEED========NEED*/
/*NEW UNASSIGNED TO NEW ASSIGNED*/
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="new" && $old.assigned_to_id="" ) && 
($new.assigned_to=="accepted" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 60) &&
 $ticket.priority == Highest', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="new" && $old.assigned_to_id="" ) && 
($new.assigned_to=="accepted" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 1440) &&
 $ticket.priority == High', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="new" && $old.assigned_to_id="" ) && 
($new.assigned_to=="accepted" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 10080) &&
 $ticket.priority == Normal', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="new" && $old.assigned_to_id="" ) && 
($new.assigned_to=="accepted" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 20160) &&
 $ticket.priority == Low', 4);
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="new" && $old.assigned_to_id="" ) && 
($new.assigned_to=="accepted" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 43200) &&
 $ticket.priority == Lowest', 4);
 /*NEW UNASSIGNED TO NEW ASSIGNED*/
 
 /*NEW ASSIGNED TO NEW ACCEPTED*/
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="new" && $old.assigned_to_id="" ) && 
($new.assigned_to=="accepted" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 60) &&
 $ticket.priority == Highest', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="new" && $old.assigned_to_id="" ) && 
($new.assigned_to=="accepted" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 1440) &&
 $ticket.priority == High', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="new" && $old.assigned_to_id="" ) && 
($new.assigned_to=="accepted" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 10080) &&
 $ticket.priority == Normal', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="new" && $old.assigned_to_id="" ) && 
($new.assigned_to=="accepted" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 20160) &&
 $ticket.priority == Low', 4);
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="new" && $old.assigned_to_id="" ) && 
($new.assigned_to=="accepted" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 43200) &&
 $ticket.priority == Lowest', 4);
 /*NEW ASSIGNED TO NEW ACCEPTED*/
 
 /*NEW ACCEPTED TO NEW REVIEW*/
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="new" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="accepted" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 60) &&
 $ticket.priority == Highest', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="new" && $old.assigned_to_id="" ) && 
($new.assigned_to=="new" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 1440) &&
 $ticket.priority == High', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="new" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="accepted" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 1440) &&
 $ticket.priority == Normal', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="new" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="accepted" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 1440) &&
 $ticket.priority == Low', 4);
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="new" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="accepted" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 1440) &&
 $ticket.priority == Lowest', 4);
 /*NEW ASSIGNED TO NEW ACCEPTED*/
 
 /*STEP 3 ACCEPTED TO TEST */
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="accepted" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="test" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 60) &&
 $ticket.priority == Highest', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="accepted" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="test" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 10080) &&
 $ticket.priority == High', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="accepted" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="test" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 43200) &&
 $ticket.priority == Normal', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="accepted" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="test" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 43200) &&
 $ticket.priority == Low', 4);
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="accepted" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="test" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 86400) &&
 $ticket.priority == Lowest', 4);
 
 /*STEP 3 ACCEPTED TO TEST */
 
 /*STEP 3 ACCEPTED TO BLOCKED */
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="accepted" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="blocked" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 60) &&
 $ticket.priority == Highest', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="accepted" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="blocked" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 1440) &&
 $ticket.priority == High', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="accepted" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="blocked" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 10080) &&
 $ticket.priority == Normal', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="accepted" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="blocked" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 43200) &&
 $ticket.priority == Low', 4);
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="accepted" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="blocked" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 86400) &&
 $ticket.priority == Lowest', 4);
 
 /*STEP 3 ACCEPTED TO BLOCKED */
 
 /*STEP 4 TEST TO REVIEW */
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="test" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="review" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 60) &&
 $ticket.priority == Highest', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="test" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="review" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 1440) &&
 $ticket.priority == High', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="test" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="review" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 10080) &&
 $ticket.priority == Normal', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="test" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="review" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 20160) &&
 $ticket.priority == Low', 4);
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="test" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="review" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 20160) &&
 $ticket.priority == Lowest', 4);
 
 /*STEP 4 TEST TO REVIEW */
 
 /*STEP 5 REVIEW TO FIX */
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="review" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="fixed" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 60) &&
 $ticket.priority == Highest', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="review" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="fixed" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 1440) &&
 $ticket.priority == High', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="review" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="fixed" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 10080) &&
 $ticket.priority == Normal', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="review" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="fixed" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 20160) &&
 $ticket.priority == Low', 4);
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="review" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="fixed" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 20160) &&
 $ticket.priority == Lowest', 4);
 
 /*STEP 5 REVIEW TO FIX */
 
/*NEED========NEED========NEED========NEED========NEED========NEED========NEED========NEED*/