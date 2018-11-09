INSERT INTO workflow(CREATED, UPDATED, NAME) VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'Bug');
INSERT INTO workflow(CREATED, UPDATED, NAME) VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'Support');
INSERT INTO workflow(CREATED, UPDATED, NAME) VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'Enhancement');
INSERT INTO workflow(CREATED, UPDATED, NAME) VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'Need');
INSERT INTO workflow(CREATED, UPDATED, NAME) VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'Regression Bug');

INSERT INTO workflow_transition(ID, CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID, IS_START) 
VALUES(1, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR1', 'Bug should be assigned after 1 hour of creation',
'(old_status == "new" && old_assigned_to_id == "") && 
(new_status == "new" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 60) && ticket_priority == 1', 1, true);

INSERT INTO workflow_transition(ID, CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID, IS_START) 
VALUES(2, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR6', 'Bug should be accepted after 1 hour of assignment',
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 60) && ticket_priority == 1', 1,false);

INSERT INTO workflow_transition(ID, CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID, IS_START)  
VALUES(3, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR16', 'Bug should be set to test after 1 hour of debugging',
'(old_status=="accepted" && old_assigned_to_id!="" ) && 
(new_assigned_to=="test" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 60) &&
 ticket_priority == Highest', 1,false);
 
INSERT INTO workflow_transition(ID, CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID, IS_START) 
VALUES(4, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR21', 'Bug should be set to blocked after 1 hour of checking',
'(old_status=="accepted" && old_assigned_to_id!="" ) && 
(new_assigned_to=="blocked" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 60) &&
 ticket_priority == Highest', 1,false);
 
INSERT INTO workflow_transition(ID, CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID, IS_START)  
VALUES(5, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR26', 'Bug should be set to review after 1 hour of testing',
'(old_status=="test" && old_assigned_to_id!="" ) && 
(new_assigned_to=="review" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 60) &&
 ticket_priority == Highest', 1,false);
 
INSERT INTO workflow_transition(ID, CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID, IS_START)  
VALUES(6, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR37', 'Bug should be set to fixed after 1 hour of reviewing',
'(old_status=="review" && old_assigned_to_id!="" ) && 
(new_assigned_to=="fixed" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 60) &&
 ticket_priority == Highest', 1,false);
 
INSERT INTO workflow_transition_workflow_transition (workflowtransition_id,workflowtransitions_id)
 VALUES(1,2);
 INSERT INTO workflow_transition_workflow_transition (workflowtransition_id,workflowtransitions_id)
 VALUES(2,3);
 INSERT INTO workflow_transition_workflow_transition (workflowtransition_id,workflowtransitions_id)
 VALUES(3,4);
 INSERT INTO workflow_transition_workflow_transition (workflowtransition_id,workflowtransitions_id)
 VALUES(4,5);
 INSERT INTO workflow_transition_workflow_transition (workflowtransition_id,workflowtransitions_id)
 VALUES(5,6);
 
 /*------------------------------------------------*/
 
 INSERT INTO workflow_transition(ID, CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID, IS_START)  
VALUES(7, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR1', 'Bug should be assigned after 1 hour of creation', 
'(old_status == "new" && old_assigned_to_id == "") && 
(new_status == "new" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 60) && ticket_priority == 1',2,true);

INSERT INTO workflow_transition(ID, CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID, IS_START)  
VALUES(8, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR6', 'Bug should be accepted after 1 hour of assignment',
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 60) && ticket_priority == 1',2,false);

INSERT INTO workflow_transition(ID, CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID, IS_START) 
VALUES(9, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR16', 'Bug should be set to test after 1 hour of debugging',
'(old_status=="accepted" && old_assigned_to_id!="" ) && 
(new_assigned_to=="test" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 60) &&
 ticket_priority == Highest',2,false);
 
INSERT INTO workflow_transition(ID, CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID, IS_START)  
VALUES(10, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR21', 'Bug should be set to blocked after 1 hour of checking',
'(old_status=="accepted" && old_assigned_to_id!="" ) && 
(new_assigned_to=="blocked" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 60) &&
 ticket_priority == Highest',2,false);
 
INSERT INTO workflow_transition(ID, CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID, IS_START)  
VALUES(11, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR26', 'Bug should be set to review after 1 hour of testing',
'(old_status=="test" && old_assigned_to_id!="" ) && 
(new_assigned_to=="review" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 60) &&
 ticket_priority == Highest',2,false);
 
INSERT INTO workflow_transition(ID, CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID, IS_START)  
VALUES(12, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR37', 'Bug should be set to fixed after 1 hour of reviewing',
'(old_status=="review" && old_assigned_to_id!="" ) && 
(new_assigned_to=="fixed" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 60) &&
 ticket_priority == Highest',2,false);
 
 INSERT INTO workflow_transition_workflow_transition (workflowtransition_id,workflowtransitions_id)
 VALUES(7,8);
 INSERT INTO workflow_transition_workflow_transition (workflowtransition_id,workflowtransitions_id)
 VALUES(8,9);
 INSERT INTO workflow_transition_workflow_transition (workflowtransition_id,workflowtransitions_id)
 VALUES(9,10);
 INSERT INTO workflow_transition_workflow_transition (workflowtransition_id,workflowtransitions_id)
 VALUES(10,11);
 INSERT INTO workflow_transition_workflow_transition (workflowtransition_id,workflowtransitions_id)
 VALUES(11,12);
  /*------------------------------------------------*/
 
 INSERT INTO workflow_transition(ID, CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID, IS_START)  
VALUES(13, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR1', 'Bug should be assigned after 1 hour of creation',
'(old_status == "new" && old_assigned_to_id == "") && 
(new_status == "new" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 60) && ticket_priority == 1',3,true);

INSERT INTO workflow_transition(ID, CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID, IS_START)  
VALUES(14, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR6', 'Bug should be accepted after 1 hour of assignment',
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 60) && ticket_priority == 1',3,false);

INSERT INTO workflow_transition(ID, CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID, IS_START) 
VALUES(15, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR16', 'Bug should be set to test after 1 hour of debugging',
'(old_status=="accepted" && old_assigned_to_id!="" ) && 
(new_assigned_to=="test" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 60) &&
 ticket_priority == Highest',3,false);
 
INSERT INTO workflow_transition(ID, CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID, IS_START)  
VALUES(16, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR21', 'Bug should be set to blocked after 1 hour of checking',
'(old_status=="accepted" && old_assigned_to_id!="" ) && 
(new_assigned_to=="blocked" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 60) &&
 ticket_priority == Highest',3,false);
 
INSERT INTO workflow_transition(ID, CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID, IS_START)  
VALUES(17, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR26', 'Bug should be set to review after 1 hour of testing',
'(old_status=="test" && old_assigned_to_id!="" ) && 
(new_assigned_to=="review" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 60) &&
 ticket_priority == Highest',3,false);
 
INSERT INTO workflow_transition(ID, CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID, IS_START)  
VALUES(18, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR37', 'Bug should be set to fixed after 1 hour of reviewing',
'(old_status=="review" && old_assigned_to_id!="" ) && 
(new_assigned_to=="fixed" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 60) &&
 ticket_priority == Highest',3,false);
 
 INSERT INTO workflow_transition_workflow_transition (workflowtransition_id,workflowtransitions_id)
 VALUES(13,14);
 INSERT INTO workflow_transition_workflow_transition (workflowtransition_id,workflowtransitions_id)
 VALUES(14,15);
 INSERT INTO workflow_transition_workflow_transition (workflowtransition_id,workflowtransitions_id)
 VALUES(15,16);
 INSERT INTO workflow_transition_workflow_transition (workflowtransition_id,workflowtransitions_id)
 VALUES(16,17);
 INSERT INTO workflow_transition_workflow_transition (workflowtransition_id,workflowtransitions_id)
 VALUES(17,18);
 
   /*------------------------------------------------*/
 
 INSERT INTO workflow_transition(ID, CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID, IS_START) 
VALUES(19, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR1', 'Bug should be assigned after 1 hour of creation',
'(old_status == "new" && old_assigned_to_id == "") && 
(new_status == "new" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 60) && ticket_priority == 1',4,true);

INSERT INTO workflow_transition(ID, CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID, IS_START) 
VALUES(20, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR6', 'Bug should be accepted after 1 hour of assignment',  
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 60) && ticket_priority == 1',4,false);

INSERT INTO workflow_transition(ID, CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID, IS_START) 
VALUES(21, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR16', 'Bug should be set to test after 1 hour of debugging',
'(old_status=="accepted" && old_assigned_to_id!="" ) && 
(new_assigned_to=="test" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 60) &&
 ticket_priority == Highest',4,false);
 
INSERT INTO workflow_transition(ID, CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID, IS_START) 
VALUES(22, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR21', 'Bug should be set to blocked after 1 hour of checking',
'(old_status=="accepted" && old_assigned_to_id!="" ) && 
(new_assigned_to=="blocked" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 60) &&
 ticket_priority == Highest',4,false);
 
INSERT INTO workflow_transition(ID, CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID, IS_START) 
VALUES(23, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR26', 'Bug should be set to review after 1 hour of testing',
'(old_status=="test" && old_assigned_to_id!="" ) && 
(new_assigned_to=="review" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 60) &&
 ticket_priority == Highest',4,false);
 
INSERT INTO workflow_transition(ID, CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID, IS_START) 
VALUES(24, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR37', 'Bug should be set to fixed after 1 hour of reviewing',
'(old_status=="review" && old_assigned_to_id!="" ) && 
(new_assigned_to=="fixed" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 60) &&
 ticket_priority == Highest',4,false);
 
 INSERT INTO workflow_transition_workflow_transition (workflowtransition_id,workflowtransitions_id)
 VALUES(19,20);
 INSERT INTO workflow_transition_workflow_transition (workflowtransition_id,workflowtransitions_id)
 VALUES(20,21);
 INSERT INTO workflow_transition_workflow_transition (workflowtransition_id,workflowtransitions_id)
 VALUES(21,22);
 INSERT INTO workflow_transition_workflow_transition (workflowtransition_id,workflowtransitions_id)
 VALUES(22,23);
 INSERT INTO workflow_transition_workflow_transition (workflowtransition_id,workflowtransitions_id)
 VALUES(23,24);
    /*------------------------------------------------*/
 
 INSERT INTO workflow_transition(ID, CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID, IS_START) 
VALUES(25, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR1', 'Bug should be assigned after 1 hour of creation', 
'(old_status == "new" && old_assigned_to_id == "") && 
(new_status == "new" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 60) && ticket_priority == 1',5,true);

INSERT INTO workflow_transition(ID, CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID, IS_START) 
VALUES(26, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR6', 'Bug should be accepted after 1 hour of assignment',  
'(old_status == "New" && old_assigned_to_id == "") && 
(new_status == "Accepted" && new_assigned_to_id != "") && 
((new_updated_at - ticket_created) < 60) && ticket_priority == 1',5,false);

INSERT INTO workflow_transition(ID, CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID, IS_START) 
VALUES(27, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR16', 'Bug should be set to test after 1 hour of debugging',
'(old_status=="accepted" && old_assigned_to_id!="" ) && 
(new_assigned_to=="test" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 60) &&
 ticket_priority == Highest',5,false);
 
INSERT INTO workflow_transition(ID, CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID, IS_START) 
VALUES(28, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR21', 'Bug should be set to blocked after 1 hour of checking',
'(old_status=="accepted" && old_assigned_to_id!="" ) && 
(new_assigned_to=="blocked" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 60) &&
 ticket_priority == Highest',5,false);
 
INSERT INTO workflow_transition(ID, CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID, IS_START) 
VALUES(29, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR26', 'Bug should be set to review after 1 hour of testing',
'(old_status=="test" && old_assigned_to_id!="" ) && 
(new_assigned_to=="review" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 60) &&
 ticket_priority == Highest',5,false);
 
INSERT INTO workflow_transition(ID, CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID, IS_START) 
VALUES(30, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'BUG_ERR37', 'Bug should be set to fixed after 1 hour of reviewing',
'(old_status=="review" && old_assigned_to_id!="" ) && 
(new_assigned_to=="fixed" && new_assigned_to_id!="") && 
((new_updated_at - ticket_created) < 60) &&
 ticket_priority == Highest',5,false);
 
 INSERT INTO workflow_transition_workflow_transition (workflowtransition_id,workflowtransitions_id)
 VALUES(25,26);
 INSERT INTO workflow_transition_workflow_transition (workflowtransition_id,workflowtransitions_id)
 VALUES(26,27);
 INSERT INTO workflow_transition_workflow_transition (workflowtransition_id,workflowtransitions_id)
 VALUES(27,28);
 INSERT INTO workflow_transition_workflow_transition (workflowtransition_id,workflowtransitions_id)
 VALUES(28,29);
 INSERT INTO workflow_transition_workflow_transition (workflowtransition_id,workflowtransitions_id)
 VALUES(29,30);