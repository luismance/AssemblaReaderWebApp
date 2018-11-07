INSERT INTO workflow(CREATED, UPDATED, NAME) VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'Bug');
INSERT INTO workflow(CREATED, UPDATED, NAME) VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'Support');
INSERT INTO workflow(CREATED, UPDATED, NAME) VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'Enhancement');
INSERT INTO workflow(CREATED, UPDATED, NAME) VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'Need');
INSERT INTO workflow(CREATED, UPDATED, NAME) VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'Regression Bug');



/*SUPPORT========SUPPORT========SUPPORT========SUPPORT========SUPPORT========SUPPORT*/
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="new" && $old.assigned_to_id="" ) && 
($new.assigned_to=="accepted" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 60) &&
 $ticket.priority == Highest', 2);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="new" && $old.assigned_to_id="" ) && 
($new.assigned_to=="accepted" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 1440) &&
 $ticket.priority == High', 2);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="new" && $old.assigned_to_id="" ) && 
($new.assigned_to=="accepted" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 10080) &&
 $ticket.priority == Normal', 2);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="new" && $old.assigned_to_id="" ) && 
($new.assigned_to=="accepted" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 20160) &&
 $ticket.priority == Low', 2);
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="new" && $old.assigned_to_id="" ) && 
($new.assigned_to=="accepted" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 43200) &&
 $ticket.priority == Lowest', 2);
 
/*SUPPORT========SUPPORT========SUPPORT========SUPPORT========SUPPORT========SUPPORT*/
 
/*ENHANCEMENT========ENHANCEMENT========ENHANCEMENT========ENHANCEMENT========ENHANCEMENT*/
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="new" && $old.assigned_to_id="" ) && 
($new.assigned_to=="accepted" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 60) &&
 $ticket.priority == Highest', 3);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="new" && $old.assigned_to_id="" ) && 
($new.assigned_to=="accepted" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 1440) &&
 $ticket.priority == High', 3);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="new" && $old.assigned_to_id="" ) && 
($new.assigned_to=="accepted" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 10080) &&
 $ticket.priority == Normal', 3);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="new" && $old.assigned_to_id="" ) && 
($new.assigned_to=="accepted" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 20160) &&
 $ticket.priority == Low', 3);
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="new" && $old.assigned_to_id="" ) && 
($new.assigned_to=="accepted" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 43200) &&
 $ticket.priority == Lowest', 3);
 
/*ENHANCEMENT========ENHANCEMENT========ENHANCEMENT========ENHANCEMENT========ENHANCEMENT*/

/*REGRESSION BUG========REGRESSION BUG========REGRESSION BUG========REGRESSION BUG========REGRESSION BUG*/
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="new" && $old.assigned_to_id="" ) && 
($new.assigned_to=="accepted" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 60) &&
 $ticket.priority == Highest', 5);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="new" && $old.assigned_to_id="" ) && 
($new.assigned_to=="accepted" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 1440) &&
 $ticket.priority == High', 5);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="new" && $old.assigned_to_id="" ) && 
($new.assigned_to=="accepted" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 10080) &&
 $ticket.priority == Normal', 5);
 
INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="new" && $old.assigned_to_id="" ) && 
($new.assigned_to=="accepted" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 20160) &&
 $ticket.priority == Low', 5);
 
 INSERT INTO workflow_transition(CREATED, UPDATED, ERROR_CODE, ERROR_MESSAGE, EXPRESSION_LANGUAGE, WORKFLOW_ID) 
VALUES(CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 
'($old.status=="new" && $old.assigned_to_id="" ) && 
($new.assigned_to=="accepted" && $new.assigned_to_id!="") && 
(($new.updated_at - $ticket.created) < 43200) &&
 $ticket.priority == Lowest', 5);
 
/*REGRESSION BUG========REGRESSION BUG========REGRESSION BUG========REGRESSION BUG========REGRESSION BUG*/