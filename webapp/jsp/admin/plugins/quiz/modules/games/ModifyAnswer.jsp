<%@ page errorPage="../../../../ErrorPage.jsp" %>
<jsp:include page="../../../../AdminHeader.jsp" />

<jsp:useBean id="quizGame" scope="session" class="fr.paris.lutece.plugins.quiz.modules.games.web.RandJspBean" />

<% quizGame.init( request, quizGame.RIGHT_MANAGE_QUIZ ); %>
<%= quizGame.getModifyAnswer ( request ) %>

<%@ include file="../../../../AdminFooter.jsp" %>