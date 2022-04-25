package at.noahb.minecraftmcaintellijplugin;

import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.impl.FileTypeOverrider;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class McaFileTypeOverrider implements FileTypeOverrider {
	@Nullable
	@Override
	public FileType getOverriddenFileType(@NotNull VirtualFile file) {
		return "mca".equals(file.getExtension()) ? McaFileType.INSTANCE : null;
	}
}
