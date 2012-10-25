/*
 * This file is part of Quelea, free projection software for churches.
 * 
 * 
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.quelea.windows.main.menus;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.javafx.dialog.Dialog;
import org.quelea.languages.LabelGrabber;
import org.quelea.utils.LoggerUtils;
import org.quelea.utils.QueleaProperties;
import org.quelea.windows.help.AboutDialog;

/**
 * Quelea's help menu.
 *
 * @author Michael
 */
public class HelpMenu extends Menu {

    private static final Logger LOGGER = LoggerUtils.getLogger();
    private final MenuItem queleaSite;
    private final MenuItem queleaFacebook;
    private final MenuItem queleaDiscuss;
    private final MenuItem queleaDownload;
    private final MenuItem updateCheck;
    private final MenuItem about;
    private AboutDialog aboutDialog;

    /**
     * Create a new help menu
     */
    public HelpMenu() {
        super(LabelGrabber.INSTANCE.getLabel("help.menu"));
        
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                aboutDialog = new AboutDialog();
            }
        });
        
        if(Desktop.isDesktopSupported()) {
            queleaSite = new MenuItem(LabelGrabber.INSTANCE.getLabel("help.menu.website"), new ImageView(new Image("file:icons/website.png", 16, 16, false, true)));
            queleaSite.setOnAction(new EventHandler<javafx.event.ActionEvent>() {

                @Override
                public void handle(javafx.event.ActionEvent t) {
                    try {
                        Desktop.getDesktop().browse(new URI(QueleaProperties.get().getWebsiteLocation()));
                    }
                    catch(URISyntaxException | IOException ex) {
                        LOGGER.log(Level.WARNING, "Couldn't launch Quelea website", ex);
                        showError();
                    }
                }
            });
            getItems().add(queleaSite);
            queleaFacebook = new MenuItem(LabelGrabber.INSTANCE.getLabel("help.menu.facebook"), new ImageView(new Image("file:icons/facebook.png", 16, 16, false, true)));
            queleaFacebook.setOnAction(new EventHandler<javafx.event.ActionEvent>() {

                @Override
                public void handle(javafx.event.ActionEvent t) {
                    try {
                        Desktop.getDesktop().browse(new URI(QueleaProperties.get().getFacebookPageLocation()));
                    }
                    catch(URISyntaxException | IOException ex) {
                        LOGGER.log(Level.WARNING, "Couldn't launch Quelea Facebook page", ex);
                        showError();
                    }
                }
            });
            getItems().add(queleaFacebook);
            queleaDiscuss = new MenuItem(LabelGrabber.INSTANCE.getLabel("help.menu.discussion"), new ImageView(new Image("file:icons/discuss.png", 16, 16, false, true)));
            queleaDiscuss.setOnAction(new EventHandler<javafx.event.ActionEvent>() {

                @Override
                public void handle(javafx.event.ActionEvent t) {
                    try {
                        Desktop.getDesktop().browse(new URI(QueleaProperties.get().getDiscussLocation()));
                    }
                    catch(URISyntaxException | IOException ex) {
                        LOGGER.log(Level.WARNING, "Couldn't launch Quelea discuss", ex);
                        showError();
                    }
                }
            });
            getItems().add(queleaDiscuss);
            queleaDownload = new MenuItem(LabelGrabber.INSTANCE.getLabel("help.menu.download"), new ImageView(new Image("file:icons/download.png", 16, 16, false, true)));
            queleaDownload.setOnAction(new EventHandler<javafx.event.ActionEvent>() {

                @Override
                public void handle(javafx.event.ActionEvent t) {
                    try {
                        Desktop.getDesktop().browse(new URI(QueleaProperties.get().getDownloadLocation()));
                    }
                    catch(URISyntaxException | IOException ex) {
                        LOGGER.log(Level.WARNING, "Couldn't launch Quelea download page", ex);
                        showError();
                    }
                }
            });
            getItems().add(queleaDownload);
        }
        else {
            queleaSite = null;
            queleaDiscuss = null;
            queleaDownload = null;
            queleaFacebook = null;
        }
        updateCheck = new MenuItem(LabelGrabber.INSTANCE.getLabel("help.menu.update"), new ImageView(new Image("file:icons/update.png", 16, 16, false, true)));
        updateCheck.setOnAction(new EventHandler<javafx.event.ActionEvent>() {

            @Override
            public void handle(javafx.event.ActionEvent t) {
//                new UpdateChecker(Application.get().getMainWindow()).checkUpdate(true, true, true);
            }
        });
        getItems().add(updateCheck);
        about = new MenuItem(LabelGrabber.INSTANCE.getLabel("help.menu.about"), new ImageView(new Image("file:icons/about.png", 16, 16, false, true)));
        about.setOnAction(new EventHandler<javafx.event.ActionEvent>() {

            @Override
            public void handle(javafx.event.ActionEvent t) {
                aboutDialog.show();
            }
        });
        getItems().add(about);
    }

    /**
     * Show a dialog saying we couldn't open the given location.
     *
     * @param location the location that failed to open.
     */
    private void showError() {
        Dialog.showError(LabelGrabber.INSTANCE.getLabel("help.menu.error.title"), LabelGrabber.INSTANCE.getLabel("help.menu.error.text"));
    }

    /**
     * Get the quelea discuss menu item.
     *
     * @return the quelea discuss menu item.
     */
    public MenuItem getQueleaDiscuss() {
        return queleaDiscuss;
    }

    /**
     * Get the quelea download menu item.
     *
     * @return the quelea download menu item.
     */
    public MenuItem getQueleaDownload() {
        return queleaDownload;
    }

    /**
     * Get the quelea website menu item.
     *
     * @return the quelea website menu item.
     */
    public MenuItem getQueleaSite() {
        return queleaSite;
    }

    /**
     * Get the about menu item.
     *
     * @return the about menu item.
     */
    public MenuItem getAbout() {
        return about;
    }

    /**
     * Get the "check update" menu item.
     *
     * @return the "check update" menu item.
     */
    public MenuItem getUpdateCheck() {
        return updateCheck;
    }
}
