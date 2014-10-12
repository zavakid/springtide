ALTER TABLE `Task` ADD finished CHAR(1) DEFAULT 0;
ALTER TABLE `Task` ADD description TEXT;
ALTER TABLE `Task` ADD INDEX `finished_index` (`finished`)