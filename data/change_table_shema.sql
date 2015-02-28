alter table one_on_one_meeting_request add column (must_have_flag bit );
ALTER TABLE `fund`
	ADD COLUMN `priority` INT NULL AFTER `contact`;