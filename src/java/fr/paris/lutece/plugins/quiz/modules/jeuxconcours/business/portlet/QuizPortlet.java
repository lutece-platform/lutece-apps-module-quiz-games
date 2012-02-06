/*
 * Copyright (c) 2002-2011, Mairie de Paris
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
package fr.paris.lutece.plugins.quiz.modules.jeuxconcours.business.portlet;

import fr.paris.lutece.plugins.quiz.business.Quiz;
import fr.paris.lutece.plugins.quiz.service.QuizService;
import fr.paris.lutece.portal.business.portlet.Portlet;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.util.html.HtmlTemplate;
import fr.paris.lutece.util.xml.XmlUtil;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * This class represents business objects QuizPortlet
 */
public class QuizPortlet extends Portlet
{
    /////////////////////////////////////////////////////////////////////////////////
    // Constants
    private static final String TAG_QUIZ_PORTLET = "quiz-portlet";
    private static final String TAG_QUIZ_PORTLET_CONTENT = "quiz-portlet-content";
    private static final String TAG_QUIZ_IS_ACTIVE = "quiz-active";
    private Integer _nQuiz;
    private static final String BEAN_QUIZ_SERVICE = "quiz.quizService";
    private static final String TEMPLATE_QUESTIONS_LIST = "skin/plugins/quiz/modules/jeuxconcours/quiz.html";

    /**
     * Sets the identifier of the portlet type to value specified
     */
    public QuizPortlet(  )
    {
        setPortletTypeId( QuizPortletHome.getInstance( ).getPortletTypeId( ) );
    }

    /**
     * Sets the Quiz portlet content
     * 
     * @param nQuiz the Quiz code to sets content
     */
    public void setQuiz( Integer nQuiz )
    {
        _nQuiz = nQuiz;
    }

    /**
     * Returns the content of the Quiz portlet
     * 
     * @return the Quiz code content
     */
    public Integer getQuiz( )
    {
        return _nQuiz;
    }

    /**
     * Returns the Xml code of the QUIZ portlet without XML heading
     * 
     * @param request The Request
     * @return the Xml code of the QUIZ portlet content
     */
    public String getXml( HttpServletRequest request )
    {
        StringBuffer strXml = new StringBuffer(  );
        XmlUtil.beginElement( strXml, TAG_QUIZ_PORTLET );
        XmlUtil.addElementHtml( strXml, TAG_QUIZ_PORTLET_CONTENT, getQuizContent( request.getLocale( ) ) );
        XmlUtil.endElement( strXml, TAG_QUIZ_PORTLET );
        QuizService serviceQuiz = (QuizService) SpringContextService.getBean( BEAN_QUIZ_SERVICE );
        Map model = serviceQuiz.getQuiz( getQuiz( ) );
        Quiz quiz = (Quiz) model.get( "quiz" );
        String isActive = "false";
        if ( quiz.isEnabled( ) )
        {
            isActive = "true";
        }
        XmlUtil.addElement( strXml, TAG_QUIZ_IS_ACTIVE, isActive );

        return addPortletTags( strXml );
    }

    private String getQuizContent( Locale locale )
    {
        QuizService serviceQuiz = (QuizService) SpringContextService.getBean( BEAN_QUIZ_SERVICE );
        Map model = serviceQuiz.getQuiz( getQuiz( ) );
        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_QUESTIONS_LIST, locale, model );

        return template.getHtml( );
    }

    /**
     * Returns the Xml code of the QUIZ portlet with XML heading
     * 
     * @param request The request
     * @return the Xml code of the QUIZ portlet
     */
    public String getXmlDocument( HttpServletRequest request )
    {
        return XmlUtil.getXmlHeader(  ) + getXml( request );
    }

    /**
     * Updates the current instance of the QuizPortlet object
     */
    public void update(  )
    {
        QuizPortletHome.getInstance( ).update( this );
    }

    /**
     * Removes the current instance of the QuizPortlet object
     */
    public void remove(  )
    {
        QuizPortletHome.getInstance( ).remove( this );
    }
}
