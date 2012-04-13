<%@ page errorPage="../../../../ErrorPage.jsp" %>
<jsp:include page="../../../../PortletAdminHeader.jsp" />

<jsp:useBean id="quizPortlet" scope="session" class="fr.paris.lutece.plugins.quiz.modules.games.web.portlet.QuizPortletJspBean" />


<% quizPortlet.init( request, quizPortlet.RIGHT_MANAGE_ADMIN_SITE ); %>
<%= quizPortlet.getCreate ( request ) %>

<%@ include file="../../../../AdminFooter.jsp" %>