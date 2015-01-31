package eu.crushedpixel.replaymod.gui.online;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.resources.I18n;

import org.lwjgl.input.Keyboard;

import scala.actors.threadpool.Arrays;

import com.mojang.realmsclient.gui.GuiCallback;

import eu.crushedpixel.replaymod.ReplayMod;
import eu.crushedpixel.replaymod.api.client.ApiException;
import eu.crushedpixel.replaymod.api.client.holders.FileInfo;
import eu.crushedpixel.replaymod.gui.GuiConstants;
import eu.crushedpixel.replaymod.gui.replaymanager.GuiReplayManager;
import eu.crushedpixel.replaymod.online.authentication.AuthenticationHandler;

public class GuiReplayCenter extends GuiScreen implements GuiYesNoCallback {

	@Override
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		if(!AuthenticationHandler.isAuthenticated()) {
			mc.displayGuiScreen(new GuiLoginPrompt(new GuiMainMenu(), this));
			return;
		}

		//Top Button Bar
		List<GuiButton> buttonBar = new ArrayList<GuiButton>();

		GuiButton recentButton = new GuiButton(GuiConstants.CENTER_RECENT_BUTTON, 20, 30, "Newest Replays");
		buttonBar.add(recentButton);
		
		GuiButton bestButton = new GuiButton(GuiConstants.CENTER_BEST_BUTTON, 20, 30, "Best Replays");
		buttonBar.add(bestButton);

		GuiButton ownReplayButton = new GuiButton(GuiConstants.CENTER_MY_REPLAYS_BUTTON, 20, 30, "My Replays");
		buttonBar.add(ownReplayButton);

		GuiButton searchButton = new GuiButton(GuiConstants.CENTER_SEARCH_BUTTON, 20, 30, "Search");
		buttonBar.add(searchButton);

		int i = 0;
		for(GuiButton b : buttonBar) {
			int w = this.width - 30;
			int w2 = w/buttonBar.size();

			int x = 15+(w2*i);
			b.xPosition = x+2;
			b.yPosition = 20;
			b.width = w2-4;

			buttonList.add(b);

			i++;
		}

		//Bottom Button Bar (dat alliteration)
		List<GuiButton> bottomBar = new ArrayList<GuiButton>();

		GuiButton exitButton = new GuiButton(GuiConstants.CENTER_BACK_BUTTON, 20, 20, "Main Menu");
		bottomBar.add(exitButton);
		
		GuiButton managerButton = new GuiButton(GuiConstants.CENTER_MANAGER_BUTTON, 20, 20, "Replay Manager");
		bottomBar.add(managerButton);

		GuiButton logoutButton = new GuiButton(GuiConstants.CENTER_LOGOUT_BUTTON, 20, 20, "Logout");
		bottomBar.add(logoutButton);

		i = 0;
		for(GuiButton b : bottomBar) {
			int w = this.width - 30;
			int w2 = w/bottomBar.size();

			int x = 15+(w2*i);
			b.xPosition = x+2;
			b.yPosition = height-30;
			b.width = w2-4;

			buttonList.add(b);

			i++;
		}
		
		showOnlineRecent();
	}

	@Override
	protected void actionPerformed(GuiButton button) throws java.io.IOException {
		if(!button.enabled) return;
		if(button.id == GuiConstants.CENTER_BACK_BUTTON) {
			mc.displayGuiScreen(new GuiMainMenu());
		} else if(button.id == GuiConstants.CENTER_LOGOUT_BUTTON) {
			mc.displayGuiScreen(getYesNoGui(this, LOGOUT_CALLBACK_ID));
		} else if(button.id == GuiConstants.CENTER_MANAGER_BUTTON) {
			mc.displayGuiScreen(new GuiReplayManager());
		} else if(button.id == GuiConstants.CENTER_RECENT_BUTTON) {
			showOnlineRecent();
		} else if(button.id == GuiConstants.CENTER_BEST_BUTTON) {
			
		} else if(button.id == GuiConstants.CENTER_MY_REPLAYS_BUTTON) {
			
		} else if(button.id == GuiConstants.CENTER_SEARCH_BUTTON) {
			
		}
	}

	private static final int LOGOUT_CALLBACK_ID = 1;
	@Override
	public void confirmClicked(boolean result, int id) {
		if(id == LOGOUT_CALLBACK_ID) {
			if(result) {
				mc.addScheduledTask(new Runnable() {
					@Override
					public void run() {
						AuthenticationHandler.logout();
						mc.displayGuiScreen(new GuiMainMenu());
					}
				});
			} else {
				mc.displayGuiScreen(this);
			}
		}
	}

	public static GuiYesNo getYesNoGui(GuiYesNoCallback p_152129_0_, int p_152129_2_) {
		String s1 = I18n.format("Do you really want to log out?", new Object[0]);
		GuiYesNo guiyesno = new GuiYesNo(p_152129_0_, s1, "", "Logout", "Cancel", p_152129_2_);
		return guiyesno;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		this.drawCenteredString(fontRendererObj, "Replay Center", this.width/2, 8, Color.WHITE.getRGB());

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	public void showOnlineRecent() {
		mc.addScheduledTask(new Runnable() {
			@Override
			public void run() {
				try {
					FileInfo[] files = ReplayMod.apiClient.getRecentFiles();
					for(FileInfo i : files) {
						//TODO: Display the Files
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ApiException e) { //TODO: Error message
					e.printStackTrace();
				}
			}
		});
		

	}
}