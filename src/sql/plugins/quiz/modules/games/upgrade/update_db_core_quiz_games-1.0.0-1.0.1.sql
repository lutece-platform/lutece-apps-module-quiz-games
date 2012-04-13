UPDATE core_portlet_type SET 
name='module.quiz.games.portlet.name',
url_creation='plugins/quiz/modules/games/CreatePortletQuiz.jsp',
url_update='plugins/quiz/modules/games/ModifyPortletQuiz.jsp',
home_class='fr.paris.lutece.plugins.quiz.modules.games.business.portlet.QuizPortletHome',
plugin_name='quiz-games',
url_docreate='plugins/quiz/modules/games/DoCreatePortletQuiz.jsp',
create_specific='/admin/plugins/quiz/modules/games/portlet_quiz.html',
url_domodify='plugins/quiz/modules/games/DoModifyPortletQuiz.jsp',
modify_specific='/admin/plugins/quiz/modules/games/portlet_quiz.html'
WHERE id_portlet_type='QUIZ_PORTLET';

UPDATE core_admin_right SET
id_right='QUIZ_MANAGEMENT_GAMES',
name='module.quiz.games.adminFeature.quiz_management.name',
admin_url='jsp/admin/plugins/quiz/modules/games/ManageQuiz.jsp',
description='module.quiz.games.adminFeature.quiz_management.description',
plugin_name='quiz-games',
icon_url='images/admin/skin/plugins/quiz/quiz.png'
WHERE id_right='QUIZ_MANAGEMENT_JUEXCONCOURS';


UPDATE core_user_right SET id_right='QUIZ_MANAGEMENT_GAMES' WHERE id_right='QUIZ_MANAGEMENT_JEUXCONCOURS';