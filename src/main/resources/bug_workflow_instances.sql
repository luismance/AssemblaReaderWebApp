/* BUG WORKFLOW
	STEP 1 : NEW UNNASSIGNED
		|-STEP 2 : NEW ASSIGNED
			|- STEP 3 : ACCEPTED
				|- STEP 4 : TEST
					|- STEP 5 : REVIEW
						|- STEP 6 : FIXED
						|- STEP 6 : NEW ASSIGNED(RE-OPENED)
				|- STEP 4 : BLOCKED
					|- STEP 5 : INVALID
/*BUG========BUG========BUG========BUG========BUG========BUG========BUG========BUG*/
/*STEP 1 NEW UNASSIGNED TO NEW ASSIGNED */
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR1', 'Error 1',
'(old_status=="new" && old_assigned_to_id="" ) && 
(new_assigned_to=="new" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 60) &&
 ticket_priority == Highest', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR2', 'Error 2',
'(old_status=="new" && old_assigned_to_id="" ) && 
(new_assigned_to=="new" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 1440) &&
 ticket_priority == High', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR3', 'Error 3',
'(old_status=="new" && old_assigned_to_id="" ) && 
(new_assigned_to=="new" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 10080) &&
 ticket_priority == Normal', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR4', 'Error 4',
'(old_status=="new" && old_assigned_to_id="" ) && 
(new_assigned_to=="new" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 20160) &&
 ticket_priority == Low', 1);
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR5', 'Error 5',
'(old_status=="new" && old_assigned_to_id="" ) && 
(new_assigned_to=="new" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 43200) &&
 ticket_priority == Lowest', 1);
 /*STEP 1 NEW UNASSIGNED TO NEW ASSIGNED */
 
 /*STEP 2 NEW ASSIGNED TO ACCEPTED */
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR6', 'Error 6',
'(old_status=="new" && old_assigned_to_id!="" ) && 
(new_assigned_to=="accepted" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 60) &&
 ticket_priority == Highest', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR7', 'Error 7',
'(old_status=="new" && old_assigned_to_id="" ) && 
(new_assigned_to=="new" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 1440) &&
 ticket_priority == High', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR8', 'Error 8',
'(old_status=="new" && old_assigned_to_id!="" ) && 
(new_assigned_to=="accepted" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 1440) &&
 ticket_priority == Normal', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR9', 'Error 9',
'(old_status=="new" && old_assigned_to_id!="" ) && 
(new_assigned_to=="accepted" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 1440) &&
 ticket_priority == Low', 1);
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR10', 'Error 10',
'(old_status=="new" && old_assigned_to_id!="" ) && 
(new_assigned_to=="accepted" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 1440) &&
 ticket_priority == Lowest', 1);
 /*STEP 2 NEW ASSIGNED TO ACCEPTED */
 
 /*STEP 2 NEW ASSIGNED TO BLOCKED */
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR11', 'Error 11',
'(old_status=="new" && old_assigned_to_id!="" ) && 
(new_assigned_to=="blocked" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 60) &&
 ticket_priority == Highest', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR12', 'Error 12',
'(old_status=="new" && old_assigned_to_id="" ) && 
(new_assigned_to=="blocked" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 1440) &&
 ticket_priority == High', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR13', 'Error 13',
'(old_status=="new" && old_assigned_to_id!="" ) && 
(new_assigned_to=="blocked" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 1440) &&
 ticket_priority == Normal', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR14', 'Error 14',
'(old_status=="new" && old_assigned_to_id!="" ) && 
(new_assigned_to=="blocked" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 1440) &&
 ticket_priority == Low', 1);
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR15', 'Error 15',
'(old_status=="new" && old_assigned_to_id!="" ) && 
(new_assigned_to=="blocked" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 1440) &&
 ticket_priority == Lowest', 1);
 /*STEP 2 NEW ASSIGNED TO BLOCKED */
 
 /*STEP 3 ACCEPTED TO TEST */
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR16', 'Error 16',
'(old_status=="accepted" && old_assigned_to_id!="" ) && 
(new_assigned_to=="test" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 60) &&
 ticket_priority == Highest', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR17', 'Error 17',
'(old_status=="accepted" && old_assigned_to_id!="" ) && 
(new_assigned_to=="test" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 10080) &&
 ticket_priority == High', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR18', 'Error 18',
'(old_status=="accepted" && old_assigned_to_id!="" ) && 
(new_assigned_to=="test" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 43200) &&
 ticket_priority == Normal', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR19', 'Error 19',
'(old_status=="accepted" && old_assigned_to_id!="" ) && 
(new_assigned_to=="test" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 43200) &&
 ticket_priority == Low', 1);
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR20', 'Error 20',
'(old_status=="accepted" && old_assigned_to_id!="" ) && 
(new_assigned_to=="test" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 86400) &&
 ticket_priority == Lowest', 1);
 
 /*STEP 3 ACCEPTED TO TEST */
 
 /*STEP 3 ACCEPTED TO BLOCKED */
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR21', 'Error 21',
'(old_status=="accepted" && old_assigned_to_id!="" ) && 
(new_assigned_to=="blocked" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 60) &&
 ticket_priority == Highest', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR22', 'Error 22',
'(old_status=="accepted" && old_assigned_to_id!="" ) && 
(new_assigned_to=="blocked" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 1440) &&
 ticket_priority == High', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR23', 'Error 23',
'(old_status=="accepted" && old_assigned_to_id!="" ) && 
(new_assigned_to=="blocked" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 10080) &&
 ticket_priority == Normal', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR24', 'Error 24',
'(old_status=="accepted" && old_assigned_to_id!="" ) && 
(new_assigned_to=="blocked" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 43200) &&
 ticket_priority == Low', 1);
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR25', 'Error 25',
'(old_status=="accepted" && old_assigned_to_id!="" ) && 
(new_assigned_to=="blocked" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 86400) &&
 ticket_priority == Lowest', 1);
 
 /*STEP 3 ACCEPTED TO BLOCKED */
 
 /*STEP 4 TEST TO REVIEW */
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR26', 'Error 26',
'(old_status=="test" && old_assigned_to_id!="" ) && 
(new_assigned_to=="review" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 60) &&
 ticket_priority == Highest', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR27', 'Error 27',
'(old_status=="test" && old_assigned_to_id!="" ) && 
(new_assigned_to=="review" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 1440) &&
 ticket_priority == High', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR28', 'Error 28',
'(old_status=="test" && old_assigned_to_id!="" ) && 
(new_assigned_to=="review" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 10080) &&
 ticket_priority == Normal', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR29', 'Error 29',
'(old_status=="test" && old_assigned_to_id!="" ) && 
(new_assigned_to=="review" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 20160) &&
 ticket_priority == Low', 1);
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR30', 'Error 30',
'(old_status=="test" && old_assigned_to_id!="" ) && 
(new_assigned_to=="review" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 20160) &&
 ticket_priority == Lowest', 1);
 
 /*STEP 4 TEST TO REVIEW */
 
 /*STEP 4 TEST TO ACCEPTED/RE-OPENED */
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR31', 'Error 31',
'(old_status=="test" && old_assigned_to_id!="" ) && 
(new_assigned_to=="accepted" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 60) &&
 ticket_priority == Highest', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR32', 'Error 32',
'(old_status=="test" && old_assigned_to_id!="" ) && 
(new_assigned_to=="accepted" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 1440) &&
 ticket_priority == High', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR33', 'Error 33',
'(old_status=="test" && old_assigned_to_id!="" ) && 
(new_assigned_to=="accepted" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 10080) &&
 ticket_priority == Normal', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR34', 'Error 34',
'(old_status=="test" && old_assigned_to_id!="" ) && 
(new_assigned_to=="accepted" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 20160) &&
 ticket_priority == Low', 1);
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR36', 'Error 35',
'(old_status=="test" && old_assigned_to_id!="" ) && 
(new_assigned_to=="accepted" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 20160) &&
 ticket_priority == Lowest', 1);
 
 /*STEP 4 TEST TO ACCEPTED/RE-OPENED */
 
 /*STEP 5 REVIEW TO FIX */
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR37', 'Error 37',
'(old_status=="review" && old_assigned_to_id!="" ) && 
(new_assigned_to=="fixed" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 60) &&
 ticket_priority == Highest', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR38', 'Error 38',
'(old_status=="review" && old_assigned_to_id!="" ) && 
(new_assigned_to=="fixed" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 1440) &&
 ticket_priority == High', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR39', 'Error 39',
'(old_status=="review" && old_assigned_to_id!="" ) && 
(new_assigned_to=="fixed" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 10080) &&
 ticket_priority == Normal', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR40', 'Error 40',
'(old_status=="review" && old_assigned_to_id!="" ) && 
(new_assigned_to=="fixed" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 20160) &&
 ticket_priority == Low', 1);
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR41', 'Error 41',
'(old_status=="review" && old_assigned_to_id!="" ) && 
(new_assigned_to=="fixed" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 20160) &&
 ticket_priority == Lowest', 1);
 
 /*STEP 5 REVIEW TO FIX */
 
 /*STEP 4 BLOCKED TO INVALID */
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR42', 'Error 42',
'(old_status=="blocked" && old_assigned_to_id!="" ) && 
(new_assigned_to=="invalid" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 60) &&
 ticket_priority == Highest', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR43', 'Error 43',
'(old_status=="blocked" && old_assigned_to_id!="" ) && 
(new_assigned_to=="invalid" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 1440) &&
 ticket_priority == High', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR44', 'Error 44',
'(old_status=="blocked" && old_assigned_to_id!="" ) && 
(new_assigned_to=="invalid" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 10080) &&
 ticket_priority == Normal', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR45', 'Error 45',
'(old_status=="blocked" && old_assigned_to_id!="" ) && 
(new_assigned_to=="invalid" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 20160) &&
 ticket_priority == Low', 1);
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR46', 'Error 46',
'(old_status=="blocked" && old_assigned_to_id!="" ) && 
(new_assigned_to=="invalid" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 20160) &&
 ticket_priority == Lowest', 1);
 
 /*STEP 4 BLOCKED TO INVALID */

/*BUG========BUG========BUG========BUG========BUG========BUG========BUG========BUG*/