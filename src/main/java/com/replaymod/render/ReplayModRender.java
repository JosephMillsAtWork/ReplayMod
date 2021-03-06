package com.replaymod.render;

import com.replaymod.core.Module;
import com.replaymod.core.ReplayMod;
import com.replaymod.render.utils.RenderJob;
import com.replaymod.replay.events.ReplayClosedCallback;
import de.johni0702.minecraft.gui.utils.EventRegistrations;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashException;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static com.replaymod.core.versions.MCVer.*;

public class ReplayModRender extends EventRegistrations implements Module {
    { instance = this; }
    public static ReplayModRender instance;

    private ReplayMod core;

    public static Logger LOGGER = LogManager.getLogger();

    private final List<RenderJob> renderQueue = new ArrayList<>();

    public ReplayModRender(ReplayMod core) {
        this.core = core;

        core.getSettingsRegistry().register(Setting.class);
    }

    public ReplayMod getCore() {
        return core;
    }

    @Override
    public void initClient() {
        on(ReplayClosedCallback.EVENT, replayHandler -> renderQueue.clear());

        register();
    }

    public File getVideoFolder() {
        String path = core.getSettingsRegistry().get(Setting.RENDER_PATH);
        File folder = new File(path.startsWith("./") ? core.getMinecraft().runDirectory : null, path);
        try {
            FileUtils.forceMkdir(folder);
        } catch (IOException e) {
            throw new CrashException(CrashReport.create(e, "Cannot create video folder."));
        }
        return folder;
    }

    public Path getRenderSettingsPath() {
        return core.getMinecraft().runDirectory.toPath().resolve("config/replaymod-rendersettings.json");
    }

    public List<RenderJob> getRenderQueue() {
        return renderQueue;
    }
}
