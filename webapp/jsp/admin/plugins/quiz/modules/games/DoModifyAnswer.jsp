<%@ page errorPage="../../../../ErrorPage.jsp" %>

<jsp:useBean id="quizGame" scope="session" class="fr.paris.lutece.plugins.quiz.modules.games.web.RandJspBean" />

<%
    quizGame.init( request, quizGame.RIGHT_MANAGE_QUIZ );
    response.sendRedirect( quizGame.doModifyAnswer( request ) );
%>


