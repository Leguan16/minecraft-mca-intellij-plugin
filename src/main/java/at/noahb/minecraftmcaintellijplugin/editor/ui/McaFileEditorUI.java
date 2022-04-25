package at.noahb.minecraftmcaintellijplugin.editor.ui;

import at.noahb.minecraftmcaintellijplugin.util.McaFileUtil;
import com.github.tth05.minecraftnbtintellijplugin.NBTTagTreeNode;
import com.github.tth05.minecraftnbtintellijplugin.NBTTagType;
import com.github.tth05.minecraftnbtintellijplugin.editor.ui.NBTFileEditorTreeCellRenderer;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTextField;
import com.intellij.ui.treeStructure.Tree;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

public class McaFileEditorUI extends JPanel implements DataProvider {

	public static final DataKey<McaFileEditorUI> DATA_KEY = DataKey.create(McaFileEditorUI.class.getName());

	private Tree tree;

	private boolean autoSaveEnabled = true;

	public McaFileEditorUI(@NotNull VirtualFile file, @NotNull Project project) {
		this.setLayout(new BorderLayout());

		//Toolbar
		JPanel northSection = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton saveButton = new JButton("Save", AllIcons.Actions.MenuSaveall);
		saveButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				McaFileUtil.saveTreeToFile(McaFileEditorUI.this.tree, file, project);
			}
		});

		JBCheckBox autoSaveCheckbox = new JBCheckBox("Save On Change");
		autoSaveCheckbox.setSelected(true);
		autoSaveCheckbox.addItemListener(e -> autoSaveEnabled = e.getStateChange() == ItemEvent.SELECTED);

		northSection.add(saveButton);
		northSection.add(autoSaveCheckbox);

		//Tree Section
		DefaultMutableTreeNode root = McaFileUtil.loadNBTFileIntoTree(file);
		if (root == null) {
			JBLabel errorText = new JBLabel("Invalid mca File!");
			errorText.setForeground(JBColor.RED);
			errorText.setHorizontalAlignment(SwingConstants.CENTER);
			this.add(errorText, BorderLayout.CENTER);
			return;
		}

		TreeModel model = new DefaultTreeModel(root);
		//The listener updates the indices in the node names if their parent is some sort of list
		model.addTreeModelListener(new TreeModelListener() {
			@Override
			public void treeNodesChanged(TreeModelEvent e) {
			}

			@Override
			public void treeNodesInserted(TreeModelEvent e) {
			}

			@Override
			public void treeNodesRemoved(TreeModelEvent e) {
				NBTTagTreeNode parent = (NBTTagTreeNode) e.getTreePath().getLastPathComponent();

				if (parent.getChildCount() > 0 && parent.getType() != NBTTagType.COMPOUND) {
					Enumeration<TreeNode> children = parent.children();
					for (int i = 0; children.hasMoreElements(); i++)
						((NBTTagTreeNode) children.nextElement()).setName(i + "");
				}
			}

			@Override
			public void treeStructureChanged(TreeModelEvent e) {
			}
		});

		this.tree = new Tree(model);
		this.tree.setEditable(true);
		this.tree.setCellEditor(new DefaultCellEditor(new JBTextField()));
		this.tree.setCellRenderer(new NBTFileEditorTreeCellRenderer());
		this.tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					//Find right clicked row and make sure it's selected
					int row = tree.getClosestRowForLocation(e.getX(), e.getY());

					if (!tree.isRowSelected(row))
						tree.setSelectionRow(row);

					ActionPopupMenu menu = ActionManager.getInstance().createActionPopupMenu(ActionPlaces.POPUP,
							(ActionGroup) ActionManager.getInstance().getAction(
									"com.github.tth05.minecraftnbtintellijplugin.actions.NBTFileEditorPopupGroup"));
					menu.setTargetComponent(McaFileEditorUI.this);
					menu.getComponent().show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});

		this.add(northSection, BorderLayout.NORTH);
		this.add(new JBScrollPane(this.tree), BorderLayout.CENTER);
	}

	public Tree getTree() {
		return this.tree;
	}

	@Nullable
	@Override
	public Object getData(@NotNull String dataId) {
		if (DATA_KEY.is(dataId))
			return this;
		return null;
	}

	public boolean isAutoSaveEnabled() {
		return autoSaveEnabled;
	}
}
