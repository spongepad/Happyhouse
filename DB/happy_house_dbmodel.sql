-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema happyhouse
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema happyhouse
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `happyhouse` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `happyhouse` ;

-- -----------------------------------------------------
-- Table `happyhouse`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `happyhouse`.`user` ;

CREATE TABLE IF NOT EXISTS `happyhouse`.`user` (
  `userid` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `tel` VARCHAR(45) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`userid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `happyhouse`.`notice`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `happyhouse`.`notice` ;

CREATE TABLE IF NOT EXISTS `happyhouse`.`notice` (
  `no` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  `contents` VARCHAR(1000) NOT NULL,
  `type` VARCHAR(45) NOT NULL,
  `regdate` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `userid` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`no`),
  INDEX `fk_notice_user1_idx` (`userid` ASC) VISIBLE,
  CONSTRAINT `fk_notice_user1`
    FOREIGN KEY (`userid`)
    REFERENCES `happyhouse`.`user` (`userid`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 34
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `happyhouse`.`answer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `happyhouse`.`answer` ;

CREATE TABLE IF NOT EXISTS `happyhouse`.`answer` (
  `no` INT NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(1000) NOT NULL,
  `contact` INT NOT NULL DEFAULT 0,
  `userid` VARCHAR(45) NOT NULL,
  `like` INT NOT NULL DEFAULT '0',
  `regdate` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `notice_no` INT NOT NULL,
  PRIMARY KEY (`no`),
  INDEX `fk_answer_notice1_idx` (`notice_no` ASC) VISIBLE,
  CONSTRAINT `fk_answer_notice1`
    FOREIGN KEY (`notice_no`)
    REFERENCES `happyhouse`.`notice` (`no`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `happyhouse`.`answer-like-log`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `happyhouse`.`answer-like-log` ;

CREATE TABLE IF NOT EXISTS `happyhouse`.`answer-like-log` (
  `answer_no` INT NOT NULL,
  `no` INT NOT NULL AUTO_INCREMENT,
  `userid` VARCHAR(45) NOT NULL,
  INDEX `fk_answer-like-log_answer1_idx` (`answer_no` ASC) VISIBLE,
  PRIMARY KEY (`no`),
  CONSTRAINT `fk_answer-like-log_answer1`
    FOREIGN KEY (`answer_no`)
    REFERENCES `happyhouse`.`answer` (`no`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `happyhouse`.`dongcode`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `happyhouse`.`dongcode` ;

CREATE TABLE IF NOT EXISTS `happyhouse`.`dongcode` (
  `dongCode` VARCHAR(10) NOT NULL,
  `sidoName` VARCHAR(30) NULL DEFAULT NULL,
  `gugunName` VARCHAR(30) NULL DEFAULT NULL,
  `dongName` VARCHAR(30) NULL DEFAULT NULL,
  PRIMARY KEY (`dongCode`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `happyhouse`.`baseaddress`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `happyhouse`.`baseaddress` ;

CREATE TABLE IF NOT EXISTS `happyhouse`.`baseaddress` (
  `no` INT NOT NULL AUTO_INCREMENT,
  `sidoName` VARCHAR(30) NULL DEFAULT NULL,
  `gugunName` VARCHAR(30) NULL DEFAULT NULL,
  `dongName` VARCHAR(30) NULL DEFAULT NULL,
  `dongCode` VARCHAR(10) NULL DEFAULT NULL,
  `lat` VARCHAR(20) NULL DEFAULT NULL,
  `lng` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`no`),
  INDEX `dongCode` (`dongCode` ASC) VISIBLE,
  CONSTRAINT `baseaddress_ibfk_1`
    FOREIGN KEY (`dongCode`)
    REFERENCES `happyhouse`.`dongcode` (`dongCode`))
ENGINE = InnoDB
AUTO_INCREMENT = 56638
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `happyhouse`.`environment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `happyhouse`.`environment` ;

CREATE TABLE IF NOT EXISTS `happyhouse`.`environment` (
  `no` INT NOT NULL AUTO_INCREMENT,
  `company` VARCHAR(30) NOT NULL,
  `gu` VARCHAR(10) NOT NULL,
  `dong` VARCHAR(30) NULL DEFAULT NULL,
  `type` VARCHAR(30) NULL DEFAULT NULL,
  `address` VARCHAR(30) NULL DEFAULT NULL,
  PRIMARY KEY (`no`))
ENGINE = InnoDB
AUTO_INCREMENT = 1824
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `happyhouse`.`guguncode`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `happyhouse`.`guguncode` ;

CREATE TABLE IF NOT EXISTS `happyhouse`.`guguncode` (
  `gugunCode` VARCHAR(10) NOT NULL,
  `gugunName` VARCHAR(30) NULL DEFAULT NULL,
  PRIMARY KEY (`gugunCode`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `happyhouse`.`houseinfo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `happyhouse`.`houseinfo` ;

CREATE TABLE IF NOT EXISTS `happyhouse`.`houseinfo` (
  `aptCode` BIGINT NOT NULL,
  `buildYear` INT NULL DEFAULT NULL,
  `roadName` VARCHAR(40) NULL DEFAULT NULL,
  `roadNameBonbun` VARCHAR(5) NULL DEFAULT NULL,
  `roadNameBubun` VARCHAR(5) NULL DEFAULT NULL,
  `roadNameSeq` VARCHAR(2) NULL DEFAULT NULL,
  `roadNameBasementCode` VARCHAR(1) NULL DEFAULT NULL,
  `roadNameCode` VARCHAR(7) NULL DEFAULT NULL,
  `dong` VARCHAR(40) NULL DEFAULT NULL,
  `bonbun` VARCHAR(4) NULL DEFAULT NULL,
  `bubun` VARCHAR(4) NULL DEFAULT NULL,
  `sigunguCode` VARCHAR(5) NULL DEFAULT NULL,
  `eubmyundongCode` VARCHAR(5) NULL DEFAULT NULL,
  `dongCode` VARCHAR(10) NULL DEFAULT NULL,
  `landCode` VARCHAR(1) NULL DEFAULT NULL,
  `apartmentName` VARCHAR(40) NULL DEFAULT NULL,
  `jibun` VARCHAR(10) NULL DEFAULT NULL,
  `lng` VARCHAR(30) NULL DEFAULT NULL,
  `lat` VARCHAR(30) NULL DEFAULT NULL,
  PRIMARY KEY (`aptCode`),
  UNIQUE INDEX `UNIQUE` (`buildYear` ASC, `apartmentName` ASC, `jibun` ASC, `sigunguCode` ASC, `eubmyundongCode` ASC) INVISIBLE,
  INDEX `houseinfo_dongCode_dongcode_dongCode_fk_idx` (`dongCode` ASC) INVISIBLE,
  CONSTRAINT `houseinfo_dongCode_dongcode_dongCode_fk`
    FOREIGN KEY (`dongCode`)
    REFERENCES `happyhouse`.`dongcode` (`dongCode`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `happyhouse`.`housedeal`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `happyhouse`.`housedeal` ;

CREATE TABLE IF NOT EXISTS `happyhouse`.`housedeal` (
  `no` BIGINT NOT NULL,
  `dealAmount` VARCHAR(20) NULL DEFAULT NULL,
  `dealYear` INT NULL DEFAULT NULL,
  `dealMonth` INT NULL DEFAULT NULL,
  `dealDay` INT NULL DEFAULT NULL,
  `area` VARCHAR(20) NULL DEFAULT NULL,
  `floor` VARCHAR(4) NULL DEFAULT NULL,
  `cancelDealType` VARCHAR(1) NULL DEFAULT NULL,
  `aptCode` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`no`),
  INDEX `housedeal_aptCode_houseinfo_aptCode_fk_idx` (`aptCode` ASC) VISIBLE,
  CONSTRAINT `housedeal_aptCode_houseinfo_aptCode_fk`
    FOREIGN KEY (`aptCode`)
    REFERENCES `happyhouse`.`houseinfo` (`aptCode`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `happyhouse`.`likeapt`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `happyhouse`.`likeapt` ;

CREATE TABLE IF NOT EXISTS `happyhouse`.`likeapt` (
  `no` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(45) NOT NULL,
  `aptCode` BIGINT NOT NULL,
  PRIMARY KEY (`no`),
  INDEX `fk_likearea_user_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_likeapt_houseinfo1_idx` (`aptCode` ASC) VISIBLE,
  CONSTRAINT `fk_likearea_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `happyhouse`.`user` (`userid`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_likeapt_houseinfo1`
    FOREIGN KEY (`aptCode`)
    REFERENCES `happyhouse`.`houseinfo` (`aptCode`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `happyhouse`.`reply`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `happyhouse`.`reply` ;

CREATE TABLE IF NOT EXISTS `happyhouse`.`reply` (
  `no` INT NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(100) NOT NULL,
  `regdate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `notice_no` INT NOT NULL,
  `userid` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`no`),
  INDEX `fk_reply_notice1_idx` (`notice_no` ASC) VISIBLE,
  INDEX `fk_reply_user1_idx` (`userid` ASC) VISIBLE,
  CONSTRAINT `fk_reply_notice1`
    FOREIGN KEY (`notice_no`)
    REFERENCES `happyhouse`.`notice` (`no`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_reply_user1`
    FOREIGN KEY (`userid`)
    REFERENCES `happyhouse`.`user` (`userid`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `happyhouse`.`sidocode`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `happyhouse`.`sidocode` ;

CREATE TABLE IF NOT EXISTS `happyhouse`.`sidocode` (
  `sidoCode` VARCHAR(10) NOT NULL,
  `sidoName` VARCHAR(30) NULL DEFAULT NULL,
  PRIMARY KEY (`sidoCode`),
  UNIQUE INDEX `sidoName` (`sidoName` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
