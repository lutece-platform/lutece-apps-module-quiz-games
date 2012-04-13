<%@ page errorPage="../../../../ErrorPage.jsp" %>
<jsp:include page="../../../../AdminHeader.jsp" />

<jsp:useBean id="rand" scope="session" class="fr.paris.lutece.plugins.quiz.modules.games.web.RandJspBean" />

<% rand.init( request, rand.RIGHT_MANAGE_QUIZ ); %>
<%= rand.getModifyQuiz ( request ) %>

<%@ include file="../../../../AdminFooter.jsp" %>
