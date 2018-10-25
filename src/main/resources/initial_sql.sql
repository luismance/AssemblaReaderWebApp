INSERT INTO workflow(CREATED, UPDATED, NAME) VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'Bug');
INSERT INTO workflow(CREATED, UPDATED, NAME) VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'Support');
INSERT INTO workflow(CREATED, UPDATED, NAME) VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'Enhancement');
INSERT INTO workflow(CREATED, UPDATED, NAME) VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'Need');
INSERT INTO workflow(CREATED, UPDATED, NAME) VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'Regression Bug');

INSERT INTO `user` (`ID`, `CREATED`, `UPDATED`, `BEARER_TOKEN`, `EMAIL`, `EXTERNAL_REF_ID`, `NAME`, `PASSWORD`, `PHONE_NUM`, `REFRESH_TOKEN`, `USERNAME`) VALUES (1, '2017-11-29 16:46:51', '2017-11-29 16:46:51', 'e97f807952aa741dc08eb641920060c8', 'luis.mance@web-drone.fr', 'dJstESRMyr55hcacwqjQXA', 'luis.mance@web-drone.fr', '0D1EA4C256CD50A2A7CCBFD22B3D9959F6FD30BD840B9FF3C7C65EE4E21DF06D', '', '7b028a2c53b6e4e361c14a28078a92c0', 'qweqweqwe');

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
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 60) && ticket_priority == 1', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR2', 'Error 2',
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 1440) && ticket_priority == 2', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR3', 'Error 3',
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 10080) && ticket_priority == 3', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR4', 'Error 4',
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 20160) && ticket_priority == 4', 1);
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR5', 'Error 5',
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 43200) && ticket_priority == 5', 1);
 /*STEP 1 NEW UNASSIGNED TO NEW ASSIGNED */
/*BUG========BUG========BUG========BUG========BUG========BUG========BUG========BUG*/

/*SUPPORT========SUPPORT========SUPPORT========SUPPORT========SUPPORT========SUPPORT========SUPPORT========SUPPORT*/
/*STEP 1 NEW UNASSIGNED TO NEW ASSIGNED */
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR1', 'Error 1', 
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 60) && ticket_priority == 1', 2);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR2', 'Error 2',
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 1440) && ticket_priority == 2', 2);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR3', 'Error 3',
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 10080) && ticket_priority == 3', 2);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR4', 'Error 4',
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 20160) && ticket_priority == 4', 2);
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR5', 'Error 5',
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 43200) && ticket_priority == 5', 2);
 /*STEP 1 NEW UNASSIGNED TO NEW ASSIGNED */
/*SUPPORT========SUPPORT========SUPPORT========SUPPORT========SUPPORT========SUPPORT========SUPPORT========SUPPORT*/

/*ENHANCEMENT========ENHANCEMENT========ENHANCEMENT========ENHANCEMENT========ENHANCEMENT========ENHANCEMENT========ENHANCEMENT========ENHANCEMENT*/
/*STEP 1 NEW UNASSIGNED TO NEW ASSIGNED */
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR1', 'Error 1', 
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && webdrone_assemblawebdrone_assemblawebdrone_assemblawebdrone_assembla
((new_updated_at - ticket_created) < 60) && ticket_priority == 1', 3);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR2', 'Error 2',
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 1440) && ticket_priority == 2', 3);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR3', 'Error 3',
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 10080) && ticket_priority == 3', 3);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR4', 'Error 4',
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 20160) && ticket_priority == 4', 3);
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR5', 'Error 5',
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 43200) && ticket_priority == 5', 3);
 /*STEP 1 NEW UNASSIGNED TO NEW ASSIGNED */
/*ENHANCEMENT========ENHANCEMENT========ENHANCEMENT========ENHANCEMENT========ENHANCEMENT========ENHANCEMENT========ENHANCEMENT========ENHANCEMENT*/

/*NEED========NEED========NEED========NEED========NEED========NEED========NEED========NEED*/
/*STEP 1 NEW UNASSIGNED TO NEW ASSIGNED */
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR1', 'Error 1', 
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 60) && ticket_priority == 1', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR2', 'Error 2',
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 1440) && ticket_priority == 2', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR3', 'Error 3',
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 10080) && ticket_priority == 3', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR4', 'Error 4',
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 20160) && ticket_priority == 4', 4);
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR5', 'Error 5',
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 43200) && ticket_priority == 5', 4);
 /*STEP 1 NEW UNASSIGNED TO NEW ASSIGNED */
/*NEED========NEED========NEED========NEED========NEED========NEED========NEED========NEED*/

/*REGRESSION_BUG========REGRESSION_BUG========REGRESSION_BUG========REGRESSION_BUG========REGRESSION_BUG========REGRESSION_BUG========REGRESSION_BUG========REGRESSION_BUG*/
/*STEP 1 NEW UNASSIGNED TO NEW ASSIGNED */
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR1', 'Error 1', 
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 60) && ticket_priority == 1', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR2', 'Error 2',
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 1440) && ticket_priority == 2', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR3', 'Error 3',
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 10080) && ticket_priority == 3', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR4', 'Error 4',
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 20160) && ticket_priority == 4', 4);
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR5', 'Error 5',
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 43200) && ticket_priority == 5', 4);
 /*STEP 1 NEW UNASSIGNED TO NEW ASSIGNED */
/*REGRESSION_BUG========REGRESSION_BUG========REGRESSION_BUG========REGRESSION_BUG========REGRESSION_BUG========REGRESSION_BUG========REGRESSION_BUG========REGRESSION_BUG*/