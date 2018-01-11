CREATE TABLE `t_user` (
  `oid` varchar(32) NOT NULL,
	`gusername` varchar(32),
  `gmobile` varchar(11) NOT NULL,
  `gemail` varchar(32) NOT NULL,
  `gpassword` varchar(50) NOT NULL,
	`glev1` varchar(2) DEFAULT '00',
	`glev2` varchar(2) DEFAULT '00',
	`glev3` varchar(2) DEFAULT '00',
  `ts` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_staff` (
  `oid` varchar(32) NOT NULL,
	`gusername` varchar(32),
  `gmobile` varchar(11) NOT NULL,
  `gemail` varchar(32) NOT NULL,
  `gpassword` varchar(50) NOT NULL,
	`cid` varchar(32) NOT NULL,
	`glev1` varchar(2) DEFAULT '00',
	`glev2` varchar(2) DEFAULT '00',
	`glev3` varchar(2) DEFAULT '00',
  `ts` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_company` (
  `oid` varchar(32) NOT NULL,
  `gcompany` varchar(32) NOT NULL,
	`glev1` varchar(2) DEFAULT '00',
	`glev2` varchar(2) DEFAULT '00',
	`glev3` varchar(2) DEFAULT '00',
  `ts` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_companyinfo` (
  `oid` varchar(32) NOT NULL,
  `gcontact` varchar(32),
	`gcity` varchar(32),
	`gaddress` varchar(50),
	`ginfo` varchar(200),
	`gmould` varchar(50),
	`cid` varchar(32) NOT NULL,
  `ts` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
