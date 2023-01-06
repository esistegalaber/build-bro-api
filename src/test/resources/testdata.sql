# noinspection SqlWithoutWhereForFile

delete
from deployed_builds;
delete
from deployment_label;
delete
from deployment;
delete
from server;
delete
from build_template_labels;
delete
from build_template;
delete
from build_set_template;
delete
from build_number;
delete
from build_label;
delete
from build;
delete
from branch;
delete
from project;

-- a build count
INSERT INTO build_number (id, branch, number, project)
VALUES (1, 'main', 10, 'backend'),
       (2, 'next', 10, 'backend'),
       (3, 'feature/feature-backend', 10, 'backend'),
       (4, 'main', 10, 'frontend'),
       (5, 'next', 10, 'frontend'),
       (6, 'feature/feature-frontend', 10, 'frontend'),
       (7, 'main', 10, 'backoffice'),
       (8, 'next', 10, 'backoffice'),
       (9, 'feature/feature-backoffice', 10, 'backoffice');

INSERT INTO `build` (`id`, `branch`, `build_number`, `project`)
VALUES (21, 'feature-backend', 1, 'backend'),
       (22, 'feature-backend', 2, 'backend'),
       (23, 'feature-backend', 3, 'backend'),
       (24, 'feature-backend', 4, 'backend'),
       (25, 'feature-backend', 5, 'backend'),
       (26, 'feature-backend', 6, 'backend'),
       (27, 'feature-backend', 7, 'backend'),
       (28, 'feature-backend', 8, 'backend'),
       (29, 'feature-backend', 9, 'backend'),
       (30, 'feature-backend', 10, 'backend'),
       (1, 'main', 1, 'backend'),
       (2, 'main', 2, 'backend'),
       (3, 'main', 3, 'backend'),
       (4, 'main', 4, 'backend'),
       (5, 'main', 5, 'backend'),
       (6, 'main', 6, 'backend'),
       (7, 'main', 7, 'backend'),
       (8, 'main', 8, 'backend'),
       (9, 'main', 9, 'backend'),
       (10, 'main', 10, 'backend'),
       (11, 'next', 1, 'backend'),
       (12, 'next', 2, 'backend'),
       (13, 'next', 3, 'backend'),
       (14, 'next', 4, 'backend'),
       (15, 'next', 5, 'backend'),
       (16, 'next', 6, 'backend'),
       (17, 'next', 7, 'backend'),
       (18, 'next', 8, 'backend'),
       (19, 'next', 9, 'backend'),
       (20, 'next', 10, 'backend'),
       (81, 'feature-backoffice', 1, 'backoffice'),
       (82, 'feature-backoffice', 2, 'backoffice'),
       (83, 'feature-backoffice', 3, 'backoffice'),
       (84, 'feature-backoffice', 4, 'backoffice'),
       (85, 'feature-backoffice', 5, 'backoffice'),
       (86, 'feature-backoffice', 6, 'backoffice'),
       (87, 'feature-backoffice', 7, 'backoffice'),
       (88, 'feature-backoffice', 8, 'backoffice'),
       (89, 'feature-backoffice', 9, 'backoffice'),
       (90, 'feature-backoffice', 10, 'backoffice'),
       (61, 'main', 1, 'backoffice'),
       (62, 'main', 2, 'backoffice'),
       (63, 'main', 3, 'backoffice'),
       (64, 'main', 4, 'backoffice'),
       (65, 'main', 5, 'backoffice'),
       (66, 'main', 6, 'backoffice'),
       (67, 'main', 7, 'backoffice'),
       (68, 'main', 8, 'backoffice'),
       (69, 'main', 9, 'backoffice'),
       (70, 'main', 10, 'backoffice'),
       (71, 'next', 1, 'backoffice'),
       (72, 'next', 2, 'backoffice'),
       (73, 'next', 3, 'backoffice'),
       (74, 'next', 4, 'backoffice'),
       (75, 'next', 5, 'backoffice'),
       (76, 'next', 6, 'backoffice'),
       (77, 'next', 7, 'backoffice'),
       (78, 'next', 8, 'backoffice'),
       (79, 'next', 9, 'backoffice'),
       (80, 'next', 10, 'backoffice'),
       (51, 'feature-frontend', 1, 'frontend'),
       (52, 'feature-frontend', 2, 'frontend'),
       (53, 'feature-frontend', 3, 'frontend'),
       (54, 'feature-frontend', 4, 'frontend'),
       (55, 'feature-frontend', 5, 'frontend'),
       (56, 'feature-frontend', 6, 'frontend'),
       (57, 'feature-frontend', 7, 'frontend'),
       (58, 'feature-frontend', 8, 'frontend'),
       (59, 'feature-frontend', 9, 'frontend'),
       (60, 'feature-frontend', 10, 'frontend'),
       (31, 'main', 1, 'frontend'),
       (32, 'main', 2, 'frontend'),
       (33, 'main', 3, 'frontend'),
       (34, 'main', 4, 'frontend'),
       (35, 'main', 5, 'frontend'),
       (36, 'main', 6, 'frontend'),
       (37, 'main', 7, 'frontend'),
       (38, 'main', 8, 'frontend'),
       (39, 'main', 9, 'frontend'),
       (40, 'main', 10, 'frontend'),
       (41, 'next', 1, 'frontend'),
       (42, 'next', 2, 'frontend'),
       (43, 'next', 3, 'frontend'),
       (44, 'next', 4, 'frontend'),
       (45, 'next', 5, 'frontend'),
       (46, 'next', 6, 'frontend'),
       (47, 'next', 7, 'frontend'),
       (48, 'next', 8, 'frontend'),
       (49, 'next', 9, 'frontend'),
       (50, 'next', 10, 'frontend');

INSERT INTO `build_label` (`id`, `label_key`, `label_value`, `build_id`)
VALUES (1, 'integration-test', 'ok', 1),
       (2, 'jenkins-build', 'http://my-jenkins/5b13db47-0fd7-4d86-b305-b44267b2502d', 1),
       (3, 'revision', '8760f3516abcfa33f62a7e2c26c077d87725b27e9958de75478fbfe8078d8a8e', 1),
       (4, 'revision', '9d64f5039738fb9e90d3afb989f0f9f9f2cd1d37251a2cec8e9e306aeb0ae8d1', 2),
       (5, 'integration-test', 'broken', 2),
       (6, 'jenkins-build', 'http://my-jenkins/bd9a7cec-3f88-4c4f-b35d-2dea49bb2c64', 2),
       (7, 'integration-test', 'ok', 3),
       (8, 'revision', 'b6f670f21c57a56a1a4717e3df5f196e25a1a3c4ef3e66877f9bee9f2debf50f', 3),
       (9, 'jenkins-build', 'http://my-jenkins/7aed032d-98e7-42fc-a22c-46c329639a84', 3),
       (10, 'jenkins-build', 'http://my-jenkins/46b56583-85a6-4315-9d11-32bf5d750476', 4),
       (11, 'revision', '986d006cbdac48d1f3af89b339fa7215299463634fff84297ca3f67ba9839f79', 4),
       (12, 'integration-test', 'broken', 4),
       (13, 'integration-test', 'ok', 5),
       (14, 'jenkins-build', 'http://my-jenkins/294fe7e5-3fcb-474f-a495-b6aa4d7afb84', 5),
       (15, 'revision', 'e6e9b1095b263445eae734ca74c5c9d539a1570f7f5ea1fae14da1fe4827cb3b', 5),
       (16, 'jenkins-build', 'http://my-jenkins/5fd6949c-a285-4138-a1b8-a3cb9b71d9d3', 6),
       (17, 'revision', '12a42ac063ce04ee979d953ef465e131bfc7468a4fa6af001a704b8b9d9e9714', 6),
       (18, 'integration-test', 'broken', 6),
       (19, 'integration-test', 'ok', 7),
       (20, 'revision', '2b5ce56a3c6cf073d8ed9a298ee5953e2163471151c2de8257389588c0b447ae', 7),
       (21, 'jenkins-build', 'http://my-jenkins/8597411b-13c1-4ee3-8853-416902ec8431', 7),
       (22, 'jenkins-build', 'http://my-jenkins/3ecae83e-c106-4af9-9021-8d3f0ebbd890', 8),
       (23, 'integration-test', 'broken', 8),
       (24, 'revision', '9ebdb32aa5fd934523426ad65cc231048514d387346f8c0aa7a5cd3eaa4273a1', 8),
       (25, 'integration-test', 'ok', 9),
       (26, 'jenkins-build', 'http://my-jenkins/5cb77caf-f7fc-4a80-b149-d4ba3e361a23', 9),
       (27, 'revision', 'fe903f3e57405dc360ae7f4390692cdeea8617f9be7f9a4b21154d11e16eb9e4', 9),
       (28, 'jenkins-build', 'http://my-jenkins/356da47e-7f72-4cb6-a3ea-e3ef57162b0e', 10),
       (29, 'integration-test', 'broken', 10),
       (30, 'revision', 'e6859b79b73630f6250f569a127668910a1e1ef3801c80b30dd9faff5bdde214', 10),
       (31, 'revision', '0a8c54132650d1beca829e2d68accdb8e78add8e87d2645ad0522167efad6895', 11),
       (32, 'integration-test', 'ok', 11),
       (33, 'jenkins-build', 'http://my-jenkins/0e235036-a8e9-4798-8bad-1710c141d2b0', 11),
       (34, 'revision', 'badefed498e91b775f074973f6067c385393a000e23005a09d61ddc3fc4dfc42', 12),
       (35, 'jenkins-build', 'http://my-jenkins/8181db5a-e852-4283-a9d3-5854f4548aa3', 12),
       (36, 'integration-test', 'broken', 12),
       (37, 'integration-test', 'ok', 13),
       (38, 'revision', '0f6c489558dbc7164bca61ee78773f1034afe6996c3e359727b6a6b2931bafc6', 13),
       (39, 'jenkins-build', 'http://my-jenkins/1aac8b80-f1e6-48ed-a696-1f2c8c55e8a6', 13),
       (40, 'revision', '53d492d9efab58b9d24d809aa5a0e8ff18f70bccc2e2ab8bfee1df691ad0c449', 14),
       (41, 'jenkins-build', 'http://my-jenkins/f751551b-f5de-4678-ac31-31e4e9eee390', 14),
       (42, 'integration-test', 'broken', 14),
       (43, 'revision', 'ca95784df97560a29fb3eb1b2f64ac0888be0abadec317d90011065298815d72', 15),
       (44, 'integration-test', 'ok', 15),
       (45, 'jenkins-build', 'http://my-jenkins/967ef51c-20cb-4d2e-85cf-966f84ef8435', 15),
       (46, 'jenkins-build', 'http://my-jenkins/daefb9f9-55d0-4a3c-a411-2ee4986fbe65', 16),
       (47, 'revision', 'b892cc87736c644ed2877500011f822b124de689d8ef08a29a357587c8131a3e', 16),
       (48, 'integration-test', 'broken', 16),
       (49, 'revision', '6eb5091adea6fd74009f24f33f95b4753d62696631b1a6790f932a6846049a2f', 17),
       (50, 'integration-test', 'ok', 17),
       (51, 'jenkins-build', 'http://my-jenkins/f3ff6c54-ce38-4c7c-886d-59f75acddb91', 17),
       (52, 'jenkins-build', 'http://my-jenkins/00b1979f-648d-4f77-ab3d-35507f584764', 18),
       (53, 'revision', 'd801a71d85d5b89527c108ac087e4d39f8dcd5930f375206436ac8b3a58ee115', 18),
       (54, 'integration-test', 'broken', 18),
       (55, 'jenkins-build', 'http://my-jenkins/39def01f-dd92-4a5f-bd29-756e233004c7', 19),
       (56, 'integration-test', 'ok', 19),
       (57, 'revision', '5ff50b23bd8c703f2d8e9ed700e6dd5cd05ac32c3e81137443ea4c2fcf0d206e', 19),
       (58, 'revision', '0f5c2488df990d86ca126721e836f027f890b4bd42f23f36153f806f13f4b67f', 20),
       (59, 'jenkins-build', 'http://my-jenkins/ccf230bc-35bc-4045-8804-7a231a42bb41', 20),
       (60, 'integration-test', 'broken', 20),
       (61, 'revision', 'fe623bd5439393b8406d0e3098ac053f487d5a33470db17ff46e668e75761b2b', 21),
       (62, 'integration-test', 'ok', 21),
       (63, 'jenkins-build', 'http://my-jenkins/78d8591e-068b-4804-bb1e-bab1cbe8a1d6', 21),
       (64, 'jenkins-build', 'http://my-jenkins/127d20a1-8a94-4aff-89f4-8eef47d4072a', 22),
       (65, 'revision', '5ec84e5d1b78f522a9c0d23a4024958ccfc42126c8e7e43a2f1858c61eb21ce0', 22),
       (66, 'integration-test', 'broken', 22),
       (67, 'integration-test', 'ok', 23),
       (68, 'jenkins-build', 'http://my-jenkins/84f54660-afc8-4c50-9dcc-bbb57ba93b1e', 23),
       (69, 'revision', '7db2b209f044a1a590df58c97047667409472e68593788abdbd43975d69d4abc', 23),
       (70, 'jenkins-build', 'http://my-jenkins/6989274c-ca3c-47fe-a578-c75c80fd57b1', 24),
       (71, 'revision', '0f52632d66625c9b7da139acbe03d2b97df459e7e088ea1b4154903ad99bf349', 24),
       (72, 'integration-test', 'broken', 24),
       (73, 'integration-test', 'ok', 25),
       (74, 'revision', 'c8967afc9d1336570d5ed28127679d69932bb03d9824293a5665e50dbadcd52d', 25),
       (75, 'jenkins-build', 'http://my-jenkins/f76672f7-951b-4e8d-a392-fa1835e2b5a2', 25),
       (76, 'revision', '5f180abfcc6134cd893cfb722f7e55946d957e640c2655369b56a839c9b3cd4c', 26),
       (77, 'jenkins-build', 'http://my-jenkins/c69e14b3-ff74-454e-86ac-cb99aead6cb3', 26),
       (78, 'integration-test', 'broken', 26),
       (79, 'integration-test', 'ok', 27),
       (80, 'revision', '8946fa9edb88c0502ae6185437c0cde3f0514a0b7a31ddf4cde0e29adcc3f806', 27),
       (81, 'jenkins-build', 'http://my-jenkins/e60bf305-eebf-4525-ab2b-0ef2a30b324e', 27),
       (82, 'revision', '80e9043aceda5008c8d148108f3cd5f04a09226d11a096d93cbfeb9fb01c1f95', 28),
       (83, 'jenkins-build', 'http://my-jenkins/a7d3d110-a187-45cb-b79f-ccbad5f95c08', 28),
       (84, 'integration-test', 'broken', 28),
       (85, 'integration-test', 'ok', 29),
       (86, 'revision', '1530b519af58cc3cb5fb2626dbde1e3464eaf8c7a647be38ec3b139506cd02e1', 29),
       (87, 'jenkins-build', 'http://my-jenkins/989a7215-ea0a-4acf-b0a1-40022674bcd5', 29),
       (88, 'revision', '4b0b3539341b1359a9efd0f17433a258feb7b45bbfc20b494d6a44f2199e8f5a', 30),
       (89, 'jenkins-build', 'http://my-jenkins/7852f5fe-0c9b-43df-9809-d65c4236ba43', 30),
       (90, 'integration-test', 'broken', 30),
       (91, 'revision', 'b02258e52a2470b0593d035f3d862343006427075d11ab64443cf998a59d26d6', 31),
       (92, 'integration-test', 'ok', 31),
       (93, 'jenkins-build', 'http://my-jenkins/20162926-0e18-4523-8637-fcbde1003a08', 31),
       (94, 'jenkins-build', 'http://my-jenkins/5178bdc2-4a47-4f7d-a024-84eabad47085', 32),
       (95, 'integration-test', 'broken', 32),
       (96, 'revision', '08905032877cdf1c5fd5795f1df6039e706ba056e9d52187ae4ac72d7a8519a8', 32),
       (97, 'integration-test', 'ok', 33),
       (98, 'revision', '23028f16dcb991682c0219d6008d79d97f0ba71883cd39a5d98f96e62633f66f', 33),
       (99, 'jenkins-build', 'http://my-jenkins/027d1168-9784-44e9-9ec5-8a5ce65ecfc8', 33),
       (100, 'revision', '0c2f2474587f6a416e6c93bb265d158f56d3ccfa898067aa147c4762aa4ccf71', 34),
       (101, 'jenkins-build', 'http://my-jenkins/ca84d0f2-cd15-42fe-861d-ff8caf7f887c', 34),
       (102, 'integration-test', 'broken', 34),
       (103, 'revision', '25f0a53908ded195a56fdc59d842d044301e336d61244064cc32bfc5c0319f7e', 35),
       (104, 'integration-test', 'ok', 35),
       (105, 'jenkins-build', 'http://my-jenkins/1932be99-b758-4fde-adec-3e93d7d5a393', 35),
       (106, 'revision', 'ecfa340d5a59f3ef7e14d3c648b15477502ad3b181cdf6e21d50a1915495de72', 36),
       (107, 'jenkins-build', 'http://my-jenkins/24ae842b-12d0-4fe1-bcff-58a65ca80686', 36),
       (108, 'integration-test', 'broken', 36),
       (109, 'integration-test', 'ok', 37),
       (110, 'revision', 'a2c80de68f974e4978b9d94dc4e111de632a2122595b425f35e8bde00ce17ccd', 37),
       (111, 'jenkins-build', 'http://my-jenkins/05bd7982-ebe9-4d3a-9a4e-1666f96b6503', 37),
       (112, 'revision', '94d1e098f2687db33ab95953c4c658a55a63ebdd91f7f902429720750dd136d8', 38),
       (113, 'jenkins-build', 'http://my-jenkins/75057c7f-2315-49c8-b4f9-b9ba7917415b', 38),
       (114, 'integration-test', 'broken', 38),
       (115, 'integration-test', 'ok', 39),
       (116, 'revision', '1929692bfbc4cb263f83b7f8ef45b0bcf5228c2ca84d3fbd48add48ca460acc0', 39),
       (117, 'jenkins-build', 'http://my-jenkins/47387862-53e0-418b-8585-68574408fe80', 39),
       (118, 'jenkins-build', 'http://my-jenkins/3ae4bfc1-0b3d-4bd5-981a-a4c165ff0ce1', 40),
       (119, 'integration-test', 'broken', 40),
       (120, 'revision', '656ff23403981fe2085803870b7143b5f0532ca44f038ee461c96c326c74fbef', 40),
       (121, 'integration-test', 'ok', 41),
       (122, 'revision', '94d9bc2bc0d86cc84a2daa545dcf0eec14ad2e9eaadc5bfb2135549198619d60', 41),
       (123, 'jenkins-build', 'http://my-jenkins/123f3f45-aba4-4ef7-a3a8-5f32f1adf33c', 41),
       (124, 'jenkins-build', 'http://my-jenkins/acb93e91-6545-4dd7-88e5-182255816c99', 42),
       (125, 'revision', 'c1dfbd14403a2e53c70dd5ca2cbf8fa1317aebe07ffb778b5f9e92b5e04637f8', 42),
       (126, 'integration-test', 'broken', 42),
       (127, 'integration-test', 'ok', 43),
       (128, 'revision', 'a2c0ed28373d423493de1dc7f2984f0ab11d3da7e2eb03124bf9abf42bc781e8', 43),
       (129, 'jenkins-build', 'http://my-jenkins/a81143d6-7e7d-4742-811e-107d677a6a53', 43),
       (130, 'revision', 'd9fff76c0d5f567cdefb5a89954f1a29120b613c04abbd6223b0cd446b812dd5', 44),
       (131, 'jenkins-build', 'http://my-jenkins/05fb14a6-8204-4b13-8e21-c94c181233f2', 44),
       (132, 'integration-test', 'broken', 44),
       (133, 'integration-test', 'ok', 45),
       (134, 'revision', 'c2d382211dc7ad873b29c50df3b0b885a5d707056d5c26cb49bce222eb629941', 45),
       (135, 'jenkins-build', 'http://my-jenkins/2a78dbdf-0167-4599-bae8-c560e5ab2997', 45),
       (136, 'jenkins-build', 'http://my-jenkins/0b798fed-e9fc-4dea-9ceb-b61e45d57d8f', 46),
       (137, 'integration-test', 'broken', 46),
       (138, 'revision', 'd0daab0ac63202ac2fea971cf9e97d40ee6e1d19c29db29549ac91610f74a6dc', 46),
       (139, 'integration-test', 'ok', 47),
       (140, 'jenkins-build', 'http://my-jenkins/fd0bfa65-952e-4c6d-abe6-51db62a11ea5', 47),
       (141, 'revision', 'c679dc14371091e701b4c35abfb2ddcffb5dd904aea64eaf9af678be0aa06e4b', 47),
       (142, 'revision', '0e5cf3d0511522c9a1f06ac40c546e2215d0c4ff545c25f3c5b9e491f0e33688', 48),
       (143, 'integration-test', 'broken', 48),
       (144, 'jenkins-build', 'http://my-jenkins/fd99d6cc-ede1-4155-b45f-66b9a996fd84', 48),
       (145, 'jenkins-build', 'http://my-jenkins/1311895d-ada0-4f35-84bc-86b1941a97e3', 49),
       (146, 'integration-test', 'ok', 49),
       (147, 'revision', '0b12d00fc8ae462c40a032ce54b4629fe7e712cd3faa68eb39f0316ac505b9e6', 49),
       (148, 'jenkins-build', 'http://my-jenkins/e0cf2a3a-d7e3-4090-b4f4-a65686927d25', 50),
       (149, 'integration-test', 'broken', 50),
       (150, 'revision', 'ed69e1d977e0889131880b80eacc6b9dcdc59083c20918b984aec1fe316a9c4e', 50),
       (151, 'revision', '8ae319f2e1b507653c616427a6f55ce86b2e1f324736e83945d56bad9fb8fb33', 51),
       (152, 'integration-test', 'ok', 51),
       (153, 'jenkins-build', 'http://my-jenkins/cfa5c265-2ead-4270-8644-7faafb526e96', 51),
       (154, 'jenkins-build', 'http://my-jenkins/2f840448-97b6-4d07-b635-52ea43b18ce8', 52),
       (155, 'integration-test', 'broken', 52),
       (156, 'revision', '199bd68794ef5c6816f7848b5b7deefd6ff6de5deeda1125db2f8c34826faca6', 52),
       (157, 'integration-test', 'ok', 53),
       (158, 'revision', 'ee230ab5d817af4a1c62a257a1f05d242aea41978e9a682e2fcdecedad7eab00', 53),
       (159, 'jenkins-build', 'http://my-jenkins/ab42d7e3-bc78-4cf0-8121-0530782e8cff', 53),
       (160, 'jenkins-build', 'http://my-jenkins/276fc716-e9f7-4ae1-9849-b1195bac7f26', 54),
       (161, 'revision', '9691ac6068fc34f619f3b5ec128d9f2dc996cb9c2c9cf245cd5522b03c03ffca', 54),
       (162, 'integration-test', 'broken', 54),
       (163, 'integration-test', 'ok', 55),
       (164, 'revision', '9d2a81a1945eef57b3730431b474d264d8442a31ee0bd61e09c5b0506c5316f0', 55),
       (165, 'jenkins-build', 'http://my-jenkins/abe3f040-176b-40cd-96b2-dc82ecebaaee', 55),
       (166, 'jenkins-build', 'http://my-jenkins/fd32d2f3-dbf8-43a9-9d09-37b120f2097b', 56),
       (167, 'revision', '14768e5ead09d53d710da9c9a17458379080a641d0d4be2f7f85f731f3951bd4', 56),
       (168, 'integration-test', 'broken', 56),
       (169, 'integration-test', 'ok', 57),
       (170, 'revision', 'b019bbb57fa5c6674f5d60f27a42c43cf6c7ad76f288c22c56e161b041a94118', 57),
       (171, 'jenkins-build', 'http://my-jenkins/e841698e-a9ff-43fc-9b67-eda8baefc1b5', 57),
       (172, 'jenkins-build', 'http://my-jenkins/fa274d91-56ea-481c-b1bb-4d44ce59757c', 58),
       (173, 'revision', '65061e6fad11566a890ef96e7784b159404d0fb9f6123cc75545446d3f5b603f', 58),
       (174, 'integration-test', 'broken', 58),
       (175, 'integration-test', 'ok', 59),
       (176, 'jenkins-build', 'http://my-jenkins/cf8c947e-abc8-477e-9135-484119daf268', 59),
       (177, 'revision', 'dd4293677ebf85db922d51e97537d072752d662607387f61dcfcc7f460274825', 59),
       (178, 'revision', 'a93d02d0b77aec5ec9a26b2efbe4e449fc122228d81f63c8e79f54f7b0c15df6', 60),
       (179, 'jenkins-build', 'http://my-jenkins/9d9f8cb4-b258-4c4e-bf04-c4f8e01f210d', 60),
       (180, 'integration-test', 'broken', 60),
       (181, 'integration-test', 'ok', 61),
       (182, 'revision', 'a00ee4b4ef50cd7ed98024350c5395f3b67eb60a0f17ab0a0d6b5e0df38bd210', 61),
       (183, 'jenkins-build', 'http://my-jenkins/767123bf-a370-4463-9833-6ded1e99de55', 61),
       (184, 'jenkins-build', 'http://my-jenkins/cd8e61b0-3a67-42f5-ad48-39fb99fa5e90', 62),
       (185, 'revision', 'b1647cc0dbb59516a1b6fc29bc811cd5917e30bcc79f57706b053f30f1421a82', 62),
       (186, 'integration-test', 'broken', 62),
       (187, 'revision', 'c1f90981e5d3d0b2a38be613dc3168f50c819e2fd8dcf719269ed6bf63f632c7', 63),
       (188, 'integration-test', 'ok', 63),
       (189, 'jenkins-build', 'http://my-jenkins/f429a895-cf71-4052-9e98-9d063aee2d1f', 63),
       (190, 'jenkins-build', 'http://my-jenkins/df596579-fb41-4e89-bd15-f95ac6e88443', 64),
       (191, 'revision', 'a7333d06a0ad3389c3e24b5beaed6e49ace328b92e80b90f3a3100d48d01f29d', 64),
       (192, 'integration-test', 'broken', 64),
       (193, 'revision', 'e543fa27fd1aabd774fb1a47cb73b61ffce6b5650fe16cedbacf5e06fb943dc1', 65),
       (194, 'integration-test', 'ok', 65),
       (195, 'jenkins-build', 'http://my-jenkins/f06134df-4e0a-40ed-8555-c719164c318d', 65),
       (196, 'revision', '945137ef9820a45857eb259de4cbc115143229f0a900692fb9ce76a347f8154c', 66),
       (197, 'jenkins-build', 'http://my-jenkins/b753611e-f129-4344-855b-34804a9bc298', 66),
       (198, 'integration-test', 'broken', 66),
       (199, 'integration-test', 'ok', 67),
       (200, 'revision', '622dfbfe66a3213e436cfb83f3063ea35bdc5788e991137d3d0a8323e7136fd1', 67),
       (201, 'jenkins-build', 'http://my-jenkins/ac143588-e922-4074-8871-c71134e7ba74', 67),
       (202, 'revision', '8e9172961adba373548777c9a3f692d2dfaf7e14c5243b1acc044899db08c7ac', 68),
       (203, 'integration-test', 'broken', 68),
       (204, 'jenkins-build', 'http://my-jenkins/f7d2e9d2-6b18-479c-b317-c6aaea449dbd', 68),
       (205, 'revision', '515c9c441449706dec94a5cffa211ffcf60c4b1fab7673a61c45ddcea744f074', 69),
       (206, 'integration-test', 'ok', 69),
       (207, 'jenkins-build', 'http://my-jenkins/ed8c7a36-2d9a-4a7a-9d82-b77243cf2405', 69),
       (208, 'jenkins-build', 'http://my-jenkins/6e9996dc-e7a2-41ff-9954-486ec8342ee5', 70),
       (209, 'revision', '276e59ef610bd854f88933cd199c8064d0e7fa66ec891e3a79785ab1bb7174b2', 70),
       (210, 'integration-test', 'broken', 70),
       (211, 'integration-test', 'ok', 71),
       (212, 'jenkins-build', 'http://my-jenkins/d6bf1e75-9860-4539-a58d-2dc22003e5a4', 71),
       (213, 'revision', '07054f68d2608c606e00874a430e9c5cbca15e8f760796da89c9eda10c2c3aa8', 71),
       (214, 'jenkins-build', 'http://my-jenkins/9050b5d4-bc93-4a65-87a2-5dad892cfb62', 72),
       (215, 'integration-test', 'broken', 72),
       (216, 'revision', '9ef95a4b6725d8c81d98c8c2460fc1556b9f7ffe0a6cf577df1ebbacc49c3ccd', 72),
       (217, 'revision', 'c7eba34de1b18dd043340b7e271701f20017e71d47f1f9b404a858f7ae0412ee', 73),
       (218, 'integration-test', 'ok', 73),
       (219, 'jenkins-build', 'http://my-jenkins/18d00503-b668-4c16-8a28-d4ca05d76bea', 73),
       (220, 'jenkins-build', 'http://my-jenkins/4fc9ff30-8d80-4906-9470-3eadd3599f91', 74),
       (221, 'revision', 'b41a84999a1c99c0bd62f9153c99f40eaf6fd31a0da29a9d628a6cb375aee885', 74),
       (222, 'integration-test', 'broken', 74),
       (223, 'integration-test', 'ok', 75),
       (224, 'revision', 'e6efc9db213941b639dae3263f952784b578688ccd3e2119f82f9c065f927eb0', 75),
       (225, 'jenkins-build', 'http://my-jenkins/d7eca267-662d-4523-b8bd-cfee40c55d07', 75),
       (226, 'revision', '8bc7a33e95492352d59378f37ffc8fa41e1eb69384757bfe39eb4f08242e2a7a', 76),
       (227, 'jenkins-build', 'http://my-jenkins/a66c1737-3dd2-4bba-82e6-d511357577b6', 76),
       (228, 'integration-test', 'broken', 76),
       (229, 'revision', '7864d4e8dbab093ad65d7819e79a857266217bebf2e1dda8763dfce69c3d6bc7', 77),
       (230, 'integration-test', 'ok', 77),
       (231, 'jenkins-build', 'http://my-jenkins/22fc4a9f-9317-4378-9a6a-cfc69db44ada', 77),
       (232, 'revision', '2c101552d35c0d3fa2f66c3950c7ab19c7d0ca192d70658ae699d65e80d2730f', 78),
       (233, 'integration-test', 'broken', 78),
       (234, 'jenkins-build', 'http://my-jenkins/618c3286-069f-43c2-accc-5deca9c3a3db', 78),
       (235, 'jenkins-build', 'http://my-jenkins/51e99073-02b3-49ce-ae9d-bcb1ac609397', 79),
       (236, 'integration-test', 'ok', 79),
       (237, 'revision', '2d4e087c1ed53791f794c2390fdde176716a3747118bbc5739c94f9b13d529e0', 79),
       (238, 'revision', '8ab1b57a203f95c47dd54eeac67f286ff642fd10799128d80f2187e86f0e2999', 80),
       (239, 'jenkins-build', 'http://my-jenkins/b594a27c-f107-420a-8c24-ded064cf768c', 80),
       (240, 'integration-test', 'broken', 80),
       (241, 'integration-test', 'ok', 81),
       (242, 'jenkins-build', 'http://my-jenkins/285a96d6-2f48-46f9-a0fe-7032f20289ab', 81),
       (243, 'revision', 'f4e65dfb900436f01eada73152b61b2a92e2ba752b5cc12ea8377b6a695e55dc', 81),
       (244, 'integration-test', 'broken', 82),
       (245, 'jenkins-build', 'http://my-jenkins/4338af2c-4805-43e3-8952-0a1a391f98ca', 82),
       (246, 'revision', '2fd5762ef77c84a5ff215a081bb33dd702fbce58a3585b9fc20d845156a31f21', 82),
       (247, 'integration-test', 'ok', 83),
       (248, 'jenkins-build', 'http://my-jenkins/8b1b3839-176e-4c6e-bf05-795d262488fb', 83),
       (249, 'revision', 'd9dd45b73bc0b798b3d3eee93bd300cdf58bbf252f243ea9b38674d417a7dfe6', 83),
       (250, 'jenkins-build', 'http://my-jenkins/b28e6d5e-f248-48eb-bfec-cf27422d0294', 84),
       (251, 'revision', 'da437eab3828dff6bee7c38142cd4742b836349dd0cc766fd813c204a8a0cc48', 84),
       (252, 'integration-test', 'broken', 84),
       (253, 'integration-test', 'ok', 85),
       (254, 'revision', 'f5ddf600d261746ff5eb6a7d0e60d84af147b810c8d2e65ffbf23559e5321c12', 85),
       (255, 'jenkins-build', 'http://my-jenkins/2f6537e2-cf32-42a2-9f0c-572394a6b056', 85),
       (256, 'revision', 'd43e3b81d37e722e58fd65791bde4cd216806ea1da135c8677bd1bdc12780bbe', 86),
       (257, 'jenkins-build', 'http://my-jenkins/84b4c4d7-f990-4fa5-93ea-9ccaf029136d', 86),
       (258, 'integration-test', 'broken', 86),
       (259, 'integration-test', 'ok', 87),
       (260, 'jenkins-build', 'http://my-jenkins/9d8158bf-5c6f-4829-a2e3-d9b48ced693e', 87),
       (261, 'revision', 'a871664744d248d2863fbedd1ac38dcfb03fedf3e9564493678b89119310fb48', 87),
       (262, 'revision', 'a8f0617c99449b37edde87b5de569a1f6d2a8acd683d547895a5890c11855f57', 88),
       (263, 'jenkins-build', 'http://my-jenkins/25037d87-259c-4dd2-a53a-d665439b063d', 88),
       (264, 'integration-test', 'broken', 88),
       (265, 'revision', '811ec49ff958c4cb2c4cb5f4bd9195140c6440d2e6b99ea0f7a44c759b8fcd0f', 89),
       (266, 'integration-test', 'ok', 89),
       (267, 'jenkins-build', 'http://my-jenkins/a4cf21cd-30e1-4f1d-83de-422ef19d0212', 89),
       (268, 'jenkins-build', 'http://my-jenkins/609d3e53-940b-485f-a473-e4e84280a189', 90),
       (269, 'revision', 'd9d7c16b3085404927c61080cb9fbc3cace99b38a49955783225ed1d0d6a2bea', 90),
       (270, 'integration-test', 'broken', 90),
       (271, 'release.name', 'Analog-Amoeba', 10),
       (272, 'release.date', '01/01/2023', 10),
       (273, 'release.version', '1', 10);

-- environments
INSERT INTO build_set_template (id, name)
VALUES (1, 'main');
INSERT INTO build_template (id, branch, project, build_set_template_id, build_number)
VALUES (1, 'main', 'backend', 1, '9');
INSERT INTO build_template (id, branch, project, build_set_template_id)
VALUES (2, 'main', 'frontend', 1);


INSERT INTO build_set_template (id, name)
VALUES (2, 'feature-test-stage-1');

INSERT INTO build_set_template (id, name)
VALUES (3, 'Environment of StageServer');

INSERT INTO project(id, name, active)
VALUES (1, 'backend', 1),
       (2, 'backoffice', 1),
       (3, 'frontend', 1),
       (4, 'abandoned', 0);

INSERT INTO branch(id, name, project_id, active)
VALUES (1, 'main', 1, 1),
       (2, 'next', 1, 1),
       (3, 'feature-backend', 1, 1),
       (4, 'merged-feature-backend', 1, 0),
       (5, 'main', 3, 1),
       (6, 'next', 3, 1),
       (7, 'feature-frontend', 3, 1),
       (8, 'merged-feature-frontend', 3, 0),
       (9, 'main', 2, 1),
       (10, 'next', 2, 1),
       (11, 'feature-backoffice', 2, 1),
       (12, 'merged-feature-backoffice', 2, 0),
       (13, 'main', 4, 1),
       (14, 'merged', 4, 0);

INSERT INTO server(id, name, nick_name, description)
VALUES (1, 'testserver', 'Tester', 'Main Testing Server'),
       (2, 'staging', 'Company Staging', 'Main Staging Server'),
       (3, 'prod-1', 'Prod', 'Prod-1');

INSERT INTO deployment(id, deployed_at, server_id)
VALUES (1, NOW() - INTERVAL 1 DAY, 1);
INSERT INTO deployment(id, deployed_at, server_id)
VALUES (2, NOW() - INTERVAL 2 DAY, 1);
INSERT INTO deployment(id, deployed_at, server_id)
VALUES (3, NOW() - INTERVAL 3 DAY, 1);
INSERT INTO deployment(id, deployed_at, server_id)
VALUES (4, NOW() - INTERVAL 4 DAY, 1);
INSERT INTO deployment(id, deployed_at, server_id)
VALUES (5, NOW() - INTERVAL 5 DAY, 2);
INSERT INTO deployment(id, deployed_at, server_id)
VALUES (6, NOW() - INTERVAL 6 DAY, 1);
INSERT INTO deployment(id, deployed_at, server_id)
VALUES (7, NOW() - INTERVAL 7 DAY, 1);
INSERT INTO deployment(id, deployed_at, server_id)
VALUES (8, NOW() - INTERVAL 8 DAY, 1);
INSERT INTO deployment(id, deployed_at, server_id)
VALUES (9, NOW() - INTERVAL 9 DAY, 1);
INSERT INTO deployment(id, deployed_at, server_id)
VALUES (10, NOW() - INTERVAL 10 DAY, 1);
INSERT INTO deployment(id, deployed_at, server_id)
VALUES (11, NOW() - INTERVAL 11 DAY, 1);

INSERT INTO deployed_builds(deployment_id, build_id)
VALUES (1, 10),
       (1, 40),
       (1, 70),
       (2, 9),
       (2, 39),
       (2, 69),
       (3, 8),
       (3, 38),
       (3, 68),
       (4, 7),
       (4, 37),
       (4, 67),
       (5, 6),
       (5, 36),
       (5, 66),
       (6, 30),
       (6, 60),
       (6, 90),
       (7, 80),
       (7, 50),
       (7, 20),
       (8, 81),
       (8, 51),
       (8, 21),
       (9, 82),
       (9, 52),
       (9, 22),
       (10, 83),
       (10, 53),
       (10, 23),
       (11, 84),
       (11, 54),
       (11, 24)
;
insert into deployment_label(id, label_key, label_value, deployment_id)
VALUES (1, 'part-of-build-set', 'main', 1),
       (2, 'part-of-build-set', 'main', 1),
       (3, 'part-of-build-set', 'main', 1),
       (4, 'part-of-build-set', 'main', 1),
       (5, 'part-of-build-set', 'main', 1),
       (6, 'part-of-build-set', 'feature', 1);
