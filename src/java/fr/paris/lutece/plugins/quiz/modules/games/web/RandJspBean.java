/*
 * Copyright (c) 2002-2017, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.quiz.modules.games.web;

import fr.paris.lutece.plugins.quiz.business.Answer;
import fr.paris.lutece.plugins.quiz.business.AnswerHome;
import fr.paris.lutece.plugins.quiz.business.QuestionGroupHome;
import fr.paris.lutece.plugins.quiz.business.Quiz;
import fr.paris.lutece.plugins.quiz.business.QuizHome;
import fr.paris.lutece.plugins.quiz.business.QuizQuestion;
import fr.paris.lutece.plugins.quiz.business.QuizQuestionHome;
import fr.paris.lutece.plugins.quiz.modules.games.business.QuizParticipant;
import fr.paris.lutece.plugins.quiz.modules.games.service.RandService;
import fr.paris.lutece.plugins.quiz.web.QuizJspBean;
import fr.paris.lutece.portal.service.admin.AdminUserService;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.portal.web.constants.Messages;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.date.DateUtil;
import fr.paris.lutece.util.html.HtmlTemplate;
import fr.paris.lutece.util.url.UrlItem;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;


/**
 * This class provides the user interface to manage quiz features ( manage,
 * create, modify, remove)
 */
public class RandJspBean extends QuizJspBean
{
    private static final long serialVersionUID = 8191245123673411012L;
    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_RAND_LIST = "module.quiz.games.rand_list.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MANAGE_QUIZ = "quiz.manage_quiz.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_QUIZ = "quiz.create_quiz.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_QUIZ = "quiz.modify_quiz.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MANAGE_QUESTIONS = "quiz.manage_questions.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_QUESTION = "quiz.create_question.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_ANSWER = "quiz.create_answer.pageTitle";
    // Properties
    private static final String PROPERTY_CONFIRM_DELETE_QUIZ = "quiz.remove_quiz.confirmRemoveQuiz";
    private static final String PROPERTY_LABEL_NO = "portal.util.labelNo";
    private static final String PROPERTY_LABEL_YES = "portal.util.labelYes";
    // Messages
    private static final String MESSAGE_NB_WIN_NOT_A_NUMBER = "module.quiz.games.rand_list.message.nb_win.not_a_number";
    private static final String MESSAGE_NB_WIN_MANDATORY = "module.quiz.games.rand_list.message.mandatory";
    private static final String MESSAGE_NB_WIN_SUPERIOR_TO_NB_VALID_PARTICIPANT = "module.quiz.games.rand_list.message.nbWinSupToNbValid";
    private static final String MESSAGE_CANNOT_DELETE_QUIZ = "quiz.message_quiz.cannotDeleteQuiz";
    //Parameters
    private static final String PARAMETER_QUIZ_ID = "quiz_id";
    private static final String PARAMETER_NB_WIN = "nb_win";
    private static final String PARAMETER_ASK_GENERATE = "ask_generate";
    private static final String PARAMETER_QUIZ_NAME = "quiz_name";
    private static final String PARAMETER_ACTIVE_CAPTCHA = "active_captcha";
    private static final String PARAMETER_INTRODUCTION = "quiz_introduction";
    private static final String PARAMETER_CONCLUSION = "quiz_conclusion";
    private static final String PARAMETER_DATE_BEGIN_DISPONIBILITY = "date_begin_disponibility";
    private static final String PARAMETER_DATE_END_DISPONIBILITY = "date_end_disponibility";
    private static final String PARAMETER_QUIZ_STATUS = "quiz_status";
    private static final String PARAMETER_ACTIVE_REQUIREMENT = "active_requirement";
    private static final String PARAMETER_QUESTION_ID = "question_id";
    private static final String PARAMETER_ANSWER_ID = "answer_id";
    private static final String PARAMETER_QUESTION_LABEL = "question_label";
    private static final String PARAMETER_EXPLAINATION = "explaination";
    // Templates
    private static final String TEMPLATE_RAND_LIST = "admin/plugins/quiz/modules/games/rand_list.html";
    private static final String TEMPLATE_MANAGE_QUIZ = "admin/plugins/quiz/modules/games/manage_quiz.html";
    private static final String TEMPLATE_CREATE_QUIZ = "admin/plugins/quiz/modules/games/create_quiz.html";
    private static final String TEMPLATE_MODIFY_QUIZ = "admin/plugins/quiz/modules/games/modify_quiz.html";
    private static final String TEMPLATE_MANAGE_QUESTIONS = "admin/plugins/quiz/modules/games/manage_questions.html";
    private static final String TEMPLATE_CREATE_QUESTION = "admin/plugins/quiz/modules/games/create_question.html";
    private static final String TEMPLATE_MODIFY_QUESTION = "admin/plugins/quiz/modules/games/modify_question.html";
    private static final String TEMPLATE_CREATE_ANSWER = "admin/plugins/quiz/modules/games/create_answer.html";
    private static final String TEMPLATE_MODIFY_ANSWER = "admin/plugins/quiz/modules/games/modify_answer.html";

    // Markers
    private static final String MARK_QUIZ = "quiz";
    private static final String MARK_LIST_WIN_PARTICIPANT = "list_win_participant";
    private static final String MARK_ERROR = "error";
    private static final String MARK_QUIZ_LIST = "quiz_list";
    private static final String MARK_WEBAPP_URL = "webapp_url";
    private static final String MARK_LOCALE = "locale";
    private static final String MARK_QUESTION = "question";
    private static final String MARK_QUESTIONS_LIST = "questions_list";
    private static final String MARK_ANSWER = "answer";
    private static final String MARK_ANSWER_LIST = "answer_list";
    private static final String MARK_YESNO_LIST = "yesno_list";
    // Jsp
    private static final String JSP_MANAGE_QUIZ = "jsp/admin/plugins/quiz/modules/games/ManageQuiz.jsp";
    private static final String JSP_DO_REMOVE_QUIZ = "jsp/admin/plugins/quiz/modules/games/DoRemoveQuiz.jsp";
    //Urls
    private static final String JSP_URL_MANAGE_QUIZ = "ManageQuiz.jsp";
    private static final String JSP_URL_MANAGE_QUESTIONS = "ManageQuestions.jsp";
    private static final String JSP_URL_MODIFY_QUESTION = "ModifyQuestion.jsp";

    private final RandService _randService = SpringContextService.getContext( ).getBean( RandService.class );

    /**
     * Returns quiz management form
     * 
     * @param request The Http request
     * @return Html form
     */
    @Override
    public String getManageQuiz( HttpServletRequest request )
    {
        setPageTitleProperty( PROPERTY_PAGE_TITLE_MANAGE_QUIZ );

        Collection<Quiz> listQuiz = QuizHome.findAll( getPlugin( ) );

        HashMap<String, Object> model = new HashMap<String, Object>( );
        model.put( MARK_QUIZ_LIST, listQuiz );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MANAGE_QUIZ, getLocale( ), model );

        return getAdminPage( template.getHtml( ) );
    }

    /**
     * Returns the Quiz creation form
     * 
     * @param request The Http request
     * @return Html creation form
     */
    @Override
    public String getCreateQuiz( HttpServletRequest request )
    {
        setPageTitleProperty( PROPERTY_PAGE_TITLE_CREATE_QUIZ );

        HashMap<String, Object> model = new HashMap<String, Object>( );
        model.put( MARK_WEBAPP_URL, AppPathService.getBaseUrl( request ) );
        model.put( MARK_LOCALE, AdminUserService.getLocale( request ).getLanguage( ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_CREATE_QUIZ, getLocale( ), model );

        return getAdminPage( template.getHtml( ) );
    }

    /**
     * Returns the form for quiz modification
     * 
     * @param request The Http request
     * @return Html form
     */
    @Override
    public String getModifyQuiz( HttpServletRequest request )
    {
        setPageTitleProperty( PROPERTY_PAGE_TITLE_MODIFY_QUIZ );

        int nQuizId = Integer.parseInt( request.getParameter( PARAMETER_QUIZ_ID ) );
        Quiz quiz = QuizHome.findByPrimaryKey( nQuizId, getPlugin( ) );

        HashMap<String, Object> model = new HashMap<String, Object>( );
        model.put( MARK_QUIZ, quiz );
        model.put( MARK_WEBAPP_URL, AppPathService.getBaseUrl( request ) );
        model.put( MARK_LOCALE, AdminUserService.getLocale( request ).getLanguage( ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MODIFY_QUIZ, getLocale( ), model );

        return getAdminPage( template.getHtml( ) );
    }

    /**
     * Process the Quiz modifications
     * 
     * @param request The Http request
     * @return Html form
     */
    @Override
    public String doModifyQuiz( HttpServletRequest request )
    {
        int nIdQuiz = Integer.parseInt( request.getParameter( PARAMETER_QUIZ_ID ) );
        String strIntroduction = request.getParameter( PARAMETER_INTRODUCTION );
        String strConclusion = request.getParameter( PARAMETER_CONCLUSION );
        String strDateBeginDisponibility = request.getParameter( PARAMETER_DATE_BEGIN_DISPONIBILITY );
        java.util.Date tDateBeginDisponibility = null;
        tDateBeginDisponibility = DateUtil.formatDate( strDateBeginDisponibility, getLocale( ) );

        String strDateEndDisponibility = request.getParameter( PARAMETER_DATE_END_DISPONIBILITY );
        java.util.Date tDateEndDisponibility = null;
        tDateEndDisponibility = DateUtil.formatDate( strDateEndDisponibility, getLocale( ) );

        int nCaptcha;
        int nRequirement;

        if ( request.getParameter( PARAMETER_ACTIVE_CAPTCHA ) == null )
        {
            nCaptcha = 0;
        }
        else
        {
            nCaptcha = Integer.parseInt( request.getParameter( PARAMETER_ACTIVE_CAPTCHA ) );
        }

        if ( request.getParameter( PARAMETER_ACTIVE_REQUIREMENT ) == null )
        {
            nRequirement = 0;
        }
        else
        {
            nRequirement = Integer.parseInt( request.getParameter( PARAMETER_ACTIVE_REQUIREMENT ) );
        }

        Quiz quiz = QuizHome.findByPrimaryKey( nIdQuiz, getPlugin( ) );

        // Mandatory field
        if ( request.getParameter( PARAMETER_QUIZ_NAME ).equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        quiz.setName( request.getParameter( PARAMETER_QUIZ_NAME ) );

        if ( !( request.getParameter( PARAMETER_QUIZ_STATUS ) == null ) )
        {
            quiz.setStatus( 1 );
        }
        else
        {
            quiz.setStatus( 0 );
        }

        quiz.setIntroduction( strIntroduction );
        quiz.setConclusion( strConclusion );
        quiz.setActiveCaptcha( nCaptcha );
        quiz.setActiveRequirement( nRequirement );
        quiz.setDateBeginDisponibility( tDateBeginDisponibility );
        quiz.setDateEndDisponibility( tDateEndDisponibility );

        QuizHome.update( quiz, getPlugin( ) );

        // if the operation occurred well, redirects towards the view of the User
        UrlItem url = new UrlItem( JSP_URL_MANAGE_QUIZ );

        return url.getUrl( );
    }

    /**
     * Returns quiz displaying form
     * 
     * @param request The Http request
     * @return Html form
     */
    @Override
    public String doDisplayQuiz( HttpServletRequest request )
    {
        int nQuizId = Integer.parseInt( request.getParameter( PARAMETER_QUIZ_ID ) );
        Quiz quiz = QuizHome.findByPrimaryKey( nQuizId, getPlugin( ) );

        if ( quiz.isEnabled( ) )
        {
            quiz.setStatus( 0 );
        }
        else
        {
            quiz.setStatus( 1 );
        }

        QuizHome.update( quiz, getPlugin( ) );

        UrlItem url = new UrlItem( JSP_URL_MANAGE_QUIZ );

        return url.getUrl( );
    }

    /**
     * Return the manage questions page
     * @param request The HTTP request
     * @return The page
     */
    public String getManageQuestions( HttpServletRequest request )
    {
        setPageTitleProperty( PROPERTY_PAGE_TITLE_MANAGE_QUESTIONS );

        int nIdQuiz = Integer.parseInt( request.getParameter( PARAMETER_QUIZ_ID ) );
        Quiz quiz = QuizHome.findByPrimaryKey( nIdQuiz, getPlugin( ) );

        Collection<QuizQuestion> questionsList = QuizQuestionHome.findAll( nIdQuiz, getPlugin( ) );

        HashMap<String, Object> model = new HashMap<String, Object>( );
        model.put( MARK_QUIZ, quiz );
        model.put( MARK_QUESTIONS_LIST, questionsList );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MANAGE_QUESTIONS, getLocale( ), model );

        return getAdminPage( template.getHtml( ) );
    }

    /**
     * Returns the Question creation form
     * 
     * @param request The Http request
     * @return Html creation form
     */
    public String getCreateQuizQuestion( HttpServletRequest request )
    {
        setPageTitleProperty( PROPERTY_PAGE_TITLE_CREATE_QUESTION );

        int nIdQuiz = Integer.parseInt( request.getParameter( PARAMETER_QUIZ_ID ) );
        Quiz quiz = QuizHome.findByPrimaryKey( nIdQuiz, getPlugin( ) );

        HashMap<String, Object> model = new HashMap<String, Object>( );
        model.put( MARK_QUIZ, quiz );
        model.put( MARK_WEBAPP_URL, AppPathService.getBaseUrl( request ) );
        model.put( MARK_LOCALE, AdminUserService.getLocale( request ).getLanguage( ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_CREATE_QUESTION, getLocale( ), model );

        return getAdminPage( template.getHtml( ) );
    }

    /**
     * Process question creation
     * 
     * @param request The Http request
     * @return URL
     */
    public String doCreateQuizQuestion( HttpServletRequest request )
    {
        // Mandatory field
        int nIdQuiz = Integer.parseInt( request.getParameter( PARAMETER_QUIZ_ID ) );
        String strQuestion = request.getParameter( PARAMETER_QUESTION_LABEL );
        String strExplication = request.getParameter( PARAMETER_EXPLAINATION );

        // Mandatory fields
        if ( StringUtils.isEmpty( strQuestion ) )
        {
            return AdminMessageService.getMessageUrl( request, "Vous n'avez pas remplis...", AdminMessage.TYPE_STOP );
        }

        QuizQuestion quizQuestion = new QuizQuestion( );
        quizQuestion.setQuestionLabel( strQuestion );
        quizQuestion.setIdQuiz( nIdQuiz );
        quizQuestion.setExplaination( strExplication );
        QuizQuestionHome.create( quizQuestion, getPlugin( ) );

        QuizQuestion questionCreated = QuizQuestionHome.findLastQuestion( getPlugin( ) );

        // if the operation occurred well, redirects towards the view of the Quiz
        UrlItem url = new UrlItem( JSP_URL_MODIFY_QUESTION );
        url.addParameter( PARAMETER_QUIZ_ID, questionCreated.getIdQuiz( ) );
        url.addParameter( PARAMETER_QUESTION_ID, questionCreated.getIdQuestion( ) );

        return url.getUrl( );
    }

    /**
     * Returns the question modification form
     * 
     * @param request The Http request
     * @return Html form
     */
    public String getModifyQuizQuestion( HttpServletRequest request )
    {
        int nIdQuiz = Integer.parseInt( request.getParameter( PARAMETER_QUIZ_ID ) );
        int nIdQuestion = Integer.parseInt( request.getParameter( PARAMETER_QUESTION_ID ) );
        Quiz quiz = QuizHome.findByPrimaryKey( nIdQuiz, getPlugin( ) );
        QuizQuestion quizQuestion = QuizQuestionHome.findByPrimaryKey( nIdQuestion, getPlugin( ) );
        Collection<Answer> listAnswer = AnswerHome.getAnswersList( nIdQuestion, getPlugin( ) );

        HashMap<String, Object> model = new HashMap<String, Object>( );
        model.put( MARK_QUIZ, quiz );
        model.put( MARK_WEBAPP_URL, AppPathService.getBaseUrl( request ) );
        model.put( MARK_LOCALE, AdminUserService.getLocale( request ).getLanguage( ) );
        model.put( MARK_QUESTION, quizQuestion );
        model.put( MARK_ANSWER_LIST, listAnswer );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MODIFY_QUESTION, getLocale( ), model );

        return getAdminPage( template.getHtml( ) );
    }

    /**
     * Process the modifications of a question
     * 
     * @param request The Http request
     * @return The Jsp URL of the process result
     */
    public String doModifyQuizQuestion( HttpServletRequest request )
    {
        int nIdQuiz = Integer.parseInt( request.getParameter( PARAMETER_QUIZ_ID ) );
        int nQuestionId = Integer.parseInt( request.getParameter( PARAMETER_QUESTION_ID ) );
        String strQuestion = request.getParameter( PARAMETER_QUESTION_LABEL );
        String strExplaination = request.getParameter( PARAMETER_EXPLAINATION );

        // Mandatory fields
        if ( StringUtils.isEmpty( strQuestion ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        QuizQuestion quizQuestion = QuizQuestionHome.findByPrimaryKey( nQuestionId, getPlugin( ) );

        quizQuestion.setQuestionLabel( strQuestion );
        quizQuestion.setIdQuiz( nIdQuiz );
        quizQuestion.setExplaination( strExplaination );
        QuizQuestionHome.update( quizQuestion, getPlugin( ) );

        UrlItem url = new UrlItem( JSP_URL_MANAGE_QUESTIONS );
        url.addParameter( PARAMETER_QUIZ_ID, nIdQuiz );

        return url.getUrl( );
    }

    /**
     * Return the rand list page
     * @param request The HTTP request
     * @return The page
     */
    public String getRandList( HttpServletRequest request )
    {
        setPageTitleProperty( PROPERTY_PAGE_TITLE_RAND_LIST );

        int nIdQuiz = Integer.parseInt( request.getParameter( PARAMETER_QUIZ_ID ) );

        Quiz quiz = QuizHome.findByPrimaryKey( nIdQuiz, getPlugin( ) );

        HashMap<String, Object> model = new HashMap<String, Object>( );
        model.put( MARK_QUIZ, quiz );

        // if the number of win exists, return a random list
        String askGenerate = request.getParameter( PARAMETER_ASK_GENERATE );
        String nbWin = request.getParameter( PARAMETER_NB_WIN );
        if ( StringUtils.isNotEmpty( askGenerate ) )
        {
            // nb_win is mandatory
            if ( StringUtils.isNotEmpty( nbWin ) )
            {
                Integer nNbWin = 0;
                try
                {
                    nNbWin = Integer.valueOf( nbWin );
                }
                catch ( NumberFormatException e )
                {
                    model.put( MARK_ERROR,
                            I18nService.getLocalizedString( MESSAGE_NB_WIN_NOT_A_NUMBER, request.getLocale( ) ) );
                }

                // Get the list of valid participants
                List<QuizParticipant> listValidParticipant = this._randService.findAllValidByQuizId( nIdQuiz,
                        getPlugin( ) );

                // Number of win must be >= to number of valid participant
                boolean nbWinValid = true;
                if ( listValidParticipant != null && listValidParticipant.size( ) < nNbWin )
                {
                    nbWinValid = false;
                    String[] args = new String[1];
                    args[0] = String.valueOf( listValidParticipant.size( ) );
                    model.put( MARK_ERROR, I18nService.getLocalizedString(
                            MESSAGE_NB_WIN_SUPERIOR_TO_NB_VALID_PARTICIPANT, args, request.getLocale( ) ) );
                }
                if ( nbWinValid )
                {
                    // Disable quiz
                    if ( quiz.isEnabled( ) )
                    {
                        quiz.setStatus( 0 );
                        QuizHome.update( quiz, getPlugin( ) );
                    }

                    Collections.shuffle( listValidParticipant );
                    // Get a random list of nNbWin participants
                    //                    List<QuizParticipant> listWinParticipant = new ArrayList<QuizParticipant>( );

                    List<QuizParticipant> listWinParticipant = listValidParticipant.subList( 0, nNbWin );
                    //                    while ( listWinParticipant.size( ) != nNbWin )
                    //                    {
                    //                        Random rand = new Random( );
                    //                        Integer randNumber = rand.nextInt( nNbWin );
                    //                        QuizParticipant participant = listValidParticipant.get( randNumber );
                    //                        if ( !listWinParticipant.contains( participant ) )
                    //                        {
                    //                            listWinParticipant.add( listValidParticipant.get( randNumber ) );
                    //                        }
                    //                    }
                    model.put( MARK_LIST_WIN_PARTICIPANT, listWinParticipant );
                }
            }
            else
            {
                model.put( MARK_ERROR, I18nService.getLocalizedString( MESSAGE_NB_WIN_MANDATORY, request.getLocale( ) ) );
            }
        }

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_RAND_LIST, getLocale( ), model );

        return getAdminPage( template.getHtml( ) );
    }

    /**
     * Returns the quiz remove page
     * 
     * @param request The Http request
     * @return Html form
     */
    @Override
    public String getRemoveQuiz( HttpServletRequest request )
    {
        int nIdQuiz = Integer.parseInt( request.getParameter( PARAMETER_QUIZ_ID ) );
        Quiz quiz = QuizHome.findByPrimaryKey( nIdQuiz, getPlugin( ) );
        String strAdminMessageUrl;
        String strUrl;

        if ( quiz.isEnabled( ) )
        {
            String strMessageKey = MESSAGE_CANNOT_DELETE_QUIZ;
            strAdminMessageUrl = AdminMessageService.getMessageUrl( request, strMessageKey, JSP_MANAGE_QUIZ, "",
                    AdminMessage.TYPE_STOP );
        }
        else
        {
            strUrl = JSP_DO_REMOVE_QUIZ + "?" + PARAMETER_QUIZ_ID + "=" + nIdQuiz;

            String strMessageKey = PROPERTY_CONFIRM_DELETE_QUIZ;
            strAdminMessageUrl = AdminMessageService.getMessageUrl( request, strMessageKey, strUrl, "",
                    AdminMessage.TYPE_CONFIRMATION );
        }

        return strAdminMessageUrl;
    }

    /**
     * Remove a quiz
     * 
     * @param request The Http request
     * @return Html form
     */
    @Override
    public String doRemoveQuiz( HttpServletRequest request )
    {
        int nIdQuiz = Integer.parseInt( request.getParameter( PARAMETER_QUIZ_ID ) );
        Quiz quiz = QuizHome.findByPrimaryKey( nIdQuiz, getPlugin( ) );
        Collection<QuizQuestion> questions = QuizQuestionHome.findAll( nIdQuiz, getPlugin( ) );
        quiz.setQuestions( questions );

        QuestionGroupHome.removeByQuiz( nIdQuiz, getPlugin( ) );
        QuizHome.remove( nIdQuiz, getPlugin( ) );
        QuizQuestionHome.removeQuestionsByQuiz( nIdQuiz, getPlugin( ) );
        this._randService.removeParticipantsByQuiz( nIdQuiz, getPlugin( ) );
        AnswerHome.removeAnswersByQuestionList( questions, getPlugin( ) );

        // Go to the parent page
        return getHomeUrl( request );
    }

    /**
     * Returns the create answer page
     * @param request The HTTP request
     * @return The page
     */
    public String getCreateAnswer( HttpServletRequest request )
    {
        setPageTitleProperty( PROPERTY_PAGE_TITLE_CREATE_ANSWER );

        int nIdQuiz = Integer.parseInt( request.getParameter( PARAMETER_QUIZ_ID ) );
        int nIdQuestion = Integer.parseInt( request.getParameter( PARAMETER_QUESTION_ID ) );
        Quiz quiz = QuizHome.findByPrimaryKey( nIdQuiz, getPlugin( ) );
        QuizQuestion quizQuestion = QuizQuestionHome.findByPrimaryKey( nIdQuestion, getPlugin( ) );

        HashMap<String, Object> model = new HashMap<String, Object>( );
        model.put( MARK_WEBAPP_URL, AppPathService.getBaseUrl( request ) );
        model.put( MARK_LOCALE, AdminUserService.getLocale( request ).getLanguage( ) );
        model.put( MARK_QUIZ, quiz );
        model.put( MARK_QUESTION, quizQuestion );
        model.put( MARK_YESNO_LIST, getYesNoList( ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_CREATE_ANSWER, getLocale( ), model );

        return getAdminPage( template.getHtml( ) );
    }

    /**
     * Returns the Modify answer page
     * @param request The HTTP request
     * @return The page
     */
    public String getModifyAnswer( HttpServletRequest request )
    {
        int nIdQuiz = Integer.parseInt( request.getParameter( PARAMETER_QUIZ_ID ) );
        Quiz quiz = QuizHome.findByPrimaryKey( nIdQuiz, getPlugin( ) );
        int nIdQuestion = Integer.parseInt( request.getParameter( PARAMETER_QUESTION_ID ) );
        QuizQuestion question = QuizQuestionHome.findByPrimaryKey( nIdQuestion, getPlugin( ) );
        int nIdAnswer = Integer.parseInt( request.getParameter( PARAMETER_ANSWER_ID ) );
        Answer answer = AnswerHome.findByPrimaryKey( nIdAnswer, getPlugin( ) );

        HashMap<String, Object> model = new HashMap<String, Object>( );
        model.put( MARK_QUIZ, quiz );
        model.put( MARK_QUESTION, question );
        model.put( MARK_ANSWER, answer );
        model.put( MARK_WEBAPP_URL, AppPathService.getBaseUrl( request ) );
        model.put( MARK_LOCALE, AdminUserService.getLocale( request ).getLanguage( ) );
        model.put( MARK_YESNO_LIST, getYesNoList( ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MODIFY_ANSWER, getLocale( ), model );

        return getAdminPage( template.getHtml( ) );
    }

    /**
     * Return a reference list with Yes/No choice
     * @return The list
     */
    private ReferenceList getYesNoList( )
    {
        ReferenceList list = new ReferenceList( );
        list.addItem( 0, I18nService.getLocalizedString( PROPERTY_LABEL_NO, getLocale( ) ) );
        list.addItem( 1, I18nService.getLocalizedString( PROPERTY_LABEL_YES, getLocale( ) ) );

        return list;
    }
}
