package com.github.tth05.minecraftnbtintellijplugin.editor.ui;

import com.github.tth05.minecraftnbtintellijplugin.NBTTagTreeNode;
import com.intellij.ide.util.treeView.NodeRenderer;
import org.jetbrains.annotations.NotNull;

import javax.swing.JTree;

public class NBTFileEditorTreeCellRenderer extends NodeRenderer {

	@Override
	public void customizeCellRenderer(@NotNull JTree tree, Object value, boolean selected, boolean expanded,
	                                  boolean leaf, int row, boolean hasFocus) {
		super.customizeCellRenderer(tree, value, selected, expanded, leaf, row, hasFocus);
		if (value instanceof NBTTagTreeNode) {
			NBTTagTreeNode node = (NBTTagTreeNode) value;
			setIcon(node.getType().getIcon());
		}
	}
}
