--
-- Dumping data for table core_portlet_type
--

INSERT INTO core_portlet_type (id_portlet_type,name,url_creation,url_update,home_class,plugin_name,url_docreate,create_script,create_specific,create_specific_form,url_domodify,modify_script,modify_specific,modify_specific_form) VALUES ('QUIZ_PORTLET', 'module.quiz.games.portlet.name', 'plugins/quiz/modules/games/CreatePortletQuiz.jsp', 'plugins/quiz/modules/games/ModifyPortletQuiz.jsp', 'fr.paris.lutece.plugins.quiz.modules.games.business.portlet.QuizPortletHome', 'quiz-games', 'plugins/quiz/modules/games/DoCreatePortletQuiz.jsp', '/admin/portlet/script_create_portlet.html', '/admin/plugins/quiz/modules/games/portlet_quiz.html', '', 'plugins/quiz/modules/games/DoModifyPortletQuiz.jsp', '/admin/portlet/script_modify_portlet.html', '/admin/plugins/quiz/modules/games/portlet_quiz.html', '');

--
-- Dumping data for table core_style
--

INSERT INTO core_style (id_style,description_style,id_portlet_type,id_portal_component) VALUES (3500,'Quiz style','QUIZ_PORTLET',0);

--
-- Dumping data for table core_style_mode_stylesheet
--

INSERT INTO core_style_mode_stylesheet (id_style,id_mode,id_stylesheet) VALUES (3500,0,3500);

--
-- Dumping data for table core_stylesheet
--

INSERT INTO core_stylesheet (id_stylesheet, description, file_name, source) VALUES (3500, 'Rubrique QUIZ', 'portlet_quiz.xsl', 0x3c3f786d6c2076657273696f6e3d22312e30223f3e0d0a3c78736c3a7374796c6573686565742076657273696f6e3d22312e302220786d6c6e733a78736c3d22687474703a2f2f7777772e77332e6f72672f313939392f58534c2f5472616e73666f726d223e0d0a0d0a09093c78736c3a74656d706c617465206d617463683d22706f72746c6574223e0d0a0909093c78736c3a63686f6f73653e0d0a090909093c78736c3a7768656e20746573743d22737472696e67287175697a2d616374697665293d277472756527223e0d0a0909090909093c64697620636c6173733d22706f72746c657420617070656e642d626f74746f6d223e0d0a090909090909093c68333e0d0a09090909090909093c78736c3a76616c75652d6f662064697361626c652d6f75747075742d6573636170696e673d22796573222073656c6563743d22706f72746c65742d6e616d6522202f3e0d0a090909090909093c2f68333e0d0a090909090909093c64697620636c6173733d22706f72746c65742d636f6e74656e74202d6c75746563652d626f726465722d726164697573223e0d0a09090909090909093c78736c3a6170706c792d74656d706c617465732073656c6563743d227175697a2d706f72746c657422202f3e0d0a090909090909093c2f6469763e0d0a0909090909093c2f6469763e0d0a090909093c2f78736c3a7768656e3e0d0a090909093c78736c3a6f74686572776973653e0d0a090909093c2f78736c3a6f74686572776973653e0d0a0909093c2f78736c3a63686f6f73653e0d0a09093c2f78736c3a74656d706c6174653e0d0a09090d0a09093c78736c3a74656d706c617465206d617463683d227175697a2d706f72746c6574223e0d0a0909093c78736c3a6170706c792d74656d706c617465732073656c6563743d227175697a2d706f72746c65742d636f6e74656e7422202f3e0d0a09093c2f78736c3a74656d706c6174653e0d0a09090d0a09093c78736c3a74656d706c617465206d617463683d227175697a2d706f72746c65742d636f6e74656e74223e0d0a0909093c78736c3a76616c75652d6f662064697361626c652d6f75747075742d6573636170696e673d22796573222073656c6563743d222e22202f3e0d0a09093c2f78736c3a74656d706c6174653e0d0a3c2f78736c3a7374796c6573686565743e0d0a);

/*==============================================================*/
/*	Init  table core_admin_right								*/
/*==============================================================*/
INSERT INTO core_admin_right (id_right,name,level_right,admin_url,description,is_updatable,plugin_name,id_feature_group,icon_url, documentation_url) VALUES ('QUIZ_MANAGEMENT_GAMES','module.quiz.games.adminFeature.quiz_management.name',2,'jsp/admin/plugins/quiz/modules/games/ManageQuiz.jsp','module.quiz.games.adminFeature.quiz_management.description',0,'quiz-games','APPLICATIONS','images/admin/skin/plugins/quiz/quiz.png', 'jsp/admin/documentation/AdminDocumentation.jsp?doc=admin-quiz');


INSERT INTO core_user_right (id_right,id_user) VALUES ('QUIZ_MANAGEMENT_GAMES',1);
INSERT INTO core_user_right (id_right,id_user) VALUES('QUIZ_MANAGEMENT_GAMES',2);

/* Change url of admin feature plugin quizz */
UPDATE core_admin_right set admin_url='jsp/admin/plugins/quiz/modules/games/ManageQuiz.jsp' WHERE id_right = 'QUIZ_MANAGEMENT';