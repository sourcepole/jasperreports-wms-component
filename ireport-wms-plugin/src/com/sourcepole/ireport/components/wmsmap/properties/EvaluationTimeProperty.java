/*
 * JasperReports/iReport WMS Component
 * 
 * Copyright (C) 2013 Sourcepole AG
 * 
 * JasperReports/iReport WMS Component is free software: you can redistribute
 * it and/or modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * JasperReports/iReport WMS Component is distributed in the hope that it will
 * be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with JasperReports/iReport WMS Component.
 * If not, see <http://www.gnu.org/licenses/>.
 */
package com.sourcepole.ireport.components.wmsmap.properties;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.sheet.Tag;
import com.jaspersoft.ireport.designer.sheet.editors.ComboBoxPropertyEditor;
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import com.jaspersoft.ireport.locale.I18n;
import com.sourcepole.jasperreports.wmsmap.StandardWmsMapComponent;
import java.beans.PropertyEditor;
import java.lang.reflect.InvocationTargetException;
import net.sf.jasperreports.components.map.StandardMapComponent;
import net.sf.jasperreports.engine.JRGroup;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.type.EvaluationTimeEnum;
import org.openide.ErrorManager;
import org.openide.nodes.PropertySupport;

/**
 * Class to manage the component PROPERTY_EVALUATION_TIME property
 */
public class EvaluationTimeProperty extends PropertySupport {
    
    // FIXME: refactorize this
    private final JRDesignDataset dataset;
    private final StandardWmsMapComponent component;
    private ComboBoxPropertyEditor editor;

    public EvaluationTimeProperty(StandardWmsMapComponent component, JRDesignDataset dataset)
    {
        super( StandardWmsMapComponent.PROPERTY_EVALUATION_TIME,
                EvaluationTimeEnum.class, 
                I18n.getString("Global.Property.EvaluationTime"), 
                I18n.getString("Global.Property.EvaluationTimedetail"), 
                true, true);
        this.component = component;
        this.dataset = dataset;
        setValue("suppressCustomEditor", Boolean.TRUE);
    }

    @Override
    public boolean isDefaultValue() {
        return component.getEvaluationTime() == EvaluationTimeEnum.NOW;
    }

    @Override
    public void restoreDefaultValue() throws IllegalAccessException, InvocationTargetException {
        setPropertyValue(EvaluationTimeEnum.NOW);
    }

    @Override
    public boolean supportsDefaultValue() {
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public PropertyEditor getPropertyEditor() {

        if (editor == null)
        {
            java.util.ArrayList l = new java.util.ArrayList();

            l.add(new Tag(EvaluationTimeEnum.NOW, I18n.getString("Global.Property.Now")));
            l.add(new Tag(EvaluationTimeEnum.REPORT, I18n.getString("Global.Property.Report")));
            l.add(new Tag(EvaluationTimeEnum.PAGE, I18n.getString("Global.Property.Page")));
            l.add(new Tag(EvaluationTimeEnum.COLUMN, I18n.getString("Global.Property.Column")));
            l.add(new Tag(EvaluationTimeEnum.GROUP, I18n.getString("Global.Property.Group")));
            l.add(new Tag(EvaluationTimeEnum.BAND, I18n.getString("Global.Property.Band")));
           // l.add(new Tag(new Byte(JRExpression.EVALUATION_TIME_AUTO), I18n.getString("Global.Property.Auto")));

            editor = new ComboBoxPropertyEditor(false, l);
        }
        return editor;
    }

    public Object getValue() throws IllegalAccessException, InvocationTargetException {
        return component.getEvaluationTime();
    }

    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (val instanceof EvaluationTimeEnum)
        {
             setPropertyValue((EvaluationTimeEnum)val);
        }
    }

    private void setPropertyValue(EvaluationTimeEnum val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
            EvaluationTimeEnum oldValue = component.getEvaluationTime();
            EvaluationTimeEnum newValue = val;

            ObjectPropertyUndoableEdit urob =
                    new ObjectPropertyUndoableEdit(
                        component,
                        "EvaluationTime", 
                        EvaluationTimeEnum.class,
                        oldValue,newValue);

            String oldGroupValue = component.getEvaluationGroup();
            String newGroupValue = null;
            if ( val == EvaluationTimeEnum.GROUP )
            {
                if (dataset.getGroupsList().size() == 0)
                {
                    IllegalArgumentException iae = annotateException(I18n.getString("Global.Property.NogroupsTextFielddetail")); 
                    throw iae; 
                }

                newGroupValue = ((JRGroup)dataset.getGroupsList().get(0)).getName();
            }
            component.setEvaluationTime(newValue);

            boolean sameGroup = ((oldGroupValue == null) ? "" : oldGroupValue).equals( ((newGroupValue == null) ? "" : newGroupValue) );
            if (!sameGroup)
            {
                ObjectPropertyUndoableEdit urobGroup =
                        new ObjectPropertyUndoableEdit(
                            component,
                            "EvaluationGroup", 
                            String.class,
                            oldGroupValue,newGroupValue);
                component.setEvaluationGroup(newGroupValue);
                urob.concatenate(urobGroup);
            }

            // Find the undoRedo manager...
            IReportManager.getInstance().addUndoableEdit(urob);
    }

    public IllegalArgumentException annotateException(String msg)
    {
        IllegalArgumentException iae = new IllegalArgumentException(msg); 
        ErrorManager.getDefault().annotate(iae, 
                                ErrorManager.EXCEPTION,
                                msg,
                                msg, null, null); 
        return iae;
    }

}
