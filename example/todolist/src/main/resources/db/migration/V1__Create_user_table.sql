CREATE TABLE `User` (
  id           BIGINT(11) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  gmtCreate    DATETIME            NOT NULL,
  gmtModify    DATETIME            NOT NULL,
  name         VARCHAR(32)         NOT NULL,
  email        VARCHAR(128)        NOT NULL,
  avatarUrl    VARCHAR(255),
  encodePasswd VARCHAR(255)        NOT NULL,
  gmtRegister  DATETIME            NOT NULL,
  UNIQUE KEY email (email),
  UNIQUE KEY `name` (name)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8;