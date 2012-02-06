<%@ page errorPage="../../../../ErrorPage.jsp" %>

<jsp:useBean id="quizPortlet" scope="session" class="fr.paris.lutece.plugins.quiz.modules.jeuxconcours.web.portlet.QuizPortletJspBean" />

<%
	quizPortlet.init( request, quizPortlet.RIGHT_MANAGE_ADMIN_SITE );
    response.sendRedirect( quizPortlet.doModify( request )   );
%>


