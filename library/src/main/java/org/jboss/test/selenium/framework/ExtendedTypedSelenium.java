/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.test.selenium.framework;

import org.jboss.test.selenium.geometry.Point;
import org.jboss.test.selenium.locator.AttributeLocator;
import org.jboss.test.selenium.locator.ElementLocator;
import org.jboss.test.selenium.locator.IterableLocator;
import org.jboss.test.selenium.locator.JQueryLocator;
import org.jboss.test.selenium.locator.LocatorUtils;

/**
 * Type-safe selenium wrapper for Selenium API with extension of some useful commands defined in
 * {@link ExtendedSelenium}
 * 
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class ExtendedTypedSelenium extends DefaultTypedSelenium {

    ExtendedSelenium getExtendedSelenium() {
        if (selenium instanceof ExtendedSelenium) {
            return (ExtendedSelenium) selenium;
        } else {
            throw new UnsupportedOperationException("Assigned Selenium isn't instance of ExtendedSelenium");
        }
    }

    /**
     * Get current style value of element given by locator.
     * 
     * Use CSS style notation instead of JavaScript's camel notation!
     * 
     * This methods of getting current style value haven't absolute browser compatibility.
     * 
     * E.g.: use property "background-color" instead of "backgroundColor"
     * 
     * @param locator
     *            of element from what we want to get current style value
     * @param property
     *            CSS style property what we can to recognize
     * @return current value of property if its element exists and has this property value set; null value otherwise
     * @throws IllegalStateException
     *             if is caught unrecognized throwable
     */
    public String getStyle(ElementLocator elementLocator, String property) {
        return getExtendedSelenium().getStyle(elementLocator.getAsString(), property);
    }

    /**
     * Aligns screen to top (resp. bottom) of element given by locator.
     * 
     * @param locator
     *            of element which should be screen aligned to
     * @param alignToTop
     *            should be top border of screen aligned to top border of element
     */
    public void scrollIntoView(ElementLocator elementLocator, boolean alignToTop) {
        getExtendedSelenium().scrollIntoView(elementLocator.getAsString(), String.valueOf(alignToTop));
    }

    /**
     * Simulates a user hovering a mouse over the specified element at specific coordinates relative to element.
     * 
     * @param locator
     *            element's locator
     * @param coordString
     *            specifies the x,y position (i.e. - 10,20) of the mouse event relative to the element returned by the
     *            locator.
     */
    public void mouseOverAt(ElementLocator elementLocator, Point point) {
        getExtendedSelenium().mouseOverAt(elementLocator.getAsString(), point.getCoords());
    }

    /**
     * Returns whether the element is displayed on the page.
     * 
     * @param locator
     *            element locator
     * @return if style contains "display: none;" returns false, else returns true
     */
    public boolean isDisplayed(ElementLocator elementLocator) {
        return getExtendedSelenium().isDisplayed(elementLocator.getAsString());
    }

    /**
     * Checks if element given by locator is member of CSS class given by className.
     * 
     * @param className
     *            name of CSS class
     * @param locator
     *            element's locator
     * @return true if element given by locator is member of CSS class given by className
     */
    public boolean belongsClass(ElementLocator elementLocator, String className) {
        return getExtendedSelenium().belongsClass(elementLocator.getAsString(), className);
    }

    /**
     * Verifies that the specified attribute is defined for the element.
     * 
     * @param attributeLocator
     *            an attribute locator
     * @return true if the element's attribute is present, false otherwise
     * @throws SeleniumException
     *             when element isn't present
     */
    public boolean isAttributePresent(AttributeLocator attributeLocator) {
        final String elementLocator = attributeLocator.getAssociatedElement().getAsString();
        final String attributeName = attributeLocator.getAttribute().getAttributeName();
        return getExtendedSelenium().isAttributePresent(elementLocator, attributeName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.jboss.test.selenium.framework.DefaultTypedSelenium#getCount(org.jboss.test.selenium.locator.IterableLocator)
     */
    @Override
    public int getCount(IterableLocator<?> locator) {
        Object reference = (Object) locator;
        if (reference instanceof JQueryLocator) {
            return getExtendedSelenium().getJQueryCount(LocatorUtils.getRawLocator((JQueryLocator) reference))
                .intValue();
        }
        try {
            return super.getCount(locator);
        } catch (UnsupportedOperationException e) {
            throw new UnsupportedOperationException("Only JQuery and XPath locators are supported for counting");
        }
    }
}