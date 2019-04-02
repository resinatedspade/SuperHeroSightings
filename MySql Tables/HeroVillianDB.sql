DROP DATABASE IF EXISTS herovillianDB;
CREATE DATABASE herovillianDB;

USE herovillianDB;

CREATE TABLE powers(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(15) NOT NULL,
`description` VARCHAR(50) NOT NULL);

CREATE TABLE herovills_powers(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`power_id` int NOT NULL,
`herovill_id` int NOT NULL);

CREATE TABLE herovillians(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(15) NOT NULL,
`description` VARCHAR(50) NOT NULL);

CREATE TABLE herovills_orgs(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`herovill_id` int NOT NULL,
`org_id` int NOT NULL);

CREATE TABLE organizations(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(15) NOT NULL,
`description` VARCHAR(30) NOT NULL,
`contact` VARCHAR(30) NOT NULL,
`loc_id` INT);

CREATE TABLE herovills_sighted(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`herovill_id` int NOT NULL,
`sighted_id` int NOT NULL);

CREATE TABLE sightings(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`date` VARCHAR(15) NOT NULL,
`time` VARCHAR(30) NOT NULL,
`loc_id` INT);

CREATE TABLE locations(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(15) NOT NULL,
`description` VARCHAR(30) NOT NULL,
`address` VARCHAR(50) NOT NULL,
`longitude` DECIMAL(11,8) NOT NULL,
`latitude` DECIMAL(11,8) NOT NULL);

ALTER TABLE `herovills_powers`
ADD CONSTRAINT `herovills_powers_HV` FOREIGN KEY (`herovill_id`) REFERENCES `herovillians` (`id`);
 ALTER TABLE `herovills_powers`
ADD CONSTRAINT `herovills_powers_pow` FOREIGN KEY (`power_id`) REFERENCES `powers` (`id`);

ALTER TABLE `herovills_orgs`
ADD CONSTRAINT `herovills_orgs_HV` FOREIGN KEY (`herovill_id`) REFERENCES `herovillians` (`id`);
ALTER TABLE `herovills_orgs`
ADD CONSTRAINT `herovills_orgs_org` FOREIGN KEY (`org_id`) REFERENCES `organizations` (`id`);

ALTER TABLE `organizations`
ADD CONSTRAINT `org_location` FOREIGN KEY (`loc_id`) REFERENCES `locations` (`id`);

ALTER TABLE `herovills_sighted`
ADD CONSTRAINT `herovills_sighted_HV` FOREIGN KEY (`herovill_id`) REFERENCES `herovillians` (`id`);
ALTER TABLE `herovills_sighted`
ADD CONSTRAINT `herovills_sighted_sight` FOREIGN KEY (`sighted_id`) REFERENCES `sightings` (`id`);

ALTER TABLE `sightings`
ADD CONSTRAINT `sighting_location` FOREIGN KEY (`loc_id`) REFERENCES `locations` (`id`);