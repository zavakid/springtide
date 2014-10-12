CREATE TABLE `Task` (
  id          BIGINT(11) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  gmtCreate   DATETIME            NOT NULL,
  gmtModify   DATETIME            NOT NULL,
  title       VARCHAR(128)        NOT NULL,
  gmtFinished DATETIME,
  userId      BIGINT(11) UNSIGNED NOT NULL,
  KEY userIdKey (userId)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8;
