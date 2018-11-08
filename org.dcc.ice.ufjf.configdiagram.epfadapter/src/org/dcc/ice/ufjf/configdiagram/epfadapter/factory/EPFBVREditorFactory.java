/*******************************************************************************
 * Copyright (c)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.dcc.ice.ufjf.configdiagram.epfadapter.factory;

import java.util.HashMap;
import java.util.Map;

import no.sintef.bvr.thirdparty.interfaces.editor.IBVREnabledEditor;

import org.dcc.ice.ufjf.configdiagram.epfadapter.EPFBVREditorAdapter;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.epf.authoring.ui.editors.ConfigurationEditor;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;

public class EPFBVREditorFactory implements IAdapterFactory {

	private Map<Object, IBVREnabledEditor> adapterMap = new HashMap<Object, IBVREnabledEditor>();

	@Override
	public Object getAdapter(final Object adaptableObject, Class adapterType) {

		if (adapterType == IBVREnabledEditor.class
				&& adaptableObject instanceof ConfigurationEditor) {

			if (adapterMap.containsKey(adaptableObject)) {
				return adapterMap.get(adaptableObject);
			}

			// Ensure that adapter is removed in case the editor is closed
			ConfigurationEditor editor = (ConfigurationEditor) adaptableObject;
			editor.getSite().getPage().getActiveEditor().getSite().getPage()
					.addPartListener(new IPartListener() {
						//final Object adaptableObject_ = adapterMap.get(adaptableObject);
						@Override
						public void partOpened(IWorkbenchPart part) {

						}

						@Override
						public void partDeactivated(IWorkbenchPart part) {
							// TODO Auto-generated method stub

						}

						@Override
						public void partClosed(IWorkbenchPart part) {
							adapterMap.remove(adaptableObject);

						}

						@Override
						public void partBroughtToTop(IWorkbenchPart part) {
							// TODO Auto-generated method stub

						}

						@Override
						public void partActivated(IWorkbenchPart part) {
							// TODO Auto-generated method stub

						}
					});

			// Create and return new adapter
			IBVREnabledEditor adapter = new EPFBVREditorAdapter(editor);
			adapterMap.put(adaptableObject, adapter);

			return adapter;
		}
		return null;
	}

	@Override
	public Class[] getAdapterList() {
		return new Class[] { IBVREnabledEditor.class };
	}

}
