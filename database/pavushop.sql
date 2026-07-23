-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: localhost    Database: pavushop
-- ------------------------------------------------------
-- Server version	8.0.43

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `brands`
--

DROP TABLE IF EXISTS `brands`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `brands` (
  `brand_id` int NOT NULL AUTO_INCREMENT,
  `brand_name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`brand_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brands`
--

LOCK TABLES `brands` WRITE;
/*!40000 ALTER TABLE `brands` DISABLE KEYS */;
INSERT INTO `brands` VALUES (2,'Vascara','Vascara@gmail.com','0915767465'),(3,'Sablanca','Sablanca@gmail.com','0915999999'),(4,'Juno','Juno@gmail.com','0915868565'),(5,'Sneakers','Sneakers@gamil.com','0496586526'),(6,'Rayban','Rayban@gmail.com','0915636598'),(7,'Prada','Prada@gmail.com','0945265869');
/*!40000 ALTER TABLE `brands` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_items`
--

DROP TABLE IF EXISTS `cart_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_items` (
  `id` int NOT NULL AUTO_INCREMENT,
  `quantity` int NOT NULL,
  `total_price` double NOT NULL,
  `unit_price` double NOT NULL,
  `product_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1re40cjegsfvw58xrkdp6bac6` (`product_id`),
  KEY `FKc2p31hvlup1ekik991pkrmmw7` (`user_id`),
  CONSTRAINT `FK1re40cjegsfvw58xrkdp6bac6` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`),
  CONSTRAINT `FKc2p31hvlup1ekik991pkrmmw7` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_items`
--

LOCK TABLES `cart_items` WRITE;
/*!40000 ALTER TABLE `cart_items` DISABLE KEYS */;
/*!40000 ALTER TABLE `cart_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `category_id` int NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'Túi xách'),(2,'Giày'),(3,'Kính'),(4,'Balo');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_details`
--

DROP TABLE IF EXISTS `order_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_details` (
  `order_detail_id` int NOT NULL AUTO_INCREMENT,
  `price` double DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  `order_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  PRIMARY KEY (`order_detail_id`),
  KEY `FKjyu2qbqt8gnvno9oe9j2s2ldk` (`order_id`),
  KEY `FK4q98utpd73imf4yhttm3w0eax` (`product_id`),
  CONSTRAINT `FK4q98utpd73imf4yhttm3w0eax` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`),
  CONSTRAINT `FKjyu2qbqt8gnvno9oe9j2s2ldk` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_details`
--

LOCK TABLES `order_details` WRITE;
/*!40000 ALTER TABLE `order_details` DISABLE KEYS */;
INSERT INTO `order_details` VALUES (2,460,1,368,2,2),(3,460,1,368,3,2),(4,650,1,650,4,19),(5,650,1,585,5,11),(6,750,1,675,6,10),(7,650,1,585,7,5),(8,750,1,675,8,7),(9,300,1,300,8,25),(10,750,1,600,8,13),(11,650,1,585,9,11),(12,650,1,585,10,5),(23,460,1,368,11,2),(24,550,1,522.5,11,8),(25,750,1,675,11,10),(26,300,1,300,12,25),(27,650,1,585,12,11),(28,550,1,522.5,13,8),(29,650,1,585,14,11),(30,455,1,364,14,28),(31,650,7,4550,15,19),(32,460,3,1242,15,24),(33,455,2,728,15,28),(34,500,1,475,15,20),(35,460,5,1840,15,2),(36,650,1,585,15,5),(37,300,1,300,15,25),(38,300,1,300,16,25),(39,650,1,585,16,11),(40,650,1,650,16,19),(41,650,1,650,17,19),(42,300,1,300,18,25),(43,650,1,650,18,19);
/*!40000 ALTER TABLE `order_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `order_date` date DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `receiver` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `FKq0ny5rek18pjqb8a86pnnyt9d` (`user_id`),
  CONSTRAINT `FKq0ny5rek18pjqb8a86pnnyt9d` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'TP. Đông Hà','Túi Xách','2024-09-08','0915767465','Trần Hữu Đồng','Đã thanh toán!',368,1),(2,'TP.  Đông Hà','Túi Xách','2024-09-08','0915767465','Trần Hữu Đồng','Đã thanh toán!',368,1),(3,'TP.  Đông Hà','Demo mô tả','2024-09-08','0915999999','Trần Hữu Đồng','Đã thanh toán!',368,1),(4,'266 Dũng sĩ Thanh Khê -  Đà Nẵng','Demo mô tả','2024-09-08','0915999999','Nguyễn Ngọc Khánh','Đã thanh toán!',650,2),(5,'TP.  Đông Hà','Demo mô tả','2024-09-08','0915999999','Trần Hoài Nam','Đã thanh toán!',585,2),(6,'TP.  Đông Hà','Demo mô tả','2024-09-08','0915999999','Trần Hoài Nam','Đã thanh toán!',675,3),(7,'TP.  Đông Hà','Demo mô tả','2024-09-08','0915999999','Trần Hữu Đồng','Đã thanh toán!',585,3),(8,'TP.  Đông Hà','Demo mô tả','2024-09-08','0915999999','Nguyễn Văn Tú','Đã thanh toán!',1575,3),(9,'TP. Đà Nẵng','Demo mô tả','2024-09-09','0915999999','Trần Hoài Nam','Đang giao dịch!',585,2),(10,'Tp Đà Nẵng','Demo mô tả','2024-09-09','0915999999','Nguyễn Thanh Lam','Đang giao dịch!',585,1),(11,'TP. Đà Nẵng','Demo mô tả','2024-09-10','0915999999','Trần Hoài Nam','Đang giao dịch!',1565.5,1),(12,'Tp. Đà Nẵng','Demo mô tả','2024-09-10','0915767465','Nguyễn Thanh Lam','Đang giao dịch!',885,2),(13,'TP. Đà Nẵng','Demo mô tả','2024-09-10','0915767465','Nguyễn Văn Tú','Đang giao dịch!',522.5,2),(14,'Hoàng Văn Thái - Đà Nẵng','Giao giờ hành chính\n','2025-08-05','0961270671','Phạm Đức Thắng','Đã thanh toán',949,4),(15,'Hoàng Văn Thái - Đà Nẵng',NULL,'2026-07-01','0961270671','Phạm Đức Thắng','Đang Chờ Xử Lý',9720,102),(16,'Hoàng Văn Thái - Đà Nẵng',NULL,'2026-07-01','0961270671','Lê Văn Thành','Đang Chờ Xử Lý',1535,152),(17,'Hoàng Văn Thái - Đà Nẵng',NULL,'2026-07-02','0961270671','Trần Thế Cao','Đang giao dịch',650,152),(18,'Hoàng Văn Thái - Đà Nẵng',NULL,'2026-07-02','0961270671','Trần Mai Lan','Đang giao dịch',950,152);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(2000) DEFAULT NULL,
  `discount` double NOT NULL,
  `entered_date` date DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` double NOT NULL,
  `quantity` int NOT NULL,
  `brand_id` int DEFAULT NULL,
  `category_id` int DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  KEY `FKa3a4mpsfdf4d2y6r8ra3sc8mv` (`brand_id`),
  KEY `FKog2rp4qthbtt2lfyhfo32lsw9` (`category_id`),
  CONSTRAINT `FKa3a4mpsfdf4d2y6r8ra3sc8mv` FOREIGN KEY (`brand_id`) REFERENCES `brands` (`brand_id`),
  CONSTRAINT `FKog2rp4qthbtt2lfyhfo32lsw9` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (2,'Nắp phối viền màu nổi bật, ấn tượng. Tặng kèm quai xích đeo chéo để phối đồ như ví đi tiệc\r\n\r\nChất liệu da tổng hợp cao cấp. Ví phù hợp dùng như ví tiền hoặc đi tiệc, dạo phố',20,'2021-09-15','tui1.jpg','Túi The Maze',460,300,2,1),(5,'Túi xách color block phối nhiều màu thanh lịch, thời thượng\r\n\r\nChi tiết khóa kim loại hiện đại. Phần quai phối màu hài hòa với thân túi. Bên trong có 1 ngăn rộng rãi kèm khóa kéo tiện dụng. Túi có 2 cách đeo: xách tay hoặc sử dụng quai đeo dài để đeo chéo\r\n\r\nChất liệu da tổng hợp cao cấp. Túi phù hợp để đi làm, dạo phố hay đi tiệc',10,'2021-09-06','tui2.jpg','Túi Xách Nhỏ Phối Color Block',650,300,2,1),(6,'Túi hộp hình học phối khóa vân đá lạ mắt, thanh lịch\r\n\r\nThiết kế tối giản nên rất dễ phối đồ. Bên trong gồm 01 ngăn lớn rộng rãi kèm khóa kéo tiện dụng\r\n\r\nĐi kèm với quai bản vừa duyên dáng, hiện đại để đeo chéo\r\n\r\nChất liệu da tổng hợp cao cấp. Túi phù hợp để đi làm, dạo phố hay đi tiệc',10,'2021-09-07','tui3.jpg','Túi Xách Nhỏ Tay Cầm Cách Điệu',500,200,2,1),(7,'Túi xách nhỏ có đáy nắp gập cong nhẹ phối đậm nhạt cá tính\r\n\r\nCó 3 sự lựa chọn về màu sắc ở chiếc túi này. Những màu sắc vừa đơn giản nhưng không kém phần hiện đại rất dễ sử dụng và phối đồ.',10,'2021-09-07','tui4.jpg','Túi Xách Nhỏ Đeo Vai Có 2 Thiết Kế Dây Đeo',750,300,2,1),(8,'Túi xách trung phối viền màu và khóa trang trí kim loại nổi bật\r\n\r\nBên trong túi một ngăn lớn, kèm dây đeo tiện dụng\r\n\r\nChất liệu da tổng hợp cao cấp. Kiểu dáng phù hợp đi làm, đi chơi hay dự tiệc',5,'2021-09-03','tui5.jpg','Túi Xách Trung Buckle Flap',550,300,3,1),(9,'Túi đeo vai gắn charm túi mini nổi bật\r\n\r\nBên trong túi một ngăn lớn, kèm dây đeo tiện dụng\r\n\r\nChất liệu da tổng hợp cao cấp. Kiểu dáng phù hợp đi làm, đi chơi hay dự tiệc',0,'2021-09-15','tui7.jpg','Túi Đeo Vai Gắn Charm Túi Mini',650,300,3,1),(10,'Túi xách nhỏ nắp gập, ngăn ngoài phối khóa kim loại hình maze nổi bật\r\n\r\nBên trong túi có nhiều ngăn nhỏ, kèm dây kim loại phối chi tiết da hiện đại\r\n\r\nChất liệu da tổng hợp cao cấp. Kiểu dáng phù hợp đi làm, đi chơi hay dự tiệc',10,'2020-09-06','tui8.jpg',' Túi Xách Nhỏ Trang Trí Khóa The Maze',750,300,3,1),(11,'Túi xách trung trang trí khóa kim loại raw tạo nét hiện đại, nổi bật\r\n\r\nTúi có 2 ngăn cho bạn thoải mái mang nhiều vật dụng\r\n\r\nChất liệu da tổng hợp cao cấp, phù hợp với đi làm, đi chơi và đi dự tiệc.',10,'2021-09-23','tui9.jpg','Túi Xách Trung Trang Trí Khóa Kim Loại Raw',650,300,3,1),(13,'Túi xách nhỏ dáng hộp, khóa phối đan mây nổi bật, lạ mắt\r\n\r\nThiết kế túi nhỏ gọn, có 2 ngăn riêng biệt đựng được nhiều đồ dùng tiện dụng.\r\n\r\nChất liệu da tổng hợp cao cấp với 3 tông màu dễ chọn.\r\n',20,'2020-07-08','tui10.jpg','Túi Xách Nhỏ Đeo Vai Khóa Mây',750,300,2,1),(14,'Túi xách nhỏ đan cạnh vền trẻ trung, hiện đại\r\n\r\nThiết kế túi kèm dây đeo tiện dụng\r\n\r\nCó nhiều sự lựa chọn về màu sắc để nàng thoải mái phối đồ và tạo phong cách mới',5,'2020-08-20','tui.jpg','Túi Xách Nhỏ Đan Cạnh Vền',500,300,2,1),(15,'Ba lô có thể biến hóa thành túi xách phối màu color block thời thương, hiện đại\r\n\r\nQuai xích phối da có thể đeo nhiều kiểu: ba lô, cầm tay hoặc đeo chéo. Bên trong có 01 ngăn lớn rộng rãi kèm khóa kéo. Nắp gài lạ mắt\r\n\r\nChất liệu da tổng hợp cao cấp, phù hợp mang mọi dịp: đi làm, dạo phố, dự tiệc',0,'2020-08-13','balo1.jpg','Balo Chần Bông 3 Tông Màu',800,300,4,4),(16,'Balo phong đứng với họa tiết dập nổi 3D lạ mắt, nổi bật\r\n\r\nChất liệu da tổng hợp sang trọng, dễ vệ sinh\r\n\r\nDây đeo chắc chắn, phối charm tinh tế',0,'2021-07-15','balo2.jpg','Balo Phom Đứng Hoạ Tiết 3D BL063',650,300,4,4),(17,'Balo kiểu dáng đơn giản, hiện đại gắn charm pixel nổi bật\r\n\r\nBên trong có ngăn đựng lớn, và ngăn đựng nhỏ bên ngoài. Ba lô có quai để xách hoặc đeo.\r\n\r\nChất liệu da tổng hợp cao cấp bền đẹp, dễ vệ sinh, sử dụng nhiều dịp: đi làm, dạo phố',10,'2021-09-15','balo3.jpg','Balo Gắn Charm Pixel',850,300,4,4),(19,'Giày sneakers kiểu dáng năng động, trẻ trung\r\n\r\nCó nhiều tông màu được phối bắt mắt, cá tính \r\n\r\nPhù hợp mang nhiều dịp: đi làm, đi học hay dạo phố',0,'2021-10-01','giay1.jpg','Giày Sneakers Rush Crush',650,300,5,2),(20,'Giày Classic Sneakers 2 sọc màu thời trang\r\n\r\nKiểu dáng thanh lịch và với tông màu chủ đạo đơn giản giúp việc phối đồ dễ dàng nhưng vẫn thời trang.\r\n\r\nĐôi giày được nhấn nhá bằng 2 sọc màu ở phần thân cực kì bắt mắt\r\n\r\nChất liệu da tổng hợp cao cấp, phù hợp mang nhiều dịp: đi làm, đi học hay dạo phố',5,'2021-03-18','giay2.jpg','Giày Classic Side By Side',500,300,5,2),(21,'Giày Sneakers dòng Comfort siêu êm phối màu thời trang\r \r Với công nghệ không đường may, chất liệu đặc biệt giúp ôm gọn chân, mang lại cảm giác êm ái, thoải mái trong lúc hoạt động.\r \r Đặc biệt thoáng khí nhờ lỗ dệt chạy suốt thân giày, giúp chân dễ chịu, khử mùi tự nhiên. ',10,'2021-09-10','giay3.jpg','Giày Comfy Moon Walk',550,300,5,2),(22,'Giày oxford đế thể thao năng động, thanh lịch\r\n\r\nThân giày cắt lazer đục lỗ làm điểm nhấn thú vị. Đế cao 3cm siêu êm, siêu nhẹ\r\n\r\nChất liệu da tổng hợp cao cấp, phù hợp với nhiều dịp: đi làm, đi chơi',15,'2021-02-18','giay4.jpg','Giày Thể Thao Phong Cách Oxford',600,300,5,2),(23,'Mắt kính khung viền nhựa cao cấp\r\n\r\nTròng nâu, đen, đỏ chống tia UVA/UVB, TAC phân cực\r\n\r\nThích hợp phối với nhiều trang phục diện đi chơi, đi biển, ....',10,'2021-06-24','kinh1.jpg','Mắt Kính Mắt Mèo Nhựa Phối Kim Loại',300,300,6,3),(24,'Mắt kính dạng tròn viền nhựa lạ mắt\r\n\r\nChất liệu gọng kim loại cao cấp, thanh mảnh\r\n\r\nThích hợp diện nhiều dịp khác nhau như đi chơi, đi du dịch...',10,'2021-08-11','kinh2.jpg','Mắt Kính Tròn Kim Loại Phối Nhựa',460,300,6,3),(25,'Mắt kính vuông kim loại phối viền nhựa nổi bật\r\n\r\nTròng nâu đỏ, đen, hồng chống tia UVA/UVB, TAC phân cực\r\n\r\nHộp kính tam giác da PU chống nước, nắp nam châm và kèm khăn lau kính',0,'2021-09-24','kinh3.jpg','Mắt Kính Vuông Kim Loại Phối Nhựa',300,300,6,3),(26,'Mắt kính ovan cá tính, sành điệu\r\n\r\nChất liệu gọng kim loại cao cấp, thanh mảnh\r\n\r\nChống tia UV cao và dễ dàng phối hợp với nhiều loại trang phục khác nhau',0,'2021-09-15','kinh4.jpg','Mắt Kính Ovan Gọng Kim Loại Kép',350,300,7,3),(27,'Mắt kính vuông kim loại nổi bật\r\n\r\nĐệm mũi silicon mềm không in hằn\r\n\r\nHộp kính tam giác da PU chống nước, nắp nam châm và kèm khăn lau kính',5,'2021-09-10','kinh5.jpg','Mắt Kính Vuông Kim Loại',320,200,7,3),(28,'Kính mát vuông hiện đại, trẻ trung\r\n\r\nMàu đen và hồng, phân cực chống tia UV và chống chói\r\n\r\nChất liệu gọng kim loại cao cấp',20,'2021-11-17','kinh6.jpg','Mắt Kính Vuông Kim Loại Phối Nhựa',455,300,7,3),(29,'Mắt kính tròn viền khung kim loại mảnh dập nổi\r\n\r\nTròng xanh, hồng (tráng gương) TAC phân cực, chống tia UVA/UVB\r\n\r\nĐệm mũi silicon mềm không in hằn\r\n\r\nHộp kính tam giác da PU chống nước, nắp nam châm và kèm khăn lau kính',10,'2021-09-04','kinh7.jpg','Mắt Kính Tròn Tràn Viền Kim Loại',350,300,3,3);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `refresh_tokens`
--

DROP TABLE IF EXISTS `refresh_tokens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `refresh_tokens` (
  `id` bigint NOT NULL,
  `refresh_token` varchar(10000) NOT NULL,
  `revoked` bit(1) DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKnpiq3a870qyx0ilrx2gvfuiee` (`user_id`),
  CONSTRAINT `FKnpiq3a870qyx0ilrx2gvfuiee` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refresh_tokens`
--

LOCK TABLES `refresh_tokens` WRITE;
/*!40000 ALTER TABLE `refresh_tokens` DISABLE KEYS */;
INSERT INTO `refresh_tokens` VALUES (352,'eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ0aGFuZ3BoYW0iLCJzdWIiOiJ0aGFuZyIsImV4cCI6MTc2MDI5NTU2NSwiaWF0IjoxNzU4OTk5NTY1LCJzY29wZSI6IlJFRlJFU0hfVE9LRU4ifQ.FpImB2CG_OvSFHsAbm8FFd_o-DilK48iyaxhSHF4AbBLymMxPwAvJtO0dT9AFS87yDiD-39V90eCDZYoXzeQLtBnUbzzKQQDbj_chDJ9jNu_b_yDB8uIcxehACzY5Ocol3MOxY-i6t1PLK8vTV8MNK7aL85JEmCl74qgP5lvy449nr6GD5SeZGOfFYdQe3mTqo7FIABvzwDmZFBjRK4ES5MPvNFRz745snahzvjLuQoXlgorqWxDNeDLRgmUdu2OckJPBXcy2ORbBHIQmSHETnLjZ55TocoL7j-N_Gg7nVCSBgNmq4UpOZ-nFxD8IaSlGV_S9MjmeAcWdHYr2ClsXg',_binary '\0',4),(406,'eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ0aGFuZ3BoYW0iLCJzdWIiOiJhZG1pbiIsImV4cCI6MTc2MjAxODQxNSwiaWF0IjoxNzYwNzIyNDE1LCJzY29wZSI6IlJFRlJFU0hfVE9LRU4ifQ.FkjjUqJdTyJr7f80atSveXjPHDa0ic6VphPRC1fpgs5Wo1eiIzxMzHd78o_NcXnvSqct-W5MrWQ9kOJYEgnn5E7g8DRRtEEEqFwgL6ExMZarygCaw6nV4o6PhJgSzzrss9Jb2wWC5twlQbwfQZ7SAFFO9J95iyGKaNxpjMFs_VO0bkOx6bKTl8EB68LEhxl4NMlx7lL5RVMAwLza3tZMHbniWM36uFlGSxMYoxcmv9G6U316NTP6gDRHyjlX7JXxknf15kjoc8EzXeHrrZBzyDQJ_KrXiRg2hpMf1eP9urONugdq7GxsTsFZn3rQPqIlqq9PBgiSJW2roORoVI90Zw',_binary '\0',102),(452,'eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ0aGFuZ3BoYW0iLCJzdWIiOiJhZG1pbiIsImV4cCI6MTc2Mjc4NjY3NCwiaWF0IjoxNzYxNDkwNjc0LCJzY29wZSI6IlJFRlJFU0hfVE9LRU4ifQ.mtE66nIw6wPZzwFuBq8vvK7hsj-Lg0T8kBuOnnsxMYZVDO96bZX2s_4TzdEaOottZXJPTEOXYPctCDIfYz3RULtaCQKYf3vPSLqSDVb5dSWEWmdlgEkoLF4kQTdlRPgVj9pPqHvdqg5RnoTrWjplaRVFPaWqf-5o43yzmgNvzFgNMoxAnPKWr1bgueo4XDLBdRvXxU51OJiRlm7C5fRGgWBVx62X6pvyAtJt1KXV8L7KllNNwcaW72upL-gz_W2fTSgf4K4GEW51tzTC9SFheMAQUAhOGkL7QNgaYx3KRyHaw-w-QTvfQG3phCJBJAV_-nFMbY94onIpLfUrKyIAXg',_binary '\0',102),(902,'eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ0aGFuZ3BoYW0iLCJzdWIiOiJhZG1pbiIsImV4cCI6MTc4NDM2MDcyOSwiaWF0IjoxNzgzMDY0NzI5LCJzY29wZSI6IlJFRlJFU0hfVE9LRU4ifQ.KJUE1DxYdlSL1w3w5u23mGKPEmSv14iZoasX34LaAffSVOxZ6obO2ZwqEeIgCbo-GPJKf9z-B6_DHu2wOVqM55FRp6olNzo-gh32DxH23WBcJPZyRlEDyG6SBT6tsh6NA2uSZxwPXA1cmtrcSSz2T2RvH58hEpSlYGAlWtauTTYXEiQugRDwS74-mM8Q2lghFanbV0Q7apT7HKAxrCzcyDu3yaF-0azMMSFvQr0IeacTOkJNHrcWgRuvNBZn53vX2NqE68TAfd2erce5LCJn10I81Cw0BSG6JK6tWU5FzJegIlgFjjOe8gSHenUqaitYdGxNNkAm2nxgSaLdjC1tFg',_binary '\0',102);
/*!40000 ALTER TABLE `refresh_tokens` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `refresh_tokens_seq`
--

DROP TABLE IF EXISTS `refresh_tokens_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `refresh_tokens_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refresh_tokens_seq`
--

LOCK TABLES `refresh_tokens_seq` WRITE;
/*!40000 ALTER TABLE `refresh_tokens_seq` DISABLE KEYS */;
INSERT INTO `refresh_tokens_seq` VALUES (1001);
/*!40000 ALTER TABLE `refresh_tokens_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ADMIN'),(2,'USER');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_info`
--

DROP TABLE IF EXISTS `user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_info` (
  `id` int NOT NULL,
  `email_id` varchar(255) NOT NULL,
  `full_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKnd4xxe4sfscx08oods9gi8y2v` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_info`
--

LOCK TABLES `user_info` WRITE;
/*!40000 ALTER TABLE `user_info` DISABLE KEYS */;
INSERT INTO `user_info` VALUES (1,'phamduc1000@gmail.com','Phạm Đức Thắng','123123','thangpham'),(2,'ngoquocduan@gmail.com','Ngô Quốc Duẩn','123123','duan'),(3,'tranlananh@gmail.com','Trần Lan Anh','123123','lananh'),(4,'phamduc1112@gmail.com','Phạm Đỗ Đức Thắng','$2a$10$nUYIXU1sBeirc2midUarseOUszbhU7FLnP2Ze8RqJpm7d0qYakG1y','thang'),(52,'duc@gmail.com','Phạm Văn Đức','$2a$10$.loCeM23PMIpb4vXdd6gWeL55qjHmgQlZVR/3LdwciAxn/foaKvbu','duc'),(102,'admin@gmail.com','ADMIN','$2a$10$w.LaYms6Gs4d3JY93h2GT.Y/oOVAqzHNwCM7XfdBo/iaQ2vn2RlsW','admin'),(152,'phamduc2299@gmail.com','Phan Văn Hoá','$2a$10$HpSHWM..gJAxEF3sN4AD7.AZGN08Nf1SRm59CXyizI9/3GVNXrVRi','user1');
/*!40000 ALTER TABLE `user_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_info_seq`
--

DROP TABLE IF EXISTS `user_info_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_info_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_info_seq`
--

LOCK TABLES `user_info_seq` WRITE;
/*!40000 ALTER TABLE `user_info_seq` DISABLE KEYS */;
INSERT INTO `user_info_seq` VALUES (251);
/*!40000 ALTER TABLE `user_info_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  KEY `FKh8ciramu9cc9q3qcqiv4ue8a6` (`role_id`),
  KEY `FK2iy1s5r3nifbmix9n24lj7gqc` (`user_id`),
  CONSTRAINT `FK2iy1s5r3nifbmix9n24lj7gqc` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`id`),
  CONSTRAINT `FKh8ciramu9cc9q3qcqiv4ue8a6` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (4,2),(52,2),(102,1),(152,2);
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-07-09 13:44:39
