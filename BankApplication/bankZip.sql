/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 5.5.41 : Database - mukulbankapp
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mukulbankapp` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `mukulbankapp`;

/*Table structure for table `customer` */

DROP TABLE IF EXISTS `customer`;

CREATE TABLE `customer` (
  `name` varchar(100) DEFAULT NULL,
  `account_no` varchar(10) DEFAULT NULL,
  `phone` varchar(10) DEFAULT NULL,
  `amount` varchar(10) DEFAULT NULL,
  `password` varchar(10) DEFAULT NULL,
  `active` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `customer` */

insert  into `customer`(`name`,`account_no`,`phone`,`amount`,`password`,`active`) values 
('Mukul','1001001010','1029385474','10000','muk1010','false'),
('Deepak','1001002222','1029837563','1000000000','dee2222','true'),
('Rahul','1001003333','9999977777','16000','rah3333','true'),
('Gagan','1001004444','9876543210','999000','gag4444','false');

/*Table structure for table `statement` */

DROP TABLE IF EXISTS `statement`;

CREATE TABLE `statement` (
  `account_no` varchar(10) DEFAULT NULL,
  `dept_width` varchar(10) DEFAULT NULL,
  `amount` varchar(10) DEFAULT NULL,
  `comment` varchar(100) DEFAULT NULL,
  `date1` varchar(10) DEFAULT NULL,
  `time1` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `statement` */

insert  into `statement`(`account_no`,`dept_width`,`amount`,`comment`,`date1`,`time1`) values 
('1001002222','deposit','40000','cash','20/05/2023','01:32:07'),
('1001002222','Withdraw','50000','cash','20/05/2023','01:39:31'),
('1001001010','withdraw','10000','10000rs transferred to 1001002222','21/05/2023','22:45:05'),
('1001002222','deposit','10000','10000rs deposited by Mukulfrom1001001010','21/05/2023','22:45:05'),
('1001002222','deposit','1000000000','cash','24/05/2023','18:25:45'),
('1001004444','Withdraw','1000','cheque','24/05/2023','18:27:57'),
('1001002222','withdraw','10000','10000rs transferred to 1001003333','24/05/2023','18:31:44'),
('1001003333','deposit','10000','10000rs deposited by Deepakfrom1001002222','24/05/2023','18:31:44');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
