/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
 *
 * This file is part of the Java Data Mining Package (JDMP).
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership and licensing.
 *
 * JDMP is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * JDMP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with JDMP; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package org.jdmp.gui.module;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.table.AbstractTableModel;

import org.jdmp.core.module.HasModules;
import org.jdmp.core.module.Module;

public class ModuleListTableModel extends AbstractTableModel implements ListDataListener {
	private static final long serialVersionUID = -1372599464095754625L;

	public static final int ICONCOLUMN = 0;

	public static final int LABELCOLUMN = 1;

	public static final int ALGORITHMCOUNTCOLUMN = 2;

	public static final int VARIABLECOUNTCOLUMN = 3;

	public static final int MODULECOUNTCOLUMN = 4;

	public static final int DATASETCOUNTCOLUMN = 5;

	private HasModules iModules = null;

	public ModuleListTableModel(HasModules iModules) {
		this.iModules = iModules;
		iModules.getModules().addListDataListener(this);
	}

	public int getRowCount() {
		return iModules.getModules().getSize();
	}

	public int getColumnCount() {
		return 6;
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case ICONCOLUMN:
			return "";
		case LABELCOLUMN:
			return "Label";
		case VARIABLECOUNTCOLUMN:
			return "VariableCount";
		case MODULECOUNTCOLUMN:
			return "ModuleCount";
		case DATASETCOUNTCOLUMN:
			return "DataSetCount";
		case ALGORITHMCOUNTCOLUMN:
			return "AlgorithmCount";
		default:
			return "unknown";

		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return Module.class;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return iModules.getModules().getElementAt(rowIndex);
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		fireTableRowsUpdated(e.getIndex0(), e.getIndex1());
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		fireTableRowsInserted(e.getIndex0(), e.getIndex1());
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		fireTableRowsDeleted(e.getIndex0(), e.getIndex1());
	}

}
