-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.2.7-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for assembla_db
DROP DATABASE IF EXISTS `assembla_db`;
CREATE DATABASE IF NOT EXISTS `assembla_db` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `assembla_db`;

-- Dumping structure for table assembla_db.max_delay
DROP TABLE IF EXISTS `max_delay`;
CREATE TABLE IF NOT EXISTS `max_delay` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `expression_language` text NOT NULL,
  `max_delay` bigint(20) NOT NULL,
  `created` datetime NOT NULL,
  `updated` datetime NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table assembla_db.milestone
DROP TABLE IF EXISTS `milestone`;
CREATE TABLE IF NOT EXISTS `milestone` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `planner_type` int(11) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `release_notes` text DEFAULT NULL,
  `pretty_release_level` text DEFAULT NULL,
  `created_by_id` bigint(20) DEFAULT NULL,
  `completed_date` datetime DEFAULT NULL,
  `due_date` datetime DEFAULT NULL,
  `is_completed` tinyint(1) DEFAULT NULL,
  `title` text DEFAULT NULL,
  `updated_by_id` bigint(20) DEFAULT NULL,
  `external_ref_id` text DEFAULT NULL,
  `space_id` bigint(20) DEFAULT NULL,
  `remotely_created` datetime DEFAULT NULL,
  `remotely_updated` datetime DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_milestone_created_by_id` (`created_by_id`),
  KEY `fk_milestone_update_by_id` (`updated_by_id`),
  KEY `fk_milestone_space_id` (`space_id`),
  CONSTRAINT `fk_milestone_created_by_id` FOREIGN KEY (`created_by_id`) REFERENCES `user` (`ID`),
  CONSTRAINT `fk_milestone_space_id` FOREIGN KEY (`space_id`) REFERENCES `space` (`ID`),
  CONSTRAINT `fk_milestone_update_by_id` FOREIGN KEY (`updated_by_id`) REFERENCES `user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table assembla_db.notification
DROP TABLE IF EXISTS `notification`;
CREATE TABLE IF NOT EXISTS `notification` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `workflow_transition_instance_id` bigint(20) DEFAULT NULL,
  `workflow_transition_violated_id` bigint(20) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_notification_workflow_transition_instance_id` (`workflow_transition_instance_id`),
  KEY `fk_notification_workflow_transition_violated_id` (`workflow_transition_violated_id`),
  CONSTRAINT `fk_notification_workflow_transition_instance_id` FOREIGN KEY (`workflow_transition_instance_id`) REFERENCES `workflow_transition_instance` (`ID`),
  CONSTRAINT `fk_notification_workflow_transition_violated_id` FOREIGN KEY (`workflow_transition_violated_id`) REFERENCES `workflow_transition` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table assembla_db.role
DROP TABLE IF EXISTS `role`;
CREATE TABLE IF NOT EXISTS `role` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` text DEFAULT NULL,
  `description` text DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table assembla_db.space
DROP TABLE IF EXISTS `space`;
CREATE TABLE IF NOT EXISTS `space` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `external_ref_id` text NOT NULL,
  `remotely_created` datetime NOT NULL,
  `remotely_updated` datetime NOT NULL,
  `description` text NOT NULL,
  `wikiname` text NOT NULL,
  `public_permissions` int(11) NOT NULL,
  `team_permissions` int(11) NOT NULL,
  `watcher_permissions` int(11) NOT NULL,
  `team_tab_role` int(11) NOT NULL,
  `default_show_page` text NOT NULL,
  `tabs_order` text NOT NULL,
  `parent_space_id` bigint(20) NOT NULL,
  `restricted` tinyint(1) NOT NULL,
  `restricted_date` datetime NOT NULL,
  `commercial_from` datetime NOT NULL,
  `banner_path` text NOT NULL,
  `banner_height` int(11) NOT NULL,
  `banner_text` text NOT NULL,
  `banner_link` text NOT NULL,
  `style` text NOT NULL,
  `status` int(11) NOT NULL,
  `is_approved` tinyint(1) NOT NULL,
  `is_manager` tinyint(1) NOT NULL,
  `is_volunteer` tinyint(1) NOT NULL,
  `is_commercial` tinyint(1) NOT NULL,
  `can_join` tinyint(1) NOT NULL,
  `can_apply` tinyint(1) NOT NULL,
  `last_payer_changed_at` datetime NOT NULL,
  `created` datetime NOT NULL,
  `updated` datetime NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_space_parent_space_id` (`parent_space_id`),
  CONSTRAINT `fk_space_parent_space_id` FOREIGN KEY (`parent_space_id`) REFERENCES `space` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table assembla_db.ticket
DROP TABLE IF EXISTS `ticket`;
CREATE TABLE IF NOT EXISTS `ticket` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `space_id` bigint(20) NOT NULL,
  `external_ref_id` text NOT NULL,
  `remotely_created` datetime NOT NULL,
  `remotely_updated` datetime NOT NULL,
  `ticket_number` int(11) NOT NULL,
  `summary` text NOT NULL,
  `description` text NOT NULL,
  `status` text NOT NULL,
  `priority_type_id` int(11) NOT NULL,
  `total_working_hours` float NOT NULL,
  `milestone_id` bigint(20) NOT NULL,
  `estimate` float NOT NULL,
  `is_story` tinyint(1) NOT NULL,
  `reporter_id` bigint(20) NOT NULL,
  `assigned_to_id` bigint(20) NOT NULL,
  `story_importance` int(11) NOT NULL,
  `total_invested_hours` float NOT NULL,
  `total_estimate` float NOT NULL,
  `working_hours` float NOT NULL,
  `importance` float NOT NULL,
  `completed_date` datetime NOT NULL,
  `workflow_id` bigint(20) NOT NULL,
  `created` datetime NOT NULL,
  `updated` datetime NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_ticket_assigned_to_id` (`assigned_to_id`),
  KEY `fk_ticket_reporter_id` (`reporter_id`),
  KEY `fk_ticket_space_id` (`space_id`),
  KEY `fk_ticket_workflow_id` (`workflow_id`),
  KEY `fk_ticket_milestone_id` (`milestone_id`),
  CONSTRAINT `fk_ticket_assigned_to_id` FOREIGN KEY (`assigned_to_id`) REFERENCES `user` (`ID`),
  CONSTRAINT `fk_ticket_milestone_id` FOREIGN KEY (`milestone_id`) REFERENCES `milestone` (`ID`),
  CONSTRAINT `fk_ticket_reporter_id` FOREIGN KEY (`reporter_id`) REFERENCES `user` (`ID`),
  CONSTRAINT `fk_ticket_space_id` FOREIGN KEY (`space_id`) REFERENCES `space` (`ID`),
  CONSTRAINT `fk_ticket_workflow_id` FOREIGN KEY (`workflow_id`) REFERENCES `workflow` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table assembla_db.user
DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` text DEFAULT NULL,
  `password` text DEFAULT NULL,
  `external_ref_id` text DEFAULT NULL,
  `bearer_token` text DEFAULT NULL,
  `refresh_token` text DEFAULT NULL,
  `name` text DEFAULT NULL,
  `email` text DEFAULT NULL,
  `phone_num` text DEFAULT NULL,
  `created` datetime DEFAULT current_timestamp(),
  `updated` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table assembla_db.user_role
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE IF NOT EXISTS `user_role` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  `updated` datetime DEFAULT current_timestamp(),
  `created` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`ID`),
  KEY `fk_user_role_role_id` (`role_id`),
  KEY `fk_user_role_user_id` (`user_id`),
  CONSTRAINT `fk_user_role_role_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`ID`),
  CONSTRAINT `fk_user_role_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table assembla_db.workflow
DROP TABLE IF EXISTS `workflow`;
CREATE TABLE IF NOT EXISTS `workflow` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` text DEFAULT NULL,
  `created` datetime DEFAULT current_timestamp(),
  `updated` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table assembla_db.workflow_transition
DROP TABLE IF EXISTS `workflow_transition`;
CREATE TABLE IF NOT EXISTS `workflow_transition` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `expression_language` text DEFAULT '0',
  `error_code` text DEFAULT '0',
  `error_message` text DEFAULT '0',
  `workflow_id` bigint(20) DEFAULT 0,
  `created` datetime DEFAULT current_timestamp(),
  `updated` datetime DEFAULT current_timestamp(),
  PRIMARY KEY (`ID`),
  KEY `fk_workflow_transition_workflow_id` (`workflow_id`),
  CONSTRAINT `fk_workflow_transition_workflow_id` FOREIGN KEY (`workflow_id`) REFERENCES `workflow` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table assembla_db.workflow_transition_instance
DROP TABLE IF EXISTS `workflow_transition_instance`;
CREATE TABLE IF NOT EXISTS `workflow_transition_instance` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ticket_id` bigint(20) DEFAULT NULL,
  `message` text DEFAULT NULL,
  `remotely_created` datetime DEFAULT NULL,
  `remotely_updated` datetime DEFAULT NULL,
  `space_id` bigint(20) DEFAULT NULL,
  `external_ref_id` text DEFAULT NULL,
  `has_violation` tinyint(1) DEFAULT NULL,
  `origin_state` text DEFAULT NULL,
  `target_state` text DEFAULT NULL,
  `workflow_transition_id` bigint(20) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_workflow_transition_instance_space_id` (`space_id`),
  KEY `fk_workflow_transition_instance_workflow_transition_id` (`workflow_transition_id`),
  KEY `fk_workflow_transition_instance_ticket_id` (`ticket_id`),
  CONSTRAINT `fk_workflow_transition_instance_space_id` FOREIGN KEY (`space_id`) REFERENCES `space` (`ID`),
  CONSTRAINT `fk_workflow_transition_instance_ticket_id` FOREIGN KEY (`ticket_id`) REFERENCES `ticket` (`ID`),
  CONSTRAINT `fk_workflow_transition_instance_workflow_transition_id` FOREIGN KEY (`workflow_transition_id`) REFERENCES `workflow_transition` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
