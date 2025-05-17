CREATE SCHEMA `ssafytrip`;

USE `ssafytrip`;

CREATE TABLE Members (
    email VARCHAR(40) NOT NULL PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(20) NOT NULL,
    title VARCHAR(30),
    exp BIGINT DEFAULT 0,
    created_at DATETIME DEFAULT NOW(),
    is_admin TINYINT(1) DEFAULT 0 COMMENT '0:MEMBER, 1:ADMIN',
    mode TINYINT(1) DEFAULT 0 COMMENT '0:OFF, 1:ON',
    is_deleted TINYINT(1) DEFAULT 0 COMMENT '0:FALSE, 1:TRUE',
    profile_image_url TEXT
);

CREATE TABLE Posts (
    post_id INT AUTO_INCREMENT PRIMARY KEY,
    user_email VARCHAR(40) NOT NULL,
    plan_id INT NOT NULL,
    title VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    created_at DATETIME DEFAULT NOW(),
    updated_at DATETIME,
    category ENUM('NORMAL', 'NOTICE') DEFAULT 'NORMAL'
);

CREATE TABLE Likes (
    like_id INT AUTO_INCREMENT PRIMARY KEY,
    post_id INT NOT NULL,
    member_email VARCHAR(40) NOT NULL
);

CREATE TABLE Comments (
    comment_id INT AUTO_INCREMENT PRIMARY KEY,
    post_id INT NOT NULL,
    member_email VARCHAR(40) NOT NULL,
    content VARCHAR(1000) NOT NULL,
    created_at DATETIME DEFAULT NOW(),
    updated_at DATETIME
);

CREATE TABLE Plans (
    plan_id INT AUTO_INCREMENT PRIMARY KEY,
    member_email VARCHAR(40) NOT NULL,
    title VARCHAR(200),
    description VARCHAR(200),
    start_date DATETIME,
    end_date DATETIME,
    created_at DATETIME DEFAULT NOW(),
    is_start TINYINT(1) DEFAULT 0
);

CREATE TABLE Routes (
    route_id INT AUTO_INCREMENT PRIMARY KEY,
    plan_id INT NOT NULL,
    day INT,
    sequence INT
);

CREATE TABLE Plans_Routes (
    plan_id INT NOT NULL,
    route_id INT NOT NULL
);

CREATE TABLE Follows (
    following_email VARCHAR(40),
    follow_email VARCHAR(40),
    status ENUM('ACCEPTED', 'DENIED', 'APPLIED') NOT NULL DEFAULT 'APPLIED'
);

CREATE TABLE Titles (
    title_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE Members_Titles (
    member_email VARCHAR(40) NOT NULL,
    title_id INT NOT NULL,
    created_at DATETIME DEFAULT NOW()
);

CREATE TABLE Quests (
    location_quest_id INT AUTO_INCREMENT PRIMARY KEY,
    attraction_id INT NOT NULL,
    member_email VARCHAR(40) NOT NULL,
    title VARCHAR(100),
    quest_description VARCHAR(10000) NULL,
    difficulty ENUM('EASY', 'MEDIUM', 'HARD') DEFAULT 'MEDIUM',
	created_at	DATETIME NOT NULL DEFAULT NOW(),
    is_private TINYINT(1) DEFAULT 0,
    stamp_image_url VARCHAR(100) NOT NULL,
    stamp_description TEXT
);

CREATE TABLE Members_Quests (
    member_email VARCHAR(40) NOT NULL,
    location_quest_id INT NOT NULL,
    is_completed ENUM('YET', 'COMPLETE') DEFAULT 'YET',
    created_at DATETIME DEFAULT NOW()
);