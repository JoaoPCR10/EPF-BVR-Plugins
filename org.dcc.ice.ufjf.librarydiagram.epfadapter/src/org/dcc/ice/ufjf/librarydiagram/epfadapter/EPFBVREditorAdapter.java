/*******************************************************************************
 * Copyright (c)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.dcc.ice.ufjf.librarydiagram.epfadapter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import no.sintef.bvr.thirdparty.interfaces.editor.AbstractBVREnabledEditor;
import no.sintef.bvr.thirdparty.interfaces.editor.IBVREnabledEditor;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.epf.authoring.ui.editors.MethodLibraryEditor;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramGraphicalViewer;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.IEditorPart;

// TODO: Auto-generated Javadoc
/**
 * The Class PapyrusBVREditorAdapter implements IBVREnabledEditor. The plugins
 * adopts standard PapyrusMultiDiagramEditor editor to interact with BVR Tool
 * Bundle.
 */
public class EPFBVREditorAdapter extends AbstractBVREnabledEditor {

	private MethodLibraryEditor editor;

	/** The foreground color. */
	private Map<IFigure, Color> foregroundColor = new HashMap<IFigure, Color>();

	/** The background color. */
	private Map<IFigure, Color> backgroundColor = new HashMap<IFigure, Color>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see no.sintef.bvr.thirdparty.interfaces.editor.IBVREnabledEditor#
	 * clearHighlighting()
	 */

	public EPFBVREditorAdapter(MethodLibraryEditor _editor) {
		editor = _editor;
	}

	@Override
	public void clearHighlighting() {
		for (Iterator<IFigure> it = foregroundColor.keySet().iterator(); it.hasNext();) {
			IFigure figure = (IFigure) it.next();
			figure.setForegroundColor((Color) foregroundColor.get(figure));
			figure.repaint();

		}
		for (Iterator<IFigure> it = backgroundColor.keySet().iterator(); it.hasNext();) {
			IFigure figure = (IFigure) it.next();
			figure.setBackgroundColor((Color) backgroundColor.get(figure));
			figure.repaint();

		}
		foregroundColor.clear();
		backgroundColor.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see no.sintef.bvr.thirdparty.interfaces.editor.IBVREnabledEditor#
	 * getSelectedObjects()
	 */
	@Override
	public List<Object> getSelectedObjects() {
		ISelection selection = editor.getSite().getSelectionProvider().getSelection();
		StructuredSelection structuredSelection = (StructuredSelection) selection;
		return structuredSelection.toList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * no.sintef.bvr.thirdparty.interfaces.editor.IBVREnabledEditor#highlightObject
	 * (java.lang.Object, int)
	 */
	@Override
	public void highlightObject(Object object, int type) {
		if (!(object instanceof EObject))
			return;

		EObject eObject = (EObject) object;
		Color c = ColorConstants.black;
		switch (type) {
		case IBVREnabledEditor.HL_PLACEMENT:
			c = IBVREnabledEditor.PLACEMENT;
			break;
		case IBVREnabledEditor.HL_PLACEMENT_OUT:
			c = IBVREnabledEditor.PLACEMENT_OUT;
			break;
		case IBVREnabledEditor.HL_PLACEMENT_IN:
			c = IBVREnabledEditor.PLACEMENT_IN;
			break;
		case IBVREnabledEditor.HL_PLACEMENT_IN_OUT:
			c = IBVREnabledEditor.PLACEMENT_IN_OUT;
			break;
		case IBVREnabledEditor.HL_REPLACEMENT:
			c = IBVREnabledEditor.REPLACEMENT;
			break;
		case IBVREnabledEditor.HL_REPLACEMENT_OUT:
			c = IBVREnabledEditor.REPLACEMENT_OUT;
			break;
		case IBVREnabledEditor.HL_REPLACEMENT_IN:
			c = IBVREnabledEditor.REPLACEMENT_IN;
			break;
		case IBVREnabledEditor.HL_REPLACEMENT_IN_OUT:
			c = IBVREnabledEditor.REPLACEMENT_IN_OUT;
			break;
		default:
			throw new UnsupportedOperationException("coloring of this type is not supported " + type);
		}

		setColor(eObject, c, editor.getSite().getPage().getActiveEditor());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * no.sintef.bvr.thirdparty.interfaces.editor.IBVREnabledEditor#selectObjects
	 * (java.util.List)
	 */
	@Override
	public void selectObjects(List<Object> objects) {
		IStructuredSelection selection = new StructuredSelection(objects);
		editor.getSite().getSelectionProvider().setSelection(selection);
	}

	/**
	 * Sets the color.
	 *
	 * @param obj
	 *            the obj
	 * @param fg
	 *            the fg
	 * @param editor
	 *            the editor
	 */
	public void setColor(EObject obj, Color fg, IEditorPart editor) {
		IDiagramGraphicalViewer gv = ((IDiagramWorkbenchPart) editor).getDiagramGraphicalViewer();

		List<?> editParts = gv.findEditPartsForElement(IDProvider.getXMIId(obj), EditPart.class);

		for (Object object : editParts) {
			//if (object instanceof IPapyrusEditPart) {
			if(object instanceof MethodLibraryEditor)	{
			//IPapyrusEditPart ep = (IPapyrusEditPart) object;
				MethodLibraryEditor ep = (MethodLibraryEditor) object;
				//eop.getFigure()
				if (!foregroundColor.containsKey(((GraphicalEditPart) ep).getFigure())) {
					//foregroundColor.put(ep.getPrimaryShape(), ep.getPrimaryShape().getForegroundColor());
					foregroundColor.put(((GraphicalEditPart) ep).getFigure(), ((GraphicalEditPart) ep).getFigure().getForegroundColor());
				}

				((GraphicalEditPart) ep).getFigure().setForegroundColor(fg);
				((GraphicalEditPart) ep).getFigure().repaint();
			}
		}
	}

	@Override
	public List<EObject> getModelObjects(List<Object> objects) {
		List<EObject> eObjects = new BasicEList<EObject>();
		for (Object object : objects) {
			EObject eObject = null;
			if (object instanceof EObject) {
				eObject = (EObject) object;
			} else {
				if (object instanceof IGraphicalEditPart) {
					eObject = ((IGraphicalEditPart) object).resolveSemanticElement();
					eObjects.add(eObject);
				}
			}
		}
		return eObjects;
	}

	@Override
	public List<Object> getGraphicalObjects(List<EObject> objects) {
		IEditorPart part = editor.getSite().getPage().getActiveEditor();
		IDiagramGraphicalViewer gv = ((IDiagramWorkbenchPart) part).getDiagramGraphicalViewer();

		HashSet<Object> grapthicalParts = new HashSet<Object>();

		for (EObject eObject : objects) {
			List<?> editParts = gv.findEditPartsForElement(IDProvider.getXMIId(eObject), EditPart.class);
			for (Object diagramPart : editParts) {
				if(diagramPart instanceof MethodLibraryEditor){
				//if (diagramPart instanceof IGraphicalEditPart) {
					grapthicalParts.add(diagramPart);
				}
			}
		}

		return new ArrayList<Object>(grapthicalParts);
	}
}