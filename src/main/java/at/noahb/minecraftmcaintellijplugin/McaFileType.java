package at.noahb.minecraftmcaintellijplugin;

import com.github.tth05.minecraftnbtintellijplugin.NBTTagType;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class McaFileType implements FileType {

	public static final McaFileType INSTANCE = new McaFileType();

	@NotNull
	@Override
	public String getName() {
		return "Minecraft Anvil";
	}

	@NotNull
	@Override
	public String getDescription() {
		return "Minecraft anvil file";
	}

	@NotNull
	@Override
	public String getDefaultExtension() {
		return "mca";
	}

	@Nullable
	@Override
	public Icon getIcon() {
		return NBTTagType.COMPOUND.getIcon();
	}

	@Override
	public boolean isBinary() {
		return true;
	}

	@Override
	public boolean isReadOnly() {
		return true;
	}

	@Nullable
	@Override
	public String getCharset(@NotNull VirtualFile file, byte @NotNull [] content) {
		return null;
	}
}
