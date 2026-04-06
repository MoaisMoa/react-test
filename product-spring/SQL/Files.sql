DROP TABLE IF EXISTS `files`;
CREATE TABLE `files` (
    `no` BIGINT NOT NULL AUTO_INCREMENT,
    `id` VARCHAR(64) NOT NULL,
    `p_id` VARCHAR(64) NOT NULL,
    `file_name` text NOT NULL,
    `origin_name` text,
    `file_path` text NOT NULL,
    `file_size` BIGINT NOT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `type` ENUM('MAIN', 'SUB') NOT NULL DEFAULT 'SUB',
    `seq` BIGINT NULL DEFAULT 0,
    PRIMARY KEY (`no`)
);

