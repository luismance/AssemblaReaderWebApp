INSERT INTO workflow(CREATED, UPDATED, NAME) VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'Bug');
INSERT INTO workflow(CREATED, UPDATED, NAME) VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'Support');
INSERT INTO workflow(CREATED, UPDATED, NAME) VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'Enhancement');
INSERT INTO workflow(CREATED, UPDATED, NAME) VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'Need');
INSERT INTO workflow(CREATED, UPDATED, NAME) VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'Regression Bug');

INSERT INTO `user` (`ID`, `CREATED`, `UPDATED`, `BEARER_TOKEN`, `EMAIL`, `EXTERNAL_REF_ID`, `NAME`, `PASSWORD`, `PHONE_NUM`, `REFRESH_TOKEN`, `SYNC_STATUS`, `USERNAME`) VALUES (1, '2018-11-06 10:24:08', '2018-11-06 10:24:08', 'ca5c15f44c3df7c2087892104d410c77', 'luis.mance@web-drone.fr', 'dJstESRMyr55hcacwqjQXA', 'Luis Mance', '0D1EA4C256CD50A2A7CCBFD22B3D9959F6FD30BD840B9FF3C7C65EE4E21DF06D', '', '40f82d4b96824bc4f30ca4342ecc53d0', 'Ready to start', 'luis.mance@web-drone.fr');


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
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR1', 'Bug should be assigned after 1 hour of creation', 
'(old_status == "new" && old_assigned_to_id == "") && 
(new_status == "new" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 60) && ticket_priority == 1', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR2', 'Bug should be assigned after 24 hours of creation',
'(old_status == "new" && old_assigned_to_id == "") && 
(new_status == "new" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 1440) && ticket_priority == 2', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR3', 'Bug should be assigned after 1 week of creation',
'(old_status == "new" && old_assigned_to_id == "") && 
(new_status == "new" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 10080) && ticket_priority == 3', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR4', 'Bug should be assigned after 2 weeks of creation',
'(old_status == "new" && old_assigned_to_id == "") && 
(new_status == "new" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 20160) && ticket_priority == 4', 1);
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR5', 'Bug should be assigned after 1 month of creation',
'(old_status == "new" && old_assigned_to_id == "") && 
(new_status == "new" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 43200) && ticket_priority == 5', 1);
 /*STEP 1 NEW UNASSIGNED TO NEW ASSIGNED */
 
  /*STEP 2 NEW ASSIGNED TO ACCEPTED */
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR6', 'Bug should be accepted after 1 hour of assignment', 
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 60) && ticket_priority == 1', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR7', 'Bug should be accepted after 24 hours of assignment',
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 1440) && ticket_priority == 2', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR8', 'Bug should be accepted after 1 week of assignment',
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 10080) && ticket_priority == 3', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR9', 'Bug should be accepted after 2 weeks of assignment',
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 20160) && ticket_priority == 4', 1);
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR10', 'Bug should be accepted after 1 month of assignment',
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 43200) && ticket_priority == 5', 1);
 /*STEP 2 NEW ASSIGNED TO ACCEPTED */
 
  /*STEP 2 NEW ASSIGNED TO BLOCKED */
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR11', 'Bug should be set to blocked after 1 hour of checking',
'(old_status=="new" && old_assigned_to_id!="" ) && 
(new_assigned_to=="blocked" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 60) &&
 ticket_priority == Highest', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR12', 'Bug should be set to blocked after 24 hours of checking',
'(old_status=="new" && old_assigned_to_id="" ) && 
(new_assigned_to=="blocked" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 1440) &&
 ticket_priority == High', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR13', 'Bug should be set to blocked after 24 hours of checking',
'(old_status=="new" && old_assigned_to_id!="" ) && 
(new_assigned_to=="blocked" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 1440) &&
 ticket_priority == Normal', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR14', 'Bug should be set to blocked after 24 hours of checking',
'(old_status=="new" && old_assigned_to_id!="" ) && 
(new_assigned_to=="blocked" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 1440) &&
 ticket_priority == Low', 1);
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR15', 'Bug should be set to blocked after 24 hours of checking',
'(old_status=="new" && old_assigned_to_id!="" ) && 
(new_assigned_to=="blocked" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 1440) &&
 ticket_priority == Lowest', 1);
 /*STEP 2 NEW ASSIGNED TO BLOCKED */
 
  /*STEP 3 ACCEPTED TO TEST */
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR16', 'Bug should be set to test after 1 hour of debugging',
'(old_status=="accepted" && old_assigned_to_id!="" ) && 
(new_assigned_to=="test" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 60) &&
 ticket_priority == Highest', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR17', 'Bug should be set to test after 1 week of debugging',
'(old_status=="accepted" && old_assigned_to_id!="" ) && 
(new_assigned_to=="test" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 10080) &&
 ticket_priority == High', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR18', 'Bug should be set to test after 1 month of debugging',
'(old_status=="accepted" && old_assigned_to_id!="" ) && 
(new_assigned_to=="test" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 43200) &&
 ticket_priority == Normal', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR19', 'Bug should be set to test after 1 month of debugging',
'(old_status=="accepted" && old_assigned_to_id!="" ) && 
(new_assigned_to=="test" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 43200) &&
 ticket_priority == Low', 1);
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR20', 'Bug should be set to test after 2 months of debugging',
'(old_status=="accepted" && old_assigned_to_id!="" ) && 
(new_assigned_to=="test" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 86400) &&
 ticket_priority == Lowest', 1);
 
 /*STEP 3 ACCEPTED TO TEST */
 
  /*STEP 3 ACCEPTED TO BLOCKED */
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR21', 'Bug should be set to blocked after 1 hour of checking',
'(old_status=="accepted" && old_assigned_to_id!="" ) && 
(new_assigned_to=="blocked" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 60) &&
 ticket_priority == Highest', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR22', 'Bug should be set to blocked after 24 hours of checking',
'(old_status=="accepted" && old_assigned_to_id!="" ) && 
(new_assigned_to=="blocked" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 1440) &&
 ticket_priority == High', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR23', 'Bug should be set to blocked after 1 week of checking',
'(old_status=="accepted" && old_assigned_to_id!="" ) && 
(new_assigned_to=="blocked" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 10080) &&
 ticket_priority == Normal', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR24', 'Bug should be set to blocked after 1 month of checking',
'(old_status=="accepted" && old_assigned_to_id!="" ) && 
(new_assigned_to=="blocked" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 43200) &&
 ticket_priority == Low', 1);
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR25', 'Bug should be set to blocked after 2 months of checking',
'(old_status=="accepted" && old_assigned_to_id!="" ) && 
(new_assigned_to=="blocked" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 86400) &&
 ticket_priority == Lowest', 1);
 
 /*STEP 3 ACCEPTED TO BLOCKED */
 
 /*STEP 4 TEST TO REVIEW */
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR26', 'Bug should be set to review after 1 hour of testing',
'(old_status=="test" && old_assigned_to_id!="" ) && 
(new_assigned_to=="review" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 60) &&
 ticket_priority == Highest', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR27', 'Bug should be set to review after 24 hours of testing',
'(old_status=="test" && old_assigned_to_id!="" ) && 
(new_assigned_to=="review" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 1440) &&
 ticket_priority == High', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR28', 'Bug should be set to review after 1 week of testing',
'(old_status=="test" && old_assigned_to_id!="" ) && 
(new_assigned_to=="review" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 10080) &&
 ticket_priority == Normal', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR29', 'Bug should be set to review after 2 weeks of testing',
'(old_status=="test" && old_assigned_to_id!="" ) && 
(new_assigned_to=="review" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 20160) &&
 ticket_priority == Low', 1);
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR30', 'Bug should be set to review after 2 weeks of testing',
'(old_status=="test" && old_assigned_to_id!="" ) && 
(new_assigned_to=="review" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 20160) &&
 ticket_priority == Lowest', 1);
 
 /*STEP 4 TEST TO REVIEW */
 
  /*STEP 4 TEST TO ACCEPTED/RE-OPENED */
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR31', 'Bug should be set to accepted/reopened after 1 hour of testing',
'(old_status=="test" && old_assigned_to_id!="" ) && 
(new_assigned_to=="reopened" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 60) &&
 ticket_priority == Highest', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR32', 'Bug should be set to accepted/reopened after 24 hours of testing',
'(old_status=="test" && old_assigned_to_id!="" ) && 
(new_assigned_to=="reopened" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 1440) &&
 ticket_priority == High', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR33', 'Bug should be set to accepted/reopened after 1 week of testing',
'(old_status=="test" && old_assigned_to_id!="" ) && 
(new_assigned_to=="reopened" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 10080) &&
 ticket_priority == Normal', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR34', 'Bug should be set to accepted/reopened after 2 weeks of testing',
'(old_status=="test" && old_assigned_to_id!="" ) && 
(new_assigned_to=="reopened" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 20160) &&
 ticket_priority == Low', 1);
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR36', 'Bug should be set to accepted/reopened after 2 weeks of testing',
'(old_status=="test" && old_assigned_to_id!="" ) && 
(new_assigned_to=="reopened" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 20160) &&
 ticket_priority == Lowest', 1);
 
 /*STEP 4 TEST TO ACCEPTED/RE-OPENED */
 
  /*STEP 5 REVIEW TO FIX */
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR37', 'Bug should be set to fixed after 1 hour of reviewing',
'(old_status=="review" && old_assigned_to_id!="" ) && 
(new_assigned_to=="fixed" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 60) &&
 ticket_priority == Highest', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR38', 'Bug should be set to fixed after 24 hours of reviewing',
'(old_status=="review" && old_assigned_to_id!="" ) && 
(new_assigned_to=="fixed" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 1440) &&
 ticket_priority == High', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR39', 'Bug should be set to fixed after 1 week of reviewing',
'(old_status=="review" && old_assigned_to_id!="" ) && 
(new_assigned_to=="fixed" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 10080) &&
 ticket_priority == Normal', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR40', 'Bug should be set to fixed after 2 weeks of reviewing',
'(old_status=="review" && old_assigned_to_id!="" ) && 
(new_assigned_to=="fixed" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 20160) &&
 ticket_priority == Low', 1);
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR41', 'Bug should be set to fixed after 2 weeks of reviewing',
'(old_status=="review" && old_assigned_to_id!="" ) && 
(new_assigned_to=="fixed" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 20160) &&
 ticket_priority == Lowest', 1);
 
 /*STEP 5 REVIEW TO FIX */
 
  /*STEP 4 BLOCKED TO INVALID */
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR42', 'Bug should be set to invalid if it has been blocked for 1 hour',
'(old_status=="blocked" && old_assigned_to_id!="" ) && 
(new_assigned_to=="invalid" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 60) &&
 ticket_priority == Highest', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR43', 'Bug should be set to invalid if it has been blocked for 24 hours',
'(old_status=="blocked" && old_assigned_to_id!="" ) && 
(new_assigned_to=="invalid" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 1440) &&
 ticket_priority == High', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR44', 'Bug should be set to invalid if it has been blocked for 1 week',
'(old_status=="blocked" && old_assigned_to_id!="" ) && 
(new_assigned_to=="invalid" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 10080) &&
 ticket_priority == Normal', 1);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR45', 'Bug should be set to invalid if it has been blocked for 2 weeks',
'(old_status=="blocked" && old_assigned_to_id!="" ) && 
(new_assigned_to=="invalid" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 20160) &&
 ticket_priority == Low', 1);
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR46', 'Bug should be set to invalid if it has been blocked for 2 weeks',
'(old_status=="blocked" && old_assigned_to_id!="" ) && 
(new_assigned_to=="invalid" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 20160) &&
 ticket_priority == Lowest', 1);
 
 /*STEP 4 BLOCKED TO INVALID */
 
/*BUG========BUG========BUG========BUG========BUG========BUG========BUG========BUG*/

/*SUPPORT========SUPPORT========SUPPORT========SUPPORT========SUPPORT========SUPPORT========SUPPORT========SUPPORT*/
/*STEP 1 NEW UNASSIGNED TO NEW ASSIGNED */
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'SUPP_ERR1', 'Support ticket should be accepted after 1 hour of assignment', 
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 60) && ticket_priority == 1', 2);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'SUPP_ERR2', 'Support ticket should be accepted after 24 hours of assignment',
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 1440) && ticket_priority == 2', 2);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'SUPP_ERR3', 'Support ticket should be accepted after 1 week of assignment',
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 10080) && ticket_priority == 3', 2);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'SUPP_ERR4', 'Support ticket should be accepted after 2 weeks of assignment',
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 20160) && ticket_priority == 4', 2);
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'SUPP_ERR5', 'Support ticket should be accepted after 1 month of assignment',
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 43200) && ticket_priority == 5', 2);
 /*STEP 1 NEW UNASSIGNED TO NEW ASSIGNED */
/*SUPPORT========SUPPORT========SUPPORT========SUPPORT========SUPPORT========SUPPORT========SUPPORT========SUPPORT*/

/*ENHANCEMENT========ENHANCEMENT========ENHANCEMENT========ENHANCEMENT========ENHANCEMENT========ENHANCEMENT========ENHANCEMENT========ENHANCEMENT*/
/*STEP 1 NEW UNASSIGNED TO NEW ASSIGNED */
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'ENHANCE_ERR1', 'Enhancement ticket should be accepted after 1 hour of assignment', 
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 60) && ticket_priority == 1', 3);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'ENHANCE_ERR2', 'Enhancement ticket should be accepted after 24 hours of assignment',
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 1440) && ticket_priority == 2', 3);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'ENHANCE_ERR3', 'Enhancement ticket should be accepted after 1 week of assignment',
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 10080) && ticket_priority == 3', 3);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'ENHANCE_ERR4', 'Enhancement ticket should be accepted after 2 weeks of assignment',
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 20160) && ticket_priority == 4', 3);
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'ENHANCE_ERR5', 'Enhancement ticket should be accepted after 1 month of assignment',
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 43200) && ticket_priority == 5', 3);
 /*STEP 1 NEW UNASSIGNED TO NEW ASSIGNED */
/*ENHANCEMENT========ENHANCEMENT========ENHANCEMENT========ENHANCEMENT========ENHANCEMENT========ENHANCEMENT========ENHANCEMENT========ENHANCEMENT*/

/*NEED========NEED========NEED========NEED========NEED========NEED========NEED========NEED*/
/*STEP 1 NEW UNASSIGNED TO NEW ASSIGNED */
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'NEED_ERR1', 'Needs ticket should be assigned after 1 hour of creation', 
'(old_status == "new" && old_assigned_to_id == "") && 
(new_status == "new" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 60) && ticket_priority == 1', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'NEED_ERR2', 'Needs ticket should be assigned after 24 hours of creation',
'(old_status == "new" && old_assigned_to_id == "") && 
(new_status == "new" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 1440) && ticket_priority == 2', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'NEED_ERR3', 'Needs ticket should be assigned after 1 week of creation',
'(old_status == "new" && old_assigned_to_id == "") && 
(new_status == "new" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 10080) && ticket_priority == 3', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'NEED_ERR4', 'Needs ticket should be assigned after 2 weeks of creation',
'(old_status == "new" && old_assigned_to_id == "") && 
(new_status == "new" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 20160) && ticket_priority == 4', 4);
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'NEED_ERR5', 'Needs ticket should be assigned after 1 month of creation',
'(old_status == "new" && old_assigned_to_id == "") && 
(new_status == "new" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 43200) && ticket_priority == 5', 4);
 /*STEP 1 NEW UNASSIGNED TO NEW ASSIGNED */
 
 /*STEP 2 NEW ASSIGNED TO NEW ACCEPTED*/
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'NEED_ERR6', 'Needs ticket should be accepted after 1 hour of assignment',
'($old.status=="new" && $old.assigned_to_id="" ) && 
($new.assigned_to=="accepted" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 60) &&
 $ticket.priority == Highest', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'NEED_ERR7', 'Needs ticket should be accepted after 24 hours of assignment',
'($old.status=="new" && $old.assigned_to_id="" ) && 
($new.assigned_to=="accepted" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 1440) &&
 $ticket.priority == High', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'NEED_ERR8', 'Needs ticket should be accepted after 1 week of assignment',
'($old.status=="new" && $old.assigned_to_id="" ) && 
($new.assigned_to=="accepted" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 10080) &&
 $ticket.priority == Normal', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'NEED_ERR9', 'Needs ticket should be accepted after 2 weeks of assignment',
'($old.status=="new" && $old.assigned_to_id="" ) && 
($new.assigned_to=="accepted" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 20160) &&
 $ticket.priority == Low', 4);
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'NEED_ERR10', 'Needs ticket should be accepted after 1 month of assignment',
'($old.status=="new" && $old.assigned_to_id="" ) && 
($new.assigned_to=="accepted" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 43200) &&
 $ticket.priority == Lowest', 4);
 /*STEP 2 NEW ASSIGNED TO NEW ACCEPTED*/
 
 /*STEP 3 ACCEPTED TO NEW REVIEW*/
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'NEED_ERR11', 'Needs ticket should be reviewed after 1 hour of accepting',
'($old.status=="accepted" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="review" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 60) &&
 $ticket.priority == Highest', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'NEED_ERR12', 'Needs ticket should be reviewed after 24 hours of accepting',
'($old.status=="accepted" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="review" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 1440) &&
 $ticket.priority == High', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'NEED_ERR13', 'Needs ticket should be reviewed after 24 hours of accepting',
'($old.status=="accepted" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="review" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 1440) &&
 $ticket.priority == Normal', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'NEED_ERR14', 'Needs ticket should be reviewed after 24 hours of accepting',
'($old.status=="accepted" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="review" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 1440) &&
 $ticket.priority == Low', 4);
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'NEED_ERR15', 'Needs ticket should be reviewed after 24 hours of accepting',
'($old.status=="accepted" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="review" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 1440) &&
 $ticket.priority == Lowest', 4);
 /*STEP 3 ACCEPTED TO NEW REVIEW*/
 
 /*STEP 3 ACCEPTED TO TEST */
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'NEED_ERR16', 'Needs ticket should be tested after 1 hour of accepting',
'($old.status=="accepted" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="test" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 60) &&
 $ticket.priority == Highest', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'NEED_ERR17', 'Needs ticket should be tested after 1 week of accepting',
'($old.status=="accepted" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="test" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 10080) &&
 $ticket.priority == High', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'NEED_ERR18', 'Needs ticket should be tested after 1 month of accepting',
'($old.status=="accepted" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="test" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 43200) &&
 $ticket.priority == Normal', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'NEED_ERR19', 'Needs ticket should be tested after 1 month of accepting',
'($old.status=="accepted" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="test" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 43200) &&
 $ticket.priority == Low', 4);
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'NEED_ERR20', 'Needs ticket should be tested after 2 months of accepting',
'($old.status=="accepted" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="test" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 86400) &&
 $ticket.priority == Lowest', 4);
 
 /*STEP 3 ACCEPTED TO TEST */
 
 /*STEP 3 ACCEPTED TO BLOCKED */
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'NEED_ERR21', 'Needs ticket should be blocked after 1 hour of accepting',
'($old.status=="accepted" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="blocked" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 60) &&
 $ticket.priority == Highest', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'NEED_ERR22', 'Needs ticket should be blocked after 24 hours of accepting',
'($old.status=="accepted" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="blocked" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 1440) &&
 $ticket.priority == High', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'NEED_ERR23', 'Needs ticket should be blocked after 1 week of accepting',
'($old.status=="accepted" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="blocked" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 10080) &&
 $ticket.priority == Normal', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'NEED_ERR24', 'Needs ticket should be blocked after 1 month of accepting',
'($old.status=="accepted" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="blocked" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 43200) &&
 $ticket.priority == Low', 4);
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'NEED_ERR25', 'Needs ticket should be blocked after 2 months of accepting',
'($old.status=="accepted" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="blocked" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 86400) &&
 $ticket.priority == Lowest', 4);
 
 /*STEP 3 ACCEPTED TO BLOCKED */
 
 /*STEP 4 TEST TO REVIEW */
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'NEED_ERR26', 'Needs ticket should be reviewed after 1 hour of testing',
'($old.status=="test" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="review" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 60) &&
 $ticket.priority == Highest', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'NEED_ERR27', 'Needs ticket should be reviewed after 24 hours of testing',
'($old.status=="test" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="review" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 1440) &&
 $ticket.priority == High', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'NEED_ERR28', 'Needs ticket should be reviewed after 1 week of testing',
'($old.status=="test" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="review" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 10080) &&
 $ticket.priority == Normal', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'NEED_ERR29', 'Needs ticket should be reviewed after 2 weeks of testing',
'($old.status=="test" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="review" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 20160) &&
 $ticket.priority == Low', 4);
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'NEED_ERR30', 'Needs ticket should be reviewed after 2 weeks of testing',
'($old.status=="test" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="review" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 20160) &&
 $ticket.priority == Lowest', 4);
 
 /*STEP 4 TEST TO REVIEW */
 
 /*STEP 5 REVIEW TO FIX */
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'NEED_ERR31', 'Needs ticket should be fixed after 1 hour of reviewing',
'($old.status=="review" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="fixed" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 60) &&
 $ticket.priority == Highest', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'NEED_ERR32', 'Needs ticket should be fixed after 24 hours of reviewing',
'($old.status=="review" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="fixed" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 1440) &&
 $ticket.priority == High', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'NEED_ERR33', 'Needs ticket should be fixed after 1 week of reviewing',
'($old.status=="review" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="fixed" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 10080) &&
 $ticket.priority == Normal', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'NEED_ERR34', 'Needs ticket should be fixed after 2 weeks of reviewing',
'($old.status=="review" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="fixed" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 20160) &&
 $ticket.priority == Low', 4);
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'NEED_ERR35', 'Needs ticket should be fixed after 2 weeks of reviewing',
'($old.status=="review" && $old.assigned_to_id!="" ) && 
($new.assigned_to=="fixed" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 20160) &&
 $ticket.priority == Lowest', 4);
 
 /*STEP 5 REVIEW TO FIX */
/*NEED========NEED========NEED========NEED========NEED========NEED========NEED========NEED*/

/*REGRESSION_BUG========REGRESSION_BUG========REGRESSION_BUG========REGRESSION_BUG========REGRESSION_BUG========REGRESSION_BUG========REGRESSION_BUG========REGRESSION_BUG*/
/*STEP 1 NEW UNASSIGNED TO NEW ASSIGNED */
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'REGRESSION_ERR1', 'Regression bug should be accepted after 1 hour of assignment', 
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 60) && ticket_priority == 1', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'REGRESSION_ERR2', 'Regression bug should be accepted after 24 hours of assignment',
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 1440) && ticket_priority == 2', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'REGRESSION_ERR3', 'Regression bug should be accepted after 1 week of assignment',
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 10080) && ticket_priority == 3', 4);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'REGRESSION_ERR4', 'Regression bug should be accepted after 2 weeks of assignment',
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 20160) && ticket_priority == 4', 4);
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'REGRESSION_ERR5', 'Regression bug should be accepted after 1 month of assignment',
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 43200) && ticket_priority == 5', 4);
 /*STEP 1 NEW UNASSIGNED TO NEW ASSIGNED */
/*REGRESSION_BUG========REGRESSION_BUG========REGRESSION_BUG========REGRESSION_BUG========REGRESSION_BUG========REGRESSION_BUG========REGRESSION_BUG========REGRESSION_BUG*/