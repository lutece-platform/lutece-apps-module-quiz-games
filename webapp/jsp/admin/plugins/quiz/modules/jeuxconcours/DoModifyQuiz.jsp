<%@ page errorPage="../../../../ErrorPage.jsp" %>

<jsp:useBean id="rand" scope="session" class="fr.paris.lutece.plugins.quiz.modules.jeuxconcours.web.RandJspBean" />

<%
    rand.init( request, rand.RIGHT_MANAGE_QUIZ );
    response.sendRedirect( rand.doModifyQuiz( request ) );
%>


